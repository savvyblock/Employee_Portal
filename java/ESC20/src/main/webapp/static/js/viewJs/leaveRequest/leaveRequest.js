$(document).ready(
		function() {
			console.log(initialLocaleCode)
			var formDate = $('#SearchStartDate').fdatepicker({
				format:'mm/dd/yyyy',
				language:initialLocaleCode
			}).on('changeDate', function(ev) {
				let fromInput = $("#SearchStartDate").val()
				let toInput = $("#SearchEndDate").val()
				if(fromInput&&toInput){
					let from = ev.date.valueOf()
					let to = toDate.date.valueOf()
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
				format:'mm/dd/yyyy',
				language:initialLocaleCode
			}).on('changeDate', function(ev) {
				console.log(ev)
				let fromInput = $("#SearchStartDate").val()
				let toInput = $("#SearchEndDate").val()
				if(fromInput&&toInput){
					let to = ev.date.valueOf()
					let from = formDate.date.valueOf()
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
				console.log(ev)
			})
			.data('datepicker')
			setGlobal()
			$(".sureDelete").click(function(){
				$("#deleteForm")[0].submit();
			})
			$("#retrieve").click(function(){
				let fromInput = changeDateYMD($("#SearchStartDate").val())
				let toInput = changeDateYMD($("#SearchEndDate").val())
				console.log(fromInput)
				console.log(toInput)
				if(fromInput && toInput && fromInput<=toInput){
					$("#timeErrorMessage").addClass("hide")
					$("#changeFreqForm")[0].submit();
				}else{
					$("#timeErrorMessage").removeClass("hide")
				}
			})
	});

	function editLeave(id,leaveType,absenceReason,leaveStartDate,leaveEndDate,lvUnitsDaily,lvUnitsUsed){
		let comments;
		leaveList.forEach(element => {
			if(element.id == id){
				console.log(element)
				comments = element.comments
			}
		});
			$('#requestForm')
		.data('bootstrapValidator')
		.destroy()
			$('#requestForm').data('bootstrapValidator', null)
			$('.dateValidator').hide()
		formValidator()
		let start_arry = leaveStartDate.split(" ")
		let end_arry = leaveEndDate.split(" ")
		let startTime = start_arry[1].split(":")
		let endTime = end_arry[1].split(":")
		console.log(startTime)
		console.log(endTime)
		let startH = parseInt(startTime[0])
		let endH = parseInt(endTime[0])
		let startAMOrPM,endAMOrPM;
		startH = startTime[0].trim();
		startAMOrPM = start_arry[2].trim();
		endH = endTime[0].trim();
		endAMOrPM = end_arry[2].trim();
		$("#startHour").val(startH);
		$("#endHour").val(endH);
		$("#startAmOrPm").val(startAMOrPM)
		$("#endAmOrPm").val(endAMOrPM)
		let startTimeValue = startH + ":" + startTime[1] + " " + startAMOrPM
		let endTimeValue = endH + ":" + endTime[1] + " " + endAMOrPM
		$("#startTimeValue").val(startTimeValue)
		$("#endTimeValue").val(endTimeValue)
		$("#startMinute").val(startTime[1])
		$("#endMinute").val(endTime[1])
		$("#cancelAdd").hide();
		$("#deleteLeave").show();	
		$(".firstSubmit").hide();
		$(".secondSubmit").show();
		$(".edit-title").show();
		$(".new-title").hide();
		$("#commentList").html("")
		console.log(comments)
		for(let i=0;i<comments.length;i++){
				let html = '<p>'+comments[i].detail+'</p>'
				$("#commentList").append(html)
		}
		$("[name='leaveId']").attr("value", id+"");
		$("[name='leaveType']").val(leaveType);
		$("#absenceReason").val(absenceReason);
		$("#startDate").val(start_arry[0]);
		$("#endDate").val(end_arry[0]);
		$("#leaveHoursDaily").val(lvUnitsDaily);
		$("#totalRequested").val(lvUnitsUsed);
	}
	
	function deleteLeave(id){
		$("#deleteId").val(id);
	}

	function changeMMDDFormat(date){
		let dateArry = date.split("-")
		return dateArry[1]+"/"+dateArry[2]+"/"+dateArry[0]
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
			$('#cancelAdd').show()
			$('#deleteLeave').hide()
			$(".edit-title").hide();
			$('.dateValidator').hide()
			$('.dateValidator01').hide()
			$(".new-title").show();
			$(".firstSubmit").show();
			$(".secondSubmit").hide();
	//Initializes the time control when edit event modal show
			$("#commentList").html("")
}
	function changeFormatTimeAm(value){
			let array = value.split(/[,: ]/);
			let hour,minute,time
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
		let dateArry = date.split("/")
		let DateFormat = new Date(dateArry[2]+"-"+dateArry[0]+"-"+dateArry[1])
		return DateFormat
	}