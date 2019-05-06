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
import com.esc20.service.IndexService;
import com.esc20.service.InquiryService;
import com.esc20.util.DataSourceContextHolder;
import com.esc20.util.DateUtil;
import com.esc20.util.NumberUtil;
import com.esc20.util.StringUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/information1095")
public class Information1095Controller{

	@Autowired
	private InquiryService service;

    @Autowired
    private IndexService indexService;
	
	private final String module = "1095 Information";
	
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
		if(consent==null||year==null) {
			mav.setViewName("visitFailed");
			mav.addObject("module", module);
			mav.addObject("action", "Update 1095 consent");
			mav.addObject("errorMsg", "Not all mandotary fields provided.");
			return mav;
		}
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
		if(year==null) {
			mav.setViewName("visitFailed");
			mav.addObject("module", module);
			mav.addObject("action", "Get 1095 information by year");
			mav.addObject("errorMsg", "Not all mandotary fields provided.");
			return mav;
		}
		mav.setViewName("/inquiry/information1095");
		mav = init1095(mav, session, year, 1, 1, null, null, null);
		return mav;
	}

	@RequestMapping("sortOrChangePageForTypeB")
	public ModelAndView sortOrChangePageForTypeB(HttpServletRequest req, String year, String BPageNo, String sortBy,
			String sortOrder) {
		HttpSession session = req.getSession();
		ModelAndView mav = new ModelAndView();
		if(year==null||BPageNo==null) {
			mav.setViewName("visitFailed");
			mav.addObject("module", module);
			mav.addObject("action", "Get 1095-B information");
			mav.addObject("errorMsg", "Not all mandotary fields provided.");
			return mav;
		}
		mav.setViewName("/inquiry/information1095");
		mav = init1095(mav, session, year, Integer.parseInt(BPageNo), 1, sortBy, sortOrder, "B");
		return mav;
	}

	@RequestMapping("sortOrChangePageForTypeC")
	public ModelAndView sortOrChangePageForTypeC(HttpServletRequest req, String year, String CPageNo, String sortBy,
			String sortOrder) {
		HttpSession session = req.getSession();
		ModelAndView mav = new ModelAndView();
		if(year==null||CPageNo==null) {
			mav.setViewName("visitFailed");
			mav.addObject("module", module);
			mav.addObject("action", "Get 1095-C information");
			mav.addObject("errorMsg", "Not all mandotary fields provided.");
			return mav;
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
		String message = options.getMessageElecConsent1095().trim();
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
		mav.addObject("sortBy", sortBy);
		mav.addObject("sortOrder", sortOrder);
		return mav;
	}
	
//	@RequestMapping("exportPDF")
//	public void exportPDF(HttpServletRequest request, HttpServletResponse response,  String year, Integer BPageNo, Integer CPageNo,
//			String sortBy, String sortOrder, String type) throws Exception {
//		String strBackUrl = "http://" + request.getServerName() + ":" + request.getServerPort()  + request.getContextPath();
//		System.out.println("prefix" + strBackUrl);
//		byte[] pdf = PDFUtil.get1095InformationPDF(strBackUrl+"/information1095/information1095UnprotectedPDF", request, year,BPageNo,CPageNo,sortBy,sortOrder,type);
//		response.reset();
//		response.setHeader("Content-Disposition", "attachment; filename=\"1095 Information for "+ year +" type "+ type +".pdf\"");
//		response.setContentType("application/octet-stream;charset=UTF-8");
//		OutputStream out = response.getOutputStream();
//		out.write(pdf);
//		out.flush();
//	}
	
	@RequestMapping("information1095UnprotectedPDF")
	public ModelAndView information1095UnprotectedPDF(HttpServletRequest req, String empNbr, String districtId,String language, String year, String BPageNo, String CPageNo,
			String sortBy, String sortOrder, String type) throws IOException {
		DataSourceContextHolder.setDataSourceType("java:jboss/DBNEW"+districtId);
		HttpSession session = req.getSession();
		ModelAndView mav = new ModelAndView();
		BhrEmpDemo userDetail = this.indexService.getUserDetail(empNbr);
		session.setAttribute("userDetail", userDetail);
		District districtInfo = this.indexService.getDistrict(districtId);
		session.setAttribute("district", districtInfo);
		Options options = this.indexService.getOptions();
		session.setAttribute("options", options);
		String path = req.getSession().getServletContext().getRealPath("/") +"/static/js/lang/text-"+language+".json";
		File file = new File(path);
		String input = FileUtils.readFileToString(file, "UTF-8");
		JSONObject jsonObject = JSONObject.fromObject(input);
		session.setAttribute("languageJSON", jsonObject);
		mav.setViewName("/inquiry/information1095");
		mav = init1095(mav, session, year, Integer.parseInt(BPageNo), Integer.parseInt(CPageNo), sortBy, sortOrder, type);
		return mav;
	}
}
