$(document).ready(function() {
    console.log(initialLocaleCode)
    var formDate = $('#SearchStartDate')
        .fdatepicker({
            format: 'mm/dd/yyyy',
            language: initialLocaleCode
        })
        .on('changeDate', function(ev) {
            let fromInput = $('#SearchStartDate').val()
            let toInput = $('#SearchEndDate').val()
            if (fromInput && toInput) {
                let from = ev.date.valueOf()
                let to = toDate.date.valueOf()
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
            let fromInput = $('#SearchStartDate').val()
            let toInput = $('#SearchEndDate').val()
            if (fromInput && toInput) {
                let to = ev.date.valueOf()
                let from = formDate.date.valueOf()
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
    setGlobal()
    $("#retrieve").click(function(){
        let fromInput = changeDateYMD($("#SearchStartDate").val())
        let toInput = changeDateYMD($("#SearchEndDate").val())
        console.log(fromInput)
        console.log(toInput)
        if(fromInput && toInput && fromInput<=toInput){
            $("#timeErrorMessage").addClass("hide")
            $("#changeFreqForm")[0].submit();
        }else{
            $("#timeErrorMessage").removeClass("hide")
        }
    })
})

function changeMMYYDDFormat(date) {
    let string = date.split('/')
    console.log(string[2] + string[0] + string[1])
    return string[2] + string[0] + string[1]
}
function changeDateYMD(date){
    let dateArry = date.split("/")
    let DateFormat = new Date(dateArry[2]+"-"+dateArry[0]+"-"+dateArry[1])
    return DateFormat
}