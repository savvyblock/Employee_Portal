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
var pdf
function downloadPDF() {
    $('.exportPDFBox').show()
    $('.exportPDFBox').addClass("printStatus")
    $('.exportPDFBox').append($('.needToClone').clone())
    let size = $(".exportPDFBox .pdfPage").length;
    console.log(size)

    pdf = new jsPDF('', 'pt', 'a4')
    $(".exportPDFBox .pdfPage").each(function(index){
        let that = $(this)[0]
        html2canvas(that, { scale: 2 }).then(function(canvas) {
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
            
    
            pdf.internal.scaleFactor = 1.33
            //There are two heights to distinguish, one is the actual height of the HTML page, and the height of the page that generates the PDF (841.89).
            //No pagination is required when the content does not exceed the range shown on a PDF page
            if (leftHeight < pageHeight) {
                pdf.addImage(pageData, 'JPEG', 27, 40, imgWidth, imgHeight)
            } else {
                while (leftHeight > 0) {
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
            
        })
    })
    
}
function save(){
    let name = (new Date()).valueOf()
    let pdfDom = '.exportPDFBox .needToClone'
    let fileName = '1095'
    pdf.save(`${fileName}-${name}.pdf`)
    $('.exportPDFBox').hide()
    $('.exportPDFBox').removeClass("printStatus")
    $(pdfDom).remove()
}