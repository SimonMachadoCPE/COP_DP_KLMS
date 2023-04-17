package cla.command.undo;

import cla.command.Command;

/*
 * Le coeur de l’implémentation est l’utilisation de 2 stacks (FIFO), une pour l’undo et une pour le redo: 
 * intuitivement, on sent bien que:
	chaque exécution de commande rajoute un undo potentiel
	chaque undo rajoute un redo potentiel
	
	La classe AbstractConversation utilise 2 paramètres de type, C et S. 
	En effet, chacune des 3 implémentations concrètes de Conversation a besoin de connaître:

Le type de commandes exécutés (paramètre C), qui dérive de Command. 
Par exemple CompensationConversation ne peut accepter que des CompensableCommand.
Le type d’état conversationnel stocké (paramètre S). 
Dans la variante Memento, on ne mémorise pas les commandes 
mais plutôt des snapshots de l’état du système (dans l’interface Conversation il n’y a toujours que le paramètre C, car le type d’état conversationnel stocké est un détail d’implémentation du point de vue de l’utilisateur de l’API Conversation)
 */

/**
 * @param <C> Type of executed commands
 * @param <S> Type of stored state (commands or mementos) 
 */
public abstract class AbstractConversation<C extends Command, S> implements Conversation<C> {

	protected final Stack<S> undos, redos;
	
	public AbstractConversation() {
		this.undos = new Stack<S>();
		this.redos = new Stack<S>();
	}

	@Override public String toString() {
		return String.format(
				"%s{undos:%s, redos:%s}", 
				getClass().getSimpleName(), 
				undos, 
				redos
		);
	}
}
