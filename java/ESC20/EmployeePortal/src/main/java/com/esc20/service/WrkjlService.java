package com.esc20.service;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esc20.dao.WrkjlDao;
import com.esc20.model.BeaEmpWrkJrnl;
import com.esc20.nonDBModels.BeaEmpWrkJrnlId;

@Service
public class WrkjlService {

	@Autowired
	private WrkjlDao wrkjlDao;
	
	public List<BeaEmpWrkJrnl> getWrkjl(String empNbr){
		return wrkjlDao.getWrkjl(empNbr);
	}
	
	@SuppressWarnings("deprecation")
	public void saveWrkjl(BeaEmpWrkJrnl param) {
		
		//cal week start date based on the entered date

		LocalDate begWd = calStartDate(param.getId().getWrkDate());

		param.setBegWrkWkDt(begWd.getYear()
					+""+
			(String.valueOf(begWd.getMonthValue()).length()== 1? "0"+begWd.getMonthValue():begWd.getMonthValue() )
					+""+
			(String.valueOf(begWd.getDayOfMonth()).length() == 1? "0"+begWd.getDayOfMonth():begWd.getDayOfMonth() ));
		//cal total time diff
		//from
		param.setTotTm(calTotalTime(param));
		wrkjlDao.save(param);
	}
	
	private String calTotalTime(BeaEmpWrkJrnl param) {
		Calendar calendarFrom = new GregorianCalendar();
		calendarFrom.set(Calendar.HOUR, Integer.valueOf(param.getStartHour()));

		calendarFrom.set(Calendar.MINUTE, Integer.valueOf(param.getStartMinute()));
		if(param.getStartAmOrPm().equalsIgnoreCase("AM")) {
			calendarFrom.set(Calendar.AM_PM, Calendar.AM);
		}else {
			calendarFrom.set(Calendar.AM_PM, Calendar.PM);
		}
		//to
		Calendar calendarto = new GregorianCalendar();
		calendarto.set(Calendar.HOUR, Integer.valueOf(param.getEndHour()));

		calendarto.set(Calendar.MINUTE, Integer.valueOf(param.getEndMinute()));
		if(param.getEndAmOrPm().equalsIgnoreCase("AM")) {
			calendarto.set(Calendar.AM_PM, Calendar.AM);
		}else {
			calendarto.set(Calendar.AM_PM, Calendar.PM);
		}
		long difMilSec = calendarto.getTimeInMillis() - calendarFrom.getTimeInMillis() ;
		
		

		long min = TimeUnit.MILLISECONDS.toMinutes(difMilSec);
		return fromTotMinToHhMm(min);
	}
	
	   private String fromTotMinToHhMm(long time)
	   {
	           String finalTime = "";
	           long hour = (time%(24*60)) / 60;
	           long minutes = (time%(24*60)) % 60;

	           finalTime = String.format("%02d:%02d",
	                   TimeUnit.HOURS.toHours(hour) ,
	                   TimeUnit.MINUTES.toMinutes(minutes));
	           return finalTime;
	       }

	private LocalDate calStartDate(String wrkDate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		LocalDate date = LocalDate.parse(wrkDate, formatter);
		
		String sWrkWeek = wrkjlDao.getOptionsStartDay();
		
		
		return date.with( TemporalAdjusters.previous( DayOfWeek.of(Integer.parseInt(sWrkWeek.toString())) ) ) ;
		
	}

	public Object getMaxSec(String date,String empNbr) {
		return wrkjlDao.getMaxSeq(date, empNbr);
	}


}
