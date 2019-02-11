<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> <%@ taglib
uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> <%@ page
language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="en">
    <head>
        <title data-localize="headTitle.calendarYTD"></title>
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <%@ include file="../commons/header.jsp"%>
    </head>
    <body class="hold-transition sidebar-mini">
        <div class="wrapper">
            <%@ include file="../commons/bar.jsp"%>

            <main class="content-wrapper" id="content">
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
                                    ${calendar.contrAmt}
                                    </td>
                                    <td class="td-title" data-localize="calendarTable.nonContractPay"></td>
                                    <td
                                        class="td-content"
                                        data-localize="calendarTable.nonContractPay"
                                    >
                                    ${calendar.noncontrAmt}
                                    </td>
                                    <td class="td-title" data-localize="calendarTable.supplementalPay"></td>
                                    <td
                                        class="td-content"
                                        data-localize="calendarTable.supplementalPay"
                                    >
                                    ${calendar.supplPayAmt}
                                    </td>
                                </tr>
                                <tr>
                                    <td class="td-title" data-localize="calendarTable.withholdingGross"></td>
                                    <td
                                        class="td-content"
                                        data-localize="calendarTable.withholdingGross"
                                    >
                                    ${calendar.whGross}
                                    </td>
                                    <td class="td-title" data-localize="calendarTable.withholdingTax"></td>
                                    <td
                                        class="td-content"
                                        data-localize="calendarTable.withholdingTax"
                                    >
                                    ${calendar.whTax}
                                    </td>
                                    <td class="td-title" data-localize="calendarTable.earnedIncomeCredit">
                                        
                                    </td>
                                    <td
                                        class="td-content"
                                        data-localize="calendarTable.earnedIncomeCredit"
                                    >
                                    ${calendar.eicAmt}
                                    </td>
                                </tr>
                                <tr>
                                    <td class="td-title" data-localize="calendarTable.ficaGross"></td>
                                    <td
                                        class="td-content"
                                        data-localize="calendarTable.ficaGross"
                                    >
                                    ${calendar.ficaGross}
                                    </td>
                                    <td class="td-title" data-localize="calendarTable.ficaTax"></td>
                                    <td
                                        class="td-content"
                                        data-localize="calendarTable.ficaTax"
                                    >
                                    ${calendar.ficaTax}
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
                                    ${calendar.dependCare}
                                    </td>
                                    <td class="td-title" data-localize="calendarTable.dependentCareEmployer">
                                        
                                    </td>
                                    <td
                                        class="td-content"
                                        data-localize="calendarTable.dependentCareEmployer"
                                    >
                                    ${calendar.emplrDependCare}
                                    </td>
                                    <td class="td-title" data-localize="calendarTable.dependentCareExceeds">
                                        
                                    </td>
                                    <td
                                        class="td-content"
                                        data-localize="calendarTable.dependentCareExceeds"
                                    >
                                    ${calendar.emplrDependCareTax}
                                    </td>
                                </tr>
                                <tr>
                                    <td class="td-title" data-localize="calendarTable.medicareGross"></td>
                                    <td
                                        class="td-content"
                                        data-localize="calendarTable.medicareGross"
                                    >
                                    ${calendar.medGross}
                                    </td>
                                    <td class="td-title" data-localize="calendarTable.medicareTax"></td>
                                    <td
                                        class="td-content"
                                        data-localize="calendarTable.medicareTax"
                                    >
                                    ${calendar.medTax}
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
                                    ${calendar.annuityDed}
                                    </td>
                                    <td class="td-title" data-localize="calendarTable.roth403BAfterTax">
                                        
                                    </td>
                                    <td
                                        class="td-content"
                                        data-localize="calendarTable.roth403BAfterTax"
                                    >
                                    ${calendar.annuityRoth}
                                    </td>
                                    <td class="td-title" data-localize="calendarTable.taxableBenefits"></td>
                                    <td
                                        class="td-content"
                                        data-localize="calendarTable.taxableBenefits"
                                    >
                                    ${calendar.taxedBenefits}
                                    </td>
                                </tr>
                                <tr>
                                    <td class="td-title" data-localize="calendarTable.annuity457Employee">
                                       
                                    </td>
                                    <td
                                        class="td-content"
                                        data-localize="calendarTable.annuity457Employee"
                                    >
                                    ${calendar.emp457Contrib}
                                    </td>
                                    <td class="td-title" data-localize="calendarTable.annuity457Employer">
                                        
                                    </td>
                                    <td
                                        class="td-content"
                                        data-localize="calendarTable.annuity457Employer"
                                    >
                                    ${calendar.emplr457Contrib}
                                    </td>
                                    <td class="td-title" data-localize="calendarTable.annuity457Withdraw"></td>
                                    <td
                                        class="td-content"
                                        data-localize="calendarTable.annuity457Withdraw"
                                    >
                                    ${calendar.withdraw457}
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
                                    ${calendar.nontrsBusAllow}
                                    </td>
                                    <td class="td-title" data-localize="calendarTable.nonTrsReimbursementBase">
                                        
                                    </td>
                                    <td
                                        class="td-content"
                                        data-localize="calendarTable.nonTrsReimbursementBase"
                                    >
                                    ${calendar.nontrsReimbrBase}
                                    </td>
                                    <td class="td-title" data-localize="calendarTable.nonTrsReimbursementExcess">
                                        
                                    </td>
                                    <td
                                        class="td-content"
                                        data-localize="calendarTable.nonTrsReimbursementExcess"
                                    >
                                    ${calendar.nontrsReimbrExcess}
                                    </td>
                                </tr>
                                <tr>
                                    <td class="td-title" data-localize="calendarTable.movingExpenseReimbursement">
                                        
                                    </td>
                                    <td
                                        class="td-content"
                                        data-localize="calendarTable.movingExpenseReimbursement"
                                    >
                                    ${calendar.movingExpReimbr}
                                    </td>
                                    <td class="td-title" data-localize="calendarTable.nonTrsNonTaxBusinessAllow">
                                        
                                    </td>
                                    <td
                                        class="td-content"
                                        data-localize="calendarTable.nonTrsNonTaxBusinessAllow"
                                    >
                                    ${calendar.nontrsNontaxBusAllow}
                                    </td>
                                    <td class="td-title" data-localize="calendarTable.nonTrsNonTaxNonPayAllow">
                                        
                                    </td>
                                    <td
                                        class="td-content"
                                        data-localize="calendarTable.nonTrsNonTaxNonPayAllow"
                                    >
                                    ${calendar.nontrsNontaxNonpayAllow}
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
                                    ${calendar.trsSalaryRed}
                                    </td>
                                    <td class="td-title" data-localize="calendarTable.trsInsurance"></td>
                                    <td
                                        class="td-content"
                                        data-localize="calendarTable.trsInsurance"
                                    >
                                    ${trsIns}
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
                                    ${calendar.hsaEmplrContrib}
                                    </td>
                                    <td class="td-title" data-localize="calendarTable.hsaEmployeeSalaryReductionContribution">
                                        
                                    </td>
                                    <td
                                        class="td-content"
                                        data-localize="calendarTable.hsaEmployeeSalaryReductionContribution"
                                    >
                                    ${calendar.hsaEmpSalRedctnContrib}
                                    </td>

                                    <td class="td-title" data-localize="calendarTable.hireExemptWgs"></td>
                                    <td
                                        class="td-content"
                                        data-localize="calendarTable.hireExemptWgs"
                                    >
                                    ${calendar.hireExemptWgs}
                                    </td>
                                </tr>
                                <tr>
                                    <td class="td-title" data-localize="calendarTable.taxedLifeContribution">
                                        
                                    </td>
                                    <td
                                        class="td-content"
                                        data-localize="calendarTable.taxedLifeContribution"
                                    >
                                    ${calendar.taxEmplrLife}
                                    </td>
                                    <td class="td-title" data-localize="calendarTable.taxedGroupContribution">
                                    </td>
                                    <td
                                        class="td-content"
                                        data-localize="calendarTable.taxedGroupContribution"
                                    >
                                    ${calendar.taxEmplrLifeGrp}
                                    </td>
                                    <td class="td-title" data-localize="calendarTable.healthInsuranceDeduction">
                                       
                                    </td>
                                    <td
                                        class="td-content"
                                        data-localize="calendarTable.healthInsuranceDeduction"
                                    >
                                    ${calendar.hlthInsDed}
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
                                    ${calendar.emplrPrvdHlthcare}
                                    </td>

                                    <td class="td-title" data-localize="calendarTable.annuityRoth457b"></td>
                                    <td
                                        class="td-content"
                                        data-localize="calendarTable.annuityRoth457b"
                                    >
                                    ${calendar.annuityRoth457b}
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
