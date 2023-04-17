package cla.command.undo.memento;

import cla.command.Command;

/*
 * Chaque type de commande peut modifier un type d��tat diff�rent, pour lequel la fa�on de capturer un Memento sera diff�rente. 
 * Par exemple la capture d�un �tat persist� en BD sera tr�s diff�rente de la capture de l��tat d�un logiciel de dessin. 
 * Le plus simple est donc de sp�cialiser Command en MementoableCommand, pour que les impl�mentations de MementoableCommand 
 * r�alisent � la fois la modification et la capture de cet �tat, suivant les sp�cificit�s de ce dernier
 */

public interface MementoableCommand extends Command {
	
	Memento takeSnapshot();
	
}
