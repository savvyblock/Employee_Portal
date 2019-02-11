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
<script type="text/javascript" src="<c:url value="/scripts/leave.js" />"></script>
<script type="text/javascript" src="<c:url value="/scripts/leaveCalendar.js" />"></script>

<div id="mainDiv" style="width: 99%; padding: 10px 0px 0px 0px;">
	<div class="button_margins">
		<form:form id="leaveRequestsForm" modelAttribute="LeaveRequestCalendarCommand" method="POST" action="/EmployeeAccess/app/leave/leaveRequestCalendar">
			<form:hidden path="userEmpNumber" />
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
</div>