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
			
            <main class="content-wrapper">
                    <section class="content">
                            <h2 class="clearfix section-title">
                                    <span data-localize="title.approveLeaveRequests"></span>
                                <a class="btn btn-primary pull-right"
                                data-toggle="modal" data-target="#leaveListCalendarModal" 
                                    data-localize="label.switchToCalendarView" data-localize-location="title">
                                    <i class="fa fa-calendar"></i>
                                </a>
                            </h2>
                            <div class="container-fluid">
                                    <form
                                    class="no-print searchForm"
                                    action="nextLevelFromApproveLeave"
                                    id="filterSupervisor"
                                    method="POST"
                                >
                                <span hidden="hidden" id="chainValue">${chain}</span>
                                    <input hidden="hidden" id="chain" name="chain" type="text" value="">
                                    <div class="form-group in-line flex-auto">
                                        <label class="form-title"><span data-localize="label.directReportSupervisor"></span>:</label>
                                        <select  class="form-control" name="selectEmpNbr" onchange="changeLevel()"
                                            id="selectEmpNbr">
                                            <c:forEach var="item" items="${directReportEmployee}" varStatus="count">
                                                <option value="${item.employeeNumber}">${item.selectOptionLabel}</option>
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
                                <form hidden="hidden" action="previousLevelFromApproveLeave" id="previousLevel" method="POST">
                                        <input hidden="hidden" type="text" value="${level}" name="level" id="preLevel">
                                        <input hidden="hidden" name="chain" type="text" value="" id="preChain">
                                </form>
                                <div class="showSelectSupervisor">
                                        <label class="form-title"><span data-localize="label.supervisorHierarchy"></span>: </label>
                                        <c:forEach var="item" items="${chain}" varStatus="status">
                                                <b> ${item.employeeNumber}:${item.lastName},${item.firstName}</b>
                                                <c:if test="${!status.last}"> ➝ </c:if>
                                        </c:forEach>
                                </div>
                                <br>
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
                                                                onClick="actionLeave('${item.id}')">Action</button>
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
		console.log(directReportEmployee)
        console.log(chain)
        $(function(){
            changeLevel()
            let level = $("#level").val()
            if(chain.length>1){
                $("#prevLevel").removeClass("disabled").removeAttr("disabled");
            }else{
                $("#prevLevel").addClass("disabled").attr('disabled',"true");
            }
            $("#nextLevel").click(function(){
                let chainString = $("#chainValue").text()
                console.log(chain)
                $("#chain").val(chain)
                $("#filterSupervisor")[0].submit()  
            })
            $("#prevLevel").click(function(){
                let chainString = $("#chainValue").text()
                console.log(chain)
                $("#preChain").val(chain)
                $("#previousLevel")[0].submit()  
            })
            let chainSt = $("#chainValue").text()
            $("#disChain").val(chainSt)
            $("#appChain").val(chainSt)
        })
            function actionLeave(id){
                let leaveRequest;
                leaves.forEach(element => {
                    if(element.id == id){
                        leaveRequest = element
                    }
                });
                console.log(leaveRequest)
                let type
                leaveTypes.forEach(element => {
                    if(element.code == leaveRequest.LeaveType){
                        type = element.description
                    }
                });
                let reason
                absRsns.forEach(element => {
                    if(element.code == leaveRequest.AbsenseReason){
                        reason = element.description
                    }
                });
                
                $("#leaveId").attr("value", leaveRequest.id+"");
                $("#disId").attr("value", leaveRequest.id+"");
                $("#appId").attr("value", leaveRequest.id+"");
                $("#employee").text(leaveRequest.lastName)
                $("#startDate").html(leaveRequest.LeaveStartDate)
                $("#endDate").html(leaveRequest.LeaveEndDate)
                $("#leaveType").html(type)
                $("#absenceReason").html(reason)
                $("#leaveRequested").html(leaveRequest.lvUnitsUsed)
                // $("#commentLog").html("")
                // let comments = leaveRequest.comments
                // for(let i=0;i<comments.length;i++){
                //         let html = '<p>'+comments[i].detail+'</p>'
                //         $("#commentLog").append(html)
                // }
                $("infoEmpName").html(leaveRequest.empNbr + ":" +leaveRequest.firstName+ ","+leaveRequest.firstName)
                $("#infoDetail").html("")
                let infoDetail = leaveRequest.infos
                for(let i=0;i<infoDetail.length;i++){
                    let unit
                    if(infoDetail[i].daysHrs=="D"){
                        unit = '<span data-localize="label.days"></span>'
                    }else{
                        unit = '<span data-localize="label.hours"></span>'
                    }
                    let html = `
                        <div><span data-localize="label.payrollFreq"></span>: ` + infoDetail[i].frequency + `</div>
                            <table class="table responsive-table mt-3">
                                <thead>
                                    <tr>
                                        <th data-localize="leaveBalance.leaveType"></th>
                                        <th data-localize="leaveBalance.beginningBalance"></th>
                                        <th data-localize="leaveBalance.advancedEarned"></th>
                                        <th data-localize="leaveBalance.pendingEarned"></th>
                                        <th data-localize="leaveBalance.used"></th>
                                        <th data-localize="leaveBalance.pendingUsed"> </th>
                                        <th data-localize="leaveBalance.available"></th>
                                        <th data-localize="leaveBalance.units"></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td data-localize="leaveBalance.leaveType" data-localize-location="scope">` + infoDetail[i].lvTyp + infoDetail[i].longDescr +` </td>
                                        <td data-localize="leaveBalance.beginningBalance" data-localize-location="scope">` + infoDetail[i].beginBalance + `</td>
                                        <td data-localize="leaveBalance.advancedEarned" data-localize-location="scope">` + infoDetail[i].advancedEarned + `</td>
                                        <td data-localize="leaveBalance.pendingEarned" data-localize-location="scope"> `+ infoDetail[i].pendingEarned + `</td>
                                        <td data-localize="leaveBalance.used" data-localize-location="scope"> `+ infoDetail[i].used +` </td>
                                        <td data-localize="leaveBalance.pendingUsed" data-localize-location="scope"> `+ infoDetail[i].pendingUsed +` </td>
                                        <td data-localize="leaveBalance.available" data-localize-location="scope"> `+ infoDetail[i].availableBalance +` </td>
                                        <td data-localize="leaveBalance.units" data-localize-location="scope">`+ unit +`</td>
                                    </tr>
                                </tbody>
                            </table>`
                    $("#infoDetail").append(html)
                }
                $("#supervisorComment").val("")
                $(".icheck[name='approve']").iCheck('uncheck');
                $('.approveValidator').hide()
                $('.commentValidator').hide()
                $('.supervisorComment').hide()
                $('#approveModal').modal('show')
                initLocalize(initialLocaleCode)
            }
            function changeLevel(){
                let selectNum = $("#selectEmpNbr").val()
                let numDirect = 0 ;
                directReportEmployee.forEach(element => {
                    if(element.employeeNumber == selectNum){
                        numDirect = element.numDirectReports
                    }
                });
                console.log(numDirect)
                if(parseInt(numDirect)>0){
                    $("#nextLevel").removeClass("disabled").removeAttr("disabled");
                }else{
                    $("#nextLevel").addClass("disabled").attr('disabled',"true");
                }
            }
    </script>
</html>
