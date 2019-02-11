package net.esc20.txeis.EmployeeAccess.domain;

import java.io.Serializable;
import java.util.List;

import net.esc20.txeis.EmployeeAccess.domainobject.LeaveEmployeeData;
import net.esc20.txeis.EmployeeAccess.domainobject.LeaveRequest;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.LeaveParameters;

public class LeaveRequestSubmittalsCommand implements Serializable {

	private static final long serialVersionUID = 5495989451349056042L;
	
	private LeaveParameters leaveParameters;
	private String supervisorFirstName;
	private String supervisorLastName;
	private List<LeaveEmployeeData> supervisorChain; /* index position 0 is the currently logged in user */
	private int supervisorChainLevel=0;
	private List<LeaveRequest> leaveRequests;
	private List<LeaveEmployeeData> directReportEmployees;
	private String selectedDirectReportEmployeeNumber="";
	private String selectDirectReportSelectOptionLabel="";

	public LeaveParameters getLeaveParameters() {
		return leaveParameters;
	}
	public void setLeaveParameters(LeaveParameters leaveParameters) {
		this.leaveParameters = leaveParameters;
	}
	public String getSupervisorFirstName() {
		return supervisorFirstName;
	}
	public void setSupervisorFirstName(String supervisorFirstName) {
		this.supervisorFirstName = supervisorFirstName;
	}
	public String getSupervisorLastName() {
		return supervisorLastName;
	}
	public void setSupervisorLastName(String supervisorLastName) {
		this.supervisorLastName = supervisorLastName;
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
}
