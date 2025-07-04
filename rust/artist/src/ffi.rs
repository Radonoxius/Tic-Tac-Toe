#[link(name = "helper", kind = "static")]
unsafe extern "C" {
    pub(crate) fn init();

    pub(crate) fn terminal_dimension(dimen: *mut i32);

    pub(crate) fn get_input() -> u8;

    pub(crate) fn std_print_string(x_start: u32, y_start: u32, str: *const u8);

    pub(crate) fn std_attr_on(selector: i32);

    pub(crate) fn std_attr_off(selector: i32);

    pub(crate) fn std_refresh();

    pub(crate) fn end();
}