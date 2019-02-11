package net.esc20.txeis.EmployeeAccess.dao.api;

import net.esc20.txeis.EmployeeAccess.domainobject.PayInfo;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.Frequency;

public interface IPayDao
{
	PayInfo getPayInfo(String employeeNumber, Frequency frequency);
}