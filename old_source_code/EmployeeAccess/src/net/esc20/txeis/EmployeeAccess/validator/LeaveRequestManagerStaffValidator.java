package net.esc20.txeis.EmployeeAccess.validator;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.esc20.txeis.EmployeeAccess.domain.LeaveRequestManageStaffCommand;
import net.esc20.txeis.EmployeeAccess.domainobject.LeaveInfo;
import net.esc20.txeis.EmployeeAccess.domainobject.LeaveRequest;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.Frequency;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.LeaveType;
import net.esc20.txeis.EmployeeAccess.service.LeaveRequestService;
import net.esc20.txeis.EmployeeAccess.service.LeaveService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class LeaveRequestManagerStaffValidator implements Validator {

	private SimpleDateFormat dateTimeFormat = new SimpleDateFormat("MM-dd-yyyy hh:mmaa");
	private SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
	
	@Autowired
	private LeaveRequestService leaveRequestService;

	@Autowired
	private LeaveService leaveService;

	@Override
	public boolean supports(Class<?> obj) {
		return LeaveRequestManageStaffCommand.class.equals(obj);
	}

	@Override
	public void validate(Object object, Errors errors) {
		LeaveRequestManageStaffCommand command = (LeaveRequestManageStaffCommand) object;
		
		List<LeaveType> leaveTypes = leaveRequestService.getLeaveTypes(command.getSelectedPayFrequencyCode(), command.getSelectedDirectReportEmployeeNumber(), "");
		HashMap<String, BigDecimal> totalRequestedByLeaveTypeHash = new HashMap<String, BigDecimal>();
		// get employee's current leave balances
		Map<Frequency,List<LeaveInfo>> leaveInfoMap = leaveService.retrieveLeaveInfos(command.getSelectedDirectReportEmployeeNumber(), false);
		List<LeaveInfo> leaveInfos = leaveInfoMap.get(Frequency.getFrequency(command.getSelectedPayFrequencyCode()));
		if (leaveInfos == null) {
			leaveInfos = new ArrayList<LeaveInfo>();
		}
				
		boolean creatingNewRequests=false;
		if (command.getEditLeaveRequests().size()>0 && command.getEditLeaveRequests().get(0).getId()==0) {
			creatingNewRequests = true;
		} else {
			// leave requests are being edited; adjust the leave balances
			for (LeaveRequest editRequest : command.getEditLeaveRequests()) {		
				// get the leave info record corresponding to this request and adjust it's beginning balance
				for (LeaveInfo leaveInfo : leaveInfos) {
					if (leaveInfo.getType().getCode().trim().equals(editRequest.getLeaveType())) {
						// add the request value of the saved request back into the leave info for the corresponding type
						for (LeaveRequest request : command.getLeaveRequests()) {
							if (request.getId()==editRequest.getId()) {
								double adjustedBeginningBalance = leaveInfo.getBeginBalance().doubleValue()+request.getLeaveRequested().doubleValue();
								leaveInfo.setBeginBalance(new BigDecimal(adjustedBeginningBalance).setScale(3, BigDecimal.ROUND_HALF_UP));
								break;
							}
						}
						break;
					}
				}
			}							
		}
		
		HashMap<Integer,Date> fromDateHash = new HashMap<Integer,Date>();
		HashMap<Integer,Date> toDateHash = new HashMap<Integer,Date>();
		HashMap<Integer,Integer> numberDaysHash = new HashMap<Integer,Integer>();
		List<LeaveRequest> savedRequests =  null;
		// set up the list of saved leave requests to validate against the leave periods of the requests being created/edited
		savedRequests = leaveRequestService.getEmployeeLeaveRequestsPeriods(command.getSelectedDirectReportEmployeeNumber());
		// exclude any that are currently being edited
		for (LeaveRequest editRequest : command.getEditLeaveRequests()) {
			if (editRequest.getId() != 0) {
				int index = savedRequests.indexOf(editRequest);
				if (index >= 0) {
					// a disapproved request will not be in the savedRequests list ... index would be -1 in that case
					savedRequests.remove(index);
				}
			}
		}

		int requestIndex = 0;
		int requestCount=0;
		for (LeaveRequest leaveRequest : command.getEditLeaveRequests()) {
			// if the user is creating requests (id=0) and the request is empty ... skip
			if (!(leaveRequest.isEmpty() && leaveRequest.getId()==0)) {
				requestCount++;

				// check dates
				Date fromDateObj=null;
				Date toDateObj=null;
				Date fromTimeDateObj=null;
				Date toTimeDateObj=null;
	
				int fromHour=-1;
				int fromMinute=-1;
				int toHour=-1;
				int toMinute=-1;
				boolean validateOverlappingIntervals=true;
				
				String fromDate = (leaveRequest.getFromDate().equals("  -  -    ") ? "" : leaveRequest.getFromDate().trim());
				if (fromDate.length() == 0) {
					errors.rejectValue("editLeaveRequests["+requestIndex+"].fromDate", "-1", "Select a Start Date.");
				} else {
					try {
						fromDateObj = dateFormat.parse(fromDate);
					} catch (Exception e) {
						
					}
					if (fromDateObj==null) {
						errors.rejectValue("editLeaveRequests["+requestIndex+"].fromDate", "-1", "Enter Start Date in the format MM-DD-YYYY.");
					}
				}
				if (leaveRequest.getFromHour()==null || leaveRequest.getFromHour().trim().length() == 0) {
					errors.rejectValue("editLeaveRequests["+requestIndex+"].fromHour", "-1", "Enter a Start Hour.");
				} else {
					try {
						fromHour = Integer.parseInt(leaveRequest.getFromHour());
					} catch (Exception e) {
						fromHour = -1;
					}
					if (fromHour < 0 || fromHour==0 || fromHour>12) {
						errors.rejectValue("editLeaveRequests["+requestIndex+"].fromHour", "-1", "Enter a Start Hour from 01 - 12.");
					}  
				}
				if (leaveRequest.getFromMinute()==null || leaveRequest.getFromMinute().trim().length() == 0) {
					errors.rejectValue("editLeaveRequests["+requestIndex+"].fromMinute", "-1", "Enter a Start Minute.");
				} else {
					try {
						fromMinute = Integer.parseInt(leaveRequest.getFromMinute());
					} catch (Exception e) {
						fromMinute = -1;
					}
					if (fromMinute < 0 || fromMinute>59) {
						errors.rejectValue("editLeaveRequests["+requestIndex+"].fromMinute", "-1", "Enter a Start Minute from 00 - 59.");
					}  
				}
	
				String toDate = (leaveRequest.getToDate().equals("  -  -    ") ? "" : leaveRequest.getToDate().trim().trim());
				if (toDate.length() == 0) {
					errors.rejectValue("editLeaveRequests["+requestIndex+"].toDate", "-1", "Select an End Date.");
				} else {
					try {
						toDateObj = dateFormat.parse(toDate);
					} catch (Exception e) {
					}
					if (toDateObj==null) {
						errors.rejectValue("editLeaveRequests["+requestIndex+"].toDate", "-1", "Enter End Date in the format MM-DD-YYYY.");
					}				
				}
				if (leaveRequest.getToHour()==null || leaveRequest.getToHour().trim().length() == 0) {
					errors.rejectValue("editLeaveRequests["+requestIndex+"].toHour", "-1", "Enter an End Hour.");
				} else {			
					try {
						toHour = Integer.parseInt(leaveRequest.getToHour());
					} catch (Exception e) {
						toHour = -1;
					}
					if (toHour < 0 || toHour==0 || toHour>12) {
						errors.rejectValue("editLeaveRequests["+requestIndex+"].toHour", "-1", "Enter an End Hour from 01 - 12.");
					}  
				}
				if (leaveRequest.getToMinute()==null || leaveRequest.getToMinute().trim().length() == 0) {
					errors.rejectValue("editLeaveRequests["+requestIndex+"].toMinute", "-1", "Enter an End Minute.");
				} else {
					try {
						toMinute = Integer.parseInt(leaveRequest.getToMinute());
					} catch (Exception e) {
						toMinute = -1;
					}
					if (toMinute < 0 || toMinute>59) {
						errors.rejectValue("editLeaveRequests["+requestIndex+"].toMinute", "-1", "Enter an End Minute from 00 - 59.");
					}  
				}
				
				if (fromDateObj!=null && toDateObj!=null) { 
					if (fromDateObj.compareTo(toDateObj)>0) {
						errors.rejectValue("editLeaveRequests["+requestIndex+"].fromDate", "-1", "Start Date may not come after End Date.");
						errors.rejectValue("editLeaveRequests["+requestIndex+"].toDate", "-1", "End Date may not come before Start Date.");
						validateOverlappingIntervals = false;
					}
				} else {
					validateOverlappingIntervals = false;
				}
				
				// check that the times do not cross over into the next day
				if (fromHour>0 && fromHour<=12 && fromMinute>=0 && fromMinute<=59 && toHour>0 && toHour<=12 && toMinute>=0 && toMinute<=59) {
					try {
						fromTimeDateObj = dateTimeFormat.parse("08-18-2016 "+leaveRequest.getFromHour().trim()+":"+leaveRequest.getFromMinute().trim()+leaveRequest.getFromAmPm().trim());
						toTimeDateObj = dateTimeFormat.parse("08-18-2016 "+leaveRequest.getToHour().trim()+":"+leaveRequest.getToMinute().trim()+leaveRequest.getToAmPm().trim());
					} catch (Exception e) {
						
					}
					if (fromTimeDateObj!=null && toTimeDateObj!=null) {
						if (fromTimeDateObj.compareTo(toTimeDateObj)==0) {
							errors.rejectValue("editLeaveRequests["+requestIndex+"].fromHour", "-1", "Start and End times must be different.");
							errors.rejectValue("editLeaveRequests["+requestIndex+"].fromMinute", "-1", "Start and End times must be different.");
							errors.rejectValue("editLeaveRequests["+requestIndex+"].fromAmPm", "-1", "Start and End times must be different.");
							errors.rejectValue("editLeaveRequests["+requestIndex+"].toHour", "-1", "Start and End times must be different.");
							errors.rejectValue("editLeaveRequests["+requestIndex+"].toMinute", "-1", "Start and End times must be different.");
							errors.rejectValue("editLeaveRequests["+requestIndex+"].toAmPm", "-1", "Start and End times must be different.");
							validateOverlappingIntervals = false;
						} else if (fromTimeDateObj.compareTo(toTimeDateObj)>0) {
							errors.rejectValue("editLeaveRequests["+requestIndex+"].toHour", "-1", "End time may not cross into the next day.");
							errors.rejectValue("editLeaveRequests["+requestIndex+"].toMinute", "-1", "End time may not cross into the next day.");
							errors.rejectValue("editLeaveRequests["+requestIndex+"].toAmPm", "-1", "End time may not cross into the next day.");
							validateOverlappingIntervals = false;
						}
					} else {
						validateOverlappingIntervals = false;
					}
				} else {
					validateOverlappingIntervals = false;
				}
					
				if (validateOverlappingIntervals) {
					Date fromDateFromTimeObj=null;
					Date fromDateToTimeObj=null;
					try {
						fromDateFromTimeObj = dateTimeFormat.parse(leaveRequest.getFromDate()+" "+leaveRequest.getFromHour().trim()+":"+
								leaveRequest.getFromMinute().trim()+leaveRequest.getFromAmPm().trim());
						fromDateToTimeObj = dateTimeFormat.parse(leaveRequest.getFromDate()+" "+leaveRequest.getToHour().trim()+":"+
								leaveRequest.getToMinute().trim()+leaveRequest.getToAmPm().trim());
						leaveRequest.setFromDateString(dateFormat.format(leaveRequest.getFromDate()));
					} catch (Exception e) {
						
					}
					
					// check if overlapping ranges
					boolean validDateRange = true;
					for (Integer key : fromDateHash.keySet()) {
						boolean overlapping = leaveRequestService.isLeavePeriodsOverlapping(fromDateHash.get(key), toDateHash.get(key), numberDaysHash.get(key), 
								fromDateFromTimeObj, fromDateToTimeObj, leaveRequest.getRequestNumberDays());
					    if (overlapping) {
							errors.rejectValue("editLeaveRequests["+requestIndex+"].fromDate", "-1", "Date/Time period overlaps with a prior one.");	
							errors.rejectValue("editLeaveRequests["+requestIndex+"].fromHour", "-1", "Date/Time period overlaps with a prior one.");	
							errors.rejectValue("editLeaveRequests["+requestIndex+"].fromMinute", "-1", "Date/Time period overlaps with a prior one.");	
							errors.rejectValue("editLeaveRequests["+requestIndex+"].fromAmPm", "-1", "Date/Time period overlaps with a prior one.");	
							errors.rejectValue("editLeaveRequests["+requestIndex+"].toDate", "-1", "Date/Time period overlaps with a prior one.");	
							errors.rejectValue("editLeaveRequests["+requestIndex+"].toHour", "-1", "Date/Time period overlaps with a prior one.");	
							errors.rejectValue("editLeaveRequests["+requestIndex+"].toMinute", "-1", "Date/Time period overlaps with a prior one.");	
							errors.rejectValue("editLeaveRequests["+requestIndex+"].toAmPm", "-1", "Date/Time period overlaps with a prior one.");								
							validDateRange = false;	
							break;
					    }
					}
					if (validDateRange) {
					    // check if there is an overlap with a previously saved leave request other than any being edited
						for (LeaveRequest savedRequest : savedRequests) {
							try {
								toDateObj = dateTimeFormat.parse(savedRequest.getFromDateString()+" "+savedRequest.getToTimeString());	
							} catch (Exception e) {
								
							}
							boolean overlapping = false;
							if (toDateObj != null) {
								overlapping = leaveRequestService.isLeavePeriodsOverlapping(savedRequest.getFromDateTime(), toDateObj, savedRequest.getRequestNumberDays(), 
									fromDateFromTimeObj, fromDateToTimeObj, leaveRequest.getRequestNumberDays());
							}
						    if (overlapping) {
								errors.rejectValue("editLeaveRequests["+requestIndex+"].fromDate", "-1", "Date/Time period overlaps with a submitted request.");	
								errors.rejectValue("editLeaveRequests["+requestIndex+"].fromHour", "-1", "Date/Time period overlaps with a submitted request.");	
								errors.rejectValue("editLeaveRequests["+requestIndex+"].fromMinute", "-1", "Date/Time period overlaps with a submitted request.");	
								errors.rejectValue("editLeaveRequests["+requestIndex+"].fromAmPm", "-1", "Date/Time period overlaps with a submitted request.");	
								errors.rejectValue("editLeaveRequests["+requestIndex+"].toDate", "-1", "Date/Time period overlaps with a submitted request.");	
								errors.rejectValue("editLeaveRequests["+requestIndex+"].toHour", "-1", "Date/Time period overlaps with a submitted request.");	
								errors.rejectValue("editLeaveRequests["+requestIndex+"].toMinute", "-1", "Date/Time period overlaps with a submitted request.");	
								errors.rejectValue("editLeaveRequests["+requestIndex+"].toAmPm", "-1", "Date/Time period overlaps with a submitted request.");								
								validDateRange = false;	
								break;
						    }					
						}
					}
					if (validDateRange) {
				    	Integer integerObj = new Integer(requestIndex);
				    	fromDateHash.put(integerObj, fromDateFromTimeObj);
				    	toDateHash.put(integerObj, fromDateToTimeObj);		
				    	numberDaysHash.put(integerObj,  new Integer(leaveRequest.getRequestNumberDays()));
					}
					
				}

				LeaveType selectedLeaveTypeRecord=null;
				if (leaveRequest.getLeaveType().length() == 0) {
					errors.rejectValue("editLeaveRequests["+requestIndex+"].leaveType", "-1", "Select a Leave Type.");
				} else {
					// get the leave type record selected
					for (LeaveType leaveTypeRecord : leaveTypes) {
						if (leaveTypeRecord.getLvType().equals(leaveRequest.getLeaveType())) {
							selectedLeaveTypeRecord = leaveTypeRecord;
							break;
						}
					}
					// check if today's date is after the entry cutoff date, if any
					boolean leaveTypeError = false;
					if (selectedLeaveTypeRecord.getSubmissionDateCutoff() != null) {
						Date today=null;
						try {
							today = dateFormat.parse(dateFormat.format(new Date()));
						} catch (Exception e) {
							
						}
						if (today!=null && today.compareTo(selectedLeaveTypeRecord.getSubmissionDateCutoff())>0) {
							errors.rejectValue("editLeaveRequests["+requestIndex+"].leaveType", "-1", "Leave of type selected cannot be entered after "+dateFormat.format(selectedLeaveTypeRecord.getSubmissionDateCutoff()));
							leaveTypeError = true;
						}
					}

					// check if dates of leave requested fall after the annual cutoff date, if any
					if (leaveTypeError==false && selectedLeaveTypeRecord.getAbsenceDateCutoff() != null) {
						if ((fromDateObj!=null && fromDateObj.compareTo(selectedLeaveTypeRecord.getAbsenceDateCutoff()) > 0) ||
							(toDateObj!=null && toDateObj.compareTo(selectedLeaveTypeRecord.getAbsenceDateCutoff()) > 0)) {
							errors.rejectValue("editLeaveRequests["+requestIndex+"].leaveType", "-1", "Leave of type selected cannot be taken after "+dateFormat.format(selectedLeaveTypeRecord.getAbsenceDateCutoff()));
						} 
					}
				}
				
				if (leaveRequest.getAbsenceReason().length() == 0) {
					errors.rejectValue("editLeaveRequests["+requestIndex+"].absenceReason", "-1", "Select an Absence Reason.");
				}
				
				if (leaveRequest.getLeaveHoursDaily()==null || leaveRequest.getLeaveHoursDaily().compareTo(BigDecimal.ZERO)==0) {
					errors.rejectValue("editLeaveRequests["+requestIndex+"].leaveHoursDaily", "-1", "Enter a non-zero value.");
				} else if (leaveRequest.getLeaveHoursDaily().doubleValue() < 0) {
					errors.rejectValue("editLeaveRequests["+requestIndex+"].leaveHoursDaily", "-1", "Enter a positive value.");
				} else {
					if (leaveRequest.getLeaveRequested()==null || leaveRequest.getLeaveRequested().compareTo(BigDecimal.ZERO)==0) {
						errors.rejectValue("editLeaveRequests["+requestIndex+"].leaveHoursDaily", "-1", "Enter an Hours/Day value that produces a non-zero value for Total Requested.");
					} else if (selectedLeaveTypeRecord!=null && !selectedLeaveTypeRecord.isPostAgainstZeroBalance()) {
						// get the employee's current balance for the leave type selected 
						LeaveInfo leaveInfoRecord=null;
						for (LeaveInfo leaveInfo : leaveInfos) {
							if (leaveInfo.getType().getCode().trim().equals(leaveRequest.getLeaveType())) {
								leaveInfoRecord = leaveInfo;
								break;
							}
						}
						
						// verify the total requested does not exceed the employee's balance for the leave type
						double totalRequestedAccumulated=leaveRequest.getLeaveRequested().doubleValue();
						BigDecimal totalAccumulatedByLeaveType = totalRequestedByLeaveTypeHash.get(selectedLeaveTypeRecord.getLvType());
						if (totalAccumulatedByLeaveType!=null) {
							totalRequestedAccumulated += totalAccumulatedByLeaveType.doubleValue();
						} 
						if (leaveInfoRecord==null || totalRequestedAccumulated > leaveInfoRecord.getAvailableBalance().doubleValue()) {
							errors.rejectValue("editLeaveRequests["+requestIndex+"].leaveHoursDaily", "-1", "Total leave requested for this leave type exceeds available balance.");							
						}
						totalRequestedByLeaveTypeHash.put(selectedLeaveTypeRecord.getLvType(), new BigDecimal(totalRequestedAccumulated).setScale(3, BigDecimal.ROUND_HALF_UP));
					}
				}
				
				// validate a comment has been entered if the leave type requires one
				if (selectedLeaveTypeRecord!=null && selectedLeaveTypeRecord.isCommentRequired() && leaveRequest.getRequestComment().trim().length()==0) {
					errors.rejectValue("editLeaveRequests["+requestIndex+"].requestComment", "-1", "A comment must be entered for the leave type selected.");
				}
			}
			requestIndex++;
		}
		
		if (creatingNewRequests && requestCount==0) {
			errors.reject("-1","Create failed.  No data entered.");
		}
	}

}
