package net.esc20.txeis.EmployeeAccess.service.api;

import java.util.List;
import java.util.Map;

import net.esc20.txeis.EmployeeAccess.domain.EA1095BInfoCommand;
import net.esc20.txeis.EmployeeAccess.domain.EA1095CInfoCommand;
import net.esc20.txeis.EmployeeAccess.domain.EA1095InfoCommand;
import net.esc20.txeis.EmployeeAccess.domainobject.Options;
import net.esc20.txeis.EmployeeAccess.domainobject.User;
import net.esc20.txeis.EmployeeAccess.domainobject.District;
import net.esc20.txeis.common.domainobjects.ReportingContact;
import net.esc20.txeis.common.util.Page;

public interface IEA1095ervice {
	public String retrieveEA1095ElecConsent(String empNbr);
	public String retrieveEA1095ElecConsentMsg();
	boolean updateEA1095ElecConsent(String employeeNumber, String ea1095ElecConsnt);

	public Options getOptions();
	public List<String> retrieveAvailableCalYrs(String employeeNumber);
	public String retrieveLatestYr(List<String> calYrs);

	public List<String> retrieveEA1095BEmpInfo(String activeTab, String empNbr, String calYr);
	public List<EA1095BInfoCommand> retrieveEA1095BCovrgInfo(String activeTab, String empNbr, String calYr);
	public Page<EA1095BInfoCommand> retrieveEA1095BCovrgInfo(String activeTab, String empNbr, String calYr, String sortColumn, String sortOrder, int pageNbr, int pageSize);
	public List<EA1095CInfoCommand> retrieveEA1095CEmpInfo(String activeTab, String empNbr, String calYr);
	public Page<EA1095CInfoCommand> retrieveEA1095CCovrgInfo(String activeTab, String empNbr, String calYr, String sortColumn, String sortOrder, int pageNbr, int pageSize);
	public void buildEA1095CEmpDataDisplay(EA1095InfoCommand ea1095Info);
	public Map<String, String> buildEA1095CCalMonMap();

	public String getEA1095BCovrgInfoSQL(String activeTab, String empNbr, String calYr, String sortColumn, String sortOrder, int pageNbr, int pageSize);
//	public String getEA1095CCovrgInfoSQL(String activeTab, String empNbr, String calYr, String sortColumn, String sortOrder, int pageNbr, int pageSize);
	public User retrieveEmployee(String employeeNumber);
	public District retrieveDistrict();
	public District get1095SHOPParams(String calYr);
	public List<EA1095CInfoCommand> retrieveEA1095CCovrgInfo(String activeTab, String empNbr, String calYr);
	public ReportingContact getReportingContact();
}