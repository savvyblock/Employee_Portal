package com.esc20.service;

import java.io.File;
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
import com.esc20.nonDBModels.report.ICustomReport;
import com.esc20.nonDBModels.report.IReport;
import com.esc20.nonDBModels.report.ParameterReport;
import com.esc20.nonDBModels.report.ReportParameter;
import com.esc20.nonDBModels.report.ReportParameterDataSource;

import net.sf.jasperreports.engine.JRBand;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRElement;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRGroup;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JRSubreport;
import net.sf.jasperreports.engine.JasperCompileManager;
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

	public BhrCalYtd retrieveCalendar(String employeeNumber, String year)
	{
		return calendarYearToDateDao.getCalenderYTD(employeeNumber, year);
		//return calendarYearToDateDao.getCalendars(employeeNumber, year);
	}
	
	//jf20140110 Print Report on Calendar YTD screen
	public List<CalYTDPrint> generateCalYTDPrint(BhrEmpDemo user, District district , BhrCalYtd calendar)
	{
		String dCitySt = "";
		
		dCitySt = district.getCity() + ", " + district.getState() + " " + district.getZip();
		
		if(district.getZip4().length() > 0)
		{
			dCitySt = dCitySt + "-" + district.getZip4();
		}
		
		String eName = "";
		String middleName = user.getNameM().trim();
		if (middleName.length() > 0) {
			middleName = middleName + " ";
		} else {
			middleName = "";
		}
		
		eName = user.getNameF() + " " + middleName + user.getNameL() + " " + user.getGenDescription();   //jf20150113 Display description instead of code fix
		
		List<CalYTDPrint> calYTDRpt = new ArrayList<CalYTDPrint>();
		CalYTDPrint print = null;
		
			print = new CalYTDPrint();
			print.setDname(district.getName());
			print.setDaddress(district.getAddress());
			print.setDcityst(dCitySt);
			
			print.setEname(eName);
			print.setEmployeeNumber(user.getEmpNbr());
			
			print.setCalYr(calendar.getId().getCalYr());
			print.setFrequency(String.valueOf(calendar.getId().getPayFreq()));
		
			// TODO 
			// lastPostedPayDate != null ? new SimpleDateFormat("MM-dd-yyyy").format(lastPostedPayDate) : "no pay date";  
			String postedDate = "no pay date";
			postedDate = postedDate  == null ? "no pay date": postedDate;
			print.setLastPostedPayDate(postedDate);
			
			print.setContractPay(calendar.getContrAmt());
			print.setNonContractPay(calendar.getNoncontrAmt());
			print.setSupplementalPay(calendar.getSupplPayAmt());
			
			print.setWithholdingGross(calendar.getWhGross());
			print.setWithholdingTax(calendar.getWhTax());
			print.setEarnedIncomeCredit(calendar.getEicAmt());
			
			print.setFicaGross(calendar.getFicaGross());
			print.setFicaTax(calendar.getFicaTax());
			
			print.setDependentCare(calendar.getDependCare());
			print.setDependentCareEmployer(calendar.getEmplrDependCare());
			print.setDependentCareExceeds(calendar.getEmplrDependCareTax());
			
			print.setMedicareGross(calendar.getMedGross());
			print.setMedicareTax(calendar.getMedTax());
			
			print.setAnnuityDeduction(calendar.getAnnuityDed());
			print.setRoth403BAfterTax(calendar.getAnnuityRoth());
			print.setTaxableBenefits(calendar.getTaxedBenefits());
			
			print.setAnnuity457Employee(calendar.getEmp457Contrib());
			print.setAnnuity457Employer(calendar.getEmplr457Contrib());
			print.setAnnuity457Withdraw(calendar.getWithdraw457());
			
			print.setNonTrsBusinessExpense(calendar.getNontrsBusAllow());
			print.setNonTrsReimbursementBase(calendar.getNontrsReimbrBase());
			print.setNonTrsReimbursementExcess(calendar.getNontrsReimbrExcess());
			
			print.setMovingExpenseReimbursement(calendar.getMovingExpReimbr());
			print.setNonTrsNonTaxBusinessAllow(calendar.getNontrsNontaxBusAllow());
			print.setNonTrsNonTaxNonPayAllow(calendar.getNontrsNontaxNonpayAllow());
			
			print.setSalaryReduction(calendar.getTrsSalaryRed());
			//TODO 
//			print.setTrsInsurance(calendar.getTrsInsurance());
			
			print.setHsaEmployerContribution(calendar.getHsaEmplrContrib());
			print.setHsaEmployeeSalaryReductionContribution(calendar.getHsaEmpSalRedctnContrib());
			print.setHireExemptWgs(calendar.getHireExemptWgs());
			
			print.setTaxedLifeContribution(calendar.getTaxEmplrLife());
			print.setTaxedGroupContribution(calendar.getTaxEmplrLifeGrp());
			print.setHealthInsuranceDeduction(calendar.getHlthInsDed());
			
			print.setEmplrPrvdHlthcare(calendar.getEmplrPrvdHlthcare());
			print.setAnnuityRoth457b(calendar.getAnnuityRoth457b());
			calYTDRpt.add(print);
		
		return calYTDRpt;
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
				result = JasperFillManager.fillReport(jasperReport,parameters,report.getDataSource());
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

	String path = "F:/setup(1)/setup/Server/wildfly-14.0.0.Final/standalone/deployments/ESC20.war/reports";
	
//	// get the report id and make sure the report is lower case
	String template = "CalYTDPrint"; // report.getFileName();
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
}
