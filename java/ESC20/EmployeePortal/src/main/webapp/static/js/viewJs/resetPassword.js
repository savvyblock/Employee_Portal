$('.icheck').iCheck({
    checkboxClass: 'icheckbox_square-green',
    radioClass: 'iradio_square-green',
    increaseArea: '20%' // optional
})
$('#updatePassword').bootstrapValidator({
    live: 'enable',
    trigger: null,
    feedbackIcons: {
        valid: 'fa fa-check ',
        // invalid: 'fa fa-times',
        validating: 'fa fa-refresh'
    },
    fields: {
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
        }
    }
})