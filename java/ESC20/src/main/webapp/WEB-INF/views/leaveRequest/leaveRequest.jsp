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
		<main class="content-wrapper" tabindex="-1">
		<section class="content">
			<div class="clearfix section-title">
					<h1 class="pageTitle" data-localize="title.leaveRequest"></h1>
				<div class="pull-right right-btn">
					<button class="btn btn-primary"  onclick="showRequestForm()" id="new-btn" data-toggle="modal" data-target="#requestModal">
						<span data-localize="label.add"></span>
					</button>
					<a class="btn btn-primary" href="/<%=request.getContextPath().split("/")[1]%>/leaveRequestCalendar/eventCalendar"  aria-label="" data-localize="label.switchToCalendarView" data-localize-location="aria-label" data-localize-notText="true">
						<i class="fa fa-calendar"></i>
					</a>
				</div>
				
				
			</div>
			<div class="content-white">
					<form
                            class="no-print searchForm"
                            action="leaveRequestByFreqency"
                            id="changeFreqForm"
                            method="POST"
										>
										<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
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
					id="SearchForm"
					method="POST"
						>
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						<input type="text" name="freq" hidden="hidden" value="${selectedFreq}" aria-label="" data-localize="accessHint.frequency">
					<div class="form-group type-group">
						<label class="form-title" for="SearchType"><span data-localize="label.type"></span>:</label> 
						<select id="SearchType"
							class="form-control" name="SearchType" autocomplete="off">
							<c:forEach var="type" items="${leaveTypesforSearch}" varStatus="count">
									<option value="${type.code}" <c:if test="${type.code == SearchType }">selected</c:if>>${type.description}</option>
							</c:forEach>
						</select>
					</div>
					<div class="form-group">
							<label class="form-title" for="SearchStartDate"><span data-localize="label.from"></span>:</label> 
							<div class="button-group">
							<input
								class="form-control" type="text" name="SearchStart"
								data-date-format="mm/dd/yyyy"  autocomplete="off"
								aria-label=""
								data-localize="label.mmddyyyyFormat"
								placeholder=""
								title=""
								id="SearchStartDate"  value="${SearchStart}" />
								<button class="clear-btn" type="button" role="button" onclick="clearDate(this)" tabindex="0"  aria-label="" data-localize="label.removeContent" data-localize-location="aria-label" data-localize-notText="true">
									<i class="fa fa-times"></i>
								</button>
							</div>
						</div>
						<div class="form-group">
							<label class="form-title" for="SearchEndDate"> <span data-localize="label.to"></span>: </label> 
							<div class="button-group">
							<input
								class="form-control" type="text" name="SearchEnd"
								data-date-format="mm/dd/yyyy"  autocomplete="off"
								aria-label=""
								data-localize="label.mmddyyyyFormat"
								placeholder=""
								title=""
								id="SearchEndDate" value="${SearchEnd}" />
								<button class="clear-btn" type="button" role="button" onclick="clearDate(this)" tabindex="0"  aria-label="" data-localize="label.removeContent" data-localize-location="aria-label" data-localize-notText="true">
									<i class="fa fa-times"></i>
								</button>
							</div>
						</div>
						<div class="form-group btn-group">
							<div style="margin-top:20px;">
									<button id="retrieve" type="button" role="button" class="btn btn-primary" data-localize="leaveBalance.retrieve">
										</button>
							</div>
						</div>
					</form>
					<div class="form-group">
						<p class="error-hint hide" role="alert" aria-atomic="true" id="timeErrorMessage" data-localize="validator.fromDateNotGreaterToDate"></p>
					</div>
					<h2 class="table-top-title">
						<b data-localize="label.unprocessedLeaveRequest"></b>
					</h2>
					<div class="hr-black"></div>
				<c:if test="${fn:length(leaves) > 0}">
					<table class="table request-list responsive-table">
						<thead>
							<tr>
								<!-- <th data-localize="leaveRequest.sno"></th> -->
								<th data-localize="leaveRequest.leaveType"></th>
								<th data-localize="leaveRequest.absenceReason"></th>
								<th class="text-center" data-localize="leaveRequest.startDate"></th>
								<th class="text-center" data-localize="leaveRequest.endDate"></th>
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
									<!-- <td  data-localize="leaveRequest.sno" data-localize-location="scope">${leaves.index + 1}</td> -->
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
									<td class="text-center" data-localize="leaveRequest.startDate" data-localize-location="scope">${leave.start}</td>
									<td class="text-center" data-localize="leaveRequest.endDate" data-localize-location="scope">${leave.end}</td>
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
											<c:if test="${leave.statusCd != 'A'}">
													<button class="btn btn-primary sm edit-btn" id="editLeave" data-toggle="modal" data-target="#requestModal" 
													onClick='editLeave("${leave.id}","${leave.LeaveType}","${leave.AbsenseReason}","${leave.start}",
													"${leave.end}", "${leave.lvUnitsDaily}","${leave.lvUnitsUsed}")' data-localize="label.edit"></button>
													<button class="btn btn-secondary sm"  data-toggle="modal" data-target="#deleteModal"  onClick="deleteLeave(${leave.id})" data-localize="label.delete"></button>
											</c:if>
										
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
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				<input type="text" id="deleteId" name="id" aria-label="" data-localize="accessHint.id"/>
			</form>
		</main>
	</div>
	<%@ include file="../commons/footer.jsp"%>
	<%@ include file="../modal/eventModal.jsp"%>
	<%@ include file="../modal/deleteModal.jsp"%>
</body>
<script>
var leaveList = eval(${leaves});
</script>
<script src="/<%=request.getContextPath().split("/")[1]%>/js/viewJs/leaveRequest/leaveRequest.js"></script>

</html>
