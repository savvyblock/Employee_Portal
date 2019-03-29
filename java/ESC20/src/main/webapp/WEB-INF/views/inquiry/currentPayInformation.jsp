<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> <%@ taglib
uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> <%@ page
language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html lang="en">
    <head>
        <title data-localize="headTitle.currentPayInfo"></title>
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <%@ include file="../commons/header.jsp"%>
        <script>
        	var languageJSON = ${sessionScope.languageJSON};
        </script>
    </head>
    <body class="hold-transition sidebar-mini">
        <div class="wrapper">
            <%@ include file="../commons/bar.jsp"%>

            <main class="content-wrapper" id="content" tabindex="-1">
                <section class="content">
                    <div class="clearfix no-print section-title">
                        <h1 class="pageTitle" data-localize="title.currentPayInfo"></h1>
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
                    <form class="no-print" action="exportPDF" method="POST">
						<input type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}" />
						<button type="submit" role="button" class="btn btn-primary"
							data-localize="label.submit"></button>
					</form>
                    <div class="toPrint content-white EMP-detail">
                        <div class="exportPDFBox">
                                <div class="print-block print-title">
                                        <div style="text-align:center;margin-bottom:10px;">
                                                ${sessionScope.district.name}<br />${sessionScope.district.address}<br />
                                                ${sessionScope.district.city},
                                                ${sessionScope.district.state} ${sessionScope.district.zip}-${sessionScope.district.zip4}
                                            </div>
                                        <div style="text-align:center;">
                                                <span data-localize="label.currentPayInfo"></span><br />
                                            <div id="date-now"></div>
                                        </div>
                                    </div>
                        </div>
                        
                        <c:if test="${not empty message}">
                            <br/>
                            <p class="topMsg">${message}</p>
                            <br/>
                        </c:if>
                        <div class="needToClone">
                        	<input type="hidden" id="isPrintPDF" value="${isPrintPDF}" />
                        	<input type="hidden" id="language" value="${language}" />
                            <h2 class="table-top-title">
                                <b data-localize="label.employeeInformation"></b>
                            </h2>
                            <table
                                class="table border-table responsive-table no-thead print-table noNumTable"
                            >
                                <tbody>
                                    <tr>
                                        <td id="userDetailName" class="td-title" ><b>${sessionScope.languageJSON.currentPayTable.name}</b></td>
                                        <td
                                            headers="userDetailName" class="td-content"
                                            colspan="3"
                                        >
                                        ${sessionScope.userDetail.nameF} ${sessionScope.userDetail.nameM} ${sessionScope.userDetail.nameL}
                                        </td>
                                    </tr>
                                    <tr>
                                        <td id="userDetailAddress" class="td-title"  rowspan="2"><b>${sessionScope.languageJSON.currentPayTable.address}</b></td>
                                        <td headers="userDetailAddress" class="td-content" data-title="Address" rowspan="2">
                                            ${sessionScope.district.address}<br/>
                                            ${sessionScope.district.city},
                                            ${sessionScope.district.state} ${sessionScope.district.zip}
                                        </td>
                                        <td id="employeeId" class="td-title" ><b>${sessionScope.languageJSON.currentPayTable.employeeId}</b></td>
                                        <td
                                            headers="employeeId" class="td-content"
                                            data-title="Employee ID"
                                        >
                                        ${sessionScope.userDetail.empNbr}
                                        </td>
                                    </tr>
                                    <tr>
                                        
                                        <td id="dateOfBirth" class="td-title" >
                                            <b>${sessionScope.languageJSON.currentPayTable.dateOfBirth}</b>
                                        </td>
                                        <td
                                            headers="dateOfBirth" class="td-content"
                                            data-title="Date of Birth"
                                        >
                                        ${sessionScope.userDetail.dob}
                                        </td>
                                    </tr>
                                    <tr>
                                        <td id="phoneNumber" class="td-title" >
                                            <b>${sessionScope.languageJSON.currentPayTable.phoneNumber}</b>
                                        </td>
                                        <td
                                            headers="phoneNumber" class="td-content"
                                            data-title="Phone Number"
                                        >
                                        ${sessionScope.district.phone}
                                        </td>
                                        <td id="gender" class="td-title" ><b>${sessionScope.languageJSON.currentPayTable.gender}</b></td>
                                        <td headers="gender" class="td-content" data-title="Gender">
                                            <c:if test="${sessionScope.userDetail.sex =='F'}">
                                                <span data-localize="label.female"></span>
                                            </c:if>
                                            <c:if test="${sessionScope.userDetail.sex =='M'}">
                                                    <span data-localize="label.male"></span>
                                            </c:if>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td id="degree" class="td-title" ><b>${sessionScope.languageJSON.currentPayTable.degree}</b></td>
                                        <td
                                            headers="degree" class="td-content"
                                            data-title="Degree"
                                            colspan="3"
                                        >
                                            ${employeeInfo.highDegreeDescription}
                                        </td>
                                    </tr>
                                    <tr>
                                        <td id="professionalYearsExp" class="td-title" >
                                            <b>${sessionScope.languageJSON.currentPayTable.professionalYearsExp}</b>
                                        </td>
                                        <td
                                            headers="professionalYearsExp" class="td-content"
                                            data-title="Professional Years Experience"
                                        >
                                        ${employeeInfo.yrsProExper}
                                        </td>
                                        <td id="professionalDistrictExp" class="td-title" >
                                            <b>${sessionScope.languageJSON.currentPayTable.professionalDistrictExp}</b>
                                        </td>
                                        <td
                                            headers="professionalDistrictExp" class="td-content"
                                            data-title="Professional District Experience"
                                        >
                                        ${employeeInfo.yrsExpDist}
                                        </td>
                                    </tr>
                                    <tr>
                                        <td id="nonProfessionalYearExp" class="td-title" >
                                            <b>${sessionScope.languageJSON.currentPayTable.nonProfessionalYearExp}</b>
                                        </td>
                                        <td
                                            headers="nonProfessionalYearExp" class="td-content"
                                            data-title="Non Professional Years Experience"
                                        >
                                        ${employeeInfo.yrsProExperLoc}
                                        </td>
                                        <td id="noneProfessionalDistrictExp" class="td-title" >
                                            <b>${sessionScope.languageJSON.currentPayTable.noneProfessionalDistrictExp}</b>
                                        </td>
                                        <td
                                            headers="noneProfessionalDistrictExp" class="td-content"
                                            data-title="Non Professional District Experience"
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
                                <c:forEach var="frequency" items="${frequencies}"  varStatus="count">
                                    <h2 class="table-top-title">
                                        <b><span>${sessionScope.languageJSON.label.frequency}</span>: ${frequency}</b>
                                    </h2>
                                    <table
                                        class="table border-table responsive-table no-thead print-table noNumTable"
                                    >
                                        <tbody>
                                            <tr>
                                                <td id="martialStatus_${count.index}" class="td-title" >
                                                    <b>${sessionScope.languageJSON.currentPayTable.martialStatus}</b>
                                                </td>
                                                <td
                                                    headers="martialStatus_${count.index}" class="td-content"
                                                    data-title="Marital Status"
                                                    data-localize="currentPayTable.martialStatus"
                                                >
                                                    <c:if test="${payInfos[frequency].maritalStatTax =='M'}">
                                                            <span>${sessionScope.languageJSON.label.married}</span>
                                                    </c:if>
                                                    <c:if test="${payInfos[frequency].maritalStatTax =='S'}">
                                                            <span>${sessionScope.languageJSON.label.single}</span>
                                                    </c:if>
                                                </td>
                                                <td id="numOfExemptions_${count.index}" class="td-title" >
                                                    <b>${sessionScope.languageJSON.currentPayTable.numOfExemptions}</b>
                                                </td>
                                                <td
                                                    headers="numOfExemptions_${count.index}" class="td-content"
                                                    data-title="Number of Exemptions"
                                                >
                                                    ${payInfos[frequency].nbrTaxExempts}
                                                </td>
                                                <td id="payCampus_${count.index}" class="td-title" ><b>${sessionScope.languageJSON.currentPayTable.payCampus}</b></td>
                                                <td
                                                    headers="payCampus" class="td-content"
                                                    data-title="Pay Campus"
                                                >
                                                ${payCampuses[frequency]}
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                    <h2 class="table-top-title">
                                        <b><span>${sessionScope.languageJSON.label.positions}</span></b>
                                    </h2>
                                    
                                        <table class="table border-table responsive-table no-thead print-table" style="border:0;">
                                            <tbody>
                                                    <c:forEach var="job" items="${jobs[frequency]}" varStatus="jobCount">
                                                <tr>
                                                    <td id="jobCdDescription_${jobCount.index}" class="td-title">${sessionScope.languageJSON.currentPayTable.title}</td>
                                                    <td headers="jobCdDescription_${jobCount.index}" class="td-content text-left" data-title="Title">
                                                        <b>
                                                                ${job.jobCdDescription}
                                                        </b>
                                                    </td>
                                                    <td id="annualPayments_${jobCount.index}" class="td-title">${sessionScope.languageJSON.currentPayTable.annualPayments}</td>
                                                    <td headers="annualPayments_${jobCount.index}" class="td-content" data-title="Annual Payments">
                                                            ${job.nbrAnnualPymts}
                                                    </td>
                                                    <td id="regularHour_${jobCount.index}" class="td-title">${sessionScope.languageJSON.currentPayTable.regularHour}</td>
                                                    <td headers="regularHour_${jobCount.index}" class="td-content" data-title="Regular Hours" data-localize="currentPayTable.regularHour">
                                                            <fmt:formatNumber value="${job.regHrsWrk}" pattern="#,##0.00"/>
                                                    </td>
                                                    <td id="remailPayments_${jobCount.index}" class="td-title">${sessionScope.languageJSON.currentPayTable.remailPayments}</td>
                                                    <td headers="remailPayments_${jobCount.index}" class="td-content" data-title="Remain Payments">
                                                            ${job.nbrRemainPymts}
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td id="annualSalary_${jobCount.index}" class="td-title">${sessionScope.languageJSON.currentPayTable.annualSalary}</td>
                                                    <td headers="annualSalary_${jobCount.index}" class="td-content" data-title="Annual Salary">
                                                            <fmt:formatNumber value="${job.contrAmt}" pattern="#,##0.00"/>
                                                    </td>
                                                    <td id="dailyRate_${jobCount.index}" class="td-title">${sessionScope.languageJSON.currentPayTable.dailyRate}</td>
                                                    <td headers="dailyRate_${jobCount.index}" class="td-content" data-title="Daily Rate">
                                                            <fmt:formatNumber value="${job.dlyRateofPay}" pattern="#,##0.00"/>
                                                    </td>
                                                    <td id="payRate_${jobCount.index}" class="td-title">${sessionScope.languageJSON.currentPayTable.payRate}</td>
                                                    <td headers="payRate_${jobCount.index}" class="td-content" data-title="Pay Rate">
                                                            <fmt:formatNumber value="${job.payRate}" pattern="#,##0.00"/>
                                                    </td>
                                                    <td id="overtimeRate_${jobCount.index}" class="td-title">${sessionScope.languageJSON.currentPayTable.overtimeRate}</td>
                                                    <td headers="overtimeRate_${jobCount.index}" class="td-content" data-title="Overtime Rate">
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
                                            <h2 class="table-top-title">
                                                    <b><span>${sessionScope.languageJSON.label.bankCardInfo}</span></b>
                                            </h2>
                                        <table class="table border-table responsive-table print-table">
                                            <thead>
                                                <tr>
                                                    <th id="bankCode">${sessionScope.languageJSON.currentPayTable.bankCode}</th>
                                                    <th id="bankName">${sessionScope.languageJSON.currentPayTable.bankName}</th>
                                                    <th id="accountType">${sessionScope.languageJSON.currentPayTable.accountType}</th>
                                                    <th id="acctNbr">${sessionScope.languageJSON.currentPayTable.acctNbr}</th>
                                                    <th id="depAmt">${sessionScope.languageJSON.currentPayTable.depAmt}</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                    <c:forEach items="${accounts[frequency]}" var="account" varStatus="counter">
                                                        <tr>
                                                            <td headers="bankCode" data-title="">
                                                                    ${account.bankCd}
                                                            </td>
                                                            <td headers="bankName" class="text-left" data-title="">
                                                                ${account.bankName}
                                                            </td>
                                                            <td headers="accountType" class="text-left" data-title="">
                                                                    ${account.bankAccountType} - ${account.bankAccountTypeDescription}
                                                            </td>
                                                            <td headers="acctNbr" class="text-left" data-title="">
                                                                    ${account.bankAccountNumber}
                                                            </td>
                                                            <td headers="depAmt" data-title="">
                                                                    <fmt:formatNumber value="${account.bankAccountAmount.amount}" pattern="#,##0.00"/> ${account.bankAccountAmount.currency}
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                            </tbody>
                                        </table>
                                    </c:if>

                                    <c:if test="${fn:length(stipends[frequency]) > 0}">
                                            <h2 class="table-top-title">
                                                    <b><span>${sessionScope.languageJSON.label.stipendInfo}</span> </b>
                                            </h2>
                                        <table class="table border-table responsive-table print-table">
                                            <thead>
                                                <tr>
                                                    <th id="extraDuty" >${sessionScope.languageJSON.currentPayTable.extraDuty}</th>
                                                    <th id="defaultAccountType" class="text-center">${sessionScope.languageJSON.currentPayTable.type}</th>
                                                    <th id="extraDutyAmt" class="text-right">${sessionScope.languageJSON.currentPayTable.amount}</th>
                                                    <th id="remainAmt" class="text-right">${sessionScope.languageJSON.currentPayTable.remainAmount}</th>
                                                    <th id="remainPayments" class="text-right">${sessionScope.languageJSON.currentPayTable.remainPayments}</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                    <c:forEach items="${stipends[frequency]}" var="stipend" varStatus="counter">
                                                        <tr>
                                                            <td headers="extraDuty" data-title="">
                                                                    ${stipend.extraDutyDescription}
                                                            </td>
                                                            <td headers="defaultAccountType" class="text-center" data-title="">
                                                                    ${stipend.defaultAccountType}
                                                            </td>
                                                            <td headers="extraDutyAmt" data-title="">
                                                                    <fmt:formatNumber value="${stipend.extraDutyAmt}" pattern="#,##0.00"/>
                                                            </td>
                                                            <td headers="remainAmt" data-title="">
                                                                    <fmt:formatNumber value="${stipend.remainAmt}" pattern="#,##0.00"/>
                                                            </td>
                                                            <td headers="remainPayments" data-title="">
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
                    </div>
                </section>
            </main>
        </div>
        <%@ include file="../commons/footer.jsp"%>
    </body>
    <script src="/<%=request.getContextPath().split("/")[1]%>/js/viewJs/inquiry/currentPayInformation.js"></script>
</html>
