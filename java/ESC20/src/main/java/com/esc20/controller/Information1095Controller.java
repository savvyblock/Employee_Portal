package com.esc20.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.esc20.model.BeaUsers;
import com.esc20.model.BhrAca1095bCovrdHist;
import com.esc20.model.BhrAca1095cCovrdHist;
import com.esc20.model.BhrCalYtd;
import com.esc20.model.BhrEmpDemo;
import com.esc20.model.BhrThirdPartySickPay;
import com.esc20.model.BhrW2;
import com.esc20.nonDBModels.Account;
import com.esc20.nonDBModels.CurrentPayInformation;
import com.esc20.nonDBModels.Deduction;
import com.esc20.nonDBModels.District;
import com.esc20.nonDBModels.Earnings;
import com.esc20.nonDBModels.EmployeeInfo;
import com.esc20.nonDBModels.Frequency;
import com.esc20.nonDBModels.Options;
import com.esc20.nonDBModels.PayDate;
import com.esc20.nonDBModels.PayInfo;
import com.esc20.nonDBModels.Stipend;
import com.esc20.nonDBModels.W2Print;
import com.esc20.service.InquiryService;
import com.esc20.util.DateUtil;
import com.esc20.util.NumberUtil;
import com.esc20.util.StringUtil;

@Controller
@RequestMapping("/information1095")
public class Information1095Controller{

	@Autowired
	private InquiryService service;

	@RequestMapping("information1095")
	public ModelAndView getInformation1095(HttpServletRequest req) {
		HttpSession session = req.getSession();
		ModelAndView mav = new ModelAndView();
		mav = init1095(mav, session, null, 1, 1, null, null, null);
		return mav;
	}

	@RequestMapping("update1095Consent")
	public ModelAndView update1095Consent(HttpServletRequest req, String year, String consent) {
		HttpSession session = req.getSession();
		ModelAndView mav = new ModelAndView();
		BhrEmpDemo userDetail = (BhrEmpDemo) session.getAttribute("userDetail");
		String employeeNumber = userDetail.getEmpNbr();
		Boolean isSuccess = this.service.update1095ElecConsent(employeeNumber, consent);
		mav.setViewName("/inquiry/information1095");
		mav = init1095(mav, session, year, 1, 1, null, null, null);
		mav.addObject("isUpdate", true);
		mav.addObject("isSuccess", isSuccess);
		return mav;
	}
	
	@RequestMapping("information1095ByYear")
	public ModelAndView getInformation1095ByYear(HttpServletRequest req, String year) {
		HttpSession session = req.getSession();
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/inquiry/information1095");
		mav = init1095(mav, session, year, 1, 1, null, null, null);
		return mav;
	}

	@RequestMapping("sortOrChangePageForTypeB")
	public ModelAndView sortOrChangePageForTypeB(HttpServletRequest req, String year, String BPageNo, String sortBy,
			String sortOrder) {
		HttpSession session = req.getSession();
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/inquiry/information1095");
		mav = init1095(mav, session, year, Integer.parseInt(BPageNo), 1, sortBy, sortOrder, "B");
		return mav;
	}

	@RequestMapping("sortOrChangePageForTypeC")
	public ModelAndView sortOrChangePageForTypeC(HttpServletRequest req, String year, String CPageNo, String sortBy,
			String sortOrder) {
		HttpSession session = req.getSession();
		ModelAndView mav = new ModelAndView();
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
		String message = options.getMessageElecConsent1095();
		String consent = this.service.get1095Consent(employeeNumber);
		Integer BTotal = this.service.getBInfoTotal(employeeNumber, year);
		Integer CTotal = this.service.getCInfoTotal(employeeNumber, year);
		// List<String> bCovrgTypList =
		// this.service.retrieveEA1095BEmpInfo(employeeNumber,year);
		List<BhrAca1095bCovrdHist> bList;
		if (("B").equals(type))
			bList = this.service.retrieveEA1095BInfo(employeeNumber, year, sortBy, sortOrder, BPageNo);
		else
			bList = this.service.retrieveEA1095BInfo(employeeNumber, year, null, null, 1);
		// List<BhrAca1095cEmpHist> cEmpList =
		// this.service.retrieveEA1095CEmpInfo(employeeNumber,year);
		List<BhrAca1095cCovrdHist> cList;
		if (("C").equals(type))
			cList = this.service.retrieveEA1095CInfo(employeeNumber, year, sortBy, sortOrder, CPageNo);
		else
			cList = this.service.retrieveEA1095CInfo(employeeNumber, year, null, null, 1);
//		if (bCovrgTypList.size() > 0) {
//			mav.addObject("BCovrgTyp", bCovrgTypList.get(0));
//			mav.addObject("BCovrgTypDescr", bCovrgTypList.get(1));
//		}
		if (type == null) {
			if (cList != null && cList.size() > 0)
				mav.addObject("type", "C");
			else
				mav.addObject("type", "B");
		} else {
			mav.addObject("type", type);
		}
		mav.addObject("years", years);
		mav.addObject("selectedYear", year);
		mav.addObject("consent", consent);
		mav.addObject("message", message);
		mav.addObject("bList", bList);
		mav.addObject("cList", cList);
		mav.addObject("BPageNo", BPageNo);
		mav.addObject("CPageNo", CPageNo);
		mav.addObject("BTotal", BTotal);
		mav.addObject("CTotal", CTotal);
		return mav;
	}
}
