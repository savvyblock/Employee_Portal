package com.esc20.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.esc20.model.BeaUsers;
import com.esc20.model.BhrEmpDemo;
import com.esc20.nonDBModels.Code;
import com.esc20.nonDBModels.LeaveBalance;
import com.esc20.nonDBModels.LeaveInfo;
import com.esc20.service.LeaveRequestService;
import com.esc20.service.ReferenceService;
import com.esc20.util.DateUtil;

@Controller
@RequestMapping("/leaveBalance")
public class LeaveBalanceController{

	@Autowired
	private LeaveRequestService service;

	@Autowired
	private ReferenceService referenceService;
	
	private final String module = "Leave Balance";
	
	@RequestMapping("leaveBalance")
	public ModelAndView leaveBalance(HttpServletRequest req, String SearchType, String SearchStart, String SearchEnd,
			String freq) throws ParseException {
		HttpSession session = req.getSession();
		ModelAndView mav = new ModelAndView();
		SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
		String start = SearchStart;
		String end = SearchEnd;
		if (SearchStart != null && !("").equals(SearchStart)) {
			start = sdf2.format(DateUtil.getUTCTime(sdf1.parse(SearchStart)));
		}
		if (SearchEnd != null && !("").equals(SearchEnd)) {
			end = sdf2.format(DateUtil.getUTCTime(sdf1.parse(SearchEnd)));
		}
		BhrEmpDemo demo = ((BhrEmpDemo) session.getAttribute("userDetail"));
		List<Code> availableFreqs = this.service.getAvailableFrequencies(demo.getEmpNbr());
		List<Code> leaveTypesWithAll = new ArrayList<>();
		Code code = new Code("", "All");
		leaveTypesWithAll.add(code);
		if (freq == null || ("").equals(freq)) {
			if (availableFreqs.size() > 0) {
				freq = availableFreqs.get(0).getCode();
				List<LeaveBalance> approvedLeaves = this.service.getApprovedLeaves(demo.getEmpNbr(), SearchType,
						start, end, freq);
				List<Code> absRsns = this.service.getAbsRsns(demo.getEmpNbr(), freq, "");
				List<Code> leaveTypes = this.service.getLeaveTypes(demo.getEmpNbr(), freq, "");
				List<LeaveInfo> leaveInfo = this.service.getLeaveInfo(demo.getEmpNbr(), freq, false);
				leaveTypesWithAll.addAll(leaveTypes);
				mav.setViewName("/leaveBalance/leaveBalance");
				mav.addObject("selectedFreq", freq);
				mav.addObject("absRsns", absRsns);
				mav.addObject("leaveTypes", leaveTypesWithAll);
				mav.addObject("leaveInfo", leaveInfo);
				mav.addObject("leaves", approvedLeaves);
			}
		} else {
			List<LeaveBalance> approvedLeaves = this.service.getApprovedLeaves(demo.getEmpNbr(), SearchType, start,
					end, freq);
			List<Code> absRsns = this.service.getAbsRsns(demo.getEmpNbr(), freq, "");
			List<Code> leaveTypes = this.service.getLeaveTypes(demo.getEmpNbr(), freq, "");
			List<LeaveInfo> leaveInfo = this.service.getLeaveInfo(demo.getEmpNbr(), freq, false);
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
