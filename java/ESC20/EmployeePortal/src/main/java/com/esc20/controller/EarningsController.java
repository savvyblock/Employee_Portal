package com.esc20.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.esc20.model.BeaUsers;
import com.esc20.model.BeaW4;
import com.esc20.model.BhrCalYtd;
import com.esc20.model.BhrEmpDemo;
import com.esc20.nonDBModels.Code;
import com.esc20.nonDBModels.CurrentPayInformation;
import com.esc20.nonDBModels.District;
import com.esc20.nonDBModels.Earnings;
import com.esc20.nonDBModels.EarningsOther;
import com.esc20.nonDBModels.EarningsPrint;
import com.esc20.nonDBModels.Frequency;
import com.esc20.nonDBModels.Options;
import com.esc20.nonDBModels.PayDate;
import com.esc20.nonDBModels.PayInfo;
import com.esc20.nonDBModels.report.IReport;
import com.esc20.nonDBModels.report.ParameterReport;
import com.esc20.nonDBModels.report.ReportParameterConnection;
import com.esc20.service.IndexService;
import com.esc20.service.InquiryService;
import com.esc20.service.PDFService;
import com.esc20.service.ReferenceService;
import com.esc20.util.DateUtil;
import com.esc20.util.StringUtil;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.json.JSONArray;

@Controller
@RequestMapping("/earnings")
public class EarningsController {
	@Autowired
	private IndexService indexService;

	@Autowired
	private InquiryService service;

	@Autowired
	private ReferenceService referenceService;

	@Autowired
	private PDFService pDFService;

	private final String module = "Earnings";

	@RequestMapping("earnings")
	public ModelAndView getEarnings(HttpServletRequest req) {
		HttpSession session = req.getSession();
		BeaUsers user = (BeaUsers) session.getAttribute("user");
		BhrEmpDemo userDetail = this.indexService.getUserDetail(user.getEmpNbr());
		Options options = this.indexService.getOptions();
		String district = (String) session.getAttribute("districtId");
		District districtInfo = this.indexService.getDistrict(district);
		userDetail.setEmpNbr(user.getEmpNbr());
		userDetail.setDob(DateUtil.formatDate(userDetail.getDob(), "yyyyMMdd", "MM-dd-yyyy"));
		List<Code> gens = referenceService.getGenerations();
		for (Code gen : gens) {
			if (userDetail.getNameGen() != null
					&& gen.getCode().trim().equals(userDetail.getNameGen().toString().trim())) {
				userDetail.setGenDescription(gen.getDescription());
			}
		}
		String phone = districtInfo.getPhone();
		districtInfo.setPhone(
				StringUtil.left(phone, 3) + "-" + StringUtil.mid(phone, 4, 3) + "-" + StringUtil.right(phone, 4));

		session.setAttribute("options", options);
		session.setAttribute("userDetail", userDetail);
		session.setAttribute("companyId", district);
		session.setAttribute("district", districtInfo);

		Boolean isSupervisor = this.indexService.isSupervisor(user.getEmpNbr(), options.getUsePMISSpvsrLevels());
		Boolean isTempApprover = this.indexService.isTempApprover(user.getEmpNbr());
		session.setAttribute("isSupervisor", isSupervisor);
		session.setAttribute("isTempApprover", isTempApprover);

		ModelAndView mav = new ModelAndView();
		// BhrEmpDemo userDetail = (BhrEmpDemo) session.getAttribute("userDetail");
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
			latestPayDate = service.getLatestPayDate(payDates);
			message = ((Options) session.getAttribute("options")).getMessageEarnings();
			earnings = service.retrieveEarnings(employeeNumber, latestPayDate);
			YTDEarnings = service.getTYDEarnings(employeeNumber, latestPayDate);
			if (earnings != null && YTDEarnings != null) {

				// BRM-735 add any YTD EarningsOthers to the "this period" EarningsOthers with a
				// 0 balance
				List<EarningsOther> difference = new ArrayList<EarningsOther>(YTDEarnings.getOther());
				Set<String> earningsCodes = earnings.getOther().stream().map(earns -> earns.getCode())
						.collect(Collectors.toSet());
				difference = difference.stream().filter(diff -> !earningsCodes.contains(diff.getCode()))
						.collect(Collectors.toList());
				if (difference.size() > 0) {
					for (EarningsOther earningsOther : difference) {
						earningsOther.setAmt(BigDecimal.valueOf(0.00));
						earnings.getOther().add(earningsOther);
					}
				}

				for (int i = 0; i < earnings.getOther().size(); i++) {
					for (int j = 0; j < YTDEarnings.getOther().size(); j++) {
						if (earnings.getOther().get(i).getCode().equals(YTDEarnings.getOther().get(j).getCode())) {
							earnings.getOther().get(i).setTydAmt(YTDEarnings.getOther().get(j).getTydAmt());
							earnings.getOther().get(i).setTydContrib(YTDEarnings.getOther().get(j).getContrib());
						} 
					}
				}
			}
			freq = Frequency.getFrequency(StringUtil.mid(latestPayDate.getDateFreq(), 9, 1));
			year = latestPayDate.getDateFreq().substring(0, 4);
		}
		if (earnings != null && earnings.getOther() != null && earnings.getOther().size() > 0) {
			// Sort for earnings.others
			Collections.sort(earnings.getOther(), new Comparator<EarningsOther>() {
				@Override
				public int compare(EarningsOther o1, EarningsOther o2) {
					String s1 = String.valueOf(o1.getDescription());
					String s2 = String.valueOf(o2.getDescription());
					return s1.compareTo(s2);
				}
			});
		}

		Map<Frequency, List<CurrentPayInformation>> jobs = this.service.getJob(employeeNumber);
		List<Frequency> frequencies = this.service.getFrequencies(jobs);
		Map<Frequency, PayInfo> payInfos = this.service.retrievePayInfo(employeeNumber, frequencies);
		Map<Frequency, BeaW4> w4Request = this.indexService.getBeaW4Info(employeeNumber, frequencies);
		List<Code> w4FileStatOptions = this.referenceService.getW4MaritalActualStatuses();
		Map<Frequency, String> payCampuses = this.service.retrievePayCampuses(employeeNumber, frequencies);

		mav.addObject("payCampuses", payCampuses);
		mav.addObject("w4Request", w4Request);
		mav.addObject("payInfos", payInfos);
		mav.addObject("w4FileStatOptions", w4FileStatOptions);
		mav.setViewName("/inquiry/earnings");
		mav.addObject("days", days);
		mav.addObject("selectedPayDate", latestPayDate);
		mav.addObject("message", message);
		mav.addObject("earnings", earnings);
		mav.addObject("YTDEarnings", YTDEarnings);
		if (earnings != null && earnings.getSupplemental() != null && earnings.getSupplemental().size() > 0) {
			if (!(earnings.getSupplemental().size() == 1 && earnings.getSupplemental().get(0).getCode().equals("ZZZ")))
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
		Earnings earnings = service.retrieveEarnings(employeeNumber, payDate);
		Earnings YTDEarnings = service.getTYDEarnings(employeeNumber, payDate);
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
		if (earnings.getSupplemental().size() > 0) {
			if (!(earnings.getSupplemental().size() == 1 && earnings.getSupplemental().get(0).getCode().equals("ZZZ")))
				mav.addObject("isSupplemental", true);
		}
		mav.addObject("YTDEarnings", YTDEarnings);
		mav.addObject("year", year);
		mav.addObject("freq", freq);
		return mav;
	}

	@RequestMapping("exportPDF")
	public void exportPDF(HttpServletRequest request, HttpServletResponse response, String selectedPayDate)
			throws Exception {
		HttpSession session = request.getSession();
		BhrEmpDemo userDetail = (BhrEmpDemo) session.getAttribute("userDetail");
		String employeeNumber = userDetail.getEmpNbr();
		Integer days = ((Options) session.getAttribute("options")).getMaxDays();
		if (days == null)
			days = 0;
		response.setContentType("application/x-msdownload;charset=UTF-8");
		PayDate payDate;
		if (selectedPayDate != null)
			payDate = PayDate.getPaydate(selectedPayDate);
		else {
			List<PayDate> payDates = this.service.getAvailablePayDates(employeeNumber, days);
			payDate = this.service.getLatestPayDate(payDates);
		}
		response.setHeader("Content-Disposition",
				"attachment;filename=Earnings for " + payDate.getFormatedDate() + ".pdf");

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
		System.out.println("Earnings Print: " + jsonArray.toString());
		IReport ireport = setupReport(report, earningsPrint);

		JasperPrint jasperPrint = pDFService.buildReport(ireport);
		JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
	}

	@RequestMapping("printPDF")
	@ResponseBody
	public void printPDF(HttpServletRequest request, HttpServletResponse response, String selectedPayDate)
			throws Exception {
		HttpSession session = request.getSession();
		BhrEmpDemo userDetail = (BhrEmpDemo) session.getAttribute("userDetail");
		String employeeNumber = userDetail.getEmpNbr();
		Integer days = ((Options) session.getAttribute("options")).getMaxDays();
		if (days == null)
			days = 0;
		response.setContentType("application/pdf;charset=UTF-8");
		PayDate payDate;
		if (selectedPayDate != null)
			payDate = PayDate.getPaydate(selectedPayDate);
		else {
			List<PayDate> payDates = this.service.getAvailablePayDates(employeeNumber, days);
			payDate = this.service.getLatestPayDate(payDates);
		}
		response.setHeader("Content-Disposition",
				"application/pdf;filename=Earnings for " + payDate.getFormatedDate() + ".pdf");

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
		System.out.println("Earnings Print: " + jsonArray.toString());
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
		print.setBhr_emp_demo_addr_nbr(userDetail.getAddrNbr());
		print.setBhr_emp_demo_addr_str(userDetail.getAddrStr());
		print.setBhr_emp_demo_addr_apt(userDetail.getAddrApt());
		print.setBhr_emp_demo_addr_city(userDetail.getAddrCity());
		print.setBhr_emp_demo_addr_st(userDetail.getAddrSt());
		print.setBhr_emp_demo_addr_zip(userDetail.getAddrZip());
		print.setBhr_emp_demo_addr_zip4(userDetail.getAddrZip4() == null ? "" : userDetail.getAddrZip4().trim());
		print.setBhr_emp_pay_pay_campus(earnings.getInfo().getCampusId());
		print.setBhr_emp_pay_emp_nbr(userDetail.getEmpNbr());
		print.setBhr_emp_job_campus_id(primaryCampusId);
		print.setBhr_emp_job_campus_id_displayvalue(primaryCampusId + " " + primaryCampusName);
		print.setBhr_emp_pay_pay_campus_displayvalue(primaryCampusId + " " + primaryCampusName);
		// w4

		PayInfo payInfos = this.indexService.getPayInfo(userDetail, print.getBhr_emp_pay_pay_freq());
		BeaW4 w4Request = this.indexService.getBeaW4(userDetail, print.getBhr_emp_pay_pay_freq());

		print.setMaritalStatTax(payInfos.getMaritalStatTax());
		print.setNbrTaxExempts(payInfos.getNbrTaxExempts());
		print.setW4FileStat(w4Request.getW4FileStat());
		print.setW4MultiJob(w4Request.getW4MultiJob());
		print.setW4NbrChldrn(w4Request.getW4NbrChldrn());
		print.setW4OthrIncAmt(w4Request.getW4OthrIncAmt());
		print.setW4OthrDedAmt(w4Request.getW4OthrDedAmt());
		print.setW4OthrExmptAmt(w4Request.getW4OthrExmptAmt() );
		print.setW4NbrOthrDep(w4Request.getW4NbrOthrDep());
		return print;
	}

	private IReport setupReport(ParameterReport report, EarningsPrint data) throws Exception {
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
