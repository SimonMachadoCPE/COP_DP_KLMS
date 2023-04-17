package view;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;
import shared.PieceSquareColor;
import view.command.memento.Memento;

public class GuiConfig {
	
	public static ObjectProperty<PaintStyle> paintStyle = new SimpleObjectProperty<PaintStyle>();
	public static ObjectProperty<PieceSquareColor> beginColor = new SimpleObjectProperty<PieceSquareColor>();
	public static ObjectProperty<Color> blackSquareColor = new SimpleObjectProperty<Color>();
	public static ObjectProperty<Color> whiteSquareColor = new SimpleObjectProperty<Color>();
	public static ObjectProperty<Color> lightColor = new SimpleObjectProperty<Color>();
	public static IntegerProperty height = new SimpleIntegerProperty();
	public static IntegerProperty width = new SimpleIntegerProperty();
	public static IntegerProperty nbLigne = new SimpleIntegerProperty();
	public static IntegerProperty nbColonne = new SimpleIntegerProperty();

	static {
		GuiConfig.setInitState();
	}

	/**
	 * Cette méthode permet d'initialiser les attributs
	 * Rq : ici les valeurs sont codées en dur mais pourraient/devraient
	 * être importées d'un fichier de config
	 */
	public static void setInitState() {
		//GuiConfig.paintStyle.set(PaintStyle.GRADIENT);
		GuiConfig.beginColor.set(PieceSquareColor.WHITE);
		GuiConfig.whiteSquareColor.set(Color.rgb(118, 150, 86, 1.0));
		GuiConfig.blackSquareColor.set(Color.rgb(238, 238, 210, 1.0));
		GuiConfig.lightColor.set(Color.BLUE);
		GuiConfig.height.set(700);
		GuiConfig.width.set(700);
		GuiConfig.nbLigne.set(8);
		GuiConfig.nbColonne.set(8);
	}

	//////////////////////////////////////////////////////////////////////////////////////
	//
	// Méthodes nécessaires à mise en place DP Memento
	// pour sauvegarder et restaurer l'état de l'objet
	//
	///////////////////////////////////////////////////////////////////////////////////////

	public static Memento saveIntoMemento() {

		return new GuiConfigMemento( GuiConfig.paintStyle.get(),
				GuiConfig.beginColor.get(),
				GuiConfig.blackSquareColor.get(),
				GuiConfig.whiteSquareColor.get(),
				GuiConfig.lightColor.get(),
				GuiConfig.height.get(),
				GuiConfig.width.get(),
				GuiConfig.nbLigne.get(),
				GuiConfig.nbColonne.get());
	}

	public static void restoreFromMemento(Memento m) {
		GuiConfigMemento memento = (GuiConfigMemento)m;

		GuiConfig.paintStyle.set(memento.paintStyle);
		GuiConfig.beginColor.set(memento.beginColor);
		GuiConfig.blackSquareColor.set(memento.blackSquareColor);
		GuiConfig.whiteSquareColor.set(memento.whiteSquareColor);
		GuiConfig.lightColor.set(memento.lightColor);
		GuiConfig.height.set(memento.height);
		GuiConfig.width.set(memento.width);
		GuiConfig.nbLigne.set(memento.nbLigne);
		GuiConfig.nbColonne.set(memento.nbColonne);
	}
}

class GuiConfigMemento implements Memento {

	PaintStyle paintStyle ;
	PieceSquareColor beginColor ;
	Color blackSquareColor ;
	Color whiteSquareColor;
	Color lightColor ;
	int height ;
	int width;
	int nbLigne ;
	int nbColonne ;

	public GuiConfigMemento(PaintStyle paintStyle, PieceSquareColor beginColor,
							Color blackSquareColor, Color whiteSquareColor,
							Color lightColor, int height, int width,
							int nbLigne, int nbColonne) {
		super();
		this.paintStyle = paintStyle;
		this.beginColor = beginColor;
		this.blackSquareColor = blackSquareColor;
		this.whiteSquareColor = whiteSquareColor;
		this.lightColor = lightColor;
		this.height = height;
		this.width = width;
		this.nbLigne = nbLigne;
		this.nbColonne = nbColonne;
	}

	@Override
	public void restore() {
		GuiConfig.restoreFromMemento(this);
	}

	@Override
	public String toString() {
		return "GuiConfigMemento [paintStyle=" + this.paintStyle + ", blackSquareColor=" + this.blackSquareColor
				+ ", whiteSquareColor=" + this.whiteSquareColor + "]";
	}

}
