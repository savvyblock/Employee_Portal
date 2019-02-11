<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ea" uri="http://www.esc20.net/tags/employeeaccess-util"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script type="text/javascript" src="<c:url value="/scripts/timeStamp.js" />" ></script>
<script type="text/javascript" >
	function showReport()
	{
		Spring.remoting.submitForm('printButton','mainForm',{ _eventId : 'retrieve' });
		window.open("/EmployeeAccess/app/viewreport.htm?hidebuttons=true&hidecsv=true", "reportWindow");
	}
</script>
<div style="padding: 10px 10px 10px 10px">
	<table style="width:1200px;">
		<tr>
			<td style="width:100%">
				<form:form id="mainForm" modelAttribute="pay" method="post">
					<ea:button id="printButton" target="_blank"
						tabindex="1" type="" formid="mainForm" ajax="true"
						label="Print" checkunsaved="false" cssClass="default last_field"
						onclick="showReport(); return false;"/>
					<input id="hiddenInputButton" class="hidden" type="submit" name="_eventId_revert_restriction_codes"/>
				</form:form>
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
	<c:if test="${not empty message}">
		<span style="color:red"><ea:label value="${message}"/></span><br/>
		<br/>
	</c:if>
	<div class="groupBox">
		<div class="groupTitle">
			Employee Information
		</div>
		<div class="groupContent" style="width:1200px">
			<table style="width:100%;" class="niceTable gridTable">
				<col style="width:200px;"></col>
				<col style="width:400px;"></col>
				<col style="width:200px;"></col>
				<col style="width:400px;"></col>
				<tr>
					<td>
						<b>Name</b>
					</td>
					<td>
						<ea:label value="${user.firstName}"/> <ea:label value="${user.middleName}"/> <ea:label value="${user.lastName}"/> <ea:label value="${user.generationDescr}"/>
					</td>
					<td>
					</td>
					<td>
					</td>
				</tr>
				<tr>
					<td>
						<b>Address</b>
					</td>
					<td>
						<ea:label value="${user.address}"/>
					</td>
					<td>
						<b>Employee ID</b>
					</td>
					<td>
						<ea:label value="${employeeId}"/>
					</td>
				</tr>
				<tr>
					<td>
					</td>
					<td>
						<ea:label value="${user.city}"/>, <ea:label value="${user.state}"/> <ea:label value="${user.zipCode}"/>
						<c:if test="${not empty user.zipCode4}">
							-<ea:label value="${user.zipCode4}"/>
						</c:if>
					</td>
					<td>
						<b>Date of Birth</b>
					</td>
					<td>
						<ea:label value="${user.formattedDateOfBirth}"/>
					</td>
				</tr>
				<tr>
					<td>
						<b>Phone Number</b>
					</td>
					<td>
						<ea:label value="${user.phoneNumber}"/>
					</td>
					<td>
						<b>Gender</b>
					</td>
					<td>
						<ea:label value="${user.gender}"/>
					</td>
				</tr>
				<tr>
					<td>
						<b>Degree</b>
					</td>
					<td>
						<ea:label value="${employeeInfo.degree.description}"/>
					</td>
					<td>
						
					</td>
					<td>
		
					</td>
				</tr>
				<tr>
					<td>
						<b>Professional Years Experience</b>
					</td>
					<td>
						<ea:label value="${employeeInfo.proExperience}"/>
					</td>
					<td>
						<b>Professional District Experience</b>
					</td>
					<td>
						<ea:label value="${employeeInfo.proExperienceDistrict}"/>
					</td>
				</tr>
				<tr>
					<td>
						<b>Non Professional Years Experience</b>
					</td>
					<td>
						<ea:label value="${employeeInfo.nonProExperience}"/>
					</td>
					<td>
						<b>Non Professional District Experience</b>
					</td>
					<td>
						<ea:label value="${employeeInfo.nonProExperienceDistrict}"/>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<br/>
	<c:if test="${empty frequencies}">
		No Current Pay Information is available.
	</c:if>
	<c:if test="${not empty frequencies}">
		<c:forEach var="frequency" items="${frequencies}">
			<div class="groupBox">
				<div class="groupTitle">
					Frequency: <ea:label value="${frequency.label}"/>
				</div>
				<div class="groupContent" style="width:1200px">
					<table style="width:100%;" class="niceTable gridTable">
						<col style="width:150px;"></col>
						<col style="width:150px;"></col>
						<col style="width:150px;"></col>
						<col style="width:150px;"></col>
						<col style="width:150px;"></col>
						<col style="width:150px;"></col>
						<tr>
							<td>
								<b>Marital Status</b>
							</td>
							<td>
								<ea:label value="${payInfos[frequency].maritalStatus.displayLabel}"/>
							</td>
							<td>
								<b>Number of Exemptions</b>
							</td>
							<td>
								<ea:label value="${payInfos[frequency].numberOfExemptions}"/>
							</td>
							<td>
								<b>Pay Campus</b>
							</td>
							<td>
								<ea:label value="${payCampuses[frequency]}"/>
							</td>
						</tr>
					</table>
				<br/>
				<b>Positions:</b><br/>
				<br/>
				<c:forEach var="job" items="${jobs[frequency]}">
					<table style="width:100%;" class="niceTable gridTable">
						<col style="width:200px;"></col>
						<col style="width:100px;"></col>
						<col style="width:200px;"></col>
						<col style="width:100px;"></col>
						<col style="width:200px;"></col>
						<col style="width:100px;"></col>
						<col style="width:200px;"></col>
						<col style="width:100px;"></col>
						<tr>
							<td style="text-align:left" colspan="2">
								Title: <b><ea:label value="${job.title.description}"/></b>
							</td>
							<td style="text-align:left">
								Annual Payments
							</td>
							<td style="text-align:right;">
								<ea:label value="${job.annualPayments}"/>
							</td>
							<td style="text-align:left">
								Regular Hours
							</td>
							<td style="text-align:right;">
								<ea:label value="${job.regularHours}"/>
							</td>
							<td style="text-align:left">
								Remain Payments
							</td>
							<td style="text-align:right;">
								<ea:label value="${job.remainPayments}"/>
							</td>
						</tr>
						<tr>
							<td style="text-align:left">
								Annual Salary
							</td>
							<td style="text-align:right;">
								<ea:label value="${job.annualSalary}"/>
							</td>
							<td style="text-align:left">
								Daily Rate
							</td>
							<td style="text-align:right;">
								<ea:label value="${job.dailyRate}"/>
							</td>
							<td style="text-align:left">
								Pay Rate
							</td>
							<td style="text-align:right;">
								<ea:label value="${job.hourlyRate}"/>
							</td>
							<td style="text-align:left">
								Overtime Rate
							</td>
							<td style="text-align:right;">
								<ea:label value="${job.overtimeRate}"/>
							</td>
						</tr>
					</table>
					<br/>
				</c:forEach>
				<c:if test="${not empty accounts[frequency]}">
				<ea:Table height="200px;" width="900px;" tabindex="1" showNumberRows="false">
					<ea:Header title="Bank Code"/>
					<ea:Header title="Bank Name"/>
					<ea:Header title="Account Type"/>
					<ea:Header title="Account Number"/>
					<ea:Header title="Deposit Amount"/>
					<c:forEach items="${accounts[frequency]}" var="account" varStatus="counter">
						<ea:Row id="${counter.index}">
							<ea:Cell value="${account.code.code}"/>
							<ea:Cell value="${account.code.description}"/>
							<ea:Cell value="${account.accountType.code} - ${account.accountType.description}"/>
							<ea:Cell value="${account.accountNumberLabel}"/>
							<ea:Cell value="${account.depositAmount.displayAmount}" cssStyle="text-align:right"/>
						</ea:Row>
					</c:forEach>
				</ea:Table>
				<br/>
				</c:if>
				</div>
			</div>
			<br/>
			<c:if test="${not empty stipends[frequency]}">
			<div class="groupBox">
				<div class="groupTitle">
					Stipend Information: <ea:label value="${frequency.label}"/>
				</div>
				<div class="groupContent">
					<ea:Table height="200px;" width="100%;" tabindex="1" showNumberRows="false">
						<ea:Header title="Extra Duty" cssStyle="width:300px;"/>
						<ea:Header title="Type"/>
						<ea:Header title="Amount"/>
						<ea:Header title="Remain Amount"/>
						<ea:Header title="Remain Payments"/>
						<c:forEach items="${stipends[frequency]}" var="stipend" varStatus="counter">
							<ea:Row id="${counter.index}">
								<ea:Cell value="${stipend.extraDuty.description}"/>
								<ea:Cell value="${stipend.type}"/>
								<ea:Cell value="${stipend.amount}" cssStyle="text-align:right"/>
								<ea:Cell value="${stipend.remainAmount}" cssStyle="text-align:right"/>
								<ea:Cell value="${stipend.remainPayments}" cssStyle="text-align:right"/>
							</ea:Row>
						</c:forEach>
					</ea:Table>
				</div>
			</div>
			</c:if>
			<br/>
		</c:forEach>
	</c:if>
</div>