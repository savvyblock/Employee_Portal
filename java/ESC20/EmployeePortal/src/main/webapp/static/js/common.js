var language = (
    navigator.language || navigator.browserLanguage
).toLowerCase();
if (language.indexOf('en') > -1) {
    language = 'en'
}
if (language.indexOf('zh') > -1) {
    language = 'en'
}
console.log(language);
var pathPrefix = "/"+ctx+"/js/lang"; //language json location
console.log(pathPrefix);
var name = 'somoveLanguage';
var initialLocaleCode;
$(function() {
    $("#skipNav").on("click",function(){
        console.log($(".content-wrapper"))
        $(".content-wrapper").focus()
        $(".content-body").focus()
        $(document).scrollTop(0)
    })
    $('.icheck').iCheck({
        checkboxClass: 'icheckbox_square-green',
        radioClass: 'iradio_square-green',
        increaseArea: '20%' // optional
    });
    $(".icheckbox_square-green").each(function(){
        $(this).attr("role","radio")
    })
    $(".iradio_square-green").each(function(){
        $(this).attr("role","checkbox")
    })
    $('.amount_2').change(function() {
        var val = $(this).val();
        $(this).val(Number(val).toFixed(2));
    });
    $('.amount_3').change(function() {
        var val = $(this).val();
        $(this).val(Number(val).toFixed(3));
    });
    $(".closeModal").click(function(){
        hideBody()
    })
    $("input.form-control").each(function(){   
        var inputVal = $(this).val()     
        if($.trim(inputVal).length==0){
            $(this).val('')
        }
    })
    console.log(languageSet)
    if(languageSet&&languageSet!=''){
        $("#globalSet").val(languageSet)
        initialLocaleCode = languageSet
    }else{
        $("#globalSet").val(language)
        initialLocaleCode = language
    }
    
})
function setGlobal(){
    if (getCookie(name)&&getCookie(name) != '') {
        $('#globalSet').val(getCookie(name))
        initialLocaleCode = getCookie(name)
        initLocalize(getCookie(name))
    }else{
        $('#globalSet').val(language)
        initialLocaleCode = language
        initLocalize(language)
    }
    // console.log(initialLocaleCode)
}

function chgLang() {
    var value = $('#globalSet').val();
    console.log(value)
    //SetCookie(name, value);
    $.ajax({
        type: 'post',
        url: urlMain + '/changeLanguage',
        cache: false,
        data: {csrfmiddlewaretoken: $("#csrfmiddlewaretoken").val(), language:value},
        dataType: 'json',
        success: function(data) {
        	// alert('data success');
        	location.reload(true);
        }
   });
}
function SetCookie(name, value) {
    var Days = 30; // cookie will stay 30 days
    var exp = new Date(); //new Date("December 31, 9998");
    exp.setTime(exp.getTime() + Days * 24 * 60 * 60 * 1000);
    document.cookie = name + '=' + escape(value) + ';expires=' + exp.toGMTString() + ";path="+"/"+ctx+"/;";
    console.log(document.cookie);
    location.reload();
}
function getCookie(name) {
    var arr = document.cookie.match(
        new RegExp('(^| )' + name + '=([^;]*)(;|$)')
    );
    if (arr != null) return unescape(arr[2]);
    return null;
}


function initLocalize(language){
    $('[data-localize]').localize('text', {
        pathPrefix: pathPrefix,
        language: language,
        timeout:50000
    })
}

function clearDate(e){
    $(e).parent().find(".form-control").val("")
}

function doPrint() {
    window.focus()
    window.print()
    // window.close()    
}

function hideBody(){
    setTimeout(function(){
        $(".modal").each(function(){
            if($(this).is(':visible')){
                $("body").addClass("modal-open")
            }
        })
    }, 200);
}

function convert2canvasDownload(shareContent,pdfDom,fileName,title){

    html2canvas(shareContent, { scale: 4 ,background: "#fff",onrendered:function(canvas) {
            var contentWidth = canvas.width
            var contentHeight = canvas.height
            var pageHeight = (contentWidth / 595.28) * 841.89
            var leftHeight = contentHeight
            //page offset
            var position = 0
            //a4 paper size [595.28,841.89], width of canvas in pdf
            var imgWidth = 595.28
            var imgHeight = (imgWidth / contentWidth) * contentHeight

            var pageData = canvas.toDataURL('image/jpeg', 1.0)
            var pdf = new jsPDF('p', 'pt', 'a4')

            pdf.internal.scaleFactor = 2
            //There are two heights to distinguish, one is the actual height of the HTML page, and the height of the page that generates the PDF (841.89).
            //No pagination is required when the content does not exceed the range shown on a PDF page
        console.log(title)
        pdf.setFontSize(8);
            if (leftHeight < pageHeight) {
                pdf.text( title,300 ,10,'center');
                pdf.addImage(pageData, 'JPEG', 0, 40, imgWidth, imgHeight)
            } else {
                while (leftHeight > 0) {
                    pdf.text( title,300 ,10,'center');
                    pdf.addImage(
                        pageData,
                        'JPEG',
                        0,
                        position + 40,
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
            var name = (new Date()).valueOf()
            pdf.save(fileName + '-'+name+'.pdf')
            $('.exportPDFBox').hide()
            $('.exportPDFBox').removeClass("printStatus")
            $(pdfDom).remove()
        }
    })
}