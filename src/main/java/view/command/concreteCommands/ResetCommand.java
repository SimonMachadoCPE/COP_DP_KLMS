package view.command.concreteCommands;

import view.GuiConfig;
import view.command.commands.Command;

public class ResetCommand implements Command {

    @Override
    public void execute() {

        GuiConfig.setInitState();
    }

}
