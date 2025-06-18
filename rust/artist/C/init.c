#include <ncurses.h>

void init() {
    initscr();
    keypad(stdscr, TRUE);
    cbreak();
    getch();
}

void end() {
    endwin();
}

void terminal_dimension(__int32_t *dimen) {
    dimen[0] = COLS;
    dimen[1] = LINES;
}