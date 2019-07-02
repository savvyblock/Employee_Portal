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

function submitCalendarForm() {
    $('#selectCalendar')[0].submit()
}
function downloadPDF() {
    $('.exportPDFBox').show()
    $('.exportPDFBox').addClass("printStatus")
    $('.exportPDFBox').append($('.calendarYTDTable').clone())
    var shareContent = $('.exportPDFBox')[0]

    var pdfDom = '.exportPDFBox .calendarYTDTable'
    var fileName = 'calendarYearToDate'
    var title = $(document).attr("title")
    convert2canvasDownload(shareContent,pdfDom,fileName,title)
}

function load(){
	$("#printIframe")[0].contentWindow.focus();
	$("#printIframe")[0].contentWindow.print();
}