#include <ncurses.h>

WINDOW *create_window(int x_size, int y_size, int x_start, int y_start) {
    WINDOW *w = newwin(y_size, x_size, y_start, x_start);
    wrefresh(w);
    return w;
}

void create_border(WINDOW *w) {
    wborder(w, '|', '|', '-', '-', '+', '+', '+', '+');
}

void print_string(WINDOW *w, int x_start, int y_start, char *str) {
    mvwprintw(w, y_start, x_start, "%s", str);
}

void window_refresh(WINDOW *w) {
    wrefresh(w);
}

void delete_window(WINDOW *w) {
    wborder(w, ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ');
    delwin(w);
}