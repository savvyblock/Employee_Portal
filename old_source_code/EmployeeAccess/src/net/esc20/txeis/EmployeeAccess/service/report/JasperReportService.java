package net.esc20.txeis.EmployeeAccess.service.report;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.sql.DataSource;

import net.esc20.txeis.EmployeeAccess.domainobject.RoutedPreferenceFactory;
import net.esc20.txeis.EmployeeAccess.domainobject.report.ICustomReport;
import net.esc20.txeis.EmployeeAccess.domainobject.report.IReport;
import net.esc20.txeis.EmployeeAccess.domainobject.report.ParameterReport;
import net.esc20.txeis.EmployeeAccess.domainobject.report.ReportFilter;
import net.esc20.txeis.EmployeeAccess.domainobject.report.ReportParameter;
import net.esc20.txeis.EmployeeAccess.domainobject.report.ReportParameterDataSource;
import net.esc20.txeis.EmployeeAccess.domainobject.report.ReportSort;
import net.sf.jasperreports.engine.JRBand;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRElement;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRExpressionChunk;
import net.sf.jasperreports.engine.JRGroup;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JRSortField;
import net.sf.jasperreports.engine.JRSubreport;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignExpressionChunk;
import net.sf.jasperreports.engine.design.JRDesignSortField;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
import net.sf.jasperreports.engine.fill.JRSwapFileVirtualizer;
import net.sf.jasperreports.engine.type.SortOrderEnum;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.util.JRSwapFile;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author dflint
 *
 */
public class JasperReportService implements IJasperReportService {


	protected final Log logger = LogFactory.getLog(getClass());
	
	private String reportPath;
	private DataSource dataSource;	
	private String realPath;	
	private RoutedPreferenceFactory prefFactory;
	

	public RoutedPreferenceFactory getPrefFactory() {
		return prefFactory;
	}

	public void setPrefFactory(RoutedPreferenceFactory prefFactory) {
		this.prefFactory = prefFactory;
	}

	public void setReportPath(String reportPath) {
		this.reportPath = reportPath;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void setRealPath(String realPath) {
		this.realPath = realPath;		
	}
	

	/* (non-Javadoc)
	 * @see net.esc20.txeis.common.services.IJasperReportService#buildReport(java.lang.String, java.util.Map)
	 */
	public JasperPrint buildReport(IReport report) throws JRException, SQLException {
		
		JasperPrint result = null;
				
		if (report != null) {
						
			// run any pre-report tasks
			report.runPreReport();
			
			//will insert an error message if one exists
			if (report instanceof ParameterReport) {
				String errorMsg = ((ParameterReport)report).getErrorMsg();
				if (errorMsg != null && !"".equals(errorMsg.trim())) {
					result = new JasperPrint();
					result.setProperty("errorMsg", errorMsg);
					return result;
				}
			}
			
			// load report template
			JasperReport jasperReport = loadTemplate(report);

			// builds JasperPrint object to return
			Map<String, Object> parameters = getParameterMap(report.getParameters()); // moved line here in case preReport() makes changes to parameters
			result = buildJasperPrint(parameters, jasperReport, report.getDataSource());
			
			// run any post-report tasks
			report.runPostReport();
					
		}
		
		return result;		
	}
	
	/* (non-Javadoc)
	 * @see net.esc20.txeis.common.services.IJasperReportService#buildReportWithSort(net.esc20.txeis.common.domainobjects.IReport, java.util.List)
	 */
	public JasperPrint buildReportWithSort(IReport report, List<ReportSort> sortedColumns)
			throws JRException, SQLException {
		
		JasperPrint result = null;
				
		// gets design object created from original report 
		String templateFileName = report.getFileName().trim();
		
		File xmlFile = new File(realPath + reportPath + "/" + templateFileName + ".jrxml");
		JasperDesign design = JRXmlLoader.load(xmlFile);
		
		// Remove existing sort fields from the design object
		JRSortField[] sortFields = new JRSortField[design.getSortFields().length];
		System.arraycopy(design.getSortFields(), 0, sortFields, 0, design.getSortFields().length);
		
		for (int i = 0; i < sortFields.length; i++) {
			design.removeSortField(sortFields[i]);
		}
		
		// Add new sort fields to the design object
		for (ReportSort sortInfo : sortedColumns) {
			JRDesignSortField sortField = new JRDesignSortField();
			sortField.setName(sortInfo.getColumn());
			if (sortInfo.getAscending().equals("decending"))
			{
				sortField.setOrder(SortOrderEnum.DESCENDING);
			}
			else
			{
				sortField.setOrder(SortOrderEnum.ASCENDING);
			}
			design.addSortField(sortField);
		}
		
		// run any pre-report tasks
		report.runPreReport();
		
		// gets compiled report
		JasperReport jasperReport = loadTemplate(design);
		
		// builds JasperPrint object to return
		Map<String, Object> parameters = getParameterMap(report.getParameters()); // moved line here in case preReport() makes changes to parameters
		result = buildJasperPrint(parameters, jasperReport, report.getDataSource());
		
		// run any post-report tasks
		report.runPostReport();
		
		return result;
	}
	
	/* (non-Javadoc)
	 * @see net.esc20.txeis.common.services.IJasperReportService#buildReportWithFilter(net.esc20.txeis.common.domainobjects.IReport, java.util.List)
	 */
	public JasperPrint buildReportWithFilter(IReport report, List<ReportFilter> filterCriteria)
			throws JRException, SQLException {
		
		JasperPrint result = null;
				
		// gets design object created from original report
		String templateFileName = report.getFileName().trim();
		
		File xmlFile = new File(realPath + reportPath + "/" + templateFileName + ".jrxml");
		JasperDesign design = JRXmlLoader.load(xmlFile);
		
		// filter
		JRDesignExpression exp = createFilterExpression(filterCriteria);
		design.setFilterExpression(exp);
		
		// run any pre-report tasks
		report.runPreReport();
		
		// gets compiled report
		JasperReport jasperReport = loadTemplate(design);
		
		// builds JasperPrint object to return
		Map<String, Object> parameters = getParameterMap(report.getParameters()); // moved line here in case preReport() makes changes to parameters
		result = buildJasperPrint(parameters, jasperReport, report.getDataSource());
		
		// run any post-report tasks
		report.runPostReport();
		
		return result;
	}

	/**
	 * @param parameters
	 * @param jasperReport
	 * @return
	 * @throws JRException
	 * @throws SQLException
	 */
	private JasperPrint buildJasperPrint(Map<String, Object> parameters, JasperReport jasperReport, JRDataSource jrDataSource)
			throws JRException, SQLException {
		
		JasperPrint result = null;
		JRSwapFile swapFile = new JRSwapFile(realPath + reportPath + "/",1024,1024);
		JRSwapFileVirtualizer virtualizer = new JRSwapFileVirtualizer(2, swapFile, true);
		
		//JRGzipVirtualizer virtualizer = new JRGzipVirtualizer(10);
		//JRFileVirtualizer virtualizer = new JRFileVirtualizer(2,"c:/reports");
		parameters.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);
		JRGroup[] groups = jasperReport.getMainDataset().getGroups();
		if(jasperReport.getName().equals("sch0160") || jasperReport.getName().equals("sch0150")){
			for(JRGroup grp : groups){
				if(grp.getGroupFooterSection().getBands() != null){
					for (JRBand band : grp.getGroupFooterSection().getBands()) {
						JRElement[] elements = band.getElements();
						for (JRElement element : elements) {
							if (element instanceof JRSubreport) {
								String template = ((JRSubreport)element).getKey();
								JasperReport subJasperReport = loadTemplate(template);
								parameters.put(template, subJasperReport);
							}
						}
					}
				}
			}
		}
		// put sub report parameters
		if (jasperReport.getDetailSection().getBands() != null) {
			for (JRBand band : jasperReport.getDetailSection().getBands()) {
				JRElement[] elements = band.getElements();
				for (JRElement element : elements) {
					if (element instanceof JRSubreport) {
						String template = ((JRSubreport)element).getKey();
						JasperReport subJasperReport = loadTemplate(template);
						parameters.put(template, subJasperReport);
						//jf20120724 process sub report's sub report(s)
						for (JRBand subBand : subJasperReport.getDetailSection().getBands()) {
							for(JRElement subElement : subBand.getElements())
							{
								if (subElement instanceof JRSubreport) {
									String subTemplate = ((JRSubreport)subElement).getKey();
									JasperReport subSubJasperReport = loadTemplate(subTemplate);
									parameters.put(subTemplate, subSubJasperReport);
								}
							}
						}
					}
				}
			}
		}

		Connection con = null;

		// check to see if a jrdatasource was passed in, if not use the database connection
		if (jrDataSource == null) {
			try {
				con = dataSource.getConnection();
				// fill report
				result = JasperFillManager.fillReport(jasperReport, parameters, con);
				virtualizer.setReadOnly(true);
			} finally {
				if (con != null) {
					con.close();
				}
			}
		} else {
			//jf20120724 for DRptPay report pass the connection in the parameters, because
			//jf20120724 the main report has a jrDataSource and no sql, but the subreports have
			//jf20120714 sql and need a DB connection, so must pass this connection through the
			//jf20120714 parameters.
			if ("DRptPay".equals(jasperReport.getName()) || "DHrs2500WageandearningstmtTab".equals(jasperReport.getName())) {
				try {
					con = dataSource.getConnection();
					parameters.put("subRptConnection", con);
					result = JasperFillManager.fillReport(jasperReport, parameters, jrDataSource);
					virtualizer.setReadOnly(true);
				}
				finally {
					if (con != null) {
						con.close();
					}
				}
			} else {
				result = JasperFillManager.fillReport(jasperReport, parameters, jrDataSource);
				virtualizer.setReadOnly(true);
			}
		}
		
		//virtualizer.cleanup();
		return result;
	}

	/**
	 * @param template
	 * @return
	 * @throws JRException
	 */
	private JasperReport loadTemplate(IReport report) throws JRException {

		// get the report id and make sure the report is lower case
		String template = report.getFileName().toLowerCase();

		File directoryPath = new File(realPath + reportPath);
		File jasperFile = null;

		// check to see if this is a custom report
		if (report instanceof ICustomReport) {
			jasperFile = ((ICustomReport) report).compileReport(directoryPath.getPath());

		} 
		else {
			// compile report
			File xmlFile = new File(realPath + reportPath + "/" + template + ".jrxml");
			JasperCompileManager.compileReportToFile(xmlFile.getPath());
			jasperFile = new File(realPath + reportPath + "/" + template + ".jasper");

		}

		// check compiled report
		if (!jasperFile.exists()) {
			throw new JRRuntimeException("File " + template + ".jasper not found. The report design must be compiled first.");
		}
		// load
		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperFile.getPath());

		return jasperReport;
	}
	
	/**
	 * @param template
	 * @return
	 * @throws JRException
	 */
	private JasperReport loadTemplate(String template) throws JRException {
		
		// make sure the report is lower case
		template = template.toLowerCase();
		// compile report
		File xmlFile = new File(realPath + reportPath + "/" + template + ".jrxml");
		
		JasperCompileManager.compileReportToFile(xmlFile.getPath(), realPath + reportPath + "/" + template + ".jasper");
		
		// check compiled report
		File jasperFile = new File(realPath + reportPath + "/" + template + ".jasper");
		if (!jasperFile.exists()) {
			throw new JRRuntimeException("File " + template + 
					".jasper not found. The report design must be compiled first.");
		}
		// load
		JasperReport jasperReport = (JasperReport)JRLoader.loadObject(jasperFile.getPath());
		
		return jasperReport;
	}
	
	/**
	 * @param design
	 * @param report
	 * @return
	 * @throws JRException
	 */
	private JasperReport loadTemplate(JasperDesign design) throws JRException {
		
		// compiles report using modified design object		
		JasperReport jasperReport = JasperCompileManager.compileReport(design);
		
		return jasperReport;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.esc20.txeis.common.services.IJasperReportService#getParameterMap(java.util.List)
	 */
	private Map<String, Object> getParameterMap(List<ReportParameter> parameters) {
		
		// create the map of parameters
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		
		for (ReportParameter reportParameter : parameters) {
			logger.debug("adding reportParameter = " + reportParameter.getName());
			if (reportParameter instanceof ReportParameterDataSource) {
				parameterMap.put(reportParameter.getName(), ((ReportParameterDataSource)reportParameter).getDataSource());
			}
			else {
				if (reportParameter.getQuotedValue() != null)
				{
					if(reportParameter.isUseLiteralValue())
						parameterMap.put(reportParameter.getName(), reportParameter.getValue());
					else
						parameterMap.put(reportParameter.getName(), reportParameter.getQuotedValue());
				}
				
				if (reportParameter.isMultiSelect()) 
					parameterMap.put(reportParameter.getAllName(), reportParameter.getQuotedAllValue());
			}
		}	
		
		return parameterMap;
	}
	
	/**
	 * @param reportFilters
	 * @return
	 */
	private JRDesignExpression createFilterExpression(List<ReportFilter> reportFilters) {
		
		JRDesignExpression expression = new JRDesignExpression();
		expression.setValueClass(Boolean.class);

		JRDesignExpressionChunk start = new JRDesignExpressionChunk();
		start.setText("new Boolean(");
		start.setType(JRExpressionChunk.TYPE_TEXT);
		expression.addChunk(start);

		for (ReportFilter reportFilter : reportFilters) {
			String field = reportFilter.getColumn();
			String value = reportFilter.getValue();
			String logicalOp = null;
			
			String filterOperator = reportFilter.getComparator();
			
			if (reportFilter.getLogicalOperator() != null 
					&& reportFilter.getLogicalOperator().trim().length() > 0) {
				
				logicalOp = ((reportFilter.getLogicalOperator()).equalsIgnoreCase("AND")) ? " && " : " || ";
			}

			JRDesignExpressionChunk fieldStartChunk = new JRDesignExpressionChunk();
			fieldStartChunk.setText("((");
			fieldStartChunk.setType(JRExpressionChunk.TYPE_TEXT);
			expression.addChunk(fieldStartChunk);

			JRDesignExpressionChunk fieldChunk = new JRDesignExpressionChunk();
			fieldChunk.setText(field);
			fieldChunk.setType(JRExpressionChunk.TYPE_FIELD);
			expression.addChunk(fieldChunk);

			JRDesignExpressionChunk compareToChunk = new JRDesignExpressionChunk();
			compareToChunk.setText(").compareTo(\"" + value + "\")"
					+ getCompareToExp(filterOperator) + ")");
			compareToChunk.setType(JRExpressionChunk.TYPE_TEXT);
			expression.addChunk(compareToChunk);

			if (logicalOp != null) {
				JRDesignExpressionChunk logicalOpChunk = new JRDesignExpressionChunk();
				logicalOpChunk.setText(logicalOp);
				logicalOpChunk.setType(JRExpressionChunk.TYPE_TEXT);
				expression.addChunk(logicalOpChunk);
			}
		}

		JRDesignExpressionChunk end = new JRDesignExpressionChunk();
		end.setText(")");
		end.setType(JRExpressionChunk.TYPE_TEXT);
		expression.addChunk(end);

		return expression;
	}
	
	/**
	 * @param filterOp
	 * @return
	 */
	private String getCompareToExp(String filterOp) {
		
		String exp = "";
		
		if (filterOp.equalsIgnoreCase("EQUAL"))
			exp = " == 0 ";
		else if (filterOp.equalsIgnoreCase("GT"))
			exp = " > 0 "; //1
		else if (filterOp.equalsIgnoreCase("LT"))
			exp = " < 0 "; //-1
		else if (filterOp.equalsIgnoreCase("GTE"))
			exp = " >= 0 "; //1 or 0
		else if (filterOp.equalsIgnoreCase("LTE"))
			exp = " <= 0 "; //-1 or 0
		else if (filterOp.equalsIgnoreCase("NOTEQUAL"))
			exp = " != 0 "; // 1 or -1
		
		return exp;
	}
	
	public void printReport(JasperPrint jprint) {

		PrintService printService = getPrintService();

		JRPrintServiceExporter exporter = new JRPrintServiceExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jprint);
		exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE, printService);
		exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, printService.getAttributes());
		exporter.setParameter(JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, getPrintAttributes());
		exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
		exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
		exporter.setParameter(JRPrintServiceExporterParameter.OFFSET_X,5);
		
		try {
			exporter.exportReport();
		} catch (JRException e) {
			throw new RuntimeException(e);
		}
	}
	
	private PrintService getPrintService() {
		for(PrintService service : PrintServiceLookup.lookupPrintServices(null,null)) {
			if (service.getName().trim().equalsIgnoreCase(prefFactory.getItem().getReportPrinter())) {
				return service;
			}
		}
		
		return PrintServiceLookup.lookupDefaultPrintService();
	}
	
	private PrintRequestAttributeSet getPrintAttributes() {
		PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
		printRequestAttributeSet.add(new Copies(1));
		return printRequestAttributeSet;
	}






	
}
