package net.esc20.txeis.EmployeeAccess.dao.api;

import java.util.Map;

import net.esc20.txeis.EmployeeAccess.domainobject.EmployeeInfo;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.Frequency;

public interface IEmployeeDao
{
	EmployeeInfo getEmployeeInfo(String employeeNumber);
	Map<Frequency,String> getPayCampuses(String employeeNumber);
}