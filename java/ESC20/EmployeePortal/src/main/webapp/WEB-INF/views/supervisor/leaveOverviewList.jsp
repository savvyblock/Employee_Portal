<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ page
language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html> 
<html lang="en">
    <head>
        <title>${sessionScope.languageJSON.headTitle.leaveOverview}</title>
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
                                    <h1 class="pageTitle">${sessionScope.languageJSON.title.leaveOverview}</h1>
                                <div class="pull-right right-btn">
					                <button class="btn btn-primary" id="new-btn" data-toggle="modal" data-target="#requestModal" onclick="showRequestForm()">${sessionScope.languageJSON.label.add}</button>
                                    <button class="btn btn-primary pull-right" style="height:35px;display:flex;align-items:center;"  data-toggle="modal" data-target="#leaveOverviewCalendarModal" 
                                        onclick="showOverviewCalendar()"  aria-label="${sessionScope.languageJSON.label.switchToCalendarView}">
                                        <i class="fa fa-calendar"></i>
                                    </button>
                                </div>
                            </div>
                            <div class="container-fluid">
                                    <div class="showSelectSupervisor">
                                            <label class="form-title"><span>${sessionScope.languageJSON.label.supervisorHierarchy}</span>: </label>
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
                                    <input type="hidden" type="text" value="${level}" name="level" id="level" aria-label="${sessionScope.languageJSON.accessHint.level}"/>
                                    <input type="hidden" id="chain" name="chain" type="text" value="" aria-label="${sessionScope.languageJSON.accessHint.chain}"/>
                                    <input type="hidden" type="text" name="isChangeLevel" class="isChangeLevel"  aria-label="${sessionScope.languageJSON.accessHint.whetherChangeLevel}"/>
                                    <div class="form-group in-line flex-auto">
                                        <label class="form-title" for="selectEmpNbr"><span>${sessionScope.languageJSON.label.directReportEmployees}</span>:</label>
                                        <select  class="form-control"name="selectEmpNbr" onchange="changeEmployee()"
                                            id="selectEmpNbr">
                                            <c:forEach var="item" items="${directReportEmployee}" varStatus="count">
                                                    <c:choose>
                                                            <c:when test="${item.selectOptionLabel==''}">
                                                                    <option value="${item.employeeNumber}" <c:if test="${item.employeeNumber==selectedEmployee}">selected</c:if>>&nbsp;</option>
                                                            </c:when>
                                                            <c:otherwise>
                                                                 <option value="${item.employeeNumber}" <c:if test="${item.employeeNumber==selectedEmployee}">selected</c:if>>${item.selectOptionLabel}</option>
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
                                <form hidden="hidden" action="previousLevelFromLeaveOverview" id="previousLevel" method="POST">
                                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                        <input type="hidden" type="text" value="${level}" name="level" id="preLevel" aria-label="${sessionScope.languageJSON.accessHint.level}"/>
                                        <input type="hidden" name="chain" type="text" value="" id="preChain" aria-label="${sessionScope.languageJSON.accessHint.chain}"/>
                                </form>
                                <h2 class="tableTitle" style="padding:0;">
                                    <span>${sessionScope.languageJSON.label.LeaveRequests}</span>
                                    <span id="forWord" class="hide">${sessionScope.languageJSON.label.for}</span>
                                    <span id="currentLeaveRequests"></span>
                                </h2>
                                <div class="content-white">
                                        
                                        <form
                                        class="no-print searchForm"
                                        action="leaveOverviewList"
                                        id="changeFreqForm"
                                        method="POST"
                                            >
                                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                            <input type="hidden" type="text" name="empNbr" class="employeeNum" value="${selectedEmployee}" aria-label="${sessionScope.languageJSON.accessHint.employeeNumber}"/>
                                            <input type="hidden" class="chain" name="chain" type="text" value="" aria-label="${sessionScope.languageJSON.accessHint.chain}"/>
                                            <input type="hidden" type="text" name="isChangeLevel" class="isChangeLevel" value="false" aria-label="${sessionScope.languageJSON.accessHint.whetherChangeLevel}"/>
                                        <div class="form-group type-group">
                                                <label class="form-title"  for="freq">${sessionScope.languageJSON.label.payrollFreq}</label>
                                                <select class="form-control" name="freq" id="freq" onchange="changeFreq()">
                                                        <c:forEach var="freq" items="${availableFreqs}" varStatus="count">
                                                            <option value="${freq.code}" <c:if test="${freq.code == selectedFreq }">selected</c:if>>${freq.description}</option>
                                                        </c:forEach>
                                                    
                                                </select>
                                        </div>
                                        <div class="form-group">
                                                <label class="form-title" for="SearchStartInput"><span>${sessionScope.languageJSON.label.from}</span>:</label> 
                                                <div class="button-group">
                                                        <div class="fDateGroup date" id="SearchStartDate" data-date-format="mm/dd/yyyy">
                                                            <button class="prefix" type="button" aria-label="${sessionScope.languageJSON.label.showDatepicker}"><i class="fa fa-calendar"></i></button>
                                                            <input class="form-control dateInput"type="text" name="startDate"
                                                            data-date-format="mm/dd/yyyy"  autocomplete="off"
                                                            aria-label="${sessionScope.languageJSON.label.from}"
                                                            placeholder="${sessionScope.languageJSON.label.mmddyyyyFormat}"
                                                            title="${sessionScope.languageJSON.label.mmddyyyyFormat}"
                                                            id="SearchStartInput" value="${startDate}" />
                                                            <button class="clear-btn" type="button" role="button" onclick="clearDate(this)" tabindex="0" aria-label="${sessionScope.languageJSON.label.removeContent}">
                                                                    <i class="fa fa-times"></i>
                                                                </button>
                                                    </div>
                                                <!-- <input
                                                    class="form-control" type="text" name="startDate"
                                                    data-date-format="mm/dd/yyyy"  autocomplete="off"
                                                    aria-label="${sessionScope.languageJSON.label.from}"
                                                    placeholder="${sessionScope.languageJSON.label.mmddyyyyFormat}"
                                                    title="${sessionScope.languageJSON.label.mmddyyyyFormat}"
                                                    id="SearchStartDate" value="${startDate}" />
                                                    <button class="clear-btn" type="button" role="button" onclick="clearDate(this)" tabindex="0" aria-label="${sessionScope.languageJSON.label.removeContent}">
                                                        <i class="fa fa-times"></i>
                                                    </button> -->
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="form-title" for="SearchEndInput"> <span>${sessionScope.languageJSON.label.to}</span>: </label> 
                                                <div class="button-group">
                                                        <div class="fDateGroup date" id="SearchEndDate" data-date-format="mm/dd/yyyy">
                                                            <button class="prefix" type="button" aria-label="${sessionScope.languageJSON.label.showDatepicker}"><i class="fa fa-calendar"></i></button>
                                                            <input class="form-control dateInput" type="text" name="endDate" data-date-format="mm/dd/yyyy"  autocomplete="off"
                                                            aria-label="${sessionScope.languageJSON.label.to}"
                                                            placeholder="${sessionScope.languageJSON.label.mmddyyyyFormat}"
                                                            title="${sessionScope.languageJSON.label.mmddyyyyFormat}"
                                                            id="SearchEndInput" value="${endDate}" />
                                                            <button class="clear-btn" type="button" role="button" onclick="clearDate(this)" tabindex="0"   aria-label="${sessionScope.languageJSON.label.removeContent}">
                                                                <i class="fa fa-times"></i>
                                                            </button>
                                                        </div>
                                                <!-- <input
                                                    class="form-control" type="text" name="endDate" data-date-format="mm/dd/yyyy"  autocomplete="off"
                                                    aria-label="${sessionScope.languageJSON.label.to}"
                                                    placeholder="${sessionScope.languageJSON.label.mmddyyyyFormat}"
                                                    title="${sessionScope.languageJSON.label.mmddyyyyFormat}"
                                                    id="SearchEndDate" value="${endDate}" />
                                                    <button class="clear-btn" type="button" role="button" onclick="clearDate(this)" tabindex="0"   aria-label="${sessionScope.languageJSON.label.removeContent}">
                                                        <i class="fa fa-times"></i>
                                                    </button> -->
                                                </div>
                                            </div>
                                            <div class="form-group btn-group">
                                                <div style="margin-top:20px;">
                                                        <button type="submit" role="button" class="btn btn-primary">
                                                        	${sessionScope.languageJSON.leaveBalance.retrieve}
                                                        </button>
                                                </div>
                                            </div>
                                        </form>
                                        
                                        <table class="table request-list responsive-table" id="leaveOverviewList">
                                                <thead>
                                                    <tr>
                                                            <th>${sessionScope.languageJSON.approveRequest.leaveStartDate}</th>
                                                            <th>${sessionScope.languageJSON.approveRequest.leaveEndDate}</th>
                                                            <th>${sessionScope.languageJSON.approveRequest.leaveType}</th>
                                                            <th>${sessionScope.languageJSON.approveRequest.absenceReason}</th>
                                                            <th>${sessionScope.languageJSON.approveRequest.leaveRequested}</th>
                                                            <th>${sessionScope.languageJSON.approveRequest.commentLog}</th>
                                                            <th>${sessionScope.languageJSON.approveRequest.status}</th>
                                                            <td aria-label="${sessionScope.languageJSON.approveRequest.supervisorAction}"></td>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:if test="${isEmpty==false}">
                                                        <c:forEach var="item" items="${employeeList}" varStatus="status">
                                                            <tr class="hide">
                                                                    <td data-title="${sessionScope.languageJSON.approveRequest.leaveStartDate}">${item.LeaveStartDate}</td>
                                                                    <td data-title="${sessionScope.languageJSON.approveRequest.leaveEndDate}">${item.LeaveEndDate}</td>
                                                                    <td data-title="${sessionScope.languageJSON.approveRequest.leaveType}">
                                                                        <c:forEach var="type" items="${leaveTypes}" varStatus="statusType">
                                                                        	<c:if test="${type.code==item.LeaveType}">${type.description}</c:if>
                                                                        </c:forEach>
                                                                    </td>
                                                                    <td data-title="${sessionScope.languageJSON.approveRequest.absenceReason}" >
                                                                            <c:forEach var="reason" items="${absRsns}" varStatus="statusReason">
                                                                                    <c:if test="${reason.code==item.AbsenseReason}">${reason.description}</c:if>
                                                                            </c:forEach>
                                                                    </td>
                                                                    
                                                                    <td data-title="${sessionScope.languageJSON.approveRequest.leaveRequested}">${item.lvUnitsUsed} 
                                                                        <span>${sessionScope.languageJSON.label.days}</span>
                                                                    </td>
                                                                    <td data-title="${sessionScope.languageJSON.approveRequest.commentLog}">
                                                                            <c:forEach var="comment" items="${item.comments}" varStatus="statusComment">
                                                                                    <p>${comment.detail}</p>
                                                                                </c:forEach>
                                                                    </td>
                                                                    <td data-title="${sessionScope.languageJSON.approveRequest.status}">  
                                                                        ${item.statusDescr}
                                                                    </td>
                                                                    <td data-title="${sessionScope.languageJSON.approveRequest.supervisorAction}" style="width:150px;">
                                                                        
                                                                            <c:if test="${item.statusCd =='P'||item.statusCd =='D'}">
                                                                                <button class="btn btn-primary sm edit-btn" data-toggle="modal" data-target="#requestModal" 
                                                                                onClick='editLeave("${item.id}","${item.LeaveType}","${item.AbsenseReason}","${item.start}",
                                                                                "${item.end}", "${item.lvUnitsDaily}","${item.lvUnitsUsed}")'>${sessionScope.languageJSON.label.edit}</button>
                                                                                <button class="btn btn-secondary sm delete-btn" data-toggle="modal" data-target="#deleteModal" 
                                                                                onClick="deleteLeave('${item.id}')">${sessionScope.languageJSON.label.delete}</button>
                                                                            </c:if>
                                                                            
                                                                    
                                                                </td>
                                                            </tr>
                                                        </c:forEach>
                                                    </c:if>
                                                    <c:if test="${isEmpty==true}">
                                                        <tr>
                                                            <td colspan="8">
                                                                <span>${sessionScope.languageJSON.label.noData}</span>
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
                            <input type="hidden" name="empNbr" id="empNbrDelete"/>
                            <input type="hidden" name="startDate" id="searchStartDelete"/>
                            <input type="hidden" name="endDate" id="searchEndDelete"/>
                            <input type="hidden" name="chain" id="chainDelete"/>
                            <input type="hidden" name="freq" id="freqDelete"/>
                            <input type="hidden" name="leaveId" id="deleteId" aria-label="${sessionScope.languageJSON.accessHint.id}"/>
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
