function submitCalendarForm(){
    $("#selectCalendar")[0].submit()
}
$(function(){
    var consentVal = $("#elecConsntW2Flag").val()
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
        var year = $("#consentYear").val()
        var consentOption = $("#elecConsntW2Flag").val()
        
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

var pdf

function downloadPDF() {
    var title = $(document).attr("title")
    $('.exportPDFBox').show()
    $('.exportPDFBox').addClass("printStatus")
    $('.exportPDFBox').append($('.needToClone').clone())
    var size = $(".exportPDFBox .pdfPage").length;

    pdf = new jsPDF('', 'pt', 'a4')
    $(".exportPDFBox .pdfPage").each(function(index){
        var that = $(this)[0]
        console.log(index)
        console.log(that)
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
                console.log(title)
                pdf.text( title,300 ,10,'center');
                pdf.addImage(pageData, 'JPEG', 27, 40, imgWidth, imgHeight)
            } else {
                while (leftHeight > 0) {
                    console.log(title)
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
    var fileName = 'W2'
    pdf.save(fileName + '-'+name+'.pdf')
    // $('.exportPDFBox').hide()
    // $('.exportPDFBox').removeClass("printStatus")
    // $(pdfDom).remove()
}