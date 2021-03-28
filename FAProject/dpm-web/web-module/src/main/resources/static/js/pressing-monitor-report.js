/**
 * Author: <b> LongVT7 </b>
 */

$('#currentSize').change(function() {
	$('#inputSize').val($('#currentSize').val());
})

// Show/hide columns selected by modal
function showhidetable(val) {
	var tb = document.getElementById("record-table");
	var tbtr = tb.getElementsByTagName("tr");
	$(tbtr[0].getElementsByTagName("th")[val]).toggle();

	for (let i = 0; i < tbtr.length; i++) {
		$(tbtr[i].getElementsByTagName("td")[val]).toggle();
	}
}

// Show all columns
function showAllCol() {
	$(":checkbox:not(:checked)").prop("checked", true);
	$("th").show();
	$("td").show();
}

function hideAllCol() {
	$(":checkbox").prop("checked", false);
	$("th").hide();
	$("td").hide();
}

function toggle() {
	var status = document.getElementById('toggle-state').checked
	if (status) {
		showAllCol();
	} else {
		hideAllCol();
	}
}

function submitFilter(page){
	$('#inputPage').val(page);
	$('#filterForm').submit();
}

$('#startInputDate, #endInputDate').change(function(){
	$('#inputPage').val(0);
})
   
   $('#configButton').click(function() {
   // reset modal if it isn't visible
   if (!($('.modal.in').length)) {
     $('.modal-dialog').css({
       top: 0,
       left: 0
     })
   };

   $('#configModal').draggable({
     handle: ".modal-header"
   });
   
   $('#configModal').modal('show');
   
 });;