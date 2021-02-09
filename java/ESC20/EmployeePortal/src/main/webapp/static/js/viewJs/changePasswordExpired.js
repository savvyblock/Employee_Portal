$(function() {
    stepValidator01()
    // ALC-29 when related input was set to null, hide the error message in case error message overlap
    $("#inputPassword,#inputRePassword").keyup(function(){
        if($("#inputPassword").val()==''){
            $(".passwordError").hide()
        }
        if($("#inputRePassword").val()==''){
            $(".repasswordError").hide()
        }
    })
    $("#finishBtn").click(function(){
        var validPsdForm = $('#changePdsExpired').data('bootstrapValidator')
        validPsdForm.validate();
        if (validPsdForm.isValid()) {
            var password = $("#inputPassword").val()
            var repassword = $("#inputRePassword").val()
            //ALC-29 set length limit
            var validPassword = validatePSD(password,repassword,minPSDLen,maxPSDLen)
            //1:success
            //2:first password Invalid
            //3:first password corrected but Mismatching
            //4:first password Invalid + Mismatching
            if(validPassword != 1){
                if(validPassword == 2){
                    $("#inputPassword").parents(".form-group").addClass("has-error").removeClass("has-success")
                    $(".passwordError").show().text(invalidPasswordWord)
                    $("#inputRePassword").parents(".form-group").addClass("has-success").removeClass("has-error")
                    $(".repasswordError").hide()
                }
                if(validPassword == 3){
                    $("#inputRePassword").parents(".form-group").addClass("has-error").removeClass("has-success")
                    $(".repasswordError").show().text(passwordMatchWord)
                    $("#inputPassword").parents(".form-group").addClass("has-success").removeClass("has-error")
                    $(".passwordError").hide()
                }
                if(validPassword == 4){
                    $("#inputPassword").parents(".form-group").addClass("has-error").removeClass("has-success")
                    $(".passwordError").show().text(invalidPasswordWord)

                    $("#inputRePassword").parents(".form-group").addClass("has-error").removeClass("has-success")
                    $(".repasswordError").show().text(passwordMatchWord)
                }
                return false
            }else{
                $("#inputPassword").parents(".form-group").addClass("has-success").removeClass("has-error")
                $(".passwordError").hide()
                $("#inputRePassword").parents(".form-group").addClass("has-success").removeClass("has-error")
                $(".repasswordError").hide()
            }
            // to do ajax
            var psdObj = {
                'oldPass': $('#currentPsd').val(),
                'newPass': $('#inputPassword').val()
            }
            $(".loadingOn").show()
            $.ajax({
                url:"changePassword",
                type:"POST",
                data : JSON.stringify(psdObj),
                dataType:"json",
                contentType : "application/json; charset=utf-8",
                async: true,
                success:function(result){
                    $("#errorOldPsd").hide()
                    $("#notSameError").hide()
                    $("#successSubmit").hide()
                  if(result.code == 1){
                      //success
                      $("#successSubmit").show()
                      setTimeout(function(){
                        window.location.href = "/EmployeePortal/login?distid="+districtID;
                      },2500)
                  }else if(result.code == 2){
                        //wrong old password
                        $("#errorOldPsd").show()
                    }else if(result.code == 3){
                        //should not same
                        $("#notSameError").show()
                    }else{
                        alert(somethingWrongWord)
                    }
                    setTimeout(function(){
                        $(".loadingOn").hide()
                    },200)
                },
                error:function(err){
                    alert(somethingWrongWord)
                    setTimeout(function(){
                        $(".loadingOn").hide()
                    },200)
                }
           });
           
        }
    })

})
function stepValidator01(){
    $('#changePdsExpired').bootstrapValidator({
        fields: {
            currentPsd:{
                validators: {
                    notEmpty: {
                        message: requiredWord
                    }
                }
            },
            password: {
                validators: {
                    notEmpty: {
                        message: requiredWord
                    }
                }
            },
            repassword: {
                validators: {
                    notEmpty: {
                        message: requiredWord
                    }
                }
            }
        }
    })
}