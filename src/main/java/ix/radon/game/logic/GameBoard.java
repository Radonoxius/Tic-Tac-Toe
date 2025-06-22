package ix.radon.game.logic;

import ix.radon.game.ui.GameUI;
import java.util.function.Function;

//Represents a logical game board containing 9 (3x3) tiles
public class GameBoard {
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

    private GameBoard() {
        for (byte x = 0; x < 3; x++) {
            for (byte y = 0; y < 3; y++) {
                tiles[x][y] = new GameBoardTile();
            }
        }
    }

    //Initializes the game board. All the 9 tiles have 'TileSymbol.BLANK' as default symbol
    public static void init(Function<GameBoard, Void> f) {
        //Starts the game UI directly
        GameUI.Start(f, new GameBoard());
    }

    //This function takes the matrix-index of the tile and sets its symbol
    //Remember, both indices starts at 0 (max value is 2)
    public void setTileSymbol(int x, int y, TileSymbol symbol) throws IndexOutOfBoundsException {
        if (x > 2 || y > 2) {
            throw new IndexOutOfBoundsException();
        }
        tiles[x][y].setSymbol(symbol);
    }

    //This function takes the matrix-index of the tile and gets the symbol that's currently stored
    //Remember, both indices starts at 0 (max value is 2)
    public TileSymbol getTileSymbol(int x, int y) throws IndexOutOfBoundsException {
        if (x > 2 || y > 2) {
            throw new IndexOutOfBoundsException();
        }
        return tiles[x][y].getSymbol();
    }
}