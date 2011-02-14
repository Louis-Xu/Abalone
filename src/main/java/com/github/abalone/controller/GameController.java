package com.github.abalone.controller;

import com.github.abalone.elements.Ball;
import com.github.abalone.elements.Board;
import com.github.abalone.elements.Game;
import com.github.abalone.elements.Typelignepl;
import com.github.abalone.util.Color;
import com.github.abalone.util.Coords;
import com.github.abalone.util.Direction;
import com.github.abalone.view.Window;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author sardemff7
 */
public class GameController
{
    private static GameController singleton;
    private Window window;
    private Game game;

    private GameController() {}

    public static GameController getInstance()
    {
        if ( GameController.singleton == null )
            GameController.singleton = new GameController();
        return GameController.singleton;
    }

    /// Launch a new game
    public void launch()
    {
        Board.getInstance().fill(null);
        this.game = new Game(Color.WHITE, -1, -1);
        this.window.updateBoard();
    }

    /// Save the game
    public void save()
    {
        /*
         * TODO: serialize the save to the file
         */
    }

    /// Load the saved game
    public void load()
    {
        /*
         * TODO: deserialize form the file
         */
        this.window.updateBoard();
    }

    /// Quit the game
    public void quit()
    {
        System.exit(0);
    }

    public Color opponent(Color self) {
        Color opponent = Color.NONE;
        if (self == Color.BLACK) {
            opponent = Color.WHITE;
        } else if (self == Color.WHITE) {
            opponent = Color.BLACK;
        }
        return opponent;
    }

    //renvoi la coordonnée de la bille la plus proche de la bille adverse ou de la case vide
    public Coords closer(Set<Ball> selectedBalls, Direction to) {
        Iterator itb = selectedBalls.iterator();
        Coords closer = new Coords(10, 10);
        switch (to) {
        case DOWNLEFT:
        case DOWNRIGHT:
            closer.setRow(10);
            while (itb.hasNext()) {
                Ball b = (Ball) itb.next();
                if (closer.getRow() > b.getCoords().getRow()) {
                    closer = new Coords(b.getCoords());
                }
            }
            break;
        case UPLEFT:
        case UPRIGHT:
            closer.setRow(-10);
            while (itb.hasNext()) {
                Ball b = (Ball) itb.next();
                if (closer.getRow() < b.getCoords().getRow()) {
                    closer = new Coords(b.getCoords());
                }
            }
            break;
        case LEFT:
            closer.setCol(10);
            while (itb.hasNext()) {
                Ball b = (Ball) itb.next();
                if (closer.getCol() > b.getCoords().getCol()) {
                    closer = new Coords(b.getCoords());
                }
            }
            break;
        case RIGHT:
            closer.setCol(-10);
            while (itb.hasNext()) {
                Ball b = (Ball) itb.next();
                if (closer.getCol() < b.getCoords().getCol()) {
                    closer = new Coords(b.getCoords());
                }
            }
        }
        return closer;

    }

    /*public Boolean coupValide(Set<Coords> selectedBalls,Direction direction){
            Iterator itc=selectedBalls.iterator();
            //deplacement d'une bille
            Boolean result = false;

            switch ( selectedBalls.size() )
            {
            case 1:
                Coords c;
                c = (Coords) itc.next();
                if (this.game.getBoard().elementAt(c.moveTo(direction)) == Color.NONE
                        && this.game.getBoard().elementAt(c.moveTo(direction)) != Color.INVALID) {
                    result = true;
                }
            break;
            case 2:
                Coords c21 = (Coords) itc.next();
                Coords c22 = (Coords) itc.next();
                if (Typelignepl.lesDirectionPerpendiculaire(c21.LignePl(c22)).contains(direction)) {
                    if (this.game.getBoard().elementAt(c21.moveTo(direction)) == Color.NONE
                            && this.game.getBoard().elementAt(c21.moveTo(direction)) != Color.INVALID
                            && this.game.getBoard().elementAt(c22.moveTo(direction)) == Color.NONE
                            && this.game.getBoard().elementAt(c22.moveTo(direction)) != Color.INVALID) {
                        result = true;
                    }
                } else {
                    Coords closer = closer(selectedBalls, direction);
                    Coords next = closer.moveTo(direction);
                    if (game.getBoard().elementAt(next) == Color.NONE) {
                        result = true;
                    } else if (game.getBoard().elementAt(next) == this.adversaire()) {
                        if (game.getBoard().elementAt(next.moveTo(direction)) == Color.NONE
                                || game.getBoard().elementAt(next.moveTo(direction)) == Color.INVALID) {
                            return true;
                        }
                    }
                }
            break;
            case 3:
                // billes supposées sur la meme ligne
                Coords c31 = (Coords) itc.next();
                Coords c32 = (Coords) itc.next();
                Coords c33 = (Coords) itc.next();
                if (Typelignepl.lesDirectionPerpendiculaire(c31.LignePl(c32)).contains(direction)) {
                    if (this.game.getBoard().elementAt(c31.moveTo(direction)) == Color.NONE
                            && this.game.getBoard().elementAt(c31.moveTo(direction)) != Color.INVALID
                            && this.game.getBoard().elementAt(c32.moveTo(direction)) == Color.NONE
                            && this.game.getBoard().elementAt(c32.moveTo(direction)) != Color.INVALID
                            && this.game.getBoard().elementAt(c33.moveTo(direction)) == Color.NONE
                            && this.game.getBoard().elementAt(c33.moveTo(direction)) != Color.INVALID) {
                        result = true;
                    } else {
                        Coords closer = closer(selectedBalls, direction);
                        Coords next1 = closer.moveTo(direction);
                        Coords next2 = next1.moveTo(direction);
                        Coords next3 = next2.moveTo(direction);
                        if (this.game.getBoard().elementAt(next1.moveTo(direction)) == Color.NONE) {
                            result = true;
                        } else if (this.game.getBoard().elementAt(next1.moveTo(direction)) == this.adversaire()) {
                            if (this.game.getBoard().elementAt(next2.moveTo(direction)) == Color.NONE
                                    || this.game.getBoard().elementAt(next2.moveTo(direction)) == Color.INVALID) {
                                result = true;
                            } else if (this.game.getBoard().elementAt(next2.moveTo(direction)) == this.adversaire()) {
                                if (this.game.getBoard().elementAt(next3.moveTo(direction)) == Color.NONE
                                        || this.game.getBoard().elementAt(next3.moveTo(direction)) == Color.INVALID) {
                                    result = true;
                                }
                            }
                        }
                    }
                }
            break;
        }
        return result;
    }*/

    //fonction qui revois la liste des coordonné des conserne par un coup... si un coup n'est pas valide
    //la list revoiyée sera vide
    public Set<Ball> coupValid2(Set<Ball> selectedBalls, Direction direction, Color selfColor) {
        Iterator itb = selectedBalls.iterator();
        Set<Ball> result = new HashSet<Ball>();
        switch (selectedBalls.size()) {
            case 1:
                Ball b = (Ball) itb.next();
                if (this.game.getBoard().getBallAt(b, direction).getColor() == Color.NONE) {
                    result.add(b);
                }
                break;
            case 2:
                Ball b21 = (Ball) itb.next();
                Ball b22 = (Ball) itb.next();
                if (Typelignepl.lesDirectionPerpendiculaire(b21.getCoords().LignePl(b22.getCoords())).contains(direction)) {
                    if (this.game.getBoard().getBallAt(b21, direction).getColor() == Color.NONE
                            && this.game.getBoard().getBallAt(b22, direction).getColor() == Color.NONE) {
                        result.add(b21);
                        result.add(b22);
                    }
                } else {
                    Coords closer = closer(selectedBalls, direction);
                    Coords next = closer.moveTo(direction);
                    if (this.game.getBoard().elementAt(next) == Color.NONE) {
                        result.add(b21);
                        result.add(b22);
                    } else if (this.game.getBoard().elementAt(next) == this.opponent(selfColor)
                        && (this.game.getBoard().elementAt(next.moveTo(direction)) == Color.NONE
                              || this.game.getBoard().elementAt(next.moveTo(direction)) == Color.INVALID)) {
                            result.add(b21);
                            result.add(b21);
                            result.add(this.game.getBoard().getBallAt(next));
                    }
                }
                break;
            case 3:
                // billes supposées sur la meme ligne
                Ball b31 = (Ball) itb.next();
                Ball b32 = (Ball) itb.next();
                Ball b33 = (Ball) itb.next();
                if (Typelignepl.lesDirectionPerpendiculaire(b31.getCoords().LignePl(b32.getCoords())).contains(direction)) {
                    if (this.game.getBoard().getBallAt(b31, direction).getColor() == Color.NONE
                            && this.game.getBoard().getBallAt(b32, direction).getColor() == Color.NONE
                            && this.game.getBoard().getBallAt(b33, direction).getColor() == Color.NONE) {
                        result.add(b31);
                        result.add(b32);
                        result.add(b33);
                    } else {
                        Coords closer = closer(selectedBalls, direction);
                        Coords next1 = closer.moveTo(direction);
                        Coords next2 = next1.moveTo(direction);
                        Coords next3 = next2.moveTo(direction);
                        if (this.game.getBoard().elementAt(next1.moveTo(direction)) == Color.NONE) {
                            result.add(b31);
                            result.add(b32);
                            result.add(b33);
                        } else if (this.game.getBoard().elementAt(next1.moveTo(direction)) == this.opponent(selfColor)) {
                            if (this.game.getBoard().elementAt(next2.moveTo(direction)) == Color.NONE
                                        || this.game.getBoard().elementAt(next2.moveTo(direction)) == Color.INVALID) {
                                result.add(b31);
                                result.add(b32);
                                result.add(b33);
                                result.add(this.game.getBoard().getBallAt(next1));
                            } else if (this.game.getBoard().elementAt(next2.moveTo(direction)) == this.opponent(selfColor)) {
                                if (this.game.getBoard().elementAt(next3.moveTo(direction)) == Color.NONE
                                        || this.game.getBoard().elementAt(next3.moveTo(direction)) == Color.INVALID) {
                                    result.add(b31);
                                    result.add(b32);
                                    result.add(b33);
                                    result.add(this.game.getBoard().getBallAt(next1));
                                    result.add(this.game.getBoard().getBallAt(next2));
                                }
                            }
                        }
                    }
                }
                break;
            default:
                break;
        }
        return result;
    }
   public void joueUncoup(Set<Coords> selectedBallsCoords, Direction direction){
       /*
        * TODO: move the check to a "validMoves" function
        * that the view will call to display needed controls
        * (implies there will not be unvalid moves here)
        */
       Set<Ball> selectedBalls = new HashSet<Ball>();
       for (Coords c : selectedBallsCoords) {
          selectedBalls.add(this.game.getBoard().getBallAt(c));
       }
       Set<Ball> ballsTomove = coupValid2(selectedBalls, direction, this.game.getTurnAndGoNext());
       if(!ballsTomove.isEmpty()){
           this.game.getBoard().move(ballsTomove, direction);
       }
   }

    public void setWindow(Window window)
    {
        this.window = window;
    }
}
