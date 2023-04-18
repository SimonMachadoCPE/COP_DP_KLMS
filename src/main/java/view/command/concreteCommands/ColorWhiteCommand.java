package view.command.concreteCommands;

import javafx.scene.paint.Color;
import view.GuiConfig;
import view.command.commands.MementoableCommand;
import view.command.memento.Memento;

public class ColorWhiteCommand implements MementoableCommand {

    private Color color;
    private Color latestColor;

    public ColorWhiteCommand(Color color) {
        this.color = color;
    }

    @Override
    public void execute() {
        this.latestColor = GuiConfig.whiteSquareColor.get();
        GuiConfig.whiteSquareColor.set(this.color);
    }

    @Override
    public void compensate() {
        GuiConfig.whiteSquareColor.set(this.latestColor);
    }

    @Override
    public Memento takeSnapshot() {
        return GuiConfig.saveIntoMemento();
    }

}
