/**
 * Author by DinhDN 
 */



/**
* ------------------------------------------------
* Function Create select list have input. 
* dropdownParent: is fix error don't work when used select2 in modal bootstrap.
* width: is override width of element
* ------------------------------------------------
*/
$(document).ready(function () {

    generateDropdownList();

    generateDateRangeInput();

    setValueDateRangeTagInput();

    viewListJSONmetalDetector('');

});


function getParamDateRange() {
    var dateRangwValue = $('#date-range').val();
    var dateRangeArr = dateRangwValue.split(' - ');
    var param = 'startDate=' + dateRangeArr[0] + '&endDate=' + dateRangeArr[1];

    return param;
};

function generateDateRangeInput() {
    $('#date-range').daterangepicker(function (start, end) {
    });
};

/**
 * Function get current date and set to input have id="date-range"
 */
function setValueDateRangeTagInput() {
    //Get current dateTime
    var toDay = new Date();
    var dateDefault = ('0' + toDay.getDate()).slice(-2) + '/' + ('0' + (toDay.getMonth() + 1)).slice(-2) + '/' + toDay.getFullYear();
    var dateRangeValue = dateDefault + ' - ' + dateDefault;

    $('#date-range').val(dateRangeValue);
};

/**
 * 
 * Function get list object type json and add to to table, 
 * hide button export-excell if list empty
 * Call rest full API link: "metal-detector/getListPeportFormMetalDetector"
 * 
 * @param {*} parameter
 * 
 */
function viewListJSONmetalDetector(parameter) {
    // var data1 = $('form[name=filterForm]').serializeArray();
    var formData = $('form[name=filterForm]').serialize();

    if (parameter == '') {
        parameter = '?' + getParamDateRange() + '&' + getParamPageSize();

    } else {
        parameter += '&' + getParamDateRange() + '&' + getParamPageSize();
    }

    if (getSearchValue().length > 0) {
        parameter += '&' + getSearchValue();
    }

    $.post({
        url: 'getListHomeFormMetalDetector' + parameter,
        data: formData,
        success: function (resData) {
            var tableHtml = '';

            viewPagingHtml(resData.totalPages, resData.pageable.pageNumber);

            if (resData.totalElements == 0) {

                swal(message_listEmpty);

                //Hide button export excell
                $("#exportButton").hide();

                // add row empty for table
                tableHtml += '<tr class="btn-default text-center">';
                tableHtml += '    <td colspan="12"><span>' + message_listEmpty + '</span> </td>';
                tableHtml += '</tr >';
            } else {
                //Show button export excell
                $("#exportButton").show();

                // add row empty for table
                for (var j = 0; j < resData.size; j++) {
                    if (resData.content[j] != null) {
                        tableHtml += '<tr>';
                        tableHtml += '<td>' + (j + 1) + '</td>';
                        tableHtml += '<td>' + resData.content[j].checkingDate + '</td>';
                        tableHtml += '<td>' + resData.content[j].productLot.lotCode + '</td>';
                        tableHtml += '<td>' + resData.content[j].productLot.typeProduct.typeProductName + '</td>';
                        tableHtml += '<td>' + resData.content[j].checkingTime + '</td>';
                        tableHtml += '<td>' + (resData.content[j].checkingSampleFe ? 'Đạt' : 'Không đạt') + '</td>';
                        tableHtml += '<td>' + (resData.content[j].checkingSampleNonFe ? 'Đạt' : 'Không đạt') + '</td>';
                        tableHtml += '<td>' + (resData.content[j].checkingSampleSus ? 'Đạt' : 'Không đạt') + '</td>';
                        tableHtml += '<td>' + resData.content[j].frequency + '</td>';
                        tableHtml += '<td>' + ((resData.content[j].correctiveAction != null) ? resData.content[j].correctiveAction : '-') + '</td>';
                        tableHtml += '<td>' + resData.content[j].inspector.employeeName + '</td>';
                        tableHtml += '<td>';
                        switch (resData.content[j].status) {
                            case 'Pending':
                                tableHtml += '<i class="fa fa-eye-slash icon-md text-warning" title="Chờ duyệt"></i>';
                                break;
                            case 'Rejected':
                                tableHtml += '<i class="fa fa-times-circle icon-md text-danger" title="Không duyệt"></i>';
                                break;
                            case 'Approved':
                                tableHtml += '<i class="fa fa-eye icon-md text-primary" title="Đã duyệt"></i>';
                                break;
                        }
                        tableHtml += '</td >';
                        tableHtml += '<td>';
                        tableHtml += '  <div class="dropdown">';
                        tableHtml += '      <button class="btn btn-primary dropdown-toggle" type="button" id="dropdownMenuButton1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"> <i class="fa fa-sliders" title="Tác vụ"> </i> </button>';
                        tableHtml += '      <div class="dropdown-menu" aria-labelledby="dropdownMenuButton1" x-placement="bottom-start" style="position: absolute; transform: translate3d(0px, 33px, 0px); top: 0px; left: 0px; will-change: transform;">';
                        tableHtml += '          <button type="button" onclick="updateMetalDetectorById(' + resData.content[j].id + ')" class="btn w-100 text-left bg-white btn-hover " data-toggle="modal" data-target="#add-modal">';
                        tableHtml += '              <i class="icon-pencil"></i>Cập nhật';
                        tableHtml += '          </button>';
                        tableHtml += '          <br>';
                        tableHtml += '          <button type="button" onclick="deleteMetalDetectorById(' + resData.content[j].id + ')" class="btn w-100 text-left bg-white btn-hover ">';
                        tableHtml += '              <i class="icon-trash"></i>Xóa';
                        tableHtml += '          </button>';
                        tableHtml += '          <br>';
                        tableHtml += '          <button type="button" onclick="setDefaulSetting(' + resData.content[j].id + ')" class="btn w-100 text-left bg-white btn-hover ">';
                        tableHtml += '              <i class="fa fa-bolt"></i> Đặt làm mặc định';
                        tableHtml += '          </button>';
                        tableHtml += '      </div>';
                        tableHtml += '  </div>';
                        tableHtml += '</td >';
                        tableHtml += '</tr >';
                    }
                }
            }
            //add html code into table
            $('#body-table-metal-detector').html(tableHtml);
        }
    })

};




/**
 * ------------------------------------------------
 * Function generate code view paging link
 * 
 * @param {*} totalPage 
 * ------------------------------------------------
 */
function viewPagingHtml(totalPage, currentPage) {
    var pagingHtml = '';
    if (totalPage != 0) {
        var next = 1;
        var previous = 1;

        if (currentPage == 0) {
            previous = 0;
        }
        pagingHtml += '<li class="page-item">';
        pagingHtml += '    <a class="page-link" onclick="viewListOfPage(' + (currentPage - previous) + ')">';
        pagingHtml += '        <i class="icon-arrow-left"></i>';
        pagingHtml += '    </a>';
        pagingHtml += '</li>';


        for (var j = 0; j < totalPage; j++) {
            if (j == currentPage) {
                pagingHtml += '<li class="page-item active">';
            } else {
                pagingHtml += '<li class="page-item">';
            }
            pagingHtml += '    <a class="page-link" onclick="viewListOfPage(' + j + ')">' + (j + 1) + '</a>';
            pagingHtml += '</li>';
        }

        if (currentPage == totalPage - 1) {
            next = 0;
        }
        pagingHtml += '<li class="page-item">';
        pagingHtml += '    <a class="page-link" onclick="viewListOfPage(' + (currentPage + next) + ')">';
        pagingHtml += '        <i class="icon-arrow-right"></i>';
        pagingHtml += '    </a>';
        pagingHtml += '</li>';
        pagingHtml += '<input type="text" id="txtCurrentPage" value="' + currentPage + '" readonly hidden>';
    }

    //add html code into id="body-nav-paging"
    $('#body-nav-paging').html(pagingHtml);

};




function viewListOfPage(pageNumber) {
    var parameter = '?page=' + pageNumber;
    viewListJSONmetalDetector(parameter);
};


/**
 * ------------------------------------------------
 * Function Create select list have input. 
 * dropdownParent: is fix error don't work when used select2 in modal bootstrap.
 * width: is override width of element
 * ------------------------------------------------
 */
function generateDropdownList() {
    $('.js-example-basic-single').select2({
        width: '100%'
    });
};



/**
 * ------------------------------------------------
 * Function change size of list view.
 * Listen seclect box and get list object type json to view data of table.
 * ------------------------------------------------
 */
$('#element_size').change(function () {
    viewListJSONmetalDetector('');
});


function getParamPageSize() {
    var pageSize = 'size=' + $('#element_size').val();
    return pageSize;
};


function getSearchValue() {
    var searchValue = 'search=' + $('#txtSearch').val().trim();
    return searchValue;
};


function getCurrentPage() {
    var parameter = '?page=' + $('#txtCurrentPage').val();
    return parameter;
}

/**
 * Function Listen event button addMetalDetector1 click
 */
$('#addMetalDetector1').click(function () {
    getDefaultSettingAndAddToFormInput();
});


/**
 * Function Listen event button addMetalDetector2 click
 */
$('#addMetalDetector2').click(function () {
    getDefaultSettingAndAddToFormInput();
});


$('#btnSearch').click(function () {
    viewListJSONmetalDetector('');
});



/**
 * Return true, if the value is a valid date, also making this formal check dd/mm/yyyy.
 *
 * @example $.validator.methods.date("01/01/1900")
 * @result true
 *
 * @example $.validator.methods.date("01/13/1990")
 * @result false
 *
 * @example $.validator.methods.date("01.01.1900")
 * @result false
 *
 * @example <input name="pippo" class="{dateITA:true}" />
 * @desc Declares an optional input element whose value must be a valid date.
 *
 * @name $.validator.methods.dateITA
 * @type Boolean
 * @cat Plugins/Validate/Methods
 */
$.validator.addMethod("dateITA", function (value, element) {
    var check = false,
        re = /^\d{1,2}\/\d{1,2}\/\d{4}$/,
        adata, gg, mm, aaaa, xdata;
    if (re.test(value)) {
        adata = value.split("/");
        gg = parseInt(adata[0], 10);
        mm = parseInt(adata[1], 10);
        aaaa = parseInt(adata[2], 10);
        xdata = new Date(aaaa, mm - 1, gg, 12, 0, 0, 0);
        if ((xdata.getUTCFullYear() === aaaa) && (xdata.getUTCMonth() === mm - 1) && (xdata.getUTCDate() === gg)) {
            check = true;
        } else {
            check = false;
        }
    } else {
        check = false;
    }
    return this.optional(element) || check;
}, "Please enter a correct date");


/**
 * Function validate form  id=metalDetectorForm  by jquery
 */
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
            required: true,
            dateITA: true
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
        userName: {
            required: true
        }

    },
    messages: {
        departmentCode: {
            required: message_departmentCode
        },
        typeProductCode: {
            required: message_typeProductCode
        },
        lotNo: {
            required: message_lotNo
        },
        dateCheck: {
            required: message_dateCheck,
            dateITA: message_dateITA
        },
        timeCheck: {
            required: message_timeCheck
        },
        frequency: {
            required: message_frequency,
            minlength: message_minlength,
            maxlength: message_maxlength
        },
        optionsRadiosFe: {
            required: message_optionsRadiosFe
        },
        optionsRadiosNonfe: {
            required: message_optionsRadiosNonfe
        },
        optionsRadiosSus: {
            required: message_optionsRadiosSus
        },
        userName: {
            required: message_inspector
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



/**
 * Function validate form  id=configDefault  by jquery
 */
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
        userName: {
            required: true
        }
    },
    messages: {
        departmentCode: {
            required: message_departmentCode
        },
        typeProductCode: {
            required: message_typeProductCode
        },
        lotNo: {
            required: message_lotNo
        },
        frequency: {
            required: message_frequency,
            minlength: message_minlength,
            maxlength: message_maxlength
        },
        optionsRadiosFe: {
            required: message_optionsRadiosFe
        },
        optionsRadiosNonfe: {
            required: message_optionsRadiosNonfe
        },
        optionsRadiosSus: {
            required: message_optionsRadiosSus
        },
        userName: {
            required: message_inspector
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

        // Disable button submit modal form addMetalDetector
        $('#btnSaveMetalDetector').prop("disabled", true);

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
                        // Disable button submit modal form addMetalDetector
                        $('#btnSaveMetalDetector').prop("disabled", false);
                        $('#add-modal').modal('toggle');

                        viewListJSONmetalDetector(getCurrentPage());
                    });
                } else {
                    //Set error messages
                    $('#btnSaveMetalDetector').prop("disabled", false);

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
                        if (object.field == 'userName') {
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
                        // Disable button submit modal form addMetalDetector
                        $('#config-modal').modal('toggle');
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
                        if (object.field == 'userName') {
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
            $("#departmentCode").val(metalDetectorDTO.departmentCode).change();
            $("#typeProductCode").val(metalDetectorDTO.typeProductCode).change();
            $("#lotNo").val(metalDetectorDTO.lotNo).change();
            var dateArr = metalDetectorDTO.dateCheck.split('-');
            $("#dateCheck").val(dateArr[2] + '/' + dateArr[1] + '/' + dateArr[0]);
            var timeArr = metalDetectorDTO.timeCheck.split(':');
            $("#timeCheck").val(timeArr[0] + ':' + timeArr[1]);
            $("#frequency").val(metalDetectorDTO.frequency);
            if (metalDetectorDTO.optionsRadiosFe) {
                $("#optionsRadios_fe1").prop("checked", true);
            } else if (!metalDetectorDTO.optionsRadiosFe) {
                $("#optionsRadios_fe2").prop("checked", true);
            }
            if (metalDetectorDTO.optionsRadiosNonfe) {
                $("#optionsRadios_nonfe1").prop("checked", true);
            } else if (!metalDetectorDTO.optionsRadiosNonfe) {
                $("#optionsRadios_nonfe2").prop("checked", true);
            }
            if (metalDetectorDTO.optionsRadiosSus) {
                $("#optionsRadios_sus1").prop("checked", true);
            } else if (!metalDetectorDTO.optionsRadiosSus) {
                $("#optionsRadios_sus2").prop("checked", true);
            }

            $("#correctiveAction").val(metalDetectorDTO.correctiveAction);
            $("#userName").val(metalDetectorDTO.userName).change();
            $("#status").val(metalDetectorDTO.status);


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

                    viewListJSONmetalDetector(getCurrentPage());
                },
                error: function () {
                    swal("Xóa!", "Xóa dữ liệu thất bại!)", "error");
                }
            });
        }
    });

};


function setDefaulSetting(metalDetectorId) {
    $.ajax({
        type: "GET",
        url: "setDefaultSetting/" + metalDetectorId,

        success: function (data) {
            if (data == 'SUCCESS') {
                swal("Cài đặt dữ liệu mặc định!", "Cài đặt dữ liệu mặc định thành công!", "success");
            } else {
                swal("Cài đặt dữ liệu mặc định!", "v thất bại!)", "error");
            }
        },
        error: function () {
            swal("Cài đặt dữ liệu mặc định!", "v thất bại!)", "error");
        }
    });
}


/**
 * Function set value default for model add new metalDetector when form modal show
 */
function getDefaultSettingAndAddToFormInput() {
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
                $("#departmentCode").val(defaultSetting.departmentCode).change();
                $("#typeProductCode").val(defaultSetting.typeProductCode).change();
                $("#lotNo").val(defaultSetting.lotNo).change();
                if (defaultSetting.frequency != null) {
                    $("#frequency").val(defaultSetting.frequency);
                }
                if (defaultSetting.optionsRadiosFe) {
                    $("#optionsRadios_fe1").prop("checked", true);
                } else if (!defaultSetting.optionsRadiosFe) {
                    $("#optionsRadios_fe2").prop("checked", true);
                }
                if (defaultSetting.optionsRadiosNonfe) {
                    $("#optionsRadios_nonfe1").prop("checked", true);
                } else if (!defaultSetting.optionsRadiosNonfe) {
                    $("#optionsRadios_nonfe2").prop("checked", true);
                }
                if (defaultSetting.optionsRadiosSus) {
                    $("#optionsRadios_sus1").prop("checked", true);
                } else if (!defaultSetting.optionsRadiosSus) {
                    $("#optionsRadios_sus2").prop("checked", true);
                }
                $("#userName").val(defaultSetting.userName).change();
                $("#status").val(defaultSetting.status);
            }

        }
    })
};

/**
* Function set value default for model default setting when form modal show
*/
$('#defaultSettingButton').click(function () {
    $.get({
        url: 'getDefaultSetting',
        success: function (defaultSetting) {

            if (Object.keys(defaultSetting) != null) {
                $("#defaultDepartmentCode").val(defaultSetting.departmentCode).change();
                $("#defaultTypeProductCode").val(defaultSetting.typeProductCode).change();
                $("#defaultLotNo").val(defaultSetting.lotNo).change();
                $("#defaultFrequency").val(defaultSetting.frequency);
                if (defaultSetting.optionsRadiosFe) {
                    $("#defaultOptionsRadios_fe1").prop("checked", true);
                } else if (!defaultSetting.optionsRadiosFe) {
                    $("#defaultOptionsRadios_fe2").prop("checked", true);
                }
                if (defaultSetting.optionsRadiosNonfe) {
                    $("#defaultOptionsRadios_nonfe1").prop("checked", true);
                } else if (!defaultSetting.optionsRadiosNonfe) {
                    $("#defaultOptionsRadios_nonfe2").prop("checked", true);
                }
                if (defaultSetting.optionsRadiosSus) {
                    $("#defaultOptionsRadios_sus1").prop("checked", true);
                } else if (!defaultSetting.optionsRadiosSus) {
                    $("#defaultOptionsRadios_sus2").prop("checked", true);
                }
                $("#defaultUserName").val(defaultSetting.userName).change();
                $("#defaultStatus").val(defaultSetting.status);
            }

        }
    })
});


