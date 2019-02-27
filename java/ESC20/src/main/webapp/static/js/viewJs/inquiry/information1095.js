function submitYear(){
    $("#selectCalendar")[0].submit()
}
$(function(){
    let consentVal = $("#elecConsnt1095").val()
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
    $("#saveConsent").on('click', function(event) {
        let year = $("#consentYear").val()
        let consentOption = $("#consentModal").val()
        
        if(year && year!='' && consentOption && consentOption!=''){
            $("#noChooseError").hide()
            $("#update1095Consent")[0].submit()
        }else{
            if(!consentOption || consentOption==''){
                $("#noChooseError").show()
            }
        }
    })
})
function toggleOptions(value){
    $("#consentModal").val(value)            
}