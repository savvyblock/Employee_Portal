package net.esc20.txeis.EmployeeAccess.domain;

import org.codehaus.jackson.annotate.JsonTypeName;

import java.io.Serializable;
import java.util.List;

import net.esc20.txeis.EmployeeAccess.domainobject.LeaveInfo;
import net.esc20.txeis.EmployeeAccess.domainobject.LeaveRequest;
import net.esc20.txeis.EmployeeAccess.domainobject.LeaveRequestError;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.AbsenceReason;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.LeaveParameters;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.LeaveType;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.LeaveUnitsConversion;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.PayFrequency;

@JsonTypeName("LeaveRequestCommand")
public class LeaveRequestCommand implements Serializable {

	private static final long serialVersionUID = 2582635694689410881L;

	LeaveParameters leaveParameters;
	String userEmpNumber;
	String supervisorEmpNumber="";
	private String selectedPayFrequencyCode;
	private List<PayFrequency> userPayFrequencies;
	private List<LeaveRequest> leaveRequests;
	private List<LeaveRequest> editLeaveRequests;
	private List<LeaveRequest> emptyLeaveRequestRow;
	private List<LeaveType> leaveTypes;
	private List<AbsenceReason> absenceReasons;
	
	private List<LeaveInfo> leaveInfos;
	
	int numFieldErrors=0;
	private List<LeaveRequestError> fieldErrors;
	
	public LeaveParameters getLeaveParameters() {
		return leaveParameters;
	}

	public void setLeaveParameters(LeaveParameters leaveParameters) {
		this.leaveParameters = leaveParameters;
	}

	public String getUserEmpNumber() {
		return userEmpNumber;
	}

	public void setUserEmpNumber(String userEmpNumber) {
		this.userEmpNumber = userEmpNumber;
	}

	public String getSupervisorEmpNumber() {
		return supervisorEmpNumber;
	}

	public void setSupervisorEmpNumber(String supervisorEmpNumber) {
		this.supervisorEmpNumber = supervisorEmpNumber;
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

	public List<LeaveRequest> getLeaveRequests() {
		return leaveRequests;
	}

	public void setLeaveRequests(List<LeaveRequest> leaveRequests) {
		this.leaveRequests = leaveRequests;
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

	public List<LeaveInfo> getLeaveInfos() {
		return leaveInfos;
	}

	public void setLeaveInfos(List<LeaveInfo> leaveInfos) {
		this.leaveInfos = leaveInfos;
	}

	public int getNumFieldErrors() {
		return numFieldErrors;
	}

	public void setNumFieldErrors(int numFieldErrors) {
		this.numFieldErrors = numFieldErrors;
	}

	public List<LeaveRequestError> getFieldErrors() {
		return fieldErrors;
	}

	public void setFieldErrors(List<LeaveRequestError> fieldErrors) {
		this.fieldErrors = fieldErrors;
	}


}
