package ix.radon.game.ui.ffi;

import java.lang.foreign.*;
import java.lang.invoke.MethodHandle;
import java.nio.file.Path;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Consumer;

public class ArtistLibrary {
    private final static String ArtistLibraryPath = "nativeLibs/libartist.so";

    private final static Linker linker = Linker.nativeLinker();

    public static MethodHandle loadFunction(
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

    public static Consumer<MemorySegment> free() {
        var free_addr = linker.defaultLookup().find("free").orElseThrow();
        MethodHandle free = linker.downcallHandle(
                free_addr,
                FunctionDescriptor.ofVoid(ValueLayout.ADDRESS)
        );

        return heapMemory -> {
            try {
                free.invokeExact(heapMemory);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        };
    }
}
