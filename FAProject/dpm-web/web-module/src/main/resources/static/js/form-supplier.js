$(document).ready(function () {



	$("#create-button").click(function () {
		$("#exampleModalLabel").html("Thêm Nhà Cung Cấp");
		$("#supplierCode").val("");
		$("#supplierName").val("");
		$("#phoneNumber").val("");
		$("#address").val("");
		$("#id").val(0);
	});



	$(".edit-button").each(function (index) {
		$(this).on("click", function () {
			$("#exampleModalLabel").html("Sửa Nhà Cung Cấp");

			// $("#signupForm").action =


			// Get value from current row
			var supplierCode = $("#" + (index) + " .supplierCode").text();
			var supplierName = $("#" + (index) + " .supplierName").text();
			var phoneNumber = $("#" + (index) + " .phoneNumber").text();
			var address = $("#" + (index) + " .address").text();
			var id = $("#" + (index) + " .supId").attr("value");

			// Set value to edit table
			$("#supplierCode").val(supplierCode);
			$("#supplierName").val(supplierName);
			$("#phoneNumber").val(phoneNumber);
			$("#address").val(address);
			$("#id").val(id);

		});
	});
	
	$( ".delete-button").each(function(index){
		$(this).on("click", function(){
			// Get value from current row
			var supId = $("#" + (index) + " .supId").attr("value");
			console.log(supId);
			// Set value to delete form
			$("#deleteId").val(supId);
		});
	});
	




});