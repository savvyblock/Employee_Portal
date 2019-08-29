
var calUnit
var mealBreakHours,standardHoursDaily,leaveHoursRequestedEntry,conversionMinuteHour
$(function() {
    showTimeUnit()
    mealBreakHours = $("#mealBreakHours").val()
    standardHoursDaily = $("#standardHoursDaily").val()
    leaveHoursRequestedEntry = $("#requireLeaveHoursRequestedEntry").val()
    standardHoursDaily = Number(standardHoursDaily)
    mealBreakHours = Number(mealBreakHours)
    console.log("mealBreakHours>>>>:" + mealBreakHours)
    console.log("standardHoursDaily>>>>:" + standardHoursDaily)
    console.log("leaveHoursRequestedEntry>>>>:" + leaveHoursRequestedEntry)
    if(isAddValue == 'true'){
        $("#requestModal").modal('show')
        $(".edit-title").hide()
        $(".secondSubmit").hide();
        $("#deleteLeave").hide();	
    }
    formValidator()
    var nowTemp = new Date()
    var now = new Date(
        nowTemp.getFullYear(),
        nowTemp.getMonth(),
        nowTemp.getDate(),
        0,
        0,
        0,
        0
    )
    var haveEndDate = false
    var checkin = $('#startDate')
        .fdatepicker({
            // startDate: now,
            format: 'mm-dd-yyyy',
            language:initialLocaleCode,
        })
        .on('changeDate', function(ev) {
            var endDate = $('#endDateInput').val()
            var startDate = $('#startDateInput').val()
            if (
                ev.date &&
                !endDate
                // (ev.date.valueOf() >= checkout.date.valueOf() || !endDate)
            ) {
                // var newDate = new Date(ev.date)
                // newDate.setDate(newDate.getDate())
                startDate = new Date(startDate)
                startDate.setDate(startDate.getDate())
                checkout.update(startDate)
                console.log(startDate)
                $('#endDateInput').change()
                $('#startDateInput').change()
            }
        })
        .data('datepicker')
    var checkout = $('#endDate')
        .fdatepicker({
            // startDate: now,
            format: 'mm-dd-yyyy',
            language:initialLocaleCode,
            onRender: function(date) {
                return date.valueOf() < checkin.date.valueOf()
                    ? 'disabled'
                    : ''
            }
        })
        .on('changeDate', function(ev) {})
        .data('datepicker')

        $("input").bind('keypress', function(e)  {
            var eCode = e.keyCode
                ? e.keyCode
                : e.which
                ? e.which
                : e.charCode
            if (eCode == 13) {
                $(this).click()
                event.preventDefault();
            }
        })
})
function changeLeaveType(){
    var leaveType = $("#modalLeaveType").val()
    $("#absenceReason").html('')

    if(leaveType != ''){
        var reason = leaveTypesAbsrsnsMap.filter(function(item){
            return item.leaveType == leaveType
        })
        reason.forEach(function(item){
            $("#absenceReason").append("<option value='"+item.absRsn +"'>" + item.absRsnDescrption +"</option>")
        })
    }else{
        var reasonJson = new Array()
        leaveTypesAbsrsnsMap.forEach(function(item){
            var count = 0
            if(item.absRsnDescrption){
                for(var i=0,len = reasonJson.length;i<len;i++){
                    if(item.absRsn == reasonJson[i].absRsn){
                        count++
                    }
                }
                if(count == 0){
                    reasonJson.push(item)
                }
                
            }
        })
        reasonJson.forEach(function(item){
            $("#absenceReason").append("<option value='"+item.absRsn +"'>" + item.absRsnDescrption +"</option>")
        })
        calUnit = false
    }
    
    showTimeUnit()
}

function changeFormatTime(value) {
    var array = value.split(/[,: ]/)
    if (array[2] == 'PM') {
        var h
        if (array[0] == 12) {
            h = array[0]
        } else {
            h = parseInt(array[0]) + 12
        }
        return h + ':' + array[1]
    } else {
        var h
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
        trigger:null,
        submitButtons: '.save',
        feedbackIcons: {
            valid: 'fa fa-check ',
            // invalid: 'fa fa-times',
            validating: 'fa fa-refresh'
        },
        fields: {
            leaveType:{
                trigger:'change',
                notEmpty: {
                    message: requiredFieldValidator
                },
            },
            absenseReason:{
                notEmpty: {
                    message: requiredFieldValidator
                }
            },
            LeaveStartDate: {
                trigger:'change',
                validators: {
                    notEmpty: {
                        message: startDateCannotBeEmptyValidator
                    }
                }
            },
            LeaveEndDate: {
                trigger:'change',
                validators: {
                    notEmpty: {
                        message: endDateCannotBeEmptyValidator
                    }
                }
            },
            startHour: {
                validators: {
                    notEmpty: {
                        message: startTimeCannotBeEmptyValidator
                    }
                }
            },
            endHour: {
                validators: {
                    notEmpty: {
                        message: endTimeCannotBeEmptyValidator
                    }
                }
            },
            Remarks: {
                validators: {
                     notEmpty: {
                         message: remarksCannotBeEmptyValidator
                     }
                }
            }
        }
    })
    // setGlobal()
}
$("#startDateInput").blur(function(){
    var fromValue = convertRightFormat($("#startDateInput").val())
    var toValue = convertRightFormat($("#endDateInput").val())
    var leaveFrom = fromValue?changeDateYMD(fromValue):null
    var leaveTo = toValue?changeDateYMD(toValue):null
    if(fromValue && toValue){
        if( leaveFrom<=leaveTo){
            $('.dateValidator01').hide()
            calcDays()
        }else{
            $('.dateValidator01').show()
            setTimeout(function(){
                $("#endDateInput").val('')
            },500)
        }
    }
    
});

 $("#endDateInput").blur(function(){
    var fromValue = convertRightFormat($("#startDateInput").val())
    var toValue = convertRightFormat($("#endDateInput").val())
    var leaveFrom = changeDateYMD(fromValue)
    var leaveTo = changeDateYMD(toValue)
    if(fromValue && toValue){
            if( leaveFrom<=leaveTo){
                $('.dateValidator01').hide()
            }else{
                $('.dateValidator01').show()
                setTimeout(function(){
                    $("#startDateInput").val('')
                },500)
            }
            calcDays()
        }
        
    });


$("#leaveHoursDaily").change(function(){
    var val = $(this).val()
    $(this).val(Number(val).toFixed(3))

    calcDaysOrHours()
});

    var timeError = false
    function calcTime(){
        $('#requestForm').bootstrapValidator('disableSubmitButtons', false);  
        var startH = $("#startHour").val()
        var startM = $("#startMinute").val()
        if(startM === ''){
            $("#startMinute").val('00');
            startM='00';
        }
        var startTo = $("#startAmOrPm").val()
        var startTime = startH&&startM&&startTo?changeFormatTime(startH+":"+startM+" "+startTo):null
        var endH = $("#endHour").val()
        var endM = $("#endMinute").val()
        if(endM === ''){
            $("#endMinute").val('00');
            startM='00';
        }
        var endTo = $("#endAmOrPm").val()
        var endTime = endH&&endM&&endTo?changeFormatTime(endH+":"+endM+" "+endTo):null
        var start = startTime?new Date("2000/01/01 " + startTime):null;
        var end = endTime?new Date("2000/01/01 " + endTime):null;
        var time = end&&start?(end.getTime()-start.getTime()):0
        var hours = time/(3600*1000)
        if(hours>0){
            timeError = false
            // if()
            hours = Number(hours).toFixed(3)
        }else{
            timeError = true
            hours = Number(0) .toFixed(3)
        }
        if(leaveHoursRequestedEntry == 'false'){
            hours = hours > 5 ? hours - mealBreakHours:hours
            $('#leaveHoursDaily').val(Number(hours).toFixed(3))
        }
        
        calcDays()
        calValueTime()
    }
    function calcDays(){
        calcDaysOrHours()
    }
    
    function calValueTime(){
        var start = $("#startHour").val() + ":" + $("#startMinute").val() + " " + $("#startAmOrPm").val()
        var end = $("#endHour").val() + ":" + $("#endMinute").val() + " " + $("#endAmOrPm").val()
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

    function changeDateYMD(date){
        if(!date){
            return
        }
		var dateArry = date.split("/")
		var DateFormat = new Date(dateArry[2]+"-"+dateArry[0]+"-"+dateArry[1])
		return DateFormat
    }
    
    function convertRightFormat(str){
        var reg = /^[0-9]{1,2}[^\d]{1}[0-9]{1,2}[^\d]{1}[0-9]{4}$/;
        var regStr = /^[0-9]{1,2}[0-9]{1,2}[0-9]{4}$/;
        if(reg.test(str)||regStr.test(str)){
            if(reg.test(str)){
                return str
            }
            if(regStr.test(str)){
                var pattern = /(\d{2})(\d{2})(\d{4})/;
                var formatedDate = str.replace(pattern, '$1/$2/$3');
                return formatedDate
            }
        }else{
            return false
        }
    }

    function saveRequest(isAdd){
        var leaveHoursDaily = $("#leaveHoursDaily").val()
        if(Number(leaveHoursDaily) == 0){
            $(".leaveHoursDailyNotZero").show()
            $(".leaveHoursDailyWrap").addClass('has-error')
            return false
        }
        $(".leaveHoursDailyNotZero").hide()
        $(".leaveHoursDailyWrap").removeClass('has-error')
        if(isAdd){
            $("#isAdd").val(isAdd)
        }
        $(event.currentTarget).parents(".modal").focus()
        var bootstrapValidator = $('#requestForm').data('bootstrapValidator')
        bootstrapValidator.validate()
        if (bootstrapValidator.isValid()) {
            console.log('success')
            var startDate = $('#startDateInput').val()
            var endDate = $('#endDateInput').val()
            var start = new Date(startDate)
            var end = new Date(endDate)
            var dateTotal = $("#totalRequested").val()
            var typeCode = $("#modalLeaveType").val()
            var balanceAvailable = $("#available"+typeCode+"").text()
            // if (start.valueOf() > end.valueOf()) {
                console.log(startDate)
                console.log(endDate)
            if (timeError) {
                $('.dateValidator').show()
                $("#endHour").focus()
                return false
            } else {
                $('.dateValidator').hide()
                if(parseFloat(dateTotal)>0){
                    $(".dateValidator01").hide()
                    // console.log(parseFloat(dateTotal))
                    // console.log(parseFloat(balanceAvailable))

                    if(parseFloat(dateTotal)<=parseFloat(balanceAvailable)){
                        $(".availableError").hide()
                        // return false
                        $('#requestForm')[0].submit()
                    }else{
                        $(".availableError").show()
                    }
                }else{
                    $(".dateValidator01").show()
                }
                
            }
        } else return
    }
    function showTimeUnit(){
        var leaveTypeCurrent = $("#modalLeaveType option:selected").data('label')
        if(!leaveTypeCurrent){
            $(".timeUnit").hide()
        }else{
            var ConversionRecsUrl = ''
            if(leaveTypeCurrent.toLowerCase() == 'h'){
                $(".timeUnit.hours").show()
                $(".timeUnit.days").hide()
                calUnit = 'h'
                ConversionRecsUrl = '/getMinutesToHoursConversionRecs'
            }else{
                $(".timeUnit.days").show()
                $(".timeUnit.hours").hide()
                calUnit = 'd'
                ConversionRecsUrl = '/getHoursToDaysConversionRecs'
            }
            var leaveType = $("#modalLeaveType").val()
            var freq = $("#freq").val()
            return false
            $.ajax({
                type:'POST',
                url:urlMain + '/leaveRequest' + ConversionRecsUrl,
                data:{
                    payFrequency:freq.trim(),
                    leaveType:leaveType.trim()
                },
                success : function (res) {
                    console.log(res)
                    conversionMinuteHour = res
                    calcDaysOrHours()
                },
                error:function(res){
                     console.log(res);
                }
            })
        }
    }

    function calcDaysOrHours(){
        var startDate = $('#startDateInput').val()
        var endDate = $('#endDateInput').val()
        var leaveHoursDaily = Number($('#leaveHoursDaily').val().trim())
        var day1 = new Date(startDate);
        var day2 = new Date(endDate);
        var dayDate = ((day2 - day1) / (1000 * 60 * 60 * 24)) + 1;
        var totalDays = 0
        console.log(dayDate)
        dayDate = dayDate?dayDate:0;
        if(leaveHoursDaily <= 0){
            $("#totalRequested").val(Number(0).toFixed(3));
        }

        if(leaveHoursDaily > 0){
            console.log('calc...')
            if(calUnit.toLowerCase() == 'd'){
                var intDays = parseInt(leaveHoursDaily/standardHoursDaily)
                var intDaysRemainHours = parseFloat(leaveHoursDaily%standardHoursDaily)
                var floatDays = 0
                console.log(intDays)
                console.log(intDaysRemainHours)
                if(conversionMinuteHour && conversionMinuteHour.length>0){
                    for(var i = 0,len = conversionMinuteHour.length;i<len;i++){
                        if(intDaysRemainHours >0 && intDaysRemainHours <= conversionMinuteHour[i].toUnit){
                            floatDays = conversionMinuteHour[i].fractionalAmount
                            break;
                        }
                    }
                }else{
                    var cal01 = parseFloat(leaveHoursDaily/standardHoursDaily) //0.465
                    var cal02 = parseInt(leaveHoursDaily/standardHoursDaily)
                    if(cal01 - cal02 > 0){
                        floatDays = cal01 - cal02<=0.5?0.5:1
                    }
                }
                
                console.log('floatDays'+floatDays)
                totalDays = intDays + floatDays
                console.log('totalDays' + totalDays)
                $("#totalRequested").val(Number(totalDays).toFixed(3));
            }else if(calUnit.toLowerCase() == 'h'){
                if(leaveHoursRequestedEntry == 'false'){
                    var intHours = parseInt(leaveHoursDaily)
                    var intHoursRemainMinutes = (leaveHoursDaily - intHours) * 60
                    var floatHours = leaveHoursDaily - intHours
                    console.log(intHours)
                    console.log(intHoursRemainMinutes)
                    if(conversionMinuteHour && conversionMinuteHour.length>0){
                        for(var i = 0,len = conversionMinuteHour.length;i<len;i++){
                            if(intHoursRemainMinutes >0 && intHoursRemainMinutes <= conversionMinuteHour[i].toUnit){
                                floatHours = conversionMinuteHour[i].fractionalAmount
                                break;
                            }
                        }
                    }
                    $('#leaveHoursDaily').val(Number(floatHours + intHours).toFixed(3))
                    var totalHours = (floatHours + intHours) * dayDate
                    $("#totalRequested").val(Number(totalHours).toFixed(3));
                }else{
                    var totalHours = leaveHoursDaily * dayDate
                    $("#totalRequested").val(Number(totalHours).toFixed(3));
                }                
            }
        }
         
    }