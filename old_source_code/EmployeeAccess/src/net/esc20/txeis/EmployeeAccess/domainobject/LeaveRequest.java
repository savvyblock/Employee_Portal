package net.esc20.txeis.EmployeeAccess.domainobject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class LeaveRequest implements Serializable {

	public LeaveRequest() {
	}

	private static final long serialVersionUID = -4932206514835864458L;
	
	public static final String REQUEST_STATUS_PENDING_APPROVAL = "P";
	public static final String REQUEST_STATUS_PENDING_PAYROLL = "A";
//	public static final String REQUEST_STATUS_PENDING_PAYROLL_INPROCESS = "I";  // if the leave request resulted in more than one transmittal rec, not all may be processed
	public static final String REQUEST_STATUS_DISAPPROVED = "D";
	public static final String REQUEST_STATUS_PROCESSED = "C";
//	public static final String REQUEST_STATUS_APPROVED = "V";  // had been inserted in xmital table although no longer can be mapped
	public static final String REQUEST_STATUS_DELETED = "X";
	
	public static final String APPROVER_ACTION_NOACTION = "noaction";
	public static final String APPROVER_ACTION_APPROVE = "approve";
	public static final String APPROVER_ACTION_DISAPPROVE = "disapprove";

	public static final String COMMENT_TYP_CURRENT_WF_REQUEST = "C";
	public static final String COMMENT_TYP_PRIOR_WF_REQUEST = "P";
	public static final String COMMENT_TYP_APPROVAL_COMMENT = "A";
	public static final String COMMENT_TYP_DISAPPROVAL_COMMENT = "D";

	private SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
	
	private boolean selected;
	private int id=0;
	private String payFrequency;
	private String employeeNumber;
	private String employeeFirstName;
	private String employeeMiddleName;
	private String employeeLastName;
	
	private Timestamp submittedDateTime;
	private String submittedDateTimeString;
	private int workflowSequenceNumber;
	
/* ??? DELETE ??? */
	private String submittedDate;
	private String submittedHour;
	private String submittedMinute;
	private String submittedAmPm;
	
	private Timestamp fromDateTime;
	private String fromDateString;
	private String fromTimeString;
	
/* ??? DELETE ??? */
	private String fromDate;
	private String fromHour;
	private String fromMinute;
	private String fromAmPm;
	
	private Timestamp toDateTime;
	private String toDateString;
	private String toTimeString;
	
/* ??? DELETE ??? */
	private String toDate;
	private String toHour;
	private String toMinute;
	private String toAmPm;

	private BigDecimal leaveHoursDaily; // units of value is hours
	private BigDecimal leaveRequested;  // units of value is the units of the leave type selected
	private int leaveNumberDays=0;
	private String leaveUnits;
	private String leaveType;
	private String leaveTypeDescription;
	private String absenceReason;
	private String absenceReasonDescription;
	private String status;
	private String statusDescription;
	
	private String requestComment="";
	 // if a supervisor created this request on behalf of an employee and entered a comment, this field will contain the supervisor's emp number
	private String requestCommentEmpNumber=""; 
	private List<LeaveRequestComment> requestComments;
	private String commentLog="";
	// the approverAction is only used on the Approve Leave Request screen and is not saved to the database
	// values are set on the jsp page as noaction, approve, disapprove
	private String approverAction=APPROVER_ACTION_NOACTION; 
	private String approverComment;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	// for this class, tests of equality are needed only 
	// for leave requests that currently exits in the database
	// so using the id is sufficient
	@Override
	public boolean equals(Object o) {
		boolean returnValue=false;
		if (o instanceof LeaveRequest) {
			if (id == ((LeaveRequest)o).getId()) {
				return true;
			} 
		}
		return returnValue;
	}
	@Override
	public int hashCode() {
		return id;
	}
	
	public boolean isEditable() {
		if (status.equals(LeaveRequest.REQUEST_STATUS_PENDING_APPROVAL) || 
			status.equals(LeaveRequest.REQUEST_STATUS_DISAPPROVED) || 
			(status.equals(LeaveRequest.REQUEST_STATUS_PENDING_PAYROLL) && id!=0)) {
			return true;
	} else {
			return false;
		}
	}
	
	// test whether the leave request corresponds to an "empty" row in the create/edit table
	public boolean isEmpty() {
		if (
			(this.fromDate.trim().equals("") || this.fromDate.equals("  -  -    ")) &&
			(this.toDate.trim().equals("") || this.toDate.equals("  -  -    ")) &&
			(this.fromHour.trim().equals("")) &&
			(this.fromMinute.trim().equals("")) &&
			(this.toHour.trim().equals("")) &&
			(this.toMinute.trim().equals("")) &&
			(this.leaveHoursDaily.compareTo(BigDecimal.ZERO) == 0)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPayFrequency() {
		return payFrequency;
	}

	public void setPayFrequency(String payFrequency) {
		this.payFrequency = payFrequency;
	}

	public String getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public String getEmployeeFirstName() {
		return employeeFirstName;
	}

	public void setEmployeeFirstName(String employeeFirstName) {
		this.employeeFirstName = employeeFirstName;
	}

	public String getEmployeeMiddleName() {
		return employeeMiddleName;
	}

	public void setEmployeeMiddleName(String employeeMiddleName) {
		this.employeeMiddleName = employeeMiddleName;
	}

	public String getEmployeeLastName() {
		return employeeLastName;
	}

	public void setEmployeeLastName(String employeeLastName) {
		this.employeeLastName = employeeLastName;
	}

	public String getEmployeeLabel() {
		StringBuffer labelSB = new StringBuffer((employeeNumber.trim().length()==0 ? "000000" : employeeNumber))
			.append(" : ").append(employeeLastName).append(",").append(employeeFirstName);
		if (employeeMiddleName!=null && employeeMiddleName.length() > 0) {
			labelSB.append(" ").append(employeeMiddleName.substring(0,1));
		}
		return labelSB.toString();
	}	

	public Timestamp getSubmittedDateTime() {
		return submittedDateTime;
	}

	public void setSubmittedDateTime(Timestamp submittedDateTime) {
		this.submittedDateTime = submittedDateTime;
	}

	public String getSubmittedDateTimeString() {
		return submittedDateTimeString;
	}

	public void setSubmittedDateTimeString(String submittedDateTimeString) {
		this.submittedDateTimeString = submittedDateTimeString;
	}

	public int getWorkflowSequenceNumber() {
		return workflowSequenceNumber;
	}

	public void setWorkflowSequenceNumber(int workflowSequenceNumber) {
		this.workflowSequenceNumber = workflowSequenceNumber;
	}

	public String getSubmittedDate() {
		return submittedDate;
	}

	public void setSubmittedDate(String submittedDate) {
		this.submittedDate = submittedDate;
	}

	public String getSubmittedHour() {
		return submittedHour;
	}

	public void setSubmittedHour(String submittedHour) {
		this.submittedHour = submittedHour;
	}

	public String getSubmittedMinute() {
		return submittedMinute;
	}

	public void setSubmittedMinute(String submittedMinute) {
		this.submittedMinute = submittedMinute;
	}

	public String getSubmittedAmPm() {
		return submittedAmPm;
	}

	public void setSubmittedAmPm(String submittedAmPm) {
		this.submittedAmPm = submittedAmPm;
	}

	public Timestamp getFromDateTime() {
		return fromDateTime;
	}

	public void setFromDateTime(Timestamp fromDateTime) {
		this.fromDateTime = fromDateTime;
	}

	public String getFromDateString() {
		return fromDateString;
	}

	public void setFromDateString(String fromDateString) {
		this.fromDateString = fromDateString;
	}

	public String getFromTimeString() {
		return fromTimeString;
	}

	public void setFromTimeString(String fromTimeString) {
		this.fromTimeString = fromTimeString;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getFromHour() {
		return fromHour;
	}

	public void setFromHour(String fromHour) {
		this.fromHour = fromHour;
	}

	public String getFromMinute() {
		return fromMinute;
	}

	public void setFromMinute(String fromMinute) {
		this.fromMinute = fromMinute;
	}

	public String getFromAmPm() {
		return fromAmPm;
	}

	public void setFromAmPm(String fromAmPm) {
		this.fromAmPm = fromAmPm;
	}

	public Timestamp getToDateTime() {
		return toDateTime;
	}

	public void setToDateTime(Timestamp toDateTime) {
		this.toDateTime = toDateTime;
	}

	public String getToDateString() {
		return toDateString;
	}

	public void setToDateString(String toDateString) {
		this.toDateString = toDateString;
	}

	public String getToTimeString() {
		return toTimeString;
	}

	public void setToTimeString(String toTimeString) {
		this.toTimeString = toTimeString;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getToHour() {
		return toHour;
	}

	public void setToHour(String toHour) {
		this.toHour = toHour;
	}

	public String getToMinute() {
		return toMinute;
	}

	public void setToMinute(String toMinute) {
		this.toMinute = toMinute;
	}

	public String getToAmPm() {
		return toAmPm;
	}

	public void setToAmPm(String toAmPm) {
		this.toAmPm = toAmPm;
	}

	public BigDecimal getLeaveHoursDaily() {
		return leaveHoursDaily;
	}

	public void setLeaveHoursDaily(BigDecimal leaveHoursDaily) {
		this.leaveHoursDaily = leaveHoursDaily.setScale(3, BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal getLeaveRequested() {
		return leaveRequested;
	}

	public void setLeaveRequested(BigDecimal leaveRequested) {
		this.leaveRequested = leaveRequested.setScale(3, BigDecimal.ROUND_HALF_UP);
	}

	public String getLeaveRequestedLabel() {
		return (new DecimalFormat("0.000")).format(leaveRequested);
	}
	
	public int getLeaveNumberDays() {
		return leaveNumberDays;
	}

	public void setLeaveNumberDays(int leaveNumberDays) {
		this.leaveNumberDays = leaveNumberDays;
	}

	public String getLeaveUnits() {
		return leaveUnits;
	}

	public void setLeaveUnits(String leaveUnits) {
		this.leaveUnits = leaveUnits;
	}

	public String getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}

	public String getLeaveTypeDescription() {
		return leaveTypeDescription;
	}

	public void setLeaveTypeDescription(String leaveTypeDescription) {
		this.leaveTypeDescription = leaveTypeDescription;
	}

	public String getAbsenceReason() {
		return absenceReason;
	}

	public void setAbsenceReason(String absenceReason) {
		this.absenceReason = absenceReason;
	}

	public String getAbsenceReasonDescription() {
		return absenceReasonDescription;
	}

	public void setAbsenceReasonDescription(String absenceReasonDescription) {
		this.absenceReasonDescription = absenceReasonDescription;
	}

	public String getRequestComment() {
		return requestComment;
	}

	public void setRequestComment(String requestComment) {
		this.requestComment = requestComment;
	}

	public String getRequestCommentEmpNumber() {
		return requestCommentEmpNumber;
	}

	public void setRequestCommentEmpNumber(String requestCommentEmpNumber) {
		this.requestCommentEmpNumber = requestCommentEmpNumber;
	}

	public List<LeaveRequestComment> getRequestComments() {
		return requestComments;
	}

	public void setRequestComments(List<LeaveRequestComment> requestComments) {
		this.requestComments = requestComments;
		if (requestComments!=null && requestComments.size()>0) {
			// create the comment log
			StringBuilder strBuf = new StringBuilder();
			for (LeaveRequestComment leaveRequestComment : requestComments) {
				if (requestComments.get(0).getId()!=leaveRequestComment.getId()) {
					strBuf.append("\n\n");
				}
				strBuf.append("<span style='font-size:12px;font-style:italic;'>&#10095;&#10095;&#10095; ").append(leaveRequestComment.getCommentDateString()).append(" ").append(leaveRequestComment.getCommentTimeString()); 
			
				if (leaveRequestComment.getCommentType().equals(LeaveRequestComment.REQUEST_COMMENT_TYPE_REQUEST_CURRENT_WF)) {
					strBuf.append(" Request Comment");
				} else if (leaveRequestComment.getCommentType().equals(LeaveRequestComment.REQUEST_COMMENT_TYPE_REQUEST_PRIOR_WF)) {
					strBuf.append(" Request Comment");
				} else if (leaveRequestComment.getCommentType().equals(LeaveRequestComment.REQUEST_COMMENT_TYPE_APPROVAL)) {
					strBuf.append(" Approval Comment");
				} else if (leaveRequestComment.getCommentType().equals(LeaveRequestComment.REQUEST_COMMENT_TYPE_DISAPPROVAL)) {
					strBuf.append(" Disapproval Comment");
				}
				strBuf.append("</span>\n");
				strBuf.append("<span style='font-size:12px;font-style:italic;'>&#10095;&#10095;&#10095; ").append(leaveRequestComment.getCommentEmp().getSelectOptionLabel());
				strBuf.append("</span>\n").append(leaveRequestComment.getComment());
			}
			this.commentLog = strBuf.toString();
		} else {
			this.commentLog = "";
		}
	}

	public String getCommentLog() {
		return commentLog;
	}

	public void setCommentLog(String commentLog) {
		this.commentLog = commentLog;
	}

	public String getNewestComment() {
		String newestComment = "";
		if (requestComments!=null && requestComments.size()>0) {
			newestComment = requestComments.get(requestComments.size()-1).getComment();
		} 
		return newestComment;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusDescription() {
		return statusDescription;
	}

	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}

	public String getApproverAction() {
		return approverAction;
	}

	public void setApproverAction(String approverAction) {
		this.approverAction = approverAction;
	}

	public String getApproverComment() {
		return approverComment;
	}

	public void setApproverComment(String approverComment) {
		this.approverComment = approverComment;
	}
	
	public int getRequestNumberDays () {
		int leaveNumberDays = 0;
		
		try {
			String localFromDate = this.fromDate;
			String localToDate = this.toDate;
			// a correct fromDate would consist of 10 characters in the format "MM-dd-yyyy"
			if (localFromDate==null || localFromDate.trim().length()!=10) {
				localFromDate = (this.fromDateString==null) ? "" : this.fromDateString;
			}
			if (localToDate==null || localToDate.trim().length()!=10) {
				localToDate = (this.toDateString==null) ? "" : this.toDateString;
			}
			Date fromDateObj = dateFormat.parse(localFromDate);
			Date toDateObj = dateFormat.parse(localToDate);
			leaveNumberDays = ((int)((toDateObj.getTime() - fromDateObj.getTime())/(1000*60*60*24))) + 1;
		} catch (ParseException e) {
		}
		return leaveNumberDays;
	}

}
