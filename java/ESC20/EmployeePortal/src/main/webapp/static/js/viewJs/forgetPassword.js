$('#retrieveUserName').bootstrapValidator({
    live: 'enable',
    trigger: 'blur',
    feedbackIcons: {
        valid: 'fa fa-check ',
        // invalid: 'fa fa-times',
        validating: 'fa fa-refresh'
    },
    fields: {
        email: {
            validators: {
                notEmpty: {
                    message: requiredFieldValidator
                },
                emailAddress: {
                    message: pleaseEnterCorrectMailFormatValidator
                }
            }
        },
        empNumber: {
            validators: {
                notEmpty: {
                        message: requiredFieldValidator
                    },
                    regexp: {
                        regexp: /^[0-9]\d{5}$/,
                        message: pleaseEnterCorrectFormatValidator
                    }
            }
        },
        dateMonth: {
            validators: {
                notEmpty: {
                        message: requiredFieldValidator
                    },
                    regexp: {
                        regexp: /0[1-9]|1[0-2]/,
                        message: pleaseEnterCorrectFormatValidator
                    }
            }
        },
        dateDay: {
            validators: {
                notEmpty: {
                        message: requiredFieldValidator
                    },
                    regexp: {
                        regexp: /0[1-9]|[1-2][0-9]|3[0-1]/,
                        message: pleaseEnterCorrectFormatValidator
                    }
            }
        },
        dateYear: {
            validators: {
                notEmpty: {
                        message: requiredFieldValidator
                    },
                    regexp: {
                        regexp: /^[1-2]\d{3}$/,
                        message: pleaseEnterCorrectFormatValidator
                    }
            }
        },
        zipCode: {
            validators: {
                notEmpty: {
                        message: requiredFieldValidator
                    },
                    regexp: {
                        regexp: /^[0-9]\d{4}$/,
                        message: pleaseEnterCorrectFormatValidator
                    }
            }
        }
    }
})
$('#resetPassword').bootstrapValidator({
    live: 'enable',
    trigger: 'blur',
    feedbackIcons: {
        valid: 'fa fa-check ',
        // invalid: 'fa fa-times',
        validating: 'fa fa-refresh'
    },
    fields: {
        userName: {
            validators: {
                notEmpty: {
                    message: requiredFieldValidator
                }
            }
        },
        email: {
            validators: {
                notEmpty: {
                    message: requiredFieldValidator
                },
                emailAddress: {
                    message: pleaseEnterCorrectMailFormatValidator
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
var paramArry = window.location.href.split("/");
var len = paramArry.length
console.log(paramArry[len-1])
if(paramArry[len-1]){
    if(paramArry[len-1]=='resetPassword'){
        $('#reset').iCheck('check');
        $('#retrieveUserName').hide()
        $('#resetPassword').show()
    }
}