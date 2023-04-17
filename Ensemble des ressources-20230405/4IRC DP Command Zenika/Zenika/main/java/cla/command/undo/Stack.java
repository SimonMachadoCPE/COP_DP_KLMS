package cla.command.undo;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.function.Consumer;

/*
 * Stack délègue à une Deque (double-ended queue) l’implémentation de la stack; 
 * en effet il existe une classe java.util.Stack mais elle est obsolète comme Vector. 
 * Déléguer à LinkedList plutôt que l’étendre présente 2 avantages:

Evite la pollution d’API: Stack n’expose que les méthodes utiles pour notre framework
Liberté de nommage: dans Deque, push() et pop() n’ont pas la sémantique souhaitée (pop(): 
exception si vide) donc on utilise pollLast()/addLast(). 
Mais push et pop sont plus compréhensibles donc ce sont ces noms-là qu’on expose à notre framework
Enfin, précisons que forEachFifo() est utilisé par la variation Replay. 
FIFO signifie first-in first-out donc un parcours dans l’ordre d’insertion.
 */
public class Stack<T> {

	private final Deque<T> stack = new ArrayDeque<>();
	
	/**
	 * @return null if stack is empty
	 */
	public T pop() {
		return stack.pollLast(); //Not using pop since it throws NoSuchElementException if the deque is empty
	}
	
	public void push(T cmd) {
		stack.addLast(cmd);
	}

	public void clear() {
		stack.clear();
	}
	
	public void forEachFifo(Consumer<? super T> action) {
		stack.stream().forEachOrdered(action);
	}

	@Override public String toString() {
		return stack.toString();
	}
}
