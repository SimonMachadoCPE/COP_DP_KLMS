package cla.command.undo;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.function.Consumer;

/*
 * Stack d�l�gue � une Deque (double-ended queue) l�impl�mentation de la stack; 
 * en effet il existe une classe java.util.Stack mais elle est obsol�te comme Vector. 
 * D�l�guer � LinkedList plut�t que l��tendre pr�sente 2 avantages:

Evite la pollution d�API: Stack n�expose que les m�thodes utiles pour notre framework
Libert� de nommage: dans Deque, push() et pop() n�ont pas la s�mantique souhait�e (pop(): 
exception si vide) donc on utilise pollLast()/addLast(). 
Mais push et pop sont plus compr�hensibles donc ce sont ces noms-l� qu�on expose � notre framework
Enfin, pr�cisons que forEachFifo() est utilis� par la variation Replay. 
FIFO signifie first-in first-out donc un parcours dans l�ordre d�insertion.
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
