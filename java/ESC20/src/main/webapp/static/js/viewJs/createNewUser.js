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
                    message: requiredFieldValidator
                },
                regexp: {
                    regexp: /^[^\s]*$/,
                    message: haveNotSpaceValidator
                },
                stringLength: {
                    min: 6,
                    max: 8,
                    message:lengthNotLessThan6_8Validator
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
                    message:passwordLengthNotLessThan6Validator
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
                    message:passwordNotMatchValidator
                },
                stringLength: {
                    min: 6,
                    message: passwordLengthNotLessThan6Validator
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
            trigger: 'blur keyup change',
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
            trigger: 'blur keyup change',
            validators: {
                identical: {
                    field: 'homeEmail',
                    message:emailNotMatchValidator
                },
                emailAddress: {
                    message: pleaseEnterCorrectFormatValidator
                }
            }
        },
        hintQuestion: {
            validators: {
                notEmpty: {
                        message: requiredFieldValidator
                    },
            }
        },
        hintAnswer: {
            validators: {
                notEmpty: {
                        message: requiredFieldValidator
                    },
            }
        },
      
  }
})

$(function(){
    $("#saveNewUser").on('click',function(){
        var workE = $("#workEmail").val()
        var workEV = $("#verifyWorkEmail").val()
        var homeE = $("#homeEmail").val()
        var homeEV = $("#verifyHomeEmail").val()
        var newUserFormValidator = $('#createNewUserForm').data(
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