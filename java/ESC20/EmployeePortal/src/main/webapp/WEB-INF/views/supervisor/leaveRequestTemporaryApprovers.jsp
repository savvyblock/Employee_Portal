<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ page
language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html> 
<html lang="en">
    <head>
        <title>${sessionScope.languageJSON.headTitle.setTempApprovers}</title>
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <link rel="stylesheet" href="/<%=request.getContextPath().split("/")[1]%>/css/fullcalendar.min.css" />
        <link rel="stylesheet" href="/<%=request.getContextPath().split("/")[1]%>/css/jquery-ui.css" />
        <%@ include file="../commons/header.jsp"%>
    </head>
    <body class="hold-transition sidebar-mini">
        <div class="wrapper">
            <%@ include file="../commons/bar.jsp"%>
			
            <main class="content-wrapper" tabindex="-1">
                    <section class="content">
                    <div class="clearfix section-title">
                        <h1 class="pageTitle">${sessionScope.languageJSON.title.setTemporaryApprovers}</h1>
                        <button class="btn btn-primary pull-right" onclick="showCalendarModal()"
                                data-toggle="modal" data-target="#leaveListCalendarModal" aria-label="${sessionScope.languageJSON.label.switchToCalendarView}">
                                    <i class="fa fa-calendar"></i>
                                </button>
                    </div>
                    <div class="showSelectSupervisor">
                        <label class="form-title"><span>${sessionScope.languageJSON.label.supervisorHierarchy}</span>: </label>
                        <c:forEach var="item" items="${chain}" varStatus="status">
                             <b> ${item.employeeNumber}: ${item.lastName}, ${item.firstName} ${item.middleName} </b>
                             <c:if test="${!status.last}"> ➝ </c:if>
                        </c:forEach>
                    </div>
                    <div class="container-fluid">
                            <form
                                    class="no-print searchForm"
                                    action="nextLevelFromTempApprovers"
                                    id="filterSupervisor"
                                    method="POST"
                                >
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <span hidden="hidden" id="chainValue">${chain}</span>
                                    <input hidden="hidden" id="chain" class="chain" name="chain" type="text" value="" aria-label="${sessionScope.languageJSON.accessHint.chain}"/>
                                    <div class="form-group in-line flex-auto">
                                        <label class="form-title" for="selectEmpNbr"><span>${sessionScope.languageJSON.label.directReportSupervisor}</span>:</label>
                                        <select  class="form-control" name="selectEmpNbr" onchange="changeLevel()"
                                            id="selectEmpNbr">
                                            <c:forEach var="item" items="${directReportEmployee}" varStatus="count">
                                                <c:choose>
												   <c:when test="${item.selectOptionLabel==''}">
											   			<option value="${item.employeeNumber}">&nbsp;</option>
												   </c:when>
												   <c:otherwise>
												        <option value="${item.employeeNumber}">
                                                            ${item.employeeNumber}: ${item.lastName}, ${item.firstName} ${item.middleName} 
                                                        </option>
												   </c:otherwise>
												</c:choose>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="form-group in-line flex-auto">
                                            <button type="button" role="button" class="btn btn-primary disabled" 
                                                id="prevLevel" 
                                                disabled
                                                >${sessionScope.languageJSON.label.previousLevel}</button>
                                            <button type="button" role="button" class="btn btn-primary  disabled" 
                                            id="nextLevel" 
                                            disabled
                                            >${sessionScope.languageJSON.label.nextLevel}</button>
                                    </div>
                                </form>
                                <form hidden="hidden" action="previousLevelFromTempApprovers" id="previousLevel" method="POST">
                                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                        <input hidden="hidden" type="text" value="${level}" name="level" id="preLevel" aria-label="${sessionScope.languageJSON.accessHint.level}"/>
                                        <input hidden="hidden" name="chain" type="text" value="" id="preChain" aria-label="${sessionScope.languageJSON.accessHint.chain}"/>
                                </form>
                                <h2 class="tableTitle" style="margin-top:5px;">
                                    <span>${sessionScope.languageJSON.label.temporaryApproversFor}</span>
                                    <b class="highlight">
                                            <c:forEach var="item" items="${chain}" varStatus="status">
                                                    <c:if test="${status.last}"><span id="currentEmployee">${item.employeeNumber}</span>: ${item.lastName}, ${item.firstName} </c:if>
                                            </c:forEach>
                                        </b>
                                        </h2>
                        <div class="content-white EMP-detail">
                            <c:if test="${saveSuccess}">
                            <p class="success-hint"><b>${sessionScope.languageJSON.validator.saveSuccess}</b></p>
                            </c:if>
                            <form action="saveTempApprovers" id="saveTempApprovers" method="POST">
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                    <input hidden="hidden" id="chainString" class="chain" name="chain" type="text" value="" aria-label="${sessionScope.languageJSON.accessHint.chain}"/>
                                    <input hidden="hidden" id="empNbrForm" name="empNbr" type="text" value="" aria-label="${sessionScope.languageJSON.accessHint.employeeNumber}"/>
                                    <input hidden="hidden" id="approverJson" name="approverJson" type="text" value="" aria-label="${sessionScope.languageJSON.accessHint.approverJson}"/>
                                    <div class="text-left">
                                        <button
                                            type="button" role="button"
                                            class="btn btn-primary"
                                            aria-label="${sessionScope.languageJSON.label.save}"
                                            id="saveSet">
                                            ${sessionScope.languageJSON.label.save}
                                        </button>
                                        <button
                                            type="button" role="button"
                                            class="btn btn-secondary"
                                            aria-label="${sessionScope.languageJSON.label.reset}"
                                            id="reset">
                                            ${sessionScope.languageJSON.label.reset}
                                        </button>
                                    </div>
                                    <p id="tableSummary" class="forAria">${sessionScope.languageJSON.accessHint.deletedRowSummary}</p>    
                                <table aria-describedby="tableSummary"
                                    class="table border-table setApprovers-list responsive-table"
                                >
                                    <thead>
                                        <tr>
                                            <th scope="col" style="width:55px;">${sessionScope.languageJSON.setTemporaryApprovers.delete}</th>
                                            <th scope="col" style="width:65px;">${sessionScope.languageJSON.setTemporaryApprovers.rowNbr}</th>
                                            <th scope="col" style="width:350px;">${sessionScope.languageJSON.setTemporaryApprovers.temporaryApprover}</th>
                                            <th scope="col">${sessionScope.languageJSON.setTemporaryApprovers.from}</th>
                                            <th scope="col">${sessionScope.languageJSON.setTemporaryApprovers.to}</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="tem" items="${tmpApprovers}" varStatus="status">
                                                <tr class="listTr">
                                                   <td style="text-align:center;" data-title="${sessionScope.languageJSON.setTemporaryApprovers.delete}">
                                                            <button
                                                                type="button" 
                                                                class="a-btn deleteApprover"
                                                                aria-label="${sessionScope.languageJSON.label.delete}">
                                                                <i
                                                                    class="fa fa-trash"
                                                                ></i>
                                                            </button>
                                                        </td>
                                                        <td
                                                            class="countIndex"
                                                            data-title="${sessionScope.languageJSON.setTemporaryApprovers.rowNbr}"
                                                        >
                                                            ${status.index + 1}
                                                        </td>
                                                        <td class="empNumber"
                                                        data-title="${sessionScope.languageJSON.setTemporaryApprovers.temporaryApprover}">
                                                        <input type="hidden" class="empId" value="${tem.tmpApprvrEmpNbr}">
                                                        <input type="hidden" class="trId" value="${tem.id}">
                                                        ${tem.tmpApprvrEmpNbr}: ${tem.approverName}
                                                    </td>
                                                        <td class="empFrom" data-title="${sessionScope.languageJSON.setTemporaryApprovers.from}">${tem.datetimeFrom}</td>
                                                        <td class="empTo" data-title="${sessionScope.languageJSON.setTemporaryApprovers.to}" >${tem.datetimeTo}</td>
                                                     
                                                    </tr>
                                        </c:forEach>
                                        
                                        <tr class="approver_tr">
                                            <td style="text-align:center;" data-title="${sessionScope.languageJSON.setTemporaryApprovers.delete}">
                                                <button
                                                    type="button" role="button"
                                                    class="a-btn"
                                                    onclick="deleteRow(this)"
                                                    aria-label="${sessionScope.languageJSON.setTemporaryApprovers.delete}">
                                                    <i class="fa fa-trash"></i>
                                                </button>
                                            </td>
                                            <td data-title="${sessionScope.languageJSON.setTemporaryApprovers.rowNbr}"
                                                class="countIndex">
                                                <span id="firstRow"></span>
                                            </td>
                                            <td data-title="${sessionScope.languageJSON.setTemporaryApprovers.temporaryApprover}">
                                                <div class="form-group">
                                                    <input
                                                        class="form-control empControl lastRowTabOut"
                                                        type="text"
                                                        aria-label="${sessionScope.languageJSON.setTemporaryApprovers.temporaryApprover}"
                                                        name="name_01"
                                                        id="name_01"
                                                    />
                                                    <small class="help-block invalid" role="alert" aria-atomic="true" style="display:none;">${sessionScope.languageJSON.validator.employeeInvalid}</small>
                                                    <small class="help-block required" role="alert" aria-atomic="true" style="display:none;">${sessionScope.languageJSON.validator.enterSelectEmp}</small>
                                                </div>
                                            </td>
                                            <td data-title="${sessionScope.languageJSON.setTemporaryApprovers.from}">
                                                <div class="form-group">
                                                        <div class="fDateGroup date dateFromControl" data-date-format="mm-dd-yyyy">
                                                            <button class="prefix" type="button" aria-label="${sessionScope.languageJSON.label.showDatepicker}"><i class="fa fa-calendar"></i></button>
                                                            <input class="form-control dateInput date-control"
                                                            aria-label="${sessionScope.languageJSON.setTemporaryApprovers.fromDate}"
                                                            data-title="from"
                                                            type="text"
                                                            name="temporaryApprovers[${row.index}].fromDateString"
                                                            id="fromDate_01" autocomplete="off"
                                                            placeholder="mm-dd-yyyy"
                                                        />
                                                    </div>
                                                    <small class="help-block required" role="alert" aria-atomic="true" style="display:none;">${sessionScope.languageJSON.validator.selectAFromDate}</small>
                                                    <small class="help-block overlapsDate" role="alert" aria-atomic="true" style="display:none;">${sessionScope.languageJSON.validator.overlapsDate}</small>
                                                    <!-- <input
                                                        class="form-control date-control dateFromControl"
                                                        aria-label="${sessionScope.languageJSON.setTemporaryApprovers.fromDate}"
                                                        data-title="from"
                                                        type="text"
                                                        name="temporaryApprovers[${row.index}].fromDateString"
                                                        id="fromDate_01" autocomplete="off"
                                                        placeholder="mm-dd-yyyy"
                                                    /> -->
                                                </div>
                                            </td>
                                            <td data-title="${sessionScope.languageJSON.setTemporaryApprovers.to}">
                                                <div class="form-group">
                                                        <div class="fDateGroup date dateToControl" data-date-format="mm-dd-yyyy">
                                                            <button class="prefix" type="button" aria-label="${sessionScope.languageJSON.label.showDatepicker}"><i class="fa fa-calendar"></i></button>
                                                            <input class="form-control dateInput  date-control"
                                                            aria-label="${sessionScope.languageJSON.setTemporaryApprovers.toDate}"
                                                            type="text"
                                                            name="temporaryApprovers[${row.index}].toDateString"
                                                            id="toDate_01" autocomplete="off"
                                                            placeholder="mm-dd-yyyy"
                                                        />
                                                        </div>
                                                        <small class="help-block required" role="alert" aria-atomic="true" style="display:none;">${sessionScope.languageJSON.validator.selectAToDate}</small>
                                                        <small class="help-block overlapsDate" role="alert" aria-atomic="true" style="display:none;">${sessionScope.languageJSON.validator.overlapsDate}</small>
                                                    <!-- <input
                                                        class="form-control  date-control dateToControl"
                                                        aria-label="${sessionScope.languageJSON.setTemporaryApprovers.toDate}"
                                                        type="text"
                                                        data-title="to"
                                                        name="temporaryApprovers[${row.index}].toDateString"
                                                        id="toDate_01" autocomplete="off"
                                                        placeholder="mm-dd-yyyy"
                                                    /> -->
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="no-title" colspan="5">
                                                <button
                                                    type="button" role="button"
                                                    class="a-btn add-new-row"
                                                    aria-label="${sessionScope.languageJSON.label.add}">
                                                    <i class="fa fa-plus"></i>
                                                    <span>${sessionScope.languageJSON.label.add}</span>
                                                </button>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                                <div class="errorList">
                                    <p class="error-hint hide" role="alert" aria-atomic="true" id="repeatError">${sessionScope.languageJSON.validator.repeatError}</p>
                                    <p class="error-hint hide" role="alert" aria-atomic="true" id="noResultError">${sessionScope.languageJSON.validator.noResultError}</p>
                                    <p class="error-hint hide" role="alert" aria-atomic="true" id="errorComplete">${sessionScope.languageJSON.validator.pleaseCompleteForm}</p>
                                    <p class="error-hint hide" role="alert" aria-atomic="true" id="errorDate">${sessionScope.languageJSON.validator.startNotBeGreaterThanEndDate}</p>
                                    <p class="success-hint" style="display:none;" role="alert" aria-atomic="true" id="saveSuccess">${sessionScope.languageJSON.validator.saveSuccess}</p>
                                </div>
                                
                            </form>
                             <form hidden="hidden" action="leaveRequestTemporaryApprovers" id="resetForm" method="POST">
                             	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                             </form>
                        </div>
                    </div>
                </section>
            </main>
        </div>
        <form hidden="hidden"  id="hiddenForm">
        	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <input type="text" id="deleteEmpID" aria-label="${sessionScope.languageJSON.accessHint.employeeId}">
        </form>
        <%@ include file="../commons/footer.jsp"%>
        <%@ include file="../modal/leaveListCalendar.jsp"%>
    </body>
    <script>
    var directReportEmployee = eval(${directReportEmployee});
    var addedApprover = eval(${tmpApprovers});
    var chain = eval(${chain});
    var leaveTypes = eval(${leaveTypes});
    var absRsns = eval(${absRsns});
    var employeeInvalid = '${sessionScope.languageJSON.validator.employeeInvalid}'
    var selectAFromDate = '${sessionScope.languageJSON.validator.selectAFromDate}'
    var selectAToDate = '${sessionScope.languageJSON.validator.selectAToDate}'
    var enterSelectEmp = '${sessionScope.languageJSON.validator.enterSelectEmp}'
    var overlapsDate = '${sessionScope.languageJSON.validator.overlapsDate}'
    </script>
    <script src="/<%=request.getContextPath().split("/")[1]%>/js/plug-in/jquery-ui.js"></script>
    <script src="/<%=request.getContextPath().split("/")[1]%>/js/viewJs/supervisor/leaveRequestTemporaryApprovers.js"></script>

</html>
