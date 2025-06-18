package ix.radon.game.logic;

public class GameBoard {
    static class GameBoardTile {
        private TileSymbol symbol;
        private final byte xCoordinate;
        private final byte yCoordinate;

        GameBoardTile(byte x, byte y) {
            symbol = TileSymbol.BLANK;
            xCoordinate = x;
            yCoordinate = y;
        }

        void setSymbol(TileSymbol symbol) {
            this.symbol = symbol;
        }

        TileSymbol getSymbol() {
            return symbol;
        }

        byte[] getCoordinates() {
            return new byte[] { xCoordinate, yCoordinate };
        }
    }

    private final GameBoardTile[][] tiles = new GameBoardTile[3][3];

    public GameBoard() {
        for (byte x = 0; x < 3; x++) {
            for (byte y = 0; y < 3; y++) {
                tiles[x][y] = new GameBoardTile(x, y);
            }
        }
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

