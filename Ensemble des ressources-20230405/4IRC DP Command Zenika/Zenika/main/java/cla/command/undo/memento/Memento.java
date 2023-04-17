package cla.command.undo.memento;

/*
 * Plutôt que de mémoriser les commandes, on peut aussi implémenter l’undo en mémorisant leur effet, plus précisément l’état du système avant et après chaque exécution.
Ceci évoque un autre pattern du GOF, le Memento
L‘Originator instancie un Memento en passant à son constructeur un snapshot de l’état du système. 
Le CareTaker est chargé de connaître le Memento, afin de pouvoir demander, 
ultérieurement, la restauration de l’état capturé.
Le Memento doit être capable de restaurer un état capturé antérieurement, d’où l’exigence d’immutabilité. 
On ne veut pas voir les changements de l’état du système postérieurs au snapshot, il faut donc utiliser des techniques comme la copie défensive.
 */


/**
 * Implementations must be immutable (the memento must capture a snapshot)
 */
@FunctionalInterface
public interface Memento {
	void restore();
}
