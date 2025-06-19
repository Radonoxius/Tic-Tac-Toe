use std::mem::ManuallyDrop;

use crate::ffi::{init, terminal_dimension, end};

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