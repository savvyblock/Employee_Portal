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
			
            <main class="content-wrapper">
                    <section class="content">
                            <h2 class="clearfix section-title">
                                <span data-localize="title.leaveOverview"></span>
                                <a class="btn btn-primary pull-right" href="/<%=request.getContextPath().split("/")[1]%>/supervisor/leaveOverviewList" data-localize="label.switchToTableView"></a>
                            </h2>
                            <div class="container-fluid">
                                    <div class="showSelectSupervisor">
                                            <label class="form-title"><span data-localize="label.supervisorHierarchy"></span>: </label>
                                            <b> 000922 : RONQUILLO, RAYMUNDO</b>
                                    </div>
                                    <form
                                    class="no-print searchForm"
                                    action=""
                                    id="filterSupervisor"
                                >
                                <div class="form-group in-line flex-auto">
                                        <label class="form-title"><span data-localize="label.directReportSupervisor"></span>:</label>
                                        <select  class="form-control"name="selectedDirectReportEmployeeNumber"
                                            id="selectedDirectReportEmployeeNumber">
                                            <option value=""></option>
                                            <option value=""></option>
                                        </select>
                                    </div>
                                    <div class="form-group in-line flex-auto">
                                            <button type="submit" class="btn btn-primary" id="prevLevel" data-localize="label.previousLevel"> </button>
                                            <button type="submit" class="btn btn-primary" id="nextLevel" data-localize="label.nextLevel"></button>
                                    </div>
                                </form>
                                
                                <div class="content-white">
                                        <div class="container-fluid"><div id="calendar"></div></div>
                                    
                                </div>
                            </div>
                        </section>
            </main>
        </div>
        <%@ include file="../modal/eventModal.jsp"%>
        <%@ include file="../modal/eventModalStatic.jsp"%>
        <%@ include file="../commons/footer.jsp"%>
    </body>
    <script>
        $(document).ready(function() {
            var requestList = [{
                    AbsenseReason: "JURY DUTY",
                    LeaveEndDate: "12/27/2018",
                    LeaveEndDateType: 0,
                    LeaveStartDate: "12/27/2018",
                    LeaveStartDateType: 0,
                    LeaveType: "1",
                    Remarks: "test",
                    end: "2018-12-27 14:30",
                    id: 1,
                    start: "2018-12-27 14:00",
                    title: "Leave"
                },{
                    AbsenseReason: "JURY DUTY",
                    LeaveEndDate: "12/18/2018",
                    LeaveEndDateType: 0,
                    LeaveStartDate: "12/12/2018",
                    LeaveStartDateType: 0,
                    LeaveType: "1",
                    Remarks: "test",
                    end: "2018-12-18 14:30",
                    id: 1,
                    start: "2018-12-12 14:00",
                    title: "Leave"
                }];
                console.log(requestList)
            initThemeChooser({
                init: function(themeSystem) {
                    $('#calendar').fullCalendar({
                        themeSystem: themeSystem,
                        header: {
                            left: 'prev,next today',
                            center: 'title',
                            right: 'month'
                            // right: 'month,agendaWeek,agendaDay,listMonth'
                        },
                        buttonText: {
                            today: 'Today',
                            month: 'Month'
                        },
                        timeFormat: 'hh:mm A',
                        displayEventEnd: true,
                        defaultDate: new Date(),
                        weekNumbers: false,
                        navLinks: true, // can click day/week names to navigate views
                        editable: false,
                        eventLimit: true, // allow "more" link when too many events
                        events: requestList,
                        locale: initialLocaleCode,
                        eventClick: function(calEvent, jsEvent, view) {
                            console.log(calEvent)
                            // $('#requestModal').modal('show')
                            $("#EventDetailModal").modal('show')
							$("#cancelAdd").hide();
                            $("#deleteLeave").hide();
                            $(".edit-title").show();
                            $(".new-title").hide();
                            $("[name='leaveType']").val(calEvent.LeaveType);
				            $("[name='absenseReason']").val(calEvent.AbsenseReason);
							$("[name='Remarks']").text(calEvent.Remarks);
							$("#leaveId").attr("value", calEvent.id+"");
                            $("#startDate").val(calEvent.LeaveStartDate);
                            $("#startTime").val(start);
                            $("#startTimeValue").val(start);
							$("#endDate").val(calEvent.LeaveEndDate);
                            $("#endTime").val(end)
                            $("#endTimeValue").val(end);
                            $("[name='Remarks']").val(calEvent.Remarks);
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
                            $(".fc-day-top").each(function(){
                                let title = $(this).attr("data-date")
                                // let newBtn = `<button class="btn btn-primary xs"  data-title="${title}" title="Add a new request" onclick="newEvent(this)">Add</button>`
                                let newBtn = `<button class="btn btn-primary xs" data-title="`
                                                + title +
                                                `" title="Add a new request" onclick="newEvent(this)">Add</button>`
                                $(this).prepend(newBtn)
                            })
                        }
                    })
                },

            })
        })
        function showRequestForm() {
        $('#leaveId').attr('value', '')
        $("[name='Remarks']").text('')
        $('#requestForm')[0].reset()
        $('#requestForm')
            .data('bootstrapValidator')
            .destroy()
        $('#requestForm').data('bootstrapValidator', null)
        formValidator()
        $('#cancelAdd').show()
				$('#deleteLeave').hide()
				$(".edit-title").hide();
        $(".new-title").show();
        //Initializes the time control when edit event modal show
				setStartTime()
				setEndTime()
    }
    function newEvent(dom){
            console.log(dom)
            let date = $(dom).attr("data-title")
            console.log(date)
            $("#leaveId").attr("value","");
            $("[name='Remarks']").text("");
            $('#requestForm')[0].reset();
            $('#requestForm').data('bootstrapValidator').destroy()
            $('#requestForm').data('bootstrapValidator', null)
            formValidator()
            $("#cancelAdd").show();
            $("#deleteLeave").hide();
            $(".edit-title").hide();
            $(".new-title").show();
            $('#requestModal').modal('show')
            $("#startDate").val(date);
            $("#endDate").val(date);
            //Initializes the time control when new event modal show
            setStartTime()
            setEndTime()
        }
    </script>
</html>
