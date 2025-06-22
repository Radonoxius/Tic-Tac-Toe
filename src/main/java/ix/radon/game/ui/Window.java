package ix.radon.game.ui;

import java.lang.foreign.Arena;
import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.lang.invoke.MethodHandle;

record Window(Arena arena, MemorySegment windowPtr) {
    static Window createWindow(
            Arena arena,
            int xSize,
            int ySize,
            int xStart,
            int yStart
    ) throws Throwable {
        MethodHandle createWindow = WindowLibrary.loadFunction(
                arena,
                "create_window",
                FunctionDescriptor.of(
                        ValueLayout.ADDRESS,
                        ValueLayout.JAVA_INT,
                        ValueLayout.JAVA_INT,
                        ValueLayout.JAVA_INT,
                        ValueLayout.JAVA_INT
                )
        );

        return new Window(arena, (MemorySegment) createWindow.invoke(xSize, ySize, xStart, yStart));
    }

    void createBorder() throws Throwable {
        MethodHandle createBorder = WindowLibrary.loadFunction(
                arena,
                "create_border",
                FunctionDescriptor.ofVoid(ValueLayout.ADDRESS)
        );

        createBorder.invoke(windowPtr);
    }

    void printString(
            int xStart,
            int yStart,
            String str
    ) throws Throwable {
        MethodHandle printString = WindowLibrary.loadFunction(
                arena,
                "print_string",
                FunctionDescriptor.ofVoid(
                        ValueLayout.ADDRESS,
                        ValueLayout.JAVA_INT,
                        ValueLayout.JAVA_INT,
                        ValueLayout.ADDRESS
                )
        );

        MemorySegment strSegment = arena.allocateUtf8String(str);

        printString.invoke(
                windowPtr,
                xStart,
                yStart,
                strSegment
        );
    }

    void refresh() throws Throwable {
        MethodHandle windowRefresh = WindowLibrary.loadFunction(
                arena,
                "window_refresh",
                FunctionDescriptor.ofVoid(ValueLayout.ADDRESS)
        );

        windowRefresh.invoke(windowPtr);
    }

    void delete() throws Throwable {
        MethodHandle deleteWindow = WindowLibrary.loadFunction(
                arena,
                "delete_window",
                FunctionDescriptor.ofVoid(ValueLayout.ADDRESS)
        );

        deleteWindow.invoke(windowPtr);
    }
}