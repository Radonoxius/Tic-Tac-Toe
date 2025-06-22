package ix.radon.game;

import ix.radon.game.logic.GameBoard;
import ix.radon.game.logic.TileSymbol;
import ix.radon.game.ui.GameUI;
import ix.radon.game.ui.ScoreBoard;

public class Main {
    public static void main(String[] args) {
        GameBoard.init(board -> {
            try {
                board.setTileSymbol(0, 0, TileSymbol.X);
                board.setTileSymbol(1, 1, TileSymbol.O);

                Thread.sleep(2000);
                ScoreBoard.incrementPlayerScore();

                Thread.sleep(2000);
                ScoreBoard.incrementComputerScore();

                Thread.sleep(2000);
                ScoreBoard.incrementPlayerScore();
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }

            return null;
        });
    }
}