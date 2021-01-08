$('.icheck').iCheck({
    checkboxClass: 'icheckbox_square-green',
    radioClass: 'iradio_square-green',
    increaseArea: '20%' // optional
})
// ALC-26 validation from commonwebPortals 
$('#updatePassword').bootstrapValidator({
    trigger: 'blur',
    live: 'enable',
    fields: {
        password: {
            validators: {
                notEmpty: {
                    message: requiredFieldValidator
                }
            }
        },
        newPassword: {
            validators: {
                notEmpty: {
                    message: requiredFieldValidator
                }
            }
        }
    }
})
$(function(){
    // ALC-26 when related input was set to null, hide the error message in case error message overlap
    $("#newPassword,#newCheckPassword").keyup(function(){
        if($("#newPassword").val()==''){
            $(".passwordError").hide()
        }
        if($("#newCheckPassword").val()==''){
            $(".repasswordError").hide()
        }
    })
    $("#resetPsdBtn").click(function(){
        var bootstrapValidator = $("#updatePassword").data('bootstrapValidator');
        bootstrapValidator.validate();
        if (bootstrapValidator.isValid()) {
            // ALC-26 validation from commonwebPortals 
            var password = $("#newPassword").val()
            var repassword = $("#newCheckPassword").val()
            var validPassword = validatePSD(password,repassword,minPSDLen,maxPSDLen)
            //1:success
            //2:first password Invalid
            //3:first password corrected but Mismatching
            //4:first password Invalid + Mismatching
            $(".passwordError").removeClass("moreText")
            if(validPassword != 1){
                if(validPassword == 2){
                    $("#newPassword").parents(".form-group").addClass("has-error").removeClass("has-success")
                    $(".passwordError").show().text(invalidPasswordWord).addClass("moreText")
                    $("#newCheckPassword").parents(".form-group").addClass("has-success").removeClass("has-error")
                    $(".repasswordError").hide()
                }
                if(validPassword == 3){
                    $("#newCheckPassword").parents(".form-group").addClass("has-error").removeClass("has-success")
                    $(".repasswordError").show().text(passwordMatchWord)
                    $("#newPassword").parents(".form-group").addClass("has-success").removeClass("has-error")
                    $(".passwordError").hide()
                }
                if(validPassword == 4){
                    $("#newPassword").parents(".form-group").addClass("has-error").removeClass("has-success")
                    $(".passwordError").show().text(invalidPasswordWord).addClass("moreText")

                    $("#newCheckPassword").parents(".form-group").addClass("has-error").removeClass("has-success")
                    $(".repasswordError").show().text(passwordMatchWord)
                }
            }else{
                $("#newPassword").parents(".form-group").addClass("has-success").removeClass("has-error")
                $(".passwordError").hide()
                $("#newCheckPassword").parents(".form-group").addClass("has-success").removeClass("has-error")
                $(".repasswordError").hide()
            }
            if(validPassword != 1){
                return false
            }
            $("#updatePassword")[0].submit()
        }
    })
})