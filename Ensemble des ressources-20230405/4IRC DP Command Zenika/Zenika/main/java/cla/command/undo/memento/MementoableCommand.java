package cla.command.undo.memento;

import cla.command.Command;

/*
 * Chaque type de commande peut modifier un type d’état différent, pour lequel la façon de capturer un Memento sera différente. 
 * Par exemple la capture d’un état persisté en BD sera très différente de la capture de l’état d’un logiciel de dessin. 
 * Le plus simple est donc de spécialiser Command en MementoableCommand, pour que les implémentations de MementoableCommand 
 * réalisent à la fois la modification et la capture de cet état, suivant les spécificités de ce dernier
 */

public interface MementoableCommand extends Command {
	
	Memento takeSnapshot();
	
}
