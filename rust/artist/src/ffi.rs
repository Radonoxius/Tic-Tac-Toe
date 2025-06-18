#[link(name = "helper", kind = "static")]
unsafe extern "C" {
    pub(crate) fn init();


    pub(crate) fn terminal_dimension(dimen: *mut i32);

    pub(crate) fn end();
}