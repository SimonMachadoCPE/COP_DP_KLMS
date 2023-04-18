package view.command.invokers.concreteInvoker;

import java.util.Deque;
import java.util.LinkedList;

import view.command.commands.Command;
import view.command.commands.MementoableCommand;
import view.command.invokers.Invoker;
import view.command.memento.Memento;


/**
 * @author francoise.perrin
 * Inspiration : https://zenika.developpez.com/tutoriels/java/patterns-command/
 *
 * Cet Invoker est utilisé de le cadre de la mise en oeuvre du DP Command
 * mais il s'appuie sur celle du DP Memento : plutôt que de stocker les commandes
 * comme dans les variations par Compensation ou par Replay, il s'agit de stocker
 * l'état des objets modifiés par les commandes, avant et après l'exécution des commandes
 * Ainsi en cas de "undo" ou "redo", l'état peut être restauré
 *
 * Cet Invoker est donc également le Caretaker des Memento (il les mémorise).
 * l'Originator (celui qui crée les Memento) est le Receiver du DP Command
 * (dans notre contexte GuiConfig) mais comme l'Invoker est totalement découplé du Receiver
 * l'invocation de la méthode de création se fait à travers la Commande
 *
 * Petite subtilité : pour implémenter le "redo", on a besoin de capturer l'état avant ET
 * après exécution de la commande, d'où l'existence d'un objet OldAndNewSnapshots
 *
 *
 * @param <C>
 */
public class MementoInvoker < C extends Command > implements  Invoker< C >{

    private class OldAndNewSnapshots {

        final Memento oldMemento, newMemento;

        public OldAndNewSnapshots(Memento oldMemento, Memento newMemento) {
            this.oldMemento = oldMemento;
            this.newMemento = newMemento;
        }
        @Override
        public String toString() {
            return String.format("%s{oldMemento:%s, newMemento:%s}", OldAndNewSnapshots.class.getSimpleName(), oldMemento, newMemento);
        }
    }

    private final Deque< OldAndNewSnapshots > undos = new LinkedList< OldAndNewSnapshots >();
    private final Deque< OldAndNewSnapshots > redos = new LinkedList< OldAndNewSnapshots >();

    @Override
    public void exec(C command) {
        Memento beforeExecMemento = ((MementoableCommand) command).takeSnapshot();
        command.execute();
        Memento afterExecMemento = ((MementoableCommand) command).takeSnapshot();
        undos.addLast(new OldAndNewSnapshots(beforeExecMemento, afterExecMemento));
        redos.clear();
    }

    @Override
    public void undo() {
        OldAndNewSnapshots oldAndNewSnapshots = undos.pollLast();
        if(oldAndNewSnapshots==null) return;
        Memento oldMemento = oldAndNewSnapshots.oldMemento;
        oldMemento.restore();
        redos.addLast(oldAndNewSnapshots);
    }

    @Override
    public void redo() {
        OldAndNewSnapshots oldAndNewSnapshots = redos.pollLast();
        if(oldAndNewSnapshots==null) return;
        Memento newMemento = oldAndNewSnapshots.newMemento;
        newMemento.restore();
        undos.addLast(oldAndNewSnapshots);
    }

}

