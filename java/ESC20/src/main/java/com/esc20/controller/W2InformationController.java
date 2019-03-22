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
import com.esc20.model.BhrEmpDemo;
import com.esc20.model.BhrThirdPartySickPay;
import com.esc20.model.BhrW2;
import com.esc20.nonDBModels.District;
import com.esc20.nonDBModels.Options;
import com.esc20.nonDBModels.W2Print;
import com.esc20.service.InquiryService;
import com.esc20.util.DateUtil;
import com.esc20.util.NumberUtil;

@Controller
@RequestMapping("/w2Information")
public class W2InformationController{

	@Autowired
	private InquiryService service;
	
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

		String elecConsntMsgW2 = ((Options) session.getAttribute("options")).getMessageElecConsentW2();
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
		mav.addObject("isUpdate", true);
		mav.addObject("isSuccess", isSuccess);
		return mav;
	}
}
