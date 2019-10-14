package com.esc20.util;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.esc20.nonDBModels.PayDate;

public class DateUtil {
	
	public static String getLatestYear(List<String> years)
	{
		if(years.size() > 0)
		{
			String latest = years.get(0);
			
			for(String year : years)
			{
				if(Integer.parseInt(year) > Integer.parseInt(latest))
				{
					latest = year;
				}
			}
			
			return latest;
		}
		
		return null;
	}
	
	public PayDate getLatestPayDate(List<PayDate> payDates)
	{
		if(payDates.size() > 0)
		{
			PayDate latest = payDates.get(0);
			String tempDateString = StringUtil.left(latest.getDateFreq(), latest.getDateFreq().length()-1);
			Date latestDate = DateUtil.getDate(tempDateString, "yyyyMMdd");
			
			for(PayDate payDate : payDates)
			{
				tempDateString = StringUtil.left(payDate.getDateFreq(), payDate.getDateFreq().length()-1);
				Date tempDate = DateUtil.getDate(tempDateString, "yyyyMMdd");
				if(tempDate.getTime() >  latestDate.getTime())
				{
					latest = payDate;
					latestDate = tempDate;
				}
			}
			
			return latest;
		}
		
		return null;
	}
	
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
	
    public static int differentDays(Date date1,Date date2)
    {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
       int day1= cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);
        
        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if(year1 != year2)
        {
            int timeDistance = 0 ;
            for(int i = year1 ; i < year2 ; i ++)
            {
                if(i%4==0 && i%100!=0 || i%400==0)       
                {
                    timeDistance += 366;
                }
                else
                {
                    timeDistance += 365;
                }
            }
            
            return timeDistance + (day2-day1) ;
        }
        else
        {
            return day2-day1;
        }
    }
    public static Date getUTCTime() {
    	return getUTCTime(null);
    }
    //get current UAT time as of GMT 0
    public static Date getUTCTime(Date localTime) {
    	Calendar cal = Calendar.getInstance();
    	if(localTime!=null)
    		cal.setTime(localTime);
//    	int zoneOffset = cal.get(java.util.Calendar.ZONE_OFFSET);  
//    	int dstOffset = cal.get(java.util.Calendar.DST_OFFSET);  
//    	cal.add(java.util.Calendar.MILLISECOND, -(zoneOffset + dstOffset));
    	return cal.getTime();
    }
    
    //get local time relates to GMT
    public static Date getLocalTime(Date GMTTime) {
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(GMTTime);
//    	int zoneOffset = cal.get(java.util.Calendar.ZONE_OFFSET);  
//    	int dstOffset = cal.get(java.util.Calendar.DST_OFFSET);  
//    	cal.add(java.util.Calendar.MILLISECOND, (zoneOffset + dstOffset));
    	return cal.getTime();
    }
    
    public static Date minusDays(Date localDate, int days) {
    	//Date dNow = new Date();  
    	Date dBefore = new Date();
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(localDate);
    	calendar.add(Calendar.DAY_OF_MONTH, -days);  
    	dBefore = calendar.getTime();  
    	
    	return dBefore;
    }
}
