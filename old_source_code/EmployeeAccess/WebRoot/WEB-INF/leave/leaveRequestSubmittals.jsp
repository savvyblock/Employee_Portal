<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ea" uri="http://www.esc20.net/tags/employeeaccess-util"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<link rel="stylesheet" href="/CommonWeb/scripts/fullcalendar-2.8.0/fullcalendar.css" type="text/css" media="all" />
<link type="text/css" rel="stylesheet" href="<c:url value="/styles/leave.css" />" media="all" />

<script type="text/javascript" src="<spring:theme code="commonBase" />scripts/fullcalendar-2.8.0/lib/moment.min.js"></script>
<script type="text/javascript" src="<spring:theme code="commonBase" />scripts/fullcalendar-2.8.0/fullcalendar.min.js"></script>
<script type="text/javascript" src="<c:url value="/scripts/fixedHeadersTable.js" />"></script>
<script type="text/javascript" src="<c:url value="/scripts/leave.js" />"></script>
<script type="text/javascript" src="<c:url value="/scripts/leaveSubmittals.js" />"></script>

<div class="hoverCommentDiv"></div>
<div id="leaveBalancesDetail" class="leaveBalancesDetailDiv" style="visibility: hidden;" onclick="return hideDetailDiv(event,'leaveBalancesDetail');">

					<h3 style="margin: 0px;">Leave Balance Summary for <span id="employeeLabel"></span></h3>
					<div style="display: block;padding-right: 5px;">Payroll Frequency:&nbsp;<span id="payFrequency"></span></div><br/>
					<table id="leaveBalancesTable" class="addRowColor tabular marginless">
						<thead>
							<tr>
								<th class="leaverequest">Leave Type</th>
								<th class="leaverequest">Beginning Balance</th>
								<th class="leaverequest">Advanced/Earned</th>
								<th class="leaverequest">Pending Earned</th>
								<th class="leaverequest">Used</th>
								<th class="leaverequest">Pending Used</th>
								<th class="leaverequest">Available</th>
								<th class="leaverequest">Units</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td colspan="8" style="text-align: center;">No Rows</td>
							</tr>
						</tbody>
					</table>

</div>

<div id="mainDiv" style="width: 99%; padding: 10px 0px 0px 0px;">
	<div id="errorDiv" style="margin-left:8px">
		<div class="error">
			<spring:hasBindErrors name="LeaveRequestSubmittalsCommand">
				<p>
					<c:forEach items="${errors.fieldErrors}" var="error" varStatus="iterator">
						<spring:message text="${error.defaultMessage}" />
						<br />
						<input type="hidden" id="error_fieldName_${iterator.index}" value="${error.field}" />
						<input type="hidden" id="error_defaultMessage_${iterator.index}" value="${error.defaultMessage}" />
					</c:forEach>
				</p>
			</spring:hasBindErrors>
		</div>		
	</div>

	<form:form id="leaveRequestSubmittalsForm"
		modelAttribute="LeaveRequestSubmittalsCommand" method="POST"
		action="/EmployeeAccess/app/leave/leaveRequestSubmittals">

		<form:hidden path="leaveParameters.usePMIS" />
		<form:hidden path="leaveParameters.ignoreCutoffDates" />
		<form:hidden path="leaveParameters.urlEAHome" />
		
		<div class="box button_margins"
			style="display: inline-block;padding-left: 25px;padding-right: 25px;">
			<div id="supervisorViewDiv" style="margin: 0px;white-space: nowrap;">
				Supervisor Hierarchy:&nbsp;&nbsp;
				<c:forEach var="result"
					items="${LeaveRequestSubmittalsCommand.supervisorChain}"
					varStatus="row">
					<c:if test="${row.index > 0}">
						<span style='font-size: 16px;'>&nbsp;&#10141;&nbsp;</span>
					</c:if>
					<span>${LeaveRequestSubmittalsCommand.supervisorChain[row.index].selectOptionLabel}
						<form:hidden path="supervisorChain[${row.index}].firstName" />
						<form:hidden path="supervisorChain[${row.index}].middleName" />
						<form:hidden path="supervisorChain[${row.index}].lastName" />
						<form:hidden path="supervisorChain[${row.index}].employeeNumber" />
						<form:hidden path="supervisorChain[${row.index}].numDirectReports" />
						<form:hidden path="supervisorChain[${row.index}].selectOptionLabel" />
					</span>
				</c:forEach>
				<img id="calendarImage" alt="Calendar"
					src="<spring:theme code="commonBase" />images/calendar.png"
					class="calendarIcon hit_area"
					onclick='showCalendar(event);return false;' />
			</div>
			<form:input type="hidden" path="supervisorChainLevel" />
			<div style="display: table;">
				<div style="display: table-row;">
					<div style="display: table-cell;padding-right: 5px;">Direct
						Report Supervisors:</div>
					<div style="display: table-cell;padding-right: 5px;">
						<form:select cssClass="wrap_field" tabindex="1" path="selectedDirectReportEmployeeNumber" 
							style="width:240px;" onchange="selectDirectReportSupervisor(event);">
							<option value="">&nbsp;</option>
							<c:forEach var="result"
								items="${LeaveRequestSubmittalsCommand.directReportEmployees}"
								varStatus="row">
								<option
									<c:if test="${LeaveRequestSubmittalsCommand.directReportEmployees[row.index].employeeNumber eq LeaveRequestSubmittalsCommand.selectedDirectReportEmployeeNumber}">
    									selected='selected'  
    								</c:if>
									<c:if test="${LeaveRequestSubmittalsCommand.directReportEmployees[row.index].numDirectReports > 0}">
    									class="important" 
    								</c:if>
<%--	formerly, was showing all direct reports and not allowing selection of those who were not also supervisors; the java code now populates  --%>    	
<%--	direct reports with employees who are supervisors... or who have submitted requests to approve so no need to disable any of the options  --%>		
<%--									<c:if test="${LeaveRequestSubmittalsCommand.directReportEmployees[row.index].numDirectReports eq 0}">  --%>
<%--    									disabled="disabled"  --%>
<%--    								</c:if> --%>
   									value="${LeaveRequestSubmittalsCommand.directReportEmployees[row.index].employeeNumber}">
									${LeaveRequestSubmittalsCommand.directReportEmployees[row.index].selectOptionLabel}
								</option>
							</c:forEach>
						</form:select>
						<form:hidden path="selectDirectReportSelectOptionLabel"/>
					</div>
					<div style="display: table-cell;">
						<button id='prevLevel' tabindex="1" disabled="disabled"
							onclick='return previousSupervisorLevel(event);'>Previous
							Level</button>
					</div>
					<div style="display: table-cell;">
						<button id='nextLevel' tabindex="1" disabled="disabled"
							onclick='return nextSupervisorLevel(event);'>Next Level</button>
					</div>
				</div>
			</div>
		</div>
		<div id="buttonsDiv">
			<button id='saveApproverActionsButton' tabindex="1"
				onclick='return submitLeaveRequestSubmittalsSave(event);'
				disabled='disabled'>Save</button>
			<form:label path="" id="saveMessage" class="saveMessage">${saveSuccess}</form:label>
		</div>

		<h3 id='submittedLeaveRequestsHeading'>Leave Requests Pending
			Action
			by&nbsp;&nbsp;${LeaveRequestSubmittalsCommand.supervisorChain[LeaveRequestSubmittalsCommand.supervisorChainLevel].selectOptionLabel}</h3>


		<img id="commentEnteredImage" alt="+"
			src="<spring:theme code="commonBase" />images/comments.gif"
			class="buttonIcon" style="display:none;" />
		<div id="resultsDiv" class="button_margins"
			style="display: block;width: 100%;">
			<table id="submittedRequestsTable"
				style="width: 98%;margin: 0px;line-height: 0.75;">
				<tbody>
					<tr>
						<td>
							<div id="resultsTableDiv" class="filerecoverydiv">
								<table id="resultsTable"
									class="addRowColor tabular marginless fixedheadertable">
									<thead id="resultsTableTHead">
										<tr onmouseover="hideCommentDiv(event);">
											<th class="leaverequest" style="text-align: center;">Supervisor
												Action</th>
											<th class="leaverequest" style="text-align: center;">Supervisor
												Comment</th>
											<th class="leaverequest" style="text-align: center;">Employee</th>
											<th class="leaverequest" style="text-align: center;">Start
												Date</th>
											<th class="leaverequest" style="text-align: center;">End
												Date</th>
											<th class="leaverequest" style="text-align: center;">Start
												Time</th>
											<th class="leaverequest" style="text-align: center;">End
												Time</th>
											<th class="leaverequest" style="text-align: center;">Leave
												Type</th>
											<th class="leaverequest" style="text-align: center;">Absence
												Reason</th>
											<th class="leaverequest" style="text-align: center;">Leave
												Requested</th>
											<th class="leaverequest" style="text-align: center;">Comment
												Log</th>
										</tr>
									</thead>

									<tbody id="resultsTableTBody">
										<c:if
											test="${fn:length(LeaveRequestSubmittalsCommand.leaveRequests) == 0}">
											<tr>
												<td class="filerecovery" colspan="14"
													style="text-align: center;">No Rows</td>
											</tr>
										</c:if>
										<c:if
											test="${fn:length(LeaveRequestSubmittalsCommand.leaveRequests) > 0}">
											<c:forEach var="result"
												items="${LeaveRequestSubmittalsCommand.leaveRequests}"
												varStatus="row">
												<tr>
													<td class="leaverequestButton" style="text-align: center;white-space: nowrap;">
														<form:hidden path="leaveRequests[${row.index}].id" /> 
														<span class="approveSpan errorSpan" style="display: inline-block;width: 15ch;"> 
															<form:radiobutton class="radioapprove" tabindex="1" style="vertical-align: middle;" path="leaveRequests[${row.index}].approverAction"
																value="approve" onclick="approverActionClicked(event);" />
															&nbsp;Approve
														</span> 
														<span class="disapproveSpan errorSpan" style="display: inline-block;width: 17ch;"> 
															<form:radiobutton class="radioreject" tabindex="1" style="vertical-align: middle;" path="leaveRequests[${row.index}].approverAction"
																value="disapprove" onclick='approverActionClicked(event);' />
															&nbsp;Disapprove
														</span> 
														<span class="noactionSpan errorSpan" style="display: inline-block;width: 15ch;"> 
															<form:radiobutton class="radionoaction" tabindex="1" style="vertical-align: middle;" path="leaveRequests[${row.index}].approverAction"
																value="noaction" onclick='approverActionClicked(event);' />
															&nbsp;No Action
														</span>
													</td>
													<td style="text-align: center;">
														<button class="tableButton" tabindex="1"
															<c:if test="${LeaveRequestSubmittalsCommand.leaveRequests[row.index].approverAction eq 'noaction'}">
														disabled='disabled'
</c:if>
															onclick='editApproverComment(event);return false;'>Comment</button>
														<form:hidden
															path="leaveRequests[${row.index}].approverComment" />
													</td>
													<td>
														<div style="vertical-align: middle;white-space: nowrap;display: flex;">
															<div style="width: 32ch;overflow: hidden;">${LeaveRequestSubmittalsCommand.leaveRequests[row.index].employeeLabel}&nbsp;</div>

															<span
																style="float: right;display: inline-block;vertical-align: middle;"
																class="detailsRowButtonCenter"
																onclick='return showLeaveBalancesDetail(event);'>
															</span>
														</div> 
														<form:hidden path="leaveRequests[${row.index}].employeeNumber" />
														<form:hidden path="leaveRequests[${row.index}].employeeLabel" />
														<form:hidden path="leaveRequests[${row.index}].payFrequency" />
													</td>
													<td class="leaverequest"
														style="text-align: center;white-space: nowrap;">
														${LeaveRequestSubmittalsCommand.leaveRequests[row.index].fromDateString}
														<form:hidden path="leaveRequests[${row.index}].fromDateString" />
													</td>
													<td class="leaverequest"
														style="text-align: center;white-space: nowrap;">
														${LeaveRequestSubmittalsCommand.leaveRequests[row.index].toDateString}
														<form:hidden path="leaveRequests[${row.index}].toDateString" />
													</td>
													<td class="leaverequest" style="text-align: center;">
														${LeaveRequestSubmittalsCommand.leaveRequests[row.index].fromTimeString}
														<form:hidden path="leaveRequests[${row.index}].fromTimeString" />
													</td>
													<td class="leaverequest" style="text-align: center;">
														${LeaveRequestSubmittalsCommand.leaveRequests[row.index].toTimeString}
														<form:hidden path="leaveRequests[${row.index}].toTimeString" />
													</td>
													<td class="leaverequest"
														style="text-align: left;white-space: nowrap;">
														${LeaveRequestSubmittalsCommand.leaveRequests[row.index].leaveTypeDescription}
														<form:hidden path="leaveRequests[${row.index}].leaveType" />
													</td>
													<td class="leaverequest"
														style="text-align: left;white-space: nowrap;">
														${LeaveRequestSubmittalsCommand.leaveRequests[row.index].absenceReasonDescription}
														<form:hidden
															path="leaveRequests[${row.index}].absenceReason" />
													</td>
													<td class="leaverequest"
														style="text-align: right;padding-right: 20px !important;white-space: nowrap;"
														onmouseover="hideCommentDiv(event);">
														${LeaveRequestSubmittalsCommand.leaveRequests[row.index].leaveRequestedLabel}
														&nbsp; <c:if
															test="${LeaveRequestSubmittalsCommand.leaveRequests[row.index].leaveUnits eq 'D'}">													
    													DAYS
													</c:if> <c:if
															test="${LeaveRequestSubmittalsCommand.leaveRequests[row.index].leaveUnits eq 'H'}">													
    													HOURS
													</c:if>
													</td>
													<td class="leaverequest" style="text-align: left;width: 25px;"
														<c:if test="${fn:length(LeaveRequestSubmittalsCommand.leaveRequests[row.index].commentLog) == 0}">													
															onmouseover="hideCommentDiv(event);" 
														</c:if>
														>
														<div style="overflow: hidden;text-overflow: ellipsis;white-space: nowrap;word-wrap: break-word;width: 280px;"
															<c:if test="${fn:length(LeaveRequestSubmittalsCommand.leaveRequests[row.index].commentLog) > 0}">													
																onmouseover="showCommentDiv(event);"
															</c:if>
															>
															${LeaveRequestSubmittalsCommand.leaveRequests[row.index].newestComment}
															<c:if test="${fn:length(LeaveRequestSubmittalsCommand.leaveRequests[row.index].newestComment) == 0}">													
																&nbsp; 
															</c:if>

														</div> 
														<form:hidden path="leaveRequests[${row.index}].requestComment" /> 
														<pre style="display: none;">${LeaveRequestSubmittalsCommand.leaveRequests[row.index].commentLog}</pre>
													</td>
												</tr>
											</c:forEach>
										</c:if>
									</tbody>
								</table>
							</div>
						</td>
					</tr>
					<tr>
						<td class="tabular_footer" onmouseover="hideCommentDiv(event);"></td>
					</tr>
				</tbody>
			</table>
		</div>

		<div id="editCommentDialogDiv"
			class="section popup dialog hidden popup_dialog_div">
			<br />
			<textarea id="commentTextarea" rows="5" cols="60"
				style="width: 80em; height: 6em;" maxlength="400"></textarea>
		</div>

		<div id="calendarDiv" class="section popup dialog hidden popup_dialog_div" style="margin: 0px !important;">
			<div id='calendarPlugin'></div>
			<div id="detailDialog" style="display:none;">
			 	<table class="tabular marginless">
			 		<thead>
			 			<tr>
			 				<th style="vertical-align: bottom">From Time</th>
			 				<th style="vertical-align: bottom">To Time</th>
			 				<th style="vertical-align: bottom">Units</th>
			 				<th>Leave<br/>Code</th>
			 				<th style="vertical-align: bottom">Description</th>
			 				<th style="vertical-align: bottom">Reason</th>
			 				<th style="vertical-align: bottom">Status</th>
			 				<th style="vertical-align: bottom">Approver</th>
			 			</tr>
			 		</thead>
			 		<tbody>
			 			<tr>
			 				<td style="text-align:center"><span id="fromTime"></span></td>
			 				<td style="text-align:center"><span id="toTime"></span></td>
			 				<td style="text-align:center"><span id="units"></span></td>
			 				<td style="text-align:center"><span id="leaveCd"></span></td>
			 				<td><span id="descr"></span></td>
			 				<td><span id="reason"></span></td>
			 				<td><span id="status"></span></td>
			 				<td><span id="approver"></span></td>
			 			</tr>
			 			<tr>
			 				<td colspan="8">
			 				Comments: <span id="comments"></span>
			 				</td>
			 			</tr>
			 			<tr>
			 				<td colspan="8">
			 				Approver Comments: <span id="appComments"></span>
			 				</td>
			 			</tr>
			 		</tbody>
			 	</table>
			</div>			
		</div>
	</form:form>
</div>