(function ($) {
    'use strict';

    // Save message
    /*$.validator.setDefaults({
        submitHandler: function () {
            alert("Saved!");
        }
    });*/

    $( ".edit-button" ).each(function(index) {
		$(this).on("click", function(){
			$( "#exampleModalLabel" ).html("Sửa loại sản phẩm");
			
			// Get value from current row
			var typeProductNote = $	("#" + 	(index + 1) + " .typeProductNote").text();
			var typeProductName = $	("#" + 	(index + 1) + " .typeProductName").text();
			var typeProductCode = $	("#" + 	(index + 1) + " .typeProductCode").text();
			var typeProductId =   $	("#" + 	(index + 1) + " .typeProductID").attr("value");
			
		
			
			// Set value to edit table
			$("#typeProductNote").val(typeProductNote);	
			$("#typeProductName").val(typeProductName);
			$("#typeProductCode").val(typeProductCode);
			$("#id").val(typeProductId);
		});
    });
    $( ".create-button" ).click(function() {
        $( "#exampleModalLabel" ).html("Tạo mới loại sản phẩm");

		// Set emty value to create table
		$("#typeProductNote").val("");
		$("#typeProductName").val("");
		$("#typeProductCode").val("");
		$("#id").val(0);
    });
	
    
    
    // Delete button listener
	$(".delete-button").each(function (index) {
		$(this).on("click", function () {
			// Get value from current row
			var typeProductID = $("#" + (index + 1) + " .typeProductID").attr("value");
			console.log(typeProductID);

			// Set value to delete form
			$("#deleteIda").val(typeProductID);
		});
	});
    
    
    
    
    
    
    
})(jQuery);
