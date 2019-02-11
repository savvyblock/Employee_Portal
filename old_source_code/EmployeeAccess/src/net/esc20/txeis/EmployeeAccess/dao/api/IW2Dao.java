package net.esc20.txeis.EmployeeAccess.dao.api;

import java.util.List;

import net.esc20.txeis.EmployeeAccess.domainobject.W2Info;

public interface IW2Dao
{
	List<String> getAvailableYears(String employeeNumber);
	public String retrieveEA1095ElecConsent(String empNbr);
	public String retrieveEA1095ElecConsentMsg();
	W2Info getW2Info(String employeeNumber, String year);
	boolean updateW2ElecConsent(String employeeNumber, String elecConsntW2);
}