package ix.radon.game.ui;

import ix.radon.game.logic.GameBoard;
import ix.radon.game.logic.TileSymbol;

public class Draw {
    public static void drawPlayerSymbol(int x, int y, GameBoard game) throws Throwable {
        if (game.player.tileSymbol == TileSymbol.X)
            GameBoardUI.tiles[x][y].tileWindow
                    .attributeOn(FontAttributes.GREEN)
                    .attributeOn(FontAttributes.BOLD)
                    .printX(GameBoardUI.yCoordinateMax)
                    .attributeOff(FontAttributes.BOLD)
                    .attributeOff(FontAttributes.GREEN)
                    .refresh();

        else if (game.player.tileSymbol == TileSymbol.O)
            GameBoardUI.tiles[x][y].tileWindow
                    .attributeOn(FontAttributes.GREEN)
                    .attributeOn(FontAttributes.BOLD)
                    .printO(GameBoardUI.yCoordinateMax)
                    .attributeOff(FontAttributes.BOLD)
                    .attributeOff(FontAttributes.GREEN)
                    .refresh();
    }

    public static void drawComputerSymbol(int x, int y, GameBoard game) throws Throwable {
        GameBoardUI.tiles[x][y].isOccupied = true;

        if (game.computer.tileSymbol == TileSymbol.X)
            GameBoardUI.tiles[x][y].tileWindow
                    .attributeOn(FontAttributes.RED)
                    .attributeOn(FontAttributes.BOLD)
                    .printX(GameBoardUI.yCoordinateMax)
                    .attributeOff(FontAttributes.BOLD)
                    .attributeOff(FontAttributes.RED)
                    .refresh();

        else if (game.computer.tileSymbol == TileSymbol.O)
            GameBoardUI.tiles[x][y].tileWindow
                    .attributeOn(FontAttributes.RED)
                    .attributeOn(FontAttributes.BOLD)
                    .printO(GameBoardUI.yCoordinateMax)
                    .attributeOff(FontAttributes.BOLD)
                    .attributeOff(FontAttributes.RED)
                    .refresh();
    }
}