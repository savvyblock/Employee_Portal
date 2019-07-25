package com.esc20.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.esc20.model.BhrEmpDemo;
import com.esc20.nonDBModels.Code;
import com.esc20.nonDBModels.LeaveBalance;
import com.esc20.nonDBModels.LeaveInfo;
import com.esc20.nonDBModels.Options;
import com.esc20.service.IndexService;
import com.esc20.service.LeaveRequestService;
import com.esc20.util.DateUtil;

@Controller
@RequestMapping("/leaveBalance")
public class LeaveBalanceController{
	@Autowired
	private IndexService indexService;

	@Autowired
	private LeaveRequestService service;
	
	private final String module = "Leave Balance";
	
	@RequestMapping("leaveBalance")
	public ModelAndView leaveBalance(HttpServletRequest req, String SearchType, String SearchStart, String SearchEnd,
			String freq) throws ParseException {
		HttpSession session = req.getSession();
		Options options = this.indexService.getOptions();
		session.setAttribute("options", options);
		
		ModelAndView mav = new ModelAndView();
		SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
		
		String start = SearchStart;
		String end = SearchEnd;
		if (SearchStart != null && !("").equals(SearchStart)) {
			start = sdf2.format(DateUtil.getUTCTime(sdf1.parse(SearchStart)));
		} else {
			Date endL;
			if (SearchEnd != null && !("").equals(SearchEnd)) {
				endL = DateUtil.getUTCTime(sdf1.parse(SearchEnd));
			} else {
				endL =  new Date();
			}
			Calendar fromC = Calendar.getInstance();
			fromC.setTime(endL);
			fromC.add(Calendar.MONTH, -18);
			start = sdf2.format(fromC.getTime());
		}
		if (SearchEnd != null && !("").equals(SearchEnd)) {
			end = sdf2.format(DateUtil.getUTCTime(sdf1.parse(SearchEnd)));
		} else {
			end =  sdf2.format(new Date());
		}
		BhrEmpDemo demo = ((BhrEmpDemo) session.getAttribute("userDetail"));
		List<Code> availableFreqs = this.service.getAvailableFrequencies(demo.getEmpNbr());
		if (availableFreqs.size() > 0) {
			for (int i = 0; i < availableFreqs.size(); i++) {
				String freqCode = availableFreqs.get(i).getCode();
				if("4".equals(freqCode) || "Biweekly".equals(freqCode))
				{
					availableFreqs.get(i).setDescription("Biweekly");
				}
				else if("5".equals(freqCode) || "Semimonthly".equals(freqCode)) 
				{
					availableFreqs.get(i).setDescription("Semimonthly");
				}
				else if("6".equals(freqCode) || "Monthly".equals(freqCode))
				{
					availableFreqs.get(i).setDescription("Monthly");
				}
			}
		}
		List<Code> leaveTypesWithAll = new ArrayList<>();
		Code code = new Code("", "ALL");
		leaveTypesWithAll.add(code);
		if (freq == null || ("").equals(freq)) {
			if (availableFreqs.size() > 0) {
				freq = availableFreqs.get(0).getCode();
				//List<LeaveBalance> approvedLeaves = this.service.getApprovedLeaves(demo.getEmpNbr(), SearchType,
				//		start, end, freq);
				List<Code> absRsns = this.service.getAbsRsns(demo.getEmpNbr(), freq, "");
				List<Code> leaveTypes = this.service.getLeaveTypes(demo.getEmpNbr(), freq, "");
				List<LeaveInfo> leaveInfo = this.service.getLeaveInfo(demo.getEmpNbr(), freq, true);
				leaveTypesWithAll.addAll(leaveTypes);
				mav.setViewName("/leaveBalance/leaveBalance");
				mav.addObject("selectedFreq", freq);
				mav.addObject("absRsns", absRsns);
				mav.addObject("leaveTypes", leaveTypesWithAll);
				mav.addObject("leaveInfo", leaveInfo);
				//mav.addObject("leaves", approvedLeaves);
			}
		} else {
			List<LeaveBalance> approvedLeaves = this.service.getApprovedLeaves(demo.getEmpNbr(), SearchType, start,
					end, freq);
			List<Code> absRsns = this.service.getAbsRsns(demo.getEmpNbr(), freq, "");
			List<Code> leaveTypes = this.service.getLeaveTypes(demo.getEmpNbr(), freq, "");
			List<LeaveInfo> leaveInfo = this.service.getLeaveInfo(demo.getEmpNbr(), freq, true);
			leaveTypesWithAll.addAll(leaveTypes);
			mav.setViewName("/leaveBalance/leaveBalance");
			mav.addObject("selectedFreq", freq);
			mav.addObject("absRsns", absRsns);
			mav.addObject("leaveTypes", leaveTypesWithAll);
			mav.addObject("leaveInfo", leaveInfo);
			mav.addObject("leaves", approvedLeaves);
		}
		mav.addObject("SearchType", SearchType);
		mav.addObject("SearchStart", SearchStart);
		mav.addObject("SearchEnd", SearchEnd);
		mav.addObject("availableFreqs", availableFreqs);
		return mav;
	}

	@RequestMapping("leaveBalanceByFreqency")
	public ModelAndView leaveBalanceByFreqency(HttpServletRequest req, String freq) throws ParseException {
		ModelAndView mav = new ModelAndView();
		if(freq==null) {
			mav.setViewName("visitFailed");
			mav.addObject("module", module);
			mav.addObject("action", "Get leave balance by frequency");
			mav.addObject("errorMsg", "No frequency selected.");
			return mav;
		}
		mav = this.leaveBalance(req, null, null, null, freq);
		mav.setViewName("/leaveBalance/leaveBalance");
		return mav;
	}
}
