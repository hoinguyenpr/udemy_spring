
// ThuanLV6
$(document).ready(function () {
		loadFilterElements();
		loadData2();
		
	    $("#scale-calibration-form").submit(function (event) {
	    	 //stop submit the form, we will post it manually.
	        event.preventDefault();
	        addScaleCalibration();
		});
		

		// search and filter button event
		$('#form-search').submit(function(event){
			event.preventDefault();
			// alert("submit");
			// loadWithFilterAndSearch(); // load data table with filter and search
			loadData2();
		});
		$('#btn-export').click(function(){
		
			exportExcel();
		});

		
		$('.js-example-basic-single').select2({

			width: '100%'
		});
	
	

});

// load default select 2 option
$("#exampleModal").on('shown.bs.modal', function () {
	console.log("Modal loaded!");
	$('#department').val($('#department option:first-child').val());
	$('#calibrated-device').val($('#calibrated-device option:first-child').val());
	$('#calibrated-device-symbol').val($('#calibrated-device-symbol option:first-child').val());
	$('#standard-device').val($('#standard-device option:first-child').val());
	$('#calibrator').val($('#calibrator option:first-child').val());

});


function getFilterAndSearchDto(){
	console.log("Do filter and search...");
	var filterDto = {
		startDate : $('#from-date-filter').val(),
		endDate : $('#to-date-filter').val(),
		calibratorId : $('#calibrator-filter').val(),
		inspectorId : $('#inspector-filter').val(),
		isApproved : $('#approved-filter').is(':checked'),
		isPending : $('#pending-filter').is(':checked'),
		isRejected : $('#rejected-filter').is(':checked'),
		keyword : $('#search-text').val()
	}
	return filterDto;

}

function loadFilterElements(){
	loadCalibratorsList();
	loadInspectorsList();
}
function loadInspectorsList(){
	$('#inspector-filter').empty(); // clear the calibrator selector
		$('#inspector-filter').append($('<option>')
									.append('---Vui lòng chọn---')
									.attr('value',0)
										);
}
// load calibrators list
function loadCalibratorsList(){
	urlStringSave = "/scale-calibration/api/get-calibrators-list";
	$.ajax({
	type : "GET",
	contentType : "application/json",
	url : urlStringSave,
	dataType : 'json',
	success : function(result) {
		// console.log('loadCalibratorsList() :');
		$('#calibrator-filter').empty(); // clear the calibrator selector
		$('#calibrator-filter').append($('<option>')
									.append('---Vui lòng chọn---')
									.attr('value',0)
										);

		result.forEach(element => {
			$('#calibrator-filter').append($('<option>')
									.attr('value',element.id)
									.append(element.employeeName)
								)
		});
		
		console.log(result);
	},
	error : function(e) {
		swal("Lỗi", "Không tải được danh sách người hiệu chỉnh!", "error");
		console.log("ERROR: ", e);
	}
	});
}

$('#checkbox-other-method')
	.change(function() {
				if (this.checked) {
					$('#method-input').prop('disabled',false);
				} else {
					$('#method-input').prop('disabled',true);
				}
			});

function addScaleCalibration(){
		
	var dto = {
			id: $("#id").val(),
			createdDate: $("#created-datetime").val(),
			calibratedDeviceId : $("#calibrated-device").val(),
			departmentCode :  $("#department").val(),
			calibratingMethod: $("#method-input").val(),
			firstResult: $("#result-1").val(),
			secondResult: $("#result-2").val(),
			thirdResult: $("#result-3").val(),
			comment: $("#comment").val(),
			standardDeviceId: $("#standard-device").val(),
			scaleSymbolId: $("#calibrated-device-symbol").val(),
			calibratorId: $("#calibrator").val()
	}
	// console.log(dto);
	
		// DO POST save new record
	urlStringSave = "/scale-calibration/api/save";
	$.ajax({
	type : "POST",
	contentType : "application/json",
	url : urlStringSave,
	data : JSON.stringify(dto),
	dataType : 'json',
	success : function(result) {
		if(result.msg == "Done"){
		swal("Lưu", "Lưu dữ liệu thành công!", "success");
			loadData2(getCurrentPageNumber());
			$("#exampleModal").modal("toggle");
		}
//	          else{
//	           alert("Not done!");
//	          }
		
		// console.log(result);
	},
	error : function(e) {
		swal("Lưu", "Lưu dữ liệu thất bại!", "error");
		console.log("ERROR: ", e);
		}
	});
}
//	start delete

function deleteScaleCalibration(id){
	// ==> start sweet alert delete
	 swal({
	        title: 'Xác nhận xóa?',
	        text: "Chú ý: Sau khi xoá sẽ không thể khôi phục.",
	        icon: 'warning',
	        showCancelButton: true,
	        confirmButtonColor: '#3f51b5',
	        cancelButtonColor: '#ff4081',
	        confirmButtonText: 'Great ',
	        buttons: {
	            confirm: {
	                text: "Xóa",
	                value: "true",
	                visible: true,
	                className: "btn btn-danger",
	                closeModal: true
	            },
	            cancel: {
	                text: "Hủy",
	                value: "null",
	                visible: true,
	                className: "btn btn-light",
	                closeModal: true,
	            }
	        }
	    }).then(function (value) {
	        /*
	        * Check action user submit.
	        */
	        if (value === "true") {
//	        	==> start AJAX
	       	 // DO POST save new record
	       	urlStringSave = "/scale-calibration/api/delete/"+id;
	       	$.ajax({
	           type : "POST",
	           contentType : "application/json",
	           url : urlStringSave,
//	           data : JSON.stringify(dto),
	           dataType : 'json',
	           success : function(result) {
	             if(result.msg == "Done"){
	            	swal("Xóa", "Xóa dữ liệu thành công!", "success");
	             	loadData2(getCurrentPageNumber());
	             
	             }
	            /* else{
	              alert("Not done!");
	             }*/
	             
	             console.log(result);
	           },
	           error : function(e) {
	        	 swal("Xóa", "Xóa dữ liệu thất bại!", "error");
	             console.log("ERROR: ", e);
	           }
	         });
	           
	           // end Ajax <==
	        }
	    });
	// end sweet alert delete <==
	

}

	
// end delete

	  function getScaleCalibrationById(id){
		  
		  $('#btn-add').val("Cập nhật");
		  $('#btn-add').addClass('btn-warning').removeClass('btn-primary');
		  $('#checkbox-other-method').prop('checked', false);
		  $('#method-input').prop('disabled',true);
			urlString = "/scale-calibration/api/"+id;
			$.ajax({method:"GET",url: urlString})
			.done(function(responseJson){
				console.log('scale calibration with id: '+id);
				console.log(responseJson);
				$('#id').val(responseJson.id);
				$('#created-datetime').val(responseJson.createdDate);
				$('#department').val(responseJson.department.departmentCode);
				$('#calibrated-device').val(responseJson.calibratedDevice.id);
				$('#calibrated-device-symbol').val(responseJson.scaleSymbol.id);
				$('#standard-device').val(responseJson.standardDevice.id);
				$('#method-input').val(responseJson.calibratingMethod);
				$('#calibrator').val(responseJson.calibrator.id);
				$('#result-1').val(responseJson.firstResult);
				$('#result-2').val(responseJson.secondResult);
				$('#result-3').val(responseJson.thirdResult);
				$('#comment').val(responseJson.comment);
				calculateAverage();
				
			})
			.fail(function(){
				console.log("There is error calling REST API...");
			})
			.always(function(){
				/* alert("always exeuted..."); */
			})
	    }
	  
	
	
//	start loadData2()
	function loadData2(pageNumber) {
//		alert(pageNumber);
		urlString = "scale-calibration/api/get-page-with-filter?pageNumber="
			+(pageNumber === undefined ? 0 : pageNumber-1)
			+"&amount="+($('#element').val()=== undefined ? 10 : $('#element').val());
		// console.log('current page: '+$('ul.pagination.d-flex.justify-content-end li.active').text());
//		console.log(urlString);
		$('ul.pagination.d-flex.justify-content-end').empty(); // clear paging bar
		$.ajax(
			{
			type : "POST",
			contentType : "application/json",
			url : urlString,
			data : JSON.stringify(getFilterAndSearchDto()),
			dataType : 'json'
			}
		)
		.done(function(responseJson){
			
			// append previous button
			$('ul.pagination.d-flex.justify-content-end')
			.append($('<li>')
						.attr('class','page-item')
						.append($('<a>')
									.attr('class','page-link')
									.append($('<i>')
										.attr('class', 'icon-arrow-left')	
									)
								)	
			);
			console.log(responseJson);
			for(var i=1; i<=responseJson.totalPages; i++){
//				console.log('pageNumber '+i + (i-1==responseJson.number));
				// append page numbers
				$('ul.pagination.d-flex.justify-content-end')
				.append($('<li>')
							.attr('class','page-item'+ (i-1 == responseJson.number ? ' active': ''))
//							.attr('class','page-item'+ (i-1 == pageNumber ? ' active': ''))
							.attr('onclick','onPageNumberClick('+i+')')
							.append($('<a>')
										.attr('class','page-link')
										.append(i)
									)	
				);
				if(i-1==responseJson.number){
					// console.log('active page is: '+i);
				}
			}
			// append previous button
			$('ul.pagination.d-flex.justify-content-end')
			.append($('<li>')
						.attr('class','page-item')
					
						.append($('<a>')
									.attr('class','page-link')
									.append($('<i>')
										.attr('class', 'icon-arrow-right')	
									)
								)	
			);
			
			
			if(responseJson.empty){
				console.log("Error: pageNumber or amount is out of range!!!");
				loadData2(1);
			}
			$("#my-data-table").find('tbody').empty();
			var n = responseJson.number * responseJson.numberOfElements + 1; // numerical order
			console.log(n);
			console.log(responseJson);
			$.each(responseJson.content, function(index,object){
			
				
				$("#my-data-table").find('tbody')
			   		.append($('<tr>')
					   		.append($('<td>')
							 		.append(n++)
							   )
			   				.append($('<td>')
							   		.attr("hidden","true")
			   						.append(object.id))
			   				.append($('<td>')
			   						.append(object.createdDate))
			   				.append($('<td>')
			   						.append(object.calibratedDevice.machineName))
			   				.append($('<td>')
			   						.append(object.department.departmentName))
			   				.append($('<td>')
			   						.append(object.scaleSymbol.symbolString))
			   				.append($('<td>')
			   						.append(object.calibrator.employeeName))
			   				.append($('<td>')
			   						.append(object.inspector))
			   				.append($('<td>')
			   						.append(object.avarageResult))
			   				.append($('<td>')
							   .append($('<div>')
									   .append($('<i>')
											 .attr('class','fas'+(object.status == 'Pending'?' fa-eye':
											 (object.status == 'Approved'?	' fa-check-circle':' fa-times-circle')))  
											 .attr('style','color: '+(object.status == 'Pending'?'#ff922b;':
											 (object.status == 'Approved'?	'#51cf66;':'#ff6b6b;')))
											 .attr('title',''+(object.status == 'Pending'?'Chờ duyệt':
											 (object.status == 'Approved'?	'Đã duyệt':'Bị từ chối')))
											 .attr('value',object.status)
									   )
							   )
							)
									
			   				.append($('<td>')
			   						.append($('<button>')
			   								.append($('<i>').attr('class','icon-pencil'))
			   								.append('Edit')
			   								.attr('type','button')
			   								.attr('class','btn-edit btn btn-outline-info btn-fw')
			   								.attr('data-toggle','modal')
			   								.attr('data-target', '#exampleModal')
			   								.attr('onclick','getScaleCalibrationById('+object.id+')')
			   								)
			   						.append($('<button>')
			   								.append($('<i>').attr('class', 'icon-trash'))
			   								.append('Delete')
			   								.attr('onclick','deleteScaleCalibration('+object.id+')')
			   								.attr('class','btn btn-outline-danger btn-fw')))
			   						);
			})
		})
		.fail(function(){
			console.log("There is error calling REST API...");
		})
		.always(function(){
			/* alert("always executed..."); */
		})
}
	
//	end loadData2()
	
	function onPageNumberClick(buttonIndex){
		$('ul.pagination.d-flex.justify-content-end li.active').removeClass('active');
//		console.log(buttonIndex);
		$('ul.pagination.d-flex.justify-content-end').children().eq(buttonIndex).addClass('active');
		
		loadData2(getCurrentPageNumber());
	}
	
	$('#element').click(()=>{
		loadData2(getCurrentPageNumber());
		$('ul.pagination.d-flex.justify-content-end li.active').children().eq(1).addClass('active');
	});
	
	function getCurrentPageNumber(){
		// console.log('current Page number: '+$('ul.pagination.d-flex.justify-content-end li.active').text() );
		return $('ul.pagination.d-flex.justify-content-end li.active').text();
	}
	
	function calculateAverage(){
		$('#avg-result').text(
				Number(((parseFloat($('#result-1').val())+parseFloat($('#result-2').val())+parseFloat($('#result-3').val()))/3).toFixed(2)));
	}
	
	$("#result-1, #result-2, #result-3").change(()=>{
		calculateAverage();
	});
	
	 function reloadForm(){
		 	$('#id').val('');
			$('#created-datetime').val('');
			$('#department').val('');
			$('#calibrated-device').val('');
			$('#calibrated-device-symbol').val('');
			$('#standard-device').val('');
			$('#method-input').val('Thủ công');
			$('#calibrator').val('');
			$('#result-1').val(1);
			$('#result-2').val(1);
			$('#result-3').val(1);
			$('#comment').val('');
			$('#checkbox-other-method').prop('checked', false);
			$('#method-input').prop('disabled',true);
			calculateAverage();
			$('#btn-add').val("Thêm");
			$('#btn-add').addClass('btn-primary').removeClass('btn-warning');
	    }
	 
	 $('#btn-create').click(()=>{
		 reloadForm();
	 })
function exportExcel() {
	var filterDto = getFilterAndSearchDto();
	var downloadUrl = "/scale-calibration/api/export?"
	+"fromDate="+ filterDto.startDate
	+"&toDate="+filterDto.endDate
	+"&calibratorId="+filterDto.calibratorId
	+"&inspectorId="+filterDto.inspectorId
	+"&isApproved="+filterDto.isApproved
	+"&isRejected="+filterDto.isRejected
	+"&isPending="+filterDto.isPending
	+"&keyword="+filterDto.keyword;
// (optionally) provide the user with a message that the download is starting
	window.location.href = downloadUrl;
}