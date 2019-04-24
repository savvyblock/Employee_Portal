package com.esc20.nonDBModels.report;

import java.util.List;

public interface IReportTask {
	
	public void performTask(List<ReportParameter> params, IReport report);
}
