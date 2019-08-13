package com.esc20.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.esc20.model.BeaUsers;
import com.esc20.model.BhrEmpDemo;
import com.esc20.nonDBModels.Account;
import com.esc20.nonDBModels.Code;
import com.esc20.nonDBModels.CurrentPayInformation;
import com.esc20.nonDBModels.District;
import com.esc20.nonDBModels.EmployeeInfo;
import com.esc20.nonDBModels.Frequency;
import com.esc20.nonDBModels.Options;
import com.esc20.nonDBModels.PayInfo;
import com.esc20.nonDBModels.PayPrint;
import com.esc20.nonDBModels.Stipend;
import com.esc20.nonDBModels.report.IReport;
import com.esc20.nonDBModels.report.ParameterReport;
import com.esc20.nonDBModels.report.ReportParameterConnection;
import com.esc20.service.IndexService;
import com.esc20.service.InquiryService;
import com.esc20.service.PDFService;
import com.esc20.service.ReferenceService;
import com.esc20.util.DateUtil;
import com.esc20.util.StringUtil;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Controller
@RequestMapping("/currentPayInformation")
public class CurrentPayInformationController{
	@Autowired
	private IndexService indexService;
	
	@Autowired
	private InquiryService service;
	
    @Autowired
    private ReferenceService referenceService;
    
    @Autowired
    private PDFService pDFService;
	
	@RequestMapping("currentPayInformation")
	public ModelAndView getCurrentPayInformation(HttpServletRequest req) throws IOException {
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

		 session.setAttribute("options", options);
		 session.setAttribute("userDetail", userDetail);
         session.setAttribute("companyId", district);
         session.setAttribute("options", options);
         session.setAttribute("district", districtInfo);
		
		ModelAndView mav = new ModelAndView();
		//BhrEmpDemo userDetail = (BhrEmpDemo) session.getAttribute("userDetail");
		String employeeNumber = userDetail.getEmpNbr();
		List<Code> gens = referenceService.getGenerations();
		 for(Code gen: gens) {
		    	if(userDetail.getNameGen() != null && gen.getCode().trim().equals(userDetail.getNameGen().toString().trim())) {
		    		userDetail.setGenDescription(gen.getDescription());
		    	}
		    }
		
		Map<Frequency, List<CurrentPayInformation>> jobs = this.service.getJob(employeeNumber);
		Map<Frequency, List<Stipend>> stipends = this.service.getStipends(employeeNumber);
		Map<Frequency, List<Account>> accounts = this.service.getAccounts(employeeNumber);
		List<Frequency> frequencies = this.service.getFrequencies(jobs);
		Map<Frequency, PayInfo> payInfos = this.service.retrievePayInfo(employeeNumber, frequencies);
		Map<Frequency, String> payCampuses = this.service.retrievePayCampuses(employeeNumber);
		EmployeeInfo employeeInfo = this.service.getEmployeeInfo(employeeNumber);
		
		String message = ((Options) session.getAttribute("options")).getMessageCurrentPayInformation();
		mav.setViewName("/inquiry/currentPayInformation");
		mav.addObject("jobs", jobs);
		mav.addObject("stipends", stipends);
		mav.addObject("accounts", accounts);
		mav.addObject("frequencies", frequencies);
		mav.addObject("payInfos", payInfos);
		mav.addObject("message", message);
		mav.addObject("payCampuses", payCampuses);
		mav.addObject("employeeInfo", employeeInfo);
		mav.addObject("isPrintPDF", true);
		return mav;
	}
	
	@RequestMapping("exportPDF")
	public void exportPDF(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("application/x-msdownload;charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment;filename=Current Pay Information.pdf");
		
		String path = request.getServletContext().getRealPath("/");
		if (path != null && !path.endsWith("\\")) {
			path = path.concat("\\");
		}
		pDFService.setRealPath(path);
		
		ParameterReport report = new ParameterReport();
		report.setTitle("Pay Report");
		report.setId("payReport");
		report.setFileName("DRptPay");
		report.setSortable(false);
		report.setFilterable(false);
		
		PayPrint payPrint = generatePayPrint(request, response);
		IReport ireport = setupReport(report, payPrint);
		
	    JasperPrint jasperPrint = pDFService.buildReport(ireport);
    	JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
	}

	@RequestMapping("printPDF")
	@ResponseBody
	public void printPDF(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("application/pdf;charset=UTF-8");
		response.setHeader("Content-Disposition", "application/pdf;filename=Current Pay Information.pdf");
		
		String path = request.getServletContext().getRealPath("/");
		if (path != null && !path.endsWith("\\")) {
			path = path.concat("\\");
		}
		pDFService.setRealPath(path);
		
		ParameterReport report = new ParameterReport();
		report.setTitle("Pay Report");
		report.setId("payReport");
		report.setFileName("DRptPay");
		report.setSortable(false);
		report.setFilterable(false);
		
		PayPrint payPrint = generatePayPrint(request, response);
		IReport ireport = setupReport(report, payPrint);
		
	    JasperPrint jasperPrint = pDFService.buildReport(ireport);
    	JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
	}
	
	public PayPrint generatePayPrint(HttpServletRequest request, HttpServletResponse response)
	{
		PayPrint print = new PayPrint();
		District district = (District) request.getSession().getAttribute("district");
		BhrEmpDemo userDetail = (BhrEmpDemo) request.getSession().getAttribute("userDetail");
		print.setDname(district.getName());
		print.setDaddress(district.getAddress());
		print.setDcityst(district.getCity() + ", " + district.getState() + " " + district.getZip());
		
		if(district.getZip4()!=null && district.getZip4().length() > 0)
		{
			print.setDcityst(print.getDcityst() + "-" + district.getZip4());
		}
		
		String middleName = userDetail.getNameM();
		if (middleName!=null && (middleName.trim()).length() > 0) {
			middleName = middleName.trim() + " ";
		} else {
			middleName = "";
		}
		
		List<Code> gens = referenceService.getGenerations();
		 for(Code gen: gens) {
		    	if(userDetail.getNameGen() != null && gen.getCode().trim().equals(userDetail.getNameGen().toString().trim())) {
		    		userDetail.setGenDescription(gen.getDescription());
		    	}
		    }
		
		print.setEname(userDetail.getNameF() + " " + middleName + userDetail.getNameL() + " " + (userDetail.getGenDescription()==null?"":userDetail.getGenDescription()));
		print.setEaddress(StringUtil.trim(userDetail.getAddrNbr())+ " "+ StringUtil.trim(userDetail.getAddrStr()));
		String apt = StringUtil.trim(userDetail.getAddrApt());
		if(apt.length() > 0)
		{
			print.setEaddress(print.getEaddress() + " " + apt);
		}
		print.setEcityst(userDetail.getAddrCity() + ", " + userDetail.getAddrSt() + " " + userDetail.getAddrZip());
		
		if(userDetail.getAddrZip4()!=null && userDetail.getAddrZip4().trim().length() > 0)
		{
			print.setEcityst(print.getEcityst() + "-" + userDetail.getAddrZip4().trim());
		}
		if(userDetail.getPhoneArea()!=null && userDetail.getPhoneNbr() != null && userDetail.getPhoneNbr().trim().length()>0) {
			print.setPhoneNumber(userDetail.getPhoneArea().trim()+"-"+userDetail.getPhoneNbr().trim().substring(0,3)+"-"+userDetail.getPhoneNbr().trim().substring(3));
		}
		else {
			print.setPhoneNumber("");
		}
		
		print.setEmployeeNumber(userDetail.getEmpNbr());
		if(userDetail.getDob()!=null && userDetail.getDob().length()>=8)
			print.setDateOfBirth(userDetail.getDob());
		else
			print.setDateOfBirth("");
		String gender;
		if (userDetail.getSex() == 'M') {
			gender = "Male";
		} else if (userDetail.getSex() == 'F') {
			gender = "Female";
		} else {
			gender = "";
		}
		print.setGender(gender);
		
		EmployeeInfo employeeInfo = this.service.getEmployeeInfo(userDetail.getEmpNbr());
		
		if (employeeInfo.getHighDegree() != null) {
			if (employeeInfo.getHighDegreeDescription()!= null) {
				print.setDegree(employeeInfo.getHighDegreeDescription());
			}
		}
		if (employeeInfo.getYrsProExper() != null) {
			print.setProExperience(employeeInfo.getYrsProExper());
		}
		if (employeeInfo.getYrsExpDist() != null) {
			print.setNonProExperience(employeeInfo.getYrsExpDist());
		}
		if (employeeInfo.getYrsProExperLoc() != null) {
			print.setProExperienceDistrict(employeeInfo.getYrsProExperLoc());
		}
		if (employeeInfo.getYrsExpDistLoc() != null) {
			print.setNonProExperienceDistrict(employeeInfo.getYrsExpDistLoc());
		}
		
		return print;
	}
	
	private IReport setupReport(ParameterReport report, PayPrint data) throws Exception 
	{
		report.getParameters().clear();
		ReportParameterConnection parameter = new ReportParameterConnection();
		parameter.setName("subRptConnection");
		parameter.setConnection(pDFService.getConn());
		report.getParameters().add(parameter);
		
		report.setFileName("DRptPay");
		
		List<PayPrint> forms = new ArrayList<PayPrint>();
		forms.add(data);
		report.setDataSource(new JRBeanCollectionDataSource(forms));
		
		return report;
	}
}
