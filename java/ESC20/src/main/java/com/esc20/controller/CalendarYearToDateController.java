package com.esc20.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.esc20.model.BeaUsers;
import com.esc20.model.BhrCalYtd;
import com.esc20.model.BhrEmpDemo;
import com.esc20.nonDBModels.Frequency;
import com.esc20.service.InquiryService;
import com.esc20.util.DateUtil;

@Controller
@RequestMapping("/calendarYearToDate")
public class CalendarYearToDateController {

	@Autowired
	private InquiryService service;
	
	private final String module = "Calendar Year to Date";
	
	@RequestMapping("calendarYearToDate")
	public ModelAndView getCalendarYearToDate(HttpServletRequest req) {
		HttpSession session = req.getSession();
		ModelAndView mav = new ModelAndView();
		BhrEmpDemo userDetail = (BhrEmpDemo) session.getAttribute("userDetail");
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
		BhrEmpDemo userDetail = (BhrEmpDemo) session.getAttribute("userDetail");
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
}
