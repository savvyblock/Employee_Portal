
// ALC-13
$(function(){
    stepValidator01()
    stepValidator02()

    $(".next-step1").click(function(){
    	
       var bootstrapValidator01 = $('#personalDetailForm').data('bootstrapValidator')
       bootstrapValidator01.validate()
       if (bootstrapValidator01.isValid()) {
            var empNumber = $("#employeeNumber").val()
            var ssn = $("#SSNumber").val()
            var birthDate = $("#birthDate").val()
            var zipCode = $("#zipCode").val()
            var dateMonth = splitDate(birthDate)['month']
            var dateDay = splitDate(birthDate)['day']
            var dateYear = splitDate(birthDate)['year']

            var userObj = {
                empNumber:empNumber,
                ssn:ssn,
                dateMonth:dateMonth,
                dateDay:dateDay,
                dateYear:dateYear,
                zipCode:zipCode
            }
            $.ajax({
                type: 'post',
                url: urlMain+'/resetPassword/recoverUserNameStep1',
                cache: false,
                data: userObj,
                dataType: 'json',
                success: function(result) {
                    console.log(result);
                    $("#noEmployeeError").hide()
                    if(result.code === 2){
                        $('#infoStep').removeClass('show active')
                        $('#infoTab').addClass('done')
                        $('#securityStep').addClass('show active')
                        $('#infoTab').removeClass('active')
                        $('#securityTab').addClass('active')
                        $('#infoTab').attr("aria-selected",false)
                        $('#securityTab').attr("aria-selected",true)
                        
                        getQuestionAndAnswer(result.data);
                        
                    }else{
                    	$("#noEmployeeError").show()
                    }
                },
                error:function(err){
                    alert(somethingWrongWord)
                }
            })
       }
    })

    $("#getUsernameBtn").click(function(){
        // to do test security answer
    	var answers = {
      			 hintAns : $('#answerHidden').val(),
      			 answer :  $('#hintAnswer').val()
               }
        var bootstrapValidator02 = $('#securityForm').data('bootstrapValidator')
        bootstrapValidator02.validate()
        if (bootstrapValidator02.isValid()) {

        	
          $.ajax({
	          url: urlMain+'/resetPassword/recoverUserNameStep2',
	          type:"POST",
	          data: answers,
	          success:function(result){
                $("#answerError").hide()
	        	  if(result.code === 2){
		        	  $('#securityStep').removeClass('show active')
		              $('#securityTab').removeClass('active')
		              $('#securityTab').addClass('done')
		              $('#completeStep').addClass('show active')
		              $('#completeTab').addClass('active')
		              $('#infoStep').attr("aria-selected",false)
		              $('#securityStep').attr("aria-selected",false)
		              $('#completeStep').attr("aria-selected",true)
	        	  }else{
	        		  $("#answerError").show()
	        	  }
	          },
              error:function(err){
                  alert(somethingWrongWord)
              }
	      });
        	
        }
    })
    $(".back-step1").click(function(){
        $('#infoStep').addClass('show active')
        $('#infoTab').removeClass('done')
        $('#securityStep').removeClass('show active')
        $('#infoTab').addClass('active')
        $('#securityTab').removeClass('active')
        $('#infoTab').attr("aria-selected",true)
        $('#securityTab').attr("aria-selected",false)
    })
})
function getQuestionAndAnswer(data) {
	
	console.log(data);
	$('#questionText').html(data.hintQuestion);
	$("#usernameShow").text(data.username);
	$("#answerHidden").val(data.hintAnswer);

}

function stepValidator01() {
    $('#personalDetailForm').bootstrapValidator({
            trigger: 'blur',
            live: 'enable',
            fields: {
                empNumber: {
                    validators: {
                        notEmpty: {
                                message: requiredFieldValidator
                            },
                            regexp: {
                                regexp: /^[0-9]\d{5}$/,
                                message: pleaseEnterCorrectFormatValidator
                            }
                    }
                },
                ssn:{
                    validators: {
                        notEmpty: {
                                message: requiredFieldValidator
                            },
                            regexp: {
                                regexp: /^[0-9]\d{8}$/,
                                message: pleaseEnterCorrectFormatValidator
                            }
                    }
                },
                birthDate: {
                    validators: {
                        notEmpty: {
                                message: requiredFieldValidator
                            },
                    }
                },
                zipCode: {
                    validators: {
                        notEmpty: {
                                message: requiredFieldValidator
                            },
                            regexp: {
                                regexp: /^[0-9]\d{4}$/,
                                message: pleaseEnterCorrectFormatValidator
                            }
                    }
                }
            }
        })
}

function stepValidator02(){
    $('#securityForm').bootstrapValidator({
        fields: {
			hintAnswer: {
                validators: {
                    notEmpty: {
                        message: requiredFieldValidator
                    },
                }
			}
        }
    })
}


function backToLogin(path, distid){
	window.location.href="/"+path+"/login?distid="+distid;
}

function jsMasking(obj) {
    //var inputTag = document.getElementById(txtBoxID);

    try {
        if (obj.getAttribute("data-type") == "DATE") {
            $("#" + obj.id).mask("99/99/9999");
        }
        if (obj.getAttribute("data-type") == "AREACODE") {
            $("#" + obj.id).mask("999");
        }
        if (obj.getAttribute("data-type") == "PHONENUM") {
            $("#" + obj.id).mask("999-9999");
        }
        if (obj.getAttribute("data-type") == "FULLPHONE") {
            $("#" + obj.id).mask("(999) 999-9999");
        }
        if (obj.getAttribute("data-type") == "SSN") {
            $("#" + obj.id).mask("999-99-9999");
        }
        if (obj.getAttribute("data-type") == "ZIP5") {
            $("#" + obj.id).mask("99999");
        }
        if (obj.getAttribute("data-type") == "ZIP4") {
            $("#" + obj.id).mask("9999");
        }
        if (obj.getAttribute("data-type") == "STUDENTID") {
            $("#" + obj.id).mask("999999");
        }
        if (obj.getAttribute("data-type") == "SECURITYNUM") {
            $("#" + obj.id).mask("9999");
        }
    }
    catch (err) {
        // do nothing
    }
}

function splitDate(date){
    var month='',day='',year=''
    var dateArray = date.split('/')
    month = dateArray[0]
    day = dateArray[1]
    year = dateArray[2]
    var dateObj = {
        month:month,
        day:day,
        year:year
    }
    return dateObj
}
// ALC-26 Show modal when Session time is out
var intervalId,intervalId,maxCountDown
initialTime()
$('body').on('keydown mousedown',function(e) {
    if(e.target.id=="resetTimeBtn"){
        // to do refresh
        location.reload() 
        $("#sessionNotLoginModal").modal("hide")
    }
})

function initialTime(){
    maxTime = 20; // minutes
    maxCountDown = 5; // minutes
    if (!maxTime ||  maxTime == "") {
        maxTime = 30;
    }
    if (!maxCountDown ||  maxCountDown == "") {
        maxCountDown = 10;
    }
    maxTime = parseInt(maxTime) * 60
    maxCountDown = parseInt(maxCountDown) * 60
    time = maxTime;
    clearInterval(intervalId)
    startCountTime();
}

function startCountTime() {
    intervalId = setInterval(function() {
        time--;
        if(time <= maxCountDown){
            var m = Math.ceil(time / 60)
            setCountText(m)
            if($('#sessionNotLoginModal').css('display')=="none"){
                $("#sessionNotLoginModal").modal('show')
            }
            if(time == 0){
                // to do refresh
                location.reload() 
            }
        }
    }, 1000)
}

function setCountText(m){
    $("#timeCountdown").text(m)
}

