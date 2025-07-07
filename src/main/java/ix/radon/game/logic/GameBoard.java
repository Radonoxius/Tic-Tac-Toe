package ix.radon.game.logic;

import ix.radon.game.ui.Draw;
import ix.radon.game.ui.GameUI;
import ix.radon.game.ui.InputHandler;
import ix.radon.game.ui.ScoreBoard;

import java.security.InvalidParameterException;
import java.util.function.Function;

//Represents a logical game board containing 9 (3x3) tiles
public class GameBoard {
    //player is the human who is playing the game
    public Player player;

    //computer is the algorithm that's playing against the human
    public Player computer;

    //Represents a logical tile on the game board
    static class GameBoardTile {
        private TileSymbol symbol;

        GameBoardTile() {
            symbol = TileSymbol.BLANK;
        }

        void setSymbol(TileSymbol symbol) {
            this.symbol = symbol;
        }

        TileSymbol getSymbol() {
            return symbol;
        }
    }

    //Stores the 9 tiles as a 3x3 matrix
    private static final GameBoardTile[][] tiles = new GameBoardTile[3][3];

    private GameBoard(
            String playerName,
            TileSymbol playerSymbol
    ) throws InvalidParameterException {
        for (byte x = 0; x < 3; x++) {
            for (byte y = 0; y < 3; y++) {
                tiles[x][y] = new GameBoardTile();
            }
        }

        if (playerSymbol == TileSymbol.BLANK) {
            throw new InvalidParameterException("Player's tileSymbol can't be BLANK!");
        }

        player = new Player(playerName, playerSymbol);

        if (playerSymbol == TileSymbol.X)
            computer = new Player("Computer", TileSymbol.O);
        else if (playerSymbol == TileSymbol.O)
            computer = new Player("Computer", TileSymbol.X);
    }

    //Initializes the game board and the players.
    //All the 9 tiles have 'TileSymbol.BLANK' as default symbol
    public static void init(
            String playerName,
            TileSymbol playerSymbol,
            Function<GameBoard, Void> f
    ) {
        //Starts the game UI directly
        //Also initialises ScoreBoard, InputHandler and GameBoardUI
        GameUI.Start(f, new GameBoard(playerName, playerSymbol));
    }

    //This function takes input from the user and sets the symbol
    public void setPlayerTileSymbol() throws Throwable {
        int[] coordinates = getUserInput();

        tiles[coordinates[0]][coordinates[1]].setSymbol(player.tileSymbol);
        Draw.drawPlayerSymbol(coordinates[0], coordinates[1], this);
    }

    //This function takes the matrix-index of the tile and sets the symbol
    //Remember, both indices starts at 0 (max value is 2)
    public void setComputerTileSymbol(
            int x,
            int y
    ) throws Throwable {
        if (x > 2 || y > 2) {
            throw new IndexOutOfBoundsException();
        }
        tiles[x][y].setSymbol(computer.tileSymbol);
        Draw.drawComputerSymbol(x, y, this);
    }

    //This function takes the matrix-index of the tile and gets the symbol that's currently stored
    //Remember, both indices starts at 0 (max value is 2)
    public TileSymbol getTileSymbol(
            int x,
            int y
    ) throws IndexOutOfBoundsException {
        if (x > 2 || y > 2) {
            throw new IndexOutOfBoundsException();
        }
        return tiles[x][y].getSymbol();
    }

    private int[] getUserInput() throws Throwable {
        return InputHandler.getInput();
    }

    //Gets the current score of the player
    public int getPlayerScore() {
        return ScoreBoard.getPlayerScore();
    }

    //Gets the current score of the computer
    public int getComputerScore() {
        return ScoreBoard.getComputerScore();
    }

    //Increases the player score by 1, and updates the UI
    public void incrementPlayerScore() throws Throwable {
        ScoreBoard.incrementPlayerScore();
    }

    //Increases the computer score by 1, and updates the UI
    public void incrementComputerScore() throws Throwable {
        ScoreBoard.incrementComputerScore();
    }
}