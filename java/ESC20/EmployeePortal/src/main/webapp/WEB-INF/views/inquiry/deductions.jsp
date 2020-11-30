<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> <%@ taglib
uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> <%@ page
language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html> 
<html lang="en">
    <head>
        <title>${sessionScope.languageJSON.headTitle.deductions}</title>
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <%@ include file="../commons/header.jsp"%>
    </head>
    <body class="hold-transition sidebar-mini">
        <div class="wrapper">
            <%@ include file="../commons/bar.jsp"%>

            <main class="content-wrapper" id="content" tabindex="-1">
                <section class="content">
                    <div class="clearfix no-print section-title">
                        <h1 class="pageTitle">${sessionScope.languageJSON.title.deductions}</h1> 
                    </div>
                    <div class="content-white EMP-detail heightFull">
                            <c:if test="${not empty sessionScope.options.messageDeductions}">
                                <p class="topMsg error-hint" role="alert">${sessionScope.options.messageDeductions}</p>
                            </c:if>
                            <c:if test="${fn:length(frequencies) == 0}">
                                <div class="error">${sessionScope.languageJSON.label.noDeductions}</div>
                            </c:if>
                            <c:if test="${fn:length(frequencies) > 0}">
                                    <c:forEach items="${frequencies}" var="frequency">
                                        <h2 class="no-print table-top-title"><b><span>${sessionScope.languageJSON.label.frequency}</span>: ${frequency}</b></h2>
                                        <%-- <div>
                                                <span>${sessionScope.languageJSON.label.maritalStatus}</span>:  
                                                <c:if test="${payInfos[frequency].maritalStatTax =='M'}">
                                                    ${payInfos[frequency].maritalStatTax} - <span>${sessionScope.languageJSON.label.married}</span>
                                                </c:if>
                                                <c:if test="${payInfos[frequency].maritalStatTax =='S'}">
                                                    ${payInfos[frequency].maritalStatTax} - <span>${sessionScope.languageJSON.label.single}</span>
                                                </c:if>
                                                <br>
                                                <span>${sessionScope.languageJSON.label.numOfExemptions}</span>: ${payInfos[frequency].nbrTaxExempts}<br>
                                        </div> --%>
                                        <table
                                    class="table border-table responsive-table no-thead print-table noNumTable smTitleTable">
                                    <tbody>
                                        <tr>
                                            <th id="martialStatus_${count.index}" class="td-title">
                                                <b>${sessionScope.languageJSON.currentPayTable.martialStatus}</b>
                                            </th>
                                            <td headers="martialStatus_${count.index}" class="td-content"
                                                data-title="${sessionScope.languageJSON.currentPayTable.martialStatus}">
                                                <c:if test="${payInfos[frequency].maritalStatTax =='M'}">
                                                    ${payInfos[frequency].maritalStatTax} -
                                                    <span>${sessionScope.languageJSON.label.married}</span>
                                                </c:if>
                                                <c:if test="${payInfos[frequency].maritalStatTax =='S'}">
                                                    ${payInfos[frequency].maritalStatTax} -
                                                    <span>${sessionScope.languageJSON.label.single}</span>
                                                </c:if>
                                            </td>
                                            <th id="numOfExemptions_${count.index}" class="td-title">
                                                <b>${sessionScope.languageJSON.currentPayTable.numOfExemptions}</b>
                                            </th>
                                            <td headers="numOfExemptions_${count.index}" class="td-content"
                                                data-title="${sessionScope.languageJSON.currentPayTable.numOfExemptions}">
                                                ${payInfos[frequency].nbrTaxExempts}
                                            </td>
                                            <th id="payCampus_${count.index}" class="td-title">
                                                <b>${sessionScope.languageJSON.currentPayTable.payCampus}</b></th>
                                            <td headers="payCampus_${count.index}" class="td-content"
                                                data-title="${sessionScope.languageJSON.currentPayTable.payCampus}">
                                                ${payCampuses[frequency]}
                                            </td>
                                        </tr>
                                        <tr>
                                        <th id="fillingStatus_${count.index}">
                                                    <b>${sessionScope.languageJSON.profile.fillingStatus}</b></th>

                                                <td headers="fillingStatus_${count.index}" class="text-left"
                                                    data-title="${sessionScope.languageJSON.profile.fillingStatus}">
                                                    <input type="hidden" name="w4FileStat" value="${w4Request[frequency].w4FileStat }">

                                                    <!-- ${payInfos[frequency].w4FileStat} -->
                                                    <c:forEach var="w4FileStat" items="${w4FileStatOptions}"
                                                        varStatus="count">
                                                        <c:if
                                                            test="${w4FileStat.code == w4Request[frequency].w4FileStat }">
                                                            ${w4FileStat.displayLabel}
                                                        </c:if>
                                                    </c:forEach>
                                                </td>
                                               
                                                <th id="multiJobs_${count.index}">
                                                    <b>${sessionScope.languageJSON.profile.multiJobs}</b></th>

                                                <td headers="multiJobs_${count.index}" class="text-left"
                                                    data-title="${sessionScope.languageJSON.profile.multiJobs}">
                                                    ${w4Request[frequency].w4MultiJob}
                                                </td>

                                                <th id="childrenUnder17_${count.index}">
                                                    <b>${sessionScope.languageJSON.profile.childrenUnder17}</b></th>

                                                <td headers="childrenUnder17_${count.index}" class="text-left"
                                                    data-title="${sessionScope.languageJSON.profile.multiJobs}" colspan="3">
                                                    <fmt:formatNumber value="${w4Request[frequency].w4NbrChldrn}"
                                                        pattern="#,##0" />
                                                </td>
                                                </tr>
                                                <tr>
                                                <th id="otherDependents_${count.index}">
                                                    <b>${sessionScope.languageJSON.profile.otherDependents}</b></th>

                                                <td headers="otherDependents_${count.index}" class="text-left"
                                                    data-title="${sessionScope.languageJSON.profile.otherDependents}">
                                                    <fmt:formatNumber value="${w4Request[frequency].w4NbrOthrDep}"
                                                        pattern="#,##0" />
                                                </td>
                                                
                                                    <th id="otherd_${count.index}">
                                                    <b>${sessionScope.languageJSON.profile.otherExemption}</b></th>

                                                    <td headers="otherd_${count.index}" class="text-left"
                                                        data-title="${sessionScope.languageJSON.profile.otherExemption}">
                                                        <fmt:formatNumber value="${w4Request[frequency].w4OthrExmptAmt}" pattern="#,##0.00"/>
                                                    </td>

                                                <th id="otherIncome_${count.index}">
                                                    <b>${sessionScope.languageJSON.profile.otherIncome}</b></th>

                                                <td headers="otherIncome_${count.index}" class="text-left"
                                                    data-title="${sessionScope.languageJSON.profile.otherIncome}">
                                                    <fmt:formatNumber value="${w4Request[frequency].w4OthrIncAmt}"
                                                        pattern="#,##0" />
                                                </td>
                                                

                                                <th id="deductions_${count.index}">
                                                    <b>${sessionScope.languageJSON.profile.deductions}</b></th>

                                                <td headers="deductions_${count.index}" class="text-left"
                                                    data-title="${sessionScope.languageJSON.profile.deductions}">
                                                    <fmt:formatNumber value="${w4Request[frequency].w4OthrDedAmt}"
                                                        pattern="#,##0" />
                                                </td>
                                            </tr>
                                    </tbody>
                                </table>
                                        <table class="table border-table responsive-table print-table deductionTable">
                                            <thead>
                                                <tr>
                                                    <th class="text-center">${sessionScope.languageJSON.deductionsTable.deductionCode}</th>
                                                    <th>${sessionScope.languageJSON.deductionsTable.description}</th>
                                                    <th class="text-right">${sessionScope.languageJSON.deductionsTable.amount}</th>
                                                    <th class="text-center">${sessionScope.languageJSON.deductionsTable.cafeteriaFlag}</th>
                                                    <th class="text-right">${sessionScope.languageJSON.deductionsTable.employerContributionAmount}</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                    <c:forEach items="${deductions[frequency]}" var="deduct" varStatus="counter">
                                                        <tr>
                                                            <td class="text-center" data-title="${sessionScope.languageJSON.deductionsTable.deductionCode}">${deduct.dedCd}</td>
                                                            <td data-title="${sessionScope.languageJSON.deductionsTable.description}">${deduct.dedCdDesc}</td>
                                                            <td data-title="${sessionScope.languageJSON.deductionsTable.amount}">
                                                                <fmt:formatNumber value="${deduct.empAmt}" pattern="#,##0.00"/>
                                                            </td>
                                                            <td class="text-center" data-title="${sessionScope.languageJSON.deductionsTable.cafeteriaFlag}">${deduct.cafeFlgStr}</td>
                                                            <td data-title="${sessionScope.languageJSON.deductionsTable.employerContributionAmount}">
                                                                <fmt:formatNumber value="${deduct.emplrAmt}" pattern="#,##0.00"/>
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                            </tbody>
                                        </table>
                                    </c:forEach>
                            </c:if>
                    </div>
                </section>
            </main>
        </div>
        <%@ include file="../commons/footer.jsp"%>
    </body>
</html>
