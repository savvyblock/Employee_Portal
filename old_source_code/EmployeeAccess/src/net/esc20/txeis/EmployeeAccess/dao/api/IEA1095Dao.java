package net.esc20.txeis.EmployeeAccess.dao.api;

import java.util.List;
import net.esc20.txeis.EmployeeAccess.domain.EA1095BInfoCommand;
import net.esc20.txeis.EmployeeAccess.domain.EA1095CInfoCommand;
import net.esc20.txeis.EmployeeAccess.domainobject.District;
import net.esc20.txeis.common.domainobjects.ReportingContact;
import net.esc20.txeis.common.util.Page;

public interface IEA1095Dao
{
	public List<String> retrieveEA1095BEmpInfo(String activeTab, String empNbr, String calYr);
	public Page<EA1095BInfoCommand> retrieveEA1095BCovrgInfo (String activeTab, String empNbr, String calYr, String sortColumn, String sortOrder, int pageNbr, int pageSize);
	public List<EA1095CInfoCommand> retrieveEA1095CEmpInfo (String activeTab, String empNbr, String calYr);
	public Page<EA1095CInfoCommand> retrieveEA1095CCovrgInfo (String activeTab, String empNbr, String calYr, String sortColumn, String sortOrder, int pageNbr, int pageSize);
	public String getEA1095BCovrgInfoSQL(String activeTab, String empNbr, String calYr, String sortColumn, String sortOrder, int pageNbr, int pageSize);
	//public String getEA1095CCovrgInfoSQL(String activeTab, String empNbr, String calYr, String sortColumn, String sortOrder, int pageNbr, int pageSize);

	public String retrieveEA1095ElecConsent(String empNbr);
	public String retrieveEA1095ElecConsentMsg();
	boolean updateEA1095ElecConsent(String employeeNumber, String ea1095ElecConsnt);
	public List<String> retrieveAvailableCalYrs(String employeeNumber);
	public List<EA1095BInfoCommand> retrieveEA1095BCovrgInfo(String activeTab, String empNbr, String calYr);
	public District get1095SHOPParams(String calYr);
	public List<EA1095CInfoCommand> retrieveEA1095CCovrgInfo(String activeTab, String empNbr, String calYr);
	public ReportingContact getReportingContact();
}