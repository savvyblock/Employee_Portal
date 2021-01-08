/* ALC-26 valid password*/
	formPasswordValidator();
	
    function formPasswordValidator() {
        $('#updatePassword').bootstrapValidator({
            trigger: 'blur',
            live: 'enable',
            fields: {
                oldPassword: {
                    validators: {
                        notEmpty: {
                            message: requiredFieldValidator
                        }
                    }
                },
                password: {
                    validators: {
                        notEmpty: {
                            message: requiredFieldValidator
                        }
                    }
                },
                newPassword: {
                    validators: {
                        notEmpty: {
                            message: requiredFieldValidator
                        }
                    }
                }
            }
        })
    
    
    }
    
    $(function(){
        $("#changePsd").click(function(e){
        	var bootstrapValidator = $("#updatePassword").data('bootstrapValidator');
            bootstrapValidator.validate();
            var oldPsd = $("#oldPassword").val()
            var newPsd = $("#newPassword").val()
            if(oldPsd == newPsd){
                $(".oldPsdNewPsdSame").show()
                return
            }
            $(".oldPsdNewPsdSame").hide()
            if (bootstrapValidator.isValid()) {
                // ALC-26 validation from commonwebPortals 
                var password = $("#newPassword").val()
                var repassword = $("#newCheckPassword").val()
                var validPassword = validatePSD(password,repassword,minPSDLen,maxPSDLen)
                //1:success
                //2:first password Invalid
                //3:first password corrected but Mismatching
                //4:first password Invalid + Mismatching
                $(".passwordError").removeClass("moreText")
                if(validPassword != 1){
                    if(validPassword == 2){
                        $("#newPassword").parents(".form-group").addClass("has-error").removeClass("has-success")
                        $(".passwordError").show().text(invalidPasswordWord).addClass("moreText")
                        $("#newCheckPassword").parents(".form-group").addClass("has-success").removeClass("has-error")
                        $(".repasswordError").hide()
                    }
                    if(validPassword == 3){
                        $("#newCheckPassword").parents(".form-group").addClass("has-error").removeClass("has-success")
                        $(".repasswordError").show().text(passwordMatchWord)
                        $("#newPassword").parents(".form-group").addClass("has-success").removeClass("has-error")
                        $(".passwordError").hide()
                    }
                    if(validPassword == 4){
                        $("#newPassword").parents(".form-group").addClass("has-error").removeClass("has-success")
                        $(".passwordError").show().text(invalidPasswordWord).addClass("moreText")

                        $("#newCheckPassword").parents(".form-group").addClass("has-error").removeClass("has-success")
                        $(".repasswordError").show().text(passwordMatchWord)
                    }
                }else{
                    $("#newPassword").parents(".form-group").addClass("has-success").removeClass("has-error")
                    $(".passwordError").hide()
                    $("#newCheckPassword").parents(".form-group").addClass("has-success").removeClass("has-error")
                    $(".repasswordError").hide()
                }
                if(validPassword != 1){
                    return false
                }
                $.ajax({
                    type: 'post',
                    url: urlMain + '/profile/validatePassword',
                    cache: false,
                    data: {csrfmiddlewaretoken: $("#csrfmiddlewaretoken").val(), password: $("#oldPassword").val()},
                    dataType: 'json',
                    success: function(data) {
                    	if(data.success){
		                    $('.oldPsdValidator').hide()
		                    $('#updatePassword')[0].submit()
		                    sessionStorage.setItem("sessionPws",$("#newPassword").val());
                    	}else{
                            $('.oldPsdValidator').show()
                            return false
                        }
                }
                });
            }
        })
    })
    