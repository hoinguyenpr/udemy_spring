/**
 * @author LongVT7
 */

$('#selectAll').click(function() {
	if ($(this).prop("checked")) {
		$('.checkRow').each((index, ele) => {
			$(ele).prop("checked", true);
		})
	} else {
		$('.checkRow').each((index, ele) => {
			$(ele).prop("checked", false);
		})
	}
})

$('.checkRow').click(function() {

	if ($('.checkRow').length === $("input.checkRow:checked").length) {
		$("#selectAll").prop("checked", true);
	} else {
		$("#selectAll").prop("checked", false);
	}

})

function getIdsFromCheckedInput() {

	let arr = [];

	$("input.checkRow:checked").each((index, ele) => {
		arr.push($(ele).val());
	})

	return arr;

}

$('#approveall').click(() => {

	let arr = getIdsFromCheckedInput();

	if (arr.length > 0) {

		$("#ids").val(arr);
		$('#approveForm').attr('action', '/pressing-monitor/approve')
		$('#approveForm').submit();

	} else {
		alert('Please check least 1 checkbox')
	}
});

$('#refuseall').click(() => {

	let arr = getIdsFromCheckedInput();

	if (arr.length > 0) {

		$("#ids").val(arr);
		$('#approveForm').attr('action', '/pressing-monitor/refuse')
		$('#approveForm').submit();

	} else {
		alert('Please check least 1 checkbox')
	}
});

$('#currentSize').change(function() {
	$('#size').val(this.value);
	$('#filterForm').submit();
})

$('#currentStatus').change(function() {
	$('#status').val(this.value);
	$('#filterForm').submit();
})

function submitFilter(index) {
	$('#page').val(index);
	$('#filterForm').submit();
}

function approve(id) {
	$("#ids").val(id);
	$('#approveForm').attr('action', '/pressing-monitor/approve')
	$('#approveForm').submit();
}

function refuse(id) {
	$("#ids").val(id);
	$('#approveForm').attr('action', '/pressing-monitor/refuse')
	$('#approveForm').submit();
}