$(document).ready(function() {
    var path = (window.location + '').split('/')
    var menuItem
    var item = path[path.length - 2]
    
    if (
        item == 'calendarYearToDate' ||
        item == 'currentPayInformation' ||
        item == 'deductions' ||
        item == 'earnings' ||
        item == 'w2Information' ||
        item == 'information1095'
    ) {
        menuItem = 'inquiry'
    }
    if (
        item == 'approveLeaveRequest' ||
        item == 'leaveOverview' ||
        item == 'supervisorCalendar' ||
        item == 'leaveRequestTemporaryApprovers'
    ) {
        menuItem = 'supervisor'
    }
    if (
        item == 'travelRequest' ||
        item == 'approveTravelRequest'        
    ) {
        menuItem = 'travelRequests'
    }
    var menuElement = $('#' + menuItem)
    var itemElement = $('#' + item)
    if (menuElement) {
        menuElement.addClass('menu-open')
        menuElement.children('ul')
        .attr('style', 'display: block;')
    }
    if (itemElement){
        itemElement.addClass('active')
    }
    getBudgeDetail()
    updateBudgeCountAndInfo()
})
var maxTime = sessionTimeOut // seconds
console.log("timeout config: " + sessionTimeOut);
var time = maxTime
var budgeCount = 0
$('body').on(
    'keydown mousedown',
    function(e) {
        time = maxTime // reset
    }
)
startCountTime()
function startCountTime() {
    var intervalId = setInterval(function() {
        time--
        if (time <= 30) {
            // $('#logoutModal').modal('show')
            // $('#timeCounter').text(time)
            // clearInterval(intervalId);
            if (time == 0) {
                $.ajax({
                    type: 'post',
                    url: urlMain + '/markTimeout',
                    success: function(data) {
                    	$("#sessionOutForm")[0].submit();
                    }
                });
            }
        }
    }, 1000)
}
function updateBudgeCountAndInfo() {
    console.log('here')
    // var budgeCount = setInterval(function() {
        getBudgeDetail()
    // }, 3000)
}
function getBudgeDetail() {
    $.ajax({
        type: 'post',
        url: urlMain + '/notifications/getBudgeCount',
        cache: false,
        data: {csrfmiddlewaretoken: $("#csrfmiddlewaretoken").val()},
        dataType: 'json',
        success: function(data) {
            if (budgeCount == data.count) {
                return
            } else {
                // console.log('data', data)
                budgeCount = data.count
                // console.log(budgeCount)
                if (budgeCount > 0) {
                    $('#navBadge').show()
                }
                $('#budgeCount').html(budgeCount)
                $('#navBadge').html(budgeCount)
                $.ajax({
                    type: 'post',
                    url: urlMain + '/notifications/getTop5Alerts',
                    cache: false,
                    dataType: 'json',
                    success: function(data) {
                        // console.log('list', data)
                        var list = '<ul>'
                        var items = data.list
                        var content
                        for (var s in items) {
                            content = items[s].msgContent + ''
                            // console.log(content)
                            if (content.length >= 20) {
                                content = content.substring(0, 80) + '...'
                            }
                            list += '<li>' + content + '</li>'
                        }
                        list += '</ul>'
                        $('#top5Alert').html(list)
                    }
                })
            }
        }
    })
}
