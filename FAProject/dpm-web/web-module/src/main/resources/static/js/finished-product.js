$(document).ready(function() {

	// Edit button listener
	$(".edit-button").each(function(index) {
		$(this).on("click", function() {
			$("#exampleModalLabel").html("Sửa thông tin thành phẩm");

			// Get value from current row
			var productCode = $("#" + (index + 1) + " .productCode").text();
			var productName = $("#" + (index + 1) + " .productName").text();
			var finishedProduct = $("#" + (index + 1) + " .finishedProduct").text();
			var unsatisfiedProduct = $("#" + (index + 1) + " .unsatisfiedProduct").text();
			var waterExtract = $("#" + (index + 1) + " .waterExtract").text();
			var coconutSilk = $("#" + (index + 1) + " .coconutSilk").text();
			var scrap = $("#" + (index + 1) + " .scrap").text();
			var crusted = $("#" + (index + 1) + " .crusted").text();
			var residue = $("#" + (index + 1) + " .residue").text();
			var total = $("#" + (index + 1) + " .total").text();
			var note = $("#" + (index + 1) + " .note").text();
			var id = $("#" + (index + 1) + " .deleteID").attr("value");

			// Set value to edit table
			$("#productCode").val(productCode);
			$("#productName").val(productName);
			$("#finishedProduct").val(finishedProduct);
			$("#unsatisfiedProduct").val(unsatisfiedProduct);
			$("#waterExtract").val(waterExtract);
			$("#coconutSilk").val(coconutSilk);
			$("#scrap").val(scrap);
			$("#crusted").val(crusted);
			$("#residue").val(residue);
			$("#total").val(total);
			$("#note").val(note);
			$("#id").val(id);




		});
	});



	// Create button listener
	$(".create-button").click(function() {
		$("#exampleModalLabel").html("Tạo mới thành phẩm");

		// Set emty value to create table
		$("#productCode").val("");
		$("#productName").val("");
		$("#finishedProduct").val("");
		$("#unsatisfiedProduct").val("");
		$("#waterExtract").val("");
		$("#coconutSilk").val("");
		$("#scrap").val("");
		$("#crusted").val("");
		$("#residue").val("");
		$("#total").val("");
		$("#note").val("");
		$("#id").val(0);
	});


	$(".delete-button").each(function(index) {
		$(this).on("click", function() {
			// Get value from current row
			var deleteID = $("#" + (index + 1) + " .deleteID").attr("value");

			// Set value to delete form
			$("#deleteIda").val(deleteID);
		});
	});



});
	

 (jQuery);