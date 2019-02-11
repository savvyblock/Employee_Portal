package net.esc20.txeis.EmployeeAccess.domainobject.report;

import java.util.List;

public interface IReportTask {
	
	public void performTask(List<ReportParameter> params, IReport report);
}
