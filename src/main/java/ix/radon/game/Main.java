package ix.radon.game;

import ix.radon.game.logic.GameBoard;
import ix.radon.game.logic.TileSymbol;

public class Main {
    public static void main(String[] args) {
        /* If you want to do anything before starting the game,
         * write the code below. (Before calling GameBoard.init(playerName, playerSymbol, f))
         */

        /* GameBoard.init(playerName, playerSymbol, f) creates a GameBoard, players and
         * provides it to you. This function will also start the Game UI
         *
         * Call this function ONLY ONCE in the entire program
         */
        GameBoard.init(
                "TestName", //Player's Name
                TileSymbol.X, //Symbol the player will use
                game /* 'game' is a GameBoard object */ -> {
                    //This is called a 'Lambda' btw

                    //DON'T call GameBoard.init(playerName, playerSymbol, f) again here!

                    /* DON'T USE 'System.out.print/ln' IN THIS BLOCK!
                     * DON'T TAKE USER INPUT HERE AS WELL!
                     * DO IT BEFORE CALLING GameBoard.init(playerName, playerSymbol, f)!
                     */

                    /* This init function provides you a GameBoard object called 'game'
                     * All the 9 tiles of this game have 'TileSymbol.BLANK' as its default symbol
                     */

                    /* All of your logic code should be within this below 'try' block
                     *
                     * Split your logic into separate classes (or packages) and
                     * call that code inside the 'try' block below
                     */
                    try {
                        game.getUserInput();
                        Thread.sleep(500);

                        game.getUserInput();

                        Thread.sleep(500);
                        game.incrementPlayerScore();

                        Thread.sleep(500);
                        game.incrementComputerScore();
                    }

                    //Code below is for error handling. Just ignore it
                    catch (Throwable e) {
                        throw new RuntimeException(e);
                    }

                    //Compulsory to return null
                    //It doesn't do anything though
                    return null;
                }
        );
    }
}