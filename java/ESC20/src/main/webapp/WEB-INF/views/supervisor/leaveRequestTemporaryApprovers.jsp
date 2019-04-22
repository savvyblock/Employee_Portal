<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ page
language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="en">
    <head>
        <title>${sessionScope.languageJSON.headTitle.setTempApprovers}</title>
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <link rel="stylesheet" href="/<%=request.getContextPath().split("/")[1]%>/css/fullcalendar.min.css" />
        <%@ include file="../commons/header.jsp"%>
    </head>
    <body class="hold-transition sidebar-mini">
        <div class="wrapper">
            <%@ include file="../commons/bar.jsp"%>
			
            <main class="content-wrapper" tabindex="-1">
                    <section class="content">
                    <h1 class="clearfix section-title">
                        <span>${sessionScope.languageJSON.title.setTemporaryApprovers}</span>
                    </h1>
                    <div class="showSelectSupervisor">
                        <label class="form-title"><span>${sessionScope.languageJSON.label.supervisorHierarchy}</span>: </label>
                        <c:forEach var="item" items="${chain}" varStatus="status">
                             <b> ${item.employeeNumber}:${item.lastName},${item.firstName}</b>
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
                                                <option value="${item.employeeNumber}">${item.selectOptionLabel}</option>
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
                                <h2 class="mb-3 tableTitle">
                                    <span>${sessionScope.languageJSON.label.temporaryApproversFor}</span>
                                    <b class="highlight">
                                            <c:forEach var="item" items="${chain}" varStatus="status">
                                                    <c:if test="${status.last}"><span id="currentEmployee">${item.employeeNumber}</span>:${item.lastName},${item.firstName} </c:if>
                                            </c:forEach>
                                        </b
                                    >
                                        </h2>
                        <div class="content-white EMP-detail">
                            
                            <form action="saveTempApprovers" id="saveTempApprovers" method="POST">
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                    <input hidden="hidden" id="chainString" class="chain" name="chain" type="text" value="" aria-label="${sessionScope.languageJSON.accessHint.chain}"/>
                                    <input hidden="hidden" id="empNbrForm" name="empNbr" type="text" value="" aria-label="${sessionScope.languageJSON.accessHint.employeeNumber}"/>
                                    <input hidden="hidden" id="approverJson" name="approverJson" type="text" value="" aria-label="${sessionScope.languageJSON.accessHint.approverJson}"/>
                                <table summary="${sessionScope.languageJSON.accessHint.deletedRowSummary}"
                                    class="table border-table setApprovers-list responsive-table"
                                >
                                    <thead>
                                        <tr>
                                            <th>${sessionScope.languageJSON.setTemporaryApprovers.rowNbr}</th>
                                            <th>${sessionScope.languageJSON.setTemporaryApprovers.temporaryApprover}</th>
                                            <th>${sessionScope.languageJSON.setTemporaryApprovers.from}</th>
                                            <th>${sessionScope.languageJSON.setTemporaryApprovers.to}</th>
                                            <th>${sessionScope.languageJSON.setTemporaryApprovers.delete}</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                            <c:forEach var="tem" items="${tmpApprovers}" varStatus="status">
                                                    <tr class="listTr">
                                                            <td
                                                                class="countIndex"
                                                                data-title="${sessionScope.languageJSON.setTemporaryApprovers.rowNbr}"
                                                                scope="${sessionScope.languageJSON.setTemporaryApprovers.rowNbr}"
                                                            >
                                                                ${status.index + 1}
                                                            </td>
                                                            <td class="empNumber"
                                                            data-title="${sessionScope.languageJSON.setTemporaryApprovers.temporaryApprover}"
                                                            scope="${sessionScope.languageJSON.setTemporaryApprovers.temporaryApprover}"
                                                            >
                                                            <input hidden="hidden" type="text" class="empId" value="${tem.tmpApprvrEmpNbr}" aria-label="${sessionScope.languageJSON.accessHint.employeeId}">
                                                            ${tem.tmpApprvrEmpNbr}-${tem.approverName}
                                                        </td>
                                                            <td class="empFrom" data-title="${sessionScope.languageJSON.setTemporaryApprovers.fromDate}" scope="${sessionScope.languageJSON.setTemporaryApprovers.fromDate}">${tem.datetimeFrom}</td>
                                                            <td class="empTo" data-title="${sessionScope.languageJSON.setTemporaryApprovers.toDate}" scope="${sessionScope.languageJSON.setTemporaryApprovers.toDate}">${tem.datetimeTo}</td>
                                                            <td data-title="${sessionScope.languageJSON.setTemporaryApprovers.delete}" scope="${sessionScope.languageJSON.setTemporaryApprovers.delete}">
                                                                <button
                                                                    type="button" role="button"
                                                                    class="a-btn deleteApprover"
                                                                    aria-label="${sessionScope.languageJSON.label.delete}">
                                                                    <i
                                                                        class="fa fa-trash"
                                                                    ></i>
                                                                </button>
                                                            </td>
                                                        </tr>
                                            </c:forEach>
                                        
                                        <tr class="approver_tr">
                                            <td
                                                class="countIndex"
                                                scope="${sessionScope.languageJSON.setTemporaryApprovers.rowNbr}">
                                                <span id="firstRow"></span>
                                            </td>
                                            <td scope="${sessionScope.languageJSON.setTemporaryApprovers.temporaryApprover}">
                                                <div class="form-group">
                                                    <input
                                                        class="form-control empControl"
                                                        type="text"
                                                        aria-label="${sessionScope.languageJSON.setTemporaryApprovers.temporaryApprover}"
                                                        name=""
                                                        id="name_01"
                                                    />
                                                </div>
                                            </td>
                                            <td scope="${sessionScope.languageJSON.setTemporaryApprovers.fromDate}">
                                                <div class="form-group">
                                                    <input
                                                        class="form-control date-control dateFromControl"
                                                        aria-label="${sessionScope.languageJSON.setTemporaryApprovers.fromDate}"
                                                        data-title="from"
                                                        type="text"
                                                        name="temporaryApprovers[${row.index}].fromDateString"
                                                        id="fromDate_01" autocomplete="off"
                                                        placeholder="mm/dd/yyyy"
                                                    />
                                                </div>
                                            </td>
                                            <td scope="${sessionScope.languageJSON.setTemporaryApprovers.toDate}">
                                                <div class="form-group">
                                                    <input
                                                        class="form-control  date-control dateToControl"
                                                        aria-label="${sessionScope.languageJSON.setTemporaryApprovers.toDate}"
                                                        type="text"
                                                        data-title="to"
                                                        name="temporaryApprovers[${row.index}].toDateString"
                                                        id="toDate_01" autocomplete="off"
                                                        placeholder="mm/dd/yyyy"
                                                    />
                                                </div>
                                            </td>
                                            <td scope="${sessionScope.languageJSON.setTemporaryApprovers.delete}">
                                                <button
                                                    type="button" role="button"
                                                    class="a-btn"
                                                    onclick="deleteRow(this)"
                                                    aria-label="${sessionScope.languageJSON.setTemporaryApprovers.delete}">
                                                    <i class="fa fa-trash"></i>
                                                </button>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="no-title" colspan="5" scope="${sessionScope.languageJSON.label.add}">
                                                <button
                                                    type="button" role="button"
                                                    class="a-btn add-new-row"
                                                    aria-label="${sessionScope.languageJSON.label.add}">
                                                    <i class="fa fa-plus"></i>
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
    
                                </div>
                                <div class="text-right mt-3">
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
                            </form>
                             <form hidden="hidden" action="leaveRequestTemporaryApprovers" id="resetForm" method="POST">
                             	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                             </form>
                        </div>
                    </div>
                </section>
            </main>
        </div>
        <form hidden="hidden" action="" id="">
        	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <input type="text" id="deleteEmpID" aria-label="${sessionScope.languageJSON.accessHint.employeeId}">
        </form>
        <%@ include file="../commons/footer.jsp"%>
    </body>
    <script>
    var directReportEmployee = eval(${directReportEmployee});
    var addedApprover = eval(${tmpApprovers});
    var chain = eval(${chain});
    </script>
    <script src="/<%=request.getContextPath().split("/")[1]%>/js/viewJs/supervisor/leaveRequestTemporaryApprovers.js"></script>

</html>
