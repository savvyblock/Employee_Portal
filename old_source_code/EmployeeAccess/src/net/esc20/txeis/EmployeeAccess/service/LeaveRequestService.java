package net.esc20.txeis.EmployeeAccess.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.esc20.txeis.EmployeeAccess.Exception.LeaveRequestException;
import net.esc20.txeis.EmployeeAccess.dao.LeaveRequestDao;
import net.esc20.txeis.EmployeeAccess.domainobject.CalendarDetail;
import net.esc20.txeis.EmployeeAccess.domainobject.CalendarEvents;
import net.esc20.txeis.EmployeeAccess.domainobject.DatabaseContextHolder;
import net.esc20.txeis.EmployeeAccess.domainobject.LeaveEmployeeData;
import net.esc20.txeis.EmployeeAccess.domainobject.LeaveEmployeePMISData;
import net.esc20.txeis.EmployeeAccess.domainobject.LeavePayDate;
import net.esc20.txeis.EmployeeAccess.domainobject.LeaveRequest;
import net.esc20.txeis.EmployeeAccess.domainobject.LeaveRequestComment;
import net.esc20.txeis.EmployeeAccess.domainobject.LeaveTemporaryApprover;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.AbsenceReason;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.LeaveParameters;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.LeaveType;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.LeaveUnitsConversion;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.PayFrequency;
import net.esc20.txeis.EmployeeAccess.service.api.IMailUtilService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ContextLoader;

@Service
@Transactional(readOnly = true)
public class LeaveRequestService {

	public static final int REQUEST_SERVICE_SUCCESS = 0;
	public static final int REQUEST_SERVICE_ERROR = -1;
	

	@Autowired
	private LeaveRequestDao leaveRequestDao;
	
	private IMailUtilService mailUtilService;

	public LeaveParameters getLeaveParams() {
		return leaveRequestDao.getLeaveParams();
	}
	
	public String getLeaveTypeNotes(String leaveType) {
		return leaveRequestDao.getLeaveTypeNotes(leaveType);
	}
	
	public LeaveEmployeeData getEmployeeData(String employeeNumber) {
		return leaveRequestDao.getEmployeeData(employeeNumber);
	}
	
	public List<PayFrequency> getUserPayFrequencies(String employeeNumber) {
		
		return leaveRequestDao.getEmployeeLeavePayFrequencies(employeeNumber);
	}
	
	public List<LeaveRequest> getEmployeeUnprocessedLeaveRequests(String employeeNumber, String payFrequency) {
		List<LeaveRequest> leaveRequests = leaveRequestDao.getEmployeeUnprocessedLeaveRequests(employeeNumber, payFrequency);
		for (LeaveRequest leaveRequest : leaveRequests) {
			leaveRequest.setRequestComments(getLeaveRequestComments(leaveRequest.getId()));
		}
		return leaveRequests;
	}
	
	public List<LeaveRequest> getEmployeeLeaveRequests(String employeeNumber, String payFrequency, String filterFromDate, String filterToDate) {
		if (filterFromDate==null || filterFromDate.trim().equals("") || filterFromDate.equals("  -  -    ")) {
			filterFromDate = "01-01-1900";
		}
		if (filterToDate==null || filterToDate.trim().equals("") || filterToDate.equals("  -  -    ")) {
			filterToDate = "01-01-2200";
		}
		List<LeaveRequest> leaveRequests = leaveRequestDao.getEmployeeLeaveRequests(employeeNumber, payFrequency, filterFromDate, filterToDate);
		for (LeaveRequest leaveRequest : leaveRequests) {
			if (leaveRequest.getId() != 0) {
				leaveRequest.setRequestComments(getLeaveRequestComments(leaveRequest.getId()));
			}
		}
		return leaveRequests;
	}

	public List<LeaveRequestComment> getLeaveRequestComments(int leaveId) {
		return leaveRequestDao.getLeaveRequestComments(leaveId);
	}
	
//	public String getLeaveRequestCommentLog(int leaveId) {
//		List<LeaveRequestComment> leaveRequestComments = leaveRequestDao.getLeaveRequestComments(leaveId);
//		StringBuilder strBuf = new StringBuilder();
//		for (LeaveRequestComment leaveRequestComment : leaveRequestComments) {
//			strBuf.append("&#10095;&#10095;&#10095; ").append(leaveRequestComment.getCommentDateString()).append(" ").append(leaveRequestComment.getCommentTimeString()); 
//			
//			if (leaveRequestComment.getCommentType().equals(LeaveRequestComment.REQUEST_COMMENT_TYPE_REQUEST_CURRENT_WF)) {
//				strBuf.append(" Request Comment\n");
//			} else if (leaveRequestComment.getCommentType().equals(LeaveRequestComment.REQUEST_COMMENT_TYPE_REQUEST_PRIOR_WF)) {
//				strBuf.append(" Request Comment\n");
//			} else if (leaveRequestComment.getCommentType().equals(LeaveRequestComment.REQUEST_COMMENT_TYPE_APPROVAL)) {
//				strBuf.append(" Approval Comment\n");
//			} else if (leaveRequestComment.getCommentType().equals(LeaveRequestComment.REQUEST_COMMENT_TYPE_DISAPPROVAL)) {
//				strBuf.append(" Disapproval Comment\n");
//			}
//			strBuf.append("&#10095;&#10095;&#10095; ").append(leaveRequestComment.getCommentEmp().getSelectOptionLabel());
//			strBuf.append("\n").append(leaveRequestComment.getComment()).append("\n\n");
//		}
//		return strBuf.toString();
//	}
	
	public List<LeaveType> getLeaveTypes(String payFrequency, String employeeNumber, String absenceReason) {
		return leaveRequestDao.getLeaveTypes(payFrequency, employeeNumber, absenceReason);
	}
	
	public List<AbsenceReason> getAbsenceReasons(String payFrequency, String employeeNumber, String leaveType) {
		return leaveRequestDao.getAbsenceReasons(payFrequency, employeeNumber, leaveType);
	}
	
	@Transactional (propagation = Propagation.REQUIRED, readOnly = false, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = Exception.class)
	public void addLeaveRequest (LeaveRequest leaveRequest, LeaveEmployeeData firstLineSupervisor, LeaveEmployeeData employeeData) {
		int numRows = leaveRequestDao.addLeaveRequest(leaveRequest);
		if (numRows > 0) {
			addLeaveRequestWorkflow(firstLineSupervisor.getEmployeeNumber(), leaveRequest.getId(), 1);
/*			
			// get the consecutive units threshold for the absence reason
			BigDecimal threshold = leaveRequestDao.getAbsenceReasonDocRequiredConsecutiveUnitsThreshold(leaveRequest.getAbsenceReason());
			// get the consecutive units for the absence reason of this leave request
			double consecutiveUnits = computeAbsenceReasonConsecutiveUnits(leaveRequest);
			if (threshold.doubleValue() > 0.0 && consecutiveUnits >= threshold.doubleValue()) {
				String message = getMessageBodyDocRequiredNotification (employeeData.getFullNameTitleCase(), leaveRequest.getFromDate(), leaveRequest.getToDate(), 
						leaveRequest.getAbsenceReasonDescription().toUpperCase());
				String subject = "Documention Requred for Leave request submitted for "+employeeData.getFullNameTitleCase();
				sendEmail(subject, employeeData.getEmailAddress(), message);
			}
*/
		}
		
	}
	
	private double computeAbsenceReasonConsecutiveUnits (LeaveRequest leaveRequest) {
		// TODO: impment computation of consecutive units
		// algorithm: go backward/forward day by day... if leave requested per day adds to standard hours, count that day
		// for now, just returned the number of units requested for this leave request
		double consecutiveUnits = leaveRequest.getLeaveRequested().doubleValue();
		return consecutiveUnits;
	}

	@Transactional (propagation = Propagation.REQUIRED, readOnly = false, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = Exception.class)
	public void updateLeaveRequest (LeaveRequest leaveRequest, LeaveEmployeeData firstLineSupervisor, LeaveEmployeeData employeeData) {
		int numRows = leaveRequestDao.updateLeaveRequest(leaveRequest);
		if (numRows > 0) {
			// delete transmittal record, if any
//			numRows = leaveRequestDao.deleteLeaveRequestTransmittal(leaveRequest);
			// re-start workflow
			addLeaveRequestWorkflow (firstLineSupervisor.getEmployeeNumber(), leaveRequest.getId(), 1);
/*			
			// get the consecutive units threshold for the absence reason
			BigDecimal threshold = leaveRequestDao.getAbsenceReasonDocRequiredConsecutiveUnitsThreshold(leaveRequest.getAbsenceReason());
			// get the consecutive units for the absence reason of this leave request
			double consecutiveUnits = computeAbsenceReasonConsecutiveUnits(leaveRequest);
			if (consecutiveUnits >= threshold.doubleValue()) {
				String message = getMessageBodyDocRequiredNotification (employeeData.getFullNameTitleCase(), leaveRequest.getFromDate(), leaveRequest.getToDate(), 
						leaveRequest.getAbsenceReasonDescription().toUpperCase());
				String subject = "Documention Requred for Leave request submitted for "+employeeData.getFullNameTitleCase();
				String[] ccEmailAddresses = new String[1];
				ccEmailAddresses[0] = firstLineSupervisor.getEmailAddress();	
				sendEmail(subject, employeeData.getEmailAddress(), message, ccEmailAddresses);
			}
*/
		}
	}
	
	public void deleteLeaveRequest (LeaveRequest leaveRequest, LeaveEmployeeData employeeData, LeaveEmployeeData employeeSupervisorData, LeaveEmployeeData deleteEmployeeData, String url) {
		boolean sendEmails = false;
		if (leaveRequest.getStatus().equals(LeaveRequest.REQUEST_STATUS_PENDING_PAYROLL)) {
			// an approved request is being deleted ... send notifications
			sendEmails = true;
		}
		int numRows = leaveRequestDao.deleteLeaveRequest(leaveRequest);
/*
		if (numRows > 0) {
			numRows = leaveRequestDao.deleteLeaveRequestTransmittal(leaveRequest);
		}
*/
		
		if (sendEmails) {
			if (employeeData.getEmployeeNumber().equals(deleteEmployeeData.getEmployeeNumber())) {
				// the employee is deleting their own approved request 
				// ... notify the employee's immediate supervisor
				String message = getMessageBodyRequestDeleted2ImmediateSupervisorNotification (employeeSupervisorData.getFullNameTitleCase(), employeeData.getFullNameTitleCase(), 
						employeeData.getEmployeeNumber(), leaveRequest.getFromDateString(), leaveRequest.getToDateString(), 
						leaveRequest.getFromTimeString(), leaveRequest.getToTimeString(), url);
				String subject = "Approved Leave Request deleted by "+employeeData.getFullNameTitleCase();
				sendEmail(subject, employeeSupervisorData.getEmailAddress(), message);
				
			} else {
				// the employee's approved request is being deleted by a supervisor ... notify the employee
				String message = getMessageBodyRequestDeleted2RequesterNotification (employeeSupervisorData.getFullNameTitleCase(), employeeData.getFullNameTitleCase(), 
						leaveRequest.getFromDateString(), leaveRequest.getToDateString(), 
						leaveRequest.getFromTimeString(), leaveRequest.getToTimeString(), url);
				String subject = "Approved Leave Request deleted by "+ deleteEmployeeData.getFullNameTitleCase();
				sendEmail(subject, employeeData.getEmailAddress(), message);
				
				if (!employeeSupervisorData.getEmployeeNumber().equals(deleteEmployeeData.getEmployeeNumber())) {
					// the employee's request was not deleted by the employee's immediate supervisor
					// ... notify the the employee's immediate supervisor
					message = getMessageBodyRequestDeletedBySuper2ImmediateSupervisorNotification (employeeSupervisorData.getFullNameTitleCase(), employeeData.getFullNameTitleCase(),
							employeeData.getEmployeeNumber(), deleteEmployeeData.getFullNameTitleCase(),
							leaveRequest.getFromDateString(), leaveRequest.getToDateString(), 
							leaveRequest.getFromTimeString(), leaveRequest.getToTimeString(), url);
					subject = "Approved Leave Request for " + employeeData.getFullNameTitleCase() + " deleted by "+ deleteEmployeeData.getFullNameTitleCase();
					sendEmail(subject, employeeSupervisorData.getEmailAddress(), message);
					
				} 
			}
		}
		
	}

	public List<LeaveEmployeeData> getSupervisorDirectReports(String supervisorEmployeeNumber, boolean usePMIS, boolean supervisorsOnly, boolean excludeTempApprovers) {
		List<LeaveEmployeeData> directReports=null;
		if (usePMIS) {
			LeaveEmployeePMISData employeePMISData = leaveRequestDao.getEmployeePMISData(supervisorEmployeeNumber);
			if (employeePMISData!=null) {
				directReports = leaveRequestDao.getPMISSupervisorDirectReports(employeePMISData.getBilletNumber(), employeePMISData.getPosNumber());
			} else {
				directReports = new ArrayList<LeaveEmployeeData>();
			}
		} else {
			directReports = leaveRequestDao.getSupervisorDirectReports(supervisorEmployeeNumber);
		}
		if (supervisorsOnly && directReports!=null) {
			// remove direct reports who are not supervisors AND who do not have submittals to process
			ArrayList<LeaveEmployeeData> directReportSupervisors = new ArrayList<LeaveEmployeeData>();
			for (LeaveEmployeeData employeeData : directReports) {
				if (employeeData.getNumDirectReports() > 0) {
					directReportSupervisors.add(employeeData);
				} else if (!excludeTempApprovers){
					// if an employee is a not a supervisor but does have submittals to process, add them
					List<LeaveRequest> leaveRequestSubmittals = getSupervisorSumittedLeaveRequests(employeeData.getEmployeeNumber());
					if (leaveRequestSubmittals.size() > 0) {
						directReportSupervisors.add(employeeData);
					}
				}
			}
			return directReportSupervisors;
		} else {
			return directReports;
		}
	}

	public LeaveEmployeeData getFirstLineSupervisor(String employeeNumber, boolean usePMIS) {
		LeaveEmployeeData firstLineSupervisor=null;
		String directReportEmployeeNumber = employeeNumber;
		
		while (firstLineSupervisor==null && directReportEmployeeNumber!=null) {
			LeaveEmployeePMISData employeeSupervisorPMISData=null;
			if (usePMIS) {
				employeeSupervisorPMISData = leaveRequestDao.getEmployeeSupervisorPMISData(directReportEmployeeNumber);
				if (employeeSupervisorPMISData==null) {
					return null;
				}
				firstLineSupervisor = leaveRequestDao.getPMISFirstLineSupervisor(employeeSupervisorPMISData.getBilletNumber(), employeeSupervisorPMISData.getPosNumber(), true);
			} else {
				firstLineSupervisor = leaveRequestDao.getFirstLineSupervisor(directReportEmployeeNumber, true);	
			}
			if (firstLineSupervisor == null) {
				return null;
			} else if (firstLineSupervisor.getEmployeeNumber().equals(employeeNumber)) {
				// this will occur is if the employee is the temp approver for the first-line supervisor;
				// attempt to use the first-line supervisor of the first-line supervisor
				firstLineSupervisor = null;
				if (usePMIS) {
					firstLineSupervisor = leaveRequestDao.getPMISFirstLineSupervisor(employeeSupervisorPMISData.getBilletNumber(), employeeSupervisorPMISData.getPosNumber(), false);
				} else {
					firstLineSupervisor = leaveRequestDao.getFirstLineSupervisor(directReportEmployeeNumber, false);
				}
				if (firstLineSupervisor != null) {
					directReportEmployeeNumber = firstLineSupervisor.getEmployeeNumber();
					firstLineSupervisor = null;
				} else {
					directReportEmployeeNumber =null;
				}
			}
		}
		
		return firstLineSupervisor;
	}
	
	public void addLeaveRequestWorkflow (String approverEmployeeNumber, int leaveId, int sequence) {
		leaveRequestDao.addLeaveRequestWorkflow (approverEmployeeNumber, leaveId, sequence);
	}
	
	public List<LeaveRequest> getSupervisorSumittedLeaveRequests(String supervisorEmployeeNumber) {
		List<LeaveRequest> leaveRequests = leaveRequestDao.getSupervisorSumittedLeaveRequests(supervisorEmployeeNumber);
		for (LeaveRequest leaveRequest : leaveRequests) {
			leaveRequest.setRequestComments(getLeaveRequestComments(leaveRequest.getId()));
		}
		return leaveRequests;
	}
	
	public List<LeaveUnitsConversion> getMinutesToHoursConversionRecs(String payFrequency, String leaveType) {
		List<LeaveUnitsConversion> conversionRecs = leaveRequestDao.getMinutesToHoursConversionRecs(payFrequency, leaveType);
		BigDecimal fromMinute = new BigDecimal(1.0).setScale(3, BigDecimal.ROUND_HALF_UP);
		for (LeaveUnitsConversion rec : conversionRecs) {
			rec.setFromUnit(fromMinute);
			fromMinute = new BigDecimal (rec.getToUnit().intValue()+1).setScale(3, BigDecimal.ROUND_HALF_UP);
		}
		return conversionRecs;
	}
	
	public List<LeaveUnitsConversion> getHoursToDaysConversionRecs(String payFrequency, String leaveType) {
		List<LeaveUnitsConversion> conversionRecs = leaveRequestDao.getHoursToDaysConversionRecs(payFrequency, leaveType);
		BigDecimal fromHour = new BigDecimal(0.001).setScale(3, BigDecimal.ROUND_HALF_UP);
		for (LeaveUnitsConversion rec : conversionRecs) {
			rec.setFromUnit(fromHour);
			fromHour = new BigDecimal (rec.getToUnit().doubleValue()+0.001).setScale(3, BigDecimal.ROUND_HALF_UP);
		}
		return conversionRecs;
	}

	public List<LeaveTemporaryApprover> getTemporaryApprovers(String supervisorEmployeeNumber) {
		return leaveRequestDao.getTemporaryApprovers(supervisorEmployeeNumber);
	}
	
	public void addTemporaryApprover (LeaveTemporaryApprover leaveTemporaryApprover, String supervisorEmployeeNumber) {
		leaveRequestDao.addTemporaryApprover (leaveTemporaryApprover, supervisorEmployeeNumber);
	}
	
	public void updateTemporaryApprover (LeaveTemporaryApprover leaveTemporaryApprover) {
		leaveRequestDao.updateTemporaryApprover(leaveTemporaryApprover);
	}
	
	public void deleteTemporaryApprover (LeaveTemporaryApprover leaveTemporaryApprover) {
		leaveRequestDao.deleteTemporaryApprover(leaveTemporaryApprover);
	}
	
	@Transactional (propagation = Propagation.REQUIRED, readOnly = false, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = Exception.class)
	public int approveRequest(LeaveEmployeeData approver, LeaveEmployeeData employee, LeaveRequest leaveRequest, 
			double workdayHours, boolean ignoreCutoffDates, String url) throws LeaveRequestException, ParseException {
		int status = REQUEST_SERVICE_SUCCESS;
		boolean error = false;
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		SimpleDateFormat xmitalDateFormat = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		
		// insert approver comment 
		leaveRequestDao.updateLeaveRequestApproverActionComment (leaveRequest, approver.getEmployeeNumber());
		
		// get current workflow sequence 
		int sequenceNumber = leaveRequestDao.getWorkflowSequenceNumber(leaveRequest.getId());
		// check if additional approvals are needed
		LeaveEmployeeData nextLineSupervisor=null;

			leaveRequestDao.updateLeaveRequestStatus(leaveRequest.getId(), LeaveRequest.REQUEST_STATUS_PENDING_PAYROLL);
			// update requester's comment (if any) ... set the comment type to prior workflow since this workflow is complete
			leaveRequestDao.updateLeaveRequestRequesterCommentTypeToPriorWF(leaveRequest);
			
			// notify requester that the leave request has been approved
			if (employee.getEmailAddress()!=null && employee.getEmailAddress().trim().length() > 0) {
				String fromTimeString = (leaveRequest.getFromTimeString()==null || leaveRequest.getFromTimeString().trim().length()==0) ? 
						leaveRequest.getFromHour()+":"+leaveRequest.getFromMinute()+leaveRequest.getFromAmPm() : leaveRequest.getFromTimeString();
				String toTimeString = (leaveRequest.getToTimeString()==null || leaveRequest.getToTimeString().trim().length()==0) ? 
						leaveRequest.getToHour()+":"+leaveRequest.getToMinute()+leaveRequest.getToAmPm() : leaveRequest.getToTimeString();
				if (fromTimeString.trim().length()==0) {
					fromTimeString = "";
				}
				if (toTimeString.trim().length()==0) {
					toTimeString = "";
				}
				String message = this.getMessageBodyRequestApprovedNotification(approver.getFullNameTitleCase(), employee.getFullNameTitleCase(), 
						leaveRequest.getFromDateString(), leaveRequest.getToDateString(), fromTimeString, toTimeString, url);
				String subject = "Leave Request Approved";
				sendEmail(subject, employee.getEmailAddress(), message);				
			}
/*			
		}
*/	
		return status;
	}
	
	public int disapproveRequest(LeaveEmployeeData disapprover, LeaveEmployeeData employee, LeaveRequest leaveRequest, String url) {
		int status = REQUEST_SERVICE_SUCCESS;
		leaveRequestDao.updateLeaveRequestStatus(leaveRequest.getId(), LeaveRequest.REQUEST_STATUS_DISAPPROVED);
		leaveRequestDao.updateLeaveRequestApproverActionComment (leaveRequest, disapprover.getEmployeeNumber());
		leaveRequestDao.updateLeaveRequestRequesterCommentTypeToPriorWF (leaveRequest);
		
		// notify requester that the leave request has been disapproved
		if (employee.getEmailAddress()!=null && employee.getEmailAddress().trim().length() > 0) {
			String message = this.getMessageBodyRequestDisapprovedNotification(disapprover.getFullNameTitleCase(), employee.getFullNameTitleCase(), 
					leaveRequest.getFromDateString(), leaveRequest.getToDateString(), 
					leaveRequest.getFromTimeString(), leaveRequest.getToTimeString(), url);
			String subject = "Leave Request Disapproved";
			sendEmail(subject, employee.getEmailAddress(), message);							
		}
		
		return status; 
	}

	// assumes leavePayDates are sorted chronologically
	private Date getPayDate(Date absenceDate, List<LeavePayDate> leavePayDates, boolean ignoreCutoffDates) {
		Date payDate=null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		Date today = null;
		try {
			today = dateFormat.parse(dateFormat.format(new Date())); // drop off the hours/minutes
		} catch (Exception e) {}
		
		for (LeavePayDate leavePayDate : leavePayDates) {
			// skip is true if the cutoff date or last approval date has been set to 'XXXXXXXX'
			// if not skipping, ensure the paydate hasn't passed
			if (!leavePayDate.isSkip() && leavePayDate.getPayDate().compareTo(today) >= 0) {
				// use the pay date if the absence date is within the pay period's before/end date 
				// ... or the absence date is prior to the pay period's begin date
				if ((leavePayDate.getPayPeriodBeginDate().compareTo(absenceDate)<=0 && leavePayDate.getPayPeriodEndDate().compareTo(absenceDate)>=0) ||
						(absenceDate.compareTo(leavePayDate.getPayPeriodBeginDate())<0)) {
					// check the 
					if (ignoreCutoffDates ||
						((leavePayDate.getLeaveRequestCutoffDate()==null || leavePayDate.getLeaveRequestCutoffDate().compareTo(absenceDate)>=0) &&
						 (leavePayDate.getLeaveLastApprovalDate()==null || leavePayDate.getLeaveLastApprovalDate().compareTo(today)>=0)
						)) {
						payDate = leavePayDate.getPayDate();
						break;
					}
				}
			}
		}
		return payDate;
	}
	
	public boolean isLeavePeriodsOverlapping(Date fromDateFromTime1, Date fromDateToTime1, int numberDays1, Date fromDateFromTime2, Date fromDateToTime2, int numberDays2) {

		boolean overlappingPeriods = false;
		Calendar cal = Calendar.getInstance();
		cal.setTime(fromDateFromTime2);
		Date savedFromDateFromTime = cal.getTime();
		cal.setTime(fromDateToTime2);
		Date savedFromDateToTime = cal.getTime();
		
		for (int i=0; i < numberDays1; i++) {
			for (int j=-0; j < numberDays2; j++) {
				if ((fromDateFromTime1.compareTo(fromDateToTime2)<0) && (fromDateToTime1.compareTo(fromDateFromTime2)>0)) {
					overlappingPeriods = true;
					break;
				}
				// increment day
		        cal.setTime(fromDateFromTime2);
		        cal.add(Calendar.DATE, 1);
		        fromDateFromTime2 = cal.getTime();		
		        cal.setTime(fromDateToTime2);
		        cal.add(Calendar.DATE, 1);
		        fromDateToTime2 = cal.getTime();		
			}
			if (overlappingPeriods) {
				break;
			}
			// increment day
	        cal.setTime(fromDateFromTime1);
	        cal.add(Calendar.DATE, 1);
	        fromDateFromTime1 = cal.getTime();		
	        cal.setTime(fromDateToTime1);
	        cal.add(Calendar.DATE, 1);
	        fromDateToTime1 = cal.getTime();
	        // reset values of fromDateFromTime2 and fromDateToTime2
	        fromDateFromTime2 = savedFromDateFromTime;
	        fromDateToTime2 = savedFromDateToTime;
		}
		
		return overlappingPeriods;
	}

	public List<LeaveRequest> getEmployeeLeaveRequestsPeriodsPayFeq(String employeeNumber, String payFrequency) {
		List<LeaveRequest> leaveRequests = leaveRequestDao.getEmployeeLeaveRequestsPeriodsPayFreq(employeeNumber, payFrequency);
		return leaveRequests;
	}
	
	public List<LeaveRequest> getEmployeeLeaveRequestsPeriods(String employeeNumber) {
		List<LeaveRequest> leaveRequests = leaveRequestDao.getEmployeeLeaveRequestsPeriods(employeeNumber);
		return leaveRequests;
	}

/*
	public void updatePendingPayrollLeaveStatuses(String employeeNumber) {
	
		List<HashMap<String, Integer>> pendingPayrollHashList =  leaveRequestDao.getPendingPayrollLeaveCounts(employeeNumber);
		for (HashMap<String, Integer> hash : pendingPayrollHashList) {
			String status="";
			int leaveId = hash.get(LeaveRequestDao.PENDING_PAYROLL_COUNTS_HASH_KEY_REQUEST_ID);
			int numberLeaveDays = hash.get(LeaveRequestDao.PENDING_PAYROLL_COUNTS_HASH_KEY_REQUEST_NUM_DAYS);
			int numberProcessed = hash.get(LeaveRequestDao.PENDING_PAYROLL_COUNTS_HASH_KEY_NUM_PROCESSED);
			int numberPending = hash.get(LeaveRequestDao.PENDING_PAYROLL_COUNTS_HASH_KEY_NUM_PENDING);
			if ((numberProcessed+numberPending) != numberLeaveDays) {
				status= LeaveRequest.REQUEST_STATUS_APPROVED;
			} else if (numberProcessed!=0 && numberPending!=0) {
				status = LeaveRequest.REQUEST_STATUS_PENDING_PAYROLL_INPROCESS;
			} else if (numberProcessed==numberLeaveDays) {
				status= LeaveRequest.REQUEST_STATUS_PROCESSED;
			} else if (numberPending==numberLeaveDays) {
				status= LeaveRequest.REQUEST_STATUS_PENDING_PAYROLL;
			}
			if (status.length() > 0) {
				int numRows = leaveRequestDao.updateLeaveRequestStatus (leaveId, status); 
			}
		}
	}
*/
	
	public boolean isLeaveApprovalAccessGranted (String employeeNumber) {
		boolean accessGranted = false;
		accessGranted = leaveRequestDao.isLeaveApprovalAccessGranted(employeeNumber);
		return accessGranted;
	}

	//Retrieves list of calendar events for the this employee and subordinates
	public List<CalendarEvents> getCalendarEvents(String empNbr, String start, String end) {
		//Need to reformat date for SQL query
		start = start.replace("-", "");
		end = end.replace("-", "");
		
		return leaveRequestDao.getCalendarEvents(empNbr, start, end);
	}


	//Retrieves an object of calendar detail from the calendar screen
	public CalendarDetail getCalendarDetail(String id) {
		if (id.startsWith("X")) {
			//parse id for transmittal table
			String payFreq = id.substring(1, 2);
			String empNbr = id.substring(2, 8);
			String dtPay = id.substring(8, 16);
			String seq = id.substring(16, 17);
			return leaveRequestDao.getCalendarDetailXmital(payFreq, empNbr, dtPay, seq);
		} else {
			return leaveRequestDao.getCalendarDetail(id);
		}
	}
	
	public String getConstructedEmployeeAccessURL(HttpServletRequest request) {
		String uri = request.getRequestURI();
		if (uri.indexOf("leave") >=0) {
			uri = uri.substring(0,uri.indexOf("leave"));
			uri += "login?distid="+DatabaseContextHolder.getCountyDistrict();
		}
		String url = request.getScheme() + "://" +
			request.getServerName() + 
			("http".equals(request.getScheme()) && request.getServerPort() == 80 || "https".equals(request.getScheme()) && request.getServerPort() == 443 ? "" : ":" + request.getServerPort() ) +
			uri;
		return url;
	}
	
	public String getMessageBodyApprovalNotification (String supervisorName, String employeeName, String employeeNumber, String fromDate, String toDate, String fromTime, String toTime, String url) {
		String returnBody = "";
		StringBuilder emailBody = new StringBuilder();
		emailBody.append("<p>%s:</p>");
		emailBody.append("<p>A leave request for %s, employee number %s, has been submitted and is ready for your approval.  The leave dates and times requested are as follows:</p>");
		emailBody.append("<p style='margin-left: 12pt;'>Dates:&nbsp;&nbsp;%s&nbsp;&nbsp;-&nbsp;&nbsp;%s<br/>Times:&nbsp;&nbsp;%s&nbsp;&nbsp;-&nbsp;&nbsp;%s</p>");		

		if (url==null || url.trim().length()==0) {
			emailBody.append("<p style='font-weight:bold'>Please log in to Employee Access to process this submission.</p>");
		} else {
			emailBody.append("<p style='font-weight:bold'>Please log in to Employee Access to process this submission by clicking on this link:<br/>");
			emailBody.append("<span style='text-decoration: underline;'>%s</span></p>");
		}
		emailBody.append("<p>Thank You</p>");
		if (url==null || url.trim().length()==0) {
			returnBody = String.format(emailBody.toString(), supervisorName, employeeName, employeeNumber, fromDate, toDate, fromTime, toTime);
		} else {
			returnBody = String.format(emailBody.toString(), supervisorName, employeeName, employeeNumber, fromDate, toDate, fromTime, toTime, url);
		}
		
		return returnBody;
	}
	
	public String getMessageBodyRequestApprovedNotification (String supervisorName, String employeeName, String fromDate, String toDate, String fromTime, String toTime, String url) {
		String returnBody = "";
		StringBuilder emailBody = new StringBuilder();
		emailBody.append("<p>%s:</p>");
		emailBody.append("<p>Your leave request has been approved by %s.  No action is needed on your part.  The leave dates and times requested are as follows:</p>");
		emailBody.append("<p style='margin-left: 12pt;'>Dates:&nbsp;&nbsp;%s&nbsp;&nbsp;-&nbsp;&nbsp;%s<br/>Times:&nbsp;&nbsp;%s&nbsp;&nbsp;-&nbsp;&nbsp;%s</p>");		

		if (url==null || url.trim().length()==0) {
			emailBody.append("<p style='font-weight:bold'>Please log in to Employee Access if you wish to make changes to the leave requested and resubmit the request.</p>");
		} else {
			emailBody.append("<p style='font-weight:bold'>Please log in to Employee Access if you wish to make changes to the leave requested and resubmit the request by clicking on this link:<br/>");
			emailBody.append("<span style='text-decoration: underline;'>%s</span></p>");
		}
		emailBody.append("<p>Thank You</p>");
		if (url==null || url.trim().length()==0) {
			returnBody = String.format(emailBody.toString(), employeeName, supervisorName, fromDate, toDate, fromTime, toTime);
		} else {
			returnBody = String.format(emailBody.toString(), employeeName, supervisorName, fromDate, toDate, fromTime, toTime, url);
		}
		
		return returnBody;
	}
	
	public String getMessageBodyRequestDisapprovedNotification (String supervisorName, String employeeName, String fromDate, String toDate, String fromTime, String toTime, String url) {
		String returnBody = "";
		StringBuilder emailBody = new StringBuilder();
		emailBody.append("<p>%s:</p>");
		emailBody.append("<p>The leave request you submitted has been disapproved by %s.  The leave dates and times requested are as follows:</p>");
		emailBody.append("<p style='margin-left: 12pt;'>Dates:&nbsp;&nbsp;%s&nbsp;&nbsp;-&nbsp;&nbsp;%s<br/>Times:&nbsp;&nbsp;%s&nbsp;&nbsp;-&nbsp;&nbsp;%s</p>");		

		if (url==null || url.trim().length()==0) {
			emailBody.append("<p style='font-weight:bold'>Please log in to Employee Access to view comments entered by the supervisor or if you wish to make changes to the leave requested and resubmit the request.</p>");
		} else {
			emailBody.append("<p style='font-weight:bold'>Please log in to Employee Access to view comments entered by the supervisor or if you wish to make changes to the leave requested and resubmit the request by clicking on this link:<br/>");
			emailBody.append("<span style='text-decoration: underline;'>%s</span></p>");
		}
		emailBody.append("<p>Thank You</p>");
		if (url==null || url.trim().length()==0) {
			returnBody = String.format(emailBody.toString(), employeeName, supervisorName, fromDate, toDate, fromTime, toTime);
		} else {
			returnBody = String.format(emailBody.toString(), employeeName, supervisorName, fromDate, toDate, fromTime, toTime, url);
		}
		
		return returnBody;
	}

	public String getMessageBodyNLineApprovalNotification (String supervisorName, String employeeName, String employeeNumber, String fromDate, String toDate, String fromTime, String toTime, String url, String absenceReason, String consecutiveUnits) {
		String returnBody = "";
		StringBuilder emailBody = new StringBuilder();
		emailBody.append("<p>%s:</p>");
		emailBody.append("<p>A leave request for %s, employee number %s, has been submitted and is ready for your approval.  The absence reason selected is %s and the leave dates and times requested are as follows:</p>");
		emailBody.append("<p style='margin-left: 12pt;'>Dates:&nbsp;&nbsp;%s&nbsp;&nbsp;-&nbsp;&nbsp;%s<br/>Times:&nbsp;&nbsp;%s&nbsp;&nbsp;-&nbsp;&nbsp;%s</p>");		
		emailBody.append("<p>The total consecutive units requested by this employee for the indicated leave type for this and any other back-to-back requests is %s. Due to the amount of the consecutive units this request requires your approval.</p>");
		

		if (url==null || url.trim().length()==0) {
			emailBody.append("<p style='font-weight:bold'>Please log in to Employee Access to process this submission.</p>");
		} else {
			emailBody.append("<p style='font-weight:bold'>Please log in to Employee Access to process this submission by clicking on this link:<br/>");
			emailBody.append("<span style='text-decoration: underline;'>%s</span></p>");
		}
		emailBody.append("<p>Thank You</p>");
		if (url==null || url.trim().length()==0) {
			returnBody = String.format(emailBody.toString(), supervisorName, employeeName, employeeNumber, absenceReason, fromDate, toDate, fromTime, toTime, consecutiveUnits);
		} else {
			returnBody = String.format(emailBody.toString(), supervisorName, employeeName, employeeNumber, absenceReason, fromDate, toDate, fromTime, toTime, consecutiveUnits, url);
		}
		
		return returnBody;
	}

	public String getMessageBodyDocRequiredNotification (String employeeName, String fromDate, String toDate, String absenceReasonDescription) {
		String returnBody = "";
		StringBuilder emailBody = new StringBuilder();
		emailBody.append("<p>%s:</p>");
		emailBody.append("<p>Please be advised that documentation is required for the leave request(s) submitted covering the period %s to %s.  ");
		emailBody.append("This requirement has been triggered by the amount of leave requested for the selected Absence Reason:  %s.</p>");		
		emailBody.append("<p style='font-weight:bold'>Contact your supervisor to arrange for submitting the necessary documentation.</p>");
		emailBody.append("<p>Thank You</p>");
		returnBody = String.format(emailBody.toString(), employeeName, fromDate, toDate, absenceReasonDescription);
		
		return returnBody;
	}

	public String getMessageBodyRequestDeleted2ImmediateSupervisorNotification (String supervisorName, String employeeName, String employeeNumber, String fromDate, String toDate, String fromTime, String toTime, String url) {
		String returnBody = "";
		StringBuilder emailBody = new StringBuilder();
		emailBody.append("<p>%s:</p>");
		emailBody.append("<p>This is to notify you that an approved leave request has been deleted by the employee, %s, employee number %s.  No action is needed on your part.  ");
		emailBody.append("The leave dates and times for the deleted request are as follows:</p>");
		emailBody.append("<p style='margin-left: 12pt;'>Dates:&nbsp;&nbsp;%s&nbsp;&nbsp;-&nbsp;&nbsp;%s<br/>Times:&nbsp;&nbsp;%s&nbsp;&nbsp;-&nbsp;&nbsp;%s</p>");	
		if (url==null || url.trim().length()==0) {
			emailBody.append("<p style='font-weight:bold'>Please log in to Employee Access if you wish to view the employee's current unprocessed leave requests.</p>");
		} else {
			emailBody.append("<p style='font-weight:bold'>Please log in to Employee Access if you wish to view the employee's current unprocessed leave requests by clicking on this link:<br/>");
			emailBody.append("<span style='text-decoration: underline;'>%s</span></p>");
		}
		emailBody.append("<p>Thank You</p>");
		if (url==null || url.trim().length()==0) {
			returnBody = String.format(emailBody.toString(), supervisorName, employeeName, employeeNumber, fromDate, toDate, fromTime, toTime);
		} else {
			returnBody = String.format(emailBody.toString(), supervisorName, employeeName, employeeNumber, fromDate, toDate, fromTime, toTime, url);
		}
		
		return returnBody;
	}

	public String getMessageBodyRequestDeletedBySuper2ImmediateSupervisorNotification (String supervisorName, String employeeName, String employeeNumber, String deleteSupervisorName, 
			String fromDate, String toDate, String fromTime, String toTime, String url) {
		String returnBody = "";
		StringBuilder emailBody = new StringBuilder();
		emailBody.append("<p>%s:</p>");
		emailBody.append("<p>This is to notify you that an approved leave request for %s, employee number %s, has been deleted by %s.  No action is needed on your part.  ");
		emailBody.append("The leave dates and times for the deleted request are as follows:</p>");
		emailBody.append("<p style='margin-left: 12pt;'>Dates:&nbsp;&nbsp;%s&nbsp;&nbsp;-&nbsp;&nbsp;%s<br/>Times:&nbsp;&nbsp;%s&nbsp;&nbsp;-&nbsp;&nbsp;%s</p>");	
		if (url==null || url.trim().length()==0) {
			emailBody.append("<p style='font-weight:bold'>Please log in to Employee Access if you wish to view the employee's current unprocessed leave requests.</p>");
		} else {
			emailBody.append("<p style='font-weight:bold'>Please log in to Employee Access if you wish to view the employee's current unprocessed leave requests by clicking on this link:<br/>");
			emailBody.append("<span style='text-decoration: underline;'>%s</span></p>");
		}
		emailBody.append("<p>Thank You</p>");
		if (url==null || url.trim().length()==0) {
			returnBody = String.format(emailBody.toString(), supervisorName, employeeName, employeeNumber, deleteSupervisorName, fromDate, toDate, fromTime, toTime);
		} else {
			returnBody = String.format(emailBody.toString(), supervisorName, employeeName, employeeNumber, deleteSupervisorName, fromDate, toDate, fromTime, toTime, url);
		}
		
		return returnBody;
	}

	public String getMessageBodyRequestDeleted2RequesterNotification (String supervisorName, String employeeName, String fromDate, String toDate, String fromTime, String toTime, String url) {
		String returnBody = "";
		StringBuilder emailBody = new StringBuilder();
		emailBody.append("<p>%s:</p>");
		emailBody.append("<p>This is to notify you that your approved leave request with the dates and times listed has been deleted by %s.");
		emailBody.append("<p style='margin-left: 12pt;'>Dates:&nbsp;&nbsp;%s&nbsp;&nbsp;-&nbsp;&nbsp;%s<br/>Times:&nbsp;&nbsp;%s&nbsp;&nbsp;-&nbsp;&nbsp;%s</p>");		
		emailBody.append("<p>No action is needed on your part.</p>");

		if (url==null || url.trim().length()==0) {
			emailBody.append("<p style='font-weight:bold'>Please log in to Employee Access if you wish to access the leave request system.</p>");
		} else {
			emailBody.append("<p style='font-weight:bold'>Please log in to Employee Access if you wish to access the leave request system by clicking on this link:<br/>");
			emailBody.append("<span style='text-decoration: underline;'>%s</span></p>");
		}
		emailBody.append("<p>Thank You</p>");
		if (url==null || url.trim().length()==0) {
			returnBody = String.format(emailBody.toString(), employeeName, supervisorName, fromDate, toDate, fromTime, toTime);
		} else {
			returnBody = String.format(emailBody.toString(), employeeName, supervisorName, fromDate, toDate, fromTime, toTime, url);
		}
		
		return returnBody;
	}

	public String getMessageBodyRequestModified2EmployeeNotification (String supervisorName, String employeeName, String fromDate, String toDate, String fromTime, String toTime, String url, boolean createFlag) {
		String returnBody = "";
		StringBuilder emailBody = new StringBuilder();
		emailBody.append("<p>%s:</p>");
		if (createFlag) {
			emailBody.append("<p>This is to notify you that a leave request for you has been created and submitted by %s.  No action is needed on your part.  ");
			emailBody.append("The leave dates and times for the request are as follows:</p>");
		} else {
			emailBody.append("<p>This is to notify you that a leave request previously submitted for you has been modified and resubmitted by %s.  No action is needed on your part.  ");			
			emailBody.append("The current leave dates and times for the modified request are as follows:</p>");
		}
		emailBody.append("<p style='margin-left: 12pt;'>Dates:&nbsp;&nbsp;%s&nbsp;&nbsp;-&nbsp;&nbsp;%s<br/>Times:&nbsp;&nbsp;%s&nbsp;&nbsp;-&nbsp;&nbsp;%s</p>");		

		if (url==null || url.trim().length()==0) {
			emailBody.append("<p style='font-weight:bold'>Please log in to Employee Access if you wish to access the leave request system.</p>");
		} else {
			emailBody.append("<p style='font-weight:bold'>Please log in to Employee Access if you wish to access the leave request system by clicking on this link:<br/>");
			emailBody.append("<span style='text-decoration: underline;'>%s</span></p>");
		}
		emailBody.append("<p>Thank You</p>");
		if (url==null || url.trim().length()==0) {
			returnBody = String.format(emailBody.toString(), employeeName, supervisorName, fromDate, toDate, fromTime, toTime);
		} else {
			returnBody = String.format(emailBody.toString(), employeeName, supervisorName, fromDate, toDate, fromTime, toTime, url);
		}
		
		return returnBody;
	}
	
	public void sendEmail (String subject, String emailAddress, String message) {
		sendEmail(subject, emailAddress, message, null);
	}
	
	public void sendEmail (String subject, String emailAddress, String message, String ccEmailAddresses[]) {
		mailUtilService = (IMailUtilService)ContextLoader.getCurrentWebApplicationContext().getBean("mailUtilService");
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setSubject(subject);
		mailMessage.setText(message);
		mailMessage.setTo(emailAddress);
		if (ccEmailAddresses!=null && ccEmailAddresses.length>0) {
			// FAL16 release ... cc does not appear to be working
			mailMessage.setCc(ccEmailAddresses);
		}
		try{
			mailUtilService.sendMail(mailMessage, "text/html; charset=utf-8");
		} 
		catch(Exception e) {
			e.printStackTrace();
		} 				
	}
	
	public String getLeaveReqStatus(int id) {
		return leaveRequestDao.getLeaveReqStatus(id);
	}
}
