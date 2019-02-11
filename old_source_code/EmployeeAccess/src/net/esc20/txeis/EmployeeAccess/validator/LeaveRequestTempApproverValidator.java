package net.esc20.txeis.EmployeeAccess.validator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import net.esc20.txeis.EmployeeAccess.domain.LeaveRequestTemporaryApproversCommand;
import net.esc20.txeis.EmployeeAccess.domainobject.LeaveEmployeeData;
import net.esc20.txeis.EmployeeAccess.domainobject.LeaveTemporaryApprover;
import net.esc20.txeis.EmployeeAccess.service.LeaveRequestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class LeaveRequestTempApproverValidator implements Validator  {

	private SimpleDateFormat dateTimeFormat24hr = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");

	@Autowired
	private LeaveRequestService leaveRequestService;

	@Override
	public boolean supports(Class<?> obj) {
		return LeaveRequestTemporaryApproversCommand.class.equals(obj);
	}

	@Override
	public void validate(Object object, Errors errors) {
		// TODO Auto-generated method stub
		LeaveRequestTemporaryApproversCommand command = (LeaveRequestTemporaryApproversCommand) object;
		HashMap<Integer,Date> fromDateHash = new HashMap<Integer,Date>();
		HashMap<Integer,Date> toDateHash = new HashMap<Integer,Date>();
		
		int index = 0;
		for (LeaveTemporaryApprover temporaryApprover : command.getTemporaryApprovers()) {
			if (!temporaryApprover.isDeleteIndicated()) {
				boolean noTemporaryApproverEntered=false;
				if (temporaryApprover.getTemporaryApprover().getAutoCompleteString()==null || temporaryApprover.getTemporaryApprover().getAutoCompleteString().trim().length()==0) {
					noTemporaryApproverEntered = true;
				}
				boolean noFromDateEntered=false;
				String fromDate = (temporaryApprover.getFromDateString().equals("  -  -    ") ? "" : temporaryApprover.getFromDateString().trim());
				if (fromDate.length() == 0) {
					noFromDateEntered = true;
				}
				boolean noToDateEntered=false;
				String toDate = (temporaryApprover.getToDateString().equals("  -  -    ") ? "" : temporaryApprover.getToDateString().trim());
				if (toDate.length() == 0) {
					noToDateEntered = true;
				}
				
				if (temporaryApprover.getId()==0 && noTemporaryApproverEntered && noFromDateEntered && noToDateEntered) {
					// this is an added row with no data ... ignore it
					temporaryApprover.setIgnoreRow(true);
				} else {					// validate temporary approver emp number
					if (noTemporaryApproverEntered) {
						errors.rejectValue("temporaryApprovers["+index+"].temporaryApprover.autoCompleteString", "-1", "Enter or Select an Employee Number.");
					} else {
						// there is a value for autoCompleteString ... see if it needs to be validated
						LeaveEmployeeData empData=null;
						if (temporaryApprover.getTemporaryApprover().getEmployeeNumber()==null || temporaryApprover.getTemporaryApprover().getEmployeeNumber().trim().length()==0) {
							String userEnteredEmployeeNumber = temporaryApprover.getTemporaryApprover().getAutoCompleteString().trim();
							empData = leaveRequestService.getEmployeeData(userEnteredEmployeeNumber);
							if (empData==null) {
								errors.rejectValue("temporaryApprovers["+index+"].temporaryApprover.autoCompleteString", "-1", "Employee Number entered is invalid. Enter or Select an Employee Number.");
							} 
						} else {
							empData = leaveRequestService.getEmployeeData(temporaryApprover.getTemporaryApprover().getEmployeeNumber());
							if (empData == null) {
								errors.rejectValue("temporaryApprovers["+index+"].temporaryApprover.autoCompleteString", "-1", "Selected employee is not valid.  Please reselect.");
							}
						}
						if (empData != null) {
							String currentLevelSupervisorEmpNumber = command.getSupervisorChain().get(command.getSupervisorChainLevel()).getEmployeeNumber();
							// verify the employee is a TxEIS user
							if (empData.getUserLoginName()==null || empData.getUserLoginName().trim().length()==0) {
								errors.rejectValue("temporaryApprovers["+index+"].temporaryApprover.autoCompleteString", "-1", "The employee selected is not a TxEIS User. Please reselect.");
							} else if (empData.getEmployeeNumber().equals(currentLevelSupervisorEmpNumber)) {							
								errors.rejectValue("temporaryApprovers["+index+"].temporaryApprover.autoCompleteString", "-1", "The employee selected is the Supervisor. Please reselect.");
							} else {
								temporaryApprover.setTemporaryApprover(empData);
							}
						}
					}
					
					// validate dates
					boolean validDateRange = true;
					if (noFromDateEntered) {
						validDateRange = false;
						errors.rejectValue("temporaryApprovers["+index+"].fromDateString", "-1", "Select a From Date.");
					}
					
					if (noToDateEntered) {
						validDateRange = false;
						errors.rejectValue("temporaryApprovers["+index+"].toDateString", "-1", "Select a To Date.");
					}

					if (validDateRange) {
						StringBuffer fromDateTimeSB = new StringBuffer(fromDate.substring(0,2)).append("-")
								.append(fromDate.substring(3,5)).append("-").append(fromDate.substring(6,10))
								.append(" 00:00:00");
						StringBuffer toDateTimeSB = new StringBuffer(toDate.substring(0,2)).append("-")
								.append(toDate.substring(3,5)).append("-").append(toDate.substring(6,10))
								.append(" 23:59:59");
						Date fromDateObj = null;
						Date toDateObj = null;
						try {
							fromDateObj = dateTimeFormat24hr.parse(fromDateTimeSB.toString());
							toDateObj = dateTimeFormat24hr.parse(toDateTimeSB.toString());
						} catch (Exception e) {
								
						}
						if (fromDateObj == null) {
							errors.rejectValue("temporaryApprovers["+index+"].fromDateString", "-1", "From Date format invalid.");
							validDateRange = false;
						} else if (toDateObj == null) {
							errors.rejectValue("temporaryApprovers["+index+"].toDateString", "-1", "To Date format invalid.");
							validDateRange = false;
						} else  if (fromDateObj.compareTo(toDateObj) >= 0) {
							errors.rejectValue("temporaryApprovers["+index+"].fromDateString", "-1", "From Date may not follow To Date.");	
							validDateRange = false;
						} else {
							// check if overlapping ranges
							for (Integer key : fromDateHash.keySet()) {
							    Date rangeFromDateObj = fromDateHash.get(key);
							    Date rangeToDateObj = toDateHash.get(key);
							    if ((rangeFromDateObj.compareTo(toDateObj) < 0) && (rangeToDateObj.compareTo(fromDateObj) > 0)) {
									errors.rejectValue("temporaryApprovers["+index+"].fromDateString", "-1", "Date range overlaps with a prior one.");	
									errors.rejectValue("temporaryApprovers["+index+"].toDateString", "-1", "Date range overlaps with a prior one.");	
									validDateRange = false;	
									break;
							    } 
							}
							if (validDateRange) {
						    	Integer integerObj = new Integer(index);
						    	fromDateHash.put(integerObj, fromDateObj);
						    	toDateHash.put(integerObj, toDateObj);							
							}
						}
					}
				
				}
			}
			index++;
		}
	}

}
