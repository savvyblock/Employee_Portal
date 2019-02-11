<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ea" uri="http://www.esc20.net/tags/employeeaccess-util"%>

<script type="text/javascript">
	function showReport()
	{
		Spring.remoting.submitForm('printButton','mainForm',{ _eventId : 'retrieve' });
		window.open("/EmployeeAccess/app/viewreport.htm?hidebuttons=true&hidecsv=true", "reportWindow");
	}
</script>
<script type="text/javascript" src="<c:url value="/scripts/w2Information.js" />" ></script>

<div id="w2ConsentDiv" style="padding: 10px 10px 10px 10px">
	<c:if test="${not empty options.messageW2}">
		<span style="color:red"><ea:label value="${options.messageW2}"/></span><br/>
		<br/>
	</c:if>
	<!-- W-2 Electronic Consent Update Message -->
	<c:if test="${showSuccessMsg}">
		<span id="updateMsg" style="color:red"><b>Update was successful - an email will be sent confirming your selection.</b></span><br/>
		<br/>
	</c:if>
	<form:form id="mainForm" modelAttribute="w2" method="post">
		<table style="width:1200px;">
			<tr>
				<td style="width:20%;">
					<c:if test="${not empty years}">
						<c:if test="${w2.year >= '2009' && w2.year <= options.w2Latest}">
							<ea:button id="printButton" target="_blank"
								tabindex="1" type="" formid="mainForm" ajax="true"
								label="Print" checkunsaved="false" cssClass="default last_field"
								onclick="showReport(); return false;"/>
						</c:if>
					</c:if>

					<c:if test="${options.enableElecConsntW2}">
						<ea:button id="w2Consent" target="_blank"
							tabindex="1" type="" formid="mainForm" ajax="true"
							label="W-2 Consent" checkunsaved="false" cssClass="disabled last_field"
							onclick="displayW2ElecConsntPopup();"/>
					</c:if>
					<br/>

					<c:if test="${not empty years}">
						Please select a calendar year:
						<form:select path="year" items="${years}" 
						cssClass="ignore_changes default initial_focus last_field wrap_field" 
						onchange="Spring.remoting.submitForm('year','mainForm',{ _eventId : 'retrieve' });"
						htmlEscape="true"/>
					</c:if>
				</td>
				<!-- Hidden Fields -->
				<form:hidden path="elecConsntW2" id="elecConsntW2" />
				<input type="hidden" id="elecConsntMsgW2" value="${w2.elecConsntMsgW2}"/>
				<input type="hidden" id="year" value="${w2.year}"/>
				<input type="hidden" id="w2Latest" value="${options.w2Latest}"/>
				<input type="text" class="hidden" id="elecConsntW2Flag" value="${w2.elecConsntW2}"/>
				<input type="text" class="hidden" id="enableElecConsntW2" value="${options.enableElecConsntW2}"/>
			</tr>
		</table>
	</form:form>

	<c:if test="${empty years}">
		<div class="error">No W-2 Information is available.</div>
	</c:if>

	<c:if test="${not empty years}">
		<br/>
		<div class="groupBox">
			<div class="groupTitle">W-2 Information</div>
			<div class="groupContent" style="width:1200px">
				<table style="width:100%;" class="niceTable gridTable">
					<col style="width:300px;"></col>
					<col style="width:100px;"></col>
					<col style="width:300px;"></col>
					<col style="width:100px;"></col>
					<col style="width:300px;"></col>
					<col style="width:100px;"></col>
					<tr>
						<td style="text-align:left">
							Taxable Gross Pay
						</td>
						<td style="text-align:right;">
							<ea:label value="${w2Info.taxableGrossPay}"/>
						</td>
						<td style="text-align:left">
							Withholding Tax
						</td>
						<td style="text-align:right;">
							<ea:label value="${w2Info.withholdingTax}"/>
						</td>
						<td style="text-align:left">
							Pension
						</td>
						<td style="text-align:right;">
							<c:if test="${w2Info.pension}">Y</c:if>
							<c:if test="${!w2Info.pension}">N</c:if>
						</td>
					</tr>
					<tr>
						<td style="text-align:left">
							FICA Gross
						</td>
						<td style="text-align:right;">
							<ea:label value="${w2Info.ficaGross}"/>
						</td>
						<td style="text-align:left">
							FICA Tax
						</td>
						<td style="text-align:right;">
							<ea:label value="${w2Info.ficaTax}"/>
						</td>
						<td></td>
						<td style="text-align:right;"></td>
					</tr>
					<tr>
						<td style="text-align:left">
							Medicare Gross
						</td>
						<td style="text-align:right;">
							<ea:label value="${w2Info.medicareGross}"/>
						</td>
						<td style="text-align:left">
							Medicare Tax
						</td>
						<td style="text-align:right;">
							<ea:label value="${w2Info.medicareTax}"/>
						</td>
						<td></td>
						<td style="text-align:right;"></td>
					</tr>
					<tr>
						<td style="text-align:left">
							Earned Income Credit
						</td>
						<td style="text-align:right;">
							<ea:label value="${w2Info.earnedIncomeCredit}"/>
						</td>
						<td style="text-align:left">
							Dependent Care
						</td>
						<td style="text-align:right;">
							<ea:label value="${w2Info.dependentCare}"/>
						</td>
						<td style="text-align:left"></td>
						<td style="text-align:right;"></td>
					</tr>
					<tr>
						<td colspan="6">&nbsp;</td>
					</tr>
					<tr>
						<td style="text-align:left">
							Annuity Deduction
						</td>
						<td style="text-align:right;">
							<ea:label value="${w2Info.annuityDeduction}"/>
						</td>
						<td style="text-align:left">
							457 Withdraw
						</td>
						<td style="text-align:right;">
							<ea:label value="${w2Info.annuity457Withdraw}"/>
						</td>
						<td style="text-align:left">
							457 Annuities - Box 12
						</td>
						<td style="text-align:right;">
							<ea:label value="${w2Info.annuity457Employee + w2Info.annuity457Employer}"/>
						</td>
					</tr>
					<tr>
						<td style="text-align:left">
							Cafeteria 125
						</td>
						<td style="text-align:right;">
							<ea:label value="${w2Info.cafeteria125}"/>
						</td>
						<td style="text-align:left">
							Roth 403B After Tax
						</td>
						<td style="text-align:right;">
							<ea:label value="${w2Info.roth403BAfterTax}"/>
						</td>
						<td style="text-align:left"></td>
						<td style="text-align:right;"></td>
					</tr>
					<tr>
						<td colspan="6">&nbsp;</td>
					</tr>
					<tr>
						<td style="text-align:left">
							Non-TRS Business Expense
						</td>
						<td style="text-align:right;">
							<ea:label value="${w2Info.nonTrsBusinessExpense}"/>
						</td>
						<td style="text-align:left">
							Taxable Allowance
						</td>
						<td style="text-align:right;">
							<ea:label value="${w2Info.taxableAllowance}"/>
						</td>
						<td style="text-align:left">
							Emp Business Expense
						</td>
						<td style="text-align:right;">
							<ea:label value="${w2Info.employeeBusinessExpense}"/>
						</td>
					</tr>
					<tr>
						<td style="text-align:left">
							Moving Expense Reimbursement
						</td>
						<td style="text-align:right;">
							<ea:label value="${w2Info.movingExpenseReimbursement}"/>
						</td>
						<c:choose>
							<c:when test="${w2.year >= '2012'}">
								<td style="text-align:left">Emplr Sponsored Health Coverage</td>
								<td style="text-align:right;"><ea:label value="${w2Info.emplrPrvdHlthcare}"/></td>
							</c:when>
							<c:otherwise>
								<td style="text-align:left"></td>
								<td style="text-align:right;"></td>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${w2.year >= '2016'}">
								<td style="text-align:left">Annuity Roth 457b</td>
								<td style="text-align:right;"><ea:label value="${w2Info.annuityRoth457b}"/></td>
							</c:when>
							<c:otherwise>
								<td style="text-align:left"></td>
								<td style="text-align:right;"></td>
							</c:otherwise>
						</c:choose>
					</tr>
					<tr>
						<td colspan="6">&nbsp;</td>
					</tr>
					<tr>
						<td style="text-align:left">
							TRS Salary Reduction
						</td>
						<td style="text-align:right;">
							<ea:label value="${w2Info.salaryReduction}"/>
						</td>
						<td style="text-align:left"></td>
						<td style="text-align:right;"></td>
						<td style="text-align:left"></td>
						<td style="text-align:right;"></td>
					</tr>
					<tr>
						<td colspan="6">&nbsp;</td>
					</tr>
					<tr>
						<td style="text-align:left">
							Taxed Life Contribution
						</td>
						<td style="text-align:right;">
							<ea:label value="${w2Info.taxedLifeContribution}"/>
						</td>
						<td style="text-align:left">
							Health Insurance Deduction
						</td>
						<td style="text-align:right;">
							<ea:label value="${w2Info.healthInsuranceDeduction}"/>
						</td>
						<td style="text-align:left">
							Taxable Fringe Benefits
						</td>
						<td style="text-align:right;">
							<ea:label value="${w2Info.taxableBenefits}"/>
						</td>
					</tr>
					<tr>
						<td colspan="6">&nbsp;</td>
					</tr>
					<tr>
						<td style="text-align:left">
							Health Savings Account
						</td>
						<td style="text-align:right;">
							<ea:label value="${w2Info.healthSavingsAccount}"/>
						</td>
						<td style="text-align:left">
							Non-Tax Sick Pay
						</td>
						<td style="text-align:right;">
							<ea:label value="${w2Info.nonTaxSickPay}"/>
						</td>
						<c:choose>
							<c:when test="${w2.year >= '2010'}">
								<td style="text-align:left">HIRE Exempt Wages</td>
								<td style="text-align:right;"><ea:label value="${w2Info.hireExemptWgs}"/></td>
							</c:when>
							<c:otherwise>
								<td style="text-align:left"></td>
								<td style="text-align:right;"></td>
							</c:otherwise>
						</c:choose>
					</tr>
				</table>
			</div>
		</div>
		<br/>
		<c:forEach var="sick" items="${sickPayInfos}">
			<div class="groupBox">
				<div class="groupTitle">Third Party Sick Pay W-2 Amounts - <ea:label value="${sick.frequency.label}"/></div>
				<div class="groupContent" style="width:1200px">
					<table style="width:100%;" class="niceTable gridTable">
						<col style="width:300px;"></col>
						<col style="width:100px;"></col>
						<col style="width:300px;"></col>
						<col style="width:100px;"></col>
						<col style="width:300px;"></col>
						<col style="width:100px;"></col>
						<tr>
							<td style="text-align:left">
								Withholding Gross
							</td>
							<td style="text-align:right;">
								<ea:label value="${sick.withholdingGross}"/>
							</td>
							<td style="text-align:left">
								Withholding Tax
							</td>
							<td style="text-align:right;">
								<ea:label value="${sick.withholdingTax}"/>
							</td>
							<td style="text-align:left"></td>
							<td style="text-align:right;"></td>
						</tr>
						<tr>
							<td style="text-align:left">
								FICA Gross
							</td>
							<td style="text-align:right;">
								<ea:label value="${sick.ficaGross}"/>
							</td>
							<td style="text-align:left">
								FICA Tax
							</td>
							<td style="text-align:right;">
								<ea:label value="${sick.ficaTax}"/>
							</td>
							<td></td>
							<td style="text-align:right;"></td>
						</tr>
						<tr>
							<td style="text-align:left">
								Medicare Gross
							</td>
							<td style="text-align:right;">
								<ea:label value="${sick.medicareGross}"/>
							</td>
							<td style="text-align:left">
								Medicare Tax
							</td>
							<td style="text-align:right;">
								<ea:label value="${sick.medicareTax}"/>
							</td>
							<td></td>
							<td style="text-align:right;"></td>
						</tr>
						<tr>
							<td style="text-align:left">
								Nontaxable Pay
							</td>
							<td style="text-align:right;">
								<ea:label value="${sick.nonTaxablePay}"/>
							</td>
							<td style="text-align:left"></td>
							<td style="text-align:right;"></td>
							<td style="text-align:left"></td>
							<td style="text-align:right;"></td>
						</tr>
					</table>
				</div>
			</div>
			<br/>
		</c:forEach>
	</c:if>
	<%-- Print Button Popup --%>
	<c:import url="/WEB-INF/inquiry/w2/w2ElecConsentPopup.jsp" />
</div>