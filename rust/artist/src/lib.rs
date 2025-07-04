use std::mem::ManuallyDrop;

use crate::ffi::{end, get_input, init, std_print_string, std_attr_off, std_attr_on, std_refresh, terminal_dimension};

mod ffi;

#[unsafe(no_mangle)]
pub extern "C" fn get_terminal_dimension() -> *const i32 {
    let mut dimen = ManuallyDrop::new(Box::new([0; 2]));
    unsafe {
        terminal_dimension(dimen.as_mut_ptr());
    }
    dimen.as_ptr()
}

#[unsafe(no_mangle)]
pub extern "C" fn stdscr_get_input() -> u8 {
    unsafe {
        get_input()
    }
}

#[unsafe(no_mangle)]
pub extern "C" fn stdscr_print(x_start: u32, y_start: u32, str: *const u8) {
    unsafe {
        std_print_string(x_start, y_start, str)
    }
}

#[unsafe(no_mangle)]
pub extern "C" fn stdscr_refresh() {
    unsafe {
        std_refresh();
    }
}

#[unsafe(no_mangle)]
pub extern "C" fn stdscr_attr_on(selector: i32) {
    unsafe {
        std_attr_on(selector);
    }
}

#[unsafe(no_mangle)]
pub extern "C" fn stdscr_attr_off(selector: i32) {
    unsafe {
        std_attr_off(selector);
    }
}

#[unsafe(no_mangle)]
pub extern "C" fn start_game() {
    unsafe {
        init();
    }
}

#[unsafe(no_mangle)]
pub extern "C" fn end_game() {
    unsafe {
        end();
    }
}
