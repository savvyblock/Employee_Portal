$(function(){
    $('#loginForm').bootstrapValidator({
        fields: {
            username: {
                validators: {
                    notEmpty: {
                        message: 'validator.usernameCannotBeEmpty'
                    }
                }
            },
            password: {
                validators: {
                    notEmpty: {
                        message: 'validator.passwordCannotBeEmpty'
                    }
                }
            }
        },

    });
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