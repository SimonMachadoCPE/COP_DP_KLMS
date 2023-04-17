package cla.command.undo.replay;

import cla.command.Command;
import cla.command.undo.AbstractConversation;

// Invoker pour undo/redo par Replay

/*
 * Il est parfois difficile de trouver une action compensatrice. 
 * Dans ce cas si on a invoqué N commandes, une implémentation alternative de l’undo 
 * est de réinitialiser l’état à zéro, puis de rejouer les N-1 premières commandes. 
 * Le redo consiste ensuite à rejouer la Nième commande.
 * Contrairement à la variation Compensation, puisque la variation Replay se repose 
 * uniquement sur l’exécution des commandes dans leur sens normal, 
 * ReplayConversation n’a pas besoin d’un type de commande particulier de commandes: 
 * S=C=Command dans AbstractConversation<C,S>). 
 * Par contre elle a besoin d’une instance de commande capable de resetter l’état à zéro
 */
public class ReplayConversation extends AbstractConversation<Command, Command> {

	private final Command reset;
	
	public ReplayConversation(Command reset) {
		this.reset = reset;
	}

	@Override public void exec(Command todo) {
		todo.execute();
		undos.push(todo);
		redos.clear();
	}

	@Override public void undo() {
		Command latestCmd = undos.pop() ;
		if(latestCmd==null) return;
        redos.push(latestCmd);
        reset.execute();
        undos.forEachFifo(Command::execute);
	}

	@Override public void redo() {
		Command latestCmd = redos.pop() ;
		if(latestCmd==null) return;
		latestCmd.execute();
        undos.push(latestCmd); 
	}
	
}
