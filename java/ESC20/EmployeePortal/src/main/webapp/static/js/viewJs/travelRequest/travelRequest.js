$(document).ready(function() {
	if (isAddValue == 'true') {
		$("#travelRequestModal").modal('show')
	}
	if (changeCommuteDist == 'true') {
		$("#travelChangeModal").modal('show')
	}
	var formDate = $('#SearchStartDate').fdatepicker({
		format : 'mm-dd-yyyy',
		language : initialLocaleCode,
		weekStart : 7
	}).on('changeDate', function(ev) {
		var fromInput = $("#SearchStartInput").val()
		var toInput = $("#SearchEndInput").val()
		if (fromInput && toInput) {
			var from = ev.date.valueOf()
			var to = toDate.date.valueOf()
			if (from > to) {
				$("#timeErrorMessage").removeClass("hide")
				$("#retrieve").attr("disabled", "disabled")
				$("#retrieve").addClass("disabled")
			} else {
				$("#timeErrorMessage").addClass("hide")
				$("#retrieve").removeAttr("disabled")
				$("#retrieve").removeClass("disabled")
			}
		}
	})
		.data('datepicker')
	var toDate = $('#SearchEndDate').fdatepicker({
		format : 'mm-dd-yyyy',
		language : initialLocaleCode,
		weekStart : 7
	}).on('changeDate', function(ev) {
		var fromInput = $("#SearchStartInput").val()
		var toInput = $("#SearchEndInput").val()
		if (fromInput && toInput) {
			var to = ev.date.valueOf()
			var from = formDate.date.valueOf()
			if (from > to) {
				$("#timeErrorMessage").removeClass("hide")
				$("#retrieve").attr("disabled", "disabled")
				$("#retrieve").addClass("disabled")
			} else {
				$("#timeErrorMessage").addClass("hide")
				$("#retrieve").removeAttr("disabled")
				$("#retrieve").removeClass("disabled")
			}
		}
	}).on('outOfRange', function(ev) {})
		.data('datepicker')
	$(".sureDelete").click(function() {
		$("#deleteForm")[0].submit();
	})
	$("#retrieve").click(function() {
		var fromValue = $("#SearchStartInput").val()
		var toValue = $("#SearchEndInput").val()
		var fromInput = changeDateYMD(fromValue)
		var toInput = changeDateYMD(toValue)
		if ((!fromValue || !toValue) || (fromInput && toInput && fromInput <= toInput)) {
			$("#timeErrorMessage").addClass("hide")
			$("#SearchForm")[0].submit();
		} else {
			$("#timeErrorMessage").removeClass("hide")
		}
	})
});

function changeMMDDFormat(date) {
	var dateArry = date.split("-")
	return dateArry[1] + "-" + dateArry[2] + "-" + dateArry[0]
}

function showChangeRequestForm() {
	$('#changeRequestForm')[0].reset()
	$('#changeRequestForm')
		.data('bootstrapValidator')
		.destroy()
	$('#changeRequestForm').data('bootstrapValidator', null)
	changeformValidator()
	setTimeout(function(){
        $("#changeCommuteDist").focus();
    }, 200);
}

function changeDateYMD(date, notFormat) {
	if (!date) {
		return
	}
	var dateArry = date.split("-")
	if (notFormat) {
		return dateArry[2] + "-" + dateArry[0] + "-" + dateArry[1]
	} else {
		var DateFormat = new Date(dateArry[2] + "-" + dateArry[0] + "-" + dateArry[1])
		return DateFormat
	}

}