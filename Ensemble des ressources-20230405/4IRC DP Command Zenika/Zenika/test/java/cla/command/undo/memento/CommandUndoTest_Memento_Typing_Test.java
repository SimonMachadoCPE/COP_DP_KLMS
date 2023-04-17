package cla.command.undo.memento;

import cla.command.undo.CommandUndoTest_Typing;
import cla.command.undo.Conversation;
import cla.domain.typing.TypeString;


/*
 * Dans cette variation, la commande est donc l‘Originator du pattern Memento du GOF 
 * (l’acteur qui déclenche le snapshot), et 
 * la conversation en est le Caretaker (l’acteur qui mémorise les mementos).
 */
public class CommandUndoTest_Memento_Typing_Test extends CommandUndoTest_Typing<MementoableCommand> {

	@Override protected Conversation<MementoableCommand> newConversation() {
		return new MementoConversation();
	}

	@Override protected MementoableCommand typeString(String stringToType) {
		return new TypeString(display, stringToType);
	}

}
