<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html> 
<html lang="en">
<head>
<title>${sessionScope.languageJSON.headTitle.leaveRequestList}</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@ include file="../commons/header.jsp"%>
</head>
<body class="hold-transition sidebar-mini">
	<div class="wrapper">
		<%@ include file="../commons/bar.jsp"%>
		<main class="content-wrapper" tabindex="-1">
		<section class="content">
			<div class="clearfix section-title">
					<h1 class="pageTitle">${sessionScope.languageJSON.title.leaveRequest}</h1>
				<div class="pull-right right-btn">
					<button class="btn btn-primary"  onclick="showRequestForm()" id="new-btn" data-toggle="modal" <c:if test="${!haveSupervisor}">disabled</c:if> data-target="#requestModal">
						<span>${sessionScope.languageJSON.label.add}</span>
					</button>
					<a class="btn btn-primary" href="/<%=request.getContextPath().split("/")[1]%>/leaveRequestCalendar/eventCalendar"  aria-label="${sessionScope.languageJSON.label.switchToCalendarView}">
						<i class="fa fa-calendar"></i>
					</a>
				</div>
				
			</div>
			<input type="hidden" id="sessionEmpNbr" value="${sessionScope.userDetail.empNbr}">
			<div class="content-white">
					<c:if test="${not empty sessionScope.options.messageLeaveRequest}">
							<p class="topMsg error-hint" role="alert">${sessionScope.options.messageLeaveRequest}</p>
					</c:if>
					<c:if test="${!haveSupervisor}">
					<p class="topMsg error-hint" role="alert">${sessionScope.languageJSON.label.noSupervisorFound}</p>
				</c:if>
 
					<form
                            class="no-print searchForm"
                            action="leaveRequestByFreqency"
                            id="changeFreqForm"
                            method="POST"
										>
										<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
										<div class="form-group in-line">
										<label class="form-title"  for="freq">${sessionScope.languageJSON.label.payrollFreq}:</label>
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
						<input type="text" name="freq" hidden="hidden" value="${selectedFreq}" aria-label="${sessionScope.languageJSON.accessHint.frequency}"/>
					<div class="form-group type-group">
						<label class="form-title" for="SearchType"><span>${sessionScope.languageJSON.label.type}</span>:</label> 
						<select id="SearchType"
							class="form-control" name="SearchType" autocomplete="off">
							<c:forEach var="type" items="${leaveTypesforSearch}" varStatus="count">
									<option value="${type.code}" <c:if test="${type.code == SearchType }">selected</c:if>>${type.description}</option>
							</c:forEach>
						</select>
					</div>
					<div class="form-group">
							<label class="form-title" for="SearchStartInput"><span>${sessionScope.languageJSON.label.from}</span>:</label> 
							<div class="button-group">
								<div class="fDateGroup date" id="SearchStartDate" data-date-format="mm-dd-yyyy">
										<button class="prefix" type="button" aria-label="${sessionScope.languageJSON.label.showDatepicker}"><i class="fa fa-calendar"></i></button>
										<input class="form-control dateInput" name="SearchStart" autocomplete="off" type="text"
										placeholder="${sessionScope.languageJSON.label.mmddyyyyFormat}" id="SearchStartInput" value="${SearchStart}">
										<button class="clear-btn" type="button" role="button" onclick="clearDate(this)" aria-label="${sessionScope.languageJSON.label.removeContent}">
											<i class="fa fa-times"></i>
										</button>
								</div>
							<!-- <input
								class="form-control" type="text" name="SearchStart"
								data-date-format="mm-dd-yyyy"  autocomplete="off"
								aria-label="${sessionScope.languageJSON.label.mmddyyyyFormat}"
								placeholder="${sessionScope.languageJSON.label.mmddyyyyFormat}"
								title="${sessionScope.languageJSON.label.mmddyyyyFormat}"
								id="SearchStartDate"  value="${SearchStart}" />
								<button class="clear-btn" type="button" role="button" onclick="clearDate(this)" tabindex="0"  aria-label="${sessionScope.languageJSON.label.removeContent}">
									<i class="fa fa-times"></i>
								</button> -->
							</div>
						</div>
						<div class="form-group">
							<label class="form-title" for="SearchEndInput"> <span>${sessionScope.languageJSON.label.to}</span>:</label> 
							<div class="button-group">
									<div class="fDateGroup date" id="SearchEndDate" data-date-format="mm-dd-yyyy">
										<button class="prefix" type="button" aria-label="${sessionScope.languageJSON.label.showDatepicker}"><i class="fa fa-calendar"></i></button>
										<input class="form-control dateInput" type="text" name="SearchEnd"
										data-date-format="mm-dd-yyyy"  autocomplete="off"
										placeholder="${sessionScope.languageJSON.label.mmddyyyyFormat}"
										title="${sessionScope.languageJSON.label.mmddyyyyFormat}"
										id="SearchEndInput" value="${SearchEnd}"/>
										<button class="clear-btn" type="button" role="button" onclick="clearDate(this)" aria-label="${sessionScope.languageJSON.label.removeContent}" tabindex="0">
												<i class="fa fa-times"></i>
											</button>
							</div>
							<!-- <input
								class="form-control" type="text" name="SearchEnd"
								data-date-format="mm-dd-yyyy"  autocomplete="off"
								aria-label="${sessionScope.languageJSON.label.mmddyyyyFormat}"
								placeholder="${sessionScope.languageJSON.label.mmddyyyyFormat}"
								title="${sessionScope.languageJSON.label.mmddyyyyFormat}"
								id="SearchEndDate" value="${SearchEnd}" />
								<button class="clear-btn" type="button" role="button" onclick="clearDate(this)" tabindex="0"  aria-label="${sessionScope.languageJSON.label.removeContent}">
									<i class="fa fa-times"></i>
								</button> -->
							</div>
						</div>
						<div class="form-group btn-group">
							<div style="margin-top:20px;">
									<button id="retrieve" type="button" role="button" class="btn btn-primary">
										${sessionScope.languageJSON.leaveBalance.retrieve}
									</button>
							</div>
						</div>
					</form>
					<div class="form-group">
						<p class="error-hint hide" role="alert" aria-atomic="true" id="timeErrorMessage">
							${sessionScope.languageJSON.validator.fromDateNotGreaterToDate}
						</p>
					</div>
					<h2 class="table-top-title">
						<b>${sessionScope.languageJSON.label.unprocessedLeaveRequest}</b>
					</h2>
					<div class="hr-black"></div>
				
					<table class="table request-list responsive-table">
						<thead>
							<tr>
							    <th class="text-center" style="width:8%">${sessionScope.languageJSON.leaveRequest.startDate}</th>
								<th class="text-center" style="width:8%">${sessionScope.languageJSON.leaveRequest.endDate}</th>
							    <th class="text-center" style="width:8%">${sessionScope.languageJSON.leaveRequest.startTime}</th>
								<th class="text-center" style="width:8%">${sessionScope.languageJSON.leaveRequest.endTime}</th>
								<th style="width:10%">${sessionScope.languageJSON.leaveRequest.leaveType}</th>
								<th style="width:12%">${sessionScope.languageJSON.leaveRequest.absenceReason}</th>
								<th style="width:10%">${sessionScope.languageJSON.leaveRequest.leaveRequested}</th>
								<th style="width:20%" class="commentLog">${sessionScope.languageJSON.leaveRequest.commentLog}</th>
								<th style="width:10%">${sessionScope.languageJSON.leaveRequest.status}</th>
								<td></td>
							</tr>
						</thead>
						<tbody>
						<c:if test="${fn:length(leaves) > 0}">
							
							<c:forEach var="leave" items="${leaves}" varStatus="leaves">
								<tr>
									<td data-title="${sessionScope.languageJSON.leaveRequest.startDate}" class="text-center">${leave.LeaveStartDate}</td>
									<td data-title="${sessionScope.languageJSON.leaveRequest.endDate}" class="text-center">${leave.LeaveEndDate}</td>
								    <td data-title="${sessionScope.languageJSON.leaveRequest.startTime}" class="text-center">${leave.LeaveStartTime}</td>
									<td data-title="${sessionScope.languageJSON.leaveRequest.endTime}" class="text-center">${leave.LeaveEndTime}</td>
									<td data-title="${sessionScope.languageJSON.leaveRequest.leaveType}">
											<c:forEach var="type" items="${leaveTypes}" varStatus="count">
													<c:if test="${type.code == leave.LeaveType}">${type.description}</c:if>
											</c:forEach>
									</td>
									<td data-title="${sessionScope.languageJSON.leaveRequest.absenceReason}">
										<c:forEach var="abs" items="${absRsns}" varStatus="count">
													<c:if test="${abs.code == leave.AbsenseReason }">${abs.description}</c:if>
											</c:forEach>
									</td>
									<td data-title="${sessionScope.languageJSON.leaveRequest.leaveRequested}">
										${leave.lvUnitsUsed} 
										 <c:if test="${leave.daysHrs=='D'}">
                                             <span>${sessionScope.languageJSON.label.days}</span>
                                         </c:if>
                                          <c:if test="${leave.daysHrs=='H'}">
                                           <span>${sessionScope.languageJSON.label.hours}</span>
                                         </c:if>
									</td>
									<td data-title="${sessionScope.languageJSON.leaveRequest.commentLog}">
										<div>
												<c:forEach var="comment" items="${leave.comments}" varStatus="count">
														${comment.detail}<br/>
												</c:forEach>
										</div>
									</td>
									<td data-title="${sessionScope.languageJSON.leaveRequest.status}">${leave.statusDescr}</td>
									<td style="width:150px;">
											<c:if test="${leave.statusCd != 'A'}">
													<button class="btn btn-primary sm edit-btn" data-toggle="modal" data-target="#requestModal" 
													onClick='editLeave("${leave.id}","${leave.LeaveType}","${leave.AbsenseReason}","${leave.start}",
													"${leave.end}", "${leave.lvUnitsDaily}","${leave.lvUnitsUsed}")'>
														${sessionScope.languageJSON.label.edit}
													</button>
													<button class="btn btn-secondary sm"  data-toggle="modal" data-target="#deleteModal"  onClick="deleteLeave(${leave.id})">
														${sessionScope.languageJSON.label.delete}
													</button>
											</c:if>
										
									</td>
								</tr>
							</c:forEach>

							</c:if>
							<c:if test="${fn:length(leaves) == 0}">
                                 <tr>
                                     <td colspan="7" class="text-left">
                                         <span>${sessionScope.languageJSON.label.noData}</span>
                                     </td>
                                 </tr>
                             </c:if>
						</tbody>

					</table>
			
				<%-- <c:if test="${fn:length(leaves) == 0}">
					<div>
						${sessionScope.languageJSON.label.noData}
					</div>
				</c:if> --%>
			</div>
		</section>
			<form id="deleteForm" action="deleteLeaveRequest" method="post">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				<input type="hidden" value="<%=token %>" name="token"/>
				<input type="hidden" id="deleteId" name="id" aria-label="${sessionScope.languageJSON.accessHint.id}"/>
			</form>
		</main>
	</div>
	<%@ include file="../commons/footer.jsp"%>
	<%@ include file="../modal/eventModal.jsp"%>
	<%@ include file="../modal/deleteModal.jsp"%>
</body>
<script>
var leaveList = eval(${leaves});
var isAddValue = '${addRow}'
</script>
<script src="/<%=request.getContextPath().split("/")[1]%>/js/viewJs/leaveRequest/leaveRequest.js"></script>

</html>
