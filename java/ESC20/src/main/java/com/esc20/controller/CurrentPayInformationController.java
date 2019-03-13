package com.esc20.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.esc20.model.BeaUsers;
import com.esc20.model.BhrEmpDemo;
import com.esc20.nonDBModels.Account;
import com.esc20.nonDBModels.CurrentPayInformation;
import com.esc20.nonDBModels.EmployeeInfo;
import com.esc20.nonDBModels.Frequency;
import com.esc20.nonDBModels.Options;
import com.esc20.nonDBModels.PayInfo;
import com.esc20.nonDBModels.Stipend;
import com.esc20.service.InquiryService;

@Controller
@RequestMapping("/currentPayInformation")
public class CurrentPayInformationController{
	
	@Autowired
	private InquiryService service;
	
	@RequestMapping("currentPayInformation")
	public ModelAndView getCurrentPayInformation(HttpServletRequest req) {
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
		return mav;
	}
}
