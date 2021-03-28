$('#productCode').blur(function() {

	$.get(`get-produc?productCode=P${$('#productCode').val()}`, function(data) {
		if (data) {
			$('#productCode').addClass("is-invalid");
		} else {
			$('#productCode').removeClass("is-invalid");
			/* $('#submitButton').removeAttr('disabled'); */
		}
	});

});

$('#formCreate')
		.submit(
				function(e) {
					e.preventDefault();

					$('#productCode').removeClass("is-invalid");

					$('#formCreate').addClass('was-validated');

					let form = document.getElementById('formCreate');

					if (form.checkValidity()) {

						$('#formCreate').removeClass('was-validated');

						$
								.get(
										`get-produc?productCode=P${$('#productCode').val()}`,
										function(data) {
											if (data) {
												$('#productCode').addClass(
														"is-invalid");
												showToastDialog("CREATE FAIL",
														"Create prdocuct fail",
														"fail");

											} else {
												$('#productCode').removeClass(
														"is-invalid");
												$
														.get(
																`check-date?strDate=${$('#dateInput').val()}`,
																function(date) {
																	if (!date) {
																		$(
																				'#dateInput')
																				.addClass(
																						"is-invalid");
																		showToastDialog(
																				"CREATE FAIL",
																				"Create prdocuct fail",
																				"fail");
																	} else {
																		$(
																				'#formCreate')
																				.unbind();
																		$(
																				'#formCreate')
																				.submit();
																	}
																});
											}
										});
					} else {
						showToastDialog("CREATE FAIL", "Create prdocuct fail",
								"fail");

					}

				});

showToastDialog = function(heading, text, icon) {
	'use strict';
	$.toast({
		heading : heading,
		text : text,
		showHideTransition : 'slide',
		icon : icon,
		loaderBg : '#57c7d4',
		position : 'top-right'
	})
};