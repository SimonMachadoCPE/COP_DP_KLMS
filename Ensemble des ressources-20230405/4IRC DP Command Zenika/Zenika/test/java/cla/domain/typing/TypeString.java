package cla.domain.typing;

import java.util.List;

import cla.command.undo.compensation.CompensableCommand;
import cla.command.undo.memento.Memento;
import cla.command.undo.memento.MementoableCommand;

// Cette commande représente la saisie d'un String dans un Display (le receiver)

public class TypeString implements CompensableCommand, MementoableCommand {

	private final Display display;
	private final String stringToType;
	
	public TypeString(Display display, String stringToType) {
		this.display = display;
		this.stringToType = stringToType;
	}

	@Override public void execute() {
		display.append(stringToType);
	}

	@Override public void compensate() {
		display.unappend();
	}

	@Override public Memento takeSnapshot() {
		final List<String> snapshot =  display.getState();//snapshot is a defensive copy
		return () -> display.setState(snapshot);
	}
	
	@Override public String toString() {
		return String.format("%s{stringToType:%s}", TypeString.class.getSimpleName(), stringToType);
	}  
	
}