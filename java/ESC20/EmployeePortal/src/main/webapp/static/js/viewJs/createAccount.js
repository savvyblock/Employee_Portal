
// ALC-13
$(function(){
    stepValidator01()
    stepValidator02()
    stepValidator03()

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
            // to to ajax, retrieve user
            $.ajax({
                type: 'post',
                url: urlMain+'/createUser/retrieveEmployeeUser',
                cache: false,
                data: userObj,
                dataType: 'json',
                success: function(data) {
                    console.log(data);
                    $("#EmpExitError").hide()
                    $("#noEmployeeError").hide()
                    if(data.success){
                        $('#step1').removeClass('show active')
                        $('#step1-tab').addClass('done')
                        $('#step2').addClass('show active')
                        $('#step1-tab').removeClass('active')
                        $('#step2-tab').addClass('active')
                        $('#step1-tab').attr("aria-selected",false)
                        $('#step2-tab').attr("aria-selected",true)
                        $('#step3-tab').attr("aria-selected",false)
                        $('#step4-tab').attr("aria-disabled",false)
                    }else{
                        if(data.isExistUser){
                            $("#EmpExitError").show()
                            $("#noEmployeeError").hide()
                        }else{
                            $("#EmpExitError").hide()
                            $("#noEmployeeError").show()
                        }
                        
                    }
                    
                },
                error:function(err){
                    alert(somethingWrongWord)
                }
            })
        }
    })
    $("#usernameCreate,#passwordCreate,#newPassword").keyup(function(){
        if($("#usernameCreate").val()==''){
            $(".userNameError").hide()
        }
        if($("#passwordCreate").val()==''){
            $(".passwordError").hide()
        }
        if($("#newPassword").val()==''){
            $(".repasswordError").hide()
        }
    })
    $(".next-step2").click(function(){
        var homeE = $("#homeEmail").val()
        var homeEV = $("#verifyHomeEmail").val()
        var homeStatic = $("#staticHomeEmail").text()
        var workE = $("#workEmail").val()
        var workEV = $("#verifyWorkEmail").val()
        var workStatic = $("#staticWorkEmail").text()

        var bootstrapValidator02 = $('#accountDetailForm').data('bootstrapValidator')
        bootstrapValidator02.validate()
        if (bootstrapValidator02.isValid()) {
            if(workE!=workEV){
                $("#workEmail").parents(".form-group").addClass("has-error").removeClass('has-success')
                $("#verifyWorkEmail").parents(".form-group").addClass("has-error").removeClass('has-success')
                .find(".help-block[data-bv-validator='identical']")
                .show()
                return
            }
            if(homeE!=homeEV){
                $("#homeEmail").parents(".form-group").addClass("has-error").removeClass('has-success')
                $("#verifyHomeEmail")
                .parents(".form-group")
                .addClass("has-error")
                .removeClass('has-success')
                .find(".help-block[data-bv-validator='identical']")
                .show()
                return
            }
            // ALC-26 validation from commonwebPortals 
            var userName = $("#usernameCreate").val()
            var password = $("#passwordCreate").val()
            var repassword = $("#newPassword").val()
            var validUsername = validateUsername(userName)
            var validPassword = validatePSD(password,repassword,minPSDLen,maxPSDLen)
            //1:success
            //2:username is Invalid
            if(validUsername !=1){
                $("#usernameCreate").parents(".form-group").addClass("has-error").removeClass("has-success")
                $(".userNameError").show().text(usernameValidWord)
            }else{
                $("#usernameCreate").parents(".form-group").addClass("has-success").removeClass("has-error")
                $(".userNameError").hide()
            }
            //1:success
            //2:first password Invalid
            //3:first password corrected but Mismatching
            //4:first password Invalid + Mismatching
            $(".passwordError").removeClass("moreText")
            if(validPassword != 1){
                if(validPassword == 2){
                    $("#passwordCreate").parents(".form-group").addClass("has-error").removeClass("has-success")
                    $(".passwordError").show().text(invalidPasswordWord).addClass("moreText")
                    $("#newPassword").parents(".form-group").addClass("has-success").removeClass("has-error")
                    $(".repasswordError").hide()
                }
                if(validPassword == 3){
                    $("#newPassword").parents(".form-group").addClass("has-error").removeClass("has-success")
                    $(".repasswordError").show().text(passwordMatchWord)
                    $("#passwordCreate").parents(".form-group").addClass("has-success").removeClass("has-error")
                    $(".passwordError").hide()
                }
                if(validPassword == 4){
                    $("#passwordCreate").parents(".form-group").addClass("has-error").removeClass("has-success")
                    $(".passwordError").show().text(invalidPasswordWord).addClass("moreText")

                    $("#newPassword").parents(".form-group").addClass("has-error").removeClass("has-success")
                    $(".repasswordError").show().text(passwordMatchWord)
                }
            }else{
                $("#passwordCreate").parents(".form-group").addClass("has-success").removeClass("has-error")
                $(".passwordError").hide()
                $("#newPassword").parents(".form-group").addClass("has-success").removeClass("has-error")
                $(".repasswordError").hide()
            }
            if(validUsername !=1 || validPassword != 1){
                return false
            }

            $('#step2').removeClass('show active')
            $('#step2-tab').removeClass('active')
            $('#step2-tab').addClass('done')
            $('#step3').addClass('show active')
            $('#step3-tab').addClass('active')
            $('#step1-tab').attr("aria-selected",false)
            $('#step2-tab').attr("aria-selected",false)
            $('#step3-tab').attr("aria-selected",true)
            $('#step4-tab').attr("aria-selected",false)
        }
    })
    $("#hintAnswer").keyup(function(){
        if($(this).val() == ''){
            $(".sameAnswer").hide()
        }
    })
    $("#hintAnswer").keyup(function(){
        if($(this).val() == ''){
            $(".sameAnswer").hide()
        }
    })
    // ALC-9
    $('.next-step3').click(function(){
        var hintQuestion = $("#hintQuestion").val();
        var hintAnswer = $("#hintAnswer").val();

        if(hintQuestion!='' && hintAnswer!='' && hintQuestion == hintAnswer){
            $("#password").val('')
            $("#newPassword").val('')
            $("#hintAnswer").parents('.form-group').removeClass('has-success').addClass('has-error')
            $(".sameAnswer").show()
            return
        }else{
            $(".sameAnswer").hide()
        }

        var bootstrapValidator03 = $('#securityForm').data('bootstrapValidator')
        bootstrapValidator03.validate()
        if (bootstrapValidator03.isValid()) {
            $('#step3').removeClass('show active')
            $('#step3-tab').removeClass('active')
            $('#step3-tab').addClass('done')
            $('#step4').addClass('show active')
            $('#step4-tab').addClass('active')
            $('#step1-tab').attr("aria-selected",false)
            $('#step2-tab').attr("aria-selected",false)
            $('#step3-tab').attr("aria-selected",false)
            $('#step4-tab').attr("aria-selected",true)
            
        }
    })
    $("#createAccount").click(function(){
        var empNbr = $("#employeeNumber").val();
        var ssn = $("#SSNumber").val();
        var username = $("#usernameCreate").val();
        var password= $("#passwordCreate").val();
        var homeE = $("#homeEmail").val()
        var homeEV = $("#verifyHomeEmail").val()
        var homeStatic = $("#staticHomeEmail").text()
        var workE = $("#workEmail").val()
        var workEV = $("#verifyWorkEmail").val()
        var workStatic = $("#staticWorkEmail").text()
        var hintQuestion = $("#hintQuestion").val();
        var hintAnswer = $("#hintAnswer").val();
        var wEmail = (workE || workStatic).trim()
        var hEmail = (homeE || homeStatic).trim()
        var userObj = {
            empNbr: empNbr, 
            ssn:ssn,
            username: username, 
            password: password, 
            workEmail: wEmail,
            homeEmail: hEmail, 
            hintQuestion: hintQuestion, 
            hintAnswer: hintAnswer, 
            csrfmiddlewaretoken: $("#csrfmiddlewaretoken").val()
        }
        $.ajax({
            type: 'post',
            url: urlMain+'/createUser/saveNewUser',
            cache: false,
            data: userObj,
            dataType: 'json',
            success: function(data) {
                console.log(data);
                $("#errorMsg").hide()
                if(data.success == "true"){
                    $("#loginUsername").val(username)
                    $("#loginPassword").val(password)
                    $('#step4').removeClass('show active')
                    $('#step4-tab').removeClass('active')
                    $('#step4-tab').addClass('done')
                    $('#step5').addClass('show active')
                    $('#step5-tab').addClass('active')
                    $('#step1-tab').attr("aria-selected",false)
                    $('#step2-tab').attr("aria-selected",false)
                    $('#step3-tab').attr("aria-selected",false)
                    $('#step4-tab').attr("aria-selected",false)
                    $('#step5-tab').attr("aria-selected",true)
                }else{
                    $("#errorMsg").show()
                }
                
            },
            error:function(err){
                alert(somethingWrongWord)
            }
        })
    })
    $('.back-step3').click(function(){
        $('#step3').addClass('show active')
        $('#step3-tab').removeClass('done')
        $('#step4').removeClass('show active')
        $('#step3-tab').addClass('active')
        $('#step4-tab').removeClass('active')
    })
    $(".back-step1").click(function(){
        $('#step1').addClass('show active')
        $('#step1-tab').removeClass('done')
        $('#step2').removeClass('show active')
        $('#step1-tab').addClass('active')
        $('#step2-tab').removeClass('active')
        $('#step1-tab').attr("aria-selected",true)
        $('#step2-tab').attr("aria-selected",false)
        $('#step3-tab').attr("aria-selected",false)
        $('#step4-tab').attr("aria-disabled",false)
    })
    $(".back-step2").click(function(){
        $('#step2').addClass('show active')
        $('#step2-tab').removeClass('done')
        $('#step3').removeClass('show active')
        $('#step2-tab').addClass('active')
        $('#step3-tab').removeClass('active')
        $('#step1-tab').attr("aria-selected",false)
        $('#step2-tab').attr("aria-selected",true)
        $('#step3-tab').attr("aria-selected",false)
        $('#step4-tab').attr("aria-disabled",false)
    })
    $("#finishBtn").click(function(){
        $("#loginFormCreate").submit();
    })
})
function stepValidator01() {
    $('#personalDetailForm').bootstrapValidator({
            trigger: 'blur',
            live: 'enable',
            fields: {
                empNumber: {
                    validators: {
                        notEmpty: {
                                message: enRequiredValidator
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
                                message: ssnRequiredValidator
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
                                message: dobRequiredValidator
                            },
                    }
                },
                zipCode: {
                    validators: {
                        notEmpty: {
                                message: zipRequiredValidator
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
    $('#accountDetailForm').bootstrapValidator({
        trigger: 'blur',
        live: 'enable',
        fields: {
            txtUsername: {
                  validators: {
                      notEmpty: {
                          message: usRequiredValidator
                      },
                      remote: {
						message: userExistWord,
                        url: urlMain+'/isUserExisted',
                        type:"post",
                        data : {username:function() {
                            return $('input[name="txtUsername"]').val() }
                         },
                        delay:2000,
                        cache:false,
                        async:false
					}
                  }
              },
              txtPassword: {
                  validators: {
                      notEmpty: {
                          message: psdRequiredValidator
                      }
                  }
              },
              newPassword: {
                  validators: {
                      notEmpty: {
                          message: psdReRequiredValidator
                      }
                  }
              },
              workEmail: {
                  validators: {
                      emailAddress: {
                          message: pleaseEnterCorrectFormatValidator
                      }
                  }
              },
              workEmailVerify: {
                  trigger: null,
                  validators: {
                      identical: {
                          field: 'workEmail',
                          message:emailNotMatchValidator
                      },
                      emailAddress: {
                          message: pleaseEnterCorrectFormatValidator
                      }
                  }
              },
              homeEmail: {
                  validators: {
                    emailAddress: {
                          message: pleaseEnterCorrectFormatValidator
                      }
                  }
              },
              homeEmailVerify: {
                  trigger: null,
                  validators: {
                      identical: {
                          field: 'homeEmail',
                          message:emailNotMatchValidator
                      },
                      emailAddress: {
                          message: pleaseEnterCorrectFormatValidator
                      }
                  }
              }
            
        }
    })
}

function stepValidator03(){
    $('#securityForm').bootstrapValidator({
        fields: {
            hintQuestion: {
                validators: {
                    notEmpty: {
                        message: sqRequiredValidator
                    }
                }
            },
			hintAnswer: {
                validators: {
                    notEmpty: {
                        message: saRequiredValidator
                    },
                    // regexp: {
                    // 	regexp: /^[\S\s]{1,250}$/,
                    // 	message: 'validation.hintAnswerValidate'
                    // }
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

