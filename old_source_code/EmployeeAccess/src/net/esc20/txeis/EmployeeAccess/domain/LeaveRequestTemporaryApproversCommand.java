package net.esc20.txeis.EmployeeAccess.domain;

import java.io.Serializable;
import java.util.List;

import net.esc20.txeis.EmployeeAccess.domainobject.LeaveEmployeeData;
import net.esc20.txeis.EmployeeAccess.domainobject.LeaveRequest;
import net.esc20.txeis.EmployeeAccess.domainobject.LeaveRequestError;
import net.esc20.txeis.EmployeeAccess.domainobject.LeaveTemporaryApprover;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.LeaveParameters;

public class LeaveRequestTemporaryApproversCommand implements Serializable {

	private static final long serialVersionUID = -2213475053922368711L;
	
	private LeaveParameters leaveParameters;
	private List<LeaveTemporaryApprover> temporaryApprovers;
	private List<LeaveTemporaryApprover> emptyTempApproverRow;

	private int numFieldErrors=0;
	private List<LeaveRequestError> fieldErrors;
	
	private List<LeaveEmployeeData> supervisorChain; /* index position 0 is the currently logged in user */
	private int supervisorChainLevel=0;
	private List<LeaveEmployeeData> directReportEmployees;
	private String selectedDirectReportEmployeeNumber="";
	private String selectDirectReportSelectOptionLabel="";

	public LeaveParameters getLeaveParameters() {
		return leaveParameters;
	}
	public void setLeaveParameters(LeaveParameters leaveParameters) {
		this.leaveParameters = leaveParameters;
	}
	public List<LeaveTemporaryApprover> getTemporaryApprovers() {
		return temporaryApprovers;
	}
	public void setTemporaryApprovers(
			List<LeaveTemporaryApprover> temporaryApprovers) {
		this.temporaryApprovers = temporaryApprovers;
	}
	public List<LeaveTemporaryApprover> getEmptyTempApproverRow() {
		return emptyTempApproverRow;
	}
	public void setEmptyTempApproverRow(List<LeaveTemporaryApprover> emptyTempApproverRow) {
		this.emptyTempApproverRow = emptyTempApproverRow;
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

}
