var leaveListArry = new Array()
for(var i = 0,len = leaveList.length;i<len;i++){
    var item = leaveList[i]
    item.start = item.start?convertSlashDate(item.start):''
    item.end = item.end?convertSlashDate(item.end):''
    leaveListArry.push(item)
}
function convertSlashDate(date){
    var dateArry = date.split(' ')
    var dateArry01 = dateArry[0].split('-')
    var fullDate = dateArry01[2] + '-' + dateArry01[0] + '-' + dateArry01[1]
    return fullDate + ' ' + convertDay24(dateArry[1],dateArry[2])
}
function convertDay24(day,m){
    if(m == 'PM'){
        var dayArry = day.split(':')
        return (Number(dayArry[0])+12) + ':'+dayArry[1]
    }else{
        return day
    }
}
console.log(leaveListArry)
$(document).ready(function() {
    console.log(initialLocaleCode)
    initThemeChooser({
        init: function(themeSystem) {
            $('#calendar').fullCalendar({
                themeSystem: themeSystem,
                header: {
                    left: 'prev,next today',
                    center: 'title',
                    right: ''
                    // right: 'month,agendaWeek,agendaDay,listMonth'
                },
                timeFormat: 'hh:mm A',
                displayEventEnd: true,
                defaultDate: new Date(),
                weekNumbers: false,
                navLinks: false, // can click day/week names to navigate views
                editable: false,
                eventLimit: true, // allow "more" link when too many events
                events: leaveListArry,
                locale: initialLocaleCode,
                eventClick: function(calEvent, jsEvent, view) {
                    console.log(calEvent)
                    var leaveRequest = calEvent
                    console.log(leaveRequest)
                    var type
                    leaveTypes.forEach(function(element) {
                        if (element.code == leaveRequest.LeaveType) {
                            type = element.description
                        }
                    })
                    var reason
                    absRsns.forEach(function(element) {
                        if (element.code == leaveRequest.AbsenseReason) {
                            reason = element.description
                        }
                    })
                    var leaveStartDate = leaveRequest.start._i
                    var leaveEndDate = leaveRequest.end._i

                    var start_arry = leaveStartDate.split(' ')
                    var end_arry = leaveEndDate.split(' ')

                    var startTime = changeFormatTimeAm(start_arry[1])
                    var endTime = changeFormatTimeAm(end_arry[1])

                    var startDate = changeMMDDFormat(start_arry[0])
                    var endDate = changeMMDDFormat(end_arry[0])

                    // var start = startDate + ' ' + startTime
                    // var end = endDate + ' ' + endTime
                    var start = leaveRequest.LeaveStartDate + ' ' + leaveRequest.LeaveStartTime
                    var end = leaveRequest.LeaveEndDate + ' ' + leaveRequest.LeaveEndTime
                    // $("#leaveIdStatic").attr("value", leaveRequest.id+"");
                    $('#disIdStatic').attr('value', leaveRequest.id + '')
                    $('#appIdStatic').attr('value', leaveRequest.id + '')
                    $('#employeeStatic').text(leaveRequest.lastName)
                    $('#startDateStatic').html(start)
                    $('#endDateStatic').html(end)
                    $('#leaveTypeStatic').html(type)
                    $('#absenceReasonStatic').html(reason)
                    $('#leaveRequestedStatic').html(leaveRequest.lvUnitsUsed)
                    $('#commentLogStatic').html('')
                    $('#leaveStatusStatic').text(leaveRequest.statusDescr)
                    $('#leaveApproverStatic').text(leaveRequest.approver)
                    var comments = leaveRequest.comments
                    for (var i = 0; i < comments.length; i++) {
                        var html = '<p>' + comments[i].detail + '</p>'
                        $('#commentLogStatic').append(html)
                    }
                },
                eventRender: function(event, element, view) {
                    element.attr('data-toggle', 'modal')
                    element.attr('data-target', '#EventDetailModal')

                    if(event.statusCd.toLowerCase() == 'p'){
                        element.append('<b>('+event.statusCd+')</b>')
                    }
                    var startEv = changeYMDFormat(event.LeaveStartDate)
                    var endEv = changeYMDFormat(event.LeaveEndDate)
                    // var ariaLabel = "from " + startEv + " to " + endEv
                    var ariaLabel = startEv + " / " + endEv
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
                },
                viewRender: function() {
                }
            })
        }
    })
})
function changeMMDDFormat(date) {
    var dateArry = date.split('-')
    return dateArry[0]
}
function changeYMDFormat(date) {
    var dateArry = date.split('/')
    return dateArry[2] + '-' + dateArry[0] + '-' + dateArry[1]
}
function changeFormatTimeAm(value) {
    var array = value.split(/[,: ]/)
    var hour, minute, time
    hour = parseInt(array[0])
    minute = array[1]

    if (hour > 12) {
        hour = hour - 12 < 10 ? '0' + (hour - 12) : hour - 12
        time = hour + ':' + minute + ' PM'
    } else {
        if (hour == 12) {
            time = hour + ':' + minute + ' PM'
        } else {
            hour = hour < 10 ? '0' + hour : hour
            time = hour + ':' + minute + ' AM'
        }
    }
    return time
}
