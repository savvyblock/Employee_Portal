var language = (
    navigator.language || navigator.browserLanguage
).toLowerCase();
var pathPrefix = "/"+ctx+"/js/lang"; //language json location
console.log(pathPrefix);
var name = 'somoveLanguage';
$(function() {
    // $(".dateInput").inputmask("mm/dd/yyyy", { "placeholder": "mm/dd/yyyy" });
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
    
    $('.amount_2').change(function() {
        let val = $(this).val();
        $(this).val(Number(val).toFixed(2));
    });
    $('.amount_3').change(function() {
        let val = $(this).val();
        $(this).val(Number(val).toFixed(3));
    });

    console.log(language);
    if (language.indexOf('en') > -1) {
    	language = 'en'
        initLocalize(language)
    }
    if (language.indexOf('zh') > -1) {
        language = 'en'
        initLocalize(language)
    }
    setGlobal();
})

function chgLang() {
    var value = $('#globalSet')
        .children('option:selected')
        .val();
    SetCookie(name, value);
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
    window.print()
}