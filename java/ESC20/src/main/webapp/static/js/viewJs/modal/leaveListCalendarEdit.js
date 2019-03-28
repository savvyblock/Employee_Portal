function changeMMDDFormat(date){
    let dateArry = date.split("-")
    return dateArry[0]
}
function changeFormatTimeAm(value){
    let array = value.split(/[,: ]/);
    let hour,minute,time
    hour = parseInt(array[0])
    minute = array[1]

    if(hour>12){
    hour = (hour-12) < 10 ? "0" + (hour-12) : hour-12;
    time = hour+ ":" +minute+" PM"
    }else{
    if(hour==12){
        time = hour+ ":" +minute+" PM"
    }else{
        hour = hour < 10 ? "0" + hour : hour;
        time = hour+ ":" +minute+" AM"
    }

    }
    return time
}
function initialLeaveCalendarStaticModal(){
    initThemeChooser({
        init: function(themeSystem) {
            $('#calendar').fullCalendar({
                themeSystem: themeSystem,
                header: {
                    left: 'prev,next today',
                    center: 'title',
                    right: ''
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
                    $("#requestForm").attr("action","updateLeaveFromLeaveOverview")
                    console.log(calEvent)
                    let leaveRequest = calEvent;
                    console.log(leaveRequest)
                    let type
                    leaveTypes.forEach(function(element) {
                        if(element.code == leaveRequest.LeaveType){
                            type = element.description
                        }
                    });
                    let reason
                    absRsns.forEach(function(element) {
                        if(element.code == leaveRequest.AbsenseReason){
                            reason = element.description
                        }
                    });
                    let leaveStartDate = leaveRequest.start._i
                    let leaveEndDate = leaveRequest.end._i

                    let start_arry = leaveStartDate.split(" ")
                    let end_arry = leaveEndDate.split(" ")

                    let startTime = changeFormatTimeAm(start_arry[1])
                    let endTime = changeFormatTimeAm(end_arry[1])

                    let startDate = changeMMDDFormat(start_arry[0])
                    let endDate = changeMMDDFormat(end_arry[0])

                    let start = startDate + " " + startTime
                    let end = endDate + " " +endTime

                    // $("#leaveIdStatic").attr("value", leaveRequest.id+"");
                    $("#disIdStatic").attr("value", leaveRequest.id+"");
                    $("#appIdStatic").attr("value", leaveRequest.id+"");
                    $("#employeeStatic").text(leaveRequest.lastName)
                    $("#startDateStatic").html(leaveRequest.start._i)
                    $("#endDateStatic").html(leaveRequest.end._i)
                    $("#leaveTypeStatic").html(type)
                    $("#absenceReasonStatic").html(reason)
                    $("#leaveRequestedStatic").html(leaveRequest.lvUnitsUsed)
                    $("#commentLogStatic").html("")
                    $("#leaveStatusStatic").text(leaveRequest.statusDescr)
                    $("#leaveApproverStatic").text(leaveRequest.approver)
                    let comments = leaveRequest.comments
                    for(let i=0;i<comments.length;i++){
                            let html = '<p>'+comments[i].detail+'</p>'
                            $("#commentLogStatic").append(html)
                    }
                    $("infoEmpNameStatic").html(leaveRequest.empNbr + ":" +leaveRequest.firstName+ ","+leaveRequest.firstName)
                    $("#infoDetailStatic").html("")
                    // $('#EventDetailModal').modal('show')
                    initLocalize(initialLocaleCode)
                },
                eventRender: function(event, element, view) {
                    element.attr('data-toggle', 'modal')
                    element.attr('data-target', '#EventDetailModal')
                    let startEv = changeYMDFormat(event.LeaveStartDate)
                    let endEv = changeYMDFormat(event.LeaveEndDate)
                    let time = element.find(".fc-time").text()
                    // let ariaLabel = "from " + startEv + " to " + endEv
                    let ariaLabel = startEv + " / " + endEv +" " + time + " " + event.title
                    element.attr('aria-label', ariaLabel)
                    element.attr('tabindex', 0)
                    element.bind('keypress', function(e)  {
                        var eCode = e.keyCode
                            ? e.keyCode
                            : e.which
                            ? e.which
                            : e.charCode
                        if (eCode == 13) {
                            $(this).click()
                        }
                    })
                    initLocalize(initialLocaleCode)
                },
                viewRender:function(){
                    $(".fc-today-button").html('<span data-localize="label.currentMonth"></span>')
                    initLocalize(initialLocaleCode) //Initialize multilingual function
                }
            })
        }
    })
    $(".fc-today-button").html('<span data-localize="label.currentMonth"></span>')
    initLocalize(initialLocaleCode) //Initialize multilingual function
}

function changeYMDFormat(date) {
    let dateArry = date.split('/')
    return dateArry[2] + '-' + dateArry[0] + '-' + dateArry[1]
}