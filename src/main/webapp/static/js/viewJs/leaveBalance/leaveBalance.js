$(document).ready(function() {
    console.log(initialLocaleCode)
    var formDate = $('#SearchStartDate')
        .fdatepicker({
            format: 'mm/dd/yyyy',
            language: initialLocaleCode
        })
        .on('changeDate', function(ev) {
            var fromInput = $('#SearchStartInput').val()
            var toInput = $('#SearchEndInput').val()
            if (fromInput && toInput) {
                var from = ev.date.valueOf()
                var to = toDate.date.valueOf()
                if (from > to) {
                    $('#timeErrorMessage').removeClass('hide')
                    $('#retrieve').attr('disabled', 'disabled')
                    $('#retrieve').addClass('disabled')
                } else {
                    $('#timeErrorMessage').addClass('hide')
                    $('#retrieve').removeAttr('disabled')
                    $('#retrieve').removeClass('disabled')
                }
            }
        })
        .data('datepicker')
    var toDate = $('#SearchEndDate')
        .fdatepicker({
            format: 'mm/dd/yyyy',
            language: initialLocaleCode
        })
        .on('changeDate', function(ev) {
            var fromInput = $('#SearchStartInput').val()
            var toInput = $('#SearchEndInput').val()
            if (fromInput && toInput) {
                var to = ev.date.valueOf()
                var from = formDate.date.valueOf()
                if (from > to) {
                    $('#timeErrorMessage').removeClass('hide')
                    $('#retrieve').attr('disabled', 'disabled')
                    $('#retrieve').addClass('disabled')
                } else {
                    $('#timeErrorMessage').addClass('hide')
                    $('#retrieve').removeAttr('disabled')
                    $('#retrieve').removeClass('disabled')
                }
            }
        })
        .data('datepicker')
    $("#retrieve").click(function(){
        var fromValue = $("#SearchStartInput").val()
        var toValue = $("#SearchEndInput").val()
        var fromInput = changeDateYMD(fromValue)
        var toInput = changeDateYMD(toValue)
        console.log(fromInput)
        console.log(toInput)
        if((!fromValue || !toValue) || (fromInput && toInput && fromInput<=toInput)){
            $("#timeErrorMessage").addClass("hide")
            $("#SearchForm")[0].submit();
        }else{
            $("#timeErrorMessage").removeClass("hide")
        }
    })
})

function changeMMYYDDFormat(date) {
    var string = date.split('/')
    console.log(string[2] + string[0] + string[1])
    return string[2] + string[0] + string[1]
}
function changeDateYMD(date){
    var dateArry = date.split("/")
    var DateFormat = new Date(dateArry[2]+"-"+dateArry[0]+"-"+dateArry[1])
    return DateFormat
}