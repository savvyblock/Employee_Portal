var myDate = new Date()
var year = myDate.getFullYear()
var month = myDate.getMonth() + 1
var date = myDate.getDate()
var h = myDate.getHours() //get h (0-23)
var m = myDate.getMinutes() //get m (0-59)
var s = myDate.getSeconds()
if (parseInt(h) < 12) {
    var now =
        getNow(month) +
        '-' +
        getNow(date) +
        '-' +
        year +
        ' ' +
        getNow(h) +
        ':' +
        getNow(m) +
        ' ' +
        'AM'
} else {
    var hour = parseInt(h) - 12
    var now =
        getNow(month) +
        '-' +
        getNow(date) +
        '-' +
        year +
        ' ' +
        getNow(hour) +
        ':' +
        getNow(m) +
        ' ' +
        'PM'
}

function getNow(s) {
    return s < 10 ? '0' + s : s
}
document.getElementById('date-now').innerHTML = now
function submitEarning(){
    $("#selectEarnings")[0].submit();
}
$(function() {
    let showEarningDetail = false
    let showDeductionsDetail = false
    $(".totalEarningBtn").click(function(){
        if(!showEarningDetail){
            $(".earning-detail").show()
            $(this).html("Close <")
            showEarningDetail = true
        }else{
            $(".earning-detail").hide()
            $(this).html("Detail >")
            showEarningDetail = false
        }
        
    })
    $(".totalDeductionsBtn").click(function(){
        if(!showDeductionsDetail){
            $(".deduction-table").show()
            $(this).html("Close <")
            showDeductionsDetail = true
        }else{
            $(".deduction-table").hide()
            $(this).html("Detail >")
            showDeductionsDetail = false
        }
    })
})