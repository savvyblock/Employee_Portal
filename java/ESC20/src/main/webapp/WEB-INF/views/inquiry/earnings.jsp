<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> <%@ taglib
uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> <%@ page
language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html> 
<html lang="en">
    <head>
        <title>${sessionScope.languageJSON.headTitle.earnings}</title>
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <%@ include file="../commons/header.jsp"%>
    </head>
    <body class="hold-transition sidebar-mini">
        <div class="wrapper">
            <%@ include file="../commons/bar.jsp"%>

            <main class="content-wrapper" id="content" tabindex="-1">
                    <section class="content">
                            <div class="clearfix no-print section-title">
                                <h1 class="pageTitle">${sessionScope.languageJSON.title.earnings}</h1>
                                <div class="pull-right right-btn">
		                            <%-- <form class="no-print" action="exportPDF" method="POST">
										<input type="hidden" name="${_csrf.parameterName}"
											value="${_csrf.token}" />
										<input type="hidden" name="selectedPayDate"
											value="${selectedPayDate.dateFreqVoidAdjChk}" />
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
                            <div class="toPrint content-white EMP-detail earningPage">
                                <div class="exportPDFBox" id="exportPDFBox">
                                        <div class="print-block print-title">
                                                <div style="text-align:center;margin-bottom:10px;">
                                                        ${sessionScope.district.name}<br />
                                                        <span>${sessionScope.languageJSON.title.earnings}</span>
                                                    <div id="date-now"></div>
                                                </div>
                                            </div>
                                            <table class="print-block  employee-info-table">
                                                <tbody>
                                                    <tr>
                                                        <td>
                                                            <div class="info-flex-item">
                                                                <div class="info-title">
                                                                    <b><span>${sessionScope.languageJSON.earningTable.empNbr}</span>:</b>
                                                                </div>
                                                                <div class="info-content">
                                                                    ${sessionScope.userDetail.empNbr}
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <td>
                                                            <div class="info-flex-item">
                                                                <div class="info-title">
                                                                    <b><span>${sessionScope.languageJSON.earningTable.frequency}</span>:</b>
                                                                </div>
                                                                <div class="info-content">
                                                                    ${freq}
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <td>
                                                            <div class="info-flex-item">
                                                                <div class="info-title">
                                                                    <b><span>${sessionScope.languageJSON.earningTable.primaryCampus}</span>:</b>
                                                                </div>
                                                                <div class="info-content">
                                                                    ${earnings.info.campusId} ${earnings.info.campusName}
                                                                </div>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="text-left" colspan="2">
                                                            <div class="info-flex-item">
                                                                <div class="info-title">
                                                                    <b><span>${sessionScope.languageJSON.earningTable.employeeName}</span>:</b>
                                                                </div>
                                                                <div class="info-content">
                                                                    ${sessionScope.userDetail.nameF} ${sessionScope.userDetail.nameM} ${sessionScope.userDetail.nameL}
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <td>
                                                            <div class="info-flex-item">
                                                                <div class="info-title">
                                                                    <b><span>${sessionScope.languageJSON.earningTable.payCampus}</span>:</b>
                                                                </div>
                                                                <div class="info-content">
                                                                    ${earnings.info.campusId} ${earnings.info.campusName}
                                                                </div>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td>
                                                            <div class="info-flex-item">
                                                                <div class="info-title">
                                                                    <b><span>${sessionScope.languageJSON.earningTable.checkNbr}</span>:</b>
                                                                </div>
                                                                <div class="info-content">
                                                                    ${earnings.info.checkNumber}
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <td>
                                                            <div class="info-flex-item">
                                                                <div class="info-title">
                                                                    <b><span>${sessionScope.languageJSON.earningTable.withholdStat}</span>:</b>
                                                                </div>
                                                                <div class="info-content">
                                                                    <c:if test="${earnings.info.withholdingStatus =='M'}">
                                                                        <span>${sessionScope.languageJSON.label.married}</span>
                                                                    </c:if>
                                                                    <c:if test="${earnings.info.withholdingStatus =='S'}">
                                                                            <span>${sessionScope.languageJSON.label.single}</span>
                                                                    </c:if>
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <td>
                                                            <div class="info-flex-item">
                                                                <div class="info-title">
                                                                    <b><span>${sessionScope.languageJSON.earningTable.exempt}</span>:</b>
                                                                </div>
                                                                <div class="info-content">
                                                                    ${earnings.info.numExceptions}
                                                                </div>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td>
                                                            <div class="info-flex-item">
                                                                <div class="info-title">
                                                                    <b><span>${sessionScope.languageJSON.earningTable.payDate}</span>:</b>
                                                                </div>
                                                                <div class="info-content">
                                                                    ${selectedPayDate.formatedDate}
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <td>
                                                            <div class="info-flex-item">
                                                                <div class="info-title">
                                                                    <b ><span>${sessionScope.languageJSON.earningTable.periodBegin}</span>:</b>
                                                                </div>
                                                                <div class="info-content">
                                                                    ${earnings.info.periodBeginningDate}
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <td>
                                                            <div class="info-flex-item">
                                                                <div class="info-title">
                                                                    <b><span>${sessionScope.languageJSON.earningTable.periodEnd}</span>:</b>
                                                                </div>
                                                                <div class="info-content">
                                                                    ${earnings.info.periodEndingDate}
                                                                </div>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                </tbody>
                                            </table>
                                            <div class="print-block  hr-black"></div>
                                            <div class="print-block print-title">
                                                <div
                                                    style="padding-left:60px;text-align:left;margin-bottom:0;"
                                                >
                                                ${sessionScope.userDetail.nameF} ${sessionScope.userDetail.nameM} ${sessionScope.userDetail.nameL}<br />
                                                    ${sessionScope.district.address}<br />
                                                    ${sessionScope.district.city}, ${sessionScope.district.state} ${sessionScope.district.zip}
                                                </div>
                                                <p
                                                    style="text-align:right;padding-right: 20px;margin: 0;"
                                                   
                                                >
                                                ${sessionScope.languageJSON.label.noteEarning}
                                                </p>
                                                <div class="print-block hr-black"></div>
                                            </div>
                                </div>
                                
                                <c:if test="${not empty message}">
                                    <br/>
                                    <p class="topMsg">${message}</p>
                                    <br/>
                                </c:if>
                                <form
                                    class="no-print searchForm"
                                    action="earningsByPayDate"
                                    id="selectEarnings"
                                    method="POST"
                                >
                                	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                    <div class="form-group in-line">
                                        <label class="form-title" for="payDateString"><span>${sessionScope.languageJSON.label.payDates}</span>:</label>
                                        <select class="form-control" name="payDateString" id="payDateString" onchange="submitEarning()" style="max-width:280px;">
                                            <c:forEach var="payDate" items="${payDates}" varStatus="counter">
                                                <option value="${payDate.dateFreqVoidAdjChk}" <c:if test="${payDate.dateFreq == selectedPayDate.dateFreq }">selected</c:if>>${payDate.label}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </form>
                                <div class="needToClone">                                
                                    <table
                                        class="no-print no-thead table no-border-table responsive-table max-w-550 earning-title-table"
                                    >
                                        <tbody>
                                            <tr>
                                                <td id="campus" class="td-title column1"><span>${sessionScope.languageJSON.earningTable.campus}</span>:</td>
                                                <td headers="campus"
                                                    class="td-content text-left" data-title="${sessionScope.languageJSON.earningTable.campus}"
                                                   
                                                    
                                                >
                                                ${earnings.info.campusId} ${earnings.info.campusName}
                                                </td>
                                                <td colspan="2"></td>
                                            </tr>
                                            <tr>
                                                <td id="checkNumber" class="td-title column1"><span>${sessionScope.languageJSON.earningTable.checkNumber}</span>:</td>
                                                <td headers="checkNumber"
                                                    class="td-content" data-title="${sessionScope.languageJSON.earningTable.checkNumber}"
                                                   
                                                >
                                                ${earnings.info.checkNumber}
                                                </td>
                                                <td id="periodEndingDate" class="td-title">
                                                        <span>${sessionScope.languageJSON.earningTable.periodEndingDate}</span>:
                                                </td>
                                                <td headers="periodEndingDate"
                                                    class="td-content" data-title="${sessionScope.languageJSON.earningTable.periodEndingDate}"
                                                   
                                                >
                                                ${earnings.info.periodEndingDate}
                                                </td>
                                            </tr>
                                            <tr>
                                                <td id="withholdingStatus" class="td-title column1">
                                                        <span>${sessionScope.languageJSON.earningTable.withholdingStatus}</span>:
                                                </td>
                                                <td headers="withholdingStatus"
                                                data-title="${sessionScope.languageJSON.earningTable.withholdingStatus}"
                                                    class="td-content"
                                                   
                                                >
                                                    <c:if test="${earnings.info.withholdingStatus =='M'}">
                                                        <span>${sessionScope.languageJSON.label.married}</span>
                                                    </c:if>
                                                    <c:if test="${earnings.info.withholdingStatus =='S'}">
                                                            <span>${sessionScope.languageJSON.label.single}</span>
                                                    </c:if>
                                                </td>
                                                <td id="numberOfExemptions" class="td-title">
                                                        <span>${sessionScope.languageJSON.earningTable.numberOfExemptions}</span>:
                                                </td>
                                                <td headers="numberOfExemptions"
                                                    class="td-content" data-title="${sessionScope.languageJSON.earningTable.numberOfExemptions}"
                                                   
                                                >
                                                ${earnings.info.numExceptions}
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                    <div class="hr-black"></div>
                                    <div class="earning-body">

                                    
                                        <div class="clearfix">
                                            <div class="total-table  pull-left">
                                                <h2 class="no-print table-top-title">
                                                    <b>${sessionScope.languageJSON.label.earningAndDeductions}</b>
                                                </h2>
                                                <table
                                                    class="table border-table no-thead print-table earning-table"
                                                >
                                                    <thead>
                                                        <tr>
                                                            <th id="earningDeductionsTitle01">${sessionScope.languageJSON.earningTable.earningDeductions}</th>
                                                            <th id="thisPeriodTitle01" class="text-right earning-table-thisPeriod">${sessionScope.languageJSON.earningTable.thisPeriod}</th>
                                                            <th id="calendarYTDTitle01" class="text-right earning-table-calendarYTD" class="print-td">
                                                                <span>${sessionScope.languageJSON.earningTable.calendarYTD}</span> ${year}
                                                            </th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                    <c:if test="${not empty earnings}">
                                                        <tr>
                                                            <td id="standardGross"><span>${sessionScope.languageJSON.earningTable.standardGross}</span></td>
                                                            <td headers="thisPeriodTitle01 standardGross" scope="${sessionScope.languageJSON.earningTable.thisPeriod}">
                                                                    <fmt:formatNumber value="${earnings.deductions.standardGross}" pattern="#,##0.00"/>
                                                            </td>
                                                            <td headers="calendarYTDTitle01 standardGross" class="print-td" scope="${sessionScope.languageJSON.earningTable.calendarYTD}">
                                                                    <fmt:formatNumber value="${YTDEarnings.deductions.standardGross}" pattern="#,##0.00"/>
                                                            </td>
                                                        </tr>
                                                        <tr class="print-tr">
                                                            <td id="supplementalPay"><span>${sessionScope.languageJSON.earningTable.supplementalPay}</span></td>
                                                            <td headers="thisPeriodTitle01 supplementalPay" scope="${sessionScope.languageJSON.earningTable.thisPeriod}">
                                                                    <fmt:formatNumber value="${earnings.deductions.supplementalPay}" pattern="#,##0.00"/>
                                                            </td>
                                                            <td headers="calendarYTDTitle01 supplementalPay" scope="${sessionScope.languageJSON.earningTable.calendarYTD}">
                                                                <fmt:formatNumber value="${YTDEarnings.deductions.supplementalPay}" pattern="#,##0.00"/>
                                                            </td>
                                                        </tr>
                                                        <tr class="print-tr">
                                                            <td id="overtimePay"><span>${sessionScope.languageJSON.earningTable.overtimePay}</span></td>
                                                            <td headers="thisPeriodTitle01 overtimePay" scope="${sessionScope.languageJSON.earningTable.thisPeriod}">
                                                                    <fmt:formatNumber value="${earnings.deductions.overtimePay}" pattern="#,##0.00"/>
                                                            </td>
                                                            <td headers="calendarYTDTitle01 overtimePay" scope="${sessionScope.languageJSON.earningTable.calendarYTD}">
                                                                <fmt:formatNumber value="${YTDEarnings.deductions.overtimePay}" pattern="#,##0.00"/>
                                                            </td>
                                                        </tr>
                                                        <tr class="print-tr">
                                                            <td id="absenceRefund"><span>${sessionScope.languageJSON.earningTable.absenceRefund}</span></td>
                                                            <td headers="thisPeriodTitle01 absenceRefund" scope="${sessionScope.languageJSON.earningTable.thisPeriod}">
                                                                    <fmt:formatNumber value="${earnings.deductions.absenceRefund}" pattern="#,##0.00"/>
                                                            </td>
                                                            <td headers="calendarYTDTitle01 absenceRefund" scope="${sessionScope.languageJSON.earningTable.calendarYTD}">
                                                                <fmt:formatNumber value="${YTDEarnings.deductions.absenceRefund}" pattern="#,##0.00"/>
                                                            </td>
                                                        </tr>
                                                        <tr class="print-tr">
                                                            <td id="taxedFringeBenefits"><span>${sessionScope.languageJSON.earningTable.taxedFringeBenefits}</span></td>
                                                            <td headers="thisPeriodTitle01 taxedFringeBenefits" scope="${sessionScope.languageJSON.earningTable.thisPeriod}">
                                                                    <fmt:formatNumber value="${earnings.deductions.taxedFringe}" pattern="#,##0.00"/>
                                                            </td>
                                                            <td headers="calendarYTDTitle01 taxedFringeBenefits" scope="${sessionScope.languageJSON.earningTable.calendarYTD}">
                                                                <fmt:formatNumber value="${YTDEarnings.deductions.taxedFringe}" pattern="#,##0.00"/>
                                                            </td>
                                                        </tr>
                                                        <tr class="print-tr">
                                                            <td id="earnedIncomeCredit"><span>${sessionScope.languageJSON.earningTable.earnedIncomeCredit}</span></td>
                                                            <td headers="thisPeriodTitle01 earnedIncomeCredit" scope="${sessionScope.languageJSON.earningTable.thisPeriod}">
                                                                    <fmt:formatNumber value="${earnings.deductions.earnedIncomeCred}" pattern="#,##0.00"/>
                                                            </td>
                                                            <td headers="calendarYTDTitle01 earnedIncomeCredit" scope="${sessionScope.languageJSON.earningTable.calendarYTD}">
                                                                <fmt:formatNumber value="${YTDEarnings.deductions.earnedIncomeCred}" pattern="#,##0.00"/>
                                                            </td>
                                                        </tr>
                                                        <tr class="print-tr">
                                                            <td id="nonTRSTax"><span>${sessionScope.languageJSON.earningTable.nonTRSTax}</span></td>
                                                            <td headers="thisPeriodTitle01 nonTRSTax" scope="${sessionScope.languageJSON.earningTable.thisPeriod}">
                                                                    <fmt:formatNumber value="${earnings.deductions.nonTrsTax}" pattern="#,##0.00"/>
                                                            </td>
                                                            <td headers="calendarYTDTitle01 nonTRSTax" scope="${sessionScope.languageJSON.earningTable.calendarYTD}">
                                                                <fmt:formatNumber value="${YTDEarnings.deductions.nonTrsTax}" pattern="#,##0.00"/>
                                                            </td>
                                                        </tr>
                                                        <tr class="print-tr">
                                                            <td id="nonTRSNonTax"><span>${sessionScope.languageJSON.earningTable.nonTRSNonTax}</span></td>
                                                            <td headers="thisPeriodTitle01 nonTRSNonTax" scope="${sessionScope.languageJSON.earningTable.thisPeriod}">
                                                                    <fmt:formatNumber value="${earnings.deductions.nonTrsNonTax}" pattern="#,##0.00"/>
                                                            </td>
                                                            <td headers="calendarYTDTitle01 nonTRSNonTax" scope="${sessionScope.languageJSON.earningTable.calendarYTD}">
                                                                <fmt:formatNumber value="${YTDEarnings.deductions.nonTrsNonTax}" pattern="#,##0.00"/>
                                                            </td>
                                                        </tr>
                                                        <tr class="print-tr">
                                                            <td id="TRSSupp"><span>${sessionScope.languageJSON.earningTable.TRSSupp}</span></td>
                                                            <td headers="thisPeriodTitle01 TRSSupp" scope="${sessionScope.languageJSON.earningTable.thisPeriod}">
                                                                    <fmt:formatNumber value="${earnings.deductions.trsSupplemental}" pattern="#,##0.00"/>
                                                            </td>
                                                            <td headers="calendarYTDTitle01 TRSSupp" scope="${sessionScope.languageJSON.earningTable.calendarYTD}">
                                                                <fmt:formatNumber value="${YTDEarnings.deductions.trsSupplemental}" pattern="#,##0.00"/>
                                                            </td>
                                                        </tr>
                                                        <tr class="total-tr totalEarningTr">
                                                            <td id="totalEarnings">--- <span>${sessionScope.languageJSON.earningTable.totalEarnings}</span></td>
                                                            <td headers="thisPeriodTitle01 totalEarnings" scope="${sessionScope.languageJSON.earningTable.thisPeriod}">
                                                                    <fmt:formatNumber value="${earnings.deductions.totalEarnings}" pattern="#,##0.00"/>
                                                            </td>
                                                            <td headers="calendarYTDTitle01 totalEarnings" class="print-td" scope="${sessionScope.languageJSON.earningTable.calendarYTD}">
                                                                <fmt:formatNumber value="${YTDEarnings.deductions.totalEarnings}" pattern="#,##0.00"/>
                                                            </td>
                                                        </tr>
                                                        <tr class="print-tr">
                                                            <td id="absenceDeductions"><span>${sessionScope.languageJSON.earningTable.absenceDeductions}</span></td>
                                                            <td headers="thisPeriodTitle01 absenceDeductions" scope="${sessionScope.languageJSON.earningTable.thisPeriod}">
                                                                    <fmt:formatNumber value="${earnings.deductions.absenceDed}" pattern="#,##0.00"/>
                                                            </td>
                                                            <td headers="calendarYTDTitle01 absenceDeductions" scope="${sessionScope.languageJSON.earningTable.calendarYTD}">
                                                                <fmt:formatNumber value="${YTDEarnings.deductions.absenceDed}" pattern="#,##0.00"/>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td id="withTax"><span>${sessionScope.languageJSON.earningTable.withTax}</span></td>
                                                            <td headers="thisPeriodTitle01 withTax" scope="${sessionScope.languageJSON.earningTable.thisPeriod}">
                                                                    <fmt:formatNumber value="${earnings.deductions.withholdingTax}" pattern="#,##0.00"/>
                                                            </td>
                                                            <td headers="calendarYTDTitle01 withTax" class="print-td" scope="${sessionScope.languageJSON.earningTable.calendarYTD}">
                                                                <fmt:formatNumber value="${YTDEarnings.deductions.withholdingTax}" pattern="#,##0.00"/>
                                                            </td>
                                                        </tr>
                                                        <tr class="print-tr">
                                                            <td id="FICATax"><span>${sessionScope.languageJSON.earningTable.FICATax}</span></td>
                                                            <td headers="thisPeriodTitle01 FICATax" scope="${sessionScope.languageJSON.earningTable.thisPeriod}">
                                                                    <fmt:formatNumber value="${earnings.deductions.ficaTax}" pattern="#,##0.00"/>
                                                            </td>
                                                            <td headers="calendarYTDTitle01 FICATax" scope="${sessionScope.languageJSON.earningTable.calendarYTD}">
                                                                <fmt:formatNumber value="${YTDEarnings.deductions.ficaTax}" pattern="#,##0.00"/>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td id="medicareTax"><span>${sessionScope.languageJSON.earningTable.medicareTax}</span></td>
                                                            <td headers="thisPeriodTitle01 medicareTax" scope="${sessionScope.languageJSON.earningTable.thisPeriod}">
                                                                    <fmt:formatNumber value="${earnings.deductions.medicareTax}" pattern="#,##0.00"/>
                                                            </td>
                                                            <td headers="calendarYTDTitle01 medicareTax" class="print-td" scope="${sessionScope.languageJSON.earningTable.calendarYTD}">
                                                                <fmt:formatNumber value="${YTDEarnings.deductions.medicareTax}" pattern="#,##0.00"/>
                                                            </td>
                                                        </tr>
                
                                                        <tr>
                                                            <td id="TRSSalaryRed"><span>${sessionScope.languageJSON.earningTable.TRSSalaryRed}</span></td>
                                                            <td headers="thisPeriodTitle01 TRSSalaryRed" scope="${sessionScope.languageJSON.earningTable.thisPeriod}">
                                                                    <fmt:formatNumber value="${earnings.deductions.trsSalaryRed}" pattern="#,##0.00"/>
                                                            </td>
                                                            <td headers="calendarYTDTitle01 TRSSalaryRed" class="print-td" scope="${sessionScope.languageJSON.earningTable.calendarYTD}">
                                                                <fmt:formatNumber value="${YTDEarnings.deductions.trsSalaryRed}" pattern="#,##0.00"/>
                                                            </td>
                                                        </tr>
                
                                                        <tr>
                                                            <td id="TRSInsurance"><span>${sessionScope.languageJSON.earningTable.TRSInsurance}</span></td>
                                                            <td headers="thisPeriodTitle01 TRSInsurance" scope="${sessionScope.languageJSON.earningTable.thisPeriod}">
                                                                    <fmt:formatNumber value="${earnings.deductions.trsInsurance}" pattern="#,##0.00"/>
                                                            </td>
                                                            <td headers="calendarYTDTitle01 TRSInsurance" class="print-td" scope="${sessionScope.languageJSON.earningTable.calendarYTD}">
                                                                <fmt:formatNumber value="${YTDEarnings.deductions.trsInsurance}" pattern="#,##0.00"/>
                                                            </td>
                                                        </tr>
                
                                                        <tr class="totalDeductionsTr">
                                                            <td id="totalOtherDeductions"><span>${sessionScope.languageJSON.earningTable.totalOtherDeductions}</span></td>
                                                            <td headers="thisPeriodTitle01 totalOtherDeductions" scope="${sessionScope.languageJSON.earningTable.thisPeriod}">
                                                                    <fmt:formatNumber value="${earnings.deductions.totOtherDed}" pattern="#,##0.00"/>
                                                            </td>
                                                            <td headers="calendarYTDTitle01 totalOtherDeductions" class="print-td" scope="${sessionScope.languageJSON.earningTable.calendarYTD}">
                                                                <fmt:formatNumber value="${YTDEarnings.deductions.totOtherDed}" pattern="#,##0.00"/>
                                                            </td>
                                                        </tr>
                
                                                        <tr class="total-tr">
                                                            <td id="totalDeductions">--- <span>${sessionScope.languageJSON.earningTable.totalDeductions}</span></td>
                                                            <td headers="thisPeriodTitle01 totalDeductions" scope="${sessionScope.languageJSON.earningTable.thisPeriod}">
                                                                    <fmt:formatNumber value="${earnings.deductions.totDed}" pattern="#,##0.00"/>
                                                            </td>
                                                            <td headers="calendarYTDTitle01 totalDeductions" class="print-td" scope="${sessionScope.languageJSON.earningTable.calendarYTD}">
                                                                <fmt:formatNumber value="${YTDEarnings.deductions.totDed}" pattern="#,##0.00"/>
                                                            </td>
                                                        </tr>
                                                        <tr class="total-tr">
                                                            <td id="netPay">--- <span>${sessionScope.languageJSON.earningTable.netPay}</span></td>
                                                            <td headers="thisPeriodTitle01 netPay" scope="${sessionScope.languageJSON.earningTable.thisPeriod}">
                                                                    <fmt:formatNumber value="${earnings.deductions.netPay}" pattern="#,##0.00"/>
                                                            </td>
                                                            <td headers="calendarYTDTitle01 netPay" class="print-td" scope="${sessionScope.languageJSON.earningTable.calendarYTD}">
                                                                <fmt:formatNumber value="${YTDEarnings.deductions.netPay}" pattern="#,##0.00"/>
                                                            </td>
                                                        </tr>
                                                        <tr class="print-tr">
                                                            <td id="nonTRSnonPayTaxable">
                                                                    <span>${sessionScope.languageJSON.earningTable.nonTRSnonPayTaxable}</span></td>
                                                            <td headers="thisPeriodTitle01 nonTRSnonPayTaxable" scope="${sessionScope.languageJSON.earningTable.thisPeriod}">
                                                                    <fmt:formatNumber value="${earnings.deductions.nonTrsNonPayTax}" pattern="#,##0.00"/>
                                                            </td>
                                                            <td headers="calendarYTDTitle01 nonTRSnonPayTaxable" scope="${sessionScope.languageJSON.earningTable.calendarYTD}">
                                                                <fmt:formatNumber value="${YTDEarnings.deductions.nonTrsNonPayTax}" pattern="#,##0.00"/>
                                                            </td>
                                                        </tr>
                                                        <tr class="print-tr">
                                                            <td id="nonTRSnonPayNonTaxable">
                                                                    <span>${sessionScope.languageJSON.earningTable.nonTRSnonPayNonTaxable}</span>
                                                            </td>
                                                            <td headers="thisPeriodTitle01 nonTRSnonPayNonTaxable" scope="${sessionScope.languageJSON.earningTable.thisPeriod}">
                                                                    <fmt:formatNumber value="${earnings.deductions.nonTrsNonPayNonTax}" pattern="#,##0.00"/>
                                                            </td>
                                                            <td headers="calendarYTDTitle01 nonTRSnonPayNonTaxable" scope="${sessionScope.languageJSON.earningTable.calendarYTD}">
                                                                <fmt:formatNumber value="${YTDEarnings.deductions.nonTrsNonPayNonTax}" pattern="#,##0.00"/>
                                                            </td>
                                                        </tr>
                                                        <tr class="print-tr bold-tr">
                                                            <td id="taxableWages"><span>${sessionScope.languageJSON.earningTable.taxableWages}</span></td>
                                                            <td headers="thisPeriodTitle01 taxableWages" scope="${sessionScope.languageJSON.earningTable.thisPeriod}">
                                                                <fmt:formatNumber value="${earnings.deductions.taxableWage}" pattern="#,##0.00"/>
                                                            </td>
                                                            <td headers="calendarYTDTitle01 taxableWages" scope="${sessionScope.languageJSON.earningTable.calendarYTD}">
                                                                <fmt:formatNumber value="${YTDEarnings.deductions.taxableWage}" pattern="#,##0.00"/>
                                                            </td>
                                                        </tr>
                                                        <tr class="print-tr">
                                                            <td id="FICAGross"><span>${sessionScope.languageJSON.earningTable.FICAGross}</span></td>
                                                            <td headers="thisPeriodTitle01 FICAGross" scope="${sessionScope.languageJSON.earningTable.thisPeriod}">
                                                                <fmt:formatNumber value="${earnings.deductions.ficaWage}" pattern="#,##0.00"/>
                                                            </td>
                                                            <td headers="calendarYTDTitle01 FICAGross" scope="${sessionScope.languageJSON.earningTable.calendarYTD}">
                                                                <fmt:formatNumber value="${YTDEarnings.deductions.ficaWage}" pattern="#,##0.00"/>
                                                            </td>
                                                        </tr>
                                                        <tr class="print-tr">
                                                            <td id="medicareGross"><span>${sessionScope.languageJSON.earningTable.medicareGross}</span></td>
                                                            <td headers="thisPeriodTitle01 medicareGross" scope="${sessionScope.languageJSON.earningTable.thisPeriod}">
                                                                <fmt:formatNumber value="${earnings.deductions.medGross}" pattern="#,##0.00"/>
                                                            </td>
                                                            <td headers="calendarYTDTitle01 medicareGross" scope="${sessionScope.languageJSON.earningTable.calendarYTD}">
                                                                <fmt:formatNumber value="${YTDEarnings.deductions.medGross}" pattern="#,##0.00"/>
                                                            </td>
                                                        </tr>
                                                        <tr class="print-tr">
                                                            <td id="emplrSponsoredHealth">
                                                                <span>${sessionScope.languageJSON.earningTable.emplrSponsoredHealth}</span>
                                                            </td>
                                                            <td headers="thisPeriodTitle01 emplrSponsoredHealth" scope="${sessionScope.languageJSON.earningTable.thisPeriod}"></td>
                                                            <td headers="calendarYTDTitle01 emplrSponsoredHealth" scope="${sessionScope.languageJSON.earningTable.calendarYTD}">
                                                                <fmt:formatNumber value="${YTDEarnings.emplrPrvdHlthcare}" pattern="#,##0.00"/>
                                                            </td>
                                                        </tr>
                                                        </c:if>
                                                    </tbody>
                                                </table>
                                            </div>
                                            <div class="tips-table pull-left">
                                                <div class="earning-detail">
                                                    <table class="table border-table">
                                                        <thead>
                                                            <tr>
                                                                <th id="jobDescriptionTitle01" class="earning-detail-titleTd">${sessionScope.languageJSON.earningTable.jobDescription}</th>
                                                                <th id="units01" class="earning-detail-units text-right">${sessionScope.languageJSON.earningTable.units}</th>
                                                                <th id="payRate01" class="earning-detail-payRate text-right">${sessionScope.languageJSON.earningTable.payRate}</th>
                                                                <th id="thisPeriod01" class="earning-detail-thisPeriod text-right">${sessionScope.languageJSON.earningTable.thisPeriod}</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                            <c:forEach items="${earnings.job}" var="job" varStatus="counter">
                                                                <tr>
                                                                    <td id="jobDescription01_${counter.index}" scope="${sessionScope.languageJSON.earningTable.jobDescription}">
                                                                            ${job.code} - ${job.description}
                                                                    </td>
                                                                    <td headers="units01 jobDescription01_${counter.index}" scope="${sessionScope.languageJSON.earningTable.units}">
                                                                            <fmt:formatNumber value="${job.units}" pattern="#,##0.00"/>
                                                                    </td>
                                                                    <td headers="payRate01 jobDescription01_${counter.index}" scope="${sessionScope.languageJSON.earningTable.payRate}">
                                                                            <fmt:formatNumber value="${job.payRate}" pattern="#,##0.00"/>
                                                                    </td>
                                                                    <td headers="thisPeriod01 jobDescription01_${counter.index}" scope="${sessionScope.languageJSON.earningTable.thisPeriod}">
                                                                            <fmt:formatNumber value="${job.amt}" pattern="#,##0.00"/>
                                                                    </td>
                                                                </tr>
                                                            </c:forEach>
                                                            <tr class="total-tr">
                                                                <td  id="totalStandardGross" colspan="3" scope="${sessionScope.languageJSON.earningTable.totalStandardGross}">
                                                                    <b>${sessionScope.languageJSON.earningTable.totalStandardGross}</b>
                                                                </td>
                                                                <td headers="thisPeriod01 totalStandardGross" scope="${sessionScope.languageJSON.earningTable.thisPeriod}">
                                                                    <b>
                                                                        <fmt:formatNumber value="${earnings.earningsJobTotal}" pattern="#,##0.00"/>
                                                                    </b>
                                                                </td>
                                                            </tr>
                                                        </tbody>
                                                    </table>
                                                    <table class="table border-table no-print">
                                                        <thead>
                                                            <tr>
                                                                <th id="jobDescriptionTitle02" class="earning-detail-titleTd">${sessionScope.languageJSON.earningTable.jobDescription}</th>
                                                                <th id="units02" class="text-right earning-detail-units">${sessionScope.languageJSON.earningTable.units}</th>
                                                                <th id="payRate02" class="text-right earning-detail-payRate">${sessionScope.languageJSON.earningTable.payRate}</th>
                                                                <th id="thisPeriod02" class="text-right earning-detail-thisPeriod">${sessionScope.languageJSON.earningTable.thisPeriod}</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                                <c:forEach items="${earnings.overtime}" var="overtime" varStatus="counter">
                                                                    <tr>
                                                                            <td id="jobDescription02_${counter.index}" scope="${sessionScope.languageJSON.earningTable.jobDescription}">
                                                                                    ${overtime.jobCd} - ${overtime.description}
                                                                            </td> 
                                                                            <td headers="units02 jobDescription02_${counter.index}" scope="${sessionScope.languageJSON.earningTable.units}">
                                                                                    <fmt:formatNumber value="${overtime.overtimeUnits}" pattern="#,##0.00"/>
                                                                            </td> 
                                                                            <td headers="payRate02 jobDescription02_${counter.index}" scope="${sessionScope.languageJSON.earningTable.payRate}">
                                                                                    <fmt:formatNumber value="${overtime.overtimeRate}" pattern="#,##0.00"/>
                                                                            </td> 
                                                                            <td headers="thisPeriod02 jobDescription02_${counter.index}" scope="${sessionScope.languageJSON.earningTable.thisPeriod}">
                                                                                    <fmt:formatNumber value="${overtime.thisPeriod}" pattern="#,##0.00"/>
                                                                            </td>
                                                                    </tr>
                                                                </c:forEach>
                                                            <tr class="total-tr">
                                                                <td id="totalOvertimePay" colspan="3" scope="${sessionScope.languageJSON.earningTable.totalOvertimePay}">
                                                                    <span>${sessionScope.languageJSON.earningTable.totalOvertimePay}</span>
                                                                </td>
                                                                <td headers="thisPeriod02 totalOvertimePay" scope="${sessionScope.languageJSON.earningTable.thisPeriod}">
                                                                        <fmt:formatNumber value="${earnings.earningsOvertimeTotal}" pattern="#,##0.00"/>
                                                                </td>
                                                            </tr>
                                                        </tbody>
                                                    </table>
                                                    <table class="table border-table no-print">
                                                        <thead>
                                                            <tr>
                                                                <th id="supType">${sessionScope.languageJSON.earningTable.supType}</th>
                                                                <th id="thisPeriod03" class="text-right earning-detail-thisPeriod">${sessionScope.languageJSON.earningTable.thisPeriod}</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                                <c:forEach items="${earnings.supplemental}" var="supplemental" varStatus="counter">
                                                                    <tr>
                                                                            <td id="description03_${counter.index}" scope="${sessionScope.languageJSON.earningTable.supType}">
                                                                                <c:if test="${supplemental.description != 'ZZZ - default'}"> 
                                                                                        ${supplemental.description}
                                                                                </c:if>
                                                                                <c:if test="${supplemental.description == 'ZZZ - default'}"> 
                                                                                    <span>${sessionScope.languageJSON.label.otherSupplemental}</span>
                                                                                </c:if>
                                                                            </td> 
                                                                            <td headers="thisPeriod03 description03_${counter.index}" scope="${sessionScope.languageJSON.earningTable.thisPeriod}">
                                                                                    <fmt:formatNumber value="${supplemental.amt}" pattern="#,##0.00"/>
                                                                            </td> 
                                                                    </tr>
                                                                </c:forEach>
                                                            <tr class="total-tr">
                                                                <td id="totalSupPay" scope="${sessionScope.languageJSON.earningTable.totalSupPay}">
                                                                    <span>${sessionScope.languageJSON.earningTable.totalSupPay}</span>
                                                                </td>
                                                                <td headers="thisPeriod03 totalSupPay" scope="${sessionScope.languageJSON.earningTable.thisPeriod}">
                                                                        <fmt:formatNumber value="${earnings.earningsSupplementalTotal}" pattern="#,##0.00"/>
                                                                </td>
                                                            </tr>
                                                        </tbody>
                                                    </table>
                                                    <table class="table border-table no-print">
                                                        <thead>
                                                            <tr>
                                                                <th id="nonTRSTaxType" >${sessionScope.languageJSON.earningTable.nonTRSTaxType}</th>
                                                                <th id="thisPeriod04" class="text-right earning-detail-thisPeriod">${sessionScope.languageJSON.earningTable.thisPeriod}</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                                <c:forEach items="${earnings.nonTrsTax}" var="nontrstax" varStatus="counter">
                                                                    <tr>
                                                                            <td id="nonTRSTaxType_${counter.index}" scope="${sessionScope.languageJSON.earningTable.nonTRSTaxType}">
                                                                                    <c:if test="${nontrstax.description != 'ZZZ - default'}"> ${nontrstax.description}</c:if>
                                                                                    <c:if test="${nontrstax.description == 'ZZZ - default'}"> 
                                                                                        <span>${sessionScope.languageJSON.label.otherNonTRSTax}</span>
                                                                                    </c:if>
                                                                            </td> 
                                                                            <td headers="thisPeriod04 nonTRSTaxType_${counter.index}" scope="${sessionScope.languageJSON.earningTable.thisPeriod}">
                                                                                    <fmt:formatNumber value="${nontrstax.amt}" pattern="#,##0.00"/>
                                                                            </td> 
                                                                    </tr>
                                                                </c:forEach>
                                                            <tr class="total-tr">
                                                                <td id="totalNonTRSTax" scope="${sessionScope.languageJSON.earningTable.totalNonTRSTax}">
                                                                    <span>${sessionScope.languageJSON.earningTable.totalNonTRSTax}</span>
                                                                </td>
                                                                <td headers="thisPeriod04 totalNonTRSTax" scope="${sessionScope.languageJSON.earningTable.thisPeriod}">
                                                                        <fmt:formatNumber value="${earnings.earningsNonTrsTaxTotal}" pattern="#,##0.00"/>
                                                                </td>
                                                            </tr>
                                                        </tbody>
                                                    </table>
                                                    <table class="table border-table no-print">
                                                        <thead>
                                                            <tr>
                                                                <th id="nonTRSNonTaxType">${sessionScope.languageJSON.earningTable.nonTRSNonTaxType}</th>
                                                                <th id="thisPeriod05" class="text-right earning-detail-thisPeriod">${sessionScope.languageJSON.earningTable.thisPeriod}</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                                <c:forEach items="${earnings.nonTrsNonTax}" var="nontax" varStatus="counter">
                                                                    <tr>
                                                                        <td id="nonTRSNonTaxType_${counter.index}" scope="${sessionScope.languageJSON.earningTable.nonTRSNonTaxType}">
                                                                                <c:if test="${nontax.description != 'ZZZ - default'}"> 
                                                                                        ${nontax.description}
                                                                                </c:if>
                                                                                <c:if test="${nontax.description == 'ZZZ - default'}"> 
                                                                                    <span>${sessionScope.languageJSON.label.otherNonTRSNonTax}</span>
                                                                                </c:if>
                                                                        </td> 
                                                                        <td headers="thisPeriod05 nonTRSNonTaxType_${counter.index}" scope="${sessionScope.languageJSON.earningTable.thisPeriod}">
                                                                                <fmt:formatNumber value="${nontax.amt}" pattern="#,##0.00"/>
                                                                        </td> 
                                                                    </tr>
                                                                </c:forEach>
                                                            <tr class="total-tr">
                                                                <td id="totalNonTRSNonTax" scope="${sessionScope.languageJSON.earningTable.totalNonTRSNonTax}">
                                                                    <span>${sessionScope.languageJSON.earningTable.totalNonTRSNonTax}</span>
                                                                </td>
                                                                <td headers="thisPeriod05 totalNonTRSNonTax" scope="${sessionScope.languageJSON.earningTable.thisPeriod}">
                                                                        <fmt:formatNumber value="${earnings.earningsNonTrsNonTaxTotal}" pattern="#,##0.00"/>
                                                                </td>
                                                            </tr>
                                                        </tbody>
                                                    </table>
                                                </div>
                                                <div class="deduction-table">
                                                    <table
                                                        class="table border-table earning-left-table"
                                                    >
                                                        <thead>
                                                            <tr>
                                                                <th id="otherDeductionsTitle">${sessionScope.languageJSON.earningTable.otherDeductions}</th>
                                                                <th id="cafeTitle">${sessionScope.languageJSON.earningTable.cafe}</th>
                                                                <th id="thisPeriod06" class="text-right sameWidth20">${sessionScope.languageJSON.earningTable.thisPeriod}</th>
                                                                <th id="employerContribution" class="text-right sameWidth20">${sessionScope.languageJSON.earningTable.employerContribution}</th>
                                                                <th id="calendarYTD" class="print-td text-right sameWidth20">
                                                                    <span>${sessionScope.languageJSON.earningTable.calendarYTD}</span> ${year}
                                                                </th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                            <c:forEach items="${earnings.other}" var="other" varStatus="counter">
                                                                <tr>
                                                                    <td id="otherDeductionsDesc_${counter.index}" scope="${sessionScope.languageJSON.earningTable.otherDeductions}">
                                                                            ${other.code} - ${other.description}
                                                                    </td>
                                                                    <td headers="cafeTitle otherDeductionsDesc_${counter.index}" class="text-center" scope="${sessionScope.languageJSON.earningTable.cafe}">
                                                                            ${other.cafe_flg}
                                                                    </td>
                                                                    <td headers="thisPeriod06 otherDeductionsDesc_${counter.index}" scope="${sessionScope.languageJSON.earningTable.thisPeriod}">
                                                                            <fmt:formatNumber value="${other.amt}" pattern="#,##0.00"/>
                                                                    </td>
                                                                    <td headers="employerContribution otherDeductionsDesc_${counter.index}" scope="${sessionScope.languageJSON.earningTable.employerContribution}">
                                                                            <fmt:formatNumber value="${other.contrib}" pattern="#,##0.00"/>
                                                                    </td>
                                                                    <td headers="calendarYTD otherDeductionsDesc_${counter.index}" class="print-td" scope="${sessionScope.languageJSON.earningTable.calendarYTD}">
                                                                            <fmt:formatNumber value="${other.tydAmt}" pattern="#,##0.00"/>
                                                                    </td>
                                                                </tr>
                                                            </c:forEach>
                                                            <c:if test="${fn:length(earnings.other) > 0}">   
                                                                <c:if test="${earnings.other[0].depCareMax == 1}">
                                                                    <tr>
                                                                        <td colspan="4" scope="${sessionScope.languageJSON.label.dependentCareTotal}">
                                                                            <span>${sessionScope.languageJSON.label.dependentCareTotalExceed}</span>
                                                                        </td>
                                                                    </tr>
                                                                </c:if>
                                                                <c:if test="${earnings.other[0].hsaCareMax == 1}">
                                                                    <tr>
                                                                        <td colspan="4" scope="${sessionScope.languageJSON.label.HSAEmployerTotal}">
                                                                            <span>${sessionScope.languageJSON.label.HSAEmployerTotalExceed}</span>
                                                                        </td>
                                                                    </tr>
                                                                </c:if>
                                                            </c:if>
                                                            <tr class="total-tr">
                                                                <td id="totalOtherDeductions" colspan="2" scope="${sessionScope.languageJSON.earningTable.totalOtherDeductions}">
                                                                    <b>${sessionScope.languageJSON.earningTable.totalOtherDeductions}</b>
                                                                </td>
                                                                <td headers="thisPeriod06 totalOtherDeductions" scope="${sessionScope.languageJSON.earningTable.thisPeriod}">
                                                                        <fmt:formatNumber value="${earnings.earningsOtherTotal}" pattern="#,##0.00"/>
                                                                </td>
                                                                <td headers="employerContribution totalOtherDeductions" scope="${sessionScope.languageJSON.earningTable.employerContribution}">
                                                                        <fmt:formatNumber value="${earnings.earningsOtherContribTotal}" pattern="#,##0.00"/>
                                                                </td>
                                                                <td headers="calendarYTD totalOtherDeductions" class="print-td" scope="${sessionScope.languageJSON.earningTable.calendarYTD}">
                                                                        <fmt:formatNumber value="${YTDEarnings.earningsOtherTydTotal}" pattern="#,##0.00"/>
                                                                </td>
                                                            </tr>
                                                        </tbody>
                                                    </table>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="print-block hr-black"></div>
                                        <table
                                            class="table responsive-table border-table print-table"
                                        >
                                            <thead>
                                                <tr>
                                                    <th>${sessionScope.languageJSON.earningTable.leaveType}</th>
                                                    <th class="text-right">${sessionScope.languageJSON.earningTable.unitsUsedThisPeriod}</th>
                                                    <th class="text-right">${sessionScope.languageJSON.earningTable.balance}</th>
                                                    <th class="print-td text-right">${sessionScope.languageJSON.earningTable.unitsUsedYearToDate}</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                    <c:if test="${fn:length(earnings.leave) > 0}">
                                                    <c:forEach items="${earnings.leave}" var="leave" varStatus="counter">
                                                    <tr>
                                                        <td class="text-left" data-title="${sessionScope.languageJSON.earningTable.leaveType}" scope="${sessionScope.languageJSON.earningTable.leaveType}">
                                                                ${leave.code} - ${leave.description}
                                                        </td>
                                                        <td class="text-right" data-title="${sessionScope.languageJSON.earningTable.unitsUsedThisPeriod}" scope="${sessionScope.languageJSON.earningTable.unitsUsedThisPeriod}">
                                                                <fmt:formatNumber value="${leave.unitsPrior}" pattern="#,##0.000"/>
                                                        </td>
                                                        <td class="text-right" data-title="${sessionScope.languageJSON.earningTable.balance}" scope="${sessionScope.languageJSON.earningTable.balance}">
                                                                <fmt:formatNumber value="${leave.balance}" pattern="#,##0.000"/>
                                                        </td>
                                                        <td
                                                            class="print-td text-right"
                                                            data-title="${sessionScope.languageJSON.earningTable.unitsUsedYearToDate}"
                                                           
                                                            scope="${sessionScope.languageJSON.earningTable.unitsUsedYearToDate}"
                                                        >
                                                            1.500
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                                </c:if>
                                                <c:if test="${fn:length(earnings.leave) == 0}">
                                                <tr>
                                                    <td colspan="4" class="text-center">
                                                        <span>${sessionScope.languageJSON.earningTable.noData}</span>
                                                    </td>
                                                </tr>
                                            </c:if>
                                            </tbody>
                                        </table>
                                        <div class="show-all-screen hr-black"></div>
                                        <table
                                            class="table border-table responsive-table payRoll-table print-table"
                                        >
                                            <thead>
                                                <tr>
                                                    <th>${sessionScope.languageJSON.currentPayTable.bankName}</th>
                                                    <th>${sessionScope.languageJSON.currentPayTable.accountType}</th>
                                                    <th>${sessionScope.languageJSON.currentPayTable.acctNbr}</th>
                                                    <th class="text-right">${sessionScope.languageJSON.currentPayTable.depAmt}</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                    <c:forEach items="${earnings.bank}" var="bank" varStatus="counter">
                                                <tr>
                                                    <td class="text-left" data-title="Bank Name"  scope="${sessionScope.languageJSON.currentPayTable.bankName}">
                                                            ${bank.name} (${bank.code})
                                                    </td>
                                                    <td class="text-left" data-title="Account Type" scope="${sessionScope.languageJSON.currentPayTable.accountType}">
                                                            ${bank.acctTypeCode} - ${bank.acctType}
                                                    </td>
                                                    <td class="text-left" data-title="Acct Nbr" scope="${sessionScope.languageJSON.currentPayTable.acctNbr}">
                                                            ${bank.acctNumLabel}
                                                    </td>
                                                    <td  class="text-right" data-title="Dep Amt" scope="${sessionScope.languageJSON.currentPayTable.depAmt}">
                                                            <fmt:formatNumber value="${bank.amt}" pattern="#,##0.00"/>
                                                    </td>
                                                </tr>
                                                <tr class="total-tr">
                                                    <td colspan="3" style="text-align:right">
                                                        <b><span>${sessionScope.languageJSON.earningTable.total}</span>:</b>
                                                    </td>
                                                    <td  class="text-right"><b>
                                                            <fmt:formatNumber value="${earnings.earningsBankTotal}" pattern="#,##0.00"/>
                                                    </b></td>
                                                </tr>
                                            </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </section>
            </main>
        </div>
        <%@ include file="../commons/footer.jsp"%>
    </body>
    <script src="/<%=request.getContextPath().split("/")[1]%>/js/viewJs/inquiry/earnings.js"></script>
</html>
