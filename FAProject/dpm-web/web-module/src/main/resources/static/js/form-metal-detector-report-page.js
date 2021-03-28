
/**
 * Author by DinhDN 
 */


$(document).ready(function () {
    generateDropdownList();
    setDefaultValueFormFilter();
    hideAllError();
    viewListJSONmetalDetector('');

})

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
        parameter = '?' + getParamPageSize();

    } else {
        parameter += '&' + getParamPageSize();
    }

    $.post({
        url: 'getListPeportFormMetalDetector' + parameter,
        data: formData,
        success: function (resData) {
            var tableHtml = '';

            viewPagingHtml(resData.totalPages, resData.number);

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
                        if (resData.content[j].status == 'Pending') {
                            tableHtml += '<input type="checkbox" name="checkBox-element"  value="' + resData.content[j].id + '"></td>';
                        } else {
                            tableHtml += '</td>';
                        }
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
                        tableHtml += '</tr >';
                    }
                }

                $('html, body').animate({
                    scrollTop: $("#scrollToHere").offset().top
                }, 200);
            }
            //add html code into table
            $('#body-table-metal-detector').html(tableHtml);
        }
    })

}


/**
 * Function generate code view paging link
 * @param {*} totalPage 
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


/**
 * ------------------------------------------------
 * Function listen user check input.
 * If list input don't check -> hide button id="approve-button"
 * else if single or multi check -> show button id="approve-button"
 * ------------------------------------------------
 */
$(document).on('click', '[name="checkBox-element"]', function () {
    //Listen list input is checked or not
    var listen = $('[name="checkBox-element"]').is(":checked");
    if (listen == false) {
        $('#approve-button').prop('hidden', true);
        $('#reject-button').prop('hidden', true);
        $('#select-all-checkBox').prop("checked", false);
    } else {
        $('#approve-button').prop('hidden', false);
        $('#reject-button').prop('hidden', false);
        $('#select-all-checkBox').prop('checked', true);
    }
});


/**
 * ------------------------------------------------
 * Function get all value input type='checkbox' with name='checkBox-element'
 * 
 * @return array list
 * ------------------------------------------------
 */
function getValueAllCheckBoxIsChecked() {

    var arrCheckBox = [];
    var getAllCheckBox = document.getElementsByName("checkBox-element");

    for (var i = 0; i < getAllCheckBox.length; i++) {
        if (getAllCheckBox[i].type == 'checkbox' && getAllCheckBox[i].checked == true) {
            arrCheckBox.push(getAllCheckBox[i].value);
        }
    }
    return arrCheckBox;
}


/**
 * ------------------------------------------------
 * Function event listen input type='checkbox' with id='select-all-checkBox'
 * if input is checked -> checked all input type='checkbox' with name='checkBox-element'
 * if input is not checked -> unchecked all input type='checkbox' with name='checkBox-element'
 * ------------------------------------------------
 */
$('#select-all-checkBox').click(function () {
    var getAllCheckBox = document.getElementsByName("checkBox-element");

    var checked = $('#select-all-checkBox').is(":checked");

    if (checked) {
        $('#approve-button').prop('hidden', false);
        $('#reject-button').prop('hidden', false);
    } else {
        $('#approve-button').prop('hidden', true);
        $('#reject-button').prop('hidden', true);
    }

    for (var i = 0; i < getAllCheckBox.length; i++) {
        if (getAllCheckBox[i].type == 'checkbox') {
            getAllCheckBox[i].checked = checked;
        }
    }

    var listen = $('[name="checkBox-element"]').is(":checked");
    if (listen == false) {
        $('#approve-button').prop('hidden', true);
        $('#reject-button').prop('hidden', true);
        $('#select-all-checkBox').prop("checked", false);
    }
});


$('#approve-button').click(function () {
    var data = getValueAllCheckBoxIsChecked();
    var status = 'Approved';
    updateStatusMetalDetector(data, status);

});


$('#reject-button').click(function () {
    var data = getValueAllCheckBoxIsChecked();
    var status = 'Rejected';
    updateStatusMetalDetector(data, status);
});


function updateStatusMetalDetector(data, status) {
    $.post({
        url: 'updateStatusMetalDetector?data=' + data + '&status=' + status,
        success: function (message) {

            if (message == "") {
                //Show message 
                swal({
                    title: "Success!",
                    text: status + " success!",
                    icon: "success",
                }).then(function () {
                    var currentPage = $('#txtCurrentPage').val();
                    viewListOfPage(currentPage);
                    $('#approve-button').prop('hidden', true);
                    $('#reject-button').prop('hidden', true);
                    $('#select-all-checkBox').prop("checked", false);

                });
            } else {
                $('#approve-button').prop('hidden', false);
                $('#reject-button').prop('hidden', false);
                $('#select-all-checkBox').prop("checked", true);
            }
        }
    })
}


function viewListOfPage(pageNumber) {
    var parameter = '?page=' + pageNumber;
    viewListJSONmetalDetector(parameter);
};


function getParamPageSize() {
    var pageSize = 'size=' + $('#element_size').val();
    return pageSize;
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

    $('#approve-button').prop('hidden', true);
    $('#reject-button').prop('hidden', true);
    $('#select-all-checkBox').prop("checked", false);

})

/**
 * ------------------------------------------------
 * function set value default for filter form when page load.
 * ------------------------------------------------
 */
function setDefaultValueFormFilter() {
    // Get current dateTime
    var toDay = new Date();
    var endDate = ('0' + toDay.getDate()).slice(-2) + '/' + ('0' + (toDay.getMonth() + 1)).slice(-2) + '/' + toDay.getFullYear();
    $("#endDate").val(endDate);

    // Get dateTime with 30 day ago.
    var fromDay = new Date(toDay.setDate(toDay.getDate() - 30));
    var statDate = ('0' + fromDay.getDate()).slice(-2) + '/' + ('0' + (fromDay.getMonth() + 1)).slice(-2) + '/' + fromDay.getFullYear();
    $("#startDate").val(statDate);

    // Set default value
    $("#departmentCode").val('all').change();
    $("#typeProductCode").val('all').change();
    $("#lotNo").val('all').change();
    $("#checkingSample").val('all').change();
    $("#status").val('all').change();
    $("#userName").val('all').change();
};


/**
 * ------------------------------------------------
 * Function hide all error when page load
 * ------------------------------------------------
 */
function hideAllError() {
    $("#departmentCodeError").prop("hidden", true);
    $("#typeProductCodeError").prop("hidden", true);
    $("#lotNoError").prop("hidden", true);
    $("#checkingSampleError").prop("hidden", true);
    $("#statusError").prop("hidden", true);
    $("#userNameError").prop("hidden", true);
    $("#startDateError").prop("hidden", true);
    $("#endDateError").prop("hidden", true);
    $('#startDate').removeClass("border").removeClass("border-danger");
    $('#endDate').removeClass("border").removeClass("border-danger");
};


/**
 * ------------------------------------------------
 * Show/hide columns selected by modal 
 * @param {*} columnNumber 
 * ------------------------------------------------
 */
function showHideColumnTable(columnNumber) {
    var tb = document.getElementById("metalDetectorTable");
    var tbtr = tb.getElementsByTagName("tr");

    // Show or hidden column of head table
    if (columnNumber == 5) {
        $(tbtr[0].getElementsByTagName("th")[columnNumber]).toggle();
        $(tbtr[1].getElementsByTagName("th")[0]).toggle();
        $(tbtr[1].getElementsByTagName("th")[1]).toggle();
        $(tbtr[1].getElementsByTagName("th")[2]).toggle();
    } else {
        $(tbtr[0].getElementsByTagName("th")[columnNumber]).toggle();
    }

    // Show or hidden column in each row 
    for (let i = 0; i < tbtr.length + 3; i++) {
        if (columnNumber < 5) {
            $(tbtr[i].getElementsByTagName("td")[columnNumber]).toggle();
        } else if (columnNumber == 5) {
            $(tbtr[i].getElementsByTagName("td")[columnNumber]).toggle();
            $(tbtr[i].getElementsByTagName("td")[columnNumber + 1]).toggle();
            $(tbtr[i].getElementsByTagName("td")[columnNumber + 2]).toggle();
        } else {
            $(tbtr[i].getElementsByTagName("td")[columnNumber + 2]).toggle();
        }
    }
}

/**
 * Listening even user click button show all.
 * Then Show all columns in table.
 */
$('#toggle-state-ShowAll').click(function () {
    $(":checkbox:not(:checked)").prop("checked", true);
    $("th").show();
    $("td").show();
    // $("#nav-paging").show();
    // $("#exportButton").show();
})

/**
 * Listening even user click button hidden all.
 * Then hidden all columns in table
 */
$('#toggle-state-hidden').click(function () {
    $(":checkbox").prop("checked", false);
    $("th").hide();
    $("td").hide();
    // $("#nav-paging").hide();
    // $("#exportButton").hide();
})

/**
 * ------------------------------------------------
 * Function draggable modal poppup up and down
 * ------------------------------------------------
 */
$(function () {
    $('body').on('mousedown', '#configModal', function () {
        $('#configModal').addClass('draggable').parents().on('mousemove', function (e) {
            $('.draggable').offset({
                top: e.pageY - $('.draggable').outerHeight() / 5
            }).on('mouseup', function () {
                $('#configModal').removeClass('draggable');
            });
        });
        e.preventDefault();
    }).on('mouseup', function () {
        $('.draggable').removeClass('draggable');
    });
});


/**
 * Function validate form  id=metalDetectorForm  data get from controller 'filterValidate' 
 * 
 * Return Error if controller send objectErrors has not null
 */
$(document).ready(function () {

    $("#filterButton").click(function () {

        //hide all error in page
        hideAllError();
        // $("#exportButton").hide();
        $('#body-table-metal-detector').empty();
        $('#body-nav-paging').empty();
        $('#element_size').val('10');

        var formData = $('form[name=filterForm]').serialize();
        $.post({
            url: 'filterValidate',
            data: formData,
            //objectErrors get from function saveOrUpdate in controller
            success: function (objectErrors) {

                if (Object.keys(objectErrors).length == 0) {

                    viewListJSONmetalDetector('');

                } else {

                    $("#exportButton").hide();

                    //Set error messages
                    for (var object of objectErrors) {

                        switch (object.field) {
                            case "departmentCode":
                                $("#departmentCodeError").prop("hidden", false);
                                break;
                            case "typeProductCode":
                                $("#typeProductCodeError").prop("hidden", false);
                                break;
                            case "lotNo":
                                $("#lotNoError").prop("hidden", false);
                                break;
                            case "checkingSample":
                                $("#checkingSampleError").prop("hidden", false);
                                break;
                            case "status":
                                $("#statusError").prop("hidden", false);
                                break;
                            case "userName":
                                $("#userNameError").prop("hidden", false);
                                break;
                            case "startDate":
                                $("#startDateError").prop("hidden", false);
                                $('#startDate').addClass("border").addClass("border-danger");
                                break;
                            case "endDate":
                                $("#endDateError").prop("hidden", false);
                                $('#endDate').addClass("border").addClass("border-danger");
                                break;
                        }
                    }


                }
            }
        })



    });

    /*  Submit form using Ajax */



    $("#exportButton").click(function () {

        /*  Submit form using Ajax */
        $("#filterForm").submit();


    });




});






