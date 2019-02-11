package net.esc20.txeis.EmployeeAccess.dao.api;

import java.util.List;

import net.esc20.txeis.EmployeeAccess.domainobject.Bank;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.Frequency;

public interface IPayrollAccountDao
{
	List<Bank> getAccounts(String employeeNumber);
	List<Bank> getAccounts(String employeeNumber, Frequency frequency);
}