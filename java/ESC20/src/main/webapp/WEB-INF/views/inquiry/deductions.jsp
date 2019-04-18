<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> <%@ taglib
uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> <%@ page
language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
                    <h1 class="clearfix no-print section-title">${sessionScope.languageJSON.title.deductions}</h1>
                    <div class="content-white EMP-detail">
                            <c:if test="${not empty message}">
                                <br/>
                                <p class="topMsg">${message}</p>
                                <br/>
                            </c:if>
                            <c:if test="${fn:length(frequencies) == 0}">
                                <div class="error">${sessionScope.languageJSON.label.noDeductions}</div>
                            </c:if>
                            <c:if test="${fn:length(frequencies) > 0}">
                                    <c:forEach items="${frequencies}" var="frequency">
                                        <h2 class="no-print table-top-title"><b><span>${sessionScope.languageJSON.label.frequency}</span>: ${frequency}</b></h2>
                                        <div>
                                                <span>${sessionScope.languageJSON.label.maritalStatus}</span>:  
                                                <c:if test="${payInfos[frequency].maritalStatTax =='M'}">
                                                        <span>${sessionScope.languageJSON.label.married}</span>
                                                </c:if>
                                                <c:if test="${payInfos[frequency].maritalStatTax =='S'}">
                                                        <span>${sessionScope.languageJSON.label.single}</span>
                                                </c:if>
                                                <br>
                                                <span>${sessionScope.languageJSON.label.numOfExemptions}</span>: ${payInfos[frequency].nbrTaxExempts}<br>
                                        </div>
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
                                                            <td class="text-center" scope="${sessionScope.languageJSON.deductionsTable.deductionCode}" data-title="${sessionScope.languageJSON.deductionsTable.deductionCode}">${deduct.dedCd}</td>
                                                            <td scope="${sessionScope.languageJSON.deductionsTable.description}" data-title="${sessionScope.languageJSON.deductionsTable.description}">${deduct.dedCdDesc}</td>
                                                            <td scope="${sessionScope.languageJSON.deductionsTable.amount}" data-title="${sessionScope.languageJSON.deductionsTable.amount}">
                                                                <fmt:formatNumber value="${deduct.empAmt}" pattern="#,##0.00"/>
                                                            </td>
                                                            <td class="text-center" scope="${sessionScope.languageJSON.deductionsTable.cafeteriaFlag}" data-title="${sessionScope.languageJSON.deductionsTable.cafeteriaFlag}">${deduct.cafeFlgStr}</td>
                                                            <td scope="${sessionScope.languageJSON.deductionsTable.employerContributionAmount}" data-title="${sessionScope.languageJSON.deductionsTable.employerContributionAmount}">
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
