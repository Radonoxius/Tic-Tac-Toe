package ix.radon.game.ui;

import ix.radon.game.logic.GameBoard;
import java.lang.foreign.*;
import java.lang.invoke.MethodHandle;
import java.util.function.Function;

public class GameUI {
    public static void Start(
            Function<GameBoard, Void> f,
            GameBoard board
    ) throws RuntimeException {
        try(Arena arena = Arena.ofConfined()) {
            MethodHandle startGame = ArtistLibrary.loadFunction(
                    arena,
                    "start_game",
                    FunctionDescriptor.ofVoid()
            );

            System.out.println("INSTRUCTIONS:\n");
            System.out.println("Use ARROW keys to select the tiles.");
            System.out.println("Use the ENTER key to confirm selection.\n");
            System.out.println("Press any key to start the game!");
            int _ = System.in.read();

            startGame.invoke();
            setTerminalSize(arena);

            ScoreBoard.init(arena, board);
            InputHandler.init(arena, board);
            GameBoardUI.init(arena);

            f.apply(board);

            ScoreBoard.deleteBoards();
            GameBoardUI.deleteTiles();

            Thread.sleep(500);
            declareWinner(arena, board);

            End(arena);

            System.in.close();
            System.out.println("Thanks for playing!");
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private static void declareWinner(
            Arena arena,
            GameBoard board
    ) throws Throwable {
        if (ScoreBoard.getComputerScore() > ScoreBoard.getPlayerScore()) {
            int xSize = 25;

            if (board.computer.name.length() + 19 > 25)
                xSize = board.computer.name.length() + 19;

            new Window(
                    arena,
                    xSize,
                    7,
                    (Terminal.xSize - xSize) / 2,
                    (Terminal.ySize - 7) / 2
                    )
                    .makeDefaultBorder()
                    .applyPrintAttributes(
                            window -> {
                                try {
                                    window.printHorizontallyCentred(
                                            1,
                                            "GAME OVER!"
                                    );
                                } catch (Throwable e) {
                                    throw new RuntimeException(e);
                                }
                                return null;
                            },
                            FontAttributes.BOLD,
                            FontAttributes.RED
                    )
                    .printHorizontallyCentred(
                            3,
                            board.computer.name + " won the match!"
                    )
                    .attributeOn(FontAttributes.BOLD)
                    .printHorizontallyCentred(5, "Press any key to exit")
                    .attributeOff(FontAttributes.BOLD)
                    .refresh()
                    .delete();
        }
        else if (ScoreBoard.getComputerScore() < ScoreBoard.getPlayerScore()) {
            int xSize = 25;

            if (board.player.name.length() + 19 > 25)
                xSize = board.player.name.length() + 19;

            new Window(
                    arena,
                    xSize,
                    7,
                    (Terminal.xSize - xSize) / 2,
                    (Terminal.ySize - 7) / 2
                    )
                    .makeDefaultBorder()
                    .applyPrintAttributes(
                            window -> {
                                try {
                                    window.printHorizontallyCentred(
                                            1,
                                            "GAME OVER!"
                                    );
                                } catch (Throwable e) {
                                    throw new RuntimeException(e);
                                }
                                return null;
                            },
                            FontAttributes.BOLD,
                            FontAttributes.GREEN
                    )
                    .printHorizontallyCentred(
                            3,
                            board.player.name + " won the match!"
                    )
                    .attributeOn(FontAttributes.BOLD)
                    .printHorizontallyCentred(5, "Press any key to exit")
                    .attributeOff(FontAttributes.BOLD)
                    .refresh()
                    .delete();
        }
        else
            new Window(
                    arena,
                    25,
                    7,
                    (Terminal.xSize - 25) / 2,
                    (Terminal.ySize - 7) / 2
                    )
                    .makeDefaultBorder()
                    .applyPrintAttributes(
                            window -> {
                                try {
                                    window.printHorizontallyCentred(
                                            1,
                                            "GAME OVER!"
                                    );
                                } catch (Throwable e) {
                                    throw new RuntimeException(e);
                                }
                                return null;
                            },
                            FontAttributes.BOLD,
                            FontAttributes.YELLOW
                    )
                    .printHorizontallyCentred(
                            3,
                            "Its a Draw!"
                    )
                    .attributeOn(FontAttributes.BOLD)
                    .printHorizontallyCentred(5, "Press any key to exit")
                    .attributeOff(FontAttributes.BOLD)
                    .refresh()
                    .delete();
    }

    private static void End(Arena arena) throws Throwable {
        MethodHandle endGame = ArtistLibrary.loadFunction(
                arena,
                "end_game",
                FunctionDescriptor.ofVoid()
        );

        endGame.invoke();
    }

    private static void setTerminalSize(Arena arena) throws Throwable {
        MethodHandle getTerminalDimension = ArtistLibrary.loadFunction(
                arena,
                "get_terminal_dimension",
                FunctionDescriptor.of(ValueLayout.ADDRESS)
        );

        MemorySegment dimensionArraySegmentPtr =
                (MemorySegment) getTerminalDimension.invoke();

        MemorySegment dimensionArraySegment =
                dimensionArraySegmentPtr.reinterpret(8, arena, ArtistLibrary.free());

        Terminal.xSize = dimensionArraySegment.get(ValueLayout.JAVA_INT, 0);
        Terminal.ySize = dimensionArraySegment.get(ValueLayout.JAVA_INT, 4);
    }
}