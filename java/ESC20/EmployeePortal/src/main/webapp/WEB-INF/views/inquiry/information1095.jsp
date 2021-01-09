<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> <%@ taglib
uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> <%@ page
language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html> 
<html lang="en">
</style>
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
                                    <c:if test="${sessionScope.options.enableElecConsnt1095 == true }">
                                            <button
                                            class="btn btn-primary"
                                            data-toggle="modal"
                                            data-target="#electronicConsent"
                                           >${sessionScope.languageJSON.label.consent1095}</button>
                                    </c:if>
                                    <c:if test="${sessionScope.options.enable1095 == true && selectedYear <= latestYear }">
	                                    <form class="no-print" action="exportPDF" method="POST">
												<input type="hidden" name="${_csrf.parameterName}"
													value="${_csrf.token}" />
												<input type="hidden" name="year" value="${selectedYear}" />
												<input type="hidden" name="type" value="${type}" />
												<input type="hidden" name="BPageNo" value="${BPageNo}" />
												<input type="hidden" name="CPageNo" value="${CPageNo}" />
												<input type="hidden" name="sortOrder" value="${sortOrder}" />
												<input type="hidden" name="sortBy" value="${sortBy}" />
												<button type="submit" role="button" class="btn btn-primary" 
													aria-label="${sessionScope.languageJSON.label.print}">${sessionScope.languageJSON.label.print}</button>
	                                    </form>
                                         </c:if>
                                </div>
                            </div>
                        
                            <div class="toPrint content-white EMP-detail info-1095 heightFull">
                                    <c:if test="${not empty sessionScope.options.message1095}">
                                            <p class="topMsg error-hint" role="alert">${sessionScope.options.message1095}</p>
                                        </c:if>
                                    <div id="updateMsg" style="display:none;">
                                            <span class="error-hint font13" role="alert" aria-atomic="true">
                                                    <b>${sessionScope.languageJSON.validator.updateWasSuccessful}</b>
                                                </span>
                                            <br/>
                                            <br/>
                                    </div>
                                    
                                <form
                                    class="no-print searchForm"
                                    action="information1095ByYear"
                                    id="selectCalendar"
                                    method="POST"
                                >
                                	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                    <div class="form-group in-line  paddingSide-0">
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
                                                <c:if test="${disabled}">disabled</c:if>
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
                                                <c:if test="${disabled}">disabled</c:if>
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
                                                                <span>${sessionScope.constantJSON.info1095Table.from}</span> <span class="font-24" id="tableTile">1095-${type}</span><br />
                                                                <span>${sessionScope.constantJSON.info1095Table.departmentOfTreasury}</span><br />
                                                            </div>
                                                        </td>
                                                        <td class="no-border-td">
                                                            <span class="font-20"
                                                                ><span>${sessionScope.constantJSON.info1095Table.employerProvidedHealth}</span></span
                                                            ><br />
                                                            · <span>${sessionScope.constantJSON.info1095Table.donotAttach}</span>.<br />
                                                            · <span>${sessionScope.constantJSON.info1095Table.goToForInstructions}</span>.
                                                        </td>
                                                        <td class="no-border-td" style="width:110px;">
                                                            <div>
                                                                <label for="void" class="print-flex">
                                                                    <input
                                                                        type="checkbox"
                                                                        name="void"
                                                                        id="void"
                                                                    />
                                                                    <span>${sessionScope.constantJSON.info1095Table.void}</span>
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
                                                                    <span>${sessionScope.constantJSON.info1095Table.corrected}</span>
                                                                </label>
                                                            </div>
                                                        </td>
                                                        <td class="no-padding text-center">
                                                            <div class="border-btm-black">
                                                                <span>${sessionScope.constantJSON.info1095Table.OMBNo}</span> 1545-2251<br /><br />
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
                                                            <span>${sessionScope.constantJSON.info1095Table.partIEmployee}</span>
                                                        </td>
                                                        <td colspan="3">
                                                                <span>${sessionScope.constantJSON.info1095Table.applicableEmployer}</span>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td colspan="2">
                                                            1 <span>${sessionScope.constantJSON.info1095Table.nameOfEmployee}</span>
                                                            <div class="focused-value">
                                                                    ${sessionScope.userDetail.nameF} ${sessionScope.userDetail.nameM} ${sessionScope.userDetail.nameL}
                                                            </div>
                                                        </td>
                                                        <td>
                                                            2 <span>${sessionScope.constantJSON.info1095Table.SSN}</span>
                                                            <div class="focused-value">SSN</div>
                                                        </td>
                                                        <td colspan="2">
                                                            7 <span>${sessionScope.constantJSON.info1095Table.nameOfEmployer}</span>
                                                            <div class="focused-value">${sessionScope.district.name}</div>
                                                        </td>
                                                        <td>
                                                            8 <span>${sessionScope.constantJSON.info1095Table.EIN}</span>
                                                            <div class="focused-value">EIN</div>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td colspan="3">
                                                            3 <span>${sessionScope.constantJSON.info1095Table.streetAddressApt}</span>
                                                            <div class="focused-value">
                                                                    ${sessionScope.userDetail.addrApt}
                                                            </div>
                                                        </td>
                                                        <td colspan="2">
                                                            9 <span>${sessionScope.constantJSON.info1095Table.streetAddressRoomSuite}</span>
                                                            <div class="focused-value">
                                                                    ${sessionScope.userDetail.addrNbr} ${sessionScope.userDetail.addrStr}
                                                            </div>
                                                        </td>
                                                        <td>
                                                            10 <span>${sessionScope.constantJSON.info1095Table.contactTelephoneNum}</span>
                                                            <div class="focused-value">
                                                                    (${sessionScope.userDetail.phoneArea}) ${sessionScope.userDetail.phoneNbr}
                                                            </div>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td>
                                                            4 <span>${sessionScope.constantJSON.info1095Table.cityOrTown}</span>
                                                            <div class="focused-value">${sessionScope.district.city}</div>
                                                        </td>
                                                        <td>
                                                            5 <span>${sessionScope.constantJSON.info1095Table.stateOrProvince}</span>
                                                            <div class="focused-value">${sessionScope.district.state}</div>
                                                        </td>
                                                        <td>
                                                            6 <span>${sessionScope.constantJSON.info1095Table.countryAndZip}</span>
                                                            <div class="focused-value">${sessionScope.userDetail.addrZip}</div>
                                                        </td>
                                                        <td>
                                                            11 <span>${sessionScope.constantJSON.info1095Table.cityOrTown}</span>
                                                            <div class="focused-value">${sessionScope.district.city}</div>
                                                        </td>
                                                        <td>
                                                            12 <span>${sessionScope.constantJSON.info1095Table.stateOrProvince}</span>
                                                            <div class="focused-value">${sessionScope.district.state}TX</div>
                                                        </td>
                                                        <td>
                                                            13 <span>${sessionScope.constantJSON.info1095Table.countryAndZip}</span>
                                                            <div class="focused-value">${sessionScope.district.zip}-${sessionScope.district.zip4}</div>
                                                        </td>
                                                    </tr>
                                                </table>
                                                <c:if test="${type == 'B' }">
                                                        <%@ include file="../inquiry/info1095BList.jsp"%>
                                                </c:if>
                                                <c:if test="${type == 'C' }">
                                                        <%@ include file="../inquiry/info1095CList.jsp"%>
                                                </c:if>
                                                <table class="table no-border-table print-block-table">
                                                    <tr>
                                                        <td>
                                                            <span>${sessionScope.constantJSON.info1095Table.forPrivacyAct}</span>
                                                        </td>
                                                        <td>Cat. No. 60705M</td>
                                                        <td style="text-align:right">
                                                            <span>${sessionScope.constantJSON.info1095Table.from}</span> <span id="fromWitch1095"></span> (<span class="selectYearSpan">${selectedYear}</span>)
                                                        </td>
                                                    </tr>
                                                </table>
                                            </div>
                                            <div class="PageNext"></div>
                                           <%--  <div class="forAria">
                                               1095${type}-${selectedYear}
                                            </div> --%>
                                            <jsp:include page="../report-1095/1095${fn:trim(type)}-${fn:trim(selectedYear)}.jsp"></jsp:include>
                                    </c:if>
                                </div>
                                <form id="changePageFormC" hidden="hidden" action="sortOrChangePageForTypeC" method="POST">
                                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                        <input type="hidden" name="year" value="${selectedYear}">
                                                <input type="hidden" name="CPageNo" id="selectPageNowC">
                                                <input type="hidden" name="sortBy">
                                </form>
                                <form id="changePageFormB" hidden="hidden" action="sortOrChangePageForTypeB" method="POST">
                                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                    <input type="hidden" name="year" value="${selectedYear}">
                                        <input type="hidden" name="BPageNo" id="selectPageNowB">
                                        <input type="hidden" name="sortBy">
                                </form>
                        </div>
                </section>
            </main>
        </div>
        <input type="hidden" name="consentCancel" id="elecConsnt1095Cancel" value="${sessionScope.cancel1095Consent}" aria-hidden="true">
        <%@ include file="../commons/footer.jsp"%>
        <c:if test="${sessionScope.options.enableElecConsnt1095 == true && empty consent}">
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
                            		<input type="hidden" name="${_csrf.parameterName}" id="csrfmiddlewaretokenConsent" value="${_csrf.token}"/>
                                    <input hidden="hidden" type="hidden" name="year" id="consentYear" value="${selectedYear}">
                                    <input hidden="hidden" type="hidden" name="consent" id="consentModal" value="">
                                <div class="form-group">
                                    <label for="consentText">${sessionScope.languageJSON.label.customMessageHere}</label>
                                     <c:if test="${message==''}">
	                                     <input id="consentText" type="text" class="form-control form-text static" aria-label="${sessionScope.languageJSON.label.blankValueForCustomMessage}" readonly="readonly"/>
                                    </c:if>
                                     <c:if test="${message!=''}">
                                        <textarea id="consentText" class="form-control form-text static" aria-label="${sessionScope.languageJSON.label.blankValueForCustomMessage}" readonly="readonly">${message}</textarea>
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
                                <p class="error-hint" role="alert" aria-atomic="true" id="noChooseError" style="display:none;">${sessionScope.languageJSON.validator.pleaseSelectAgreeWay}</p>
                            </form>
                        </div>
                        <div class="modal-footer">
                                <button
                                    id="saveConsent"
                                    type="button" role="button"
                                    class="btn btn-primary"
                                   
                                >${sessionScope.languageJSON.label.save}</button>
                            <button
                                id="cancelConsent"
                                type="button" role="button"
                                class="btn btn-secondary"
                                data-dismiss="modal"
                                onclick="submitCancelConsent()"
                            >${sessionScope.languageJSON.label.cancel}</button>
                        </div>
                    </div>
                </div>
            </div>
        </c:if>
    </body>
    <script src="/<%=request.getContextPath().split("/")[1]%>/js/viewJs/inquiry/information1095.js"></script>
</html>
