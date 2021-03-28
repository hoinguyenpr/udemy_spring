/**
 * Author: <b> TruongDD </b>
 */

$(document).ready(function() {
    $(".form-need-validate").each(function() {
        $(this).validate({
            rules: {
                inputTime: {
                    required: true
                },
                inputDate: {
                    required: true,
                    dateISO: true
                },
                moisture: {
                    required: true,
                    min: 0,
                    max: 2.0,
                    step: 0.1
                },
                pressure: {
                    required: true,
                    min: 100,
                    max: 200,
                    step: 1
                },
                inputTemp: {
                    required: true,
                    min: 160,
                    max: 200,
                    step: 0.1
                },
                pH: {
                    required: true,
                    min: 6,
                    max: 7,
                    step: 0.1
                },
                outputTemp: {
                    required: true,
                    min: 90,
                    max: 95,
                    step: 0.1
                },
            }
        })
    })

    $("#setDefault").validate({
        rules: {
            inputDate: {
                dateISO: true
            },
            moisture: {
                min: 0,
                max: 2.0,
                step: 0.1
            },
            pressure: {
                min: 100,
                max: 200,
                step: 1
            },
            inputTemp: {
                min: 160,
                max: 200,
                step: 0.1
            },
            pH: {
                min: 6,
                max: 7,
                step: 0.1
            },
            outputTemp: {
                min: 90,
                max: 95,
                step: 0.1
            },
        }
    })

});

jQuery.validator.setDefaults({
    onfocusout: function(e) {
        this.element(e);
    },
    onkeyup: function(element) { $(element).valid() },

    highlight: function(element) {
        jQuery(element).closest('.form-control').addClass('is-invalid');
    },
    unhighlight: function(element) {
        jQuery(element).closest('.form-control').removeClass('is-invalid');
        jQuery(element).closest('.form-control').addClass('is-valid');
    },

    errorElement: 'div',
    errorClass: 'invalid-feedback',
    errorPlacement: function(error, element) {
        if (element.parent('.input-group-prepend').length) {
            $(element).siblings(".invalid-feedback").append(error);
            error.insertAfter(element.parent());
        } else {
            error.insertAfter(element);
        }
    },
});

function clockUpdate() {
    let today = new Date();
    let current_time = ("0" + today.getHours()).slice(-2) + ":" +
        ("0" + today.getMinutes()).slice(-2);
    //document.getElementById("myTime").value = current_time;

    let myTimes = document.getElementsByClassName("myTime");

    for (let index = 0; index < myTimes.length; index++) {
        const element = myTimes[index];
        element.value = current_time;
    }
}

function calendarUpdate() {
    let today = new Date();
    let current_day = today.getFullYear() + "-" +
        ("0" + (today.getMonth() + 1)).slice(-2) + "-" +
        ("0" + today.getDate()).slice(-2);
    //document.getElementById("myCalendar").value = current_day;
    let myDates = document.getElementsByClassName("myCalendar");
    for (let index = 0; index < myDates.length; index++) {
        const element = myDates[index];
        element.value = current_day
    }
}

$(".remove-record").click(function() {
    var id = $(this).attr("data-remove");
    $(".deleteId").attr("value", id);

})

$(".set-record-as-default").click(function() {
    var id = $(this).attr("data-set-default");
    $(".setDefaultId").attr("value", id);

})

function setPageId(page_Id) {
    let pageId = page_Id
    $("#pageId").val(pageId)
    $("#pageSize").val($("#select-pageSize").val())
}
$('#select-pageSize').change(function() {
    $("#pageId").val(0)
    $("#pageSize").val($(this).val())
    $('#filterForm').submit();
});
// FUNCTION TO FORMATTING TOAST DIALOG

$(document).ready(function() {
    $('select').select2({
        // dropdownParent: $("#add-modal"),
        // dropdownParent: $("#config-modal"),
        width: '100%'
    });

});

$(document).ready(function() {
    $('select').addClass('js-example-basic-single')
    $('.select2-container').addClass("col-md-8 col-sm-12 col-xs-12 m-0 p-0")
})

showToastDialog = function(heading, text, icon) {
    'use strict';
    $.toast({
        heading: heading,
        text: text,
        showHideTransition: 'slide',
        icon: icon,
        loaderBg: '#57c7d4',
        position: 'top-right'
    })
};

$("#saveRecord").submit(function() {
    if ($("#saveRecord").valid()) {
        $("#btnSaveRecord").attr("disabled", "disabled");
    }
});

$(".update-record")
    .click(
        function() {
            var id = $(this).attr("data-update");
            $(".updateId").attr("value", id);

            $.ajax({
                type: "GET",
                contentType: "application/json",
                url: "/sieve-drying/getRecordBy?id=" +
                    id,
                data: null,
                dataType: 'json',
                timeout: 100000,
                success: function(data) {
                    console.log("SUCCESS: ", data);
                    //input value into input
                    $("#updateRecordId").val(data.id);
                    $("#update-worker").val(data.worker.id);
                    $("#update-QC").val(data.qc.id);
                    $("#update-verifier").val(data.verifier.id);
                    $("#update-product-type").val(data.typeProduct.id);
                    $("#update-inputDate").val(data.inputDate);
                    $("#update-inputTime").val(('0' + data.inputTime[0]).slice(-2) + ':' + ('0' + data.inputTime[1]).slice(-2));
                    $("#update-productLot").val(data.lotId.id);
                    $("#update-MaterialLot").val(data.ingredientBatch.id);
                    $("#update-machine").val(data.machine.id);
                    $("#update-moisture").val(data.moisture);
                    $("#update-pressure").val(data.pressure);
                    $("#update-inputTemp").val(data.inputTemp);
                    $("#update-pH").val(data.pH);
                    $("#update-outputTemp").val(data.outputTemp);
                    if (data.impurities == true) {
                        $("#update-impurities-true").attr('checked', true)
                    } else {
                        $("#update-impurities-false").attr('checked', true)
                    }
                    if (data.size == true) {
                        $("#update-size-true").attr('checked', true)
                    } else {
                        $("#update-size-false").attr('checked', true)
                    }
                    if (data.color == true) {
                        $("#update-color-true").attr('checked', true)
                    } else {
                        $("#update-color-false").attr('checked', true)
                    }
                    if (data.odor == true) {
                        $("#update-odor-true").attr('checked', true)
                    } else {
                        $("#update-odor-false").attr('checked', true)
                    }
                    if (data.taste == true) {
                        $("#update-taste-true").attr('checked', true)
                    } else {
                        $("#update-taste-false").attr('checked', true)
                    }
                    if (data.netStat == true) {
                        $("#update-netStat-true").attr('checked', true)
                    } else {
                        $("#update-netStat-false").attr('checked', true)
                    }
                    $("#update-correctiveAction").val(data.correctiveAction);

                },
                error: function(e) {
                    console.log("ERROR: ", e);
                }
            });
        })