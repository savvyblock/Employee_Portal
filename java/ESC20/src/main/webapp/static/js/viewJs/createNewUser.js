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
        homeEmail: {
            validators: {
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
