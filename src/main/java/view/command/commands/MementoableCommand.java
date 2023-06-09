package view.command.commands;

import view.command.memento.Memento;

public interface MementoableCommand extends Command {
    void compensate();

    Memento takeSnapshot();
}
