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

import com.esc20.model.BeaEmpLvTmpApprovers;
import com.esc20.model.BeaUsers;
import com.esc20.model.BhrEmpDemo;
import com.esc20.nonDBModels.LeaveEmployeeData;
import com.esc20.nonDBModels.LeaveParameters;
import com.esc20.service.IndexService;
import com.esc20.service.LeaveRequestService;
import com.esc20.service.ReferenceService;
import com.esc20.service.SupervisorService;
import com.esc20.util.DateUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/leaveRequestTemporaryApprovers")
public class TempApproverController extends BaseSupervisorController {

	@Autowired
	private LeaveRequestService service;

	@Autowired
	private SupervisorService supService;

	@Autowired
	private IndexService indexService;
	
	private final String module = "Set Temp Approvers";
	
	@RequestMapping("leaveRequestTemporaryApprovers")
	public ModelAndView getLeaveRequestTemporaryApprovers(HttpServletRequest req, String empNbr) {
		HttpSession session = req.getSession();
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/supervisor/leaveRequestTemporaryApprovers");
		BhrEmpDemo demo = ((BhrEmpDemo) session.getAttribute("userDetail"));
		LeaveParameters params = this.service.getLeaveParameters();
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
		List<BeaEmpLvTmpApprovers> records = this.supService.getBeaEmpLvTmpApprovers(empNbr);
		BhrEmpDemo employee;
		JSONArray tmpApprovers = new JSONArray();
		for (int i = 0; i < records.size(); i++) {
			employee = this.indexService.getUserDetail(records.get(i).getTmpApprvrEmpNbr());
			tmpApprovers.add(toJSON(records.get(i), employee));
		}
		JSONArray chain = new JSONArray();
		JSONObject currentLevel = new JSONObject();
		currentLevel.put("level", 0);
		currentLevel.put("firstName", demo.getNameF());
		currentLevel.put("lastName", demo.getNameL());
		currentLevel.put("middleName", demo.getNameM());
		currentLevel.put("employeeNumber", demo.getEmpNbr());
		chain.add(currentLevel);
		if (demo.getEmpNbr().equals(empNbr)) {
			mav.addObject("chain", chain);
		}
		mav.addObject("tmpApprovers", tmpApprovers);
		mav.addObject("directReportEmployee", employeeDataJSON);
		return mav;
	}

	@RequestMapping("nextLevelFromTempApprovers")
	public ModelAndView nextLevelFromTempApprovers(HttpServletRequest req, String level, String chain,
			String selectEmpNbr) throws ParseException {
		ModelAndView mav = new ModelAndView();
		if(chain==null||selectEmpNbr==null) {
			mav.setViewName("visitFailed");
			mav.addObject("module", module);
			mav.addObject("action", "Next level from set temp approvers");
			mav.addObject("errorMsg", "Not all mandotary fields provided.");
			return mav;
		}
		mav.setViewName("/supervisor/leaveRequestTemporaryApprovers");
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
			mav = this.getLeaveRequestTemporaryApprovers(req, nextLevelSupervisor.getEmpNbr());
			mav.addObject("chain", levels);
		} else {
			mav = this.getLeaveRequestTemporaryApprovers(req, currentSupervisorEmployeeNumber);
			mav.addObject("chain", levels);
		}
		return mav;
	}

	@RequestMapping("previousLevelFromTempApprovers")
	public ModelAndView previousLevelFromTempApprovers(HttpServletRequest req, String level, String chain)
			throws ParseException {
		ModelAndView mav = new ModelAndView();
		if(chain==null) {
			mav.setViewName("visitFailed");
			mav.addObject("module", module);
			mav.addObject("action", "Previous level from set temp approvers");
			mav.addObject("errorMsg", "Not all mandotary fields provided.");
			return mav;
		}
		mav.setViewName("/supervisor/approveLeaveRequestList");
		JSONArray levels = JSONArray.fromObject(chain);
		Integer prevLevel = levels.size() - 2;
		String empNbr = ((JSONObject) levels.get(prevLevel)).getString("employeeNumber");
		levels.remove(levels.size() - 1);
		mav = this.getLeaveRequestTemporaryApprovers(req, empNbr);
		mav.addObject("chain", levels);
		return mav;
	}

	@RequestMapping("saveTempApprovers")
	public ModelAndView saveTempApprovers(HttpServletRequest req, String level, String chain, String empNbr,
			String approverJson) throws ParseException {
		HttpSession session = req.getSession();
		ModelAndView mav = new ModelAndView();
		if(chain==null||approverJson==null) {
			mav.setViewName("visitFailed");
			mav.addObject("module", module);
			mav.addObject("action", "Save temp approvers");
			mav.addObject("errorMsg", "Not all mandotary fields provided.");
			return mav;
		}
		mav.setViewName("/supervisor/approveLeaveRequestList");
		JSONArray levels = JSONArray.fromObject(chain);
		BhrEmpDemo demo = ((BhrEmpDemo) session.getAttribute("userDetail"));
		if (empNbr == null || ("").equals(empNbr)) {
			empNbr = demo.getEmpNbr();
		} else {
			demo = this.indexService.getUserDetail(empNbr);
		}
		List<BeaEmpLvTmpApprovers> records = this.supService.getBeaEmpLvTmpApprovers(empNbr);
		JSONArray inputs = JSONArray.fromObject(approverJson);
		BeaEmpLvTmpApprovers tempApprover;
		JSONObject temp;
		SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy");
		boolean isDelete = true;
		for (int j = 0; j < records.size(); j++) {
			isDelete = true;
			for (int i = 0; i < inputs.size(); i++) {
				temp = ((JSONObject) inputs.get(i));
				if (temp.getString("id") != null && !("").equals(temp.getString("id")) && Integer.parseInt(temp.getString("id")) == records.get(j).getId()) {
					isDelete = false;
				}
			}
			if (isDelete) {
				tempApprover = records.get(j);
				this.supService.deleteTempApprover(tempApprover);
			}
		}
		for (int i = 0; i < inputs.size(); i++) {
			temp = ((JSONObject) inputs.get(i));
			tempApprover = new BeaEmpLvTmpApprovers();
			tempApprover.setDatetimeFrom(DateUtil.getUTCTime(sdf1.parse(temp.getString("from"))));
			tempApprover.setDatetimeTo(DateUtil.getUTCTime(sdf1.parse(temp.getString("to"))));
			tempApprover.setSpvsrEmpNbr(empNbr);
			tempApprover.setTmpApprvrEmpNbr(temp.getString("empNbr"));
			this.supService.saveTempApprover(tempApprover, !(temp.getString("id") == null || temp.getString("id").equals("")));
		}
		mav = this.getLeaveRequestTemporaryApprovers(req, empNbr);
		mav.addObject("chain", levels);
		return mav;
	}
}
