let chainString = JSON.stringify(chain)
console.log(chainString)
$(function() {
    changeLevel()
    $('.chain').val(chainString)
    let level = $('#level').val()
    console.log(initialLocaleCode)
    $('#SearchStartDate').fdatepicker({
        format: 'mm/dd/yyyy',
        language: initialLocaleCode
    })
    $('#SearchEndDate').fdatepicker({
        format: 'mm/dd/yyyy',
        language: initialLocaleCode
    })
    setGlobal()
    if (chain && chain != '' && chain.length > 1) {
        $('#prevLevel')
            .removeClass('disabled')
            .removeAttr('disabled')
    } else {
        $('#prevLevel')
            .addClass('disabled')
            .attr('disabled', 'true')
    }
    $('#nextLevel').click(function() {
        let chain = $('#chainValue').text()
        console.log(chain)
        $('#chain').val(chain)
        $('.isChangeLevel').val(true)
        $('#filterSupervisor')[0].submit()
    })
    $('#prevLevel').click(function() {
        let chain = $('#chainValue').text()
        console.log(chain)
        $('#preChain').val(chain)
        $('.isChangeLevel').val(true)
        $('#previousLevel')[0].submit()
    })
    initList()

    $('.sureDelete').click(function() {
        $('#deleteForm')[0].submit()
    })
})
function initList() {
    let employeeSelect = $('#selectEmpNbr').val()
    if (!employeeSelect || employeeSelect == '') {
        let options = "<option values=''></option>"
        $('#freq').html('options')
        $('#new-btn')
            .addClass('disabled')
            .attr('disabled', 'disabled')
        let noResult = `<tr>
                                                            <td colspan="8">
                                                                <span data-localize="label.noData"></span>
                                                            </td>
                                                        </tr>`
        $('#leaveOverviewList tbody').html(noResult)
        setGlobal()
    } else {
        $('#leaveOverviewList tbody tr').removeClass('hide')
    }
    let requester = $('#selectEmpNbr option:selected').text()
    if (requester && requester != '') {
        $('#forWord').removeClass('hide')
        $('#currentLeaveRequests').text(requester)
    } else {
        $('#forWord').addClass('hide')
        $('#currentLeaveRequests').text('')
    }
}
function changeLevel() {
    let selectNum = $('#selectEmpNbr').val()
    let numDirect = 0
    directReportEmployee.forEach((element) => {
        if (element.employeeNumber == selectNum) {
            numDirect = element.numDirectReports
        }
    })
    console.log(numDirect)
    if (parseInt(numDirect) > 0) {
        $('#nextLevel')
            .removeClass('disabled')
            .removeAttr('disabled')
    } else {
        $('#nextLevel')
            .addClass('disabled')
            .attr('disabled', 'true')
    }
}
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
    $('.edit-title').hide()
    $('.new-title').show()
    $('.firstSubmit').show()
    $('.secondSubmit').hide()
    $('#chainModal').val(chainString)
    let empNbr = $('#selectEmpNbr').val()
    $('#empNbrModal').val(empNbr)
    $('#requestForm').attr('action', 'updateLeaveFromLeaveOverview')
}
function changeFreq() {
    let select = $('#freq').val()
    $('.selectFreq').val()
    // $("#changeFreqForm")[0].submit();
}
function changeEmployee() {
    let selectNum = $('#selectEmpNbr').val()
    $('#SearchStartDate').val('')
    $('#SearchEndDate').val('')
    $('.employeeNum').val(selectNum)
    $('.isChangeLevel').val(false)
    if (selectNum && selectNum != '') {
        $('#changeFreqForm')[0].submit()
    }
    initList()
}

function editLeave(
    id,
    leaveType,
    absenceReason,
    leaveStartDate,
    leaveEndDate,
    lvUnitsDaily,
    lvUnitsUsed
) {
    $('#requestForm').attr('action', 'updateLeaveFromLeaveOverview')
    let comments
    leaveList.forEach((element) => {
        if (element.id == id) {
            console.log(element)
            comments = element.comments
        }
    })
    $('#requestForm')
        .data('bootstrapValidator')
        .destroy()
    $('#requestForm').data('bootstrapValidator', null)
    formValidator()
    let start_arry = leaveStartDate.split(' ')
    let end_arry = leaveEndDate.split(' ')
    let startTime = start_arry[1].split(':')
    let endTime = end_arry[1].split(':')
    console.log(startTime)
    console.log(endTime)
    let startH = parseInt(startTime[0])
    let endH = parseInt(endTime[0])
    let startAMOrPM, endAMOrPM
    if (startH >= 12) {
        startAMOrPM = 'PM'
        if (startH == 12) {
            startH = 12
        } else {
            startH = startH - 12
        }
    } else {
        startAMOrPM = 'AM'
        startH = startH
    }
    if (endH >= 12) {
        endAMOrPM = 'PM'
        if (endH == 12) {
            endH = 12
        } else {
            endH = endH - 12
        }
    } else {
        endAMOrPM = 'AM'
        endH = endH
    }
    let chain = $('#chainValue').text()
    let searchStart = $("#SearchStartDate").val()
    let searchEnd = $("#SearchEndDate").val()
    let empNbr = $('#selectEmpNbr').val()
    let currentFreq=$("#freq").val()
    $('#empNbrModal').val(empNbr)
    $('#searchStartModal').val(searchStart)
    $('#searchEndModal').val(searchEnd)
    $('#chainModal').val(chain)
    $("#freqModal").val(currentFreq)
    if (startH >= 10) {
        $('#startHour').val(startH)
    } else {
        $('#startHour').val('0' + startH)
    }

    if (endH >= 10) {
        $('#endHour').val(endH)
    } else {
        $('#endHour').val('0' + endH)
    }
    $('#startAmOrPm').val(startAMOrPM)
    $('#endAmOrPm').val(endAMOrPM)
    let startTimeValue = startH + ':' + startTime[1] + ' ' + startAMOrPM
    let endTimeValue = endH + ':' + endTime[1] + ' ' + endAMOrPM
    $('#startTimeValue').val(startTimeValue)
    $('#endTimeValue').val(endTimeValue)
    $('#startMinute').val(startTime[1])
    $('#endMinute').val(endTime[1])
    $('#cancelAdd').hide()
    $('#deleteLeave').show()
    $('.edit-title').show()
    $('.new-title').hide()
    $('.firstSubmit').hide()
    $('.secondSubmit').show()
    $('#commentList').html('')
    for (let i = 0; i < comments.length; i++) {
        let html = '<p>' + comments[i].detail + '</p>'
        $('#commentList').append(html)
    }
    $("[name='leaveId']").attr('value', id + '')
    $("[name='leaveType']").val(leaveType)
    $('#absenceReason').val(absenceReason)
    $('#startDate').val(changeMMDDFormat(start_arry[0]))
    $('#endDate').val(changeMMDDFormat(end_arry[0]))
    $('#leaveHoursDaily').val(lvUnitsDaily)
    $('#totalRequested').val(lvUnitsUsed)
}

function deleteLeave(id) {
    let chain = $('#chainValue').text()
    let searchStart = $("#SearchStartDate").val()
    let searchEnd = $("#SearchEndDate").val()
    let empNbr = $('#selectEmpNbr').val()
    let currentFreq=$("#freq").val()
    $('#empNbrDelete').val(empNbr)
    $('#searchStartDelete').val(searchStart)
    $('#searchEndDelete').val(searchEnd)
    $('#chainDelete').val(chain)
    $("#freqDelete").val(currentFreq)
    $('#deleteId').val(id)
}
function changeMMDDFormat(date) {
    let dateArry = date.split('-')
    return dateArry[0]
}
function showOverviewCalendar() {
    setTimeout('initialLeaveCalendarStaticModal()', 100)
}
