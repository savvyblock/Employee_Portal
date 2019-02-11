package net.esc20.txeis.EmployeeAccess.domainobject.report;

import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;

public interface IReport {

	public String getId();
	public String getTitle();
	public List<ReportParameter> getParameters();
	public Map<String,String> getSortColumns();
	public String getFileName();
	public JRDataSource getDataSource();
	public void runPreReport();
	public void runPostReport();
}
