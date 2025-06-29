package ix.radon.game;

import ix.radon.game.logic.GameBoard;
import ix.radon.game.logic.TileSymbol;
import ix.radon.game.ui.ScoreBoard;

public class Main {
    public static void main(String[] args) {
        /* If you want to do anything before starting the game,
         * write the code below. (Before calling GameBoard.init(f))
         */

        /* GameBoard.init(f) creates a GameBoard and
         * provides it to you. This function will also start the Game UI
         *
         * Call this function ONLY ONCE in the entire program
         */
        GameBoard.init(board /* 'board' is a GameBoard object */ -> {
            //This is called a 'Lambda' btw

            /* DON'T USE 'System.out.print/ln' IN THIS BLOCK!
             * DON'T TAKE USER INPUT HERE AS WELL!
             * DO IT BEFORE CALLING GameBoard.init(f)!
             */

            /* This init function provides you a GameBoard object called 'board'
             * All the 9 tiles of this board have 'TileSymbol.BLANK' as its default symbol
             */

            /* All of your logic code should be within this below 'try' block
             *
             * Split your logic into separate classes (or packages) and
             * call that code in the 'try' block below
             */
            try {
                board.setTileSymbol(0, 0, TileSymbol.X);
                board.setTileSymbol(1, 1, TileSymbol.O);

                Thread.sleep(2000);
                ScoreBoard.incrementPlayerScore();

                Thread.sleep(2000);
                ScoreBoard.incrementComputerScore();

                Thread.sleep(2000);
                ScoreBoard.incrementPlayerScore();
            }

            //Code below is for error handling. Just ignore it
            catch (Throwable e) {
                throw new RuntimeException(e);
            }

            //Compulsory to return null
            //It doesn't do anything though
            return null;
        });
    }
}