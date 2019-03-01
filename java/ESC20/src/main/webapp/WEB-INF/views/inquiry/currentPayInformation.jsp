<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> <%@ taglib
uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> <%@ page
language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html lang="en">
    <head>
        <title data-localize="headTitle.currentPayInfo"></title>
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <%@ include file="../commons/header.jsp"%>
    </head>
    <body class="hold-transition sidebar-mini">
        <div class="wrapper">
            <%@ include file="../commons/bar.jsp"%>

            <main class="content-wrapper" id="content" tabindex="-1">
                <section class="content">
                    <h2 class="clearfix no-print section-title">
                        <span data-localize="title.currentPayInfo"></span>
                        <button
                            class="btn btn-primary pull-right"
                            onclick="doPrint()"
                            data-localize="label.print"
                        ></button>
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
                        <c:if test="${not empty message}">
                            <br/>
                            <p class="topMsg">${message}</p>
                            <br/>
                        </c:if>
                        <p class="table-top-title">
                            <b data-localize="label.employeeInformation"></b>
                        </p>
                        <table
                            class="table border-table responsive-table no-thead print-table noNumTable"
                        >
                            <tbody>
                                <tr>
                                    <td class="td-title"><b data-localize="currentPayTable.name"></b></td>
                                    <td
                                        class="td-content"
                                        data-localize="currentPayTable.name"
                                        colspan="3"
                                    >
                                    ${sessionScope.userDetail.nameF} ${sessionScope.userDetail.nameM} ${sessionScope.userDetail.nameL}
                                    </td>
                                </tr>
                                <tr>
                                    <td class="td-title"><b data-localize="currentPayTable.address"></b></td>
                                    <td class="td-content" data-title="Address" data-localize="currentPayTable.address">
                                        ${sessionScope.district.address}
                                    </td>
                                    <td class="td-title"><b data-localize="currentPayTable.employeeId"></b></td>
                                    <td
                                        class="td-content"
                                        data-title="Employee ID"
                                        data-localize="currentPayTable.employeeId"
                                    >
                                    ${sessionScope.userDetail.empNbr}
                                    </td>
                                </tr>
                                <tr>
                                    <td class="td-title"></td>
                                    <td class="td-content" data-title="">
                                        ${sessionScope.district.city},
                                    ${sessionScope.district.state} ${sessionScope.district.zip}
                                    </td>
                                    <td class="td-title">
                                        <b data-localize="currentPayTable.dateOfBirth"></b>
                                    </td>
                                    <td
                                        class="td-content"
                                        data-title="Date of Birth"
                                        data-localize="currentPayTable.dateOfBirth"
                                    >
                                    ${sessionScope.userDetail.dob}
                                    </td>
                                </tr>
                                <tr>
                                    <td class="td-title">
                                        <b data-localize="currentPayTable.phoneNumber"></b>
                                    </td>
                                    <td
                                        class="td-content"
                                        data-title="Phone Number"
                                        data-localize="currentPayTable.phoneNumber"
                                    >
                                    ${sessionScope.district.phone}
                                    </td>
                                    <td class="td-title"><b data-localize="currentPayTable.gender"></b></td>
                                    <td class="td-content" data-title="Gender" data-localize="currentPayTable.gender">
                                        <c:if test="${sessionScope.userDetail.sex =='F'}">
                                            <span data-localize="label.female"></span>
                                        </c:if>
                                        <c:if test="${sessionScope.userDetail.sex =='M'}">
                                                <span data-localize="label.male"></span>
                                        </c:if>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="td-title"><b data-localize="currentPayTable.degree"></b></td>
                                    <td
                                        class="td-content"
                                        data-title="Degree"
                                        colspan="3"
                                        data-localize="currentPayTable.degree"
                                    >
                                        ${employeeInfo.highDegreeDescription}
                                    </td>
                                </tr>
                                <tr>
                                    <td class="td-title">
                                        <b data-localize="currentPayTable.professionalYearsExp"></b>
                                    </td>
                                    <td
                                        class="td-content"
                                        data-title="Professional Years Experience"
                                        data-localize="currentPayTable.professionalYearsExp"
                                    >
                                    ${employeeInfo.yrsProExper}
                                    </td>
                                    <td class="td-title">
                                        <b data-localize="currentPayTable.professionalDistrictExp"></b>
                                    </td>
                                    <td
                                        class="td-content"
                                        data-title="Professional District Experience"
                                        data-localize="currentPayTable.professionalDistrictExp"
                                    >
                                    ${employeeInfo.yrsExpDist}
                                    </td>
                                </tr>
                                <tr>
                                    <td class="td-title">
                                        <b data-localize="currentPayTable.nonProfessionalYearExp"></b>
                                    </td>
                                    <td
                                        class="td-content"
                                        data-title="Non Professional Years Experience"
                                        data-localize="currentPayTable.nonProfessionalYearExp"
                                    >
                                    ${employeeInfo.yrsProExperLoc}
                                    </td>
                                    <td class="td-title">
                                        <b data-localize="currentPayTable.noneProfessionalDistrictExp"></b>
                                    </td>
                                    <td
                                        class="td-content"
                                        data-title="Non Professional District Experience"
                                        data-localize="currentPayTable.noneProfessionalDistrictExp"
                                    >
                                    ${employeeInfo.yrsExpDistLoc}
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                        <c:if test="${fn:length(frequencies) == 0}">
                            <p data-localize="label.noCrrentPay"></p>
                        </c:if>
                        <c:if test="${fn:length(frequencies) > 0}">
                            <c:forEach var="frequency" items="${frequencies}">
                                <p class="table-top-title">
                                    <b><span data-localize="label.frequency"></span>: ${frequency}</b>
                                </p>
                                <table
                                    class="table border-table responsive-table no-thead print-table noNumTable"
                                >
                                    <tbody>
                                        <tr>
                                            <td class="td-title">
                                                <b data-localize="currentPayTable.martialStatus"></b>
                                            </td>
                                            <td
                                                class="td-content"
                                                data-title="Marital Status"
                                                data-localize="currentPayTable.martialStatus"
                                            >
                                                <c:if test="${payInfos[frequency].maritalStatTax =='M'}">
                                                        <span data-localize="label.married"></span>
                                                </c:if>
                                                <c:if test="${payInfos[frequency].maritalStatTax =='S'}">
                                                        <span data-localize="label.single"></span>
                                                </c:if>
                                            </td>
                                            <td class="td-title">
                                                <b data-localize="currentPayTable.numOfExemptions"></b>
                                            </td>
                                            <td
                                                class="td-content"
                                                data-title="Number of Exemptions"
                                                data-localize="currentPayTable.numOfExemptions"
                                            >
                                                ${payInfos[frequency].nbrTaxExempts}
                                            </td>
                                            <td class="td-title"><b data-localize="currentPayTable.payCampus"></b></td>
                                            <td
                                                class="td-content"
                                                data-title="Pay Campus"
                                                data-localize="currentPayTable.payCampus"
                                            >
                                            ${payCampuses[frequency]}
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                                <p class="table-top-title">
                                    <b><span data-localize="label.positions"></span></b>
                                </p>
                                
                                    <table class="table border-table responsive-table no-thead print-table" style="border:0;">
                                        <tbody>
                                                <c:forEach var="job" items="${jobs[frequency]}" varStatus="jobCount">
                                            <tr>
                                                <td class="td-title" data-localize="currentPayTable.title"></td>
                                                <td class="td-content text-left" data-title="Title" data-localize="currentPayTable.title">
                                                    <b>
                                                            ${job.jobCdDescription}
                                                    </b>
                                                </td>
                                                <td class="td-title" data-localize="currentPayTable.annualPayments"></td>
                                                <td class="td-content" data-title="Annual Payments" data-localize="currentPayTable.annualPayments">
                                                        ${job.nbrAnnualPymts}
                                                </td>
                                                <td class="td-title" data-localize="currentPayTable.regularHour"></td>
                                                <td class="td-content" data-title="Regular Hours" data-localize="currentPayTable.regularHour">
                                                        <fmt:formatNumber value="${job.regHrsWrk}" pattern="#,##0.00"/>
                                                </td>
                                                <td class="td-title" data-localize="currentPayTable.remailPayments"></td>
                                                <td class="td-content" data-title="Remain Payments" data-localize="currentPayTable.remailPayments">
                                                        ${job.nbrRemainPymts}
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="td-title" data-localize="currentPayTable.annualSalary"></td>
                                                <td class="td-content" data-title="Annual Salary" data-localize="currentPayTable.annualSalary">
                                                        <fmt:formatNumber value="${job.contrAmt}" pattern="#,##0.00"/>
                                                </td>
                                                <td class="td-title" data-localize="currentPayTable.dailyRate"></td>
                                                <td class="td-content" data-title="Daily Rate" data-localize="currentPayTable.dailyRate">
                                                        <fmt:formatNumber value="${job.dlyRateofPay}" pattern="#,##0.00"/>
                                                </td>
                                                <td class="td-title" data-localize="currentPayTable.payRate"></td>
                                                <td class="td-content" data-title="Pay Rate" data-localize="currentPayTable.payRate">
                                                        <fmt:formatNumber value="${job.payRate}" pattern="#,##0.00"/>
                                                </td>
                                                <td class="td-title" data-localize="currentPayTable.overtimeRate"></td>
                                                <td class="td-content" data-title="Overtime Rate" data-localize="currentPayTable.overtimeRate">
                                                        <fmt:formatNumber value="${job.ovtmRate}" pattern="#,##0.00"/>
                                                </td>
                                            </tr>
                                            <c:if test="${jobCount.index < fn:length(jobs[frequency]) - 1}">
                                                <tr>
                                                    <td colspan="8" style="border:0;"></td>
                                                </tr>
                                            </c:if>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                
                                
                                <c:if test="${fn:length(accounts[frequency]) > 0}">
                                        <p class="table-top-title">
                                                <b><span data-localize="label.bankCardInfo"></span></b>
                                            </p>
                                    <table class="table border-table responsive-table print-table">
                                        <thead>
                                            <tr>
                                                <th data-localize="currentPayTable.bankCode"></th>
                                                <th data-localize="currentPayTable.bankName"></th>
                                                <th data-localize="currentPayTable.accountType"></th>
                                                <th data-localize="currentPayTable.acctNbr"></th>
                                                <th data-localize="currentPayTable.depAmt"></th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                                <c:forEach items="${accounts[frequency]}" var="account" varStatus="counter">
                                                    <tr>
                                                        <td data-title="" data-localize="currentPayTable.bankCode" data-localize-location="scope">
                                                                ${account.bankCd}
                                                        </td>
                                                        <td class="text-left" data-title="" data-localize="currentPayTable.bankName" data-localize-location="scope">
                                                            ${account.bankName}
                                                        </td>
                                                        <td class="text-left" data-title="" data-localize="currentPayTable.accountType" data-localize-location="scope">
                                                                ${account.bankAccountType} - ${account.bankAccountTypeDescription}
                                                        </td>
                                                        <td class="text-left" data-title="" data-localize="currentPayTable.acctNbr" data-localize-location="scope">
                                                                ${account.bankAccountNumber}
                                                        </td>
                                                        <td data-title="" data-localize="currentPayTable.depAmt" data-localize-location="scope">
                                                                <fmt:formatNumber value="${account.bankAccountAmount.amount}" pattern="#,##0.00"/> ${account.bankAccountAmount.currency}
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                        </tbody>
                                    </table>
                                </c:if>

                                <c:if test="${fn:length(stipends[frequency]) > 0}">
                                        <p class="table-top-title">
                                                <b><span data-localize="label.stipendInfo"></span> :</b>
                                        </p>
                                    <table class="table border-table responsive-table print-table">
                                        <thead>
                                            <tr>
                                                <th data-localize="currentPayTable.extraDuty"></th>
                                                <th data-localize="currentPayTable.type"></th>
                                                <th data-localize="currentPayTable.amount"></th>
                                                <th data-localize="currentPayTable.remainAmount"></th>
                                                <th data-localize="currentPayTable.remainPayments"></th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                                <c:forEach items="${stipends[frequency]}" var="stipend" varStatus="counter">
                                                    <tr>
                                                        <td data-title="" data-localize="currentPayTable.extraDuty" data-localize-location="scope">
                                                                ${stipend.extraDutyDescription}
                                                        </td>
                                                        <td data-title="" data-localize="currentPayTable.type" data-localize-location="scope">
                                                                ${stipend.defaultAccountType}
                                                        </td>
                                                        <td data-title="" data-localize="currentPayTable.amount" data-localize-location="scope">
                                                                <fmt:formatNumber value="${stipend.extraDutyAmt}" pattern="#,##0.00"/>
                                                        </td>
                                                        <td data-title="" data-localize="currentPayTable.remainAmount" data-localize-location="scope">
                                                                <fmt:formatNumber value="${stipend.remainAmt}" pattern="#,##0.00"/>
                                                        </td>
                                                        <td data-title="" data-localize="currentPayTable.remainPayments" data-localize-location="scope">
                                                                <fmt:formatNumber value="${stipend.remainPayments}" pattern="#,##0.00"/>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                        </tbody>
                                    </table>
                                </c:if>
                            </c:forEach>
                        </c:if>
                    </div>
                </section>
            </main>
        </div>
        <%@ include file="../commons/footer.jsp"%>
    </body>
    <script src="/<%=request.getContextPath().split("/")[1]%>/js/viewJs/inquiry/currentPayInformation.js"></script>
</html>
