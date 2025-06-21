package ix.radon.game.ui;

import ix.radon.game.ui.ffi.ArtistLibrary;

import java.lang.foreign.*;
import java.lang.invoke.MethodHandle;
import java.util.function.Supplier;

public class GameUI {
    public static void Start(Supplier<Void> f) throws RuntimeException {
        try(Arena arena = Arena.ofConfined()) {
            MethodHandle startGame = ArtistLibrary.loadFunction(
                    arena,
                    "start_game",
                    FunctionDescriptor.ofVoid()
            );

            startGame.invoke();
            ScoreBoard.createBoards(arena);

            f.get();

            ScoreBoard.deleteBoards();

            showExitWindow(arena);
            End();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private static void showExitWindow(Arena arena) throws Throwable {
        Window exitWindow = Window.createWindow(
                arena,
                26,
                3,
                (terminalSize().xSize - 26) / 2,
                (terminalSize().ySize - 3) / 2
        );
        exitWindow.createBorder();
        exitWindow.printString(1, 1, " Press any key to exit. ");
        exitWindow.refresh();
        exitWindow.delete();
    }

    private static void End() throws RuntimeException {
        try(Arena arena = Arena.ofConfined()) {
            MethodHandle endGame = ArtistLibrary.loadFunction(
                    arena,
                    "end_game",
                    FunctionDescriptor.ofVoid()
            );

            endGame.invoke();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    static Terminal terminalSize() throws RuntimeException {
        try(Arena arena = Arena.ofConfined()) {
            MethodHandle getTerminalDimension = ArtistLibrary.loadFunction(
                    arena,
                    "get_terminal_dimension",
                    FunctionDescriptor.of(ValueLayout.ADDRESS)
            );

            MemorySegment dimensionArraySegmentPtr =
                    (MemorySegment) getTerminalDimension.invoke();

            MemorySegment dimensionArraySegment =
                    dimensionArraySegmentPtr.reinterpret(8, arena, ArtistLibrary.free());

            int[] dimension = new int[2];

            MemorySegment.copy(
                    dimensionArraySegment,
                    ValueLayout.JAVA_INT,
                    0,
                    MemorySegment.ofArray(dimension),
                    ValueLayout.JAVA_INT,
                    0,
                    2
            );

            return new Terminal(dimension[0], dimension[1]);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}

class Terminal {
    public final int xSize;
    public final int ySize;

    Terminal(int xSize, int ySize) {
        this.xSize = xSize;
        this.ySize = ySize;
    }
}