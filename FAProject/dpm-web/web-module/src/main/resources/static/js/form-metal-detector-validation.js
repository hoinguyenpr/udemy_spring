/**
 * Author by DinhDN 
 */


/**
 * Function validate form  id=metalDetectorForm  by jquery
 */
(function ($) {
    'use strict';
    $(function () {

        // validate metalDetectorForm on keyup 
        $("#metalDetectorForm").validate({
            rules: {
                departmentCode: {
                    required: true
                },
                typeProductCode: {
                    required: true
                },
                lotNo: {
                    required: true,
                },
                dateCheck: {
                    required: true
                },
                timeCheck: {
                    required: true
                },
                frequency: {
                    required: true,
                    minlength: 2,
                    maxlength: 50
                },
                optionsRadiosFe: {
                    required: true
                },
                optionsRadiosNonfe: {
                    required: true
                },
                optionsRadiosSus: {
                    required: true
                },
                employeeId: {
                    required: true
                }

            },
            messages: {
                departmentCode: {
                    required: "Nhập phòng ban"
                },
                typeProductCode: {
                    required: "Nhập loại sản phẩm"
                },
                lotNo: {
                    required: "Nhập mã lô",
                },
                dateCheck: {
                    required: ""
                },
                timeCheck: {
                    required: ""
                },
                frequency: {
                    required: "Nhập tần xuất",
                    minlength: "Tối thiểu 2 ký tự",
                    maxlength: "Tối đa 50 ký tự"
                },
                optionsRadiosFe: {
                    required: "Chưa chọn Fe"
                },
                optionsRadiosNonfe: {
                    required: "Chưa chọn nonFe"
                },
                optionsRadiosSus: {
                    required: "Chưa chọn sus"
                },
                employeeId: {
                    required: "Nhập nhân viên"
                }
            },
            errorPlacement: function (label, element) {
                label.addClass('mt-2 text-danger');
                label.insertAfter(element);
            },
            highlight: function (element, errorClass) {
                $(element).parent().addClass('has-danger')
                $(element).addClass('form-control-danger')
            }
        });

    });
})(jQuery);

/**
 * Function validate form  id=configDefault  by jquery
 */
(function ($) {
    'use strict';
    $(function () {

        // validate configDefault on keyup 
        $("#configDefault").validate({
            rules: {
                departmentCode: {
                    required: true
                },
                typeProductCode: {
                    required: true
                },
                lotNo: {
                    required: true,
                },
                frequency: {
                    required: true,
                    minlength: 2,
                    maxlength: 50
                },
                optionsRadiosFe: {
                    required: true
                },
                optionsRadiosNonfe: {
                    required: true
                },
                optionsRadiosSus: {
                    required: true
                },
                employeeId: {
                    required: true
                }

            },
            messages: {
                departmentCode: {
                    required: "Nhập phòng ban"
                },
                typeProductCode: {
                    required: "Nhập loại sản phẩm"
                },
                lotNo: {
                    required: "Nhập mã lô",
                },
                frequency: {
                    required: "Nhập tần xuất",
                    minlength: "Tối thiểu 2 ký tự",
                    maxlength: "Tối đa 50 ký tự"
                },
                optionsRadiosFe: {
                    required: "Chưa chọn Fe"
                },
                optionsRadiosNonfe: {
                    required: "Chưa chọn nonFe"
                },
                optionsRadiosSus: {
                    required: "Chưa chọn sus"
                },
                employeeId: {
                    required: "Nhập nhân viên"
                }
            },
            errorPlacement: function (label, element) {
                label.addClass('mt-2 text-danger');
                label.insertAfter(element);
            },
            highlight: function (element, errorClass) {
                $(element).parent().addClass('has-danger')
                $(element).addClass('form-control-danger')
            }
        });

    });
})(jQuery);

/**
 * Function validate form  id=metalDetectorForm  data get from controller 'save' 
 * 
 * Return Error if controller send objectErrors has not null
 */
$(function () {
    /*  Submit form using Ajax */
    $("#metalDetectorForm").submit(function (e) {

        //Prevent default submission of form
        e.preventDefault();

        //Remove all errors
        $("#editFieldError").remove();

        $.post({
            url: 'save',
            data: $('form[name=metalDetectorForm]').serialize(),
            //objectErrors get from function saveOrUpdate in controller
            success: function (objectErrors) {

                if (Object.keys(objectErrors).length == 0) {
                    //Show message 
                    swal({
                        title: "Success!",
                        text: "Create success!",
                        icon: "success",
                    }).then(function () {
                        document.location.href = "/metal-detector/";
                    });
                } else {
                    //Set error messages

                    var message = "Vui lòng kiểm tra thông tin: ";
                    for (var object of objectErrors) {

                        if (object.field == 'departmentCode') {
                            message = message + '[\"Phòng ban\"] ';
                        }
                        if (object.field == 'typeProductCode') {
                            message = message + '[\"Loại sản phẩm\"] ';
                        }
                        if (object.field == 'lotNo') {
                            message = message + '[\"Mã lô\"] ';
                        }
                        if (object.field == 'dateCheck') {
                            message = message + '[\"Ngày kiểm tra\"] ';
                        }
                        if (object.field == 'timeCheck') {
                            message = message + '[\"Giờ kiểm tra\"] ';
                        }
                        if (object.field == 'frequency') {
                            message = message + '[\"Tần xuất\"] ';
                        }
                        if (object.field == 'optionsRadiosFe') {
                            message = message + '[\"chọn Fe\"] ';
                        }
                        if (object.field == 'optionsRadiosNonfe') {
                            message = message + '[\"chọn nonFe\"] ';
                        }
                        if (object.field == 'optionsRadiosSus') {
                            message = message + '[\"chọn sus\"] ';
                        }
                        if (object.field == 'employeeId') {
                            message = message + '[\"Nhân viên\"] ';
                        }
                    }

                    $("#metalDetectorFormTitle").after('<p id="editFieldError" class="error mt-2 text-danger text-center pb-4">' + message + '</p>');
                }
            }
        })
    });
});


/**
 * Function validate form  id=configDefault  data get from controller 'saveOrUpdateDefaultSetting' 
 * 
 * Return Error if controller send objectErrors has not null
 */
$(function () {
    /*  Submit form using Ajax */
    $("#configDefault").submit(function (e) {

        //Prevent default submission of form
        e.preventDefault();

        //Remove all errors
        $("#editFieldError").remove();

        $.post({
            url: 'saveOrUpdateDefaultSetting',
            data: $('form[name=configDefault]').serialize(),
            //objectErrors get from function saveOrUpdate in controller
            success: function (objectErrors) {

                if (Object.keys(objectErrors).length == 0) {
                    //Show message 
                    swal({
                        title: "Success!",
                        text: "Create default setting success!",
                        icon: "success",
                    }).then(function () {
                        document.location.href = "/metal-detector/";
                    });
                } else {
                    //Set error messages

                    var message = "Vui lòng kiểm tra thông tin: ";
                    for (var object of objectErrors) {

                        if (object.field == 'departmentCode') {
                            message = message + '[\"Phòng ban\"] ';
                        }
                        if (object.field == 'typeProductCode') {
                            message = message + '[\"Loại sản phẩm\"] ';
                        }
                        if (object.field == 'lotNo') {
                            message = message + '[\"Mã lô\"] ';
                        }
                        if (object.field == 'frequency') {
                            message = message + '[\"Tần xuất\"] ';
                        }
                        if (object.field == 'optionsRadiosFe') {
                            message = message + '[\"chọn Fe\"] ';
                        }
                        if (object.field == 'optionsRadiosNonfe') {
                            message = message + '[\"chọn nonFe\"] ';
                        }
                        if (object.field == 'optionsRadiosSus') {
                            message = message + '[\"chọn sus\"] ';
                        }
                        if (object.field == 'employeeId') {
                            message = message + '[\"Nhân viên\"] ';
                        }
                    }
                    $("#configDefaultTitle").after('<p id="editFieldError" class="error mt-2 text-danger text-center pb-4">' + message + '</p>');
                }
            }
        })
    });
});



/**
 * Function show popup for user confirm delete or cancel
 * 
 * @param {*} metalDetectorId 
 */
function updateMetalDetectorById(metalDetectorId) {
    $.post({
        url: 'getById/' + metalDetectorId,

        success: function (metalDetectorDTO) {
            $("#id").val(metalDetectorDTO.id);
            $("#departmentCode").val(metalDetectorDTO.departmentCode);
            $("#typeProductCode").val(metalDetectorDTO.typeProductCode);
            $("#lotNo").val(metalDetectorDTO.lotNo);
            var dateAr = metalDetectorDTO.dateCheck.split('-');
            $("#dateCheck").val(dateAr[2] + '/' + dateAr[1] + '/' + dateAr[0]);
            $("#timeCheck").val(metalDetectorDTO.timeCheck);
            $("#frequency").val(metalDetectorDTO.frequency);
            if (metalDetectorDTO.optionsRadiosFe) {
                $("#optionsRadios_fe1").attr("checked", "checked");
            } else if (!metalDetectorDTO.optionsRadiosFe) {
                $("#optionsRadios_fe2").attr("checked", "checked");
            }
            if (metalDetectorDTO.optionsRadiosNonfe) {
                $("#optionsRadios_nonfe1").attr("checked", "checked");
            } else if (!metalDetectorDTO.optionsRadiosNonfe) {
                $("#optionsRadios_nonfe2").attr("checked", "checked");
            }
            if (metalDetectorDTO.optionsRadiosSus) {
                $("#optionsRadios_sus1").attr("checked", "checked");
            } else if (!metalDetectorDTO.optionsRadiosSus) {
                $("#optionsRadios_sus2").attr("checked", "checked");
            }

            $("#correctiveAction").val(metalDetectorDTO.correctiveAction);
            $("#employeeId").val(metalDetectorDTO.employeeId);
            if (metalDetectorDTO.status) {
                $("#status").attr("checked", "checked");
            }

        }
    })
};

/**
 * Function show popup for user confirm delete or cancel
 * If user has click delete check action delete and show popup.
 * 
 * @param {*} metalDetectorId 
 * @returns View popup success if delete success, otherwise show popup fail.
 */
function deleteMetalDetectorById(metalDetectorId) {
    swal({
        title: 'Xác nhận xóa?',
        text: "Dữ liệu sẽ được xóa hoàn toàn!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3f51b5',
        cancelButtonColor: '#ff4081',
        confirmButtonText: 'Great ',
        buttons: {
            cancel: {
                text: "Hủy",
                value: "null",
                visible: true,
                className: "btn btn-danger",
                closeModal: true,
            },
            confirm: {
                text: "Xóa",
                value: "true",
                visible: true,
                className: "btn btn-primary",
                closeModal: true
            }
        }
    }).then(function (value) {
        /*
        * Check action user submit.
        */
        if (value === "true") {
            $.ajax({
                type: "GET",
                url: "deleteById/" + metalDetectorId,

                success: function (data) {
                    swal("Xóa!", "Xóa dữ liệu thành công!", "success");
                    document.location.href = "/metal-detector/";
                },
                error: function () {
                    swal("Xóa!", "Xóa dữ liệu thất bại!)", "error");
                }
            });
        }
    });

};

/**
 * Function listen action when page load.
 */
$(document).ready(function () {

    /**
     * Function change size of list view.
     * Listen seclect box and get value to call address
     */
    $('#element_size').change(function () {
        var size = $('#element_size').val();

        var arr = window.location.href.split('?');

        if (arr[1] == null) {
            document.location.href = '?size=' + size;
        } else {
            if (arr[1].includes('size=')) {
                //Get current url address
                var str = document.location.href;
                var splitSize1 = str.split("size=");
                var splitSize2 = splitSize1[1].split("&");

                //Set new url address
                document.location.href = str.replace('size=' + splitSize2[0], 'size=' + size);
            } else {
                document.location.href = document.location.href + '&size=' + size;
            }
        }
    })

    /**
     * Function set value default for model add new metalDetector when form modal show
     */
    $('#addMetalDetector').click(function () {
        $.get({
            url: 'getDefaultSetting',
            success: function (defaultSetting) {
                //Get current dateTime
                var toDay = new Date();
                var dateDefault = ('0' + toDay.getDate()).slice(-2) + '/' + ('0' + (toDay.getMonth() + 1)).slice(-2) + '/' + toDay.getFullYear();
                var timeDefault = ('0' + (toDay.getHours())).slice(-2) + ':' + ('0' + (toDay.getMinutes())).slice(-2);   //   06:38

                $("#dateCheck").val(dateDefault);
                $("#timeCheck").val(timeDefault);

                if (Object.keys(defaultSetting) != null) {
                    $("#departmentCode").val(defaultSetting.departmentCode);
                    $("#typeProductCode").val(defaultSetting.typeProductCode);
                    $("#lotNo").val(defaultSetting.lotNo);
                    $("#frequency").val(defaultSetting.frequency);
                    if (defaultSetting.optionsRadiosFe) {
                        $("#optionsRadios_fe1").attr("checked", "checked");
                    } else if (!defaultSetting.optionsRadiosFe) {
                        $("#optionsRadios_fe2").attr("checked", "checked");
                    }
                    if (defaultSetting.optionsRadiosNonfe) {
                        $("#optionsRadios_nonfe1").attr("checked", "checked");
                    } else if (!defaultSetting.optionsRadiosNonfe) {
                        $("#optionsRadios_nonfe2").attr("checked", "checked");
                    }
                    if (defaultSetting.optionsRadiosSus) {
                        $("#optionsRadios_sus1").attr("checked", "checked");
                    } else if (!defaultSetting.optionsRadiosSus) {
                        $("#optionsRadios_sus2").attr("checked", "checked");
                    }
                    $("#employeeId").val(defaultSetting.employeeId);
                }

            }
        })
    })

    /**
    * Function set value default for model default setting when form modal show
    */
    $('#defaultSettingButton').click(function () {
        $.get({
            url: 'getDefaultSetting',
            success: function (defaultSetting) {

                if (Object.keys(defaultSetting) != null) {
                    $("#defaultDepartmentCode").val(defaultSetting.departmentCode);
                    $("#defaultTypeProductCode").val(defaultSetting.typeProductCode);
                    $("#defaultLotNo").val(defaultSetting.lotNo);
                    $("#defaultFrequency").val(defaultSetting.frequency);
                    if (defaultSetting.optionsRadiosFe) {
                        $("#defaultOptionsRadios_fe1").attr("checked", "checked");
                    } else if (!defaultSetting.optionsRadiosFe) {
                        $("#defaultOptionsRadios_fe2").attr("checked", "checked");
                    }
                    if (defaultSetting.optionsRadiosNonfe) {
                        $("#defaultOptionsRadios_nonfe1").attr("checked", "checked");
                    } else if (!defaultSetting.optionsRadiosNonfe) {
                        $("#defaultOptionsRadios_nonfe2").attr("checked", "checked");
                    }
                    if (defaultSetting.optionsRadiosSus) {
                        $("#defaultOptionsRadios_sus1").attr("checked", "checked");
                    } else if (!defaultSetting.optionsRadiosSus) {
                        $("#defaultOptionsRadios_sus2").attr("checked", "checked");
                    }
                    $("#defaultEmployeeId").val(defaultSetting.employeeId);
                }

            }
        })
    })

})

/**
 * Function change page of list view.
 * Listen link and get value to call address
 */
function changePage(str, currentPage, totalPage) {

    if (str == 'previous' && currentPage >= 0) {
        currentPage--;
    }
    if (str == 'next' && currentPage < totalPage - 1) {
        currentPage++;
    }
    if (currentPage < 0) {
        currentPage = 0;
    }
    if (currentPage > totalPage) {
        currentPage = totalPage - 1;
    }

    var arr = window.location.href.split('?');

    if (arr[1] == null) {
        document.location.href = '?page=' + currentPage;
    } else {
        if (arr[1].includes('page=')) {
            //Get current url address
            var str = document.location.href;
            var splitSize1 = str.split("page=");
            var splitSize2 = splitSize1[1].split("&");

            //Set new url address
            document.location.href = str.replace('page=' + splitSize2[0], 'page=' + currentPage);
        } else {
            document.location.href = document.location.href + '&page=' + currentPage;
        }
    }
}