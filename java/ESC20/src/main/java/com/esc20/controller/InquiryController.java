package com.esc20.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
@RequestMapping("/inquiry")
public class InquiryController {

	@Autowired
	private InquiryService service;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView getIndexPage(ModelAndView mav) {
		mav.setViewName("index");
		return mav;
	}

	@RequestMapping("calendarYearToDate")
	public ModelAndView getCalendarYearToDate(HttpServletRequest req) {
		HttpSession session = req.getSession();
		BeaUsers user = (BeaUsers) session.getAttribute("user");
		ModelAndView mav = new ModelAndView();
		if (null == user) {
			return this.getIndexPage(mav);
		}
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
		BeaUsers user = (BeaUsers) session.getAttribute("user");
		ModelAndView mav = new ModelAndView();
		if (null == user) {
			return this.getIndexPage(mav);
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

	@RequestMapping("currentPayInformation")
	public ModelAndView getCurrentPayInformation(HttpServletRequest req) {
		HttpSession session = req.getSession();
		BeaUsers user = (BeaUsers) session.getAttribute("user");
		ModelAndView mav = new ModelAndView();
		if (null == user) {
			return this.getIndexPage(mav);
		}
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

	@RequestMapping("deductions")
	public ModelAndView getDeductions(HttpServletRequest req) {
		HttpSession session = req.getSession();
		BeaUsers user = (BeaUsers) session.getAttribute("user");
		ModelAndView mav = new ModelAndView();
		if (null == user) {
			return this.getIndexPage(mav);
		}
		BhrEmpDemo userDetail = (BhrEmpDemo) session.getAttribute("userDetail");
		String employeeNumber = userDetail.getEmpNbr();
		List<Frequency> frequencies = this.service.getAvailableFrequencies(employeeNumber);
		Map<Frequency, PayInfo> payInfos = this.service.retrievePayInfo(employeeNumber, frequencies);
		Map<Frequency, List<Deduction>> deductions = this.service.retrieveAllDeductions(employeeNumber, frequencies);
		mav.setViewName("/inquiry/deductions");
		mav.addObject("frequencies", frequencies);
		mav.addObject("payInfos", payInfos);
		mav.addObject("deductions", deductions);
		return mav;
	}

	@RequestMapping("earnings")
	public ModelAndView getEarnings(HttpServletRequest req) {
		HttpSession session = req.getSession();
		BeaUsers user = (BeaUsers) session.getAttribute("user");
		ModelAndView mav = new ModelAndView();
		if (null == user) {
			return this.getIndexPage(mav);
		}
		BhrEmpDemo userDetail = (BhrEmpDemo) session.getAttribute("userDetail");
		String employeeNumber = userDetail.getEmpNbr();
		Integer days = ((Options) session.getAttribute("options")).getMaxDays();
		if (days == null)
			days = 0;
		List<PayDate> payDates = this.service.getAvailablePayDates(employeeNumber, days);
		mav.addObject("payDates", payDates);
		PayDate latestPayDate = this.service.getLatestPayDate(payDates);
		String message = ((Options) session.getAttribute("options")).getMessageEarnings();
		Earnings earnings = this.service.retrieveEarnings(employeeNumber, latestPayDate);
		Earnings YTDEarnings = this.service.getTYDEarnings(employeeNumber, payDates, latestPayDate);
		for (int i = 0; i < earnings.getOther().size(); i++) {
			for (int j = 0; j < YTDEarnings.getOther().size(); j++) {
				if (earnings.getOther().get(i).getCode().equals(YTDEarnings.getOther().get(j).getCode())) {
					earnings.getOther().get(i).setTydAmt(YTDEarnings.getOther().get(j).getTydAmt());
					earnings.getOther().get(i).setTydContrib(YTDEarnings.getOther().get(j).getContrib());
				}
			}
		}
		YTDEarnings.setEmplrPrvdHlthcare(this.service.getEmplrPrvdHlthcare(employeeNumber, latestPayDate));
		Frequency freq = Frequency.getFrequency(StringUtil.mid(latestPayDate.getDateFreq(), 9, 1));
		String year = latestPayDate.getDateFreq().substring(0, 4);
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
		BeaUsers user = (BeaUsers) session.getAttribute("user");
		ModelAndView mav = new ModelAndView();
		if (null == user) {
			return this.getIndexPage(mav);
		}
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

	@RequestMapping("w2Information")
	public ModelAndView getW2Information(HttpServletRequest req) {
		HttpSession session = req.getSession();
		BeaUsers user = (BeaUsers) session.getAttribute("user");
		ModelAndView mav = new ModelAndView();
		if (null == user) {
			return this.getIndexPage(mav);
		}
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
		BeaUsers user = (BeaUsers) session.getAttribute("user");
		ModelAndView mav = new ModelAndView();
		if (null == user) {
			return this.getIndexPage(mav);
		}
		BhrEmpDemo userDetail = (BhrEmpDemo) session.getAttribute("userDetail");
		String employeeNumber = userDetail.getEmpNbr();
		BhrW2 w2Info = this.service.getW2Info(employeeNumber, year);
		if (isSuccess == null)
			mav = setW2ValuesByCalYr(session, mav, employeeNumber, w2Info, year, true);
		else
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
		BeaUsers user = (BeaUsers) session.getAttribute("user");
		ModelAndView mav = new ModelAndView();
		if (null == user) {
			return this.getIndexPage(mav);
		}
		BhrEmpDemo userDetail = (BhrEmpDemo) session.getAttribute("userDetail");
		String employeeNumber = userDetail.getEmpNbr();
		Boolean isSuccess = this.service.updateW2ElecConsent(employeeNumber, consent);
		return this.getW2InformationByYear(req, year, isSuccess);
	}

	@RequestMapping("information1095")
	public ModelAndView getInformation1095(HttpServletRequest req) {
		HttpSession session = req.getSession();
		BeaUsers user = (BeaUsers) session.getAttribute("user");
		ModelAndView mav = new ModelAndView();
		if (null == user) {
			return this.getIndexPage(mav);
		}
		mav = init1095(mav, session, null, 1, 1, null, null, null);
		return mav;
	}

	@RequestMapping("information1095ByYear")
	public ModelAndView getInformation1095ByYear(HttpServletRequest req, String year) {
		HttpSession session = req.getSession();
		BeaUsers user = (BeaUsers) session.getAttribute("user");
		ModelAndView mav = new ModelAndView();
		if (null == user) {
			return this.getIndexPage(mav);
		}
		mav.setViewName("/inquiry/information1095");
		mav = init1095(mav, session, year, 1, 1, null, null, null);
		return mav;
	}

	@RequestMapping("sortOrChangePageForTypeB")
	public ModelAndView sortOrChangePageForTypeB(HttpServletRequest req, String year, String BPageNo, String sortBy,
			String sortOrder) {
		HttpSession session = req.getSession();
		BeaUsers user = (BeaUsers) session.getAttribute("user");
		ModelAndView mav = new ModelAndView();
		if (null == user) {
			return this.getIndexPage(mav);
		}
		mav.setViewName("/inquiry/information1095");
		Options options = ((Options) session.getAttribute("options"));
		BhrEmpDemo userDetail = (BhrEmpDemo) session.getAttribute("userDetail");
		String employeeNumber = userDetail.getEmpNbr();
		List<String> years = this.service.retrieveAvailable1095CalYrs(employeeNumber);
		mav = init1095(mav, session, year, Integer.parseInt(BPageNo), 1, sortBy, sortOrder, "B");
		return mav;
	}

	@RequestMapping("sortOrChangePageForTypeC")
	public ModelAndView sortOrChangePageForTypeC(HttpServletRequest req, String year, String CPageNo, String sortBy,
			String sortOrder) {
		HttpSession session = req.getSession();
		BeaUsers user = (BeaUsers) session.getAttribute("user");
		ModelAndView mav = new ModelAndView();
		if (null == user) {
			return this.getIndexPage(mav);
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
