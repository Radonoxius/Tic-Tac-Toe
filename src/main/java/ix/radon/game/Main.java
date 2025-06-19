package ix.radon.game;

import ix.radon.game.logic.GameBoard;
import ix.radon.game.ui.GameUI;

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