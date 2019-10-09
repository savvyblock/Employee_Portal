$('#createNewUserForm').bootstrapValidator({
    live: 'disabled',
    trigger: null,
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
                    max:9,
                    message:lengthNotLessThan6_9Validator
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
                    max:9,
                    message: lengthNotLessThan6_9Validator
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
            trigger: null,
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
            trigger: null,
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
$("#hintAnswer").keydown(function(){
    $(".sameAnswer").hide()
})
$("#hintAnswer").change(function(){
    $(".sameAnswer").hide()
})
$(function(){
    $("#saveNewUser").on('click',function(){
    	var empNbr = $("#empNbr").val();
    	var username = $("#username").val();
    	var password= $("#password").val();
        var homeE = $("#homeEmail").val()
        var homeEV = $("#verifyHomeEmail").val()
        var workE = $("#workEmail").val()
        var workEV = $("#verifyWorkEmail").val()
        var hintQuestion = $("#hintQuestion").val();
        var hintAnswer = $("#hintAnswer").val();
        var newUserFormValidator = $('#createNewUserForm').data(
            'bootstrapValidator'
        )
        
        // console.log(newUserFormValidator.isValid())
        if(hintQuestion!='' && hintAnswer!='' && hintQuestion == hintAnswer){
            $("#password").val('')
            $("#newPassword").val('')
            $("#hintAnswer").parents('.form-group').removeClass('has-success').addClass('has-error')
            $(".sameAnswer").show()
            return
        }else{
            $(".sameAnswer").hide()
        }
        newUserFormValidator.validate()
        if (newUserFormValidator.isValid()) {
           
            if(workE===workEV && homeE===homeEV){
                $.ajax({
                    type: 'post',
                    url: urlMain+'/createUser/saveNewUser',
                    cache: false,
                    data: {empNbr: empNbr, username: username, password: password, workEmail: workE,
                    		homeEmail: homeE, hintQuestion: hintQuestion, hintAnswer: hintAnswer, 
                    		csrfmiddlewaretoken: $("#csrfmiddlewaretoken").val()},
                    dataType: 'json',
                    success: function(data) {
                    	console.log(data);
                    	$("#loginUsername").val(data.username);
                    	$("#loginPassword").val(data.password);
                    	$("#loginForm").submit();
                    }
                })
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