package view.command.concreteCommands;

import javafx.scene.paint.Color;
import view.GuiConfig;
import view.command.commands.MementoableCommand;

public class ColorBlackCommand implements MementoableCommand {

    private Color color;
    private Color latestColor;

    public ColorBlackCommand(Color color) {
        this.color = color;
    }

    @Override
    public void execute() {
        this.latestColor = GuiConfig.blackSquareColor.get();
        GuiConfig.blackSquareColor.set(this.color);
    }

    @Override
    public void compensate() {
        GuiConfig.blackSquareColor.set(this.latestColor);
    }

    @Override
    public Memento takeSnapshot() {
        return GuiConfig.saveIntoMemento();
    }

}

