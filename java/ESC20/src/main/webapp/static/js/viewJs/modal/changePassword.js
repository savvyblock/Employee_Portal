
	formValidator();
	
    function formValidator() {
        $('#updatePassword').bootstrapValidator({
                live: 'enable',
                trigger: 'blur keyup',
                feedbackIcons: {
                    valid: 'fa fa-check ',
                    // invalid: 'fa fa-times',
                    validating: 'fa fa-refresh'
                },
                fields: {
                  oldPassword: {
                        validators: {
                            notEmpty: {
                                message: 'validator.requiredField'
                            },
                            stringLength: {
                                min: 6,
                                message:
                                    'validator.passwordLengthNotLessThan6'
                            },
                            password:{
                            	message:''
                            }
                        }
                    },
                    password: {
                        validators: {
                            notEmpty: {
                                message: 'validator.requiredField'
                            },
                            stringLength: {
                                min: 6,
                                message:
                                    'validator.passwordLengthNotLessThan6'
                            }
                        }
                    },
                    newPassword: {
                        validators: {
                            notEmpty: {
                                message: 'validator.requiredField'
                            },
                            identical: {
                                field: 'password',
                                message:
                                    'validator.twoPasswordsNotMatch'
                            },
                            stringLength: {
                                min: 6,
                                message: 'validator.passwordLengthNotLessThan6'
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
            if (bootstrapValidator.isValid()) {
                let old = sessionStorage.getItem("sessionPws");
                let currentOld = sha256_digest($("#oldPassword").val());
                console.log("old", old);
                
                console.log("currentOld", currentOld);
                if(old == currentOld){
                    $('.oldPsdValidator').hide()
                    $('#updatePassword')[0].submit()
                    sessionStorage.setItem("sessionPws",$("#newPassword").val());
                }else{
                    $('.oldPsdValidator').show()
                    return false
                }
            }
        })
    })
    