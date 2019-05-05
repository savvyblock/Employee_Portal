<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> <%@ taglib
uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> <%@ page
language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html> 
<html lang="en">
    <head>
        <title>${sessionScope.languageJSON.headTitle.calendarYTD}</title>
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <%@ include file="../commons/header.jsp"%>
    </head>
    <body class="hold-transition sidebar-mini">
        <div class="wrapper">
            <%@ include file="../commons/bar.jsp"%>

            <main class="content-wrapper" id="content" tabindex="-1">
                <section class="content">
                    <div class="clearfix no-print section-title">
                        <h1 class="pageTitle">${sessionScope.languageJSON.title.calendarYearToDate}</h1>
                        <div class="pull-right right-btn">
		                    <%-- <form class="no-print" action="exportPDF" method="POST">
								<input type="hidden" name="${_csrf.parameterName}"
									value="${_csrf.token}" />
								<input type="hidden" name="year"
									value="${selectedYear}" />
								<button type="submit" role="button" class="btn btn-primary download-pdf"
									aria-label="${sessionScope.languageJSON.label.exportPDF}"><i class="fa fa-file-pdf-o"></i></button>
                            </form> --%>
                            <button class="btn btn-primary download-pdf" onclick="downloadPDF()" title="" aria-label="${sessionScope.languageJSON.label.exportPDF}">
                                <i class="fa fa-file-pdf-o"></i>
                            </button>
                            <button
                                class="btn btn-primary"
                                onclick="doPrint()"
                               
                            >
                            ${sessionScope.languageJSON.label.print}
                            </button>
                        </div>
                    </div>
                    <div class="toPrint content-white EMP-detail">
                        <div class="exportPDFBox">
                                <div class="print-block print-title">
                                        <div style="text-align:center;margin-bottom:10px;">
                                            ${sessionScope.district.name}<br />${sessionScope.district.address}<br />
                                            ${sessionScope.district.city},
                                            ${sessionScope.district.state} ${sessionScope.district.zip}-${sessionScope.district.zip4}
                                        </div>
                                        <div style="text-align:center;">
                                                <span>${sessionScope.languageJSON.title.calendarYearToDate}</span><br />
                                            <div id="date-now"></div>
                                        </div>
                                    </div>
                                    <div class="print-block print-table-header">
                                        <div class="flex-line">
                                            <div class="left-title"><span>${sessionScope.languageJSON.label.employeeName}</span>:</div>
                                            <div class="right-conent">
                                                ${sessionScope.userDetail.nameF} ${sessionScope.userDetail.nameM} ${sessionScope.userDetail.nameL}
                                            </div>
                                        </div>
                                        <div class="flex-line">
                                            <div class="left-title"><span>${sessionScope.languageJSON.label.employeeId}</span>:</div>
                                            <div class="right-conent">${sessionScope.userDetail.empNbr}</div>
                                        </div>
                                        <div class="flex-line">
                                            <div class="left-title"><span>${sessionScope.languageJSON.label.calendarYear}</span>:</div>
                                            <div class="right-conent">${selectedYear}</div>
                                        </div>
                                        <div class="flex-line">
                                            <div class="left-title" ><span>${sessionScope.languageJSON.label.frequency}</span>:</div>
                                            <div class="right-conent">${freq}</div>
                                        </div>
                                        <div class="flex-line">
                                            <div class="left-title">
                                                <span>${sessionScope.languageJSON.label.lastPostedPayDate}</span>:
                                            </div>
                                            <div class="right-conent">${latestPayDate}</div>
                                        </div>
                                    </div>
                        </div>
                        
                        <form
                            class="no-print searchForm"
                            action="getCalendarYearToDateByYear"
                            id="selectCalendar"
                            method="POST"
                        >
                        	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <div class="form-group in-line">
                                <label class="form-title" for="year"
                                    >${sessionScope.languageJSON.label.pleaseSelectYear}</label
                                >
                                <select class="form-control" name="year" id="year" onchange="submitCalendarForm()">
                                    <c:forEach var="year" items="${years}" varStatus="years">
                                        <option value="${year}" <c:if test="${year == selectedYear }">selected</c:if>>${year}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </form>
                        <h2 class="no-print table-top-title">
                            <b><span>${sessionScope.languageJSON.label.frequency}</span>: ${freq}</b>
                        </h2>
                        <p class="no-print">
                            <span>${sessionScope.languageJSON.label.lastPostedPayDate}</span>:${latestPayDate}
                        </p>
                        <table
                            class="table border-table responsive-table no-thead print-table calendarYTDTable"
                        >
                            <tbody>
                                <tr>
                                    <td id="contractPay" class="td-title">${sessionScope.languageJSON.calendarTable.contractPay}</td>
                                    <td
                                        headers="contractPay" class="td-content" data-title="${sessionScope.languageJSON.calendarTable.contractPay}" 
                                       
                                    >
                                    <fmt:formatNumber value="${calendar.contrAmt}" pattern="#,##0.00"/>
                                    </td>
                                    <td id="nonContractPay" class="td-title">${sessionScope.languageJSON.calendarTable.nonContractPay}</td>
                                    <td
                                        headers="nonContractPay" class="td-content" data-title="${sessionScope.languageJSON.calendarTable.nonContractPay}" 
                                       
                                    >
                                    <fmt:formatNumber value="${calendar.noncontrAmt}" pattern="#,##0.00"/>
                                    </td>
                                    <td id="supplementalPay" class="td-title">${sessionScope.languageJSON.calendarTable.supplementalPay}</td>
                                    <td
                                        headers="supplementalPay" class="td-content" data-title="${sessionScope.languageJSON.calendarTable.supplementalPay}" 
                                       
                                    >
                                    <fmt:formatNumber value="${calendar.supplPayAmt}" pattern="#,##0.00"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td id="withholdingGross" class="td-title">${sessionScope.languageJSON.calendarTable.withholdingGross}</td>
                                    <td
                                        headers="withholdingGross" class="td-content" data-title="${sessionScope.languageJSON.calendarTable.withholdingGross}" 
                                       
                                    >
                                    <fmt:formatNumber value="${calendar.whGross}" pattern="#,##0.00"/>
                                    </td>
                                    <td id="withholdingTax" class="td-title">${sessionScope.languageJSON.calendarTable.withholdingTax}</td>
                                    <td
                                        headers="withholdingTax" class="td-content" data-title="${sessionScope.languageJSON.calendarTable.withholdingTax}" 
                                       
                                    >
                                    <fmt:formatNumber value="${calendar.whTax}" pattern="#,##0.00"/>
                                    </td>
                                    <td id="earnedIncomeCredit" class="td-title">
                                            ${sessionScope.languageJSON.calendarTable.earnedIncomeCredit}
                                    </td>
                                    <td
                                        headers="earnedIncomeCredit" class="td-content" data-title="${sessionScope.languageJSON.calendarTable.earnedIncomeCredit}" 
                                       
                                    >
                                    <fmt:formatNumber value="${calendar.eicAmt}" pattern="#,##0.00"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td id="ficaGross" class="td-title">${sessionScope.languageJSON.calendarTable.ficaGross}</td>
                                    <td
                                        headers="ficaGross" class="td-content" data-title="${sessionScope.languageJSON.calendarTable.ficaGross}" 
                                       
                                    >
                                    <fmt:formatNumber value="${calendar.ficaGross}" pattern="#,##0.00"/>
                                    </td>
                                    <td id="ficaTax" class="td-title">${sessionScope.languageJSON.calendarTable.ficaTax}</td>
                                    <td
                                        headers="ficaTax" class="td-content" data-title="${sessionScope.languageJSON.calendarTable.ficaTax}" 
                                       
                                    >
                                    <fmt:formatNumber value="${calendar.ficaTax}" pattern="#,##0.00"/>
                                    </td>
                                    <td class="td-title" colspan="2"></td>
                                </tr>
                                <tr>
                                    <td id="dependentCare" class="td-title">
                                            ${sessionScope.languageJSON.calendarTable.dependentCare}
                                    </td>
                                    <td
                                        headers="dependentCare" class="td-content" data-title="${sessionScope.languageJSON.calendarTable.dependentCare}" 
                                       
                                    >
                                    <fmt:formatNumber value="${calendar.dependCare}" pattern="#,##0.00"/>
                                    </td>
                                    <td id="dependentCareEmployer" class="td-title">
                                            ${sessionScope.languageJSON.calendarTable.dependentCareEmployer}
                                    </td>
                                    <td
                                        headers="dependentCareEmployer" class="td-content" data-title="${sessionScope.languageJSON.calendarTable.dependentCareEmployer}" 
                                       
                                    >
                                    <fmt:formatNumber value="${calendar.emplrDependCare}" pattern="#,##0.00"/>
                                    </td>
                                    <td id="dependentCareExceeds" class="td-title">
                                            ${sessionScope.languageJSON.calendarTable.dependentCareExceeds}
                                    </td>
                                    <td
                                        headers="dependentCareExceeds" class="td-content" data-title="${sessionScope.languageJSON.calendarTable.dependentCareExceeds}" 
                                       
                                    >
                                    <fmt:formatNumber value="${calendar.emplrDependCareTax}" pattern="#,##0.00"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td id="medicareGross" class="td-title">${sessionScope.languageJSON.calendarTable.medicareGross}</td>
                                    <td
                                        headers="medicareGross" class="td-content" data-title="${sessionScope.languageJSON.calendarTable.medicareGross}" 
                                       
                                    >
                                    <fmt:formatNumber value="${calendar.medGross}" pattern="#,##0.00"/>
                                    </td>
                                    <td id="medicareTax" class="td-title">${sessionScope.languageJSON.calendarTable.medicareTax}</td>
                                    <td
                                        headers="medicareTax" class="td-content" data-title="${sessionScope.languageJSON.calendarTable.medicareTax}" 
                                       
                                    >
                                    <fmt:formatNumber value="${calendar.medTax}" pattern="#,##0.00"/>
                                    </td>
                                    <td class="td-title" colspan="2"></td>
                                </tr>
                                <tr>
                                    <td colspan="6">&nbsp;</td>
                                </tr>
                                <tr>
                                    <td id="annuityDeduction" class="td-title">${sessionScope.languageJSON.calendarTable.annuityDeduction}</td>
                                    <td
                                        headers="annuityDeduction" class="td-content" data-title="${sessionScope.languageJSON.calendarTable.annuityDeduction}" 
                                       
                                    >
                                    <fmt:formatNumber value="${calendar.annuityDed}" pattern="#,##0.00"/>
                                    </td>
                                    <td id="roth403BAfterTax" class="td-title">
                                            ${sessionScope.languageJSON.calendarTable.roth403BAfterTax}
                                    </td>
                                    <td
                                        headers="roth403BAfterTax" class="td-content" data-title="${sessionScope.languageJSON.calendarTable.roth403BAfterTax}" 
                                       
                                    >
                                    <fmt:formatNumber value="${calendar.annuityRoth}" pattern="#,##0.00"/>
                                    </td>
                                    <td id="taxableBenefits" class="td-title">${sessionScope.languageJSON.calendarTable.taxableBenefits}</td>
                                    <td
                                        headers="taxableBenefits" class="td-content" data-title="${sessionScope.languageJSON.calendarTable.taxableBenefits}" 
                                       
                                    >
                                    <fmt:formatNumber value="${calendar.taxedBenefits}" pattern="#,##0.00"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td id="annuity457Employee" class="td-title">
                                            ${sessionScope.languageJSON.calendarTable.annuity457Employee}
                                    </td>
                                    <td
                                        headers="annuity457Employee" class="td-content" data-title="${sessionScope.languageJSON.calendarTable.annuity457Employee}" 
                                       
                                    >
                                    <fmt:formatNumber value="${calendar.emp457Contrib}" pattern="#,##0.00"/>
                                    </td>
                                    <td id="annuity457Employer" class="td-title">
                                            ${sessionScope.languageJSON.calendarTable.annuity457Employer}
                                    </td>
                                    <td
                                        headers="annuity457Employer" class="td-content" data-title="${sessionScope.languageJSON.calendarTable.annuity457Employer}" 
                                       
                                    >
                                    <fmt:formatNumber value="${calendar.emplr457Contrib}" pattern="#,##0.00"/>
                                    </td>
                                    <td id="annuity457Withdraw" class="td-title">${sessionScope.languageJSON.calendarTable.annuity457Withdraw}</td>
                                    <td
                                        headers="annuity457Withdraw" class="td-content" data-title="${sessionScope.languageJSON.calendarTable.annuity457Withdraw}" 
                                       
                                    >
                                    <fmt:formatNumber value="${calendar.withdraw457}" pattern="#,##0.00"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="6">&nbsp;</td>
                                </tr>
                                <tr>
                                    <td id="nonTrsBusinessExpense" class="td-title">
                                            ${sessionScope.languageJSON.calendarTable.nonTrsBusinessExpense}
                                    </td>
                                    <td
                                        headers="nonTrsBusinessExpense" class="td-content" data-title="${sessionScope.languageJSON.calendarTable.nonTrsBusinessExpense}" 
                                       
                                    >
                                    <fmt:formatNumber value="${calendar.nontrsBusAllow}" pattern="#,##0.00"/>
                                    </td>
                                    <td id="nonTrsReimbursementBase" class="td-title">
                                            ${sessionScope.languageJSON.calendarTable.nonTrsReimbursementBase}
                                    </td>
                                    <td
                                        headers="nonTrsReimbursementBase" class="td-content" data-title="${sessionScope.languageJSON.calendarTable.nonTrsReimbursementBase}" 
                                       
                                    >
                                    <fmt:formatNumber value="${calendar.nontrsReimbrBase}" pattern="#,##0.00"/>
                                    </td>
                                    <td id="nonTrsReimbursementExcess" class="td-title">
                                            ${sessionScope.languageJSON.calendarTable.nonTrsReimbursementExcess}
                                    </td>
                                    <td
                                        headers="nonTrsReimbursementExcess" class="td-content" data-title="${sessionScope.languageJSON.calendarTable.nonTrsReimbursementExcess}" 
                                       
                                    >
                                    <fmt:formatNumber value="${calendar.nontrsReimbrExcess}" pattern="#,##0.00"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td id="movingExpenseReimbursement" class="td-title">
                                            ${sessionScope.languageJSON.calendarTable.movingExpenseReimbursement}
                                    </td>
                                    <td
                                        headers="movingExpenseReimbursement" class="td-content" data-title="${sessionScope.languageJSON.calendarTable.movingExpenseReimbursement}" 
                                       
                                    >
                                    <fmt:formatNumber value="${calendar.movingExpReimbr}" pattern="#,##0.00"/>
                                    </td>
                                    <td id="nonTrsNonTaxBusinessAllow" class="td-title">
                                            ${sessionScope.languageJSON.calendarTable.nonTrsNonTaxBusinessAllow}
                                    </td>
                                    <td
                                        headers="nonTrsNonTaxBusinessAllow" class="td-content" data-title="${sessionScope.languageJSON.calendarTable.nonTrsNonTaxBusinessAllow}" 
                                       
                                    >
                                    <fmt:formatNumber value="${calendar.nontrsNontaxBusAllow}" pattern="#,##0.00"/>
                                    </td>
                                    <td id="nonTrsNonTaxNonPayAllow" class="td-title">
                                            ${sessionScope.languageJSON.calendarTable.nonTrsNonTaxNonPayAllow}
                                    </td>
                                    <td
                                        headers="nonTrsNonTaxNonPayAllow" class="td-content" data-title="${sessionScope.languageJSON.calendarTable.nonTrsNonTaxNonPayAllow}" 
                                       
                                    >
                                    <fmt:formatNumber value="${calendar.nontrsNontaxNonpayAllow}" pattern="#,##0.00"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="6">&nbsp;</td>
                                </tr>
                                <tr>
                                    <td id="salaryReduction" class="td-title">
                                            ${sessionScope.languageJSON.calendarTable.salaryReduction}
                                    </td>
                                    <td
                                        headers="salaryReduction" class="td-content" data-title="${sessionScope.languageJSON.calendarTable.salaryReduction}" 
                                       
                                    >
                                    <fmt:formatNumber value="${calendar.trsSalaryRed}" pattern="#,##0.00"/>
                                    </td>
                                    <td id="trsInsurance" class="td-title">${sessionScope.languageJSON.calendarTable.trsInsurance}</td>
                                    <td
                                        headers="trsInsurance" class="td-content" data-title="${sessionScope.languageJSON.calendarTable.trsInsurance}" 
                                       
                                    >
                                    <fmt:formatNumber value="${trsIns}" pattern="#,##0.00"/>
                                    </td>
                                    <td class="td-title" colspan="2"></td>
                                </tr>
                                <tr>
                                    <td id="hsaEmployerContribution" class="td-title">
                                            ${sessionScope.languageJSON.calendarTable.hsaEmployerContribution}
                                    </td>
                                    <td
                                        headers="hsaEmployerContribution" class="td-content" data-title="${sessionScope.languageJSON.calendarTable.hsaEmployerContribution}" 
                                       
                                    >
                                    <fmt:formatNumber value="${calendar.hsaEmplrContrib}" pattern="#,##0.00"/>
                                    </td>
                                    <td id="hsaEmployeeSalaryReductionContribution" class="td-title">
                                            ${sessionScope.languageJSON.calendarTable.hsaEmployeeSalaryReductionContribution}
                                    </td>
                                    <td
                                        headers="hsaEmployeeSalaryReductionContribution" class="td-content" data-title="${sessionScope.languageJSON.calendarTable.hsaEmployeeSalaryReductionContribution}" 
                                       
                                    >
                                    <fmt:formatNumber value="${calendar.hsaEmpSalRedctnContrib}" pattern="#,##0.00"/>
                                    </td>

                                    <td id="hireExemptWgs" class="td-title">${sessionScope.languageJSON.calendarTable.hireExemptWgs}</td>
                                    <td
                                        headers="hireExemptWgs" class="td-content" data-title="${sessionScope.languageJSON.calendarTable.hireExemptWgs}" 
                                       
                                    >
                                    <fmt:formatNumber value="${calendar.hireExemptWgs}" pattern="#,##0.00"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td id="taxedLifeContribution" class="td-title">
                                            ${sessionScope.languageJSON.calendarTable.taxedLifeContribution}
                                    </td>
                                    <td
                                        headers="taxedLifeContribution" class="td-content" data-title="${sessionScope.languageJSON.calendarTable.taxedLifeContribution}" 
                                       
                                    >
                                    <fmt:formatNumber value="${calendar.taxEmplrLife}" pattern="#,##0.00"/>
                                    </td>
                                    <td id="taxedGroupContribution" class="td-title">
                                            ${sessionScope.languageJSON.calendarTable.taxedGroupContribution}
                                    </td>
                                    <td
                                        headers="taxedGroupContribution" class="td-content" data-title="${sessionScope.languageJSON.calendarTable.taxedGroupContribution}" 
                                       
                                    >
                                    <fmt:formatNumber value="${calendar.taxEmplrLifeGrp}" pattern="#,##0.00"/>
                                    </td>
                                    <td id="healthInsuranceDeduction" class="td-title">
                                            ${sessionScope.languageJSON.calendarTable.healthInsuranceDeduction}
                                    </td>
                                    <td
                                        headers="healthInsuranceDeduction" class="td-content" data-title="${sessionScope.languageJSON.calendarTable.healthInsuranceDeduction}" 
                                       
                                    >
                                    <fmt:formatNumber value="${calendar.hlthInsDed}" pattern="#,##0.00"/>
                                    </td>
                                </tr>

                                <tr>
                                    <td colspan="6">&nbsp;</td>
                                </tr>
                                <tr>
                                    <td id="emplrPrvdHlthcare" class="td-title">
                                            ${sessionScope.languageJSON.calendarTable.emplrPrvdHlthcare}
                                    </td>
                                    <td
                                        headers="emplrPrvdHlthcare" class="td-content" data-title="${sessionScope.languageJSON.calendarTable.emplrPrvdHlthcare}" 
                                       
                                    >
                                    <fmt:formatNumber value="${calendar.emplrPrvdHlthcare}" pattern="#,##0.00"/>
                                    </td>

                                    <td id="annuityRoth457b" class="td-title">${sessionScope.languageJSON.calendarTable.annuityRoth457b}</td>
                                    <td
                                        headers="annuityRoth457b" class="td-content" data-title="${sessionScope.languageJSON.calendarTable.annuityRoth457b}" 
                                       
                                    >
                                    <fmt:formatNumber value="${calendar.annuityRoth457b}" pattern="#,##0.00"/>
                                    </td>

                                    <td class="td-title" colspan="2"></td>
                                </tr>
                            </tbody>
                        
                        </table>
                    </div>
                </section>
            </main>
        </div>
        <%@ include file="../commons/footer.jsp"%>
    </body>
    <script src="/<%=request.getContextPath().split("/")[1]%>/js/viewJs/inquiry/calendarYearToDate.js"></script>

</html>
