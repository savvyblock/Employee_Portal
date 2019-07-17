package com.esc20.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.esc20.model.BhrEmpDemo;
import com.esc20.nonDBModels.Deduction;
import com.esc20.nonDBModels.Frequency;
import com.esc20.nonDBModels.Options;
import com.esc20.nonDBModels.PayInfo;
import com.esc20.service.IndexService;
import com.esc20.service.InquiryService;

@Controller
@RequestMapping("/deductions")
public class DeductionsController{
	@Autowired
	private IndexService indexService;

	@Autowired
	private InquiryService service;
	
	@RequestMapping("deductions")
	public ModelAndView getDeductions(HttpServletRequest req) {
		HttpSession session = req.getSession();
		ModelAndView mav = new ModelAndView();
		BhrEmpDemo userDetail = (BhrEmpDemo) session.getAttribute("userDetail");
		String employeeNumber = userDetail.getEmpNbr();
		List<Frequency> frequencies = this.service.getAvailableFrequencies(employeeNumber);
		Map<Frequency, PayInfo> payInfos = this.service.retrievePayInfo(employeeNumber, frequencies);
		Map<Frequency, List<Deduction>> deductions = this.service.retrieveAllDeductions(employeeNumber, frequencies);
		Options options = this.indexService.getOptions();
		session.setAttribute("options", options);
		mav.setViewName("/inquiry/deductions");
		mav.addObject("frequencies", frequencies);
		mav.addObject("payInfos", payInfos);
		mav.addObject("deductions", deductions);
		return mav;
	}
}
