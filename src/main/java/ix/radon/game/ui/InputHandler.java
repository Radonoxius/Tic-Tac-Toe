package ix.radon.game.ui;

import java.lang.foreign.Arena;
import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.lang.invoke.MethodHandle;

//Handles input from the user
public class InputHandler {
    private final Arena arena;

    private final int[] CurrentCoordinates;

    InputHandler(Arena arena) {
        CurrentCoordinates = new int[] {0, 0};

        this.arena = arena;
    }

    //Returns the tile coordinates - {x, y} - selected by the user
    public int[] getInput() throws Throwable {
        MethodHandle stdscrGetInput = ArtistLibrary.loadFunction(
                arena,
                "stdscr_get_input",
                FunctionDescriptor.of(ValueLayout.JAVA_BYTE)
        );

        printCurrentCoordinates();
        while (true) {
            int key = (int) stdscrGetInput.invoke();

            if (key == 1 && CurrentCoordinates[1] != 0) {
                CurrentCoordinates[1] -= 1;
                printCurrentCoordinates();
            }
            else if (key == 2 && CurrentCoordinates[1] != 2) {
                CurrentCoordinates[1] += 1;
                printCurrentCoordinates();
            }
            else if (key == 3 && CurrentCoordinates[0] != 0) {
                CurrentCoordinates[0] -= 1;
                printCurrentCoordinates();
            }
            else if (key == 4 && CurrentCoordinates[0] != 2) {
                CurrentCoordinates[0] += 1;
                printCurrentCoordinates();
            }
            else if (key == 0) {
                clearCurrentCoordinates();
                break;
            }
        }

        return CurrentCoordinates;
    }

    private void refresh() throws Throwable {
        MethodHandle stdscrRefresh = ArtistLibrary.loadFunction(
                arena,
                "stdscr_refresh",
                FunctionDescriptor.ofVoid()
        );

        stdscrRefresh.invoke();
    }

    private void standoutAttributeOn() throws Throwable {
        MethodHandle stdscrAttrOn = ArtistLibrary.loadFunction(
                arena,
                "stdscr_attr_on",
                FunctionDescriptor.ofVoid(
                        ValueLayout.JAVA_INT
                )
        );

        stdscrAttrOn.invoke(1);
    }

    private void standoutAttributeOff() throws Throwable {
        MethodHandle stdscrAttrOff = ArtistLibrary.loadFunction(
                arena,
                "stdscr_attr_off",
                FunctionDescriptor.ofVoid(
                        ValueLayout.JAVA_INT
                )
        );

        stdscrAttrOff.invoke(1);
    }

    private void printCurrentCoordinates() throws Throwable {
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
                CurrentCoordinates[0] + ", " + CurrentCoordinates[1] +
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

    private void clearCurrentCoordinates() throws Throwable {
        MethodHandle stdscrPrint = ArtistLibrary.loadFunction(
                arena,
                "stdscr_print",
                FunctionDescriptor.ofVoid(
                        ValueLayout.JAVA_INT,
                        ValueLayout.JAVA_INT,
                        ValueLayout.ADDRESS
                )
        );

        String mask = "                                                  ";

        MemorySegment strPtr = arena.allocateFrom(mask);

        stdscrPrint.invoke(
                Terminal.xSize - 50,
                Terminal.ySize - 1,
                strPtr
        );
        refresh();
    }
}