package net.esc20.txeis.EmployeeAccess.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.esc20.txeis.EmployeeAccess.domainobject.LeaveInfo;
import net.esc20.txeis.EmployeeAccess.domainobject.LeaveRequest;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.AbsenceReason;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.Frequency;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.LeaveType;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.LeaveUnitsConversion;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.PayFrequency;
import net.esc20.txeis.EmployeeAccess.service.LeaveRequestService;
import net.esc20.txeis.EmployeeAccess.service.LeaveService;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/leave/leaveUtilities")
public class LeaveUtilitiesController {

	private SimpleDateFormat dateTimeFormat = new SimpleDateFormat("MM-dd-yyyy hh:mmaa");
	private SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");

	@Autowired
	private LeaveRequestService leaveRequestService;
	
	@Autowired
	private LeaveService leaveService;

	@RequestMapping(method = RequestMethod.GET, value = "/getLeaveTypeNotes")
	public ModelAndView getLeaveTypeNotes(@RequestParam("leaveType") String leaveType) {
		ModelMap modelMap = new ModelMap();
		String notes = leaveRequestService.getLeaveTypeNotes(leaveType);
		modelMap.addAttribute("notes", notes);
		return new ModelAndView("jsonView", modelMap);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getLeaveBalances")
	public ModelAndView getLeaveBalances(@RequestParam("employeeNumber") String employeeNumber, @RequestParam("payFrequency") String payFrequency) {
		ModelMap modelMap = new ModelMap();
		Map<Frequency,List<LeaveInfo>> leaveInfoMap = leaveService.retrieveLeaveInfos(employeeNumber, false);
		List<LeaveInfo> leaveInfos = leaveInfoMap.get(Frequency.getFrequency(payFrequency));
		if (leaveInfos == null) {
			leaveInfos = new ArrayList<LeaveInfo>();
		}
		List<PayFrequency> payFrequencies = leaveRequestService.getUserPayFrequencies(employeeNumber);
		String payFrequencyDescription = payFrequency;
		for (PayFrequency payFrequencyRecord : payFrequencies) {
			if (payFrequencyRecord.getCode().trim().equals(payFrequency)){
				payFrequencyDescription = payFrequencyRecord.getDescription().trim();
				break;
			}
		}
		
		modelMap.addAttribute("leaveBalances", leaveInfos);
		modelMap.addAttribute("payFrequencyDescription",payFrequencyDescription);
		return new ModelAndView("jsonView", modelMap);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/adjustLeaveTypeAbsenceReason")
	public ModelAndView adjustLeaveTypeAbsenceReason(@RequestParam("leaveType") String leaveType,
			@RequestParam("absenceReason") String absenceReason,
			@RequestParam("payFrequency") String payFrequency, 
			@RequestParam("employeeNumber") String employeeNumber
			) {
		ModelMap modelMap = new ModelMap();
		List<LeaveType> leaveTypes = leaveRequestService.getLeaveTypes(payFrequency, employeeNumber, absenceReason);
		List<AbsenceReason> absenceReasons = leaveRequestService.getAbsenceReasons(payFrequency, employeeNumber, leaveType);

		modelMap.addAttribute("leaveTypes", leaveTypes);
		modelMap.addAttribute("absenceReasons", absenceReasons);
		
		return new ModelAndView("jsonView", modelMap);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/calculateLeaveUnitsRequested")
	public ModelAndView calculateLeaveUnitsRequested(@RequestBody Map<String,Object> requestBodyMap) {
		
		ModelMap modelMap = new ModelMap();
	    DecimalFormat df=new DecimalFormat("0.000");
		String leaveUnitsRequestedStr="0.000";
		
		String startDate = (String)requestBodyMap.get("startDate");
		String startTime = (String)requestBodyMap.get("startTime");
		String endDate = (String)requestBodyMap.get("endDate");
		String endTime = (String)requestBodyMap.get("endTime");
		// if the start & end date-times and the leave type have been specified then leaveHoursDailyStr, leaveUnitsRequested and leaveNumberDays will be computed
		// however, if both dates have been specified but one or both of the times have not been specified, then leaveHoursDailyStr will be used to compute leaveUnitsRequested and leaveNumberDays
		String leaveHoursDailyStr = (String)requestBodyMap.get("leaveHoursDailyStr");
		String leaveUnitsDorH = (String)requestBodyMap.get("leaveUnitsDorH");
		String workdayHoursStr = (String)requestBodyMap.get("workdayHoursStr");
		String mealBreakHoursStr = (String)requestBodyMap.get("mealBreakHoursStr");
		String leaveType = (String) requestBodyMap.get("leaveType");
		String payFrequency = (String) requestBodyMap.get("payFrequency");
		
		double leaveHoursDaily=-1.0;
		double workdayHours=-1.0;
		double mealBreakHours=-1.0;
		double totalHours=-1.0;
		
		String startDateTime = startDate + " " + startTime;
		String endDateTime = endDate + " " + endTime;
		Date fromDateObj = null;
		Date toDateObj = null;
		Date fromDateTimeObj = null;
		Date toDateTimeObj = null;
		Date fromTimeDateObj = null;
		Date toTimeDateObj = null;
		try {
			fromDateObj = dateFormat.parse(startDate);
			toDateObj = dateFormat.parse(endDate);
			leaveHoursDaily = Double.parseDouble(leaveHoursDailyStr);
			workdayHours = Double.parseDouble(workdayHoursStr);
			mealBreakHours = Double.parseDouble(mealBreakHoursStr);
			
			fromDateTimeObj = dateTimeFormat.parse(startDateTime);
			toDateTimeObj = dateTimeFormat.parse(endDateTime);
			// the purpose of the next to objects is to check the times based on the same day
			fromTimeDateObj = dateTimeFormat.parse("08-17-2016 "+startTime);
			toTimeDateObj = dateTimeFormat.parse("08-17-2016 "+endTime);
		} catch (Exception e) {
				
		}
	
		int leaveNumberDays=0;
		if (fromDateObj!=null && toDateObj!=null && fromDateObj.compareTo(toDateObj)<=0) {
			// both dates specified ... compute number of days requested
		    leaveNumberDays = ((int)((toDateObj.getTime() - fromDateObj.getTime())/(1000*60*60*24))) + 1;
		    			
		    if (leaveType.trim().length()>0) {
		    	
			    // get conversion arrays
				List<LeaveUnitsConversion> hoursToDaysConversionList = leaveRequestService.getHoursToDaysConversionRecs(payFrequency, leaveType);
				LeaveUnitsConversion[] hoursToDaysConversionArray = new LeaveUnitsConversion[hoursToDaysConversionList.size()];
				hoursToDaysConversionArray = hoursToDaysConversionList.toArray(hoursToDaysConversionArray);
	
				List<LeaveUnitsConversion> minutesToHoursConversionList = leaveRequestService.getMinutesToHoursConversionRecs(payFrequency, leaveType);
				LeaveUnitsConversion[] minutesToHoursConversionArray = new LeaveUnitsConversion[minutesToHoursConversionList.size()];
				minutesToHoursConversionArray = minutesToHoursConversionList.toArray(minutesToHoursConversionArray);
	
			    
			    boolean computeLeaveUnitsRequested=false;
				if (fromDateTimeObj!=null && toDateTimeObj!=null && fromTimeDateObj!=null && toTimeDateObj!=null) {
					if (fromTimeDateObj.compareTo(toTimeDateObj)<0) {
						// both times specified correctly ... compute leaveHoursDaily ... the number of hours requested per day
						computeLeaveUnitsRequested = true;
									
						int minutesPerDay = (int)((toTimeDateObj.getTime() - fromTimeDateObj.getTime())/(1000*60));
						int hours = minutesPerDay/60 ;
						int remainingMinutes = minutesPerDay % 60;
						if (((hours == 5 && remainingMinutes >0) || (hours > 5)) && mealBreakHours>0) {
							// subtract off lunch
							int mealBreakMinutes = (int) Math.round(mealBreakHours * 60.0);
							minutesPerDay -= mealBreakMinutes;
							// recompute hours and remainingMinutes
							hours = minutesPerDay/60 ;
							remainingMinutes = minutesPerDay % 60;
						}
						// convert minutes to fraction of an hour
						double fraction= -1.0;
						if (remainingMinutes==0) {
							fraction = 0.0;
						} else if (leaveUnitsDorH.equals("H")) {
							for (int i=0; i < minutesToHoursConversionArray.length; i++) {
								LeaveUnitsConversion rec = minutesToHoursConversionArray[i];
								if (remainingMinutes >= rec.getFromUnit().intValue() && remainingMinutes <= rec.getToUnit().intValue()) {
									fraction = rec.getFractionalAmount().doubleValue();
									break;
								}
							}
						}
						if (fraction < 0.0) {					
							// ... if no conversion rec, could just do a simple computation: fraction = ((double)remainingMinutes) / 60.0;
							// no conversion rec found... bump up to next whole hour if units is hours
							// or compute fraction of an hour if units is days
							if (leaveUnitsDorH.equals("H")) {
								fraction = 1.0;
							} else {
								fraction = remainingMinutes / 60.0;
							}
						}
						leaveHoursDaily = ((double)hours) + fraction;
						leaveHoursDailyStr = df.format(leaveHoursDaily);
					} else {
						// times are not in the right order
						// reset the leaveHoursDaily to zero
					    leaveHoursDaily = 0.0;
					    leaveHoursDailyStr = df.format(leaveHoursDaily);
					}
				} else if (leaveHoursDaily >= 0) {
					// one or both times unspecified...
					// compute leaveUnitsRequested from input leaveHoursDaily passed in without altering it
					computeLeaveUnitsRequested = true;
				} 
	
				if (computeLeaveUnitsRequested) {
	
					if (leaveUnitsDorH.equals("H")) {
						// hours
						totalHours = leaveHoursDaily * leaveNumberDays;
						leaveUnitsRequestedStr = df.format(totalHours);
					} else if ((leaveUnitsDorH.equals("D")) && (workdayHours > 0)) {
						// days ... 
						// use the conversion table entries for hours/days 
						// in theory, leave requested per day could exceed the one workday day
						// for example, if a workday is defined as 8 hours and leave requested for a day is 12 hours ... 
						// this would be 1.5 workdays requested for the day
						double leaveDaysDaily = Math.floor(leaveHoursDaily / workdayHours); // compute total whole days requested per day 
						double remainingHours = leaveHoursDaily - (leaveDaysDaily * workdayHours); // in most cases, leaveDaysDaily will be zero
					    	
						// convert fraction part of day based on conversion array
						double fraction= -1.0;
				    	if (remainingHours == 0.0) {
				    		fraction = 0.0;
				    	} else {
				    		for (int i=0; i < hoursToDaysConversionArray.length; i++) {
								LeaveUnitsConversion rec = hoursToDaysConversionArray[i];
								// assume list of conversion recs are sorted by toUnit ascending
								if (remainingHours <= rec.getToUnit().doubleValue()) {
									fraction = rec.getFractionalAmount().doubleValue();
									break;
								}
							}
				    	}
	
						double totalDays = 0.0;
						if (fraction < 0.0) {
							// ... if no conversion rec, could just do a simple computation: fraction = remainingHours / workdayHours;
							// no conversion rec found... bump up to next whole unit
							fraction = 1.0;
						}
						leaveDaysDaily += fraction;
						totalDays = leaveDaysDaily * leaveNumberDays;
						leaveUnitsRequestedStr = df.format(totalDays);
					}
				}
		    }
		}
		
		modelMap.addAttribute("leaveHoursDaily", leaveHoursDailyStr);
		modelMap.addAttribute("leaveUnitsRequested", leaveUnitsRequestedStr);
		modelMap.addAttribute("leaveNumberDays",  Integer.toString(leaveNumberDays) );
		
		return new ModelAndView("jsonView", modelMap);
	}

	
	@RequestMapping(method = RequestMethod.POST, value = "/calculateLeaveUnitsRequestedFromDaily")
	public ModelAndView calculateLeaveUnitsRequestedFromDailyHours(@RequestBody Map<String,Object> requestBodyMap) {

		ModelMap modelMap = new ModelMap();
		String leaveUnitsRequestedString="0.000";
	    DecimalFormat df=new DecimalFormat("0.000");
	    
		String startDate = (String)requestBodyMap.get("startDate");
		String endDate = (String)requestBodyMap.get("endDate");
		String leaveHoursDailyStr = (String)requestBodyMap.get("leaveHoursDailyStr");
		String leaveUnitsDorH = (String)requestBodyMap.get("leaveUnitsDorH");
		String workdayHoursStr = (String)requestBodyMap.get("workdayHoursStr");
		String leaveType = (String) requestBodyMap.get("leaveType");
		String payFrequency = (String) requestBodyMap.get("payFrequency");

		Date fromDateObj = null;
		Date toDateObj = null;
		double leaveHoursDaily = -1.0;
		double workdayHours = -1.0;
		int leaveNumberDays = 0;
		try {
			fromDateObj = dateFormat.parse(startDate);
			toDateObj = dateFormat.parse(endDate);
			leaveHoursDaily = Double.parseDouble(leaveHoursDailyStr);
			workdayHours = Double.parseDouble(workdayHoursStr);
		} catch (Exception e) {
				
		}
	
		if (fromDateObj!=null && toDateObj!=null) {
			// verify the start date/time precedes the end date date/time ... and the start time precedes the end time
			if ((fromDateObj.compareTo(toDateObj)<= 0)) {
			    leaveNumberDays = ((int)((toDateObj.getTime() - fromDateObj.getTime())/(1000*60*60*24))) + 1;
			    if (leaveHoursDaily>0 && leaveType.trim().length()>0) {
			    	if (leaveUnitsDorH.equals("H")) {
			    		// hours
				    	double totalHours = leaveHoursDaily * leaveNumberDays;
			    		leaveUnitsRequestedString = df.format(totalHours);
			    	} else if ((leaveUnitsDorH.equals("D")) && (workdayHours > 0)) {
			    		// days
					    // get conversion array for hours-to-days
			    		List<LeaveUnitsConversion> hoursToDaysConversionList = leaveRequestService.getHoursToDaysConversionRecs(payFrequency, leaveType);
						LeaveUnitsConversion[] hoursToDaysConversionArray = new LeaveUnitsConversion[hoursToDaysConversionList.size()];
					    hoursToDaysConversionArray = hoursToDaysConversionList.toArray(hoursToDaysConversionArray);
			    		
				    	// use the conversion table entries for hours/days 
						// in theory, leave requested per day could exceed the one workday day
						// for example, if a workday is defined as 8 hours and leave requested for a day is 12 hours ... 
						// this would be 1.5 workdays requested for the day
						double leaveDaysDaily = Math.floor(leaveHoursDaily / workdayHours); // compute total whole days requested per day 
						double remainingHours = leaveHoursDaily - (leaveDaysDaily * workdayHours); // in most cases, leaveDaysDaily will be zero

						double fraction= -1.0;
				    	if (remainingHours == 0.0) {
				    		fraction = 0.0;
				    	} else {
				    		// convert fraction part of day based on conversion array
				    		for (int i=0; i < hoursToDaysConversionArray.length; i++) {
								LeaveUnitsConversion rec = hoursToDaysConversionArray[i];
								// assume list of conversion recs are sorted by toUnit ascending
								if (remainingHours <= rec.getToUnit().doubleValue()) {
									fraction = rec.getFractionalAmount().doubleValue();
									break;
								}
							}
				    	}
						double totalDays = 0.0;
						if (fraction < 0.0) {
//							fraction = remainingHours / workdayHours;
							// no conversion rec found... bump up to next whole unit
							fraction = 1.0;
						}
						leaveDaysDaily += fraction;
						totalDays = leaveDaysDaily * leaveNumberDays;
						leaveUnitsRequestedString = df.format(totalDays);
			    	}
			    }
			}
		}
		
		modelMap.addAttribute("leaveUnitsRequested", leaveUnitsRequestedString);
		modelMap.addAttribute("leaveNumberDays",  Integer.toString(leaveNumberDays) );
		
		return new ModelAndView("jsonView", modelMap);
	}
	
}
