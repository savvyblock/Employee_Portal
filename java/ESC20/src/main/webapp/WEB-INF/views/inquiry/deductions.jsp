<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> <%@ taglib
uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> <%@ page
language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html lang="en">
    <head>
        <title data-localize="headTitle.deductions"></title>
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <%@ include file="../commons/header.jsp"%>
    </head>
    <body class="hold-transition sidebar-mini">
        <div class="wrapper">
            <%@ include file="../commons/bar.jsp"%>

            <main class="content-wrapper" id="content" tabindex="-1">
                <section class="content">
                    <h2 class="clearfix no-print section-title" data-localize="title.deductions"></h2>
                    <div class="content-white EMP-detail">
                            <c:if test="${not empty message}">
                                <br/>
                                <p class="topMsg">${message}</p>
                                <br/>
                            </c:if>
                            <c:if test="${fn:length(frequencies) == 0}">
                                <div class="error" data-localize="label.noDeductions"></div>
                            </c:if>
                            <c:if test="${fn:length(frequencies) > 0}">
                                    <c:forEach items="${frequencies}" var="frequency">
                                        <p class="no-print table-top-title"><b><span data-localize="label.frequency"></span>: ${frequency}</b></p>
                                        <div>
                                                <span data-localize="label.maritalStatus"></span>:  
                                                <c:if test="${payInfos[frequency].maritalStatTax =='M'}">
                                                        <span data-localize="label.married"></span>
                                                </c:if>
                                                <c:if test="${payInfos[frequency].maritalStatTax =='S'}">
                                                        <span data-localize="label.single"></span>
                                                </c:if>
                                                <br>
                                                <span data-localize="label.numOfExemptions"></span>: ${payInfos[frequency].nbrTaxExempts}<br>
                                        </div>
                                        <table class="table border-table responsive-table print-table deductionTable">
                                            <thead>
                                                <tr>
                                                    <th class="text-center" data-localize="deductionsTable.deductionCode"></th>
                                                    <th class="text-center" data-localize="deductionsTable.description"></th>
                                                    <th class="text-center" data-localize="deductionsTable.amount"></th>
                                                    <th class="text-center" data-localize="deductionsTable.cafeteriaFlag"></th>
                                                    <th class="text-center" data-localize="deductionsTable.employerContributionAmount"></th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                    <c:forEach items="${deductions[frequency]}" var="deduct" varStatus="counter">
                                                        <tr>
                                                            <td data-localize="deductionsTable.deductionCode" data-localize-location="scope,data-title">${deduct.dedCd}</td>
                                                            <td data-localize="deductionsTable.description" data-localize-location="scope,data-title">${deduct.dedCdDesc}</td>
                                                            <td data-localize="deductionsTable.amount" data-localize-location="scope,data-title">
                                                                <fmt:formatNumber value="${deduct.empAmt}" pattern="#,##0.00"/>
                                                            </td>
                                                            <td class="text-center" data-localize="deductionsTable.cafeteriaFlag" data-localize-location="scope,data-title">${deduct.cafeFlgStr}</td>
                                                            <td data-localize="deductionsTable.employerContributionAmount" data-localize-location="scope,data-title">
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
