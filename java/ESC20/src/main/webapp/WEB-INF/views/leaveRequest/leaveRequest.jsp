<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html lang="en">
<head>
<title data-localize="headTitle.leaveRequestList"></title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@ include file="../commons/header.jsp"%>
</head>
<body class="hold-transition sidebar-mini">
	<div class="wrapper">
		<%@ include file="../commons/bar.jsp"%>
		<main class="content-wrapper">
		<section class="content">
			<h2 class="clearfix section-title">
					<span data-localize="title.leaveRequest"></span>
				<div class="pull-right right-btn">
					<button class="btn btn-primary" id="new-btn" data-toggle="modal" data-target="#requestModal" onclick="showRequestForm()" data-localize="label.addNewRequest" data-localize-location="title">
						<span data-localize="label.add"></span>
					</button>
					<a class="btn btn-primary" href="/<%=request.getContextPath().split("/")[1]%>/leaveRequest/eventCalendar" data-localize="label.switchToCalendarView"></a>
				</div>
				
				
			</h2>
			<div class="content-white">
					<form
                            class="no-print searchForm"
                            action="leaveRequestByFreqency"
                            id="changeFreqForm"
                            method="POST"
										>
										<div class="form-group in-line">
										<label class="form-title"  for="freq"  data-localize="label.payrollFreq"></label>
						<select class="form-control" name="freq" id="freq" onchange="changeFreq()">
	                                    <c:forEach var="freq" items="${availableFreqs}" varStatus="count">
	                                        <option value="${freq.code}" <c:if test="${freq.code == selectedFreq }">selected</c:if>>${freq.description}</option>
	                                    </c:forEach>
											</select>
										</div>
					</form>
					<form
					class="no-print searchForm"
					action="leaveRequest"
					id="changeFreqForm"
					method="POST"
						>
						<input type="text" name="freq" hidden="hidden" value="${selectedFreq}">
					<div class="form-group type-group">
						<label class="form-title" for="SearchType"><span data-localize="label.type"></span>:</label> 
						<select id="SearchType"
							class="form-control" name="SearchType" autocomplete="off">
							<c:forEach var="type" items="${leaveTypes}" varStatus="count">
									<option value="${type.code}" <c:if test="${type.code == SearchType }">selected</c:if>>${type.description}</option>
							</c:forEach>
						</select>
					</div>
					<div class="form-group">
							<label class="form-title" for="SearchStartDate"><span data-localize="label.from"></span>:</label> 
							<div class="button-group">
							<input
								class="form-control" type="text" name="SearchStart"
								id="SearchStartDate" readonly value="${SearchStart}" />
								<button class="clear-btn" type="button" onclick="clearDate(this)" data-localize="label.removeContent" data-localize-location="title" tabindex="0"><i class="fa fa-times"></i></button>
							</div>
						</div>
						<div class="form-group">
							<label class="form-title" for="SearchEndDate"> <span data-localize="label.to"></span>: </label> 
							<div class="button-group">
							<input
								class="form-control" type="text" name="SearchEnd"
								id="SearchEndDate" value="${SearchEnd}" readonly />
								<button class="clear-btn" type="button" onclick="clearDate(this)" data-localize="label.removeContent" data-localize-location="title" tabindex="0"><i class="fa fa-times"></i></button>
							</div>
						</div>
						<div class="form-group btn-group">
							<div style="margin-top:20px;">
									<button id="retrieve" type="submit" class="btn btn-primary" data-localize="leaveBalance.retrieve">
										</button>
							</div>
						</div>
					</form>
					<div class="form-group">
						<p class="error-hint hide" id="timeErrorMessage" data-localize="validator.fromDateNotGreaterToDate"></p>
					</div>
					<p class="table-top-title">
						<b data-localize="label.unprocessedLeaveRequest"></b>
					</p>
					<div class="hr-black"></div>
				<c:if test="${fn:length(leaves) > 0}">
					<table class="table request-list responsive-table">
						<thead>
							<tr>
								<th data-localize="leaveRequest.sno"></th>
								<th data-localize="leaveRequest.leaveType"></th>
								<th data-localize="leaveRequest.absenceReason"></th>
								<th data-localize="leaveRequest.startDate"></th>
								<th data-localize="leaveRequest.endDate"></th>
								<!-- <th data-localize="leaveRequest.startTime"></th>
								<th data-localize="leaveRequest.endTime"></th> -->
								<th data-localize="leaveRequest.leaveRequested"></th>
								<th data-localize="leaveRequest.commentLog"></th>
								<th data-localize="leaveRequest.status"></th>
								<td></td>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="leave" items="${leaves}" varStatus="leaves">
								<tr>
									<td  data-localize="leaveRequest.sno" data-localize-location="scope">${leaves.index + 1}</td>
									<td data-localize="leaveRequest.leaveType" data-localize-location="scope">
											<c:forEach var="type" items="${leaveTypes}" varStatus="count">
													<c:if test="${type.code == leave.LeaveType}">${type.description}</c:if>
											</c:forEach>
									</td>
									<td data-localize="leaveRequest.absenceReason" data-localize-location="scope">
										<c:forEach var="abs" items="${absRsns}" varStatus="count">
													<c:if test="${abs.code == leave.AbsenseReason }">${abs.description}</c:if>
											</c:forEach>
									</td>
									<td data-localize="leaveRequest.startDate" data-localize-location="scope">${leave.start}</td>
									<td data-localize="leaveRequest.endDate" data-localize-location="scope">${leave.end}</td>
									<td data-localize="leaveRequest.leaveRequested" data-localize-location="scope">${leave.lvUnitsUsed} 
											<span data-localize="label.days"></span>
									</td>
									<td data-localize="leaveRequest.commentLog" data-localize-location="scope">
										<c:forEach var="comment" items="${leave.comments}" varStatus="count">
													${comment.detail}<br>
											</c:forEach>
									</td>
									<td data-localize="leaveRequest.status" data-localize-location="scope">${leave.statusDescr}</td>
									<td style="width:150px;">
										<button class="btn btn-primary sm edit-btn" id="editLeave" data-toggle="modal" data-target="#requestModal" 
										onClick='editLeave("${leave.id}","${leave.LeaveType}","${leave.AbsenseReason}","${leave.start}",
										"${leave.end}", "${leave.lvUnitsDaily}","${leave.lvUnitsUsed}")' data-localize="label.edit"></button>
										<button class="btn btn-secondary sm" onClick="deleteLeave(${leave.id})" data-localize="label.delete"></button>
									</td>
								</tr>
							</c:forEach>
						</tbody>

					</table>
				</c:if>
				<c:if test="${fn:length(leaves) == 0}">
					<div data-localize="label.noData">
					<div>
				</c:if>
			</div>
		</section>
			<form hidden="true" id="deleteForm" action="deleteLeaveRequest" method="post">
				<input type="text" id="deleteId" name="id"/>
			</form>
		</main>
	</div>
	<%@ include file="../commons/footer.jsp"%>
	<%@ include file="../modal/eventModal.jsp"%>
	<%@ include file="../modal/deleteModal.jsp"%>
</body>

<script>
	var leaveList = eval(${leaves});
	var currentChooseId
	console.log(leaveList)
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
				})
				.data('datepicker')
				$(".sureDelete").click(function(){
					$("#deleteForm")[0].submit();
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
			// currentChooseId = id
			$("#deleteId").val(id);
			$("#deleteModal").modal("show")
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
        $(".new-title").show();
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
</script>
</html>
