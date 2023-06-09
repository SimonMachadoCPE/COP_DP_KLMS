package model.noStrategy.pieces.templateMethod;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import model.ModelFactory;
import model.noStrategy.pieces.AbstractPiece;
import shared.ActionType;
import shared.ModelCoord;
import shared.PieceSquareColor;



/**
 * @author francoise.perrin - Alain BECKER
 * Inspiration Jacques SARAYDARYAN, Adrien GUENARD*
 */
public abstract class AbstractPion extends AbstractPiece {

	/**
	 * @param couleur
	 * @param coord
	 */
	public AbstractPion(PieceSquareColor couleur, ModelCoord coord) {
		super(couleur, coord);
	}
	

	/* (non-Javadoc)
	 * @see model.AbstractPiece#isAlgoMoveOk(ModelCoord , ActionType )
	 * Return true si le déplacement est possible vers la case de destination
	 */
	@Override
	public final boolean isAlgoMoveOk(ModelCoord finalCoord, ActionType actionType) {
		int xFinal = finalCoord.getCol() -'a';
		int yFinal = 8 - finalCoord.getLigne();
		boolean ret = false;

		// Déplacement d'1 case en diagonale avec prise
		if (actionType == ActionType.TAKE) {
			if (xFinal == this.getX()+1){
				ret = this.isColorTakeOk(yFinal,xFinal);
			}

		}
		// Déplacement vertical sans prise
		// d'1 case si le pion a déjà bougé, de 2 cases sinon
		// vers le haut ou vers le bas selon sa couleur
		else {
			if ((xFinal == this.getX())
					&& (Math.abs(yFinal - this.getY()) <= 1 ||
					(Math.abs(yFinal - this.getY()) <= 2 && !this.hasMoved()))) {

				ret = this.isColorMoveOk(yFinal,xFinal);

			}
		}
		return ret;
	}
	protected abstract boolean isColorTakeOk(int yFinal,int xFinal);
	protected abstract boolean isColorMoveOk(int yFinal,int xFinal);


	/* (non-Javadoc)
	 * @see model.AbstractPiece#getMoveItinerary(int, int)
	 * dans le cas du pion, il n'y a pas d'itinéraire
	 * puisqu'il se déplace sur une case adjacente
	 * sauf pour le 1er coup où il se déplace de 2 cases
	 */
	@Override
	public List<ModelCoord> getMoveItinerary(ModelCoord finalCoord) {
		int yFinal = 8 - finalCoord.getLigne();
		List<ModelCoord> ret = Collections.emptyList(); 
		if (this.getY()==yFinal-2 || this.getY()==yFinal+2){
			ret = new LinkedList<ModelCoord>();

			int yEtape = (this.getY() + yFinal) / 2;			// Y est la ligne entre départ et arrivée
			ModelCoord coordEtape = new ModelCoord((char)('a'+this.getX()), (8-yEtape));	// et X est dans la même colonne

			ret.add(coordEtape);
		}
		return ret;
	}




	/* (non-Javadoc)
	 * @see model.AbstractPiece#movePiece(int, int)
	 * gère le code de retour lorsqu'il faut promouvoir le pion
	 */
	@Override
	public ActionType doMove(ModelCoord finalCoord){
		ActionType ret = ActionType.UNKNOWN;
		ret = super.doMove(finalCoord);

		if(this.getY() == ModelFactory.nbLigne.get()-1 || this.getY() == 0) {
			ret = ActionType.PROMOTION;
		}
		return ret;
	}
}
