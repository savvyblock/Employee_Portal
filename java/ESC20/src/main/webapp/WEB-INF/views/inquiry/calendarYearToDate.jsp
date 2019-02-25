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
                    <h2 class="clearfix no-print section-title">
                        <span data-localize="title.calendarYearToDate"></span>
                        <button
                            class="btn btn-primary pull-right"
                            onclick="doPrint()"
                            data-localize="label.print"
                        >
                        </button>
                    </h2>
                    <div class="content-white EMP-detail">
                        <div class="print-block print-title">
                            <div style="text-align:center;margin-bottom:10px;">
                                ${sessionScope.district.name}<br />${sessionScope.district.address}<br />
                                ${sessionScope.district.city},
                                ${sessionScope.district.state} ${sessionScope.district.phone}
                            </div>
                            <div style="text-align:center;">
                                    <span data-localize="label.currentPayInfo"></span><br />
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
                        <form
                            class="no-print searchForm"
                            action="getCalendarYearToDateByYear"
                            id="selectCalendar"
                            method="POST"
                        >
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
                        <table
                            class="table border-table responsive-table no-thead print-table"
                        >
                            <tbody>
                                <tr>
                                    <td class="td-title" data-localize="calendarTable.contractPay"></td>
                                    <td
                                        class="td-content"
                                        data-localize="calendarTable.contractPay"
                                    >
                                    <fmt:formatNumber value="${calendar.contrAmt}" pattern="#,##0.00"/>
                                    </td>
                                    <td class="td-title" data-localize="calendarTable.nonContractPay"></td>
                                    <td
                                        class="td-content"
                                        data-localize="calendarTable.nonContractPay"
                                    >
                                    <fmt:formatNumber value="${calendar.noncontrAmt}" pattern="#,##0.00"/>
                                    </td>
                                    <td class="td-title" data-localize="calendarTable.supplementalPay"></td>
                                    <td
                                        class="td-content"
                                        data-localize="calendarTable.supplementalPay"
                                    >
                                    <fmt:formatNumber value="${calendar.supplPayAmt}" pattern="#,##0.00"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="td-title" data-localize="calendarTable.withholdingGross"></td>
                                    <td
                                        class="td-content"
                                        data-localize="calendarTable.withholdingGross"
                                    >
                                    <fmt:formatNumber value="${calendar.whGross}" pattern="#,##0.00"/>
                                    </td>
                                    <td class="td-title" data-localize="calendarTable.withholdingTax"></td>
                                    <td
                                        class="td-content"
                                        data-localize="calendarTable.withholdingTax"
                                    >
                                    <fmt:formatNumber value="${calendar.whTax}" pattern="#,##0.00"/>
                                    </td>
                                    <td class="td-title" data-localize="calendarTable.earnedIncomeCredit">
                                        
                                    </td>
                                    <td
                                        class="td-content"
                                        data-localize="calendarTable.earnedIncomeCredit"
                                    >
                                    <fmt:formatNumber value="${calendar.eicAmt}" pattern="#,##0.00"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="td-title" data-localize="calendarTable.ficaGross"></td>
                                    <td
                                        class="td-content"
                                        data-localize="calendarTable.ficaGross"
                                    >
                                    <fmt:formatNumber value="${calendar.ficaGross}" pattern="#,##0.00"/>
                                    </td>
                                    <td class="td-title" data-localize="calendarTable.ficaTax"></td>
                                    <td
                                        class="td-content"
                                        data-localize="calendarTable.ficaTax"
                                    >
                                    <fmt:formatNumber value="${calendar.ficaTax}" pattern="#,##0.00"/>
                                    </td>
                                    <td class="td-title" colspan="2"></td>
                                </tr>
                                <tr>
                                    <td class="td-title" data-localize="calendarTable.dependentCare">
                                        
                                    </td>
                                    <td
                                        class="td-content"
                                        data-localize="calendarTable.dependentCare"
                                    >
                                    <fmt:formatNumber value="${calendar.dependCare}" pattern="#,##0.00"/>
                                    </td>
                                    <td class="td-title" data-localize="calendarTable.dependentCareEmployer">
                                        
                                    </td>
                                    <td
                                        class="td-content"
                                        data-localize="calendarTable.dependentCareEmployer"
                                    >
                                    <fmt:formatNumber value="${calendar.emplrDependCare}" pattern="#,##0.00"/>
                                    </td>
                                    <td class="td-title" data-localize="calendarTable.dependentCareExceeds">
                                        
                                    </td>
                                    <td
                                        class="td-content"
                                        data-localize="calendarTable.dependentCareExceeds"
                                    >
                                    <fmt:formatNumber value="${calendar.emplrDependCareTax}" pattern="#,##0.00"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="td-title" data-localize="calendarTable.medicareGross"></td>
                                    <td
                                        class="td-content"
                                        data-localize="calendarTable.medicareGross"
                                    >
                                    <fmt:formatNumber value="${calendar.medGross}" pattern="#,##0.00"/>
                                    </td>
                                    <td class="td-title" data-localize="calendarTable.medicareTax"></td>
                                    <td
                                        class="td-content"
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
                                    <td class="td-title" data-localize="calendarTable.annuityDeduction"></td>
                                    <td
                                        class="td-content"
                                        data-localize="calendarTable.annuityDeduction"
                                    >
                                    <fmt:formatNumber value="${calendar.annuityDed}" pattern="#,##0.00"/>
                                    </td>
                                    <td class="td-title" data-localize="calendarTable.roth403BAfterTax">
                                        
                                    </td>
                                    <td
                                        class="td-content"
                                        data-localize="calendarTable.roth403BAfterTax"
                                    >
                                    <fmt:formatNumber value="${calendar.annuityRoth}" pattern="#,##0.00"/>
                                    </td>
                                    <td class="td-title" data-localize="calendarTable.taxableBenefits"></td>
                                    <td
                                        class="td-content"
                                        data-localize="calendarTable.taxableBenefits"
                                    >
                                    <fmt:formatNumber value="${calendar.taxedBenefits}" pattern="#,##0.00"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="td-title" data-localize="calendarTable.annuity457Employee">
                                       
                                    </td>
                                    <td
                                        class="td-content"
                                        data-localize="calendarTable.annuity457Employee"
                                    >
                                    <fmt:formatNumber value="${calendar.emp457Contrib}" pattern="#,##0.00"/>
                                    </td>
                                    <td class="td-title" data-localize="calendarTable.annuity457Employer">
                                        
                                    </td>
                                    <td
                                        class="td-content"
                                        data-localize="calendarTable.annuity457Employer"
                                    >
                                    <fmt:formatNumber value="${calendar.emplr457Contrib}" pattern="#,##0.00"/>
                                    </td>
                                    <td class="td-title" data-localize="calendarTable.annuity457Withdraw"></td>
                                    <td
                                        class="td-content"
                                        data-localize="calendarTable.annuity457Withdraw"
                                    >
                                    <fmt:formatNumber value="${calendar.withdraw457}" pattern="#,##0.00"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="6">&nbsp;</td>
                                </tr>
                                <tr>
                                    <td class="td-title" data-localize="calendarTable.nonTrsBusinessExpense">
                                        
                                    </td>
                                    <td
                                        class="td-content"
                                        data-localize="calendarTable.nonTrsBusinessExpense"
                                    >
                                    <fmt:formatNumber value="${calendar.nontrsBusAllow}" pattern="#,##0.00"/>
                                    </td>
                                    <td class="td-title" data-localize="calendarTable.nonTrsReimbursementBase">
                                        
                                    </td>
                                    <td
                                        class="td-content"
                                        data-localize="calendarTable.nonTrsReimbursementBase"
                                    >
                                    <fmt:formatNumber value="${calendar.nontrsReimbrBase}" pattern="#,##0.00"/>
                                    </td>
                                    <td class="td-title" data-localize="calendarTable.nonTrsReimbursementExcess">
                                        
                                    </td>
                                    <td
                                        class="td-content"
                                        data-localize="calendarTable.nonTrsReimbursementExcess"
                                    >
                                    <fmt:formatNumber value="${calendar.nontrsReimbrExcess}" pattern="#,##0.00"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="td-title" data-localize="calendarTable.movingExpenseReimbursement">
                                        
                                    </td>
                                    <td
                                        class="td-content"
                                        data-localize="calendarTable.movingExpenseReimbursement"
                                    >
                                    <fmt:formatNumber value="${calendar.movingExpReimbr}" pattern="#,##0.00"/>
                                    </td>
                                    <td class="td-title" data-localize="calendarTable.nonTrsNonTaxBusinessAllow">
                                        
                                    </td>
                                    <td
                                        class="td-content"
                                        data-localize="calendarTable.nonTrsNonTaxBusinessAllow"
                                    >
                                    <fmt:formatNumber value="${calendar.nontrsNontaxBusAllow}" pattern="#,##0.00"/>
                                    </td>
                                    <td class="td-title" data-localize="calendarTable.nonTrsNonTaxNonPayAllow">
                                        
                                    </td>
                                    <td
                                        class="td-content"
                                        data-localize="calendarTable.nonTrsNonTaxNonPayAllow"
                                    >
                                    <fmt:formatNumber value="${calendar.nontrsNontaxNonpayAllow}" pattern="#,##0.00"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="6">&nbsp;</td>
                                </tr>
                                <tr>
                                    <td class="td-title" data-localize="calendarTable.salaryReduction">
                                        
                                    </td>
                                    <td
                                        class="td-content"
                                        data-localize="calendarTable.salaryReduction"
                                    >
                                    <fmt:formatNumber value="${calendar.trsSalaryRed}" pattern="#,##0.00"/>
                                    </td>
                                    <td class="td-title" data-localize="calendarTable.trsInsurance"></td>
                                    <td
                                        class="td-content"
                                        data-localize="calendarTable.trsInsurance"
                                    >
                                    <fmt:formatNumber value="${trsIns}" pattern="#,##0.00"/>
                                    </td>
                                    <td class="td-title" colspan="2"></td>
                                </tr>
                                <tr>
                                    <td class="td-title" data-localize="calendarTable.hsaEmployerContribution">
                                        
                                    </td>
                                    <td
                                        class="td-content"
                                        data-localize="calendarTable.hsaEmployerContribution"
                                    >
                                    <fmt:formatNumber value="${calendar.hsaEmplrContrib}" pattern="#,##0.00"/>
                                    </td>
                                    <td class="td-title" data-localize="calendarTable.hsaEmployeeSalaryReductionContribution">
                                        
                                    </td>
                                    <td
                                        class="td-content"
                                        data-localize="calendarTable.hsaEmployeeSalaryReductionContribution"
                                    >
                                    <fmt:formatNumber value="${calendar.hsaEmpSalRedctnContrib}" pattern="#,##0.00"/>
                                    </td>

                                    <td class="td-title" data-localize="calendarTable.hireExemptWgs"></td>
                                    <td
                                        class="td-content"
                                        data-localize="calendarTable.hireExemptWgs"
                                    >
                                    <fmt:formatNumber value="${calendar.hireExemptWgs}" pattern="#,##0.00"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="td-title" data-localize="calendarTable.taxedLifeContribution">
                                        
                                    </td>
                                    <td
                                        class="td-content"
                                        data-localize="calendarTable.taxedLifeContribution"
                                    >
                                    <fmt:formatNumber value="${calendar.taxEmplrLife}" pattern="#,##0.00"/>
                                    </td>
                                    <td class="td-title" data-localize="calendarTable.taxedGroupContribution">
                                    </td>
                                    <td
                                        class="td-content"
                                        data-localize="calendarTable.taxedGroupContribution"
                                    >
                                    <fmt:formatNumber value="${calendar.taxEmplrLifeGrp}" pattern="#,##0.00"/>
                                    </td>
                                    <td class="td-title" data-localize="calendarTable.healthInsuranceDeduction">
                                       
                                    </td>
                                    <td
                                        class="td-content"
                                        data-localize="calendarTable.healthInsuranceDeduction"
                                    >
                                    <fmt:formatNumber value="${calendar.hlthInsDed}" pattern="#,##0.00"/>
                                    </td>
                                </tr>

                                <tr>
                                    <td colspan="6">&nbsp;</td>
                                </tr>
                                <tr>
                                    <td class="td-title" data-localize="calendarTable.emplrPrvdHlthcare">
                                        
                                    </td>
                                    <td
                                        class="td-content"
                                        data-localize="calendarTable.emplrPrvdHlthcare"
                                    >
                                    <fmt:formatNumber value="${calendar.emplrPrvdHlthcare}" pattern="#,##0.00"/>
                                    </td>

                                    <td class="td-title" data-localize="calendarTable.annuityRoth457b"></td>
                                    <td
                                        class="td-content"
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
    <script>
        var myDate = new Date()
        var year = myDate.getFullYear()
        var month = myDate.getMonth() + 1
        var date = myDate.getDate()
        var h = myDate.getHours() //get h (0-23)
        var m = myDate.getMinutes() //get m (0-59)
        var s = myDate.getSeconds()
        if (parseInt(h) < 12) {
            var now =
                getNow(month) +
                '-' +
                getNow(date) +
                '-' +
                year +
                ' ' +
                getNow(h) +
                ':' +
                getNow(m) +
                ' ' +
                'AM'
        } else {
            var hour = parseInt(h) - 12
            var now =
                getNow(month) +
                '-' +
                getNow(date) +
                '-' +
                year +
                ' ' +
                getNow(hour) +
                ':' +
                getNow(m) +
                ' ' +
                'PM'
        }

        function getNow(s) {
            return s < 10 ? '0' + s : s
        }
        document.getElementById('date-now').innerHTML = now
        function doPrint() {
            window.print()
        }
        function submitCalendarForm(){
            $("#selectCalendar")[0].submit()
        }
    </script>
</html>
