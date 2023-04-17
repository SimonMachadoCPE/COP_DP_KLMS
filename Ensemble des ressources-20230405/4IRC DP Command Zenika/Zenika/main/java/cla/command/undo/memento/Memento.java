package cla.command.undo.memento;

/*
 * Plut�t que de m�moriser les commandes, on peut aussi impl�menter l�undo en m�morisant leur effet, plus pr�cis�ment l��tat du syst�me avant et apr�s chaque ex�cution.
Ceci �voque un autre pattern du GOF, le Memento
L�Originator instancie un Memento en passant � son constructeur un snapshot de l��tat du syst�me. 
Le CareTaker est charg� de conna�tre le Memento, afin de pouvoir demander, 
ult�rieurement, la restauration de l��tat captur�.
Le Memento doit �tre capable de restaurer un �tat captur� ant�rieurement, d�o� l�exigence d�immutabilit�. 
On ne veut pas voir les changements de l��tat du syst�me post�rieurs au snapshot, il faut donc utiliser des techniques comme la copie d�fensive.
 */


/**
 * Implementations must be immutable (the memento must capture a snapshot)
 */
@FunctionalInterface
public interface Memento {
	void restore();
}
