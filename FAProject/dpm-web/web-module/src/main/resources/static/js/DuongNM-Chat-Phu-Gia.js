(function ($) {
    'use strict';

    // Save message
    /*$.validator.setDefaults({
        submitHandler: function () {
            alert("Saved!");
        }
    });*/
    $(function () {
        // validate signup form on keyup and submit
        $("#signupForm").validate({

            /* Rules */
            rules: {
                additiveCode: "required",
                additiveName: "required"
            },
            /* End Rules */

            /* Messages */
            messages: {
                additiveCode: "Nhập mã phụ gia!",
                additiveName: "Nhập loại phụ gia!"
            },
            /* End Messages */

            errorPlacement: function (label, element) {
                label.addClass('mt-2 text-danger');
                label.insertAfter(element);
            },
            highlight: function (element, errorClass) {
                $(element).parent().addClass('has-danger')
                $(element).addClass('form-control-danger')
            }
        });
       
        //code to hide topic selection, disable for demo
        var newsletter = $("#newsletter");
        // newsletter topics are optional, hide at first
        var inital = newsletter.is(":checked");
        var topics = $("#newsletter_topics")[inital ? "removeClass" : "addClass"]("gray");
        var topicInputs = topics.find("input").attr("disabled", !inital);
        // show when newsletter is checked
        newsletter.on("click", function () {
            topics[this.checked ? "removeClass" : "addClass"]("gray");
            topicInputs.attr("disabled", !this.checked);
        });
    });

    $( ".edit-button" ).each(function(index) {
		$(this).on("click", function(){
			$( "#exampleModalLabel" ).html("Sửa thông tin loại phụ gia");
			
			// Get value from current row
			var additiveName = $("#" + (index + 1) + " .additiveName").text();
			var additiveCode = $("#" + (index + 1) + " .additiveCode").text();
			var additiveId = $("#" + (index + 1) + " .additiveId").attr("value");
			
			// Set value to edit table
			$("#additiveName").val(additiveName);
			$("#additiveCode").val(additiveCode);
			$("#id").val(additiveId);
		});
    });
    $( "#create-button" ).click(function() {
        $( "#exampleModalLabel" ).html("Tạo mới loại phụ gia");

		// Set emty value to create table
		$("#additiveName").val("");
		$("#additiveCode").val("");
		$("#id").val(0);
    });
	
	$( ".delete-button").each(function(index){
		$(this).on("click", function(){
			// Get value from current row
			var additiveId = $("#" + (index + 1) + " .additiveId").attr("value");
			
			// Set value to delete form
			$("#deleteId").val(additiveId);
		});
	});
    
})(jQuery);