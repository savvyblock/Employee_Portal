package net.esc20.txeis.EmployeeAccess.service.api;

import java.util.List;
import java.util.Map;

import net.esc20.txeis.EmployeeAccess.domainobject.Bank;
import net.esc20.txeis.EmployeeAccess.domainobject.EmployeeInfo;
import net.esc20.txeis.EmployeeAccess.domainobject.Job;
import net.esc20.txeis.EmployeeAccess.domainobject.PayInfo;
import net.esc20.txeis.EmployeeAccess.domainobject.Stipend;
import net.esc20.txeis.EmployeeAccess.domainobject.User;
import net.esc20.txeis.EmployeeAccess.domainobject.PayPrint;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.Frequency;
import net.esc20.txeis.EmployeeAccess.domainobject.report.IReport;
import net.esc20.txeis.EmployeeAccess.domainobject.report.ParameterReport;

public interface IJobService
{
	List<Frequency> getFrequencies(Map<Frequency,?> map);
	Map<Frequency,List<Job>> retrieveJobs(String employeeNumber);
	Map<Frequency,List<Stipend>> retrieveStipends(String employeeNumber);
	Map<Frequency,List<Bank>> retrieveAccounts(String employeeNumber);
	Map<Frequency,PayInfo> retrievePayInfo(String employeeNumber, List<Frequency> frequencies);
	User retrieveUser(String employeeNumber);
	Map<Frequency,String> retrievePayCampuses(String employeeNumber);
	EmployeeInfo retrieveEmployeeInfo(String employeeNumber);
	String getMessage();
	PayPrint generatePayPrint(User user, EmployeeInfo employeeInfo);
	IReport setupReport(ParameterReport report, PayPrint parameters);
}