$('.icheck').iCheck({
    checkboxClass: 'icheckbox_square-green',
    radioClass: 'iradio_square-green',
    increaseArea: '20%' // optional
})
$('#updatePassword').bootstrapValidator({
    live: 'enable',
    trigger: 'blur keyup',
    feedbackIcons: {
        valid: 'fa fa-check ',
        // invalid: 'fa fa-times',
        validating: 'fa fa-refresh'
    },
    fields: {
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
        }
    }
})