<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> <%@ taglib
uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> <%@ page
language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html lang="en">
    <head>
        <title data-localize="headTitle.w2Info"></title>
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <%@ include file="../commons/header.jsp"%>
    </head>
    <body class="hold-transition sidebar-mini">
        <div class="wrapper">
            <%@ include file="../commons/bar.jsp"%>

            <main class="content-wrapper" id="content" tabindex="-1">
                    <section class="content">
                            <h2 class="clearfix no-print section-title">
                                <span data-localize="title.w2Info"></span>
                                <div class="right-btn pull-right">
                                    <c:if test="${sessionScope.options.enableElecConsntW2}">
                                        <button class="btn btn-primary" data-toggle="modal" data-target="#electronicConsent" data-localize="label.w-2Consent"></button>
                                    </c:if>
                                    <c:if test="${selectedYear >= '2009' && selectedYear <= sessionScope.options.w2Latest}">
                                        <button class="btn btn-primary" onclick="doPrint()" data-localize="label.print">
                                        </button>
                                    </c:if>
                                </div>
                            </h2>
                            <div class="content-white EMP-detail w-2">
                                <c:if test="${isSuccess}">
                                    <div id="updateMsg" class="no-print">
                                        <span class="error-hint">
                                            <b data-localize="validator.updateWasSuccessful"></b>
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
                                    <div class="form-group in-line">
                                        <label class="form-title"  for="year"  data-localize="label.pleaseSelectYear"></label>
                                        <select class="form-control" name="year" id="year" onchange="submitCalendarForm()">
                                            <c:forEach var="year" items="${years}" varStatus="years">
                                                <option value="${year}" <c:if test="${year == selectedYear }">selected</c:if>>${year}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </form>
                                <table
                                    class="table border-table responsive-table no-thead print-table no-print"
                                >
                                    <tbody>
                                        <tr>
                                            <td class="td-title" data-localize="w2InformationTable.taxableGrossPay"></td>
                                            <td
                                                class="td-content"
                                                data-localize="w2InformationTable.taxableGrossPay"
                                            >
                                            <fmt:formatNumber value="${w2Info.whGross}" pattern="#,##0.00"/>
                                            </td>
                                            <td class="td-title" data-localize="w2InformationTable.withholdingTax"></td>
                                            <td
                                                class="td-content"
                                                data-localize="w2InformationTable.withholdingTax"
                                            >
                                            <fmt:formatNumber value="${w2Info.whTax}" pattern="#,##0.00"/>
                                            </td>
                                            <td class="td-title" data-localize="w2InformationTable.pension"></td>
                                            <td class="td-content" data-localize="w2InformationTable.pension">
                                                    ${w2Info.pension}
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="td-title" data-localize="w2InformationTable.FICAGross"></td>
                                            <td
                                                class="td-content"
                                                data-localize="w2InformationTable.FICAGross"
                                            >
                                            <fmt:formatNumber value="${w2Info.ficaGross}" pattern="#,##0.00"/>
                                            </td>
                                            <td class="td-title" data-localize="w2InformationTable.FICATax"></td>
                                            <td
                                                class="td-content"
                                                data-localize="w2InformationTable.FICATax"
                                                colspan="3"
                                            >
                                            <fmt:formatNumber value="${w2Info.ficaTax}" pattern="#,##0.00"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="td-title" data-localize="w2InformationTable.medicareGross"></td>
                                            <td
                                                class="td-content"
                                                data-localize="w2InformationTable.medicareGross"
                                            >
                                            <fmt:formatNumber value="${w2Info.medGross}" pattern="#,##0.00"/>
                                            </td>
                                            <td class="td-title" data-localize="w2InformationTable.medicareTax"></td>
                                            <td
                                                class="td-content"
                                                data-localize="w2InformationTable.medicareTax"
                                                colspan="3"
                                            >
                                            <fmt:formatNumber value="${w2Info.medTax}" pattern="#,##0.00"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="td-title" data-localize="w2InformationTable.earnedIncomeCredit">
                                            </td>
                                            <td
                                                class="td-content"
                                                data-localize="w2InformationTable.earnedIncomeCredit"
                                            >
                                            <fmt:formatNumber value="${w2Info.eicAmt}" pattern="#,##0.00"/>
                                            </td>
                                            <td class="td-title" data-localize="w2InformationTable.dependentCare"></td>
                                            <td
                                                class="td-content"
                                                data-localize="w2InformationTable.dependentCare"
                                                colspan="3"
                                            >
                                            <fmt:formatNumber value="${w2Info.dependCare}" pattern="#,##0.00"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td colspan="6">&nbsp;</td>
                                        </tr>
                                        <tr>
                                            <td class="td-title" data-localize="w2InformationTable.annuityDeduction"></td>
                                            <td
                                                class="td-content"
                                                data-localize="w2InformationTable.annuityDeduction"
                                            >
                                            <fmt:formatNumber value="${w2Info.annuityDed}" pattern="#,##0.00"/>
                                            </td>
                                            <td class="td-title" data-localize="w2InformationTable.457withdraw"></td>
                                            <td
                                                class="td-content"
                                                data-localize="w2InformationTable.457withdraw"
                                            >
                                            <fmt:formatNumber value="${w2Info.withdraw457}" pattern="#,##0.00"/>
                                            </td>
                                            <td class="td-title" data-localize="w2InformationTable.457Annuities">
                                            </td>
                                            <td
                                                class="td-content"
                                                data-localize="w2InformationTable.457Annuities"
                                            >
                                            <fmt:formatNumber value="${w2Info.emp457Contrib + w2Info.emplrContrib457}" pattern="#,##0.00"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="td-title" data-localize="w2InformationTable.cafeteria125"></td>
                                            <td
                                                class="td-content"
                                                data-localize="w2InformationTable.cafeteria125"
                                            >
                                            <fmt:formatNumber value="${w2Info.cafeAmt}" pattern="#,##0.00"/>
                                            </td>
                                            <td class="td-title" data-localize="w2InformationTable.roth403BAfterTax">
                                            </td>
                                            <td
                                                class="td-content"
                                                data-localize="w2InformationTable.roth403BAfterTax"
                                                colspan="3"
                                            >
                                            <fmt:formatNumber value="${w2Info.annuityRoth}" pattern="#,##0.00"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td colspan="6">&nbsp;</td>
                                        </tr>
                                        <tr>
                                            <td class="td-title" data-localize="w2InformationTable.nonTrsBusinessExpense">
                                            </td>
                                            <td
                                                class="td-content"
                                                data-localize="w2InformationTable.nonTrsBusinessExpense"
                                            >
                                            <fmt:formatNumber value="${w2Info.nontrsNontaxBusAllow}" pattern="#,##0.00"/>
                                            </td>
                                            <td class="td-title" data-localize="w2InformationTable.taxableAllowance"></td>
                                            <td
                                                class="td-content"
                                                data-localize="w2InformationTable.taxableAllowance"
                                            >
                                            <fmt:formatNumber value="${w2Info.nontrsBusAllow}" pattern="#,##0.00"/>
                                            </td>
                                            <td class="td-title" data-localize="w2InformationTable.empBusinessExpense">
                                            </td>
                                            <td
                                                class="td-content"
                                                data-localize="w2InformationTable.empBusinessExpense"
                                            >
                                            <fmt:formatNumber value="${w2Info.empBusinessExpense}" pattern="#,##0.00"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="td-title" data-localize="w2InformationTable.movingExpenseReimbursement">
                                            </td>
                                            <td
                                                class="td-content"
                                                data-localize="w2InformationTable.movingExpenseReimbursement"
                                            >
                                            <fmt:formatNumber value="${w2Info.movingExpReimbr}" pattern="#,##0.00"/>
                                            </td>
                                            <c:choose>
							                    <c:when test="${selectedYear >= '2012'}">
                                                    <td class="td-title" data-localize="w2InformationTable.empSponsoredHealthCoverage">
                                                    </td>
                                                    <td
                                                        class="td-content"
                                                        data-localize="w2InformationTable.empSponsoredHealthCoverage"
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
                                                    <td class="td-title" data-localize="w2InformationTable.annuityRoth"></td>
                                                    <td
                                                        class="td-content"
                                                        data-localize="w2InformationTable.annuityRoth"
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
                                            <td class="td-title" data-localize="w2InformationTable.TRSSalaryReduction">
                                            </td>
                                            <td
                                                class="td-content"
                                                data-localize="w2InformationTable.TRSSalaryReduction"
                                                colspan="5"
                                            >
                                            <fmt:formatNumber value="${w2Info.trsDeposit}" pattern="#,##0.00"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td colspan="6">&nbsp;</td>
                                        </tr>
                                        <tr>
                                            <td class="td-title" data-localize="w2InformationTable.taxedLifeContribution">
                                            </td>
                                            <td
                                                class="td-content"
                                                data-localize="w2InformationTable.taxedLifeContribution"
                                            >
                                            <fmt:formatNumber value="${w2Info.taxEmplrLifeGrp}" pattern="#,##0.00"/>
                                            </td>
                                            <td class="td-title" data-localize="w2InformationTable.healthInsuranceDeduction">
                                            </td>
                                            <td
                                                class="td-content"
                                                data-localize="w2InformationTable.healthInsuranceDeduction"
                                            >
                                            <fmt:formatNumber value="${w2Info.hlthInsDed}" pattern="#,##0.00"/>
                                            </td>
                                            <td class="td-title" data-localize="w2InformationTable.taxableFringeBenefits">
                                            </td>
                                            <td
                                                class="td-content"
                                                data-localize="w2InformationTable.taxableFringeBenefits"
                                            >
                                            <fmt:formatNumber value="${w2Info.taxedBenefits}" pattern="#,##0.00"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td colspan="6">&nbsp;</td>
                                        </tr>
                                        <tr>
                                            <td class="td-title" data-localize="w2InformationTable.healthSavingsAccount">
                                            </td>
                                            <td
                                                class="td-content"
                                                data-localize="w2InformationTable.healthSavingsAccount"
                                            >
                                            <fmt:formatNumber value="${w2Info.hsaContrib}" pattern="#,##0.00"/>
                                            </td>
                                            <td class="td-title" data-localize="w2InformationTable.nonTaxSickPay"></td>
                                            <td
                                                class="td-content"
                                                data-localize="w2InformationTable.nonTaxSickPay"
                                            >
                                            <fmt:formatNumber value="${w2Info.sickPayNontax}" pattern="#,##0.00"/>
                                            </td>
                                            <c:choose>
                                                <c:when test="${selectedYear >= '2010'}">
                                                    <td class="td-title" data-localize="w2InformationTable.hireExemptWages"></td>
                                                    <td
                                                        class="td-content"
                                                        data-localize="w2InformationTable.hireExemptWages"
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

                                <c:forEach var="sick" items="${thirdPartyPay}">
                                        <div class="groupTitle no-print">
                                            <span data-localize="label.thirdPartySickPayW2Amounts"></span> - 
                                            ${sick.frequency.label}
                                        </div>
                                        <table class="table border-table responsive-table no-thead print-table no-print">
                                            <tr>
                                                <td class="td-title" data-localize="w2InformationTable.withholdingGross">
                                                </td>
                                                <td class="td-content" data-localize="w2InformationTable.withholdingGross">
                                                        <fmt:formatNumber value="${sick.sickPayWhGross}" pattern="#,##0.00"/>
                                                </td>
                                                <td class="td-title" data-localize="w2InformationTable.withholdingTax">
                                                </td>
                                                <td class="td-content" data-localize="w2InformationTable.withholdingTax">
                                                        <fmt:formatNumber value="${sick.sickPayWhTax}" pattern="#,##0.00"/>
                                                </td>
                                                <td class="td-title"></td>
                                                <td class="td-content"></td>
                                            </tr>
                                            <tr>
                                                <td class="td-title" data-localize="w2InformationTable.FICAGross">
                                                </td>
                                                <td class="td-content" data-localize="w2InformationTable.FICAGross">
                                                        <fmt:formatNumber value="${sick.sickPayFicaGross}" pattern="#,##0.00"/>
                                                </td>
                                                <td class="td-title" data-localize="w2InformationTable.FICATax">
                                                </td>
                                                <td class="td-content" data-localize="w2InformationTable.FICATax">
                                                        <fmt:formatNumber value="${sick.sickPayFicaTax}" pattern="#,##0.00"/>
                                                </td>
                                                <td class="td-title"></td>
                                                <td class="td-content"></td>
                                            </tr>
                                            <tr>
                                                <td class="td-title" data-localize="w2InformationTable.medicareGross">
                                                </td>
                                                <td class="td-content" data-localize="w2InformationTable.medicareGross">
                                                        <fmt:formatNumber value="${sick.sickPayMedGross}" pattern="#,##0.00"/>
                                                </td>
                                                <td class="td-title" data-localize="w2InformationTable.medicareTax">
                                                </td>
                                                <td class="td-content" data-localize="w2InformationTable.medicareTax">
                                                        <fmt:formatNumber value="${sick.sickPayMedTax}" pattern="#,##0.00"/>
                                                </td>
                                                <td class="td-title"></td>
                                                <td class="td-content"></td>
                                            </tr>
                                            <tr>
                                                <td class="td-title" data-localize="w2InformationTable.nontaxablePay">
                                                </td>
                                                <td class="td-content" data-localize="w2InformationTable.nontaxablePay">
                                                        <fmt:formatNumber value="${sick.sickNontax}" pattern="#,##0.00"/>
                                                </td>
                                                <td class="td-title"></td>
                                                <td class="td-content"></td>
                                                <td class="td-title"></td>
                                                <td class="td-content"></td>
                                            </tr>
                                        </table>
                                </c:forEach>

                                
                                <table class="table border-table mb-5 print-block-table noNumTable">
                                    <tr>
                                        <td class="header" colspan="3" valign="top">
                                            <span data-localize="w2InformationTable.formW2WageAndTaxStatement"></span>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <div class="boxtitle" data-localize="w2InformationTable.aEmployeeSocial">
                                            </div>
                                            <div class="boxvalue">
                                                ${w2Print.ssn}
                                            </div>
                                            <div class="boxtitle">
                                                &nbsp;
                                            </div>
                                        </td>
                                        <td class="doubleborder">
                                            <div class="boxtitle" data-localize="w2InformationTable.1WagesTipsOtherCompensation">
                                            </div>
                                            <div class="boxvalue">
                                                ${w2Print.tgross}
                                            </div>
                                            <div class="boxtitle">
                                                &nbsp;
                                            </div>
                                        </td>
                                        <td class="doubleborder">
                                            <div class="boxtitle" data-localize="w2InformationTable.2FederalIncomeTaxWithheld">
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
                                            <div class="boxtitle" data-localize="w2InformationTable.bEIN">
                                            </div>
                                            <div class="boxvalue">
                                                ${w2Print.ein}
                                            </div>
                                            <div class="boxtitle">
                                                &nbsp;
                                            </div>
                                        </td>
                                        <td>
                                            <div class="boxtitle" data-localize="w2InformationTable.3SocialSecurityWages">
                                            </div>
                                            <div class="boxvalue">
                                                ${w2Print.fgross}
                                            </div>
                                            <div class="boxtitle">
                                                &nbsp;
                                            </div>
                                        </td>
                                        <td>
                                            <div class="boxtitle" data-localize="w2InformationTable.4SocialSecurityTaxWithheld">
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
                                            <div class="boxtitle" data-localize="w2InformationTable.cEmployerNameAddressZip">
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
                                            <div class="boxtitle" data-localize="w2InformationTable.5MedicareWagesAndTips">
                                            </div>
                                            <div class="boxvalue">
                                                ${w2Print.mgross}
                                            </div>
                                            <div class="boxtitle">
                                                &nbsp;
                                            </div>
                                        </td>
                                        <td>
                                            <div class="boxtitle" data-localize="w2InformationTable.6MedicareTaxWithheld">
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
                                            <div class="boxtitle" data-localize="w2InformationTable.9AdvancedEICpayment">
                                            </div>
                                            <div class="boxvalue">
                                                ${w2Print.eic}
                                            </div>
                                            <div class="boxtitle">
                                                &nbsp;
                                            </div>
                                        </td>
                                        <td class="doubleborder">
                                            <div class="boxtitle" data-localize="w2InformationTable.10dependentCareBenefits">
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
                                            <div class="boxtitle" data-localize="w2InformationTable.eEmployeeName">
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
                                                            <span data-localize="w2InformationTable.12seeInstrs"></span>
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
                                                            <span data-localize="w2InformationTable.14seeInstrs"></span>
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
                                                        <td valign="top">
                                                            <div class="boxtitle">
                                                                13
                                                            </div>
                                                        </td>
                                                        <td valign="top">
                                                            <div class="boxtitle" data-localize="w2InformationTable.statutoryEmployee">
                                                            </div>
                                                            <div class="boximage">
                                                                <c:if test="${w2Print.statemp == 'checkedbox'}">
                                                                        <span class="print-check-disabled">
                                                                                <i class="fa fa-times"></i>
                                                                            </span>
                                                                </c:if>
                                                                <c:if test="${w2Print.statemp=='uncheckedbox'}">
                                                                        <input class="checkBoxOld" type="checkbox" title="" data-localize="accessHint.statutoryEmployeeCheckbox" />
                                                                </c:if>
                                                            </div>
                                                        </td>
                                                        <td valign="top">
                                                            <div class="boxtitle" data-localize="w2InformationTable.retirementPlan">
                                                            </div>
                                                            <div class="boximage">
                                                                <c:if test="${w2Print.retplan == 'checkedbox'}">
                                                                        <span class="print-check-disabled">
                                                                                <i class="fa fa-times"></i>
                                                                            </span>
                                                                </c:if>
                                                                <c:if test="${w2Print.retplan=='uncheckedbox'}">
                                                                        <input class="checkBoxOld" type="checkbox"  title="" data-localize="accessHint.retirementPlanCheckbox"/>
                                                                </c:if>
                                                            </div>
                                                        </td>
                                                        <td valign="top">
                                                            <div class="boxtitle" data-localize="w2InformationTable.thirdPartySickPay">
                                                            </div>
                                                            <div class="boximage">
                                                                <c:if test="${w2Print.thrdsick == 'checkedbox'}">
                                                                        <span class="print-check-disabled">
                                                                                <i class="fa fa-times"></i>
                                                                            </span>
                                                                </c:if>
                                                                <c:if test="${w2Print.thrdsick=='uncheckedbox'}">
                                                                        <input class="checkBoxOld" type="checkbox"   title="" data-localize="accessHint.thirdPartySickPayCheckbox"/>
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
                                                <span data-localize="w2InformationTable.copyB">
                                                </span>
                                            </div>
                                            <div class="footer-right pull-right" data-localize="w2InformationTable.departmentOfTheTreasury">
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
                                <jsp:include page="../report-w2/${selectedYear}-1.jsp"></jsp:include> 
                                <div class="PageNext"></div>
                               
                                <table class="table border-table mb-5 print-block-table noNumTable">
                                        <tr>
                                            <td class="header" colspan="3" valign="top">
                                                <span data-localize="w2InformationTable.formW2WageAndTaxStatement"></span>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <div class="boxtitle" data-localize="w2InformationTable.aEmployeeSocial">
                                                </div>
                                                <div class="boxvalue">
                                                    ${w2Print.ssn}
                                                </div>
                                                <div class="boxtitle">
                                                    &nbsp;
                                                </div>
                                            </td>
                                            <td class="doubleborder">
                                                <div class="boxtitle" data-localize="w2InformationTable.1WagesTipsOtherCompensation">
                                                </div>
                                                <div class="boxvalue">
                                                    ${w2Print.tgross}
                                                </div>
                                                <div class="boxtitle">
                                                    &nbsp;
                                                </div>
                                            </td>
                                            <td class="doubleborder">
                                                <div class="boxtitle" data-localize="w2InformationTable.2FederalIncomeTaxWithheld">
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
                                                <div class="boxtitle" data-localize="w2InformationTable.bEIN">
                                                </div>
                                                <div class="boxvalue">
                                                    ${w2Print.ein}
                                                </div>
                                                <div class="boxtitle">
                                                    &nbsp;
                                                </div>
                                            </td>
                                            <td>
                                                <div class="boxtitle" data-localize="w2InformationTable.3SocialSecurityWages">
                                                </div>
                                                <div class="boxvalue">
                                                    ${w2Print.fgross}
                                                </div>
                                                <div class="boxtitle">
                                                    &nbsp;
                                                </div>
                                            </td>
                                            <td>
                                                <div class="boxtitle" data-localize="w2InformationTable.4SocialSecurityTaxWithheld">
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
                                                <div class="boxtitle" data-localize="w2InformationTable.cEmployerNameAddressZip">
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
                                                <div class="boxtitle" data-localize="w2InformationTable.5MedicareWagesAndTips">
                                                </div>
                                                <div class="boxvalue">
                                                    ${w2Print.mgross}
                                                </div>
                                                <div class="boxtitle">
                                                    &nbsp;
                                                </div>
                                            </td>
                                            <td>
                                                <div class="boxtitle" data-localize="w2InformationTable.6MedicareTaxWithheld">
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
                                                <div class="boxtitle" data-localize="w2InformationTable.9AdvancedEICpayment">
                                                </div>
                                                <div class="boxvalue">
                                                    ${w2Print.eic}
                                                </div>
                                                <div class="boxtitle">
                                                    &nbsp;
                                                </div>
                                            </td>
                                            <td class="doubleborder">
                                                <div class="boxtitle" data-localize="w2InformationTable.10dependentCareBenefits">
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
                                                <div class="boxtitle" data-localize="w2InformationTable.eEmployeeName">
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
                                                                <span data-localize="w2InformationTable.12seeInstrs"></span>
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
                                                                <span data-localize="w2InformationTable.14seeInstrs"></span>
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
                                                        <td valign="top">
                                                            <div class="boxtitle">
                                                                13
                                                            </div>
                                                        </td>
                                                        <td valign="top">
                                                            <div class="boxtitle" data-localize="w2InformationTable.statutoryEmployee">
                                                            </div>
                                                            <div class="boximage">
                                                                <c:if test="${w2Print.statemp == 'checkedbox'}">
                                                                        <span class="print-check-disabled">
                                                                                <i class="fa fa-times"></i>
                                                                            </span>
                                                                </c:if>
                                                                <c:if test="${w2Print.statemp=='uncheckedbox'}">
                                                                        <input class="checkBoxOld" type="checkbox" title="" data-localize="accessHint.statutoryEmployeeCheckbox" />
                                                                </c:if>
                                                            </div>
                                                        </td>
                                                        <td valign="top">
                                                            <div class="boxtitle" data-localize="w2InformationTable.retirementPlan">
                                                            </div>
                                                            <div class="boximage">
                                                                <c:if test="${w2Print.retplan == 'checkedbox'}">
                                                                        <span class="print-check-disabled">
                                                                                <i class="fa fa-times"></i>
                                                                            </span>
                                                                </c:if>
                                                                <c:if test="${w2Print.retplan=='uncheckedbox'}">
                                                                        <input class="checkBoxOld" type="checkbox" title="" data-localize="accessHint.retirementPlanCheckbox"/>
                                                                </c:if>
                                                            </div>
                                                        </td>
                                                        <td valign="top">
                                                            <div class="boxtitle" data-localize="w2InformationTable.thirdPartySickPay">
                                                            </div>
                                                            <div class="boximage">
                                                                <c:if test="${w2Print.thrdsick == 'checkedbox'}">
                                                                        <span class="print-check-disabled">
                                                                                <i class="fa fa-times"></i>
                                                                            </span>
                                                                </c:if>
                                                                <c:if test="${w2Print.thrdsick=='uncheckedbox'}">
                                                                        <input class="checkBoxOld" type="checkbox"   title="" data-localize="accessHint.thirdPartySickPayCheckbox"/>
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
                                                    <span data-localize="w2InformationTable.copyC">
                                                    </span>
                                                </div>
                                                <div class="footer-right pull-right" data-localize="w2InformationTable.departmentOfTheTreasury">
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
                                <jsp:include page="../report-w2/${selectedYear}-2.jsp"></jsp:include>
                                
                            </div>
                        </section>
            </main>
        </div>
        <%@ include file="../commons/footer.jsp"%>
        <div
            class="modal fade"
            id="electronicConsent"
            tabindex="-1"
            role="dialog"
            aria-labelledby="electronicConsent"
            aria-hidden="true"
        >
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button
                            type="button"
                            class="close"
                            data-dismiss="modal"
                            aria-hidden="true"
                        >
                            <span class="hide" data-localize="label.closeModal"></span>
                            &times;
                        </button>
                        <h4 class="modal-title new-title" data-localize="label.w2ElectronicConsent">
                        </h4>
                    </div>
                    <div class="modal-body">
                        <form >
                            <div class="form-group">
                                <label for="customMessage" data-localize="label.customMessageHere"></label>
                                <textarea class="form-control form-text" name="customMessage" id="customMessage" cols="30" rows="6" title="" placeholder="" data-localize="label.customMessageHere" disabled>
                                    ${elecConsntMsgW2}
                                </textarea>
                            </div>
                            <div class="form-group">
                                    <div class="checkbox mb-2">
                                        <label for="consent">
                                            <input class="icheck" type="radio" name="consent" id="consent"> 
                                            <span data-localize="label.w2Yes"></span>
                                        </label>
                                    </div>
                                    <div class="checkbox">
                                            <label for="notConsent">
                                                <input class="icheck" type="radio" name="consent" id="notConsent"> 
                                                <span data-localize="label.w2No"></span>
                                            </label>
                                    </div>
                            </div>
                        </form>
                        <form hidden="hidden" id="consentForm" action="updateW2Consent" method="POST">
                            <input type="text" name="year" id="consentYear" value="${selectedYear}" title="" data-localize="accessHint.year">
                            <input type="hidden" id="w2Latest" value="${sessionScope.options.w2Latest}" title="" data-localize="accessHint.w2Latest"/>
                            <input type="text" name="consentMsg" id="elecConsntMsgW2" value="${elecConsntMsgW2}" title="" data-localize="accessHint.elecConsntMsgW2">
                            <input type="text" name="consent" id="elecConsntW2Flag" value="${consent}" title="" data-localize="accessHint.consent">
                            <input type="text"  id="enableElecConsntW2" value="${sessionScope.options.enableElecConsntW2}" title="" data-localize="accessHint.enableElecConsntW2"/>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button
                            class="btn btn-secondary"
                            data-dismiss="modal"
                            aria-hidden="true"
                            title=""
                            data-localize="label.cancel"
                        >
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </body>
    <script>
        function doPrint() {
            window.print()
        }
        function submitCalendarForm(){
            $("#selectCalendar")[0].submit()
        }
        $(function(){
            let consentVal = $("#elecConsntW2Flag").val()
            if (consentVal == 'Y') {
                $('#consent').iCheck('check');
            } else if (consentVal == 'N') {
                $('#notConsent').iCheck('check');
            } 
            if(consentVal==''&& $('#enableElecConsntW2').val() == 'true'){
                $("#updateMsg").addClass("hidden");
                if ($("#elecConsntMsgW2").val() != null) {
                    if (consentVal == 'Y') {
                        $('#consent').iCheck('check');
                    } else if (consentVal == 'N') {
                        $('#notConsent').iCheck('check');
                    } 
                    $('#electronicConsent').modal('show')
                }
            }
            
            $('#consent').on('ifChecked', function(event) {
                toggleOptions('Y')
            })
            $('#notConsent').on('ifChecked', function(event) {
                toggleOptions('N')
            })
        })
        function toggleOptions(value){
            $("#elecConsntW2Flag").val(value)
            $("#consentForm")[0].submit()
        }
    </script>
</html>
