function submitCalendarForm(){
    $("#selectCalendar")[0].submit()
}
$(function(){
    var consentVal = $("#elecConsntW2Flag").val()
    if (consentVal == 'Y') {
        $("#consent").prop('checked',true);
        $("#consent").attr("aria-checked",true)
    } else if (consentVal == 'N') {
        $("#notConsent").prop('checked',true);
        $("#notConsent").attr("aria-checked",true)
    }
    $(".consentRadio") .on('keypress', function(e) {
        console.log(e)
        var eCode = e.keyCode ? e.keyCode : e.which ? e.which : e.charCode;
        if (eCode == 13){
            $(this).click()
        }
    })

    $("#consent").on('click', function(event) {
        if($(this).is(':checked')){
            $("#consent").attr("aria-checked",true)
            $("#notConsent").attr("aria-checked",false)
            toggleOptions('Y')
        }
    })
    $("#notConsent").on('click', function(event) {
        if($(this).is(':checked')){
            $("#notConsent").attr("aria-checked",true)
            $("#consent").attr("aria-checked",false)
            toggleOptions('N')
        }
    })
    if(consentVal==''&& $('#enableElecConsntW2').val() == 'true'){
        $("#updateMsg").addClass("hidden");
        var consentCancel = $("#elecConsntW2Cancel").val()
        if ($("#elecConsntMsgW2").val() != null) {
            if (consentVal == 'Y') {
                $('#consent').iCheck('check');
            } else if (consentVal == 'N') {
                $('#notConsent').iCheck('check');
            } 
            if(consentCancel != 'true'){
                $('#electronicConsent').modal('show')
            }
            
        }
    }
    $("#saveConsent").on('click', function(event) {
        var year = $("#consentYear").val()
        var consentOption = $("#elecConsntW2Flag").val()
        
        if(year && year!='' && consentOption && consentOption!=''){
            $("#noChooseError").hide()
            // $("#consentForm")[0].submit()
            var data = {
                csrfmiddlewaretoken: $("#csrfmiddlewaretokenConsent").val(),
                year:year,
                consent:consentOption
            }
            saveConsent(data)
        }else{
            if(!consentOption || consentOption==''){
                $("#noChooseError").show()
            }
        }
    })
})
function toggleOptions(value){
    $("#elecConsntW2Flag").val(value)
}

function load(){
	$("#printIframe")[0].contentWindow.focus();
	$("#printIframe")[0].contentWindow.print();
}

function submitCancelConsent(){
    $.ajax({
        url:urlMain + '/w2Information/cancelW2Consent',
        type:'POST',
        success:function(res){
            console.log(res)
        },
        error:function(err){
            console.log(err)
        }
    })
}

function saveConsent(data){
    $.ajax({
        url:urlMain + '/w2Information/updateW2Consent',
        type:'POST',
        data:data,
        success:function(res){
            console.log(res)
            if(res.isSuccess && res.isUpdate){
                $("#electronicConsent").modal("hide")
                $("#updateMsg").show()
            }
            
        },
        error:function(err){
            console.log(err)
        }
    })
}