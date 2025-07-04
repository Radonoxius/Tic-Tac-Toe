package ix.radon.game.logic;

//Represents a Player who is playing the game
public class Player {
    //Player's name
    public final String name;
    //TileSymbol the player will use in the match
    public final TileSymbol tileSymbol;

    public Player(
            String playerName,
            TileSymbol playerSymbol
    ) {
        name = playerName;
        tileSymbol = playerSymbol;
    }
}