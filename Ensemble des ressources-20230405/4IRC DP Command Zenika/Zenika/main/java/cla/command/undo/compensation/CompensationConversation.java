package cla.command.undo.compensation;

import cla.command.undo.AbstractConversation;

// Invoker pour undo/redo avec des commandes de compensation

/*
 * 	Dans la variation Compensation, les deux type parameters sont identiques 
	(S=C=CompensableCommand dans AbstractConversation<C,S>), puisque l��tat m�moris� est constitu� de commandes. 
	En effet, la compensation utilise les commandes d�j� ex�cut�es pour les compenser (undo) ou les r�ex�cuter (redo).
	
	A la fin d�exec(), on vide la stack de redo: 
	les commandes undo�es qui pr�c�dent l�ex�cution d�une commande sont compl�tement effac�es de la m�moire. 
	La raison de ce choix est de se conformer aux conventions de toutes les IHM offrant un undo/redo 
	(principe de moindre surprise).
	Il est �videmment n�cessaire dans cette variation que les commandes utilis�es soient compensable; 
	pour la commande de test TypeString, cette fonctionnalit� se traduit par le fait que TypeString 
	impl�mente CompensableCommand et non pas simplement Command:
 */
public class CompensationConversation extends AbstractConversation<CompensableCommand, CompensableCommand> {

	@Override public void exec(CompensableCommand todo) {
		todo.execute();
		undos.push(todo);
		redos.clear();
	}

	@Override public void undo() {
		CompensableCommand latestCmd = undos.pop();
		if(latestCmd==null) return; 
		latestCmd.compensate();
		redos.push(latestCmd);
	}

	@Override public void redo() {
		CompensableCommand latestCmd = redos.pop();
		if(latestCmd==null) return; 
		latestCmd.execute();
		undos.push(latestCmd);
	}
	
}
