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
    if(consentVal==''){
        $("#updateMsg").addClass("hidden");
        if(consentCancel != 'true'){
            $('#electronicConsent').modal('show')
        }
    }
    $("#saveConsent").on('click', function(event) {
        var year = $("#consentYear").val()
        var consentOption = $("#consentModal").val()
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
var pdf

function downloadPDF() {
    var title = $(document).attr("title")
    $('.exportPDFBox').show()
    $('.exportPDFBox').addClass("printStatus")
    $('.exportPDFBox').append($('.needToClone').clone())
    var size = $(".exportPDFBox .pdfPage").length;
    console.log(size)
    // return false

    pdf = new jsPDF('', 'pt', 'a4')
    $(".exportPDFBox .pdfPage").each(function(index){
        var that = $(this)[0]
        html2canvas(that, { scale: 6 ,background: "#fff",onrendered: function (canvas) {
            var contentWidth = canvas.width
            var contentHeight = canvas.height
            var pageHeight = (contentWidth / 592.28) * 841.89
            var leftHeight = contentHeight
            //page offset
            var position = 0
            //a4 paper size [595.28,841.89], width of canvas in pdf
            // var imgWidth = 595.28
            var imgWidth = 541
            var imgHeight = (imgWidth / contentWidth) * contentHeight
    
            var pageData = canvas.toDataURL('image/jpeg', 1.0)
            
    
            pdf.internal.scaleFactor = 2
            pdf.setFontSize(8);
            //There are two heights to distinguish, one is the actual height of the HTML page, and the height of the page that generates the PDF (841.89).
            //No pagination is required when the content does not exceed the range shown on a PDF page
            if (leftHeight < pageHeight) {
                pdf.text( title,300 ,10,'center');
                pdf.addImage(pageData, 'JPEG', 27, 40, imgWidth, imgHeight)
            } else {
                while (leftHeight > 0) {
                    pdf.text( title,300 ,10,'center');
                    pdf.addImage(
                        pageData,
                        'JPEG',
                        27,
                        position + 80,
                        imgWidth,
                        imgHeight
                    )
                    leftHeight -= pageHeight
                    position -= 841.89
                    //Avoid adding blank pages
                    if (leftHeight > 0) {
                        pdf.addPage()
                    }
                }
            }
            if(index<size-1){
                pdf.addPage()
            }
            if(index==size-1){

                save()
            }
        }})
    })
    
}
function save(){
    var name = (new Date()).valueOf()
    var pdfDom = '.exportPDFBox .needToClone'
    var fileName = '1095'
    pdf.save(fileName + '-'+name+'.pdf')

    $('.exportPDFBox').hide()
    $('.exportPDFBox').removeClass("printStatus")
    $(pdfDom).remove()
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
    $.ajax({
        url:urlMain + '/information1095/cancel1095Consent',
        type:'POST',
        success:function(res){
            console.log(res)
        },
        error:function(err){
            console.log(err)
        }
    })
}