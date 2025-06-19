package ix.radon.game.ui;

import java.lang.foreign.*;
import java.lang.invoke.MethodHandle;
import java.nio.file.Path;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Supplier;

public class GameUI {
    private final static String ArtistLibraryPath =
            "nativeLibs/libartist.so";
    private final static Linker linker = Linker.nativeLinker();

    private static MethodHandle loadArtistFunction(
            Arena arena,
            String functionName,
            FunctionDescriptor functionDescriptor
    ) throws NoSuchElementException {
        SymbolLookup artistLookup = SymbolLookup.libraryLookup(
                Path.of(ArtistLibraryPath),
                arena
        );

        Optional<MemorySegment> optionalFunctionAddress =
                artistLookup.find(functionName);

        if (optionalFunctionAddress.isPresent()) {
            MemorySegment functionAddress = optionalFunctionAddress.get();

            return linker.downcallHandle(
                    functionAddress,
                    functionDescriptor
            );
        } else {
            throw new NoSuchElementException();
        }
    }

    public static void Start(Supplier<Void> f) throws RuntimeException {
        try(Arena arena = Arena.ofConfined()) {
            MethodHandle startGame = loadArtistFunction(
                    arena,
                    "start_game",
                    FunctionDescriptor.ofVoid()
            );

            startGame.invoke();

            f.get();

            End();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private static void End() throws RuntimeException {
        try(Arena arena = Arena.ofConfined()) {
            MethodHandle endGame = loadArtistFunction(
                    arena,
                    "end_game",
                    FunctionDescriptor.ofVoid()
            );

            endGame.invoke();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static int[] terminalSize() throws RuntimeException {
        try(Arena arena = Arena.ofConfined()) {
            MethodHandle getTerminalDimension = loadArtistFunction(
                    arena,
                    "get_terminal_dimension",
                    FunctionDescriptor.of(ValueLayout.ADDRESS)
            );

            MemorySegment dimensionArraySegmentPtr =
                    (MemorySegment) getTerminalDimension.invoke();

            MemorySegment dimensionArraySegment =
                    dimensionArraySegmentPtr.reinterpret(8, arena, null);

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

            return dimension;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
