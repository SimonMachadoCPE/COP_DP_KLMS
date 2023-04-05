package model.noStrategy.pieces.templateMethod;

import shared.ActionType;
import shared.ModelCoord;
import shared.PieceSquareColor;

public class PionNoir extends AbstractPion {
    /**
     * @param couleur
     * @param coord
     */
    public PionNoir(PieceSquareColor couleur, ModelCoord coord) {
        super(PieceSquareColor.BLACK, coord);
    }

    @Override
    protected boolean isColorTakeOk(int yFinal, int xFinal) {
        if ((yFinal == this.getY()+1) || (yFinal == this.getY()+1 && xFinal == this.getX()-1)) {
            return true;
        }
        return false;
    }

    @Override
    protected boolean isColorMoveOk(int yFinal,int xFinal) {
        if (this.hasThisColor(PieceSquareColor.BLACK) && (yFinal - this.getY() > 0)) {
            return true;
        }
        return false;
    }
}
