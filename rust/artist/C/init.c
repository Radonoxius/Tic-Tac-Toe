#include <ncurses.h>

void init() {
    initscr();
    start_color();

    init_pair(1, COLOR_RED, COLOR_BLACK);
    init_pair(2, COLOR_GREEN, COLOR_BLACK);
    init_pair(3, COLOR_YELLOW, COLOR_BLACK);

    cbreak();
    noecho();
    keypad(stdscr, TRUE);
    curs_set(0);

    refresh();
}

void end() {
    refresh();
    getch();
    endwin();
}

uint8_t get_input() {
    int ch = getch();

    if (ch == KEY_ENTER | ch == '\n')
        return 0;
    else if (ch == KEY_UP)
        return 1;
    else if (ch == KEY_DOWN)
        return 2;
    else if (ch == KEY_LEFT)
        return 3;
    else if (ch == KEY_RIGHT)
        return 4;
    else
        return 5;
}

void std_attr_on(int selector) {
    if (selector == 0)
        attr_on(A_NORMAL, NULL);
    else if (selector == 1)
        attr_on(A_STANDOUT, NULL);
    else if (selector == 2)
        attr_on(A_DIM, NULL);
    else if (selector == 3)
        attr_on(A_BOLD, NULL);
    else if (selector == 4)
        attr_on(COLOR_PAIR(1), NULL);
    else if (selector == 5)
        attr_on(COLOR_PAIR(2), NULL);
}

void std_print_string(int x_start, int y_start, char *str) {
    mvprintw(y_start, x_start, "%s", str);
}

void std_attr_off(int selector) {
    if (selector == 0)
        attr_off(A_NORMAL, NULL);
    else if (selector == 1)
        attr_off(A_STANDOUT, NULL);
    else if (selector == 2)
        attr_off(A_DIM, NULL);
    else if (selector == 3)
        attr_off(A_BOLD, NULL);
    else if (selector == 4)
        attr_off(COLOR_PAIR(1), NULL);
    else if (selector == 5)
        attr_off(COLOR_PAIR(2), NULL);
}

void std_refresh() {
    refresh();
}

void terminal_dimension(__int32_t *dimen) {
    dimen[0] = COLS;
    dimen[1] = LINES;
}