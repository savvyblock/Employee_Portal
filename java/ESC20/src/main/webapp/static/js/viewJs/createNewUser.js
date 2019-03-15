$('#createNewUserForm').bootstrapValidator({
    live: 'enable',
    trigger: 'blur keyup',
    feedbackIcons: {
        valid: 'fa fa-check ',
        validating: 'fa fa-refresh'
    },
    fields: {
      username: {
            validators: {
                notEmpty: {
                        message: 'validator.requiredField'
                    },
            }
        },
        password: {
            validators: {
              notEmpty: {
                message: 'validator.requiredField'
            },
            stringLength: {
                min: 6,
                message:'validator.passwordLengthNotLessThan6'
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
                    message:'validator.passwordNotMatch'
                },
                stringLength: {
                    min: 6,
                    message: 'validator.passwordLengthNotLessThan6'
                }
            }
        },
        workEmail: {
            validators: {
              emailAddress: {
                    message: 'validator.pleaseEnterCorrectFormat'
                }
            }
        },
        workEmailVerify: {
            trigger: 'blur keyup change',
            validators: {
                identical: {
                    field: 'workEmail',
                    message:'validator.emailNotMatch'
                },
                emailAddress: {
                    message: 'validator.pleaseEnterCorrectFormat'
                }
            }
        },
        homeEmail: {
            validators: {
              emailAddress: {
                    message: 'validator.pleaseEnterCorrectFormat'
                }
            }
        },
        homeEmailVerify: {
            trigger: 'blur keyup change',
            validators: {
                identical: {
                    field: 'homeEmail',
                    message:'validator.emailNotMatch'
                },
                emailAddress: {
                    message: 'validator.pleaseEnterCorrectFormat'
                }
            }
        },
        hintQuestion: {
            validators: {
                notEmpty: {
                        message: 'validator.requiredField'
                    },
            }
        },
        hintAnswer: {
            validators: {
                notEmpty: {
                        message: 'validator.requiredField'
                    },
            }
        },
      
  }
})

$(function(){
    $("#saveNewUser").on('click',function(){
        let workE = $("#workEmail").val()
        let workEV = $("#verifyWorkEmail").val()
        let homeE = $("#homeEmail").val()
        let homeEV = $("#verifyHomeEmail").val()
        let newUserFormValidator = $('#createNewUserForm').data(
            'bootstrapValidator'
        )
        newUserFormValidator.validate()
        console.log(newUserFormValidator.isValid())
        if (newUserFormValidator.isValid()) {
            if(workE===workEV && homeE===homeEV){
                $("#createNewUserForm")[0].submit()
            }else{
                if(workE!=workEV){
                    $("#workEmail").parents(".form-group").addClass("has-error").removeClass('has-success')
                    $("#verifyWorkEmail").parents(".form-group")
                    .addClass("has-error")
                    .removeClass('has-success')
                    .find(".help-block[data-bv-validator='identical']")
                    .show()
                }
                if(homeE!=homeEV){
                    $("#homeEmail").parents(".form-group").addClass("has-error").removeClass('has-success')
                    $("#verifyHomeEmail")
                    .parents(".form-group")
                    .addClass("has-error")
                    .removeClass('has-success')
                    .find(".help-block[data-bv-validator='identical']")
                    .show()
                }
            }
            
        }
    })
})