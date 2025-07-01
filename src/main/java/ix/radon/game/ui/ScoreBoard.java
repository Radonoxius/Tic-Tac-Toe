package ix.radon.game.ui;

import ix.radon.game.logic.GameBoard;

import java.lang.foreign.*;

//Represents the game scoreboard
public class ScoreBoard {
    private int playerScore;
    private int computerScore;

    private final Window playerScoreWindow;
    private final Window computerScoreWindow;

    private final GameBoard board;

    ScoreBoard(Arena arena, GameBoard board) throws Throwable {
        playerScore = 0;
        computerScore = 0;

        this.board = board;

        playerScoreWindow = new Window(
                        arena,
                        Terminal.xSize / 2,
                        3,
                        0,
                        0
                )
                .makeDefaultBorder()
                .attributeOn(FontAttributes.GREEN)
                .print(3, 1, this.board.player.name + "'s")
                .attributeOff(FontAttributes.GREEN)
                .print(5 + this.board.player.name.length(), 1, " Score: ")
                .attributeOn(FontAttributes.GREEN)
                .attributeOn(FontAttributes.BOLD)
                .print(13 + this.board.player.name.length(), 1, "0")
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
                .print(3, 1, this.board.computer.name + "'s")
                .attributeOff(FontAttributes.RED)
                .print(5 + this.board.computer.name.length(), 1, " Score: ")
                .attributeOn(FontAttributes.RED)
                .attributeOn(FontAttributes.BOLD)
                .print(13 + this.board.computer.name.length(), 1, "0")
                .attributeOff(FontAttributes.BOLD)
                .attributeOff(FontAttributes.RED)
                .refresh();
    }

    void updatePlayerScoreWindow() throws Throwable {
        playerScoreWindow
                .attributeOn(FontAttributes.GREEN)
                .attributeOn(FontAttributes.BOLD)
                .print(
                        board.player.name.length() + 13,
                        1,
                        String.valueOf(playerScore)
                )
                .attributeOff(FontAttributes.BOLD)
                .attributeOff(FontAttributes.GREEN)
                .refresh();
    }

    //Increases the 'playerScore' by 1 and updates the score on the terminal
    public void incrementPlayerScore() throws Throwable {
        playerScore += 1;
        updatePlayerScoreWindow();
    }

    //Provides the current score of the player
    public int getPlayerScore() {
        return playerScore;
    }

    void updateComputerScoreWindow() throws Throwable {
        computerScoreWindow
                .attributeOn(FontAttributes.RED)
                .attributeOn(FontAttributes.BOLD)
                .print(
                        board.computer.name.length() + 13,
                        1,
                        String.valueOf(computerScore)
                )
                .attributeOff(FontAttributes.BOLD)
                .attributeOff(FontAttributes.RED)
                .refresh();
    }

    //Increases the 'computerScore' by 1 and updates the score on the terminal
    public void incrementComputerScore() throws Throwable {
        computerScore += 1;
        updateComputerScoreWindow();
    }

    //Provides the current score of the computer
    public int getComputerScore() {
        return computerScore;
    }

    void deleteBoards() throws Throwable {
        playerScoreWindow.delete();
        computerScoreWindow.delete();
    }
}