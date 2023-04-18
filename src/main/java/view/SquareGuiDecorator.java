package view;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import shared.GUICoord;

public class SquareGuiDecorator extends BorderPane implements ChessSquareGui, ChangeListener {

    private SquareGui squareGui;
    public SquareGuiDecorator(SquareGui squareGui) {
        this.squareGui = squareGui;
        this.setCenter((Node)this.squareGui);

        //Ajout des listeners sur les attributs pour détecter les changements
        GuiConfig.paintStyle.addListener(this);
        GuiConfig.blackSquareColor.addListener(this);
        GuiConfig.whiteSquareColor.addListener(this);
    }

    @Override
    public GUICoord getCoord() {
        return squareGui.getCoord();
    }

    @Override
    public void resetColor(boolean isLight) {

    }

    @Override
    public void paint() {
        squareGui.paint();
    }

    @Override
    public void changed(ObservableValue observable, Object oldValue, Object newValue) {
        this.squareGui.paint();    }
}
