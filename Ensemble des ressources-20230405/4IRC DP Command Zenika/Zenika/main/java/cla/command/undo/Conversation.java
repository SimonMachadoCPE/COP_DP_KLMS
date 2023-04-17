package cla.command.undo;

import cla.command.Command;

/*
 * La conversation est identifiée à l’Invocator (du pattern Command du GOF), 
 * qui a le double rôle de mémoriser et de déclencher les commandes. 
 * Ici, c’est le même Client qui instancie les commandes concrètes et 
 * qui demande à l’Invocator de les déclencher. 
 * Notons que l’interface Conversation est générique: elle porte un type parameter C extends Command. 
 * Le but est d’avoir une seule interface Conversation commune aux 3 variations d’undo, 
 * tout en permettant l’utilisation par ses implémentations des 3 déclinaisons de Command que nous allons présenter.
 */
public interface Conversation<C extends Command> {

	void exec(C cmd);

	void undo();

	void redo();

}
