package com.esc20.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.esc20.model.BeaUsers;
import com.esc20.model.BhrCalYtd;
import com.esc20.model.BhrEmpDemo;
import com.esc20.nonDBModels.CalYTDPrint;
import com.esc20.nonDBModels.Code;
import com.esc20.nonDBModels.District;
import com.esc20.nonDBModels.Frequency;
import com.esc20.nonDBModels.Options;
import com.esc20.nonDBModels.report.IReport;
import com.esc20.nonDBModels.report.ParameterReport;
import com.esc20.service.IndexService;
import com.esc20.service.InquiryService;
import com.esc20.service.PDFService;
import com.esc20.service.ReferenceService;
import com.esc20.util.DateUtil;
import com.esc20.util.StringUtil;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;

@Controller
@RequestMapping("/calendarYearToDate")
public class CalendarYearToDateController {
	@Autowired
	private IndexService indexService;
	
	@Autowired
	private InquiryService service;
	
	@Autowired
	private ReferenceService referenceService;
    
    @Autowired
    private PDFService pDFService;
	
	private final String module = "Calendar Year to Date";
	
	@RequestMapping("calendarYearToDate")
	public ModelAndView getCalendarYearToDate(HttpServletRequest req) {
		HttpSession session = req.getSession();
		BeaUsers user = (BeaUsers) session.getAttribute("user");
		BhrEmpDemo userDetail = this.indexService.getUserDetail(user.getEmpNbr());
        Options options = this.indexService.getOptions();
       /* String district = (String)session.getAttribute("districtId");
        District districtInfo = this.indexService.getDistrict(district);*/
        userDetail.setEmpNbr(user.getEmpNbr());
        userDetail.setDob(DateUtil.formatDate(userDetail.getDob(), "yyyyMMdd", "MM-dd-yyyy"));
        List<Code> gens = referenceService.getGenerations();
	 	for(Code gen: gens) {
	    	if(userDetail.getNameGen() != null && gen.getCode().trim().equals(userDetail.getNameGen().toString().trim())) {
	    		userDetail.setGenDescription(gen.getDescription());
	    	}
	    }
		
       /* String phone = districtInfo.getPhone();
        districtInfo.setPhone(StringUtil.left(phone, 3)+"-"+StringUtil.mid(phone, 4, 3)+"-"+StringUtil.right(phone, 4));*/

		session.setAttribute("options", options);
		session.setAttribute("userDetail", userDetail);
      //  session.setAttribute("companyId", district);
        session.setAttribute("options", options);
       // session.setAttribute("district", districtInfo);
		
		ModelAndView mav = new ModelAndView();
		//BhrEmpDemo userDetail = (BhrEmpDemo) session.getAttribute("userDetail");
		String employeeNumber = userDetail.getEmpNbr();
		List<String> years = service.getAvailableYears(employeeNumber);
		String latestYear = DateUtil.getLatestYear(years);
		BhrCalYtd calYTD = service.getCalenderYTD(employeeNumber, latestYear);
		BigDecimal trsIns = calYTD.getTrsDeposit().subtract(calYTD.getTrsSalaryRed());
		Frequency freq = Frequency.getFrequency(calYTD.getId().getPayFreq() + "");
		String latestPayDate = service.getLatestPayDate(employeeNumber, freq);
	
		mav.setViewName("/inquiry/calendarYearToDate");
		mav.addObject("years", years);
		mav.addObject("selectedYear", latestYear);
		mav.addObject("calendar", calYTD);
		mav.addObject("latestPayDate", latestPayDate);
		mav.addObject("trsIns", trsIns);
		mav.addObject("freq", freq);
		return mav;
	}

	@RequestMapping("getCalendarYearToDateByYear")
	public ModelAndView getCalendarYearToDateByYear(HttpServletRequest req, String year) {
		HttpSession session = req.getSession();
		ModelAndView mav = new ModelAndView();
		if(year==null) {
			mav.setViewName("visitFailed");
			mav.addObject("module", module);
			mav.addObject("action", "Get calendar year to date information by year");
			mav.addObject("errorMsg", "Year not provided.");
			return mav;
		}
		BeaUsers user = (BeaUsers) session.getAttribute("user");
		BhrEmpDemo userDetail = this.indexService.getUserDetail(user.getEmpNbr());
        userDetail.setEmpNbr(user.getEmpNbr());
        userDetail.setDob(DateUtil.formatDate(userDetail.getDob(), "yyyyMMdd", "MM-dd-yyyy"));
        List<Code> gens = referenceService.getGenerations();
		 	for(Code gen: gens) {
		    	if(userDetail.getNameGen() != null && gen.getCode().trim().equals(userDetail.getNameGen().toString().trim())) {
		    		userDetail.setGenDescription(gen.getDescription());
		    	}
		    }
		
   	 	session.setAttribute("userDetail", userDetail);

		//BhrEmpDemo userDetail = (BhrEmpDemo) session.getAttribute("userDetail");
		String employeeNumber = userDetail.getEmpNbr();
		BhrCalYtd calYTD = service.getCalenderYTD(employeeNumber, year);
		BigDecimal trsIns = calYTD.getTrsDeposit().subtract(calYTD.getTrsSalaryRed());
		List<String> years = service.getAvailableYears(employeeNumber);
		Frequency freq = Frequency.getFrequency(calYTD.getId().getPayFreq() + "");
		String latestPayDate = service.getLatestPayDate(employeeNumber, freq);
		mav.setViewName("/inquiry/calendarYearToDate");
		mav.addObject("years", years);
		mav.addObject("selectedYear", year);
		mav.addObject("calendar", calYTD);
		mav.addObject("latestPayDate", latestPayDate);
		mav.addObject("trsIns", trsIns);
		mav.addObject("freq", freq);
		return mav;
	}
	
	@RequestMapping("exportPDF")
	public void exportPDF(HttpServletRequest request, HttpServletResponse response, String year) throws Exception {
		
		response.setContentType("application/x-msdownload;charset=UTF-8");
		response.setHeader("Content-Disposition",
				"attachment;filename=Calendar Year to Date Report for "+ year+".pdf");
		
		String path = request.getServletContext().getRealPath("/");
		if (path != null && !path.endsWith("\\")) {
			path = path.concat("\\");
		}
		pDFService.setRealPath(path);
		
		BeaUsers user = (BeaUsers) request.getSession().getAttribute("user");
		BhrEmpDemo userDetail = this.indexService.getUserDetail(user.getEmpNbr());
        String districtId = (String)request.getSession().getAttribute("districtId");
        District district = this.indexService.getDistrict(districtId);
        userDetail.setEmpNbr(user.getEmpNbr());
        userDetail.setDob(DateUtil.formatDate(userDetail.getDob(), "yyyyMMdd", "MM-dd-yyyy"));
        String phone = district.getPhone();
        district.setPhone(StringUtil.left(phone, 3)+"-"+StringUtil.mid(phone, 4, 3)+"-"+StringUtil.right(phone, 4));

		
		//BhrEmpDemo userDetail = (BhrEmpDemo) request.getSession().getAttribute("userDetail");
		//District district = (District) request.getSession().getAttribute("district");
		
		BhrCalYtd b = service.getCalenderYTD(userDetail.getEmpNbr(), year);
		
		List<CalYTDPrint> parameters = this.generateCalYTDPrint(userDetail, district, b);
		
		ParameterReport report = new ParameterReport();
		
		report.setTitle("Calendar Year to Date Report for "+ year);
		report.setId("calYTDReport");
		report.setFileName("Calendar Year to Date Report for "+ year);
		report.setSortable(false);
		report.setFilterable(false);
		
		IReport ireport = pDFService.setupReport(report, parameters);
	    JasperPrint jasperPrint = null;
		jasperPrint = pDFService.buildReport(ireport);
    	JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
	}

	@RequestMapping("printPDF")
	@ResponseBody
	public void printPDF(HttpServletRequest request, HttpServletResponse response, String year) throws Exception {
		response.setContentType("application/pdf;charset=UTF-8");
		response.setHeader("Content-Disposition",
				"application/pdf;filename=Calendar Year to Date Report for "+ year+".pdf");
		String path = request.getServletContext().getRealPath("/");
		if (path != null && !path.endsWith("\\")) {
			path = path.concat("\\");
		}
		pDFService.setRealPath(path);
		
		BeaUsers user = (BeaUsers) request.getSession().getAttribute("user");
		BhrEmpDemo userDetail = this.indexService.getUserDetail(user.getEmpNbr());
        String districtId = (String)request.getSession().getAttribute("districtId");
        District district = this.indexService.getDistrict(districtId);
        userDetail.setEmpNbr(user.getEmpNbr());
        userDetail.setDob(DateUtil.formatDate(userDetail.getDob(), "yyyyMMdd", "MM-dd-yyyy"));
        String phone = district.getPhone();
        district.setPhone(StringUtil.left(phone, 3)+"-"+StringUtil.mid(phone, 4, 3)+"-"+StringUtil.right(phone, 4));

        
		//BhrEmpDemo userDetail = (BhrEmpDemo) request.getSession().getAttribute("userDetail");
		//District district = (District) request.getSession().getAttribute("district");
		
		BhrCalYtd b = service.getCalenderYTD(userDetail.getEmpNbr(), year);
		
		List<CalYTDPrint> parameters = this.generateCalYTDPrint(userDetail, district, b);
		
		ParameterReport report = new ParameterReport();
		
		report.setTitle("Calendar Year to Date Report for "+ year);
		report.setId("calYTDReport");
		report.setFileName("Calendar Year to Date Report for "+ year);
		report.setSortable(false);
		report.setFilterable(false);
		
		IReport ireport = pDFService.setupReport(report, parameters);
	    JasperPrint jasperPrint = null;
		jasperPrint = pDFService.buildReport(ireport);
		JasperExportManager.exportReportToPdfStream(jasperPrint,response.getOutputStream());
	}
	
	//jf20140110 Print Report on Calendar YTD screen
	public List<CalYTDPrint> generateCalYTDPrint(BhrEmpDemo user, District district , BhrCalYtd calendar)
	{
		String dCitySt = "";
		
		dCitySt = district.getCity().trim() + ", " + district.getState().trim() + " " + district.getZip().trim();
		
		if(district.getZip4().trim().length() > 0)
		{
			dCitySt = dCitySt + "-" + district.getZip4().trim();
		}
		
		String eName = "";
		String middleName = user.getNameM().trim();
		if (middleName.length() > 0) {
			middleName = middleName + " ";
		} else {
			middleName = "";
		}
		
		eName = user.getNameF() + " " + middleName + user.getNameL() + " " + (user.getGenDescription()==null?"":user.getGenDescription());   //jf20150113 Display description instead of code fix
		
		List<CalYTDPrint> calYTDRpt = new ArrayList<CalYTDPrint>();
		CalYTDPrint print = null;
		
			print = new CalYTDPrint();
			print.setDname(district.getName().trim());
			print.setDaddress(district.getAddress().trim());
			print.setDcityst(dCitySt.trim());
			
			print.setEname(eName);
			print.setEmployeeNumber(user.getEmpNbr());
			
			print.setCalYr(calendar.getId().getCalYr());
			Frequency freq = Frequency.getFrequency(calendar.getId().getPayFreq() + "");
			print.setFrequency(freq.getLabel());

			String latestPayDate = service.getLatestPayDate(user.getEmpNbr(), freq);
			print.setLastPostedPayDate(latestPayDate);
			
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
			print.setHsaEmployerContribution(calendar.getHsaEmplrContrib());
			print.setHsaEmployeeSalaryReductionContribution(calendar.getHsaEmpSalRedctnContrib());
			print.setHireExemptWgs(calendar.getHireExemptWgs());
			
			print.setTaxedLifeContribution(calendar.getTaxEmplrLife());
			print.setTaxedGroupContribution(calendar.getTaxEmplrLifeGrp());
			print.setHealthInsuranceDeduction(calendar.getHlthInsDed());
			
			print.setEmplrPrvdHlthcare(calendar.getEmplrPrvdHlthcare());
			print.setAnnuityRoth457b(calendar.getAnnuityRoth457b());
			BigDecimal trsIns = calendar.getTrsDeposit().subtract(calendar.getTrsSalaryRed());
			print.setTrsInsurance(trsIns);
			calYTDRpt.add(print);
		
		return calYTDRpt;
	}
}
