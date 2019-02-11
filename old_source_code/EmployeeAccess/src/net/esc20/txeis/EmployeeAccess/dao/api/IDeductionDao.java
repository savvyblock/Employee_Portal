package net.esc20.txeis.EmployeeAccess.dao.api;

import java.util.List;

import net.esc20.txeis.EmployeeAccess.domainobject.Deduction;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.Frequency;

public interface IDeductionDao
{
	List<Deduction> getDeductions(String employeeNumber, Frequency frequency);
	List<Frequency> getAvailableFrequencies(String employeeNumber);
}