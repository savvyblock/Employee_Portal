package com.esc20.service;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esc20.dao.CalendarYearToDateDao;
import com.esc20.model.BhrCalYtd;
import com.esc20.model.BhrEmpDemo;
import com.esc20.nonDBModels.CalYTDPrint;
import com.esc20.nonDBModels.District;
import com.esc20.nonDBModels.EarningsPrint;
import com.esc20.nonDBModels.report.IReport;
import com.esc20.nonDBModels.report.ParameterReport;
import com.esc20.nonDBModels.report.ReportParameter;
import com.esc20.nonDBModels.report.ReportParameterConnection;
import com.esc20.nonDBModels.report.ReportParameterDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

@Service
public class PDFService {

	private Logger logger = LoggerFactory.getLogger(PDFService.class);

	private String reportPath = "reports";
	private DataSource dataSource;	
	private String realPath;	
//	private RoutedPreferenceFactory prefFactory;

	
	
	@Autowired
	private CalendarYearToDateDao calendarYearToDateDao;

	
	public CalendarYearToDateDao getCalendarYearToDateDao() {
		return calendarYearToDateDao;
	}
	public String getReportPath() {
		return reportPath;
	}

	public void setReportPath(String reportPath) {
		this.reportPath = reportPath;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public String getRealPath() {
		return realPath;
	}

	public void setRealPath(String realPath) {
		this.realPath = realPath;
	}
	
	//jf20140110 Print Report on Current Calendar YTD screen
	public IReport setupReport(ParameterReport report, List<CalYTDPrint> parameters) 
	{
		report.getParameters().clear();
		List<CalYTDPrint> forms = new ArrayList<CalYTDPrint>();
		for (int i = 0; i < parameters.size(); i++) {
			forms.add(parameters.get(i));
		}
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(forms);
		// Set the JRXML report
		report.setFileName("CalYTDPrint");

		report.setDataSource(dataSource);

		return report;
	}
    public Connection getConn() throws Exception {
    	return calendarYearToDateDao.getConn();
    }
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
			
			try {
				if (report.getDataSource() != null) {
					result = JasperFillManager.fillReport(jasperReport,parameters,report.getDataSource());
				} else {
					result = JasperFillManager.fillReport(jasperReport,parameters, calendarYearToDateDao.getConn());
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//result = buildJasperPrint(parameters, jasperReport, report.getDataSource());
			
			// run any post-report tasks
			report.runPostReport();
					
		}
		
		return result;		
	}

//private JasperPrint buildJasperPrint(Map<String, Object> parameters, JasperReport jasperReport, JRDataSource jrDataSource)
//		throws JRException, SQLException {
//	
//	JasperPrint result = null;
//	JRSwapFile swapFile = new JRSwapFile(realPath + reportPath + "/",1024,1024);
//	JRSwapFileVirtualizer virtualizer = new JRSwapFileVirtualizer(2, swapFile, true);
//	
//	//JRGzipVirtualizer virtualizer = new JRGzipVirtualizer(10);
//	//JRFileVirtualizer virtualizer = new JRFileVirtualizer(2,"c:/reports");
//	parameters.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);
//	JRGroup[] groups = jasperReport.getMainDataset().getGroups();
//	if(jasperReport.getName().equals("sch0160") || jasperReport.getName().equals("sch0150")){
//		for(JRGroup grp : groups){
//			if(grp.getGroupFooterSection().getBands() != null){
//				for (JRBand band : grp.getGroupFooterSection().getBands()) {
//					JRElement[] elements = band.getElements();
//					for (JRElement element : elements) {
//						if (element instanceof JRSubreport) {
//							String template = ((JRSubreport)element).getKey();
//							JasperReport subJasperReport = loadTemplate(template);
//							parameters.put(template, subJasperReport);
//						}
//					}
//				}
//			}
//		}
//	}
//	// put sub report parameters
//	if (jasperReport.getDetailSection().getBands() != null) {
//		for (JRBand band : jasperReport.getDetailSection().getBands()) {
//			JRElement[] elements = band.getElements();
//			for (JRElement element : elements) {
//				if (element instanceof JRSubreport) {
//					String template = ((JRSubreport)element).getKey();
//					JasperReport subJasperReport = loadTemplate(template);
//					parameters.put(template, subJasperReport);
//					//jf20120724 process sub report's sub report(s)
//					for (JRBand subBand : subJasperReport.getDetailSection().getBands()) {
//						for(JRElement subElement : subBand.getElements())
//						{
//							if (subElement instanceof JRSubreport) {
//								String subTemplate = ((JRSubreport)subElement).getKey();
//								JasperReport subSubJasperReport = loadTemplate(subTemplate);
//								parameters.put(subTemplate, subSubJasperReport);
//							}
//						}
//					}
//				}
//			}
//		}
//	}
//
//	Connection con = null;
//
//	// check to see if a jrdatasource was passed in, if not use the database connection
//	if (jrDataSource == null) {
//		try {
//			con = dataSource.getConnection();
//			// fill report
//			result = JasperFillManager.fillReport(jasperReport, parameters, con);
//			virtualizer.setReadOnly(true);
//		} finally {
//			if (con != null) {
//				con.close();
//			}
//		}
//	} else {
//		//jf20120724 for DRptPay report pass the connection in the parameters, because
//		//jf20120724 the main report has a jrDataSource and no sql, but the subreports have
//		//jf20120714 sql and need a DB connection, so must pass this connection through the
//		//jf20120714 parameters.
//		if ("DRptPay".equals(jasperReport.getName()) || "DHrs2500WageandearningstmtTab".equals(jasperReport.getName())) {
//			try {
//				con = dataSource.getConnection();
//				parameters.put("subRptConnection", con);
//				result = JasperFillManager.fillReport(jasperReport, parameters, jrDataSource);
//				virtualizer.setReadOnly(true);
//			}
//			finally {
//				if (con != null) {
//					con.close();
//				}
//			}
//		} else {
//			result = JasperFillManager.fillReport(jasperReport, parameters, jrDataSource);
//			virtualizer.setReadOnly(true);
//		}
//	}
//	
//	//virtualizer.cleanup();
//	return result;
//}

/**
 * @param template
 * @return
 * @throws JRException
 */
private JasperReport loadTemplate(IReport report) throws JRException {

	String path = System.getProperty("EmployeeAccess.root")+"/reports";
	System.out.println("path: "+ path);
//	// get the report id and make sure the report is lower case
	//String template = "CalYTDPrint"; // report.getFileName();
	String template = report.getFileName();
////
//	File directoryPath = new File(path);
//	File jasperFile = null;
////
////	// check to see if this is a custom report
//	if (report instanceof ICustomReport) {
//		jasperFile = ((ICustomReport) report).compileReport(directoryPath.getPath());
//
//	} 
//	else {
//		// compile report
//		
//		File xmlFile = new File(path + "/" + template + ".jrxml");
//		System.out.println(xmlFile.exists());
//		JasperCompileManager.compileReportToFile(xmlFile.getPath());
//		jasperFile = new File(path + "/" + template + ".jasper");
//		System.out.println(jasperFile.exists());
//	}
//
//	// check compiled report
//	if (!jasperFile.exists()) {
//		throw new JRRuntimeException("File " + template + ".jasper not found. The report design must be compiled first.");
//	}
	
	File jasperFile = new File(path + "/" + template + ".jasper");
	
	// load
	JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperFile.getPath());

	return jasperReport;
}
private Map<String, Object> getParameterMap(List<ReportParameter> parameters) {
	
	// create the map of parameters
	Map<String, Object> parameterMap = new HashMap<String, Object>();
	
	parameterMap.put("SUBREPORT_DIR", System.getProperty("EmployeeAccess.root") + "/reports/");
	
	for (ReportParameter reportParameter : parameters) {
		logger.debug("adding reportParameter = " + reportParameter.getName());
		if (reportParameter instanceof ReportParameterDataSource) {
			parameterMap.put(reportParameter.getName(), ((ReportParameterDataSource)reportParameter).getDataSource());
		}if (reportParameter instanceof ReportParameterConnection) {
			parameterMap.put(reportParameter.getName(), ((ReportParameterConnection)reportParameter).getConnection());
		}else {
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
}
