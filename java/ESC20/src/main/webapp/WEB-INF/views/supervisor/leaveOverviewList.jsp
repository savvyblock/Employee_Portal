<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ page
language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="en">
    <head>
        <title data-localize="headTitle.leaveOverview"></title>
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <link rel="stylesheet" href="/<%=request.getContextPath().split("/")[1]%>/css/fullcalendar.min.css" />
        <%@ include file="../commons/header.jsp"%>
    </head>
    <body class="hold-transition sidebar-mini">
        <div class="wrapper">
            <%@ include file="../commons/bar.jsp"%>
			
            <main class="content-wrapper" tabindex="-1">
                    <section class="content">
                            <h2 class="clearfix section-title">
                                    <span data-localize="title.leaveOverview"></span>
                                <div class="pull-right right-btn">
					                <button class="btn btn-primary" id="new-btn" data-toggle="modal" data-target="#requestModal" onclick="showRequestForm()" data-localize="label.add"></button>
                                    <button class="btn btn-primary pull-right" style="height:35px;display:flex;align-items:center;"  data-toggle="modal" data-target="#leaveOverviewCalendarModal" 
                                        onclick="showOverviewCalendar()"  aria-label="" data-localize="label.switchToCalendarView" data-localize-location="aria-label" data-localize-notText="true">
                                        <i class="fa fa-calendar"></i>
                                    </button>
                                </div>
                                </h2>
                            <div class="container-fluid">
                                    <div class="showSelectSupervisor">
                                            <label class="form-title"><span data-localize="label.supervisorHierarchy"></span>: </label>
                                            <c:forEach var="item" items="${chain}" varStatus="status">
                                                    <b> ${item.employeeNumber}:${item.lastName},${item.firstName}</b>
                                                    <c:if test="${!status.last}"> ➝ </c:if>
                                            </c:forEach>
                                    </div>
                                    <form
                                    class="no-print searchForm"
                                    action="nextLevelFromLeaveOverview"
                                    id="filterSupervisor"
                                    method="POST"
                                >
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <span hidden="hidden" id="chainValue">${chain}</span>
                                    <input type="hidden" type="text" value="${level}" name="level" id="level" aria-label="" data-localize="accessHint.level">
                                    <input type="hidden" id="chain" name="chain" type="text" value="" aria-label="" data-localize="accessHint.chain">
                                    <input type="hidden" type="text" name="isChangeLevel" class="isChangeLevel"  aria-label="" data-localize="accessHint.whetherChangeLevel">
                                    <div class="form-group in-line flex-auto">
                                        <label class="form-title" for="selectEmpNbr"><span data-localize="label.directReportEmployees"></span>:</label>
                                        <select  class="form-control"name="selectEmpNbr" onchange="changeEmployee()"
                                            id="selectEmpNbr">
                                            <c:forEach var="item" items="${directReportEmployee}" varStatus="count">
                                                <option value="${item.employeeNumber}" <c:if test="${item.employeeNumber==selectedEmployee}">selected</c:if>>${item.selectOptionLabel}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="form-group in-line flex-auto">
                                            <button type="button" class="btn btn-primary disabled" 
                                                id="prevLevel" 
                                                data-localize="label.previousLevel"
                                                disabled
                                                ></button>
                                            <button type="button" class="btn btn-primary  disabled" 
                                            id="nextLevel" 
                                            data-localize="label.nextLevel"
                                            disabled
                                            ></button>
                                    </div>
                                </form>
                                <form hidden="hidden" action="previousLevelFromLeaveOverview" id="previousLevel" method="POST">
                                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                        <input type="hidden" type="text" value="${level}" name="level" id="preLevel"  aria-label="" data-localize="accessHint.level">
                                        <input type="hidden" name="chain" type="text" value="" id="preChain" aria-label="" data-localize="accessHint.chain">
                                </form>
                                <div class="content-white">
                                        
                                        <form
                                        class="no-print searchForm"
                                        action="leaveOverviewList"
                                        id="changeFreqForm"
                                        method="POST"
                                            >
                                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                            <input type="hidden" type="text" name="empNbr" class="employeeNum" value="${selectedEmployee}" aria-label="" data-localize="accessHint.employeeNumber">
                                            <input type="hidden" class="chain" name="chain" type="text" value="" aria-label="" data-localize="accessHint.chain">
                                            <input type="hidden" type="text" name="isChangeLevel" class="isChangeLevel" value="false" aria-label="" data-localize="accessHint.whetherChangeLevel">
                                        <div class="form-group type-group">
                                                <label class="form-title"  for="freq"  data-localize="label.payrollFreq"></label>
                                                <select class="form-control" name="freq" id="freq" onchange="changeFreq()">
                                                    <c:forEach var="freq" items="${availableFreqs}" varStatus="count">
                                                        <option value="${freq.code}" <c:if test="${freq.code == selectedFreq }">selected</c:if>>${freq.description}</option>
                                                    </c:forEach>
                                                </select>
                                        </div>
                                        <div class="form-group">
                                                <label class="form-title" for="SearchStartDate"><span data-localize="label.from"></span>:</label> 
                                                <div class="button-group">
                                                <input
                                                    class="form-control" type="text" name="startDate"
                                                    data-date-format="mm/dd/yyyy"  autocomplete="off"
                                                    aria-label=""
                                                    data-localize="label.mmddyyyyFormat"
                                                    placeholder=""
                                                    title=""
                                                    id="SearchStartDate" value="${startDate}" />
                                                    <button class="clear-btn" type="button" onclick="clearDate(this)"  tabindex="0"   aria-label="" data-localize="label.removeContent" data-localize-location="aria-label" data-localize-notText="true">
                                                        <i class="fa fa-times"></i>
                                                    </button>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="form-title" for="SearchEndDate"> <span data-localize="label.to"></span>: </label> 
                                                <div class="button-group">
                                                <input
                                                    class="form-control" type="text" name="endDate" data-date-format="mm/dd/yyyy"  autocomplete="off"
                                                    aria-label=""
                                                    data-localize="label.mmddyyyyFormat"
                                                    placeholder=""
                                                    title=""
                                                    id="SearchEndDate" value="${endDate}" />
                                                    <button class="clear-btn" type="button" onclick="clearDate(this)" tabindex="0"   aria-label="" data-localize="label.removeContent" data-localize-location="aria-label" data-localize-notText="true">
                                                        <i class="fa fa-times"></i>
                                                    </button>
                                                </div>
                                            </div>
                                            <div class="form-group btn-group">
                                                <div style="margin-top:20px;">
                                                        <button type="submit" class="btn btn-primary" data-localize="leaveBalance.retrieve">
                                                            </button>
                                                </div>
                                            </div>
                                        </form>
                                        <p>
                                            <span data-localize="label.LeaveRequests"></span>
                                            <span id="forWord" class="hide" data-localize="label.for"></span>
                                            <span id="currentLeaveRequests"></span>
                                        </p>
                                        <table class="table request-list responsive-table" id="leaveOverviewList">
                                                <thead>
                                                    <tr>
                                                            <!-- <th data-localize="approveRequest.employee"></th> -->
                                                            <th data-localize="approveRequest.leaveStartDate"></th>
                                                            <th data-localize="approveRequest.leaveEndDate"> </th>
                                                            <!-- <th data-localize="approveRequest.startTime"></th>
                                                            <th data-localize="approveRequest.endTime"></th> -->
                                                            <th data-localize="approveRequest.leaveType"></th>
                                                            <th data-localize="approveRequest.absenceReason"></th>
                                                            <th data-localize="approveRequest.leaveRequested"></th>
                                                            <th data-localize="approveRequest.commentLog"></th>
                                                            <th data-localize="approveRequest.status"></th>
                                                            <th aria-label="" data-localize="approveRequest.supervisorAction" data-localize-location="aria-label"></th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:if test="${isEmpty==false}">
                                                        <c:forEach var="item" items="${employeeList}" varStatus="status">
                                                            <tr class="hide">
                                                                    <!-- <td data-localize="approveRequest.employee" data-localize-location="scope">
                                                                            ${item.firstName} ${item.lastName}
                                                                    </td> -->
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
                                                                                    <c:if test="${reason.code==item.AbsenseReason}">${reason.description}</c:if>
                                                                            </c:forEach>
                                                                    </td>
                                                                    
                                                                    <td data-localize="approveRequest.leaveRequested" data-localize-location="scope">${item.lvUnitsUsed} 
                                                                        <span data-localize="label.days"></span>
                                                                    </td>
                                                                    <td data-localize="approveRequest.commentLog" data-localize-location="scope">
                                                                            <c:forEach var="comment" items="${item.comments}" varStatus="statusComment">
                                                                                    <p>${comment.detail}</p>
                                                                                </c:forEach>
                                                                    </td>
                                                                    <td data-localize="approveRequest.status" data-localize-location="scope">
                                                                            
                                                                        ${item.statusDescr}
                                                                    </td>
                                                                    <td style="width:150px;" data-localize="approveRequest.supervisorAction" data-localize-location="scope">
                                                                        
                                                                            <c:if test="${item.statusCd =='P'||item.statusCd =='D'}">
                                                                                <button class="btn btn-primary sm edit-btn" id="editLeave" data-toggle="modal" data-target="#requestModal" 
                                                                                onClick='editLeave("${item.id}","${item.LeaveType}","${item.AbsenseReason}","${item.start}",
                                                                                "${item.end}", "${item.lvUnitsDaily}","${item.lvUnitsUsed}")'' data-localize="label.edit"></button>
                                                                                <button class="btn btn-secondary sm delete-btn" id="deleteLeave"   data-toggle="modal" data-target="#deleteModal" 
                                                                                onClick="deleteLeave('${item.id}')" data-localize="label.delete"></button>
                                                                            </c:if>
                                                                            
                                                                    
                                                                </td>
                                                            </tr>
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
                        <form hidden="hidden" id="deleteForm" action="deleteLeaveFromLeaveOverview" method="POST">
                        	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <input type="hidden" name="empNbr" id="empNbrDelete">
                            <input type="hidden" name="startDate" id="searchStartDelete">
                            <input type="hidden" name="endDate" id="searchEndDelete">
                            <input type="hidden" name="chain" id="chainDelete">
                            <input type="hidden" name="freq" id="freqDelete">
                            <input type="hidden" name="leaveId" id="deleteId" aria-label="" data-localize="accessHint.id">
                        </form>
            </main>
        </div>
        <%@ include file="../modal/eventModal.jsp"%>
        <%@ include file="../modal/leaveListCalendarEdit.jsp"%>
        <%@ include file="../modal/eventModalStatic.jsp"%>
        <%@ include file="../commons/footer.jsp"%>
        <%@ include file="../modal/deleteModal.jsp"%>
    </body>
    <script>
    var directReportEmployee = eval(${directReportEmployee});
    var chain = eval(${chain});
    </script>
    <script src="/<%=request.getContextPath().split("/")[1]%>/js/viewJs/supervisor/leaveOverviewList.js"></script>

</html>
