<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ page
language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="en">
    <head>
        <title>${sessionScope.languageJSON.headTitle.approveLeaveRequest}</title>
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <link rel="stylesheet" href="/<%=request.getContextPath().split("/")[1]%>/css/fullcalendar.min.css" />
        <%@ include file="../commons/header.jsp"%>
    </head>
    <body class="hold-transition sidebar-mini">
        <div class="wrapper">
            <%@ include file="../commons/bar.jsp"%>
			
            <main class="content-wrapper" tabindex="-1">
                    <section class="content">
                            <div class="clearfix section-title">
                                    <h1 class="pageTitle">${sessionScope.languageJSON.title.approveLeaveRequests}</h1>
                                <button class="btn btn-primary pull-right" onclick="showCalendarModal()"
                                data-toggle="modal" data-target="#leaveListCalendarModal" aria-label="${sessionScope.languageJSON.label.switchToCalendarView}">
                                    <i class="fa fa-calendar"></i>
                                </button>
                            </div>
                            <div class="showSelectSupervisor">
                                <label class="form-title"><span>${sessionScope.languageJSON.label.supervisorHierarchy}</span>: </label>
                                <c:forEach var="item" items="${chain}" varStatus="status">
                                    <b> ${item.employeeNumber}:${item.lastName},${item.firstName}</b>
                                 <c:if test="${!status.last}"> ➝ </c:if>
                                </c:forEach>
                            </div>
                            <br/>
                            <div class="container-fluid">
                                    <form
                                    class="no-print searchForm"
                                    action="nextLevelFromApproveLeave"
                                    id="filterSupervisor"
                                    method="POST"
                                >
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <span hidden="hidden" id="chainValue">${chain}</span>
                                    <input hidden="hidden" id="chain" name="chain" type="text" value="" aria-label="${sessionScope.languageJSON.accessHint.chain}"/>
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
                                <form hidden="hidden" action="previousLevelFromApproveLeave" id="previousLevel" method="POST">
                                		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                        <input hidden="hidden" type="text" value="${level}" name="level" id="preLevel" aria-label="${sessionScope.languageJSON.accessHint.level}"/>
                                        <input hidden="hidden" name="chain" type="text" value="" id="preChain" aria-label="${sessionScope.languageJSON.accessHint.chain}"/>
                                </form>
                                <h2 id="approveTableTitle" class="tableTitle" style="padding:0 10px;">
                                    <b>
                                        <span>${sessionScope.languageJSON.approveRequest.leavePendingActionBy}</span>
                                        <span>
                                                <c:forEach var="item" items="${chain}" varStatus="status">
                                                     <c:if test="${status.last}"> 
                                                            ${item.employeeNumber}:${item.lastName},${item.firstName}     
                                                    </c:if>
                                                    </c:forEach>
                                        </span>
                                    </b>
                                </h2>
                                <div class="content-white">
                                    
                                    <table class="table request-list responsive-table">
                                            <thead>
                                                <tr>
                                                        <th>${sessionScope.languageJSON.approveRequest.employee}</th>
                                                        <th>${sessionScope.languageJSON.approveRequest.leaveStartDate}</th>
                                                        <th>${sessionScope.languageJSON.approveRequest.leaveEndDate}</th>
                                                        <th>${sessionScope.languageJSON.approveRequest.leaveType}</th>
                                                        <th>${sessionScope.languageJSON.approveRequest.absenceReason}</th>
                                                        <th>${sessionScope.languageJSON.approveRequest.leaveRequested}</th>
                                                        <th>${sessionScope.languageJSON.approveRequest.commentLog}</th>
                                                        <th>${sessionScope.languageJSON.approveRequest.supervisorAction}</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                    <c:if test="${isEmpty==false}">
                                                    <c:forEach var="item" items="${leaves}" varStatus="status">
                                                        <c:if test="${item.statusCd !='A' && item.statusCd !='D'}">
                                                        <tr>
                                                            <td data-title="${sessionScope.languageJSON.approveRequest.employee}" scope="${sessionScope.languageJSON.approveRequest.employee}">${item.firstName} ${item.lastName}</td>
                                                            <td data-title="${sessionScope.languageJSON.approveRequest.leaveStartDate}" scope="${sessionScope.languageJSON.approveRequest.leaveStartDate}">${item.LeaveStartDate}</td>
                                                            <td data-title="${sessionScope.languageJSON.approveRequest.leaveEndDate}" scope="${sessionScope.languageJSON.approveRequest.leaveEndDate}">${item.LeaveEndDate}</td>
                                                            <td data-title="${sessionScope.languageJSON.approveRequest.leaveType}" scope="${sessionScope.languageJSON.approveRequest.leaveType}">
                                                                    <c:forEach var="type" items="${leaveTypes}" varStatus="statusType">
                                                                            <c:if test="${type.code==item.LeaveType}">${type.description}</c:if>
                                                                    </c:forEach>
                                                            </td>
                                                            <td data-title="${sessionScope.languageJSON.approveRequest.absenceReason}" scope="${sessionScope.languageJSON.approveRequest.absenceReason}">
                                                                    <c:forEach var="reason" items="${absRsns}" varStatus="statusReason">
                                                                            <c:if test="${reason.code==item.LeaveType}">${reason.description}</c:if>
                                                                    </c:forEach>
                                                            </td>
                                                            
                                                            <td data-title="${sessionScope.languageJSON.approveRequest.leaveRequested}" scope="${sessionScope.languageJSON.approveRequest.leaveRequested}"> 
                                                                    ${item.lvUnitsUsed}
                                                                <span>${sessionScope.languageJSON.label.days}</span>
                                                            </td>
                                                            <td scope="${sessionScope.languageJSON.approveRequest.commentLog}" scope="${sessionScope.languageJSON.approveRequest.commentLog}">
                                                                    <c:forEach var="comment" items="${item.comments}" varStatus="statusComment">
                                                                        <p>${comment.detail}</p>
                                                                    </c:forEach>
                                                            </td>
                                                            <td data-title="${sessionScope.languageJSON.approveRequest.supervisorAction}" scope="${sessionScope.languageJSON.approveRequest.supervisorAction}" style="width:150px;">
                                                                <button class="btn btn-primary sm" id="actionLeave" data-toggle="modal" data-target="#approveModal" 
                                                                onClick="actionLeave('${item.id}')"><span>${sessionScope.languageJSON.label.action}</span></button>
                                                            </td>
                                                        </tr>
                                                    </c:if>
                                                    </c:forEach>
                                                    </c:if>
                                                    <c:if test="${isEmpty==true}">
                                                        <tr>
                                                            <td colspan="8" class="text-center">
                                                                <span>${sessionScope.languageJSON.label.noData}</span>
                                                            </td>
                                                        </tr>
                                                    </c:if>
                                            </tbody>
                    
                                        </table>
                                </div>
                            </div>
                        </section>
            </main>
        </div>
        <%@ include file="../modal/leaveListCalendar.jsp"%>
        <%@ include file="../modal/approveLeaveModal.jsp"%>
        <%@ include file="../commons/footer.jsp"%>
    </body>
    <script>
    var directReportEmployee = eval(${directReportEmployee});
    var chain = eval(${chain});
    var leaves = eval(${leaves});
    var leaveTypes = eval(${leaveTypes});
    var absRsns = eval(${absRsns});
    </script>
    <script src="/<%=request.getContextPath().split("/")[1]%>/js/viewJs/supervisor/approveLeaveRequestList.js"></script>

</html>
