package ix.radon.game.ui;

import ix.radon.game.logic.GameBoard;

import java.lang.foreign.*;

public class ScoreBoard {
    private static int playerScore = 0;
    private static int computerScore = 0;

    private static Window playerScoreWindow;
    private static Window computerScoreWindow;

    private static GameBoard game;

    static void init(Arena arena, GameBoard game) throws Throwable {
        ScoreBoard.game = game;

        playerScoreWindow = new Window(
                        arena,
                        Terminal.xSize / 2,
                        3,
                        0,
                        0
                )
                .makeDefaultBorder()
                .attributeOn(FontAttributes.GREEN)
                .print(3, 1, game.player.name + "'s")
                .attributeOff(FontAttributes.GREEN)
                .print(5 + game.player.name.length(), 1, " Score: ")
                .attributeOn(FontAttributes.GREEN)
                .attributeOn(FontAttributes.BOLD)
                .print(13 + game.player.name.length(),
                        1,
                        String.valueOf(playerScore)
                )
                .attributeOff(FontAttributes.BOLD)
                .attributeOff(FontAttributes.GREEN)
                .refresh();

        computerScoreWindow = new Window(
                        arena,
                        Terminal.xSize / 2,
                        3,
                        (Terminal.xSize / 2) - 1,
                        0
                )
                .makeDefaultBorder()
                .attributeOn(FontAttributes.RED)
                .print(3, 1, game.computer.name + "'s")
                .attributeOff(FontAttributes.RED)
                .print(5 + game.computer.name.length(), 1, " Score: ")
                .attributeOn(FontAttributes.RED)
                .attributeOn(FontAttributes.BOLD)
                .print(13 + game.computer.name.length(),
                        1,
                        String.valueOf(computerScore)
                )
                .attributeOff(FontAttributes.BOLD)
                .attributeOff(FontAttributes.RED)
                .refresh();
    }

    static void updatePlayerScoreWindow() throws Throwable {
        playerScoreWindow
                .attributeOn(FontAttributes.GREEN)
                .attributeOn(FontAttributes.BOLD)
                .print(
                        game.player.name.length() + 13,
                        1,
                        String.valueOf(playerScore)
                )
                .attributeOff(FontAttributes.BOLD)
                .attributeOff(FontAttributes.GREEN)
                .refresh();
    }

    public static void incrementPlayerScore() throws Throwable {
        playerScore += 1;
        updatePlayerScoreWindow();
    }

    public static int getPlayerScore() {
        return playerScore;
    }

    static void updateComputerScoreWindow() throws Throwable {
        computerScoreWindow
                .attributeOn(FontAttributes.RED)
                .attributeOn(FontAttributes.BOLD)
                .print(
                        game.computer.name.length() + 13,
                        1,
                        String.valueOf(computerScore)
                )
                .attributeOff(FontAttributes.BOLD)
                .attributeOff(FontAttributes.RED)
                .refresh();
    }

    public static void incrementComputerScore() throws Throwable {
        computerScore += 1;
        updateComputerScoreWindow();
    }

    public static int getComputerScore() {
        return computerScore;
    }

    static void deleteBoards() throws Throwable {
        playerScoreWindow.delete();
        computerScoreWindow.delete();
    }
}