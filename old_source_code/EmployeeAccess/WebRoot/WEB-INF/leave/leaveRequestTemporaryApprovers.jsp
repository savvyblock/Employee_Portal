<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ea" uri="http://www.esc20.net/tags/employeeaccess-util"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<link rel="stylesheet" href="/CommonWeb/scripts/fullcalendar-2.8.0/fullcalendar.css" type="text/css" media="all" />
<link type="text/css" rel="stylesheet" href="<c:url value="/styles/leave.css" />" media="all" />

<script type="text/javascript" src="<spring:theme code="commonBase" />scripts/fullcalendar-2.8.0/lib/moment.min.js"></script>
<script type="text/javascript" src="<spring:theme code="commonBase" />scripts/fullcalendar-2.8.0/fullcalendar.min.js"></script>
<script type="text/javascript" src="<c:url value="/scripts/fixedHeadersTable.js" />"></script>
<script type="text/javascript" src="<c:url value="/scripts/leave.js" />"></script>
<script type="text/javascript" src="<c:url value="/scripts/leaveTemporaryApprovers.js" />"></script>

<div id="mainDiv" style="width: 99%; padding: 10px 0px 0px 0px;">
	<div id="errorDiv" style="margin-left:8px">
		<spring:hasBindErrors name="LeaveRequestTemporaryApproversCommand">
			<!-- 			<div class="error"> -->
			<!-- 				<p> -->
			<c:forEach items="${errors.fieldErrors}" var="error"
				varStatus="iterator">

				<input type="hidden" id="error_fieldName_${iterator.index}"
					value="${error.field}" />
				<input type="hidden" id="error_defaultMessage_${iterator.index}"
					value="${error.defaultMessage}" />

				<!-- 						<spring:message text="${error.defaultMessage}" /> -->
				<!-- 						<br /> -->
			</c:forEach>
			<!-- 				</p> -->
			<!-- 			</div> -->
		</spring:hasBindErrors>
	</div>
	<form:form id="leaveTemporaryApproversForm"
		modelAttribute="LeaveRequestTemporaryApproversCommand" method="POST"
		action="/EmployeeAccess/app/leave/leaveRequestTemporaryApprovers">

		<form:hidden path="leaveParameters.usePMIS" />

		<div class="box button_margins"
			style="display: inline-block;padding-left: 25px;padding-right: 25px;">
			<div id="supervisorViewDiv" class="supervisorHierarchyDiv">
				Supervisor Hierarchy:&nbsp;&nbsp;
				<c:forEach var="result"
					items="${LeaveRequestTemporaryApproversCommand.supervisorChain}"
					varStatus="row">
					<c:if test="${row.index > 0}">
						<span style='font-size: 16px;'>&nbsp;&#10141;&nbsp;</span>
					</c:if>
					<span>${LeaveRequestTemporaryApproversCommand.supervisorChain[row.index].selectOptionLabel}
						<form:hidden path="supervisorChain[${row.index}].firstName" />
						<form:hidden path="supervisorChain[${row.index}].middleName" />
						<form:hidden path="supervisorChain[${row.index}].lastName" />
						<form:hidden path="supervisorChain[${row.index}].employeeNumber" />
						<form:hidden path="supervisorChain[${row.index}].numDirectReports" />
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
							style="width:240px;"
							onchange="selectDirectReportSupervisor(event);">
							<option value="">&nbsp;</option>
							<c:forEach var="result"
								items="${LeaveRequestTemporaryApproversCommand.directReportEmployees}"
								varStatus="row">
								<option
									<c:if test="${LeaveRequestTemporaryApproversCommand.directReportEmployees[row.index].employeeNumber eq LeaveRequestTemporaryApproversCommand.selectedDirectReportEmployeeNumber}">
    									selected='selected'  
    								</c:if>
									<c:if test="${LeaveRequestTemporaryApproversCommand.directReportEmployees[row.index].numDirectReports > 0}">
    									class="important" 
    								</c:if>
   									value="${LeaveRequestTemporaryApproversCommand.directReportEmployees[row.index].employeeNumber}">
									${LeaveRequestTemporaryApproversCommand.directReportEmployees[row.index].selectOptionLabel}
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
		
		<div id="buttonsDiv">
			<button id='saveButton' tabindex="1" onclick='return submitSave(event);'>Save</button>
			<button id='resetButton' tabindex="1"  type="button" onclick='return submitReset(event);'>Reset</button>

			<button id="errors" value="errors" class="hidden"
				style="border-style: solid;border-color:red;border-width: 2px; margin-top: 0px"
				onclick="return false;">Errors</button>
			<form:label path="" id="saveMessage" class="saveMessage">${saveSuccess}</form:label>
		</div>

		<h3 id='temporaryApproversHeading'>Temporary Approvers for&nbsp;&nbsp;${LeaveRequestTemporaryApproversCommand.supervisorChain[LeaveRequestTemporaryApproversCommand.supervisorChainLevel].selectOptionLabel}</h3>

		<div id="resultsDiv" class="button_margins"
			style="display: block;width: 600px;">
			<table style="width: 98%;margin: 0px;line-height: 0.75;">
				<tbody>
					<tr>
						<td>
							<div id="resultsTableDiv" class="filerecoverydiv">
								<table id="resultsTable"
									class="addRowColor tabular marginless fixedheadertable">
									<thead id="resultsTableTHead">
										<tr>
											<th class="leaverequest"
												style="vertical-align: center; text-align: center;">Delete</th>
											<th class="leaverequest" style="text-align: center;">Row
												Nbr</th>
											<th class="leaverequest" style="text-align: center;">Temporary
												Approver</th>
											<th class="leaverequest" style="text-align: center;">From
												Date</th>
											<th class="leaverequest" style="text-align: center;">To
												Date</th>
										</tr>
									</thead>
									<tbody id="resultsTableTBody">
										<c:choose>
											<c:when
												test="${fn:length(LeaveRequestTemporaryApproversCommand.temporaryApprovers) > 0}">
												<c:forEach var="result"
													items="${LeaveRequestTemporaryApproversCommand.temporaryApprovers}"
													varStatus="row">
													<tr
														<c:if test="${LeaveRequestTemporaryApproversCommand.temporaryApprovers[row.index].deleteIndicated==true}">
													class="deleteRowIndicated"
</c:if>>
														<td style="text-align: center;"><span
															id="deleteRowSpan_${row.index}" index="${row.index}"
															<c:if test="${LeaveRequestTemporaryApproversCommand.temporaryApprovers[row.index].deleteIndicated==false}">

															class="deleteRowButton" 
</c:if>
															<c:if test="${LeaveRequestTemporaryApproversCommand.temporaryApprovers[row.index].deleteIndicated==true}">

															class="deleteRowButtonSelected" 
</c:if>
															onclick="toggleTempApproverDeleteRow(event);">
														</span> <form:hidden
																path="temporaryApprovers[${row.index}].deleteIndicated" />
															<form:hidden path="temporaryApprovers[${row.index}].id" />
															<form:hidden
																path="temporaryApprovers[${row.index}].modified" /></td>
														<td class="leaverequest" style="text-align: center;">
															${row.index+1}</td>
														<td class="leaverequest"
															style="text-align: left;white-space: nowrap;"><form:input
																type="text" class="employeeTempApproverAutoComplete"
																path="temporaryApprovers[${row.index}].temporaryApprover.autoCompleteString"
																maxlength="50" size="50"
																tabindex="1" 
																onblur="onBlurTempApproverEntry(event); onBlurSetModified(event);" />
															<form:input type="hidden"
																path="temporaryApprovers[${row.index}].temporaryApprover.employeeNumber" />
														</td>
														<td class="leaverequest" style="text-align: center;">
															<form:input type="text"
																path="temporaryApprovers[${row.index}].fromDateString"
																edit-mask="##-##-####"
																cssClass="text edit_mask center_align popupDatepicker"
																maxlength="10" size="10"
																tabindex="1" 
																onblur="onBlurSetModified(event);" onkeyup="return checkAutoTabDate(event);"
																onclick="onClickDateField(event)" />
														</td>
														<td class="leaverequest" style="text-align: center;">

															<form:input type="text"
																path="temporaryApprovers[${row.index}].toDateString"
																edit-mask="##-##-####"
																cssClass="text edit_mask center_align popupDatepicker"
																maxlength="10" size="10"
																tabindex="1" 
																onblur="onBlurSetModified(event);" onkeyup="return checkAutoTabDate(event);"
																onclick="onClickDateField(event)" />
														</td>
													</tr>
												</c:forEach>
											</c:when>
											<c:otherwise>

												<tr class="no_rows">
													<td class="filerecovery" colspan="5"
														style="text-align: center;">No Rows</td>
												</tr>

											</c:otherwise>
										</c:choose>
									</tbody>
								</table>


								<table id="emptyTemporaryApproverTable" style="display: none;">
									<tbody>
										<tr>
											<td style="text-align: center;"><span
												id="emptyTempApproverRowDeleteRowSpan_0" index="0"
												class="deleteRowButton"
												onclick="toggleTempApproverDeleteRow(event);"></span>
												<form:hidden path="emptyTempApproverRow[0].deleteIndicated" />
												<form:hidden path="emptyTempApproverRow[0].id" /> <form:hidden
													path="emptyTempApproverRow[0].modified" value="true" /></td>
											<td class="leaverequest row_number"
												style="text-align: center;">0</td>
											<td class="leaverequest"
												style="text-align: left;white-space: nowrap;"><form:input
													type="text"
													path="emptyTempApproverRow[0].temporaryApprover.autoCompleteString"
													maxlength="50" size="50"
													tabindex="1" 
													onblur="onBlurTempApproverEntry(event); onBlurSetModified(event);" />
												<form:input type="hidden"
													path="emptyTempApproverRow[0].temporaryApprover.employeeNumber" />
											</td>
											<td class="leaverequest" style="text-align: center;"><form:input
													type="text" path="emptyTempApproverRow[0].fromDateString"
													edit-mask="##-##-####"
													cssClass="text center_align popupDatepicker" maxlength="10"
													size="10"
													tabindex="1" 
													onblur="onBlurSetModified(event);"
													onclick="onClickDateField(event)" onkeyup="return checkAutoTabDate(event);"/></td>
											<td class="leaverequest" style="text-align: center;"><form:input
													type="text" path="emptyTempApproverRow[0].toDateString"
													edit-mask="##-##-####"
													cssClass="text center_align popupDatepicker" maxlength="10"
													size="10"
													tabindex="1" 
													onblur="onBlurSetModified(event);"
													onclick="onClickDateField(event)" onkeyup="return checkAutoTabDate(event);"/></td>
										</tr>
									</tbody>
								</table>

							</div>
						</td>
					</tr>




					<tr>
						<td class="tabular_footer">
							<div class="table_buttons">
								<a href="javascript:;" tabindex="1" id="addRow" class="last_field" 
									style="float:left; margin:0px"
									onclick="addTemporaryApproverRow(event);"
									hoverSrc="<spring:theme code="commonBase"/>images/table_add_h.gif">
									<img
									src="<spring:theme code="commonBase" />images/table_add.gif"
									style="margin:0px 0px 0px 0px" />Add
								</a> <a href="javascript:;" tabindex="1" id="addLink"
									style="display: none;"
									onclick="addTemporaryApproverRow(event);"> </a>
							</div>
						</td>
					</tr>
				</tbody>
			</table>

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