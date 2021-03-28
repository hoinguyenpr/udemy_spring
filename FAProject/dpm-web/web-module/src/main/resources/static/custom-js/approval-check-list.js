$(document).ready(function () {
	
	let isCreate = (window.location.href.indexOf("#create") > -1);
	if(isCreate){
		$('#card-create').removeClass('hide');
	}
	
	$('#checkall').click(function(e) {
		
		if (this.checked == true) {
		
			$('input:checkbox[name=ids]').prop('checked', true);
			
		}else{
			console.log('!=')
			
			$('input:checkbox[name=ids]').prop('checked', false);
		}
		
	});
	
	$('#approval-selected').click(function (e) {
		e.preventDefault();
		$('#approvalOrRejectForm').attr('action','/checklist/approval');
		let data = $('#approvalOrRejectForm').serialize();
		if (data=='') {
			swal({
	            title: "Không có bản ghi nào được chọn!",
	            text: "Không thể duyệt mà không có bản ghi nào được chọn!",
	            icon: "error",
	        })
		}else{
			$('#approvalOrRejectForm').submit();
		}
				

	});
	$('#reject-selected').click(function (e) {
		e.preventDefault();
		$('#approvalOrRejectForm').attr('action','/checklist/reject');
		let data = $('#approvalOrRejectForm').serialize();
		if (data=='') {
			swal({
	            title: "Không có bản ghi nào được chọn!",
	            text: "Không thể hủy  mà không có bản ghi nào được chọn!",
	            icon: "error",
	        })
		}else{
			$('#approvalOrRejectForm').submit();
		}	
		
	});
	
	$('#remove-selected').click(function (e) {
		e.preventDefault();
		$('#approvalOrRejectForm').attr('action','/checklist/remove');
		let data = $('#approvalOrRejectForm').serialize();
		if (data=='') {
			swal({
	            title: "Không có bản ghi nào được chọn!",
	            text: "Không thể xóa mà không có bản ghi nào được chọn!",
	            icon: "error",
	        })
		}else{
			$('#approvalOrRejectForm').submit();
		}
				

	});
	$('.approval-checklist').click(function(e) {
		e.preventDefault();
		let id = $(this).attr('data-approval');
		$('#inputApproval').val(id);
		$('#approvalForm').submit();
		
	});
	$('.reject-checklist').click(function(e) {
		e.preventDefault();
		let id = $(this).attr('data-reject');
		$('#inputReject').val(id);
		
	});
	$('#createChecklist, #createChecklist2').click(function(e) {
		e.preventDefault();
		
		$('#card-create').removeClass('hide');
		$("html, body").animate({ scrollTop: 70 }, "slow");
		
	});
	
	
	$('#numberResultSelect').on('change', function() {
		
//	    let size = $(this).val();
//		$('#sizeInput').val(size);
		$('#pageResult').val('0');
		$('#searchtForm').submit();
	});
	
	$('#dateResultSelect').on('change', function() {
		
//	    let size = $(this).val();
//		$('#sizeInput').val(size);
		$('#searchtForm').submit();
	});
	
});