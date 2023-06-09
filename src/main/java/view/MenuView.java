package view;


import java.util.HashMap;
import java.util.Map;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
import view.command.commands.Command;
import view.command.commands.MementoableCommand;
import view.command.concreteCommands.ColorBlackCommand;
import view.command.concreteCommands.ColorWhiteCommand;
import view.command.concreteCommands.StyleCommand;
import view.command.invokers.Invoker;
import view.command.invokers.concreteInvoker.MementoInvoker;


/**
 * @author francoise.perrin
 * Cette classe est le menu du jeu d'échec
 */
public class MenuView extends MenuBar {

	private Invoker<Command> invoker ;

	public MenuView () {
		super();
		this.getMenus().add(newMenuStyle());
		this.getMenus().add(newMenuColor());
		this.getMenus().add(newMenuEdit());

		invoker = new MementoInvoker<Command>();
	}

	private Menu newMenuColor () {

		Menu menu = new Menu("Couleur d'affichage");
		MenuItem color1 = new MenuItem("Couleur cases noires");
		MenuItem color2 = new MenuItem("Couleur cases blanches");

		color1.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle (ActionEvent event) {
				Dialog<Void> colorDialog = new Dialog<>();
				colorDialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

				ColorPicker colorPicker = new ColorPicker(GuiConfig.whiteSquareColor.get());
				colorPicker.setOnAction(colorEvent -> {
					/* sans pattern Command */
					//GuiConfig.whiteSquareColor.set(colorPicker.getValue());

					/* AVEC pattern command */
					Command command = new ColorBlackCommand(colorPicker.getValue());
					invoker.exec((MementoableCommand) command);
					colorDialog.close();
				});

				colorDialog.getDialogPane().setContent(colorPicker);
				colorDialog.show();
			}
		});

		color2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle (ActionEvent event) {
				Dialog<Void> colorDialog = new Dialog<>();
				colorDialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

				ColorPicker colorPicker = new ColorPicker(GuiConfig.blackSquareColor.get());
				colorPicker.setOnAction(colorEvent -> {
					/* sans pattern Command */
					//GuiConfig.blackSquareColor.set(colorPicker.getValue());

					/* AVEC pattern command */
					Command command = new ColorBlackCommand(colorPicker.getValue());
					invoker.exec((MementoableCommand) command);
					colorDialog.close();
				});

				colorDialog.getDialogPane().setContent(colorPicker);
				colorDialog.show();
			}
		});

		menu.getItems().addAll(color1, color2);
		return menu;
	}

	private Menu newMenuStyle () {

		Menu menu = new Menu("Style d'affichage");
		RadioMenuItem style1 = new RadioMenuItem("Dégradé");
		RadioMenuItem style2 = new RadioMenuItem("Uni");
		ToggleGroup btnGroup = new ToggleGroup();

		style1.setToggleGroup(btnGroup);
		style2.setToggleGroup(btnGroup);

		Map<MenuItem, PaintStyle> btnMap = new HashMap<>();
		btnMap.put(style1, PaintStyle.GRADIENT);
		btnMap.put(style2, PaintStyle.SOLID);

		Object style = GuiConfig.paintStyle.get();
		btnMap.forEach((menuItem, paintstyle) -> {
			if (paintstyle.equals(style)) {
				((RadioMenuItem) menuItem).setSelected(true);
			}
		});


		style1.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle (ActionEvent event) {
				 /* sans pattern Command */
				//GuiConfig.paintStyle.set(PaintStyle.GRADIENT);

				/* AVEC pattern command */
				Command command = new StyleCommand(PaintStyle.GRADIENT);
				invoker.exec((MementoableCommand) command);

			}
		});


		style2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle (ActionEvent event) {
				 /* sans pattern Command */
				//GuiConfig.paintStyle.set(PaintStyle.SOLID);
				/* AVEC pattern command */
				Command command = new StyleCommand(PaintStyle.SOLID);
				invoker.exec((MementoableCommand) command);

			}
		});


		menu.getItems().addAll(style1, style2);
		return menu;
	}

	private Menu newMenuEdit () {

		Menu menu = new Menu("Gestion Commandes");
		MenuItem undo = new MenuItem("undo");
		MenuItem redo = new MenuItem("redo");

		undo.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle (ActionEvent event) {
				System.out.println("UNDO");
				invoker.undo();
			}
		});

		redo.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle (ActionEvent event) {
				System.out.println("REDO");
				invoker.redo();
			}
		});

		menu.getItems().addAll(undo, redo);
		return menu;
	}
}
