package ix.radon.game.ui;

import java.lang.foreign.*;
import java.lang.invoke.MethodHandle;
import java.nio.file.Path;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class GameUI {
    private final static String ArtistLibraryPath =
            "nativeLibs/libartist.so";
    private final static Linker linker = Linker.nativeLinker();

    public static void Start(Supplier<Void> f) throws RuntimeException {
        try(Arena arena = Arena.ofConfined()) {
            SymbolLookup artistLookup = SymbolLookup.libraryLookup(
                    Path.of(ArtistLibraryPath),
                    arena
            );

            Optional<MemorySegment> optionalStartGameAddress =
                    artistLookup.find("start_game");

            if (optionalStartGameAddress.isPresent()) {
                MemorySegment startGameAddress = optionalStartGameAddress.get();
                FunctionDescriptor startGameDescriptor = FunctionDescriptor.ofVoid();

                MethodHandle startGame = linker.downcallHandle(
                        startGameAddress,
                        startGameDescriptor
                );

                startGame.invoke();

                f.get();

                End();
            } else {
                throw new NoSuchElementException();
            }
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private static void End() throws RuntimeException {
        try(Arena arena = Arena.ofConfined()) {
            SymbolLookup artistLookup = SymbolLookup.libraryLookup(
                    Path.of(ArtistLibraryPath),
                    arena
            );

            Optional<MemorySegment> optionalEndGameAddress =
                    artistLookup.find("end_game");

            if (optionalEndGameAddress.isPresent()) {
                MemorySegment endGameAddress = optionalEndGameAddress.get();
                FunctionDescriptor endGameDescriptor = FunctionDescriptor.ofVoid();

                MethodHandle endGame = linker.downcallHandle(
                        endGameAddress,
                        endGameDescriptor
                );

                endGame.invoke();
            } else {
                throw new NoSuchElementException();
            }
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static int[] terminalSize() throws RuntimeException {
        try(Arena arena = Arena.ofConfined()) {
            SymbolLookup artistLookup = SymbolLookup.libraryLookup(
                    Path.of(ArtistLibraryPath),
                    arena
            );

            Optional<MemorySegment> optionalGetTerminalDimensionAddress =
                    artistLookup.find("get_terminal_dimension");

            if (optionalGetTerminalDimensionAddress.isPresent()) {
                MemorySegment getTerminalDimensionAddress =
                        optionalGetTerminalDimensionAddress.get();
                FunctionDescriptor getTerminalDimensionDescriptor = FunctionDescriptor.of(
                        ValueLayout.ADDRESS
                );

                MethodHandle getTerminalDimension = linker.downcallHandle(
                        getTerminalDimensionAddress,
                        getTerminalDimensionDescriptor
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
            } else {
                throw new NoSuchElementException();
            }
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
