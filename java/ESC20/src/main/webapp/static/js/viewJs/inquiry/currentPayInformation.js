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

function displayAccountNumber(num) {
    var lastFourNum = num.substr(-4);
    
    return lastFourNum;
}

function downloadPDF() {
    $('.exportPDFBox').show()
    $('.exportPDFBox').append($('.needToClone').clone())
    var shareContent = $('.exportPDFBox')[0]

    let pdfDom = '.exportPDFBox .needToClone'
    let fileName = 'currentPayInformation'
    convert2canvasDownload(shareContent,pdfDom,fileName)
}
