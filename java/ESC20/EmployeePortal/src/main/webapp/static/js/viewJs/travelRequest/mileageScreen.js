$(function() {
	addformValidator();
	var checkin = $('#startDateTravel')
		.fdatepicker({
			format : 'mm-dd-yyyy',
			language : initialLocaleCode,
			weekStart : 7
		})
		.on('changeDate', function(ev) {
			var startDate = $('#startDateInputTravel').val()
			var endDate = $('#endDateInputTravel').val()
			var filterValue = formattedDate('01-01-2000')
			var travelFrom = formattedDate(startDate)
			var travelTo = formattedDate(endDate)
			if (travelFrom > filterValue && travelTo > filterValue) {
				if (travelTo > travelFrom) {
					$('#overnightTripShowAll').show()
					if ($("#overnightTrip").is(":checked")) {
						$('#overnightTrip').prop('checked', false);
					}
					$("#startHourTravel").val('').change()
					$("#startMinuteTravel").val('').change()
					$("#endHourTravel").val('').change()
					$("#endMinuteTravel").val('').change()
					$("#startAmOrPmTravel").val('AM').change()
					$("#endAmOrPmTravel").val('AM').change()
					$('#overnightTripShow').hide()
				} else {
					$('#addRequestForm')
						.data('bootstrapValidator')
						.destroy();
					if ($("#overnightTrip").is(":checked")) {
						$('#overnightTrip').prop('checked', false);
					}
					$("#startHourTravel").val('').change()
					$("#startMinuteTravel").val('').change()
					$("#endHourTravel").val('').change()
					$("#endMinuteTravel").val('').change()
					$("#startAmOrPmTravel").val('AM').change()
					$("#endAmOrPmTravel").val('AM').change()
					$('#overnightTripShowAll').hide()
				}
			}
		})
		.data('datepicker')
	var checkout = $('#endDateTravel')
		.fdatepicker({
			// startDate: now,	
			format : 'mm-dd-yyyy',
			language : initialLocaleCode,
			weekStart : 7,
			onRender : function(date) {
				return date.valueOf() < checkin.date.valueOf()
					? 'disabled'
					: ''
			}
		})
		.on('changeDate', function(ev) {
			calcTimeTravel()
			var startDate = $('#startDateInputTravel').val()
			var endDate = $('#endDateInputTravel').val()
			var filterValue = formattedDate('01-01-2000')
			var travelFrom = formattedDate(startDate)
			var travelTo = formattedDate(endDate)
			if (travelFrom > filterValue && travelTo > filterValue) {
				if (travelTo > travelFrom) {
					$('#overnightTripShowAll').show()
					if ($("#overnightTrip").is(":checked")) {
						$('#overnightTrip').prop('checked', false);
					}
					$("#startHourTravel").val('').change()
					$("#startMinuteTravel").val('').change()
					$("#endHourTravel").val('').change()
					$("#endMinuteTravel").val('').change()
					$("#startAmOrPmTravel").val('AM').change()
					$("#endAmOrPmTravel").val('AM').change()
					$('#overnightTripShow').hide()
				} else {
					$('#addRequestForm')
						.data('bootstrapValidator')
						.destroy();
					if ($("#overnightTrip").is(":checked")) {
						$('#overnightTrip').prop('checked', false);
					}
					$("#startHourTravel").val('').change()
					$("#startMinuteTravel").val('').change()
					$("#endHourTravel").val('').change()
					$("#endMinuteTravel").val('').change()
					$("#startAmOrPmTravel").val('AM').change()
					$("#endAmOrPmTravel").val('AM').change()
					$('#overnightTripShowAll').hide()
				}
			}
		})
		.data('datepicker')
	$("input").bind('keypress', function(e) {
		var eCode = e.keyCode
			? e.keyCode
			: e.which
				? e.which
				: e.charCode
		if (eCode == 13) {
			$(this).click()
			event.preventDefault();
		}
	})
	$("#startDateInputTravel").blur(function() {
		var fromValue = convertRightFormat($("#startDateInputTravel").val())
		var toValue = convertRightFormat($("#endDateInputTravel").val())
		var leaveFrom = fromValue ? changeDateYMD(fromValue) : null
		var leaveTo = toValue ? changeDateYMD(toValue) : null
		if (fromValue && toValue) {
			if (leaveFrom <= leaveTo) {
				$('.toDateIsEarlierThanFromDate').hide()
				$(".toDate01").removeClass('has-error')
			} else {
				$('.toDateIsEarlierThanFromDate').show()
				$(".toDate01").addClass('has-error')
			}
		}
	});
	$("#endDateInputTravel").blur(function() {
		var fromValue = convertRightFormat($("#startDateInputTravel").val())
		var toValue = convertRightFormat($("#endDateInputTravel").val())
		var leaveFrom = changeDateYMD(fromValue)
		var leaveTo = changeDateYMD(toValue)
		if (toValue == '') {
			$('.toDateIsEarlierThanFromDate').hide()
			$(".toDate01").removeClass('has-error')
		}
		if (fromValue && toValue) {
			if (leaveFrom <= leaveTo) {
				$('.toDateIsEarlierThanFromDate').hide()
				$(".toDate01").removeClass('has-error')
			} else {
				$('.toDateIsEarlierThanFromDate').show()
				$(".toDate01").addClass('has-error')
			}
		}
	});
	$("#overnightTrip").click(function() {
		if ($(this).is(":checked")) {
			$('#addRequestForm')
				.data('bootstrapValidator')
				.destroy();
			datetimeValidator();
			$("#overnightTripShow").show();
		} else {
			$('#addRequestForm')
				.data('bootstrapValidator')
				.destroy();
			addformValidator();
			$("#startHourTravel").val('').change()
			$("#startMinuteTravel").val('').change()
			$("#endHourTravel").val('').change()
			$("#endMinuteTravel").val('').change()
			$("#startAmOrPmTravel").val('AM').change()
			$("#endAmOrPmTravel").val('AM').change()
			$("#overnightTripShow").hide();
		}
	});
	$("#addRequestForm").focusin(function() {
		$("#startDateFocus").removeClass("startDateFocus");
	})
	$(".requestModalBtn .btn").focus(function() {
		$(".submitClose").removeClass('highlight')
	})
	
	setTimeout(function(){
         $("#contact_0").focus();
     }, 200);
	
	$(".decimal-group-one").addClass("number-seperator-one-decimal");
	$(".decimal-group-two").addClass("number-seperator-two-decimal");
	$(".decimal-group-three").addClass("number-seperator-three-decimal");
	
    var commaCounter = 5;

    function numberSeparator(Number) {
        Number += '';
        for (var i = 0; i < commaCounter; i++) {
            Number = Number.replace(',', '');
        }
        x = Number.split('.');
        y = x[0];
        z = x.length > 1 ? '.' + x[1] : '';
        var rgx = /(\d+)(\d{3})/;

        while (rgx.test(y)) {
            y = y.replace(rgx, '$1' + ',' + '$2');
        }
        commaCounter++;
        return y + z;
    }
    $(document).on('keypress , paste', '.number-seperator-one-decimal', function (e) {
        if (/^-?\d*[,.]?(\d{0,3},)*(\d{3},)?\d{0,3}$/.test(e.key)) {
            $('.number-seperator-one-decimal').on('input', function () {
                e.target.value = numberSeparator(e.target.value).replace(/(\.[\d]{1})./g, '$1');
            });
        } else {
            e.preventDefault();
            return false;
        }
    });
    $(document).on('keypress , paste', '.number-seperator-two-decimal', function (e) {
        if (/^-?\d*[,.]?(\d{0,3},)*(\d{3},)?\d{0,3}$/.test(e.key)) {
            $('.number-seperator-two-decimal').on('input', function () {
                e.target.value = numberSeparator(e.target.value).replace(/(\.[\d]{2})./g, '$1');
            });
        } else {
            e.preventDefault();
            return false;
        }
    });
    $(document).on('keypress , paste', '.number-seperator-three-decimal', function (e) {
        if (/^-?\d*[,.]?(\d{0,3},)*(\d{3},)?\d{0,3}$/.test(e.key)) {
            $('.number-seperator-three-decimal').on('input', function () {
                e.target.value = numberSeparator(e.target.value).replace(/(\.[\d]{3})./g, '$1');
            });
        } else {
            e.preventDefault();
            return false;
        }
    });
    
    initialAutoCompleteList();
})

function removeFocus() {
	$("#startDateFocus").removeClass("startDateFocus");
}

var indx = 0;
function calculateMilesTotal() {
	var totalMiles = 0;
	for (var i = 0; i <= indx + deletedRows; i++) {
		if ($('#total_' + i).text() != '') {
			totalMiles = addFloat(parseFloat(numberWithoutCommas(totalMiles)), parseFloat(numberWithoutCommas($('#total_' + i).text())));
		}
	}
	$('#totalMiles').text(numberWithCommas(totalMiles.toFixed(1)));
}
function calculateMiscTotal() {
	var totalMisc = 0;
	for (var i = 0; i <= indx + deletedRows; i++) {
		if ($('#miscAmt_' + i).val() != '' && $('#miscAmt_' + i).val() !== '0.00' && $('#miscAmt_' + i).val() !== undefined) {
			totalMisc = addFloat(parseFloat(numberWithoutCommas(totalMisc)), parseFloat(numberWithoutCommas($('#miscAmt_' + i).val())));
		}
	}
	$('#totalMisc').text(numberWithCommas(totalMisc.toFixed(2)));
}

function calculateMiscTotalExtended() {
	var totalMisc = 0;
	for (var i = 0; i <= indx + deletedRows; i++) {
		if ($('#other_' + i).val() != '' && $('#other_' + i).val() !== '0.00' && $('#other_' + i).val() !== undefined) {
			totalMisc = addFloat(parseFloat(numberWithoutCommas(totalMisc)), parseFloat(numberWithoutCommas($('#other_' + i).val())));
		}
	}
	$('#totalMisc').text(numberWithCommas(totalMisc.toFixed(2)));
}

function calculateRequestTotal() {
	var totalRequest = 0;
	for (var i = 0; i <= indx + deletedRows; i++) {
		if ($('#totReiumbersment_' + i).text() != '') {
			totalRequest = addFloat(parseFloat(Number(numberWithoutCommas(totalRequest))), parseFloat(numberWithoutCommas($('#totReiumbersment_' + i).text())));
		}
	}
	$('#totalRequest').text(numberWithCommas(totalRequest.toFixed(2)));
}
function closeRequestForm() {
	$('#addRequestForm')
		.data('bootstrapValidator')
		.destroy()
	$('#addRequestForm').data('bootstrapValidator', null)
	newformValidator()
	$(".submitClose").addClass('highlight')
}
function changeFormatTime(value) {
	var array = value.split(/[,: ]/)
	if (array[2] == 'PM') {
		var h
		if (array[0] == 12) {
			h = array[0]
		} else {
			h = parseInt(array[0]) + 12
		}
		return h + ':' + array[1]
	} else {
		var h
		if (array[0] == 12) {
			h = h = parseInt(array[0]) - 12
			return h + ':' + array[1]
		}
		return array[0] + ':' + array[1]
	}
}
var timeErrorTravel = false
function calcTimeTravel() {
	$('#addRequestForm').bootstrapValidator('disableSubmitButtons', false);
	var startH = $("#startHourTravel").val()
	var startM = $("#startMinuteTravel").val()
	if (startM === '' && startH !== '') {
		$("#startMinuteTravel").val('');
		startM = '00'
	}
	var startTo = $("#startAmOrPmTravel").val()
	var startTime = startH && startM && startTo ? changeFormatTime(startH + ":" + startM + " " + startTo) : null
	var endH = $("#endHourTravel").val()
	var endM = $("#endMinuteTravel").val()
	if (endM === '' && endH !== '') {
		$("#endMinuteTravel").val('');
		endM = '00'
	}
	var endTo = $("#endAmOrPmTravel").val()
	var endTime = endH && endM && endTo ? changeFormatTime(endH + ":" + endM + " " + endTo) : null
	var start = startTime ? new Date("2000/01/01 " + startTime) : null;
	var end = endTime ? new Date("2000/01/01 " + endTime) : null;
	var time = end && start ? (end.getTime() - start.getTime()) : 0
	var hours = time / (3600 * 1000)
	if (hours > 0) {
		timeError = false
		hours = Number(hours).toFixed(3)
	} else {
		timeError = true
		hours = Number(0).toFixed(3)
	}
	calValueTimeTravel()
}
function calValueTimeTravel() {
	var start = $("#startHourTravel").val() + ":" + $("#startMinuteTravel").val() + " " + $("#startAmOrPmTravel").val()
	var end = $("#endHourTravel").val() + ":" + $("#endMinuteTravel").val() + " " + $("#endAmOrPmTravel").val()
	$("#startTimeValueTravel").val(start).change()
	$("#endTimeValueTravel").val(end).change()
}
function isHourKeyTravel(keyPressEvent) {
	var charCode = (keyPressEvent.which) ? keyPressEvent.which : keyPressEvent.keyCode;
	if (charCode > 31 && (charCode < 48 || charCode > 57)) { // the character entered must be a number	
		return false;
	} else if (charCode <= 31) {
		return true;
	}
	var value = $(keyPressEvent.target).val().trim();
	var valueLength = value.length;
	var selStart = keyPressEvent.target.selectionStart;
	var selEnd = keyPressEvent.target.selectionEnd;
	var selLength = keyPressEvent.target.selectionEnd - keyPressEvent.target.selectionStart;
	if (value.length > 0 && selLength < value.length) {
		if (selStart == 0) {
			if (charCode != 48) { // if the character entered is not a zero	
				if (charCode == 49) { // if the character entered is the number one	
					var secondCharCode;
					if (value.length == 1) {
						secondCharCode = value.charCodeAt(0);
					} else {
						secondCharCode = value.charCodeAt(1);
					}
					if (secondCharCode != 48 && secondCharCode != 49 && secondCharCode != 50) {
						return false;
					}
				} else {
					return false;
				}
			}
		} else if (selStart == 1) {
			var firstCharCode = value.charCodeAt(0);
			if (firstCharCode == 48) { // if the first character is a zero	
				if (charCode == 48) { // then do not accept a second zero	
					return false;
				}
			} else if (firstCharCode == 49) { // if the first character is a one	
				if (charCode != 48 && charCode != 49 && charCode != 50) { // then the second char can only be 0, 1, or 2	
					return false;
				}
			} else { // the first character is a number between 2 and 9 ... no character is valid as the second char	
				return false;
			}
		} else {
			return false;
		}
	}
	return true;
}
function isMinuteKeyTravel(keyPressEvent) {
	// in theory, a keyPress event should only get fired for printable characters although this is not always the case.	
	// firefox, for example, will generate a keyPress event for the tab key (charCode=9).	
	// return true for non-printable characters less than 31 which would include the tab key	
	var charCode = (keyPressEvent.which) ? keyPressEvent.which : keyPressEvent.keyCode;
	if (charCode > 31 && (charCode < 48 || charCode > 57)) { // the character entered must be a number	
		return false;
	} else if (charCode <= 31) {
		return true;
	}
	var value = $(keyPressEvent.target).val().trim();
	var valueLength = value.length;
	var selStart = keyPressEvent.target.selectionStart;
	var selEnd = keyPressEvent.target.selectionEnd;
	var selLength = keyPressEvent.target.selectionEnd - keyPressEvent.target.selectionStart;
	if (value.length > 0 && selLength < value.length) {
		if (selStart == 0) {
			if (charCode > 53) { // the first character may not be greater than five	
				return false;
			}
		} else if (selStart == 1) {
			var firstCharCode = value.charCodeAt(0);
			if (firstCharCode > 53) { // the first character is a number greater than 5 ... no character is valid as the second char	
				return false;
			}
		} else {
			return false;
		}
	}
	return true;
}
function addformValidator() {
	$('#addRequestForm').bootstrapValidator({
		live : 'disabled',
		trigger : null,
		excluded : [ ":disabled" ],
		feedbackIcons : {
			valid : 'fa fa-check ',
			validating : 'fa fa-refresh'
		},
		fields : {
			fromDate : {
				trigger : 'change',
				validators : {
					notEmpty : {
						message : startDateCannotBeEmptyValidator
					}
				}
			}
		}
	})
}
function datetimeValidator() {
	$('#addRequestForm').bootstrapValidator({
		live : 'disabled',
		trigger : null,
		excluded : [ ":disabled" ],
		feedbackIcons : {
			valid : 'fa fa-check ',
			validating : 'fa fa-refresh'
		},
		fields : {
			fromDate : {
				trigger : 'change',
				validators : {
					notEmpty : {
						message : startDateCannotBeEmptyValidator
					}
				}
			},
			addStartTime : {
				validators : {
					notEmpty : {
						message : fromTimeRequired
					}
				}
			},
			addEndTime : {
				validators : {
					notEmpty : {
						message : toTimeRequired
					}
				}
			}
		}
	})
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
function convertRightFormat(str) {
	var reg = /^[0-9]{1,2}[^\d]{1}[0-9]{1,2}[^\d]{1}[0-9]{4}$/;
	var regStr = /^[0-9]{1,2}[0-9]{1,2}[0-9]{4}$/;
	if (reg.test(str) || regStr.test(str)) {
		if (reg.test(str)) {
			return str
		}
		if (regStr.test(str)) {
			var pattern = /(\d{2})(\d{2})(\d{4})/;
			var formatedDate = str.replace(pattern, '$1-$2-$3');
			return formatedDate
		}
	} else {
		return false
	}
}
function changeMMDDFormat(date) {
	var dateArry = date.split("-")
	return dateArry[1] + "-" + dateArry[2] + "-" + dateArry[0]
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
function saveRequestForm() {
	submit('save');
}
function submitRequestForm() {
	submit('submit');
}
function removeDateValidators() {
	$('.startDateBeGreaterThen2000').hide()
	$(".fromDate01").removeClass('has-error')
	$('.endDateBeGreaterThen2000').hide()
	$(".toDate01").removeClass('has-error')
	$('.toDateIsEarlierThanFromDate').hide()
	$(".fromDate01").removeClass('has-error')
	$('.toDateIsEarlierThanFromDate').hide()
	$(".toDate01").removeClass('has-error')
}
function showAddRequestForm() {
	$('#addRequestForm')[0].reset()
	$("#startDateFocus").addClass("startDateFocus");
	if ($('#addRequestForm').data('bootstrapValidator') !== null) {
		$('#addRequestForm')
			.data('bootstrapValidator')
			.destroy()
	}
	$('#addRequestForm').data('bootstrapValidator', null)
	$("#overnightTripShowAll").hide();
	$("#overnightTripShow").hide();
	removeDateValidators()
	addformValidator();
}
function closeAddRequestForm() {
	$('#addRequestForm')
		.data('bootstrapValidator')
		.destroy()
	$('#addRequestForm').data('bootstrapValidator', null)
	$("#overnightTripShowAll").hide();
	$("#overnightTripShow").hide();
	$('.dateValidator').hide()
	$('.dateValidator01').hide()
	$(".dateTimePeriodOverlap").hide()
	removeDateValidators()
	$(".submitClose").addClass('highlight')
}
function saveRequestForm() {
	submit('save');
}
function submitRequestForm() {
	submit('submit');
}
function saveRequestTravel(isAddTravel) {
	addformValidator();
	if (isAddTravel) {
		$("#isAddTravel").val(isAddTravel)
	}
	$(event.currentTarget).parents(".modal").focus()
	var fromDate = $("#startDateInputTravel").val();
	var toDate = $("#endDateInputTravel").val();
	if (fromDate == '') {
		$('.startDateBeGreaterThen2000').hide()
	}
	var startTimeValueTravel = $("#addStartTime").val();
	var endTimeValueTravel = $("#addEndTime").val();
	var overnightTrip = $("#overnightTrip").is(":checked");
	var empNbr = $("#empNbrModal").val();
	var bootstrapValidator = $('#addRequestForm').data('bootstrapValidator')
	bootstrapValidator.validate();
	if (bootstrapValidator.isValid()) {
		{
			var x = 0;
			if (fromDate !== null && fromDate !== '') {
				if (compareDates(fromDate) == false) {
					$('.startDateBeGreaterThen2000').show()
					$(".fromDate01").addClass('has-error')
					var x = 1;
				}
			}
			if (toDate !== null && toDate !== '') {
				if (compareDates(toDate) == false) {
					$('.endDateBeGreaterThen2000').show()
					$(".toDate01").addClass('has-error')
					var x = 1;
				}
			}
			if ((fromDate !== null && fromDate !== '') && compareDates(fromDate) == true
				&& (toDate !== null && toDate !== '') && compareDates(toDate) == true) {
				if (formattedDate(fromDate) > formattedDate(toDate)) {
					$('.toDateIsEarlierThanFromDate').show()
					$(".toDate01").addClass('has-error')
					var x = 1;
				}
			}
			if (x === 1) {
				return false;
			}
			var payload = JSON.stringify({
				'fromDate' : fromDate,
				'toDate' : toDate,
				'startTimeValueTravel' : startTimeValueTravel,
				'endTimeValueTravel' : endTimeValueTravel,
				'overnightTrip' : overnightTrip,
				'empNbr' : empNbr
			})
			var gridForMileage = new Array();
			gridForMileage.push(payload);
			var gridForMileageObj = {
				gridForMileage : gridForMileage
			}
			$.ajax({
				type : "POST",
				url : urlMain + "/travelRequest/travelRequestAdd",
				dataType : 'JSON',
				async : false,
				processData : false,
				contentType : 'application/json;charset=UTF-8',
				data : JSON.stringify(gridForMileageObj),
				cache : false,
				complete : function(xhr) {
					if (xhr.status === 200) {
						window.location.href = urlMain + '/travelRequest/travelRequestMapper';
					} else {
						console.log(xhr.status);
					}
				}
			})
		}
	} else return;
}

function showTravelInformation(tripNbr) {
	if (tripNbr != null && jsonTravelStatus.length > 0) {
		for (var i = 0; i < jsonTravelStatus.length; i++) {
			if (jsonTravelStatus[i].tripNbr == tripNbr) {
				var ovrnight = jsonTravelStatus[i].ovrnight;
				var empNbr = $("#sessionEmpNbr").val();
			}
		}
		if (ovrnight === 'N') {
			var overnight = false;
		} else if (ovrnight === 'Y') {
			var overnight = true;
		}
		var payload = JSON.stringify({
			'tripNbr' : tripNbr,
			'empNbr' : empNbr,
			'overnightTrip' : overnight
		})
		var gridForMileage = new Array();
		gridForMileage.push(payload);
		var gridForMileageObj = {
			gridForMileage : gridForMileage
		}
		$.ajax({
			type : "POST",
			url : urlMain + "/travelRequest/travelRequestAdd",
			dataType : 'JSON',
			async : false,
			processData : false,
			contentType : 'application/json;charset=UTF-8',
			data : JSON.stringify(gridForMileageObj),
			cache : false,
			complete : function(xhr) {
				if (xhr.status === 200) {
					window.location.href = urlMain + '/travelRequest/travelRequestMapper';
				} else {
					console.log(xhr.status);
				}
			}
		})
	}
}

function getRightFormatStartTime(startTime) {
	var startH = $("#startHourTravel").val()
	var startM = $("#startMinuteTravel").val()
	if (startH == '' && startM == '') {
		$("#startTimeValueTravel").val('').change()
	}
	if (startH != '' && startH.length == 1) {
		startH = '0' + startH;
	}
	if (startM != '' && startM.length == 1) {
		startM = '0' + startM;
	}
	var start = startH + ":" + startM + " " + $("#startAmOrPmTravel").val()
	$("#startTimeValueTravel").val(start).change()
	var startTime = $("#startTimeValueTravel").val();
	return startTime;
}

function getRightFormatEndTime(endTime) {
	var endH = $("#endHourTravel").val()
	var endM = $("#endMinuteTravel").val()
	if (endH == '' && endM == '') {
		$("#endTimeValueTravel").val('').change()
	}
	if (endH != '' && endH.length == 1) {
		endH = '0' + endH;
	}
	if (endM != '' && endM.length == 1) {
		endM = '0' + endM;
	}
	var end = endH + ":" + endM + " " + $("#endAmOrPmTravel").val()
	$("#endTimeValueTravel").val(end).change()
	var endTime = $("#endTimeValueTravel").val();
	return endTime;
}

function compareDates(date) {
	var currentDate = moment(date, "MM-DD-YYYY");
	var stopDate = moment('01-01-2000', "MM-DD-YYYY");
	if (currentDate >= stopDate) {
		return true
	} else {
		return false;
	}
}

function changeDateFormatMMDDYYYY(date) {
	var year = date.substring(0, 4);
	var month = date.substr(4, 2);
	var day = date.substr(6, 2);
	return month + '-' + day + '-' + year;
}

function get24HrsFrmAMPM(timeStr) {
	if (timeStr && timeStr.indexOf(' ') !== -1 && timeStr.indexOf(':') !== -1) {
		var hrs = 0;
		var tempAry = timeStr.split(' ');
		var hrsMinAry = tempAry[0].split(':');
		hrs = parseInt(hrsMinAry[0], 10);
		if ((tempAry[1] == 'AM' || tempAry[1] == 'am') && hrs == 12) {
			hrs = 0;
		} else if ((tempAry[1] == 'PM' || tempAry[1] == 'pm') && hrs != 12) {
			hrs += 12;
		}
		return ('0' + hrs).slice(-2) + ':' + ('0' + parseInt(hrsMinAry[1], 10)).slice(-2);
	} else {
		return null;
	}
}

function formattedDate(fromDate) {
	if (fromDate === "" || fromDate === null || fromDate === undefined) {
		return false;
	} else {
		var formattedDate = moment(fromDate, "MM-DD-YYYY");
		return formattedDate;
	}
}

var getDates = function(fromDate, toDate) {
	var dateArray = [];
	var currentDate = moment(fromDate);
	var stopDate = moment(toDate);
	while (currentDate <= stopDate) {
		dateArray.push(moment(currentDate).format('MM-DD-YYYY'))
		currentDate = moment(currentDate).add(1, 'days');
	}
	return dateArray;
}

var deletedRows = 0;
function deleteRowQuery(e) {
	var result = confirm(areUDeleteRow + "\n\n" + pressContinue);	
		if(result == false){ 
			e.preventDefault();
		}
		return result;
}
function deleteRow(id) {
	if(deleteRowQuery(event)){
		for (var i = 0; i <= 8; i++) {
			$('#detailRow_' + i + id).remove();
		}
	}
	assignedAccountCodes.splice(id);
	calculateMilesTotal();
	calculateMiscTotal();
	calculateRequestTotal();
	sumSameAccountSummary();
	deletedRows++;
	indx--;
}
var countRows;
function createGrid() {
	var indxRows = indx + deletedRows;
	countRows = indxRows;
	var fromZip = "'fromZip'";
	var toZip = "'toZip'";
	var mileageRt = bfnOptionsTravel[2];
	if (mileageRt === null && mileageRt === undefined && mileageRt === '') {
		mileageRt = 0;
	}
	$('#addRowTR').remove();
	var gridData = '<tr id="detailRow_0' + indxRows + '"><td colspan="6"><div style="margin-left:10px;" id="mileageTotalMinusCommute_' + indxRows + '" >' + $('#mileageTotalMinusCommuteMsg').val() + '</div></td></tr><tr id="detailRow_1' + indxRows + '" class="deleteRow"> <td>' +
	'<button class="btn btn-secondary" id="deleteRowBtn_'+ indxRows +'" onclick="deleteRow(' + indxRows + ')">Delete</button> <span class="hidden">' +
	' <input id="delete_trvl_' + indxRows + '" name="trvl.pageItems[0].deleteRow" type="checkbox" value="true">' +
	' <input type="hidden" name="trvlPage.pageItems[0].deleteRow" value="on"> </span></td> <td>' +
	' <div class="button-group float-left"> <div class="fDateGroup date" onclick="setDate(' + indxRows + ')" id="SearchStartDate_' + indxRows + '"' +
	' data-date-format="mm/dd/yyyy">' +
	' <button id="datePickerCal" class="prefix" type="button" aria-label="show Date picker">' +
	' <i class="fa fa-calendar"></i> </button><input class="form-control dateInput" size="10" placeholder="mm-dd-yyyy" autocomplete="off" type="text"' +
	'  id="startDate_' + indxRows + '" onchange="hideDivError(' + indxRows + ')" value="">' +
	//<button id="clearBtnDate" class="clear-btn" type="button" role="button" onclick="clearDate(this)" aria-label="' + $('#lblRemoveContent').val() + '">' +
	//' <i class="fa fa-times"></i> </button>
	' </div><div id="startDateError_' + indxRows + '" class="form-group has-error dateValidator01"><small ' +
	'class="help-block" role="alert" aria-atomic="true">' + $('#travelDateEmptyError').val() + '</small></div></div></td>' +
	'<td></td><td><label class="label-color float-left">' + $('#lblContact1').val() + ':&nbsp;&nbsp;</label>' +
	'<input class="form-control" onClick="this.select();" onchange="hideDivError(' + indxRows + ')" style="width:70%" type="text"  id="contact_' + indxRows + '">' +
	'<div id="contactError_' + indxRows + '" class="form-group has-error dateValidator01"><small ' +
	'class="help-block" role="alert" aria-atomic="true">' + $('#travelContactEmptyError').val() + '</small></div>' +
	' </td><td>	<input type="text" class="form-control"' +
	'  style="width:95%" onClick="this.select();" onchange="hideDivError(' + indxRows + ')" placeholder="Origin Description" id="addressFromLine1_' + indxRows + '"><div id="addressFromLine1Error_' + indxRows + '"' +
	' class="form-group has-error dateValidator01">' +
	'<small class="help-block" role="alert" aria-atomic="true">' + $('#originDescriptionEmptyError').val() + '</small></div></td>' +
	' <td><input type="text" class="form-control"' +
	' style="width:95%" onClick="this.select();" onchange="hideDivError(' + indxRows + ')" placeholder="Destination Description" id="addressToLine1_' + indxRows + '"><div id="addressToLine1Error_' + indxRows + '"' +
	' class="form-group has-error dateValidator01"><small ' +
	'class="help-block" role="alert" aria-atomic="true">' + $('#destinationDescriptionEmptyError').val() + '</small></div></td> </tr>' +
	'<tr id="detailRow_2' + indxRows + '"> <td /><td><div><input type="time" onchange="hideDivError(' + indxRows + ')" class="form-control" style="width:100%"' +
	'id="startTime_' + indxRows + '"><div id="startTimeError_' + indxRows + '" class="form-group has-error dateValidator01"><small ' +
	'class="help-block" role="alert" aria-atomic="true">' + $('#startTimeEmptyError').val() + '</small></div><div id="startEndTimeGreaterError_' + indxRows + '" class="form-group has-error dateValidator01"><small ' +
	'class="help-block" role="alert" aria-atomic="true">' + $('#startEndTimeGreaterMsg').val() + '</small></div><div id="startEndTimeOverlapError_' + indxRows + '" class="form-group has-error dateValidator01"><small ' +
	'class="help-block" role="alert" aria-atomic="true">' + $('#timeOverlapError').val() + '</small></div></div></td><td><div style="margin-right:3px;"><input type="time"' +
	' class="form-control" style="width:100%" onchange="hideDivError(' + indxRows + ')" id="endTime_' + indxRows + '"><div id="endTimeError_' + indxRows + '" ' +
	'class="form-group has-error dateValidator01"><small class="help-block" role="alert" aria-atomic="true">' + $('#endTimeEmptyError').val() + '</small>' +
	'</div></div></td><td><label class="label-color float-left">' + $('#lblPurpose').val() + ':&nbsp;&nbsp;</label>' +
	'<textarea class="form-control" style="padding: 0.375rem 0.75rem; width: 70%; height: 100%;' +
	' rows="3" cols="19" onClick="this.select();" onchange="hideDivError(' + indxRows + ')" id="purpose_' + indxRows + '" /><div id="purposeError_' + indxRows + '" class="form-group has-error dateValidator01"><small ' +
	'class="help-block" role="alert" aria-atomic="true">' + $('#purposeEmptyError').val() + '</small></div></td><td><input ' +
	'class="form-control" type="text" onClick="this.select();" style="width:95%" placeholder="Origin Address"' +
	' id="addressFromLine2_' + indxRows + '" ></td> <td><input type="text"' +
	' class="form-control" onClick="this.select();" style="width:95%" placeholder="Destination Address"' +
	' id="addressToLine2_' + indxRows + '" ></td> </tr><tr id="detailRow_3' + indxRows + '"> <td /> <td><div><label class="label-color float-left">' + $('#lblMilage').val() + ':&nbsp;' +
	'&nbsp;</label><input class="form-control decimal-group-one number-seperator-one-decimal"' +
	' type="text" onClick="this.select();" oninput="validate(this)" onkeypress="return checkDigit(event,this,7,1);" placeholder= "Start" id="mileageBegining_' + indxRows + '" style="width:65%"' +
	' onblur="calculateMileage(' + indxRows + ')"><div id="mileageBeginingError_' + indxRows + '" class="form-group has-error dateValidator01"><small ' +
	'class="help-block" role="alert" aria-atomic="true">' + $('#mileageStartEmptyError').val() + '</small></div><div style="color:red"' +
	' id="mileageError_' + indxRows + '"></div><div id="mileageBeginingGreaterError_' + indxRows + '" class="form-group has-error dateValidator01"><small ' +
	'class="help-block" role="alert" aria-atomic="true">' + $('#mileageBeginingGreater').val() + '</small></div><div style="color:red"' +
	' id="mileageError_' + indxRows + '"></div></div></td><td><div style="width: 80%;" class="float-left"><input class="form-control decimal-group-one number-seperator-one-decimal"' +
	' type="text" onClick="this.select();" oninput="validate(this)" onkeypress="return checkDigit(event,this,7,1);" placeholder= "Stop" id="mileageEnd_' + indxRows + '" style="width:75%"' +
	' onblur="calculateMileage(' + indxRows + ')"><div id="mileageEndError_' + indxRows + '" class="form-group has-error dateValidator01"><small ' +
	'class="help-block" role="alert" aria-atomic="true">' + $('#mileageEndEmptyError').val() + '</small></div></div><div class="form-inline">' +
	' <label class="label-color">OR&nbsp;&nbsp;</label>' +
	' </div></td><td><div style="width: 40%;" class="float-left"><input class="form-control number-seperator-one-decimal" type="text" oninput="validate(this)" onkeypress="return checkDigit(event,this,3,1);"' +
	' placeholder= "Map" id="map_' + indxRows + '" onClick="this.select();" style="width:80%" onblur="calculateMileage(' + indxRows + ')"><div id="mapError_' + indxRows + '"' +
	' class="form-group has-error dateValidator01"><small ' +
	'class="help-block" role="alert" aria-atomic="true">' + $('#mapEmptyError').val() + '</small></div></div><div class="form-inline">' +
	' <label class="label-color">Tot:&nbsp;&nbsp;</label><label class="label-color" id="total_' + indxRows + '">0.0</label>' +
	' </div></td><td ><label class="label-color float-left">' + $('#lblCity').val() + ':</label>' +
	'<input class="form-control" type="text" onClick="this.select();" style="width:81%"  id="fromCity_' + indxRows + '">' +
	'</td> <td><label class="label-color float-left">' + $('#lblCity').val() + ':&nbsp;' +
	'&nbsp;&nbsp;&nbsp;</label><input class="form-control" style="width:81%" type="text"' +
	'onClick="this.select();" id="toCity_' + indxRows + '"></td></tr>' +
	'<tr id="detailRow_4' + indxRows + '"> <td /> <td><label class="label-color float-left">' + $('#lblRoundTrip').val() + ':</label>' +
	'<input class="float-left" type="checkbox" id="isRoundTrip_' + indxRows + '" onchange="calculateMileage(' + indxRows + ')"></td><td>' +
	'<label class="label-color float-left">&nbsp;&nbsp;&nbsp;&nbsp;' + $('#lblCommute').val() + ':&nbsp;&nbsp;</label>' +
	'<input class="float-left" type="checkbox" id="isCommute_' + indxRows + '" onchange="calculateMileage(' + indxRows + ')"></td><td></td><td>' +
	' <div class="col-lg-12"><div class="col-lg-5 float-left" style="width: 41%;"><label class="label-color float-left">'
	+ $('#lblState').val() + ':</label>' + formStates('fromState_' + indxRows + '') +
	' <div id="fromStateError_' + indxRows + '" class="form-group has-error dateValidator01"><small ' +
	'class="help-block" role="alert" aria-atomic="true">' + $('#requiredField').val() + '</small></div></div>' +
	'<div style="width: 59%;display: flex;"><div style="margin-left:10px; width: 140%;"><label class="label-color float-left"> ' + $('#lblZip').val() + ':</label><div style="width:77%"><input class="form-control "' +
	' type="text"  onClick="this.select();" id="fromZip_' + indxRows + '" style="width: 125%;" onkeyup="zipChange(event, this.value, \'fromZip\', ' + indxRows + ')" onchange="validateZipField(\'fromZip\', ' + indxRows + ')" inputmode="numeric" maxlength="10">' +
	'<div id="fromZipError_' + indxRows + '" class="form-group has-error dateValidator01">' +
	'<small class="help-block" role="alert" aria-atomic="true">' + $('#fromZipValidError').val() + '</small></div></div></div></div>' +
	' <td><div class="col-lg-12"><div class="col-lg-5 float-left" style="width: 41%;"><label class="label-color float-left">'
	+ $('#lblState').val() + ':</label>' + toStates('toState_' + indxRows + '') +
	' <div id="toStateError_' + indxRows + '" class="form-group has-error dateValidator01"><small ' +
	'class="help-block" role="alert" aria-atomic="true">' + $('#requiredField').val() + '</small></div></div>' +
	'<div style="width: 59%;display: flex;"><div style="margin-left:10px;"><label class="label-color float-left"> ' + $('#lblZip').val() + ':</label><div style="width:77%"><input class="form-control "' +
	' type="text" onClick="this.select();" id="toZip_' + indxRows + '" style="width: 125%;" onkeyup="zipChange(event, this.value, \'toZip\', ' + indxRows + ')" onchange="validateZipField(\'toZip\', ' + indxRows + ')" inputmode="numeric" maxlength="10">' +
	'<div id="toZipError_' + indxRows + '" class="form-group has-error dateValidator01">' +
	'<small class="help-block" role="alert" aria-atomic="true">' + $('#toZipValidError').val() + '</small></div></div></div></div></td></tr>' +
	'<tr id="detailRow_5' + indxRows + '"><td /><td /><td /><td /><td></td><td></td></tr><tr id="detailRow_6' + indxRows + '"> <td /> <td><div class="float-left"><label class="label-color' +
	' float-left">' + $('#lblMilageRate').val() + ':&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>' +
	'<label class="label-color float-right" id="mileageRate_' + indxRows + '">' + mileageRt + '</label></div><div class="float-right" /></td><td></td> <td></td><td>' +
	'<a href="javascript:void(0);" style="color:#0065ff; text-decoration: underline">Locations</a>' +
	'</td> <td> <div class="col-sm-2">&nbsp;</div> </td> </tr><tr id="detailRow_7' + indxRows + '"> <td /> <td><div class="float-left"><label class="label-color ' +
	'float-left">' + $('#lblTotMilageAmnt').val() + ':&nbsp;&nbsp;</label><label class="label-color float-right" id="totalMileageRate_' + indxRows + '">' +
	'0.00</label></div><div class="float-right" /></td><td><label class="label-color float-left">' + $('#lblMiscAmnt').val() + ':&nbsp;&nbsp;</label>' +
	'<input class="form-control decimal-group-two number-seperator-two-decimal" onClick="this.select();" onkeypress="return checkDigit(event,this,4,2);" type="text" max aria-label="Search" ' +
	'id="miscAmt_' + indxRows + '" oninput="validate(this)" placeholder="Misc" style="width:50%" onchange="calculateMileage(' + indxRows + ')"></td><td><input type="text" class="form-control input-disabled"' +
	'  style="width:85%" onClick="this.select();" onchange="hideDivError(' + indxRows + ')" placeholder="Misc Reason" id="otherReason_' + indxRows + '" disabled><div id="otherReasonError_' + indxRows + '" ' + 'class="form-group has-error dateValidator01"><small class="help-block" role="alert" aria-atomic="true">' + $('#otherReasonEmptyError').val() + '</small></div></td><td>' +
	'<a href="javascript:void(0);" style="color:#0065ff; margin-right:10px; text-decoration: underline" onclick="showAssignAccountCodeForm(' + indxRows + ')">' + $('#lblAccntCodes').val() + '</a>' +
	'<label class="label-color" id="accountCodeTot_' + indxRows + '"></label><div id="accountCodeTotError_' + indxRows + '" class="form-group has-error dateValidator01"><small class="help-block" role="alert" ' +
	'aria-atomic="true">' + $('#accountCodesEmptyError').val() + '</small></div></td>' +
	' <td><label class="label-color">' + $('#lblTotReiumbersment').val() + ':&nbsp;&nbsp;</label><label class="label-color" ' +
	'id="totReiumbersment_' + indxRows + '">0.00</label> </td> </tr>' +
	'<tr id="detailRow_8' + indxRows + '"><td colspan="6"><hr></td></tr>' +
	'<tr id="addRowTR"><td colspan="6"><button id="addButton" onclick="addRow()" class="btn btn-primary float-right">+</button></td></tr>';
$('#trvlTable').append(gridData);
$('#mileageTotalMinusCommute_' + indxRows).hide();
}

function hideDivErrorAll(indx) {
	$('#startDateError_' + indx).hide();
	$('#contactError_' + indx).hide();
	$('#addressFromLine1Error_' + indx).hide();
	$('#addressToLine1Error_' + indx).hide();
	$('#startTimeError_' + indx).hide();
	$('#endTimeError_' + indx).hide();
	$('#startEndTimeGreaterError_' + indx).hide();
	$('#startEndTimeOverlapError_' + indx).hide();
	$('#purposeError_' + indx).hide();
	$('#fromStateError_' + indx).hide();
	$('#fromZipError_' + indx).hide();
	$('#toStateError_' + indx).hide();
	$('#toZipError_' + indx).hide();
	$('#accountCodeTotError_' + indx).hide();
	$('#accountCodeEmptyError_' + indx).hide();
	$('#percentCodeEmptyError_' + indx).hide();
	$('#amountCodeExistsError_' + indx).hide();
}

function hideDivError(indx) {
	if ($('#startDate_' + indx).val() !== "") {
		$('#startDateError_' + indx).hide();
	}
	if ($('#contact_' + indx).val() !== "") {
		$('#contactError_' + indx).hide();
	}
	if ($('#addressFromLine1_' + indx).val() != "") {
		$('#addressFromLine1Error_' + indx).hide();
	}
	if ($('#addressToLine1_' + indx).val() != "") {
		$('#addressToLine1Error_' + indx).hide();
	}
	if ($('#startTime_' + indx).val() != "") {
		$('#startTimeError_' + indx).hide();
		$('#startEndTimeGreaterError_' + indx).hide();
		$('#startEndTimeOverlapError_' + indx).hide();
	}
	if ($('#endTime_' + indx).val() != "") {
		$('#endTimeError_' + indx).hide();
	}
	if ($('#purpose_' + indx).val() != "") {
		$('#purposeError_' + indx).hide();
	}
	if ($('#accountCodeTot_' + indx).val() != "") {
		$('#accountCodeTotError_' + indx).hide();
	}
	if($('#overrideReason_' + indx).val() != ""){
		$('#overrideReasonError_' + indx).hide();
	}
	if($('#otherReason_' + indx).val() != ""){
		$('#otherReasonError_' + indx).hide();
	}
}

function hideDivErrorInModal() {
	if ($('#startTime').val() != "") {
		$('#startTimeError').hide();
		$('#startEndTimeGreaterError').hide();
		$('#startEndTimeOverlapError').hide();
	}
	if ($('#endTime').val() != "") {
		$('#endTimeError').hide();
	}
}

function isNumberKey(evt) {
	var charCode = (evt.which) ? evt.which : event.keyCode
	if (charCode > 31 && (charCode < 48 || charCode > 57) && charCode !== 46) {
		return false;
	} else {
		  return true;
	}
}

var validate = function(e) {
	if (!isNaN(e.value)) {
		var t = e.value;
		e.value = (t.indexOf(".") >= 0) ? (t.substr(0, t.indexOf(".")) + t.substr(t.indexOf("."), 3)) : t;
	} else {
		return false;
	}
}
function getStates() {
	serverCall('/' + ctx + '/travelRequest/getStates', 'GET', null, 'JSON', 'application/json;charset=UTF-8', statesResponse);
}
function statesResponse() {
	if (responseData != 'error') {
		for (var i = 0; i < responseData.result.length; i++) {
			statesList[i] = responseData.result[i].displayLabel;
		}
	} else {
		console.log('error getting states list.');
	}
}

function formStates(id){
	var selectElement = '<select class="form-control" style="width:100%" id="' + id + '">';
	var optionElements = '';
	for (var i = 0; i < statesList.length; i++) {
		var optionElement = '<option value="' + responseData.result[i].code + '" temp="' + responseData.result[i].code + '-' + responseData.result[i].description + '" >' + responseData.result[i].code + '-' + responseData.result[i].description + '</option>'
		optionElements = optionElements + optionElement;
	}
	selectElement = selectElement + optionElements + '</select>';
	var script = "<script>$('#" + id + " option:selected').html($('#" + id + " option:selected').attr('value')); $((this)).on('change mouseleave', function () {$('#" + id + " option').each(function () {$(this).html($(this).attr('temp'));});$('#" + id + " option:selected').html($('#" + id + " option:selected').attr('value'));$(this).blur();});$(this).on('focus', function () {$('#" + id + " option').each(function () {$(this).html($(this).attr('temp'));});});</script>";
	selectElement = selectElement + script;
	return selectElement;
}

function toStates(id){
	var selectElement = '<select class="form-control" style="width:100%" id="' + id + '">';
	var optionElements = '';
	for (var i = 0; i < statesList.length; i++) {
		var optionElement = '<option value="' + responseData.result[i].code + '" temp="' + responseData.result[i].code + '-' + responseData.result[i].description + '" >' + responseData.result[i].code + '-' + responseData.result[i].description + '</option>'
		optionElements = optionElements + optionElement;
	}
	selectElement = selectElement + optionElements + '</select>';
	var script = "<script>$('#" + id + " option:selected').html($('#" + id + " option:selected').attr('value')); $((this)).on('change mouseleave', function () {$('#" + id + " option').each(function () {$(this).html($(this).attr('temp'));});$('#" + id + " option:selected').html($('#" + id + " option:selected').attr('value'));$(this).blur();});$(this).on('focus', function () {$('#" + id + " option').each(function () {$(this).html($(this).attr('temp'));});});</script>";
	selectElement = selectElement + script;
	return selectElement;
}

function appendZerosMil(id) {
	var mileageBeginingVal = $('#mileageBegining_' + id).val();
	var mileageEndVal = $('#mileageEnd_' + id).val();
	var mapVal = $('#map_' + id).val();
	var miscAmtVal = $('#miscAmt_' + id).val();

	if (mileageBeginingVal !== null && mileageBeginingVal !== '') {
		$('#mileageBegining_' + id).val(numberWithCommas(Number(numberWithoutCommas(mileageBeginingVal)).toFixed(1)));
	}
	if (mileageEndVal !== null && mileageEndVal !== '') {
		$('#mileageEnd_' + id).val(numberWithCommas(Number(numberWithoutCommas(mileageEndVal)).toFixed(1)));
	}
	if (mapVal !== null && mapVal !== '') {
		$('#map_' + id).val(numberWithCommas(Number(numberWithoutCommas(mapVal)).toFixed(1)));
	}
	if (miscAmtVal !== null && miscAmtVal !== '') {
		$('#miscAmt_' + id).val(numberWithCommas(Number(numberWithoutCommas(miscAmtVal)).toFixed(2)));
	}
}

function calculateMileage(id) {
	appendZerosMil(id);
	if (bfnOptionsTravel[1] == 'Y') {
		disableMapField(id);
	}
	var mileageBegining = numberWithoutCommas($('#mileageBegining_' + id).val());
	var mileageEnd = numberWithoutCommas($('#mileageEnd_' + id).val())
	var map = numberWithoutCommas($('#map_' + id).val());
	var miscAmt = numberWithoutCommas($('#miscAmt_' + id).val());
	var totalAccCodeAmt = Number(numberWithoutCommas($('#accountCodeTot_' + id).text()));
	var totReiumbersment = Number(numberWithoutCommas($('#totReiumbersment_' + id).text()));
	if (totalAccCodeAmt == null || totalAccCodeAmt == '' || totalAccCodeAmt == undefined) {
		totalAccCodeAmt = 0;
	}
	if (totReiumbersment == null || totReiumbersment == '' || totReiumbersment == undefined) {
		totReiumbersment = 0;
	}
	var total = 0;
	var totalMileageRate = 0;
	var totReiumbersmentValue = 0;
	var totalValue = 0;
	var totalMileageRateValue = 0;
	if (mileageBegining == "") {
		$('#mileageBeginingGreaterError_' + id).hide();
	}
	if (mileageBegining !== "") {
		$('#mileageBeginingError_' + id).hide();
	}
	if (mileageEnd !== "") {
		$('#mileageEndError_' + id).hide();
	}
	if (map !== "") {
		$('#mileageBeginingError_' + id).hide();
		$('#mileageEndError_' + id).hide();
		$('#mapError_' + id).hide();
	}
	if ((mileageBegining !== null || mileageEnd !== null)
		&& (mileageBegining !== "" || mileageEnd !== "")
		&& (mileageBegining !== undefined || mileageEnd !== undefined)) {
		disableMapField(id);
	}
	if ((mileageBegining !== null && mileageEnd !== null)
		&& (mileageBegining !== "" && mileageEnd !== "")
		&& (mileageBegining !== undefined && mileageEnd !== undefined)) {
		disableMapField(id);
		$('#mapError_' + id).hide();
		if (Number(mileageBegining) > Number(mileageEnd)) {
			$('#mileageBeginingError_' + id).hide();
			$('#mileageBeginingGreaterError_' + id).show();
		} else {
			var total = $('#total_' + id).text();
			var mileageRate = bfnOptionsTravel[2];
			if (mileageRate === null && mileageRate === undefined && mileageRate === '') {
				mileageRate = 0;
			}
			var totalMileageRate = $('#totalMileageRate_' + id);
			if (miscAmt == undefined) {
				miscAmt = 0;
			}
			totReiumbersment = $('#totReiumbersment_' + id);
			var totalAmt = Number(mileageEnd) - Number(mileageBegining);
			var totalValue = totalAmt;
			totalMileageRateValue = totalValue * Number(mileageRate);
			totReiumbersmentValue = Number(totalMileageRateValue) + Number(miscAmt);
			$('#mileageBeginingError_' + id).hide();
			$('#mileageEndError_' + id).hide();
			$('#mileageBeginingGreaterError_' + id).hide();
			if (map == '' && mileageBegining != '' && mileageEnd != '') {
				if ($('#isRoundTrip_' + id).prop('checked')) {
					totalValue = totalAmt * 2;
					totalAmt = totalValue;
					totalMileageRateValue = totalValue * Number(mileageRate);
					totReiumbersmentValue = Number(totalMileageRateValue) + Number(miscAmt);
					if ($('#isCommute_' + id).prop('checked')) {
						if (commuteDist[0] !== '0.0' && commuteDist[0] !== '') {
							mtValue = Number(totalValue) - (Number(commuteDist[0]) * 2);
							if (mtValue > 0) {
								totalValue = mtValue;
							} else {
								totalValue = 0;
								$('#mileageTotalMinusCommute_' + id).show();
							}
						} else {
							totalValue = Number(totalValue);
						}
						var totalMileageRateValue = totalValue * Number(mileageRate);
						var totReiumbersmentValue = Number(totalMileageRateValue) + Number(miscAmt);
					} else {
						$('#mileageTotalMinusCommute_' + id).hide();
					}
				}
				if (!$('#isRoundTrip_' + id).prop('checked')) {
					if ($('#isCommute_' + id).prop('checked')) {
						if (commuteDist[0] !== '0.0' && commuteDist[0] !== '') {
							mtValue = Number(totalAmt) - Number(commuteDist[0]);
							if (mtValue > 0) {
								totalValue = mtValue;
							} else {
								totalValue = 0;
								$('#mileageTotalMinusCommute_' + id).show();
							}
						} else {
							totalValue = Number(totalAmt);
						}
						totalMileageRateValue = totalValue * Number(mileageRate);
						totReiumbersmentValue = Number(totalMileageRateValue) + Number(miscAmt);
					} else {
						$('#mileageTotalMinusCommute_' + id).hide();
					}
				}
			}
		}
	}
	if ((mileageBegining === null && mileageEnd === null)
		|| (mileageBegining === "" && mileageEnd === "")
		|| (mileageBegining === undefined && mileageEnd === undefined)) {
		disableMileageFields(id);
		$('#mileageBeginingGreaterError_' + id).hide();
	}
	if (map != '' && mileageBegining == '' && mileageEnd == '') {
		var total = $('#total_' + id);
		var mileageRate = bfnOptionsTravel[2];
		if (mileageRate === null && mileageRate === undefined && mileageRate === '') {
			mileageRate = 0;
		}
		var totalMileageRate = $('#totalMileageRate_' + id);
		if (miscAmt == undefined) {
			miscAmt = 0;
		}
		$('#miscAmt_' + id).val(Number(miscAmt).toFixed(2));
		var totReiumbersment = $('#totReiumbersment_' + id);
		totalValue = Number(map) - 0;

		totalMileageRateValue = totalValue * Number(mileageRate);
		totReiumbersmentValue = Number(totalMileageRateValue) + Number(miscAmt);
		if ($('#isRoundTrip_' + id).prop('checked')) {
			totalValue = Number(map) * 2;
			var totalMileageRateValue = totalValue * Number(mileageRate);
			var totReiumbersmentValue = Number(totalMileageRateValue) + Number(miscAmt);
			if ($('#isCommute_' + id).prop('checked')) {
				if (commuteDist[0] !== '0.0' && commuteDist[0] !== '') {
					mtValue = Number(totalValue) - (Number(commuteDist[0]) * 2);
					if (mtValue > 0) {
						totalValue = mtValue;
					} else {
						totalValue = 0;
						$('#mileageTotalMinusCommute_' + id).show();
					}
				} else {
					totalValue = Number(totalValue);
				}
				var totalMileageRateValue = totalValue * Number(mileageRate);
				var totReiumbersmentValue = Number(totalMileageRateValue) + Number(miscAmt);
			} else {
				$('#mileageTotalMinusCommute_' + id).hide();
			}
		}
		if (!$('#isRoundTrip_' + id).prop('checked')) {
			if ($('#isCommute_' + id).prop('checked')) {
				if (commuteDist[0] !== '0.0' && commuteDist[0] !== '') {
					mtValue = Number(totalValue) - Number(commuteDist[0]);
					if (mtValue > 0) {
						totalValue = mtValue;
					} else {
						totalValue = 0;
						$('#mileageTotalMinusCommute_' + id).show();
					}
				} else {
					totalValue = Number(totalValue);
				}
				var totalMileageRateValue = totalValue * Number(mileageRate);
				var totReiumbersmentValue = Number(totalMileageRateValue) + Number(miscAmt);
			} else {
				$('#mileageTotalMinusCommute_' + id).hide();
			}
		}
	}
	if ((mileageBegining === null || mileageBegining === "" || mileageBegining === undefined)
		&& (mileageEnd === null || mileageEnd === "" || mileageEnd === undefined)
		&& (map === null || map === "" || map === undefined)) {
		enableMileageFields(id);
		enableMapField(id);
	}
	if (miscAmt !== '' ){
		$('#otherReasonError_' + id).hide();
		$('#otherReason_' + id).prop("disabled", false);
		$('#otherReason_' + id).removeClass('input-disabled');
	}
	else{
		$('#otherReasonError_' + id).hide();
		$('#otherReason_' + id).prop("disabled", true);
		$('#otherReason_' + id).addClass('input-disabled');
	}
	var totReiumbersmentValue = Number(totalMileageRateValue) + Number(miscAmt);
	$('#total_' + id).text(numberWithCommas(Number(totalValue).toFixed(1)));
	$('#totalMileageRate_' + id).text(numberWithCommas(totalMileageRateValue.toFixed(2)));
	$('#totReiumbersment_' + id).text(numberWithCommas(totReiumbersmentValue.toFixed(2)));
	calculateMilesTotal();
	calculateMiscTotal();
	calculateRequestTotal();
	addEditAssignAccountCodeForm(id, 'Y');
	calculateAmountClick();
	clickAccountCodes();
}

function numberWithCommas(x) {
	if(x !== undefined){
	  return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
	}
}

function numberWithoutCommas(x) {
	if(x !== undefined){
		 return x.toString().replace(/,/g, '');
	}
}

function timeTo12HrFormat(time) {
	var time_part_array = time.split(":");
	var ampm = 'AM';
	if (time_part_array[0] >= 12) {
		ampm = 'PM';
	}
	if (time_part_array[0] > 12) {
		time_part_array[0] = (time_part_array[0] - 12).toString();
	}
	if(time_part_array[0].length == 1){
		time_part_array[0] = '0' + time_part_array[0];
	}
	if(time_part_array[1].length == 1){
		time_part_array[1] = '0' + time_part_array[1];
	}
	formatted_time = time_part_array[0] + ':' + time_part_array[1] + ':' + ampm;
	return formatted_time;
}

function timeTo12HrFormatAMPM(time) {
	var time_part_array = time.split(":");
	var ampm = 'AM';
	if (time_part_array[0] >= 12) {
		ampm = 'PM';
	}
	if (time_part_array[0] > 12) {
		time_part_array[0] = (time_part_array[0] - 12).toString();
	}
	if(time_part_array[0].length == 1){
		time_part_array[0] = '0' + time_part_array[0];
	}
	if(time_part_array[1].length == 1){
		time_part_array[1] = '0' + time_part_array[1];
	}
	formatted_time = time_part_array[0] + ':' + time_part_array[1] + ' ' + ampm;
	return formatted_time;
}

function getTimeAsNumberOfMinutes(time) {
	var timeParts = time.split(":");
	var timeInMinutes = (timeParts[0] * 60) + timeParts[1];
	return timeInMinutes;
}
var tripNbr;
function submit(action) {
	$("#mileageSuccess").text('');
	if (validateFields(action)) {
		if((action == 'submit') && (bfnOptionsTravel[7] == 'N')){
			$("#mileageSuccess").hide();
			$("#mileageError").show();
			$("#mileageError").text('You do not have a designated first approver. Contact your Business Office for assistance.');
			$(".updateMessageFailed").removeClass("hide")
			$(".loadingOn").hide()
			return false;
		}
		var travelRequests = new Array();
		if (tripNbr !== null && tripNbr !== undefined && tripNbr !== '') {
			tripNumVal = tripNbr;
			var payload = {
				tripNbr : tripNumVal
			}
			travelRequests.push(payload);
		} else {
			tripNumVal = '';
			var payload = {
				tripNbr : tripNumVal
			}
			travelRequests.push(payload);
		}
		var i;
		for (i = 0; i <= indx + deletedRows; i++) {
			var travelDate = $('#startDate_' + i).val();
			if (travelDate !== null && travelDate !== '' && travelDate !== undefined) {
				if (compareDates(travelDate) == false) {
				$('#startDateError_' + i).append("<small class='help-block' role='alert' aria-atomic='true'>The Start Date year must be >= 2000.</small>");
				$('#startDateError_' + i).show();
				return false;
				}
			}
			if (travelDate !== undefined && travelDate !== '') {
				var assignedAccountCode = new Array();
				if (assignedAccountCodes.length > 0) {
					for (k = 0; k < assignedAccountCodes.length; k++) {
						if(assignedAccountCodes[k].hasOwnProperty('id')){
							var acCodeIndex = assignedAccountCodes[k].id;
							if (acCodeIndex == i) {
								var childAccountCodes = assignedAccountCodes[k].childAccountCodes;
								var childAccountCode = {
									id : i.toString(),
									childAccountCodes : childAccountCodes
								}
								assignedAccountCode.push(childAccountCode);
							}
						}
					}
				}
				if(checkAccCodeReimbAmt(i, action) == false){
					return false;
				}
				var fromzip = validateZipField("fromZip", i)
				var tozip = validateZipField("toZip", i)
				if (!fromzip || !tozip) {
					return false;
				}
				if (travelDate !== undefined && travelDate.includes("-")) {
					var travelDateArr = travelDate.split('-');
					travelDate = travelDateArr[2] + travelDateArr[0] + travelDateArr[1];
				}
				var sTime = $('#startTime_' + i).val();
				var eTime = $('#endTime_' + i).val();
				if ((sTime !== undefined && sTime.includes(":") && sTime !== '') && (eTime !== undefined && eTime.includes(":") && eTime !== '')) {
					var noOfStartMinutes = getTimeAsNumberOfMinutes(sTime);
					var noOfEndMinutes = getTimeAsNumberOfMinutes(eTime);
					if (Number(noOfStartMinutes) >= Number(noOfEndMinutes) && (overNightData !== true)) {
						$('#startEndTimeGreaterError_' + i).show();
						return false;
					} else {
						$('#startEndTimeGreaterError_' + i).hide();
						var startTime = timeTo12HrFormat(sTime);
						var fromTmHr = startTime.split(':')[0];
						var fromTmMin = startTime.split(':')[1];
						var startMeridian = startTime.split(':')[2];
						if (startMeridian === 'AM') {
							var fromTmAp = 'A';
						} else {
							var fromTmAp = 'P';
						}
						var endTime = timeTo12HrFormat(eTime);
						var toTmHr = endTime.split(':')[0];
						var toTmMin = endTime.split(':')[1];
						var endMeridian = endTime.split(':')[2];
						if (endMeridian === 'AM') {
							var toTmAp = 'A';
						} else {
							var toTmAp = 'P';
						}
					}
				}		
				if (overNightData == true) {
					var overNightTrip = 'Y';
					var contact = $('#contact_0').val();
					var purpose = $('#purpose_0').val();
					var addressFromLine1 = $('#addressFromLine1_0').val();
					var addressFromLine2 = $('#addressFromLine2_0').val();
					var fromCity = $('#fromCity_0').val();
					var fromState = $('#fromState_0').val();
					var fromZip = $('#fromZip_0').val();
					var addressToLine1 = $('#addressToLine1_0').val();
					var addressToLine2 = $('#addressToLine2_0').val();
					var toState = $('#toState_0').val();
					var toCity = $('#toCity_0').val();
					var toZip = $('#toZip_0').val();
					var breakfastAmt = numberWithoutCommas($('#breakfast_' + i).val());
					var lunchAmt = numberWithoutCommas($('#lunch_' + i).val());
					var dinnerAmt = numberWithoutCommas($('#dinner_' + i).val());
					var altMealAmt = $('#mealOverride_' + i).val();
					if ($('#mealOverride_' + i).prop('checked')) {
						altMealAmt = 'Y'
					} else {
						altMealAmt = 'N'
					}
					var altMealRsn = $('#overrideReason_' + i).val();
					
					var parkAmt = numberWithoutCommas($('#parking_' + i).val()); 
					var taxiAmt = numberWithoutCommas($('#taxi_' + i).val()); 
					var miscAmt = numberWithoutCommas($('#other_' + i).val());
					var miscAmtRsn = $('#otherReason_' + i).val(); 
					var accomAmt = numberWithoutCommas($('#reinAmt_' + i).val()); 
					var accomDesc = $('#accommodations_' + i).val(); 
					var directBillNbr = $('#directBill_' + i).val();
					if (bfnOptionsTravel[1] == 'Y') {
						disableMapField(0);
					}
					var accomodValid = validateAccomodationField("accommodations", i);
					var directBill = validateAccomodationField("directBill", i);
					if(!accomodValid && !directBill){
						$("#directBillError_" + i).show();
						return false;
					}
					if($('#mealOverride_' + i).is(':checked')){
						if(altMealRsn == ''){
							$("#overrideReasonError_" + i).show();
							return false;
						}
					}
					if (!$('#mealOverride_' + i).is(':checked')) {
						if (checkBfnOptionsMeals(breakfastAmt, lunchAmt, dinnerAmt, altMealRsn, i) == false) {
							return false;
						}
					}
					} else {
					var overNightTrip = 'N';
					var contact = $('#contact_' + i).val();
					var purpose = $('#purpose_' + i).val();
					var addressFromLine1 = $('#addressFromLine1_' + i).val();
					var addressFromLine2 = $('#addressFromLine2_' + i).val();
					var fromCity = $('#fromCity_' + i).val();
					var fromState = $('#fromState_' + i).val();
					var fromZip = $('#fromZip_' + i).val();
					var addressToLine1 = $('#addressToLine1_' + i).val();
					var addressToLine2 = $('#addressToLine2_' + i).val();
					var toState = $('#toState_' + i).val();
					var toCity = $('#toCity_' + i).val();
					var toZip = $('#toZip_' + i).val();
					var miscAmt = numberWithoutCommas($('#miscAmt_' + i).val());
					var miscAmtRsn = $('#otherReason_' + i).val(); 
					var breakfastAmt = 0;
					var lunchAmt = 0;
					var dinnerAmt = 0;
					var altMealAmt = 'N'
					var altMealRsn = '';
					var parkAmt = 0;
					var taxiAmt = 0;
					var accomAmt = 0;
					var accomDesc = '';
					var directBillNbr = '';
				}
				if (fromState == null && fromState == undefined && fromState == '') {
					fromState == '';
				}
				if (fromZip !== null && fromZip !== undefined && fromZip !== '') {
					var fromZipCode;
					var fromZipCode4;
					if (fromZip.includes('-')) {
						var fZipCode = fromZip.split('-');
						fromZipCode = fZipCode[0];
						fromZipCode4 = fZipCode[1];
					} else {
						fromZipCode = fromZip;
						fromZipCode4 = '';
					}
				} else {
					fromZipCode = '';
					fromZipCode4 = '';
				}
				if (toState == null && toState == undefined && toState == '') {
					toState == '';
				}
				if (toZip !== null && toZip !== undefined && toZip !== '') {
					var toZipCode;
					var toZipCode4;
					if (toZip.includes('-')) {
						var tZipCode = toZip.split('-');
						toZipCode = tZipCode[0];
						toZipCode4 = tZipCode[1];
					} else {
						toZipCode = toZip;
						toZipCode4 = '';
					}
				} else {
					toZipCode = '';
					toZipCode4 = '';
				}
				if ($('#isRoundTrip_' + i).prop('checked')) {
					var isRoundTrip = 'Y'
				} else {
					var isRoundTrip = 'N'
				}
				if ($('#isCommute_' + i).prop('checked')) {
					var isCommute = 'Y'
				} else {
					var isCommute = 'N'
				}
				var mileageBegining = numberWithoutCommas($('#mileageBegining_' + i).val());
				var mileageEnd = numberWithoutCommas($('#mileageEnd_' + i).val());
				if ((mileageBegining !== null && mileageEnd !== null)
					&& (mileageBegining !== "" && mileageEnd !== "")
					&& (mileageBegining !== undefined && mileageEnd !== undefined)) {
					if (Number(mileageBegining) > Number(mileageEnd)) {
						return false;
					}
				}
				var map = numberWithoutCommas($('#map_' + i).val());
				if(map !== '' && map !== undefined && map !==''){
					if(!(Number(map) >= 0 && Number(map) <= 999.9)){
						$('#mapError_' + i).append("<small class='help-block' role='alert' aria-atomic='true'>Map : Number must be between 0 and 999.9</small>");
						$('#mapError_' + i).show();
						return false;
					}
				}
				var trvlReqStat = 'P';
				if ('save' === action) {
					trvlReqStat = 'S';
				}
				if(tripNumVal == ''){
					var entryDate = moment().format('YYYYMMDD');
				} else {
					var dateEntered = $('#dateEntered').text();
					var entryDate = moment(dateEntered, 'MM-DD-YYYY').format('YYYYMMDD')
				}
				var payload = {
					tripNbr : tripNumVal,
					tripSeqNbr : i,
					trvlReqStat : trvlReqStat,
					entryDt : entryDate,
					travelDt : travelDate,
					fromTmHr : fromTmHr,
					fromTmMin : fromTmMin,
					fromTmAp : fromTmAp,
					toTmHr : toTmHr,
					toTmMin : toTmMin,
					toTmAp : toTmAp,
					overnight : overNightTrip,
					roundTrip : isRoundTrip,
					useEmpTrvlCommuteDist : isCommute,
					contact : contact,
					purpose : purpose,
					begOdometer : mileageBegining,
					endOdometer : mileageEnd,
					mapMiles : map,
					breakfastAmt : breakfastAmt,
					lunchAmt : lunchAmt,
					dinnerAmt : dinnerAmt,
					altMealAmt : altMealAmt,
					altMealRsn : altMealRsn,
					parkAmt : parkAmt,
					taxiAmt : taxiAmt,
					miscAmtRsn : miscAmtRsn,
					accomAmt : accomAmt,
					accomDesc : accomDesc,
					directBillNbr : directBillNbr,
					miscAmt : miscAmt,
					origLocId : addressFromLine1,
					destLocId : addressToLine1,
					origLocName : addressFromLine1,
					origLocAddr : addressFromLine2,
					origLocCity : fromCity,
					origLocSt : fromState,
					origLocZip : fromZipCode,
					origLocZip4 : fromZipCode4,
					destLocName : addressToLine1,
					destLocAddr : addressToLine2,
					destLocCity : toCity,
					destLocSt : toState,
					destLocZip : toZipCode,
					destLocZip4 : toZipCode4,
					accountCodes : assignedAccountCode
				}
				travelRequests.push(payload);
				console.log(travelRequests)
			}
		}
		var sortedtravelRequests = travelRequests.slice().sort(sortByDate);
		if (!validateTimeOnSameDate(sortedtravelRequests) && (overNightData !== true)) {
			return false;
		}
		var travelRequestsObj = {
			travelRequests : sortedtravelRequests
		}
		if(action === 'save') {
			$.ajax({
			type : 'POST',
			url : '/' + ctx + '/travelRequest/saveTravelRequests',
			dataType : 'JSON',
			async : false,
			contentType : 'application/json;charset=UTF-8',
			data : JSON.stringify(travelRequestsObj),
			complete : function(xhr) {
				if (xhr.status === 200) {
					if (xhr.responseJSON !== null && xhr.responseJSON !== '' && xhr.responseJSON !== undefined) {
						var obj = xhr.responseJSON;
						tripNbr = obj.result[0];
						if (tripNbr !== null && tripNbr !== '' && tripNbr !== undefined) {
							var status = obj.result[1];
							var entryDate = obj.result[2];
							if (status === 'S') {
								statusText = 'Saved'
							}
							if (status === 'P') {
								statusText = 'Pending'
							}
							var tripNbrModal = document.getElementById("tripNbrModal");
							tripNbrModal.value = tripNbr;
							$('#documentUpload').css({'display': 'block', 'width':'125px'});
							$('#documentUpload').removeAttr('disabled');
							$('#documentUpload').click(function() {
								showDocumentModal('001', 'TRAVEL DOCUMENTS', 'TRAVEL', 'N');
							});
							$('#travelNbr').text(tripNbr);
							$('#travelStatus').text(statusText);
							if(entryDate !== null && entryDate !== '' && entryDate !== undefined){
							    var dateEntered = moment(entryDate).format('MM-DD-YYYY');
								$('#dateEntered').text(dateEntered);
							}
							$("#mileageSuccess").text('Save Successful.');
							$("#mileageError").css('visibility', 'hidden');
							$("#mileageSuccess").css('visibility', 'visible');
							$("#mileageSuccess").show();
							$(".updateMessageFailed").removeClass("hide")
							$(".loadingOn").hide()
						}
					} else {
						$("#mileageSuccess").hide();
						$("#mileageSuccess").css('visibility', 'hidden');
						$("#mileageError").css('visibility', 'visible');
						$("#mileageError").text('Error occurred while saving.');
					}
				} else {
					$("#mileageSuccess").hide();
					$("#mileageSuccess").css('visibility', 'hidden');
					$("#mileageError").css('visibility', 'visible');
					$("#mileageError").text('Error occurred while saving.');
					console.log(xhr.status);
				}
			}
			});
		}
		if(action === 'submit') {
			$.ajax({
			type : 'POST',
			url : '/' + ctx + '/travelRequest/submitTravelRequests',
			dataType : 'JSON',
			async : false,
			contentType : 'application/json;charset=UTF-8',
			data : JSON.stringify(travelRequestsObj),
			complete : function(xhr) {
				if (xhr.status === 200) {
					if (xhr.responseJSON !== null && xhr.responseJSON !== '' && xhr.responseJSON !== undefined) {
						var obj = xhr.responseJSON;
						tripNbr = obj.result[0];
						if (tripNbr !== null && tripNbr !== '' && tripNbr !== undefined) {
							var status = obj.result[1];
							var entryDate = obj.result[2];
							if (status === 'S') {
								statusText = 'Saved'
							}
							if (status === 'P') {
								statusText = 'Pending'
							}
							var tripNbrModal = document.getElementById("tripNbrModal");
							tripNbrModal.value = tripNbr;
							var documentButton = document.getElementById("documentUpload");
							documentButton.style.display = "block";
							$('#documentUpload').removeAttr('disabled');
							$('#documentUpload').click(function() {
								showDocumentModal('001', 'TRAVEL DOCUMENTS', 'TRAVEL', 'N');
							});
							$('#travelNbr').text(tripNbr);
							$('#travelStatus').text(statusText);
							if(entryDate !== null && entryDate !== '' && entryDate !== undefined){
								var dateEntered = moment(entryDate).format('MM-DD-YYYY');
								$('#dateEntered').text(dateEntered);
							}
							$("#mileageSuccess").text('Submission for approval completed.');
							$("#mileageError").css('visibility', 'hidden');
							$("#mileageSuccess").css('visibility', 'visible');
							$("#mileageSuccess").show();
							$(".updateMessageFailed").removeClass("hide")
							$(".loadingOn").hide()
						}
					} else {
						$("#mileageSuccess").hide();
						$("#mileageSuccess").css('visibility', 'hidden');
						$("#mileageError").css('visibility', 'visible');
						$("#mileageError").text('Error occurred while submitting.');
					}
				} else {
					$("#mileageSuccess").hide();
					$("#mileageSuccess").css('visibility', 'hidden');
					$("#mileageError").css('visibility', 'visible');
					$("#mileageError").text('Error occurred while submitting.');
					console.log(xhr.status);
				}
			}
			});
	}
	}
}

function checkAccCodeReimbAmt(i, action) {
	var isValid = true;
	if (action === 'submit') {
		var totalAccCodeAmt = Number(numberWithoutCommas($('#accountCodeTot_' + i).text()));
		var totalReimbAmt = Number(numberWithoutCommas($('#totReiumbersment_' + i).text()));
		if (totalAccCodeAmt !== null && totalAccCodeAmt !== '' && totalAccCodeAmt !== undefined &&
			totalReimbAmt !== null && totalReimbAmt !== '' && totalReimbAmt !== undefined) {
			if (totalAccCodeAmt !== totalReimbAmt) {
				$('#accountCodeTotError_' + i).append("<small class='help-block' role='alert' aria-atomic='true'>Account codes and reimbursement total not equal.</small>");
				$('#accountCodeTotError_' + i).show();
				isValid = false;
			}
		}
	}
	return isValid;
}

function checkBfnOptionsMeals(breakfastAmt, lunchAmt, dinnerAmt, altMealRsn,  i) {
	var isValid = true;
	var brkFstRt = bfnOptionsTravel[4];
	var lunchRt = bfnOptionsTravel[5];
	var dinRt = bfnOptionsTravel[6];
	if (brkFstRt !== null && brkFstRt !== '' && brkFstRt !== 'undefined' && brkFstRt !== undefined && Number(brkFstRt) !== 0
		&& breakfastAmt !== null && breakfastAmt !== '' && breakfastAmt !== 'undefined' && breakfastAmt !== undefined && Number(breakfastAmt) !== 0) {
		if (Number(breakfastAmt) > Number(brkFstRt)) {
			isValid = false;
			$('#breakfastError_' + i).append("<small class='help-block' role='alert' aria-atomic='true'>Breakfast cannot exceed $" + Number(brkFstRt).toFixed(2) + " limit.</small>");
			$('#breakfastError_' + i).show();
		}
	}
	if (lunchRt !== null && lunchRt !== '' && lunchRt !== 'undefined' && lunchRt !== undefined && Number(lunchRt) !== 0
		&& lunchAmt !== null && lunchAmt !== '' && lunchAmt !== 'undefined' && lunchAmt !== undefined && Number(lunchAmt) !== 0) {
		if (Number(lunchAmt) > Number(lunchRt)) {
			isValid = false;
			$('#lunchError_' + i).append("<small class='help-block' role='alert' aria-atomic='true'>Lunch cannot exceed $" + Number(lunchRt).toFixed(2) + " limit.</small>");
			$('#lunchError_' + i).show();
		}
	}
	if (dinRt !== null && dinRt !== '' && dinRt !== 'undefined' && dinRt !== undefined && Number(dinRt) !== 0
		&& dinnerAmt !== null && dinnerAmt !== '' && dinnerAmt !== 'undefined' && dinnerAmt !== undefined && Number(dinnerAmt) !== 0) {
		if (Number(dinnerAmt) > Number(dinRt)) {
			isValid = false;
			$('#dinnerError_' + i).append("<small class='help-block' role='alert' aria-atomic='true'>Dinner cannot exceed $" + Number(dinRt).toFixed(2) + " limit.</small>");
			$('#dinnerError_' + i).show();
		}
	}
	return isValid;
}

function validateTimeOnSameDate(sortedtravelRequests) {
	var isValid = true;
	for (var i = 1; i < sortedtravelRequests.length; i++) {
		for (var k = i + 1; k < sortedtravelRequests.length; k++) {
			if (sortedtravelRequests[i].travelDt === sortedtravelRequests[k].travelDt) {
				var fromTmApk = '';
				var toTmApi = '';
				if (sortedtravelRequests[i].toTmAp !== null && sortedtravelRequests[i].toTmAp !== undefined && sortedtravelRequests[i].toTmAp !== '') {
					if (sortedtravelRequests[i].toTmAp === 'A') {
						toTmApi = 'AM';
					}
					if (sortedtravelRequests[i].toTmAp === 'P') {
						toTmApi = 'PM';
					}
				}
				if (sortedtravelRequests[k].fromTmAp !== null && sortedtravelRequests[k].fromTmAp !== undefined && sortedtravelRequests[k].fromTmAp !== '') {
					if (sortedtravelRequests[k].fromTmAp === 'A') {
						fromTmApk = 'AM';
					}
					if (sortedtravelRequests[k].fromTmAp === 'P') {
						fromTmApk = 'PM';
					}
				}
				if ((sortedtravelRequests[i].toTmHr !== null && sortedtravelRequests[i].toTmHr !== undefined && sortedtravelRequests[i].toTmHr !== '') &&
					(sortedtravelRequests[i].toTmMin !== null && sortedtravelRequests[i].toTmMin !== undefined && sortedtravelRequests[i].toTmMin !== '') &&
					(toTmApi !== null && toTmApi !== undefined && toTmApi !== '')) {
					var endTime = sortedtravelRequests[i].toTmHr + ':' + sortedtravelRequests[i].toTmMin + ' ' + toTmApi;
					var endTimeValueTravel = get24HrsFrmAMPM(endTime);
				}
				if ((sortedtravelRequests[k].fromTmHr !== null && sortedtravelRequests[k].fromTmHr !== undefined && sortedtravelRequests[k].fromTmHr !== '') &&
					(sortedtravelRequests[k].fromTmMin !== null && sortedtravelRequests[k].fromTmMin !== undefined && sortedtravelRequests[k].fromTmMin !== '') &&
					(fromTmApk !== null && fromTmApk !== undefined && fromTmApk !== '')) {
					var startTime = sortedtravelRequests[k].fromTmHr + ':' + sortedtravelRequests[k].fromTmMin + ' ' + fromTmApk;
					var startTimeValueTravel = get24HrsFrmAMPM(startTime);
				}
				var noOfEndMinutes = getTimeAsNumberOfMinutes(startTimeValueTravel);
				var noOfStartMinutes = getTimeAsNumberOfMinutes(endTimeValueTravel);
				if (Number(noOfStartMinutes) > Number(noOfEndMinutes)) {
					var tripSeqNb = sortedtravelRequests[k].tripSeqNbr;
					$('#startEndTimeOverlapError_' + tripSeqNb).show();
					isValid = false;
				}
			}
		}
	}
	return isValid;
}

function sortByDate(a, b) {
	if (a.travelDt > b.travelDt) {
		return 1;
	}
	if (a.travelDt < b.travelDt) {
		return -1;
	}
	return 0;
}

var responseData;
function serverCall(url, method, payload, dataType, contentType, callback) {
	$.ajax({
		type : method,
		url : url,
		data : payload,
		dataType : dataType,
		async : false,
		contentType : contentType,
		success : function(res) {
			responseData = res;
			callback();
		},
		error : function(res) {
			responseData = 'error';
			console.log(res);
		}
	});
}
function getStates() {
	serverCall('/' + ctx + '/travelRequest/getStates', 'GET', null, 'JSON', 'application/json;charset=UTF-8', statesResponse);
}
let statesList = [];
getStates();
function statesResponse() {
	if (responseData != 'error') {
		for (var i = 0; i < responseData.result.length; i++) {
			statesList[i] = responseData.result[i].displayLabel;
		}
	} else {
		console.log('error getting states list.');
	}
}

function loadRows() {
	if (tripData != null && tripData != ''
		&& tripData != undefined && tripData != 'undefined') {
		var tripValue = tripData[0];
		if (tripValue != null && tripValue != ''
			&& tripValue != undefined && tripValue != 'undefined') {
			var travelDataArray = JSON.stringify(travelDataList);
			if (travelDataArray != null) {
				var travelData = JSON.parse(travelDataArray);
				if (accountsCodeList !== undefined && accountsCodeList.length !== 0) {
					assignedAccountCodes = accountsCodeList;
				}
				tripNbr = travelData[0].tripNbr;
				var entryDate = travelData[0].entryDt;
				var trvlStatus = travelData[0].trvlReqStat;
				if (trvlStatus === 'S') {
					statusText = 'Saved'
				}
				if (trvlStatus === 'P') {
					statusText = 'Pending'
				}
				if (trvlStatus === 'A') {
					statusText = 'Approved'
				}
				if (trvlStatus === 'R') {
					statusText = 'Returned'
				}
				$('#travelNbr').text(tripNbr);
				$('#travelStatus').text(statusText);
				if (entryDate !== null && entryDate !== '' && entryDate !== undefined) {
					var dateEntered = moment(entryDate).format('MM-DD-YYYY');
					$('#dateEntered').text(dateEntered);
				}
				if (tripNbr != null) {
					if (assignedAccountCodes != null && assignedAccountCodes.length > 0) {
						sumSameAccountSummary();
					}
					for (var i = 0; i < travelData.length; i++) {
						var indxNbr = travelData[i].tripSeqNbr;
						indx = indxNbr - 1;
						var datestring = changeDateFormatMMDDYYYY(travelData[i].trvlDt);
						var fromTmAp = '';
						var toTmAp = '';
						if (travelData[i].fromTmAp !== null && travelData[i].fromTmAp !== undefined && travelData[i].fromTmAp !== '') {
							if (travelData[i].fromTmAp === 'A') {
								fromTmAp = 'AM';
							}
							if (travelData[i].fromTmAp === 'P') {
								fromTmAp = 'PM';
							}
						}
						if (travelData[i].toTmAp !== null && travelData[i].toTmAp !== undefined && travelData[i].toTmAp !== '') {
							if (travelData[i].toTmAp === 'A') {
								toTmAp = 'AM';
							}
							if (travelData[i].toTmAp === 'P') {
								toTmAp = 'PM';
							}
						}
						if ((travelData[i].fromTmHr !== null && travelData[i].fromTmHr !== undefined && travelData[i].fromTmHr !== '') &&
							(travelData[i].fromTmMin !== null && travelData[i].fromTmMin !== undefined && travelData[i].fromTmMin !== '') &&
							(fromTmAp !== null && fromTmAp !== undefined && fromTmAp !== '')) {
							var startTime = travelData[i].fromTmHr + ':' + travelData[i].fromTmMin + ' ' + fromTmAp;
							var startTimeValueTravel = get24HrsFrmAMPM(startTime);
						}
						if ((travelData[i].toTmHr !== null && travelData[i].toTmHr !== undefined && travelData[i].toTmHr !== '') &&
							(travelData[i].toTmMin !== null && travelData[i].toTmMin !== undefined && travelData[i].toTmMin !== '') &&
							(toTmAp !== null && toTmAp !== undefined && toTmAp !== '')) {
							var endTime = travelData[i].toTmHr + ':' + travelData[i].toTmMin + ' ' + toTmAp;
							var endTimeValueTravel = get24HrsFrmAMPM(endTime);
						}
						var contact = travelData[i].contact;
						var purpose = travelData[i].purpose;
						var startTime = travelData[i].startTimeValueTravel;
						var addressFromLine1 = travelData[i].origLocId;
						var addressFromLine2 = travelData[i].origLocAddr;
						var fromState = travelData[i].origLocSt;
						var fromCity = travelData[i].origLocCity;
						var fromZip = travelData[i].origLocZip;
						var addressToLine1 = travelData[i].descLocId;
						var addressToLine2 = travelData[i].descLocAddr;
						var toState = travelData[i].descLocSt;
						var toCity = travelData[i].descLocCity;
						var toZip = travelData[i].descLocZip;
						var isRoundTrip = travelData[i].roundTrip;
						var isCommute = travelData[i].useEmpTrvlCommuteDist;
						var mileageBegining = numberWithCommas(travelData[i].begOdometer);
						var mileageEnd = numberWithCommas(travelData[i].endOdometer);
						var map = numberWithCommas(travelData[i].mapMiles);
						var miscAmt = numberWithCommas(travelData[i].miscAmt);
						var miscAmtRsn = travelData[i].miscAmtRsn;
						var trvlReqStat = travelData[i].trvlReqStat;
						createGrid();
						if (trvlReqStat === 'A' || trvlReqStat === 'P') {
							$('#documentUpload').removeAttr('disabled');
							$('#documentUpload').css({'display': 'block', 'width':'125px'});
							$('#documentUpload').click(function() {
								showDocumentModal('001', 'TRAVEL DOCUMENTS', 'TRAVEL', 'Y');
							});
							disableFieldsforView(indx);
						} else {
							$('#documentUpload').removeAttr('disabled');
							$('#documentUpload').css({'display': 'block', 'width':'125px'});
							$('#documentUpload').click(function() {
								showDocumentModal('001', 'TRAVEL DOCUMENTS', 'TRAVEL', 'N');
							});
							addEditAssignAccountCodeForm(indx, 'Y');
						}
						checkDocuments();
						$('#startDate_' + indx).val(datestring);
						$('#startTime_' + indx).val(startTimeValueTravel);
						$('#endTime_' + indx).val(endTimeValueTravel);
						$('#contact_' + indx).val(contact);
						$('#purpose_' + indx).val(purpose);
						$('#addressFromLine1_' + indx).val(addressFromLine1);
						$('#addressFromLine2_' + indx).val(addressFromLine2);
						$('#fromState_' + indx).val(fromState);
						$('#fromCity_' + indx).val(fromCity);
						$('#fromZip_' + indx).val(fromZip);
						$('#addressToLine1_' + indx).val(addressToLine1);
						$('#addressToLine2_' + indx).val(addressToLine2);
						$('#toState_' + indx).val(toState);
						$('#toCity_' + indx).val(toCity);
						$('#toZip_' + indx).val(toZip);
						if (isRoundTrip == 'Y') {
							$('#isRoundTrip_' + indx).attr("checked", true);
						}
						if (isCommute == 'Y') {
							$('#isCommute_' + indx).attr("checked", true);
						}
						$('#mileageBegining_' + indx).val(mileageBegining);
						$('#mileageEnd_' + indx).val(mileageEnd);
						if (map == '0') {
							map = '';
							$('#map_' + indx).val(map);
						} else {
							$('#map_' + indx).val(map);
						}
						if (mileageBegining == '0') {
							mileageBegining = '';
							$('#mileageBegining_' + indx).val(mileageBegining);
						} else {
							$('#mileageBegining_' + indx).val(mileageBegining);
						}
						if (mileageEnd == '0') {
							mileageEnd = '';
							$('#mileageEnd_' + indx).val(mileageEnd);
						} else {
							$('#mileageEnd_' + indx).val(mileageEnd);
						}
						if (bfnOptionsTravel[1] == 'Y') {
							disableMapField(indx);
						}
						$('#miscAmt_' + indx).val(miscAmt);
						$('#otherReason_' + indx).val(miscAmtRsn);
						hideDivErrorAll(indx)
						calculateMileage(indx);
						showAccountCodeAmtMil();
						indx++;
					}
				}
			}
		}
		} else {
		createGrid();
		var travelRequestsData = $('#travelRequestsData').val();
		var travelRequestsValue = JSON.parse(travelRequestsData.replaceAll('\'', '\"'));
		$('#startDate_0').val(travelRequestsValue.fromDate);
		if (bfnOptionsTravel[1] == 'Y') {
			disableMapField(0);
		}
		if (bfnOptionsTravel[3] == 'Y') {
			disableAddressFields(0);
		}
		var diff = dateDiff(travelRequestsValue.fromDate.replaceAll('-', '\/'), travelRequestsValue.toDate.replaceAll('-', '\/'));
		var firstDate = new Date(travelRequestsValue.fromDate.replaceAll('-', '\/'));
		var nextDate = firstDate;
		for (var i = 0; i < diff; i++) {
			indx++;
			nextDate.setTime(nextDate.getTime() + 86400000);
			var datestring = ("0" + (nextDate.getMonth() + 1)).slice(-2) + "-" + ("0" + nextDate.getDate()).slice(-2) + "-" + nextDate.getFullYear();
			createGrid();
			if (bfnOptionsTravel[1] == 'Y') {
				disableMapField(indx);
			}
			if (bfnOptionsTravel[3] == 'Y') {
				disableAddressFields(indx);
			}
			$('#startDate_' + indx).val(datestring);
		}
		indx++;
		$(".dateValidator01").hide();
	}
}
function addRow() {
	if (deletedRows > 0) {
		indx++;
	}
	createGrid();
	if (bfnOptionsTravel[1] == 'Y') {
		disableMapField(indx);
	}
	if (bfnOptionsTravel[3] == 'Y') {
		disableAddressFields(indx);
	}
	if (!deletedRows > 0) {
		indx++;
	}
	$(".dateValidator01").hide();
}

function disableMileageFields(indx) {
	$('#mileageBegining_' + indx).prop("disabled", true);
	$('#mileageBegining_' + indx).addClass('input-disabled');
	$('#mileageEnd_' + indx).prop("disabled", true);
	$('#mileageEnd_' + indx).addClass('input-disabled');
}

function enableMileageFields(indx) {
	$('#mileageBegining_' + indx).prop("disabled", false);
	$('#mileageBegining_' + indx).removeClass('input-disabled');
	$('#mileageEnd_' + indx).prop("disabled", false);
	$('#mileageEnd_' + indx).removeClass('input-disabled');
}

function disableMapField(indx) {
	$('#map_' + indx).prop("disabled", true);
	$('#map_' + indx).addClass('input-disabled');
}

function enableMapField(indx) {
	$('#map_' + indx).prop("disabled", false);
	$('#map_' + indx).removeClass('input-disabled');
}

function disableAddressFields(indx) {
	$('#addressFromLine1_' + indx).prop("disabled", true);
	$('#addressFromLine1_' + indx).addClass('input-disabled');
	$('#addressFromLine2_' + indx).prop("disabled", true);
	$('#addressFromLine2_' + indx).addClass('input-disabled');
	$('#fromState_' + indx).prop("disabled", true);
	$('#fromState_' + indx).addClass('input-disabled');
	$('#fromCity_' + indx).prop("disabled", true);
	$('#fromCity_' + indx).addClass('input-disabled');
	$('#fromZip_' + indx).prop("disabled", true);
	$('#fromZip_' + indx).addClass('input-disabled');
	$('#addressToLine1_' + indx).prop("disabled", true);
	$('#addressToLine1_' + indx).addClass('input-disabled');
	$('#addressToLine2_' + indx).prop("disabled", true);
	$('#addressToLine2_' + indx).addClass('input-disabled');
	$('#toState_' + indx).prop("disabled", true);
	$('#toState_' + indx).addClass('input-disabled');
	$('#toCity_' + indx).prop("disabled", true);
	$('#toCity_' + indx).addClass('input-disabled');
	$('#toZip_' + indx).prop("disabled", true);
	$('#toZip_' + indx).addClass('input-disabled');
}

function disableFieldsforView(indx) {
	$('#startDate_' + indx).prop("disabled", true);
	$('#startDate_' + indx).addClass('input-disabled');
	$('#startTime_' + indx).prop("disabled", true);
	$('#startTime_' + indx).addClass('input-disabled');
	$('#endTime_' + indx).prop("disabled", true);
	$('#endTime_' + indx).addClass('input-disabled');
	$('#contact_' + indx).prop("disabled", true);
	$('#contact_' + indx).addClass('input-disabled');
	$('#purpose_' + indx).prop("disabled", true);
	$('#purpose_' + indx).addClass('input-disabled');
	$('#addressFromLine1_' + indx).prop("disabled", true);
	$('#addressFromLine1_' + indx).addClass('input-disabled');
	$('#addressFromLine2_' + indx).prop("disabled", true);
	$('#addressFromLine2_' + indx).addClass('input-disabled');
	$('#fromState_' + indx).prop("disabled", true);
	$('#fromState_' + indx).addClass('input-disabled');
	$('#fromCity_' + indx).prop("disabled", true);
	$('#fromCity_' + indx).addClass('input-disabled');
	$('#fromZip_' + indx).prop("disabled", true);
	$('#fromZip_' + indx).addClass('input-disabled');
	$('#addressToLine1_' + indx).prop("disabled", true);
	$('#addressToLine1_' + indx).addClass('input-disabled');
	$('#addressToLine2_' + indx).prop("disabled", true);
	$('#addressToLine2_' + indx).addClass('input-disabled');
	$('#toState_' + indx).prop("disabled", true);
	$('#toState_' + indx).addClass('input-disabled');
	$('#toCity_' + indx).prop("disabled", true);
	$('#toCity_' + indx).addClass('input-disabled');
	$('#toZip_' + indx).prop("disabled", true);
	$('#toZip_' + indx).addClass('input-disabled');
	$('#isRoundTrip_' + indx).prop("disabled", true);
	$('#isCommute_' + indx).prop("disabled", true);
	$('#mileageBegining_' + indx).prop("disabled", true);
	$('#mileageBegining_' + indx).addClass('input-disabled');
	$('#mileageEnd_' + indx).prop("disabled", true);
	$('#mileageEnd_' + indx).addClass('input-disabled');
	$('#map_' + indx).prop("disabled", true);
	$('#map_' + indx).addClass('input-disabled');
	$('#miscAmt_' + indx).prop("disabled", true);
	$('#miscAmt_' + indx).addClass('input-disabled');
	$('#breakfast_' + indx).prop("disabled", true);
	$('#breakfast_' + indx).addClass('input-disabled');
	$('#lunch_' + indx).prop("disabled", true);
	$('#lunch_' + indx).addClass('input-disabled');
	$('#dinner_' + indx).prop("disabled", true);
	$('#dinner_' + indx).addClass('input-disabled');
	$('#mealOverride_' + indx).prop("disabled", true);
	$('#mealOverride_' + indx).addClass('input-disabled');
	$('#overrideReason_' + indx).prop("disabled", true);
	$('#overrideReason_' + indx).addClass('input-disabled');
	$('#parking_' + indx).prop("disabled", true);
	$('#parking_' + indx).addClass('input-disabled');
	$('#taxi_' + indx).prop("disabled", true);
	$('#taxi_' + indx).addClass('input-disabled');
	$('#other_' + indx).prop("disabled", true);
	$('#other_' + indx).addClass('input-disabled');
	$('#otherReason_' + indx).prop("disabled", true);
	$('#otherReason_' + indx).addClass('input-disabled');
	$('#accommodations_' + indx).prop("disabled", true);
	$('#accommodations_' + indx).addClass('input-disabled');
	$('#reinAmt_' + indx).prop("disabled", true);
	$('#reinAmt_' + indx).addClass('input-disabled');
	$('#directBill_' + indx).prop("disabled", true);
	$('#directBill_' + indx).addClass('input-disabled');
	$('#copyFrom_' + indx).prop("disabled", true);
	$('#copyFrom_' + indx).addClass('input-disabled');
	$('#deleteRowBtn_' + indx).hide();
	$('#new-btn1').hide();
	$('#new-btn2').prop("disabled", true);
	$('#new-btn4').hide();
	$('#new-btn5').hide();
	$('.help-block').hide()
	$('#addButton').hide();
	$('#clearBtnDate').remove();
	$('#datePickerCal').remove();
	$('.fa-calendar').hide()
	$('.startDateFocus').remove()
}

function setDate(i) {
	console.log('date clicked ' + i);
	var checkin = $('#SearchStartDate_' + i)
		.fdatepicker({
			format : 'mm-dd-yyyy',
			language : initialLocaleCode,
			weekStart : 7
		})
		.on('changeDate', function(ev) {})
		.data('datepicker')
	validateFields('startDate', i);
}

function showAssignAccountCodeForm(id) {
	if (tripData != null && tripData != ''
		&& tripData != undefined && tripData != 'undefined') {
		var tripValue = tripData[0];
		if (tripValue != null && tripValue != ''
			&& tripValue != undefined && tripValue != 'undefined') {
			var travelDataArray = JSON.stringify(travelDataList);
			if (travelDataArray != null) {
				var travelData = JSON.parse(travelDataArray);
				tripNbr = travelData[0].tripNbr;
				var trvlReqStat = travelData[0].trvlReqStat;
				if (trvlReqStat === 'A' || trvlReqStat === 'P') {
					viewAssignAccountCodeForm(id);
				} else {
					addEditAssignAccountCodeForm(id);
				}
			} else {
				addEditAssignAccountCodeForm(id);
			}
		}
	}
	else {
		addEditAssignAccountCodeForm(id);
	}
}

function viewAssignAccountCodeForm(id) {
	localStorage.setItem("blockId", id);
	addEditAccCodes = true;
	$("#assignAccountCodesModal").modal('show');
	if (accountCodeAddIndx == 0) {
		$("#accountCodeAdd").click();
	}
	{
		for (var i = 1; i <= accountCodeAddIndx; i++) {
			deleteAccountCodeItem(i);
		}
		accountCodeAddIndx = 0;
		for (var i = 0; i < assignedAccountCodes.length; i++) {
			var childJson = assignedAccountCodes[i];
			if (childJson.id == id) {
				childJson.childAccountCodes
				for (var j = 0; j < childJson.childAccountCodes.length; j++) {
					$("#accountCodeAdd").click();
					var secondLastIndex = childJson.childAccountCodes[j].lastIndexOf('-', childJson.childAccountCodes[j].lastIndexOf('-') - 1);
					$('#_accountCd_' + parseInt(j + 1)).val(childJson.childAccountCodes[j].substring(0, secondLastIndex));
					$('#accountCodePercent_0' + parseInt(j + 1)).val(childJson.childAccountCodes[j].substring(parseInt(secondLastIndex + 1), childJson.childAccountCodes[j].lastIndexOf('-')));
					$('#accountCodeAmount_0' + parseInt(j + 1)).val(numberWithCommas(childJson.childAccountCodes[j].substring(childJson.childAccountCodes[j].lastIndexOf('-') + 1)));
					$("#_accountCd_" + parseInt(j + 1)).prop("disabled", true);
					$("#_accountCd_" + parseInt(j + 1)).addClass('input-disabled');
					$('#accountCodePercent_0' + parseInt(j + 1)).prop("disabled", true);
					$('#accountCodePercent_0' + parseInt(j + 1)).addClass('input-disabled');
					$('#accountCodeAmount_0' + parseInt(j + 1)).prop("disabled", true);
					$('#accountCodeAmount_0' + parseInt(j + 1)).addClass('input-disabled');
					$('#accountCodeDeleteIcon_' + parseInt(j + 1)).hide();
					$('#account-code-btn' + parseInt(j + 1)).hide();
					$('#accountCodeAdd').hide();
					$('#okAccountCodes').hide();
				}
			}
		}
		$("#refreshTotals").click();
	}
}

var addEditAccCodes = false;

function addEditAssignAccountCodeForm(id, viewType) {
	localStorage.setItem("blockId", id);
	addEditAccCodes = true;
	if (viewType === undefined) {
		$("#assignAccountCodesModal").modal('show');
	}
	if (id === undefined) {
		sumSameAccountSummaryEdit();
	} else {
		{
			for (var i = 1; i <= accountCodeAddIndx + deleteAccCode; i++) {
				deleteAccountCodeItem(i);
			}
			accountCodeAddIndx = 0;
			for (var i = 0; i < assignedAccountCodes.length; i++) {
				var childJson = assignedAccountCodes[i];
				if (childJson.id == id) {
					childJson.childAccountCodes
					for (var j = 0; j < childJson.childAccountCodes.length; j++) {
						$("#accountCodeAdd").click();
						var secondLastIndex = childJson.childAccountCodes[j].lastIndexOf('-', childJson.childAccountCodes[j].lastIndexOf('-') - 1);
						$('#_accountCd_' + parseInt(j + 1)).val(childJson.childAccountCodes[j].substring(0, secondLastIndex));
						$('#accountCodePercent_0' + parseInt(j + 1)).val(childJson.childAccountCodes[j].substring(parseInt(secondLastIndex + 1), childJson.childAccountCodes[j].lastIndexOf('-')));
						$('#accountCodeAmount_0' + parseInt(j + 1)).val(numberWithCommas(childJson.childAccountCodes[j].substring(childJson.childAccountCodes[j].lastIndexOf('-') + 1)));
						getAccountCodeDesc(parseInt(j + 1));
					}
				}
			}
			if (accountCodeAddIndx == 0) {
				$("#accountCodeAdd").click();
			}
			setTimeout(function() {
				$("#_accountCd_1").focus();
			}, 200);
			$("#refreshTotals").click();
		}
	}
}

function deleteAccountCodeItem(id) {
	$("#_accountCode_tbl_tr_" + id).remove();
	updateAmtTotals();
}
var accountCodeAddIndx = 0;
$("#accountCodeAdd").on('click', function() {
	accountCodeAddIndx++;
	createAccountCodeRow();
});
function updateTotals() {
	var id = localStorage.getItem("blockId");
	var accountCodePercentTotal = '0';
	for (var i = 1; i <= accountCodeAddIndx + deleteAccCode; i++) {
		if ($("#_accountCd_" + i).length > 0) {
			var accountPercent = $('#accountCodePercent_0' + i).val();
			if (accountPercent == '') {
				accountPercent = 0;
			}
			$('#accountCodePercent_0' + i).val(Number(accountPercent).toFixed(3));
			accountCodePercentTotal = addFloat(accountCodePercentTotal, parseFloat(accountPercent));
			accountCodePercentTotal = accountCodePercentTotal.toFixed(3);
		}
		$('#accountCodePercentTotal').text(accountCodePercentTotal + "%");
		if (validatePercent() && validateAmount()) {
			if((Number(numberWithoutCommas($('#accountCodeAmountTotal').text()))) > 0){
				$('#accountCodeTot_' + localStorage.getItem("blockId")).text($('#accountCodeAmountTotal').text());
			}
		}
	}
	if (id === 'undefined') {
		var totalReimb = $('#totalRequest').text();
		$('#reimbursementTotal').text(totalReimb);
	} else {
		var totalReimb = $('#totReiumbersment_' + localStorage.getItem("blockId")).text();
		$('#reimbursementTotal').text(totalReimb);
	}
	var accountPercentage = $("#accountCodePercentTotal").text().replaceAll("%", '');
	if (parseInt(accountPercentage) == 100) {
		$('#accountCodePercentTotalError').text('');
	} 
	if (allAcountRowEmpty()){
		$('#accountCodePercentTotalError').text('');
	}
}
$("#refreshTotals").on('click', function() {
	updateTotals();
	updateAmtTotals();
});

function checkDuplicates(accountCd){
	var isValid = false;
	var recipientsArray = accountCd.sort(); 
	for (var i = 0; i < recipientsArray.length - 1; i++) {
	    if (recipientsArray[i + 1] == recipientsArray[i]) {
	        isValid = true
	    }
	}
	return isValid;
}

function accountCodeCheck() {
	var accCodeExists = false;
	var accountCdArrr = [];
	for (var i = 1; i <= accountCodeAddIndx + deleteAccCode; i++) {
		if ($("#_accountCd_" + i).length > 0) {
			var accountCd = $("#_accountCd_" + i).val();
			if (accountCd != '' && accountCd != undefined && accountCd != null) {
				var accPercent = Number($("#accountCodePercent_0" + i).val());
				var amountCd = Number(numberWithoutCommas($("#accountCodeAmount_0" + i).val()));
				var classList = $("#_accountCode_tbl_tr_" + i).attr("class").split(/\s+/);
				var accountCdDash = accountCd.replaceAll("-", "");
				var accountCdDot = accountCdDash.replaceAll(".", "");
				var payload = {
					accountCd : accountCdDot.trim()
				}
				$.ajax({
					type : 'POST',
					url : '/' + ctx + '/travelRequest/checkAccountCodes',
					dataType : 'JSON',
					async : false,
					contentType : 'application/json;charset=UTF-8',
					data : JSON.stringify(payload),
					success : function(res) {
						data = res.result;
						if (data) {
							accountCdArrr.push(accountCd)
							accCodeExists = true;
						} else {
							accCodeExists = false;
						}
					},
					error : function(res) {
						console.log(res);
					}
				});
				if (!accCodeExists && !oneAcountRowEmpty(i)) {
					$('#accountCodeEmptyError_' + i).hide();
					$('#accountCodeExistsError_' + i).show();
					accCodeExists = false;
				}
			}
		}
		var dupAccCodes = checkDuplicates(accountCdArrr);
		if (dupAccCodes) {
			$('#accountCodeDuplicateError').text('Account code should be unique');
			accCodeExists = false;
		}
	}
	return accCodeExists;
}

var assignedAccountCodes = new Array();

$("#okAccountCodes").on('click', function() {
	clickAccountCodes();
});

function clickAccountCodes() {
	if(allAcountRowEmpty() && !(notAllAcountRowEmpty()) && (onlyOneRowExistDeleted == false)){
		$("#assignAccountCodesModal").modal('hide');
	}
	if (onlyOneRowExistDeleted == true) {
		if (assignedAccountCodes.length > 0) {
			for (k = 0; k < assignedAccountCodes.length; k++) {
				if (assignedAccountCodes[k].hasOwnProperty('id')) {
					var acCodeIndex = assignedAccountCodes[k].id;
					if (acCodeIndex == deletedRowId) {
						$("#trvlSummaryTable").empty();
						assignedAccountCodes.splice(k, 1);
						sumSameAccountSummary();
						onlyOneRowExistDeleted = false;
					}
					$('#accountCodeTot_' + deletedRowId).text('');
					$("#assignAccountCodesModal").modal('hide');
					$('[id^="accountCodeTotError_"]').hide();
				}
			}
		}
	}
	var validationFields = notAllAcountRowEmpty();
	var validationAccounts = accountCodeCheck();
	if(validationFields && validationAccounts){
		$("#trvlSummaryTable").empty();
		$('#accountCodeError_' + i).append("");
		updateTotals();
		updateAmtTotals()
		if (validatePercent() && validateAmount()) {
			$('#accountCodePercentTotalError').text('');
			var childAccountCodes = new Array();
			$('#trvlSummaryTable').val('');
			var k = localStorage.getItem("blockId");
			if (k === 'undefined') {
				for (var m = 1; m <= accountCodeAddIndx + deleteAccCode; m++) {
					$('#trvlSummaryTable').find("tr:gt(" + m + ")").remove();
					if ($("#_accountCd_" + m).length > 0) {
						var accountCd = $("#_accountCd_" + m).val();
						if(accountCd != '' && accountCd != undefined && accountCd != null){
						$('#trvlSummaryTable').append("<tr class='accountCodeRow_" + m + "'><td class='label-color text-left'>" + $("#_accountCd_" + m).val() + "</td><td width='30%'></td><td class='label-color text-right'>" + $("#accountCodeAmount_0" + m).val() + "</td></tr>");
					}
					}
				}
				for (var j = 0; j <= indx + deletedRows; j++) {
					var childAccountCodes = new Array();
					var elementExists = document.getElementById('startDate_' + j);
					if (elementExists) {
						var totalReiumbersment = Number(numberWithoutCommas($('#totReiumbersment_' + j).text()));
						var arrayAmount = [];
						var arraySum = 0;
						var diff = 0;
						for (var i = 1; i <= accountCodeAddIndx + deleteAccCode; i++) {
							if ($("#_accountCd_" + i).length > 0) {
								var accountCd = $("#_accountCd_" + i).val();
								if(accountCd != '' && accountCd != undefined && accountCd != null){
							var accountPer = Number($("#accountCodePercent_0" + i).val());
							var amount = totalReiumbersment * (accountPer / 100);
							arrayAmount.push(amount.toFixed(2));
							}
						}
						}
						arrayAmount.forEach(function(value, i) {
							arraySum = arraySum + Number(arrayAmount[i]);
						});
						if (Number(arraySum.toFixed(2)) !== Number(totalReiumbersment.toFixed(2))) {
							var substract = Number(totalReiumbersment.toFixed(2)) - Number(arraySum.toFixed(2));
							diff = substract.toFixed(2);
						}
						var sumIndivTotal = 0;
						var n = 0;
						var l = 0;
						for (var i = 1; i <= accountCodeAddIndx + deleteAccCode; i++) {
							if ($("#_accountCd_" + i).length > 0) {
								var accountCd = $("#_accountCd_" + i).val();
								if(accountCd != '' && accountCd != undefined && accountCd != null){
								var accountCode = Number($('#accountCodePercent_0' + i).val());
								if (accountCode > 0 && accountCode !== '' && accountCode !== null && accountCode !== undefined) {
										childAccountCodes.push($("#_accountCd_" + i).val() + '-' + $("#accountCodePercent_0" + i).val() + '-' + numberWithoutCommas((Number(arrayAmount[parseInt(l)]) + Number(diff)).toFixed(2)));
										sumIndivTotal = (Number(arrayAmount[parseInt(l)])) + Number(diff);
									}
									var childAccountCode = {
										id : j,
										childAccountCodes : childAccountCodes
									}
									var found = false;
									var foundAt = 0;
									assignedAccountCodes.forEach(function(value, i) {
										if (assignedAccountCodes[i].id == j) {
											foundAt = i;
											found = true;
										}
									});
									if (found) {
										assignedAccountCodes[foundAt] = childAccountCode;
									} else {
										assignedAccountCodes.push(childAccountCode);
									}
									n = parseInt(i + 1);
									l = l+1;
									break;
								}
						}
							}
						for (var p = n; p <= accountCodeAddIndx + deleteAccCode; p++) {
							if ($("#_accountCd_" + p).length > 0) {
								var accountCd = $("#_accountCd_" + p).val();
								if(accountCd != '' && accountCd != undefined && accountCd != null){
								var accountCode = Number($('#accountCodePercent_0' + p).val());
								if (accountCode > 0 && accountCode !== '' && accountCode !== null && accountCode !== undefined) {
										childAccountCodes.push($("#_accountCd_" + p).val() + '-' + $("#accountCodePercent_0" + p).val() + '-' + numberWithoutCommas((Number(arrayAmount[parseInt(l)]) + Number(diff)).toFixed(2)));
										sumIndivTotal = sumIndivTotal + (Number(arrayAmount[parseInt(l)]));
									}
									var childAccountCode = {
										id : j,
										childAccountCodes : childAccountCodes
									}
									var found = false;
									var foundAt = 0;
									assignedAccountCodes.forEach(function(value, i) {
										if (assignedAccountCodes[i].id == j) {
											foundAt = i;
											found = true;
										}
									});
									if (found) {
										assignedAccountCodes[foundAt] = childAccountCode;
									} else {
										assignedAccountCodes.push(childAccountCode);
									}
									l++;
								}
						}
							}
						$('#accountCodeTot_' + j).text(sumIndivTotal.toFixed(2));
						$("#assignAccountCodesModal").modal('hide');
						$('[id^="accountCodeTotError_"]').hide();
					}
				}
			} else {
				$('.accountCodeRow_' + k + '').hide();
				for (var i = 1; i <= accountCodeAddIndx + deleteAccCode; i++) {
					if ($("#_accountCd_" + i).length > 0) {
						var accountCd = $("#_accountCd_" + i).val();
						if(accountCd != '' && accountCd != undefined && accountCd != null){
					childAccountCodes.push($("#_accountCd_" + i).val() + '-' + $("#accountCodePercent_0" + i).val() + '-' + numberWithoutCommas($("#accountCodeAmount_0" + i).val()));
					}
				}
				}
				var childAccountCode = {
					id : k,
					childAccountCodes : childAccountCodes
				}
				var found = false;
				var foundAt = 0;
				assignedAccountCodes.forEach(function(value, i) {
					if (assignedAccountCodes[i].id == k) {
						foundAt = i;
						found = true;
					}
				});
				if (found) {
					assignedAccountCodes[foundAt] = childAccountCode;
				} else {
					assignedAccountCodes.push(childAccountCode);
				}
				sumSameAccountSummary();
				$("#assignAccountCodesModal").modal('hide');
				$('[id^="accountCodeTotError_"]').hide();
			}
		}
	};
}

function sumSameAccountSummaryEdit(){
	var array = [];
	var result = [];
	assignedAccountCodes.forEach(function(value, i) {
		var id = assignedAccountCodes[i].id;
		var childCode = assignedAccountCodes[i].childAccountCodes;
		childCode.forEach(function(value, i) {
			var childCodeInd = splitAccountCode(childCode[i], id);
			array.push(childCodeInd);
		});
	});
	array.reduce(function(res, value) {
		if (!res[value.accountNum]) {
			res[value.accountNum] = {
				accountNum : value.accountNum,
				accountPer : value.accountPer,
				accountAmt : 0
			};
			result.push(res[value.accountNum])
		}
		res[value.accountNum].accountAmt += value.accountAmt;
		return res;
	}, {});
	for (var i = 1; i <= accountCodeAddIndx + deleteAccCode; i++) {
		deleteAccountCodeItem(i);
	}
	accountCodeAddIndx = 0;
	result.forEach(function(value, i) {
		var childCodeShow = result[i];
		$("#accountCodeAdd").click();
		$('#_accountCd_' + parseInt(i + 1)).val(childCodeShow.accountNum);
		var totalReiumbersment = $('#totalRequest').text();
		var amount = childCodeShow.accountAmt
		var accountPer = (Number(amount) / Number(totalReiumbersment)) * 100;
		$('#accountCodePercent_0' + parseInt(i + 1)).val(accountPer);
		$('#accountCodeAmount_0' + parseInt(i + 1)).val(numberWithCommas(numberWithCommas(childCodeShow.accountAmt.toFixed(2))));
		getAccountCodeDesc(parseInt(i + 1));
	});
	if (result.length === 0) {
		accountCodeAddIndx = 0;
		$("#accountCodeAdd").click();
	}
	updateTotals();
	updateAmtTotals();
}

function sumSameAccountSummary(){
	var array = [];
	var result = [];
	assignedAccountCodes.forEach(function(value, i) {
		var id = assignedAccountCodes[i].id;
		var childCode = assignedAccountCodes[i].childAccountCodes;
		childCode.forEach(function(value, i) {
			var childCodeInd = splitAccountCode(childCode[i], id);
			array.push(childCodeInd);
		});
	});
	array.reduce(function(res, value) {
		if (!res[value.accountNum]) {
			res[value.accountNum] = {
				accountNum : value.accountNum,
				accountPer : value.accountPer,
				accountAmt : 0
			};
			result.push(res[value.accountNum])
		}
		res[value.accountNum].accountAmt += value.accountAmt;
		return res;
	}, {});
	result.forEach(function(value, i) {
		var childCodeShow = result[i];
		$('#trvlSummaryTable').append("<tr class='accountCodeRow_" + i + "'><td class='label-color text-left'>" + childCodeShow.accountNum + "</td><td width='36%'></td><td class='label-color text-right'>" + numberWithCommas(childCodeShow.accountAmt.toFixed(2)) + "</td></tr>");
	});
}

function splitAccountCode(childCode, id) {
	if (childCode !== '' && childCode !== null && childCode !== undefined) {
		var i = 5;
		var accountCoAll = childCode.split('-')
		var accountAccc = accountCoAll.slice(0, i).join('-')
		var amountAndSec = accountCoAll.slice(i).join('-');
		var amountAndPerSplit = amountAndSec.split('-');
		var percentAcc = amountAndPerSplit[0];
		var amountAcc = amountAndPerSplit[1];
		var childAccounts = {
				id : id,
				accountNum : accountAccc,
				accountPer : Number(percentAcc),
				accountAmt : Number(amountAcc)
			}
		return childAccounts;
	}
}

$("#calculatePercent").on('click', function() {
	var id = localStorage.getItem("blockId");
	if (id === 'undefined') {
		var totalReiumbersment = $('#totalRequest').text();
	} else {
		var totalReiumbersment = $('#totReiumbersment_' + id).text();
	}
	for (var i = 1; i <= accountCodeAddIndx + deleteAccCode; i++) {
		if ($("#_accountCd_" + i).length > 0) {
			var accountCode = Number($('#accountCodeAmount_0' + i).val());
			if (accountCode) {
				accountPer = (Number(accountCode) / Number(totalReiumbersment)) * 100;

				$('#accountCodePercent_0' + i).val(accountPer.toFixed(3))
			}
		}
	}
	updateTotals();
});

function calculateAmountClick() {
	var id = localStorage.getItem("blockId");
	var totalReiumbersment;
	if (id === 'undefined') {
		totalReiumbersment = numberWithoutCommas($('#totalRequest').text());
	} else {
		totalReiumbersment = numberWithoutCommas($('#totReiumbersment_' + id).text());
	}
	var arrayAmount = [];
	var arraySum = 0;
	var diff = 0;
	for (var i = 1; i <= accountCodeAddIndx + deleteAccCode; i++) {
		if ($("#_accountCode_tbl_tr_" + i).length > 0) {
			$('#amountCodeEmptyError_' + i).hide();
				var accountPercent = numberWithoutCommas(Number($('#accountCodePercent_0' + i).val()));
				if (accountPercent > 0 && accountPercent !== '' && accountPercent !== null && accountPercent !== undefined && totalReiumbersment>0) {
					accountAmt = (Number(accountPercent) * Number(totalReiumbersment)) / 100;
					arrayAmount.push(accountAmt.toFixed(2));
				}
		}
	}
	arrayAmount.forEach(function(value, i) {
		arraySum = arraySum + Number(arrayAmount[i]);
	});
	if ((Number(arraySum).toFixed(2)) !== (Number(totalReiumbersment).toFixed(2))) {
		diff = Number(totalReiumbersment) - Number(arraySum);
		var sumDiff = Math.abs(Number(totalReiumbersment) - Number(arraySum));
	}
	if(sumDiff < 0.10) {
		var j = 0;
		var l = 0;
		for (var i = 1; i <= accountCodeAddIndx + deleteAccCode; i++) {
			if ($("#_accountCode_tbl_tr_" + i).length > 0) {
				var accountPercent = numberWithoutCommas(Number($('#accountCodePercent_0' + i).val()));
				if (accountPercent > 0 && accountPercent !== '' && accountPercent !== null && accountPercent !== undefined && totalReiumbersment>0) {
					$('#accountCodeAmount_0' + i).val(numberWithCommas((Number(arrayAmount[parseInt(l)]) + Number(diff)).toFixed(2)));
				}
				j = parseInt(i + 1);
				l = l+1;
				break;
			}
		}
		for (var k = j; k <= accountCodeAddIndx + deleteAccCode; k++) {
			if ($("#_accountCode_tbl_tr_" + i).length > 0) {
				var accountPercent = numberWithoutCommas(Number($('#accountCodePercent_0' + k).val()));
				if (accountPercent > 0 && accountPercent !== '' && accountPercent !== null && accountPercent !== undefined && totalReiumbersment>0) {
					$('#accountCodeAmount_0' + k).val(numberWithCommas((Number(arrayAmount[parseInt(l)])).toFixed(2)));
				}
				l++;
			}
		}
	} else {
		for (var i = 1; i <= accountCodeAddIndx + deleteAccCode; i++) {
			if ($("#_accountCode_tbl_tr_" + i).length > 0) {
					var accountPercent = numberWithoutCommas(Number($('#accountCodePercent_0' + i).val()));
					if (accountPercent > 0 && accountPercent !== '' && accountPercent !== null && accountPercent !== undefined && totalReiumbersment>0) {
						accountAmt = (Number(accountPercent) * Number(totalReiumbersment)) / 100;
						$('#accountCodeAmount_0' + i).val(numberWithCommas((Number(accountAmt)).toFixed(2)));
					}
			}
		}
	}
	updateTotals();
	updateAmtTotals();
}

$("#calculateAmount").on('click', function() {
	calculateAmountClick();
});

function updateAmtTotals() {
	var id = localStorage.getItem("blockId");
	var accountCodeAmountTotal = '0';
	for (var i = 1; i <= accountCodeAddIndx + deleteAccCode; i++) {
		if ($("#_accountCd_" + i).length > 0) {
			var tempAccount = ($('#accountCodeAmount_0' + i).val());
			if (tempAccount == '') {
				tempAccount = 0;
			}
			var tempAccountAmt = numberWithoutCommas(tempAccount);
			$('#accountCodeAmount_0' + i).val(numberWithCommas(Number(tempAccountAmt).toFixed(2)))
			accountCodeAmountTotal = addFloat(accountCodeAmountTotal, parseFloat(tempAccountAmt));
			accountCodeAmountTotal = accountCodeAmountTotal.toFixed(2);
			$('#accountCodeAmountTotal').text(numberWithCommas(accountCodeAmountTotal));
		}
	}
	if (id === 'undefined') {
		var totalReiumbersment = Number(numberWithoutCommas($('#totalRequest').text()));
	} else {
		var totalReiumbersment = Number(numberWithoutCommas($('#totReiumbersment_' + id).text()));
	}
	if(Number(numberWithCommas(accountCodeAmountTotal)) == totalReiumbersment){
		$('#amountCodeTotalError').text('');
	}
	if (allAcountRowEmpty()){
		$('#amountCodeTotalError').text('');
	}
}

function allAcountRowEmpty() {
	var isEmpty = true;
	for (var i = 1; i <= accountCodeAddIndx + deleteAccCode; i++) {
		if ($("#_accountCode_tbl_tr_" + i).length > 0) {
			var accountCd = $("#_accountCd_" + i).val();
			var accPercent = Number($("#accountCodePercent_0" + i).val());
			var amountCd = Number(numberWithoutCommas($("#accountCodeAmount_0" + i).val()));
			if ((accountCd !== '' && accountCd !== undefined && accountCd !== null)
				|| (accPercent !== '' && accPercent !== undefined && accPercent !== null && accPercent > 0)
				|| (amountCd !== '' && amountCd !== undefined && amountCd !== null && amountCd > 0)) {
				isEmpty = false;
			}
		}
	}
	return isEmpty;
}

function oneAcountRowEmpty(i) {
	var isEmpty = false;
		if ($("#_accountCode_tbl_tr_" + i).length > 0) {
			var accountCd = $("#_accountCd_" + i).val();
			var accPercent = Number($("#accountCodePercent_0" + i).val());
			var amountCd = Number(numberWithoutCommas($("#accountCodeAmount_0" + i).val()));
			if ((accountCd == '' || accountCd == undefined || accountCd == null)
				&& (accPercent == '' || accPercent == undefined || accPercent == null)
				&& (amountCd == '' || amountCd == undefined || amountCd == null)) {
				isEmpty = true;
			}
	}
	return isEmpty;
}

function validateAmount() {
	var isValid = true;
	updateAmtTotals();
	var id = localStorage.getItem("blockId");
	if (id === 'undefined') {
		var totalReiumbersment = Number(numberWithoutCommas($('#totalRequest').text()));
	} else {
		var totalReiumbersment = Number(numberWithoutCommas($('#totReiumbersment_' + id).text()));
	}
	var accountCodeAmountTotal = Number(numberWithoutCommas($("#accountCodeAmountTotal").text()));
	if (accountCodeAmountTotal !== totalReiumbersment) {
		isValid = false
	}
	if (accountCodeAmountTotal == totalReiumbersment) {
		$('#amountCodeTotalError').text('');
	}
	if (isValid) {
		return true;
	} else {
		return false;
	}
}

function validatePercent() {
	var isValid = true;
	var accountPercentage = $("#accountCodePercentTotal").text().replaceAll("%", '');
	if (parseInt(accountPercentage) != 100) {
		isValid = false;
	}
	if (parseInt(accountPercentage) == 100) {
		$('#accountCodePercentTotalError').text('');
	}
	return isValid;
}
function notAllAcountRowEmpty() {
	var isValid = false;
	for (var i = 1; i <= accountCodeAddIndx + deleteAccCode; i++) {
		if ($("#_accountCode_tbl_tr_" + i).length > 0) {
			var accountCd = $("#_accountCd_" + i).val();
			var accPercent = Number($("#accountCodePercent_0" + i).val());
			var amountCd = Number(numberWithoutCommas($("#accountCodeAmount_0" + i).val()));
			if (accountCd !== '' && accountCd !== undefined && accountCd !== null) {
				isValid = true;
				if (accPercent == '' || accPercent == undefined || accPercent == null || !(accPercent > 0)) {
					$('#percentCodeEmptyError_' + i).show();
					isValid = false;
					
				}
				if (amountCd == '' || amountCd == undefined || amountCd == null || !(amountCd > 0)) {
					$('#amountCodeEmptyError_' + i).show();
					isValid = false;
				}
			}
			if (accPercent > 0) {
				isValid = true;
				if (accountCd == '' || accountCd == undefined || accountCd == null) {
					$('#accountCodeExistsError_' + i).hide();
					$('#accountCodeEmptyError_' + i).show();
					isValid = false;
				}
				if (amountCd == '' || amountCd == undefined || amountCd == null || !(amountCd > 0)) {
					$('#amountCodeEmptyError_' + i).show();
					isValid = false;
				}
			}
			if (amountCd > 0) {
				isValid = true;
				if (accountCd == '' || accountCd == undefined || accountCd == null) {
					$('#accountCodeExistsError_' + i).hide();
					$('#accountCodeEmptyError_' + i).show();
					isValid = false;
				}
				if (accPercent == '' || accPercent == undefined || accPercent == null || !(accPercent > 0)) {
					$('#percentCodeEmptyError_' + i).show();
					isValid = false;
				}
			}
		}
	}
	if ((validatePercent() == false) && (allAcountRowEmpty() == false)) {
		$('#accountCodePercentTotalError').text('Total Percent must be equal to 100%');
	}
	if ((validateAmount() == false) && (allAcountRowEmpty() == false)) {
		$('#amountCodeTotalError').text('Total Amount must equal the Total Reimbursement');
	}	
	return isValid;
}

function checkOnlyOneRow(){
	var onlyOneRow = false
	var rowCount = 0;
	for (var i = 1; i <= accountCodeAddIndx + deleteAccCode; i++) {
		if (($("#_accountCode_tbl_tr_" + i).length > 0)) {
				rowCount++;
			}
	}
	if(rowCount == 1){
		onlyOneRow = true
	}
	return onlyOneRow;
}

function checkEmptyRow(){
	var emptyRow = false
	for (var i = 1; i <= accountCodeAddIndx + deleteAccCode; i++) {
		if (!($("#_accountCode_tbl_tr_" + i).length > 0)) {
			emptyRow = true;
		}
	}
	return emptyRow;
}

function hideDivErrorAccountCode(indx) {
	if ($('#_accountCd_' + indx).val() !== "") {
		$('#accountCodeEmptyError_' + indx).hide();
		$('#accountCodeExistsError_' + indx).hide();
		$('#accountCodeDuplicateError').text('');
	}
	if (Number($('#accountCodePercent_0' + indx).val()) > 0) {
		$('#percentCodeEmptyError_' + indx).hide();
	}
	if (Number($('#accountCodeAmount_0' + indx).val()) > 0) {
		$('#amountCodeEmptyError_' + indx).hide();
	}
}

function add(a, b) {
	return Number(a) + Number(b);
}
function addFloat(a, b) {
	return parseFloat(a) + parseFloat(b);
}

function createAccountCodeRow() {
	if((deleteAccCode>0) && (!addEditAccCodes)){
		accountCodeAddIndx = accountCodeAddIndx + deleteAccCode;
	}
	var gridData = '<tr class="selectable unselectedRow" id="_accountCode_tbl_tr_' + accountCodeAddIndx + '">' +
	'<td style="text-align: center; vertical-align: center; padding: 0px 0px 16px 0px !important;"><a ' +
	'href="#" id="accountCodeDeleteIcon_' + accountCodeAddIndx + '" class="actDelButton" onclick="deleteAccountItem(' + accountCodeAddIndx + ')">' +
	'<img src="/CommonWebPortals/images/trashcan.png" alt="Delete"' +
	'style="margin-bottom: 0" tabindex="-1">' +
	'</a></td>' +
	'<td style="padding: 0px 0px 16px 0px !important;" id="vendorCategory_tbl_td_0_' + accountCodeAddIndx + '" align="left" nowrap="nowrap">' +
	'<input type="text" placeholder="XXX-XX-XXXX.XX-XXX-XXXXX" onClick="this.select();" onblur="getAccountCodeDesc(' + accountCodeAddIndx + ')" onchange="hideDivErrorAccountCode(' + accountCodeAddIndx + ')" onkeydown="applyMask(' + accountCodeAddIndx + ')" id="_accountCd_' + accountCodeAddIndx + '"' +
	'class="form-control trvlAccountCodeAutoComplete "' +
	'focus-class="focus_text" size="23" maxlength="25" tabindex="0"/>' +
	'<div id="accountCodeEmptyError_' + accountCodeAddIndx + '" style="display:none" class="form-group has-error dateValidator01"><small ' +
	'class="help-block" role="alert" aria-atomic="true">Account Code cannot be Empty</small></div><div id="accountCodeExistsError_' + accountCodeAddIndx + '" style="display:none" class="form-group has-error dateValidator01"><small ' +
	'class="help-block" role="alert" aria-atomic="true">Account not valid for Users Travel Reimbursements</small></div></td>' +
	'<td id="ellipsisButton" style="text-align: center; vertical-align: center; padding: 0px 0px 16px 0px !important;"><a href="javascript:void(0);" tabindex="-1" id="account-code-btn' + accountCodeAddIndx + '"' +
	'data-toggle="modal" data-target="#accountCodesModal" onclick="showAccountCodeForm(' + accountCodeAddIndx + ', this)" alt="Lookup Account Code" style="color: #001F68 !important;">' +
	'<i class="fa fa-ellipsis-v btn" tabindex="0" style="background-color: transparent; color:#666666; padding: 20% 5% 15% 5%; line-height: 10px; margin-right: 15px"></i></a></td>' +
	'<td style="padding: 0px 0px 16px 0px !important;"><label class="label-color form-title" id="accountCodeDescription_0' + accountCodeAddIndx + '"></label></td>' +
	'<td style="padding: 0px 0px 16px 0px !important;"><input ' +
	'type="text" onClick="this.select();" onfocusout="updateTotals()" style="text-align:right" id="accountCodePercent_0' + accountCodeAddIndx + '"' +
	'class="form-control decimal-group-three number-seperator-three-decimal"' +
	' size="15"  onchange="hideDivErrorAccountCode(' + accountCodeAddIndx + ')" onkeypress="return checkDigit(event,this,3,3);" tabindex="0" value="0.000" /><div id="percentCodeEmptyError_' + accountCodeAddIndx + '" style="display:none" class="form-group has-error dateValidator01"><small ' +
	'class="help-block" role="alert" aria-atomic="true">Percent cannot be zero</small></div></span></td>' +
	'<td style="padding: 0px 0px 16px 0px !important;"><input type="text" onClick="this.select();" onkeypress="return checkDigit(event,this,4,2);" style="text-align:right" oninput="validate(this)" tabindex="0" size="15"' +
	'id="accountCodeAmount_0' + accountCodeAddIndx + '" tabindex="0" size="15" class="form-control decimal-group-two number-seperator-two-decimal"' +
	'focus-class="focus_text"  onchange="hideDivErrorAccountCode(' + accountCodeAddIndx + ')" value="0.00" onfocusout="updateAmtTotals()" /><div id="amountCodeEmptyError_' + accountCodeAddIndx + '" style="display:none" class="form-group has-error dateValidator01"><small ' +
	'class="help-block" role="alert" aria-atomic="true">Amount cannot be zero</small></div></td></tr>';
	$('#accountCodeBody').append(gridData);
	initialAutoCompleteList();
}

function getAccountCodeDesc(id) {
	var accountCode = $('#_accountCd_' + id).val();
	var payload = {
		accountCode : accountCode
	}
	$.ajax({
		type : 'post',
		url : urlMain + "/travelRequest/getAccountCodeDesc",
		contentType : 'application/json;charset=utf-8',
		dataType : 'json',
		async : false,
		data : JSON.stringify(payload),
		success : function(res) {
			data = res.result;
			if (data) {
				$('#accountCodeDescription_0' + id).text(data);
			} else {
				$('#accountCodeDescription_0' + id).text("");
			}
		},
		error : function(res) {
			console.log(res);
		}
	});
}

var value;
function applyMask(accountCodeAddIndx, e) {
	var id = "#_accountCd_" + accountCodeAddIndx;
	$(id).keydown(function(e) {
		var key = e.charCode || e.keyCode || 0;
		var id = "#_accountCd_" + accountCodeAddIndx;
		var code = $(id).val();
		if (key !== 8 && key !== 9) {
			if (code.match(/^\d{3}$/) !== null) {
				value = code + '-';
			} else if (code.match(/^\d{3}\-\d{2}$/) !== null) {
				value = code + '-';
			} else if (code.match(/^\d{3}\-\d{2}-\d{4}$/) !== null) {
				value = code + '.';
			} else if (code.match(/^\d{3}\-\d{2}-\d{4}.\d{2}$/) !== null) {
				value = code + '-';
			} else if (code.match(/^\d{3}\-\d{2}-\d{4}.\d{2}-\d{3}$/) !== null) {
				value = code + '-';
			} else {
				value = code;
			}
			$(id).val(value);
		}
	})
}
var deleteAccCode = 0;
var onlyOneRowExistDeleted = false;
var deletedRowId;
function deleteAccountItem(id) {
	var elementtoremove = [];
	$("#_accountCode_tbl_tr_" + id).addClass("deleteElement");
	for (var i = 1; i <= accountCodeAddIndx + deleteAccCode; i++) {
		if ($("#_accountCode_tbl_tr_" + i).length > 0) {
			var classList = $("#_accountCode_tbl_tr_" + i).attr("class").split(/\s+/);
			if (classList !== undefined) {
				for (var j = 0; j < classList.length; j++) {
					if (classList[j] === 'deleteElement') {
						elementtoremove.push("#_accountCode_tbl_tr_" + i);
						var accPercent = Number($("#accountCodePercent_0" + i).val(0));
						var amountCd = Number(numberWithoutCommas($("#accountCodeAmount_0" + i).val(0)));
						updateTotals();
						updateAmtTotals();
					}
				}
			}
		}
	}
	if (elementtoremove.length > 0) {
		var rowCount = checkOnlyOneRow();
			for (var i = 0; i < elementtoremove.length; i++) {
				$(elementtoremove[i]).remove();
				if(rowCount == true) {
					$("#accountCodeAdd").click();
					var id = localStorage.getItem("blockId");
					onlyOneRowExistDeleted = true;
					deletedRowId = id;
			}
		}
	}
	updateAmtTotals();
	deleteAccCode++;
}
function showAccountCodeForm(id) {

	// Display search Modal
	$("#accountCodesModal").modal('show');
	
	// Clear Search Values
	clearFilters();

	// Clear result limits text (if any): Query returned more than 1,000 results. Please limit the search with the filters above.
	$('#resultLimit').text('');

	// Set focus to Fund field
	setTimeout(function(){ $('#accountCodeSearch_0').focus(); }, 200);

	accountsCodeId = id;
	$('#accountCodeInput').prop('style', 'display: none');
	if (!accountCodesLoaded) {
		openAccountCodesLookup();
	}
}
var fundsTable = '';
function showFundsForm(type) {
	$("#fundsModal").modal('show');
	fillFundsTable(type);
	//$("#fundsTable_filter").prop('style', 'margin: 0% 25% 0% 0%;');
	//$('#accountCodeInput thead tr th:eq(0)').prop('style', 'width: 200px;');
}
function fillFundsTable(type) {
	var searchType;
	var modelHeader = '';
	switch (type) {
	case 0:
		searchType = 'fund';
		modelHeader = 'Fund';
		break;
	case 1:
		searchType = 'function';
		modelHeader = 'Function';
		break;
	case 2:
		searchType = 'object';
		modelHeader = 'Object';
		break;
	case 3:
		searchType = 'subobject';
		modelHeader = 'Subobject';
		break;
	case 4:
		searchType = 'organization';
		modelHeader = 'Organization';
		break;
	case 6:
		searchType = 'program';
		modelHeader = 'Program';
		break;
	case 7:
		searchType = 'education';
		modelHeader = 'Education';
		break;
	case 8:
		searchType = 'project';
		modelHeader = 'Project';
		break;
	default:
	}

	// Set the Modal Title
	$('#fundModalTitle').html(modelHeader);

	var data;
	$.ajax({
		type : 'GET',
		url : '/' + ctx + '/fundRequest/funds?type=' + searchType,
		dataType : 'JSON',
		async : false,
		contentType : 'application/json;charset=UTF-8',
		success : function(res) {
			data = res;
		},
		error : function(res) {
			data = res;
		}
	});

	// Distroy table so that new data can be loaded.
	if (fundsTable != '') {
		fundsTable.clear().draw();
		fundsTable.destroy();
	}

	fundsTable = $('#fundsTable').DataTable({
		data : data,
		"searching" : true,
		"info" : false,
		"paging" : false,
		responsive : true,
		destroy : true,
		orderCellsTop : true,
		fixedHeader : false,
		initComplete : function() {
			$('.dataTables_filter input[type="search"]').css({
				'width' : '300px',
				'display' : 'inline-block'
			});
			
			setTimeout(function(){ $('.dataTables_filter input[type="search"]').focus(); }, 200);
		},
		columns : [
			{
				data : 'fund',
				"render" : function(rowData, tableType, row) {
					var displayData = rowData;
					if ('fund' === searchType) {
						displayData = rowData + '/' + row.fiscalYr;
					}
					return '<a href="#" style="color: #001F68 !important; text-decoration: underline"><div style="text-align: center;" onclick="setFunction(\'' + row.fund + '\', ' + row.fiscalYr + ', ' + type + ')">' + displayData + '</div></a>';
				}
			},
			{
				data : "description"
			}
		]
	});
}
function setFunction(val, year, type) {
	if (accountCodesTable == '') {
		accountCodesLookup();
	}
	if (type == 0) {
		$('#accountCodeSearch_' + type).val(val);
		$('#accountCodeSearch_5').val(year);
		accountCodesTable
			.column(type)
			.draw();
		accountCodesTable
			.column(5)
			.search(year)
			.draw();
	} else {
		$('#accountCodeSearch_' + type).val(val);
		accountCodesTable
			.column(type)
			.search(val)
			.draw();
	}
	$("#fundsModal").modal('hide');
}
var accountsCodeId;
var accountCodesTable = '';
var accountCode;
var accountCodesDesc;
var accountCodesLoaded = false;
var maxlength = 0;
function openAccountCodesLookup() {
	$('#accountCodeInput thead tr').clone(true).appendTo('#accountCodeSearch thead');
	$('#accountCodeSearch thead tr:eq(1) th').each(function(i) {
		var placeHolderX = 'X';
		if (i !== 5 && i !== 9 && i !== 10 && i !== 11) {
			var placeHolderXValue = placeHolderX;
			var searchInputsize = 45;
			var marginLeft = 55;
			if (i == 0 || i == 4) {
				searchInputsize = 65;
				marginLeft = 0;
				placeHolderXValue = placeHolderX + placeHolderX + placeHolderX;
				maxlength = 3;
			}
			else if (i == 2) {
				searchInputsize = 65;
				marginLeft = 0;
				placeHolderXValue = placeHolderX + placeHolderX + placeHolderX + placeHolderX;
				maxlength = 4;
			}
			else if (i == 1 || i == 3 || i == 6 || i == 8) {
				searchInputsize = 65;
				marginLeft = 0;
				placeHolderXValue = placeHolderX + placeHolderX;
				maxlength = 2;
			}
			else if (i == 7) {
				searchInputsize = 65;
				marginLeft = 0;
				placeHolderXValue = placeHolderX;
				maxlength = 1;
			}

			$(this).html('<div style="width:90px;"><input type="text" placeholder="' + placeHolderXValue + '" advanceField="accountCodeSearch_' + (i + 1) + '" class="form-control-custom mr-sm-2 form-rounded border-secondary float-left"' + 'maxlength="' + maxlength + '"' +
				' id="accountCodeSearch_' + i + '" style="width:' + searchInputsize + 'px; text-align: center; text-transform: uppercase;" onclick="this.select();" onKeyUp="autoAdvance(event, this); checkEnterKey(event);" /><a href="javascript:void(0);" onclick="showFundsForm(' + i + ')"' +
				' style="color: #001F68 !important; margin-left:' + marginLeft + 'px"><i class="fa fa-ellipsis-v btn" style="background-color: transparent; color:#666666; padding: 18% 1% 10% 1%; line-height: 2px;"></i></a></div>');
		}

		// Fiscal Year
		else if (i == 5) {
			searchInputsize = 65;
			marginLeft = 0;
			maxlength = 1; 
			$(this).html('<div style="width:90px;"><input type="text" placeholder="' + placeHolderX + '" advanceField="accountCodeSearch_' + (i + 1) + '" class="form-control-custom mr-sm-2 form-rounded border-secondary float-left"' + 'maxlength="' + maxlength + '"' +
					' id="accountCodeSearch_' + i + '" style="width:' + searchInputsize + 'px; text-align: center; text-transform: uppercase;" onclick="this.select();" onKeyUp="autoAdvance(event, this); checkEnterKey(event);" /></div>');
		}
		
		// Search Button
		else if (i == 9) {
			$(this).html('<div class="float-left"><input type="button" class="btn btn-primary" id="searchFilters" onclick="searchFilters()" value="Search" /></div>' +
						 '<div class="float-right"><input type="button" class="btn btn-primary" id="clearFilters" onclick="clearFilters()" value="Clear" /></div>');
		}

		else if (i == 11) {
			$('#accountCodeSearch thead tr:eq(1) th:nth-child(11)').hide();
		}
	});
	accountCodesLoaded = true;
}

function checkEnterKey(e) {
	var enterKey = 13;

	var keyCode = e.keyCode || e.which;
	if (keyCode === enterKey) {
		$('#searchFilters').trigger('click');
	}
};

function autoAdvance(e, current) {
	var tabKey = 9;
	var enterKey = 13;
	var shiftKey = 16;

	var keyCode = e.keyCode || e.which;
	if (keyCode !== tabKey && keyCode !== shiftKey && keyCode !== enterKey) { 
		if (current.value.length >= current.maxLength) {
			var advanceField = $(current).attr("advanceField");
			if (advanceField !== undefined) {
				$('#' + advanceField).focus();
				$('#' + advanceField).select();
			}
		}
	}
};

function searchFilters() {
	accountCodesLookup();
	$.fn.dataTable.ext.errMode = 'none';
	enableSearch();
	performSearch();
};

function clearFilters() {
	for (var i = 0; i < 9; i++) {
		$('#accountCodeSearch_' + i).val('');
	}
};

function enableSearch() {
	$('#accountCodeSearch thead tr:eq(1) th').each(function(i) {
		if (i != 9 && i != 10 && i != 11) {
			$('input', this).on('keyup change', function() {
				if (accountCodesTable.column(i).search() !== this.value.trim().toUpperCase()) {
					var val = $.fn.dataTable.util.escapeRegex($(this).val().trim().toUpperCase());
					if (val != undefined && !val.includes('X') && !val.includes('x')) {
						accountCodesTable
							.column(i)
							.search(this.value.trim().toUpperCase())
							.draw();
					} else if (val != undefined) {
						var val = $.fn.dataTable.util.escapeRegex(this.value.trim().toUpperCase());
						var regSearchVal = wildReplace(val);
						accountCodesTable
							.column(i)
							.search(val ? regSearchVal : '', true, false)
							.draw();
					}
				}
			});
		}
	});
};

function performSearch() {
	$('#accountCodeInput thead tr th:eq(0)').prop('style', 'width: 180px; text-align: left;');
	$('#accountCodeInput thead tr th:eq(1)').prop('style', 'width: 250px; text-align: left;');
	for (var i = 0; i < 11; i++) {
		var searchVal = $('#accountCodeSearch_' + i).val();
		searchVal !== undefined ? searchVal.trim().toUpperCase() : searchVal;
		if (searchVal != undefined && !searchVal.includes('x') && !searchVal.includes('X')) {
			$('#accountCodeSearch_' + i).change();
		}
		else if (searchVal != undefined) {
			var val = $.fn.dataTable.util.escapeRegex(searchVal);
			var regSearchVal = wildReplace(val);
			accountCodesTable.columns($('#accountCodeSearch_' + i)).search(val ? regSearchVal : '', true, false).draw();
		}
	}
	computeResults();
};

function wildReplace(val) {
	const searchx = 'x';
	const replaceWithX = 'X';
	const resultX = val.split(searchx).join(replaceWithX);
	const search = 'X';
	const replaceWith = '.*';
	const result = resultX.split(search).join(replaceWith);
	return '^' + result;
};

$(document).ready(function() {
	$(".dataTables_filter").css({
		'float' : 'inherit !important',
		'text-align' : 'inherit !important'
	})
	if($('#totalMiles').text() == ''){
		$('#totalMiles').text('0.0');
	}
	if($('#totalMisc').text() == ''){
		$('#totalMisc').text('0.00');
	}
	if($('#totalRequest').text() == ''){
		$('#totalRequest').text('0.00');
	}
});

function accountCodesLookup() {
	var data;
	$.ajax({
		type : 'GET',
		url : '/' + ctx + '/travelRequest/getAccountCodes',
		dataType : 'JSON',
		async : false,
		contentType : 'application/json;charset=UTF-8',
		success : function(res) {
			data = res;
		},
		error : function(res) {
			console.log(res);
		}
	});
	$('#accountCodeInput tbody').on('click', 'tr', function() {
		var accountCode = accountCodesTable.row(this).data().c_account_code;
		$('#_accountCd_' + accountsCodeId).val(accountCode);
		
		var accountCodesDesc = accountCodesTable.row(this).data().descr;
		$('#accountCodeDescription_0' + accountsCodeId).text(accountCodesDesc);

		$("#accountCodesModal").modal('hide');
	});

	// Distroy table so that new data can be loaded.
	if (accountCodesTable !== '') {
		accountCodesTable.clear().draw();
		accountCodesTable.destroy();
	}

	accountCodesTable = $('#accountCodeInput').DataTable({
		data : data.result,
		"searching" : true,
		"info" : false,
		"paging" : false,
		"lengthMenu" : [ 5, 10, 25, 50, 75, 100 ],
		orderCellsTop : true,
		fixedHeader : true,
		initComplete : function() {
			$('.dataTables_filter input[type="search"]').css({
				'width' : '    width: 82%',
				'margin-top' : '11px;',
				'margin-bottom' : '11px'
			});


		},
		"columnDefs" : [
			{
				"targets" : [ 0 ],
				"visible" : false
			}, {
				"targets" : [ 1 ],
				"visible" : false
			},
			{
				"targets" : [ 2 ],
				"visible" : false
			},
			{
				"targets" : [ 3 ],
				"visible" : false
			},
			{
				"targets" : [ 4 ],
				"visible" : false
			},
			{
				"targets" : [ 5 ],
				"visible" : false
			},
			{
				"targets" : [ 6 ],
				"visible" : false
			},
			{
				"targets" : [ 7 ],
				"visible" : false
			},
			{
				"targets" : [ 8 ],
				"visible" : false
			},
			{
				"targets" : [ 9 ],
				"width" : "30%"
			}
		],
		columns : [
			{
				data : "fund"
			},
			{
				data : "func"
			},
			{
				data : "obj"
			},
			{
				data : "sobj"
			},
			{
				data : "org"
			},
			{
				data : "fscl_yr"
			},
			{
				data : "pgm"
			},
			{
				data : "ed_span"
			},
			{
				data : "proj_dtl"
			},
			{
				data : "c_account_code"
			},
			{
				data : "descr"
			}
		]
	});
	$('#accountCodeSearch thead tr:eq(1) th:gt(9)').hide();
	$("#accountCodeInput_filter").prop('style', 'display: inline-flex;margin-right: 289px;margin-top: 12px;');
	$("#accountCodeInput_filter").hide();
	accountCodesTable.on('search.dt', function() {
		computeResults();
	});

	// ReSet the Search Columns that have been entered.
	for (var i = 0; i < 9; i++) {
		$('#accountCodeSearch_' + i).change();
	}
};

function computeResults() {
	var count = 0;
	accountCodesTable.rows({
		"search" : "applied"
	}).every(function() {
		count++;
	});
	if (count > 1000) {
		$('#accountCodeInput').prop('style', 'display: none');
		$('#resultLimit').text('Query returned more than 1,000 results. Please limit the search with the filters above.');
	} else {
		$('#accountCodeInput').prop('style', 'display: block');
		$('#resultLimit').text('');
	}
}
function validateFields(id, countRow) {
	var fields = [];
	if (id == 'submit') {
		fields.push('accountCodeTot');
	}
	var isValid = true;
	var invalid = []; 
	if (countRow == undefined) {
		fields.push('startDate');
		fields.push('contact');
		fields.push('purpose'); 
		if (bfnOptionsTravel[0] == 'Y') {
			fields.push('startTime');
			fields.push('endTime');
		}
		if (bfnOptionsTravel[1] == 'Y' && overNightData === false) {
			fields.push('mileageBegining');
			fields.push('mileageEnd');
		}
		if (bfnOptionsTravel[1] == 'N' && overNightData === false) {
			fields.push('mileageBegining');
			fields.push('mileageEnd');
			fields.push('map');
		}
		if (bfnOptionsTravel[3] == 'N') {
			fields.push('addressFromLine1');
			fields.push('addressToLine1');
		}
		for (var i = 0; i <= countRows; i++) {
			if (overNightData == true && $('#mealOverride_' + i).is(':checked')){
				fields.push('overrideReason');
			}
			if($('#other_' + i).val() == undefined && $('#miscAmt_' + i).val() > 0){
				fields.push('otherReason');
			}
			else if($('#miscAmt_' + i).val() == undefined && $('#other_' + i).val() > 0){
				fields.push('otherReason');
			}
			invalid[i] = validateField(fields, i);
		}
	} else {
		var fieldValue = $("#" + id + "_" + countRows).val();
		if (fieldValue == '') {
			invalid[0] = false;
		}
	}
	for (var i = 0; i < invalid.length; i++) {
		if (invalid[i] == false) {
			isValid = false;
			break;
		}
	}
	return isValid;
}
function validateField(fields, countRows) {
	var isValid = true;
	var isMileageBegining = true;
	var isMileageEnd = true;
	var isMap = true;
	for (var i = 0; i < fields.length; i++) {
		var fieldValue = $("#" + fields[i] + "_" + countRows).val();
		if (fields[i].includes('State')) {
			fieldValue = $("#" + fields[i] + "_" + countRows + " :selected").val();
		}
		if ("accountCodeTot" == fields[i]) {
			fieldValue = $("#" + fields[i] + "_" + countRows).text();
			if (fieldValue == '' && fieldValue == undefined && fieldValue == null) {
				isValid = false;
			}
		}
		if (bfnOptionsTravel[1] == 'N') {
			if ("mileageBegining" == fields[i] && fieldValue == '') {
				isMileageBegining = false;
			}
			if ("mileageEnd" == fields[i] && fieldValue == '') {
				isMileageEnd = false;
			}
			if ("map" == fields[i] && fieldValue == '') {
				if (!isMileageBegining && !isMileageEnd) {
					$("#mileageBeginingError_" + countRows).show();
					$("#mileageEndError_" + countRows).show();
					$("#mapError_" + countRows).show();
					isValid = false;
				} else if (!isMileageBegining && isMileageEnd) {
					$("#mileageBeginingError_" + countRows).show();
					$("#mileageEndError_" + countRows).hide();
					$("#mapError_" + countRows).hide();
					isValid = false;
				} else if (isMileageBegining && !isMileageEnd) {
					$("#mileageBeginingError_" + countRows).hide();
					$("#mileageEndError_" + countRows).show();
					$("#mapError_" + countRows).hide();
					isValid = false;
				} else if (isMileageBegining && isMileageEnd) {
					$("#mileageBeginingError_" + countRows).hide();
					$("#mileageEndError_" + countRows).hide();
					$("#mapError_" + countRows).hide();
				}
				isMileageBegining = true;
				isMileageEnd = true;
				isMap = true;
			}
			if ("map" == fields[i] && fieldValue != '') {
				$("#mileageBeginingError_" + countRows).hide();
				$("#mileageEndError_" + countRows).hide();
				$("#mapError_" + countRows).hide();
			}
			if ("mileageBegining" != fields[i] && "mileageEnd" != fields[i] && "map" != fields[i] && "otherReason" != fields[i] && "overrideReason" != fields[i] ) {
				if (fieldValue == '') {
					$("#" + fields[i] + "Error_" + countRows).show();
					isValid = false;
				} else {
					$("#" + fields[i] + "Error_" + countRows).hide();
				}
			}
		}
		if (bfnOptionsTravel[1] == 'Y') {
			if (fieldValue == '') {
				$("#" + fields[i] + "Error_" + countRows).show();
				isValid = false;
			} else {
				$("#" + fields[i] + "Error_" + countRows).hide();
			}
		}
		
		if ("overrideReason" == fields[i]) {
			if ($('#mealOverride_' + countRows).is(':checked') &&  fieldValue == '') {
				$("#" + fields[i] + "Error_" + countRows).show();
				isValid = false;
			}
			else{
				$("#" + fields[i] + "Error_" + countRows).hide();
			}
		}
		if ("otherReason" == fields[i]) {
			if ($('#other_' + countRows).val() > 0  && fieldValue == '') {
				$("#" + fields[i] + "Error_" + countRows).show();
				isValid = false;
			}
			else if ($('#miscAmt_' + countRows).val() > 0 && fieldValue == '') {
				$("#" + fields[i] + "Error_" + countRows).show();
				isValid = false;
			}
			else{
				$("#" + fields[i] + "Error_" + countRows).hide();
			}
		}
		
	}
	return isValid;
}

function validateAccomodation(fieldId, countRows) {
	var isValid = true;
	console.log('fieldId:: ', fieldId);
	var fieldValue = $("#" + fieldId + "_" + countRows).val();
	console.log('fieldValue:: ', fieldValue);
	if ("fromZip" == fieldId && fieldValue == '') {
		$("#fromZipError_" + countRows).hide();
	}
	if ("toZip" == fieldId && fieldValue == '') {
		$("#toZipError_" + countRows).hide();
	}
	if ("fromZip" == fieldId && fieldValue !== '' && fieldValue !== undefined) {
		var isValidZip = /(^\d{5}$)|(^\d{5}-\d{4}$)/.test(fieldValue);
		if (fieldValue != undefined && fieldValue != '' && !isValidZip) {
			console.log(' inside error fieldValue:: ', fieldValue);
			$("#fromZipError_" + countRows).show();
			isValid = false;
		} else {
			$("#fromZipError_" + countRows).hide();
		}
	}
	if ("toZip" == fieldId && fieldValue !== '' && fieldValue !== undefined) {
		var isValidZip = /(^\d{5}$)|(^\d{5}-\d{4}$)/.test(fieldValue);
		if (fieldValue != undefined && fieldValue != '' && !isValidZip) {
			console.log(' inside error fieldValue:: ', fieldValue);
			$("#toZipError_" + countRows).show();
			isValid = false;
		} else {
			$("#toZipError_" + countRows).hide();
		}
	}
	return isValid;
}

function zipChange(e, value, fieldId, countRows) {
	if(e.keyCode == 8)
    {
        if(value.length == 6){
        var newText = value.substring(0 , 5);
		$("#" + fieldId + "_" + countRows).val(newText);
      }
    }
    else if(value.length == 6) {
        var newText = value.substring(0 , 5) + '-' + value.substring(5);
       $("#" + fieldId + "_" + countRows).val(newText);
    }
}

function validateZipField(fieldId, countRows) {
	var isValid = true;
	console.log('fieldId:: ', fieldId);
	var fieldValue = $("#" + fieldId + "_" + countRows).val();
	console.log('fieldValue:: ', fieldValue);
	if ("fromZip" == fieldId && fieldValue == '') {
		$("#fromZipError_" + countRows).hide();
	}
	if ("toZip" == fieldId && fieldValue == '') {
		$("#toZipError_" + countRows).hide();
	}
	if ("fromZip" == fieldId && fieldValue !== '' && fieldValue !== undefined) {
		var isValidZip = /(^\d{5}$)|(^\d{5}-\d{4}$)/.test(fieldValue);
		if (fieldValue != undefined && fieldValue != '' && !isValidZip) {
			console.log(' inside error fieldValue:: ', fieldValue);
			$("#fromZipError_" + countRows).show();
			isValid = false;
		} else {
			$("#fromZipError_" + countRows).hide();
		}
	}
	if ("toZip" == fieldId && fieldValue !== '' && fieldValue !== undefined) {
		var isValidZip = /(^\d{5}$)|(^\d{5}-\d{4}$)/.test(fieldValue);
		if (fieldValue != undefined && fieldValue != '' && !isValidZip) {
			console.log(' inside error fieldValue:: ', fieldValue);
			$("#toZipError_" + countRows).show();
			isValid = false;
		} else {
			$("#toZipError_" + countRows).hide();
		}
	}
	return isValid;
}

var dindx = 0;

function loadExtendedRows() {
	createExtendedGrid();
	createDetailsGrid();
	if (tripData != null && tripData != ''
		&& tripData != undefined && tripData != 'undefined') {
		var tripValue = tripData[0];
		if (tripValue != null && tripValue != ''
			&& tripValue != undefined && tripValue != 'undefined') {
			$(".dateValidator01").hide();
			var travelDataArray = JSON.stringify(travelDataList);
			var startDate;
			var endDate;
			var endTime;
			if (travelDataArray != null) {
				var travelData = JSON.parse(travelDataArray);
				if (accountsCodeList !== undefined && accountsCodeList.length !== 0) {
					assignedAccountCodes = accountsCodeList;

				}
				tripNbr = travelData[0].tripNbr;
				var entryDate = travelData[0].entryDt;
				var trvlStatus = travelData[0].trvlReqStat;
				if (trvlStatus === 'S') {
					statusText = 'Saved'
				}
				if (trvlStatus === 'P') {
					statusText = 'Pending'
				}
				if (trvlStatus === 'A') {
					statusText = 'Approved'
				}
				if (trvlStatus === 'R') {
					statusText = 'Returned'
				}
				if (trvlStatus === 'A' || trvlStatus === 'P') {
					$('#documentUpload').removeAttr('disabled');
					$('#documentUpload').css({'display': 'block', 'width':'125px'});
					$('#documentUpload').click(function() {
						showDocumentModal('001', 'TRAVEL DOCUMENTS', 'TRAVEL', 'Y');
					});
					disableFieldsforView(indx);
				} else {
					$('#documentUpload').removeAttr('disabled');
					$('#documentUpload').css({'display': 'block', 'width':'125px'});
					$('#documentUpload').click(function() {
						showDocumentModal('001', 'TRAVEL DOCUMENTS', 'TRAVEL', 'N');
					});
					addEditAssignAccountCodeForm(indx, 'Y');
				}
				checkDocuments();
				$('#travelNbr').text(tripNbr);
				$('#travelStatus').text(statusText);
				if (tripNbr != null) {
					if (assignedAccountCodes != null && assignedAccountCodes.length > 0) {
						sumSameAccountSummary();
					}
					displayExtendedValues(0);
				}
				if (entryDate !== null && entryDate !== '' && entryDate !== undefined) {
					var dateEntered = moment(entryDate).format('MM-DD-YYYY');
					$('#dateEntered').text(dateEntered);
				}
				$('#startDate_0').text(startDate);
				var startTimeValue = startTime;
				var endTimeValue = endTime;
				if (startTimeValue !== undefined && startTimeValue !== null && startTimeValue !== '') {
					var startT = get24HrsFrmAMPM(startTimeValue);
				}
				if (endTimeValue !== undefined && endTimeValue !== null && endTimeValue !== '') {
					var endT = get24HrsFrmAMPM(endTimeValue);
				}
				$('#startDate_0').val(startDate);
				$('#startTime_0').val(startT);
				$('#endTime_0').val(endT);
				$('#depart_0').html('&nbsp;&nbsp;&nbsp;&nbsp;' + startDate + '<br>&nbsp;&nbsp;&nbsp;&nbsp;' + startTime);
				$('#return_0').html('&nbsp;&nbsp;&nbsp;&nbsp;' + endDate + '<br>&nbsp;&nbsp;&nbsp;&nbsp;' + endTime);
				var diff = dateDiff(startDate.replaceAll('-', '\/'), endDate.replaceAll('-', '\/'));
				var firstDate = moment(startDate, "MM-DD-YYYY");
				var nextDate = firstDate;
				var multigrid = false;
				var datestring = startDate;
				if (bfnOptionsTravel[1] == 'Y') {
					disableMapField(0);
				}
				if (bfnOptionsTravel[3] == 'Y') {
					disableAddressFields(0);
				}
				indx = 1;
				for (var i = 0; i < diff; i++) {
					multigrid = true;
					indx++;
					dindx++;
					datestring = moment(datestring, "MM-DD-YYYY").add(1, 'days').format('MM-DD-YYYY');
					createDetailsGrid();
					$('#copyFromPrevious_' + dindx).html('<div><div style="margin-right:40px; text-align:center;" class="borderCopyFromPrev"><label>Copy<br>from<br>Previous<br>day</label><br><input id="copyFrom_' + dindx + '" type="checkbox" onclick="copyFrom(' + dindx + ', this)" /></div><div>');
					$('#startDate_' + dindx).text(datestring);
					$('#startDate_' + dindx).val(datestring);
					$('#startTime_' + dindx).val(startT);
					$('#endTime_' + dindx).val(endT);
					displayExtendedValues(dindx);
					if (!multigrid) {
						createDetailsGrid();
					}
					$('#return_0').html('&nbsp;&nbsp;&nbsp;&nbsp;' + datestring + '<br>&nbsp;&nbsp;&nbsp;&nbsp;' + endTime);
					$(".dateValidator01").hide();
				}
			}
		}
		} else {
		var travelRequestsData = $('#travelRequestsData').val();
		var travelRequestsValue = JSON.parse(travelRequestsData.replaceAll('\'', '\"'));
		var startDate = travelRequestsValue.fromDate;
		$('#startDate_0').text(travelRequestsValue.fromDate);
		entryDate = travelRequestsValue.fromDate;
		var startTimeValue = travelRequestsValue.startTimeValueTravel;
		var endTimeValue = travelRequestsValue.endTimeValueTravel;
		if (startTimeValue !== undefined && startTimeValue !== null && startTimeValue !== '') {
			var startT = startTimeValue;
		}
		if (endTimeValue !== undefined && endTimeValue !== null && endTimeValue !== '') {
			var endT = endTimeValue;
		}
		$('#startDate_0').val(entryDate);
		$('#startTime_0').val(startT);
		$('#endTime_0').val(endT);

		$('#depart_0').html('&nbsp;&nbsp;&nbsp;&nbsp;' + travelRequestsValue.fromDate + '<br>&nbsp;&nbsp;&nbsp;&nbsp;' + timeTo12HrFormatAMPM(travelRequestsValue.startTimeValueTravel));
		var diff = dateDiff(travelRequestsValue.fromDate.replaceAll('-', '\/'), travelRequestsValue.toDate.replaceAll('-', '\/'));
		var firstDate = moment(startDate, "MM-DD-YYYY");
		var nextDate = firstDate;
		var multigrid = false;
		var datestring;
		if (bfnOptionsTravel[1] == 'Y') {
			disableMapField(0);
		}
		if (bfnOptionsTravel[3] == 'Y') {
			disableAddressFields(0);
		}
		indx = 1;
		for (var i = 0; i < diff; i++) {
			multigrid = true;
			indx++;
			dindx++;
			datestring = moment(nextDate, "MM-DD-YYYY").add(1, 'days').format('MM-DD-YYYY');
			createDetailsGrid();
			$('#copyFromPrevious_' + dindx).html('<div><div style="margin-right:40px; text-align:center;" class="borderCopyFromPrev"><label>Copy<br>from<br>Previous<br>day</label><br><input type="checkbox" onclick="copyFrom(' + dindx + ', this)" /></div><div>');
			$('#startDate_' + dindx).text(datestring);
			$('#startDate_' + dindx).val(datestring);
			$('#startTime_' + dindx).val(startT);
			$('#endTime_' + dindx).val(endT);
			if (bfnOptionsTravel[1] == 'Y') {
				disableMapField(dindx);
			}
			var nextDate = datestring;
		}
		if (!multigrid) {
			createDetailsGrid();
		}
		$('#return_0').html('&nbsp;&nbsp;&nbsp;&nbsp;' + datestring + '<br>&nbsp;&nbsp;&nbsp;&nbsp;' + timeTo12HrFormatAMPM(travelRequestsValue.endTimeValueTravel));
		$(".dateValidator01").hide();
	}

function displayExtendedValues(i) {
		var indxNbr = travelData[i].tripSeqNbr;
		indx = indxNbr - 1;
		startDate = changeDateFormatMMDDYYYY(travelData[0].trvlDt);
		endDate = changeDateFormatMMDDYYYY(travelData[travelData.length - 1].trvlDt);
		var fromTmAp = '';
		var toTmAp = '';
		if (travelData[i].fromTmAp !== null && travelData[i].fromTmAp !== undefined && travelData[i].fromTmAp !== '') {
			if (travelData[i].fromTmAp === 'A') {
				fromTmAp = 'AM';
			}
			if (travelData[i].fromTmAp === 'P') {
				fromTmAp = 'PM';
			}
		}
		if (travelData[i].toTmAp !== null && travelData[i].toTmAp !== undefined && travelData[i].toTmAp !== '') {
			if (travelData[i].toTmAp === 'A') {
				toTmAp = 'AM';
			}
			if (travelData[i].toTmAp === 'P') {
				toTmAp = 'PM';
			}
		}
		if ((travelData[i].fromTmHr !== null && travelData[i].fromTmHr !== undefined && travelData[i].fromTmHr !== '') &&
			(travelData[i].fromTmMin !== null && travelData[i].fromTmMin !== undefined && travelData[i].fromTmMin !== '') &&
			(fromTmAp !== null && fromTmAp !== undefined && fromTmAp !== '')) {
			if ((travelData[i].fromTmHr.toString()).length == 1) {
				travelData[i].fromTmHr = '0' + travelData[i].fromTmHr;
			}
			if ((travelData[i].fromTmMin.toString()).length == 1) {
				travelData[i].fromTmMin = '0' + travelData[i].fromTmMin;
			}
			startTime = travelData[i].fromTmHr + ':' + travelData[i].fromTmMin + ' ' + fromTmAp;
		}
		if ((travelData[i].toTmHr !== null && travelData[i].toTmHr !== undefined && travelData[i].toTmHr !== '') &&
			(travelData[i].toTmMin !== null && travelData[i].toTmMin !== undefined && travelData[i].toTmMin !== '') &&
			(toTmAp !== null && toTmAp !== undefined && toTmAp !== '')) {
			if ((travelData[i].toTmHr.toString()).length == 1) {
				travelData[i].toTmHr = '0' + travelData[i].toTmHr;
			}
			if ((travelData[i].toTmMin.toString()).length == 1) {
				travelData[i].toTmMin = '0' + travelData[i].toTmMin;
			}
			endTime = travelData[i].toTmHr + ':' + travelData[i].toTmMin + ' ' + toTmAp;
		}
		var contact = travelData[i].contact;
		var purpose = travelData[i].purpose;
		var addressFromLine1 = travelData[i].origLocId;
		var addressFromLine2 = travelData[i].origLocAddr;
		var fromState = travelData[i].origLocSt;
		var fromCity = travelData[i].origLocCity;
		var fromZip = travelData[i].origLocZip;
		var addressToLine1 = travelData[i].descLocId;
		var addressToLine2 = travelData[i].descLocAddr;
		var toState = travelData[i].descLocSt;
		var toCity = travelData[i].descLocCity;
		var toZip = travelData[i].descLocZip;
		var isRoundTrip = travelData[i].roundTrip;
		var isCommute = travelData[i].useEmpTrvlCommuteDist;
		var mileageBegining = numberWithCommas(travelData[i].begOdometer);
		var mileageEnd = numberWithCommas(travelData[i].endOdometer);
		var map = numberWithCommas(travelData[i].mapMiles);
		var miscAmt = numberWithCommas(travelData[i].miscAmt);
		var trvlReqStat = travelData[i].trvlReqStat;
		var breakfastAmt = numberWithCommas(travelData[i].brkfstAmt);
		var lunchAmt = numberWithCommas(travelData[i].lunchAmt);
		var dinnerAmt = numberWithCommas(travelData[i].dinAmt);
		var altMealAmt = travelData[i].altMealAmt;
		var altMealRsn = travelData[i].altMealRsn;
		var parkAmt = numberWithCommas(travelData[i].parkAmt);
		var taxiAmt = numberWithCommas(travelData[i].taxiAmt);
		var miscAmtRsn = travelData[i].miscAmtRsn;
		var accomAmt = numberWithCommas(travelData[i].accomAmt);
		var accomDesc = travelData[i].accomDesc;
		var directBillNbr = travelData[i].directBillNbr;
		if (trvlReqStat === 'A' || trvlReqStat === 'P') {
			disableFieldsforView(indx);
		}
		$('#contact_' + i).val(contact);
		$('#purpose_' + i).val(purpose);
		$('#addressFromLine1_' + i).val(addressFromLine1);
		$('#addressFromLine2_' + i).val(addressFromLine2);
		$('#fromState_' + i).val(fromState);
		$('#fromCity_' + i).val(fromCity);
		$('#fromZip_' + i).val(fromZip);
		$('#addressToLine1_' + i).val(addressToLine1);
		$('#addressToLine2_' + i).val(addressToLine2);
		$('#toState_' + i).val(toState);
		$('#toCity_' + i).val(toCity);
		$('#toZip_' + i).val(toZip);
		if (isRoundTrip == 'Y') {
			$('#isRoundTrip_' + i).attr("checked", true);
		}
		if (isCommute == 'Y') {
			$('#isCommute_' + i).attr("checked", true);
		}
		if (altMealAmt == 'Y') {
			$('#mealOverride_' + i).attr("checked", true);
		}
		$('#mileageBegining_' + i).val(mileageBegining);
		$('#mileageEnd_' + i).val(mileageEnd);
		if (map == '0') {
			map = '';
			$('#map_' + i).val(map);
		} else {
			$('#map_' + i).val(map);
		}
		if (mileageBegining == '0') {
			mileageBegining = '';
			$('#mileageBegining_' + i).val(mileageBegining);
		} else {
			$('#mileageBegining_' + i).val(mileageBegining);
		}
		if (mileageEnd == '0') {
			mileageEnd = '';
			$('#mileageEnd_' + i).val(mileageEnd);
		} else {
			$('#mileageEnd_' + i).val(mileageEnd);
		}
		$('#breakfast_' + i).val(breakfastAmt);
		$('#lunch_' + i).val(lunchAmt);
		$('#dinner_' + i).val(dinnerAmt);
		$('#overrideReason_' + i).val(altMealRsn);
		$('#parking_' + i).val(parkAmt); 
		$('#taxi_' + i).val(taxiAmt); 
		$('#other_' + i).val(miscAmt);
		$('#otherReason_' + i).val(miscAmtRsn);
		$('#reinAmt_' + i).val(accomAmt); 
		$('#accommodations_' + i).val(accomDesc); 
		$('#directBill_' + i).val(directBillNbr);
		calculateExtendedMileage(i, trvlReqStat);
		showAccountCodeAmtExt();
		if (bfnOptionsTravel[1] == 'Y') {
			disableMapField(indx);
		}
	}
}

function showAccountCodeAmtMil(){
	var arrayAccCodes = [];
	assignedAccountCodes.forEach(function(value, i) {
		var id = assignedAccountCodes[i].id;
		var childCode = assignedAccountCodes[i].childAccountCodes;
		childCode.forEach(function(value, i) {
			var childCodeInd = splitAccountCode(childCode[i], id);
			arrayAccCodes.push(childCodeInd);
		});
	});
	if (arrayAccCodes.length > 0) {
		for (var j = 0; j <= indx + deletedRows; j++) {
			var sumAccount = 0;
			for (var k = 0; k < arrayAccCodes.length; k++) {
				if (j == Number(arrayAccCodes[k].id)) {
					sumAccount = sumAccount + Number(arrayAccCodes[k].accountAmt);
				}
			}
			if(sumAccount > 0) {
				$('#accountCodeTot_' + j).text(numberWithCommas((sumAccount).toFixed(2)));
			}
		}
	}
}

function showAccountCodeAmtExt(){
	var arrayAccCodes = [];
	assignedAccountCodes.forEach(function(value, i) {
		var id = assignedAccountCodes[i].id;
		var childCode = assignedAccountCodes[i].childAccountCodes;
		childCode.forEach(function(value, i) {
			var childCodeInd = splitAccountCode(childCode[i], id);
			arrayAccCodes.push(childCodeInd);
		});
	});
	if (arrayAccCodes.length > 0) {
		for (var j = 0; j <= dindx + deletedRows; j++) {
			var sumAccount = 0;
			for (var k = 0; k < arrayAccCodes.length; k++) {
				if (j == Number(arrayAccCodes[k].id)) {
					sumAccount = sumAccount + Number(arrayAccCodes[k].accountAmt);
				}
			}
			if(sumAccount > 0) {
				$('#accountCodeTot_' + j).text(numberWithCommas((sumAccount).toFixed(2)));
			}
		}
	}
}

function copyFrom(id, cb) {
	if (cb.checked) {
		var previousId = parseInt(id - 1);
		$('#mileageBegining_' + id).val($('#mileageBegining_' + previousId).val());
		$('#mileageEnd_' + id).val($('#mileageEnd_' + previousId).val());
		$('#map_' + id).val($('#map_' + previousId).val());
		if ($('#isRoundTrip_' + previousId).is(':checked')) {
			$('#isRoundTrip_' + id).prop('checked', true);
		}
		if ($('#isCommute_' + previousId).is(':checked')) {
			$('#isCommute_' + id).prop('checked', true);
		}
		$('#accommodations_' + id).val($('#accommodations_' + previousId).val());
		$('#directBill_' + id).val($('#directBill_' + previousId).val());
		$('#reinAmt_' + id).val($('#reinAmt_' + previousId).val());
		$('#breakfast_' + id).val($('#breakfast_' + previousId).val());
		$('#lunch_' + id).val($('#lunch_' + previousId).val());
		$('#dinner_' + id).val($('#dinner_' + previousId).val());
		if ($('#mealOverride_' + previousId).is(':checked')) {
			$('#mealOverride_' + id).prop('checked', true);
		}
		$('#overrideReason_' + id).val($('#overrideReason_' + previousId).val());
		$('#parking_' + id).val($('#parking_' + previousId).val());
		$('#taxi_' + id).val($('#taxi_' + previousId).val());
		$('#other_' + id).val($('#other_' + previousId).val());
		$('#otherReason_' + id).val($('#otherReason_' + previousId).val());
		calculateExtendedMileage(id);
	}
}

function dateDiff(from, to) {
	var date1 = new Date(from);
	var date2 = new Date(to);
	var Difference_In_Time = date2.getTime() - date1.getTime();
	return Difference_In_Time / (1000 * 3600 * 24);
}

function createDetailsGrid() {
	var indxRows = dindx;
	countRows = indxRows;
	var mileageRt = bfnOptionsTravel[2];
	if (mileageRt === null && mileageRt === undefined && mileageRt === '') {
		mileageRt = 0;
	}
	var gridData = '<tr id="ddetailRow_0' + indxRows + '"><td colspan="7"><div style="margin-left:10px;" id="mileageTotalMinusCommute_' + indxRows + '" >' + $('#mileageTotalMinusCommuteMsg').val() + '</div></td></tr><tr id="ddetailRow_1' + indxRows + '" class="deleteRow"><td><label style="margin-left:10px;" id="startDate_' + indxRows + '"></label><input type="hidden" id="startTime_' + indxRows + '"><input type="hidden" id="endTime_' + indxRows + '"><input type="hidden" id="startDate_' + indxRows + '"></td><td><div><label class="label-color float-left">' + $('#lblMilage').val() + ':&nbsp;' +
	'&nbsp;</label><input class="form-control decimal-group-one number-seperator-one-decimal"' +
	' type="text" oninput="validate(this)" onClick="this.select();" onkeypress="return checkDigit(event,this,7,1);" placeholder= "Start" id="mileageBegining_' + indxRows + '" style="width:55%"' +
	' onblur="calculateExtendedMileage(' + indxRows + ')"><div id="mileageBeginingError_' + indxRows + '" class="form-group has-error dateValidator01"><small ' +
	'class="help-block" role="alert" aria-atomic="true">' + $('#mileageStartEmptyError').val() + '</small></div></div></td><td><div style="width: 80%;" class="float-left"><input class="form-control decimal-group-one number-seperator-one-decimal"' +
	' type="text" oninput="validate(this)" onClick="this.select();" onkeypress="return checkDigit(event,this,7,1);" placeholder= "Stop" id="mileageEnd_' + indxRows + '" style="width:80%"' +
	' onblur="calculateExtendedMileage(' + indxRows + ')"><div id="mileageEndError_' + indxRows + '" class="form-group has-error dateValidator01"><small ' +
	'class="help-block" role="alert" aria-atomic="true">' + $('#mileageEndEmptyError').val() + '</small></div></div><div class="form-inline">' +
	' <label class="label-color">OR&nbsp;&nbsp;</label>' +
	' </div></td><td><div><input class="form-control decimal-group-one number-seperator-one-decimal"' +
	' type="text" oninput="validate(this)" onClick="this.select();" onkeypress="return checkDigit(event,this,3,1);" placeholder= "Map" id="map_' + indxRows + '" style="width:50%"' +
	' onblur="calculateExtendedMileage(' + indxRows + ')"><div id="mapError_' + indxRows + '" class="form-group has-error dateValidator01"><small ' +
	'class="help-block" role="alert" aria-atomic="true">' + $('#mapEmptyError').val() + '</small></div></div></td><td colspan="2"><label class="label-color float-left">' + $('#lblRoundTrip').val() + ':&nbsp;&nbsp</label>' +
	'<input class="float-left" type="checkbox" id="isRoundTrip_' + indxRows + '" onchange="calculateExtendedMileage(' + indxRows + ')">' +
	'<label class="label-color float-left">&nbsp;&nbsp;&nbsp;&nbsp;' + $('#lblCommute').val() + ':&nbsp;&nbsp;</label>' +
	'<input class="float-left" type="checkbox" id="isCommute_' + indxRows + '" onchange="calculateExtendedMileage(' + indxRows + ')">' +
	'<div><label class="label-color float-left">&nbsp;&nbsp;&nbsp;&nbsp;Tot:&nbsp;</label>' +
	'<label class="label-color float-left" id="total_' + indxRows + '"></label>&nbsp;&nbsp;&nbsp;&nbsp;</div></td><td colspan="2"><label class="label-color' +
	' float-left">&nbsp;&nbsp;&nbsp;&nbsp;' + $('#lblMilageRate').val() + ':&nbsp;</label>' +
	'<label class="label-color float-left" id="mileageRate_' + indxRows + '">' + mileageRt + '</label><label class="label-color ' +
	'float-right">' + $('#lblTotMilageAmnt').val() + ':&nbsp;&nbsp;</label></td><td><div style="margin-right:10px;" class="float-right"><label class="label-color float-right" id="totalMileageRate_' + indxRows + '">' +
	'0.00</label></div></td></tr><tr id="ddetailRow_2' + indxRows + '"><td rowspan="2" id="copyFromPrevious_' + indxRows + '"></td><td colspan="3"><label class="label-color float-left">' + $('#lblAccommodations').val() + ':&nbsp;&nbsp;</label>' +
	'<input type="text" class="form-control-custom mr-sm-2 form-rounded border-secondary" style="width:70%" onClick="this.select();" onchange="validateAccomodationField(\'accommodations\', ' + indxRows + ')"  id="accommodations_' + indxRows + '"><div id="accommodationsError_' + indxRows + '" class="form-group has-error dateValidator01"><small ' +
	'class="help-block" role="alert" aria-atomic="true">' + $('#accommodationsEmptyError').val() + '</small></div></td><td colspan="3">' +
	'<label class="label-color float-left" style="margin-right: 16px;">' + $('#lblDirectBill').val() + ':&nbsp;&nbsp;</label><input type="text"' +
	'class="form-control-custom mr-sm-2 form-rounded border-secondary" style="width:40%" onClick="this.select();" onchange="validateAccomodationField(\'directBill\', ' + indxRows + ')" id="directBill_' + indxRows + '"><div id="directBillError_' + indxRows + '" ' +
	'class="form-group has-error dateValidator01"><small class="help-block" role="alert" aria-atomic="true">' + $('#directBillEmptyError').val() + '</small>' +
	'</div><div style="margin-left:280px; margin-top:-45px;"><label class="label-color float-right">' + $('#lblReimburesementAmt').val() + ':&nbsp;&nbsp;</label></div></td><td colspan="2">' +
	'<input type="text" class="form-control decimal-group-two number-seperator-two-decimal" onClick="this.select();" onkeypress="return checkDigit(event,this,5,2);" oninput="validate(this)" onClick="this.select();" onchange="calculateExtendedMileage(' + indxRows + ')" id="reinAmt_' + indxRows + '"><div id="directBillError_' + indxRows + '" ' +
	'class="form-group has-error dateValidator01"><small class="help-block" role="alert" aria-atomic="true">' + $('#requiredField').val() + '</small>' +
	'</div></td></tr><tr id="ddetailRow_3' + indxRows + '"><td><label class="label-color float-left">' + $('#lblMeals').val() + ':&nbsp;&nbsp;</label>' +
	'<input class="form-control decimal-group-two number-seperator-two-decimal" type="text" style="width:65%" placeholder= "Breakfast" onClick="this.select();" onkeypress="return checkDigit(event,this,3,2);" oninput="validate(this)" onchange="calculateExtendedMileage(' + indxRows + ')" id="breakfast_' + indxRows + '"><div id="breakfastError_' + indxRows + '" class="form-group has-error dateValidator01">' +
	'</div></td><td><input class="form-control decimal-group-two number-seperator-two-decimal" style="width:65%" placeholder= "Lunch" type="text"' +
	'onkeypress="return checkDigit(event,this,3,2);" oninput="validate(this)" onClick="this.select();" onchange="calculateExtendedMileage(' + indxRows + ')" id="lunch_' + indxRows + '"><div id="lunchError_' + indxRows + '" class="form-group has-error dateValidator01">' +
	'</div></td><td><input class="form-control decimal-group-two number-seperator-two-decimal" placeholder= "Dinner" style="width:65%" type="text"' +
	'onkeypress="return checkDigit(event,this,3,2);" onClick="this.select();" oninput="validate(this)" onchange="calculateExtendedMileage(' + indxRows + ')" id="dinner_' + indxRows + '"><div id="dinnerError_' + indxRows + '" class="form-group has-error dateValidator01">' +
	'</div></td><td colspan="4"><label class="label-color float-right">' + $('#lblMealsTotal').val() + ':&nbsp;&nbsp;&nbsp;&nbsp;</label><label style="margin-top:5px;" class="label-color float-left">' + $('#lblMealOverride').val() + ':&nbsp;&nbsp;</label>' +
	'<input style="margin-top:8px; margin-right:22px;" class="float-left" type="checkbox" id="mealOverride_' + indxRows + '" onchange="calculateExtendedMileage(' + indxRows + ')"><input class="form-control-custom mr-sm-2 form-rounded border-secondary input-disabled"' +
	'placeholder= "Override Reason" style="width:55%" onClick="this.select();" onchange="hideDivError(' + indxRows + ')" type="text" id="overrideReason_' + indxRows + '" disabled><div style="margin-left:152px;" id="overrideReasonError_' + indxRows + '" ' +
	'class="form-group has-error dateValidator01"><small class="help-block" role="alert" aria-atomic="true">' + $('#overrideReasonEmptyError').val() + '</small><label class="label-color ' +
	'float-right">' + $('#lblMealsTotal').val() + ':&nbsp;&nbsp;</label></td><td><div style="margin-right:10px;" class="float-right"><label class="label-color float-right" id="mealsTotal_' + indxRows + '">' +
	'0.00</label></div><div class="float-right" /></td></tr><tr id="ddetailRow_4' + indxRows + '"> <td /><td><label class="label-color float-left">' + $('#lblAdditionalExpenses').val() + ':&nbsp;&nbsp;&nbsp;&nbsp;</label>' +
	'</td><td><input class="form-control decimal-group-two number-seperator-two-decimal" type="text" style="width:65%" placeholder= "Parking" onClick="this.select();" onkeypress="return checkDigit(event,this,3,2);" oninput="validate(this)" onchange="calculateExtendedMileage(' + indxRows + ')" id="parking_' + indxRows + '">' +
	'</td></td><td><input class="form-control decimal-group-two number-seperator-two-decimal" type="text" style="width:65%" placeholder= "Taxi" onClick="this.select();" onkeypress="return checkDigit(event,this,3,2);" oninput="validate(this)" onchange="calculateExtendedMileage(' + indxRows + ')" id="taxi_' + indxRows + '">' +
	'</td><td><input class="form-control decimal-group-two number-seperator-two-decimal" style="width:85%" placeholder= "Misc" type="text"' +
	'onkeypress="return checkDigit(event,this,5,2);" onClick="this.select();" oninput="validate(this)" onchange="calculateExtendedMileage(' + indxRows + ')" id="other_' + indxRows + '"></td><td colspan="3"><input class="form-control-custom mr-sm-2 form-rounded border-secondary float-left input-disabled" placeholder= "Misc Reason" onClick="this.select();" style="width:60%" type="text"' +
	'id="otherReason_' + indxRows + '" disabled><label class="label-color float-right">' + $('#lblAdditionalExpenseTotal').val() + ':&nbsp;&nbsp;</label><div style="margin-right:200px; margin-top:30px;" id="otherReasonError_' + indxRows + '" ' + 'class="form-group has-error dateValidator01">'+
	'<small class="help-block" role="alert" aria-atomic="true">' + $('#otherReasonEmptyError').val() + '</small></div></td><td><div style="margin-right:10px;" class="float-right"><label class="label-color float-right" id="additionalExpenseTotal_' + indxRows + '">' +
	'0.00</label></div></td></tr> ' +
	'<tr id="ddetailRow_5' + indxRows + '"> <td /> <td><td></td></td><td></td><td colspan="4"><div style="margin-left:10px;"><a href="javascript:void(0)" style="color:#0065ff; text-decoration: underline" onclick="showAssignAccountCodeForm(' + indxRows + ')">' + $('#lblAccntCodes').val() + '</a>&nbsp;&nbsp;<label class="label-color" id="accountCodeTot_' + indxRows + '"></label></div>' +
	'<div id="accountCodeTotError_' + indxRows + '" class="form-group has-error dateValidator01"><small class="help-block" role="alert" ' +
	'aria-atomic="true">' + $('#accountCodesEmptyError').val() + '</small></div>' +
	'<label class="label-color float-right">' + $('#lblDailyTotal').val() + ':&nbsp;&nbsp;</label></td>' +
	'<td><div style="margin-top:17px; margin-right:10px;" class="float-right"><label class="label-color float-right" ' +
	'id="totReiumbersment_' + indxRows + '">0.00</label></div></td></tr>' +
	'<tr id="ddetailRow_6' + indxRows + '"><td colspan="9"><hr></td></tr>';
	$('#trvlDetailsTable').append(gridData);
	$('#mileageTotalMinusCommute_' + indxRows).hide();
}

function createExtendedGrid() {
	var indxRows = indx;
	countRows = indxRows;
	var fromZip = "'fromZip'";
	var toZip = "'toZip'";
	var mileageRt = bfnOptionsTravel[2];
	if (mileageRt === null && mileageRt === undefined && mileageRt === '') {
		mileageRt = 0;
	}
	
	var gridData = '<tr id="detailRow_1' + indxRows + '" class="deleteRow"><td /> <td>Depart: <br><label id="depart_' + indxRows + '"></label></td>' +
	'<td></td><td><label class="label-color float-left">' + $('#lblContact1').val() + ':&nbsp;&nbsp;</label>' +
	'<input onchange="hideDivError(' + indxRows + ')" class="form-control-custom form-rounded border-secondary" style="width:70%" onClick="this.select();" type="text"  id="contact_' + indxRows + '">' +
	'<div id="contactError_' + indxRows + '" class="form-group has-error dateValidator01"><small ' +
	'class="help-block" role="alert" aria-atomic="true">' + $('#travelContactEmptyError').val() + '</small></div>' +
	' </td><td>	<input type="text" onClick="this.select();" onchange="hideDivError(' + indxRows + ')" class="form-control-custom mr-sm-2 form-rounded border-secondary"' +
	'  style="width:95%" placeholder="Origin Description" id="addressFromLine1_' + indxRows + '"><div id="addressFromLine1Error_' + indxRows + '"' +
	' class="form-group has-error dateValidator01">' +
	'<small class="help-block" role="alert" aria-atomic="true">' + $('#originDescriptionEmptyError').val() + '</small></div></td>' +
	' <td><input type="text" onClick="this.select();" onchange="hideDivError(' + indxRows + ')" class="form-control-custom mr-sm-2 form-rounded border-secondary"' +
	' style="width:95%" placeholder="Destination Description" id="addressToLine1_' + indxRows + '"><div id="addressToLine1Error_' + indxRows + '"' +
	' class="form-group has-error dateValidator01"><small ' +
	'class="help-block" role="alert" aria-atomic="true">' + $('#destinationDescriptionEmptyError').val() + '</small></div></td> </tr>' +
	'<tr id="detailRow_2' + indxRows + '"> <td /><td>Return: <br><label id="return_' + indxRows + '"></label></td><td /><td><label class="label-color float-left">' + $('#lblPurpose').val() + ':&nbsp;&nbsp;</label>' +
	'<textarea class="form-control" style="padding: 0.375rem 0.75rem; width: 70%; height: 100%;"' +
	' rows="3" cols="19" onchange="hideDivError(' + indxRows + ')" id="purpose_' + indxRows + '" /><div id="purposeError_' + indxRows + '" class="form-group has-error dateValidator01"><small ' +
	'class="help-block" role="alert" aria-atomic="true">' + $('#purposeEmptyError').val() + '</small></div></td><td><input ' +
	'class="form-control-custom mr-sm-2 form-rounded border-secondary" onClick="this.select();" type="text" style="width:95%" placeholder="Origin Address"' +
	' id="addressFromLine2_' + indxRows + '" ></td><td><input type="text"' +
	' class="form-control-custom mr-sm-2 form-rounded border-secondary" style="width:95%" onClick="this.select();" placeholder="Destination Address"' +
	' id="addressToLine2_' + indxRows + '" ></td></tr>' +
	' <tr id="detailRow_3' + indxRows + '"> <td /> <td /><td /><td /><td><label class="label-color float-left">' + $('#lblCity').val() + ':&nbsp;&nbsp;&nbsp;&nbsp;</label>' +
	'<input class="form-control-custom mr-sm-2 form-rounded border-secondary" type="text" onClick="this.select();" style="width:81%"  id="fromCity_' + indxRows + '">' +
	'</td> <td><label class="label-color float-left">' + $('#lblCity').val() + ':&nbsp;' +
	'&nbsp;&nbsp;&nbsp;</label><input class="form-control-custom mr-sm-2 form-rounded border-secondary" style="width:81%" onClick="this.select();" type="text"' +
	' id="toCity_' + indxRows + '"></td></tr>' +
	'<tr id="detailRow_4' + indxRows + '"> <td /> <td /><td /><td /><td>' +
	' <div class="col-lg-12"><div class="col-lg-5 float-left" style="width: 41%;"><label class="label-color float-left">'
	+ $('#lblState').val() + ':</label>' + formStates('fromState_' + indxRows + '') +
	' <div id="fromStateError_' + indxRows + '" class="form-group has-error dateValidator01"><small ' +
	'class="help-block" role="alert" aria-atomic="true">' + $('#requiredField').val() + '</small></div></div>' +
	'<div style="width: 59%;display: flex;"><div style="margin-left:10px; width: 140%;"><label class="label-color float-left">' + $('#lblZip').val() + ':</label><div style="width:77%"><input class="form-control "' +
	' type="text" onClick="this.select();" id="fromZip_' + indxRows + '" style="width: 125%;" onkeyup="zipChange(event, this.value, \'fromZip\', ' + indxRows + ')" onchange="validateZipField(\'fromZip\', ' + indxRows + ')" inputmode="numeric" maxlength="10"><div id="fromZipError_' + indxRows + '" class="form-group has-error dateValidator01">' +
	'<small class="help-block" role="alert" aria-atomic="true">' + $('#fromZipValidError').val() + '</small></div></div></div></div>' +
	' <td>' +
	' <div class="col-lg-12"><div class="col-lg-5 float-left" style="width: 41%;"><label class="label-color float-left">'
	+ $('#lblState').val() + ':</label>' + toStates('toState_' + indxRows + '') +
	' <div id="toStateError_' + indxRows + '" class="form-group has-error dateValidator01"><small ' +
	'class="help-block" role="alert" aria-atomic="true">' + $('#requiredField').val() + '</small></div></div>' +
	'<div style="width: 59%;display: flex;"><div style="margin-left:10px;"><label class="label-color float-left">' + $('#lblZip').val() + ':</label><div style="width:77%"><input class="form-control "' +
	' type="text" onClick="this.select();" id="toZip_' + indxRows + '" style="width: 125%;" onkeyup="zipChange(event, this.value, \'toZip\', ' + indxRows + ')" onchange="validateZipField(\'toZip\', ' + indxRows + ')" inputmode="numeric" maxlength="10"><div id="toZipError_' + indxRows + '" class="form-group has-error dateValidator01">' +
	'<small class="help-block" role="alert" aria-atomic="true">' + $('#toZipValidError').val() + '</small></div></div></div></div> </tr>' +
	'<tr id="detailRow_5' + indxRows + '"><td /><td /><td /><td /><td /><td /></tr>' +
	'<tr id="detailRow_6' + indxRows + '"> <td /> <td /><td /> <td /><td>' +
	'<a href="javascript:void(0)" style="color:#0065ff; text-decoration: underline">Locations</a>' +
	'</td> <td> <div class="col-sm-2">&nbsp;</div> </td> </tr><tr id="detailRow_7' + indxRows + '"> <td /> <td /><td /><td /><td />' +
	' <td /> </tr>';
	$('#trvlTable').append(gridData);
}

function appendZerosExt(id) {
	var mileageBeginingVal = $('#mileageBegining_' + id).val();
	var mileageEndVal = $('#mileageEnd_' + id).val();
	var breakfastVal = $('#breakfast_' + id).val();
	var mapVal = $('#map_' + id).val();
	var lunchVal = $('#lunch_' + id).val();
	var dinnerVal = $('#dinner_' + id).val();
	var reinAmtVal = $('#reinAmt_' + id).val();
	var parkingVal = $('#parking_' + id).val();
	var taxiVal = $('#taxi_' + id).val();
	var otherVal = $('#other_' + id).val();

	if (mileageBeginingVal !== null && mileageBeginingVal !== '') {
		$('#mileageBegining_' + id).val(numberWithCommas(Number(numberWithoutCommas(mileageBeginingVal)).toFixed(1)));
	}
	if (mileageEndVal !== null && mileageEndVal !== '') {
		$('#mileageEnd_' + id).val(numberWithCommas(Number(numberWithoutCommas(mileageEndVal)).toFixed(1)));
	}
	if (mapVal !== null && mapVal !== '') {
		$('#map_' + id).val(numberWithCommas(Number(numberWithoutCommas(mapVal)).toFixed(1)));
	}
	if (breakfastVal !== null && breakfastVal !== '') {
		$('#breakfast_' + id).val(numberWithCommas(Number(numberWithoutCommas(breakfastVal)).toFixed(2)));
	}
	if (lunchVal !== null && lunchVal !== '') {
		$('#lunch_' + id).val(numberWithCommas(Number(numberWithoutCommas(lunchVal)).toFixed(2)));
	}
	if (dinnerVal !== null && dinnerVal !== '') {
		$('#dinner_' + id).val(numberWithCommas(Number(numberWithoutCommas(dinnerVal)).toFixed(2)));
	}
	if (reinAmtVal !== null && reinAmtVal !== '') {
		$('#reinAmt_' + id).val(numberWithCommas(Number(numberWithoutCommas(reinAmtVal)).toFixed(2)));
	}
	if (parkingVal !== null && parkingVal !== '') {
		$('#parking_' + id).val(numberWithCommas(Number(numberWithoutCommas(parkingVal)).toFixed(2)));
	}
	if (taxiVal !== null && taxiVal !== '') {
		$('#taxi_' + id).val(numberWithCommas(Number(numberWithoutCommas(taxiVal)).toFixed(2)));
	}
	if (otherVal !== null && otherVal !== '') {
		$('#other_' + id).val(numberWithCommas(Number(numberWithoutCommas(otherVal)).toFixed(2)));
	}
}

function calculateExtendedMileage(id, viewType) {
	appendZerosExt(id)
	if (bfnOptionsTravel[1] == 'Y') {
		disableMapField(id);
	}
	var totalValue = 0;
	var mileageBegining = numberWithoutCommas($('#mileageBegining_' + id).val());
	var mileageEnd = numberWithoutCommas($('#mileageEnd_' + id).val());
	var map = numberWithoutCommas($('#map_' + id).val());
	var reinAmt = numberWithoutCommas($('#reinAmt_' + id).val());
	var accomodations = $('#accomodations_' + id).val();
	if (mileageBegining == "") {
		$('#mileageBeginingGreaterError_' + id).hide();
	}
	if (mileageBegining !== "") {
		$('#mileageBeginingError_' + id).hide();
	}
	if (mileageEnd !== "") {
		$('#mileageEndError_' + id).hide();
	}
	if (map !== "") {
		$('#mileageBeginingError_' + id).hide();
		$('#mileageEndError_' + id).hide();
		$('#mapError_' + id).hide();
	}
	if ((mileageBegining !== null || mileageEnd !== null)
		&& (mileageBegining !== "" || mileageEnd !== "")
		&& (mileageBegining !== undefined || mileageEnd !== undefined)) {
		disableMapField(id);
	}
	if ((mileageBegining !== null && mileageEnd !== null)
		&& (mileageBegining !== "" && mileageEnd !== "")
		&& (mileageBegining !== undefined && mileageEnd !== undefined)) {
		$('#mapError_' + id).hide();
		disableMapField(id);
		if (Number(mileageBegining) > Number(mileageEnd)) {
			$('#mileageBeginingError_' + id).hide();
			$('#mileageBeginingGreaterError_' + id).show();
		} else {
			var total = $('#total_' + id);
			var mileageRate = bfnOptionsTravel[2];
			if (mileageRate === null && mileageRate === undefined && mileageRate === '') {
				mileageRate = 0;
			}
			var totalMileageRate = $('#totalMileageRate_' + id);
			var miscAmt = $('#miscAmt_' + id).val();
			if (miscAmt == undefined) {
				miscAmt = 0;
			}
			$('#miscAmt_' + id).val(Number(miscAmt).toFixed(2));
			var totReiumbersment = $('#totReiumbersment_' + id);
			var totalAmt = Number(mileageEnd) - Number(mileageBegining);
			var totalValue = totalAmt;
			total.text(Number(totalValue).toFixed(2));
			var totalAccCodeAmt = Number($('#accountCodeTot_' + id).text());
			var totalMileageRateValue = totalValue * Number(mileageRate);
			var totReiumbersmentValue = Number(totalMileageRateValue) + Number(miscAmt);
			$('#mileageBeginingError_' + id).hide();
			$('#mileageEndError_' + id).hide();
			$('#mileageBeginingGreaterError_' + id).hide();
			if (map == '' && mileageBegining != '' && mileageEnd != '') {
				if ($('#isRoundTrip_' + id).prop('checked')) {
					totalValue = totalAmt * 2;
					totalAmt = totalValue;
					total.text(Number(totalValue).toFixed(2));
					var totalMileageRateValue = totalValue * Number(mileageRate);
					var totReiumbersmentValue = Number(totalMileageRateValue) + Number(miscAmt);
					if ($('#isCommute_' + id).prop('checked')) {
						if (commuteDist[0] !== '0.0' && commuteDist[0] !== '') {
							mtValue = Number(totalValue) - (Number(commuteDist[0]) * 2);
							if (mtValue > 0) {
								totalValue = mtValue;
							} else {
								totalValue = 0;
								$('#mileageTotalMinusCommute_' + id).show();
							}
						} else {
							totalValue = Number(totalValue);
						}
						var totalMileageRateValue = totalValue * Number(mileageRate);
						var totReiumbersmentValue = Number(totalMileageRateValue) + Number(miscAmt);
					} else {
						$('#mileageTotalMinusCommute_' + id).hide();
					}
				}
				if (!$('#isRoundTrip_' + id).prop('checked')) {
					if ($('#isCommute_' + id).prop('checked')) {
						if (commuteDist[0] !== '0.0' && commuteDist[0] !== '') {
							mtValue = Number(totalAmt) - Number(commuteDist[0]);
							if (mtValue > 0) {
								totalValue = mtValue;
							} else {
								totalValue = 0;
								$('#mileageTotalMinusCommute_' + id).show();
							}
						} else {
							totalValue = Number(totalAmt);
						}
						total.text(Number(totalValue).toFixed(2));
						var totalMileageRateValue = totalValue * Number(mileageRate);
						var totReiumbersmentValue = Number(totalMileageRateValue) + Number(miscAmt);
					} else {
						$('#mileageTotalMinusCommute_' + id).hide();
					}
				}
			}
		}
	}
	if ((mileageBegining === null && mileageEnd === null)
		|| (mileageBegining === "" && mileageEnd === "")
		|| (mileageBegining === undefined && mileageEnd === undefined)) {
		$('#mileageBeginingGreaterError_' + id).hide();
	}
	if (map != '' && mileageBegining == '' && mileageEnd == '') {
		disableMileageFields(id);
		var total = $('#total_' + id);
		var mileageRate = bfnOptionsTravel[2];
		if (mileageRate === null && mileageRate === undefined && mileageRate === '') {
			mileageRate = 0;
		}
		var totalMileageRate = $('#totalMileageRate_' + id);
		var miscAmt = $('#miscAmt_' + id).val();
		if (miscAmt == undefined) {
			miscAmt = 0;
		}
		var totReiumbersment = $('#totReiumbersment_' + id);
		totalValue = Number(map) - 0;
		total.text(Number(totalValue).toFixed(2));
		var totalAccCodeAmt = Number(numberWithoutCommas($('#accountCodeTot_' + id).text()));
		var totalMileageRateValue = totalValue * Number(mileageRate);
		var totReiumbersmentValue = Number(totalMileageRateValue) + Number(miscAmt);
		if ($('#isRoundTrip_' + id).prop('checked')) {
			totalValue = Number(map) * 2;
			total.text(Number(totalValue).toFixed(2));
			var totalMileageRateValue = totalValue * Number(mileageRate);
			var totReiumbersmentValue = Number(totalMileageRateValue) + Number(miscAmt);
			if ($('#isCommute_' + id).prop('checked')) {
				if (commuteDist[0] !== '0.0' && commuteDist[0] !== '') {
					mtValue = Number(totalValue) - (Number(commuteDist[0]) * 2);
					if (mtValue > 0) {
						totalValue = mtValue;
					} else {
						totalValue = 0;
						$('#mileageTotalMinusCommute_' + id).show();
					}
				} else {
					totalValue = Number(totalValue);
				}
				var totalMileageRateValue = totalValue * Number(mileageRate);
				var totReiumbersmentValue = Number(totalMileageRateValue) + Number(miscAmt);
			} else {
				$('#mileageTotalMinusCommute_' + id).hide();
			}
		}
		if (!$('#isRoundTrip_' + id).prop('checked')) {
			if ($('#isCommute_' + id).prop('checked')) {
				if (commuteDist[0] !== '0.0' && commuteDist[0] !== '') {
					mtValue = Number(totalValue) - Number(commuteDist[0]);
					if (mtValue > 0) {
						totalValue = mtValue;
					} else {
						totalValue = 0;
						$('#mileageTotalMinusCommute_' + id).show();
					}
				} else {
					totalValue = Number(totalValue);
				}
				total.text(Number(totalValue).toFixed(2));
				var totalMileageRateValue = totalValue * Number(mileageRate);
				var totReiumbersmentValue = Number(totalMileageRateValue) + Number(miscAmt);
			} else {
				$('#mileageTotalMinusCommute_' + id).hide();
			}
		}
	}
	if ((mileageBegining === null || mileageBegining === "" || mileageBegining === undefined)
		&& (mileageEnd === null || mileageEnd === "" || mileageEnd === undefined)
		&& (map === null || map === "" || map === undefined)) {
		if(viewType !== 'P' && viewType !== 'A') {
			enableMileageFields(id);
			enableMapField(id);
		}
	}
	if (totReiumbersmentValue === undefined) {
		var totReiumbersmentValue = 0;
	}
	var breakfast = numberWithoutCommas($('#breakfast_' + id).val());
	var lunch = numberWithoutCommas($('#lunch_' + id).val());
	var dinner = numberWithoutCommas($('#dinner_' + id).val());
	var mealsTotal = 0;
	var additionalExpenseTotal = 0;
	var reinAmt = numberWithoutCommas($('#reinAmt_' + id).val());
	if (breakfast !== '' || lunch !== '' || dinner !== '') {
		mealsTotal = Number(breakfast) + Number(lunch) + Number(dinner);
	}
	var parking = numberWithoutCommas($('#parking_' + id).val());
	var taxi = numberWithoutCommas($('#taxi_' + id).val());
	var other = numberWithoutCommas($('#other_' + id).val());
	if (parking !== '' || taxi !== '' || other !== '') {
		additionalExpenseTotal = Number(parking) + Number(taxi) + Number(other);
	}
	if (!$('#mealOverride_' + id).is(':checked')) {
		$('#breakfastError_' + id).hide();
		$('#lunchError_' + id).hide();
		$('#dinnerError_' + id).hide();
		$('#overrideReasonError_' + id).hide();
		$('#overrideReason_' + id).prop("disabled", true);
		$('#overrideReason_' + id).addClass('input-disabled');
	}
	if ($('#mealOverride_' + id).is(':checked')) {
		$('#overrideReasonError_' + id).hide();
		$('#overrideReason_' + id).prop("disabled", false);
		$('#overrideReason_' + id).removeClass('input-disabled');
	}
	if (other !== ''){
		$('#otherReasonError_' + id).hide();
		$('#otherReason_' + id).prop("disabled", false);
		$('#otherReason_' + id).removeClass('input-disabled');
	}
	else{
		$('#otherReasonError_' + id).hide();
		$('#otherReason_' + id).prop("disabled", true);
		$('#otherReason_' + id).addClass('input-disabled');
	}
	var dailyTotal = totReiumbersmentValue + Number(reinAmt) + mealsTotal + additionalExpenseTotal;
	$('#total_' + id).text(numberWithCommas(Number(totalValue).toFixed(1)));
	$('#totalMileageRate_' + id).text(numberWithCommas(totReiumbersmentValue.toFixed(2)));
	$('#mealsTotal_' + id).text(numberWithCommas(mealsTotal.toFixed(2)));
	$('#additionalExpenseTotal_' + id).text(numberWithCommas(additionalExpenseTotal.toFixed(2)));
	$('#totReiumbersment_' + id).text(numberWithCommas(dailyTotal.toFixed(2)));
	calculateMilesTotal();
	calculateMiscTotalExtended();
	calculateRequestTotal();
	addEditAssignAccountCodeForm(id, 'Y');
	calculateAmountClick();
	clickAccountCodes(id);
}

function validateAccomodationField(fieldId, countRows) {
	var isValid = true;
	console.log('fieldId:: ', fieldId);
	var fieldValue = $("#" + fieldId + "_" + countRows).val();
	console.log('fieldValue:: ', fieldValue);
	if ("accommodations" == fieldId && fieldValue !== '') {
		$("#accomodationsError_" + countRows).hide();
		isValid = false;
	} else if ("directBill" == fieldId && fieldValue !== '') {
		$("#directBillError_" + countRows).hide();
	} else if ("directBill" == fieldId && fieldValue === '') {
		isValid = false;
	} else {
		$("#directBillError_" + countRows).hide();
	}
	return isValid;
}

function checkDigit(e, obj, intsize, deczize) {
	var keycode;
	if (window.event)
		keycode = window.event.keyCode;
	else if (e) {
		keycode = e.which;
	} else {
		return true;
	}
	var fieldval = (obj.value),
		dots = fieldval.split(".").length;

	if (keycode == 46) {
		return dots <= 1;
	}
	if (keycode == 8 || keycode == 9 || keycode == 46 || keycode == 13) {
		return true;
	}
	if ((keycode >= 32 && keycode <= 45) || keycode == 47 || (keycode >= 58 && keycode <= 127)) {
		return false;
	}
	if (fieldval == "0" && keycode == 48) {
		return false;
	}
	if (fieldval.indexOf(".") != -1) {
		if (keycode == 46) {
			return false;
		}
		var splitfield = fieldval.split(".");
		if (splitfield[1].length >= deczize && keycode != 8 && keycode != 0)
			return true;
	} else if (fieldval.length >= intsize && keycode != 46) {
		return false;
	} else {
		return true;
	}
}

function checkDocuments() {
	var tripNbr = '';
	if (tripData != null && tripData != ''
		&& tripData != undefined && tripData != 'undefined') {
		var tripValue = tripData[0];
		if (tripValue != null && tripValue != ''
			&& tripValue != undefined && tripValue != 'undefined') {
			tripNbr = tripData[0];
		}
	}
	var payload = {
		tripNbr : tripNbr
	}
	$.ajax({
		type : 'post',
		url : urlMain + "/travelRequest/checkDocuments",
		contentType : 'application/json;charset=utf-8',
		dataType : 'json',
		async : false,
		data : JSON.stringify(payload),
		success : function(res) {
			data = res.result;
			if (data) {
					$('#documentUpload').css({
						'width' : '125px',
						'background-position' : '10px 10px',
						'vertical-align' : 'middle',
						'background-repeat' : 'no-repeat',
						'background-image' : 'url("/CommonWebPortals/images/document.gif")'
					});
				} else {
					$('#documentUpload').css({
						'width' : '125px',
						'background-position' : '10px 10px',
						'vertical-align' : 'middle',
						'background-repeat' : 'no-repeat',
						'background-image' : ''
					});
			}
		},
		error : function(res) {
			console.log(res);
		}
	});
}

function cancelForm() {
	window.history.back();
}

function initialAutoCompleteList() {
    $('.trvlAccountCodeAutoComplete').each(function() {
        var input = this;
        $(input).autocomplete({
            source: function(request, response){
                $.ajax({
                    type : 'POST',
                    url : urlMain + "/fundRequest/funds?type=trvlAccountCodeAutoComplete&q=" + $(input).val().trim(),
                    dataType : 'JSON',
                    contentType : 'application/json;charset=UTF-8',
                    success : function (data) {
	                    response($.map(data, function(item){
	                        return {
	                            label: item.fund + " : " + item.description,
	                            value: item.fund
	                        }
	                    }));
                    },
                    error:function(res){
                    	console.log(res);
                    	response(res)
                    }
                })
            },
    		focus: function() {
    			return false;
    		},
            select: function(event, ui) {
    			$('#' + this.id).val(ui.item.value);	// Selected Value
            }
        });
    });
};