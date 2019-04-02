package com.esc20.controller;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
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
import com.esc20.nonDBModels.Account;
import com.esc20.nonDBModels.CurrentPayInformation;
import com.esc20.nonDBModels.District;
import com.esc20.nonDBModels.EmployeeInfo;
import com.esc20.nonDBModels.Frequency;
import com.esc20.nonDBModels.Options;
import com.esc20.nonDBModels.PayInfo;
import com.esc20.nonDBModels.Stipend;
import com.esc20.service.IndexService;
import com.esc20.service.InquiryService;
import com.esc20.util.DataSourceContextHolder;
import com.esc20.util.PDFUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/currentPayInformation")
public class CurrentPayInformationController{
	
	@Autowired
	private InquiryService service;
	
    @Autowired
    private IndexService indexService;
	
	@RequestMapping("currentPayInformation")
	public ModelAndView getCurrentPayInformation(HttpServletRequest req) throws IOException {
		HttpSession session = req.getSession();
		ModelAndView mav = new ModelAndView();
		BhrEmpDemo userDetail = (BhrEmpDemo) session.getAttribute("userDetail");
		String employeeNumber = userDetail.getEmpNbr();
		
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
		byte[] pdf = PDFUtil.convertHtmlToPdf("http://localhost:8080/txeisDemo/currentPayInformation/currentPayInformationUnprotectedPDF", request);
		response.reset();
		response.setHeader("Content-Disposition", "attachment; filename=\"download.pdf\"");
		response.setContentType("application/octet-stream;charset=UTF-8");
		OutputStream out = response.getOutputStream();
		out.write(pdf);
		out.flush();
	}
	
	@RequestMapping("currentPayInformationUnprotectedPDF")
	public ModelAndView getCurrentPayInformation(HttpServletRequest req, String empNbr, String districtId,String language) throws IOException {
		DataSourceContextHolder.setDataSourceType("java:jboss/DB"+districtId);
		HttpSession session = req.getSession();
		ModelAndView mav = new ModelAndView();
		String employeeNumber = empNbr;
		BhrEmpDemo userDetail = this.indexService.getUserDetail(empNbr);
		session.setAttribute("userDetail", userDetail);
		District districtInfo = this.indexService.getDistrict(districtId);
		session.setAttribute("district", districtInfo);
		Map<Frequency, List<CurrentPayInformation>> jobs = this.service.getJob(employeeNumber);
		Map<Frequency, List<Stipend>> stipends = this.service.getStipends(employeeNumber);
		Map<Frequency, List<Account>> accounts = this.service.getAccounts(employeeNumber);
		List<Frequency> frequencies = this.service.getFrequencies(jobs);
		Map<Frequency, PayInfo> payInfos = this.service.retrievePayInfo(employeeNumber, frequencies);
		Map<Frequency, String> payCampuses = this.service.retrievePayCampuses(employeeNumber);
		EmployeeInfo employeeInfo = this.service.getEmployeeInfo(employeeNumber);
		mav.setViewName("/inquiry/currentPayInformation");
		String path = req.getSession().getServletContext().getRealPath("/") +"/static/js/lang/text-"+language+".json";
		File file = new File(path);
		String input = FileUtils.readFileToString(file, "UTF-8");
		JSONObject jsonObject = JSONObject.fromObject(input);
		req.getSession().setAttribute("languageJSON", jsonObject);
		mav.addObject("jobs", jobs);
		mav.addObject("stipends", stipends);
		mav.addObject("isPrintPDF", true);
		mav.addObject("accounts", accounts);
		mav.addObject("frequencies", frequencies);
		mav.addObject("payInfos", payInfos);
		mav.addObject("payCampuses", payCampuses);
		mav.addObject("employeeInfo", employeeInfo);
		return mav;
	}
	
}
