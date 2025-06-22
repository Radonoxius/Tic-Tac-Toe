package ix.radon.game.logic.random;

//Represents a Coin
public enum Coin {
    //Should be obvious. Right?
    HEADS,
    TAILS;

    //The toss function returns a Coin which can be Coin.HEADS or Coin.TAILS
    public static Coin toss() {
        if (Math.round(Math.random()) == 1)
            return Coin.HEADS;
        else
            return Coin.TAILS;
    }
}
