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
                        <a class="btn btn-primary pull-right" href="/<%=request.getContextPath().split("/")[1]%>/supervisor/approveLeaveRequestList" data-localize="label.switchToTableView"></a>
                    </h2>
                    <div class="container-fluid">
                            <form
                                    class="no-print searchForm"
                                    action="nextLevel"
                                    id="filterSupervisor"
                                    method="POST"
                                >
                                    <span hidden="hidden" id="chainValue">${chain}</span>
                                    <input hidden="hidden" type="text" value="${level}" name="level">
                                    <input hidden="hidden" id="chain" name="chain" type="text" value="">
                                    <div class="form-group in-line flex-auto">
                                        <label class="form-title"><span data-localize="label.directReportSupervisor"></span>:</label>
                                        <select  class="form-control"name="selectEmpNbr" onchange="changeLevel()"
                                            id="selectEmpNbr">
                                            <c:forEach var="item" items="${directReportEmployee}" varStatus="count">
                                                <option value="${item.employeeNumber}">${item.employeeNumber}:${item.lastName},${item.firstName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="form-group in-line flex-auto">
                                        <button type="button" class='btn btn-primary' id="prevLevel" data-localize="label.previousLevel" 
                                        onClick="prevLevel()" > </button>
                                        <button type="button" 
                                        class='btn btn-primary' id="nextLevel" data-localize="label.nextLevel" 
                                        onClick="nextLevel()"></button>
                                            
                                    </div>
                                </form>
                                <div class="showSelectSupervisor">
                                        <label class="form-title"><span data-localize="label.supervisorHierarchy"></span>: </label>
                                        <c:forEach var="item" items="${chain}" varStatus="status">
                                                <b> ${item.employeeNumber}:${item.lastName},${item.firstName}</b>
                                                ${status.index}
                                                ${status.size()}
                                                <c:if test="${status.index != status.size() - 1 }"> ➝ </c:if>
                                        </c:forEach>
                                </div>
                                <div class="content-white">
                                        <div id="calendar"></div>
                                    </div>
                        
                        
                        </div>
                </section>
            </main>
        </div>
        <%@ include file="../modal/leaveListCalendar.jsp"%>
        <%@ include file="../commons/footer.jsp"%>
    </body>
    <script>
        var leaveList = eval(${leaves});
        var directReportEmployee = eval(${directReportEmployee});
        var chain = eval(${chain});
		console.log(directReportEmployee)
        console.log(chain)
		console.log(leaveList)
        $(document).ready(function() {
            changeLevel()
            if(chain.length>1){
                $("#prevLevel").removeClass("disabled").removeAttr("disabled");
            }else{
                $("#prevLevel").addClass("disabled").attr('disabled',"true");
            }
            $("#nextLevel").click(function(){
                let chain = $("#chainValue").text()
                console.log(chain)
                $("#chain").val(chain)
                $("#filterSupervisor")[0].submit()  
            })
            $("#prevLevel").click(function(){
                let chain = $("#chainValue").text()
                console.log(chain)
                $("#chain").val(chain)
                $("#filterSupervisor")[0].submit()  
            })
            $("#requestForm").attr("action", "submitLeaveRequestFromCalendar");
            initThemeChooser({
                init: function(themeSystem) {
                    $('#calendar').fullCalendar({
                        themeSystem: themeSystem,
                        header: {
                            left: 'prev,next today',
                            center: 'title',
                            right: 'month,agendaWeek,agendaDay,listMonth'
                        },
                        buttonText: {
                            today: 'Today',
                            month: 'Month',
                            agendaWeek: 'AgendaWeek',
                            agendaDay: 'AgendaDay',
                            listMonth: 'ListMonth'
                        },
                        timeFormat: 'hh:mm A',
                        displayEventEnd: true,
                        defaultDate: new Date(),
                        weekNumbers: false,
                        navLinks: true, // can click day/week names to navigate views
                        editable: false,
                        eventLimit: true, // allow "more" link when too many events
                        events: leaveList,
                        locale: initialLocaleCode,
                        eventClick: function(calEvent, jsEvent, view) {
                            console.log(calEvent)
                            let type
                            if(calEvent.LeaveType == 1){
                                type="LOCAL SICK"
                            }
                            if(calEvent.LeaveType == 2){
                                type="STATE PERSON"
                            }
                            if(calEvent.LeaveType == 3){
                                type="JURY DUTY"
                            }
                            if(calEvent.LeaveType == 4){
                                type="SCHOOL BUSINESS"
                            }
                            $('#approveModal').modal('show')
                            $("#leaveId").attr("value", calEvent.id+"");
                            $("#employee").text(calEvent.empNbr)
                            $("#startDate").text(calEvent.start._i)
                            $("#endDate").text(calEvent.end._i)
                            $("#leaveType").text(type)
                            $("#absenceReason").text(calEvent.AbsenseReason)
                            $("#leaveRequested").text(calEvent.lvUnitsUsed)
                            $("#commentLog").html("")
                            let comments = calEvent.comments
                            for(let i=0;i<comments.length;i++){
                                    let html = '<p>'+comments[i].detail+'</p>'
                                    $("#commentLog").append(html)
                            }
                            $("#supervisorComment").val("")
                            $(".icheck[name='approve']").iCheck('uncheck');
                            $('.approveValidator').hide()
                            $('.commentValidator').hide()
                            $('.supervisorComment').hide()
                        },
                        viewRender:function(){
                            $(".fc-event").attr("tabindex",0)
                            $(".fc-event").keypress(function(e){
                                console.log(e)
                                var eCode = e.keyCode ? e.keyCode : e.which ? e.which : e.charCode;
                                if (eCode == 13){
                                    $(this).click()
                                }
                            })
                        }
                    })
                }
            })
        })

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
