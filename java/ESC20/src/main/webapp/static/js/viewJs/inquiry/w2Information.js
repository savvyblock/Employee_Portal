function submitCalendarForm(){
    $("#selectCalendar")[0].submit()
}
$(function(){
    let consentVal = $("#elecConsntW2Flag").val()
    if (consentVal == 'Y') {
        $("#consent").prop('checked',true);
    } else if (consentVal == 'N') {
        $("#notConsent").prop('checked',true);
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
            toggleOptions('Y')
        }
    })
    $("#notConsent").on('click', function(event) {
        if($(this).is(':checked')){
            toggleOptions('N')
        }
    })
    if(consentVal==''&& $('#enableElecConsntW2').val() == 'true'){
        $("#updateMsg").addClass("hidden");
        if ($("#elecConsntMsgW2").val() != null) {
            if (consentVal == 'Y') {
                $('#consent').iCheck('check');
            } else if (consentVal == 'N') {
                $('#notConsent').iCheck('check');
            } 
            $('#electronicConsent').modal('show')
        }
    }
    $("#saveConsent").on('click', function(event) {
        let year = $("#consentYear").val()
        let consentOption = $("#elecConsntW2Flag").val()
        
        if(year && year!='' && consentOption && consentOption!=''){
            $("#noChooseError").hide()
            $("#consentForm")[0].submit()
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