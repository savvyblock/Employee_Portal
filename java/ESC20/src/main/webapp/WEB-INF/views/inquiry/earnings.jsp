<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> <%@ taglib
uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> <%@ page
language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="en">
    <head>
        <title data-localize="headTitle.earnings"></title>
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <%@ include file="../commons/header.jsp"%>
    </head>
    <body class="hold-transition sidebar-mini">
        <div class="wrapper">
            <%@ include file="../commons/bar.jsp"%>

            <main class="content-wrapper" id="content">
                    <section class="content">
                            <h2 class="clearfix no-print section-title">
                                <span data-localize="title.earnings"></span>
                                <button
                                    class="btn btn-primary pull-right"
                                    onclick="doPrint()"
                                    data-localize="label.print"
                                >
                                </button>
                            </h2>
                            <div class="content-white EMP-detail earningPage">
                                <div class="print-block print-title">
                                    <div style="text-align:center;margin-bottom:10px;">
                                            ${sessionScope.district.name}<br />
                                            <span data-localize="title.earnings"></span>
                                        <div id="date-now"></div>
                                    </div>
                                </div>
                                <table class="print-block  employee-info-table">
                                    <tbody>
                                        <tr>
                                            <td>
                                                <div class="info-flex-item">
                                                    <div class="info-title">
                                                        <b><span data-localize="earningTable.empNbr"></span>:</b>
                                                    </div>
                                                    <div class="info-content">
                                                        ${sessionScope.userDetail.empNbr}
                                                    </div>
                                                </div>
                                            </td>
                                            <td>
                                                <div class="info-flex-item">
                                                    <div class="info-title">
                                                        <b><span data-localize="earningTable.frequency"></span>:</b>
                                                    </div>
                                                    <div class="info-content">
                                                        ${freq}
                                                    </div>
                                                </div>
                                            </td>
                                            <td>
                                                <div class="info-flex-item">
                                                    <div class="info-title">
                                                        <b><span data-localize="earningTable.primaryCampus"></span>:</b>
                                                    </div>
                                                    <div class="info-content">
                                                        ${earnings.info.campusId} ${earnings.info.campusName}
                                                    </div>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td colspan="2">
                                                <div class="info-flex-item">
                                                    <div class="info-title">
                                                        <b><span data-localize="earningTable.employeeName"></span>:</b>
                                                    </div>
                                                    <div class="info-content">
                                                        ${sessionScope.userDetail.nameF} ${sessionScope.userDetail.nameM} ${sessionScope.userDetail.nameL}
                                                    </div>
                                                </div>
                                            </td>
                                            <td>
                                                <div class="info-flex-item">
                                                    <div class="info-title">
                                                        <b><span data-localize="earningTable.payCampus"></span>:</b>
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
                                                        <b><span data-localize="earningTable.checkNbr"></span>:</b>
                                                    </div>
                                                    <div class="info-content">
                                                        ${earnings.info.checkNumber}
                                                    </div>
                                                </div>
                                            </td>
                                            <td>
                                                <div class="info-flex-item">
                                                    <div class="info-title">
                                                        <b><span data-localize="earningTable.withholdStat"></span>:</b>
                                                    </div>
                                                    <div class="info-content">
                                                        <c:if test="${earnings.info.withholdingStatus =='M'}">
                                                            <span data-localize="label.married"></span>
                                                        </c:if>
                                                        <c:if test="${earnings.info.withholdingStatus =='S'}">
                                                                <span data-localize="label.single"></span>
                                                        </c:if>
                                                    </div>
                                                </div>
                                            </td>
                                            <td>
                                                <div class="info-flex-item">
                                                    <div class="info-title">
                                                        <b><span data-localize="earningTable.exempt"></span>:</b>
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
                                                        <b><span data-localize="earningTable.payDate"></span>:</b>
                                                    </div>
                                                    <div class="info-content">
                                                        ${selectedPayDate.formatedDate}
                                                    </div>
                                                </div>
                                            </td>
                                            <td>
                                                <div class="info-flex-item">
                                                    <div class="info-title">
                                                        <b ><span data-localize="earningTable.periodBegin"></span>:</b>
                                                    </div>
                                                    <div class="info-content">
                                                        ${earnings.info.periodBeginningDate}
                                                    </div>
                                                </div>
                                            </td>
                                            <td>
                                                <div class="info-flex-item">
                                                    <div class="info-title">
                                                        <b><span data-localize="earningTable.periodEnd"></span>:</b>
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
                                        data-localize="label.noteEarning"
                                    >
                                        
                                    </p>
                                    <div class="print-block hr-black"></div>
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
                                    <div class="form-group in-line">
                                        <label class="form-title" for="SearchPayDate"><span data-localize="label.payDates"></span>:</label>
                                        <select class="form-control" name="payDateString" id="payDateString" onchange="submitEarning()" style="max-width:280px;">
                                            <c:forEach var="payDate" items="${payDates}" varStatus="counter">
                                                <option value="${payDate.dateFreqVoidAdjChk}" <c:if test="${payDate.dateFreq == selectedPayDate.dateFreq }">selected</c:if>>${payDate.label}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </form>
                                <table
                                    class="no-print no-thead table no-border-table responsive-table max-w-550 earning-title-table"
                                >
                                    <tbody>
                                        <tr>
                                            <td class="td-title"><span data-localize="earningTable.campus"></span>:</td>
                                            <td
                                                class="td-content"
                                                data-localize="earningTable.campus"
                                                colspan="3"
                                            >
                                            ${earnings.info.campusId} ${earnings.info.campusName}
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="td-title"><span data-localize="earningTable.checkNumber"></span>:</td>
                                            <td
                                                class="td-content"
                                                data-localize="earningTable.checkNumber"
                                            >
                                            ${earnings.info.checkNumber}
                                            </td>
                                            <td class="td-title">
                                                    <span data-localize="earningTable.periodEndingDate"></span>:
                                            </td>
                                            <td
                                                class="td-content"
                                                data-localize="earningTable.periodEndingDate"
                                            >
                                            ${earnings.info.periodEndingDate}
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="td-title">
                                                    <span data-localize="earningTable.withholdingStatus"></span>:
                                            </td>
                                            <td
                                                class="td-content"
                                                data-localize="earningTable.withholdingStatus"
                                            >
                                                <c:if test="${earnings.info.withholdingStatus =='M'}">
                                                    <span data-localize="label.married"></span>
                                                </c:if>
                                                <c:if test="${earnings.info.withholdingStatus =='S'}">
                                                        <span data-localize="label.single"></span>
                                                </c:if>
                                            </td>
                                            <td class="td-title">
                                                    <span data-localize="earningTable.numberOfExemptions"></span>:
                                            </td>
                                            <td
                                                class="td-content"
                                                data-localize="earningTable.numberOfExemptions"
                                            >
                                            ${earnings.info.numExceptions}
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                                <div class="hr-black"></div>
                                <div class="clearfix">
                                    <div class="total-table  pull-left">
                                        <p class="no-print table-top-title">
                                            <b data-localize="label.earningAndDeductions"></b>
                                        </p>
                                        <table
                                            class="table border-table no-thead print-table earning-table"
                                        >
                                            <thead>
                                                <tr>
                                                    <th data-localize="earningTable.earningDeductions"></th>
                                                    <th data-localize="earningTable.thisPeriod"></th>
                                                    <th class="print-td">
                                                        <span data-localize="earningTable.calendarYTD"></span> ${year}
                                                    </th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <tr>
                                                    <td><span data-localize="earningTable.standardGross"></span></td>
                                                    <td data-localize="earningTable.thisPeriod" data-localize-location="scope">
                                                            ${earnings.deductions.standardGross}
                                                    </td>
                                                    <td class="print-td" data-localize="earningTable.calendarYTD" data-localize-location="scope">
                                                    		${YTDEarnings.deductions.standardGross}
                                                    </td>
                                                </tr>
                                                <tr class="print-tr">
                                                    <td><span data-localize="earningTable.supplementalPay"></span></td>
                                                    <td data-localize="earningTable.thisPeriod" data-localize-location="scope">
                                                            ${earnings.deductions.supplementalPay}
                                                    </td>
                                                    <td data-localize="earningTable.calendarYTD" data-localize-location="scope">${YTDEarnings.deductions.supplementalPay}</td>
                                                </tr>
                                                <tr class="print-tr">
                                                    <td><span data-localize="earningTable.overtimePay"></span></td>
                                                    <td data-localize="earningTable.thisPeriod" data-localize-location="scope">
                                                            ${earnings.deductions.overtimePay}
                                                    </td>
                                                    <td data-localize="earningTable.calendarYTD" data-localize-location="scope">${YTDEarnings.deductions.overtimePay}</td>
                                                </tr>
                                                <tr class="print-tr">
                                                    <td><span data-localize="earningTable.absenceRefund"></span></td>
                                                    <td data-localize="earningTable.thisPeriod" data-localize-location="scope">
                                                            ${earnings.deductions.absenceRefund}
                                                    </td>
                                                    <td data-localize="earningTable.calendarYTD" data-localize-location="scope">${YTDEarnings.deductions.absenceRefund}</td>
                                                </tr>
                                                <tr class="print-tr">
                                                    <td><span data-localize="earningTable.taxedFringeBenefits"></span></td>
                                                    <td data-localize="earningTable.thisPeriod" data-localize-location="scope">
                                                            ${earnings.deductions.taxedFringe}
                                                    </td>
                                                    <td data-localize="earningTable.calendarYTD" data-localize-location="scope">${YTDEarnings.deductions.taxedFringe}</td>
                                                </tr>
                                                <tr class="print-tr">
                                                    <td><span data-localize="earningTable.earnedIncomeCredit"></span></td>
                                                    <td data-localize="earningTable.thisPeriod" data-localize-location="scope">
                                                            ${earnings.deductions.earnedIncomeCred}
                                                    </td>
                                                    <td data-localize="earningTable.calendarYTD" data-localize-location="scope">${YTDEarnings.deductions.earnedIncomeCred}</td>
                                                </tr>
                                                <tr class="print-tr">
                                                    <td><span data-localize="earningTable.nonTRSTax"></span></td>
                                                    <td data-localize="earningTable.thisPeriod" data-localize-location="scope">
                                                            ${earnings.deductions.nonTrsTax}
                                                    </td>
                                                    <td data-localize="earningTable.calendarYTD" data-localize-location="scope">${YTDEarnings.deductions.nonTrsTax}</td>
                                                </tr>
                                                <tr class="print-tr">
                                                    <td><span data-localize="earningTable.nonTRSNonTax"></span></td>
                                                    <td data-localize="earningTable.thisPeriod" data-localize-location="scope">
                                                            ${earnings.deductions.nonTrsNonTax}
                                                    </td>
                                                    <td data-localize="earningTable.calendarYTD" data-localize-location="scope">${YTDEarnings.deductions.nonTrsNonTax}</td>
                                                </tr>
                                                <tr class="print-tr">
                                                    <td><span data-localize="earningTable.TRSSupp"></span></td>
                                                    <td data-localize="earningTable.thisPeriod" data-localize-location="scope">
                                                            ${earnings.deductions.trsSupplemental}
                                                    </td>
                                                    <td data-localize="earningTable.calendarYTD" data-localize-location="scope">${YTDEarnings.deductions.trsSupplemental}</td>
                                                </tr>
                                                <tr class="total-tr totalEarningTr">
                                                    <td>--- <span data-localize="earningTable.totalEarnings"></span></td>
                                                    <td data-localize="earningTable.thisPeriod" data-localize-location="scope">
                                                            ${earnings.deductions.totalEarnings}
                                                    </td>
                                                    <td class="print-td" data-localize="earningTable.calendarYTD" data-localize-location="scope">${YTDEarnings.deductions.totalEarnings}</td>
                                                </tr>
                                                <tr class="print-tr">
                                                    <td><span data-localize="earningTable.absenceDeductions"></span></td>
                                                    <td data-localize="earningTable.thisPeriod" data-localize-location="scope">
                                                            ${earnings.deductions.absenceDed}
                                                    </td>
                                                    <td data-localize="earningTable.calendarYTD" data-localize-location="scope">${YTDEarnings.deductions.absenceDed}</td>
                                                </tr>
                                                <tr>
                                                    <td><span data-localize="earningTable.withTax"></span></td>
                                                    <td data-localize="earningTable.thisPeriod" data-localize-location="scope">
                                                            ${earnings.deductions.withholdingTax}
                                                    </td>
                                                    <td class="print-td" data-localize="earningTable.calendarYTD" data-localize-location="scope">${YTDEarnings.deductions.withholdingTax}</td>
                                                </tr>
                                                <tr class="print-tr">
                                                    <td><span data-localize="earningTable.FICATax"></span></td>
                                                    <td data-localize="earningTable.thisPeriod" data-localize-location="scope">
                                                            ${earnings.deductions.ficaTax}
                                                    </td>
                                                    <td data-localize="earningTable.calendarYTD" data-localize-location="scope">${YTDEarnings.deductions.ficaTax}</td>
                                                </tr>
                                                <tr>
                                                    <td><span data-localize="earningTable.medicareTax"></span></td>
                                                    <td data-localize="earningTable.thisPeriod" data-localize-location="scope">
                                                            ${earnings.deductions.medicareTax}
                                                    </td>
                                                    <td class="print-td" data-localize="earningTable.calendarYTD" data-localize-location="scope">${YTDEarnings.deductions.medicareTax}</td>
                                                </tr>
        
                                                <tr>
                                                    <td><span data-localize="earningTable.TRSSalaryRed"></span></td>
                                                    <td data-localize="earningTable.thisPeriod" data-localize-location="scope">
                                                            ${earnings.deductions.trsSalaryRed}
                                                    </td>
                                                    <td class="print-td" data-localize="earningTable.calendarYTD" data-localize-location="scope">${YTDEarnings.deductions.trsSalaryRed}</td>
                                                </tr>
        
                                                <tr>
                                                    <td><span data-localize="earningTable.TRSInsurance"></span></td>
                                                    <td data-localize="earningTable.thisPeriod" data-localize-location="scope">
                                                            ${earnings.deductions.trsInsurance}
                                                    </td>
                                                    <td class="print-td" data-localize="earningTable.calendarYTD" data-localize-location="scope">${YTDEarnings.deductions.trsInsurance}</td>
                                                </tr>
        
                                                <tr class="totalDeductionsTr">
                                                    <td><span data-localize="earningTable.totalOtherDeductions"></span></td>
                                                    <td data-localize="earningTable.thisPeriod" data-localize-location="scope">
                                                            ${earnings.deductions.totOtherDed}
                                                    </td>
                                                    <td class="print-td" data-localize="earningTable.calendarYTD" data-localize-location="scope">${YTDEarnings.deductions.totOtherDed}</td>
                                                </tr>
        
                                                <tr class="total-tr">
                                                    <td>--- <span data-localize="earningTable.totalDeductions"></span></td>
                                                    <td data-localize="earningTable.thisPeriod" data-localize-location="scope">
                                                            ${earnings.deductions.totDed}
                                                    </td>
                                                    <td class="print-td" data-localize="earningTable.calendarYTD" data-localize-location="scope">${YTDEarnings.deductions.totDed}</td>
                                                </tr>
                                                <tr class="total-tr">
                                                    <td>--- <span data-localize="earningTable.netPay"></span></td>
                                                    <td data-localize="earningTable.thisPeriod" data-localize-location="scope">
                                                            ${earnings.deductions.netPay}
                                                    </td>
                                                    <td class="print-td" data-localize="earningTable.calendarYTD" data-localize-location="scope">${YTDEarnings.deductions.netPay}</td>
                                                </tr>
                                                <tr class="print-tr">
                                                    <td>
                                                            <span data-localize="earningTable.nonTRSnonPayTaxable"></span></td>
                                                    <td data-localize="earningTable.thisPeriod" data-localize-location="scope">
                                                            ${earnings.deductions.nonTrsNonPayTax}
                                                    </td>
                                                    <td data-localize="earningTable.calendarYTD" data-localize-location="scope">${YTDEarnings.deductions.nonTrsNonPayTax}</td>
                                                </tr>
                                                <tr class="print-tr">
                                                    <td>
                                                            <span data-localize="earningTable.nonTRSnonPayNonTaxable"></span>
                                                    </td>
                                                    <td data-localize="earningTable.thisPeriod" data-localize-location="scope">
                                                            ${earnings.deductions.nonTrsNonPayNonTax}
                                                    </td>
                                                    <td data-localize="earningTable.calendarYTD" data-localize-location="scope">${YTDEarnings.deductions.nonTrsNonPayNonTax}</td>
                                                </tr>
                                                <tr class="print-tr bold-tr">
                                                    <td><span data-localize="earningTable.taxableWages"></span></td>
                                                    <td data-localize="earningTable.thisPeriod" data-localize-location="scope">${earnings.deductions.taxableWage}</td>
                                                    <td data-localize="earningTable.calendarYTD" data-localize-location="scope">${YTDEarnings.deductions.taxableWage}</td>
                                                </tr>
                                                <tr class="print-tr">
                                                    <td><span data-localize="earningTable.FICAGross"></span></td>
                                                    <td data-localize="earningTable.thisPeriod" data-localize-location="scope">${earnings.deductions.ficaWage}</td>
                                                    <td data-localize="earningTable.calendarYTD" data-localize-location="scope">${YTDEarnings.deductions.ficaWage}</td>
                                                </tr>
                                                <tr class="print-tr">
                                                    <td><span data-localize="earningTable.medicareGross"></span></td>
                                                    <td data-localize="earningTable.thisPeriod" data-localize-location="scope">${earnings.deductions.medGross}</td>
                                                    <td data-localize="earningTable.calendarYTD" data-localize-location="scope">${YTDEarnings.deductions.medGross}</td>
                                                </tr>
                                                <tr class="print-tr">
                                                    <td>
                                                        <span data-localize="earningTable.emplrSponsoredHealth"></span>
                                                    </td>
                                                    <td data-localize="earningTable.thisPeriod" data-localize-location="scope"></td>
                                                    <td data-localize="earningTable.calendarYTD" data-localize-location="scope">${YTDEarnings.emplrPrvdHlthcare}</td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                    <div class="tips-table pull-left">
                                        <div class="earning-detail">
                                            <table class="table border-table">
                                                <thead>
                                                    <tr>
                                                        <th data-localize="earningTable.jobDescription"></th>
                                                        <th data-localize="earningTable.units"></th>
                                                        <th data-localize="earningTable.payRate"></th>
                                                        <th data-localize="earningTable.thisPeriod"></th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach items="${earnings.job}" var="job" varStatus="counter">
                                                        <tr>
                                                            <td data-localize="earningTable.jobDescription" data-localize-location="scope">
                                                                    ${job.code} - ${job.description}
                                                            </td>
                                                            <td data-localize="earningTable.units" data-localize-location="scope">
                                                                    ${job.units}
                                                            </td>
                                                            <td data-localize="earningTable.payRate" data-localize-location="scope">
                                                                    ${job.payRate}
                                                            </td>
                                                            <td data-localize="earningTable.thisPeriod" data-localize-location="scope">
                                                                    ${job.amt}
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                    <tr class="total-tr">
                                                        <td colspan="3" data-localize="earningTable.totalStandardGross" data-localize-location="scope">
                                                            <b data-localize="earningTable.totalStandardGross"></b>
                                                        </td>
                                                        <td data-localize="earningTable.thisPeriod" data-localize-location="scope">
                                                            <b>${earnings.earningsJobTotal}</b>
                                                        </td>
                                                    </tr>
                                                </tbody>
                                            </table>
                                            <table class="table border-table no-print">
                                                <thead>
                                                    <tr>
                                                        <th data-localize="earningTable.jobDescription">Job Description</th>
                                                        <th data-localize="earningTable.units">Units</th>
                                                        <th data-localize="earningTable.payRate">Pay Rate</th>
                                                        <th data-localize="earningTable.thisPeriod">This Period</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                        <c:forEach items="${earnings.overtime}" var="overtime" varStatus="counter">
                                                            <tr>
                                                                    <td data-localize="earningTable.jobDescription" data-localize-location="scope">
                                                                            ${overtime.jobCd} - ${overtime.description}
                                                                    </td> 
                                                                    <td data-localize="earningTable.units" data-localize-location="scope">
                                                                            ${overtime.overtimeUnits}
                                                                    </td> 
                                                                    <td data-localize="earningTable.payRate" data-localize-location="scope">
                                                                            ${overtime.overtimeRate}
                                                                    </td> 
                                                                    <td data-localize="earningTable.thisPeriod" data-localize-location="scope">
                                                                            ${overtime.thisPeriod}
                                                                    </td>
                                                            </tr>
                                                        </c:forEach>
                                                    <tr class="total-tr">
                                                        <td colspan="3" data-localize="earningTable.totalOvertimePay" data-localize-location="scope">
                                                            <span data-localize="earningTable.totalOvertimePay"></span>
                                                        </td>
                                                        <td data-localize="earningTable.thisPeriod" data-localize-location="scope">
                                                                ${earnings.earningsOvertimeTotal}
                                                        </td>
                                                    </tr>
                                                </tbody>
                                            </table>
                                            <table class="table border-table no-print">
                                                <thead>
                                                    <tr>
                                                        <th data-localize="earningTable.supType"></th>
                                                        <th data-localize="earningTable.thisPeriod"></th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                        <c:forEach items="${earnings.supplemental}" var="supplemental" varStatus="counter">
                                                            <tr>
                                                                    <td data-localize="earningTable.supType" data-localize-location="scope">
                                                                        <c:if test="${supplemental.description != 'ZZZ - default'}"> 
                                                                                ${supplemental.description}
                                                                        </c:if>
                                                                        <c:if test="${supplemental.description == 'ZZZ - default'}"> 
                                                                            <span data-localize="label.otherSupplemental"></span>
                                                                        </c:if>
                                                                    </td> 
                                                                    <td data-localize="earningTable.thisPeriod" data-localize-location="scope">
                                                                            ${supplemental.amt}
                                                                    </td> 
                                                            </tr>
                                                        </c:forEach>
                                                    <tr class="total-tr">
                                                        <td data-localize="earningTable.totalSupPay" data-localize-location="scope">
                                                            <span data-localize="earningTable.totalSupPay"></span>
                                                        </td>
                                                        <td data-localize="earningTable.thisPeriod" data-localize-location="scope">
                                                                ${earnings.earningsSupplementalTotal}
                                                        </td>
                                                    </tr>
                                                </tbody>
                                            </table>
                                            <table class="table border-table no-print">
                                                <thead>
                                                    <tr>
                                                        <th data-localize="earningTable.nonTRSTaxType" >Non-TRS Taxable Type</th>
                                                        <th data-localize="earningTable.thisPeriod"></th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                        <c:forEach items="${earnings.nonTrsTax}" var="nontrstax" varStatus="counter">
                                                            <tr>
                                                                    <td data-localize="earningTable.nonTRSTaxType" data-localize-location="scope">
                                                                            <c:if test="${nontrstax.description != 'ZZZ - default'}"> ${nontrstax.description}</c:if>
                                                                            <c:if test="${nontrstax.description == 'ZZZ - default'}"> 
                                                                                <span data-localize="label.otherNonTRSTax"></span>
                                                                            </c:if>
                                                                    </td> 
                                                                    <td data-localize="earningTable.thisPeriod" data-localize-location="scope">
                                                                            ${nontrstax.amt}
                                                                    </td> 
                                                            </tr>
                                                        </c:forEach>
                                                    <tr class="total-tr">
                                                        <td data-localize="earningTable.totalNonTRSTax" data-localize-location="scope">
                                                            <span data-localize="earningTable.totalNonTRSTax"></span>
                                                        </td>
                                                        <td data-localize="earningTable.thisPeriod" data-localize-location="scope">
                                                                ${earnings.earningsNonTrsTaxTotal}
                                                        </td>
                                                    </tr>
                                                </tbody>
                                            </table>
                                            <table class="table border-table no-print">
                                                <thead>
                                                    <tr>
                                                        <th data-localize="earningTable.nonTRSNonTaxType"></th>
                                                        <th data-localize="earningTable.thisPeriod"></th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                        <c:forEach items="${earnings.nonTrsNonTax}" var="nontax" varStatus="counter">
                                                            <tr>
                                                                <td data-localize="earningTable.nonTRSNonTaxType" data-localize-location="scope">
                                                                        <c:if test="${nontax.description != 'ZZZ - default'}"> 
                                                                                ${nontax.description}
                                                                        </c:if>
                                                                        <c:if test="${nontax.description == 'ZZZ - default'}"> 
                                                                            <span data-localize="label.otherNonTRSNonTax"></span>
                                                                        </c:if>
                                                                </td> 
                                                                <td data-localize="earningTable.thisPeriod" data-localize-location="scope">
                                                                        ${nontax.amt}
                                                                </td> 
                                                            </tr>
                                                        </c:forEach>
                                                    <tr class="total-tr">
                                                        <td data-localize="earningTable.totalNonTRSNonTax" data-localize-location="scope">
                                                            <span data-localize="earningTable.totalNonTRSNonTax"></span>
                                                        </td>
                                                        <td data-localize="earningTable.thisPeriod" data-localize-location="scope">
                                                                ${earnings.earningsNonTrsNonTaxTotal}
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
                                                        <th data-localize="earningTable.otherDeductions"></th>
                                                        <th data-localize="earningTable.cafe"></th>
                                                        <th data-localize="earningTable.thisPeriod"></th>
                                                        <th data-localize="earningTable.employerContribution"></th>
                                                        <th class="print-td">
                                                            <span data-localize="earningTable.calendarYTD"></span> ${year}
                                                        </th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach items="${earnings.other}" var="other" varStatus="counter">
                                                        <tr>
                                                            <td data-localize="earningTable.otherDeductions" data-localize-location="scope">
                                                                    ${other.code} - ${other.description}
                                                            </td>
                                                            <td data-localize="earningTable.cafe" data-localize-location="scope">
                                                                    ${other.cafe_flg}
                                                            </td>
                                                            <td data-localize="earningTable.thisPeriod" data-localize-location="scope">
                                                                    ${other.amt}
                                                            </td>
                                                            <td data-localize="earningTable.employerContribution" data-localize-location="scope">
                                                                    ${other.contrib}
                                                            </td>
                                                            <td class="print-td" data-localize="earningTable.calendarYTD" data-localize-location="scope">
																	${other.tydAmt}
															</td>
                                                        </tr>
                                                    </c:forEach>
                                                    <c:if test="${fn:length(earnings.other) > 0}">   
                                                        <c:if test="${earnings.other[0].depCareMax == 1}">
                                                            <tr>
                                                                <td colspan="4" data-localize="label.dependentCareTotal" data-localize-location="scope">
                                                                    <span data-localize="label.dependentCareTotalExceed"></span>
                                                                </td>
                                                            </tr>
                                                        </c:if>
                                                        <c:if test="${earnings.other[0].hsaCareMax == 1}">
                                                            <tr>
                                                                <td colspan="4" data-localize="label.HSAEmployerTotal" data-localize-location="scope">
                                                                    <span data-localize="label.HSAEmployerTotalExceed"></span>
                                                                </td>
                                                            </tr>
                                                        </c:if>
                                                    </c:if>
                                                    <tr class="total-tr">
                                                        <td colspan="2" data-localize="earningTable.totalOtherDeductions" data-localize-location="scope">
                                                            <b data-localize="earningTable.totalOtherDeductions"></b>
                                                        </td>
                                                        <td data-localize="earningTable.thisPeriod" data-localize-location="scope">
                                                                ${earnings.earningsOtherTotal}
                                                        </td>
                                                        <td data-localize="earningTable.employerContribution" data-localize-location="scope">
                                                                ${earnings.earningsOtherContribTotal}
                                                        </td>
                                                        <td class="print-td" data-localize="earningTable.calendarYTD" data-localize-location="scope">
                                                                ${YTDEarnings.earningsOtherTydTotal}
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
                                            <th data-localize="earningTable.leaveType"></th>
                                            <th data-localize="earningTable.unitsUsedThisPeriod"></th>
                                            <th data-localize="earningTable.balance"></th>
                                            <th class="print-td" data-localize="earningTable.unitsUsedYearToDate"></th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                            <c:if test="${fn:length(earnings.leave) > 0}">
                                            <c:forEach items="${earnings.leave}" var="leave" varStatus="counter">
                                            <tr>
                                                <td data-title="Leave Type" data-localize="earningTable.leaveType" data-localize-location="scope">
                                                        ${leave.code} - ${leave.description}
                                                </td>
                                                <td data-title="Units Used This Period" data-localize="earningTable.unitsUsedThisPeriod" data-localize-location="scope">
                                                        ${leave.unitsPrior}
                                                </td>
                                                <td data-title="Balance" data-localize="earningTable.balance" data-localize-location="scope">
                                                        ${leave.balance}
                                                </td>
                                                <td
                                                    class="print-td"
                                                    data-localize="earningTable.unitsUsedYearToDate"
                                                    data-localize-location="scope"
                                                >
                                                    1.500
                                                </td>
                                            </tr>
                                        </c:forEach>
                                        </c:if>
                                        <c:if test="${fn:length(earnings.leave) == 0}">
                                        <tr>
                                            <td colspan="4">
                                                <span data-localize="earningTable.noData"></span>
                                            </td>
                                        </tr>
                                    </c:if>
                                    </tbody>
                                </table>
                                <div class="show-all-screen hr-black"></div>
                                <table
                                    class="table responsive-table payRoll-table print-table"
                                >
                                    <thead>
                                        <tr>
                                            <th data-localize="currentPayTable.bankName"></th>
                                            <th data-localize="currentPayTable.accountType"></th>
                                            <th data-localize="currentPayTable.acctNbr"></th>
                                            <th data-localize="currentPayTable.depAmt"></th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                            <c:forEach items="${earnings.bank}" var="bank" varStatus="counter">
                                        <tr>
                                            <td data-title="Bank Name"  data-localize="currentPayTable.bankName" data-localize-location="scope">
                                                    ${bank.name} (${bank.code})
                                            </td>
                                            <td data-title="Account Type" data-localize="currentPayTable.accountType" data-localize-location="scope">
                                                    ${bank.acctTypeCode} - ${bank.acctType}
                                            </td>
                                            <td data-title="Acct Nbr" data-localize="currentPayTable.acctNbr" data-localize-location="scope">
                                                    ${bank.acctNumLabel}
                                            </td>
                                            <td data-title="Dep Amt" data-localize="currentPayTable.depAmt" data-localize-location="scope">
                                                    ${bank.amt}
                                            </td>
                                        </tr>
                                        <tr class="total-tr">
                                            <td colspan="3" style="text-align:right">
                                                <b><span data-localize="earningTable.total"></span>:</b>
                                            </td>
                                            <td><b>
                                                    ${earnings.earningsBankTotal}
                                            </b></td>
                                        </tr>
                                    </c:forEach>
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
        function submitEarning(){
            $("#selectEarnings")[0].submit();
        }
        let dateAvailable = ['12/15/2018', '11/28/2018', '11/15/2018']
        $(function() {
            let dateArry = dateAvailable.map(function(item, key, ary) {
                let date = new Date(item)
                item = new Date(date.setHours(12, 0, 0, 0)).valueOf()
                return item
            })
            $('#SearchPayDate')
                .fdatepicker({
                    format: 'mm/dd/yyyy',
                    onRender: function(date) {
                        let a = new Date(date.setHours(12, 0, 0, 0))
                        let newDate = a.valueOf()
                        let value = $.inArray(newDate, dateArry)
                        return value === -1 ? 'disabled' : ''
                    }
                })
                .on('changeDate', function(ev) {
                    console.log(ev)
                })
                .data('datepicker')
            let showEarningDetail = false
            let showDeductionsDetail = false
            $(".totalEarningBtn").click(function(){
                if(!showEarningDetail){
                    $(".earning-detail").show()
                    $(this).html("Close <")
                    showEarningDetail = true
                }else{
                    $(".earning-detail").hide()
                    $(this).html("Detail >")
                    showEarningDetail = false
                }
                
            })
            $(".totalDeductionsBtn").click(function(){
                if(!showDeductionsDetail){
                    $(".deduction-table").show()
                    $(this).html("Close <")
                    showDeductionsDetail = true
                }else{
                    $(".deduction-table").hide()
                    $(this).html("Detail >")
                    showDeductionsDetail = false
                }
            })
        })
    </script>
</html>
