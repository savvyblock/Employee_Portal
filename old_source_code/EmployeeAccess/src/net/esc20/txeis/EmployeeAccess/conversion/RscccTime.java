package net.esc20.txeis.EmployeeAccess.conversion;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RscccTime implements Serializable
{
	private static final long serialVersionUID = -6422875013697692201L;

	public boolean equals(Object x, Object y)
	{
		if (x == y)
		{
			return true;
		} else if (x == null || y == null)
		{
			return false;
		} else
		{
			return x.equals(y);
		}
	}


	public static Date convertToDate(String dbDate) throws ParseException
	{
		return new SimpleDateFormat("hh:mm a").parse(dbDate);
	}

	public static String convertFromDate(Date date)
	{
		return new SimpleDateFormat("hh:mm a").format(date);
	}
}
