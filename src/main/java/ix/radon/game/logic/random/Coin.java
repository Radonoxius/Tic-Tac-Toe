package ix.radon.game.logic.random;

public enum Coin {
    HEADS,
    TAILS;

    public static Coin toss() {
        if (Math.round(Math.random()) == 1)
            return Coin.HEADS;
        else
            return Coin.TAILS;
    }
}
