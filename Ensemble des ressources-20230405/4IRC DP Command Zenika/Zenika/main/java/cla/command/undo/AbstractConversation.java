package cla.command.undo;

import cla.command.Command;

/*
 * Le coeur de l�impl�mentation est l�utilisation de 2 stacks (FIFO), une pour l�undo et une pour le redo: 
 * intuitivement, on sent bien que:
	chaque ex�cution de commande rajoute un undo potentiel
	chaque undo rajoute un redo potentiel
	
	La classe AbstractConversation utilise 2 param�tres de type, C et S. 
	En effet, chacune des 3 impl�mentations concr�tes de Conversation a besoin de conna�tre:

Le type de commandes ex�cut�s (param�tre C), qui d�rive de Command. 
Par exemple CompensationConversation ne peut accepter que des CompensableCommand.
Le type d��tat conversationnel stock� (param�tre S). 
Dans la variante Memento, on ne m�morise pas les commandes 
mais plut�t des snapshots de l��tat du syst�me (dans l�interface Conversation il n�y a toujours que le param�tre C, car le type d��tat conversationnel stock� est un d�tail d�impl�mentation du point de vue de l�utilisateur de l�API Conversation)
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
