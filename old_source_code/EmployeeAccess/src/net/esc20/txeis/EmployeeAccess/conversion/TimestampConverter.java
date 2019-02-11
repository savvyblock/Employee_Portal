package net.esc20.txeis.EmployeeAccess.conversion;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class TimestampConverter 
{
	public static String convertTimestamp(Timestamp stamp)
	{
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSS");
		String time = df.format(stamp.getTime());
		return time;
	}
}
