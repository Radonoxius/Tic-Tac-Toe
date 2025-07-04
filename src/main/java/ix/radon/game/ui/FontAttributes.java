package ix.radon.game.ui;

//Represents the Text-Attributes of text in the Terminal
enum FontAttributes {
    NORMAL,
    STANDOUT,
    DIM,
    BOLD,
    RED,
    GREEN,
    YELLOW;

    int toInt() {
        if (this == NORMAL)
            return 0;
        else if (this == STANDOUT)
            return 1;
        else if (this == DIM)
            return 2;
        else if (this == BOLD)
            return 3;
        else if (this == RED)
            return 4;
        else if (this == GREEN)
            return 5;
        else if (this == YELLOW)
            return 6;

        return -1;
    }
}