package cla.command.undo;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import cla.command.BaseCommandTest;
import cla.command.Command;

/*
 * On propose ici trois variations du pattern, plus ou moins adapt�es selon le type de commande:

 Compensation: pour chaque commande, on d�finit une commande inverse appel�e compensation
 Memento: lors de chaque ex�cution de commande, on m�morise l��tat du syst�me; 
 annuler une commande consiste alors � revenir � l��tat pr�c�dent
 Replay: pour annuler la derni�re commande, on commence par repositionner le syst�me dans l��tat initial (reset), 
 puis on r�-ex�cute toutes les commandes sauf la derni�re
 
 Pour s�assurer que les 3 variations sont fonctionnellement �quivalentes, on �crit les tests dans cette classe abstraite.  
 Les classes concr�tes sp�cifiques aux 3 variations ne font qu�instancier un type diff�rent de Conversation 
 (� un d�tail technique pr�s,  propre aux g�n�riques java) : CommandUndoTest_Compensation_Typing_Test, 
 CommandUndoTest_Replay_Typing_Test, CommandUndoTest_Memento_Typing_Test
 
 Les tests commencent par les cas triviaux et vont jusqu�� une conversation complexe
 */

public abstract class CommandUndoTest_Typing<C extends Command> extends BaseCommandTest {

	protected abstract Conversation<C> newConversation();
	protected abstract C typeString(String stringToType);
	
	/**
	 * Basic undo
	 * a    --> "a" 
	 * undo --> ""
	 */
	@Test public void basicUndo() {
		Conversation<C> commands = newConversation();
		
		commands.exec(typeString("a"));
		assertEquals("a", display.displayed());
		
		commands.undo();
		assertEquals("", display.displayed());
	}
	
	/**
	 * Undo is noop when there were no execs
	 * undo --> ""
	 */
	@Test public void basicNoopUndo() {
		Conversation<C> commands = newConversation();
		
		commands.undo();
		assertEquals("", display.displayed());
	}
	
	/**
	 * Undo is noop when there were no execs, even when undo is called several times 
	 * undo --> ""
	 * undo --> ""
	 */
	@Test public void basicNoopUndoTwice() {
		Conversation<C> commands = newConversation();
		
		commands.undo();
		assertEquals("", display.displayed());
		
		commands.undo();
		assertEquals("", display.displayed());
	}
	
	/**
	 * Undo is noop when there is nothing more to undo
	 * a    --> "a" 
	 * undo --> ""
	 * undo --> ""
	 */
	@Test public void noopUndo() {
		Conversation<C> commands = newConversation();
		
		commands.exec(typeString("a"));
		assertEquals("a", display.displayed());
		
		commands.undo();
		assertEquals("", display.displayed());
		
		commands.undo();
		assertEquals("", display.displayed());
	}
	
	/**
	 * Basic redo
	 * a    --> "a" 
	 * undo --> ""
	 * redo --> "a"
	 */
	@Test public void basicRedo() {
		Conversation<C> commands = newConversation();
		
		commands.exec(typeString("a"));
		assertEquals("a", display.displayed());
		
		commands.undo();
		assertEquals("", display.displayed());
		
		commands.redo();
		assertEquals("a", display.displayed());
	}
	
	/**
	 * Redo is noop when there were no undos
	 * redo --> ""
	 */
	@Test public void basicNoopRedo() {
		Conversation<C> commands = newConversation();
		
		commands.redo();
		assertEquals("", display.displayed());
	}
	
	/**
	 * Redo is noop when there were no undos, even when redo is called several times 
	 * redo --> ""
	 * redo --> ""
	 */
	@Test public void basicNoopRedoTwice() {
		Conversation<C> commands = newConversation();
		
		commands.redo();
		assertEquals("", display.displayed());
		
		commands.redo();
		assertEquals("", display.displayed());
	}
	
	/**
	 * Slightly more complex interaction between undo and redo
	 * a    --> "a" 
	 * undo --> ""
	 * redo --> "a"
	 * undo --> ""
	 */
	@Test public void exec_undo_redo_undo() {
		Conversation<C> commands = newConversation();
		
		commands.exec(typeString("a"));
		assertEquals("a", display.displayed());
		
		commands.undo();
		assertEquals("", display.displayed());
		
		commands.redo();
		assertEquals("a", display.displayed());
		
		commands.undo();
		assertEquals("", display.displayed());
	}
	
	/**
	 * a    --> "a"
	 * b    --> "ab" 
	 * undo --> "a"
	 * undo --> ""
	 */
	@Test public void typeA_typeB_undo_undo() {
		Conversation<C> commands = newConversation();
		
		commands.exec(typeString("a"));
		assertEquals("a", display.displayed());
		
		commands.exec(typeString("b"));
		assertEquals("ab", display.displayed());
		
		commands.undo();
		assertEquals("a", display.displayed());
		
		commands.undo();
		assertEquals("", display.displayed());
	}
	
	/**
	 * a    --> "a"
	 * b    --> "ab" 
	 * undo --> "a"
	 * redo --> "ab"
	 */
	@Test public void exec_exec_undo_redo() {
		Conversation<C> commands = newConversation();
		
		commands.exec(typeString("a"));
		assertEquals("a", display.displayed());
		
		commands.exec(typeString("b"));
		assertEquals("ab", display.displayed());
		
		commands.undo();
		assertEquals("a", display.displayed());
		
		commands.redo();
		assertEquals("ab", display.displayed());
	}
	
	/**
	 * a    --> "a"
	 * b    --> "ab" 
	 * undo --> "a"
	 * undo --> ""
	 * redo --> "a"
	 * redo --> "ab"
	 */
	@Test public void exec_exec_undo_undo_redo_redo() {
		Conversation<C> commands = newConversation();
		
		commands.exec(typeString("a"));
		assertEquals("a", display.displayed());
		
		commands.exec(typeString("b"));
		assertEquals("ab", display.displayed());
		
		commands.undo();
		assertEquals("a", display.displayed());
		
		commands.undo();
		assertEquals("", display.displayed());
		
		commands.redo();
		assertEquals("a", display.displayed());
		
		commands.redo();
		assertEquals("ab", display.displayed());
	}
	
	@Test public void complexConversation() {
		Conversation<C> commands = newConversation();
		
		commands.exec(typeString("a"));
		assertEquals("a", display.displayed());
		
		commands.undo();
		assertEquals("", display.displayed());
		
		commands.exec(typeString("b"));
		assertEquals("b", display.displayed());
		
		commands.exec(typeString("c"));
		assertEquals("bc", display.displayed());
		
		commands.exec(typeString("d"));
		assertEquals("bcd", display.displayed());
		
		commands.undo();
		assertEquals("bc", display.displayed());
		
		commands.redo();
		assertEquals("bcd", display.displayed());
		
		commands.undo();
		assertEquals("bc", display.displayed());
		
		commands.undo();
		assertEquals("b", display.displayed());
		
		commands.exec(typeString("e"));
		assertEquals("be", display.displayed());
		
		commands.exec(typeString("f"));
		assertEquals("bef", display.displayed());
		
		commands.undo();
		assertEquals("be", display.displayed());
		
		commands.undo();
		assertEquals("b", display.displayed());
		
		commands.undo();
		assertEquals("", display.displayed());
		
		commands.redo();
		assertEquals("b", display.displayed());
		
		commands.redo();
		assertEquals("be", display.displayed());
		
		commands.redo();
		assertEquals("bef", display.displayed());
		
		commands.redo();
		assertEquals("bef", display.displayed());
		
		commands.undo();
		assertEquals("be", display.displayed());
		
		commands.undo();
		assertEquals("b", display.displayed());
		
		commands.undo();
		assertEquals("", display.displayed());
	}
	
}
