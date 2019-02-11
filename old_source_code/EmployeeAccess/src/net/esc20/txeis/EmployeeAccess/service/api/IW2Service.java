package net.esc20.txeis.EmployeeAccess.service.api;

import java.util.List;

import net.esc20.txeis.EmployeeAccess.domainobject.Options;
import net.esc20.txeis.EmployeeAccess.domainobject.SickPayInfo;
import net.esc20.txeis.EmployeeAccess.domainobject.User;
import net.esc20.txeis.EmployeeAccess.domainobject.W2Info;
import net.esc20.txeis.EmployeeAccess.domainobject.W2Print;
import net.esc20.txeis.EmployeeAccess.domainobject.report.IReport;
import net.esc20.txeis.EmployeeAccess.domainobject.report.ParameterReport;

public interface IW2Service {
	List<String> retrieveYears(String employeeNumber);
	String getLatestYear(List<String> years);
	public String retrieveEA1095ElecConsent(String empNbr);
	public String retrieveEA1095ElecConsentMsg();
	W2Info retrieveW2Info(String employeeNumber, String year);
	boolean updateW2ElecConsent(String employeeNumber, String w2ElecConsnt);
	List<SickPayInfo> retrieveSickPayInfo(String employeeNumber, String year);
	W2Print generateW2Copy(W2Print print);
	W2Print generateW2Print(W2Info info, User user);
	Options getOptions();
	IReport setupReport(ParameterReport report, W2Print parameters, String year);
	Integer sendEmail(String userFirstName, String userLastName, String userWorkEmail, String userHomeEmail, String w2ElecConsnt);
}