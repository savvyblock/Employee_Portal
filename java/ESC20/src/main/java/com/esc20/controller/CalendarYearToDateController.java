package com.esc20.controller;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.esc20.model.BeaUsers;
import com.esc20.model.BhrCalYtd;
import com.esc20.model.BhrEmpDemo;
import com.esc20.nonDBModels.Account;
import com.esc20.nonDBModels.CalYTDPrint;
import com.esc20.nonDBModels.CurrentPayInformation;
import com.esc20.nonDBModels.District;
import com.esc20.nonDBModels.EmployeeInfo;
import com.esc20.nonDBModels.Frequency;
import com.esc20.nonDBModels.PayInfo;
import com.esc20.nonDBModels.Stipend;
import com.esc20.nonDBModels.report.IReport;
import com.esc20.nonDBModels.report.ParameterReport;
import com.esc20.service.IndexService;
import com.esc20.service.InquiryService;
import com.esc20.service.PDFService;
import com.esc20.util.DataSourceContextHolder;
import com.esc20.util.DateUtil;
import com.esc20.util.PDFUtil;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/calendarYearToDate")
public class CalendarYearToDateController {

	@Autowired
	private InquiryService service;

    @Autowired
    private IndexService indexService;
    
    @Autowired
    private PDFService pDFService;
	
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
	
//	@RequestMapping("exportPDF")
//	public void exportPDF(HttpServletRequest request, HttpServletResponse response, String year) throws Exception {
//		String strBackUrl = "http://" + request.getServerName() + ":" + request.getServerPort()  + request.getContextPath();
//		System.out.println("prefix" + strBackUrl);
//		byte[] pdf = PDFUtil.getCalendarYTDPDF(strBackUrl+"/calendarYearToDate/calendarYearToDateUnprotectedPDF", request, year);
//		response.reset();
//		response.setHeader("Content-Disposition", "attachment; filename=\"Calender Year to Date "+year+".pdf\"");
//		response.setContentType("application/octet-stream;charset=UTF-8");
//		OutputStream out = response.getOutputStream();
//		out.write(pdf);
//		out.flush();
//	}
	
	@RequestMapping("exportPDF")
	public void exportPDF(HttpServletRequest request, HttpServletResponse response, String year) throws Exception {
		
		response.setContentType("application/x-msdownload;charset=UTF-8");
		response.setHeader("Content-Disposition",
				"attachment;filename=CalYTDPrint.pdf");
		
		String path = request.getServletContext().getRealPath("/");
		if (path != null && !path.endsWith("\\")) {
			path = path.concat("\\");
		}
		pDFService.setRealPath(path);
		
		BhrEmpDemo userDetail = (BhrEmpDemo) request.getSession().getAttribute("userDetail");
		District district = (District) request.getSession().getAttribute("district");
		
		
		BhrCalYtd b = pDFService.retrieveCalendar(userDetail.getEmpNbr(), year);
		
		List<CalYTDPrint> parameters = pDFService.generateCalYTDPrint(userDetail, district, b);
		
		ParameterReport report = new ParameterReport();
		
		report.setTitle("Calendar YTD Report");
		report.setId("calYTDReport");
		report.setFileName("CalYTDPrint");
		report.setSortable(false);
		report.setFilterable(false);
		
		IReport ireport = pDFService.setupReport(report, parameters);
		
		JasperReport jasperReport;
	    JasperPrint jasperPrint = null;
		
//		ClassLoader cl = this.getClass().getClassLoader();
//		jasperReport=(JasperReport)JRLoader.loadObjectFromLocation("com/innovated/iris/server/report/n_fbt_final.jasper", cl);
		
	    ;
		jasperPrint = pDFService.buildReport(ireport);
		
//		myMap.put("CIN", cin);
//		myMap.put("PERIOD_START", start);
//		myMap.put("PERIOD_END", end);
//		myMap.put("YR_END", Boolean.TRUE);
		
    	
    	JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());

		
		
//		byte[] pdf = PDFUtil.getCalendarYTDPDF(strBackUrl+"/calendarYearToDate/calendarYearToDateUnprotectedPDF", request, year);
//		response.reset();
//		response.setHeader("Content-Disposition", "attachment; filename=\"Calender Year to Date "+year+".pdf\"");
//		response.setContentType("application/octet-stream;charset=UTF-8");
//		OutputStream out = response.getOutputStream();
//		out.write(pdf);
//		out.flush();
	}
	
	@RequestMapping("calendarYearToDateUnprotectedPDF")
	public ModelAndView calendarYearToDateUnprotectedPDF(HttpServletRequest req, String empNbr, String districtId,String language,String year) throws IOException {
		DataSourceContextHolder.setDataSourceType("java:jboss/DBNEW"+districtId);
		HttpSession session = req.getSession();
		ModelAndView mav = new ModelAndView();
		String employeeNumber = empNbr;
		BhrEmpDemo userDetail = this.indexService.getUserDetail(empNbr);
		session.setAttribute("userDetail", userDetail);
		District districtInfo = this.indexService.getDistrict(districtId);
		session.setAttribute("district", districtInfo);
		String path = req.getSession().getServletContext().getRealPath("/") +"/static/js/lang/text-"+language+".json";
		File file = new File(path);
		String input = FileUtils.readFileToString(file, "UTF-8");
		JSONObject jsonObject = JSONObject.fromObject(input);
		req.getSession().setAttribute("languageJSON", jsonObject);
		mav.addObject("isPrintPDF", true);
		List<String> years = service.getAvailableYears(employeeNumber);
		BhrCalYtd calYTD = service.getCalenderYTD(employeeNumber, year);
		BigDecimal trsIns = calYTD.getTrsDeposit().subtract(calYTD.getTrsSalaryRed());
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
