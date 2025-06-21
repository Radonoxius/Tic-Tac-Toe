package ix.radon.game;

import ix.radon.game.logic.GameBoard;
import ix.radon.game.ui.GameUI;
import ix.radon.game.ui.ScoreBoard;

public class Main {
    public static void main(String[] args) {
        new GameBoard(() -> {
            try {
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