$('#retrieveUserName').bootstrapValidator({
    live: 'enable',
    trigger: 'blur keyup',
    feedbackIcons: {
        valid: 'fa fa-check ',
        // invalid: 'fa fa-times',
        validating: 'fa fa-refresh'
    },
    fields: {
        email: {
            validators: {
                notEmpty: {
                    message: 'validator.requiredField'
                },
                emailAddress: {
                    message: 'validator.pleaseEnterCorrectMailFormat'
                }
            }
        },
        empNumber: {
            validators: {
                notEmpty: {
                        message: 'validator.requiredField'
                    },
                    regexp: {
                        regexp: /^[0-9]\d{5}$/,
                        message: 'validator.pleaseEnterCorrectFormat'
                    }
            }
        },
        dateMonth: {
            validators: {
                notEmpty: {
                        message: 'validator.requiredField'
                    },
                    regexp: {
                        regexp: /0[1-9]|1[0-2]/,
                        message: 'validator.pleaseEnterCorrectFormat'
                    }
            }
        },
        dateDay: {
            validators: {
                notEmpty: {
                        message: 'validator.requiredField'
                    },
                    regexp: {
                        regexp: /[0-2]\d|3[0-1]/,
                        message: 'validator.pleaseEnterCorrectFormat'
                    }
            }
        },
        dateYear: {
            validators: {
                notEmpty: {
                        message: 'validator.requiredField'
                    },
                    regexp: {
                        regexp: /^[0-9]\d{3}$/,
                        message: 'validator.pleaseEnterCorrectFormat'
                    }
            }
        },
        zipCode: {
            validators: {
                notEmpty: {
                        message: 'validator.requiredField'
                    },
                    regexp: {
                        regexp: /^[0-9]\d{4}$/,
                        message: 'validator.pleaseEnterCorrectFormat'
                    }
            }
        }
    }
})
$('#resetPassword').bootstrapValidator({
    live: 'enable',
    trigger: 'blur keyup',
    feedbackIcons: {
        valid: 'fa fa-check ',
        // invalid: 'fa fa-times',
        validating: 'fa fa-refresh'
    },
    fields: {
        userName: {
            validators: {
                notEmpty: {
                    message: 'validator.requiredField'
                }
            }
        },
        email: {
            validators: {
                notEmpty: {
                    message: 'validator.requiredField'
                },
                emailAddress: {
                    message: 'validator.pleaseEnterCorrectMailFormat'
                }
            }
        }
    }
})
$('#retrieve').on('ifChecked', function(event) {
    console.log(event)
    $('#retrieveUserName').show()
    $('#resetPassword').hide()
})
$('#reset').on('ifChecked', function(event) {
    console.log(event)
    $('#retrieveUserName').hide()
    $('#resetPassword').show()
})
let paramArry = window.location.href.split("/");
let len = paramArry.length
console.log(paramArry[len-1])
if(paramArry[len-1]){
    if(paramArry[len-1]=='resetPassword'){
        $('#reset').iCheck('check');
        $('#retrieveUserName').hide()
        $('#resetPassword').show()
    }
}