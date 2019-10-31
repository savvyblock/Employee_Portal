var employeeList = directReportEmployee
var thisTrIndex, repeat, currentInputNbr
var resultDeleteApprover = addedApprover,emptyRow = 0,overlapsRow = false
$(function() {
    changeLevel()
    initDateControl()
    // judgeContent()
    initialCompleteList()
    var level = $('#level').val()
    var chainString = JSON.stringify(chain)
    var empNbr = $('#currentEmployee').text()
    $('#empNbrForm').val(empNbr)
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
        addTemporaryApproverRow()
    })
    $('#reset').click(function() {
        $('#resetForm')[0].submit()
    })
    $('#saveSet').click(function() {
        judgeContent()
        console.log(emptyRow)
        console.log(overlapsRow)
        console.log(resultDeleteApprover)
        var resultApprover = []
        if(emptyRow == 0 && !overlapsRow){
            approverJson.forEach(function(item) {
                console.log(item)
                if(item.empNbr&&item.from&&item.to&&item.empNbr!=''&&item.from!=''&&item.to!=''){
                   // var toDate = getMoreDay(item.to)
                    var approver = {
                                id: item.id,
                                empNbr: item.empNbr,
                                from: item.from,
                                to: item.to
                            }
                    resultApprover.push(approver)
                }
            })
            // resultDeleteApprover.forEach(function(item, index){
            //     var approver = {
            //         id: '',
            //         empNbr: item.tmpApprvrEmpNbr,
            //         from: item.datetimeFrom,
            //         to: item.datetimeTo
            //     }
            //     resultApprover.push(approver)
            // })
            console.log(resultApprover)
            $('#approverJson').val(JSON.stringify(resultApprover))
            if(showDeleteConfirm){
                if(deleteRowQuery(event)){
                    $('#saveTempApprovers')[0].submit()
                }
            }else{
                $('#saveTempApprovers')[0].submit()
            }
            
        }

    })
    var idArry = []
    $('.deleteApprover').click(function() {
        var tr = $(this).parents('tr')
        if(tr.hasClass('redTd')){
            var id = $(this).parents('tr').find('.trId').val()
            $(this).parents('tr').removeClass('redTd').addClass('listTr')
            for(var i = 0,len = idArry.length;i<len;i++){
                if(id == [i]){
                    idArry.splice(i,1);
                }
            }
            for(var i =0,len = addedApprover.length;i<len;i++){
                if(addedApprover[i].id == id){
                    resultDeleteApprover.push(addedApprover[i])
                }
            }

        }else{
            var id = $(this).parents('tr').find('.trId').val()
            idArry.push(id)
            $(this).parents('tr').removeClass('listTr').addClass('redTd')
            resultDeleteApprover = addedApprover.filter(function(value) {
                var equal = false
                for(var i = 0,len = idArry.length;i<len;i++){
                    if(value.id == idArry[i]){
                        equal = true
                    }
                }
                if(!equal){
                    return value
                }
            })
        }
        console.log(resultDeleteApprover)
    })
    $(".dateFromControl .date-control").change(function(){
        var tr = $(this).parents('tr')
        var fromDate = $(this).val()
        if(tr.hasClass('redTd')){
            // var id = $(this).parents('tr').find('.trId').val()
            // for(var i =0,len = addedApprover.length;i<len;i++){
            //     if(addedApprover[i].id == id){
            //         resultDeleteApprover.push(addedApprover[i])
            //     }
            // }

        }else{
            var id = $(this).parents('tr').find('.trId').val()
            resultDeleteApprover = resultDeleteApprover.filter(function(value) {
                if(value.id == id){
                    console.log(fromDate)
                    value.datetimeFrom = fromDate
                }
                return value
            })
        }
        console.log(resultDeleteApprover)
    })
    // $(document).on('blur','.empControl', function() {
    //     var numberData = $(this).attr('data-number')
    //     console.log(numberData)
    //     var that = $(this)
    //     $.ajax({
    //         type:'POST',
    //         url:urlMain + '/leaveRequestTemporaryApprovers/isEmpNumberCorrect',
    //         data:numberData,
    //         success : function (res) {
    //             res = false
    //             if(res){
    //                 that.parents('.form-group').removeClass('has-error').find('.help-block.invalid').hide()
    //             }else{
    //                 that.parents('.form-group').addClass('has-error').find('.help-block.invalid').show()
    //             }
    //         },
    //         error:function(res){
    //              console.log(res);
    //         }
    //     })
    // })
    $(document).on('blur','.dateToControl', function() {
        var fromValue=$(this).parents('tr').find('.dateFromControl .date-control').val()
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
    $(document).on('input','.dateToControl .date-control', function() {
        var fromValue=$(this).parents('tr').find('.dateFromControl .date-control').val()
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
    $(document).on('blur','.dateFromControl .date-control', function() {
        console.log(">>>>>>>>>>>>")
        var toValue=$(this).parents('tr').find('.dateToControl .date-control').val()
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
    $(document).on('input','.dateFromControl .date-control', function() {
        var toValue=$(this).parents('tr').find('.dateToControl .date-control').val()
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
    var dateArry = date.split("-")
    var DateFormat = new Date(dateArry[2]+"-"+dateArry[0]+"-"+dateArry[1])
    return DateFormat
}
function getMoreDay(date){
    var dateArry = date.split("-")
    var dateNew = new Date(dateArry[2]+"-" +dateArry[0] +"-"+dateArry[1]);
    var newDay = parseInt(dateArry[1]) + 1
    // dateNew.setDate(dateNew.getDate() + 1);
    var year = dateArry[2]
    var month = dateArry[0]//dateNew.getMonth() + 1 >9?(dateNew.getMonth() + 1):'0'+ (dateNew.getMonth() + 1)
    var day = newDay >9?newDay:'0'+newDay
    var DateFormat = month +"-" +day +"-" + year
    return DateFormat
}

function initialCompleteList(dataList) {
    $('.empControl').each(function() {
        var input = this
        $(input).autocomplete({
            source: function(request,response){
                $.ajax({
                    type:'POST',
                    url:urlMain + '/leaveRequestTemporaryApprovers/getEmployeeTempApproverSearch',
                    data:{
                        searchStr:$(input).val().trim(), 
                        excludeNbr:$('#currentEmployee').text()
                    },
                    success : function (res) {
                        var data = res.tempApprover
                        console.log(data)
                        if(data.length>0){
                            $(input).parents('.form-group').removeClass('has-error').find('.help-block.invalid').hide().removeClass("shown")
                            $("#saveSet").removeAttr('disabled')
                            response($.map(data, function(item){
                                return {
                                    label: item.code + " : " + item.description,
                                    value: item.code + " : " + item.description,
                                    empNbr: item.code
                                }
                            }));
                        }else{
                            $(input).parents('.form-group').addClass('has-error').find('.help-block.invalid').show().addClass("shown")
                            $("#saveSet").attr('disabled','disabled') 
                        }
                        setTimeout(function(){
                            $(".setApprovers-list .has-error .help-block").hide()
                        },1500)
                        
                    },
                    error:function(res){
                         console.log(res);
                         response(res)
                    }
                })
            },
            select: function(event, ui) {
                console.log(ui)
				$(this).val(ui.item.value);
				// copy the employee number into the attribute data-number 
				$(this).attr('data-number',ui.item.empNbr);
				// set the value into the text field
				var inputObj = $(this).closest("td").find("input.empControl")[0];
				
				// auto tab as long as the selection was made without the use of the tab key
				var charCode = (event.which) ? event.which : event.keyCode;
				if (charCode!=9) {
					if ($(inputObj).hasClass("lastRowTabOut")) {
						// this is the last row in the table... add a row
						addTemporaryApproverRow();
					}
					focusNext(inputObj);
				}
            }
        });
    })
}

function addTemporaryApproverRow(){
    var trLen = $('.setApprovers-list tbody tr').length
    var approverLen = $('.setApprovers-list tbody tr.approver_tr').length
    var length = trLen
    var newRow =
            '<tr class="approver_tr">'+
		            '<td style="text-align:center" scope="'+deleteLabel+'">'+
		            '<button type="button" role="button" class="a-btn" onclick="deleteRow(this)" aria-label="'+deleteBtnLabel+'">'+
		                '<i class="fa fa-trash"></i>'+
		           '</button>'+
		   '</td>'+
                '<td class="countIndex" scope="'+rowNbrLabel+'">' +length +
               '</td>'+
                '<td scope="'+temporaryApproverLabel+'">'+
                    '<div class="form-group">'+
                        '<input class="form-control empControl lastRowTabOut" type="text" onblur="onBlurTempApproverEntry(event)" aria-label="'+temporaryApproverLabel+'" id="name_0'+length+'">'+
                        '<small class="help-block invalid" role="alert" aria-atomic="true" style="display:none;">'+employeeInvalid+'</small>'+
                        '<small class="help-block required" role="alert" aria-atomic="true" style="display:none;">'+enterSelectEmp+'</small>'+
                    '</div>'+
                '</td>'+
                '<td scope="'+fromDateLabel+'">'+
                    '<div class="form-group">'+
                        '<div class="fDateGroup date dateFromControl" data-date-format="mm-dd-yyyy">'+
                            '<button class="prefix" type="button" aria-label="'+showDatepickerLabel+'"><i class="fa fa-calendar"></i></button>'+
                            '<input class="form-control dateInput date-control" aria-label="'+fromDateLabel+'" type="text" autocomplete="off" id="fromDate_0'+length+'" placeholder="mm-dd-yyyy">'+
                            '<small class="help-block required" role="alert" aria-atomic="true" style="display:none;">'+selectAFromDate+'</small>'+
                            '<small class="help-block overlapsDate" role="alert" aria-atomic="true" style="display:none;">'+overlapsDate+'</small>'+
                        '</div>'+
                    '</div>'+
                '</td>'+
                '<td scope="'+toDateLabel+'">'+
                    '<div class="form-group">'+
                        '<div class="fDateGroup date dateToControl" data-date-format="mm-dd-yyyy">'+
                            '<button class="prefix" type="button" aria-label="'+showDatepickerLabel+'"><i class="fa fa-calendar"></i></button>'+
                            '<input class="form-control dateInput  date-control" aria-label="'+toDateLabel+'" type="text" autocomplete="off" id="toDate_0'+length+'" placeholder="mm-dd-yyyy">'+
                            '<small class="help-block required" role="alert" aria-atomic="true" style="display:none;">'+selectAToDate+'</small>'+
                            '<small class="help-block overlapsDate" role="alert" aria-atomic="true" style="display:none;">'+overlapsDate+'</small>'+
                        '</div>'+
                    '</div>'+
                '</td>'+
            '</tr>'
     $('.setApprovers-list tbody tr:last-child').before(newRow)
     $(".setApprovers-list").find("input.lastRowTabOut").removeClass("lastRowTabOut");
    $(".setApprovers-list").find("input.empControl:last").addClass("lastRowTabOut");
    initialCompleteList()
    initLocalize(initialLocaleCode) //Initialize multilingual function
    initDateControl()
}

function deleteRow(dom) {
    var domTr =  $(dom).parents('tr')
    if(domTr.hasClass('approver_tr')){
        domTr.removeClass('approver_tr').addClass('redTd')
    }else{
        domTr.addClass('approver_tr').removeClass('redTd')
    }

}
var checkin = []
var checkout = []
function initDateControl() {
    $('.setApprovers-list tr').each(function(index) {
        var fromCalendar = $(this).find('.dateFromControl')
        var toCalendar = $(this).find('.dateToControl')
        var fromDateDom = $(this).find('.dateFromControl .date-control')
        var toDateDom = $(this).find('.dateToControl .date-control')
        checkin[index] = fromCalendar
            .fdatepicker({
                format: 'mm-dd-yyyy',
                language: initialLocaleCode,
				weekStart:7,
                onRender: function(date) {
                    // if(checkout[index]&&haveEndDate[index]){
                    //     return date.valueOf() > checkout[index].date.valueOf() ? 'disabled' : '';
                    // }
                }
            })
            .on('changeDate', function(ev) {
                var endDate = toDateDom.val()
                var startDate = fromDateDom.val()
                if (ev.date){
                    console.log(ev.date)
                    console.log(checkout[index].date)
                    console.log(endDate)
                    if(endDate&&endDate!=''&&checkout[index].date && ev.date.valueOf()>checkout[index].date.valueOf()){
                        startDate = new Date(startDate)
                        startDate.setDate(startDate.getDate())
                        checkout[index].update(startDate)
                        toDateDom.change()
                    }
                    setTimeout(function(){
                        focusNext(fromDateDom)
                    },100)
                }
            })
            .data('datepicker')

        checkout[index] = toCalendar
            .fdatepicker({
                format: 'mm-dd-yyyy',
                language: initialLocaleCode,
				weekStart:7,
                onRender: function(date) {
                    return date.valueOf() < checkin[index].date.valueOf()
                        ? 'disabled'
                        : ''
                }
            })
            .on('changeDate', function(ev) {
                console.log(ev.date)
                var dateStart = checkin[index].date
                console.log(dateStart)
                if(ev.date && dateStart.valueOf()>ev.date.valueOf()){
                    fromDateDom.val('')
                    fromDateDom.change()
                }
            })
            .data('datepicker')
    })
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

var approverJson = []
var showDeleteConfirm = false

function judgeContent() {
    showDeleteConfirm = false
    approverJson = []
    emptyRow = 0
    var length = $('.approver_tr').length
    var lengthRed = $('.redTd').length
    if(lengthRed>0){
        showDeleteConfirm = true
    }
    $('.setApprovers-list tbody tr').each(function(index) {
        if(!$(this).hasClass('redTd') && !$(this).hasClass('add-tr')){
            var empNbr = $(this)
                .find('.empControl')
                .val()
            var trId = $(this).find('.trId').val()
            var empArry = empNbr&&empNbr!=''?empNbr.split(':'):[]
            var from = $(this)
                .find('.dateFromControl .date-control')
                .val()
            var to = $(this)
                .find('.dateToControl .date-control')
                .val()
            // Verify that the input is complete
            if((empNbr == ''&& from == '' && to == '')||(empNbr != ''&& from != '' && to != '')){
                $(this).find('.empControl').parents('.form-group').removeClass('has-error').find('.help-block.required').hide().removeClass("shown")
                $(this).find('.dateFromControl').parents('.form-group').removeClass('has-error').find('.help-block.required').hide().removeClass("shown")
                $(this).find('.dateToControl').parents('.form-group').removeClass('has-error').find('.help-block.required').hide().removeClass("shown")
            }else{
                emptyRow++
                if(empNbr == ''){
                    $(this).find('.empControl').parents('.form-group').addClass('has-error').find('.help-block.required').show().addClass("shown")
                }else{
                    $(this).find('.empControl').parents('.form-group').removeClass('has-error').find('.help-block.required').hide().removeClass("shown")
                }
                if(from == ''){
                    $(this).find('.dateFromControl').parents('.form-group').addClass('has-error').find('.help-block.required').show().addClass("shown")
                }else{
                    $(this).find('.dateFromControl').parents('.form-group').removeClass('has-error').find('.help-block.required').hide().removeClass("shown")
                }
                if(to == ''){
                    $(this).find('.dateToControl').parents('.form-group').addClass('has-error').find('.help-block.required').show().addClass("shown")
                }else{
                    $(this).find('.dateToControl').parents('.form-group').removeClass('has-error').find('.help-block.required').hide().removeClass("shown")
                }
            }
            // Verify that there are overlapping dates
            var obj
            if(empNbr.trim() != '' && from != '' && to != ''){
                obj = {
                    id: trId,
                    dom:$(this),
                    domId: index,
                    empNbr: empArry[0].trim(),
                    from: from,
                    to: to
                }
                approverJson.push(obj)
            }
        }   
    })
    overlapsRow = verifyOverlappingDate(approverJson)
    console.log(overlapsRow)
    console.log(approverJson)
    setTimeout(function(){
        $(".setApprovers-list .has-error .help-block").hide()
    },1500)
}
function verifyOverlappingDate(json){
    var jsonArry =  []
    // resultDeleteApprover.forEach(function(item, index){
    //     var approver = {
    //         id: '',
    //         empNbr: item.tmpApprvrEmpNbr,
    //         from: item.datetimeFrom,
    //         to: item.datetimeTo
    //     }
    //     jsonArry.push(approver)
    // })
    json.forEach(function(item, index){
        jsonArry.push(item)
    })
    console.log(jsonArry)
    var repeatDate = 0
    for(var i = 0;i< jsonArry.length-1;i++){
        for(var j=i+1;j<jsonArry.length;j++){
            if(
                changeDateYMD(jsonArry[i].from).valueOf() <= changeDateYMD(jsonArry[j].to).valueOf() 
                && 
                changeDateYMD(jsonArry[i].to).valueOf() >= changeDateYMD(jsonArry[j].from).valueOf()
            ){
                console.log(jsonArry[j])
                $(jsonArry[j].dom).find('.dateFromControl,.dateToControl').parents('.form-group').addClass("has-error").find(".help-block.overlapsDate").show().addClass("shown")
                repeatDate++
                
            }
        }
    }
    if(repeatDate>0){
        console.log(repeatDate)
        return true;
    }
}

function showCalendarModal(){
    setTimeout("initialLeaveCalendarModal()",100)
}

function focusNext(obj) {
	var browser='';
	if (document.selection) {
		obj.focus ();
		var sel = document.selection.createRange();
		sel.moveStart ('character', -obj.value.length);
		browser = "IE";
	} else if (obj.selectionStart || obj.selectionStart == '0') {
		browser="Firefox";
	}

	var inputs = $('input:visible');
	var indexx = inputs.index(obj);
	var nextInput = inputs.get(indexx + 1);
    if (nextInput) {
		if(browser == "Firefox")
	    {
		   	nextInput.selectionStart = 0;
			nextInput.selectionEnd = 0;
	   	}
	   	nextInput.focus();
    }
}
function onBlurTempApproverEntry(event) {
	var trObj = $(event.target).closest("tr");
    var tempApproverEmployeeNumber = $(trObj).find(".empControl").attr('data-number');
    var inputAutoCompleteString = $(trObj).find(".empControl").val().trim();
    var that = $(trObj).find(".empControl")
    if (!inputAutoCompleteString.startsWith(tempApproverEmployeeNumber)) {
		$(trObj).find(".empControl").attr('data-number',inputAutoCompleteString); // validate the approver emp number entered on the server side
        
        validateNumber(inputAutoCompleteString,that)
    }else{
        if(inputAutoCompleteString == tempApproverEmployeeNumber){
            validateNumber(inputAutoCompleteString,that)
        }else{
            // validateNumber(tempApproverEmployeeNumber,that)
            // that.parents('.form-group').removeClass('has-error').find('.help-block.invalid').hide().removeClass("shown")
            // $("#saveSet").removeAttr('disabled')
        }
        
    }
    setTimeout(function(){
        $(".setApprovers-list .has-error .help-block").hide()
    },1500)
}
function validateNumber(num,obj){
    $.ajax({
        type:'POST',
        url:urlMain + '/leaveRequestTemporaryApprovers/isEmpNumberCorrect',
        data:{number:num},
        success : function (res) {
            if(res){
                obj.parents('.form-group').removeClass('has-error').find('.help-block.invalid').hide().removeClass("shown")
                $("#saveSet").removeAttr('disabled')
            }else{
                obj.parents('.form-group').addClass('has-error').find('.help-block.invalid').show().addClass("shown")
                $("#saveSet").attr('disabled','disabled')
            }
        },
        error:function(res){
                console.log(res);
        }
    })
}

function deleteRowQuery(e) {
	var result = confirm(areUDeleteRow + "\n\n" + pressContinue);	
		if(result == false){ 
			e.preventDefault();
		}
		return result;
}