package ix.radon.game.ui;

import java.lang.foreign.*;

public class ScoreBoard {
    private static int playerScore = 0;
    private static int computerScore = 0;

    private static Window playerScoreScreen;
    private static Window computerScoreScreen;

    static void createBoards(Arena arena) throws Throwable {
        playerScoreScreen = Window.createWindow(
                arena,
                GameUI.terminalSize().xSize / 2,
                3,
                0,
                0
        );

        playerScoreScreen.createBorder();
        playerScoreScreen.printString(3, 1, "Player Score: 0");
        playerScoreScreen.refresh();

        computerScoreScreen = Window.createWindow(
                arena,
                GameUI.terminalSize().xSize / 2,
                3,
                (GameUI.terminalSize().xSize / 2) - 1,
                0
        );

        computerScoreScreen.createBorder();
        computerScoreScreen.printString(3, 1, "Computer Score: 0");
        computerScoreScreen.refresh();
    }

    public static void incrementPlayerScore() throws Throwable {
        playerScore += 1;
        playerScoreScreen.printString(17, 1, String.valueOf(playerScore));
        playerScoreScreen.refresh();
    }

    public static void incrementComputerScore() throws Throwable {
        computerScore += 1;
        computerScoreScreen.printString(19, 1, String.valueOf(computerScore));
        computerScoreScreen.refresh();
    }

    static void deleteBoards() throws Throwable {
        playerScoreScreen.delete();
        computerScoreScreen.delete();
    }
}