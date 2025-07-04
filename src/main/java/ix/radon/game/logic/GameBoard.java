package ix.radon.game.logic;

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
    private final GameBoardTile[][] tiles = new GameBoardTile[3][3];

    //The current in-game score board
    public ScoreBoard scoreBoard;
    //The current in-game user input handler
    public InputHandler inputHandler;

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
            throw new InvalidParameterException();
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
        //Also initialises scoreBoard and inputHandler
        GameUI.Start(f, new GameBoard(playerName, playerSymbol));
    }

    //This function takes the matrix-index of the tile and sets its symbol
    //Remember, both indices starts at 0 (max value is 2)
    public void setTileSymbol(
            int x,
            int y,
            TileSymbol symbol
    ) throws IndexOutOfBoundsException {
        if (x > 2 || y > 2) {
            throw new IndexOutOfBoundsException();
        }
        tiles[x][y].setSymbol(symbol);
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
}