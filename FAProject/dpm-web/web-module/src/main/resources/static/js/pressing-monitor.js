/**
 * Author: <b> LongVT7 </b>
 */

$(document).ready(function(){
	$('#inputTime').removeAttr('value');
	$('#inputRime').val(entity.inputTime);
})

function setDate(date) {
	let d = new Date(date)
	document.getElementById('dateIn').valueAsDate = d;
	let hours = d.getHours() < 10 ? "0" + d.getHours()
		: d.getHours();
	let minu = d.getMinutes() < 10 ? "0" + d.getMinutes()
		: d.getMinutes();
	$('#inputTime').val(hours + ":" + minu);
}

$(document).ready(() => {
	
	setDate(new Date());
	
	if(state != null){
		switch(state){
			case 'CREATE_SUCCESS': {
				showToastDialog("CREATE SUCCESS", "Create pressing monitor success", "success");
				break;
			}
			
			case 'CREATE_FAILED':{
				showToastDialog("CREATE FAILED", "Create pressing monitor failed", "error");
				break;
			}
			
			case 'UPDATE_SUCCESS': {
				showToastDialog("UPDATE SUCCESS", "Update pressing mornitor success", "success");
				break;
			}
			
			case 'UPDATE_FAILED': {
				showToastDialog("UPDATE FAILED", "Update pressing monitor failed", "error");
				break;
			}
			
			case 'DELETE_SUCCESS': {
				showToastDialog("DELETE SUCCESS", "Delete pressing mornitor success", "success");
				break;
			}
			
			case 'DELETE_FAILED': {
				showToastDialog("DELETE FAILED", "Delete pressing monitor failed", "error");
				break;
			}
			
			case 'UPDATE_DEFAULT_VALUE_SUCCESS': {
				showToastDialog("CONFIG SUCCESS", "Set default value pressing mornitor success", "success");
				break;
			}
			
			case 'UPDATE_DEFAULT_VALUE_FAILED': {
				showToastDialog("CONFIG FAILED", "Set default value pressing monitor failed", "error");
				break;
			}
			
		}
	}
})

$( "#inputForm" ).submit(function( event ) {
		$("#inputForm" ).validate();
	  	event.preventDefault();
	  $('#inputForm').addClass('was-validated');
	  let form = document.getElementById('inputForm');
	  if(!form.checkValidity()){
		  showToastDialog('Validation failed', 'Please check value in these field and submit again', 'warning');
	  } else {
		  $(this).unbind();
		  $(this).submit();
	  }
  
});

$( "#configForm" ).submit(function( event ) {
		$("#inputForm" ).validate();
	  	event.preventDefault();
	  $('#inputForm').addClass('was-validated');
	  let form = document.getElementById('inputForm');
	  if(!form.checkValidity()){
		  showToastDialog('Validation failed', 'Please check value in these field and submit again', 'warning');
	  } else {
		  $(this).unbind();
		  $(this).submit();
	  }
  
});

function fetchApi(id) {
	$.get(`pressing-monitor/update?id=${id}`, function (data) {
		setValuetoInput(data);
	});
}

function setValuetoInput(data) {
	$('#supplier-lot').val(data.ingredientBatchId);
	$('#typeProduct').val(data.typeProductId);
	$('#machineId').val(data.machineId);
	$('#weight').val(data.weight);
	$('#netStatus').val(`${data.netStatusBeforePress}`);
	$('#timePressure').val(data.pressingTime);
	$('#pressure').val(`${data.pressureCondition}`);
	$('#weightCoconutMilk').val(data.weightCoconutMilk);
	$('#weightResidue').val(data.weightResidue);
	$('#note').val(data.note);
	$('#createIdEmployee').val(data.createIdEmployee);
	$('#status').val(data.status);

	// Set value to hidden input
	$('#id').val(data.id);
	$('#saveDate').val(data.inputTime);

	//
	$('#inputForm').attr('action', 'pressing-monitor/update');
	$('#submitButton').text('Cập nhật');
	$('#dateIn').val(data.inputDate);
	$('#inputTime').val(data.inputTime);

	// Auto scrool
	$('body,html').animate({ scrollTop: 15 }, 800);

}

function resetData() {
	
	$('#supplier-lot').val(entity.ingredientBatchId);
	$('#weight').val(entity.weight);
	$('#netStatus').val(`${entity.netStatusBeforePress}`);
	$('#timePressure').val(entity.pressingTime);
	$('#pressure').val(`${entity.pressureCondition}`);
	$('#weightCoconutMilk').val(entity.weightCoconutMilk);
	$('#weightResidue').val(entity.weightResidue);
	$('#note').val(entity.note);
	$('#id').val(0);
	$('#createIdEmployee').val(entity.createIdEmployee);
	$('#status').val(0);
	$('#typeProduct').val(entity.typeProductId);
	$('#machineId').val(entity.machineId);
	let date = new Date().getTime();
	$('#saveDate').val(date);
	setDate(date);

	$('#submitButton').text('Thêm mới');

	$('#inputForm').attr('action', `${windown.location.pathname}/create`);

}

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

function updateDefaultValueById(id){
	window.location.href = `./pressing-monitor/default-value?id=${id}`;
}

function deletePressingMonitor(id) {
	const swalWithBootstrapButtons = Swal.mixin({
		customClass: {
			confirmButton: 'btn btn-success',
			cancelButton: 'btn btn-danger'
		},
		buttonsStyling: true
	})

	swalWithBootstrapButtons.fire({
		title: 'Are you sure?',
		text: "Xóa lần ép cốt",
		icon: 'warning',
		showCancelButton: true,
		confirmButtonText: 'Yes, delete it!',
		cancelButtonText: 'No, cancel!',
		reverseButtons: true
	}).then((result) => {
		if (result.isConfirmed) {
			window.location.href = `./pressing-monitor/delete?id=${id}`;
		} else if (
			result.dismiss === Swal.DismissReason.cancel
		) {
			swalWithBootstrapButtons.fire(
				'Cancelled',
				'Bạn đã hủy hành động',
				'error'
			)
		}
	})
}

$('#weightCoconutMilk, #weight').keyup(function () {
	$('#weightResidue').val($('#weight').val() - $('#weightCoconutMilk').val());
	$('#weightCoconutMilk').attr('max', $('#weight').val());
})

jQuery.validator.setDefaults({
    onfocusout: function (e) {
        this.element(e);
    },
    onkeyup: function(element) {$(element).valid()},

    highlight: function (element) {
        jQuery(element).closest('.form-control').addClass('is-invalid');
    },
    unhighlight: function (element) {
        jQuery(element).closest('.form-control').removeClass('is-invalid');
        jQuery(element).closest('.form-control').addClass('is-valid');
    },

    errorElement: 'div',
    errorClass: 'invalid-feedback',
    errorPlacement: function (error, element) {
        if (element.parent('.input-group-prepend').length) {
            $(element).siblings(".invalid-feedback").append(error);
            error.insertAfter(element.parent());
        } else {
            error.insertAfter(element);
        }
    },
});

jQuery.extend(jQuery.validator.messages, {
    required: "This field is required.",
    remote: "Please fix this field.",
    email: "Please enter a valid email address.",
    url: "Please enter a valid URL.",
    date: "Please enter a valid date.",
    dateISO: "Please enter a valid date (ISO).",
    number: "Please enter a valid number.",
    digits: "Please enter only digits.",
    creditcard: "Please enter a valid credit card number.",
    equalTo: "Please enter the same value again.",
    accept: "Please enter a value with a valid extension.",
    maxlength: jQuery.validator.format("Please enter no more than {0} characters."),
    minlength: jQuery.validator.format("Please enter at least {0} characters."),
    rangelength: jQuery.validator.format("Please enter a value between {0} and {1} characters long."),
    range: jQuery.validator.format("Please enter a value between {0} and {1}."),
    max: jQuery.validator.format("Please enter a value less than or equal to {0}."),
    min: jQuery.validator.format("Please enter a value greater than or equal to {0}.")
});