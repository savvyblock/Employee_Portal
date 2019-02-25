<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> <%@ taglib
uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> <%@ page
language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="en">
    <head>
        <title data-localize="headTitle.info1095"></title>
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <%@ include file="../commons/header.jsp"%>
    </head>
    <body class="hold-transition sidebar-mini">
        <div class="wrapper">
            <%@ include file="../commons/bar.jsp"%>

            <main class="content-wrapper" id="content" tabindex="-1">
                <section class="content body-1095">
                            <h2 class="clearfix no-print section-title">
                                <span data-localize="title.info1095"></span>
                                <div class="right-btn pull-right">
                                    <button
                                        class="btn btn-primary"
                                        data-toggle="modal"
                                        data-target="#electronicConsent"
                                        data-localize="label.consent1095"></button>
                                    <button class="btn btn-primary" onclick="doPrint()" data-localize="label.print">
                                    </button>
                                </div>
                            </h2>
                            <div class="content-white EMP-detail info-1095">
                                <c:if test="${isUpdate && isSuccess}">
                                    <span class="error-hint">
                                            <b data-localize="validator.updateWasSuccessful"></b>
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
                                    <div class="form-group in-line">
                                        <label class="form-title" data-localize="label.pleaseSelectYear" for="year"></label
                                        >
                                        <select class="form-control" name="year" id="year" onchange="submitYear()">
                                            <c:forEach var="year" items="${years}" varStatus="years">
                                                <option value="${year}" <c:if test="${year == selectedYear }">selected</c:if>>${year}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="form-group in-line">
                                        <label for="b-1095" class="mr-3">
                                            <input
                                                class="icheck"
                                                type="radio"
                                                name="bc-1095"
                                                id="b-1095"
                                                <c:if test="${type == 'B' }">checked</c:if>
                                                disabled
                                            />
                                            <span data-localize="label.b1095"></span>
                                        </label>
                                        <label for="c-1095">
                                            <input
                                                class="icheck"
                                                type="radio"
                                                name="bc-1095"
                                                id="c-1095"
                                                <c:if test="${type == 'C' }">checked</c:if>
                                                disabled
                                            />
                                            <span data-localize="label.c1095"></span>
                                        </label>
                                    </div>
                                </form>
                                <c:if test="${empty years}">
                                        <br> <br>
                                    <div class="error text-center" data-localize="label.no1095Info"></div>
                                </c:if>
                                <c:if test="${fn:length(years) > 0}">
                                    <p class="table-top-title no-print">
                                        <b>
                                            1095-${type}
                                            <span data-localize="label.information"></span>
                                        </b>
                                    </p>
                                    <table
                                        class="table border-table middle-td-table mb-0 print-block-table"
                                    >
                                        <tr>
                                            <td width="160">
                                                <div>
                                                    <span data-localize="info1095Table.from"></span> <span class="font-24" id="tableTile">1095-${type}</span><br />
                                                    <span data-localize="info1095Table.departmentOfTreasury"></span><br />
                                                </div>
                                            </td>
                                            <td class="no-border-td">
                                                <span class="font-20"
                                                    ><span data-localize="info1095Table.employerProvidedHealth"></span></span
                                                ><br />
                                                · <span data-localize="info1095Table.donotAttach"></span>.<br />
                                                · <span data-localize="info1095Table.goToForInstructions"></span>.
                                            </td>
                                            <td class="no-border-td" width="110">
                                                <div>
                                                    <label for="void" class="print-flex">
                                                        <input
                                                            type="checkbox"
                                                            name="void"
                                                        />
                                                        <span data-localize="info1095Table.void"></span>
                                                    </label>
                                                    <label
                                                        for="corrected"
                                                        class="print-flex"
                                                    >
                                                        <input
                                                            type="checkbox"
                                                            name="corrected"
                                                        />
                                                        <span data-localize="info1095Table.corrected"></span>
                                                    </label>
                                                </div>
                                            </td>
                                            <td class="no-padding">
                                                <div class="border-btm-black">
                                                    <span data-localize="info1095Table.OMBNo"></span> 1545-2251<br /><br />
                                                </div>
                                                <div class="font-24 selectYearSpan">${selectedYear}</div>
                                            </td>
                                        </tr>
                                    </table>
            
                                    <table
                                        class="table border-table mb-0 print-block-table"
                                    >
                                        <tr>
                                            <td colspan="3" class="sub-title" width="50%">
                                                <span data-localize="info1095Table.partIEmployee"></span>
                                            </td>
                                            <td colspan="3">
                                                    <span data-localize="info1095Table.applicableEmployer"></span>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td colspan="2">
                                                1 <span data-localize="info1095Table.nameOfEmployee"></span>
                                                <div class="focused-value">
                                                        ${sessionScope.userDetail.nameF} ${sessionScope.userDetail.nameM} ${sessionScope.userDetail.nameL}
                                                </div>
                                            </td>
                                            <td>
                                                2 <span data-localize="info1095Table.SSN"></span>
                                                <div class="focused-value">SSN</div>
                                            </td>
                                            <td colspan="2">
                                                7 <span data-localize="info1095Table.nameOfEmployer"></span>
                                                <div class="focused-value">${sessionScope.district.name}</div>
                                            </td>
                                            <td>
                                                8 <span data-localize="info1095Table.EIN"></span>
                                                <div class="focused-value">EIN</div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td colspan="3">
                                                3 <span data-localize="info1095Table.streetAddressApt"></span>
                                                <div class="focused-value">
                                                        ${sessionScope.userDetail.addrApt}
                                                </div>
                                            </td>
                                            <td colspan="2">
                                                9 <span data-localize="info1095Table.streetAddressRoomSuite"></span>
                                                <div class="focused-value">
                                                        ${sessionScope.userDetail.addrNbr} ${sessionScope.userDetail.addrStr}
                                                </div>
                                            </td>
                                            <td>
                                                10 <span data-localize="info1095Table.contactTelephoneNum"></span>
                                                <div class="focused-value">
                                                         (${sessionScope.userDetail.phoneArea}) ${sessionScope.userDetail.phoneNbr}
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                4 <span data-localize="info1095Table.cityOrTown"></span>
                                                <div class="focused-value">${sessionScope.district.city}</div>
                                            </td>
                                            <td>
                                                5 <span data-localize="info1095Table.stateOrProvince"></span>
                                                <div class="focused-value">${sessionScope.district.state}</div>
                                            </td>
                                            <td>
                                                6 <span data-localize="info1095Table.countryAndZip"></span>
                                                <div class="focused-value">${sessionScope.userDetail.addrZip}</div>
                                            </td>
                                            <td>
                                                11 <span data-localize="info1095Table.cityOrTown"></span>
                                                <div class="focused-value">${sessionScope.district.city}</div>
                                            </td>
                                            <td>
                                                12 <span data-localize="info1095Table.stateOrProvince"></span>
                                                <div class="focused-value">${sessionScope.district.state}TX</div>
                                            </td>
                                            <td>
                                                13 <span data-localize="info1095Table.countryAndZip"></span>
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
                                                    width="50%"
                                                >
                                                <span data-localize="info1095Table.partIIEmployee"></span>
                                                </td>
                                                <td colspan="7">
                                                        <span data-localize="info1095Table.planStartMonth"></span>:
                                                    <b class="font-20">09</b>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td></td>
                                                <th data-localize="info1095Table.allMonths"></th>
                                                <th data-localize="info1095Table.Jan"></th>
                                                <th data-localize="info1095Table.Feb"></th>
                                                <th data-localize="info1095Table.Mar"></th>
                                                <th data-localize="info1095Table.Apr"></th>
                                                <th data-localize="info1095Table.May"></th>
                                                <th data-localize="info1095Table.Jun"></th>
                                                <th data-localize="info1095Table.Jul"></th>
                                                <th data-localize="info1095Table.Aug"></th>
                                                <th data-localize="info1095Table.Sep"></th>
                                                <th data-localize="info1095Table.Oct"></th>
                                                <th data-localize="info1095Table.Nov"></th>
                                                <th data-localize="info1095Table.Dec"></th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <td class="tr-title">
                                                    14 <span data-localize="info1095Table.offerOfCoverage"></span>
                                                </td>
                                                <td data-localize="info1095Table.allMonths" data-localize-location="scope">2C</td>
                                                <td data-localize="info1095Table.Jan" data-localize-location="scope"></td>
                                                <td data-localize="info1095Table.Feb" data-localize-location="scope"></td>
                                                <td data-localize="info1095Table.Mar" data-localize-location="scope"></td>
                                                <td data-localize="info1095Table.Apr" data-localize-location="scope"></td>
                                                <td data-localize="info1095Table.May" data-localize-location="scope"></td>
                                                <td data-localize="info1095Table.Jun" data-localize-location="scope"></td>
                                                <td data-localize="info1095Table.Jul" data-localize-location="scope"></td>
                                                <td data-localize="info1095Table.Aug" data-localize-location="scope"></td>
                                                <td data-localize="info1095Table.Sep" data-localize-location="scope"></td>
                                                <td data-localize="info1095Table.Oct" data-localize-location="scope"></td>
                                                <td data-localize="info1095Table.Nov" data-localize-location="scope"></td>
                                                <td data-localize="info1095Table.Dec" data-localize-location="scope"></td>
                                            </tr>
                                            <tr>
                                                <td class="tr-title">
                                                    15 <span data-localize="info1095Table.employeeRequiredContribution"></span>
                                                </td>
                                                <td data-localize="info1095Table.allMonths" data-localize-location="scope">
                                                        <span class="unit-dollar" data-localize="info1095Table.dollar">$</span> 0.00
                                                </td>
                                                <td data-localize="info1095Table.Jan" data-localize-location="scope">
                                                        <span class="unit-dollar" data-localize="info1095Table.dollar">$</span> 0.00
                                                </td>
                                                <td data-localize="info1095Table.Feb" data-localize-location="scope">
                                                        <span class="unit-dollar" data-localize="info1095Table.dollar">$</span> 0.00
                                                </td>
                                                <td data-localize="info1095Table.Mar" data-localize-location="scope">
                                                        <span class="unit-dollar" data-localize="info1095Table.dollar">$</span> 0.00
                                                </td>
                                                <td data-localize="info1095Table.Apr" data-localize-location="scope">
                                                        <span class="unit-dollar" data-localize="info1095Table.dollar">$</span> 0.00
                                                </td>
                                                <td data-localize="info1095Table.May" data-localize-location="scope">
                                                        <span class="unit-dollar" data-localize="info1095Table.dollar">$</span> 0.00
                                                </td>
                                                <td data-localize="info1095Table.Jun" data-localize-location="scope">
                                                        <span class="unit-dollar" data-localize="info1095Table.dollar">$</span> 0.00
                                                </td>
                                                <td data-localize="info1095Table.Jul" data-localize-location="scope">
                                                        <span class="unit-dollar" data-localize="info1095Table.dollar">$</span> 0.00
                                                </td>
                                                <td data-localize="info1095Table.Aug" data-localize-location="scope">
                                                        <span class="unit-dollar" data-localize="info1095Table.dollar">$</span> 0.00
                                                </td>
                                                <td data-localize="info1095Table.Sep" data-localize-location="scope">
                                                        <span class="unit-dollar" data-localize="info1095Table.dollar">$</span> 0.00
                                                </td>
                                                <td data-localize="info1095Table.Oct" data-localize-location="scope">
                                                        <span class="unit-dollar" data-localize="info1095Table.dollar">$</span> 0.00
                                                </td>
                                                <td data-localize="info1095Table.Nov" data-localize-location="scope">
                                                        <span class="unit-dollar" data-localize="info1095Table.dollar">$</span> 0.00
                                                </td>
                                                <td data-localize="info1095Table.Dec" data-localize-location="scope">
                                                        <span class="unit-dollar" data-localize="info1095Table.dollar">$</span> 0.00
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="tr-title">
                                                    16 <span data-localize="info1095Table.section4980HSafeHarbor"></span>
                                                </td>
                                                <td data-localize="info1095Table.allMonths" data-localize-location="scope">2C</td>
                                                <td data-localize="info1095Table.Jan" data-localize-location="scope"></td>
                                                <td data-localize="info1095Table.Feb" data-localize-location="scope"></td>
                                                <td data-localize="info1095Table.Mar" data-localize-location="scope"></td>
                                                <td data-localize="info1095Table.Apr" data-localize-location="scope"></td>
                                                <td data-localize="info1095Table.May" data-localize-location="scope"></td>
                                                <td data-localize="info1095Table.Jun" data-localize-location="scope"></td>
                                                <td data-localize="info1095Table.Jul" data-localize-location="scope"></td>
                                                <td data-localize="info1095Table.Aug" data-localize-location="scope"></td>
                                                <td data-localize="info1095Table.Sep" data-localize-location="scope"></td>
                                                <td data-localize="info1095Table.Oct" data-localize-location="scope"></td>
                                                <td data-localize="info1095Table.Nov" data-localize-location="scope"></td>
                                                <td data-localize="info1095Table.Dec" data-localize-location="scope"></td>
                                            </tr>
                                        </tbody>
                                    </table>
                                    <p class="table-top-title no-print">
                                        <b data-localize="info1095Table.coverIndividuals"></b>
                                    </p>
                                    <div class="flex self-insured-flex no-print">
                                        <div class="self-insured-tips">
                                            <span data-localize="info1095Table.ifEmployerProvidedSelfInsurance"></span>
                                        </div>
                                        <div class="self-insured-check">
                                            <label for="selfInsured">
                                                    <span data-localize="info1095Table.selfInsured"></span>:
                                                <input
                                                    class="icheck"
                                                    type="checkbox"
                                                    name="selfInsured"
                                                    id="selfInsured"
                                                    disabled
                                                />
                                            </label>
                                        </div>
                                        <div class="self-insured-time">
                                                <span data-localize="info1095Table.planStartMonth"></span>: 09
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
                                                <span data-localize="info1095Table.forPrivacyAct"></span>
                                            </td>
                                            <td>Cat. No. 60705M</td>
                                            <td style="text-align:right">
                                                <span data-localize="info1095Table.from"></span> <span id="fromWitch1095"></span> (<span class="selectYearSpan">${selectedYear}</span>)
                                            </td>
                                        </tr>
                                    </table>
            
                                    <div class="PageNext"></div>
                                    <jsp:include page="../report-1095/1095${type}-${selectedYear}.jsp"></jsp:include>
                            </c:if>
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
                        <h4 class="modal-title new-title" data-localize="label.electronic1095Consent">
                        </h4>
                    </div>
                    <div class="modal-body">
                        <form action="update1095Consent" id="update1095Consent" method="POST">
                                <input hidden="hidden" type="text" name="year" id="consentYear" value="${selectedYear}" title="" data-localize="accessHint.year">
                                <input hidden="hidden" type="text" name="consent" id="consentModal" value="" title="" data-localize="accessHint.consent">
                            <div class="form-group">
                                <label for="customMessage" data-localize="label.customMessageHere"></label>
                                <div class="form-control form-text static">
                                    ${message}
                                </div>
                            </div>
                            <div class="form-group">
                                    <div class="checkbox mb-2">
                                        <label for="consent">
                                            <input class="icheck" type="radio" name="electronicConsent" id="consent"> 
                                            <span data-localize="label.consentElectronicAccess"></span>
                                        </label>
                                    </div>
                                    <div class="checkbox">
                                            <label for="notConsent">
                                                <input class="icheck" type="radio" name="electronicConsent" id="notConsent"> 
                                                <span data-localize="label.donotConsentElectronicAccess"></span>
                                            </label>
                                    </div>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button
                            type="button"
                            class="btn btn-secondary"
                            data-dismiss="modal"
                            aria-hidden="true"
                            data-localize="label.cancel"
                        ></button>
                    </div>
                </div>
            </div>
        </div>
    </body>
    <script>
        function doPrint() {
            window.print()
        }
        function submitYear(){
            $("#selectCalendar")[0].submit()
        }
        $(function(){
            $('#consent').on('ifChecked', function(event) {
                toggleOptions('Y')
            })
            $('#notConsent').on('ifChecked', function(event) {
                toggleOptions('N')
            })
        })
    function toggleOptions(value){
            $("#consentModal").val(value)
            let year = $("#consentYear").val()
            console.log("year" + year)
            if(year&&year!=''){
                $("#update1095Consent")[0].submit()
            }
            
        }
    </script>
</html>
