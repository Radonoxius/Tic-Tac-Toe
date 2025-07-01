package ix.radon.game.logic;

//Represents a Player who is playing the game
public class Player {
    public final String name;
    public final TileSymbol tileSymbol;

    public Player(
            String playerName,
            TileSymbol playerSymbol
    ) {
        name = playerName;
        tileSymbol = playerSymbol;
    }
}