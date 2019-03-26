<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> <%@ taglib
uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> <%@ page
language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html lang="en">
    <head>
        <title data-localize="headTitle.calendarYTD"></title>
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <%@ include file="../commons/header.jsp"%>
    </head>
    <body class="hold-transition sidebar-mini">
        <div class="wrapper">
            <%@ include file="../commons/bar.jsp"%>

            <main class="content-wrapper" id="content" tabindex="-1">
                <section class="content">
                    <div class="clearfix no-print section-title">
                        <h1 class="pageTitle" data-localize="title.calendarYearToDate"></h1>
                        <div class="pull-right right-btn">
                            <button class="btn btn-primary download-pdf" onclick="downloadPDF()" title="" aria-label="" data-localize="label.exportPDF" data-localize-notText="true">
                                <i class="fa fa-file-pdf-o"></i>
                            </button>
                            <button
                                class="btn btn-primary pull-right"
                                onclick="doPrint()"
                                data-localize="label.print"
                            >
                            </button>
                        </div>
                    </div>
                    <div class="content-white EMP-detail">
                        <div class="exportPDFBox">
                                <div class="print-block print-title">
                                        <div style="text-align:center;margin-bottom:10px;">
                                            ${sessionScope.district.name}<br />${sessionScope.district.address}<br />
                                            ${sessionScope.district.city},
                                            ${sessionScope.district.state} ${sessionScope.district.zip}-${sessionScope.district.zip4}
                                        </div>
                                        <div style="text-align:center;">
                                                <span data-localize="title.calendarYearToDate"></span><br />
                                            <div id="date-now"></div>
                                        </div>
                                    </div>
                                    <div class="print-block print-table-header">
                                        <div class="flex-line">
                                            <div class="left-title"><span data-localize="label.employeeName"></span>:</div>
                                            <div class="right-conent">
                                                ${sessionScope.userDetail.nameF} ${sessionScope.userDetail.nameM} ${sessionScope.userDetail.nameL}
                                            </div>
                                        </div>
                                        <div class="flex-line">
                                            <div class="left-title"><span data-localize="label.employeeId"></span>:</div>
                                            <div class="right-conent">${sessionScope.userDetail.empNbr}</div>
                                        </div>
                                        <div class="flex-line">
                                            <div class="left-title"><span data-localize="label.calendarYear"></span>:</div>
                                            <div class="right-conent">${selectedYear}</div>
                                        </div>
                                        <div class="flex-line">
                                            <div class="left-title" ><span data-localize="label.frequency"></span>:</div>
                                            <div class="right-conent">${freq}</div>
                                        </div>
                                        <div class="flex-line">
                                            <div class="left-title">
                                                <span data-localize="label.lastPostedPayDate"></span>:
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
                                <label class="form-title" for="year" data-localize="label.pleaseSelectYear"
                                    ></label
                                >
                                <select class="form-control" name="year" id="year" onchange="submitCalendarForm()">
                                    <c:forEach var="year" items="${years}" varStatus="years">
                                        <option value="${year}" <c:if test="${year == selectedYear }">selected</c:if>>${year}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </form>
                        <p class="no-print table-top-title">
                            <b><span data-localize="label.frequency"></span>: ${freq}</b>
                        </p>
                        <p class="no-print">
                            <span data-localize="label.lastPostedPayDate"></span>:${latestPayDate}
                        </p>
                        <table
                            class="table border-table responsive-table no-thead print-table calendarYTDTable"
                        >
                            <tbody>
                                <tr>
                                    <td id="contractPay" class="td-title" data-localize="calendarTable.contractPay"></td>
                                    <td
                                        headers="contractPay" class="td-content" data-localize-location="data-title" 
                                        data-localize="calendarTable.contractPay"
                                    >
                                    <fmt:formatNumber value="${calendar.contrAmt}" pattern="#,##0.00"/>
                                    </td>
                                    <td id="nonContractPay" class="td-title" data-localize="calendarTable.nonContractPay"></td>
                                    <td
                                        headers="nonContractPay" class="td-content" data-localize-location="data-title" 
                                        data-localize="calendarTable.nonContractPay"
                                    >
                                    <fmt:formatNumber value="${calendar.noncontrAmt}" pattern="#,##0.00"/>
                                    </td>
                                    <td id="supplementalPay" class="td-title" data-localize="calendarTable.supplementalPay"></td>
                                    <td
                                        headers="supplementalPay" class="td-content" data-localize-location="data-title" 
                                        data-localize="calendarTable.supplementalPay"
                                    >
                                    <fmt:formatNumber value="${calendar.supplPayAmt}" pattern="#,##0.00"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td id="withholdingGross" class="td-title" data-localize="calendarTable.withholdingGross"></td>
                                    <td
                                        headers="withholdingGross" class="td-content" data-localize-location="data-title" 
                                        data-localize="calendarTable.withholdingGross"
                                    >
                                    <fmt:formatNumber value="${calendar.whGross}" pattern="#,##0.00"/>
                                    </td>
                                    <td id="withholdingTax" class="td-title" data-localize="calendarTable.withholdingTax"></td>
                                    <td
                                        headers="withholdingTax" class="td-content" data-localize-location="data-title" 
                                        data-localize="calendarTable.withholdingTax"
                                    >
                                    <fmt:formatNumber value="${calendar.whTax}" pattern="#,##0.00"/>
                                    </td>
                                    <td id="earnedIncomeCredit" class="td-title" data-localize="calendarTable.earnedIncomeCredit">
                                        
                                    </td>
                                    <td
                                        headers="earnedIncomeCredit" class="td-content" data-localize-location="data-title" 
                                        data-localize="calendarTable.earnedIncomeCredit"
                                    >
                                    <fmt:formatNumber value="${calendar.eicAmt}" pattern="#,##0.00"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td id="ficaGross" class="td-title" data-localize="calendarTable.ficaGross"></td>
                                    <td
                                        headers="ficaGross" class="td-content" data-localize-location="data-title" 
                                        data-localize="calendarTable.ficaGross"
                                    >
                                    <fmt:formatNumber value="${calendar.ficaGross}" pattern="#,##0.00"/>
                                    </td>
                                    <td id="ficaTax" class="td-title" data-localize="calendarTable.ficaTax"></td>
                                    <td
                                        headers="ficaTax" class="td-content" data-localize-location="data-title" 
                                        data-localize="calendarTable.ficaTax"
                                    >
                                    <fmt:formatNumber value="${calendar.ficaTax}" pattern="#,##0.00"/>
                                    </td>
                                    <td class="td-title" colspan="2"></td>
                                </tr>
                                <tr>
                                    <td id="dependentCare" class="td-title" data-localize="calendarTable.dependentCare">
                                        
                                    </td>
                                    <td
                                        headers="dependentCare" class="td-content" data-localize-location="data-title" 
                                        data-localize="calendarTable.dependentCare"
                                    >
                                    <fmt:formatNumber value="${calendar.dependCare}" pattern="#,##0.00"/>
                                    </td>
                                    <td id="dependentCareEmployer" class="td-title" data-localize="calendarTable.dependentCareEmployer">
                                        
                                    </td>
                                    <td
                                        headers="dependentCareEmployer" class="td-content" data-localize-location="data-title" 
                                        data-localize="calendarTable.dependentCareEmployer"
                                    >
                                    <fmt:formatNumber value="${calendar.emplrDependCare}" pattern="#,##0.00"/>
                                    </td>
                                    <td id="dependentCareExceeds" class="td-title" data-localize="calendarTable.dependentCareExceeds">
                                        
                                    </td>
                                    <td
                                        headers="dependentCareExceeds" class="td-content" data-localize-location="data-title" 
                                        data-localize="calendarTable.dependentCareExceeds"
                                    >
                                    <fmt:formatNumber value="${calendar.emplrDependCareTax}" pattern="#,##0.00"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td id="medicareGross" class="td-title" data-localize="calendarTable.medicareGross"></td>
                                    <td
                                        headers="medicareGross" class="td-content" data-localize-location="data-title" 
                                        data-localize="calendarTable.medicareGross"
                                    >
                                    <fmt:formatNumber value="${calendar.medGross}" pattern="#,##0.00"/>
                                    </td>
                                    <td id="medicareTax" class="td-title" data-localize="calendarTable.medicareTax"></td>
                                    <td
                                        headers="medicareTax" class="td-content" data-localize-location="data-title" 
                                        data-localize="calendarTable.medicareTax"
                                    >
                                    <fmt:formatNumber value="${calendar.medTax}" pattern="#,##0.00"/>
                                    </td>
                                    <td class="td-title" colspan="2"></td>
                                </tr>
                                <tr>
                                    <td colspan="6">&nbsp;</td>
                                </tr>
                                <tr>
                                    <td id="annuityDeduction" class="td-title" data-localize="calendarTable.annuityDeduction"></td>
                                    <td
                                        headers="annuityDeduction" class="td-content" data-localize-location="data-title" 
                                        data-localize="calendarTable.annuityDeduction"
                                    >
                                    <fmt:formatNumber value="${calendar.annuityDed}" pattern="#,##0.00"/>
                                    </td>
                                    <td id="roth403BAfterTax" class="td-title" data-localize="calendarTable.roth403BAfterTax">
                                        
                                    </td>
                                    <td
                                        headers="roth403BAfterTax" class="td-content" data-localize-location="data-title" 
                                        data-localize="calendarTable.roth403BAfterTax"
                                    >
                                    <fmt:formatNumber value="${calendar.annuityRoth}" pattern="#,##0.00"/>
                                    </td>
                                    <td id="taxableBenefits" class="td-title" data-localize="calendarTable.taxableBenefits"></td>
                                    <td
                                        headers="taxableBenefits" class="td-content" data-localize-location="data-title" 
                                        data-localize="calendarTable.taxableBenefits"
                                    >
                                    <fmt:formatNumber value="${calendar.taxedBenefits}" pattern="#,##0.00"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td id="annuity457Employee" class="td-title" data-localize="calendarTable.annuity457Employee">
                                       
                                    </td>
                                    <td
                                        headers="annuity457Employee" class="td-content" data-localize-location="data-title" 
                                        data-localize="calendarTable.annuity457Employee"
                                    >
                                    <fmt:formatNumber value="${calendar.emp457Contrib}" pattern="#,##0.00"/>
                                    </td>
                                    <td id="annuity457Employer" class="td-title" data-localize="calendarTable.annuity457Employer">
                                        
                                    </td>
                                    <td
                                        headers="annuity457Employer" class="td-content" data-localize-location="data-title" 
                                        data-localize="calendarTable.annuity457Employer"
                                    >
                                    <fmt:formatNumber value="${calendar.emplr457Contrib}" pattern="#,##0.00"/>
                                    </td>
                                    <td id="annuity457Withdraw" class="td-title" data-localize="calendarTable.annuity457Withdraw"></td>
                                    <td
                                        headers="annuity457Withdraw" class="td-content" data-localize-location="data-title" 
                                        data-localize="calendarTable.annuity457Withdraw"
                                    >
                                    <fmt:formatNumber value="${calendar.withdraw457}" pattern="#,##0.00"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="6">&nbsp;</td>
                                </tr>
                                <tr>
                                    <td id="nonTrsBusinessExpense" class="td-title" data-localize="calendarTable.nonTrsBusinessExpense">
                                        
                                    </td>
                                    <td
                                        headers="nonTrsBusinessExpense" class="td-content" data-localize-location="data-title" 
                                        data-localize="calendarTable.nonTrsBusinessExpense"
                                    >
                                    <fmt:formatNumber value="${calendar.nontrsBusAllow}" pattern="#,##0.00"/>
                                    </td>
                                    <td id="nonTrsReimbursementBase" class="td-title" data-localize="calendarTable.nonTrsReimbursementBase">
                                        
                                    </td>
                                    <td
                                        headers="nonTrsReimbursementBase" class="td-content" data-localize-location="data-title" 
                                        data-localize="calendarTable.nonTrsReimbursementBase"
                                    >
                                    <fmt:formatNumber value="${calendar.nontrsReimbrBase}" pattern="#,##0.00"/>
                                    </td>
                                    <td id="nonTrsReimbursementExcess" class="td-title" data-localize="calendarTable.nonTrsReimbursementExcess">
                                        
                                    </td>
                                    <td
                                        headers="nonTrsReimbursementExcess" class="td-content" data-localize-location="data-title" 
                                        data-localize="calendarTable.nonTrsReimbursementExcess"
                                    >
                                    <fmt:formatNumber value="${calendar.nontrsReimbrExcess}" pattern="#,##0.00"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td id="movingExpenseReimbursement" class="td-title" data-localize="calendarTable.movingExpenseReimbursement">
                                        
                                    </td>
                                    <td
                                        headers="movingExpenseReimbursement" class="td-content" data-localize-location="data-title" 
                                        data-localize="calendarTable.movingExpenseReimbursement"
                                    >
                                    <fmt:formatNumber value="${calendar.movingExpReimbr}" pattern="#,##0.00"/>
                                    </td>
                                    <td id="nonTrsNonTaxBusinessAllow" class="td-title" data-localize="calendarTable.nonTrsNonTaxBusinessAllow">
                                        
                                    </td>
                                    <td
                                        headers="nonTrsNonTaxBusinessAllow" class="td-content" data-localize-location="data-title" 
                                        data-localize="calendarTable.nonTrsNonTaxBusinessAllow"
                                    >
                                    <fmt:formatNumber value="${calendar.nontrsNontaxBusAllow}" pattern="#,##0.00"/>
                                    </td>
                                    <td id="nonTrsNonTaxNonPayAllow" class="td-title" data-localize="calendarTable.nonTrsNonTaxNonPayAllow">
                                        
                                    </td>
                                    <td
                                        headers="nonTrsNonTaxNonPayAllow" class="td-content" data-localize-location="data-title" 
                                        data-localize="calendarTable.nonTrsNonTaxNonPayAllow"
                                    >
                                    <fmt:formatNumber value="${calendar.nontrsNontaxNonpayAllow}" pattern="#,##0.00"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="6">&nbsp;</td>
                                </tr>
                                <tr>
                                    <td id="salaryReduction" class="td-title" data-localize="calendarTable.salaryReduction">
                                        
                                    </td>
                                    <td
                                        headers="salaryReduction" class="td-content" data-localize-location="data-title" 
                                        data-localize="calendarTable.salaryReduction"
                                    >
                                    <fmt:formatNumber value="${calendar.trsSalaryRed}" pattern="#,##0.00"/>
                                    </td>
                                    <td id="trsInsurance" class="td-title" data-localize="calendarTable.trsInsurance"></td>
                                    <td
                                        headers="trsInsurance" class="td-content" data-localize-location="data-title" 
                                        data-localize="calendarTable.trsInsurance"
                                    >
                                    <fmt:formatNumber value="${trsIns}" pattern="#,##0.00"/>
                                    </td>
                                    <td class="td-title" colspan="2"></td>
                                </tr>
                                <tr>
                                    <td id="hsaEmployerContribution" class="td-title" data-localize="calendarTable.hsaEmployerContribution">
                                        
                                    </td>
                                    <td
                                        headers="hsaEmployerContribution" class="td-content" data-localize-location="data-title" 
                                        data-localize="calendarTable.hsaEmployerContribution"
                                    >
                                    <fmt:formatNumber value="${calendar.hsaEmplrContrib}" pattern="#,##0.00"/>
                                    </td>
                                    <td id="hsaEmployeeSalaryReductionContribution" class="td-title" data-localize="calendarTable.hsaEmployeeSalaryReductionContribution">
                                        
                                    </td>
                                    <td
                                        headers="hsaEmployeeSalaryReductionContribution" class="td-content" data-localize-location="data-title" 
                                        data-localize="calendarTable.hsaEmployeeSalaryReductionContribution"
                                    >
                                    <fmt:formatNumber value="${calendar.hsaEmpSalRedctnContrib}" pattern="#,##0.00"/>
                                    </td>

                                    <td id="hireExemptWgs" class="td-title" data-localize="calendarTable.hireExemptWgs"></td>
                                    <td
                                        headers="hireExemptWgs" class="td-content" data-localize-location="data-title" 
                                        data-localize="calendarTable.hireExemptWgs"
                                    >
                                    <fmt:formatNumber value="${calendar.hireExemptWgs}" pattern="#,##0.00"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td id="taxedLifeContribution" class="td-title" data-localize="calendarTable.taxedLifeContribution">
                                        
                                    </td>
                                    <td
                                        headers="taxedLifeContribution" class="td-content" data-localize-location="data-title" 
                                        data-localize="calendarTable.taxedLifeContribution"
                                    >
                                    <fmt:formatNumber value="${calendar.taxEmplrLife}" pattern="#,##0.00"/>
                                    </td>
                                    <td id="taxedGroupContribution" class="td-title" data-localize="calendarTable.taxedGroupContribution">
                                    </td>
                                    <td
                                        headers="taxedGroupContribution" class="td-content" data-localize-location="data-title" 
                                        data-localize="calendarTable.taxedGroupContribution"
                                    >
                                    <fmt:formatNumber value="${calendar.taxEmplrLifeGrp}" pattern="#,##0.00"/>
                                    </td>
                                    <td id="healthInsuranceDeduction" class="td-title" data-localize="calendarTable.healthInsuranceDeduction">
                                       
                                    </td>
                                    <td
                                        headers="healthInsuranceDeduction" class="td-content" data-localize-location="data-title" 
                                        data-localize="calendarTable.healthInsuranceDeduction"
                                    >
                                    <fmt:formatNumber value="${calendar.hlthInsDed}" pattern="#,##0.00"/>
                                    </td>
                                </tr>

                                <tr>
                                    <td colspan="6">&nbsp;</td>
                                </tr>
                                <tr>
                                    <td id="emplrPrvdHlthcare" class="td-title" data-localize="calendarTable.emplrPrvdHlthcare">
                                        
                                    </td>
                                    <td
                                        headers="emplrPrvdHlthcare" class="td-content" data-localize-location="data-title" 
                                        data-localize="calendarTable.emplrPrvdHlthcare"
                                    >
                                    <fmt:formatNumber value="${calendar.emplrPrvdHlthcare}" pattern="#,##0.00"/>
                                    </td>

                                    <td id="annuityRoth457b" class="td-title" data-localize="calendarTable.annuityRoth457b"></td>
                                    <td
                                        headers="annuityRoth457b" class="td-content" data-localize-location="data-title" 
                                        data-localize="calendarTable.annuityRoth457b"
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
