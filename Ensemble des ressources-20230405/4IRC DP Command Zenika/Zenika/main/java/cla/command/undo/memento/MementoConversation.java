package cla.command.undo.memento;

import cla.command.undo.AbstractConversation;


/*
 * Dans la variation Memento, les deux type parameters ne sont pas identiques 
 * (C=MementoableCommand, S=BeforeAfter dans AbstractConversation<C,S>), 
 * puisque l��tat m�moris� est constitu� de captures successives de l��tat et non de commandes. 
 * Petite subtilit�: pour impl�menter le redo, on a besoin de capturer l��tat avant ET 
 * apr�s ex�cution de la commande
 * Dans cette variation, la commande est donc l�Originator du pattern Memento du GOF (l�acteur qui d�clenche le snapshot), 
 * et la conversation en est le Caretaker (l�acteur qui m�morise les mementos).
 */
public class MementoConversation extends AbstractConversation<MementoableCommand, BeforeAfter> {

	@Override public void exec(MementoableCommand todo) {
		Memento before = todo.takeSnapshot();
		todo.execute();
		Memento after = todo.takeSnapshot();
		
		undos.push(new BeforeAfter(before, after));
		redos.clear();
	}

	@Override public void undo() {
		BeforeAfter latestMemento = undos.pop();
		if(latestMemento==null) return;
		Memento latestBefore = latestMemento.before;
		latestBefore.restore();
		redos.push(latestMemento);
	}

	@Override public void redo() {
		BeforeAfter latestMemento = redos.pop();
		if(latestMemento==null) return; 
		Memento latestAfter = latestMemento.after;
		latestAfter.restore();
		undos.push(latestMemento);
	}
	
}
