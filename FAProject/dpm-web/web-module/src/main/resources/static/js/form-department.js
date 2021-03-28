$(document).ready(function() {

	
	
	$("#create-button").click(function() {
		$("#exampleModalLabel").html("Thêm phòng ban");
		$("#departmentCode").attr("readonly", false);
		$("#manager").val("");
		$("#departmentName").val("");
		$("#departmentCode").val("");
	});

	
	//
	$( ".edit-button" ).each(function(index) {
		$(this).on("click", function(){
			$("#exampleModalLabel").html("Sửa Phòng Ban");
			
			// $("#signupForm").action =
			

			// Get value from current row
			var departmentCode = $("#" + (index) + " .departmentCode").text();
			var departmentName = $("#" + (index) + " .departmentName").text();
			var manager = $("#" + (index) + " .manager").text();
			
			// Get value from current row
//			var additiveName = $("#" + (index + 1) + " .additiveName").text();
//			var additiveCode = $("#" + (index + 1) + " .additiveCode").text();
//			var additiveId = $("#" + (index + 1) + " .additiveId").attr("value");
			
			// Set value to edit table
			$("#manager").val(manager);
			$("#departmentName").val(departmentName);
			$("#departmentCode").val(departmentCode);
			
			$("#departmentCode").attr('readonly', true);
		});
	});
	
	$( ".delete-button").each(function(index){
		$(this).on("click", function(){
			// Get value from current row
			var departmentCode = $("#" + (index) + " .departmentCode").text();//attr("value");
			console.log(departmentCode);
			// Set value to delete form
			$("#deleteId").val(departmentCode);
		});
	});
});