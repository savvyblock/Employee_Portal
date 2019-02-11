package net.esc20.txeis.EmployeeAccess.service.api;

import java.util.List;
import java.util.Map;

import net.esc20.txeis.EmployeeAccess.domainobject.Deduction;
import net.esc20.txeis.EmployeeAccess.domainobject.PayInfo;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.Frequency;

public interface IDeductionService
{
	List<Deduction> retrieveDeductions(String employeeNumber, Frequency frequency);
	Map<Frequency,List<Deduction>> retrieveAllDeductions(String employeeNumber);
	PayInfo retrievePayInfo(String employeeNumber, Frequency frequency);
	Map<Frequency,PayInfo> retrieveAllPayInfo(String employeeNumber);
	String getMessage();
	List<Frequency> getAvailableFrequencies(String employeeNumber);
	Frequency getInitialFrequency(List<Frequency> frequencies);
}