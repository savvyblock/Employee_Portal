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
    //ALC-13 language setting
    if(languageSet&&languageSet!=''){
        $("#globalSet").val(languageSet)
        $(".langSelectInner .dropdown-menu li").each(function(){
            var titleA = $(this).find("a").attr("title")
            var valueA = $(this).find("a").text()
            if(titleA == languageSet){
                $('#globalSet2 span').text(valueA)
            }
        })
        initialLocaleCode = languageSet
    }else{
        $("#globalSet").val(language)
        $(".langSelectInner .dropdown-menu li").each(function(){
            var titleA = $(this).find("a").attr("title")
            var valueA = $(this).find("a").text()
            if(titleA == language){
                $('#globalSet2 span').text(valueA)
            }
        })
        initialLocaleCode = language
    }
    var dateCu = new Date();
    var cuYear = dateCu.getFullYear(); 
    var cuMonth = dateCu.getMonth() + 1; 
    var cuDay = dateCu.getDate();
    var cuHour = dateCu.getHours();
    var cuMin = dateCu.getMinutes();
    var cuSecond = dateCu.getSeconds();
    var time
    cuMin = cuMin > 9?cuMin:"0"+cuMin
    cuSecond = cuSecond > 9?cuSecond:"0"+cuSecond
    if(cuHour>12){
        cuHour = cuHour-12;
        time = cuHour+ ":" +cuMin + ":" + cuSecond +" PM"
    }else{
        if(cuHour==12){
            time = cuHour+ ":" +cuMin + ":" + cuSecond+" PM"
        }else{
            // cuHour = cuHour < 10 ? "0" + cuHour : cuHour;
            time = cuHour+ ":" +cuMin + ":" + cuSecond+" AM"
        }
    }
    $(".currentTime").html(cuMonth+'-'+cuDay+'-'+cuYear + '  ' +time)
    var helpJson = {
        "login":"",
        "createUser":"newuser",
        "forgetPassword":"forgotpassword",
        "updatePassword":"changepassword",
        "calendarYearToDate":"inquiry/calendaryeartodate",
        "currentPayInformation":"inquiry/currentpayinformation",
        "deductions":"inquiry/deductions",
        "earnings":"inquiry/earnings",
        "w2Information":"inquiry/w2information",
        "information1095":"inquiry/1095information",
        "leaveBalance":"leavebalances",
        "leaveRequest":"leaverequests",
        "approveLeaveRequest":"supervisor/approveleaverequests",
        "leaveOverview":"supervisor/leaveoverview",
        "supervisorCalendar":"supervisor/calendar",
        "leaveRequestTemporaryApprovers":"supervisor/settemporaryapprovers",
        "profile":"employeeportalselfservice"
    }
    
    let helpurl;
    if(helpLinkFromProperties.includes("/test/")){
        helpurl = helpLinkFromProperties.replace('/test','');
    }
    console.log("L  :" + value)
    var url = window.location.href
    var urlArry = url.split('?')
    var itemUrlArry = urlArry[0].split('/')
    var lastItem = itemUrlArry[itemUrlArry.length - 1]
    var lastTowItem = itemUrlArry[itemUrlArry.length - 2]
    var helpLinkParam = helpJson[lastItem] || helpJson[lastTowItem]
    var helpLink = helpurl + '/'+ (helpLinkParam || '')
    var value = $('#globalSet').val();

    if(value == "es"){
        helpLink += "?culture=es-ES"
    }
    $("a.helpLink").attr('href', helpLink )
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
//ALC-13 add language jquery
function showText(lang){
    var value = lang;
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
    print.portrait=true;
    window.print()
    // window.close()    
}

function doLandscapePrint() {
    window.focus()
    print.portrait=false;
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

function setConsentCookie(name, value) {
    var Days = 30; // cookie will stay 30 days
    var exp = new Date(); //new Date("December 31, 9998");
    exp.setTime(exp.getTime() + Days * 24 * 60 * 60 * 1000);
    document.cookie = name + '=' + escape(value) + ';expires=' + exp.toGMTString() + ";path="+"/"+ctx+"/;";
    console.log(document.cookie);
}
function getConsentCookie(name) {
    var arr = document.cookie.match(
        new RegExp('(^| )' + name + '=([^;]*)(;|$)')
    );
    if (arr != null) return unescape(arr[2]);
    return null;
}