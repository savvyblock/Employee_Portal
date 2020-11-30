function submitYear(){
    $("#selectCalendar")[0].submit()
}
$(function(){
    var consentVal = $("#elecConsnt1095").val()
    var consentCancel = $("#elecConsnt1095Cancel").val()
    $("#consentModal").val(consentVal)
    if (consentVal == 'Y') {
        $("#consent").prop('checked',true);
        $("#consent").attr("aria-checked",true)
    } else if (consentVal == 'N') {
        $("#notConsent").prop('checked',true);
        $("#notConsent").attr("aria-checked",true)
    }
    
    var url = window.location.href
    var urlArry = url.split('?')
    var itemUrlArry = urlArry[0].split('/')
    var lastItem = itemUrlArry[itemUrlArry.length - 1]
    var lastTowItem = itemUrlArry[itemUrlArry.length - 2]
    var consentClose = getConsentCookie('consentCancel1095')

    if(consentVal==''){
        $("#updateMsg").addClass("hidden");
        if(consentClose !='true'){
            $('#electronicConsent').modal('show')
        }else{
            if(lastItem != 'sortOrChangePageForTypeB' && lastItem != 'sortOrChangePageForTypeC'){
                $('#electronicConsent').modal('show')
            }
        }
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

    $("#b-1095").on('ifChecked', function(event){
        var pageNow = $("#pageNow").val();
        $("#selectPageNowB").val(pageNow)
        $("#changePageFormB")[0].submit()
    });

    $("#c-1095").on('ifChecked', function(event){
        var pageNow = $("#pageNow").val();
        $("#selectPageNowC").val(pageNow)
        $("#changePageFormC")[0].submit()
    })
    
    $("#saveConsent").on('click', function(event) {
        var year = $("#consentYear").val()
        var consentOption = $("#consentModal").val()
        if(year && year!='' && consentOption && consentOption!=''){
            $("#noChooseError").hide()
            // $("#update1095Consent")[0].submit()
            var data = {
                csrfmiddlewaretoken: $("#csrfmiddlewaretokenConsent").val(),
                year:year,
                consent:consentOption
            }
            saveConsent(data)
        }else{
            if(!consentOption || consentOption==''){
                $("#noChooseError").show()
            } else {
            	 var data = {
                     csrfmiddlewaretoken: $("#csrfmiddlewaretokenConsent").val(),
                     year:year,
                     consent:consentOption
                 }
                 saveConsent(data)
            }
        }
    })

})
function toggleOptions(value){
    $("#consentModal").val(value)            
}

function changeBC(){
    var pageNow = $("#pageNow").val();
    $("#selectPageNow").val(pageNow)
    $("#changePageForm")[0].submit()
}

function load(){
	$("#printIframe")[0].contentWindow.focus();
	$("#printIframe")[0].contentWindow.print();
}

function submitCancelConsent(){
    var name = 'consentCancel1095'
    var value = true
    setConsentCookie(name, value)
}

function saveConsent(data){
    $.ajax({
        url:urlMain + '/information1095/update1095Consent',
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

