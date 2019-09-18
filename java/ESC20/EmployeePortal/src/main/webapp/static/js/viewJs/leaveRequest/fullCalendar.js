var reasonOption
var leaveListArry = new Array()
var leaveId
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
    formValidator()
    if(isAddValue == 'true'){
        $("#requestModal").modal('show')
        $(".edit-title").hide()
        $(".secondSubmit").hide();
        $("#deleteLeave").hide();	
    }
    reasonOption = $("#absenceReason").html()
    $('#requestForm').attr('action', 'submitLeaveRequestFromCalendar')
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
                    leaveId = calEvent.id
                    console.log(calEvent)
                    if (calEvent.statusCd != 'A') {
                        $('#requestForm')
                            .data('bootstrapValidator')
                            .destroy()
                        $('#requestForm').data('bootstrapValidator', null)
                        formValidator()
                        var leaveStartDate = calEvent.start._i
                        var leaveEndDate = calEvent.end?calEvent.end._i:calEvent.start._i

                        var start_arry = leaveStartDate.split(' ')
                        var end_arry = leaveEndDate.split(' ')

                        var startTime12 = changeFormatTimeAm(start_arry[1])//24 to 12
                        var endTime12 = changeFormatTimeAm(end_arry[1])

                        var time12ArryStart = startTime12.split(' ')
                        var time12ArryEnd = endTime12.split(' ')

                        var startTime = time12ArryStart[0].split(':')
                        var endTime = time12ArryEnd[0].split(':')
                        var startH = parseInt(startTime[0])
                        var endH = parseInt(endTime[0])
                        var startAMOrPM, endAMOrPM
                        startH = startTime[0].trim()
                        startAMOrPM = time12ArryStart[1].trim()
                        endH = endTime[0].trim()
                        endAMOrPM = time12ArryEnd[1].trim()
                        $('#startHour').val(startH)
                        $('#endHour').val(endH)
                        $('#startAmOrPm').val(startAMOrPM)
                        $('#endAmOrPm').val(endAMOrPM)
                        var startTimeValue = time12ArryStart[0] + ' ' + startAMOrPM
                        var endTimeValue = time12ArryEnd[0] + ' ' + endAMOrPM
                            console.log(startTimeValue)
                        $('#startTimeValue').val(startTimeValue)
                        $('#endTimeValue').val(endTimeValue)
                        $('#startMinute').val(startTime[1])
                        $('#endMinute').val(endTime[1])
                        $('#commentList').html('')
                        for (var i = 0; i < calEvent.comments.length; i++) {
                            var html =
                                '<p>' + calEvent.comments[i].detail + '</p>'
                            $('#commentList').append(html)
                        }
                        // $('#cancelAdd').hide()
                        // $('#deleteLeave').show()
                        $("#Remarks").text('').val('')
                        $('.edit-title').show()
                        $('.new-title').hide()
                        $('.firstSubmit').hide()
                        $('.secondSubmit').show()
                        // $('#requestModal').modal('show')
                        $("[name='leaveType']").val(calEvent.LeaveType)
                        changeLeaveType()
                        $("[name='absenseReason']").val(calEvent.AbsenseReason)
                        $('#leaveId').attr('value', calEvent.id + '')
                        $('#startDateInput').val(calEvent.LeaveStartDate)
                        $('#endDateInput').val(calEvent.LeaveEndDate)
                        $("#leaveHoursDaily").val(Number(calEvent.lvUnitsDaily).toFixed(3));
		                $("#totalRequested").val(Number(calEvent.lvUnitsUsed).toFixed(3));
                        
                        // remove all validator message
                        $(".availableError").hide()
                        $(".leaveHoursDailyNotZero").hide()
                        $(".leaveHoursDailyWrap").removeClass('has-error')
                        $('.dateValidator').hide()
                        $('.dateValidator01').hide()
                        $(".dateTimePeriodOverlap").hide()
                    } else {
                        var leaveRequest = calEvent
                        console.log(leaveRequest)
                        var type
                        leaveTypes.forEach(function(element){
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
                        var leaveEndDate = leaveRequest.end?leaveRequest.end._i:leaveRequest.start._i

                        var start_arry = leaveStartDate.split(' ')
                        var end_arry = leaveEndDate.split(' ')

                        var startTime = changeFormatTimeAm(start_arry[1])
                        var endTime = changeFormatTimeAm(end_arry[1])

                        var startDate = start_arry[0] //changeMMDDFormat(start_arry[0])
                        var endDate = end_arry[0] //changeMMDDFormat(end_arry[0])

                        // var start = startDate + ' ' + startTime
                        // var end = endDate + ' ' + endTime

                        var start = leaveRequest.LeaveStartDate + ' ' + leaveRequest.LeaveStartTime
                        var end = leaveRequest.LeaveEndDate + ' ' + leaveRequest.LeaveEndTime

                        // $("#leaveIdStatic").attr("value", leaveRequest.id+"");
                        $('#disIdStatic').attr('value', leaveRequest.id + '')
                        $('#appIdStatic').attr('value', leaveRequest.id + '')
                        $('#employeeStatic').text(leaveRequest.lastName + ',' +leaveRequest.firstName + ' ' + leaveRequest.middleName)
                        $('#startDateInputStatic').html(leaveRequest.start._i)
                        $('#endDateInputStatic').html(leaveRequest.end._i)
                        $('#startDateStatic').html(start)
                        $('#endDateStatic').html(end)
                        $('#leaveTypeStatic').html(type)
                        $('#absenceReasonStatic').html(reason)
                        $('#leaveRequestedStatic').html(
                            leaveRequest.lvUnitsUsed
                        )
                        $('#commentLogStatic').html('')
                        $('#leaveStatusStatic').text(leaveRequest.statusDescr)
                        $('#leaveApproverStatic').text(leaveRequest.approver)
                        var comments = leaveRequest.comments
                        for (var i = 0; i < comments.length; i++) {
                            var html = '<p>' + comments[i].detail + '</p>'
                            $('#commentLogStatic').append(html)
                        }
                        initLocalize(initialLocaleCode)
                    }
                },
                dayClick: function(date, allDay, jsEvent, view) {
                    if(haveSupervisor == 'false'){
                        return false
                    }
                    newEvent($(this))
                    $("#requestModal").modal("show")
                    $("#absenceReason").html(reasonOption)
                },
                eventRender: function(event, element, view) {
                    if (event.statusCd != 'A') {
                        element.attr('data-toggle', 'modal')
                        element.attr('data-target', '#requestModal')
                        element.attr('role','button')
                    } else {
                        element.attr('data-toggle', 'modal')
                        element.attr('data-target', '#EventDetailModal')
                        element.attr('role','button')
                    }
                    var startEv = changeYMDFormat(event.LeaveStartDate)
                    var endEv = changeYMDFormat(event.LeaveEndDate)
                    var time = element.find(".fc-time").text()
                    // var ariaLabel = "from " + startEv + " to " + endEv
                    var ariaLabel = startEv + " / " + endEv +" " + time + " " + event.title
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
                viewRender: function(view, element) {
                    $('.fc-day-top').each(function() {
                        var title = $(this).attr('data-date')
                        // var newBtn = `<button class="btn btn-primary xs"  data-title="${title}" title="Add a new request" onclick="newEvent(this)">Add</button>`
                        var newBtn =''
                        if(haveSupervisor == 'false'){
                            newBtn =
                            '<button class="btn btn-primary xs calendarAddBtn" data-title="'+title+'" aria-label="'+addNewRequestLabel+' ' +title +'" onclick="newEvent(this)"  data-toggle="modal" data-target="#requestModal" disabled="disabled"><span>'
                            + addLabel +
                            '</span></button>'
                        }else{
                            newBtn =
                            '<button class="btn btn-primary xs calendarAddBtn" data-title="'+title+'" aria-label="'+addNewRequestLabel+' ' +title +'" onclick="newEvent(this)"  data-toggle="modal" data-target="#requestModal"><span>'
                            + addLabel +
                            '</span></button>'
                        }
                        
                        $(this).prepend(newBtn)
                    })
                    var currentHtml = '<span>' + currentMonthLabel + '</span>'
                    $(".fc-today-button").html(currentHtml)
                    initLocalize(initialLocaleCode) //Initialize multilingual function
                }
            })
        },
        change: function(themeSystem) {
            $('#calendar').fullCalendar('option', 'themeSystem', themeSystem)
        }
    })
    
    

    $('.sureDelete').click(function() {
        $('#deleteForm')[0].submit()
    })
})
function newEvent(dom) {
    var date = changeMMDDFormat($(dom).attr('data-title')?$(dom).attr('data-title'):$(dom).attr('data-date'))
    
    $('#requestForm')[0].reset()
    $('#requestForm')
        .data('bootstrapValidator')
        .destroy()
    $('#requestForm').data('bootstrapValidator', null)
    formValidator()

    $('#leaveId').attr('value', '')
    $("#Remarks").text('').val('')
    $(".timeUnit").hide()
    $('#cancelAdd').show()
    $('#deleteLeave').hide()
    $('.edit-title').hide()
    $('.new-title').show()
    $('#startDateInput').val(date)
    $('#endDateInput').val(date)
    $('#commentList').html('')
    $('.firstSubmit').show()
    $('.secondSubmit').hide()
    
    // remove all validator message
    $(".availableError").hide()
    $(".leaveHoursDailyNotZero").hide()
    $(".leaveHoursDailyWrap").removeClass('has-error')
    $('.dateValidator').hide()
    $('.dateValidator01').hide()
    $(".dateTimePeriodOverlap").hide()
}

function changeMMDDFormat(date) {
    var dateArry = date.split('-')
    return dateArry[1] + '-' + dateArry[2] + '-' + dateArry[0]
}
function changeYMDFormat(date) {
    var dateArry = date.split('-')
    return dateArry[2] + '-' + dateArry[0] + '-' + dateArry[1]
}
function convertSlashDateText(date){
    var dateArry = date.split(' ')
    var dateArry01 = dateArry[0].split('-')
    var fullDate = dateArry01[2] + '/' + dateArry01[0] + '/' + dateArry01[1]
    return fullDate + ' ' + dateArry[1] + ' ' + dateArry[2]
}
function changeFormatTimeAm(value) {
    var array = value.split(/[,: ]/)
    var hour, minute, time
    hour = parseInt(array[0])
    minute = parseInt(array[1])>9?parseInt(array[1]):'0'+parseInt(array[1])

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
