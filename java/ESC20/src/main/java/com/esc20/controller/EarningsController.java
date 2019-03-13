package com.esc20.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.esc20.model.BeaUsers;
import com.esc20.model.BhrEmpDemo;
import com.esc20.nonDBModels.Earnings;
import com.esc20.nonDBModels.Frequency;
import com.esc20.nonDBModels.Options;
import com.esc20.nonDBModels.PayDate;
import com.esc20.service.InquiryService;
import com.esc20.util.StringUtil;

@Controller
@RequestMapping("/earnings")
public class EarningsController{

	@Autowired
	private InquiryService service;
	
	@RequestMapping("earnings")
	public ModelAndView getEarnings(HttpServletRequest req) {
		HttpSession session = req.getSession();
		ModelAndView mav = new ModelAndView();
		BhrEmpDemo userDetail = (BhrEmpDemo) session.getAttribute("userDetail");
		String employeeNumber = userDetail.getEmpNbr();
		Integer days = ((Options) session.getAttribute("options")).getMaxDays();
		if (days == null)
			days = 0;
		List<PayDate> payDates = this.service.getAvailablePayDates(employeeNumber, days);
		mav.addObject("payDates", payDates);
		PayDate latestPayDate = null;
		String message = "";
		Earnings earnings = null;
		Earnings YTDEarnings = null;
		Frequency freq = null;
		String year = null;
		if (!CollectionUtils.isEmpty(payDates)) {
			latestPayDate = this.service.getLatestPayDate(payDates);
			message = ((Options) session.getAttribute("options")).getMessageEarnings();
			earnings = this.service.retrieveEarnings(employeeNumber, latestPayDate);
			YTDEarnings = this.service.getTYDEarnings(employeeNumber, payDates, latestPayDate);
			for (int i = 0; i < earnings.getOther().size(); i++) {
				for (int j = 0; j < YTDEarnings.getOther().size(); j++) {
					if (earnings.getOther().get(i).getCode().equals(YTDEarnings.getOther().get(j).getCode())) {
						earnings.getOther().get(i).setTydAmt(YTDEarnings.getOther().get(j).getTydAmt());
						earnings.getOther().get(i).setTydContrib(YTDEarnings.getOther().get(j).getContrib());
					}
				}
			}
			YTDEarnings.setEmplrPrvdHlthcare(this.service.getEmplrPrvdHlthcare(employeeNumber, latestPayDate));
			freq = Frequency.getFrequency(StringUtil.mid(latestPayDate.getDateFreq(), 9, 1));
			year = latestPayDate.getDateFreq().substring(0, 4);
		}
		mav.setViewName("/inquiry/earnings");
		mav.addObject("days", days);
		mav.addObject("selectedPayDate", latestPayDate);
		mav.addObject("message", message);
		mav.addObject("earnings", earnings);
		mav.addObject("YTDEarnings", YTDEarnings);
		mav.addObject("year", year);
		mav.addObject("freq", freq);
		return mav;
	}

	@RequestMapping("earningsByPayDate")
	public ModelAndView getEarningsByPayDate(HttpServletRequest req, String payDateString) {
		HttpSession session = req.getSession();
		ModelAndView mav = new ModelAndView();
		BhrEmpDemo userDetail = (BhrEmpDemo) session.getAttribute("userDetail");
		String employeeNumber = userDetail.getEmpNbr();
		Integer days = ((Options) session.getAttribute("options")).getMaxDays();
		if (days == null)
			days = 0;
		List<PayDate> payDates = this.service.getAvailablePayDates(employeeNumber, days);
		mav.addObject("payDates", payDates);
		PayDate payDate = PayDate.getPaydate(payDateString);
		String message = ((Options) session.getAttribute("options")).getMessageEarnings();
		Earnings earnings = this.service.retrieveEarnings(employeeNumber, payDate);
		Earnings YTDEarnings = this.service.getTYDEarnings(employeeNumber, payDates, payDate);
		for (int i = 0; i < earnings.getOther().size(); i++) {
			for (int j = 0; j < YTDEarnings.getOther().size(); j++) {
				if (earnings.getOther().get(i).getCode().equals(YTDEarnings.getOther().get(j).getCode())) {
					earnings.getOther().get(i).setTydAmt(YTDEarnings.getOther().get(j).getTydAmt());
					earnings.getOther().get(i).setTydContrib(YTDEarnings.getOther().get(j).getContrib());
				}
			}
		}
		YTDEarnings.setEmplrPrvdHlthcare(this.service.getEmplrPrvdHlthcare(employeeNumber, payDate));
		Frequency freq = Frequency.getFrequency(StringUtil.mid(payDate.getDateFreq(), 9, 1));
		String year = payDate.getDateFreq().substring(0, 4);
		mav.setViewName("/inquiry/earnings");
		mav.addObject("days", days);
		mav.addObject("selectedPayDate", payDate);
		mav.addObject("message", message);
		mav.addObject("earnings", earnings);
		mav.addObject("YTDEarnings", YTDEarnings);
		mav.addObject("year", year);
		mav.addObject("freq", freq);
		return mav;
	}
}
