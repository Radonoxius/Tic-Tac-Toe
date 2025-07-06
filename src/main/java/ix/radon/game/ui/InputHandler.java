package ix.radon.game.ui;

import java.lang.foreign.Arena;
import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.lang.invoke.MethodHandle;

public class InputHandler {
    private static Arena arena;

    private static int[] currentCoordinates;

    static void init(Arena arena) {
        currentCoordinates = new int[] {0, 0};
        InputHandler.arena = arena;
    }

    public static int[] getInput() throws Throwable {
        MethodHandle stdscrGetInput = ArtistLibrary.loadFunction(
                arena,
                "stdscr_get_input",
                FunctionDescriptor.of(ValueLayout.JAVA_BYTE)
        );

        int[] previousCachedCoordinates = currentCoordinates.clone();

        printCurrentCoordinates();

        highlightTile(currentCoordinates);

        while (true) {
            int key = (int) stdscrGetInput.invoke();

            if (key == 1 && currentCoordinates[1] != 0) {
                currentCoordinates[1] -= 1;
                printCurrentCoordinates();
                if (
                        previousCachedCoordinates[0] != currentCoordinates[0] ||
                        previousCachedCoordinates[1] != currentCoordinates[1]
                )
                    clearTileHighlight(previousCachedCoordinates);
                highlightTile(currentCoordinates);
            }
            else if (key == 2 && currentCoordinates[1] != 2) {
                currentCoordinates[1] += 1;
                printCurrentCoordinates();
                if (
                        previousCachedCoordinates[0] != currentCoordinates[0] ||
                        previousCachedCoordinates[1] != currentCoordinates[1]
                )
                    clearTileHighlight(previousCachedCoordinates);
                highlightTile(currentCoordinates);
            }
            else if (key == 3 && currentCoordinates[0] != 0) {
                currentCoordinates[0] -= 1;
                printCurrentCoordinates();
                if (
                        previousCachedCoordinates[0] != currentCoordinates[0] ||
                        previousCachedCoordinates[1] != currentCoordinates[1]
                )
                    clearTileHighlight(previousCachedCoordinates);
                highlightTile(currentCoordinates);
            }
            else if (key == 4 && currentCoordinates[0] != 2) {
                currentCoordinates[0] += 1;
                printCurrentCoordinates();
                if (
                        previousCachedCoordinates[0] != currentCoordinates[0] ||
                        previousCachedCoordinates[1] != currentCoordinates[1]
                )
                    clearTileHighlight(previousCachedCoordinates);
                highlightTile(currentCoordinates);
            }
            else if (
                    key == 0 &&
                    !GameBoardUI.tiles[currentCoordinates[0]][currentCoordinates[1]].isOccupied
            ) {
                clearCurrentCoordinates();
                if (
                        previousCachedCoordinates[0] != currentCoordinates[0] ||
                        previousCachedCoordinates[1] != currentCoordinates[1]
                )
                    clearTileHighlight(previousCachedCoordinates);
                clearTileHighlight(currentCoordinates);
                GameBoardUI
                        .tiles[currentCoordinates[0]][currentCoordinates[1]]
                        .isOccupied = true;
                break;
            }

            previousCachedCoordinates = currentCoordinates.clone();
        }

        return currentCoordinates;
    }

    private static void highlightTile(int[] tileCoordinates) throws Throwable {
        String mask = "";
        for (int x = 0; x <= GameBoardUI.xCoordinateMax; x++)
            mask = mask + ' ';

        for (int y = 0; y <= GameBoardUI.yCoordinateMax; y++)
            if (!GameBoardUI.tiles[tileCoordinates[0]][tileCoordinates[1]].isOccupied)
                GameBoardUI.tiles[tileCoordinates[0]][tileCoordinates[1]].windowPtr
                        .attributeOn(FontAttributes.STANDOUT)
                        .print(0, y, mask)
                        .attributeOff(FontAttributes.STANDOUT)
                        .refresh();
    }

    private static void clearTileHighlight(int[] tileCoordinates) throws Throwable {
        String mask = "";
        for (int x = 0; x <= GameBoardUI.xCoordinateMax; x++)
            mask += ' ';

        for (int y = 0; y <= GameBoardUI.yCoordinateMax; y++)
            if (!GameBoardUI.tiles[tileCoordinates[0]][tileCoordinates[1]].isOccupied)
                GameBoardUI.tiles[tileCoordinates[0]][tileCoordinates[1]].windowPtr
                        .print(0, y, mask)
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
                currentCoordinates[0] + ", " + currentCoordinates[1] +
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