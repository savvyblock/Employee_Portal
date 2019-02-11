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
<script type="text/javascript" src="<c:url value="/scripts/leaveManageStaff.js" />"></script>


<div class="hoverCommentDiv"></div>

<div id="mainDiv" style="width: 99%; padding: 10px 0px 0px 0px;">
	<div id="errorDiv" style="margin-left:8px">
		<spring:hasBindErrors name="LeaveRequestManageStaffCommand">
			<div class="error">
				<p>
					<c:forEach items="${errors.fieldErrors}" var="error">
						<spring:message text="${error.defaultMessage}" />
						<br />
					</c:forEach>
				</p>
			</div>
		</spring:hasBindErrors>
	</div>

	<form:form id="leaveRequestStaffRequestsForm"
		modelAttribute="LeaveRequestManageStaffCommand" method="POST"
		action="/EmployeeAccess/app/leave/leaveRequestManageStaff">

		<form:hidden path="leaveParameters.standardHours" />
		<form:hidden path="leaveParameters.mealBreakHours" />
		<form:hidden path="leaveParameters.requireLeaveHoursRequestedEntry" />
		<form:hidden path="leaveParameters.usePMIS" />
		<form:hidden path="leaveParameters.ignoreCutoffDates" />
		<form:hidden path="leaveParameters.urlEAHome" />
		
		<div class="box button_margins"
			style="display: inline-block;padding-left: 25px;padding-right: 25px;">
			<div id="supervisorViewDiv" style="margin: 0px;white-space: nowrap;">
				Supervisor Hierarchy:&nbsp;&nbsp;
				<c:forEach var="result" items="${LeaveRequestManageStaffCommand.supervisorChain}" varStatus="row">
					<c:if test="${row.index > 0}">
						<span style='font-size: 16px;'>&nbsp;&#10141;&nbsp;</span>
					</c:if>
					<span>${LeaveRequestManageStaffCommand.supervisorChain[row.index].selectOptionLabel}
						<form:hidden path="supervisorChain[${row.index}].firstName" />
						<form:hidden path="supervisorChain[${row.index}].middleName" />
						<form:hidden path="supervisorChain[${row.index}].lastName" />
						<form:hidden path="supervisorChain[${row.index}].employeeNumber" />
						<form:hidden path="supervisorChain[${row.index}].numDirectReports" /> 
						<form:hidden path="supervisorChain[${row.index}].selectOptionLabel" />
					</span>
				</c:forEach>
				<!-- 				<button id='calendarButton' onclick='showCalendar(event);return false;'>Calendar</button> -->
				<!--  								<button id='calendarButton' onclick='showCalendar(event);return false;' style="min-width: 28px !important;height: 28px !important; margin: 0px !important;padding: 3px 0px 3px 3px !important;"> -->
				<img id="calendarImage" alt="Calendar"
					src="<spring:theme code="commonBase" />images/calendar.png"
					class="calendarIcon hit_area"
					onclick='showCalendar(event);return false;' />
				<!--  								</button> -->

			</div>
			<form:input type="hidden" path="supervisorChainLevel" />
			<div style="display: table;">
				<div style="display: table-row;">
					<div style="display: table-cell;padding-right: 5px;">Direct
						Report Employees:</div>
					<div style="display: table-cell;padding-right: 5px;">
						<form:select cssClass="wrap_field" tabindex="1" path="selectedDirectReportEmployeeNumber" 
							style="width:240px;" onchange="selectDirectReport(event);">
							<option value="">&nbsp;</option>
							<c:forEach var="result"
								items="${LeaveRequestManageStaffCommand.directReportEmployees}"
								varStatus="row">
								<option
									<c:if test="${LeaveRequestManageStaffCommand.directReportEmployees[row.index].employeeNumber eq LeaveRequestManageStaffCommand.selectedDirectReportEmployeeNumber}">
    									selected='selected'  
    								</c:if>
									<c:if test="${LeaveRequestManageStaffCommand.directReportEmployees[row.index].numDirectReports > 0}">
    									class="important" 
    								</c:if>
									value="${LeaveRequestManageStaffCommand.directReportEmployees[row.index].employeeNumber}">
									${LeaveRequestManageStaffCommand.directReportEmployees[row.index].selectOptionLabel}
								</option>
							</c:forEach>
						</form:select>
						<form:hidden path="selectDirectReportSelectOptionLabel"/>
					</div>
					<div style="display: table-cell;">
						<button id='prevLevel' disabled="disabled" tabindex="1"
							onclick='return previousSupervisorLevel(event);'>Previous
							Level</button>
					</div>
					<div style="display: table-cell;">
						<button id='nextLevel' disabled="disabled" tabindex="1"
							onclick='return nextSupervisorLevel(event);'>Next Level</button>
					</div>
				</div>
			</div>
		</div>
		<div class="button_margins">
			<div style="display: table-cell;padding-right: 5px;">Payroll
				Frequency:</div>
			<div style="display: table-cell;padding-right: 5px;">
				<form:select path="selectedPayFrequencyCode" style="width:160px;"
					tabindex="1" onchange="submitPayrollChange();">
					<c:if
						test="${fn:length(LeaveRequestManageStaffCommand.userPayFrequencies) == 0}">
						<option value="">&nbsp;</option>
					</c:if>
					<c:if
						test="${fn:length(LeaveRequestManageStaffCommand.userPayFrequencies) > 0}">
						<form:options
							items="${LeaveRequestManageStaffCommand.userPayFrequencies}"
							itemLabel="description" itemValue="code" />
					</c:if>
				</form:select>
			</div>
		</div>



		<div id="buttonsDiv" style="display: inline-block;">
			<button id='createRequestsButton' tabindex="1"
				onclick='createLeaveRequests(event);return false;'>Add</button>
			<button id='editRequestsButton' tabindex="1"
				onclick='editLeaveRequests(event);return false;'>Edit</button>
			<button id='deleteRequestsButton' tabindex="1"
				onclick='deleteLeaveRequests(event);return false;'>Delete</button>
			<div class="manage_requests_filterbox">
				<div style="display: table-cell;padding-right: 10px;valign: middle;">
					<span class="important">Leave Requests Filter</span>
				</div>
				<div style="display: table-cell;padding-right: 5px;valign: middle;">
					From Date:</div>
				<div style="display: table-cell;padding-right: 10px;valign: middle;">
					<form:input type="text" path="fromDateFilterString" edit-mask="##-##-####"
						cssClass="text center_align popupDatepicker" tabindex="1"
						maxlength="10" size="10" onclick="onClickDateField(event);" 
						onkeyup="return checkAutoTabDate(event);"/>
				</div>
				<div style="display: table-cell;padding-right: 5px;valign: middle;">
					To Date:</div>
				<div style="display: table-cell;padding-right: 10px;valign: middle;">
					<form:input type="text" path="toDateFilterString" edit-mask="##-##-####"
						cssClass="text center_align popupDatepicker" tabindex="1"
						maxlength="10" size="10" onclick="onClickDateField(event);" 
						onkeyup="return checkAutoTabDate(event);"/>
				</div>
				<div style="display: table-cell;padding-right: 5px;valign: middle;">
					<button id='filterRequestsButton' tabindex="1" onclick='return filterRequests(event);' >Filter</button>
					<button id='resetFilterRequestsButton' tabindex="1" onclick='return resetFilter(event);' >Reset</button>
				</div>
			</div>
		</div>
		<h3 id='unprocessedLeaveRequestsHeading'>
		<c:if test="${fn:length(LeaveRequestManageStaffCommand.selectedDirectReportEmployeeNumber) > 0}">
			Leave Requests for ${LeaveRequestManageStaffCommand.selectDirectReportSelectOptionLabel}
		</c:if>
		<c:if test="${fn:length(LeaveRequestManageStaffCommand.selectedDirectReportEmployeeNumber) == 0}">
			Leave Requests
		</c:if>
		</h3>
		<img id="commentEnteredImage" alt="+"
			src="<spring:theme code="commonBase" />images/comments.gif"
			class="buttonIcon" style="display:none;" />
		<div id="resultsDiv" class="button_margins"
			style="display: block;width: 100%;">
			<table style="width: 98%;margin: 0px;line-height: 0.75;">
				<tbody>
					<tr>
						<td>
							<div id="resultsTableDiv" class="filerecoverydiv">
								<table id="resultsTable"
									class="addRowColor tabular marginless fixedheadertable">
									<thead id="resultsTableTHead">
										<tr onmouseover="hideCommentDiv(event);">
											<th class="leaverequest"
												style="vertical-align: center; text-align: center;"><c:if
													test="${fn:length(LeaveRequestManageStaffCommand.leaveRequests) == 0}">
													<input type="checkbox" id="selectall" disabled='disabled' />
												</c:if> <c:if
													test="${fn:length(LeaveRequestManageStaffCommand.leaveRequests) > 0}">
													<input type="checkbox" id="selectall" tabindex="-1" 
														onclick='toggleCheckboxes("resultsTableDiv");' />
												</c:if></th>
											<th class="leaverequest" style="text-align: center;">Start
												Date</th>
											<th class="leaverequest" style="text-align: center;">End
												Date</th>
											<th class="leaverequest" style="text-align: center;">Start
												Time</th>
											<th class="leaverequest" style="text-align: center;">End
												Time</th>
											<th class="leaverequest" style="text-align: center;">
												Leave Type</th>
											<th class="leaverequest" style="text-align: center;">Absence
												Reason</th>
											<!-- 											<th class="leaverequest" style="text-align: center;"> -->
											<!-- 												Leave Requested</th> -->
											<th class="leaverequest" style="text-align: center;">
												Leave Requested</th>
											<th class="leaverequest" style="text-align: center;">
												Comment Log</th>
											<th class="leaverequest" style="text-align: center;">
												Status</th>
										</tr>
									</thead>

									<tbody id="resultsTableTBody">
										<c:if
											test="${fn:length(LeaveRequestManageStaffCommand.leaveRequests) == 0}">
											<tr>
												<td class="filerecovery" colspan="11"
													style="text-align: center;">No Rows</td>
											</tr>
										</c:if>
										<c:if
											test="${fn:length(LeaveRequestManageStaffCommand.leaveRequests) > 0}">
											<c:forEach var="result"
												items="${LeaveRequestManageStaffCommand.leaveRequests}"
												varStatus="row">
												<tr>
													<td style="text-align: center;">
														<c:if test="${LeaveRequestManageStaffCommand.leaveRequests[row.index].editable}">
															<form:checkbox
																cssClass="case" tabindex="1" 
																path="leaveRequests[${row.index}].selected"
																onclick='checkboxClicked("resultsTableDiv");' /> 
														</c:if>
														<form:hidden path="leaveRequests[${row.index}].id" />
													</td>
													<td class="leaverequest"
														style="text-align: center;white-space: nowrap;">
														${LeaveRequestManageStaffCommand.leaveRequests[row.index].fromDateString}
														<form:hidden path="leaveRequests[${row.index}].fromDateString"/>
													</td>
													<td class="leaverequest"
														style="text-align: center;white-space: nowrap;">
														${LeaveRequestManageStaffCommand.leaveRequests[row.index].toDateString}
														<form:hidden path="leaveRequests[${row.index}].toDateString"/>
													</td>
													<td class="leaverequest" style="text-align: center;">
														<c:if test="${LeaveRequestManageStaffCommand.leaveRequests[row.index].id !=0}">
															${LeaveRequestManageStaffCommand.leaveRequests[row.index].fromTimeString}
															<form:hidden path="leaveRequests[${row.index}].fromTimeString"/>
														</c:if>
														<c:if test="${LeaveRequestManageStaffCommand.leaveRequests[row.index].id ==0}">
															-
														</c:if>
													</td>
													<td class="leaverequest" style="text-align: center;">
														<c:if test="${LeaveRequestManageStaffCommand.leaveRequests[row.index].id !=0}">
															${LeaveRequestManageStaffCommand.leaveRequests[row.index].toTimeString}
															<form:hidden path="leaveRequests[${row.index}].toTimeString"/>
														</c:if>
														<c:if test="${LeaveRequestManageStaffCommand.leaveRequests[row.index].id ==0}">
															-
														</c:if>
													</td>
													<td class="leaverequest"
														style="text-align: left;white-space: nowrap;">
														${LeaveRequestManageStaffCommand.leaveRequests[row.index].leaveTypeDescription}
														<form:hidden path="leaveRequests[${row.index}].leaveType" />
													</td>
													<td class="leaverequest"
														style="text-align: left;white-space: nowrap;">
														${LeaveRequestManageStaffCommand.leaveRequests[row.index].absenceReasonDescription}
														<form:hidden
															path="leaveRequests[${row.index}].absenceReason" />
													</td>
													<td class="leaverequest"
														style="text-align: right;padding-right: 20px !important;white-space: nowrap;"
														onmouseover="hideCommentDiv(event);">
														${LeaveRequestManageStaffCommand.leaveRequests[row.index].leaveRequestedLabel}
														&nbsp; 
														<c:if test="${LeaveRequestManageStaffCommand.leaveRequests[row.index].leaveUnits eq 'D'}">													
    														DAYS
														</c:if> 
														<c:if test="${LeaveRequestManageStaffCommand.leaveRequests[row.index].leaveUnits eq 'H'}">													
    														HOURS
														</c:if> 
														<form:hidden path="leaveRequests[${row.index}].leaveHoursDaily" /> 
														<form:hidden path="leaveRequests[${row.index}].leaveNumberDays" />
													</td>

													<td class="leaverequest"
														style="text-align: left;width: 300px;"
														<c:if test="${fn:length(LeaveRequestManageStaffCommand.leaveRequests[row.index].commentLog) == 0}">													
														onmouseover="hideCommentDiv(event);" 
</c:if>>
														<div
															style="overflow: hidden;text-overflow: ellipsis;white-space: nowrap;word-wrap: break-word;width: 280px;"
															<c:if test="${fn:length(LeaveRequestManageStaffCommand.leaveRequests[row.index].commentLog) > 0}">													
														onmouseover="showCommentDiv(event);"
</c:if>>
															${LeaveRequestManageStaffCommand.leaveRequests[row.index].newestComment}
															<c:if
																test="${fn:length(LeaveRequestManageStaffCommand.leaveRequests[row.index].newestComment) == 0}">													
														&nbsp; 
</c:if>
														</div> <form:hidden
															path="leaveRequests[${row.index}].requestComment" /> <pre
															style="display: none;">${LeaveRequestManageStaffCommand.leaveRequests[row.index].commentLog}</pre>
													</td>

													<td class="leaverequest"
														style="text-align: left;white-space: nowrap;"
														onmouseover="hideCommentDiv(event);">
														${LeaveRequestManageStaffCommand.leaveRequests[row.index].statusDescription}
														<form:hidden path="leaveRequests[${row.index}].status" />
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
						<td class="tabular_footer" onmouseover="hideCommentDiv(event);">
						</td>
					</tr>
				</tbody>
			</table>
		</div>

		<div id="createEditRequestsDialogDiv" class="section popup dialog popup_dialog_div" myform="reportSortFilterForm">
			<input id="userAction" class="hidden" value="default">
			<div id="leaveTypeDetail" class="leaveTypeDetailDiv" style="visibility: hidden;"  onclick="return hideDetailDiv(event,'leaveTypeDetail');"></div>
			<div id="errorDiv" style="margin-left:8px">
					<div class="error">
						<p>
						</p>
					</div>
			</div>

			<div class='request_dialog_buttons_div'>
				<button id="addRequestRowButton" tabindex="1" onclick="addRequestRow(event);"></button>
				<button id="deleteRequestRowButton" tabindex="1" onclick="deleteRequestRow(event);"></button>
			</div>

			<table id='createEditRequestsTable'
				class='tabular sortFilterDialog_typography '>

				<thead>
					<tr>
						<th class='createTableCol'><input type='checkbox'
							id='selectall'
							onclick='toggleCheckboxes("createEditRequestsTable");' /></th>
						<th>Start Date</th>
						<th>End Date</th>
						<th>Start Time</th>
						<th>End Time</th>
						<th>Leave Type</th>
						<th>Absence Reason</th>
						<th>Hours/Day Requested</th>
						<th>Total Requested</th>
						<th>Requester Comment</th>
					</tr>
				</thead>

				<tbody>
					<tr class='create_edit_requests_no_rows'>
						<td colspan='10'>No Rows</td>
					</tr>
				</tbody>
			</table>
			<br />

			<table id="emptyLeaveRequestTable" style="display: none;">
				<tbody>

					<tr>
						<td class='createTableCol' style="text-align: center;"><form:checkbox
								cssClass="case" path="emptyLeaveRequestRow[0].selected"
								onclick='checkboxClicked("createEditRequestsTable");' /> <form:hidden
								path="emptyLeaveRequestRow[0].id" /> <input type="hidden"
							class="rowNumber" value="0" /></td>
						<td><form:input type="text" tabindex="1" 
								path="emptyLeaveRequestRow[0].fromDate" edit-mask="##-##-####"
								cssClass="text center_align popupDatepicker" maxlength="10"
								size="10" onclick="onClickDateField(event);"
								onBlur="calculateLeaveUnitsRequested(event);" 
								onkeyup="return checkAutoTabDate(event);" />
						</td>
						<td><form:input type="text" tabindex="1" 
								path="emptyLeaveRequestRow[0].toDate"
								class="text center_align popupDatepicker" edit-mask="##-##-####"
								maxlength="10" size="10" onclick="onClickDateField(event);"
								onBlur="calculateLeaveUnitsRequested(event);" 
								onkeyup="return checkAutoTabDate(event);"/>
						</td>
						<td>
							<form:input type="text" tabindex="1" 
								path="emptyLeaveRequestRow[0].fromHour"
								class="text center_align" maxlength="2" size="3"
								onkeypress="return isHourKey(event)" onkeyup="return checkAutoTab(event, 2);"
								onblur="onBlurHourEntry(event); calculateLeaveUnitsRequested(event);" />
							<span class="label">:</span> 
							<form:input type="text" tabindex="1" 
								path="emptyLeaveRequestRow[0].fromMinute"
								class="text center_align" maxlength="2" size="3"
								onkeypress="return isMinuteKey(event)" onkeyup="return checkAutoTab(event, 2);"
								onblur="onBlurMinuteEntry(event); calculateLeaveUnitsRequested(event);" />
							<span> 
								<form:select tabindex="1" 
									path="emptyLeaveRequestRow[0].fromAmPm" style="width:50px;"
									onChange="calculateLeaveUnitsRequested(event);">
									<form:option value="AM" label="AM" />
									<form:option value="PM" label="PM" />
								</form:select>
							</span>
						</td>
						<td>
							<form:input type="text" tabindex="1" 
								path="emptyLeaveRequestRow[0].toHour" class="text center_align"
								maxlength="2" size="3" onkeypress="return isHourKey(event)" onkeyup="return checkAutoTab(event, 2);"
								onblur="onBlurHourEntry(event); calculateLeaveUnitsRequested(event);" />
							<span class="label">:</span> 
							<form:input type="text" tabindex="1" 
								path="emptyLeaveRequestRow[0].toMinute"
								class="text center_align" maxlength="2" size="3"
								onkeypress="return isMinuteKey(event)" onkeyup="return checkAutoTab(event, 2);"
								onblur="onBlurMinuteEntry(event); calculateLeaveUnitsRequested(event);" />
							<span> 
								<form:select tabindex="1" 
									path="emptyLeaveRequestRow[0].toAmPm" style="width:50px;"
									onChange="calculateLeaveUnitsRequested(event);">
									<form:option value="AM" label="AM" />
									<form:option value="PM" label="PM" />
								</form:select>
							</span>
						</td>
						<td>
							<div style="vertical-align: middle;white-space: nowrap;display: flex;">
								<span>
									<form:select tabindex="1" path="emptyLeaveRequestRow[0].leaveType"
										style="width:160px;float: left;display: inline-block;"
										onchange="adjustLeaveTypeAbsenceReasonOptions(event); calculateLeaveUnitsRequested(event);">
										<form:option value="" label="" />
										<form:options
											items="${LeaveRequestManageStaffCommand.leaveTypes}"
											itemValue="lvType" itemLabel="lvTypeDescription"
											itemUnits="lvTypeUnits" />
									</form:select>
								</span>
								<span
									style="float: right;display: inline-block;vertical-align: middle;"
									class="detailsRowButtonCenter"
									onclick='return showLeaveTypeDetail(event);'>
								</span>
							</div>
							<form:select path="emptyLeaveRequestRow[0].leaveUnits"
								style="display: none;">
								<form:option value="" label="-" />
								<form:options
									items="${LeaveRequestManageStaffCommand.leaveTypes}"
									itemValue="lvType" itemLabel="lvTypeUnits" />
							</form:select>
						</td>
						<td>
							<span> 
								<form:select tabindex="1" 
									path="emptyLeaveRequestRow[0].absenceReason"
									style="width:160px;"
									onchange="adjustLeaveTypeAbsenceReasonOptions(event);">
									<form:option value="" label="" />
									<form:options
										items="${LeaveRequestManageStaffCommand.absenceReasons}"
										itemValue="absRsn" itemLabel="absRsnDescription" />
								</form:select>
							</span>
						</td>

						<td>
							<form:input type="text" tabindex="1" 
								path="emptyLeaveRequestRow[0].leaveHoursDaily" maxlength="8"
								size="10" onkeypress="return isPosDecimalKey(event)"
								value="0.000"
								onblur="calculateLeaveUnitsRequestedFromDailyEH(event);"
								onkeyup="return checkAutoTabDecimal(event, 3);" />
							<form:hidden path="emptyLeaveRequestRow[0].leaveNumberDays" />
						</td>
						<td style="text-align: right;padding-right: 20px !important;white-space: nowrap;">
							<span id="emptyLeaveRequestRowLeaveRequestedSpan0"
								style="width: 8em;">0.000</span>&nbsp;
							<span id="emptyLeaveRequestRowLeaveUnitSpan0" style="width: 5em;"></span>
						</td>
						<td>
							<button tabindex="1" class="tableButton"
								onclick="editComment(event,'Requester Comment',false);">Comment</button>
							<form:hidden path="emptyLeaveRequestRow[0].requestComment" />
						</td>
					</tr>
				</tbody>
			</table>
			<div style="display: table-cell;">
				<h3 style="margin: 0px;">Leave Balance Summary</h3>
				<table class="addRowColor tabular marginless">
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
						<c:if
							test="${fn:length(LeaveRequestManageStaffCommand.leaveInfos) == 0}">
							<tr>
								<td colspan="8" style="text-align: center;">No Rows</td>
							</tr>
						</c:if>
						<c:if
							test="${fn:length(LeaveRequestManageStaffCommand.leaveInfos) > 0}">
							<c:forEach items="${LeaveRequestManageStaffCommand.leaveInfos}"
								var="leaveInfo">
								<tr>
									<td class="leaverequest">${leaveInfo.type.displayLabel}</td>
									<td class="leaverequest" style="text-align: right;">${leaveInfo.beginBalanceLabel}</td>
									<td class="leaverequest" style="text-align: right;">${leaveInfo.advancedEarnedLabel}</td>
									<td class="leaverequest" style="text-align: right;">${leaveInfo.pendingEarnedLabel}</td>
									<td class="leaverequest" style="text-align: right;">${leaveInfo.usedLabel}</td>
									<td class="leaverequest" style="text-align: right;">${leaveInfo.totalPendingUsedLabel}</td>
									<td class="leaverequest" style="text-align: right;">${leaveInfo.availableBalanceLabel}</td>
									<td class="leaverequest">
										<c:if test="${leaveInfo.daysHrs eq 'D'}">													
    											DAYS
										</c:if> 
										<c:if test="${leaveInfo.daysHrs eq 'H'}">													
												HOURS
										</c:if>
									</td>
								</tr>
							</c:forEach>
						</c:if>


					</tbody>
				</table>
			</div>
			<div style="display: table-cell;width: 20px;min-width: 20px;"></div>
			<div style="display: table-cell;width: 20px;min-width: 20px;"></div>
			
			<div id="editCommentDialogDiv"
				class="section popup dialog hidden popup_dialog_div">
				<br />
				<textarea id="commentTextarea" rows="5" cols="60"
					style="width: 80em; height: 6em;" maxlength="400"></textarea>
			</div>
		</div>

		<div id="deleteConfirmationDiv"
			class="section popup dialog hidden popup_dialog_div">
			<br />
			<p>Are you sure you want to delete the selected leave requests?</p>

		</div>

		<div id="calendarDiv" class="section popup dialog hidden popup_dialog_div" style="margin: 0px !important;">
			<form:form id="leaveRequestsForm" modelAttribute="LeaveRequestCalendarCommand" method="POST" action="/EmployeeAccess/app/leave/leaveRequestCalendar">
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



			</form:form>
		</div>


	</form:form>
</div>