package com.esc20.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.esc20.dao.EA1095Dao;
import com.esc20.model.BeaUsers;
import com.esc20.model.BhrAca1095cEmpHist;
import com.esc20.model.BhrEmpDemo;
import com.esc20.model.BrRptngContact;
import com.esc20.nonDBModels.Aca1095BPrint;
import com.esc20.nonDBModels.Aca1095CPrint;
import com.esc20.nonDBModels.BCoveredHistory;
import com.esc20.nonDBModels.CCoveredHistory;
import com.esc20.nonDBModels.Code;
import com.esc20.nonDBModels.District;
import com.esc20.nonDBModels.EA1095CEmployerShare;
import com.esc20.nonDBModels.Options;
import com.esc20.nonDBModels.report.IReport;
import com.esc20.nonDBModels.report.ParameterReport;
import com.esc20.nonDBModels.report.ReportParameterConnection;
import com.esc20.service.IndexService;
import com.esc20.service.InquiryService;
import com.esc20.service.PDFService;
import com.esc20.service.ReferenceService;
import com.esc20.util.DateUtil;
import com.esc20.util.MailUtil;
import com.esc20.util.StringUtil;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Controller
@RequestMapping("/information1095")
public class Information1095Controller{
	private Logger logger = LoggerFactory.getLogger(Information1095Controller.class);
	
	@Autowired
	private IndexService indexService;

	@Autowired
	private InquiryService service;
    
    @Autowired
    private ReferenceService referenceService;
	
	@Autowired
	private PDFService pDFService;
    
	private final String module = "1095 Information";
	
	@RequestMapping("information1095")
	public ModelAndView getInformation1095(HttpServletRequest req) {
		HttpSession session = req.getSession();
		BeaUsers user = (BeaUsers) session.getAttribute("user");
		BhrEmpDemo userDetail = this.indexService.getUserDetail(user.getEmpNbr());
        Options options = this.indexService.getOptions();
        String district = (String)session.getAttribute("districtId");
        District districtInfo = this.indexService.getDistrict(district);
        userDetail.setEmpNbr(user.getEmpNbr());
        userDetail.setDob(DateUtil.formatDate(userDetail.getDob(), "yyyyMMdd", "MM-dd-yyyy"));
        String phone = districtInfo.getPhone();
        districtInfo.setPhone(StringUtil.left(phone, 3)+"-"+StringUtil.mid(phone, 4, 3)+"-"+StringUtil.right(phone, 4));

		session.setAttribute("userDetail", userDetail);
        session.setAttribute("companyId", district);
        session.setAttribute("options", options);
        session.setAttribute("district", districtInfo);
		ModelAndView mav = new ModelAndView();
		mav = init1095(mav, session, null, 1, 1, null, null, null);
		return mav;
	}

	@RequestMapping("update1095Consent")
	public ModelAndView update1095Consent(HttpServletRequest req, String year, String consent) {
		HttpSession session = req.getSession();
		ModelAndView mav = new ModelAndView();
		if(consent==null||year==null) {
			mav.setViewName("visitFailed");
			mav.addObject("module", module);
			mav.addObject("action", "Update 1095 consent");
			mav.addObject("errorMsg", "Not all mandotary fields provided.");
			return mav;
		}
		BhrEmpDemo userDetail = (BhrEmpDemo) session.getAttribute("userDetail");
		String employeeNumber = userDetail.getEmpNbr();
		Boolean isSuccess = this.service.update1095ElecConsent(employeeNumber, consent);
		mav.setViewName("/inquiry/information1095");
		mav = init1095(mav, session, year, 1, 1, null, null, null);
		this.sendEmail(userDetail.getNameF(), userDetail.getNameL(), userDetail.getEmail(), userDetail.getHmEmail(), consent);
		mav.addObject("isUpdate", true);
		mav.addObject("isSuccess", isSuccess);
		return mav;
	}
	
	public Integer sendEmail(String userFirstName, String userLastName, String userWorkEmail, String userHomeEmail, String ea1095ElecConsnt) {
		StringBuilder messageContents = new StringBuilder();
		messageContents.append(userFirstName + " " +userLastName + ", \n\n");
		messageContents.append("This receipt confirms you selected ");
		messageContents.append((ea1095ElecConsnt.equals("Y") ? " YES " : " NO "));
		messageContents.append("in participating in the 1095 Electronic Process. \n");
		messageContents.append("Effective immediately on ");

		DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		messageContents.append(dateFormat.format(cal.getTime()) + "\n");

		String subject = "A MESSAGE FROM 1095 ELECTRONIC CONSENT";

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
	
	@RequestMapping("information1095ByYear")
	public ModelAndView getInformation1095ByYear(HttpServletRequest req, String year) {
		HttpSession session = req.getSession();
		ModelAndView mav = new ModelAndView();
		if(year==null) {
			mav.setViewName("visitFailed");
			mav.addObject("module", module);
			mav.addObject("action", "Get 1095 information by year");
			mav.addObject("errorMsg", "Not all mandotary fields provided.");
			return mav;
		}
		mav.setViewName("/inquiry/information1095");
		mav = init1095(mav, session, year, 1, 1, null, null, null);
		return mav;
	}

	@RequestMapping("sortOrChangePageForTypeB")
	public ModelAndView sortOrChangePageForTypeB(HttpServletRequest req, String year, String BPageNo, String sortBy,
			String sortOrder) {
		HttpSession session = req.getSession();
		ModelAndView mav = new ModelAndView();
		if(year==null||BPageNo==null) {
			mav.setViewName("visitFailed");
			mav.addObject("module", module);
			mav.addObject("action", "Get 1095-B information");
			mav.addObject("errorMsg", "Not all mandotary fields provided.");
			return mav;
		}
		mav.setViewName("/inquiry/information1095");
		mav = init1095(mav, session, year, Integer.parseInt(BPageNo), 1, sortBy, sortOrder, "B");
		return mav;
	}

	@RequestMapping("sortOrChangePageForTypeC")
	public ModelAndView sortOrChangePageForTypeC(HttpServletRequest req, String year, String CPageNo, String sortBy,
			String sortOrder) {
		HttpSession session = req.getSession();
		ModelAndView mav = new ModelAndView();
		if(year==null||CPageNo==null) {
			mav.setViewName("visitFailed");
			mav.addObject("module", module);
			mav.addObject("action", "Get 1095-C information");
			mav.addObject("errorMsg", "Not all mandotary fields provided.");
			return mav;
		}
		mav.setViewName("/inquiry/information1095");
		mav = init1095(mav, session, year, 1, Integer.parseInt(CPageNo), sortBy, sortOrder, "C");
		return mav;
	}

	private ModelAndView init1095(ModelAndView mav, HttpSession session, String year, Integer BPageNo, Integer CPageNo,
			String sortBy, String sortOrder, String type) {
		mav.setViewName("/inquiry/information1095");
		BhrEmpDemo userDetail = (BhrEmpDemo) session.getAttribute("userDetail");
		String employeeNumber = userDetail.getEmpNbr();
		List<String> years = this.service.retrieveAvailable1095CalYrs(employeeNumber);
		if (years != null && years.size() > 0) {
			mav.addObject("empty", false);
		} else {
			mav.addObject("empty", true);
			return mav;
		}
		if (year == null)
			year = DateUtil.getLatestYear(years);
		Options options = ((Options) session.getAttribute("options"));
		String message = options.getMessageElecConsent1095().trim();
		String consent = this.service.get1095Consent(employeeNumber);
		Integer BTotal = this.service.getBInfoTotal(employeeNumber, year);
		Integer BTotalPage = this.service.getBInfoTotal(employeeNumber, year)/ 20;
		Integer BRemianList =  this.service.getBInfoTotal(employeeNumber, year) % 20;
		if(BRemianList >0) {
			BTotal = BTotalPage + 1;
		}
		else
		{
			BTotal = BTotalPage;
		}
		Integer CTotal = this.service.getCInfoTotal(employeeNumber, year);
		List<Code> bCovrgTypList = this.service.retrieveEA1095BEmpInfo(employeeNumber,year);
		List<BCoveredHistory> bList;
		if (("B").equals(type))
			bList = this.service.retrieveEA1095BInfo(employeeNumber, year, sortBy, sortOrder, BPageNo);
		else
			bList = this.service.retrieveEA1095BInfo(employeeNumber, year, null, null, 1);
		
		for(BCoveredHistory bItem: bList) {
			List<Code> gens = referenceService.getGenerations();
			 for(Code gen: gens) {
			    	if(bItem.getNameGen() != null && gen.getCode().trim().equals(bItem.getNameGen().toString().trim())) {
			    		bItem.setGenDescription(gen.getDescription());
			    	}
			    }
		}
		List<EA1095CEmployerShare> cEmpList = this.service.retrieveEA1095CEmpInfo(employeeNumber,year);
		List<CCoveredHistory> cList;
		if (("C").equals(type))
			cList = this.service.retrieveEA1095CInfo(employeeNumber, year, sortBy, sortOrder, CPageNo);
		else
			cList = this.service.retrieveEA1095CInfo(employeeNumber, year, null, null, 1);
		
		for(CCoveredHistory cItem: cList) {
			List<Code> gens = referenceService.getGenerations();
			 for(Code gen: gens) {
			    	if(cItem.getNameGen() != null && gen.getCode().trim().equals(cItem.getNameGen().toString().trim())) {
			    		cItem.setGenDescription(gen.getDescription());
			    	}
			    }
		}
		if (bCovrgTypList.size() > 0) {
			mav.addObject("BCovrgTyp", bCovrgTypList.get(0).getCode());
			mav.addObject("BCovrgTypDescr", bCovrgTypList.get(0).getDescription());
		}
		if (type == null) {
			if (bList != null && bList.size() > 0)
				mav.addObject("type", "B");
			else
				mav.addObject("type", "C");
		} else {
			mav.addObject("type", type);
		}
		String latestYear = DateUtil.getLatestYear(years);
		mav.addObject("latestYear", latestYear);
		mav.addObject("years", years);
		mav.addObject("selectedYear", year);
		mav.addObject("consent", consent);
		mav.addObject("message", message);
		mav.addObject("bList", bList);
		mav.addObject("cList", cList);
		if(bList.isEmpty() || cList.isEmpty()) {
			mav.addObject("disabled", true);
		} else {
			mav.addObject("disabled", false);
		}
		mav.addObject("BPageNo", BPageNo);
		mav.addObject("CPageNo", CPageNo);
		mav.addObject("cEmpList", cEmpList);
		mav.addObject("BTotal", BTotal);
		mav.addObject("CTotal", CTotal);
		mav.addObject("sortBy", sortBy);
		mav.addObject("sortOrder", sortOrder);
		return mav;
	}
	
	@RequestMapping("exportPDF")
	public void exportPDF(HttpServletRequest request, HttpServletResponse response, String year, String type)
			throws Exception {
		response.setContentType("application/x-msdownload;charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment;filename=1095 " + type.toUpperCase() + " Form for " + year + ".pdf");
		String path = request.getServletContext().getRealPath("/");
		if (path != null && !path.endsWith("\\")) {
			path = path.concat("\\");
		}
		pDFService.setRealPath(path);

		ParameterReport report = new ParameterReport();
		report.setTitle("1095" + type.toUpperCase() + " Form for " + year);
		report.setId("1095" + type.toUpperCase() + year);
		report.setFileName("1095" + type.toUpperCase() + " Form for " + year);
		report.setSortable(false);
		report.setFilterable(false);
		IReport ireport;
		JasperPrint jasperPrint;
		if (StringUtil.equals(type.trim().toUpperCase(), "B")) {
			List<Aca1095BPrint> aca1095BPrint = generate1095BPrint(request, year);
			ireport = setupBReport(report, aca1095BPrint, year);
			jasperPrint = pDFService.buildReport(ireport);
			JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
		} else if (StringUtil.equals(type.trim().toUpperCase(), "C")) {
			List<Aca1095CPrint> aca1095CPrint = generate1095CPrint(request, year);
			ireport = setupCReport(report, aca1095CPrint, year);
			jasperPrint = pDFService.buildReport(ireport);
			JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
		}
	}

	@RequestMapping("printPDF")
	@ResponseBody
	public void printPDF(HttpServletRequest request, HttpServletResponse response, String year, String type)
			throws Exception {
		response.setContentType("application/pdf;charset=UTF-8");
		response.setHeader("Content-Disposition", "application/pdf;filename=1095 " + type.toUpperCase() + " Form for " + year + ".pdf");
		String path = request.getServletContext().getRealPath("/");
		if (path != null && !path.endsWith("\\")) {
			path = path.concat("\\");
		}
		pDFService.setRealPath(path);

		ParameterReport report = new ParameterReport();
		report.setTitle("1095" + type.toUpperCase() + " Form for " + year);
		report.setId("1095" + type.toUpperCase() + year);
		report.setFileName("1095" + type.toUpperCase() + " Form for " + year);
		report.setSortable(false);
		report.setFilterable(false);
		IReport ireport;
		JasperPrint jasperPrint;
		if (StringUtil.equals(type.trim().toUpperCase(), "B")) {
			List<Aca1095BPrint> aca1095BPrint = generate1095BPrint(request, year);
			ireport = setupBReport(report, aca1095BPrint, year);
			jasperPrint = pDFService.buildReport(ireport);
			JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
		} else if (StringUtil.equals(type.trim().toUpperCase(), "C")) {
			List<Aca1095CPrint> aca1095CPrint = generate1095CPrint(request, year);
			ireport = setupCReport(report, aca1095CPrint, year);
			jasperPrint = pDFService.buildReport(ireport);
			JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
		}
	}
	
	private List<Aca1095BPrint> generate1095BPrint(HttpServletRequest request, String year) {
		Aca1095BPrint print = new Aca1095BPrint();
		HttpSession session = request.getSession();
		BhrEmpDemo userDetail = (BhrEmpDemo) session.getAttribute("userDetail");
		String employeeNumber = userDetail.getEmpNbr();
		List<String> years = this.service.retrieveAvailable1095CalYrs(employeeNumber);
		District district = (District) session.getAttribute("district");
		if (year == null)
			year = DateUtil.getLatestYear(years);
		List<Code> bCovrgTypList = this.service.retrieveEA1095BEmpInfo(employeeNumber,year);
		List<BCoveredHistory> bList = this.service.retrieveEA1095BInfo(employeeNumber, year, null, null, 1);
		String coverType = "";
		if (bCovrgTypList.size() > 0) {
			coverType = bCovrgTypList.get(0).getCode();
			print.setB8_policyorigincd(coverType);
		}
		String taxYr = year==null?"":year;
		print.setFormpagenbr("1");
		print.setTaxyr(taxYr);
		print.setWebroot(System.getProperty("EmployeeAccess.root")+"\\");
		this.fillPartIBoxesB(userDetail, print);
		BrRptngContact contact = this.service.getReportingContact();
		if(coverType=="A")
			this.fillPartIIBoxesB(print, contact);
		this.fillPartIIIBoxesB(district, print);
		this.fillPartIVBoxesB(bList, print, false);
		List<Aca1095BPrint> forms = new ArrayList<Aca1095BPrint>();
		forms.add(print);

		//Fill out 1095B Form - Page 3 continuation Sheet 1
		if(bList.size() >= 7){
			Aca1095BPrint print2 = new Aca1095BPrint();
			fillPartIBoxesB(userDetail, print2);
			if(coverType=="A")
				this.fillPartIIBoxesB(print, contact);
			fillPartIIIBoxesB(district, print2);
			fillPartIVBoxesB(bList, print2, false);
			print2.setFormpagenbr("3");
			print2.setTaxyr(taxYr);
			print2.setWebroot(System.getProperty("EmployeeAccess.root")+"\\");
			forms.add(print2);
		}

		//Fill out 1095B Form - Page 4 continuation Sheet 2
		if(bList.size() >= 19){
			Aca1095BPrint print3 = new Aca1095BPrint();
			fillPartIBoxesB(userDetail, print3);
			if(coverType=="A")
				this.fillPartIIBoxesB(print, contact);
			fillPartIIIBoxesB(district, print3);
			fillPartIVBoxesB(bList, print3, true);
			print3.setFormpagenbr("3");
			print3.setTaxyr(taxYr);
			print3.setWebroot(System.getProperty("EmployeeAccess.root")+"\\");
			forms.add(print3);
		}
		return forms;
	}

	
	private List<Aca1095CPrint> generate1095CPrint(HttpServletRequest request, String year) {
		Aca1095CPrint print = new Aca1095CPrint();
		HttpSession session = request.getSession();
		BhrEmpDemo userDetail = (BhrEmpDemo) session.getAttribute("userDetail");
		String employeeNumber = userDetail.getEmpNbr();
		List<String> years = this.service.retrieveAvailable1095CalYrs(employeeNumber);
		District district = (District) session.getAttribute("district");
		if (year == null)
			year = DateUtil.getLatestYear(years);
		List<BhrAca1095cEmpHist> cCovrgTypList = this.service.retrieveEA1095CEmpInfoPrint(employeeNumber,year);
		List<CCoveredHistory> cList = this.service.retrieveEA1095CInfo(employeeNumber, year, null, null, 1);
		String taxYr = year==null?"":year;
		print.setFormpagenbr("1");
		print.setTaxyr(taxYr);
		print.setWebroot(System.getProperty("EmployeeAccess.root")+"\\");
		this.fillPartIEmpBoxesC(userDetail, print);
		this.fillPartIALEBoxesC(district, print);
		this.fillPartIIBoxesC(cCovrgTypList, print);
		this.fillPartIIIBoxesC(cList, print, false);
		List<Aca1095CPrint> forms = new ArrayList<Aca1095CPrint>();
		forms.add(print);

		//Fill out 1095C Form - Page 3 continuation Sheet 1
		if(cList.size() >= 7) {
			Aca1095CPrint print2 = new Aca1095CPrint();
			fillPartIEmpBoxesC(userDetail, print2);
			fillPartIALEBoxesC(district, print2);
			fillPartIIBoxesC(cCovrgTypList, print2);
			fillPartIIIBoxesC(cList, print2, false);
			print2.setFormpagenbr("3");
			print2.setTaxyr(taxYr);
			print2.setWebroot(System.getProperty("EmployeeAccess.root")+"\\");
			forms.add(print2);
		}

		//Fill out 1095C Form - Page 4 continuation Sheet 2
		if(cList.size() >= 19) {
			Aca1095CPrint print3 = new Aca1095CPrint();
			fillPartIEmpBoxesC(userDetail, print3);
			fillPartIALEBoxesC(district, print3);
			fillPartIIBoxesC(cCovrgTypList, print3);
			fillPartIIIBoxesC(cList, print3, true);
			print3.setFormpagenbr("3");
			print3.setTaxyr(taxYr);
			print3.setWebroot(System.getProperty("EmployeeAccess.root")+"\\");
			forms.add(print3);
		}
		return forms;
	}
	
	private IReport setupBReport(ParameterReport report, List<Aca1095BPrint> forms, String year) throws Exception {
		report.getParameters().clear();
		ReportParameterConnection parameter = new ReportParameterConnection();
		parameter.setName("subRptConnection");
		parameter.setConnection(pDFService.getConn());
		report.getParameters().add(parameter);
		if (year == null) {
			return report;
		} else {
			report.setFileName("DHrs5250Form1095B" + year);
		}
		report.setDataSource(new JRBeanCollectionDataSource(forms));
		return report;
	}

	private IReport setupCReport(ParameterReport report, List<Aca1095CPrint> forms, String year) throws Exception {
		report.getParameters().clear();
		ReportParameterConnection parameter = new ReportParameterConnection();
		parameter.setName("subRptConnection");
		parameter.setConnection(pDFService.getConn());
		report.getParameters().add(parameter);
		if (year == null) {
			return report;
		} else {
			report.setFileName("DHrs5255Form1095C" + year);
		}
		report.setDataSource(new JRBeanCollectionDataSource(forms));
		return report;
	}
	
	private void fillPartIBoxesB(BhrEmpDemo userDetail, Aca1095BPrint print) {
		print.setB1_personfirstnm(userDetail.getNameF()==null?"":userDetail.getNameF());
		print.setB1_personmiddlenm(userDetail.getNameM()==null?"":userDetail.getNameM());
		print.setB1_personlastnm(userDetail.getNameL()==null?"":userDetail.getNameL());
		print.setB1_suffixnm(userDetail.getGenDescription()==null?"":userDetail.getGenDescription());

		String ssn = userDetail.getStaffId().replaceAll("-", "").trim();
		String b2_ssn = "";
		String b3_birthDt = "";
		if (ssn == null || ssn.equals("")) {
			b3_birthDt = userDetail.getDob().replaceAll("-", "").trim();
		} else {
			b2_ssn = ssn;
		}	
		
		print.setB2_ssn(b2_ssn);
		print.setB3_birthdt(b3_birthDt);
		print.setB4_addressline1txt((userDetail.getAddrNbr()==null?"":userDetail.getAddrNbr()+ " ") + userDetail.getAddrStr());
		print.setB5_citynm(userDetail.getAddrCity());
		print.setB6_usstatecd(userDetail.getAddrSt());
		print.setB7_uszipcd(userDetail.getAddrZip()==null?"":userDetail.getAddrZip());
		print.setB7_uszipextensioncd(userDetail.getAddrZip4()==null?"":userDetail.getAddrZip4().trim());
	}

	private void fillPartIIBoxesB(Aca1095BPrint print, BrRptngContact reportingContact) {
			print.setB10_businessnameline1txt(reportingContact.getShopName()==null?"":reportingContact.getShopName());
			print.setB11_ein(reportingContact.getShopEin()==null?"":reportingContact.getShopAddr());
			print.setB12_addressline1txt(reportingContact.getShopAddr()==null?"":reportingContact.getShopAddr());
			print.setB13_citynm(reportingContact.getShopCity()==null?"":reportingContact.getShopCity());
			print.setB14_usstatecd(reportingContact.getShopSt()==null?"":reportingContact.getShopSt());
			print.setB15_uszipcd(reportingContact.getShopZip()==null?"":reportingContact.getShopZip());
			print.setB15_uszipextensioncd(reportingContact.getShopZip4()==null?"":reportingContact.getShopZip4());
	}

	private void fillPartIIIBoxesB(District district,Aca1095BPrint print) {
		String ein = district.getEin();
		ein = StringUtil.left(ein, 2) + "-" + StringUtil.right(ein, 7);

		print.setB16_businessnameline1txt(StringUtil.trim(StringUtil.upper(district.getName())));
		print.setB17_ein(ein);
		print.setB18_contactphonenum(district.getPhone().replaceAll("-", ""));
		print.setB19_addressline1txt(StringUtil.trim(StringUtil.upper(district.getAddress())));
		print.setB20_citynm(StringUtil.trim(StringUtil.upper(district.getCity())));
		print.setB21_usstatecd(StringUtil.trim(StringUtil.upper(district.getState())));
		print.setB22_uszipcd(district.getZip()==null?"":district.getZip());
		print.setB22_uszipextensioncd(district.getZip4()==null?"":district.getZip4());
	}

   
    
	private void fillPartIVBoxesB(List<BCoveredHistory> bList, Aca1095BPrint print, Boolean printPage4) {
		int box = 23;
		List<Code> gens = referenceService.getGenerations();
		//if printin page 4, then cut down the list for the report
		if(printPage4 && bList.size() >= 19){
			int size = bList.size();
			bList = bList.subList(18, size);
			box = 29;
		}
		//logger.info("1095 B Infomation box : "+ box);

		//Go through part IV boxes on the form and instantiate them blank or with data
		for(int i=0; i<bList.size(); i++) {
			BCoveredHistory ea1095 = bList.get(i);
			print.setCovrgFirstName(box, ea1095.getNameF()==null?"":ea1095.getNameF());
			print.setCovrgMiddleName(box, ea1095.getNameM()==null?" ":ea1095.getNameM().trim()+" ");
			print.setCovrgLastName(box, ea1095.getNameL()==null?"":ea1095.getNameL());
			print.setCovrgSuffix(box, "");
		    for(Code gen: gens) {
		    	if(ea1095.getNameGen() != null && gen.getCode().equals(ea1095.getNameGen().toString())) {
		    		print.setCovrgSuffix(box, gen.getDescription());
		    	}
		    }
			String ssn = ea1095.getSsn().replaceAll("-", "").trim();
			String box_ssn = "";
			String box_birthDt = "";

			//if ssn then do not display dob.  if no ssn then display dob.
			if (ssn == null || ssn.equals("")) {
				box_birthDt = ea1095.getDob().replaceAll("-", "").trim();
			} else {
				box_ssn = ssn;
				//logger.info("1095 B Infomation box : "+ box+" SSN:" + ssn);
			}
			print.setCovrgSSN(box, box_ssn);
			print.setCovrgBirthDt(box, box_birthDt);
			print.setCovrgAnnualInd(box, ea1095.getMonAll() == 'Y'? true:false);
			print.setCovrgJanuaryInd(box, ea1095.getMon01() == 'Y'? true:false);
			print.setCovrgFebruaryInd(box, ea1095.getMon02() == 'Y'? true:false);
			print.setCovrgMarchInd(box, ea1095.getMon03() == 'Y'? true:false);
			print.setCovrgAprilInd(box, ea1095.getMon04() == 'Y'? true:false);
			print.setCovrgMayInd(box, ea1095.getMon05() == 'Y'? true:false);
			print.setCovrgJuneInd(box, ea1095.getMon06() == 'Y'? true:false);
			print.setCovrgJulyInd(box, ea1095.getMon07() == 'Y'? true:false);
			print.setCovrgAugustInd(box, ea1095.getMon08() == 'Y'? true:false);
			print.setCovrgSeptemberInd(box, ea1095.getMon09() == 'Y'? true:false);
			print.setCovrgOctoberInd(box, ea1095.getMon10() == 'Y'? true:false);
			print.setCovrgNovemberInd(box, ea1095.getMon11() == 'Y'? true:false);
			print.setCovrgDecemberInd(box, ea1095.getMon12() == 'Y'? true:false);	
			box++;
		}
	}

	private void fillPartIEmpBoxesC(BhrEmpDemo userDetail, Aca1095CPrint print) {
		print.setB1_personfirstnm(userDetail.getNameF()==null?"":userDetail.getNameF());
		print.setB1_personmiddlenm(userDetail.getNameM()==null?"":userDetail.getNameM());
		print.setB1_personlastnm(userDetail.getNameL()==null?"":userDetail.getNameL());
		print.setB1_suffixnm(userDetail.getGenDescription()==null?"":userDetail.getGenDescription());
		print.setB2_ssn(userDetail.getStaffId().replaceAll("-", "").trim());
		print.setB3_addressline1txt((userDetail.getAddrNbr()==null?"":userDetail.getAddrNbr()+ " ") + userDetail.getAddrStr());
		print.setB4_citynm(userDetail.getAddrCity());
		print.setB5_usstatecd(userDetail.getAddrSt());
		print.setB6_uszipcd(userDetail.getAddrZip()==null?"":userDetail.getAddrZip());
		print.setB6_uszipextensioncd(userDetail.getAddrZip4()==null?"":userDetail.getAddrZip4().trim());
	}

	private void fillPartIALEBoxesC(District district, Aca1095CPrint print) {
		String ein = district.getEin();
		ein = StringUtil.left(ein, 2) + "-" + StringUtil.right(ein, 7);

		print.setB7_businessnameline1txt(StringUtil.trim(StringUtil.upper(district.getName())));
		print.setB8_ein(ein);
		print.setB9_addressline1txt(StringUtil.trim(StringUtil.upper(district.getAddress())));
		print.setB10_contactphonenum(district.getPhone().replaceAll("-", ""));
		print.setB11_citynm(StringUtil.trim(StringUtil.upper(district.getCity())));
		print.setB12_usstatecd(StringUtil.trim(StringUtil.upper(district.getState())));
		print.setB13_uszipcd(district.getZip()==null?"":district.getZip());
		print.setB13_uszipextensioncd(district.getZip4()==null?"":district.getZip4());
	}

	private void fillPartIIBoxesC(List<BhrAca1095cEmpHist> cCovrgTypList, Aca1095CPrint print) {

		for(BhrAca1095cEmpHist hist: cCovrgTypList) {
			//logger.info("1095 C Mon for PDF: '" + hist.getId().getCalMon()+"'");
			if("ALL".equals(hist.getId().getCalMon().trim())) {
				print.setB14_annualofferofcoveragecd(hist.getOffrOfCovrg()==null?"":hist.getOffrOfCovrg().trim());
				print.setB15_annualshrlowestcostmthlypremamt(hist.getEmpShr()==null?new Double(0):hist.getEmpShr().doubleValue());
				print.setB16_annualsafeharborcd(hist.getSafeHrbr()==null?"":hist.getSafeHrbr().trim());
			} else if("01".equals(hist.getId().getCalMon().trim())) {
				print.setB14_janoffercd(hist.getOffrOfCovrg()==null?"":hist.getOffrOfCovrg().trim());
				print.setB15_januaryamt(hist.getEmpShr()==null?new Double(0):hist.getEmpShr().doubleValue());
				print.setB16_jansafeharborcd(hist.getSafeHrbr()==null?"":hist.getSafeHrbr().trim());
			} else if("02".equals(hist.getId().getCalMon().trim())) {
				print.setB14_feboffercd(hist.getOffrOfCovrg()==null?"":hist.getOffrOfCovrg().trim());
				print.setB15_februaryamt(hist.getEmpShr()==null?new Double(0):hist.getEmpShr().doubleValue());
				print.setB16_febsafeharborcd(hist.getSafeHrbr()==null?"":hist.getSafeHrbr().trim());
			} else if("03".equals(hist.getId().getCalMon().trim())) {
				print.setB14_maroffercd(hist.getOffrOfCovrg()==null?"":hist.getOffrOfCovrg().trim());
				print.setB15_marchamt(hist.getEmpShr()==null?new Double(0):hist.getEmpShr().doubleValue());
				print.setB16_marsafeharborcd(hist.getSafeHrbr()==null?"":hist.getSafeHrbr().trim());
			} else if("04".equals(hist.getId().getCalMon().trim())) {
				print.setB14_aproffercd(hist.getOffrOfCovrg()==null?"":hist.getOffrOfCovrg().trim());
				print.setB15_aprilamt(hist.getEmpShr()==null?new Double(0):hist.getEmpShr().doubleValue());
				print.setB16_aprsafeharborcd(hist.getSafeHrbr()==null?"":hist.getSafeHrbr().trim());
			} else if("05".equals(hist.getId().getCalMon().trim())) {
				print.setB14_mayoffercd(hist.getOffrOfCovrg()==null?"":hist.getOffrOfCovrg().trim());
				print.setB15_mayamt(hist.getEmpShr()==null?new Double(0):hist.getEmpShr().doubleValue());
				print.setB16_maysafeharborcd(hist.getSafeHrbr()==null?"":hist.getSafeHrbr().trim());
			} else if("06".equals(hist.getId().getCalMon().trim())) {
				print.setB14_junoffercd(hist.getOffrOfCovrg()==null?"":hist.getOffrOfCovrg().trim());
				print.setB15_juneamt(hist.getEmpShr()==null?new Double(0):hist.getEmpShr().doubleValue());
				print.setB16_junsafeharborcd(hist.getSafeHrbr()==null?"":hist.getSafeHrbr().trim());
			} else if("07".equals(hist.getId().getCalMon().trim())) {
				print.setB14_juloffercd(hist.getOffrOfCovrg()==null?"":hist.getOffrOfCovrg().trim());
				print.setB15_julyamt(hist.getEmpShr()==null?new Double(0):hist.getEmpShr().doubleValue());
				print.setB16_julsafeharborcd(hist.getSafeHrbr()==null?"":hist.getSafeHrbr().trim());
			} else if("08".equals(hist.getId().getCalMon().trim())) {
				print.setB14_augoffercd(hist.getOffrOfCovrg()==null?"":hist.getOffrOfCovrg().trim());
				print.setB15_augustamt(hist.getEmpShr()==null?new Double(0):hist.getEmpShr().doubleValue());
				print.setB16_augsafeharborcd(hist.getSafeHrbr()==null?"":hist.getSafeHrbr().trim());
			} else if("09".equals(hist.getId().getCalMon().trim())) {
				print.setB14_sepoffercd(hist.getOffrOfCovrg()==null?"":hist.getOffrOfCovrg().trim());
				print.setB15_septemberamt(hist.getEmpShr()==null?new Double(0):new Double(hist.getEmpShr().doubleValue()));
				print.setB16_sepsafeharborcd(hist.getSafeHrbr()==null?"":hist.getSafeHrbr().trim());
			} else if("10".equals(hist.getId().getCalMon().trim())) {
				print.setB14_octoffercd(hist.getOffrOfCovrg()==null?"":hist.getOffrOfCovrg().trim());
				print.setB15_octoberamt(hist.getEmpShr()==null?new Double(0):new Double(hist.getEmpShr().doubleValue()));
				print.setB16_octsafeharborcd(hist.getSafeHrbr()==null?"":hist.getSafeHrbr().trim());
			} else if("11".equals(hist.getId().getCalMon().trim())) {
				print.setB14_novoffercd(hist.getOffrOfCovrg()==null?"":hist.getOffrOfCovrg().trim());
				print.setB15_novemberamt(hist.getEmpShr()==null?new Double(0):new Double(hist.getEmpShr().doubleValue()));
				print.setB16_novsafeharborcd(hist.getSafeHrbr()==null?"":hist.getSafeHrbr().trim());
			} else if("12".equals(hist.getId().getCalMon().trim())) {
				print.setB14_decoffercd(hist.getOffrOfCovrg()==null?"":hist.getOffrOfCovrg().trim());
				print.setB15_decemberamt(hist.getEmpShr()==null?new Double(0):new Double(hist.getEmpShr().doubleValue()));
				print.setB16_decsafeharborcd(hist.getSafeHrbr()==null?"":hist.getSafeHrbr().trim());
			}
		}
		print.setPiii_coveredindividualind(cCovrgTypList.get(0).getSelfIns() == 'Y'?"Y":"N");
		print.setPlanStrtMon(cCovrgTypList.get(0).getPlanStrtMon()==null?"":cCovrgTypList.get(0).getPlanStrtMon().trim());
	}

	private void fillPartIIIBoxesC(List<CCoveredHistory> cList, Aca1095CPrint print, Boolean printPage4) {

		// start at box 17 and move to box 34
		int box = 17;
		List<Code> gens = referenceService.getGenerations();
		//If printing page 4, cut down the list for the report
		if(printPage4 && cList.size() >= 19) {
			int size = cList.size();
			cList = cList.subList(18, size);
			box = 23;
		}

		//Go through part IV boxes on the form and instantiate them blank or with data
		for(CCoveredHistory ea1095 : cList) {

			print.setCovrgFirstName(box, ea1095.getNameF()==null?"":ea1095.getNameF());
			print.setCovrgMiddleName(box, ea1095.getNameM()==null?" ":ea1095.getNameM().trim()+" ");
			print.setCovrgLastName(box, ea1095.getNameL()==null?"":ea1095.getNameL());
			print.setCovrgSuffix(box, "");
		    for(Code gen: gens) {
		    	if(ea1095.getNameGen() != null && gen.getCode().equals(ea1095.getNameGen().toString())) {
		    		print.setCovrgSuffix(box, gen.getDescription()==null?"":gen.getDescription());
		    	}
		    }
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
			print.setCovrgAnnualInd(box, ea1095.getMonAll() == 'Y'? true:false);
			print.setCovrgJanuaryInd(box, ea1095.getMon01() == 'Y'? true:false);
			print.setCovrgFebruaryInd(box, ea1095.getMon02() == 'Y'? true:false);
			print.setCovrgMarchInd(box, ea1095.getMon03() == 'Y'? true:false);
			print.setCovrgAprilInd(box, ea1095.getMon04() == 'Y'? true:false);
			print.setCovrgMayInd(box, ea1095.getMon05() == 'Y'? true:false);
			print.setCovrgJuneInd(box, ea1095.getMon06() == 'Y'? true:false);
			print.setCovrgJulyInd(box, ea1095.getMon07() == 'Y'? true:false);
			print.setCovrgAugustInd(box, ea1095.getMon08() == 'Y'? true:false);
			print.setCovrgSeptemberInd(box, ea1095.getMon09() == 'Y'? true:false);
			print.setCovrgOctoberInd(box, ea1095.getMon10() == 'Y'? true:false);
			print.setCovrgNovemberInd(box, ea1095.getMon11() == 'Y'? true:false);
			print.setCovrgDecemberInd(box, ea1095.getMon12() == 'Y'? true:false);	
			// increment box
			box++;
		}
	}
}
