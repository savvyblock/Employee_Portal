package com.esc20.nonDBModels.report;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.esc20.validation.report.IReportValidator;

import net.sf.jasperreports.engine.JRDataSource;


public class ParameterReport implements IReport {

	protected String id;
	protected String title;
	protected String fileName;
	protected String helpPath;
	protected JRDataSource dataSource = null;
	protected Map<String, String> bonusReports = new LinkedHashMap<String, String>();
	protected List<ReportParameter> parameters = new ArrayList<ReportParameter>();
	protected Map<String, String> sortColumns = new LinkedHashMap<String, String>();
	protected List<IReportValidator> reportValidators = new ArrayList<IReportValidator>(); 
	protected List<IReportTask> preReportTasks = new ArrayList<IReportTask>();
	protected List<IReportTask> postReportTasks = new ArrayList<IReportTask>();
	protected boolean sortable = true;
	protected boolean filterable = true;
	protected String errorMsg = "";

	public String getId() {
		return id;
	}

	public void setId(String reportId) {
		this.id = reportId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String reportName) {
		this.title = reportName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getHelpPath() {
		return helpPath;
	}

	public void setHelpPath(String helpPath) {
		this.helpPath = helpPath;
	}

	public JRDataSource getDataSource() {
		// if the datasource is left null it will be assumed that the datasource is to come from the database
		return dataSource;
	}
	
	public void setDataSource(JRDataSource dataSource) {
		this.dataSource = dataSource;
	}

	public List<ReportParameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<ReportParameter> parameters) {
		this.parameters = parameters;
	}

	public Map<String,String> getSortColumns() {
		return sortColumns;
	}

	public void setSortColumns(Map<String,String> sortColumns) {
		this.sortColumns = sortColumns;
	}

	public List<IReportValidator> getReportValidators() {
		return reportValidators;
	}

	public void setReportValidators(List<IReportValidator> reportValidators) {
		this.reportValidators = reportValidators;
	}

	public Map<String, String> getBonusReports() {
		return bonusReports;
	}

	public void setBonusReports(Map<String, String> bonusReports) {
		this.bonusReports = bonusReports;
	}

	public void runPostReport() {
		
		for (IReportTask task : postReportTasks) {
			task.performTask(parameters, this);
		}
	}

	public void runPreReport() {
		
		for (IReportTask task : preReportTasks) {
			task.performTask(parameters, this);
		}
	}

	public void setPreReportTasks(List<IReportTask> preReportTasks) {
		this.preReportTasks = preReportTasks;
	}

	public void setPostReportTasks(List<IReportTask> postReportTasks) {
		this.postReportTasks = postReportTasks;
	}

	public boolean isSortable() {
		return sortable;
	}

	public void setSortable(boolean sortable) {
		this.sortable = sortable;
	}

	public boolean isFilterable() {
		return filterable;
	}

	public void setFilterable(boolean filterable) {
		this.filterable = filterable;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}	
}
