function get_provisional_exp(year, month, day) {
	var expireDate = new Date(year, month, day + 30);
	var today = new Date();
	var result = "";
	
	if (today > expireDate) {
		var days_past = days_between(today, expireDate);
		if (days_past == 1) {
			var daysAgo = days_past + " day ago,";			
		} else {
			var daysAgo = days_past + " days ago,";
		}
		result = "Provisional Enrollment Expired " + daysAgo + " on " + print_date(expireDate);
	} else if (expireDate > today) {
		if (days_past == 1) {
			var daysAgo = days_past + " day,";			
		} else {
			var daysAgo = days_past + " days,";
		}
		result = "Provisional Enrollment Expires in " + daysAgo + " on " + print_date(expireDate);
	} else {
		result = "Provisional Enrollment Expires Today";
	}
	
	return result;
}

function print_date(date) {
	var month = date.getMonth() + 1;
	var day = date.getDate();
	var year = date.getFullYear();
	
	return month + "/" + day + "/" + year;
}

function days_between(date1, date2) {

    // The number of milliseconds in one day
    var ONE_DAY = 1000 * 60 * 60 * 24

    // Convert both dates to milliseconds
    var date1_ms = date1.getTime()
    var date2_ms = date2.getTime()

    // Calculate the difference in milliseconds
    var difference_ms = Math.abs(date1_ms - date2_ms)
    
    // Convert back to days and return
    return Math.round(difference_ms/ONE_DAY)
}
