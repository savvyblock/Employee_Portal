package net.esc20.txeis.EmployeeAccess.conversion;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RscccDate implements Serializable
{
	private static Log log = LogFactory.getLog(RscccDate.class);
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


	public static Date convertToDate(String dbDate)
	{
		try {
			return new SimpleDateFormat("yyyyMMdd").parse(dbDate);
		} catch (ParseException ex) {
			log.debug("Could not parse a date: " + dbDate, ex);
			return null;
		}
	}

	public static String convertFromDate(Date date)
	{
		if (date == null) {
			return null;
		} else {
			return String.format("%1$tY%1$tm%1$td", date);
		}
	}
}
