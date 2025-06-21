#include <ncurses.h>

void init() {
    initscr();
    cbreak();
    keypad(stdscr, TRUE);
    curs_set(0);
    refresh();
}

void end() {
    refresh();
    getch();
    endwin();
}

void terminal_dimension(__int32_t *dimen) {
    dimen[0] = COLS;
    dimen[1] = LINES;
}