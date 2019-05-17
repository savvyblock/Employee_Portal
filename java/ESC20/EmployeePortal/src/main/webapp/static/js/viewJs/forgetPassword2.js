$(function(){
    $(".chooseEmailGroup input[type='radio']") .on('keypress', function(e) {
        console.log(e)
        var eCode = e.keyCode ? e.keyCode : e.which ? e.which : e.charCode;
        if (eCode == 13){
            $(this).click()
        }
    })
})