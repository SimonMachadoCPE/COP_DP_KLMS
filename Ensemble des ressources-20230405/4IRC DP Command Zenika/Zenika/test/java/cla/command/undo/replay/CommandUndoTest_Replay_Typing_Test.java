package cla.command.undo.replay;

import cla.command.Command;
import cla.command.undo.CommandUndoTest_Typing;
import cla.command.undo.Conversation;
import cla.domain.typing.TypeString;

/*
 * Le reset de l��tat utilise une commande sp�cifique au type d��tat modifi� par les commandes. 
 * Dans le cas de la commande Typing (saisie) qui modifie un Display (affichage), 
 * on peut simplement effacer compl�tement l�affichage
 * Le lambda qui est pass� au constructeur de ReplayConversation correspond au champ ReplayConversation.reset. 
 * Il vide l�affichage.
 */
public class CommandUndoTest_Replay_Typing_Test extends CommandUndoTest_Typing<Command> {

	@Override protected Conversation<Command> newConversation() {
		return new ReplayConversation(()->{
			display.clear();
		});
	}

	@Override protected Command typeString(String stringToType) {
		return new TypeString(display, stringToType);
	}
	
}
