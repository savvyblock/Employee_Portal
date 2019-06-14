package com.esc20.util;


import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class TimestampConverter 
{
	public static String convertTimestamp(Timestamp stamp)
	{
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSS");
		String time = df.format(stamp.getTime());
		
		if(time.length() > 16) {
			time = time.substring(0, 16);
		}
		
		return time;
	}
}
