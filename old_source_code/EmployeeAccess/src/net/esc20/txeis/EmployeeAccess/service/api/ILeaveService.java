package net.esc20.txeis.EmployeeAccess.service.api;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.esc20.txeis.EmployeeAccess.domainobject.Leave;
import net.esc20.txeis.EmployeeAccess.domainobject.LeaveInfo;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.Frequency;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.ICode;

public interface ILeaveService
{
	Map<Frequency,List<LeaveInfo>> retrieveLeaveInfos(String employeeNumber, boolean removeZeroedOutLeaveTypes);
	List<Frequency> getAvailableFrequencies(Map<Frequency,List<LeaveInfo>> leaveInfos);
	Frequency getInitialFrequency(List<Frequency> frequencies);
	List<Leave> retrieveLeaves(String employeeNumber, Frequency frequency, Date from, Date to, String leaveType);
	List<Leave> getBlankLeaves();
	List<ICode> getAvailableLeaveTypes(String employeeNumber, Frequency frequency);
	String getMessage();
}