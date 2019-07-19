package com.esc20.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.esc20.model.BhrEmpDemo;
import com.esc20.nonDBModels.District;
import com.esc20.nonDBModels.Earnings;
import com.esc20.nonDBModels.EarningsPrint;
import com.esc20.nonDBModels.Frequency;
import com.esc20.nonDBModels.Options;
import com.esc20.nonDBModels.PayDate;
import com.esc20.nonDBModels.report.IReport;
import com.esc20.nonDBModels.report.ParameterReport;
import com.esc20.nonDBModels.report.ReportParameterConnection;
import com.esc20.service.InquiryService;
import com.esc20.service.PDFService;
import com.esc20.util.StringUtil;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.json.JSONArray;

@Controller
@RequestMapping("/earnings")
public class EarningsController {

	@Autowired
	private InquiryService service;

    @Autowired
    private PDFService pDFService;
	
	private final String module = "Earnings";

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
			if(earnings!=null && YTDEarnings!=null) {
				for (int i = 0; i < earnings.getOther().size(); i++) {
					for (int j = 0; j < YTDEarnings.getOther().size(); j++) {
						if (earnings.getOther().get(i).getCode().equals(YTDEarnings.getOther().get(j).getCode())) {
							earnings.getOther().get(i).setTydAmt(YTDEarnings.getOther().get(j).getTydAmt());
							earnings.getOther().get(i).setTydContrib(YTDEarnings.getOther().get(j).getContrib());
						}
					}
				}
				YTDEarnings.setEmplrPrvdHlthcare(this.service.getEmplrPrvdHlthcare(employeeNumber, latestPayDate));
			}
			freq = Frequency.getFrequency(StringUtil.mid(latestPayDate.getDateFreq(), 9, 1));
			year = latestPayDate.getDateFreq().substring(0, 4);
		}
		mav.setViewName("/inquiry/earnings");
		mav.addObject("days", days);
		mav.addObject("selectedPayDate", latestPayDate);
		mav.addObject("message", message);
		mav.addObject("earnings", earnings);
		mav.addObject("YTDEarnings", YTDEarnings);
		if(earnings !=null && earnings.getSupplemental() !=null && earnings.getSupplemental().size()>0) {
			if(!(earnings.getSupplemental().size()==1 && earnings.getSupplemental().get(0).getCode().equals("ZZZ")))
				mav.addObject("isSupplemental", true);
		}
		mav.addObject("year", year);
		mav.addObject("freq", freq);
		return mav;
	}

	@RequestMapping("earningsByPayDate")
	public ModelAndView getEarningsByPayDate(HttpServletRequest req, String payDateString) {
		HttpSession session = req.getSession();
		ModelAndView mav = new ModelAndView();
		if (payDateString == null) {
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
		if(earnings.getSupplemental().size()>0) {
			if(!(earnings.getSupplemental().size()==1 && earnings.getSupplemental().get(0).getCode().equals("ZZZ")))
				mav.addObject("isSupplemental", true);
		}
		mav.addObject("YTDEarnings", YTDEarnings);
		mav.addObject("year", year);
		mav.addObject("freq", freq);
		return mav;
	}

	@RequestMapping("exportPDF")
	public void exportPDF(HttpServletRequest request, HttpServletResponse response, String selectedPayDate) throws Exception {
		HttpSession session = request.getSession();
		BhrEmpDemo userDetail = (BhrEmpDemo) session.getAttribute("userDetail");
		String employeeNumber = userDetail.getEmpNbr();
		Integer days = ((Options) session.getAttribute("options")).getMaxDays();
		if (days == null)
			days = 0;
		response.setContentType("application/x-msdownload;charset=UTF-8");
		PayDate payDate;
		if(selectedPayDate !=null)
			payDate = PayDate.getPaydate(selectedPayDate);
		else {
			List<PayDate> payDates = this.service.getAvailablePayDates(employeeNumber, days);
			payDate = this.service.getLatestPayDate(payDates);
		}
		response.setHeader("Content-Disposition", "attachment;filename=Earnings for "+payDate.getFormatedDate()+".pdf");
		
		String path = request.getServletContext().getRealPath("/");
		if (path != null && !path.endsWith("\\")) {
			path = path.concat("\\");
		}
		pDFService.setRealPath(path);
		
		ParameterReport report = new ParameterReport();
		report.setTitle("Earnings Report");
		report.setId("earningsReport");
		report.setFileName("DHrs2500WageandearningstmtTab");
		report.setSortable(false);
		report.setFilterable(false);
		
		EarningsPrint earningsPrint = generateEarningsPrint(request, response, selectedPayDate);
		JSONArray jsonArray = JSONArray.fromObject(earningsPrint);
		System.out.println("Earnings Print: "+jsonArray.toString());
		IReport ireport = setupReport(report, earningsPrint);
		
	    JasperPrint jasperPrint = pDFService.buildReport(ireport);
    	JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
	}

	@RequestMapping("printPDF")
	@ResponseBody
	public void printPDF(HttpServletRequest request, HttpServletResponse response, String selectedPayDate) throws Exception {
		HttpSession session = request.getSession();
		BhrEmpDemo userDetail = (BhrEmpDemo) session.getAttribute("userDetail");
		String employeeNumber = userDetail.getEmpNbr();
		Integer days = ((Options) session.getAttribute("options")).getMaxDays();
		if (days == null)
			days = 0;
		response.setContentType("application/pdf;charset=UTF-8");
		PayDate payDate;
		if(selectedPayDate !=null)
			payDate = PayDate.getPaydate(selectedPayDate);
		else {
			List<PayDate> payDates = this.service.getAvailablePayDates(employeeNumber, days);
			payDate = this.service.getLatestPayDate(payDates);
		}
		response.setHeader("Content-Disposition", "application/pdf;filename=Earnings for "+payDate.getFormatedDate()+".pdf");
		
		String path = request.getServletContext().getRealPath("/");
		if (path != null && !path.endsWith("\\")) {
			path = path.concat("\\");
		}
		pDFService.setRealPath(path);
		
		ParameterReport report = new ParameterReport();
		report.setTitle("Earnings Report");
		report.setId("earningsReport");
		report.setFileName("DHrs2500WageandearningstmtTab");
		report.setSortable(false);
		report.setFilterable(false);
		
		EarningsPrint earningsPrint = generateEarningsPrint(request, response, selectedPayDate);
		JSONArray jsonArray = JSONArray.fromObject(earningsPrint);
		System.out.println("Earnings Print: "+jsonArray.toString());
		IReport ireport = setupReport(report, earningsPrint);
		
	    JasperPrint jasperPrint = pDFService.buildReport(ireport);
    	JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
	}
	
	public EarningsPrint generateEarningsPrint(HttpServletRequest request, HttpServletResponse response,
			String selectedPayDate) {
		EarningsPrint print = new EarningsPrint();
		District district = (District) request.getSession().getAttribute("district");
		BhrEmpDemo userDetail = (BhrEmpDemo) request.getSession().getAttribute("userDetail");
		print.setDname(district.getName());
		print.setBhr_emp_demo_name_l(userDetail.getNameL());
		print.setBhr_emp_demo_name_gen(userDetail.getNameGen() == null ? "" : userDetail.getNameGen().toString());
		print.setGen_code_descr(userDetail.getGenDescription() == null ? "" : userDetail.getGenDescription());
		print.setBhr_emp_demo_name_f(userDetail.getNameF());
		print.setBhr_emp_demo_name_m(userDetail.getNameM());
		PayDate payDate = PayDate.getPaydate(selectedPayDate);
		Earnings earnings = this.service.retrieveEarnings(userDetail.getEmpNbr(), payDate);
		print.setBhr_pay_hist_chk_nbr(earnings.getInfo().getCheckNumber());
		print.setBhr_pay_hist_dt_of_pay(StringUtil.left(selectedPayDate, 8));
		print.setBhr_pay_hist_dt_of_pay1(StringUtil.left(selectedPayDate, 8));
		print.setBthr_pay_dates_dt_payper_beg(earnings.getInfo().getPeriodBeginningDate());
		print.setBthr_pay_dates_dt_payper_end(earnings.getInfo().getPeriodEndingDate());
		print.setBhr_pay_hist_marital_stat_tax(earnings.getInfo().getWithholdingStatus());
		if (!earnings.getInfo().getNumExceptions().equals("")) {
			print.setBhr_pay_hist_nbr_tax_exempts(Integer.parseInt(earnings.getInfo().getNumExceptions()));
		}
		String primaryCampusId = earnings.getInfo().getCampusId();
		String primaryCampusName = earnings.getInfo().getCampusName();
		print.setBhr_emp_pay_pay_freq(StringUtil.right(payDate.getDateFreq(), 1));
		print.setCal_year(StringUtil.left(payDate.getDateFreq(), 4));
		print.setBhr_pay_hist_void_or_iss(payDate.getVoidIssue());
		print.setBhr_pay_hist_adj_nbr(payDate.getAdjNumber());
		print.setBhr_pay_hist_adj_nbr(StringUtil.mid(selectedPayDate, 11, 1));
		print.setBhr_emp_demo_addr_nbr("");
		print.setBhr_emp_demo_addr_str(userDetail.getAddrStr());
		print.setBhr_emp_demo_addr_apt("");
		print.setBhr_emp_demo_addr_city(userDetail.getAddrCity());
		print.setBhr_emp_demo_addr_st(userDetail.getAddrSt());
		print.setBhr_emp_demo_addr_zip(userDetail.getAddrZip());
		print.setBhr_emp_demo_addr_zip4(userDetail.getAddrZip4()==null?"":userDetail.getAddrZip4().trim());
		print.setBhr_emp_pay_pay_campus(earnings.getInfo().getCampusId());
		print.setBhr_emp_pay_emp_nbr(userDetail.getEmpNbr());
		print.setBhr_emp_job_campus_id(primaryCampusId);
		print.setBhr_emp_job_campus_id_displayvalue(primaryCampusId + " " + primaryCampusName);
		print.setBhr_emp_pay_pay_campus_displayvalue(primaryCampusId + " " + primaryCampusName);
		return print;
	}

	private IReport setupReport(ParameterReport report, EarningsPrint data) throws Exception 
	{
		report.getParameters().clear();
		ReportParameterConnection parameter = new ReportParameterConnection();
		parameter.setName("subRptConnection");
		parameter.setConnection(pDFService.getConn());
		report.getParameters().add(parameter);
		
		report.setFileName("DHrs2500WageandearningstmtTab");
		
		List<EarningsPrint> forms = new ArrayList<EarningsPrint>();
		forms.add(data);
		report.setDataSource(new JRBeanCollectionDataSource(forms));
		
		return report;
	}
	
}
