var employeeList = directReportEmployee
var thisTrIndex, repeat, currentInputNbr
$(function() {
    changeLevel()
    initDateControl()
    judgeContent()
    initialCompleteList()
    let level = $('#level').val()
    let chainString = JSON.stringify(chain)
    let empNbr = $('#currentEmployee').text()
    let lengthNow = $('.setApprovers-list tbody tr').length
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
        let trLen = $('.setApprovers-list tbody tr').length
        let approverLen = $('.setApprovers-list tbody tr.approver_tr').length
        let length = trLen
        let newRow =
            `<tr class="approver_tr">
                                            <td class="countIndex" data-localize="setTemporaryApprovers.rowNbr"
                                                data-localize-location="scope">` +
            length +
            `</td>
                                            <td data-localize="setTemporaryApprovers.temporaryApprover" data-localize-location="scope">
                                                <div class="form-group">
                                                    <input class="form-control empControl" type="text" 
                                                    title=""
                                                    data-localize="setTemporaryApprovers.temporaryApprover"
                                                    name="" 
                                                    id="name_01">
                                                </div>
                                            </td>
                                            <td data-localize="setTemporaryApprovers.fromDate" data-localize-location="scope">
                                                    <div class="form-group">
                                                        <input class="form-control date-control dateFromControl"
                                                        title=""
                                                        data-title=""
                                                        data-localize="setTemporaryApprovers.fromDate" type="text" 
                                                        name=""  autocomplete="off"
                                                        id="fromDate_01" placeholder="mm/dd/yyyy">
                                                    </div>
                                            </td>
                                            <td data-localize="setTemporaryApprovers.toDate" data-localize-location="scope">
                                                <div class="form-group">
                                                    <input class="form-control  date-control dateToControl"
                                                    title=""
                                                    data-title=""
                                                    data-localize="setTemporaryApprovers.toDate" type="text" 
                                                    name=""  autocomplete="off"
                                                    id="toDate_01" placeholder="mm/dd/yyyy">
                                                </div>
                                            </td>
                                            <td  data-localize="setTemporaryApprovers.delete" data-localize-location="scope">
                                                    <button type="button" class="a-btn" onclick="deleteRow(this)">
                                                     <span class="hide" data-localize="label.delete"></span>
                                                        <i class="fa fa-trash"></i>
                                                    </button>
                                            </td>
                                        </tr>
                    `
        console.log('tr that have empty field' + approverEmptyJson)
        if (!approverEmptyJson || approverEmptyJson.length < 1) {
            $('.setApprovers-list tbody tr:last-child').before(newRow)
            initialCompleteList()
            $('#errorComplete').hide()
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
        let length = $('.approver_tr').length
        let resultApprover = []
        approverJson.forEach((item) => {
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
            addedApprover.forEach((item, index) => {
                let approver = {
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
            if (!$('#noResultError').is(':visible')) {
                $('#saveTempApprovers')[0].submit()
            }
        }
    })

    $(document).on('blur', '.empControl', function() {
        verifyRepeat()
        judgeContent()
    })
    $('.deleteApprover').click(function() {
        let id = $(this)
            .parents('.listTr')
            .find('.empId')
            .val()
        $(this)
            .parents('.listTr')
            .removeClass('listTr')
            .addClass('redTd')
        addedApprover = addedApprover.filter((value) => {
            return value.tmpApprvrEmpNbr != id
        })
        // console.log("approver saved")
        // console.log(addedApprover)
        verifyRepeat()
    })
})
function verifyRepeat() {
    repeat = 0
    addedApprover.forEach((item, index) => {
        approverJson.forEach((emp, index) => {
            if (emp.empNbr && emp.empNbr == item.tmpApprvrEmpNbr) {
                console.log(emp.empNbr)
                console.log(item.tmpApprvrEmpNbr)
                repeat++
            }
        })
    })
    approverJson.forEach((emp, index) => {
        approverJson.forEach((item, index) => {
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
        let input = this
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

function deleteRow(dom) {
    let length = $('.setApprovers-list tbody .approver_tr').length
    $(dom)
        .parents('.approver_tr')
        .removeClass('approver_tr')
        .addClass('redTd')
    judgeContent()
    verifyRepeat()
}
var checkin = []
var checkout = []
var haveEndDate = []
function initDateControl() {
    let nowTemp = new Date()
    $('.approver_tr').each(function(index) {
        haveEndDate[index] = false
        let fromCalendar = $(this).find('.date-control[data-title="from"]')
        let toCalendar = $(this).find('.date-control[data-title="to"]')
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
                let endDate = toCalendar.val()
                let startDate = fromCalendar.val()
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

var noEmpty = 0
var approverJson = []
var approverEmptyJson = []

function judgeContent() {
    approverJson = []
    approverEmptyJson = []
    noEmpty = 0
    let length = $('.approver_tr').length
    $('.approver_tr').each(function(index) {
        let empNbr = $(this)
            .find('.empControl')
            .val()
        let empArry = empNbr.split('-')
        let from = $(this)
            .find('.dateFromControl')
            .val()
        let to = $(this)
            .find('.dateToControl')
            .val()
        let obj
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
