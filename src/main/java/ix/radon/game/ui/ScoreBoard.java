package ix.radon.game.ui;

import java.lang.foreign.*;

public class ScoreBoard {
    private static int playerScore = 0;
    private static int computerScore = 0;

    private static Window playerScoreWindow;
    private static Window computerScoreWindow;

    static void createBoards(Arena arena) throws Throwable {
        playerScoreWindow = Window.createWindow(
                arena,
                GameUI.terminalSize().xSize() / 2,
                3,
                0,
                0
        );

        playerScoreWindow.createBorder();
        playerScoreWindow.printString(3, 1, "Player Score: 0");
        playerScoreWindow.refresh();

        computerScoreWindow = Window.createWindow(
                arena,
                GameUI.terminalSize().xSize() / 2,
                3,
                (GameUI.terminalSize().xSize() / 2) - 1,
                0
        );

        computerScoreWindow.createBorder();
        computerScoreWindow.printString(3, 1, "Computer Score: 0");
        computerScoreWindow.refresh();
    }

    static void updatePlayerScoreWindow() throws Throwable {
        playerScoreWindow.printString(17, 1, String.valueOf(playerScore));
        playerScoreWindow.refresh();
    }

    public static void incrementPlayerScore() throws Throwable {
        playerScore += 1;
        updatePlayerScoreWindow();
    }

    static void updateComputerScoreWindow() throws Throwable {
        computerScoreWindow.printString(19, 1, String.valueOf(computerScore));
        computerScoreWindow.refresh();
    }

    public static void incrementComputerScore() throws Throwable {
        computerScore += 1;
        updateComputerScoreWindow();
    }

    static void deleteBoards() throws Throwable {
        playerScoreWindow.delete();
        computerScoreWindow.delete();
    }
}