<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> <%@ taglib
uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> <%@ page
language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html> 
<html lang="en">
    <head>
        <title >${sessionScope.languageJSON.headTitle.w2Info}</title>
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <%@ include file="../commons/header.jsp"%>
    </head>
    <body class="hold-transition sidebar-mini">
        <div class="wrapper">
            <%@ include file="../commons/bar.jsp"%>

            <main class="content-wrapper" id="content" tabindex="-1">
                    <section class="content">
                            <div class="clearfix no-print section-title">
                                <h1 class="pageTitle" >${sessionScope.languageJSON.title.w2Info}</h1>
                                <div class="right-btn pull-right">
                                    <c:if test="${sessionScope.options.enableElecConsntW2}">
                                        <button class="btn btn-primary" data-toggle="modal" data-target="#electronicConsent" >${sessionScope.languageJSON.label.w2Consent}</button>
                                    </c:if>
                                    <c:if test="${sessionScope.options.enableElecConsntW2 == false}">
                                            <button class="btn btn-primary disabled"  disabled>${sessionScope.languageJSON.label.w2Consent}</button>
                                    </c:if>
                                    <c:if test="${selectedYear >= '2009' && selectedYear <= sessionScope.options.w2Latest}">
                                        <form class="no-print" action="exportPDF" method="POST">
											<input type="hidden" name="${_csrf.parameterName}"
												value="${_csrf.token}" />
											<input type="hidden" name="year"
												value="${selectedYear}" />
											<button type="submit" role="button" class="btn btn-primary" 
											aria-label="${sessionScope.languageJSON.label.print}">${sessionScope.languageJSON.label.print}</button>
										
                                        </form>
			                          <%--   <form class="no-print" action="printPDF" method="POST" target="printIframe">
											<input type="hidden" name="${_csrf.parameterName}"
												value="${_csrf.token}" />
											<input type="hidden" name="year"
												value="${selectedYear}" />
				                            <button type="submit" role="button" class="btn btn-primary">
				                            	${sessionScope.languageJSON.label.print}
				                            </button>
			                            </form>
			
			                            <iframe style="display:none" name="printIframe" onload="load()" id="printIframe"></iframe> --%>
                                    </c:if>
                                </div>
                            </div>
                       
                            <div class="toPrint content-white EMP-detail w-2">
                                    <c:if test="${not empty sessionScope.options.messageW2}">
                                            <p class="topMsg error-hint" role="alert">${sessionScope.options.messageW2}</p>
                                        </c:if>
                                <div class="exportPDFBox">

                                </div>

                                <c:if test="${isSuccess && isUpdate}">
                                    <div id="updateMsg" class="no-print">
                                        <span class="error-hint" role="alert" aria-atomic="true">
                                            <b >${sessionScope.languageJSON.validator.updateWasSuccessful}</b>
                                        </span>
                                        <br/>
                                        <br/>
                                    </div>
                                </c:if>
                                <form
                                    class="no-print searchForm"
                                    action="w2InformationByYear"
                                    id="selectCalendar"
                                    method="POST"
                                >
                                	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                    <div class="form-group in-line paddingSide-0">
                                        <label class="form-title"  for="year"  >${sessionScope.languageJSON.label.pleaseSelectYear}</label>
                                        <select class="form-control" name="year" id="year" onchange="submitCalendarForm()">
                                            <c:forEach var="year" items="${years}" varStatus="years">
                                                <option value="${year}" <c:if test="${year == selectedYear }">selected</c:if>>${year}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </form>
                                <div class="max-900 needToClone">
                                    <table
                                        class="table border-table responsive-table no-thead print-table no-print"
                                    >
                                        <tbody>
                                            <tr>
                                                <th id="taxableGrossPay" class="td-title" >${sessionScope.constantJSON.w2InformationTable.taxableGrossPay}</th>
                                                <td
                                                    class="td-content" data-title="${sessionScope.constantJSON.w2InformationTable.taxableGrossPay}"  headers="taxableGrossPay" 
                                                    
                                                >
                                                <fmt:formatNumber value="${w2Info.whGross}" pattern="#,##0.00"/>
                                                </td>
                                                <th id="withholdingTax" class="td-title" >${sessionScope.constantJSON.w2InformationTable.withholdingTax}</th>
                                                <td
                                                    class="td-content" data-title="${sessionScope.constantJSON.w2InformationTable.withholdingTax}"  headers="withholdingTax" 
                                                    
                                                >
                                                <fmt:formatNumber value="${w2Info.whTax}" pattern="#,##0.00"/>
                                                </td>
                                                <th id="pension" class="td-title" >${sessionScope.constantJSON.w2InformationTable.pension}</th>
                                                <td class="td-content" data-title="${sessionScope.constantJSON.w2InformationTable.pension}"  headers="pension"  >
                                                        ${w2Info.pension}
                                                </td>
                                            </tr>
                                            <tr>
                                                <th id="FICAGross" class="td-title" >${sessionScope.constantJSON.w2InformationTable.FICAGross}</th>
                                                <td
                                                    class="td-content" data-title="${sessionScope.constantJSON.w2InformationTable.FICAGross}"  headers="FICAGross" 
                                                    
                                                >
                                                <fmt:formatNumber value="${w2Info.ficaGross}" pattern="#,##0.00"/>
                                                </td>
                                                <th id="FICATax" class="td-title" >${sessionScope.constantJSON.w2InformationTable.FICATax}</th>
                                                <td
                                                    class="td-content" data-title="${sessionScope.constantJSON.w2InformationTable.FICATax}"  headers="FICATax" 
                                                    
                                                >
                                                <fmt:formatNumber value="${w2Info.ficaTax}" pattern="#,##0.00"/>
                                                </td>
                                                <td colspan="2">

                                                </td>
                                            </tr>
                                            <tr>
                                                <th id="medicareGross" class="td-title" >${sessionScope.constantJSON.w2InformationTable.medicareGross}</th>
                                                <td
                                                    class="td-content" data-title="${sessionScope.constantJSON.w2InformationTable.medicareGross}"  headers="medicareGross" 
                                                    
                                                >
                                                <fmt:formatNumber value="${w2Info.medGross}" pattern="#,##0.00"/>
                                                </td>
                                                <th id="medicareTax" class="td-title" >${sessionScope.constantJSON.w2InformationTable.medicareTax}</th>
                                                <td
                                                    class="td-content" data-title="${sessionScope.constantJSON.w2InformationTable.medicareTax}"  headers="medicareTax" 
                                                    
                                                    
                                                >
                                                <fmt:formatNumber value="${w2Info.medTax}" pattern="#,##0.00"/>
                                                </td>
                                                <td colspan="2">

                                                </td>
                                            </tr>
                                            <tr>
                                                <th id="earnedIncomeCredit" class="td-title" >
                                                        ${sessionScope.constantJSON.w2InformationTable.earnedIncomeCredit}
                                                </th>
                                                <td
                                                    class="td-content" data-title="${sessionScope.constantJSON.w2InformationTable.earnedIncomeCredit}"  headers="earnedIncomeCredit" 
                                                    
                                                >
                                                <fmt:formatNumber value="${w2Info.eicAmt}" pattern="#,##0.00"/>
                                                </td>
                                                <th id="dependentCare" class="td-title" >${sessionScope.constantJSON.w2InformationTable.dependentCare}</th>
                                                <td
                                                    class="td-content" data-title="${sessionScope.constantJSON.w2InformationTable.dependentCare}"  headers="dependentCare" 
                                                    
                                                >
                                                <fmt:formatNumber value="${w2Info.dependCare}" pattern="#,##0.00"/>
                                                </td>
                                                <td colspan="2"></td>
                                            </tr>
                                            <tr>
                                                <td colspan="6">&nbsp;</td>
                                            </tr>
                                            <tr>
                                                <th id="annuityDeduction" class="td-title" >${sessionScope.constantJSON.w2InformationTable.annuityDeduction}</th>
                                                <td
                                                    class="td-content" data-title="${sessionScope.constantJSON.w2InformationTable.annuityDeduction}"  headers="annuityDeduction" 
                                                    
                                                >
                                                <fmt:formatNumber value="${w2Info.annuityDed}" pattern="#,##0.00"/>
                                                </td>
                                                <th id="457withdraw" class="td-title" >${sessionScope.constantJSON.w2InformationTable.withdraw457}</th>
                                                <td
                                                    class="td-content" data-title="${sessionScope.constantJSON.w2InformationTable.withdraw457}"  headers="457withdraw" 
                                                    
                                                >
                                                <fmt:formatNumber value="${w2Info.withdraw457}" pattern="#,##0.00"/>
                                                </td>
                                                <th id="457Annuities" class="td-title" >
                                                        ${sessionScope.constantJSON.w2InformationTable.Annuities457}
                                                </th>
                                                <td
                                                    class="td-content" data-title="${sessionScope.constantJSON.w2InformationTable.Annuities457}"  headers="457Annuities" 
                                                    
                                                >
                                                <fmt:formatNumber value="${w2Info.emp457Contrib + w2Info.emplrContrib457}" pattern="#,##0.00"/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <th id="cafeteria125" class="td-title" >${sessionScope.constantJSON.w2InformationTable.cafeteria125}</th>
                                                <td
                                                    class="td-content" data-title="${sessionScope.constantJSON.w2InformationTable.cafeteria125}"  headers="cafeteria125" 
                                                >
                                                <fmt:formatNumber value="${w2Info.cafeAmt}" pattern="#,##0.00"/>
                                                </td>
                                                <th id="roth403BAfterTax" class="td-title" >
                                                        ${sessionScope.constantJSON.w2InformationTable.roth403BAfterTax}
                                                </th>
                                                <td
                                                    class="td-content" data-title="${sessionScope.constantJSON.w2InformationTable.roth403BAfterTax}"  headers="roth403BAfterTax" 
                                                >
                                                <fmt:formatNumber value="${w2Info.annuityRoth}" pattern="#,##0.00"/>
                                                </td>
                                                <td colspan="2"></td>
                                            </tr>
                                            <tr>
                                                <td colspan="6">&nbsp;</td>
                                            </tr>
                                            <tr>
                                                <th id="nonTrsBusinessExpense" class="td-title" >
                                                        ${sessionScope.constantJSON.w2InformationTable.nonTrsBusinessExpense}
                                                </th>
                                                <td
                                                    class="td-content" data-title="${sessionScope.constantJSON.w2InformationTable.nonTrsBusinessExpense}"  headers="nonTrsBusinessExpense" 
                                                >
                                                <fmt:formatNumber value="${w2Info.nontrsNontaxBusAllow}" pattern="#,##0.00"/>
                                                </td>
                                                <th id="taxableAllowance" class="td-title" >${sessionScope.constantJSON.w2InformationTable.taxableAllowance}</th>
                                                <td
                                                    class="td-content" data-title="${sessionScope.constantJSON.w2InformationTable.taxableAllowance}"  headers="taxableAllowance" 
                                                >
                                                <fmt:formatNumber value="${w2Info.nontrsBusAllow}" pattern="#,##0.00"/>
                                                </td>
                                                <th id="empBusinessExpense" class="td-title" >
                                                        ${sessionScope.constantJSON.w2InformationTable.empBusinessExpense}
                                                </th>
                                                <td
                                                    class="td-content" data-title="${sessionScope.constantJSON.w2InformationTable.empBusinessExpense}"  headers="empBusinessExpense" 
                                                    
                                                >
                                                <fmt:formatNumber value="${w2Info.empBusinessExpense}" pattern="#,##0.00"/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <th id="movingExpenseReimbursement" class="td-title" >
                                                        ${sessionScope.constantJSON.w2InformationTable.movingExpenseReimbursement}
                                                </th>
                                                <td
                                                    class="td-content" data-title="${sessionScope.constantJSON.w2InformationTable.movingExpenseReimbursement}"  headers="movingExpenseReimbursement" 
                                                >
                                                <fmt:formatNumber value="${w2Info.movingExpReimbr}" pattern="#,##0.00"/>
                                                </td>
                                                <c:choose>
                                                    <c:when test="${selectedYear >= '2012'}">
                                                        <th id="empSponsoredHealthCoverage" class="td-title" >
                                                                ${sessionScope.constantJSON.w2InformationTable.empSponsoredHealthCoverage}
                                                        </th>
                                                        <td
                                                            class="td-content" data-title="${sessionScope.constantJSON.w2InformationTable.empSponsoredHealthCoverage}"  headers="empSponsoredHealthCoverage" 
                                                            
                                                        >
                                                        <fmt:formatNumber value="${w2Info.emplrPrvdHlthcare}" pattern="#,##0.00"/>
                                                        </td>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <td class="td-title"></td>
                                                        <td class="td-content"></td>
                                                    </c:otherwise>
                                                </c:choose>
                                                <c:choose>
                                                    <c:when test="${selectedYear >= '2016'}">
                                                        <th id="annuityRoth" class="td-title" >${sessionScope.constantJSON.w2InformationTable.annuityRoth}</th>
                                                        <td
                                                            class="td-content" data-title="${sessionScope.constantJSON.w2InformationTable.annuityRoth}"  headers="annuityRoth" 
                                                            
                                                        >
                                                        <fmt:formatNumber value="${w2Info.annuityRoth457b}" pattern="#,##0.00"/>
                                                        </td>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <td class="td-title"></td>
                                                        <td class="td-content"></td>
                                                    </c:otherwise>
                                                </c:choose>
                                            </tr>
                                            <tr>
                                                <td colspan="6">&nbsp;</td>
                                            </tr>
                                            <tr>
                                                <th id="TRSSalaryReduction" class="td-title" >
                                                        ${sessionScope.constantJSON.w2InformationTable.TRSSalaryReduction}
                                                </th>
                                                <td
                                                    class="td-content" data-title="${sessionScope.constantJSON.w2InformationTable.TRSSalaryReduction}"  headers="TRSSalaryReduction" 
                                                    
                                                >
                                                <fmt:formatNumber value="${w2Info.trsDeposit}" pattern="#,##0.00"/>
                                                </td>
                                                <td colspan="4"></td>
                                            </tr>
                                            <tr>
                                                <td colspan="6">&nbsp;</td>
                                            </tr>
                                            <tr>
                                                <th id="taxedLifeContribution" class="td-title" >
                                                        ${sessionScope.constantJSON.w2InformationTable.taxedLifeContribution}
                                                </th>
                                                <td
                                                    class="td-content" data-title="${sessionScope.constantJSON.w2InformationTable.taxedLifeContribution}"  headers="taxedLifeContribution" 
                                                    
                                                >
                                                <fmt:formatNumber value="${w2Info.taxEmplrLifeGrp}" pattern="#,##0.00"/>
                                                </td>
                                                <th id="healthInsuranceDeduction" class="td-title" >
                                                        ${sessionScope.constantJSON.w2InformationTable.healthInsuranceDeduction}
                                                </th>
                                                <td
                                                    class="td-content" data-title="${sessionScope.constantJSON.w2InformationTable.healthInsuranceDeduction}"  headers="healthInsuranceDeduction" 
                                                    
                                                >
                                                <fmt:formatNumber value="${w2Info.hlthInsDed}" pattern="#,##0.00"/>
                                                </td>
                                                <th id="taxableFringeBenefits" class="td-title" >
                                                    ${sessionScope.constantJSON.w2InformationTable.taxableFringeBenefits}
                                                </th>
                                                <td
                                                    class="td-content" data-title="${sessionScope.constantJSON.w2InformationTable.taxableFringeBenefits}"  headers="taxableFringeBenefits" 
                                                    
                                                >
                                                <fmt:formatNumber value="${w2Info.taxedBenefits}" pattern="#,##0.00"/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td colspan="6">&nbsp;</td>
                                            </tr>
                                            <tr>
                                                <th id="healthSavingsAccount" class="td-title" >
                                                        ${sessionScope.constantJSON.w2InformationTable.healthSavingsAccount}
                                                </th>
                                                <td
                                                    class="td-content" data-title="${sessionScope.constantJSON.w2InformationTable.healthSavingsAccount}"  headers="healthSavingsAccount" 
                                                    
                                                >
                                                <fmt:formatNumber value="${w2Info.hsaContrib}" pattern="#,##0.00"/>
                                                </td>
                                                <th id="nonTaxSickPay" class="td-title" >
                                                        ${sessionScope.constantJSON.w2InformationTable.nonTaxSickPay}
                                                </th>
                                                <td
                                                    class="td-content" data-title="${sessionScope.constantJSON.w2InformationTable.nonTaxSickPay}"  headers="nonTaxSickPay" 
                                                    
                                                >
                                                <fmt:formatNumber value="${w2Info.sickPayNontax}" pattern="#,##0.00"/>
                                                </td>
                                                <c:choose>
                                                    <c:when test="${selectedYear >= '2010'}">
                                                        <th id="hireExemptWages" class="td-title" >
                                                                ${sessionScope.constantJSON.w2InformationTable.hireExemptWages}
                                                        </th>
                                                        <td
                                                            class="td-content" data-title="${sessionScope.constantJSON.w2InformationTable.hireExemptWages}"  headers="hireExemptWages" 
                                                            
                                                        >
                                                        <fmt:formatNumber value="${w2Info.hireExemptWgs}" pattern="#,##0.00"/>
                                                        </td>
                                                    </c:when>
                                                    <c:otherwise>
                                                            <td class="td-title"></td>
                                                            <td class="td-content"></td>
                                                    </c:otherwise>
                                                </c:choose>
                                            </tr>
                                        </tbody>
                                    </table> 

                                    <c:forEach var="sick" items="${thirdPartyPay}"  varStatus="counter">
                                            <div class="groupTitle no-print">
                                                <span >${sessionScope.languageJSON.label.thirdPartySickPayW2Amounts}</span> - 
                                                ${sick.frequency.label}
                                            </div>
                                            <table class="table border-table responsive-table no-thead print-table no-print">
                                                <tr>
                                                    <th id="withholdingGross_${counter.index}" class="td-title" >
                                                            ${sessionScope.constantJSON.w2InformationTable.withholdingGross}
                                                    </th>
                                                    <td class="td-content" data-title="${sessionScope.constantJSON.w2InformationTable.withholdingGross}"  headers="withholdingGross_${counter.index}"  >
                                                            <fmt:formatNumber value="${sick.sickPayWhGross}" pattern="#,##0.00"/>
                                                    </td>
                                                    <th id="sickWithholdingTax_${counter.index}" class="td-title" >
                                                            ${sessionScope.constantJSON.w2InformationTable.withholdingTax}
                                                    </th>
                                                    <td class="td-content" data-title="${sessionScope.constantJSON.w2InformationTable.withholdingTax}"  headers="sickWithholdingTax_${counter.index}"  >
                                                            <fmt:formatNumber value="${sick.sickPayWhTax}" pattern="#,##0.00"/>
                                                    </td>
                                                    <td class="td-title"></td>
                                                    <td class="td-content"></td>
                                                </tr>
                                                <tr>
                                                    <th id="sickFICAGross_${counter.index}" class="td-title" >
                                                            ${sessionScope.constantJSON.w2InformationTable.FICAGross}
                                                    </th>
                                                    <td class="td-content" data-title="${sessionScope.constantJSON.w2InformationTable.FICAGross}"  headers="sickFICAGross_${counter.index}"  >
                                                            <fmt:formatNumber value="${sick.sickPayFicaGross}" pattern="#,##0.00"/>
                                                    </td>
                                                    <th id="sickFICATax_${counter.index}" class="td-title" >
                                                            ${sessionScope.constantJSON.w2InformationTable.FICATax}
                                                    </th>
                                                    <td class="td-content" data-title="${sessionScope.constantJSON.w2InformationTable.FICATax}"  headers="sickFICATax_${counter.index}"  >
                                                            <fmt:formatNumber value="${sick.sickPayFicaTax}" pattern="#,##0.00"/>
                                                    </td>
                                                    <td class="td-title"></td>
                                                    <td class="td-content"></td>
                                                </tr>
                                                <tr>
                                                    <th id="sickMedicareGross_${counter.index}" class="td-title" >
                                                            ${sessionScope.constantJSON.w2InformationTable.medicareGross}
                                                    </th>
                                                    <td class="td-content" data-title="${sessionScope.constantJSON.w2InformationTable.medicareGross}"  headers="sickMedicareGross_${counter.index}"  >
                                                            <fmt:formatNumber value="${sick.sickPayMedGross}" pattern="#,##0.00"/>
                                                    </td>
                                                    <th id="sickMedicareTax_${counter.index}" class="td-title" >
                                                            ${sessionScope.constantJSON.w2InformationTable.medicareTax}
                                                    </th>
                                                    <td class="td-content" data-title="${sessionScope.constantJSON.w2InformationTable.medicareTax}"  headers="sickMedicareTax_${counter.index}">
                                                            <fmt:formatNumber value="${sick.sickPayMedTax}" pattern="#,##0.00"/>
                                                    </td>
                                                    <td  class="td-title"></td>
                                                    <td class="td-content"></td>
                                                </tr>
                                                <tr>
                                                    <th id="nontaxablePay_${counter.index}" class="td-title" >
                                                            ${sessionScope.constantJSON.w2InformationTable.nontaxablePay}
                                                    </th>
                                                    <td class="td-content" data-title="${sessionScope.constantJSON.w2InformationTable.nontaxablePay}"  headers="nontaxablePay_${counter.index}">
                                                            <fmt:formatNumber value="${sick.sickNontax}" pattern="#,##0.00"/>
                                                    </td>
                                                    <td class="td-title"></td>
                                                    <td class="td-content" ></td>
                                                    <td class="td-title"></td>
                                                    <td class="td-content"></td>
                                                </tr>
                                            </table>
                                    </c:forEach>

                                    
                                    <table class="table border-table mb-5 print-block-table noNumTable pdfPage">
                                        <tr>
                                            <td class="header" colspan="3">
                                                <span >${sessionScope.constantJSON.w2InformationTable.formW2WageAndTaxStatement}</span>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <div class="boxtitle" >
                                                        ${sessionScope.constantJSON.w2InformationTable.aEmployeeSocial}
                                                </div>
                                                <div class="boxvalue">
                                                    ${w2Print.ssn}
                                                </div>
                                                <div class="boxtitle">
                                                    &nbsp;
                                                </div>
                                            </td>
                                            <td class="doubleborder">
                                                <div class="boxtitle" >
                                                        ${sessionScope.constantJSON.w2InformationTable.WagesTipsOtherCompensation}
                                                </div>
                                                <div class="boxvalue">
                                                    ${w2Print.tgross}
                                                </div>
                                                <div class="boxtitle">
                                                    &nbsp;
                                                </div>
                                            </td>
                                            <td class="doubleborder">
                                                <div class="boxtitle" >
                                                        ${sessionScope.constantJSON.w2InformationTable.FederalIncomeTaxWithheld}
                                                </div>
                                                <div class="boxvalue">
                                                    ${w2Print.whold}
                                                </div>
                                                <div class="boxtitle">
                                                    &nbsp;
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <div class="boxtitle" >
                                                        ${sessionScope.constantJSON.w2InformationTable.bEIN}
                                                </div>
                                                <div class="boxvalue">
                                                    ${w2Print.ein}
                                                </div>
                                                <div class="boxtitle">
                                                    &nbsp;
                                                </div>
                                            </td>
                                            <td>
                                                <div class="boxtitle" >
                                                        ${sessionScope.constantJSON.w2InformationTable.SocialSecurityWages}
                                                </div>
                                                <div class="boxvalue">
                                                    ${w2Print.fgross}
                                                </div>
                                                <div class="boxtitle">
                                                    &nbsp;
                                                </div>
                                            </td>
                                            <td>
                                                <div class="boxtitle" >
                                                        ${sessionScope.constantJSON.w2InformationTable.SocialSecurityTaxWithheld}
                                                </div>
                                                <div class="boxvalue">
                                                    ${w2Print.ftax}
                                                </div>
                                                <div class="boxtitle">
                                                    &nbsp;
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td rowspan="2">
                                                <div class="boxtitle" >
                                                        ${sessionScope.constantJSON.w2InformationTable.cEmployerNameAddressZip}
                                                </div>
                                                <div class="boxvalue">
                                                    ${w2Print.ename}
                                                </div>
                                                <div class="boxvalue">
                                                    ${w2Print.eaddress}
                                                </div>
                                                <div class="boxvalue">
                                                    ${w2Print.ecityst}
                                                </div>
                                            </td>
                                            <td>
                                                <div class="boxtitle" >
                                                        ${sessionScope.constantJSON.w2InformationTable.MedicareWagesAndTips}
                                                </div>
                                                <div class="boxvalue">
                                                    ${w2Print.mgross}
                                                </div>
                                                <div class="boxtitle">
                                                    &nbsp;
                                                </div>
                                            </td>
                                            <td>
                                                <div class="boxtitle" >
                                                        ${sessionScope.constantJSON.w2InformationTable.MedicareTaxWithheld}
                                                </div>
                                                <div class="boxvalue">
                                                    ${w2Print.mtax}
                                                </div>
                                                <div class="boxtitle">
                                                    &nbsp;
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="doubleborder">
                                                <div class="boxtitle" >
                                                        ${sessionScope.constantJSON.w2InformationTable.AdvancedEICpayment}
                                                </div>
                                                <div class="boxvalue">
                                                    ${w2Print.eic}
                                                </div>
                                                <div class="boxtitle">
                                                    &nbsp;
                                                </div>
                                            </td>
                                            <td class="doubleborder">
                                                <div class="boxtitle" >
                                                        ${sessionScope.constantJSON.w2InformationTable.dependentCareBenefits}
                                                </div>
                                                <div class="boxvalue">
                                                    ${w2Print.dcare}
                                                </div>
                                                <div class="boxtitle">
                                                    &nbsp;
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td rowspan="2">
                                                <div class="boxtitle" >
                                                        ${sessionScope.constantJSON.w2InformationTable.eEmployeeName}
                                                </div>
                                                <div class="boxvalue">
                                                    ${w2Print.empname}
                                                </div>
                                                <div class="boxvalue">
                                                    ${w2Print.empaddress}
                                                </div>
                                                <div class="boxvalue">
                                                    ${w2Print.empcityst}
                                                </div>
                                            </td>
                                            <td>
                                                    <table class="table no-border-table">
                                                        <tr>
                                                            <td class="boxtitle" colspan="2">
                                                                <span >${sessionScope.constantJSON.w2InformationTable.seeInstrs12}</span>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td class="boxvalue">
                                                                ${w2Print.code1201}&nbsp;
                                                            </td>
                                                            <td class="boxvaluerj">
                                                                ${w2Print.amt1201}&nbsp;
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td class="boxvalue">
                                                                ${w2Print.code1202}&nbsp;
                                                            </td>
                                                            <td class="boxvaluerj">
                                                                ${w2Print.amt1202}&nbsp;
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td class="boxvalue">
                                                                ${w2Print.code1203}&nbsp;
                                                            </td>
                                                            <td class="boxvaluerj">
                                                                ${w2Print.amt1203}&nbsp;
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td class="boxvalue">
                                                                ${w2Print.code1204}&nbsp;
                                                            </td>
                                                            <td class="boxvaluerj">
                                                                ${w2Print.amt1204}&nbsp;
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td class="boxvalue">
                                                                ${w2Print.code1205}&nbsp;
                                                            </td>
                                                            <td class="boxvaluerj">
                                                                ${w2Print.amt1205}&nbsp;
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td class="boxvalue">
                                                                ${w2Print.code1206}&nbsp;
                                                            </td>
                                                            <td class="boxvaluerj">
                                                                ${w2Print.amt1206}&nbsp;
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td class="boxvalue">
                                                                ${w2Print.code1207}&nbsp;
                                                            </td>
                                                            <td class="boxvaluerj">
                                                                ${w2Print.amt1207}&nbsp;
                                                            </td>
                                                        </tr>
                                                    </table>
                                            </td>
                                            <td>
                                                    <table class="table no-border-table">
                                                        <tr>
                                                            <td class="boxtitle" colspan="2">
                                                                <span >${sessionScope.constantJSON.w2InformationTable.seeInstrs14}</span>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td class="boxvalue">
                                                                ${w2Print.code1401}&nbsp;
                                                            </td>
                                                            <td class="boxvaluerj">
                                                                ${w2Print.amt1401}&nbsp;
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td class="boxvalue">
                                                                ${w2Print.code1402}&nbsp;
                                                            </td>
                                                            <td class="boxvaluerj">
                                                                ${w2Print.amt1402}&nbsp;
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td class="boxvalue">
                                                                ${w2Print.code1403}&nbsp;
                                                            </td>
                                                            <td class="boxvaluerj">
                                                                ${w2Print.amt1403}&nbsp;
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td class="boxvalue">
                                                                ${w2Print.code1404}&nbsp;
                                                            </td>
                                                            <td class="boxvaluerj">
                                                                ${w2Print.amt1404}&nbsp;
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td class="boxvalue">
                                                                ${w2Print.code1405}&nbsp;
                                                            </td>
                                                            <td class="boxvaluerj">
                                                                ${w2Print.amt1405}&nbsp;
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td class="boxvalue">
                                                                ${w2Print.code1406}&nbsp;
                                                            </td>
                                                            <td class="boxvaluerj">
                                                                ${w2Print.amt1406}&nbsp;
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td class="boxvalue">
                                                                ${w2Print.code1407}&nbsp;
                                                            </td>
                                                            <td class="boxvaluerj">
                                                                ${w2Print.amt1407}&nbsp;
                                                            </td>
                                                        </tr>
                                                    </table>
                                            </td>
                                        </tr>
            
                                        <tr>
                                                <td colspan="2">
                                                    <table class="table no-border-table w2Check-table">
                                                        <tr>
                                                            <td>
                                                                <div class="boxtitle">
                                                                    13
                                                                </div>
                                                            </td>
                                                            <td>
                                                                <div class="boxtitle" >
                                                                        ${sessionScope.constantJSON.w2InformationTable.statutoryEmployee}
                                                                </div>
                                                                <div class="boximage">
                                                                    <c:if test="${w2Print.statemp == 'checkedbox'}">
                                                                            <span class="print-check-disabled">
                                                                                    <i class="fa fa-times"></i>
                                                                                </span>
                                                                    </c:if>
                                                                    <c:if test="${w2Print.statemp=='uncheckedbox'}">
                                                                            <input class="checkBoxOld" type="checkbox" aria-label="${sessionScope.languageJSON.accessHint.statutoryEmployeeCheckbox}"  />
                                                                    </c:if>
                                                                </div>
                                                            </td>
                                                            <td>
                                                                <div class="boxtitle" >
                                                                        ${sessionScope.constantJSON.w2InformationTable.retirementPlan}
                                                                </div>
                                                                <div class="boximage">
                                                                    <c:if test="${w2Print.retplan == 'checkedbox'}">
                                                                            <span class="print-check-disabled">
                                                                                    <i class="fa fa-times"></i>
                                                                                </span>
                                                                    </c:if>
                                                                    <c:if test="${w2Print.retplan=='uncheckedbox'}">
                                                                            <input class="checkBoxOld" type="checkbox"  aria-label="${sessionScope.languageJSON.accessHint.retirementPlanCheckbox}" />
                                                                    </c:if>
                                                                </div>
                                                            </td>
                                                            <td>
                                                                <div class="boxtitle" >
                                                                        ${sessionScope.constantJSON.w2InformationTable.thirdPartySickPay}
                                                                </div>
                                                                <div class="boximage">
                                                                    <c:if test="${w2Print.thrdsick == 'checkedbox'}">
                                                                            <span class="print-check-disabled">
                                                                                    <i class="fa fa-times"></i>
                                                                                </span>
                                                                    </c:if>
                                                                    <c:if test="${w2Print.thrdsick=='uncheckedbox'}">
                                                                            <input class="checkBoxOld" type="checkbox" aria-label="${sessionScope.languageJSON.accessHint.thirdPartySickPayCheckbox}" />
                                                                    </c:if>
                                                                </div>
                                                            </td>
                                                        </tr>
                                                    </table>
                                                    <div class="boxtitle">
                                                        &nbsp;
                                                    </div>
                                                </td>
                                            </tr>
                                        <tr>
                                            <td colspan="3" class="no-border-td">
                                                <div class="footer-left pull-left">
                                                    <span >
                                                            ${sessionScope.constantJSON.w2InformationTable.copyB}
                                                    </span>
                                                </div>
                                                <div class="footer-right pull-right" >
                                                        ${sessionScope.constantJSON.w2InformationTable.departmentOfTheTreasury}
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="no-border-td" colspan="3"
                                                style="text-align: center">
                                                <h1 class="selectYearSpan">${selectedYear}</h1>
                                            </td>
                                        </tr>
                                    </table>
                                    
                                    <div class="PageNext"></div>
                                    <c:if test="${selectedYear >= '2009' && selectedYear <= sessionScope.options.w2Latest}">
                                        <jsp:include page="../report-w2/${selectedYear}-1.jsp"></jsp:include> 
                                    </c:if>
                                    
                                    <div class="PageNext"></div>
                                
                                    <table class="table border-table mb-5 print-block-table noNumTable  pdfPage">
                                            <tr>
                                                <td class="header" colspan="3">
                                                    <span >${sessionScope.constantJSON.w2InformationTable.formW2WageAndTaxStatement}</span>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <div class="boxtitle" >
                                                            ${sessionScope.constantJSON.w2InformationTable.aEmployeeSocial}
                                                    </div>
                                                    <div class="boxvalue">
                                                        ${w2Print.ssn}
                                                    </div>
                                                    <div class="boxtitle">
                                                        &nbsp;
                                                    </div>
                                                </td>
                                                <td class="doubleborder">
                                                    <div class="boxtitle" >
                                                            ${sessionScope.constantJSON.w2InformationTable.WagesTipsOtherCompensation}
                                                    </div>
                                                    <div class="boxvalue">
                                                        ${w2Print.tgross}
                                                    </div>
                                                    <div class="boxtitle">
                                                        &nbsp;
                                                    </div>
                                                </td>
                                                <td class="doubleborder">
                                                    <div class="boxtitle" >
                                                            ${sessionScope.constantJSON.w2InformationTable.FederalIncomeTaxWithheld}
                                                    </div>
                                                    <div class="boxvalue">
                                                        ${w2Print.whold}
                                                    </div>
                                                    <div class="boxtitle">
                                                        &nbsp;
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <div class="boxtitle" >
                                                            ${sessionScope.constantJSON.w2InformationTable.bEIN}
                                                    </div>
                                                    <div class="boxvalue">
                                                        ${w2Print.ein}
                                                    </div>
                                                    <div class="boxtitle">
                                                        &nbsp;
                                                    </div>
                                                </td>
                                                <td>
                                                    <div class="boxtitle" >
                                                            ${sessionScope.constantJSON.w2InformationTable.SocialSecurityWages}
                                                    </div>
                                                    <div class="boxvalue">
                                                        ${w2Print.fgross}
                                                    </div>
                                                    <div class="boxtitle">
                                                        &nbsp;
                                                    </div>
                                                </td>
                                                <td>
                                                    <div class="boxtitle" >
                                                            ${sessionScope.constantJSON.w2InformationTable.SocialSecurityTaxWithheld}
                                                    </div>
                                                    <div class="boxvalue">
                                                        ${w2Print.ftax}
                                                    </div>
                                                    <div class="boxtitle">
                                                        &nbsp;
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td rowspan="2">
                                                    <div class="boxtitle" >
                                                            ${sessionScope.constantJSON.w2InformationTable.cEmployerNameAddressZip}
                                                    </div>
                                                    <div class="boxvalue">
                                                        ${w2Print.ename}
                                                    </div>
                                                    <div class="boxvalue">
                                                        ${w2Print.eaddress}
                                                    </div>
                                                    <div class="boxvalue">
                                                        ${w2Print.ecityst}
                                                    </div>
                                                </td>
                                                <td>
                                                    <div class="boxtitle" >
                                                            ${sessionScope.constantJSON.w2InformationTable.MedicareWagesAndTips}
                                                    </div>
                                                    <div class="boxvalue">
                                                        ${w2Print.mgross}
                                                    </div>
                                                    <div class="boxtitle">
                                                        &nbsp;
                                                    </div>
                                                </td>
                                                <td>
                                                    <div class="boxtitle" >
                                                            ${sessionScope.constantJSON.w2InformationTable.MedicareTaxWithheld}
                                                    </div>
                                                    <div class="boxvalue">
                                                        ${w2Print.mtax}
                                                    </div>
                                                    <div class="boxtitle">
                                                        &nbsp;
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="doubleborder">
                                                    <div class="boxtitle" >
                                                            ${sessionScope.constantJSON.w2InformationTable.AdvancedEICpayment}
                                                    </div>
                                                    <div class="boxvalue">
                                                        ${w2Print.eic}
                                                    </div>
                                                    <div class="boxtitle">
                                                        &nbsp;
                                                    </div>
                                                </td>
                                                <td class="doubleborder">
                                                    <div class="boxtitle" >
                                                            ${sessionScope.constantJSON.w2InformationTable.dependentCareBenefits}
                                                    </div>
                                                    <div class="boxvalue">
                                                        ${w2Print.dcare}
                                                    </div>
                                                    <div class="boxtitle">
                                                        &nbsp;
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td rowspan="2">
                                                    <div class="boxtitle" >
                                                            ${sessionScope.constantJSON.w2InformationTable.eEmployeeName}
                                                    </div>
                                                    <div class="boxvalue">
                                                        ${w2Print.empname}
                                                    </div>
                                                    <div class="boxvalue">
                                                        ${w2Print.empaddress}
                                                    </div>
                                                    <div class="boxvalue">
                                                        ${w2Print.empcityst}
                                                    </div>
                                                </td>
                                                <td>
                                                        <table class="table no-border-table">
                                                            <tr>
                                                                <td class="boxtitle" colspan="2">
                                                                    <span >${sessionScope.constantJSON.w2InformationTable.seeInstrs12}</span>
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td class="boxvalue">
                                                                    ${w2Print.code1201}&nbsp;
                                                                </td>
                                                                <td class="boxvaluerj">
                                                                    ${w2Print.amt1201}&nbsp;
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td class="boxvalue">
                                                                    ${w2Print.code1202}&nbsp;
                                                                </td>
                                                                <td class="boxvaluerj">
                                                                    ${w2Print.amt1202}&nbsp;
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td class="boxvalue">
                                                                    ${w2Print.code1203}&nbsp;
                                                                </td>
                                                                <td class="boxvaluerj">
                                                                    ${w2Print.amt1203}&nbsp;
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td class="boxvalue">
                                                                    ${w2Print.code1204}&nbsp;
                                                                </td>
                                                                <td class="boxvaluerj">
                                                                    ${w2Print.amt1204}&nbsp;
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td class="boxvalue">
                                                                    ${w2Print.code1205}&nbsp;
                                                                </td>
                                                                <td class="boxvaluerj">
                                                                    ${w2Print.amt1205}&nbsp;
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td class="boxvalue">
                                                                    ${w2Print.code1206}&nbsp;
                                                                </td>
                                                                <td class="boxvaluerj">
                                                                    ${w2Print.amt1206}&nbsp;
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td class="boxvalue">
                                                                    ${w2Print.code1207}&nbsp;
                                                                </td>
                                                                <td class="boxvaluerj">
                                                                    ${w2Print.amt1207}&nbsp;
                                                                </td>
                                                            </tr>
                                                        </table>
                                                </td>
                                                <td>
                                                        <table class="table no-border-table">
                                                            <tr>
                                                                <td class="boxtitle" colspan="2">
                                                                    <span >${sessionScope.constantJSON.w2InformationTable.seeInstrs14}</span>
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td class="boxvalue">
                                                                    ${w2Print.code1401}&nbsp;
                                                                </td>
                                                                <td class="boxvaluerj">
                                                                    ${w2Print.amt1401}&nbsp;
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td class="boxvalue">
                                                                    ${w2Print.code1402}&nbsp;
                                                                </td>
                                                                <td class="boxvaluerj">
                                                                    ${w2Print.amt1402}&nbsp;
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td class="boxvalue">
                                                                    ${w2Print.code1403}&nbsp;
                                                                </td>
                                                                <td class="boxvaluerj">
                                                                    ${w2Print.amt1403}&nbsp;
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td class="boxvalue">
                                                                    ${w2Print.code1404}&nbsp;
                                                                </td>
                                                                <td class="boxvaluerj">
                                                                    ${w2Print.amt1404}&nbsp;
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td class="boxvalue">
                                                                    ${w2Print.code1405}&nbsp;
                                                                </td>
                                                                <td class="boxvaluerj">
                                                                    ${w2Print.amt1405}&nbsp;
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td class="boxvalue">
                                                                    ${w2Print.code1406}&nbsp;
                                                                </td>
                                                                <td class="boxvaluerj">
                                                                    ${w2Print.amt1406}&nbsp;
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td class="boxvalue">
                                                                    ${w2Print.code1407}&nbsp;
                                                                </td>
                                                                <td class="boxvaluerj">
                                                                    ${w2Print.amt1407}&nbsp;
                                                                </td>
                                                            </tr>
                                                        </table>
                                                </td>
                                            </tr>
                
                                            <tr>
                                                <td colspan="2">
                                                    <table class="table no-border-table w2Check-table">
                                                        <tr>
                                                            <td>
                                                                <div class="boxtitle">
                                                                    13
                                                                </div>
                                                            </td>
                                                            <td>
                                                                <div class="boxtitle" >
                                                                        ${sessionScope.constantJSON.w2InformationTable.statutoryEmployee}
                                                                </div>
                                                                <div class="boximage">
                                                                    <c:if test="${w2Print.statemp == 'checkedbox'}">
                                                                            <span class="print-check-disabled">
                                                                                    <i class="fa fa-times"></i>
                                                                                </span>
                                                                    </c:if>
                                                                    <c:if test="${w2Print.statemp=='uncheckedbox'}">
                                                                            <input class="checkBoxOld" type="checkbox" aria-label="${sessionScope.languageJSON.accessHint.statutoryEmployeeCheckbox}"  />
                                                                    </c:if>
                                                                </div>
                                                            </td>
                                                            <td>
                                                                <div class="boxtitle" >
                                                                        ${sessionScope.constantJSON.w2InformationTable.retirementPlan}
                                                                </div>
                                                                <div class="boximage">
                                                                    <c:if test="${w2Print.retplan == 'checkedbox'}">
                                                                            <span class="print-check-disabled">
                                                                                    <i class="fa fa-times"></i>
                                                                                </span>
                                                                    </c:if>
                                                                    <c:if test="${w2Print.retplan=='uncheckedbox'}">
                                                                            <input class="checkBoxOld" type="checkbox" aria-label="${sessionScope.languageJSON.accessHint.retirementPlanCheckbox}" />
                                                                    </c:if>
                                                                </div>
                                                            </td>
                                                            <td>
                                                                <div class="boxtitle" >
                                                                        ${sessionScope.constantJSON.w2InformationTable.thirdPartySickPay}
                                                                </div>
                                                                <div class="boximage">
                                                                    <c:if test="${w2Print.thrdsick == 'checkedbox'}">
                                                                            <span class="print-check-disabled">
                                                                                    <i class="fa fa-times"></i>
                                                                                </span>
                                                                    </c:if>
                                                                    <c:if test="${w2Print.thrdsick=='uncheckedbox'}">
                                                                            <input class="checkBoxOld" type="checkbox"   aria-label="${sessionScope.languageJSON.accessHint.thirdPartySickPayCheckbox}" />
                                                                    </c:if>
                                                                </div>
                                                            </td>
                                                        </tr>
                                                    </table>
                                                    <div class="boxtitle">
                                                        &nbsp;
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td colspan="3" class="no-border-td">
                                                    <div class="footer-left pull-left">
                                                        <span >
                                                                ${sessionScope.constantJSON.w2InformationTable.copyC}
                                                        </span>
                                                    </div>
                                                    <div class="footer-right pull-right" >
                                                            ${sessionScope.constantJSON.w2InformationTable.departmentOfTheTreasury}
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="no-border-td" colspan="3"
                                                    style="text-align: center">
                                                    <h1 class="selectYearSpan">${selectedYear}</h1>
                                                </td>
                                            </tr>
                                        </table>

                                    <div class="PageNext"></div>
                                    <c:if test="${selectedYear >= '2009' && selectedYear <= sessionScope.options.w2Latest}">
                                        <jsp:include page="../report-w2/${selectedYear}-2.jsp"></jsp:include>
                                    </c:if>
                                </div>
                            </div>
                        </section>
            </main>
        </div>
        <%@ include file="../commons/footer.jsp"%>
        <c:if test="${sessionScope.options.enableElecConsntW2 == true}">
            <div
                class="modal fade"
                id="electronicConsent"
                tabindex="-1"
                role="dialog"
                aria-labelledby="electronicConsent"
                data-backdrop="static"
            >
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                           
                            <h4 class="modal-title new-title" >
                                    ${sessionScope.languageJSON.label.w2ElectronicConsent}
                            </h4>
                            <button
                            type="button" role="button"
                            class="close"
                            data-dismiss="modal"
                            aria-label="${sessionScope.languageJSON.label.closeModal}"
                        >
                            &times;
                        </button>
                        </div>
                        <div class="modal-body">
                            <form >
                            	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <div class="form-group">
                                    <label >${sessionScope.languageJSON.label.customMessageHere}</label>
                                     <c:if test="${elecConsntMsgW2==''}">
	                                     <input type="text" class="form-control form-text static" aria-label="${sessionScope.languageJSON.label.blankValueForCustomMessage}" readonly="readonly"/>
                                    </c:if>
                                     <c:if test="${elecConsntMsgW2!=''}">
                                        <textarea class="form-control form-text static"  readonly="readonly">${elecConsntMsgW2}</textarea>
                                    </c:if>
                                </div>
                                <div class="form-group">
                                        <div class="checkbox mb-2">
                                            <label for="consent">
                                                <input class="consentRadio" type="radio" role="radio" name="consent" id="consent" aria-checked="false"> 
                                                <span >${sessionScope.languageJSON.label.w2Yes}</span>
                                            </label>
                                        </div>
                                        <div class="checkbox">
                                                <label for="notConsent">
                                                    <input class="consentRadio" type="radio" role="radio" name="consent" id="notConsent" aria-checked="false"> 
                                                    <span >${sessionScope.languageJSON.label.w2No}</span>
                                                </label>
                                        </div>
                                </div>
                            </form>
                            <form hidden="hidden" id="consentForm" action="updateW2Consent" method="POST">
                            	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <input type="hidden" name="year" id="consentYear" value="${selectedYear}">
                                <input type="hidden" id="w2Latest" value="${sessionScope.options.w2Latest}"/>
                                <input type="hidden" name="consentMsg" id="elecConsntMsgW2" value="${elecConsntMsgW2}">
                                <input type="hidden" name="consent" id="elecConsntW2Flag" value="${consent}">
                                <input type="hidden"  id="enableElecConsntW2" value="${sessionScope.options.enableElecConsntW2}"/>
                            </form>
                            <p class="error-hint hide" role="alert" aria-atomic="true" id="noChooseError" >
                                    ${sessionScope.languageJSON.validator.pleaseSelectAgreeWay}
                            </p>
                        </div>
                        <div class="modal-footer">
                            <button
                                id="saveConsent"
                                type="button" role="button"
                                class="btn btn-primary"
                                data-dismiss="modal"
                                
                            >${sessionScope.languageJSON.label.save}</button>
                            <button
                                class="btn btn-secondary"
                                data-dismiss="modal"
                                
                            >${sessionScope.languageJSON.label.cancel}
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </c:if>
    </body>
    <script src="/<%=request.getContextPath().split("/")[1]%>/js/viewJs/inquiry/w2Information.js"></script>
</html>
