<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ page
language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="en">
    <head>
        <title data-localize="headTitle.approveLeaveRequest"></title>
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
                                    <h1 class="pageTitle" data-localize="title.approveLeaveRequests"></h1>
                                <button class="btn btn-primary pull-right" onclick="showCalendarModal()"
                                data-toggle="modal" data-target="#leaveListCalendarModal" aria-label="" data-localize="label.switchToCalendarView" data-localize-location="aria-label" data-localize-notText="true">
                                    <i class="fa fa-calendar"></i>
                                </button>
                            </div>
                            <div class="showSelectSupervisor">
                                <label class="form-title"><span data-localize="label.supervisorHierarchy"></span>: </label>
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
                                    <input hidden="hidden" id="chain" name="chain" type="text" value="" aria-label="" data-localize="accessHint.chain">
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
                                <form hidden="hidden" action="previousLevelFromApproveLeave" id="previousLevel" method="POST">
                                		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                        <input hidden="hidden" type="text" value="${level}" name="level" id="preLevel" aria-label="" data-localize="accessHint.level">
                                        <input hidden="hidden" name="chain" type="text" value="" id="preChain" aria-label="" data-localize="accessHint.chain">
                                </form>
                                <h2 id="approveTableTitle" class="tableTitle" style="padding:0 10px;">
                                    <b>
                                        <span data-localize="approveRequest.leavePendingActionBy"></span>
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
                                                        <th data-localize="approveRequest.employee"></th>
                                                        <th data-localize="approveRequest.leaveStartDate"></th>
                                                        <th data-localize="approveRequest.leaveEndDate"> </th>
                                                        <!-- <th data-localize="approveRequest.startTime"></th>
                                                        <th data-localize="approveRequest.endTime"></th> -->
                                                        <th data-localize="approveRequest.leaveType"></th>
                                                        <th data-localize="approveRequest.absenceReason"></th>
                                                        <th data-localize="approveRequest.leaveRequested"></th>
                                                        <th data-localize="approveRequest.commentLog"></th>
                                                        <th data-localize="approveRequest.supervisorAction"></th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                    <c:if test="${isEmpty==false}">
                                                    <c:forEach var="item" items="${leaves}" varStatus="status">
                                                        <c:if test="${item.statusCd !='A' && item.statusCd !='D'}">
                                                        <tr>
                                                            <td data-localize="approveRequest.employee" data-localize-location="scope">${item.firstName} ${item.lastName}</td>
                                                            <td data-localize="approveRequest.leaveStartDate" data-localize-location="scope">${item.LeaveStartDate}</td>
                                                            <td data-localize="approveRequest.leaveEndDate" data-localize-location="scope">${item.LeaveEndDate}</td>
                                                            <!-- <td data-localize="approveRequest.startTime" data-localize-location="scope">Start Time</td>
                                                            <td data-localize="approveRequest.endTime">End Time</td> -->
                                                            <td data-localize="approveRequest.leaveType" data-localize-location="scope">
                                                                    <c:forEach var="type" items="${leaveTypes}" varStatus="statusType">
                                                                            <c:if test="${type.code==item.LeaveType}">${type.description}</c:if>
                                                                    </c:forEach>
                                                            </td>
                                                            <td data-localize="approveRequest.absenceReason" data-localize-location="scope">
                                                                    <c:forEach var="reason" items="${absRsns}" varStatus="statusReason">
                                                                            <c:if test="${reason.code==item.LeaveType}">${reason.description}</c:if>
                                                                    </c:forEach>
                                                            </td>
                                                            
                                                            <td data-localize="approveRequest.leaveRequested" data-localize-location="scope"> 
                                                                    ${item.lvUnitsUsed}
                                                                <span data-localize="label.days"></span>
                                                            </td>
                                                            <td data-localize="approveRequest.commentLog" data-localize-location="scope">
                                                                    <c:forEach var="comment" items="${item.comments}" varStatus="statusComment">
                                                                        <p>${comment.detail}</p>
                                                                    </c:forEach>
                                                            </td>
                                                            <td style="width:150px;" data-localize="approveRequest.supervisorAction" data-localize-location="scope">
                                                                <button class="btn btn-primary sm" id="actionLeave" data-toggle="modal" data-target="#approveModal" 
                                                                onClick="actionLeave('${item.id}')"><span data-localize="label.action"></span></button>
                                                            </td>
                                                        </tr>
                                                    </c:if>
                                                    </c:forEach>
                                                    </c:if>
                                                    <c:if test="${isEmpty==true}">
                                                        <tr>
                                                            <td colspan="8">
                                                                <span data-localize="label.noData"></span>
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
