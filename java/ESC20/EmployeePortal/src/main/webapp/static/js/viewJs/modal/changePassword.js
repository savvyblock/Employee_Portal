
	formPasswordValidator();
	
    function formPasswordValidator() {
        $('#updatePassword').bootstrapValidator({
                live: 'enable',
                trigger: null,
                feedbackIcons: {
                    valid: 'fa fa-check ',
                    // invalid: 'fa fa-times',
                    validating: 'fa fa-refresh'
                },
                fields: {
                  oldPassword: {
                        validators: {
                            notEmpty: {
                                message: requiredFieldValidator
                            },
                            stringLength: {
                                min: 6,
                                message:
                                    passwordLengthNotLessThan6Validator
                            },
                            password:{
                            	message:''
                            }
                        }
                    },
                    password: {
                        validators: {
                            notEmpty: {
                                message: requiredFieldValidator
                            },
                            stringLength: {
                                min: 6,
                                message:
                                    passwordLengthNotLessThan6Validator
                            }
                        }
                    },
                    newPassword: {
                        validators: {
                            notEmpty: {
                                message: requiredFieldValidator
                            },
                            identical: {
                                field: 'password',
                                message:twoPasswordsNotMatchValidator
                            },
                            stringLength: {
                                min: 6,
                                message: passwordLengthNotLessThan6Validator
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
    