<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
	<c:if test="${not empty message}">
		<span style="color:red"><ea:label value="${message}"/></span>
		<br/>
	</c:if>
	<c:if test="${empty payDates}">
		<div class="error">No Earnings information is available.</div>
		<table style="width:1200px;">
			<tr>
				<td style="width:100%"></td>
				<td>
					<div class="groupContent" style="width:150px;" id="timestamp">
						<script type="text/javascript">
							$(document).ready(function(){$("#timestamp").html(""+getTimeStamp());});
						</script>
					</div>
				</td>
			</tr>
		</table>
	</c:if>
	<c:if test="${not empty payDates}">
		<table>
			<tr>
				<td style="width:100%">
					<form:form id="mainForm" modelAttribute="earningsModel" method="post">
						<ea:button id="printButton" target="_blank"
							tabindex="1" type="" formid="mainForm" ajax="true"
							label="Print" checkunsaved="false" cssClass="default last_field"
							onclick="showReport(); return false;"/>
						<input id="hiddenInputButton" class="hidden" type="submit" name="_eventId_revert_restriction_codes"/>
					</form:form>
				</td>
			</tr>
		</table>
		<table style="width:1200px;">
			<tr>
				<td style="width:100%">
					<form:form id="mainForm" modelAttribute="earningsModel" method="post">
						<table>
							<tr>
								<td style="padding-left:10px"><ea:label value="Pay Dates"/></td>
								<td style="padding-top:5px;padding-left:10px">
									<form:select path="payDate" items="${payDates}" itemLabel="label" itemValue="dateFreqVoidAdjChk"
												 onchange="Spring.remoting.submitForm('payDate','mainForm',{ _eventId : 'retrieve' })" cssClass="ignore_changes default initial_focus last_field wrap_field" htmlEscape="true"/>
								</td>
							</tr>
						</table>
					</form:form>
				</td>
				<td>
					<div class="groupContent" style="width:150px;" id="timestamp">
						<script type="text/javascript">
							$(document).ready(function(){$("#timestamp").html(""+getTimeStamp());});
						</script>
					</div>
				</td>
			</tr>
		</table>
		<table>
			<tr>
				<td style="padding-top:15px;padding-left:10px"><ea:label value="Campus:"/></td>
				<td style="padding-top:15px;padding-left:10px"><ea:label value="${earnings.info.campusId}"/></td>
				<td style="padding-top:15px;padding-left:10px"><ea:label value="${earnings.info.campusName}"/></td>
			</tr>
		</table>
		<table>
			<tr>
				<td style="padding-top:5px;padding-left:10px"><ea:label value="Check Number:"/></td>
				<td align="right" style="padding-top:5px;padding-left:10px"><ea:label value="${earnings.info.checkNumber}"/></td>
				<td style="padding-top:5px;padding-left:10px"><ea:label value="Period Ending Date:"/></td>
				<td align="right" style="padding-top:5px;padding-left:10px"><ea:label value="${earnings.info.periodEndingDate}"/></td>
			</tr>
			<tr>
				<td style="padding-top:5px;padding-left:10px"><ea:label value="Withholding Status:"/></td>
				<td align="right" style="padding-top:5px;padding-left:10px">
					<c:if test="${earnings.info.withholdingStatus =='M'}">
						<ea:label value="MARRIED"/>
					</c:if>
					<c:if test="${earnings.info.withholdingStatus =='S'}">
						<ea:label value="SINGLE"/>
					</c:if>
				</td>
				<td style="padding-top:5px;padding-left:10px"><ea:label value="Number of Exemptions:"/></td>
				<td align="right" style="padding-top:5px;padding-left:10px"><ea:label value="${earnings.info.numExceptions}"/></td>
			</tr>
		</table>
			
		<table style="margin-top:20px">
			<tr>
				<td style="vertical-align:top">
					<div class="groupBox">
						<div class="groupTitle">Earnings and Deductions</div>
						<div class="groupContent" style="width:295px;height:375px">
							<table style="width:100%;overflow:auto" class="gridTable">
								<col style="width:140px;"></col>
								<col style="width:100px;"></col>
								<c:if test="${earnings.deductions.standardGross != '0.00'}">
									<tr>
										<td><ea:label value="Standard Gross"/></td>
										<td style="text-align:right;"><ea:label value="${earnings.deductions.standardGross}"/></td>
									</tr>
								</c:if>
								<c:if test="${earnings.deductions.supplementalPay != '0.00'}">
									<tr>
										<td><ea:label value="Supplemental Pay"/></td>
										<td style="text-align:right;"><ea:label value="${earnings.deductions.supplementalPay}"/></td>
									</tr>
								</c:if>
								<c:if test="${earnings.deductions.overtimePay != '0.00'}">
									<tr>
										<td><ea:label value="Overtime Pay"/></td>
										<td style="text-align:right;"><ea:label value="${earnings.deductions.overtimePay}"/></td>
									</tr>
								</c:if>
								<c:if test="${earnings.deductions.absenceRefund != '0.00'}">
									<tr>
										<td><ea:label value="Absence Refund"/></td>
										<td style="text-align:right;"><ea:label value="${earnings.deductions.absenceRefund}"/></td>
									</tr>
								</c:if>
								<c:if test="${earnings.deductions.taxedFringe != '0.00'}">
									<tr>
										<td><ea:label value="Taxed Fringe Benefits"/></td>
										<td style="text-align:right;"><ea:label value="${earnings.deductions.taxedFringe}"/></td>
									</tr>
								</c:if>
								<c:if test="${earnings.deductions.earnedIncomeCred != '0.00'}">
									<tr>
										<td><ea:label value="Earned Income Credit"/></td>
										<td style="text-align:right;"><ea:label value="${earnings.deductions.earnedIncomeCred}"/></td>
									</tr>
								</c:if>
								<c:if test="${earnings.deductions.nonTrsTax != '0.00'}">
									<tr>
										<td><ea:label value="Non-TRS Taxable"/></td>
										<td style="text-align:right;"><ea:label value="${earnings.deductions.nonTrsTax}"/></td>
									</tr>
								</c:if>
								<c:if test="${earnings.deductions.nonTrsNonTax != '0.00'}">
									<tr>
										<td><ea:label value="Non-TRS Non-Taxable"/></td>
										<td style="text-align:right;"><ea:label value="${earnings.deductions.nonTrsNonTax}"/></td>
									</tr>
								</c:if>
								<c:if test="${earnings.deductions.trsSupplemental != '0.00'}">
									<tr>
										<td><ea:label value="TRS Supplemental"/></td>
										<td style="text-align:right;"><ea:label value="${earnings.deductions.trsSupplemental}"/></td>
									</tr>
								</c:if>
								<tr>
									<td><ea:label value="--- Total Earnings"/></td>
									<td style="text-align:right;"><ea:label value="${earnings.deductions.totalEarnings}"/></td>
								</tr>
								<c:if test="${earnings.deductions.absenceDed != '0.00'}">
									<tr>
										<td><ea:label value="Absence Deductions"/></td>
										<td style="text-align:right;"><ea:label value="${earnings.deductions.absenceDed}"/></td>
									</tr>
								</c:if>
								<c:if test="${earnings.deductions.withholdingTax != '0.00'}">
									<tr>
										<td><ea:label value="Withholding Tax"/></td>
										<td style="text-align:right;"><ea:label value="${earnings.deductions.withholdingTax}"/></td>
									</tr>
								</c:if>
								<c:if test="${earnings.deductions.ficaTax != '0.00'}">
									<tr>
										<td><ea:label value="FICA Tax"/></td>
										<td style="text-align:right;"><ea:label value="${earnings.deductions.ficaTax}"/></td>
									</tr>
								</c:if>
								<c:if test="${earnings.deductions.medicareTax != '0.00'}">
									<tr>
										<td><ea:label value="Medicare Tax"/></td>
										<td style="text-align:right;"><ea:label value="${earnings.deductions.medicareTax}"/></td>
									</tr>
								</c:if>
								<c:if test="${earnings.deductions.trsSalaryRed != '0.00'}">
									<tr>
										<td><ea:label value="TRS Salary Red"/></td>
										<td style="text-align:right;"><ea:label value="${earnings.deductions.trsSalaryRed}"/></td>
									</tr>
								</c:if>
								<c:if test="${earnings.deductions.trsInsurance != '0.00'}">
									<tr>
										<td><ea:label value="TRS Insurance"/></td>
										<td style="text-align:right;"><ea:label value="${earnings.deductions.trsInsurance}"/></td>
									</tr>
								</c:if>
								<c:if test="${earnings.deductions.totOtherDed != '0.00'}">
									<tr>
										<td><ea:label value="Total Other Deductions"/></td>
										<td style="text-align:right;"><ea:label value="${earnings.deductions.totOtherDed}"/></td>
									</tr>
								</c:if>
								<tr>
									<td><ea:label value="--- Total Deductions"/></td>
									<td style="text-align:right;"><ea:label value="${earnings.deductions.totDed}"/></td>
								</tr>
								<tr>
									<td><ea:label value="--- Net Pay"/></td>
									<td style="text-align:right;"><ea:label value="${earnings.deductions.netPay}"/></td>
								</tr>
								<c:if test="${earnings.deductions.nonTrsNonPayTax != '0.00'}">
									<tr>
										<td><ea:label value="Non-TRS Non-Pay Taxable"/></td>
										<td style="text-align:right;"><ea:label value="${earnings.deductions.nonTrsNonPayTax}"/></td>
									</tr>
								</c:if>
								<c:if test="${earnings.deductions.nonTrsNonPayNonTax != '0.00'}">
									<tr>
										<td><ea:label value="Non-TRS Non-Pay Non-Taxable"/></td>
										<td style="text-align:right;"><ea:label value="${earnings.deductions.nonTrsNonPayNonTax}"/></td>
									</tr>
								</c:if>
							</table>
						</div>
					</div>
				</td>
				<td style="padding-left:10px;vertical-align:top">
					<table>	
						<tr>
							<td>
								<table>
									<tr>
										<td>
											<ea:Table height="100px;" width="430px;" tabindex="1" showNumberRows="false" showFooter="false">
												<ea:Header title="Job Description" cssStyle="width:400px;border-right:1px solid white;"/>
												<ea:Header title="Units" cssStyle="border-right:1px solid white;"/>
												<ea:Header title="Pay Rate" cssStyle="border-right:1px solid white;"/>
												<ea:Header title="This Period"/>
												<c:forEach items="${earnings.job}" var="job" varStatus="counter">
													<ea:Row id="${counter.index}">
														<ea:Cell value="${job.code} - ${job.description}"/>
														<ea:Cell value="${job.units}" cssStyle="text-align:right"/>
														<ea:Cell value="${job.payRate}" cssStyle="text-align:right"/>
														<ea:Cell value="${job.amt}" cssStyle="text-align:right"/>
													</ea:Row>
												</c:forEach>
											</ea:Table>
										</td>
									</tr>
									<tr>
										<td class="tabular_footer" style="width:390px; font-weight:bold;text-align:right;">
											<table width="100%">
												<tr>
													<td>&nbsp;&nbsp;</td>
													<td style="text-align:left;"><ea:label value="Total Standard Gross:"/></td>
													<td style="text-align:right;"><ea:label value="${earnings.earningsJobTotal}"/></td>
													<td>&nbsp;&nbsp;&nbsp;</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td>
								<table style="margin-top:10px">
									<tr>
										<td>
											<ea:Table height="100px;" width="430px;" tabindex="1" showNumberRows="false" showFooter="false">
												<ea:Header title="Job Description" cssStyle="width:400px;border-right:1px solid white;"/>
												<ea:Header title="Units" cssStyle="border-right:1px solid white;"/>
												<ea:Header title="Pay Rate" cssStyle="border-right:1px solid white;"/>
												<ea:Header title="This Period"/>
												<c:forEach items="${earnings.overtime}" var="overtime" varStatus="counter">
													<ea:Row id="${counter.index}">
														<ea:Cell value="${overtime.code.code} - ${overtime.code.description}"/>
														<ea:Cell value="${overtime.overtimeUnits}" cssStyle="text-align:right"/>
														<ea:Cell value="${overtime.overtimeRate}" cssStyle="text-align:right"/>
														<ea:Cell value="${overtime.thisPeriod}" cssStyle="text-align:right"/>
													</ea:Row>
												</c:forEach>
											</ea:Table>
										</td>
									</tr>
									<tr>
										<td class="tabular_footer" style="width:390px; font-weight:bold;text-align:right;">
											<table width="100%">
												<tr>
													<td>&nbsp;&nbsp;</td>
													<td style="text-align:left;"><ea:label value="Total Overtime Pay:"/></td>
													<td style="text-align:right;"><ea:label value="${earnings.earningsOvertimeTotal}"/> </td>
													<td>&nbsp;&nbsp;</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr> 
							<td>
								<table style="margin-top:10px">
									<tr>
										<td>
											<ea:Table height="100px;" width="430px;" tabindex="1" showNumberRows="false" showFooter="false">
												<ea:Header title="Supplemental Type" cssStyle="width:400px;border-right:1px solid white;"/>
												<ea:Header title="This Period"/>
													<c:forEach items="${earnings.supplemental}" var="supplemental" varStatus="counter">
														<ea:Row id="${counter.index}">
															<c:if test="${supplemental.description != 'ZZZ - default'}"> <ea:Cell value="${supplemental.description}"/></c:if>
															<c:if test="${supplemental.description == 'ZZZ - default'}"> <ea:Cell value="Other Supplemental"/></c:if>
															<ea:Cell value="${supplemental.amt}"  cssStyle="text-align:right"/>
														</ea:Row>
													</c:forEach>
											</ea:Table>
										</td>
									</tr>
									<tr>
										<td class="tabular_footer" style="width:390px; text-align:left;font-weight:bold; padding-right:17px">
											<table width="100%">
												<tr>
													<td>&nbsp;&nbsp;</td>
													<td style="text-align:left;"><ea:label value="Total Supplemental Pay:"/></td>
													<td style="text-align:right;"><ea:label value="${earnings.earningsSupplementalTotal}"/> </td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr> 
							<td>
								<table style="margin-top:10px">
									<tr>
										<td>
											<ea:Table height="100px;" width="430px;" tabindex="1" showNumberRows="false" showFooter="false">
												<ea:Header title="Non-TRS Taxable Type" cssStyle="width:400px;border-right:1px solid white;"/>
												<ea:Header title="This Period"/>
													<c:forEach items="${earnings.nonTrsTax}" var="nontrstax" varStatus="counter">
														<ea:Row id="${counter.index}">
															<c:if test="${nontrstax.description != 'ZZZ - default'}"> <ea:Cell value="${nontrstax.description}"/></c:if>
															<c:if test="${nontrstax.description == 'ZZZ - default'}"> <ea:Cell value="Other Non-TRS Taxable"/></c:if>
															<ea:Cell value="${nontrstax.amt}"  cssStyle="text-align:right"/>
														</ea:Row>
													</c:forEach>
											</ea:Table>
										</td>
									</tr>
									<tr>
										<td class="tabular_footer" style="width:390px; text-align:left;font-weight:bold; padding-right:17px">
											<table width="100%">
												<tr>
													<td>&nbsp;&nbsp;</td>
													<td style="text-align:left;"><ea:label value="Total Non-TRS Taxable Pay:"/></td>
													<td style="text-align:right;"><ea:label value="${earnings.earningsNonTrsTaxTotal}"/> </td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
				<td style="padding-left:10px;vertical-align:top" rowspan="4">
					<table>
						<tr>
							<td>
								<table>
									<tr>
										<td>
											<ea:Table height="100px;" width="430px;" tabindex="1" showNumberRows="false" showFooter="false">
												<ea:Header title="Non-TRS Non-Taxable Type" cssStyle="width:400px;border-right:1px solid white;"/>
												<ea:Header title="This Period"/>
													<c:forEach items="${earnings.nonTrsNonTax}" var="nontax" varStatus="counter">
														<ea:Row id="${counter.index}">
															<c:if test="${nontax.description != 'ZZZ - default'}"> <ea:Cell value="${nontax.description}"/></c:if>
															<c:if test="${nontax.description == 'ZZZ - default'}"> <ea:Cell value="Other Non-Trs Non-Taxable"/></c:if>
															<ea:Cell value="${nontax.amt}"  cssStyle="text-align:right"/>
														</ea:Row>
													</c:forEach>
											</ea:Table>
										</td>
									</tr>
									<tr>
										<td class="tabular_footer" style="width:390px; text-align:left;font-weight:bold; padding-right:17px">
											<table width="100%">
												<tr>
													<td>&nbsp;&nbsp;</td>
													<td style="text-align:left;"><ea:label value="Total Non-TRS Non-Taxable Pay:"/></td>
													<td style="text-align:right;"><ea:label value="${earnings.earningsNonTrsNonTaxTotal}"/> </td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td>
								<table>
									<tr>
									
										<td style="padding-top:10px;" >
											<ea:Table height="238px;" width="430px;" tabindex="1" showNumberRows="false" showFooter="false">
												<ea:Header title="Other Deductions Description" cssStyle="border-right:1px solid white;"/>
												<ea:Header title="Cafe" cssStyle="border-right:1px solid white;"/>
												<ea:Header title="This Period" cssStyle="border-right:1px solid white;"/>
												<ea:Header title="Employer<br/>Contribution" ignoreHtmlEscape="true"/>
												<c:forEach items="${earnings.other}" var="other" varStatus="counter">
													<ea:Row id="${counter.index}">
														<ea:Cell value="${other.code} - ${other.description}"/>
														<ea:Cell value="${other.cafe_flg}" cssStyle="text-align:center"/>
														<ea:Cell value="${other.amt}"  cssStyle="text-align:right"/>
														<ea:Cell value="${other.contrib}"  cssStyle="text-align:right"/>
													</ea:Row>
												</c:forEach>
												<c:if test="${fn:length(earnings.other) > 0}">   
													<c:if test="${earnings.other[0].depCareMax == 1}">
														<tr>
															<td colspan="4"><ea:label value="Dependent Care Total exceeds $5000.00"/></td>
														</tr>
													</c:if>
													<c:if test="${earnings.other[0].hsaCareMax == 1}">
														<tr>
															<td colspan="4"><ea:label value="HSA Employer Contribution Total exceeds $3000.00"/></td>
														</tr>
													</c:if>
												</c:if>
											</ea:Table>
										</td>
									</tr>
									<tr>
										<td class="tabular_footer" style="width:390px; text-align:right;font-weight:bold;">
											<table width="100%">
												<tr>
													<td>&nbsp;</td>
													<td style="text-align:left;width:150px;"><ea:label value="Total Other Deductions:"/></td>
													<td style="width:150px;text-align:right;"><ea:label value="${earnings.earningsOtherTotal}"/></td>
													<td style=" text-align:right;width:80px"><ea:label value="${earnings.earningsOtherContribTotal}"/></td>
													<td>&nbsp;&nbsp;</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td >
								<table>
									<tr >
										<td style="padding-top:10px;">
											<ea:Table height="238px;" width="430px;" tabindex="1" showNumberRows="false" showFooter="false">
												<ea:Header title="Leave Type" cssStyle="width:200px;border-right:1px solid white;"/>
												<ea:Header title="Units Used <br> This Period" ignoreHtmlEscape="true" cssStyle="border-right:1px solid white;"/>
												<ea:Header title="Balance" cssStyle="border-right:1px solid white;"/>
												<c:forEach items="${earnings.leave}" var="leave" varStatus="counter">
														<ea:Row id="${counter.index}">
															<ea:Cell value="${leave.code} - ${leave.description}"/>
															<ea:Cell value="${leave.unitsPrior}" cssStyle="text-align:right"/>
															<ea:Cell value="${leave.balance}"  cssStyle="text-align:right"/>
														</ea:Row>
												</c:forEach>
											</ea:Table>
										</td>
									</tr>
									<tr>
										<td class="tabular_footer" style="width:390px; text-align:right;font-weight:bold">
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<table>
						<tr>
							<td>
						   		<table style="margin-top:10px" >
									<tr>
										<td>
											<ea:Table height="100px;" width="750px;" tabindex="1" showNumberRows="false" showFooter = "false">
												<ea:Header title="Bank Name" cssStyle="border-right:1px solid white;"/>
												<ea:Header title="Account Type" cssStyle="border-right:1px solid white;"/>
												<ea:Header title="Account Number" cssStyle="border-right:1px solid white;"/>
												<ea:Header title="Amount"/>
												<c:forEach items="${earnings.bank}" var="bank" varStatus="counter">
														<ea:Row id="${counter.index}">
															<ea:Cell value="${bank.name} (${bank.code})"/>
															<ea:Cell value="${bank.acctTypeCode} - ${bank.acctType}" cssStyle="text-align:left"/>
															<ea:Cell value="${bank.acctNumLabel}"/>
															<ea:Cell value="${bank.amt}"  cssStyle="text-align:right"/>
														</ea:Row>
												</c:forEach>
											</ea:Table>
										</td>
									</tr>
									<tr>
										<td class="tabular_footer" style="width:750px; text-align:right;font-weight:bold;">
											<div style="padding-right:32px">Total: <ea:label value="${earnings.earningsBankTotal}"/></div>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</c:if>
</div>