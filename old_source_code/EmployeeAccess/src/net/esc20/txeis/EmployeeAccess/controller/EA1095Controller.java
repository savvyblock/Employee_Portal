package net.esc20.txeis.EmployeeAccess.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.esc20.txeis.EmployeeAccess.domain.EA1095BInfoCommand;
import net.esc20.txeis.EmployeeAccess.domain.EA1095CInfoCommand;
import net.esc20.txeis.EmployeeAccess.domain.EA1095InfoCommand;
import net.esc20.txeis.EmployeeAccess.domainobject.Aca1095BPrint;
import net.esc20.txeis.EmployeeAccess.domainobject.Aca1095CPrint;
import net.esc20.txeis.EmployeeAccess.domainobject.District;
import net.esc20.txeis.EmployeeAccess.domainobject.Options;
import net.esc20.txeis.EmployeeAccess.domainobject.User;
import net.esc20.txeis.EmployeeAccess.service.api.IEA1095ervice;
import net.esc20.txeis.EmployeeAccess.service.api.IMailUtilService;
import net.esc20.txeis.EmployeeAccess.domainobject.report.IReport;
import net.esc20.txeis.EmployeeAccess.domainobject.report.ParameterReport;
import net.esc20.txeis.common.domainobjects.ReportingContact;
import net.esc20.txeis.common.util.Page;
import net.esc20.txeis.common.validation.ValidationUtil;
import net.esc20.txeis.EmployeeAccess.util.StringUtil;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

@SessionAttributes("EA1095InfoCommand")
public class EA1095Controller {
	private static final String VIEWPATH = "ea1095";

	private IEA1095ervice ea1095Service;
	private IMailUtilService mailUtilService;
	private EA1095InfoCommand tempEA1095InfoCommand;

	ReportingContact reportingContact = new ReportingContact();

	public EA1095InfoCommand getTempEA1095InfoCommand() {
		return tempEA1095InfoCommand;
	}

	public void setTempEA1095InfoCommand(EA1095InfoCommand tempEA1095InfoCommand) {
		this.tempEA1095InfoCommand = tempEA1095InfoCommand;
	}

	public IEA1095ervice getEa1095Service() {
		return ea1095Service;
	}

	public void setEa1095Service(IEA1095ervice ea1095Service) {
		this.ea1095Service = ea1095Service;
	}

	public IMailUtilService getMailUtilService() {
		return mailUtilService;
	}

	public void setMailUtilService(IMailUtilService mailUtilService) {
		this.mailUtilService = mailUtilService;
	}

	//This is the initial load of the screen from the menu
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView initEa1095(Model model, HttpServletRequest request) {
		ModelMap modelMap = new ModelMap();
		EA1095InfoCommand ea1095Info = new EA1095InfoCommand();

		// Get User Information form the session
		String currentUser = (String) request.getSession().getAttribute("currentUser_employeeNumber");
		String firstName = (String) request.getSession().getAttribute("currentUser_firstName");
		String lastName = (String) request.getSession().getAttribute("currentUser_lastName");
		String workEmail = (String) request.getSession().getAttribute("currentUser_workEmail");
		String homeEmail = (String) request.getSession().getAttribute("currentUser_homeEmail");

		ea1095Info.setEmpNbr(currentUser);

		// Retrieve Information from HR
		Options information = ea1095Service.getOptions();
		ea1095Info.setLatestYr(information.getW2Latest().toString());
		ea1095Info.setMsg1095(information.getMessage1095());
		ea1095Info.setEnableElecConsnt1095(information.isEnableElecConsnt1095());

		// Retrieve Calendar Years
		ea1095Info.setCalYrs(ea1095Service.retrieveAvailableCalYrs(currentUser));

		// Retrieve latest year
		if (ea1095Info.getCalYrs().size() != 0) {
			ea1095Info.setCurrentYr(ea1095Service.retrieveLatestYr(ea1095Info.getCalYrs()));
		} else {
			ea1095Info.setCurrentYr("");
		}

		// Reset the Electronic Consent Pop-up
		ea1095Info.setEa1095ElecConsnt("");

		// Set User Information to the EA1095InfoCommand object
		ea1095Info.setCurrentUser(currentUser);
		ea1095Info.setFirstName(firstName);
		ea1095Info.setLastName(lastName);
		ea1095Info.setWorkEmail(workEmail);
		ea1095Info.setHomeEmail(homeEmail);

		// Retrieve the 1095 Electronic Consent Flag and Electronic Consent Message
		ea1095Info.setEa1095ElecConsnt(ea1095Service.retrieveEA1095ElecConsent(ea1095Info.getEmpNbr()).trim());
		ea1095Info.setEa1095ElecConsntMsg(ea1095Service.retrieveEA1095ElecConsentMsg());

		// Retrieve 1095 Information on-load of the screen
		if (ea1095Info.getCalYrs().size() > 0) {
			this.retrieve1095Info(ea1095Info, request);
		}

		modelMap.addAttribute("EA1095InfoCommand", ea1095Info);
		return new ModelAndView(VIEWPATH, modelMap);
	}

	// Functionality to retrieve data based on the radio button selection on the screen (1095-B or 1095-C)
	@RequestMapping(method = RequestMethod.POST, params = "mySubmitEAInfo=Submit Query")
	public ModelAndView retrieve1095Info(EA1095InfoCommand ea1095Info, HttpServletRequest request) {
		ModelMap modelMap = new ModelMap();
		ea1095Info.setDisableEa1095B("");
		ea1095Info.setDisableEa1095C("");
		ea1095Info.setShowReportInNewWindow(false);

		this.retrieveEA1095BInfo(ea1095Info, request);
		this.retrieveEA1095CInfo(ea1095Info, request);

		// Disable 1095-B if no 1095-B data
		if (!(ea1095Info.getEa1095BData().getPageItems().size() > 0) && ea1095Info.getCovrgTyp().trim().equals("")) {
			ea1095Info.setDisableEa1095B("Y");
		} 
		// Disable 1095-C if no 1095-C data
		if (!(ea1095Info.getEa1095CCovrgData().getPageItems().size() > 0) && ea1095Info.getEa1095CEmpData().size() == 0) {
			ea1095Info.setDisableEa1095C("Y");
		}

		// Set the 1095 option on-load
		if (ea1095Info.getEa1095BData().getPageItems().size() > 0 || !ea1095Info.getCovrgTyp().trim().equals("")) {
			ea1095Info.setEa1095("B");
		} else if (ea1095Info.getEa1095CCovrgData().getPageItems().size() > 0 || ea1095Info.getEa1095CEmpData().size() != 0) {
			ea1095Info.setEa1095("C");
		}

		// Set true to the show pop-up flag
		ea1095Info.setShowEA1095ElecConsntPopup(true);

		modelMap.addAttribute("EA1095InfoCommand", ea1095Info);
		return new ModelAndView(VIEWPATH, modelMap);
	}

	// Retrieve 1095B Information
	@RequestMapping(method = RequestMethod.POST, params = "mySubmitEA1095B=Submit Query")
	public ModelAndView retrieveEA1095BInfo(@ModelAttribute("EA1095InfoCommand") EA1095InfoCommand ea1095Info, HttpServletRequest request) {
		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("saveSuccess", "");
		ea1095Info.setShowReportInNewWindow(false);
		int pageNbr = 1, pageSize = ea1095Info.getPageSize1095B();

		Page<EA1095BInfoCommand> ea1095BData = new Page<EA1095BInfoCommand>();

		// Retrieve 1095B Emp Data
		List<String> covrgTypList = ea1095Service.retrieveEA1095BEmpInfo(ea1095Info.getActiveTab(), ea1095Info.getEmpNbr(), ea1095Info.getCurrentYr());

		if (covrgTypList.size() > 0) {
			ea1095Info.setCovrgTyp(covrgTypList.get(0));
			ea1095Info.setCovrgDescr(covrgTypList.get(1));
		}

		// Retrieve 1095B Covrg Data
		ea1095BData = ea1095Service.retrieveEA1095BCovrgInfo(ea1095Info.getActiveTab(), ea1095Info.getEmpNbr(), ea1095Info.getCurrentYr(), ea1095Info.getSortColumn1095B(), ea1095Info.getSortOrder1095B(), pageNbr, pageSize);
		if (!ea1095BData.getPageItems().isEmpty()) {
			ea1095Info.setCurrentPage1095B(1);
			ea1095Info.setSelectedPage1095B(1);
			ea1095Info.setTotalPages1095B(ea1095BData.getPagesAvailable());
		} else {
			ea1095Info.setCurrentPage1095B(null);
			ea1095Info.setSelectedPage1095B(1);
			ea1095Info.setTotalPages1095B(0);
		}
		ea1095Info.setEa1095BData(ea1095BData);

		// Reset the Electronic Consent Pop-up
		ea1095Info.setShowEA1095ElecConsntPopup(false);

		modelMap.addAttribute("EA1095InfoCommand", ea1095Info);
		return new ModelAndView(VIEWPATH, modelMap);
	}

	// Retrieve ACA YTD 1095C Data
	@RequestMapping(method = RequestMethod.POST, params = "mySubmitEA1095C=Submit Query")
	public ModelAndView retrieveEA1095CInfo(@ModelAttribute("EA1095InfoCommand") EA1095InfoCommand ea1095Info, HttpServletRequest request) {
		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("saveSuccess", "");
		ea1095Info.setShowReportInNewWindow(false);
		int pageNbr = 1, pageSize = ea1095Info.getPageSize1095C();

		// Retrieve ACA YTD 1095C Emp Data
		List<EA1095CInfoCommand> acaYTD1095CEmpData = new ArrayList<EA1095CInfoCommand>();

		acaYTD1095CEmpData = ea1095Service.retrieveEA1095CEmpInfo(ea1095Info.getActiveTab(), ea1095Info.getEmpNbr(), ea1095Info.getCurrentYr());
		ea1095Info.setEa1095CEmpData(acaYTD1095CEmpData);

		// Set the Self-Insured value
		if (acaYTD1095CEmpData.size() > 0) {
			ea1095Info.setSelfIns(acaYTD1095CEmpData.get(0).isSelfIns());
			ea1095Info.setPlanStrtMon(acaYTD1095CEmpData.get(0).getPlanStrtMon());
		} else {
			ea1095Info.setSelfIns(false);
			ea1095Info.setPlanStrtMon("");
		}
		// Build the Parent data
		ea1095Service.buildEA1095CEmpDataDisplay(ea1095Info);

		// Build CalMon Map
		ea1095Info.setCalMonMap(ea1095Service.buildEA1095CCalMonMap());

		// Retrieve ACA YTD 1095C Coverage Data
		Page<EA1095CInfoCommand> ea1095CData = new Page<EA1095CInfoCommand>();

		ea1095CData = ea1095Service.retrieveEA1095CCovrgInfo(ea1095Info.getActiveTab(), ea1095Info.getEmpNbr(), ea1095Info.getCurrentYr(), ea1095Info.getSortColumn1095C(), ea1095Info.getSortOrder1095C(), pageNbr, pageSize);
		ea1095Info.setEa1095CCovrgData(ea1095CData);

		if (!ea1095CData.getPageItems().isEmpty()) {
			ea1095Info.setCurrentPage1095C(1);
			ea1095Info.setSelectedPage1095C(1);
			ea1095Info.setTotalPages1095C(ea1095CData.getPagesAvailable());
		} else {
			ea1095Info.setCurrentPage1095C(null);
			ea1095Info.setSelectedPage1095C(1);
			ea1095Info.setTotalPages1095C(0);
		}

		// Reset the Electronic Consent Pop-up
		ea1095Info.setShowEA1095ElecConsntPopup(false);

		modelMap.addAttribute("EA1095InfoCommand", ea1095Info);
		return new ModelAndView(VIEWPATH, modelMap);
	}

	@RequestMapping(method = RequestMethod.POST, params = "mySubmitUpdateEA1095Consent=Submit Query")
	public ModelAndView updateEA1095ElecConsent(Model model, @ModelAttribute("EA1095InfoCommand") EA1095InfoCommand ea1095Info, BindingResult errors, HttpServletRequest request) {
		boolean update = false;
		String ea1095ElecConsnt = ea1095Info.getEa1095ElecConsnt();
		if (ea1095ElecConsnt != null && !ea1095ElecConsnt.equals("")) {
			update = ea1095Service.updateEA1095ElecConsent(ea1095Info.getCurrentUser(), ea1095ElecConsnt);
		}
		if (update) {
			int success = sendEmail(ea1095Info.getFirstName(), ea1095Info.getLastName(), ea1095Info.getWorkEmail(), ea1095Info.getHomeEmail(), ea1095ElecConsnt);
			if (success == 0) {
				ea1095Info.setShowSuccessMsg(true);
			} else {
				ea1095Info.setShowSuccessMsg(false);
			}
		}

		ea1095Info.setShowReportInNewWindow(false);
		model.addAttribute("EA1095InfoCommand", ea1095Info);
		return new ModelAndView(VIEWPATH);
	}

	private Integer sendEmail(String userFirstName, String userLastName, String userWorkEmail, String userHomeEmail, String ea1095ElecConsnt) {
		StringBuilder messageContents = new StringBuilder();
		messageContents.append(userFirstName + " " +userLastName + ", \n\n");
		messageContents.append("This receipt confirms you selected ");
		messageContents.append((ea1095ElecConsnt.equals("Y") ? " YES " : " NO "));
		messageContents.append("in participating in the 1095 Electronic Process. \n");
		messageContents.append("Effective immediately on ");

		DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		messageContents.append(dateFormat.format(cal.getTime()) + "\n");
		SimpleMailMessage msg = new SimpleMailMessage();

		msg.setSubject("A MESSAGE FROM 1095 ELECTRONIC CONSENT");
		msg.setText(messageContents.toString());

		if (!"".equals(userWorkEmail)) {
			msg.setTo(userWorkEmail);
			mailUtilService.sendMail(msg);
		}
		else if (!"".equals(userHomeEmail)) {
			msg.setTo(userHomeEmail);
			try{
				mailUtilService.sendMail(msg);
			} 
			catch(MailException ex) {
				ex.printStackTrace();
			} 
		}
		return 0;
	}

	// Print 1095 Report
	@RequestMapping(method = RequestMethod.POST, params = "mySubmitEA1095Print=Submit Query")
	public ModelAndView printEA1095Info(@ModelAttribute("EA1095InfoCommand") EA1095InfoCommand ea1095Info, BindingResult result, HttpServletRequest request) {
		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("saveSuccess", "");

		//get User Info
		User user = ea1095Service.retrieveEmployee(ea1095Info.getCurrentUser());
		ea1095Info.setEaUser(user);

		//get District Info
		District district = ea1095Service.retrieveDistrict();
		ea1095Info.setEaDistrict(district);

		if (ea1095Info.getEa1095().equals("B")) {
			this.printEA1095BInfo(ea1095Info, result, request);
		} else {
			this.printEA1095CInfo(ea1095Info, result, request);
		}
		modelMap.addAttribute("errorList", ValidationUtil.getErrorList(result));
		modelMap.addAttribute("EA1095InfoCommand", ea1095Info);
		return new ModelAndView("ea1095", modelMap);
	}

	// Print 1095B Information
	public ModelAndView printEA1095BInfo(@ModelAttribute("EA1095InfoCommand") EA1095InfoCommand ea1095Info, BindingResult result, HttpServletRequest request) {
		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("saveSuccess", "");

		// Retrieve 1095B Emp Data
		List<String> covrgTypList = ea1095Service.retrieveEA1095BEmpInfo(ea1095Info.getActiveTab(), ea1095Info.getEmpNbr(), ea1095Info.getCurrentYr());

		if (covrgTypList.size() > 0) {
			ea1095Info.setCovrgTyp(covrgTypList.get(0));
			ea1095Info.setCovrgDescr(covrgTypList.get(1));
		}

		Aca1095BPrint print = new Aca1095BPrint();

		String taxYr = ea1095Info.getCurrentYr();

		//get webroot path for images in the report
		String path = request.getServletContext().getRealPath("/");
		if (path != null && !path.endsWith("\\")) {
			path = path.concat("\\");
		}

		print.setFormpagenbr("1");
		print.setTaxyr(taxYr);
		print.setWebroot(path);

		reportingContact = ea1095Service.getReportingContact();

		//Fill out 1095B Form - Page 1 and 2
		fillPartIBoxesB(ea1095Info, print);
		fillPartIIBoxesB(ea1095Info, print, reportingContact);
		fillPartIIIBoxesB(ea1095Info, print);
		fillPartIVBoxesB(ea1095Info, print, false);

		List<Aca1095BPrint> forms = new ArrayList<Aca1095BPrint>();
		forms.add(print);

		//Fill out 1095B Form - Page 3 continuation Sheet 1
		if(ea1095Info.getEa1095BCovrg().size() >= 7){
			Aca1095BPrint print2 = new Aca1095BPrint();
			fillPartIBoxesB(ea1095Info, print2);
			fillPartIIBoxesB(ea1095Info, print2, reportingContact);
			fillPartIIIBoxesB(ea1095Info, print2);
			fillPartIVBoxesB(ea1095Info, print2, false);
			print2.setFormpagenbr("3");
			print2.setTaxyr(taxYr);
			print2.setWebroot(path);
			forms.add(print2);
		}

		//Fill out 1095B Form - Page 4 continuation Sheet 2
		if(ea1095Info.getEa1095BCovrg().size() >= 19){
			Aca1095BPrint print3 = new Aca1095BPrint();
			fillPartIBoxesB(ea1095Info, print3);
			fillPartIIBoxesB(ea1095Info, print3, reportingContact);
			fillPartIIIBoxesB(ea1095Info, print3);
			fillPartIVBoxesB(ea1095Info, print3, true);
			print3.setFormpagenbr("3");
			print3.setTaxyr(taxYr);
			print3.setWebroot(path);
			forms.add(print3);
		}

		ParameterReport report = new ParameterReport();

		//Set Report Info
		report.setTitle("1095B Report");
		report.setFileName("DHrs5250Form1095B" + taxYr);
		report.setId("DHrs5250Form1095B" + taxYr);
		report.setSortable(false);
		report.setFilterable(false);

		//Setup 1095B Report
		IReport myReport = setupBReport(report, forms, taxYr);

		WebUtils.setSessionAttribute(request, "PARAMETER_REPORT", myReport);
		ea1095Info.setShowReportInNewWindow(true);

		modelMap.addAttribute("errorList", ValidationUtil.getErrorList(result));
		modelMap.addAttribute("EA1095InfoCommand", ea1095Info);
		return new ModelAndView(VIEWPATH, modelMap);
	}

	// Print 1095C Information
	public ModelAndView printEA1095CInfo(@ModelAttribute("EA1095InfoCommand") EA1095InfoCommand ea1095Info, BindingResult result, HttpServletRequest request) {
		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("saveSuccess", "");

		// Retrieve ACA YTD 1095C Emp Data
		List<EA1095CInfoCommand> acaYTD1095CEmpData = new ArrayList<EA1095CInfoCommand>();

		acaYTD1095CEmpData = ea1095Service.retrieveEA1095CEmpInfo(ea1095Info.getActiveTab(), ea1095Info.getEmpNbr(), ea1095Info.getCurrentYr());
		ea1095Info.setEa1095CEmpData(acaYTD1095CEmpData);

		// Set the Self-Insured value
		if (acaYTD1095CEmpData.size() > 0) {
			ea1095Info.setSelfIns(acaYTD1095CEmpData.get(0).isSelfIns());
			ea1095Info.setPlanStrtMon(acaYTD1095CEmpData.get(0).getPlanStrtMon());
		} else {
			ea1095Info.setSelfIns(false);
			ea1095Info.setPlanStrtMon("");
		}
		// Build the Parent data
		ea1095Service.buildEA1095CEmpDataDisplay(ea1095Info);

		// Build CalMon Map
		ea1095Info.setCalMonMap(ea1095Service.buildEA1095CCalMonMap());

		Aca1095CPrint print = new Aca1095CPrint();

		String taxYr = ea1095Info.getCurrentYr();

		//get webroot path for images in the report
		String path = request.getServletContext().getRealPath("/");
		if (path != null && !path.endsWith("\\")) {
			path = path.concat("\\");
		}

		print.setFormpagenbr("1");
		print.setTaxyr(taxYr);
		print.setWebroot(path);

		//Fill out 1095C Form - Page 1 and 2
		fillPartIEmpBoxesC(ea1095Info, print);
		fillPartIALEBoxesC(ea1095Info, print);
		fillPartIIBoxesC(ea1095Info, print);
		fillPartIIIBoxesC(ea1095Info, print, false);

		List<Aca1095CPrint> forms = new ArrayList<Aca1095CPrint>();
		forms.add(print);

		//Fill out 1095C Form - Page 3 continuation Sheet 1
		if(ea1095Info.getEa1095CCovrg().size() >= 7) {
			Aca1095CPrint print2 = new Aca1095CPrint();
			fillPartIEmpBoxesC(ea1095Info, print2);
			fillPartIALEBoxesC(ea1095Info, print2);
			fillPartIIBoxesC(ea1095Info, print2);
			fillPartIIIBoxesC(ea1095Info, print2, false);
			print2.setFormpagenbr("3");
			print2.setTaxyr(taxYr);
			print2.setWebroot(path);
			forms.add(print2);
		}

		//Fill out 1095C Form - Page 4 continuation Sheet 2
		if(ea1095Info.getEa1095CCovrg().size() >= 19) {
			Aca1095CPrint print3 = new Aca1095CPrint();
			fillPartIEmpBoxesC(ea1095Info, print3);
			fillPartIALEBoxesC(ea1095Info, print3);
			fillPartIIBoxesC(ea1095Info, print3);
			fillPartIIIBoxesC(ea1095Info, print3, true);
			print3.setFormpagenbr("3");
			print3.setTaxyr(taxYr);
			print3.setWebroot(path);
			forms.add(print3);
		}

		ParameterReport report = new ParameterReport();

		//Set Report Info
		report.setTitle("1095C Report");
		report.setFileName("DHrs5255Form1095C" + taxYr);
		report.setId("DHrs5255Form1095C" + taxYr);
		report.setSortable(false);
		report.setFilterable(false);

		//Setup the 1095C Report
		IReport myReport = setupCReport(report, forms, taxYr);

		WebUtils.setSessionAttribute(request, "PARAMETER_REPORT", myReport);
		ea1095Info.setShowReportInNewWindow(true);

		modelMap.addAttribute("errorList", ValidationUtil.getErrorList(result));
		modelMap.addAttribute("EA1095InfoCommand", ea1095Info);
		return new ModelAndView(VIEWPATH, modelMap);
	}

	private void fillPartIBoxesB(EA1095InfoCommand ea1095Info, Aca1095BPrint print) {
		User eaUser = ea1095Info.getEaUser();

		print.setB1_personfirstnm(eaUser.getFirstName());
		print.setB1_personmiddlenm(eaUser.getMiddleName());
		print.setB1_personlastnm(eaUser.getLastName());
		print.setB1_suffixnm(eaUser.getGenerationDescr());

		String ssn = eaUser.getSsn().replaceAll("-", "").trim();
		String b2_ssn = "";
		String b3_birthDt = "";

		//if ssn then do not display dob. if no ssn then display dob.
		if (ssn == null || ssn.equals("")) {
			b3_birthDt = eaUser.getDateOfBirth().replaceAll("-", "").trim();
		} else {
			b2_ssn = ssn;
		}	

		print.setB2_ssn(b2_ssn);
		print.setB3_birthdt(b3_birthDt);
		print.setB4_addressline1txt(eaUser.getAddress());
		print.setB5_citynm(eaUser.getCity());
		print.setB6_usstatecd(eaUser.getState());
		print.setB7_uszipcd(eaUser.getZipCode());
		print.setB7_uszipextensioncd(eaUser.getZipCode4());
		print.setB8_policyorigincd(ea1095Info.getCovrgTyp());
	}

	private void fillPartIIBoxesB(EA1095InfoCommand ea1095Info, Aca1095BPrint print, ReportingContact reportingContact) {

		//If coverage type A, then get SHOP INFO from Report Parameters
		if(ea1095Info.getCovrgTyp().equalsIgnoreCase("A")) {
			print.setB10_businessnameline1txt(reportingContact.getShopName());
			print.setB11_ein(reportingContact.getShopEIN());
			print.setB12_addressline1txt(reportingContact.getShopAddr());
			print.setB13_citynm(reportingContact.getShopCity());
			print.setB14_usstatecd(reportingContact.getShopSt());
			print.setB15_uszipcd(reportingContact.getShopZip());
			print.setB15_uszipextensioncd(reportingContact.getShopZip4());
		}
	}

	private void fillPartIIIBoxesB(EA1095InfoCommand ea1095Info, Aca1095BPrint print) {
		District district = ea1095Info.getEaDistrict();

		String ein = district.getNumber();
		ein = StringUtil.left(ein, 2) + "-" + StringUtil.right(ein, 7);

		print.setB16_businessnameline1txt(StringUtil.trim(StringUtil.upper(district.getName())));
		print.setB17_ein(ein);
		print.setB18_contactphonenum(district.getPhone());
		print.setB19_addressline1txt(StringUtil.trim(StringUtil.upper(district.getAddress())));
		print.setB20_citynm(StringUtil.trim(StringUtil.upper(district.getCity())));
		print.setB21_usstatecd(StringUtil.trim(StringUtil.upper(district.getState())));
		print.setB22_uszipcd(district.getZip());
		print.setB22_uszipextensioncd(district.getZip4());
	}

	private void fillPartIVBoxesB(EA1095InfoCommand ea1095Info, Aca1095BPrint print, Boolean printPage4) {
		int box = 23;

		List<EA1095BInfoCommand> eA1095BCovrgInfo = ea1095Info.getEa1095BCovrg();

		//Get List of Covered Individuals
		eA1095BCovrgInfo = ea1095Service.retrieveEA1095BCovrgInfo(ea1095Info.getActiveTab(), ea1095Info.getEmpNbr(), ea1095Info.getCurrentYr());
		ea1095Info.setEa1095BCovrg(eA1095BCovrgInfo);

		//if printin page 4, then cut down the list for the report
		if(printPage4 && eA1095BCovrgInfo.size() >= 19){
			int size = eA1095BCovrgInfo.size();
			eA1095BCovrgInfo = eA1095BCovrgInfo.subList(18, size);
			box = 29;
		}

		//Go through part IV boxes on the form and instantiate them blank or with data
		for(int i=0; i<eA1095BCovrgInfo.size(); i++) {

			EA1095BInfoCommand ea1095 = eA1095BCovrgInfo.get(i);

			print.setCovrgFirstName(box, ea1095.getfName());
			print.setCovrgMiddleName(box, ea1095.getmName());
			print.setCovrgLastName(box, ea1095.getlName());
			print.setCovrgSuffix(box, ea1095.getNameGenDescr());

			String ssn = ea1095.getSsn().replaceAll("-", "").trim();
			String box_ssn = "";
			String box_birthDt = "";

			//if ssn then do not display dob.  if no ssn then display dob.
			if (ssn == null || ssn.equals("")) {
				box_birthDt = ea1095.getDob().replaceAll("-", "").trim();
			} else {
				box_ssn = ssn;
			}

			print.setCovrgSSN(box, box_ssn);
			print.setCovrgBirthDt(box, box_birthDt);
			print.setCovrgAnnualInd(box, ea1095.isAllMon());
			print.setCovrgJanuaryInd(box, ea1095.isJan());
			print.setCovrgFebruaryInd(box, ea1095.isFeb());
			print.setCovrgMarchInd(box, ea1095.isMar());
			print.setCovrgAprilInd(box, ea1095.isApr());
			print.setCovrgMayInd(box, ea1095.isMay());
			print.setCovrgJuneInd(box, ea1095.isJun());
			print.setCovrgJulyInd(box, ea1095.isJul());
			print.setCovrgAugustInd(box, ea1095.isAug());
			print.setCovrgSeptemberInd(box, ea1095.isSep());
			print.setCovrgOctoberInd(box, ea1095.isOct());
			print.setCovrgNovemberInd(box, ea1095.isNov());
			print.setCovrgDecemberInd(box, ea1095.isDec());	

			// increment box
			box++;
		}
	}

	private void fillPartIEmpBoxesC(EA1095InfoCommand ea1095Info, Aca1095CPrint print) {

		User eaUser = ea1095Info.getEaUser();

		print.setB1_personfirstnm(eaUser.getFirstName());
		print.setB1_personmiddlenm(eaUser.getMiddleName());
		print.setB1_personlastnm(eaUser.getLastName());
		print.setB1_suffixnm(eaUser.getGenerationDescr());
		print.setB2_ssn(eaUser.getSsn().replaceAll("-", "").trim());
		print.setB3_addressline1txt(eaUser.getAddress());
		print.setB4_citynm(eaUser.getCity());
		print.setB5_usstatecd(eaUser.getState());
		print.setB6_uszipcd(eaUser.getZipCode());
		print.setB6_uszipextensioncd(eaUser.getZipCode4());
	}

	private void fillPartIALEBoxesC(EA1095InfoCommand ea1095Info, Aca1095CPrint print) {

		District district = ea1095Info.getEaDistrict();

		String ein = district.getNumber();
		ein = StringUtil.left(ein, 2) + "-" + StringUtil.right(ein, 7);

		print.setB7_businessnameline1txt(StringUtil.trim(StringUtil.upper(district.getName())));
		print.setB8_ein(ein);
		print.setB9_addressline1txt(StringUtil.trim(StringUtil.upper(district.getAddress())));
		print.setB10_contactphonenum(district.getPhone());
		print.setB11_citynm(StringUtil.trim(StringUtil.upper(district.getCity())));
		print.setB12_usstatecd(StringUtil.trim(StringUtil.upper(district.getState())));
		print.setB13_uszipcd(district.getZip());
		print.setB13_uszipextensioncd(district.getZip4());
	}

	private void fillPartIIBoxesC(EA1095InfoCommand ea1095Info, Aca1095CPrint print) {

		print.setB14_annualofferofcoveragecd(ea1095Info.getEa1095CCalMonAll().getOffrOfCovrg());
		print.setB14_janoffercd(ea1095Info.getEa1095CCalMonJan().getOffrOfCovrg());
		print.setB14_feboffercd(ea1095Info.getEa1095CCalMonFeb().getOffrOfCovrg());
		print.setB14_maroffercd(ea1095Info.getEa1095CCalMonMar().getOffrOfCovrg());
		print.setB14_aproffercd(ea1095Info.getEa1095CCalMonApr().getOffrOfCovrg());
		print.setB14_mayoffercd(ea1095Info.getEa1095CCalMonMay().getOffrOfCovrg());
		print.setB14_junoffercd(ea1095Info.getEa1095CCalMonJun().getOffrOfCovrg());
		print.setB14_juloffercd(ea1095Info.getEa1095CCalMonJul().getOffrOfCovrg());
		print.setB14_augoffercd(ea1095Info.getEa1095CCalMonAug().getOffrOfCovrg());
		print.setB14_sepoffercd(ea1095Info.getEa1095CCalMonSep().getOffrOfCovrg());
		print.setB14_octoffercd(ea1095Info.getEa1095CCalMonOct().getOffrOfCovrg());
		print.setB14_novoffercd(ea1095Info.getEa1095CCalMonNov().getOffrOfCovrg());
		print.setB14_decoffercd(ea1095Info.getEa1095CCalMonDec().getOffrOfCovrg());

		print.setB15_annualshrlowestcostmthlypremamt(ea1095Info.getEa1095CCalMonAll().getEmpShr().doubleValue());
		print.setB15_januaryamt(ea1095Info.getEa1095CCalMonJan().getEmpShr().doubleValue());
		print.setB15_februaryamt(ea1095Info.getEa1095CCalMonFeb().getEmpShr().doubleValue());
		print.setB15_marchamt(ea1095Info.getEa1095CCalMonMar().getEmpShr().doubleValue());
		print.setB15_aprilamt(ea1095Info.getEa1095CCalMonApr().getEmpShr().doubleValue());
		print.setB15_mayamt(ea1095Info.getEa1095CCalMonMay().getEmpShr().doubleValue());
		print.setB15_juneamt(ea1095Info.getEa1095CCalMonJun().getEmpShr().doubleValue());
		print.setB15_julyamt(ea1095Info.getEa1095CCalMonJul().getEmpShr().doubleValue());
		print.setB15_augustamt(ea1095Info.getEa1095CCalMonAug().getEmpShr().doubleValue());
		print.setB15_septemberamt(ea1095Info.getEa1095CCalMonSep().getEmpShr().doubleValue());
		print.setB15_octoberamt(ea1095Info.getEa1095CCalMonOct().getEmpShr().doubleValue());
		print.setB15_novemberamt(ea1095Info.getEa1095CCalMonNov().getEmpShr().doubleValue());
		print.setB15_decemberamt(ea1095Info.getEa1095CCalMonDec().getEmpShr().doubleValue());

		print.setB16_annualsafeharborcd(ea1095Info.getEa1095CCalMonAll().getSafeHrbr());
		print.setB16_jansafeharborcd(ea1095Info.getEa1095CCalMonJan().getSafeHrbr());
		print.setB16_febsafeharborcd(ea1095Info.getEa1095CCalMonFeb().getSafeHrbr());
		print.setB16_marsafeharborcd(ea1095Info.getEa1095CCalMonMar().getSafeHrbr());
		print.setB16_aprsafeharborcd(ea1095Info.getEa1095CCalMonApr().getSafeHrbr());
		print.setB16_maysafeharborcd(ea1095Info.getEa1095CCalMonMay().getSafeHrbr());
		print.setB16_junsafeharborcd(ea1095Info.getEa1095CCalMonJun().getSafeHrbr());
		print.setB16_julsafeharborcd(ea1095Info.getEa1095CCalMonJul().getSafeHrbr());
		print.setB16_augsafeharborcd(ea1095Info.getEa1095CCalMonAug().getSafeHrbr());
		print.setB16_sepsafeharborcd(ea1095Info.getEa1095CCalMonSep().getSafeHrbr());
		print.setB16_octsafeharborcd(ea1095Info.getEa1095CCalMonOct().getSafeHrbr());
		print.setB16_novsafeharborcd(ea1095Info.getEa1095CCalMonNov().getSafeHrbr());
		print.setB16_decsafeharborcd(ea1095Info.getEa1095CCalMonDec().getSafeHrbr());

		print.setPiii_coveredindividualind(ea1095Info.isSelfIns() ? "Y" : "N");
		print.setPlanStrtMon(ea1095Info.getPlanStrtMon());
	}

	private void fillPartIIIBoxesC(EA1095InfoCommand ea1095Info, Aca1095CPrint print, Boolean printPage4) {

		// start at box 17 and move to box 34
		int box = 17;

		List<EA1095CInfoCommand> eA1095CCovrgInfo = ea1095Info.getEa1095CCovrg();

		//Get List of Covered Individuals
		eA1095CCovrgInfo = ea1095Service.retrieveEA1095CCovrgInfo(ea1095Info.getActiveTab(), ea1095Info.getEmpNbr(), ea1095Info.getCurrentYr());
		ea1095Info.setEa1095CCovrg(eA1095CCovrgInfo);

		//If printing page 4, cut down the list for the report
		if(printPage4 && eA1095CCovrgInfo.size() >= 19) {
			int size = eA1095CCovrgInfo.size();
			eA1095CCovrgInfo = eA1095CCovrgInfo.subList(18, size);
			box = 23;
		}

		//Go through part IV boxes on the form and instantiate them blank or with data
		for(EA1095CInfoCommand ea1095 : eA1095CCovrgInfo) {

			print.setCovrgFirstName(box, ea1095.getfName());
			print.setCovrgMiddleName(box, ea1095.getmName());
			print.setCovrgLastName(box, ea1095.getlName());
			print.setCovrgSuffix(box, ea1095.getNameGenDescr());

			String ssn = ea1095.getSsn().replaceAll("-", "").trim();
			String box_ssn = "";
			String box_birthDt = "";

			//if ssn then do not display dob.  if no ssn then display dob.
			if (ssn == null || ssn.equals("")) {
				box_birthDt = ea1095.getDob().replaceAll("-", "").trim();
			} else {
				box_ssn = ssn;
			}

			print.setCovrgSSN(box, box_ssn);
			print.setCovrgBirthDt(box, box_birthDt);
			print.setCovrgAnnualInd(box, ea1095.isAllMon());
			print.setCovrgJanuaryInd(box, ea1095.isJan());
			print.setCovrgFebruaryInd(box, ea1095.isFeb());
			print.setCovrgMarchInd(box, ea1095.isMar());
			print.setCovrgAprilInd(box, ea1095.isApr());
			print.setCovrgMayInd(box, ea1095.isMay());
			print.setCovrgJuneInd(box, ea1095.isJun());
			print.setCovrgJulyInd(box, ea1095.isJul());
			print.setCovrgAugustInd(box, ea1095.isAug());
			print.setCovrgSeptemberInd(box, ea1095.isSep());
			print.setCovrgOctoberInd(box, ea1095.isOct());
			print.setCovrgNovemberInd(box, ea1095.isNov());
			print.setCovrgDecemberInd(box, ea1095.isDec());	

			// increment box
			box++;
		}
	}

	@RequestMapping(method = RequestMethod.POST, params = "mySubmitSortButton=Submit Query")
	public ModelAndView sortColumns(@ModelAttribute("EA1095InfoCommand") EA1095InfoCommand ea1095Info, BindingResult result) {
		ModelMap model = new ModelMap();

		if (ea1095Info.getEa1095().equals("B")) {

			ea1095Info.setCurrentPage1095B(1);
			ea1095Info.setSelectedPage1095B(1);

			// Retrieve data
			Page<EA1095BInfoCommand> ea1095BData = new Page<EA1095BInfoCommand>();
			ea1095Info.setSortOrder1095B("desc".equals(ea1095Info.getSortOrder1095B()) ? "asc" : "desc");
			ea1095BData = ea1095Service.retrieveEA1095BCovrgInfo(ea1095Info.getActiveTab(), ea1095Info.getEmpNbr(), ea1095Info.getCurrentYr(), ea1095Info.getSortColumn1095B(), ea1095Info.getSortOrder1095B(), ea1095Info.getCurrentPage1095B(), ea1095Info.getPageSize1095B());
			if (!ea1095BData.getPageItems().isEmpty()) {
				ea1095Info.setEa1095BData(ea1095BData);
				ea1095Info.setTotalPages1095B(ea1095BData.getPagesAvailable());
			}
		} else if (ea1095Info.getEa1095().equals("C")) {

			ea1095Info.setCurrentPage1095C(1);
			ea1095Info.setSelectedPage1095C(1);

			// Retrieve data
			Page<EA1095CInfoCommand> ea1095CData = new Page<EA1095CInfoCommand>();
			ea1095Info.setSortOrder1095C("desc".equals(ea1095Info.getSortOrder1095C()) ? "asc" : "desc");
			ea1095CData = ea1095Service.retrieveEA1095CCovrgInfo(ea1095Info.getActiveTab(), ea1095Info.getEmpNbr(), ea1095Info.getCurrentYr(), ea1095Info.getSortColumn1095C(), ea1095Info.getSortOrder1095C(), ea1095Info.getCurrentPage1095C(), ea1095Info.getPageSize1095C());
			if (!ea1095CData.getPageItems().isEmpty()) {
				ea1095Info.setEa1095CCovrgData(ea1095CData);
				ea1095Info.setTotalPages1095C(ea1095CData.getPagesAvailable());
			}
		}

		ea1095Info.setSelectedRow(0);
		ea1095Info.setFocusField("START");
		model.addAttribute("errorList", ValidationUtil.getErrorList(result));

		model.addAttribute("EA1095InfoCommand", ea1095Info);
		return new ModelAndView(VIEWPATH, model);
	}

	@RequestMapping(method = RequestMethod.POST, params = "mySubmitSelectedPageButton=Submit Query")
	public ModelAndView selectedPage(@ModelAttribute("EA1095InfoCommand") EA1095InfoCommand ea1095Info, BindingResult result, HttpServletRequest servletRequest ) {
		ModelMap model = new ModelMap();

		if (ea1095Info.getEa1095().equals("B")) {

			ea1095Info.setCurrentPage1095B(ea1095Info.getSelectedPage1095B());

			// Retrieve data
			Page<EA1095BInfoCommand> ea1095BData = new Page<EA1095BInfoCommand>();
			ea1095BData = ea1095Service.retrieveEA1095BCovrgInfo(ea1095Info.getActiveTab(), ea1095Info.getEmpNbr(), ea1095Info.getCurrentYr(), ea1095Info.getSortColumn1095B(), ea1095Info.getSortOrder1095B(), ea1095Info.getCurrentPage1095B(), ea1095Info.getPageSize1095B());
			if (!ea1095BData.getPageItems().isEmpty()) {
				ea1095Info.setEa1095BData(ea1095BData);
				ea1095Info.setTotalPages1095B(ea1095BData.getPagesAvailable());
			}
		} else if (ea1095Info.getEa1095().equals("C")) {

			ea1095Info.setCurrentPage1095C(ea1095Info.getSelectedPage1095C());

			// Retrieve data
			Page<EA1095CInfoCommand> acaYTD1095CData = new Page<EA1095CInfoCommand>();
			acaYTD1095CData = ea1095Service.retrieveEA1095CCovrgInfo(ea1095Info.getActiveTab(), ea1095Info.getEmpNbr(), ea1095Info.getCurrentYr(), ea1095Info.getSortColumn1095C(), ea1095Info.getSortOrder1095C(), ea1095Info.getCurrentPage1095C(), ea1095Info.getPageSize1095C());
			if (!acaYTD1095CData.getPageItems().isEmpty()) {
				ea1095Info.setEa1095CCovrgData(acaYTD1095CData);
				ea1095Info.setTotalPages1095C(acaYTD1095CData.getPagesAvailable());
			}
		}

		ea1095Info.setSelectedRow(0);
		ea1095Info.setFocusField("START");
		model.addAttribute("errorList", ValidationUtil.getErrorList(result));

		// Reset the Electronic Consent Pop-up
		ea1095Info.setShowEA1095ElecConsntPopup(false);

		model.addAttribute("EA1095InfoCommand", ea1095Info);
		return new ModelAndView(VIEWPATH, model);
	}

	@RequestMapping(method = RequestMethod.POST, params = "mySubmitPaginationButton=Submit Query")
	public ModelAndView paginationPage(@ModelAttribute("EA1095InfoCommand") EA1095InfoCommand ea1095Info, BindingResult result, HttpServletRequest servletRequest ) {
		ModelMap model = new ModelMap();

		if (ea1095Info.getEa1095().equals("B")) {

			ea1095Info.setCurrentPage1095B(ea1095Info.getSelectedPage1095B());
			ea1095Info.setSelectedRow(0);

			// Retrieve data
			Page<EA1095BInfoCommand> ea1095BData = new Page<EA1095BInfoCommand>();
			ea1095BData = ea1095Service.retrieveEA1095BCovrgInfo(ea1095Info.getActiveTab(), ea1095Info.getEmpNbr(), ea1095Info.getCurrentYr(), ea1095Info.getSortColumn1095B(), ea1095Info.getSortOrder1095B(), ea1095Info.getCurrentPage1095B(), ea1095Info.getPageSize1095B());
			if (!ea1095BData.getPageItems().isEmpty()) {
				ea1095Info.setEa1095BData(ea1095BData);
				ea1095Info.setTotalPages1095B(ea1095BData.getPagesAvailable());
			}
		} else if (ea1095Info.getEa1095().equals("C")) {

			ea1095Info.setCurrentPage1095C(ea1095Info.getSelectedPage1095C());
			ea1095Info.setSelectedRow(0);

			// Retrieve data
			Page<EA1095CInfoCommand> acaYTD1095CData = new Page<EA1095CInfoCommand>();
			acaYTD1095CData = ea1095Service.retrieveEA1095CCovrgInfo(ea1095Info.getActiveTab(), ea1095Info.getEmpNbr(), ea1095Info.getCurrentYr(), ea1095Info.getSortColumn1095C(), ea1095Info.getSortOrder1095C(), ea1095Info.getCurrentPage1095C(), ea1095Info.getPageSize1095C());
			if (!acaYTD1095CData.getPageItems().isEmpty()) {
				ea1095Info.setEa1095CCovrgData(acaYTD1095CData);
				ea1095Info.setTotalPages1095C(acaYTD1095CData.getPagesAvailable());
			}
		}

		ea1095Info.setFocusField("START");
		model.addAttribute("errorList", ValidationUtil.getErrorList(result));

		// Reset the Electronic Consent Pop-up
		ea1095Info.setShowEA1095ElecConsntPopup(false);

		model.addAttribute("EA1095InfoCommand", ea1095Info);
		return new ModelAndView(VIEWPATH, model);
	}

	public IReport setupBReport(ParameterReport report, List<Aca1095BPrint> forms, String year) {
		report.getParameters().clear();

		if (year == null) {
			return report;
		}

		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(forms);
		report.setDataSource(dataSource);

		return report;
	}

	public IReport setupCReport(ParameterReport report, List<Aca1095CPrint> forms, String year) {
		report.getParameters().clear();

		if (year == null) {
			return report;
		}

		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(forms);
		report.setDataSource(dataSource);

		return report;
	}
}