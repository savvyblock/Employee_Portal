package net.esc20.txeis.EmployeeAccess.util;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	
	private static long msInDay = 24 * 60 * 60 * 1000;
	
	/**
	 * @param input
	 * @param format
	 * @return
	 */
	public static Date getDate(String input, String format) {
		
		Date result = null;
		
		if (input != null && format != null) { 
			DateFormat df = new SimpleDateFormat(format);
			df.setLenient(false);
			
			ParsePosition pp = new ParsePosition(0);
			
			result = df.parse(input, pp);
			
			if (pp.getIndex() < format.length()) {
				result = null;
			}
		}
		return result;
	}
	
	/**
	 * @param input
	 * @param format
	 * @return
	 */
	public static Date getDate(int year, int month, int date) {
		
		Calendar checkDate = Calendar.getInstance();
		checkDate.set(year, month - 1, date, 0, 0, 0);
		
		return checkDate.getTime();
	}
	
	/**
	 * @param input
	 * @param format
	 * @return
	 */
	public static String getString(Date input, String format) {
		
		String result = "";
		
		if (input != null && format != null) {
			DateFormat df = new SimpleDateFormat(format);
			df.setLenient(false);
	
			result = df.format(input);
		}

		return result;
	}
	
	/**
	 * @param date
	 * @return
	 */
	public static String formatDate(String date) {
		return DateUtil.formatDate(date, "yyyyMMdd", "MMMM dd yyyy");
	}
	
	/**
	 * @param date
	 * @param format
	 * @return
	 */
	public static String formatDate(String date, String fromformat, String toformat) {
		return DateUtil.getString(DateUtil.getDate(date, fromformat), toformat);
	}
	
	public static int daysAfter(Date date1, Date date2) {
	    // Calculate the difference in milliseconds
	    long differenceMs = Math.abs(date2.getTime() - date1.getTime());
	    
	    // Convert back to days and return
	    return Math.round(differenceMs/msInDay);
	}
	
	public static int  monthsDiff(Date date1, Date date2) {
		 Calendar calendar1 = Calendar.getInstance();

         calendar1.setTime(date1);

         Calendar calendar2 = Calendar.getInstance();

         calendar2.setTime(date2);

         return Math.abs(calendar2.get(Calendar.MONTH) - calendar1.get(Calendar.MONTH) + (calendar2.get(Calendar.YEAR) - calendar1.get(Calendar.YEAR)) * 12);
	}
	
	public static boolean isDate(String input, String format) {

		if (input == null || format == null || format.trim().length() <= 0) {
			return false;
		}

		DateFormat df = new SimpleDateFormat(format);
		df.setLenient(false);

		ParsePosition pp = new ParsePosition(0);

		try {
			@SuppressWarnings("unused")
			Date validDate = df.parse(input, pp);
			if (pp.getIndex() < format.length()) {
				return false;
			}

		}
		catch (Exception e) {
			return false;
		}

		return true;
	}
}
