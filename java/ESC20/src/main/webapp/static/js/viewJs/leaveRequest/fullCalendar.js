$(document).ready(function() {
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
                navLinks: true, // can click day/week names to navigate views
                editable: false,
                eventLimit: true, // allow "more" link when too many events
                events: leaveList,
                locale: initialLocaleCode,
                eventClick: function(calEvent, jsEvent, view) {
                    console.log(calEvent)
                    if (calEvent.statusCd != 'A') {
                        $('.dateValidator').hide()
                        $('#requestForm')
                            .data('bootstrapValidator')
                            .destroy()
                        $('#requestForm').data('bootstrapValidator', null)
                        formValidator()
                        $('.dateValidator').hide()
                        let leaveStartDate = calEvent.start._i
                        let leaveEndDate = calEvent.end._i

                        let start_arry = leaveStartDate.split(' ')
                        let end_arry = leaveEndDate.split(' ')
                        let startTime = start_arry[1].split(':')
                        let endTime = end_arry[1].split(':')
                        let startH = parseInt(startTime[0])
                        let endH = parseInt(endTime[0])
                        let startAMOrPM, endAMOrPM
                        startH = startTime[0].trim()
                        startAMOrPM = start_arry[2].trim()
                        endH = endTime[0].trim()
                        endAMOrPM = end_arry[2].trim()
                        $('#startHour').val(startH)
                        $('#endHour').val(endH)
                        $('#startAmOrPm').val(startAMOrPM)
                        $('#endAmOrPm').val(endAMOrPM)
                        let startTimeValue =
                            startH + ':' + startTime[1] + ' ' + startAMOrPM
                        let endTimeValue =
                            endH + ':' + endTime[1] + ' ' + endAMOrPM
                        $('#startTimeValue').val(startTimeValue)
                        $('#endTimeValue').val(endTimeValue)
                        $('#startMinute').val(startTime[1])
                        $('#endMinute').val(endTime[1])
                        $('#commentList').html('')
                        for (let i = 0; i < calEvent.comments.length; i++) {
                            let html =
                                '<p>' + calEvent.comments[i].detail + '</p>'
                            $('#commentList').append(html)
                        }
                        $('#cancelAdd').hide()
                        $('#deleteLeave').show()
                        $('.edit-title').show()
                        $('.new-title').hide()
                        $('.firstSubmit').hide()
                        $('.secondSubmit').show()
                        // $('#requestModal').modal('show')
                        $("[name='leaveType']").val(calEvent.LeaveType)
                        $("[name='absenseReason']").val(calEvent.AbsenseReason)
                        $('#leaveId').attr('value', calEvent.id + '')
                        $('#startDate').val(calEvent.LeaveStartDate)
                        $('#endDate').val(calEvent.LeaveEndDate)
                        console.log(calEvent.lvUnitsDaily)
                        console.log(calEvent.lvUnitsUsed)
                        $("#leaveHoursDaily").val(Number(calEvent.lvUnitsDaily).toFixed(3));
		                $("#totalRequested").val(Number(calEvent.lvUnitsUsed).toFixed(3));
                        
                        //Initializes the time control when edit event modal show
                    } else {
                        let leaveRequest = calEvent
                        console.log(leaveRequest)
                        let type
                        leaveTypes.forEach((element) => {
                            if (element.code == leaveRequest.LeaveType) {
                                type = element.description
                            }
                        })
                        let reason
                        absRsns.forEach((element) => {
                            if (element.code == leaveRequest.AbsenseReason) {
                                reason = element.description
                            }
                        })
                        let leaveStartDate = leaveRequest.start._i
                        let leaveEndDate = leaveRequest.end._i

                        let start_arry = leaveStartDate.split(' ')
                        let end_arry = leaveEndDate.split(' ')

                        let startTime = changeFormatTimeAm(start_arry[1])
                        let endTime = changeFormatTimeAm(end_arry[1])

                        let startDate = changeMMDDFormat(start_arry[0])
                        let endDate = changeMMDDFormat(end_arry[0])

                        let start = startDate + ' ' + startTime
                        let end = endDate + ' ' + endTime

                        // $("#leaveIdStatic").attr("value", leaveRequest.id+"");
                        $('#disIdStatic').attr('value', leaveRequest.id + '')
                        $('#appIdStatic').attr('value', leaveRequest.id + '')
                        $('#employeeStatic').text(leaveRequest.lastName)
                        $('#startDateStatic').html(leaveRequest.start._i)
                        $('#endDateStatic').html(leaveRequest.end._i)
                        $('#leaveTypeStatic').html(type)
                        $('#absenceReasonStatic').html(reason)
                        $('#leaveRequestedStatic').html(
                            leaveRequest.lvUnitsUsed
                        )
                        $('#commentLogStatic').html('')
                        $('#leaveStatusStatic').text(leaveRequest.statusDescr)
                        $('#leaveApproverStatic').text(leaveRequest.approver)
                        let comments = leaveRequest.comments
                        for (let i = 0; i < comments.length; i++) {
                            let html = '<p>' + comments[i].detail + '</p>'
                            $('#commentLogStatic').append(html)
                        }
                        $('infoEmpNameStatic').html(
                            leaveRequest.empNbr +
                                ':' +
                                leaveRequest.firstName +
                                ',' +
                                leaveRequest.firstName
                        )
                        $('#infoDetailStatic').html('')
                        //   $('#EventDetailModal').modal('show')
                        initLocalize(initialLocaleCode)
                    }
                },
                dayClick: function(date, allDay, jsEvent, view) {
                    newEvent($(this))
                    $("#requestModal").modal("show")
                },
                // eventMouseover: function (calEvent, jsEvent, view) {   
                //     console.log(calEvent)
                //     console.log(jsEvent)
                //     console.log(view)
                // },
                eventRender: function(event, element, view) {
                    if (event.statusCd != 'A') {
                        element.attr('data-toggle', 'modal')
                        element.attr('data-target', '#requestModal')
                    } else {
                        element.attr('data-toggle', 'modal')
                        element.attr('data-target', '#EventDetailModal')
                    }
                    element.attr('tabindex', 0)
                    element.bind('keypress', function(e)  {
                        console.log(e)
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
                        let title = $(this).attr('data-date')
                        // let newBtn = `<button class="btn btn-primary xs"  data-title="${title}" title="Add a new request" onclick="newEvent(this)">Add</button>`
                        let newBtn =
                            `<button class="btn btn-primary xs calendarAddBtn" data-title="` +
                            title +
                            `" onclick="newEvent(this)"  data-toggle="modal" data-target="#requestModal">
                            <span data-localize="label.add"></span>
                            </button>`
                        $(this).prepend(newBtn)
                    })
                    $(".fc-today-button").html('<span data-localize="label.currentMonth"></span>')
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
    $('.dateValidator').hide()
    console.log(dom)
    console.log($(dom).attr('data-title'))
    console.log($(dom).attr('data-date'))
    let date = changeMMDDFormat($(dom).attr('data-title')?$(dom).attr('data-title'):$(dom).attr('data-date'))
    console.log(date)
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
    $('.edit-title').hide()
    $('.new-title').show()
    $('#startDate').val(date)
    $('#endDate').val(date)
    $('#commentList').html('')
    $('.firstSubmit').show()
    $('.secondSubmit').hide()
    //Initializes the time control when new event modal show
}

function changeMMDDFormat(date) {
    let dateArry = date.split('-')
    return dateArry[1] + '/' + dateArry[2] + '/' + dateArry[0]
}

function changeFormatTimeAm(value) {
    let array = value.split(/[,: ]/)
    let hour, minute, time
    hour = parseInt(array[0])
    minute = parseInt(array[1])
    if (minute >= 0 && minute < 30) {
        minute = '00'
    } else {
        minute = '30'
    }
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
