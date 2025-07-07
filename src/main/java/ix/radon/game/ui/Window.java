package ix.radon.game.ui;

import java.lang.foreign.Arena;
import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.lang.invoke.MethodHandle;
import java.util.function.Function;

class Window {
    private final Arena arena;
    private final MemorySegment windowPtr;

    private final int xSize;
    private final int ySize;

    Window(
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

        this.arena = arena;
        this.windowPtr = (MemorySegment) createWindow.invoke(xSize, ySize, xStart, yStart);
        this.xSize = xSize;
        this.ySize = ySize;
    }

    Window makeDefaultBorder() throws Throwable {
        MethodHandle createBorder = WindowLibrary.loadFunction(
                arena,
                "create_default_border",
                FunctionDescriptor.ofVoid(ValueLayout.ADDRESS)
        );

        attributeOn(FontAttributes.BOLD);
        attributeOn(FontAttributes.DIM);
        createBorder.invoke(windowPtr);
        attributeOff(FontAttributes.DIM);
        attributeOff(FontAttributes.BOLD);

        return this;
    }

    Window makeBorder(
            char leftSide,
            char rightSide,
            char topSide,
            char bottomSide,
            char topLeftCorner,
            char topRightCorner,
            char bottomLeftCorner,
            char bottomRightCorner
    ) throws Throwable {
        MethodHandle createBorder = WindowLibrary.loadFunction(
                arena,
                "create_border",
                FunctionDescriptor.ofVoid(
                        ValueLayout.ADDRESS,
                        ValueLayout.JAVA_CHAR,
                        ValueLayout.JAVA_CHAR,
                        ValueLayout.JAVA_CHAR,
                        ValueLayout.JAVA_CHAR,
                        ValueLayout.JAVA_CHAR,
                        ValueLayout.JAVA_CHAR,
                        ValueLayout.JAVA_CHAR,
                        ValueLayout.JAVA_CHAR
                )
        );

        createBorder.invoke(
                windowPtr,
                leftSide,
                rightSide,
                topSide,
                bottomSide,
                topLeftCorner,
                topRightCorner,
                bottomLeftCorner,
                bottomRightCorner
        );

        return this;
    }

    Window print(
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

        MemorySegment strPtr = arena.allocateFrom(str);

        printString.invoke(
                windowPtr,
                xStart,
                yStart,
                strPtr
        );

        return this;
    }

    Window printFor(
            int xStart,
            int lineCount,
            String str
    ) throws Throwable {
        for (int i = 0; i < lineCount; i++)
            this.print(xStart, i, str);
        return this.print(xStart, lineCount, str);
    }

    Window printHorizontallyCentred(
            int yStart,
            String str
    ) throws Throwable {
        int xStart = (this.xSize / 2) - (str.length() / 2);

        print(
                xStart,
                yStart,
                str
        );

        return this;
    }

    Window attributeOn(FontAttributes attribute) throws Throwable {
        MethodHandle attributeOn = WindowLibrary.loadFunction(
                arena,
                "attribute_on",
                FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.JAVA_INT)
        );

        attributeOn.invoke(windowPtr, attribute.toInt());

        return this;
    }

    Window attributeOff(FontAttributes attribute) throws Throwable {
        MethodHandle attributeOff = WindowLibrary.loadFunction(
                arena,
                "attribute_off",
                FunctionDescriptor.ofVoid(ValueLayout.ADDRESS, ValueLayout.JAVA_INT)
        );

        attributeOff.invoke(windowPtr, attribute.toInt());

        return this;
    }

    Window printWithAttributes(
            Function<Window, Void> f,
            FontAttributes... attrs
    ) throws Throwable {
        for (FontAttributes attr : attrs)
            attributeOn(attr);

        f.apply(this);

        for (int i = attrs.length - 1; i > 0; i--)
            attributeOff(attrs[i]);

        return attributeOff(attrs[0]);
    }

    private int[] xLineEquartion(int y) {
        int[] xCoordinates = new int[2];

        xCoordinates[0] = 2 * y + 1;
        xCoordinates[1] = 2 * (GameBoardUI.xCoordinateMax / 2 - y) + 1;

        return xCoordinates;
    }

    Window printX(int lineCount) throws Throwable {
        for (int i = 1; i < lineCount; i++)
            this
                    .print(xLineEquartion(i)[0] - 1, i, "\\")
                    .print(xLineEquartion(i)[1] - 1, i, "/");
        return this
                .print(xLineEquartion(lineCount)[0] - 1, lineCount, "\\")
                .print(xLineEquartion(lineCount)[1] - 1, lineCount, "/");
    }

    private int[] xSemiCircleEquartion(int y) {
        //X = (x - 1)/2, x = 2y + 1 & x = 2(XMax - y) + 1, x = 2X - 1
        //(X - XMax/2)2 + (y - XMax/2)2 (XMax / 2)2,
        //X = root((XMax/2)2 - (y - XMax/2)2) + XMax/2
        int[] xCoordinates = new int[2];
        double x1, x2;

        x1 = Math.sqrt(
                (
                        Math.pow((double) GameBoardUI.xCoordinateMax / 4.0, 2.0) -
                        Math.pow(y - (double) GameBoardUI.xCoordinateMax / 4.0, 2.0)
                )
        ) + (double) GameBoardUI.xCoordinateMax / 4.0;

        x2 = -x1 + (double) GameBoardUI.xCoordinateMax / 2.0;

        xCoordinates[0] = (int) Math.round(2 * x1 - 1);
        xCoordinates[1] = (int) Math.round(2 * x2 - 1);

        return xCoordinates;
    }

    Window printO(int lineCount) throws Throwable {
        for (int i = 0; i < lineCount; i++)
            this
                    .print(xSemiCircleEquartion(i)[0], i, "+")
                    .print(xSemiCircleEquartion(i)[1] + 2, i, "+");
        return this
                .print(xSemiCircleEquartion(lineCount)[0] - 2, lineCount, "+")
                .print(xSemiCircleEquartion(lineCount)[1] + 4, lineCount, "+");
    }

    Window refresh() throws Throwable {
        MethodHandle windowRefresh = WindowLibrary.loadFunction(
                arena,
                "window_refresh",
                FunctionDescriptor.ofVoid(ValueLayout.ADDRESS)
        );

        windowRefresh.invoke(windowPtr);

        return this;
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