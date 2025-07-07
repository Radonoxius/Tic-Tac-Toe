package ix.radon.game.ui;

import java.lang.foreign.Arena;

public class GameBoardUI {
    private static Arena arena;

    private static final int Height = (Terminal.ySize - 3) / 3;
    private static final int Width = Height * 2 + 1;
    private static final int yBoardOffset = 4;

    static final int xCoordinateMax = Width - 2;
    static final int yCoordinateMax = Height - 2;

    static class TileUI {
        final Window tileWindow;
        boolean isOccupied = false;

        TileUI(
                int xStart,
                int yStart,
                char rightSide,
                char bottomSide,
                char topRightCorner,
                char bottomLeftCorner,
                char bottomRightCorner
        ) throws Throwable {
            tileWindow = new Window(
                    arena,
                    Width,
                    Height,
                    xStart,
                    yStart
                    )
                    .attributeOn(FontAttributes.BOLD)
                    .makeBorder(
                            ' ',
                            rightSide,
                            ' ',
                            bottomSide,
                            ' ',
                            topRightCorner,
                            bottomLeftCorner,
                            bottomRightCorner
                    )
                    .attributeOff(FontAttributes.BOLD)
                    .refresh();
        }

        void delete() throws Throwable {
            tileWindow.delete();
        }
    }

    static final TileUI[][] tiles = new TileUI[3][3];

    static void init(Arena arena) throws Throwable {
        GameBoardUI.arena = arena;

        for (int x = 0; x < 2; x++)
            for (int y = 0; y < 2; y++)
                tiles[x][y] = new TileUI(
                        x * Width,
                        y * Height + yBoardOffset,
                        '|',
                        '-',
                        '|',
                        '-',
                        '+'
                );

        for (int y = 0; y < 2; y++)
            tiles[2][y] = new TileUI(
                    2 * Width,
                    y * Height + yBoardOffset,
                    ' ',
                    '-',
                    ' ',
                    '-',
                    ' '
            );

        for (int x = 0; x < 2; x++)
            tiles[x][2] = new TileUI(
                    x * Width,
                    2 * Height + yBoardOffset,
                    '|',
                    ' ',
                    '|',
                    ' ',
                    ' '
            );

        tiles[2][2] = new TileUI(
                2 * Width,
                2 * Height + yBoardOffset,
                ' ',
                ' ',
                ' ',
                ' ',
                ' '
        );
    }

    static void deleteTiles() throws Throwable {
        for (int x = 0; x < 3; x++)
            for (int y = 0; y < 3; y++)
                tiles[x][y].delete();
    }
}