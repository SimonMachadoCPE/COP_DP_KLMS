package view.command.concreteCommands;

import view.GuiConfig;
import view.PaintStyle;
import view.command.commands.MementoableCommand;

public class StyleCommand implements MementoableCommand {

    private PaintStyle paintStyle;
    private PaintStyle latestStyle;

    public StyleCommand(PaintStyle paintStyle) {
        this.paintStyle = paintStyle;
    }

    @Override
    public void execute() {
        this.latestStyle = GuiConfig.paintStyle.get();
        GuiConfig.paintStyle.set(this.paintStyle);
    }

    @Override
    public void compensate() {
        GuiConfig.paintStyle.set(this.latestStyle);
    }

    @Override
    public Memento takeSnapshot() {
        return GuiConfig.saveIntoMemento();
    }

}
