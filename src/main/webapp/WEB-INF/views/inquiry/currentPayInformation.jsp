<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> <%@ taglib
uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> <%@ page
language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html> 
<html lang="en">
    <head>
        <title >${sessionScope.languageJSON.headTitle.currentPayInfo}</title>
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
                        <h1 class="pageTitle" >${sessionScope.languageJSON.title.currentPayInfo}</h1>
                        <div class="pull-right right-btn">
			                    <form class="no-print" action="exportPDF" method="POST">
									<input type="hidden" name="${_csrf.parameterName}"
										value="${_csrf.token}" />
									<button type="submit" role="button" class="btn btn-primary download-pdf" aria-label="${sessionScope.languageJSON.label.exportPDF}"
										><i class="fa fa-file-pdf-o"></i></button>
                                </form>
								<%--  <button class="btn btn-primary download-pdf" onclick="downloadPDF()" title="" aria-label="${sessionScope.languageJSON.label.exportPDF}">
                                <i class="fa fa-file-pdf-o"></i> --%>
                            </button>
                                <button
                                class="btn btn-primary"
                                onclick="doPrint()"
                               
                            >
                            ${sessionScope.languageJSON.label.print}
                            </button>
                            </div>
                    </div>
                    <div class="toPrint content-white EMP-detail">
                        <div class="exportPDFBox">
                                <div class="print-block print-title">
                                        <div style="text-align:center;margin-bottom:10px;">
                                                ${sessionScope.district.name}<br />${sessionScope.district.address}<br />
                                                ${sessionScope.district.city},
                                                ${sessionScope.district.state} ${sessionScope.district.zip}-${sessionScope.district.zip4}
                                            </div>
                                        <div style="text-align:center;">
                                                <span >${sessionScope.languageJSON.label.currentPayInfo}</span><br />
                                            <div id="date-now"></div>
                                        </div>
                                    </div>
                        </div>
                        
                        <c:if test="${not empty sessionScope.options.messageCurrentPayInformation}">
                            <p class="topMsg error-hint" role="alert">${sessionScope.options.messageCurrentPayInformation}</p>
                        </c:if>
                        <div class="needToClone">
                        	<input type="hidden" id="isPrintPDF" value="${isPrintPDF}" />
                        	<input type="hidden" id="language" value="${language}" />
                            <h2 class="table-top-title">
                                <b >${sessionScope.languageJSON.label.employeeInformation}</b>
                            </h2>
                            <table
                                class="table border-table responsive-table no-thead print-table noNumTable"
                            >
                                <tbody>
                                    <tr>
                                        <th id="userDetailName" class="td-title" ><b>${sessionScope.languageJSON.currentPayTable.name}</b></th>
                                        <td
                                            headers="userDetailName" class="td-content"
                                            colspan="3"
                                        >
                                        ${sessionScope.userDetail.nameF} ${sessionScope.userDetail.nameM} ${sessionScope.userDetail.nameL}
                                        </td>
                                    </tr>
                                    <tr>
                                        <th id="userDetailAddress" class="td-title"  rowspan="2"><b>${sessionScope.languageJSON.currentPayTable.address}</b></th>
                                        <td headers="userDetailAddress" class="td-content" data-title="${sessionScope.languageJSON.currentPayTable.address}" rowspan="2">
                                            ${sessionScope.userDetail.addrNbr} ${sessionScope.userDetail.addrStr}<br/>
                                            ${sessionScope.userDetail.addrCity},
                                            ${sessionScope.userDetail.addrSt} ${sessionScope.userDetail.addrZip}
                                        </td>
                                        <th id="employeeId" class="td-title" ><b>${sessionScope.languageJSON.currentPayTable.employeeId}</b></th>
                                        <td
                                            headers="employeeId" class="td-content"
                                            data-title="${sessionScope.languageJSON.currentPayTable.employeeId}"
                                        >
                                        ${sessionScope.userDetail.empNbr}
                                        </td>
                                    </tr>
                                    <tr>
                                        
                                        <th id="dateOfBirth" class="td-title" >
                                            <b>${sessionScope.languageJSON.currentPayTable.dateOfBirth}</b>
                                        </th>
                                        <td
                                            headers="dateOfBirth" class="td-content"
                                            data-title="${sessionScope.languageJSON.currentPayTable.dateOfBirth}"
                                        >
                                        ${sessionScope.userDetail.dob}
                                        </td>
                                    </tr>
                                    <tr>
                                        <th id="phoneNumber" class="td-title" >
                                            <b>${sessionScope.languageJSON.currentPayTable.phoneNumber}</b>
                                        </th>
                                        <td
                                            headers="phoneNumber" class="td-content"
                                            data-title="${sessionScope.languageJSON.currentPayTable.phoneNumber}"
                                        >
                                        	${sessionScope.userDetail.phoneArea}-${fn:substring(sessionScope.userDetail.phoneNbr,0,3)}-${fn:substring(sessionScope.userDetail.phoneNbr,3,fn:length(sessionScope.userDetail.phoneNbr))}
                                        </td>
                                        <th id="gender" class="td-title" ><b>${sessionScope.languageJSON.currentPayTable.gender}</b></th>
                                        <td headers="gender" class="td-content" data-title="Gender">
                                            <c:if test="${sessionScope.userDetail.sex =='F'}">
                                                <span >${sessionScope.languageJSON.label.female}</span>
                                            </c:if>
                                            <c:if test="${sessionScope.userDetail.sex =='M'}">
                                                    <span >${sessionScope.languageJSON.label.male}</span>
                                            </c:if>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th id="degree" class="td-title" ><b>${sessionScope.languageJSON.currentPayTable.degree}</b></th>
                                        <td
                                            headers="degree" class="td-content"
                                            data-title="${sessionScope.languageJSON.currentPayTable.degree}"
                                            colspan="3"
                                        >
                                            ${employeeInfo.highDegreeDescription}
                                        </td>
                                    </tr>
                                    <tr>
                                        <th id="professionalYearsExp" class="td-title" >
                                            <b>${sessionScope.languageJSON.currentPayTable.professionalYearsExp}</b>
                                        </th>
                                        <td
                                            headers="professionalYearsExp" class="td-content"
                                            data-title="${sessionScope.languageJSON.currentPayTable.professionalYearsExp}"
                                        >
                                        ${employeeInfo.yrsProExper}
                                        </td>
                                        <th id="professionalDistrictExp" class="td-title" >
                                            <b>${sessionScope.languageJSON.currentPayTable.professionalDistrictExp}</b>
                                        </th>
                                        <td
                                            headers="professionalDistrictExp" class="td-content"
                                            data-title="${sessionScope.languageJSON.currentPayTable.professionalDistrictExp}"
                                        >
                                        ${employeeInfo.yrsProExperLoc}
                                        </td>
                                    </tr>
                                    <tr>
                                        <th id="nonProfessionalYearExp" class="td-title" >
                                            <b>${sessionScope.languageJSON.currentPayTable.nonProfessionalYearExp}</b>
                                        </th>
                                        <td
                                            headers="nonProfessionalYearExp" class="td-content"
                                            data-title="${sessionScope.languageJSON.currentPayTable.nonProfessionalYearExp}"
                                        >
                                        ${employeeInfo.yrsExpDist}
                                        </td>
                                        <th id="noneProfessionalDistrictExp" class="td-title" >
                                            <b>${sessionScope.languageJSON.currentPayTable.noneProfessionalDistrictExp}</b>
                                        </th>
                                        <td
                                            headers="noneProfessionalDistrictExp" class="td-content"
                                            data-title="${sessionScope.languageJSON.currentPayTable.noneProfessionalDistrictExp}"
                                        >
                                        ${employeeInfo.yrsExpDistLoc}
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                            <c:if test="${fn:length(frequencies) == 0}">
                                <p >${sessionScope.languageJSON.label.noCrrentPay}</p>
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
                                                <th id="martialStatus_${count.index}" class="td-title" >
                                                    <b>${sessionScope.languageJSON.currentPayTable.martialStatus}</b>
                                                </th>
                                                <td
                                                    headers="martialStatus_${count.index}" class="td-content"
                                                    data-title="${sessionScope.languageJSON.currentPayTable.martialStatus}"
                                                    
                                                >
                                                    <c:if test="${payInfos[frequency].maritalStatTax =='M'}">
                                                            <span>${sessionScope.languageJSON.label.married}</span>
                                                    </c:if>
                                                    <c:if test="${payInfos[frequency].maritalStatTax =='S'}">
                                                            <span>${sessionScope.languageJSON.label.single}</span>
                                                    </c:if>
                                                </td>
                                                <th id="numOfExemptions_${count.index}" class="td-title" >
                                                    <b>${sessionScope.languageJSON.currentPayTable.numOfExemptions}</b>
                                                </th>
                                                <td
                                                    headers="numOfExemptions_${count.index}" class="td-content"
                                                    data-title="${sessionScope.languageJSON.currentPayTable.numOfExemptions}"
                                                >
                                                    ${payInfos[frequency].nbrTaxExempts}
                                                </td>
                                                <th id="payCampus_${count.index}" class="td-title" ><b>${sessionScope.languageJSON.currentPayTable.payCampus}</b></th>
                                                <td
                                                    headers="payCampus_${count.index}" class="td-content"
                                                    data-title="${sessionScope.languageJSON.currentPayTable.payCampus}"
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
                                                    <th id="jobCdDescription_${jobCount.index}" class="td-title">${sessionScope.languageJSON.currentPayTable.title}</th>
                                                    <td headers="jobCdDescription_${jobCount.index}" class="td-content text-left" data-title="${sessionScope.languageJSON.currentPayTable.title}">
                                                        <b>
                                                                ${job.jobCdDescription}
                                                        </b>
                                                    </td>
                                                    <th id="annualPayments_${jobCount.index}" class="td-title">${sessionScope.languageJSON.currentPayTable.annualPayments}</th>
                                                    <td headers="annualPayments_${jobCount.index}" class="td-content" data-title="${sessionScope.languageJSON.currentPayTable.annualPayments}">
                                                            ${job.nbrAnnualPymts}
                                                    </td>
                                                    <th id="regularHour_${jobCount.index}" class="td-title">${sessionScope.languageJSON.currentPayTable.regularHour}</th>
                                                    <td headers="regularHour_${jobCount.index}" class="td-content" data-title="${sessionScope.languageJSON.currentPayTable.regularHour}">
                                                            <fmt:formatNumber value="${job.regHrsWrk}" pattern="#,##0.00"/>
                                                    </td>
                                                    <th id="remailPayments_${jobCount.index}" class="td-title">${sessionScope.languageJSON.currentPayTable.remailPayments}</th>
                                                    <td headers="remailPayments_${jobCount.index}" class="td-content" data-title="${sessionScope.languageJSON.currentPayTable.remailPayments}">
                                                            ${job.nbrRemainPymts}
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <th id="annualSalary_${jobCount.index}" class="td-title">${sessionScope.languageJSON.currentPayTable.annualSalary}</th>
                                                    <td headers="annualSalary_${jobCount.index}" class="td-content" data-title="${sessionScope.languageJSON.currentPayTable.annualSalary}">
                                                            <fmt:formatNumber value="${job.contrAmt}" pattern="#,##0.00"/>
                                                    </td>
                                                    <th id="dailyRate_${jobCount.index}" class="td-title">${sessionScope.languageJSON.currentPayTable.dailyRate}</th>
                                                    <td headers="dailyRate_${jobCount.index}" class="td-content" data-title="${sessionScope.languageJSON.currentPayTable.dailyRate}">
                                                            <fmt:formatNumber value="${job.dlyRateofPay}" pattern="#,##0.00"/>
                                                    </td>
                                                    <th id="payRate_${jobCount.index}" class="td-title">${sessionScope.languageJSON.currentPayTable.payRate}</th>
                                                    <td headers="payRate_${jobCount.index}" class="td-content" data-title="${sessionScope.languageJSON.currentPayTable.payRate}">
                                                            <fmt:formatNumber value="${job.payRate}" pattern="#,##0.00"/>
                                                    </td>
                                                    <th id="overtimeRate_${jobCount.index}" class="td-title">${sessionScope.languageJSON.currentPayTable.overtimeRate}</th>
                                                    <td headers="overtimeRate_${jobCount.index}" class="td-content" data-title="${sessionScope.languageJSON.currentPayTable.overtimeRate}">
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
                                                            <td headers="bankCode" data-title="${sessionScope.languageJSON.currentPayTable.bankCode}">
                                                                    ${account.bankCd}
                                                            </td>
                                                            <td headers="bankName" class="text-left" data-title="${sessionScope.languageJSON.currentPayTable.bankName}">
                                                                ${account.bankName}
                                                            </td>
                                                            <td headers="accountType" class="text-left" data-title="${sessionScope.languageJSON.currentPayTable.accountType}">
                                                                    ${account.bankAccountType} - ${account.bankAccountTypeDescription}
                                                            </td>
                                                            <td headers="acctNbr" class="text-left" data-title="${sessionScope.languageJSON.currentPayTable.acctNbr}">
                                                                    ${account.bankAccountNumber}
                                                            </td>
                                                            <td headers="depAmt" data-title="${sessionScope.languageJSON.currentPayTable.depAmt}">
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
                                                            <td headers="extraDuty" data-title="${sessionScope.languageJSON.currentPayTable.extraDuty}">
                                                                    ${stipend.extraDutyDescription}
                                                            </td>
                                                            <td headers="defaultAccountType" class="text-center" data-title="${sessionScope.languageJSON.currentPayTable.type}">
                                                                    ${stipend.defaultAccountType}
                                                            </td>
                                                            <td headers="extraDutyAmt" data-title="${sessionScope.languageJSON.currentPayTable.amount}">
                                                                    <fmt:formatNumber value="${stipend.extraDutyAmt}" pattern="#,##0.00"/>
                                                            </td>
                                                            <td headers="remainAmt" data-title="${sessionScope.languageJSON.currentPayTable.remainAmount}">
                                                                    <fmt:formatNumber value="${stipend.remainAmt}" pattern="#,##0.00"/>
                                                            </td>
                                                            <td headers="remainPayments" data-title="${sessionScope.languageJSON.currentPayTable.remainPayments}">
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
