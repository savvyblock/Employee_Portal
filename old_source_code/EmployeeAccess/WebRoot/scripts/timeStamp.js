function getTimeStamp()
{
	var d = new Date();
	var o = (d.getMonth()+1) + "-" + d.getDate() + "-" + d.getFullYear();
	var hours = d.getHours();
	var minutes = d.getMinutes();
	var seconds = d.getSeconds();
	
	if(minutes < 10)
	{
		minutes = "0" + minutes;
	}
	
	var ampm = "AM";
	
	if(hours > 12)
	{
		hours -= 12;
		ampm = "PM";
	}
	
	if(seconds < 10)
	{
		seconds = "0" + seconds;
	}
	
	o += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + hours + ":" + minutes + ":" + seconds + " " + ampm;
	
	return o;
}