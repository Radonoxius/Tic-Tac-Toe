package ix.radon.game.ui;

import java.lang.foreign.*;
import java.lang.invoke.MethodHandle;
import java.nio.file.Path;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Consumer;

class WindowLibrary {
    private final static String WindowLibraryPath =
            "nativeLibs/libwindow.so";

    private final static Linker linker = Linker.nativeLinker();

    static MethodHandle loadFunction(
            Arena arena,
            String functionName,
            FunctionDescriptor functionDescriptor
    ) throws NoSuchElementException {
        SymbolLookup artistLookup = SymbolLookup.libraryLookup(
                Path.of(WindowLibraryPath),
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

    static Consumer<MemorySegment> free() {
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