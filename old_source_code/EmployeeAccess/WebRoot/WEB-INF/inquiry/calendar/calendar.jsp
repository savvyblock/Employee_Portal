<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ea" uri="http://www.esc20.net/tags/employeeaccess-util"%>

<script type="text/javascript" src="<c:url value="/scripts/timeStamp.js" />" ></script>
<script type="text/javascript" >
	function showReport()
	{
		Spring.remoting.submitForm('printButton','mainForm',{ _eventId : 'retrieve' });
		window.open("/EmployeeAccess/app/viewreport.htm?hidebuttons=true&hidecsv=true", "reportWindow");
	}
</script>
<div style="padding: 10px 10px 10px 10px">
	<c:if test="${not empty years}">
		<table>
			<tr>
				<td style="width:100%">
					<form:form id="mainForm" modelAttribute="calendar" method="post">
						<ea:button id="printButton" target="_blank"
							tabindex="1" type="" formid="mainForm" ajax="true"
							label="Print" checkunsaved="false" cssClass="default last_field"
							onclick="showReport(); return false;"/>
						<input id="hiddenInputButton" class="hidden" type="submit" name="_eventId_revert_restriction_codes"/>
					</form:form>
				</td>
			</tr>
		</table>
	</c:if>
	<table style="width:1200px;">
		<tr>
			<td style="width:100%">
				<form:form id="mainForm" modelAttribute="calendar" method="post">
					<c:if test="${not empty years}">
					Please select a calendar year:
					<form:select path="year" items="${years}" cssClass="ignore_changes default initial_focus last_field wrap_field" 
					onchange="Spring.remoting.submitForm('year','mainForm',{ _eventId : 'retrieve' });"
					htmlEscape="true"/>
					</c:if>
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
	<c:if test="${empty years and empty calendars}">
		<div class="error">No Calendar Year to Date information is available.</div>
	</c:if>
	<br/>
	<c:forEach var="calendar" items="${calendars}">
		<div class="groupBox">
			<div class="groupTitle">
				Frequency: <ea:label value="${calendar.frequency.label}"/>
			</div>
			<div class="groupContent" style="width:1200px">
				<c:if test="${not empty calendar.lastPostedPayDate}">
					Last Posted Pay Date: <ea:label value="${calendar.lastPostedPayDateLabel}"/><br/>
					<br/>
				</c:if>
				<table style="width:100%;" class="niceTable gridTable">
					<col style="width:300px;"></col>
					<col style="width:100px;"></col>
					<col style="width:300px;"></col>
					<col style="width:100px;"></col>
					<col style="width:300px;"></col>
					<col style="width:100px;"></col>
					<tr>
						<td style="text-align:left">
							Contract Pay
						</td>
						<td style="text-align:right;">
							<ea:label value="${calendar.contractPay}"/>
						</td>
						<td style="text-align:left">
							Non-Contract Pay
						</td>
						<td style="text-align:right;">
							<ea:label value="${calendar.nonContractPay}"/>
						</td>
						<td style="text-align:left">
							Supplemental Pay
						</td>
						<td style="text-align:right;">
							<ea:label value="${calendar.supplementalPay}"/>
						</td>
					</tr>
					<tr>
						<td style="text-align:left">
							Withholding Gross
						</td>
						<td style="text-align:right;">
							<ea:label value='${calendar.withholdingGross}'/>
						</td>
						<td style="text-align:left">
							Withholding Tax
						</td>
						<td style="text-align:right;">
							<ea:label value="${calendar.withholdingTax}"/>
						</td>
						<td style="text-align:left">
							Earned Income Credit
						</td>
						<td style="text-align:right;">
							<ea:label value="${calendar.earnedIncomeCredit}"/>
						</td>
					</tr>
					<tr>
						<td style="text-align:left">
							FICA Gross
						</td>
						<td style="text-align:right;">
							<ea:label value="${calendar.ficaGross}"/>
						</td>
						<td style="text-align:left">
							FICA Tax
						</td>
						<td style="text-align:right;">
							<ea:label value="${calendar.ficaTax}"/>
						</td>
						<td>
							
						</td>
						<td style="text-align:right;">
							
						</td>
					</tr>
					<tr>
						<td style="text-align:left">
							Employee Dependent Care
						</td>
						<td style="text-align:right;">
							<ea:label value="${calendar.dependentCare}"/>
						</td>
						<td style="text-align:left">
							Employer Dependent Care
						</td>
						<td style="text-align:right;">
							<ea:label value="${calendar.dependentCareEmployer}"/>
						</td>
						<td style="text-align:left">
							Dependent Care (Employee and Employer) exceeds $5,000
						</td>
						<td style="text-align:right;">
							<ea:label value="${calendar.dependentCareExceeds}"/>
						</td>
					</tr>
					<tr>
						<td style="text-align:left">
							Medicare Gross
						</td>
						<td style="text-align:right;">
							<ea:label value="${calendar.medicareGross}"/>
						</td>
						<td style="text-align:left">
							Medicare Tax
						</td>
						<td style="text-align:right;">
							<ea:label value="${calendar.medicareTax}"/>
						</td>
						<td>
							
						</td>
						<td style="text-align:right;">
							
						</td>
					</tr>
					<tr>
						<td colspan="6">&nbsp;</td>
					</tr>
					<tr>
						<td style="text-align:left">
							Annuity Deduction
						</td>
						<td style="text-align:right;">
							<ea:label value="${calendar.annuityDeduction}"/>
						</td>
						<td style="text-align:left">
							Roth 403B After Tax
						</td>
						<td style="text-align:right;">
							<ea:label value="${calendar.roth403BAfterTax}"/>
						</td>
						<td style="text-align:left">
							Taxable Benefits
						</td>
						<td style="text-align:right;">
							<ea:label value="${calendar.taxableBenefits}"/>
						</td>
					</tr>
					<tr>
						<td style="text-align:left">
							457 Employee Contribution
						</td>
						<td style="text-align:right;">
							<ea:label value="${calendar.annuity457Employee}"/>
						</td>
						<td style="text-align:left">
							457 Employer Contribution
						</td>
						<td style="text-align:right;">
							<ea:label value="${calendar.annuity457Employer}"/>
						</td>
						<td style="text-align:left">
							457 Withdraw
						</td>
						<td style="text-align:right;">
							<ea:label value="${calendar.annuity457Withdraw}"/>
						</td>
					</tr>
					<tr>
						<td colspan="6">&nbsp;</td>
					</tr>
					<tr>
						<td style="text-align:left">
							Non-TRS Business Allowance
						</td>
						<td style="text-align:right;">
							<ea:label value="${calendar.nonTrsBusinessExpense}"/>
						</td>
						<td style="text-align:left">
							Non-TRS Reimbursement Base
						</td>
						<td style="text-align:right;">
							<ea:label value="${calendar.nonTrsReimbursementBase}"/>
						</td>
						<td style="text-align:left">
							Non-TRS Reimbursement Excess
						</td>
						<td style="text-align:right;">
							<ea:label value="${calendar.nonTrsReimbursementExcess}"/>
						</td>
					</tr>
					<tr>
						<td style="text-align:left">
							Moving Expense Reimbursement
						</td>
						<td style="text-align:right;">
							<ea:label value="${calendar.movingExpenseReimbursement}"/>
						</td>
						<td style="text-align:left">
							Non-TRS Non-Tax Business Allow
						</td>
						<td style="text-align:right;">
							<ea:label value="${calendar.nonTrsNonTaxBusinessAllow}"/>
						</td>
						<td style="text-align:left">
							Non-TRS Non-Tax Non-Pay Allow
						</td>
						<td style="text-align:right;">
							<ea:label value="${calendar.nonTrsNonTaxNonPayAllow}"/>
						</td>
					</tr>
					<tr>
						<td colspan="6">&nbsp;</td>
					</tr>
					<tr>
						<td style="text-align:left">
							TRS Salary Reduction
						</td>
						<td style="text-align:right;">
							<ea:label value="${calendar.salaryReduction}"/>
						</td>
						<td style="text-align:left">
							TRS Insurance
						</td>
						<td style="text-align:right;">
							<ea:label value="${calendar.trsInsurance}"/>
						</td>
						<td style="text-align:left">
							
						</td>
						<td style="text-align:right;">
							
						</td>
					</tr>
					<tr>
						<td style="text-align:left">
							HSA Employer Contribution
						</td>
						<td style="text-align:right;">
							<ea:label value="${calendar.hsaEmployerContribution}"/>
						</td>
						<td style="text-align:left">
							HSA Employee Salary Reduction Contribution
						</td>
						<td style="text-align:right;">
							<ea:label value="${calendar.hsaEmployeeSalaryReductionContribution}"/>
						</td>
						<c:choose>
							<c:when test="${calendar.calYr >= '2010'}">
								<td style="text-align:left">HIRE Exempt Wages</td>
								<td style="text-align:right;"><ea:label value="${calendar.hireExemptWgs}"/></td>
							</c:when>
							<c:otherwise>
								<td style="text-align:left"></td>
								<td style="text-align:right;"></td>
							</c:otherwise>
						</c:choose>
					</tr>
					<tr>
						<td style="text-align:left">
							Taxed Employer Insurance Contribution
						</td>
						<td style="text-align:right;">
							<ea:label value="${calendar.taxedLifeContribution}"/>
						</td>
						<td style="text-align:left">
							Taxed Employer Group Insurance Contribution
						</td>
						<td style="text-align:right;">
							<ea:label value="${calendar.taxedGroupContribution}"/>
						</td>
						<td style="text-align:left">
							Health Insurance Deduction(s)
						</td>
						<td style="text-align:right;">
							<ea:label value="${calendar.healthInsuranceDeduction}"/>
						</td>
					</tr>
					<c:if test="${calendar.calYr >= '2011'}">
						<tr>
							<td colspan="6">&nbsp;</td>
						</tr>
						<tr>
							<td style="text-align:left">Employer-Sponsored Health Coverage</td>
							<td style="text-align:right;"><ea:label value="${calendar.emplrPrvdHlthcare}"/></td>
							<c:choose>
								<c:when test="${calendar.calYr >= '2016'}">
									<td style="text-align:left">Annuity Roth 457b</td>
									<td style="text-align:right;"><ea:label value="${calendar.annuityRoth457b}"/></td>
								</c:when>
								<c:otherwise>
									<td style="text-align:left"></td>
									<td style="text-align:left;"></td>
								</c:otherwise>
							</c:choose>
							<td style="text-align:left"/>
							<td style="text-align:left"/>
							</td>
						</tr>
					</c:if>
				</table>
			</div>
		</div>
		<br/>
	</c:forEach>
</div>