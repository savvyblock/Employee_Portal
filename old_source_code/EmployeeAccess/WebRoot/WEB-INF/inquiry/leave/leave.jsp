<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ea" uri="http://www.esc20.net/tags/employeeaccess-util"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<script type="text/javascript" src="<c:url value="/scripts/timeStamp.js" />" ></script>

<script type="text/javascript">
dojo.require("dijit.form.DateTextBox");
</script>

<div style="padding: 10px 10px 10px 10px">
	<table style="width:1200px;">
		<tr>
			<td style="width:100%">
			</td>
			<td>
				<div class="groupContent" style="width:150px;" id="timestamp">
					<script type="text/javascript">
					$(document).ready(function()
							{
								$("#timestamp").html(""+getTimeStamp());
							});
					</script>
				</div>
			</td>
		</tr>
	</table>
	<c:if test="${not empty flowRequestContext.messageContext.allMessages}">
		<tiles:insertAttribute name="error" />
	</c:if>
	<c:if test="${empty frequencies}">
		<div class="error">No Leave Balances information is available.</div>
		<br/>
	</c:if>
	<c:if test="${not empty frequencies}">
		<c:if test="${not empty message}">
			<span style="color:red"><ea:label value="${message}"/></span><br/>
			<br/>
		</c:if>
		<form:form id="mainForm" modelAttribute="leave" method="post">
			<table style="width:1200px;">
				<tr>
					<td style="width:100%">
						Please select a frequency type:
						<form:select path="frequency" items="${frequencies}" cssClass="ignore_changes default wrap_field initial_focus" 
						onchange="Spring.remoting.submitForm('frequency','mainForm',{ _eventId : 'retrieveLeaveInfos' });"
						htmlEscape="true" itemValue="code" itemLabel="label" tabindex="1"/>
					</td>
				</tr>
			</table>
			<br/>
			<ea:Table height="200px;" width="800px;" tabindex="1" showNumberRows="false">
				<ea:Header title="Leave Type" cssStyle="width:300px;border-right:1px solid white;"/>
				<ea:Header title="Beginning Balance" cssStyle="text-align:center;border-right:1px solid white;"/>
				<ea:Header title="Advanced / Earned" cssStyle="text-align:center;border-right:1px solid white;"/>
				<ea:Header title="Pending Earned" cssStyle="text-align:center;border-right:1px solid white;"/>
				<ea:Header title="Used" cssStyle="text-align:center;border-right:1px solid white;"/>
				<ea:Header title="Pending Used" cssStyle="text-align:center;border-right:1px solid white;"/>
				<ea:Header title="Available Balance" cssStyle="text-align:center"/>
				<c:forEach items="${leaveInfos[leave.frequency]}" var="leaveInfo" varStatus="counter">
					<ea:Row id="${counter.index}">
						<ea:Cell value="${leaveInfo.type.description}"/>
						<ea:Cell value="${leaveInfo.beginBalance}" cssStyle="text-align:right"/>
						<ea:Cell value="${leaveInfo.advancedEarned}" cssStyle="text-align:right"/>
						<ea:Cell value="${leaveInfo.pendingEarned}" cssStyle="text-align:right"/>
						<ea:Cell value="${leaveInfo.used}" cssStyle="text-align:right"/>
						<ea:Cell value="${leaveInfo.pendingUsed+leaveInfo.pendingApproval+leaveInfo.pendingPayroll}" cssStyle="text-align:right"/>
						<ea:Cell value="${leaveInfo.availableBalance}" cssStyle="text-align:right"/>
					</ea:Row>
				</c:forEach>
			</ea:Table>
			<br/>
			<table class="niceTable">
				<tr>
					<td style="vertical-align:middle">
						From Date of Leave:
						<ea:DateInput path="from" cssClass="ignore_changes" id="from" tabindex="1"/>
					</td>
					<td style="vertical-align:middle">
						To Date of Leave:
						<ea:DateInput path="to" cssClass="ignore_changes" id="to" tabindex="1"/>
					</td>
					<td style="vertical-align:middle">
						Leave Code:
						<form:select path="leaveType" items="${leaveTypes}" cssClass="ignore_changes" 
						htmlEscape="true" itemValue="code" itemLabel="description" tabindex="1"/>
					</td>
					<td>
						<ea:button id="retrieveButton" event="retrieveLeaves" 
							tabindex="1" type="" formid="mainForm" ajax="true"
							label="Retrieve" checkunsaved="false" cssClass="default last_field"/>
					</td>
				</tr>
			</table>
			<ea:Table height="200px;" width="800px;" tabindex="1" showNumberRows="false">
				<ea:Header title="Leave Type" cssStyle="width:300px;border-right:1px solid white;"/>
				<ea:Header title="Date of Pay" cssStyle="text-align:center;border-right:1px solid white;"/>
				<ea:Header title="Date of Leave" cssStyle="text-align:center;border-right:1px solid white;"/>
				<ea:Header title="Leave Used" cssStyle="text-align:center;border-right:1px solid white;"/>
				<ea:Header title="Leave Earned" cssStyle="text-align:center;border-right:1px solid white;"/>
				<ea:Header title="Status"/>
				<c:forEach items="${leaves}" var="leave" varStatus="counter">
					<ea:Row id="${counter.index}">
						<ea:Cell value="${leave.type.description}"/>
						<ea:Cell value="${leave.dateOfPay}" cssStyle="text-align:right" type="date"/>
						<ea:Cell value="${leave.dateOfLeave}" cssStyle="text-align:right" type="date"/>
						<ea:Cell value="${leave.leaveUsed}" cssStyle="text-align:right"/>
						<ea:Cell value="${leave.leaveEarned}" cssStyle="text-align:right"/>
						<ea:Cell value="${leave.status.label}" cssStyle="text-align:left"/>
					</ea:Row>
				</c:forEach>
			</ea:Table>
		</form:form>
	</c:if>
</div>