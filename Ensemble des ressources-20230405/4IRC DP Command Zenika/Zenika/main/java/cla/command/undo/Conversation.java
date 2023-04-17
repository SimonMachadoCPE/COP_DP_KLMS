package cla.command.undo;

import cla.command.Command;

/*
 * La conversation est identifi�e � l�Invocator (du pattern Command du GOF), 
 * qui a le double r�le de m�moriser et de d�clencher les commandes. 
 * Ici, c�est le m�me Client qui instancie les commandes concr�tes et 
 * qui demande � l�Invocator de les d�clencher. 
 * Notons que l�interface Conversation est g�n�rique: elle porte un type parameter C extends Command. 
 * Le but est d�avoir une seule interface Conversation commune aux 3 variations d�undo, 
 * tout en permettant l�utilisation par ses impl�mentations des 3 d�clinaisons de Command que nous allons pr�senter.
 */
public interface Conversation<C extends Command> {

	void exec(C cmd);

	void undo();

	void redo();

}
