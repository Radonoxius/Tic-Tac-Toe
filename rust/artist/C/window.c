#include <ncurses.h>

WINDOW *create_window(int x_size, int y_size, int x_start, int y_start) {
    WINDOW *w = newwin(y_size, x_size, y_start, x_start);
    wrefresh(w);
    return w;
}

void create_default_border(WINDOW *w) {
    wborder(w, '|', '|', '-', '-', '+', '+', '+', '+');
}

void create_border(WINDOW *w, char ls, char rs, char ts, char bs, char tl, char tr, char bl, char br) {
    wborder(w, ls, rs, ts, bs, tl, tr, bl, br);
}

void print_string(WINDOW *w, int x_start, int y_start, char *str) {
    mvwprintw(w, y_start, x_start, "%s", str);
}

void attribute_on(WINDOW *w, int selector) {
    if (selector == 0)
        wattr_on(w, A_NORMAL, NULL);
    else if (selector == 1)
        wattr_on(w, A_STANDOUT, NULL);
    else if (selector == 2)
        wattr_on(w, A_DIM, NULL);
    else if (selector == 3)
        wattr_on(w, A_BOLD, NULL);
    else if (selector == 4)
        wattr_on(w, COLOR_PAIR(1), NULL);
    else if (selector == 5)
        wattr_on(w, COLOR_PAIR(2), NULL);
    else if (selector == 6)
        wattr_on(w, COLOR_PAIR(3), NULL);
}

void attribute_off(WINDOW *w, int selector) {
    if (selector == 0)
        wattr_off(w, A_NORMAL, NULL);
    else if (selector == 1)
        wattr_off(w, A_STANDOUT, NULL);
    else if (selector == 2)
        wattr_off(w, A_DIM, NULL);
    else if (selector == 3)
        wattr_off(w, A_BOLD, NULL);
    else if (selector == 4)
        wattr_off(w, COLOR_PAIR(1), NULL);
    else if (selector == 5)
        wattr_off(w, COLOR_PAIR(2), NULL);
    else if (selector == 6)
        wattr_off(w, COLOR_PAIR(3), NULL);
}

void window_refresh(WINDOW *w) {
    wrefresh(w);
}

void delete_window(WINDOW *w) {
    delwin(w);
}