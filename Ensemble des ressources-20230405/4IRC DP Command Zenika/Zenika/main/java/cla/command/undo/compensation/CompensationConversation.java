package cla.command.undo.compensation;

import cla.command.undo.AbstractConversation;

// Invoker pour undo/redo avec des commandes de compensation

/*
 * 	Dans la variation Compensation, les deux type parameters sont identiques 
	(S=C=CompensableCommand dans AbstractConversation<C,S>), puisque l’état mémorisé est constitué de commandes. 
	En effet, la compensation utilise les commandes déjà exécutées pour les compenser (undo) ou les réexécuter (redo).
	
	A la fin d’exec(), on vide la stack de redo: 
	les commandes undoées qui précèdent l’exécution d’une commande sont complètement effacées de la mémoire. 
	La raison de ce choix est de se conformer aux conventions de toutes les IHM offrant un undo/redo 
	(principe de moindre surprise).
	Il est évidemment nécessaire dans cette variation que les commandes utilisées soient compensable; 
	pour la commande de test TypeString, cette fonctionnalité se traduit par le fait que TypeString 
	implémente CompensableCommand et non pas simplement Command:
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
