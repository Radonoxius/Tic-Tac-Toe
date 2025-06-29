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

void window_refresh(WINDOW *w) {
    wrefresh(w);
}

void delete_window(WINDOW *w) {
    delwin(w);
}