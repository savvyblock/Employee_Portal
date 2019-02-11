/**
 * 
 */

$.ajaxSetup({
	cache : false
});

$(document).ready(function() {	
	$('#calendarPlugin').fullCalendar('render');
});