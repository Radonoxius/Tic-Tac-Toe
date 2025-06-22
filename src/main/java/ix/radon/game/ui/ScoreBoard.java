package ix.radon.game.ui;

import java.lang.foreign.*;

//Represents the game scoreboard
public class ScoreBoard {
    private static int playerScore = 0;
    private static int computerScore = 0;

    private static Window playerScoreWindow;
    private static Window computerScoreWindow;

    static void createBoards(Arena arena) throws Throwable {
        playerScoreWindow = Window
                .create(
                        arena,
                        GameUI.getTerminal().xSize() / 2,
                        3,
                        0,
                        0
                )
                .makeDefaultBorder()
                .print(3, 1, "Player Score: 0")
                .refresh();

        computerScoreWindow = Window
                .create(
                        arena,
                        GameUI.getTerminal().xSize() / 2,
                        3,
                        (GameUI.getTerminal().xSize() / 2) - 1,
                        0
                )
                .makeDefaultBorder()
                .print(3, 1, "Computer Score: 0")
                .refresh();
    }

    static void updatePlayerScoreWindow() throws Throwable {
        playerScoreWindow
                .print(17, 1, String.valueOf(playerScore))
                .refresh();
    }

    //Increases the 'playerScore' by 1 and updates the score on the terminal
    public static void incrementPlayerScore() throws Throwable {
        playerScore += 1;
        updatePlayerScoreWindow();
    }

    //Provides the current score of the player
    public static int getPlayerScore() {
        return playerScore;
    }

    static void updateComputerScoreWindow() throws Throwable {
        computerScoreWindow
                .print(19, 1, String.valueOf(computerScore))
                .refresh();
    }

    //Increases the 'computerScore' by 1 and updates the score on the terminal
    public static void incrementComputerScore() throws Throwable {
        computerScore += 1;
        updateComputerScoreWindow();
    }

    //Provides the current score of the computer
    public static int getComputerScore() {
        return computerScore;
    }

    static void deleteBoards() throws Throwable {
        playerScoreWindow.delete();
        computerScoreWindow.delete();
    }
}