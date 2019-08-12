package com.esc20.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.esc20.model.BeaEmpLvRqst;
import com.esc20.model.BhrEmpDemo;
import com.esc20.nonDBModels.AppLeaveRequest;
import com.esc20.nonDBModels.Code;
import com.esc20.nonDBModels.LeaveEmployeeData;
import com.esc20.nonDBModels.LeaveParameters;
import com.esc20.nonDBModels.LeaveRequest;
import com.esc20.nonDBModels.LeaveRequestModel;
import com.esc20.service.IndexService;
import com.esc20.service.LeaveRequestService;
import com.esc20.service.ReferenceService;
import com.esc20.service.SupervisorService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/approveLeaveRequest")
public class ApproveLeaveRequestController extends BaseSupervisorController {

	@Autowired
	private LeaveRequestService service;

	@Autowired
	private SupervisorService supService;

	@Autowired
	private IndexService indexService;

	@Autowired
	private ReferenceService referenceService;
	
	private final String module = "Approve Leave Request";
	
	@RequestMapping("approveLeaveRequestList")
	public ModelAndView getApproveLeaveRequestList(HttpServletRequest req, String empNbr) throws ParseException {
		HttpSession session = req.getSession();
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/supervisor/approveLeaveRequestList");
		LeaveParameters params = this.service.getLeaveParameters();
		BhrEmpDemo demo = ((BhrEmpDemo) session.getAttribute("userDetail"));
		boolean supervisorsOnly = true;
		boolean excludeTempApprovers = false;
		if (empNbr == null || ("").equals(empNbr)) {
			empNbr = demo.getEmpNbr();
		} else {
			demo = this.indexService.getUserDetail(empNbr);
		}
		LeaveEmployeeData empty = new LeaveEmployeeData();
		List<LeaveEmployeeData> employeeData = this.supService.getDirectReportEmployee(empNbr, params.isUsePMIS(),
				supervisorsOnly, excludeTempApprovers);
		List<LeaveEmployeeData> directReport = new ArrayList<LeaveEmployeeData>();
		directReport.add(empty);
		directReport.addAll(employeeData);
		JSONArray employeeDataJSON = new JSONArray();
		for (int i = 0; i < directReport.size(); i++) {
			employeeDataJSON.add(directReport.get(i).toJSON());
		}

		List<Code> absRsns = this.referenceService.getAbsRsns();
		JSONArray absRsnsJson = new JSONArray();
		for (int i = 0; i < absRsns.size(); i++) {
			absRsnsJson.add(absRsns.get(i).toJSON());
		}
		List<Code> leaveTypes = this.referenceService.getLeaveTypes();
		JSONArray leaveTypesJson = new JSONArray();
		for (int i = 0; i < leaveTypes.size(); i++) {
			leaveTypesJson.add(leaveTypes.get(i).toJSON());
		}
		List<Code> leaveStatus = this.referenceService.getLeaveStatus();
		JSONArray chain = new JSONArray();
		JSONObject currentLevel = new JSONObject();
		currentLevel.put("level", 0);
		currentLevel.put("firstName", demo.getNameF());
		currentLevel.put("lastName", demo.getNameL());
		currentLevel.put("middleName", demo.getNameM());
		currentLevel.put("employeeNumber", demo.getEmpNbr());
		chain.add(currentLevel);
		JSONArray leaves = getLeaveDetails(demo.getEmpNbr());
		List<AppLeaveRequest> leavesCalendar = this.supService.getLeaveDetailsForCalendar(demo.getEmpNbr(), null, null,
				null);
		List<LeaveRequestModel> requestModels = new ArrayList<LeaveRequestModel>();
		LeaveRequestModel model;
		JSONArray calendar = new JSONArray();
		for (int i = 0; i < leavesCalendar.size(); i++) {
			model = new LeaveRequestModel(leavesCalendar.get(i));
			requestModels.add(model);
		}
		for (int i = 0; i < requestModels.size(); i++) {
			calendar.add(requestModels.get(i).toJSON(leaveStatus, null,null));
		}
		mav.addObject("directReportEmployee", employeeDataJSON);
		if (demo.getEmpNbr().equals(empNbr)) {
			mav.addObject("chain", chain);
		}
		mav.addObject("leaves", leaves);
		mav.addObject("absRsns", absRsnsJson);
		mav.addObject("leaveTypes", leaveTypesJson);
		mav.addObject("leavesCalendar", calendar);
		mav.addObject("isEmpty", leaves.size() == 0);
		return mav;
	}

	@RequestMapping("nextLevelFromApproveLeave")
	public ModelAndView nextLevelFromApproveLeave(HttpServletRequest req, String level, String chain,
			String selectEmpNbr) throws ParseException {
		ModelAndView mav = new ModelAndView();
		if(chain==null||selectEmpNbr==null) {
			mav.setViewName("visitFailed");
			mav.addObject("module", module);
			mav.addObject("action", "Next level from approve leave");
			mav.addObject("errorMsg", "Not all mandotary fields provided.");
			return mav;
		}
		mav.setViewName("/supervisor/approveLeaveRequestList");
		JSONArray levels = JSONArray.fromObject(chain);
		Integer currentLevel = levels.size() - 1;
		BhrEmpDemo nextLevelSupervisor = null;
		String currentSupervisorEmployeeNumber = ((JSONObject) levels.get(currentLevel)).getString("employeeNumber");
		if (selectEmpNbr != null && selectEmpNbr.trim().length() > 0
				&& !currentSupervisorEmployeeNumber.equals(selectEmpNbr)) {
			nextLevelSupervisor = this.indexService.getUserDetail(selectEmpNbr);
		}
		if (nextLevelSupervisor != null) {
			int nextSupervisorLevel = currentLevel + 1;
			JSONObject currentLevelDetail = new JSONObject();
			currentLevelDetail.put("level", nextSupervisorLevel);
			currentLevelDetail.put("firstName", nextLevelSupervisor.getNameF());
			currentLevelDetail.put("lastName", nextLevelSupervisor.getNameL());
			currentLevelDetail.put("middleName", nextLevelSupervisor.getNameM());
			currentLevelDetail.put("employeeNumber", nextLevelSupervisor.getEmpNbr());
			levels.add(currentLevelDetail);
			mav = this.getApproveLeaveRequestList(req, nextLevelSupervisor.getEmpNbr());
			mav.addObject("chain", levels);
		} else {
			mav = this.getApproveLeaveRequestList(req, currentSupervisorEmployeeNumber);
			mav.addObject("chain", levels);
		}
		return mav;
	}

	@RequestMapping("previousLevelFromApproveLeave")
	public ModelAndView previousLevelFromApproveLeave(HttpServletRequest req, String level, String chain)
			throws ParseException {
		ModelAndView mav = new ModelAndView();
		if(chain==null) {
			mav.setViewName("visitFailed");
			mav.addObject("module", module);
			mav.addObject("action", "Previous level from approve leave");
			mav.addObject("errorMsg", "Not all mandotary fields provided.");
			return mav;
		}
		mav.setViewName("/supervisor/approveLeaveRequestList");
		JSONArray levels = JSONArray.fromObject(chain);
		Integer prevLevel = levels.size() - 2;
		String empNbr = ((JSONObject) levels.get(prevLevel)).getString("employeeNumber");
		levels.remove(levels.size() - 1);
		mav = this.getApproveLeaveRequestList(req, empNbr);
		mav.addObject("chain", levels);
		return mav;
	}
	
	@RequestMapping("submitRequests")
	public ModelAndView submitRequests(HttpServletRequest req, String level, String chain, String actionList) throws ParseException {
		ModelAndView mav = new ModelAndView();
		if(actionList==null||chain==null) {
			mav.setViewName("visitFailed");
			mav.addObject("module", module);
			mav.addObject("action", "Approve leave");
			mav.addObject("errorMsg", "Not all mandotary fields provided.");
			return mav;
		}
		JSONArray levels = JSONArray.fromObject(chain);
		Integer currentLevel = levels.size() - 1;
		String currentSupervisorEmployeeNumber = ((JSONObject) levels.get(currentLevel)).getString("employeeNumber");
		JSONArray actions = JSONArray.fromObject(actionList);
		JSONObject item = new JSONObject();
		String errorMessage = "";
		for(int i=0;i<actions.size();i++) {
			item = (JSONObject)actions.get(i);
			if(("0").equals(item.getString("actionValue"))) {
				errorMessage += this.disapproveLeave(req, level, chain, item.getString("id"), item.getString("comments"));
			}else if (("1").equals(item.getString("actionValue"))) {
				errorMessage += this.approveLeave(req, level, chain, item.getString("id"), item.getString("comments"));
			}
		}
		mav = this.getApproveLeaveRequestList(req, currentSupervisorEmployeeNumber);
		mav.addObject("chain", levels);
		if(!errorMessage.equals(""))
			mav.addObject("actionErrors", errorMessage);
		return mav;
	}

	@Transactional
	public String approveLeave(HttpServletRequest req, String level, String chain, String id, String comment)
			throws ParseException {
		HttpSession session = req.getSession();
		BhrEmpDemo demo = ((BhrEmpDemo) session.getAttribute("userDetail"));
		LeaveRequest rqst = this.service.getBeaEmpLvRqstById(Integer.parseInt(id));
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		if (rqst.getStatusCd() == 'P') {
			this.supService.approveLeave(rqst, demo, comment);
		} else {
			return "Approve Leave Failed for leave request from " + sdf.format(rqst.getDatetimeFrom())+ " to " + sdf.format(rqst.getDatetimeTo())+".";
		}
		return "";
	}

	@Transactional
	public String disapproveLeave(HttpServletRequest req, String level, String chain, String id, String comment)
			throws ParseException {
		HttpSession session = req.getSession();
		BhrEmpDemo demo = ((BhrEmpDemo) session.getAttribute("userDetail"));
		LeaveRequest rqst = this.service.getBeaEmpLvRqstById(Integer.parseInt(id));
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		if (rqst.getStatusCd() == 'P') {
			this.supService.disApproveLeave(rqst, demo, comment);
		} else {
			return "Disapprove Leave Failed for leave request from " + sdf.format(rqst.getDatetimeFrom())+ " to " + sdf.format(rqst.getDatetimeTo())+".";
		}
		return "";
	}
}
