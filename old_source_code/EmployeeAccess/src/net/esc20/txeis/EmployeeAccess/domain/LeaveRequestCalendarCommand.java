package net.esc20.txeis.EmployeeAccess.domain;

import java.io.Serializable;
import java.util.List;

import net.esc20.txeis.EmployeeAccess.domainobject.LeaveRequest;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.LeaveParameters;

public class LeaveRequestCalendarCommand implements Serializable {

	private static final long serialVersionUID = -25040526726008030L;

	LeaveParameters leaveParameters;
	String userEmpNumber;
	private List<LeaveRequest> leaveRequests;
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
	public List<LeaveRequest> getLeaveRequests() {
		return leaveRequests;
	}
	public void setLeaveRequests(List<LeaveRequest> leaveRequests) {
		this.leaveRequests = leaveRequests;
	}
}
