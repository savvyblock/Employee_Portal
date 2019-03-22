<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ page
language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="en">
    <head>
        <title data-localize="headTitle.setTempApprovers"></title>
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
                        <span data-localize="title.setTemporaryApprovers"></span>
                    </h1>
                    <div class="showSelectSupervisor">
                        <label class="form-title"><span data-localize="label.supervisorHierarchy"></span>: </label>
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
                                    <input hidden="hidden" id="chain" class="chain" name="chain" type="text" value="" aria-label="" data-localize="accessHint.chain">
                                    <div class="form-group in-line flex-auto">
                                        <label class="form-title" for="selectEmpNbr"><span data-localize="label.directReportSupervisor"></span>:</label>
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
                                                data-localize="label.previousLevel"
                                                disabled
                                                ></button>
                                            <button type="button" role="button" class="btn btn-primary  disabled" 
                                            id="nextLevel" 
                                            data-localize="label.nextLevel"
                                            disabled
                                            ></button>
                                    </div>
                                </form>
                                <form hidden="hidden" action="previousLevelFromTempApprovers" id="previousLevel" method="POST">
                                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                        <input hidden="hidden" type="text" value="${level}" name="level" id="preLevel" aria-label="" data-localize="accessHint.level">
                                        <input hidden="hidden" name="chain" type="text" value="" id="preChain" aria-label="" data-localize="accessHint.chain">
                                </form>
                                <h2 class="mb-3 tableTitle">
                                    <span data-localize="label.temporaryApproversFor"></span>
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
                                    <input hidden="hidden" id="chainString" class="chain" name="chain" type="text" value="" aria-label="" data-localize="accessHint.chain">
                                    <input hidden="hidden" id="empNbrForm" name="empNbr" type="text" value="" aria-label="" data-localize="accessHint.employeeNumber">
                                    <input hidden="hidden" id="approverJson" name="approverJson" type="text" value="" aria-label="" data-localize="accessHint.approverJson">
                                <table
                                    class="table border-table setApprovers-list responsive-table"
                                >
                                    <thead>
                                        <tr>
                                            <th data-localize="setTemporaryApprovers.rowNbr"></th>
                                            <th data-localize="setTemporaryApprovers.temporaryApprover"></th>
                                            <th data-localize="setTemporaryApprovers.from"></th>
                                            <th data-localize="setTemporaryApprovers.to"></th>
                                            <th data-localize="setTemporaryApprovers.delete"></th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                            <c:forEach var="tem" items="${tmpApprovers}" varStatus="status">
                                                    <tr class="listTr">
                                                            <td
                                                                class="countIndex"
                                                                data-localize="setTemporaryApprovers.rowNbr" data-localize-location="scope"
                                                            >
                                                                ${status.index + 1}
                                                            </td>
                                                            <td class="empNumber"
                                                            data-localize="setTemporaryApprovers.temporaryApprover" data-localize-location="scope"
                                                            >
                                                            <input hidden="hidden" type="text" class="empId" value="${tem.tmpApprvrEmpNbr}" aria-label="" data-localize="accessHint.employeeId">
                                                            ${tem.tmpApprvrEmpNbr}-${tem.approverName}
                                                        </td>
                                                            <td class="empFrom" data-localize="setTemporaryApprovers.fromDate" data-localize-location="scope">${tem.datetimeFrom}</td>
                                                            <td class="empTo" data-localize="setTemporaryApprovers.toDate" data-localize-location="scope">${tem.datetimeTo}</td>
                                                            <td data-localize="setTemporaryApprovers.delete" data-localize-location="scope">
                                                                <button
                                                                    type="button" role="button"
                                                                    class="a-btn deleteApprover"
                                                                    aria-label="" data-localize="label.delete" data-localize-location="aria-label" data-localize-notText="true"
                                                                >
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
                                                data-localize="setTemporaryApprovers.rowNbr"
                                                data-localize-location="scope"
                                            >
                                                <span id="firstRow"></span>
                                            </td>
                                            <td data-localize="setTemporaryApprovers.temporaryApprover" data-localize-location="scope">
                                                <div class="form-group">
                                                    <input
                                                        class="form-control empControl"
                                                        type="text"
                                                        aria-label=""
                                                        data-localize="setTemporaryApprovers.temporaryApprover"
                                                        name=""
                                                        id="name_01"
                                                    />
                                                </div>
                                            </td>
                                            <td data-localize="setTemporaryApprovers.fromDate" data-localize-location="scope">
                                                <div class="form-group">
                                                    <input
                                                        class="form-control date-control dateFromControl"
                                                        aria-label=""
                                                        data-title="from"
                                                        data-localize="setTemporaryApprovers.fromDate"
                                                        type="text"
                                                        name="temporaryApprovers[${row.index}].fromDateString"
                                                        id="fromDate_01" autocomplete="off"
                                                        placeholder="mm/dd/yyyy"
                                                    />
                                                </div>
                                            </td>
                                            <td data-localize="setTemporaryApprovers.toDate" data-localize-location="scope">
                                                <div class="form-group">
                                                    <input
                                                        class="form-control  date-control dateToControl"
                                                        aria-label=""
                                                        data-localize="setTemporaryApprovers.toDate"
                                                        type="text"
                                                        data-title="to"
                                                        name="temporaryApprovers[${row.index}].toDateString"
                                                        id="toDate_01" autocomplete="off"
                                                        placeholder="mm/dd/yyyy"
                                                    />
                                                </div>
                                            </td>
                                            <td data-localize="setTemporaryApprovers.delete" data-localize-location="scope">
                                                <button
                                                    type="button" role="button"
                                                    class="a-btn"
                                                    onclick="deleteRow(this)"
                                                    aria-label="" data-localize="label.delete" data-localize-location="aria-label" data-localize-notText="true"
                                                >
                                                    <i class="fa fa-trash"></i>
                                                </button>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="no-title" colspan="5" data-localize="label.add" data-localize-location="scope">
                                                <button
                                                    type="button" role="button"
                                                    class="a-btn add-new-row"
                                                    aria-label="" data-localize="label.add" data-localize-location="aria-label" data-localize-notText="true"
                                                >
                                                    <i class="fa fa-plus"></i>
                                                </button>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                                <div class="errorList">
                                    <p class="error-hint hide" role="alert" aria-atomic="true" id="repeatError" data-localize="validator.repeatError"></p>
                                    <p class="error-hint hide" role="alert" aria-atomic="true" id="noResultError" data-localize="validator.noResultError"></p>
                                    <p class="error-hint hide" role="alert" aria-atomic="true" id="errorComplete" data-localize="validator.pleaseCompleteForm"></p>
                                    <p class="error-hint hide" role="alert" aria-atomic="true" id="errorDate" data-localize="validator.startNotBeGreaterThanEndDate"></p>
    
                                </div>
                                <div class="text-right mt-3">
                                    <button
                                        type="button" role="button"
                                        class="btn btn-primary"
                                        aria-label=""
                                        id="saveSet"
                                        data-localize="label.save"
                                    >
                                    </button>
                                    <button
                                        type="button" role="button"
                                        class="btn btn-secondary"
                                        aria-label=""
                                        id="reset"
                                        data-localize="label.reset"
                                    >
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
            <input type="text" id="deleteEmpID" aria-label="" data-localize="accessHint.employeeId">
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
