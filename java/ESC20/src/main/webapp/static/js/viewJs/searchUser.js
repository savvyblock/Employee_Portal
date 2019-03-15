$('#retrieveUser').bootstrapValidator({
    live: 'enable',
    trigger: 'blur keyup',
    feedbackIcons: {
        valid: 'fa fa-check ',
        validating: 'fa fa-refresh'
    },
    fields: {
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
                        regexp: /0[1-9]|[1-2][0-9]|3[0-1]/,
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
                        regexp: /^[1-2]\d{3}$/,
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
});



/* 
$('#retrieveBtn').click(function () {
    
    var empNumber = $("input[name='searchUser.empNumber']").val();
    var dateMonth = $("input[name='searchUser.dateMonth']").val();
    var dateDay = $("input[name='searchUser.dateDay']").val();
    var dateYear = $("input[name='searchUser.dateYear']").val();
    var zipCode = $("input[name='searchUser.zipCode']").val();
    
    var searchUser={
            empNumber:empNumber,
            dateMonth:dateMonth,
            dateDay:dateDay,
            dateYear:dateYear,
            zipCode:zipCode
    };
    
     $.ajax({
             type: "POST",
             dataType: "json",
             url: "retrieveEmployee" ,
             data:JSON.stringify(searchUser),
             contentType: 'application/json;charset=UTF-8',
             success: function (result) {
                 if (result.resultCode == 200) {
                     alert("SUCCESS");
                 }
                 ;
             },
             error : function(e) {
                 console.log(e);
             }
         });
})
*/
