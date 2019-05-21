<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> <%@ taglib
uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> <%@ page
language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html> 
<html lang="en">
    <head>
        <title>${sessionScope.languageJSON.headTitle.info1095}</title>
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <%@ include file="../commons/header.jsp"%>
    </head>
    <body class="hold-transition sidebar-mini">
        <div class="wrapper">
            <%@ include file="../commons/bar.jsp"%>

            <main class="content-wrapper" id="content" tabindex="-1">
                <section class="content body-1095">
                            <div class="clearfix no-print section-title">
                                <h1 class="pageTitle">${sessionScope.languageJSON.nav.info1095}</h1>
                                <div class="right-btn pull-right">
                                    <c:if test="${sessionScope.options.enableElecConsnt1095 == true}">
                                            <button
                                            class="btn btn-primary"
                                            data-toggle="modal"
                                            data-target="#electronicConsent"
                                           >${sessionScope.languageJSON.label.consent1095}</button>
                                    </c:if>
                                    <c:if test="${sessionScope.options.enableElecConsnt1095 == false}">
                                            <button
                                            class="btn btn-primary disabled"
                                            disabled
                                           >${sessionScope.languageJSON.label.consent1095}</button>
                                    </c:if>
                                    <c:if test="${sessionScope.options.enable1095 == true && selectedYear <= sessionScope.options.w2Latest }">
	                                    <form class="no-print" action="exportPDF" method="POST">
												<input type="hidden" name="${_csrf.parameterName}"
													value="${_csrf.token}" />
												<input type="hidden" name="year" value="${selectedYear}" />
												<input type="hidden" name="type" value="${type}" />
												<input type="hidden" name="BPageNo" value="${BPageNo}" />
												<input type="hidden" name="CPageNo" value="${CPageNo}" />
												<input type="hidden" name="sortOrder" value="${sortOrder}" />
												<input type="hidden" name="sortBy" value="${sortBy}" />
												<button type="submit" role="button" class="btn btn-primary download-pdf"
													aria-label="${sessionScope.languageJSON.label.exportPDF}"><i class="fa fa-file-pdf-o"></i></button>
	                                    </form>
	                                    <%-- <button class="btn btn-primary download-pdf" onclick="downloadPDF()" title="" aria-label="${sessionScope.languageJSON.label.exportPDF}">
			                                <i class="fa fa-file-pdf-o"></i>
			                            </button> --%>
	                                    <button
	                                            class="btn btn-primary"
	                                            onclick="doPrint()">
	                                        ${sessionScope.languageJSON.label.print}
	                                    </button>
                                    </c:if>
                                </div>
                            </div>
                            <div class="toPrint content-white EMP-detail info-1095">
                                <div class="exportPDFBox"></div>
                                <c:if test="${isUpdate && isSuccess}">
                                    <span class="error-hint" role="alert" aria-atomic="true">
                                            <b>${sessionScope.languageJSON.validator.updateWasSuccessful}</b>
                                        </span>
                                    <br/>
                                    <br/>
                                </c:if>
                                <form
                                    class="no-print searchForm"
                                    action="information1095ByYear"
                                    id="selectCalendar"
                                    method="POST"
                                >
                                	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                    <div class="form-group in-line">
                                        <label class="form-title" for="year">
                                                ${sessionScope.languageJSON.label.pleaseSelectYear}
                                        </label>
                                        <select class="form-control" name="year" id="year" onchange="submitYear()">
                                            <c:forEach var="year" items="${years}" varStatus="years">
                                                <option value="${year}" <c:if test="${year == selectedYear }">selected</c:if>>${year}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="form-group in-line">
                                        <div class="mr-3">
                                            <input
                                                class="icheck"
                                                type="radio"
                                                name="bc-1095"
                                                id="b-1095"
                                                <c:if test="${type == 'B' }">checked</c:if>
                                                disabled
                                            />
                                            <label for="b-1095">${sessionScope.languageJSON.label.b1095}</label>
                                        </div>
                                        <div>
                                            <input
                                                class="icheck"
                                                type="radio"
                                                name="bc-1095"
                                                id="c-1095"
                                                <c:if test="${type == 'C' }">checked</c:if>
                                                disabled
                                            />
                                            <label for="c-1095">${sessionScope.languageJSON.label.c1095}</label>
                                        </div>
                                    </div>
                                </form>
                                <c:if test="${empty years}">
                                        <br> <br>
                                    <div class="error text-center">${sessionScope.languageJSON.label.no1095Info}</div>
                                </c:if>
                                <div class="needToClone">                                
                                        <c:if test="${fn:length(years) > 0}">
                                            <div class="pdfPage">
                                                <h2 class="table-top-title no-print">
                                                    <b>
                                                        1095-${type}
                                                        <span>${sessionScope.languageJSON.label.information}</span>
                                                    </b>
                                                </h2>
                                                <table
                                                    class="table border-table middle-td-table mb-0 print-block-table tableHead1095"
                                                >
                                                    <tr>
                                                        <td style="width:160px;">
                                                            <div>
                                                                <span>${sessionScope.languageJSON.info1095Table.from}</span> <span class="font-24" id="tableTile">1095-${type}</span><br />
                                                                <span>${sessionScope.languageJSON.info1095Table.departmentOfTreasury}</span><br />
                                                            </div>
                                                        </td>
                                                        <td class="no-border-td">
                                                            <span class="font-20"
                                                                ><span>${sessionScope.languageJSON.info1095Table.employerProvidedHealth}</span></span
                                                            ><br />
                                                            · <span>${sessionScope.languageJSON.info1095Table.donotAttach}</span>.<br />
                                                            · <span>${sessionScope.languageJSON.info1095Table.goToForInstructions}</span>.
                                                        </td>
                                                        <td class="no-border-td" style="width:110px;">
                                                            <div>
                                                                <label for="void" class="print-flex">
                                                                    <input
                                                                        type="checkbox"
                                                                        name="void"
                                                                        id="void"
                                                                    />
                                                                    <span>${sessionScope.languageJSON.info1095Table.void}</span>
                                                                </label>
                                                                <label
                                                                    for="corrected"
                                                                    class="print-flex"
                                                                >
                                                                    <input
                                                                        type="checkbox"
                                                                        name="corrected"
                                                                        id="corrected"
                                                                    />
                                                                    <span>${sessionScope.languageJSON.info1095Table.corrected}</span>
                                                                </label>
                                                            </div>
                                                        </td>
                                                        <td class="no-padding text-center">
                                                            <div class="border-btm-black">
                                                                <span>${sessionScope.languageJSON.info1095Table.OMBNo}</span> 1545-2251<br /><br />
                                                            </div>
                                                            <div class="font-24 selectYearSpan">${selectedYear}</div>
                                                        </td>
                                                    </tr>
                                                </table>
                        
                                                <table
                                                    class="table border-table mb-0 print-block-table"
                                                >
                                                    <tr>
                                                        <td colspan="3" class="sub-title"  style="width:50%;">
                                                            <span>${sessionScope.languageJSON.info1095Table.partIEmployee}</span>
                                                        </td>
                                                        <td colspan="3">
                                                                <span>${sessionScope.languageJSON.info1095Table.applicableEmployer}</span>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td colspan="2">
                                                            1 <span>${sessionScope.languageJSON.info1095Table.nameOfEmployee}</span>
                                                            <div class="focused-value">
                                                                    ${sessionScope.userDetail.nameF} ${sessionScope.userDetail.nameM} ${sessionScope.userDetail.nameL}
                                                            </div>
                                                        </td>
                                                        <td>
                                                            2 <span>${sessionScope.languageJSON.info1095Table.SSN}</span>
                                                            <div class="focused-value">SSN</div>
                                                        </td>
                                                        <td colspan="2">
                                                            7 <span>${sessionScope.languageJSON.info1095Table.nameOfEmployer}</span>
                                                            <div class="focused-value">${sessionScope.district.name}</div>
                                                        </td>
                                                        <td>
                                                            8 <span>${sessionScope.languageJSON.info1095Table.EIN}</span>
                                                            <div class="focused-value">EIN</div>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td colspan="3">
                                                            3 <span>${sessionScope.languageJSON.info1095Table.streetAddressApt}</span>
                                                            <div class="focused-value">
                                                                    ${sessionScope.userDetail.addrApt}
                                                            </div>
                                                        </td>
                                                        <td colspan="2">
                                                            9 <span>${sessionScope.languageJSON.info1095Table.streetAddressRoomSuite}</span>
                                                            <div class="focused-value">
                                                                    ${sessionScope.userDetail.addrNbr} ${sessionScope.userDetail.addrStr}
                                                            </div>
                                                        </td>
                                                        <td>
                                                            10 <span>${sessionScope.languageJSON.info1095Table.contactTelephoneNum}</span>
                                                            <div class="focused-value">
                                                                    (${sessionScope.userDetail.phoneArea}) ${sessionScope.userDetail.phoneNbr}
                                                            </div>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td>
                                                            4 <span>${sessionScope.languageJSON.info1095Table.cityOrTown}</span>
                                                            <div class="focused-value">${sessionScope.district.city}</div>
                                                        </td>
                                                        <td>
                                                            5 <span>${sessionScope.languageJSON.info1095Table.stateOrProvince}</span>
                                                            <div class="focused-value">${sessionScope.district.state}</div>
                                                        </td>
                                                        <td>
                                                            6 <span>${sessionScope.languageJSON.info1095Table.countryAndZip}</span>
                                                            <div class="focused-value">${sessionScope.userDetail.addrZip}</div>
                                                        </td>
                                                        <td>
                                                            11 <span>${sessionScope.languageJSON.info1095Table.cityOrTown}</span>
                                                            <div class="focused-value">${sessionScope.district.city}</div>
                                                        </td>
                                                        <td>
                                                            12 <span>${sessionScope.languageJSON.info1095Table.stateOrProvince}</span>
                                                            <div class="focused-value">${sessionScope.district.state}TX</div>
                                                        </td>
                                                        <td>
                                                            13 <span>${sessionScope.languageJSON.info1095Table.countryAndZip}</span>
                                                            <div class="focused-value">${sessionScope.district.zip}-${sessionScope.district.zip4}</div>
                                                        </td>
                                                    </tr>
                                                </table>
                        
                                                <table
                                                    class="table border-table responsive-table no-thead print-table money-table mb-0"
                                                >
                                                    <thead>
                                                        <tr class="print-tr">
                                                            <td
                                                                colspan="7"
                                                                class="sub-title"
                                                                style="width:50%;"
                                                            >
                                                            <span>${sessionScope.languageJSON.info1095Table.partIIEmployee}</span>
                                                            </td>
                                                            <td colspan="7">
                                                                    <span>${sessionScope.languageJSON.info1095Table.planStartMonth}</span>:
                                                                <b class="font-20">09</b>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td></td>
                                                            <th id="allMonthsMoney">${sessionScope.languageJSON.info1095Table.allMonths}</th>
                                                            <th id="janMoney">${sessionScope.languageJSON.info1095Table.Jan}</th>
                                                            <th id="febMoney">${sessionScope.languageJSON.info1095Table.Feb}</th>
                                                            <th id="marMoney">${sessionScope.languageJSON.info1095Table.Mar}</th>
                                                            <th id="aprMoney">${sessionScope.languageJSON.info1095Table.Apr}</th>
                                                            <th id="mayMoney">${sessionScope.languageJSON.info1095Table.May}</th>
                                                            <th id="junMoney">${sessionScope.languageJSON.info1095Table.Jun}</th>
                                                            <th id="julMoney">${sessionScope.languageJSON.info1095Table.Jul}</th>
                                                            <th id="augMoney">${sessionScope.languageJSON.info1095Table.Aug}</th>
                                                            <th id="sepMoney">${sessionScope.languageJSON.info1095Table.Sep}</th>
                                                            <th id="octMoney">${sessionScope.languageJSON.info1095Table.Oct}</th>
                                                            <th id="novMoney">${sessionScope.languageJSON.info1095Table.Nov}</th>
                                                            <th id="decMoney">${sessionScope.languageJSON.info1095Table.Dec}</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <tr>
                                                            <th id="offerOfCoverage" class="tr-title">
                                                                <div class="print-show">14 <span>${sessionScope.languageJSON.info1095Table.offerOfCoverage}</span></div>
                                                                <span class="print-hide">${sessionScope.languageJSON.info1095Table.offerOfCoverageSimple}</span>
                                                            </th>
                                                            <td headers="allMonthsMoney offerOfCoverage" data-title="${sessionScope.languageJSON.info1095Table.allMonths}">2C</td>
                                                            <td headers="janMoney offerOfCoverage" data-title="${sessionScope.languageJSON.info1095Table.Jan}"></td>
                                                            <td headers="febMoney offerOfCoverage" data-title="${sessionScope.languageJSON.info1095Table.Feb}"></td>
                                                            <td headers="marMoney offerOfCoverage" data-title="${sessionScope.languageJSON.info1095Table.Mar}"></td>
                                                            <td headers="aprMoney offerOfCoverage" data-title="${sessionScope.languageJSON.info1095Table.Apr}"></td>
                                                            <td headers="mayMoney offerOfCoverage" data-title="${sessionScope.languageJSON.info1095Table.May}"></td>
                                                            <td headers="junMoney offerOfCoverage" data-title="${sessionScope.languageJSON.info1095Table.Jun}"></td>
                                                            <td headers="julMoney offerOfCoverage" data-title="${sessionScope.languageJSON.info1095Table.Jul}"></td>
                                                            <td headers="augMoney offerOfCoverage" data-title="${sessionScope.languageJSON.info1095Table.Aug}"></td>
                                                            <td headers="sepMoney offerOfCoverage" data-title="${sessionScope.languageJSON.info1095Table.Sep}"></td>
                                                            <td headers="octMoney offerOfCoverage" data-title="${sessionScope.languageJSON.info1095Table.Oct}"></td>
                                                            <td headers="novMoney offerOfCoverage" data-title="${sessionScope.languageJSON.info1095Table.Nov}"></td>
                                                            <td headers="decMoney offerOfCoverage" data-title="${sessionScope.languageJSON.info1095Table.Dec}"></td>
                                                        </tr>
                                                        <tr>
                                                            <th id="employeeShare" class="tr-title">
                                                                <div class="print-show">15 <span>${sessionScope.languageJSON.info1095Table.employeeRequiredContribution}</span></div>
                                                                <span class="print-hide">${sessionScope.languageJSON.info1095Table.employeeShare}</span>
                                                            </th>
                                                            <td headers="allMonthsMoney employeeShare" data-title="${sessionScope.languageJSON.info1095Table.allMonths}">
                                                                    <span class="unit-dollar">${sessionScope.languageJSON.info1095Table.dollar}</span> 0.00
                                                            </td>
                                                            <td headers="janMoney employeeShare" data-title="${sessionScope.languageJSON.info1095Table.Jan}">
                                                                    <span class="unit-dollar">${sessionScope.languageJSON.info1095Table.dollar}</span> 0.00
                                                            </td>
                                                            <td headers="febMoney employeeShare" data-title="${sessionScope.languageJSON.info1095Table.Feb}">
                                                                    <span class="unit-dollar">${sessionScope.languageJSON.info1095Table.dollar}</span> 0.00
                                                            </td>
                                                            <td headers="marMoney employeeShare" data-title="${sessionScope.languageJSON.info1095Table.Mar}">
                                                                    <span class="unit-dollar">${sessionScope.languageJSON.info1095Table.dollar}</span> 0.00
                                                            </td>
                                                            <td headers="aprMoney employeeShare" data-title="${sessionScope.languageJSON.info1095Table.Apr}">
                                                                    <span class="unit-dollar">${sessionScope.languageJSON.info1095Table.dollar}</span> 0.00
                                                            </td>
                                                            <td headers="mayMoney employeeShare" data-title="${sessionScope.languageJSON.info1095Table.May}">
                                                                    <span class="unit-dollar">${sessionScope.languageJSON.info1095Table.dollar}</span> 0.00
                                                            </td>
                                                            <td headers="junMoney employeeShare" data-title="${sessionScope.languageJSON.info1095Table.Jun}">
                                                                    <span class="unit-dollar">${sessionScope.languageJSON.info1095Table.dollar}</span> 0.00
                                                            </td>
                                                            <td headers="julMoney employeeShare" data-title="${sessionScope.languageJSON.info1095Table.Jul}">
                                                                    <span class="unit-dollar">${sessionScope.languageJSON.info1095Table.dollar}</span> 0.00
                                                            </td>
                                                            <td headers="augMoney employeeShare" data-title="${sessionScope.languageJSON.info1095Table.Aug}">
                                                                    <span class="unit-dollar">${sessionScope.languageJSON.info1095Table.dollar}</span> 0.00
                                                            </td>
                                                            <td headers="sepMoney employeeShare" data-title="${sessionScope.languageJSON.info1095Table.Sep}">
                                                                    <span class="unit-dollar">${sessionScope.languageJSON.info1095Table.dollar}</span> 0.00
                                                            </td>
                                                            <td headers="octMoney employeeShare" data-title="${sessionScope.languageJSON.info1095Table.Oct}">
                                                                    <span class="unit-dollar">${sessionScope.languageJSON.info1095Table.dollar}</span> 0.00
                                                            </td>
                                                            <td headers="novMoney employeeShare" data-title="${sessionScope.languageJSON.info1095Table.Nov}">
                                                                    <span class="unit-dollar">${sessionScope.languageJSON.info1095Table.dollar}</span> 0.00
                                                            </td>
                                                            <td headers="decMoney employeeShare" data-title="${sessionScope.languageJSON.info1095Table.Dec}">
                                                                    <span class="unit-dollar">${sessionScope.languageJSON.info1095Table.dollar}</span> 0.00
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <th id="SafeHarbor" class="tr-title">
                                                                <div class="print-show">16 <span>${sessionScope.languageJSON.info1095Table.section4980HSafeHarbor}</span></div>
                                                                <span class="print-hide">${sessionScope.languageJSON.info1095Table.SafeHarbor}</span>
                                                            </th>
                                                            <td headers="allMonthsMoney SafeHarbor" data-title="${sessionScope.languageJSON.info1095Table.allMonths}">2C</td>
                                                            <td headers="janMoney SafeHarbor" data-title="${sessionScope.languageJSON.info1095Table.Jan}"></td>
                                                            <td headers="febMoney SafeHarbor" data-title="${sessionScope.languageJSON.info1095Table.Feb}"></td>
                                                            <td headers="marMoney SafeHarbor" data-title="${sessionScope.languageJSON.info1095Table.Mar}"></td>
                                                            <td headers="aprMoney SafeHarbor" data-title="${sessionScope.languageJSON.info1095Table.Apr}"></td>
                                                            <td headers="mayMoney SafeHarbor" data-title="${sessionScope.languageJSON.info1095Table.May}"></td>
                                                            <td headers="junMoney SafeHarbor" data-title="${sessionScope.languageJSON.info1095Table.Jun}"></td>
                                                            <td headers="julMoney SafeHarbor" data-title="${sessionScope.languageJSON.info1095Table.Jul}"></td>
                                                            <td headers="augMoney SafeHarbor" data-title="${sessionScope.languageJSON.info1095Table.Aug}"></td>
                                                            <td headers="sepMoney SafeHarbor" data-title="${sessionScope.languageJSON.info1095Table.Sep}"></td>
                                                            <td headers="octMoney SafeHarbor" data-title="${sessionScope.languageJSON.info1095Table.Oct}"></td>
                                                            <td headers="novMoney SafeHarbor" data-title="${sessionScope.languageJSON.info1095Table.Nov}"></td>
                                                            <td headers="decMoney SafeHarbor" data-title="${sessionScope.languageJSON.info1095Table.Dec}"></td>
                                                        </tr>
                                                    </tbody>
                                                </table>
                                                <h2 class="table-top-title no-print">
                                                    <b>${sessionScope.languageJSON.info1095Table.coverIndividuals}</b>
                                                </h2>
                                                <div class="flex self-insured-flex no-print">
                                                    <div class="self-insured-tips">
                                                        <span>${sessionScope.languageJSON.info1095Table.ifEmployerProvidedSelfInsurance}</span>
                                                    </div>
                                                    <div class="self-insured-check">
                                                        <div>
                                                                <label for="selfInsured">${sessionScope.languageJSON.info1095Table.selfInsured}</label>:
                                                            <input
                                                                class="icheck"
                                                                type="checkbox"
                                                                name="selfInsured"
                                                                id="selfInsured"
                                                                disabled
                                                            />
                                                        </div>
                                                    </div>
                                                    <div class="self-insured-time">
                                                            <span>${sessionScope.languageJSON.info1095Table.planStartMonth}</span>: 09
                                                    </div>
                                                </div>
                                                <c:if test="${type == 'B' }">
                                                        <%@ include file="../inquiry/info1095BList.jsp"%>
                                                </c:if>
                                                <c:if test="${type == 'C' }">
                                                        <%@ include file="../inquiry/info1095CList.jsp"%>
                                                </c:if>
                                                <table class="table no-border-table print-block-table">
                                                    <tr>
                                                        <td>
                                                            <span>${sessionScope.languageJSON.info1095Table.forPrivacyAct}</span>
                                                        </td>
                                                        <td>Cat. No. 60705M</td>
                                                        <td style="text-align:right">
                                                            <span>${sessionScope.languageJSON.info1095Table.from}</span> <span id="fromWitch1095"></span> (<span class="selectYearSpan">${selectedYear}</span>)
                                                        </td>
                                                    </tr>
                                                </table>
                                            </div>
                                            <div class="PageNext"></div>
                                            <jsp:include page="../report-1095/1095${type}-${selectedYear}.jsp"></jsp:include>
                                    </c:if>
                                </div>
                        </div>
                </section>
            </main>
        </div>
        <%@ include file="../commons/footer.jsp"%>
        <c:if test="${sessionScope.options.enableElecConsnt1095 == true}">
            <div
                class="modal fade"
                id="electronicConsent"
                tabindex="-1"
                role="dialog"
                aria-labelledby="electronicConsent"
                aria-hidden="true"
                data-backdrop="static"
            >
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            
                            <h4 class="modal-title new-title">
                                    ${sessionScope.languageJSON.label.electronic1095Consent}
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
                            <form action="update1095Consent" id="update1095Consent" method="POST">
                            		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                    <input hidden="hidden" type="hidden" name="year" id="consentYear" value="${selectedYear}">
                                    <input hidden="hidden" type="hidden" name="consent" id="consentModal" value="">
                                <div class="form-group">
                                    <label>${sessionScope.languageJSON.label.customMessageHere}</label>
                                     <c:if test="${message==''}">
	                                     <input type="text" class="form-control form-text static" aria-label="${sessionScope.languageJSON.label.blankValueForCustomMessage}" readonly="readonly"/>
                                    </c:if>
                                     <c:if test="${message!=''}">
                                        <textarea class="form-control form-text static" readonly="readonly">${message}</textarea>
                                    </c:if>
                                </div>
                                <div class="form-group">
                                        <input hidden="hidden" type="text" name="consent" id="elecConsnt1095" value="${consent}" aria-label="${sessionScope.languageJSON.accessHint.consent}">
                                        <div class="checkbox mb-2">
                                            <label for="consent">
                                                <input class="consentRadio" type="radio" role="radio" name="electronicConsent" id="consent" aria-checked="false"> 
                                                <span>${sessionScope.languageJSON.label.consentElectronicAccess}</span>
                                            </label>
                                        </div>
                                        <div class="checkbox">
                                                <label for="notConsent">
                                                    <input class="consentRadio" type="radio" role="radio" name="electronicConsent" id="notConsent" aria-checked="false"> 
                                                    <span>${sessionScope.languageJSON.label.donotConsentElectronicAccess}</span>
                                                </label>
                                        </div>
                                </div>
                                <p class="error-hint hide" role="alert" aria-atomic="true" id="noChooseError">${sessionScope.languageJSON.validator.pleaseSelectAgreeWay}</p>
                            </form>
                        </div>
                        <div class="modal-footer">
                                <button
                                    id="saveConsent"
                                    type="button" role="button"
                                    class="btn btn-primary"
                                   
                                >${sessionScope.languageJSON.label.save}</button>
                            <button
                                type="button" role="button"
                                class="btn btn-secondary"
                                data-dismiss="modal"
                               
                            >${sessionScope.languageJSON.label.cancel}</button>
                        </div>
                    </div>
                </div>
            </div>
        </c:if>
    </body>
    <script src="/<%=request.getContextPath().split("/")[1]%>/js/viewJs/inquiry/information1095.js"></script>
</html>
