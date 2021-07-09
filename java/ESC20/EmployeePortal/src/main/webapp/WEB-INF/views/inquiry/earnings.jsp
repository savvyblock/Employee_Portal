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
                                <c:if test="${not empty payDates}">
                               	 <div class="pull-right right-btn">
		                            <form class="no-print" action="exportPDF" method="POST">
										<input type="hidden" name="${_csrf.parameterName}"
											value="${_csrf.token}" />
										<input type="hidden" name="selectedPayDate"
											value="${selectedPayDate.dateFreqVoidAdjChk}" />
												<button type="submit" role="button" class="btn btn-primary" 
											aria-label="${sessionScope.languageJSON.label.print}">${sessionScope.languageJSON.label.print}</button>
                                    </form>
		                           <%--  <form class="no-print" action="printPDF" method="POST" target="printIframe">
										<input type="hidden" name="${_csrf.parameterName}"
											value="${_csrf.token}" />
										<input type="hidden" name="selectedPayDate"
											value="${selectedPayDate.dateFreqVoidAdjChk}" />
			                            <button type="submit" role="button" class="btn btn-primary">
			                            	${sessionScope.languageJSON.label.print}
			                            </button>
		                            </form>
		
		                            <iframe style="display:none" name="printIframe" onload="load()" id="printIframe"></iframe> --%>
                                </div>
                            	</c:if>
                            </div>
                    
                       
                            <div class="toPrint content-white EMP-detail earningPage" style="height: auto;">
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
                                                                        <span class="textCapital">${sessionScope.languageJSON.label.married}</span>
                                                                    </c:if>
                                                                    <c:if test="${earnings.info.withholdingStatus =='S'}">
                                                                            <span class="textCapital">${sessionScope.languageJSON.label.single}</span>
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
                                                ${sessionScope.userDetail.addrNbr} ${sessionScope.userDetail.addrStr}<br/>
                                            	${sessionScope.userDetail.addrCity},
                                            	${sessionScope.userDetail.addrSt} ${sessionScope.userDetail.addrZip}
                                                </div>
                                                <p
                                                    style="text-align:right;padding-right: 20px;margin: 0;"
                                                   
                                                >
                                                ${sessionScope.languageJSON.label.noteEarning}
                                                </p>
                                                <div class="print-block hr-black"></div>
                                            </div>
                                </div>
                                
                                <c:if test="${not empty sessionScope.options.messageEarnings}">
		                            <p class="topMsg error-hint" role="alert">${sessionScope.options.messageEarnings}</p>
		                        </c:if>
		                        <c:if test="${empty payDates}">
									<div class="topMsg error-hint">${sessionScope.languageJSON.earningTable.NoEarnings}</div>
                                </c:if>
                                <div class="currentTimeBox">
                                        <div class="currentTime down"></div>
                                    </div>
								<c:if test="${not empty payDates}">
                           	    	 <form
                                    class="no-print searchForm"
                                    action="earningsByPayDate"
                                    id="selectEarnings"
                                    method="POST"
                                >
                                	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                    <div class="form-group in-line paddingSide-0">
                                        <label class="form-title" for="payDateString"><span>${sessionScope.languageJSON.label.payDates}</span>:</label>
                                        <select class="form-control" name="payDateString" id="payDateString" onchange="submitEarning()" style="max-width:280px;">
                                            <c:forEach var="payDate" items="${payDates}" varStatus="counter">
                                                <option value="${payDate.dateFreqVoidAdjChk}" <c:if test="${payDate.dateFreq == selectedPayDate.dateFreq }">selected</c:if>>${payDate.label}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </form>
                              
                                <div class="needToClone">  
                                    <div class="earningTitleWrap">
                                        <div class="earningTitleWrap-inner">
                                                <table
                                                class="no-print no-thead table no-border-table responsive-table earning-title-table"
                                            >
                                                <tbody>
                                                  
                                                    <tr>
                                                        <th id="checkNumber" class="td-title column1"><span>${sessionScope.languageJSON.earningTable.checkNumber}</span>:</th>
                                                        <td headers="checkNumber"
                                                            class="td-content left-td" data-title="${sessionScope.languageJSON.earningTable.checkNumber}"
                                                           
                                                        >
                                                        ${earnings.info.checkNumber}
                                                        </td>
                                                        <td>  </td>
                                                        <th id="periodBeginningDate" style='width:200px;' class="td-title">
                                                                <span>${sessionScope.languageJSON.earningTable.periodBeginningDate}</span>:
                                                        </th>
                                                        <td headers="periodBeginningDate" style='text-align:left;'
                                                            class="td-content" data-title="${sessionScope.languageJSON.earningTable.periodBeginningDate}"
                                                           
                                                        >
                                                        ${earnings.info.periodBeginningDate}
                                                        </td>
                                                        <th id="periodEndingDate" class="td-title">
                                                                <span>${sessionScope.languageJSON.earningTable.periodEndingDate}</span>:
                                                        </th>
                                                        <td headers="periodEndingDate" style='text-align:left;'
                                                            class="td-content" data-title="${sessionScope.languageJSON.earningTable.periodEndingDate}"
                                                           
                                                        >
                                                        ${earnings.info.periodEndingDate}
                                                        </td>
                                                    </tr>
                                                   
                                                </tbody>
                                            </table>
                                        </div>
                                           
                                    </div>    

                                    <table
                                    class="table border-table responsive-table no-thead print-table noNumTable smTitleTable">
                                    <tbody>
                                        <tr>
                                            <th id="martialStatus_${count.index}" class="td-title">
                                                <b>${sessionScope.languageJSON.currentPayTable.martialStatus}</b>
                                            </th>
                                            <td headers="martialStatus_${count.index}" class="td-content"
                                                data-title="${sessionScope.languageJSON.currentPayTable.martialStatus}">
                                                <c:if test="${payInfos[freq].maritalStatTax =='M'}">
                                                    ${payInfos[freq].maritalStatTax} -
                                                    <span>${sessionScope.languageJSON.label.married}</span>
                                                </c:if>
                                                <c:if test="${payInfos[freq].maritalStatTax =='S'}">
                                                    ${payInfos[freq].maritalStatTax} -
                                                    <span>${sessionScope.languageJSON.label.single}</span>
                                                </c:if>
                                            </td>
                                            <th id="numOfExemptions_${count.index}" class="td-title">
                                                <b>${sessionScope.languageJSON.currentPayTable.numOfExemptions}</b>
                                            </th>
                                            <td headers="numOfExemptions_${count.index}" class="td-content"
                                                data-title="${sessionScope.languageJSON.currentPayTable.numOfExemptions}">
                                                ${payInfos[freq].nbrTaxExempts}
                                            </td>
                                            <th id="payCampus_${count.index}" class="td-title">
                                                <b>${sessionScope.languageJSON.currentPayTable.payCampus}</b></th>
                                            <td headers="payCampus_${count.index}" class="td-content"
                                                data-title="${sessionScope.languageJSON.currentPayTable.payCampus}">
                                                ${payCampuses[freq]}
                                            </td>
                                        </tr>
                                        <tr>
                                        <th id="fillingStatus_${count.index}">
                                                    <b>${sessionScope.languageJSON.profile.fillingStatus}</b></th>

                                                <td headers="fillingStatus_${count.index}" class="text-left"
                                                    data-title="${sessionScope.languageJSON.profile.fillingStatus}">
                                                    <input type="hidden" name="w4FileStat" value="${w4Request[freq].w4FileStat }">

                                                    <!-- ${payInfos[freq].w4FileStat} -->
                                                    <c:forEach var="w4FileStat" items="${w4FileStatOptions}"
                                                        varStatus="count">
                                                        <c:if
                                                            test="${w4FileStat.code == w4Request[freq].w4FileStat }">
                                                            ${w4FileStat.displayLabel}
                                                        </c:if>
                                                    </c:forEach>
                                                </td>
                                               
                                                <th id="multiJobs_${count.index}">
                                                    <b>${sessionScope.languageJSON.profile.multiJobs}</b></th>

                                                <td headers="multiJobs_${count.index}" class="text-left"
                                                    data-title="${sessionScope.languageJSON.profile.multiJobs}">
                                                    ${w4Request[freq].w4MultiJob}
                                                </td>

                                                <th id="childrenUnder17_${count.index}">
                                                    <b>${sessionScope.languageJSON.profile.childrenUnder17}</b></th>

                                                <td headers="childrenUnder17_${count.index}" class="text-left"
                                                    data-title="${sessionScope.languageJSON.profile.childrenUnder17}" colspan="3">
                                                    <fmt:formatNumber value="${w4Request[freq].w4NbrChldrn}"
                                                        pattern="#,##0" />
                                                </td>
                                                </tr>
                                                <tr>
                                                <th id="otherDependents_${count.index}">
                                                    <b>${sessionScope.languageJSON.profile.otherDependents}</b></th>

                                                <td headers="otherDependents_${count.index}" class="text-left"
                                                    data-title="${sessionScope.languageJSON.profile.otherDependents}">
                                                    <fmt:formatNumber value="${w4Request[freq].w4NbrOthrDep}"
                                                        pattern="#,##0" />
                                                </td>
                                                
                                                <th id="otherd_${count.index}">
                                                    <b>${sessionScope.languageJSON.profile.otherExemption}</b></th>

                                                <td headers="otherd_${count.index}" class="text-left"
                                                    data-title="${sessionScope.languageJSON.profile.otherExemption}">
                                                    <fmt:formatNumber value="${w4Request[freq].w4OthrExmptAmt}" pattern="#,##0.00"/>
                                                </td>

                                                <th id="otherIncome_${count.index}">
                                                    <b>${sessionScope.languageJSON.profile.otherIncome}</b></th>

                                                <td headers="otherIncome_${count.index}" class="text-left"
                                                    data-title="${sessionScope.languageJSON.profile.otherIncome}">
                                                    <fmt:formatNumber value="${w4Request[freq].w4OthrIncAmt}"
                                                        pattern="#,##0.00" />
                                                </td>

                                                <th id="deductions_${count.index}">
                                                    <b>${sessionScope.languageJSON.profile.deductions}</b></th>

                                                <td headers="deductions_${count.index}" class="text-left"
                                                    data-title="${sessionScope.languageJSON.profile.deductions}">
                                                    <fmt:formatNumber value="${w4Request[freq].w4OthrDedAmt}"
                                                        pattern="#,##0.00" />
                                                </td>
                                            </tr>
                                    </tbody>
                                </table>                        
                                    
                                    <div class="hr-black"></div>
                                    <div class="earning-body">

                                        <div class="total-table-title">
                                            <%--  <h2 class="no-print table-top-title">
                                                <b>${sessionScope.languageJSON.label.earningAndDeductions}</b>
                                            </h2> --%>
                                            <p style="text-align:left;padding-left: 0px;margin: 0;">
                                                <b>${sessionScope.languageJSON.label.noteEarning}</b>
                                            </p>
                                        </div>
                                        <div class="clearfix">
                                            <div class="total-table  pull-left">
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
                                                            <th id="standardGross"><span>${sessionScope.languageJSON.earningTable.standardGross}</span></th>
                                                            <td headers="thisPeriodTitle01 standardGross">
                                                                    <fmt:formatNumber value="${earnings.deductions.standardGross}" pattern="#,##0.00"/>
                                                            </td>
                                                            <td headers="calendarYTDTitle01 standardGross" class="print-td">
                                                                    <fmt:formatNumber value="${YTDEarnings.deductions.standardGross}" pattern="#,##0.00"/>
                                                            </td>
                                                        </tr>
                                                        <tr class="print-tr">
                                                            <th id="supplementalPay"><span>${sessionScope.languageJSON.earningTable.supplementalPay}</span></th>
                                                            <td headers="thisPeriodTitle01 supplementalPay">
                                                                    <fmt:formatNumber value="${earnings.deductions.supplementalPay}" pattern="#,##0.00"/>
                                                            </td>
                                                            <td headers="calendarYTDTitle01 supplementalPay">
                                                                <fmt:formatNumber value="${YTDEarnings.deductions.supplementalPay}" pattern="#,##0.00"/>
                                                            </td>
                                                        </tr>
                                                        <tr class="print-tr">
                                                            <th id="overtimePay"><span>${sessionScope.languageJSON.earningTable.overtimePay}</span></th>
                                                            <td headers="thisPeriodTitle01 overtimePay">
                                                                    <fmt:formatNumber value="${earnings.deductions.overtimePay}" pattern="#,##0.00"/>
                                                            </td>
                                                            <td headers="calendarYTDTitle01 overtimePay">
                                                                <%-- <fmt:formatNumber value="${YTDEarnings.deductions.overtimePay}" pattern="#,##0.00"/> --%>
                                                            </td>
                                                        </tr>
                                                        <tr class="print-tr">
                                                            <th id="absenceRefund"><span>${sessionScope.languageJSON.earningTable.absenceRefund}</span></th>
                                                            <td headers="thisPeriodTitle01 absenceRefund">
                                                                    <fmt:formatNumber value="${earnings.deductions.absenceRefund}" pattern="#,##0.00"/>
                                                            </td>
                                                            <td headers="calendarYTDTitle01 absenceRefund">
                                                                <%-- <fmt:formatNumber value="${YTDEarnings.deductions.absenceRefund}" pattern="#,##0.00"/> --%>
                                                            </td>
                                                        </tr>
                                                        <tr class="print-tr">
                                                            <th id="taxedFringeBenefits"><span>${sessionScope.languageJSON.earningTable.taxedFringeBenefits}</span></th>
                                                            <td headers="thisPeriodTitle01 taxedFringeBenefits">
                                                                    <fmt:formatNumber value="${earnings.deductions.taxedFringe}" pattern="#,##0.00"/>
                                                            </td>
                                                            <td headers="calendarYTDTitle01 taxedFringeBenefits">
                                                                <fmt:formatNumber value="${YTDEarnings.deductions.taxedFringe}" pattern="#,##0.00"/>
                                                            </td>
                                                        </tr>
                                                        <tr class="print-tr">
                                                            <th id="earnedIncomeCredit"><span>${sessionScope.languageJSON.earningTable.earnedIncomeCredit}</span></th>
                                                            <td headers="thisPeriodTitle01 earnedIncomeCredit">
                                                                    <fmt:formatNumber value="${earnings.deductions.earnedIncomeCred}" pattern="#,##0.00"/>
                                                            </td>
                                                            <td headers="calendarYTDTitle01 earnedIncomeCredit">
                                                                <fmt:formatNumber value="${YTDEarnings.deductions.earnedIncomeCred}" pattern="#,##0.00"/>
                                                            </td>
                                                        </tr>
                                                        <tr class="print-tr">
                                                            <th id="nonTRSTax"><span>${sessionScope.languageJSON.earningTable.nonTRSTax}</span></th>
                                                            <td headers="thisPeriodTitle01 nonTRSTax">
                                                                    <fmt:formatNumber value="${earnings.deductions.nonTrsTax}" pattern="#,##0.00"/>
                                                            </td>
                                                            <td headers="calendarYTDTitle01 nonTRSTax">
                                                                <fmt:formatNumber value="${YTDEarnings.deductions.nonTrsTax}" pattern="#,##0.00"/>
                                                            </td>
                                                        </tr>
                                                        <tr class="print-tr">
                                                            <th id="nonTRSNonTax"><span>${sessionScope.languageJSON.earningTable.nonTRSNonTax}</span></th>
                                                            <td headers="thisPeriodTitle01 nonTRSNonTax">
                                                                    <fmt:formatNumber value="${earnings.deductions.nonTrsNonTax}" pattern="#,##0.00"/>
                                                            </td>
                                                            <td headers="calendarYTDTitle01 nonTRSNonTax">
                                                                <fmt:formatNumber value="${YTDEarnings.deductions.nonTrsNonTax}" pattern="#,##0.00"/>
                                                            </td>
                                                        </tr>
                                                        <tr class="print-tr">
                                                            <th id="TRSSupp"><span>${sessionScope.languageJSON.earningTable.TRSSupp}</span></th>
                                                            <td headers="thisPeriodTitle01 TRSSupp">
                                                                    <fmt:formatNumber value="${earnings.deductions.trsSupplemental}" pattern="#,##0.00"/>
                                                            </td>
                                                            <td headers="calendarYTDTitle01 TRSSupp">
                                                                <fmt:formatNumber value="${YTDEarnings.deductions.trsSupplemental}" pattern="#,##0.00"/>
                                                            </td>
                                                        </tr>
                                                        <tr class="total-tr totalEarningTr">
                                                            <th id="totalEarnings">--- <span>${sessionScope.languageJSON.earningTable.totalEarnings}</span></th>
                                                            <td headers="thisPeriodTitle01 totalEarnings">
                                                                    <fmt:formatNumber value="${earnings.deductions.totalEarnings}" pattern="#,##0.00"/>
                                                            </td>
                                                            <td headers="calendarYTDTitle01 totalEarnings" class="print-td">
                                                                <fmt:formatNumber value="${YTDEarnings.deductions.totalEarnings}" pattern="#,##0.00"/>
                                                            </td>
                                                        </tr>
                                                        <tr class="print-tr">
                                                            <th id="absenceDeductions"><span>${sessionScope.languageJSON.earningTable.absenceDeductions}</span></th>
                                                            <td headers="thisPeriodTitle01 absenceDeductions">
                                                                    <fmt:formatNumber value="${earnings.deductions.absenceDed}" pattern="#,##0.00"/>
                                                            </td>
                                                            <td headers="calendarYTDTitle01 absenceDeductions">
                                                               <%--  <fmt:formatNumber value="${YTDEarnings.deductions.absenceDed}" pattern="#,##0.00"/> --%>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <th id="withTax"><span>${sessionScope.languageJSON.earningTable.withTax}</span></th>
                                                            <td headers="thisPeriodTitle01 withTax">
                                                                    <fmt:formatNumber value="${earnings.deductions.withholdingTax}" pattern="#,##0.00"/>
                                                            </td>
                                                            <td headers="calendarYTDTitle01 withTax" class="print-td">
                                                                <fmt:formatNumber value="${YTDEarnings.deductions.withholdingTax}" pattern="#,##0.00"/>
                                                            </td>
                                                        </tr>
                                                        <tr class="print-tr">
                                                            <th id="FICATax"><span>${sessionScope.languageJSON.earningTable.FICATax}</span></th>
                                                            <td headers="thisPeriodTitle01 FICATax">
                                                                    <fmt:formatNumber value="${earnings.deductions.ficaTax}" pattern="#,##0.00"/>
                                                            </td>
                                                            <td headers="calendarYTDTitle01 FICATax">
                                                                <fmt:formatNumber value="${YTDEarnings.deductions.ficaTax}" pattern="#,##0.00"/>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <th id="medicareTax"><span>${sessionScope.languageJSON.earningTable.medicareTax}</span></th>
                                                            <td headers="thisPeriodTitle01 medicareTax">
                                                                    <fmt:formatNumber value="${earnings.deductions.medicareTax}" pattern="#,##0.00"/>
                                                            </td>
                                                            <td headers="calendarYTDTitle01 medicareTax" class="print-td">
                                                                <fmt:formatNumber value="${YTDEarnings.deductions.medicareTax}" pattern="#,##0.00"/>
                                                            </td>
                                                        </tr>
                
                                                        <tr>
                                                            <th id="TRSSalaryRed"><span>${sessionScope.languageJSON.earningTable.TRSSalaryRed}</span></th>
                                                            <td headers="thisPeriodTitle01 TRSSalaryRed">
                                                                    <fmt:formatNumber value="${earnings.deductions.trsSalaryRed}" pattern="#,##0.00"/>
                                                            </td>
                                                            <td headers="calendarYTDTitle01 TRSSalaryRed" class="print-td">
                                                                <fmt:formatNumber value="${YTDEarnings.deductions.trsSalaryRed}" pattern="#,##0.00"/>
                                                            </td>
                                                        </tr>
                
                                                        <tr>
                                                            <th id="TRSInsurance"><span>${sessionScope.languageJSON.earningTable.TRSInsurance}</span></th>
                                                            <td headers="thisPeriodTitle01 TRSInsurance">
                                                                    <fmt:formatNumber value="${earnings.deductions.trsInsurance}" pattern="#,##0.00"/>
                                                            </td>
                                                            <td headers="calendarYTDTitle01 TRSInsurance" class="print-td">
                                                                <fmt:formatNumber value="${YTDEarnings.deductions.trsInsurance}" pattern="#,##0.00"/>
                                                            </td>
                                                        </tr>
                
                                                        <tr class="totalDeductionsTr">
                                                            <th id="totalOtherDeductions"><span>${sessionScope.languageJSON.earningTable.totalOtherDeductions}</span></th>
                                                            <td headers="thisPeriodTitle01 totalOtherDeductions">
                                                                    <fmt:formatNumber value="${earnings.deductions.totOtherDed}" pattern="#,##0.00"/>
                                                            </td>
                                                            <td headers="calendarYTDTitle01 totalOtherDeductions" class="print-td">
                                                                <fmt:formatNumber value="${YTDEarnings.deductions.totOtherDed}" pattern="#,##0.00"/>
                                                            </td>
                                                        </tr>
                
                                                        <tr class="total-tr">
                                                            <th id="totalDeductions">--- <span>${sessionScope.languageJSON.earningTable.totalDeductions}</span></th>
                                                            <td headers="thisPeriodTitle01 totalDeductions">
                                                                    <fmt:formatNumber value="${earnings.deductions.totDed}" pattern="#,##0.00"/>
                                                            </td>
                                                            <td headers="calendarYTDTitle01 totalDeductions" class="print-td">
                                                                <fmt:formatNumber value="${YTDEarnings.deductions.totDed}" pattern="#,##0.00"/>
                                                            </td>
                                                        </tr>
                                                        <tr class="total-tr">
                                                            <th id="netPay">--- <span>${sessionScope.languageJSON.earningTable.netPay}</span></th>
                                                            <td headers="thisPeriodTitle01 netPay">
                                                                    <fmt:formatNumber value="${earnings.deductions.netPay}" pattern="#,##0.00"/>
                                                            </td>
                                                            <td headers="calendarYTDTitle01 netPay" class="print-td">
                                                                <%-- <fmt:formatNumber value="${YTDEarnings.deductions.netPay}" pattern="#,##0.00"/> --%>
                                                            </td>
                                                        </tr>
                                                        <tr class="print-tr">
                                                            <th id="nonTRSnonPayTaxable">
                                                                    <span>${sessionScope.languageJSON.earningTable.nonTRSnonPayTaxable}</span></th>
                                                            <td headers="thisPeriodTitle01 nonTRSnonPayTaxable">
                                                                    <fmt:formatNumber value="${earnings.deductions.nonTrsNonPayTax}" pattern="#,##0.00"/>
                                                            </td>
                                                            <td headers="calendarYTDTitle01 nonTRSnonPayTaxable">
                                                                <fmt:formatNumber value="${YTDEarnings.deductions.nonTrsNonPayTax}" pattern="#,##0.00"/>
                                                            </td>
                                                        </tr>
                                                        <tr class="print-tr">
                                                            <th id="nonTRSnonPayNonTaxable">
                                                                    <span>${sessionScope.languageJSON.earningTable.nonTRSnonPayNonTaxable}</span>
                                                            </th>
                                                            <td headers="thisPeriodTitle01 nonTRSnonPayNonTaxable">
                                                                    <fmt:formatNumber value="${earnings.deductions.nonTrsNonPayNonTax}" pattern="#,##0.00"/>
                                                            </td>
                                                            <td headers="calendarYTDTitle01 nonTRSnonPayNonTaxable">
                                                                <fmt:formatNumber value="${YTDEarnings.deductions.nonTrsNonPayNonTax}" pattern="#,##0.00"/>
                                                            </td>
                                                        </tr>
                                                        <tr class="print-tr bold-tr">
                                                            <th id="taxableWages"><span>${sessionScope.languageJSON.earningTable.taxableWages}</span></th>
                                                            <td headers="thisPeriodTitle01 taxableWages">
                                                                <fmt:formatNumber value="${earnings.deductions.taxableWage}" pattern="#,##0.00"/>
                                                            </td>
                                                            <td headers="calendarYTDTitle01 taxableWages">
                                                                <fmt:formatNumber value="${YTDEarnings.deductions.taxableWage}" pattern="#,##0.00"/>
                                                            </td>
                                                        </tr>
                                                        <tr class="print-tr">
                                                            <th id="FICAGross"><span>${sessionScope.languageJSON.earningTable.FICAGross}</span></th>
                                                            <td headers="thisPeriodTitle01 FICAGross">
                                                                <fmt:formatNumber value="${earnings.deductions.ficaWage}" pattern="#,##0.00"/>
                                                            </td>
                                                            <td headers="calendarYTDTitle01 FICAGross">
                                                                <fmt:formatNumber value="${YTDEarnings.deductions.ficaWage}" pattern="#,##0.00"/>
                                                            </td>
                                                        </tr>
                                                        <tr class="print-tr">
                                                            <th id="medicareGross"><span>${sessionScope.languageJSON.earningTable.medicareGross}</span></th>
                                                            <td headers="thisPeriodTitle01 medicareGross">
                                                                <fmt:formatNumber value="${earnings.deductions.medGross}" pattern="#,##0.00"/>
                                                            </td>
                                                            <td headers="calendarYTDTitle01 medicareGross">
                                                                <fmt:formatNumber value="${YTDEarnings.deductions.medGross}" pattern="#,##0.00"/>
                                                            </td>
                                                        </tr>
                                                        <tr class="print-tr">
                                                            <th id="emplrSponsoredHealth">
                                                                <span>${sessionScope.languageJSON.earningTable.emplrSponsoredHealth}</span>
                                                            </th>
                                                            <td headers="thisPeriodTitle01 emplrSponsoredHealth"></td>
                                                            <td headers="calendarYTDTitle01 emplrSponsoredHealth">
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
                                                                    <th id="jobDescription01_${counter.index}">
                                                                            ${job.code} - ${job.description}
                                                                    </th>
                                                                    <td headers="units01 jobDescription01_${counter.index}">
                                                                       <%--  <c:if test="${job.units != 0 && job.units != 0.00 && job.units != '0.00'}">
                                                                                <fmt:formatNumber value="${job.units}" pattern="#,##0.00"/>
                                                                        </c:if> --%>
                                                                        <fmt:formatNumber value="${job.units}" pattern="#,##0.00"/>
                                                                    </td>
                                                                    <td headers="payRate01 jobDescription01_${counter.index}">
                                                                            <fmt:formatNumber value="${job.payRate}" pattern="#,##0.00"/>
                                                                    </td>
                                                                    <td headers="thisPeriod01 jobDescription01_${counter.index}">
                                                                            <fmt:formatNumber value="${job.amt}" pattern="#,##0.00"/>
                                                                    </td>
                                                                </tr>
                                                            </c:forEach>
                                                            <tr class="total-tr">
                                                                <th  id="totalStandardGross" colspan="3">
                                                                    <b>${sessionScope.languageJSON.earningTable.totalStandardGross}:</b>
                                                                </th>
                                                                <td headers="thisPeriod01 totalStandardGross">
                                                                    <b>
                                                                        <fmt:formatNumber value="${earnings.earningsJobTotal}" pattern="#,##0.00"/>
                                                                    </b>
                                                                </td>
                                                            </tr>
                                                        </tbody>
                                                    </table>
                                                    <table class="table border-table">
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
                                                                            <th id="jobDescription02_${counter.index}">
                                                                                    ${overtime.jobCd} - ${overtime.description}
                                                                            </th> 
                                                                            <td headers="units02 jobDescription02_${counter.index}">
                                                                                    <c:if test="${overtime.overtimeUnits != 0 && overtime.overtimeUnits != 0.00 && overtime.overtimeUnits != '0.00'}">
                                                                                            <fmt:formatNumber value="${overtime.overtimeUnits}" pattern="#,##0.00"/>
                                                                                    </c:if>
                                                                                    
                                                                            </td> 
                                                                            <td headers="payRate02 jobDescription02_${counter.index}">
                                                                                    <fmt:formatNumber value="${overtime.overtimeRate}" pattern="#,##0.00"/>
                                                                            </td> 
                                                                            <td headers="thisPeriod02 jobDescription02_${counter.index}">
                                                                                    <fmt:formatNumber value="${overtime.thisPeriod}" pattern="#,##0.00"/>
                                                                            </td>
                                                                    </tr>
                                                                </c:forEach>
                                                            <tr class="total-tr">
                                                                <th id="totalOvertimePay" colspan="3">
                                                                    <span>${sessionScope.languageJSON.earningTable.totalOvertimePay}:</span>
                                                                </th>
                                                                <td headers="thisPeriod02 totalOvertimePay">
                                                                        <fmt:formatNumber value="${earnings.earningsOvertimeTotal}" pattern="#,##0.00"/>
                                                                </td>
                                                            </tr>
                                                        </tbody>
                                                    </table>
                                                    <table class="table border-table">
                                                        <thead>
                                                            <tr>
                                                                <th id="supType">${sessionScope.languageJSON.earningTable.supType}</th>
                                                                <th id="thisPeriod03" class="text-right earning-detail-thisPeriod">${sessionScope.languageJSON.earningTable.thisPeriod}</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                                <c:forEach items="${earnings.supplemental}" var="supplemental" varStatus="counter">
                                                                    <tr>
                                                                            <th id="description03_${counter.index}">
                                                                                <c:if test="${supplemental.description != 'ZZZ - default'}"> 
                                                                                        ${supplemental.description}
                                                                                </c:if>
                                                                                <c:if test="${supplemental.description == 'ZZZ - default'}"> 
                                                                                    <span>${sessionScope.languageJSON.label.otherSupplemental}</span>
                                                                                </c:if>
                                                                            </th> 
                                                                            <td headers="thisPeriod03 description03_${counter.index}">
                                                                                    <fmt:formatNumber value="${supplemental.amt}" pattern="#,##0.00"/>
                                                                            </td> 
                                                                    </tr>
                                                                </c:forEach>
                                                            <tr class="total-tr">
                                                                <th id="totalSupPay">
                                                                    <span>${sessionScope.languageJSON.earningTable.totalSupPay}:</span>
                                                                </th>
                                                                <td headers="thisPeriod03 totalSupPay">
                                                                        <fmt:formatNumber value="${earnings.earningsSupplementalTotal}" pattern="#,##0.00"/>
                                                                </td>
                                                            </tr>
                                                        </tbody>
                                                    </table>
                                                    <table class="table border-table">
                                                        <thead>
                                                            <tr>
                                                                <th id="nonTRSTaxType" >${sessionScope.languageJSON.earningTable.nonTRSTaxType}</th>
                                                                <th id="thisPeriod04" class="text-right earning-detail-thisPeriod">${sessionScope.languageJSON.earningTable.thisPeriod}</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                                <c:forEach items="${earnings.nonTrsTax}" var="nontrstax" varStatus="counter">
                                                                    <tr>
                                                                            <th id="nonTRSTaxType_${counter.index}">
                                                                                    <c:if test="${nontrstax.description != 'ZZZ - default'}"> ${nontrstax.description}</c:if>
                                                                                    <c:if test="${nontrstax.description == 'ZZZ - default'}"> 
                                                                                        <span>${sessionScope.languageJSON.label.otherNonTRSTax}</span>
                                                                                    </c:if>
                                                                            </th> 
                                                                            <td headers="thisPeriod04 nonTRSTaxType_${counter.index}">
                                                                                    <fmt:formatNumber value="${nontrstax.amt}" pattern="#,##0.00"/>
                                                                            </td> 
                                                                    </tr>
                                                                </c:forEach>
                                                            <tr class="total-tr">
                                                                <th id="totalNonTRSTax">
                                                                    <span>${sessionScope.languageJSON.earningTable.totalNonTRSTax}:</span>
                                                                </th>
                                                                <td headers="thisPeriod04 totalNonTRSTax">
                                                                        <fmt:formatNumber value="${earnings.earningsNonTrsTaxTotal}" pattern="#,##0.00"/>
                                                                </td>
                                                            </tr>
                                                        </tbody>
                                                    </table>
                                                    <table class="table border-table">
                                                        <thead>
                                                            <tr>
                                                                <th id="nonTRSNonTaxType">${sessionScope.languageJSON.earningTable.nonTRSNonTaxType}</th>
                                                                <th id="thisPeriod05" class="text-right earning-detail-thisPeriod">${sessionScope.languageJSON.earningTable.thisPeriod}</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                                <c:forEach items="${earnings.nonTrsNonTax}" var="nontax" varStatus="counter">
                                                                    <tr>
                                                                        <th id="nonTRSNonTaxType_${counter.index}">
                                                                                <c:if test="${nontax.description != 'ZZZ - default'}"> 
                                                                                        ${nontax.description}
                                                                                </c:if>
                                                                                <c:if test="${nontax.description == 'ZZZ - default'}"> 
                                                                                    <span>${sessionScope.languageJSON.label.otherNonTRSNonTax}</span>
                                                                                </c:if>
                                                                        </th> 
                                                                        <td headers="thisPeriod05 nonTRSNonTaxType_${counter.index}">
                                                                                <fmt:formatNumber value="${nontax.amt}" pattern="#,##0.00"/>
                                                                        </td> 
                                                                    </tr>
                                                                </c:forEach>
                                                            <tr class="total-tr">
                                                                <th id="totalNonTRSNonTax">
                                                                    <span>${sessionScope.languageJSON.earningTable.totalNonTRSNonTax}:</span>
                                                                </th>
                                                                <td headers="thisPeriod05 totalNonTRSNonTax">
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
                                                                <th id="otherDeductionsTitle" class="text-left" style="width:200px;">${sessionScope.languageJSON.earningTable.otherDeductions}</th>
                                                                <th id="cafeTitle" class="text-center">${sessionScope.languageJSON.earningTable.cafe}</th>
                                                                <th id="thisPeriod06" class="text-right" style="width:60px;">${sessionScope.languageJSON.earningTable.thisPeriod}</th>
                                                                <th id="employerContribution" class="text-right sameWidth20">${sessionScope.languageJSON.earningTable.employerContribution}</th>
                                                                <th id="calendarYTD" class="print-td text-right" style="width:85px;">
                                                                    ${sessionScope.languageJSON.earningTable.calendarYTD} ${year}
                                                                </th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                            <c:forEach items="${earnings.other}" var="other" varStatus="counter">
                                                                <tr>
                                                                    <th id="otherDeductionsDesc_${counter.index}">
                                                                            ${other.code} - ${other.description}
                                                                    </th>
                                                                    <td headers="cafeTitle otherDeductionsDesc_${counter.index}" class="text-center">
                                                                            ${other.cafe_flg}
                                                                    </td>
                                                                    <td headers="thisPeriod06 otherDeductionsDesc_${counter.index}">
                                                                            <fmt:formatNumber value="${other.amt}" pattern="#,##0.00"/>
                                                                    </td>
                                                                    <td headers="employerContribution otherDeductionsDesc_${counter.index}">
                                                                            <fmt:formatNumber value="${other.contrib}" pattern="#,##0.00"/>
                                                                    </td>
                                                                    <td headers="calendarYTD otherDeductionsDesc_${counter.index}" class="print-td">
                                                                            <fmt:formatNumber value="${other.tydAmt}" pattern="#,##0.00"/>
                                                                    </td>
                                                                </tr>
                                                            </c:forEach>
                                                            <c:if test="${fn:length(earnings.other) > 0}">   
                                                                <c:if test="${earnings.other[0].depCareMax == 1}">
                                                                    <tr>
                                                                        <td colspan="4">
                                                                            <span>${sessionScope.languageJSON.label.dependentCareTotalExceed}</span>
                                                                        </td>
                                                                    </tr>
                                                                </c:if>
                                                                <c:if test="${earnings.other[0].hsaCareMax == 1}">
                                                                    <tr>
                                                                        <td colspan="4">
                                                                            <span>${sessionScope.languageJSON.label.HSAEmployerTotalExceed}</span>
                                                                        </td>
                                                                    </tr>
                                                                </c:if>
                                                            </c:if>
                                                            <tr class="total-tr">
                                                                <th id="totalOtherDeductionsOther" colspan="2">
                                                                    <b>${sessionScope.languageJSON.earningTable.totalOtherDeductions}:</b>
                                                                </th>
                                                                <td headers="thisPeriod06 totalOtherDeductionsOther">
                                                                        <fmt:formatNumber value="${earnings.earningsOtherTotal}" pattern="#,##0.00"/>
                                                                </td>
                                                                <td headers="employerContribution totalOtherDeductionsOther">
                                                                        <fmt:formatNumber value="${earnings.earningsOtherContribTotal}" pattern="#,##0.00"/>
                                                                </td>
                                                                <td headers="calendarYTD totalOtherDeductionsOther" class="print-td">
                                                                        <fmt:formatNumber value="${YTDEarnings.earningsOtherTydTotal}" pattern="#,##0.00"/>
                                                                </td>
                                                            </tr>
                                                        </tbody>
                                                    </table>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="print-block hr-black"></div>
                                  		 <table class="table border-table responsive-table payRoll-table print-table tableColumn4">
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
                                                    <td class="text-left" data-title="Bank Name">
                                                            ${bank.name} (${bank.code})
                                                    </td>
                                                    <td class="text-left" data-title="Account Type">
                                                            ${bank.acctTypeCode} - ${bank.acctType}
                                                    </td>
                                                    <td class="text-left" data-title="Acct Nbr">
                                                            ${bank.acctNumLabel}
                                                    </td>
                                                    <td  class="text-right" data-title="Dep Amt">
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
                                        
                                        
                                       
                                  
                                        <div class="show-all-screen hr-black"></div>
                                           <%--    <c:if test ="${!isSupplemental}"> --%>
		                                        <table
		                                            class="table responsive-table border-table print-table tableColumn4"
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
		                                                        <td class="text-left" data-title="${sessionScope.languageJSON.earningTable.leaveType}">
		                                                                ${leave.code} - ${leave.description}
		                                                        </td>
		                                                        <td class="text-right" data-title="${sessionScope.languageJSON.earningTable.unitsUsedThisPeriod}">
		                                                                <fmt:formatNumber value="${leave.unitsPrior}" pattern="#,##0.000"/>
		                                                        </td>
		                                                        <td class="text-right" data-title="${sessionScope.languageJSON.earningTable.balance}">
		                                                                <fmt:formatNumber value="${leave.balance}" pattern="#,##0.000"/>
		                                                        </td>
		                                                        <td
		                                                            class="print-td text-right"
		                                                            data-title="${sessionScope.languageJSON.earningTable.unitsUsedYearToDate}"
		                                                        >
		                                                            ${leave.unitsUsed}
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
		                                   <%--  </c:if> --%>
                                         </div>
                                </div>
                                  </c:if>
                            </div>
                        </section>
            </main>
        </div>
        <%@ include file="../commons/footer.jsp"%>
    </body>
    <script src="/<%=request.getContextPath().split("/")[1]%>/js/viewJs/inquiry/earnings.js"></script>
</html>
