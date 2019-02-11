package net.esc20.txeis.EmployeeAccess.dao.api;

import java.util.Date;
import java.util.List;

import net.esc20.txeis.EmployeeAccess.domainobject.Leave;
import net.esc20.txeis.EmployeeAccess.domainobject.LeaveInfo;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.Frequency;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.ICode;

public interface ILeaveDao
{
	List<LeaveInfo> getLeaveInfo(String employeeNumber);
	List<Leave> getLeaves(String employeeNumber, Frequency frequency, Date from, Date to, String leaveType);
	List<ICode> getAvailableLeaveTypes(String employeeNumber, Frequency frequency);
}