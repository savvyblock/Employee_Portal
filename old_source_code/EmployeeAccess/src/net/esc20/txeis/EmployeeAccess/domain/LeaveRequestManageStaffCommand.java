package net.esc20.txeis.EmployeeAccess.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonTypeName;

import net.esc20.txeis.EmployeeAccess.domainobject.LeaveEmployeeData;
import net.esc20.txeis.EmployeeAccess.domainobject.LeaveInfo;
import net.esc20.txeis.EmployeeAccess.domainobject.LeaveRequest;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.AbsenceReason;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.LeaveParameters;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.LeaveType;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.LeaveUnitsConversion;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.PayFrequency;

@JsonTypeName("LeaveRequestManageStaffCommand")
public class LeaveRequestManageStaffCommand implements Serializable {

	private static final long serialVersionUID = 6927340615191100300L;

	private LeaveParameters leaveParameters;
	private String selectedPayFrequencyCode;
	private List<PayFrequency> userPayFrequencies;
	private LeaveInfo leaveInfo;
	private List<LeaveRequest> editLeaveRequests;
	private List<LeaveRequest> emptyLeaveRequestRow;
	private List<LeaveType> leaveTypes;
	private List<AbsenceReason> absenceReasons;
	
	private Date fromDateFilter;
	private String fromDateFilterString;
	private Date toDateFilter;
	private String toDateFilterString;

	private List<LeaveEmployeeData> supervisorChain; /* index position 0 is the currently logged in user */
	private int supervisorChainLevel=0;
	private List<LeaveRequest> leaveRequests;
	private List<LeaveEmployeeData> directReportEmployees;
	private String selectedDirectReportEmployeeNumber="";
	private String selectDirectReportSelectOptionLabel="";
	
	private List<LeaveInfo> leaveInfos;

	public LeaveParameters getLeaveParameters() {
		return leaveParameters;
	}

	public void setLeaveParameters(LeaveParameters leaveParameters) {
		this.leaveParameters = leaveParameters;
	}

	public String getSelectedPayFrequencyCode() {
		return selectedPayFrequencyCode;
	}

	public void setSelectedPayFrequencyCode(String selectedPayFrequencyCode) {
		this.selectedPayFrequencyCode = selectedPayFrequencyCode;
	}

	public List<PayFrequency> getUserPayFrequencies() {
		return userPayFrequencies;
	}

	public void setUserPayFrequencies(List<PayFrequency> userPayFrequencies) {
		this.userPayFrequencies = userPayFrequencies;
	}

	public LeaveInfo getLeaveInfo() {
		return leaveInfo;
	}

	public void setLeaveInfo(LeaveInfo leaveInfo) {
		this.leaveInfo = leaveInfo;
	}

	public List<LeaveRequest> getEditLeaveRequests() {
		return editLeaveRequests;
	}

	public void setEditLeaveRequests(List<LeaveRequest> editLeaveRequests) {
		this.editLeaveRequests = editLeaveRequests;
	}

	public List<LeaveRequest> getEmptyLeaveRequestRow() {
		return emptyLeaveRequestRow;
	}

	public void setEmptyLeaveRequestRow(List<LeaveRequest> emptyLeaveRequestRow) {
		this.emptyLeaveRequestRow = emptyLeaveRequestRow;
	}

	public List<LeaveType> getLeaveTypes() {
		return leaveTypes;
	}

	public void setLeaveTypes(List<LeaveType> leaveTypes) {
		this.leaveTypes = leaveTypes;
	}

	public List<AbsenceReason> getAbsenceReasons() {
		return absenceReasons;
	}

	public void setAbsenceReasons(List<AbsenceReason> absenceReasons) {
		this.absenceReasons = absenceReasons;
	}

	public Date getFromDateFilter() {
		return fromDateFilter;
	}

	public void setFromDateFilter(Date fromDateFilter) {
		this.fromDateFilter = fromDateFilter;
	}

	public String getFromDateFilterString() {
		return fromDateFilterString;
	}

	public void setFromDateFilterString(String fromDateFilterString) {
		this.fromDateFilterString = fromDateFilterString;
	}

	public Date getToDateFilter() {
		return toDateFilter;
	}

	public void setToDateFilter(Date toDateFilter) {
		this.toDateFilter = toDateFilter;
	}

	public String getToDateFilterString() {
		return toDateFilterString;
	}

	public void setToDateFilterString(String toDateFilterString) {
		this.toDateFilterString = toDateFilterString;
	}

	public List<LeaveEmployeeData> getSupervisorChain() {
		return supervisorChain;
	}
	public void setSupervisorChain(List<LeaveEmployeeData> supervisorChain) {
		this.supervisorChain = supervisorChain;
	}
	public int getSupervisorChainLevel() {
		return supervisorChainLevel;
	}
	public void setSupervisorChainLevel(int supervisorChainLevel) {
		this.supervisorChainLevel = supervisorChainLevel;
	}
	public List<LeaveRequest> getLeaveRequests() {
		return leaveRequests;
	}
	public void setLeaveRequests(List<LeaveRequest> leaveRequests) {
		this.leaveRequests = leaveRequests;
	}
	public List<LeaveEmployeeData> getDirectReportEmployees() {
		return directReportEmployees;
	}
	public void setDirectReportEmployees(
			List<LeaveEmployeeData> directReportEmployees) {
		this.directReportEmployees = directReportEmployees;
	}
	public String getSelectedDirectReportEmployeeNumber() {
		return selectedDirectReportEmployeeNumber;
	}
	public void setSelectedDirectReportEmployeeNumber(
			String selectedDirectReportEmployeeNumber) {
		this.selectedDirectReportEmployeeNumber = selectedDirectReportEmployeeNumber;
	}
	
	public String getSelectDirectReportSelectOptionLabel() {
		return selectDirectReportSelectOptionLabel;
	}

	public void setSelectDirectReportSelectOptionLabel(
			String selectDirectReportSelectOptionLabel) {
		this.selectDirectReportSelectOptionLabel = selectDirectReportSelectOptionLabel;
	}

	public List<LeaveInfo> getLeaveInfos() {
		return leaveInfos;
	}

	public void setLeaveInfos(List<LeaveInfo> leaveInfos) {
		this.leaveInfos = leaveInfos;
	}

}
