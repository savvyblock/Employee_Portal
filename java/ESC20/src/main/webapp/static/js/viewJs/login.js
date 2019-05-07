$(function(){
    $('#loginForm').bootstrapValidator({
        live: 'enable',
        trigger: 'blur keyup',
        feedbackIcons: {
            valid: 'fa fa-check ',
            // invalid: 'fa fa-times',
            validating: 'fa fa-refresh'
        },
        fields: {
            username: {
                validators: {
                    notEmpty: {
                        message: usernameCannotBeEmptyValidator
                    }
                }
            },
            password: {
                validators: {
                    notEmpty: {
                        message: passwordCannotBeEmptyValidator
                    }
                }
            }
        },

    });
    $('#loginBackForm').bootstrapValidator({
        live: 'enable',
        trigger: 'blur keyup',
        feedbackIcons: {
            valid: 'fa fa-check ',
            // invalid: 'fa fa-times',
            validating: 'fa fa-refresh'
        },
        fields: {
            username: {
                validators: {
                    notEmpty: {
                        message: usernameCannotBeEmptyValidator
                    }
                }
            },
            password: {
                validators: {
                    notEmpty: {
                        message: passwordCannotBeEmptyValidator
                    }
                }
            }
        },

    });
    var url = window.location.href
    var urlParams = url.split("?"); 
    console.log(urlParams)
    // if(urlParams[1]=='error'){
    //     $(".authenticateFailed").show()
    // }else{
    //     $(".authenticateFailed").hide()
    // }
    var isTimeoutArry = urlParams[1]?urlParams[1].split("&"):false;
    var isTimeout = isTimeoutArry[1]?isTimeoutArry[1].split("="):false;
    if(isTimeout[1]){
        console.log("session end")
        $(".loginPage .account-inner.loginBox").hide()
        $(".loginPage .account-inner.logBackBox").show()
        $(".loginPage .account-top").addClass("sessionEnd")
    }else{
        console.log("login ")
        $(".loginPage .account-inner.loginBox").show()
        $(".loginPage .account-inner.logBackBox").hide()
        $(".loginPage .account-top").removeClass("sessionEnd")
    }
//    $('#signin').on('click',function(){
//        var userName = $('#inputEmail').val();
//        var userPwd = $('#inputPassword').val();
//        var bootstrapValidator = $('#loginForm').data('bootstrapValidator');
//        bootstrapValidator.validate();
//        if(bootstrapValidator.isValid()){
//            
//            $.ajax({
//                type:'POST',
//                url:urlMain+'/login',
//                dataType:'JSON',
//                contentType:'application/json;charset=UTF-8',
//                data:JSON.stringify({
//                    userName: userName,
//                    userPwd: userPwd
//                }),
//                success : function (res) {
//                    if(res.isSuccess == "true"){
//                        document.location = urlMain+'/home'
//                    } else {
//                        $("#incorrectMessage").show();
//                    }
//                },
//                error:function(res){
//                    $("#errorMessage").show();
//                }
//            });
//        }
//    });
});