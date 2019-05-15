package com.esc20.controller;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.esc20.model.BeaUsers;
import com.esc20.model.BhrEmpDemo;
import com.esc20.model.BhrThirdPartySickPay;
import com.esc20.model.BhrW2;
import com.esc20.nonDBModels.Account;
import com.esc20.nonDBModels.CurrentPayInformation;
import com.esc20.nonDBModels.District;
import com.esc20.nonDBModels.EmployeeInfo;
import com.esc20.nonDBModels.Frequency;
import com.esc20.nonDBModels.Options;
import com.esc20.nonDBModels.PayInfo;
import com.esc20.nonDBModels.Stipend;
import com.esc20.nonDBModels.W2Print;
import com.esc20.nonDBModels.report.IReport;
import com.esc20.nonDBModels.report.ParameterReport;
import com.esc20.nonDBModels.report.ReportParameterConnection;
import com.esc20.service.IndexService;
import com.esc20.service.InquiryService;
import com.esc20.service.PDFService;
import com.esc20.util.CodeIterator;
import com.esc20.util.DataSourceContextHolder;
import com.esc20.util.DateUtil;
import com.esc20.util.MailUtil;
import com.esc20.util.NumberUtil;
import com.esc20.util.StringUtil;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/w2Information")
public class W2InformationController{

	@Autowired
	private InquiryService service;
	
    @Autowired
    private IndexService indexService;
	
    @Autowired
    private PDFService pDFService;
    
	private final String module = "W2 Information";
	
	@RequestMapping("w2Information")
	public ModelAndView getW2Information(HttpServletRequest req) {
		HttpSession session = req.getSession();
		ModelAndView mav = new ModelAndView();
		BhrEmpDemo userDetail = (BhrEmpDemo) session.getAttribute("userDetail");
		String employeeNumber = userDetail.getEmpNbr();
		List<String> years = this.service.getW2Years(employeeNumber);
		String latestYear = DateUtil.getLatestYear(years);
		BhrW2 w2Info = this.service.getW2Info(employeeNumber, latestYear);
		mav = setW2ValuesByCalYr(session, mav, employeeNumber, w2Info, latestYear, true);
		mav.addObject("isSuccess", false);
		return mav;
	}

	@RequestMapping("w2InformationByYear")
	public ModelAndView getW2InformationByYear(HttpServletRequest req, String year, Boolean isSuccess) {
		HttpSession session = req.getSession();
		ModelAndView mav = new ModelAndView();
		if(year==null) {
			mav.setViewName("visitFailed");
			mav.addObject("module", module);
			mav.addObject("action", "Get W2 information by year");
			mav.addObject("errorMsg", "Year is not provided.");
			return mav;
		}
		BhrEmpDemo userDetail = (BhrEmpDemo) session.getAttribute("userDetail");
		String employeeNumber = userDetail.getEmpNbr();
		BhrW2 w2Info = this.service.getW2Info(employeeNumber, year);
		if (isSuccess == null) {
			mav = setW2ValuesByCalYr(session, mav, employeeNumber, w2Info, year, true);
			mav.addObject("isSuccess", false);
		} else
			mav = setW2ValuesByCalYr(session, mav, employeeNumber, w2Info, year, isSuccess);
		return mav;
	}

	private ModelAndView setW2ValuesByCalYr(HttpSession session, ModelAndView mav, String employeeNumber, BhrW2 w2Info,
			String year, Boolean isSuccess) {
		// For calendar year >= 2010
		List<String> years = this.service.getW2Years(employeeNumber);

		String elecConsntMsgW2 = ((Options) session.getAttribute("options")).getMessageElecConsentW2().trim();
		String consent = this.service.getW2Consent(employeeNumber);

		List<BhrThirdPartySickPay> thirdPartyPay = this.service.getBhrThirdPartySickPay(employeeNumber, year);
		if (w2Info.getId().getCalYr() != null && !w2Info.getId().getCalYr().trim().equals("")
				&& Integer.valueOf(w2Info.getId().getCalYr()) >= 2010) {
			mav.addObject("hireExemptWgs", NumberUtil.value(w2Info.getHireExemptWgs()));
		} else {
			mav.addObject("hireExemptWgs", BigDecimal.valueOf(0.00).setScale(2, BigDecimal.ROUND_HALF_UP));
		}

		// For calendar year >= 2012
		if (w2Info.getId().getCalYr() != null && !w2Info.getId().getCalYr().trim().equals("")
				&& Integer.valueOf(w2Info.getId().getCalYr()) >= 2012) {
			mav.addObject("emplrPrvdHlthcare", NumberUtil.value(w2Info.getEmplrPrvdHlthcare()));
		} else {
			mav.addObject("emplrPrvdHlthcare", BigDecimal.valueOf(0.00).setScale(2, BigDecimal.ROUND_HALF_UP));
		}

		// For calendar year >= 2016
		if (w2Info.getId().getCalYr() != null && !w2Info.getId().getCalYr().trim().equals("")
				&& Integer.valueOf(w2Info.getId().getCalYr()) >= 2016) {
			mav.addObject("annuityRoth457b", NumberUtil.value(w2Info.getAnnuityRoth457b()));
		} else {
			mav.addObject("annuityRoth457b", BigDecimal.valueOf(0.00).setScale(2, BigDecimal.ROUND_HALF_UP));
		}
		BhrEmpDemo userDetail = (BhrEmpDemo) session.getAttribute("userDetail");
		District employer = (District) session.getAttribute("district");
		W2Print print = this.service.getW2Print(w2Info, userDetail, employer);
		mav.addObject("isSuccess", isSuccess);
		mav.setViewName("/inquiry/w2Information");
		mav.addObject("years", years);
		mav.addObject("selectedYear", year);
		mav.addObject("consent", consent);
		mav.addObject("elecConsntMsgW2", elecConsntMsgW2);
		mav.addObject("w2Info", w2Info);
		mav.addObject("thirdPartyPay", thirdPartyPay);
		mav.addObject("w2Print", print);
		return mav;
	}

	@RequestMapping("updateW2Consent")
	public ModelAndView updateW2Consent(HttpServletRequest req, String year, String consent) {
		HttpSession session = req.getSession();
		ModelAndView mav = new ModelAndView();
		if(year==null||consent==null) {
			mav.setViewName("visitFailed");
			mav.addObject("module", module);
			mav.addObject("action", "Update W2 consent");
			mav.addObject("errorMsg", "Not all mandotary fields provided.");
			return mav;
		}
		BhrEmpDemo userDetail = (BhrEmpDemo) session.getAttribute("userDetail");
		String employeeNumber = userDetail.getEmpNbr();
		Boolean isSuccess = this.service.updateW2ElecConsent(employeeNumber, consent);
		mav = this.getW2InformationByYear(req, year, isSuccess);
		this.sendEmail(userDetail.getNameF(), userDetail.getNameL(), userDetail.getEmail(), userDetail.getHmEmail(), consent);
		mav.addObject("isUpdate", true);
		mav.addObject("isSuccess", isSuccess);
		return mav;
	}
	
	public Integer sendEmail(String userFirstName, String userLastName, String userWorkEmail, String userHomeEmail, String elecConsntW2) {
		StringBuilder messageContents = new StringBuilder();
		messageContents.append(userFirstName + " " +userLastName + ", \n\n");
		messageContents.append("This receipt confirms you selected ");
		messageContents.append((elecConsntW2.equals("Y") ? " YES " : " NO "));
		messageContents.append("in participating in the W-2 Electronic Process. \n");
		messageContents.append("Effective immediately on ");

		DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		messageContents.append(dateFormat.format(cal.getTime()) + "\n");

		String subject = "A MESSAGE FROM W2 ELECTRONIC CONSENT";

		if (!"".equals(userWorkEmail)) {
			try{
				MailUtil.sendEmail(userWorkEmail, subject, messageContents.toString());
			}
			catch(Exception ex) {
				ex.printStackTrace();
			}
		}
		else if (!"".equals(userHomeEmail)) {
			try{
				MailUtil.sendEmail(userHomeEmail, subject, messageContents.toString());
			} 
			catch(Exception ex) {
				ex.printStackTrace();
			}
		}
		return 0;
	}
//	@RequestMapping("exportPDF")
//	public void exportPDF(HttpServletRequest request, HttpServletResponse response, String year) throws Exception {
//		String strBackUrl = "http://" + request.getServerName() + ":" + request.getServerPort()  + request.getContextPath();
//		System.out.println("prefix" + strBackUrl);
//		byte[] pdf = PDFUtil.getW2InformationPDF(strBackUrl+"/w2Information/w2InformationUnprotectedPDF", request, year);
//		response.reset();
//		response.setHeader("Content-Disposition", "attachment; filename=\"W2 Information for "+ year +".pdf\"");
//		response.setContentType("application/octet-stream;charset=UTF-8");
//		OutputStream out = response.getOutputStream();
//		out.write(pdf);
//		out.flush();
//	}
	
	@RequestMapping("exportPDF")
	public void exportPDF(HttpServletRequest request, HttpServletResponse response, String year) throws Exception {
		HttpSession session = request.getSession();
		BhrEmpDemo userDetail = (BhrEmpDemo) session.getAttribute("userDetail");
		response.setContentType("application/x-msdownload;charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment;filename=W-2 Substitute Form for "+year+".pdf");
		
		String path = request.getServletContext().getRealPath("/");
		if (path != null && !path.endsWith("\\")) {
			path = path.concat("\\");
		}
		pDFService.setRealPath(path);
		
		ParameterReport report = new ParameterReport();
		report.setTitle("W-2 Substitute Form for" + year);
		report.setId("w2Report");
		report.setFileName("W-2 Substitute Form for" + year);
		report.setSortable(false);
		report.setFilterable(false);
		
		W2Print w2Print = generateW2Print(request, year);
		IReport ireport = setupReport(report, w2Print, year);
		
	    JasperPrint jasperPrint = pDFService.buildReport(ireport);
    	JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
	}
	
	@RequestMapping("w2InformationUnprotectedPDF")
	public ModelAndView w2InformationUnprotectedPDF(HttpServletRequest req, String empNbr, String districtId,String language,String year) throws IOException {
		DataSourceContextHolder.setDataSourceType("java:jboss/DBNEW"+districtId);
		HttpSession session = req.getSession();
		ModelAndView mav = new ModelAndView();
		String employeeNumber = empNbr;
		BhrEmpDemo userDetail = this.indexService.getUserDetail(empNbr);
		session.setAttribute("userDetail", userDetail);
		District districtInfo = this.indexService.getDistrict(districtId);
		session.setAttribute("district", districtInfo);
		Options options = this.indexService.getOptions();
		session.setAttribute("options", options);
		String path = req.getSession().getServletContext().getRealPath("/") +"/static/js/lang/text-"+language+".json";
		File file = new File(path);
		String input = FileUtils.readFileToString(file, "UTF-8");
		JSONObject jsonObject = JSONObject.fromObject(input);
		req.getSession().setAttribute("languageJSON", jsonObject);
		BhrW2 w2Info = this.service.getW2Info(employeeNumber, year);
		mav = setW2ValuesByCalYr(session, mav, employeeNumber, w2Info, year, false);
		return mav;
	}

	public W2Print generateW2Print(HttpServletRequest req, String year)
	{
		W2Print print = new W2Print();
		HttpSession session = req.getSession();
		BhrEmpDemo userDetail = (BhrEmpDemo) session.getAttribute("userDetail");
		String employeeNumber = userDetail.getEmpNbr();
		District district = (District) session.getAttribute("district");
		BhrW2 w2Info = this.service.getW2Info(employeeNumber, year);
		if(w2Info == null || userDetail == null)
		{
			return print;
		}

		if(!StringUtil.isNullOrEmpty(userDetail.getStaffId()) && userDetail.getStaffId().length() == 9)
		{
			String ssn1 = userDetail.getStaffId().substring(0,3);
			String ssn2 = userDetail.getStaffId().substring(3,5);
			String ssn3 = userDetail.getStaffId().substring(5,9);
			print.setSsn(ssn1 + "-" + ssn2 + "-" + ssn3);
		}

		if(!StringUtil.isNullOrEmpty(userDetail.getEmpNbr()) && userDetail.getEmpNbr().length() == 6)
		{
			String ein1 = userDetail.getEmpNbr().substring(0,2);
			String ein2 = userDetail.getEmpNbr().substring(2,userDetail.getEmpNbr().length());
			print.setEin(ein1 + "-" + ein2);
		}

		print.setEname(district.getName());
		print.setEaddress(district.getAddress());
		print.setEcityst(district.getCity() + ", " + district.getState() + " " + district.getZip());
		
		if(district.getZip4()!=null && district.getZip4().length() > 0)
		{
			print.setEcityst(print.getEcityst() + "-" + district.getZip4());
		}

		String middleName = userDetail.getNameM();
		if (middleName!=null && (middleName.trim()).length() > 0) {
			middleName = middleName.trim() + " ";
		} else {
			middleName = "";
		}

		print.setEmpname(userDetail.getNameF() + " " + middleName + userDetail.getNameL() + " " + (userDetail.getGenDescription()==null?"":userDetail.getGenDescription()));   //jf20150113 Display description instead of code fix
		print.setEmpaddress(StringUtil.trim(userDetail.getAddrNbr())+ " "+ StringUtil.trim(userDetail.getAddrStr()));
		String apt = StringUtil.trim(userDetail.getAddrApt());
		if(apt.length() > 0)
		{
			print.setEmpaddress(print.getEmpaddress() + " " + apt);
		}
		print.setEmpcityst(userDetail.getAddrCity() + ", " + userDetail.getAddrSt() + " " + userDetail.getAddrZip());

		if(userDetail.getAddrZip4()!=null && userDetail.getAddrZip4().length() > 0)
		{
			print.setEmpcityst(print.getEmpcityst() + "-" + userDetail.getAddrZip4());
		}

		if(w2Info != null)
		{
			print.setTgross(w2Info.getWhGross().toString());
			print.setWhold(w2Info.getWhTax().toString());
			print.setFgross(w2Info.getFicaGross().toString());
			print.setFtax(w2Info.getFicaTax().toString());
			print.setEic(w2Info.getEicAmt().toString());
			print.setDcare(w2Info.getDependCare().toString());
			print.setMgross(w2Info.getMedGross().toString());
			print.setMtax(w2Info.getMedTax().toString());
		}

		String path = System.getProperty("EmployeeAccess.root")+"\\";
		String unchecked = "uncheckedbox";
		String checked = "checkedbox";

		String statemp = unchecked;
		String retplan = unchecked;
		String thrdsick = unchecked;

		if(w2Info.getPension() == 'Y')
		{
			retplan = checked;
		}
		
		BigDecimal sickPay = this.service.getThirdPartySickPay(employeeNumber, year);
		if(sickPay == null)
			sickPay = new BigDecimal(0);
		if(sickPay.doubleValue() > 0)
		{
			thrdsick = checked;
		}

		print.setStatemp(path + "reportImages\\" + statemp + ".gif");
		print.setRetplan(path + "reportImages\\"+ retplan +".gif");
		print.setThrdsick(path + "reportImages\\" + thrdsick + ".gif");

		print.setCopy("Copy B, To Be Filed With Employee\'s FEDERAL Tax Return");

		//*********************************************************************************************************************************
		// BOX12
		DecimalFormat d2Digit = new DecimalFormat("#.00");   //jf20130108 makes sure double value prints .00, instead of .0
		int row = 0;
		double ld_data = w2Info.getTaxEmplrLifeGrp().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		ld_data = Double.isNaN(ld_data) ? 0.00 : ld_data;
		if (ld_data != 0.00) {
			row ++;
			this.populatePrint(print, row, "C", d2Digit.format(ld_data));   //20130108 print .00, instead of .0
		}

		ld_data = w2Info.getEmp457Contrib().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		ld_data = Double.isNaN(ld_data) ? 0.00 : ld_data;
		if (ld_data != 0.00) {
			row ++;
			this.populatePrint(print, row, "E", d2Digit.format(ld_data));   //20130108 print .00, instead of .0
		}

		ld_data = (w2Info.getEmp457Contrib().add(w2Info.getEmplrContrib457())).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		ld_data = Double.isNaN(ld_data) ? 0.00 : ld_data;
		if (ld_data != 0.00) {
			row ++;
			this.populatePrint(print, row, "G", d2Digit.format(ld_data));   //20130108 print .00, instead of .0
		}

		ld_data = w2Info.getSickPayNontax().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		ld_data = Double.isNaN(ld_data) ? 0.00 : ld_data;
		if (ld_data != 0.00) {
			row ++;
			this.populatePrint(print, row, "J", d2Digit.format(ld_data));   //20130108 print .00, instead of .0
		}

		ld_data = w2Info.getEmpBusinessExpense().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		ld_data = Double.isNaN(ld_data) ? 0.00 : ld_data;
		if (ld_data != 0.00) {
			row ++;
			this.populatePrint(print, row, "L", d2Digit.format(ld_data));   //20130108 print .00, instead of .0
		}

		ld_data = w2Info.getMovingExpReimbr().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		ld_data = Double.isNaN(ld_data) ? 0.00 : ld_data;
		if (ld_data != 0.00) {
			row ++;
			this.populatePrint(print, row, "P", d2Digit.format(ld_data));   //20130108 print .00, instead of .0
		}

		ld_data = w2Info.getHsaContrib().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		ld_data = Double.isNaN(ld_data) ? 0.00 : ld_data;
		if (ld_data != 0.00) {
			row ++;
			this.populatePrint(print, row, "W", d2Digit.format(ld_data));   //20130108 print .00, instead of .0
		}

		ld_data = w2Info.getAnnuityRoth().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		ld_data = Double.isNaN(ld_data) ? 0.00 : ld_data;
		if (ld_data != 0.00) {
			row ++;
			this.populatePrint(print, row, "BB", d2Digit.format(ld_data));   //20130108 print .00, instead of .0
		}

		//For calendar year >= 2010
		if (w2Info.getId().getCalYr() != null && !w2Info.getId().getCalYr().trim().equals("") && Integer.valueOf(w2Info.getId().getCalYr()) >= 2010) {
			ld_data = w2Info.getHireExemptWgs().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			ld_data = Double.isNaN(ld_data) ? 0.00 : ld_data;
			if (ld_data != 0.00) {
				row ++;
				this.populatePrint(print, row, "CC", d2Digit.format(ld_data));
			}
		}

		if (w2Info.getId().getCalYr() != null && !w2Info.getId().getCalYr().trim().equals("") && Integer.valueOf(w2Info.getId().getCalYr()) >= 2012) {
			ld_data = w2Info.getEmplrPrvdHlthcare().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			ld_data = Double.isNaN(ld_data) ? 0.00 : ld_data;
			if (ld_data != 0.00) {
				row ++;
				this.populatePrint(print, row, "DD", d2Digit.format(ld_data));
				
			}
		}
		//Annuity Roth 457b reporting starts 2016   
		if (w2Info.getId().getCalYr() != null && !w2Info.getId().getCalYr().trim().equals("") && Integer.valueOf(w2Info.getId().getCalYr()) >= 2012) {
			ld_data = w2Info.getAnnuityRoth457b().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			ld_data = Double.isNaN(ld_data) ? 0.00 : ld_data;
			if (ld_data != 0.00) {
				row ++;
				this.populatePrint(print, row, "EE", d2Digit.format(ld_data));
			}
		}

		List<String> box14List = new ArrayList<String>();
		box14List.add("Non-Tax Allowance");
		box14List.add("Cafeteria 125");
		box14List.add("TRS Salary Reduction");
		box14List.add("Health Ins Ded");
		box14List.add("Taxable Allowance");
		box14List.add("Tax Fringe Benefits");
		box14List.add("Dummy Last Entry");

		Map<String,BigDecimal> box14Map = new HashMap<String,BigDecimal>();
		box14Map.put(box14List.get(0),w2Info.getNontrsNontaxBusAllow());
		box14Map.put(box14List.get(1), w2Info.getCafeAmt());
		box14Map.put(box14List.get(2), w2Info.getTrsDeposit());
		box14Map.put(box14List.get(3), w2Info.getHlthInsDed());
		box14Map.put(box14List.get(4), w2Info.getNontrsBusAllow());
		box14Map.put(box14List.get(5), w2Info.getTaxedBenefits());
		box14Map.put(box14List.get(6), new BigDecimal(0.00));

		Iterator<String> iter14 = new CodeIterator(box14List,box14Map);
		print.setCode1401(iter14.next());
		print.setAmt1401(toString(box14Map.get(print.getCode1401())));
		print.setCode1402(iter14.next());
		print.setAmt1402(toString(box14Map.get(print.getCode1402())));
		print.setCode1403(iter14.next());
		print.setAmt1403(toString(box14Map.get(print.getCode1403())));
		print.setCode1404(iter14.next());
		print.setAmt1404(toString(box14Map.get(print.getCode1404())));
		print.setCode1405(iter14.next());
		print.setAmt1405(toString(box14Map.get(print.getCode1405())));
		print.setCode1406(iter14.next());
		print.setAmt1406(toString(box14Map.get(print.getCode1406())));

		return print;
	}

	public void populatePrint(W2Print print, int row, String code, String value) {
		if (row == 1) {
			print.setCode1201(code);
			print.setAmt1201(value);
		}
		else if (row == 2) {
			print.setCode1202(code);
			print.setAmt1202(value);
		}
		else if (row == 3) {
			print.setCode1203(code);
			print.setAmt1203(value);
		}
		else if (row == 4) {
			print.setCode1204(code);
			print.setAmt1204(value);
		}
		else if (row == 5) {
			print.setCode1205(code);
			print.setAmt1205(value);
		}
		else if (row == 6) {
			print.setCode1206(code);
			print.setAmt1206(value);
		}
		else if (row == 7) {
			print.setCode1207(code);
			print.setAmt1207(value);
		}
		else if (row == 8) {
			print.setCode1208(code);
			print.setAmt1208(value);
		}
		else if (row == 9) {
			print.setCode1209(code);
			print.setAmt1209(value);
		}
		else if (row == 10) {
			print.setCode1210(code);
			print.setAmt1210(value);
		}
		else if (row == 11) {
			print.setCode1211(code);
			print.setAmt1211(value);
		}
	}
	
	private String toString(BigDecimal b) {
		if(b != null) {
			return b.toString();
		}
		else {
			return "";
		}
	}
	
	private IReport setupReport(ParameterReport report, W2Print data, String year) throws Exception 
	{
				
		report.getParameters().clear();
		ReportParameterConnection parameter = new ReportParameterConnection();
		parameter.setName("subRptConnection");
		parameter.setConnection(pDFService.getConn());
		report.getParameters().add(parameter);	
		if (year == null) {
			return report;
		} else {
			report.setFileName("W2_" + year);
		}
	
		List<W2Print> forms = new ArrayList<W2Print>();
		forms.add(data);
		forms.add(generateW2Copy(data));
		report.setDataSource(new JRBeanCollectionDataSource(forms));		
		return report;
	}
	

	private W2Print generateW2Copy(W2Print print)
	{
		W2Print copy = (W2Print)print.clone();
		copy.setCopy("Copy C, For Employee\'s RECORDS");
		return copy;
	}
}
