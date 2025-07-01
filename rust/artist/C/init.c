#include <ncurses.h>

void init() {
    initscr();
    start_color();

    init_pair(1, COLOR_RED, COLOR_BLACK);
    init_pair(2, COLOR_GREEN, COLOR_BLACK);
    init_pair(3, COLOR_YELLOW, COLOR_BLACK);

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