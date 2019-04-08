var employeeList = directReportEmployee
var thisTrIndex, repeat, currentInputNbr
$(function() {
    changeLevel()
    initDateControl()
    judgeContent()
    initialCompleteList()
    var level = $('#level').val()
    var chainString = JSON.stringify(chain)
    var empNbr = $('#currentEmployee').text()
    var lengthNow = $('.setApprovers-list tbody tr').length
    if ($('#firstRow')) {
        $('#firstRow').text(lengthNow - 1)
    }
    $('#empNbr').val(empNbr)
    $('.chain').val(chainString)
    if (chain.length > 1) {
        $('#prevLevel')
            .removeClass('disabled')
            .removeAttr('disabled')
    } else {
        $('#prevLevel')
            .addClass('disabled')
            .attr('disabled', 'true')
    }
    $('#nextLevel').click(function() {
        $('#chain').val(chainString)
        $('#filterSupervisor')[0].submit()
    })
    $('#prevLevel').click(function() {
        $('#preChain').val(chainString)
        $('#previousLevel')[0].submit()
    })
    $('.add-new-row').click(function() {
        judgeContent()
        var trLen = $('.setApprovers-list tbody tr').length
        var approverLen = $('.setApprovers-list tbody tr.approver_tr').length
        var length = trLen
        var newRow =
            '<tr class="approver_tr">'
                '<td class="countIndex" scope="'+rowNbrLabel+'">' +length +
               '</td>'
                '<td scope="'+temporaryApproverLabel+'">'
                    '<div class="form-group">'
                        '<input class="form-control empControl" type="text" aria-label="'+temporaryApproverLabel+'" id="name_0'+length+'">'
                    '</div>'
                '</td>'
                '<td scope="'+fromDateLabel+'">'
                        '<div class="form-group">'
                            '<input class="form-control date-control dateFromControl" aria-label="'+fromDateLabel+'" type="text" autocomplete="off" id="fromDate_0'+length+'" placeholder="mm/dd/yyyy">'
                        '</div>'
                '</td>'
                '<td scope="'+toDateLabel+'">'
                    '<div class="form-group">'
                        '<input class="form-control  date-control dateToControl" aria-label="'+toDateLabel+'" type="text" autocomplete="off" id="toDate_0'+length+'" placeholder="mm/dd/yyyy"'>
                    '</div>'
                '</td>'
                '<td scope="'+deleteLabel+'">'
                        '<button type="button" role="button" class="a-btn" onclick="deleteRow(this)" aria-label="'+deleteBtnLabel+'">'
                            '<i class="fa fa-trash"></i>'
                       '</button>'
               '</td>'
            '</tr>'
                    
        console.log('tr that have empty field' + approverEmptyJson)
        if (!approverEmptyJson || approverEmptyJson.length < 1) {
            $('#errorComplete').hide()
            var errorLength = veryIfError()
            if (errorLength==0) {
                $('.setApprovers-list tbody tr:last-child').before(newRow)
                initialCompleteList()
            }
        } else {
            $('#errorComplete').show()
        }

        initLocalize(initialLocaleCode) //Initialize multilingual function
        initDateControl()
    })
    $('#reset').click(function() {
        $('#resetForm')[0].submit()
    })
    $('#saveSet').click(function() {
        initDateControl()
        $('#chainString').val(chainString)
        $('#empNbrForm').val(empNbr)
        var length = $('.approver_tr').length
        var resultApprover = []
        approverJson.forEach(function(item) {
            console.log(item)
            if(item.empNbr&&item.from&&item.to&&item.empNbr!=''&&item.from!=''&&item.to!=''){
                resultApprover.push(item)
            }
        })
        console.log(resultApprover)
        console.log('tr that have empty field' + approverEmptyJson)
        if (approverEmptyJson && approverEmptyJson.length > 0) {
            $('#errorComplete').show()
        } else {
            addedApprover.forEach(function(item, index){
                var approver = {
                    id: '',
                    empNbr: item.tmpApprvrEmpNbr,
                    from: item.datetimeFrom,
                    to: item.datetimeTo
                }
                resultApprover.push(approver)
            })
            console.log(resultApprover)
            $('#approverJson').val(JSON.stringify(resultApprover))
            $('#errorComplete').hide()
            var errorLength = veryIfError()
            if (errorLength==0) {
                $('#saveTempApprovers')[0].submit()
            }
        }
    })

    $(document).on('blur', '.empControl', function() {
        verifyRepeat()
        judgeContent()
    })
    $('.deleteApprover').click(function() {
        var id = $(this)
            .parents('.listTr')
            .find('.empId')
            .val()
        $(this)
            .parents('.listTr')
            .removeClass('listTr')
            .addClass('redTd')
        addedApprover = addedApprover.filter(function(value) {
            return value.tmpApprvrEmpNbr != id
        })
        // console.log("approver saved")
        // console.log(addedApprover)
        verifyRepeat()
    })
    $(document).on('blur','.dateToControl', function() {
        var fromValue=$(this).parents('.approver_tr').find('.dateFromControl').val()
        var toValue=$(this).val()
        var fromInput = changeDateYMD(fromValue)
        var toInput = changeDateYMD(toValue)
        console.log(fromInput)
        console.log(toInput)
        if(fromValue && toValue&&fromValue.length>=10&&toValue.length>=10){
            if(fromInput<=toInput){
                $("#errorDate").hide()
            }else{
                $("#errorDate").show()
            }
        }
    })
    $(document).on('input','.dateToControl', function() {
        var fromValue=$(this).parents('.approver_tr').find('.dateFromControl').val()
        var toValue=$(this).val()
        var fromInput = changeDateYMD(fromValue)
        var toInput = changeDateYMD(toValue)
        if(fromValue && toValue&&fromValue.length>=10&&toValue.length>=10){
            if(fromInput<=toInput){
                $("#errorDate").hide()
            }else{
                $("#errorDate").show()
            }
        }
    })
    $(document).on('blur','.dateFromControl', function() {
        var toValue=$(this).parents('.approver_tr').find('.dateToControl').val()
        var fromValue=$(this).val()
        var fromInput = changeDateYMD(fromValue)
        var toInput = changeDateYMD(toValue)
        console.log(fromInput)
        console.log(toInput)
        if(fromValue && toValue&&fromValue.length>=10&&toValue.length>=10){
            if(fromInput<=toInput){
                $("#errorDate").hide()
            }else{
                $("#errorDate").show()
            }
        }
    })
    $(document).on('input','.dateFromControl', function() {
        var toValue=$(this).parents('.approver_tr').find('.dateToControl').val()
        var fromValue=$(this).val()
        var fromInput = changeDateYMD(fromValue)
        var toInput = changeDateYMD(toValue)
        if(fromValue && toValue&&fromValue.length>=10&&toValue.length>=10){
            if(fromInput<=toInput){
                $("#errorDate").hide()
            }else{
                $("#errorDate").show()
            }
        }
    })
})
function changeDateYMD(date){
    var dateArry = date.split("/")
    var DateFormat = new Date(dateArry[2]+"-"+dateArry[0]+"-"+dateArry[1])
    return DateFormat
}
function verifyRepeat() {
    repeat = 0
    addedApprover.forEach(function(item, index) {
        approverJson.forEach(function(emp, index){
            if (emp.empNbr && emp.empNbr == item.tmpApprvrEmpNbr) {
                console.log(emp.empNbr)
                console.log(item.tmpApprvrEmpNbr)
                repeat++
            }
        })
    })
    approverJson.forEach(function(emp, index){
        approverJson.forEach(function(item, index) {
            if (
                emp.empNbr &&
                item.empNbr &&
                emp.empNbr == item.empNbr &&
                emp.domId != item.domId
            ) {
                console.log(emp.empNbr)
                console.log(item.empNbr)
                repeat++
            }
        })
    })
    console.log('repeat' + repeat)
    if (repeat > 0) {
        $('#repeatError').show()
        $('#saveSet')
            .addClass('disabled')
            .attr('disabled', 'disabled')
    } else {
        $('#repeatError').hide()
        $('#saveSet')
            .removeClass('disabled')
            .removeAttr('disabled')
    }
}
function initialCompleteList() {
    $('.empControl').each(function() {
        var input = this
        $(this)
            .autocomplete(employeeList, {
                max: 10, //
                minChars: 0, //
                width: $(this).width() + 1, //
                scrollHeight: 300, //
                matchContains: true, //
                autoFill: true, //
                formatItem: function(row, i, max) {
                    if (row.employeeNumber) {
                        $('#noResultError').hide()
                        $('#saveSet')
                            .removeClass('disabled')
                            .removeAttr('disabled')
                        return (
                            row.employeeNumber +
                            '-' +
                            row.firstName +
                            ',' +
                            row.lastName +
                            row.firstName
                        )
                    } else {
                        console.log('no result')
                        if ($(input).val() && $(input).val() != '') {
                            $('#noResultError').show()
                            $('#saveSet')
                                .addClass('disabled')
                                .attr('disabled', 'disabled')
                        }
                        $('.ac_results').hide()
                    }
                },
                formatMatch: function(row, i, max) {
                    return (
                        row.employeeNumber +
                        '-' +
                        row.firstName +
                        ',' +
                        row.lastName +
                        row.firstName
                    )
                },
                formatResult: function(row) {
                    return (
                        row.employeeNumber +
                        '-' +
                        row.firstName +
                        ',' +
                        row.lastName +
                        row.firstName
                    )
                }
            })
            .result(function(event, row, formatted) {
                judgeContent()
            })
    })
}

function veryIfError(){
    var i = 0
    $(".errorList .error-hint").each(function(){
        if($(this).is(':visible')){
            i++
        }
    });
    return i
}
function deleteRow(dom) {
    var length = $('.setApprovers-list tbody .approver_tr').length
    $(dom)
        .parents('.approver_tr')
        .removeClass('approver_tr')
        .addClass('redTd')
    $("#errorDate").hide()
    judgeContent()
    verifyRepeat()
}
var checkin = []
var checkout = []
var haveEndDate = []
function initDateControl() {
    $('.approver_tr').each(function(index) {
        haveEndDate[index] = false
        var fromCalendar = $(this).find('.dateFromControl')
        var toCalendar = $(this).find('.dateToControl')
        checkin[index] = fromCalendar
            .fdatepicker({
                format: 'mm/dd/yyyy',
                language: initialLocaleCode,
                onRender: function(date) {
                    // if(checkout[index]&&haveEndDate[index]){
                    //     return date.valueOf() > checkout[index].date.valueOf() ? 'disabled' : '';
                    // }
                }
            })
            .on('changeDate', function(ev) {
                console.log(ev)
                var endDate = toCalendar.val()
                var startDate = fromCalendar.val()
                if (
                    ev.date &&
                    (ev.date.valueOf() >= checkout[index].date.valueOf() ||
                        !endDate ||
                        endDate == '')
                ) {
                    startDate = new Date(startDate)
                    startDate.setDate(startDate.getDate())
                    checkout[index].update(startDate)
                    toCalendar.change()
                    judgeContent()
                }
            })
            .data('datepicker')

        checkout[index] = toCalendar
            .fdatepicker({
                format: 'mm/dd/yyyy',
                language: initialLocaleCode,
                onRender: function(date) {
                    return date.valueOf() < checkin[index].date.valueOf()
                        ? 'disabled'
                        : ''
                }
            })
            .on('changeDate', function(ev) {
                console.log(ev)
                if (ev.date) {
                    haveEndDate[index] = true
                }
                judgeContent()
            })
            .data('datepicker')
    })
    setGlobal()
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

var noEmpty = 0
var approverJson = []
var approverEmptyJson = []

function judgeContent() {
    approverJson = []
    approverEmptyJson = []
    noEmpty = 0
    var length = $('.approver_tr').length
    $('.approver_tr').each(function(index) {
        var empNbr = $(this)
            .find('.empControl')
            .val()
        var empArry = empNbr.split('-')
        var from = $(this)
            .find('.dateFromControl')
            .val()
        var to = $(this)
            .find('.dateToControl')
            .val()
        var obj
        if (empNbr == '' || from == '' || to == '') {
        } else {
            noEmpty += 1
        }
        obj = {
            id: '',
            domId: index,
            empNbr: empArry[0],
            from: from,
            to: to
        }
        if (obj && obj != '') {
            approverJson.push(obj)
        }
        if (
            (empNbr == '' && from == '' && to == '') ||
            (empNbr != '' && from != '' && to != '')
        ) {
        } else {
            approverEmptyJson.push(index)
        }
    })
    console.log("just added +++ have empty field  tr")
    console.log(approverJson)
    console.log(approverEmptyJson)
}
