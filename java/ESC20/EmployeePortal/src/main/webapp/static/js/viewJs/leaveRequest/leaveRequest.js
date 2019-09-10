var reasonOption
$(document).ready(function() {
	if(isAddValue == 'true'){
        $("#requestModal").modal('show')
        $(".edit-title").hide()
        $(".secondSubmit").hide();
        $("#deleteLeave").hide();	
    }
	reasonOption = $("#absenceReason").html()
	var formDate = $('#SearchStartDate').fdatepicker({
		format:'mm-dd-yyyy',
		language:initialLocaleCode,
		weekStart:7
	}).on('changeDate', function(ev) {
		var fromInput = $("#SearchStartInput").val()
		var toInput = $("#SearchEndInput").val()
		if(fromInput&&toInput){
			var from = ev.date.valueOf()
			var to = toDate.date.valueOf()
			if(from>to){
				$("#timeErrorMessage").removeClass("hide")
				$("#retrieve").attr("disabled","disabled")
				$("#retrieve").addClass("disabled")
			}else{
				$("#timeErrorMessage").addClass("hide")
				$("#retrieve").removeAttr("disabled")
				$("#retrieve").removeClass("disabled")
			}
		}
	})
	.data('datepicker')
	var toDate = $('#SearchEndDate').fdatepicker({
		format:'mm-dd-yyyy',
		language:initialLocaleCode,
		weekStart:7
	}).on('changeDate', function(ev) {
		var fromInput = $("#SearchStartInput").val()
		var toInput = $("#SearchEndInput").val()
		if(fromInput&&toInput){
			var to = ev.date.valueOf()
			var from = formDate.date.valueOf()
			if(from>to){
				$("#timeErrorMessage").removeClass("hide")
				$("#retrieve").attr("disabled","disabled")
				$("#retrieve").addClass("disabled")
			}else{
				$("#timeErrorMessage").addClass("hide")
				$("#retrieve").removeAttr("disabled")
				$("#retrieve").removeClass("disabled")
			}
		}
	}).on('outOfRange',function(ev){
	})
	.data('datepicker')
	$(".sureDelete").click(function(){
		$("#deleteForm")[0].submit();
	})
	$("#retrieve").click(function(){
		var fromValue = $("#SearchStartInput").val()
		var toValue = $("#SearchEndInput").val()
		var fromInput = changeDateYMD(fromValue)
		var toInput = changeDateYMD(toValue)
		if((!fromValue || !toValue) || (fromInput && toInput && fromInput<=toInput)){
			$("#timeErrorMessage").addClass("hide")
			$("#SearchForm")[0].submit();
		}else{
			$("#timeErrorMessage").removeClass("hide")
		}
	})
});

function editLeave(id,leaveType,absenceReason,leaveStartDate,leaveEndDate,lvUnitsDaily,lvUnitsUsed){
	var comments;
	leaveList.forEach(function(element) {
		if(element.id == id){
			comments = element.comments
		}
	});
		$('#requestForm')
	.data('bootstrapValidator')
	.destroy()
		$('#requestForm').data('bootstrapValidator', null)
		$('.dateValidator').hide()
		$('.dateValidator01').hide()
	formValidator()
	var start_arry = leaveStartDate.split(" ")
	var end_arry = leaveEndDate.split(" ")
	var startTime = start_arry[1].split(":")
	var endTime = end_arry[1].split(":")
	var startH = parseInt(startTime[0])
	var endH = parseInt(endTime[0])
	var startAMOrPM,endAMOrPM;
	startH = startTime[0].trim();
	startAMOrPM = start_arry[2].trim();
	endH = endTime[0].trim();
	endAMOrPM = end_arry[2].trim();
	$("#startHour").val(startH);
	$("#endHour").val(endH);
	$("#startAmOrPm").val(startAMOrPM)
	$("#endAmOrPm").val(endAMOrPM)
	var startTimeValue = startH + ":" + startTime[1] + " " + startAMOrPM
	var endTimeValue = endH + ":" + endTime[1] + " " + endAMOrPM
	$("#startTimeValue").val(startTimeValue)
	$("#endTimeValue").val(endTimeValue)
	$("#startMinute").val(startTime[1])
	$("#endMinute").val(endTime[1])
	// $("#cancelAdd").hide();
	// $("#deleteLeave").show();	
	$(".firstSubmit").hide();
	$(".secondSubmit").show();
	$(".edit-title").show();
	$(".new-title").hide();
	$(".availableError").hide()
	$("#commentList").html("")
	for(var i=0;i<comments.length;i++){
			var html = '<p>'+comments[i].detail+'</p>'
			$("#commentList").append(html)
	}
	$("[name='leaveId']").attr("value", id+"");
	$("[name='leaveType']").val(leaveType);
	changeLeaveType()
	$("#absenceReason").val(absenceReason);
	$("#startDateInput").val(start_arry[0]);
	$("#endDateInput").val(end_arry[0]);
	$("#leaveHoursDaily").val(lvUnitsDaily);
	$("#totalRequested").val(lvUnitsUsed);
	// var empNbr = $("#sessionEmpNbr").val()
	// $("#empNbrModal").val(empNbr);
	$(".dateTimePeriodOverlap").hide()
}

function deleteLeave(id){
	$("#deleteId").val(id);
}

function changeMMDDFormat(date){
	var dateArry = date.split("-")
	return dateArry[1]+"-"+dateArry[2]+"-"+dateArry[0]
}
function showRequestForm() {
		$('#leaveId').attr('value', '')
		$("[name='Remarks']").text('')
		$('#requestForm')[0].reset()
		$('#requestForm')
			.data('bootstrapValidator')
			.destroy()
		$('#requestForm').data('bootstrapValidator', null)
		formValidator()
		$("#absenceReason").html(reasonOption)
		$('#cancelAdd').show()
		$('#deleteLeave').hide()
		$(".edit-title").hide();
		$('.dateValidator').hide()
		$('.dateValidator01').hide()
		$(".new-title").show();
		$(".firstSubmit").show();
		$(".secondSubmit").hide();
		$(".availableError").hide()
//Initializes the time control when edit event modal show
		$("#commentList").html("")
		$(".timeUnit").hide()
		$(".leaveHoursDailyNotZero").hide()
		$(".leaveHoursDailyWrap").removeClass('has-error')
		// var empNbr = $("#sessionEmpNbr").val()
		// $("#empNbrModal").val(empNbr);
		$(".dateTimePeriodOverlap").hide()
}
function changeFormatTimeAm(value){
		var array = value.split(/[,: ]/);
		var hour,minute,time
		hour = parseInt(array[0])
		minute = parseInt(array[1])
		if(minute>=0 && minute <30){
			minute = "00"
		}else{
			minute = "30"
		}
		if(hour>12){
			hour = (hour-12) < 10 ? "0" + (hour-12) : hour-12;
			time = hour+ ":" +minute+" PM"
		}else{
			if(hour==12){
				time = hour+ ":" +minute+" PM"
			}else{
				hour = hour < 10 ? "0" + hour : hour;
				time = hour+ ":" +minute+" AM"
			}

		}
		return time
}
function changeFreq(){
		$("#changeFreqForm")[0].submit();
}
function changeDateYMD(date){
	if(!date){
		return
	}
	var dateArry = date.split("-")
	var DateFormat = new Date(dateArry[2]+"-"+dateArry[0]+"-"+dateArry[1])
	return DateFormat
}