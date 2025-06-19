package ix.radon.game;

import ix.radon.game.logic.GameBoard;
import ix.radon.game.logic.random.Coin;
import ix.radon.game.ui.GameUI;

import java.io.Console;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicReference;

public class Main {
    public static void main(String[] args) {
        GameBoard board = new GameBoard();
        System.out.println(board.getTileSymbol(0, 0));

        GameUI.Start(() -> {
            int[] dimen = GameUI.terminalSize();
            return null;
        });
    }
}