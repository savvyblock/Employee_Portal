$(function(){
    //ALC-13 UI Alignment of ASCENDER and portals
    $("#AccessibilityLink").click(function(){
        $("#AccessibilityModal").show()
    })
    $(".modalAccessibility-content .close").click(function(){
        $("#AccessibilityModal").hide()
    })
    // ALC-13 improved function for  validation
    $('#loginForm').bootstrapValidator({
        live: 'enable',
        trigger:'blur',
        fields: {
            username: {
                container:"#inputEmailLabel",
                validators: {
                    notEmpty: {
                        message: requiredWord
                    }
                }
            },
            password: {
                container:"#inputPasswordLabel",
                validators: {
                    notEmpty: {
                        message: requiredWord
                    }
                }
            }
        },

    });
    $('#loginBackForm').bootstrapValidator({
        live: 'enable',
        trigger:'blur',
        fields: {
            username: {
                container:"#inputEmailLabelBack",
                validators: {
                    notEmpty: {
                        message: requiredWord
                    }
                }
            },
            password: {
                container:"#inputPasswordLabelBack",
                validators: {
                    notEmpty: {
                        message: requiredWord
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
        $("#loginForm").hide()
        $("#loginBackForm").show()
        // $(".loginPage .account-top").addClass("sessionEnd")
    }else{
        console.log("login ")
        $("#loginForm").show()
        $("#loginBackForm").hide()
        // $(".loginPage .account-top").removeClass("sessionEnd")
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