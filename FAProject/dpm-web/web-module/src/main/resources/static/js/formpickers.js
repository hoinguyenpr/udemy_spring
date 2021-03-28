(function ($) {
    'use strict';
    if ($("#timepicker-example").length) {
        $('#timepicker-example').datetimepicker({
            format: 'HH:mm',
            autoclose: true,
        });
    }
    if ($(".color-picker").length) {
        $('.color-picker').asColorPicker();
    }
    if ($("#datepicker-popup").length) {
        $('#datepicker-popup').datepicker({
            // enableOnReadonly: true,
            autoclose: true,
            todayHighlight: true,
            format: 'dd/mm/yyyy',
        });
    }
    if ($("#datepicker-popup2").length) {
        $('#datepicker-popup2').datepicker({
            // enableOnReadonly: true,
            autoclose: true,
            todayHighlight: true,
            format: 'dd/mm/yyyy',
        });
    }
    if ($("#inline-datepicker").length) {
        $('#inline-datepicker').datepicker({
            // enableOnReadonly: true,
            todayHighlight: true,
        });
    }
    if ($(".datepicker-autoclose").length) {
        $('.datepicker-autoclose').datepicker({
            autoclose: true
        });
    }
})(jQuery);