package com.github.abalone.elements;

import com.github.abalone.controller.GameController;
import com.github.abalone.util.Color;
import com.github.abalone.util.Coords;
import com.github.abalone.util.Direction;
import com.github.abalone.util.Move;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author keruspe
 */
public class Board implements Serializable {

   private static Board singleton;

   private HashSet<Ball> balls = new HashSet<Ball>(28);

   private boolean filled;

   private void addBall(Ball ball) {
      this.balls.add(ball);
   }

   public Color elementAt(Coords coords) {
      Integer col = coords.getCol();
      Integer row = Math.abs(coords.getRow());
      if (col < 0 || row > 4 || row + col > 8) {
         return Color.INVALID;
      }
      Ball ball = new Ball(Color.WHITE, coords);
      if (this.balls.contains(ball)) {
         return Color.WHITE;
      }
      ball.setColor(Color.BLACK);
      if (this.balls.contains(ball)) {
         return Color.BLACK;
      }
      return Color.NONE;
   }

   public Ball getBallAt(Coords coords) {
      Integer col = coords.getCol();
      Integer row = Math.abs(coords.getRow());
      if (col < 0 || row > 4 || row + col > 8) {
         return null;
      }
      Ball ball = new Ball(Color.WHITE, coords);
      if (this.balls.contains(ball)) {
         return ball;
      }
      ball.setColor(Color.BLACK);
      if (this.balls.contains(ball)) {
         return ball;
      }
      return null;
   }

   public Ball getBallAt(Ball ball, Direction direction) {
      Integer row = ball.getCoords().getRow();
      Integer col = ball.getCoords().getCol();
      switch (direction) {
         case UPLEFT:
            if (--row < 0) {
               --col;
            }
            break;
         case UPRIGHT:
            if (--row > -1) {
               ++col;
            }
            break;
         case LEFT:
            --col;
            break;
         case RIGHT:
            ++col;
            break;
         case DOWNLEFT:
            if (++row > 0) {
               --col;
            }
            break;
         case DOWNRIGHT:
            if (++row < 1) {
               ++col;
            }
            break;
      }
      Coords newCoords = new Coords(row, col);
      Ball returnBall = this.getBallAt(newCoords);
      if (returnBall == null) {
         returnBall = new Ball(this.elementAt(newCoords), newCoords);
      }
      return returnBall;
   }

   private Board() {
      this.filled = false;
   }

   public void fill(Game p) {
      if (this.filled) {
         return;
      }
      if (p == null) {
         for (int i = 0; i <= 4; ++i) {
            this.addBall(new Ball(Color.WHITE, -4, i));
            this.addBall(new Ball(Color.BLACK, 4, i));
         }
         for (int i = 0; i <= 5; ++i) {
            this.addBall(new Ball(Color.WHITE, -3, i));
            this.addBall(new Ball(Color.BLACK, 3, i));
         }
         for (int i = 2; i <= 4; ++i) {
            this.addBall(new Ball(Color.WHITE, -2, i));
            this.addBall(new Ball(Color.BLACK, 2, i));
         }
      } else {
         this.balls = p.getBoard().balls;
      }

      this.filled = true;
   }

   public static Board getInstance() {
      if (Board.singleton == null) {
         Board.singleton = new Board();
      }
      return Board.singleton;
   }

   /**
    * Returns the list of ball, read-only
    * @return the ball list as a {Set<Ball>}
    */
   public Set<Ball> getBalls() {
      return Collections.unmodifiableSet(this.balls);
   }

   public Move getMove(Set<Coords> selectedCoords, Direction direction, Color player) {
      Set<Ball> selectedBalls = GameController.getInstance().validMove(selectedCoords, direction, player);
      return new Move(selectedBalls, direction);
   }

   public Integer ballsCount(Color color) {
      Integer count = 0;
      for (Ball b : balls) {
         if (b.getColor() == color) {
            ++count;
         }
      }
      return count;
   }

   public Color dominant() {
      Integer white = ballsCount(Color.WHITE);
      Integer black = ballsCount(Color.BLACK);
      if (white > black) {
         return Color.WHITE;
      } else if (black > white) {
         return Color.BLACK;
      } else {
         return Color.NONE;
      }
   }

   public void apply(Move move) {
      this.balls.removeAll(move.getInitialBalls());
      this.balls.addAll(move.getFinalBalls());
   }

   public void revert(Move move) {
      this.balls.removeAll(move.getFinalBalls());
      this.balls.addAll(move.getInitialBalls());
   }

   public Boolean loose(Color color) {
      return (ballsCount(color) < 9);
   }
}
