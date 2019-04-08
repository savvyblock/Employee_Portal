package com.esc20.controller;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.esc20.model.BeaUsers;
import com.esc20.model.BhrEmpDemo;
import com.esc20.nonDBModels.Account;
import com.esc20.nonDBModels.CurrentPayInformation;
import com.esc20.nonDBModels.District;
import com.esc20.nonDBModels.Earnings;
import com.esc20.nonDBModels.EmployeeInfo;
import com.esc20.nonDBModels.Frequency;
import com.esc20.nonDBModels.Options;
import com.esc20.nonDBModels.PayDate;
import com.esc20.nonDBModels.PayInfo;
import com.esc20.nonDBModels.Stipend;
import com.esc20.service.IndexService;
import com.esc20.service.InquiryService;
import com.esc20.util.DataSourceContextHolder;
import com.esc20.util.PDFUtil;
import com.esc20.util.StringUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/earnings")
public class EarningsController{

	@Autowired
	private InquiryService service;
	
    @Autowired
    private IndexService indexService;
	
	private final String module="Earnings";
	
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
		if(payDateString==null) {
			mav.setViewName("visitFailed");
			mav.addObject("module", module);
			mav.addObject("action", "Get Earnings by pay date");
			mav.addObject("errorMsg", "No pay date selected.");
			return mav;
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
	
	@RequestMapping("exportPDF")
	public void exportPDF(HttpServletRequest request, HttpServletResponse response, String selectedPayDate) throws Exception {
		String strBackUrl = "http://" + request.getServerName() + ":" + request.getServerPort()  + request.getContextPath();
		System.out.println("prefix" + strBackUrl);
		byte[] pdf = PDFUtil.getEarningsPDF(strBackUrl+"/earnings/earningsUnprotectedPDF", request, selectedPayDate);
		PayDate payDate = PayDate.getPaydate(selectedPayDate);
		response.reset();
		response.setHeader("Content-Disposition", "attachment; filename=\"Earnings for "+ payDate.getFormatedDate() +".pdf\"");
		response.setContentType("application/octet-stream;charset=UTF-8");
		OutputStream out = response.getOutputStream();
		out.write(pdf);
		out.flush();
	}
	
	@RequestMapping("earningsUnprotectedPDF")
	public ModelAndView earningsUnprotectedPDF(HttpServletRequest req, String empNbr, String districtId,String language,String selectedPayDate) throws IOException {
		DataSourceContextHolder.setDataSourceType("java:jboss/DBNEW"+districtId);
		HttpSession session = req.getSession();
		ModelAndView mav = new ModelAndView();
		String employeeNumber = empNbr;
		BhrEmpDemo userDetail = this.indexService.getUserDetail(empNbr);
		session.setAttribute("userDetail", userDetail);
		District districtInfo = this.indexService.getDistrict(districtId);
		session.setAttribute("district", districtInfo);
		Options options = this.indexService.getOptions();
		Integer days = options.getMaxDays();
		if (days == null)
			days = 0;
		List<PayDate> payDates = this.service.getAvailablePayDates(employeeNumber, days);
		mav.addObject("payDates", payDates);
		PayDate payDate = PayDate.getPaydate(selectedPayDate);
		String message = options.getMessageEarnings();
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
