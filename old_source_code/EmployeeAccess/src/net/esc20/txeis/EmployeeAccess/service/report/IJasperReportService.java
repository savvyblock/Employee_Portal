package net.esc20.txeis.EmployeeAccess.service.report;

import java.sql.SQLException;
import java.util.List;

import net.esc20.txeis.EmployeeAccess.domainobject.report.IReport;
import net.esc20.txeis.EmployeeAccess.domainobject.report.ReportFilter;
import net.esc20.txeis.EmployeeAccess.domainobject.report.ReportSort;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;

/**
 * @author dflint
 *
 */
public interface IJasperReportService {

	/**
	 * @param report
	 * @return
	 * @throws JRException
	 * @throws SQLException
	 */
	public JasperPrint buildReport(IReport report) throws JRException, SQLException;

	/**
	 * @param report
	 * @param sortedColumns
	 * @return
	 * @throws JRException
	 * @throws SQLException
	 */
	public JasperPrint buildReportWithSort(IReport report, List<ReportSort> sortedColumns)
			throws JRException, SQLException;

	/**
	 * @param report
	 * @param filterCriteria
	 * @return
	 * @throws JRException
	 * @throws SQLException
	 */
	public JasperPrint buildReportWithFilter(IReport report, List<ReportFilter> filterCriteria)
			throws JRException, SQLException;

	/**
	 * @param realPath
	 */
	public void setRealPath(String realPath);
	
	public void printReport(JasperPrint jprint);

}