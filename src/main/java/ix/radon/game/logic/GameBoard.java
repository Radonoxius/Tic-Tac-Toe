package ix.radon.game.logic;

import ix.radon.game.ui.GameUI;

import java.util.function.Supplier;

public class GameBoard {
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

    private final GameBoardTile[][] tiles = new GameBoardTile[3][3];

    public GameBoard(Supplier<Void> f) {
        for (byte x = 0; x < 3; x++) {
            for (byte y = 0; y < 3; y++) {
                tiles[x][y] = new GameBoardTile();
            }
        }

        GameUI.Start(f);
    }

    public void setTileSymbol(int x, int y, TileSymbol symbol) throws IndexOutOfBoundsException {
        if (x > 2 || y > 2) {
            throw new IndexOutOfBoundsException();
        }
        tiles[x][y].setSymbol(symbol);
    }

    public TileSymbol getTileSymbol(int x, int y) throws IndexOutOfBoundsException {
        if (x > 2 || y > 2) {
            throw new IndexOutOfBoundsException();
        }
        return tiles[x][y].getSymbol();
    }
}

