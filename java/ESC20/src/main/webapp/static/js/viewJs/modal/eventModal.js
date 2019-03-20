console.log(leaveTypesAbsrsnsMap)
$(function() {
    formValidator()
    let nowTemp = new Date()
    let now = new Date(
        nowTemp.getFullYear(),
        nowTemp.getMonth(),
        nowTemp.getDate(),
        0,
        0,
        0,
        0
    )
    let haveEndDate = false
    var checkin = $('#startDate')
        .fdatepicker({
            // startDate: now,
            format: 'mm/dd/yyyy',
            language:initialLocaleCode,
            onRender: function(date) {
                // if(checkout&&haveEndDate){
                //     return date.valueOf() > checkout.date.valueOf() ? 'disabled' : '';
                // }
            }
        })
        .on('changeDate', function(ev) {
            let endDate = $('#endDate').val()
            let startDate = $('#startDate').val()
            if (
                ev.date &&
                (ev.date.valueOf() >= checkout.date.valueOf() || !endDate)
            ) {
                // let newDate = new Date(ev.date)
                // newDate.setDate(newDate.getDate())
                startDate = new Date(startDate)
                startDate.setDate(startDate.getDate())
                checkout.update(startDate)
                console.log(startDate)
                $('#endDate').change()
            }
        })
        .data('datepicker')
    var checkout = $('#endDate')
        .fdatepicker({
            // startDate: now,
            format: 'mm/dd/yyyy',
            language:initialLocaleCode,
            onRender: function(date) {
                return date.valueOf() < checkin.date.valueOf()
                    ? 'disabled'
                    : ''
            }
        })
        .on('changeDate', function(ev) {})
        .data('datepicker')
        setGlobal()
})
function changeLeaveType(){
    let leaveType = $("#modalLeaveType").val()
    console.log(leaveType)
    let reason = leaveTypesAbsrsnsMap.filter(function(item){
        return item.leaveType == leaveType
    })
    $("#absenceReason").html('')
    reason.forEach(function(item){
        $("#absenceReason").append("<option value='"+item.absRsn +"'>" + item.absRsnDescrption +"</option>")
    })

}

function changeFormatTime(value) {
    let array = value.split(/[,: ]/)
    if (array[2] == 'PM') {
        let h
        if (array[0] == 12) {
            h = array[0]
        } else {
            h = parseInt(array[0]) + 12
        }
        return h + ':' + array[1]
    } else {
        let h
        if (array[0] == 12) {
            h = h = parseInt(array[0]) - 12
            return h + ':' + array[1]
        }
        return array[0] + ':' + array[1]
    }
}

function deleteRequest() {
    var id = $('#leaveId').val()
    $("#deleteId").val(id);
    $("#deleteModal").modal("show")
}
function closeRequestForm() {
    $('#requestForm')
        .data('bootstrapValidator')
        .destroy()
    $('#requestForm').data('bootstrapValidator', null)
    formValidator()
}
function formValidator() {
    $('#requestForm').bootstrapValidator({
        live: 'enable',
        submitButtons: '.save',
        feedbackIcons: {
            valid: 'fa fa-check ',
            // invalid: 'fa fa-times',
            validating: 'fa fa-refresh'
        },
        fields: {
            LeaveStartDate: {
                trigger: 'change',
                validators: {
                    notEmpty: {
                        message: 'validator.startDateCannotBeEmpty'
                    }
                }
            },
            LeaveEndDate: {
                trigger: 'change',
                validators: {
                    notEmpty: {
                        message: 'validator.endDateCannotBeEmpty'
                    }
                }
            },
            startHour: {
                trigger: 'change',
                validators: {
                    notEmpty: {
                        message: 'validator.startTimeCannotBeEmpty'
                    }
                }
            },
            endHour: {
                trigger: 'change',
                validators: {
                    notEmpty: {
                        message: 'validator.endTimeCannotBeEmpty'
                    }
                }
            },
            Remarks: {
                validators: {
                     notEmpty: {
                         message: 'validator.remarksCannotBeEmpty'
                     }
                }
            }
        }
    })
    // setGlobal()
}
$("#startDate").change(function(){
    let fromValue = $("#startDate").val()
    let toValue = $("#endDate").val()
    let leaveFrom = changeDateYMD(fromValue)
    let leaveTo = changeDateYMD(toValue)
    if(fromValue && toValue){
        if( leaveFrom<=leaveTo){
            $('.dateValidator01').hide()
            calcDays()
        }else{
            $('.dateValidator01').show()
        }
    }
    
});
$('#startDate').keyup(function() {
    let fromValue = $("#startDate").val()
    let toValue = $("#endDate").val()
    let leaveFrom = changeDateYMD(fromValue)
    let leaveTo = changeDateYMD(toValue)
    if(fromValue && toValue){
        if( leaveFrom<=leaveTo){
            $('.dateValidator01').hide()
            
        }else{
            $('.dateValidator01').show()
        }
        calcDays()
    }
});
 $("#endDate").change(function(){
    let fromValue = $("#startDate").val()
    let toValue = $("#endDate").val()
    let leaveFrom = changeDateYMD(fromValue)
    let leaveTo = changeDateYMD(toValue)
    if(fromValue && toValue){
            if( leaveFrom<=leaveTo){
                $('.dateValidator01').hide()
            }else{
                $('.dateValidator01').show()
            }
            calcDays()
        }
        
    });
    $('#endDate').keyup(function() {
        let fromValue = $("#startDate").val()
    let toValue = $("#endDate").val()
    let leaveFrom = changeDateYMD(fromValue)
    let leaveTo = changeDateYMD(toValue)
    if(fromValue && toValue){
            if( leaveFrom<=leaveTo){
                $('.dateValidator01').hide()
                
            }else{
                $('.dateValidator01').show()
            }
            calcDays()
        }
    });

    $("#leaveHoursDaily").change(function(){
        let val = $(this).val()
        $(this).val(Number(val).toFixed(3))

        calcDays()
    });

    // $(".timeControl").change(function(){
    //     let val = $(this).val()
    //     if(Number(val) < 10){
    //         $(this).val("0"+val)
    //     }
    // });
    var timeError = false
    function calcTime(){
        $('#requestForm').bootstrapValidator('disableSubmitButtons', false);  
        let startH = $("#startHour").val()
        let startM = $("#startMinute").val()
        if(startM === ''){
            $("#startMinute").val('00');
            startM='00';
        }
        let startTo = $("#startAmOrPm").val()
        let startTime = startH&&startM&&startTo?changeFormatTime(startH+":"+startM+" "+startTo):null
        let endH = $("#endHour").val()
        let endM = $("#endMinute").val()
        if(endM === ''){
            $("#endMinute").val('00');
            startM='00';
        }
        let endTo = $("#endAmOrPm").val()
        let endTime = endH&&endM&&endTo?changeFormatTime(endH+":"+endM+" "+endTo):null
        let start = startTime?new Date("2000/01/01 " + startTime):null;
        let end = endTime?new Date("2000/01/01 " + endTime):null;
        let time = end&&start?(end.getTime()-start.getTime()):0
        let hours = time/(3600*1000)
        if(hours>0){
            timeError = false
            hours = Number(hours).toFixed(3)
        }else{
            timeError = true
            hours = Number(0) .toFixed(3)
        }
        $('#leaveHoursDaily').val(hours)
        calcDays(hours)
        calValueTime()
    }
    function calcDays(duration){
        let startDate = $('#startDate').val()
        let endDate = $('#endDate').val()
        let leaveHoursDaily = $('#leaveHoursDaily').val()
        var day1 = new Date(startDate);
        var day2 = new Date(endDate);
        let dayDate = ((day2 - day1) / (1000 * 60 * 60 * 24)) + 1;
        dayDate = dayDate?dayDate:0;
        let startH = $("#startHour").val()
        let startM = $("#startMinute").val()
        let endH = $("#endHour").val()
        let endM = $("#endMinute").val()

        let hour = Number(leaveHoursDaily) - 8
        let days
        if(hour > -4){
            if(hour <= 0){
                days = dayDate
            }else{
                let a = parseInt(leaveHoursDaily/8)
                let b = leaveHoursDaily%8
                if(b>4){
                    days = dayDate * (a + 1)
                }else{
                    if(b == 0){
                        days = dayDate * a
                    }else{
                        days = dayDate * a +  dayDate * 0.5
                    }
                }
            }
            
        }else{
            if(hour <= -8){
                days = 0
            }else{
                days = 0.5 * dayDate
            }
            
        }
        console.log(dayDate)
        if(dayDate>=0){
            $("#requestModal .save").removeAttr("disabled")
        }else{
            $("#requestModal .save").attr("disabled","disabled")
        }
        if(days>0){
            $("#totalRequested").val(Number(days).toFixed(3));
        }else{
            $("#totalRequested").val(Number(0).toFixed(3));
        }
    }
    
    function calValueTime(){
        let start = $("#startHour").val() + ":" + $("#startMinute").val() + " " + $("#startAmOrPm").val()
        let end = $("#endHour").val() + ":" + $("#endMinute").val() + " " + $("#endAmOrPm").val()
        console.log(start)
        console.log(end)
        $("#startTimeValue").val(start)
        $("#endTimeValue").val(end)
    }
    
    function isHourKey(keyPressEvent) {
        var charCode = (keyPressEvent.which) ? keyPressEvent.which : keyPressEvent.keyCode;
        if (charCode > 31 && (charCode < 48 || charCode > 57)) {  // the character entered must be a number
            return false;
        } else if (charCode <= 31) {
            return true;
        }
        
        
        var value = $(keyPressEvent.target).val().trim();
        var valueLength = value.length;
        var selStart = keyPressEvent.target.selectionStart;
        var selEnd = keyPressEvent.target.selectionEnd;
        var selLength = keyPressEvent.target.selectionEnd - keyPressEvent.target.selectionStart;
        if (value.length>0 && selLength<value.length) {
            if (selStart==0) {
                if (charCode!=48) { // if the character entered is not a zero
                    if (charCode==49) {  // if the character entered is the number one
                        var secondCharCode;
                        if (value.length==1) {
                            secondCharCode = value.charCodeAt(0);
                        } else {
                            secondCharCode = value.charCodeAt(1);
                        }
                        
                        if (secondCharCode!=48 && secondCharCode!=49 && secondCharCode!=50) {
                            return false;
                        }
                    } else {					
                        return false;
                    }
                }
            } else if (selStart==1) {
                var firstCharCode = value.charCodeAt(0);
                if (firstCharCode==48) { // if the first character is a zero
                    if (charCode==48) {  // then do not accept a second zero
                        return false;
                    }
                } else if (firstCharCode==49) {  // if the first character is a one
                    if (charCode!=48 && charCode!=49 && charCode!=50) {  // then the second char can only be 0, 1, or 2
                        return false;
                    }
                } else {  // the first character is a number between 2 and 9 ... no character is valid as the second char
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    } 
    
    function isMinuteKey(keyPressEvent) {
        // in theory, a keyPress event should only get fired for printable characters although this is not always the case.
        // firefox, for example, will generate a keyPress event for the tab key (charCode=9).
        // return true for non-printable characters less than 31 which would include the tab key
        var charCode = (keyPressEvent.which) ? keyPressEvent.which : keyPressEvent.keyCode;
        if (charCode > 31 && (charCode < 48 || charCode > 57)) {  // the character entered must be a number
            return false;
        } else if (charCode <= 31) {
            return true;
        }

        var value = $(keyPressEvent.target).val().trim();
        var valueLength = value.length;
        var selStart = keyPressEvent.target.selectionStart;
        var selEnd = keyPressEvent.target.selectionEnd;
        var selLength = keyPressEvent.target.selectionEnd - keyPressEvent.target.selectionStart;
        if (value.length>0 && selLength<value.length) {
            if (selStart==0) {
                if (charCode>53) { // the first character may not be greater than five
                    return false;
                }
            } else if (selStart==1) {
                var firstCharCode = value.charCodeAt(0);
                if (firstCharCode>53) {  // the first character is a number greater than 5 ... no character is valid as the second char
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }
     $('.save').on('click', function() {
        var bootstrapValidator = $('#requestForm').data('bootstrapValidator')
        bootstrapValidator.validate()
        if (bootstrapValidator.isValid()) {
            console.log('success')
            let startDate = $('#startDate').val()
            let endDate = $('#endDate').val()
            let start = new Date(startDate)
            let end = new Date(endDate)
            let dateTotal = $("#totalRequested").val()
            console.log(start)
            console.log(end)
            // if (start.valueOf() > end.valueOf()) {
            if (timeError) {
                $('.dateValidator').show()
                return false
            } else {
                $('.dateValidator').hide()
                console.log(dateTotal)
                console.log(parseFloat(dateTotal))
                if(parseFloat(dateTotal)>0){
                    $(".dateValidator01").hide()
                    $('#requestForm')[0].submit()
                }else{
                    $(".dateValidator01").show()
                }
                
            }
        } else return
    })
    function changeDateYMD(date){
		let dateArry = date.split("/")
		let DateFormat = new Date(dateArry[2]+"-"+dateArry[0]+"-"+dateArry[1])
		return DateFormat
	}