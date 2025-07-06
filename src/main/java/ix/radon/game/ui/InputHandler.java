package ix.radon.game.ui;

import ix.radon.game.logic.GameBoard;

import java.lang.foreign.Arena;
import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.lang.invoke.MethodHandle;

public class InputHandler {
    private static Arena arena;

    private static GameBoard game;

    private static int[] currentSelectedCoordinates;

    static void init(Arena arena, GameBoard game) {
        currentSelectedCoordinates = new int[] {0, 0};
        InputHandler.arena = arena;
        InputHandler.game = game;
    }

    public static void getInput() throws Throwable {
        MethodHandle stdscrGetInput = ArtistLibrary.loadFunction(
                arena,
                "stdscr_get_input",
                FunctionDescriptor.of(ValueLayout.JAVA_BYTE)
        );

        int[] previousSelectedCoordinates = currentSelectedCoordinates.clone();

        printCurrentCoordinates();
        highlightTileAt(currentSelectedCoordinates);

        while (true) {
            int key = (int) stdscrGetInput.invoke();

            if (key == 1 && currentSelectedCoordinates[1] != 0) {
                currentSelectedCoordinates[1] -= 1;
                printCurrentCoordinates();

                if (
                        previousSelectedCoordinates[0] != currentSelectedCoordinates[0] ||
                                previousSelectedCoordinates[1] != currentSelectedCoordinates[1]
                )
                    clearTileHighlightAt(previousSelectedCoordinates);

                highlightTileAt(currentSelectedCoordinates);
            }
            else if (key == 2 && currentSelectedCoordinates[1] != 2) {
                currentSelectedCoordinates[1] += 1;
                printCurrentCoordinates();

                if (
                        previousSelectedCoordinates[0] != currentSelectedCoordinates[0] ||
                                previousSelectedCoordinates[1] != currentSelectedCoordinates[1]
                )
                    clearTileHighlightAt(previousSelectedCoordinates);

                highlightTileAt(currentSelectedCoordinates);
            }
            else if (key == 3 && currentSelectedCoordinates[0] != 0) {
                currentSelectedCoordinates[0] -= 1;
                printCurrentCoordinates();

                if (
                        previousSelectedCoordinates[0] != currentSelectedCoordinates[0] ||
                                previousSelectedCoordinates[1] != currentSelectedCoordinates[1]
                )
                    clearTileHighlightAt(previousSelectedCoordinates);

                highlightTileAt(currentSelectedCoordinates);
            }
            else if (key == 4 && currentSelectedCoordinates[0] != 2) {
                currentSelectedCoordinates[0] += 1;
                printCurrentCoordinates();

                if (
                        previousSelectedCoordinates[0] != currentSelectedCoordinates[0] ||
                                previousSelectedCoordinates[1] != currentSelectedCoordinates[1]
                )
                    clearTileHighlightAt(previousSelectedCoordinates);

                highlightTileAt(currentSelectedCoordinates);
            }
            else if (
                    key == 0 &&
                    !GameBoardUI.tiles[currentSelectedCoordinates[0]][currentSelectedCoordinates[1]].isOccupied
            ) {
                clearCurrentCoordinates();

                if (
                        previousSelectedCoordinates[0] != currentSelectedCoordinates[0] ||
                        previousSelectedCoordinates[1] != currentSelectedCoordinates[1]
                )
                    clearTileHighlightAt(previousSelectedCoordinates);

                clearTileHighlightAt(currentSelectedCoordinates);

                GameBoardUI
                        .tiles[currentSelectedCoordinates[0]][currentSelectedCoordinates[1]]
                        .isOccupied = true;
                game.setTileSymbol(
                        currentSelectedCoordinates[0],
                        currentSelectedCoordinates[1],
                        game.player.tileSymbol
                );
                break;
            }

            previousSelectedCoordinates = currentSelectedCoordinates.clone();
        }
    }

    private static void highlightTileAt(int[] tileCoordinates) throws Throwable {
        String mask = "";
        for (int x = 0; x <= GameBoardUI.xCoordinateMax; x++)
            mask += ' ';

        if (!GameBoardUI.tiles[tileCoordinates[0]][tileCoordinates[1]].isOccupied)
            GameBoardUI.tiles[tileCoordinates[0]][tileCoordinates[1]].windowPtr
                    .attributeOn(FontAttributes.STANDOUT)
                    .printFor(0, GameBoardUI.yCoordinateMax, mask)
                    .attributeOff(FontAttributes.STANDOUT)
                    .refresh();
    }

    private static void clearTileHighlightAt(int[] tileCoordinates) throws Throwable {
        String mask = "";
        for (int x = 0; x <= GameBoardUI.xCoordinateMax; x++)
            mask += ' ';

        if (!GameBoardUI.tiles[tileCoordinates[0]][tileCoordinates[1]].isOccupied)
            GameBoardUI.tiles[tileCoordinates[0]][tileCoordinates[1]].windowPtr
                    .printFor(0, GameBoardUI.yCoordinateMax, mask)
                    .refresh();
    }

    private static void refresh() throws Throwable {
        MethodHandle stdscrRefresh = ArtistLibrary.loadFunction(
                arena,
                "stdscr_refresh",
                FunctionDescriptor.ofVoid()
        );

        stdscrRefresh.invoke();
    }

    private static void standoutAttributeOn() throws Throwable {
        MethodHandle stdscrAttrOn = ArtistLibrary.loadFunction(
                arena,
                "stdscr_attr_on",
                FunctionDescriptor.ofVoid(
                        ValueLayout.JAVA_INT
                )
        );

        stdscrAttrOn.invoke(FontAttributes.STANDOUT.toInt());
    }

    private static void standoutAttributeOff() throws Throwable {
        MethodHandle stdscrAttrOff = ArtistLibrary.loadFunction(
                arena,
                "stdscr_attr_off",
                FunctionDescriptor.ofVoid(
                        ValueLayout.JAVA_INT
                )
        );

        stdscrAttrOff.invoke(FontAttributes.STANDOUT.toInt());
    }

    private static void printCurrentCoordinates() throws Throwable {
        MethodHandle stdscrPrint = ArtistLibrary.loadFunction(
                arena,
                "stdscr_print",
                FunctionDescriptor.ofVoid(
                        ValueLayout.JAVA_INT,
                        ValueLayout.JAVA_INT,
                        ValueLayout.ADDRESS
                )
        );

        String content = "Currently Selected Tile: (" +
                currentSelectedCoordinates[0] + ", " + currentSelectedCoordinates[1] +
                ") [ENTER to confirm]";

        MemorySegment strPtr = arena.allocateFrom(content);

        standoutAttributeOn();
        stdscrPrint.invoke(
                Terminal.xSize - 50,
                Terminal.ySize - 1,
                strPtr
        );
        standoutAttributeOff();
        refresh();
    }

    private static void clearCurrentCoordinates() throws Throwable {
        MethodHandle stdscrPrint = ArtistLibrary.loadFunction(
                arena,
                "stdscr_print",
                FunctionDescriptor.ofVoid(
                        ValueLayout.JAVA_INT,
                        ValueLayout.JAVA_INT,
                        ValueLayout.ADDRESS
                )
        );

        String mask = "";
        for (int i = 0; i < 50; i++)
            mask += ' ';

        MemorySegment strPtr = arena.allocateFrom(mask);

        stdscrPrint.invoke(
                Terminal.xSize - 50,
                Terminal.ySize - 1,
                strPtr
        );
        refresh();
    }
}