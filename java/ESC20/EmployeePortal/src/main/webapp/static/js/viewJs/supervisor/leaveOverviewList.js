var chainString = JSON.stringify(chain)
var reasonOption
console.log(chainString)
$(function() {
    if(isAddValue == 'true'){
        $("#requestModal").modal('show')
        $('#requestForm').attr('action', 'updateLeaveFromLeaveOverview')
        var empNbr = $('#selectEmpNbr').val()
        $('#empNbrModal').val(empNbr)
        $('#chainModal').val(chainString)
        var searchStart = $("#SearchStartInput").val()
        var searchEnd = $("#SearchEndInput").val()
        $('#searchStartModal').val(searchStart)
        $('#searchEndModal').val(searchEnd)
        $(".edit-title").hide()
        $(".secondSubmit").hide();
        $("#deleteLeave").hide();	
    }
    var selectHtml = $("#freq").html().trim();
    console.log(selectHtml)
    if(selectHtml==''){
        // $("#freq").remove()
        setTimeout(function(){
            // var freqHtml = '<select class="form-control" name="freq" id="freq" onchange="changeFreq()"></select>'
            // $('.freqGroup').append(freqHtml)
            $("#freq").empty()
        },300)
       
    }
    reasonOption = $("#absenceReason").html()
    changeLevel()
    $('.chain').val(chainString)
    var level = $('#level').val()
    console.log(initialLocaleCode)
    $('#SearchStartDate').fdatepicker({
        format: 'mm-dd-yyyy',
        language: initialLocaleCode,
        weekStart:7
    })
    $('#SearchEndDate').fdatepicker({
        format: 'mm-dd-yyyy',
        language: initialLocaleCode,
        weekStart:7
    })
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
        var chain = $('#chainValue').text()
        console.log(chain)
        $('#chain').val(chain)
        $('.isChangeLevel').val(true)
        $('#filterSupervisor')[0].submit()
    })
    $('#prevLevel').click(function() {
        var chain = $('#chainValue').text()
        console.log(chain)
        $('#preChain').val(chain)
        $('.isChangeLevel').val(true)
        $('#previousLevel')[0].submit()
    })
    initList()

    $('.sureDelete').click(function() {
        var chain = $('#chainValue').text()
        var searchStart = $("#SearchStartInput").val()
        var searchEnd = $("#SearchEndInput").val()
        var empNbr = $('#selectEmpNbr').val()
        var currentFreq=$("#freq").val()
        $('#empNbrDelete').val(empNbr)
        $('#searchStartDelete').val(searchStart)
        $('#searchEndDelete').val(searchEnd)
        $('#chainDelete').val(chain)
        $("#freqDelete").val(currentFreq)
        $('#deleteForm')[0].submit()
    })
})
function initList() {
    var employeeSelect = $('#selectEmpNbr').val()
    if (!employeeSelect || employeeSelect == '') {
        var options = "<option values=''></option>"
        $('#freq').html('options')
        $('#new-btn')
            .addClass('disabled')
            .attr('disabled', 'disabled')
        var noResult = '<tr>'+
                            '<td colspan="8">'+
                                '<span>'+ noDataLabel +'</span>'+
                            '</td>'+
                        '</tr>'
        $('#leaveOverviewList tbody').html(noResult)
    } else {
        $('#leaveOverviewList tbody tr').removeClass('hide')
    }
    var requester = $('#selectEmpNbr option:selected').text().trim()
    if (requester && requester != '') {
        $('#forWord').removeClass('hide')
        $('#currentLeaveRequests').text(requester)
        $('#currentEmpModal').text(requester)
    } else {
        $('#forWord').addClass('hide')
        $('#currentLeaveRequests').text('')
        $('#currentEmpModal').text('')
    }
}
function changeLevel() {
    var selectNum = $('#selectEmpNbr').val()
    var numDirect = 0
    directReportEmployee.forEach(function(element) {
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
    $("#absenceReason").html(reasonOption)
    $('#cancelAdd').show()
    $('#deleteLeave').hide()
    $('.modal-title').hide()
    $('.firstSubmit').show()
    $('.secondSubmit').hide()
    $(".availableError").hide()
    $('#chainModal').val(chainString)
    var empNbr = $('#selectEmpNbr').val()
    $('#empNbrModal').val(empNbr)
    $("#leaveModalTitle").show()
    $("#leaveModalTitle .editSpan").hide()
    $("#leaveModalTitle .addSpan").show()
    $('#requestForm').attr('action', 'updateLeaveFromLeaveOverview')
    $("#commentList").html("")
    $(".timeUnit").hide()
    $(".leaveHoursDailyNotZero").hide()
    $(".leaveHoursDailyWrap").removeClass('has-error')
    $(".dateTimePeriodOverlap").hide()
}
function changeFreq() {
    var select = $('#freq').val()
    $('.selectFreq').val()
    // $("#changeFreqForm")[0].submit();
}
function changeEmployee() {
    var selectNum = $('#selectEmpNbr').val()
    $('#SearchStartInput').val('')
    $('#SearchEndInput').val('')
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
    var comments
    leaveList.forEach(function(element) {
        if (element.id == id) {
            console.log(element)
            comments = element.comments
        }
    })
    $('#requestForm')
        .data('bootstrapValidator')
        .destroy()
    $('#requestForm').data('bootstrapValidator', null)
    $('.dateValidator').hide()
	$('.dateValidator01').hide()
    formValidator()
    console.log(leaveStartDate)
    console.log(leaveEndDate)
    var start_arry = leaveStartDate.split(" ")
    var end_arry = leaveEndDate.split(" ")
    var startTime = start_arry[1].split(":")
    var endTime = end_arry[1].split(":")
    var startH = parseInt(startTime[0])
    var endH = parseInt(endTime[0])
    var startAMOrPM,endAMOrPM;
    startH = startTime[0].trim();
    startAMOrPM = start_arry[2].trim();
    endH = endTime[0].trim();
    endAMOrPM = end_arry[2].trim();

    var chain = $('#chainValue').text()
    var searchStart = $("#SearchStartInput").val()
    var searchEnd = $("#SearchEndInput").val()
    var empNbr = $('#selectEmpNbr').val()
    var currentFreq=$("#freq").val()
    $('#empNbrModal').val(empNbr)
    $('#searchStartModal').val(searchStart)
    $('#searchEndModal').val(searchEnd)
    $('#chainModal').val(chain)
    $("#freqModal").val(currentFreq)
    $("#startHour").val(startH);
    $("#endHour").val(endH);
    $("#startAmOrPm").val(startAMOrPM)
    $("#endAmOrPm").val(endAMOrPM)
    var startTimeValue = startH + ':' + startTime[1] + ' ' + startAMOrPM
    var endTimeValue = endH + ':' + endTime[1] + ' ' + endAMOrPM
    $("#startTimeValue").val(startTimeValue)
    $("#endTimeValue").val(endTimeValue)
    $("#startMinute").val(startTime[1])
    $("#endMinute").val(endTime[1])
    // $('#cancelAdd').hide()
    // $('#deleteLeave').show()
    $('.modal-title').hide()
    $('.firstSubmit').hide()
    $(".availableError").hide()
    $('.secondSubmit').show()
    $("#leaveModalTitle").show()
    $("#leaveModalTitle .editSpan").show()
    $("#leaveModalTitle .addSpan").hide()
    $('#commentList').html('')
    for (var i = 0; i < comments.length; i++) {
        var html = '<p>' + comments[i].detail + '</p>'
        $('#commentList').append(html)
    }
    $("[name='leaveId']").attr('value', id + '')
    $("[name='leaveType']").val(leaveType)
    changeLeaveType()
    $('#absenceReason').val(absenceReason)
    $('#startDateInput').val(start_arry[0])//changeMMDDFormat(start_arry[0])
    $('#endDateInput').val(end_arry[0])//changeMMDDFormat(end_arry[0])
    $('#leaveHoursDaily').val(lvUnitsDaily)
    $('#totalRequested').val(lvUnitsUsed)
    $(".dateTimePeriodOverlap").hide()
}

function deleteLeave(id) {
    var chain = $('#chainValue').text()
    var searchStart = $("#SearchStartInput").val()
    var searchEnd = $("#SearchEndInput").val()
    var empNbr = $('#selectEmpNbr').val()
    var currentFreq=$("#freq").val()
    $('#empNbrDelete').val(empNbr)
    $('#searchStartDelete').val(searchStart)
    $('#searchEndDelete').val(searchEnd)
    $('#chainDelete').val(chain)
    $("#freqDelete").val(currentFreq)
    $('#deleteId').val(id)
}
function changeMMDDFormat(date) {
    var dateArry = date.split('-')
    return dateArry[0]
}
function showOverviewCalendar() {
    setTimeout('initialLeaveCalendarStaticModal()', 100)
}
