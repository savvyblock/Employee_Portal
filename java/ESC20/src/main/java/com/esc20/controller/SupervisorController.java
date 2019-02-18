package com.esc20.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.esc20.model.BeaEmpLvComments;
import com.esc20.model.BeaEmpLvRqst;
import com.esc20.model.BeaEmpLvTmpApprovers;
import com.esc20.model.BeaEmpLvWorkflow;
import com.esc20.model.BeaUsers;
import com.esc20.model.BhrEmpDemo;
import com.esc20.nonDBModels.AppLeaveRequest;
import com.esc20.nonDBModels.Code;
import com.esc20.nonDBModels.LeaveEmployeeData;
import com.esc20.nonDBModels.LeaveInfo;
import com.esc20.nonDBModels.LeaveParameters;
import com.esc20.nonDBModels.LeaveRequestModel;
import com.esc20.service.IndexService;
import com.esc20.service.LeaveRequestService;
import com.esc20.service.ReferenceService;
import com.esc20.service.SupervisorService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/supervisor")
public class SupervisorController {

	@Autowired
	private LeaveRequestService service;

	@Autowired
	private SupervisorService supService;

	@Autowired
	private IndexService indexService;

	@Autowired
	private ReferenceService referenceService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView getIndexPage(ModelAndView mav) {
		mav.setViewName("index");
		return mav;
	}

	private JSONArray getLeaveDetails(String empNbr) {
		List<AppLeaveRequest> leaves = this.supService.getSupervisorSumittedLeaveRequests(empNbr);
		List<LeaveRequestModel> requestModels = new ArrayList<LeaveRequestModel>();
		List<Code> leaveStatus = this.referenceService.getLeaveStatus();
		LeaveRequestModel model;
		JSONArray json = new JSONArray();
		for (int i = 0; i < leaves.size(); i++) {
			model = new LeaveRequestModel(leaves.get(i));
			requestModels.add(model);
		}
		for (int i = 0; i < requestModels.size(); i++) {
			json.add(requestModels.get(i).toJSON(leaveStatus, null));
		}
		return json;
	}

	@RequestMapping("nextLevelFromApproveLeave")
	public ModelAndView nextLevelFromApproveLeave(HttpServletRequest req, String level, String chain,
			String selectEmpNbr) throws ParseException {
		HttpSession session = req.getSession();
		BeaUsers user = (BeaUsers) session.getAttribute("user");
		ModelAndView mav = new ModelAndView();
		if (null == user) {
			return this.getIndexPage(mav);
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
		HttpSession session = req.getSession();
		BeaUsers user = (BeaUsers) session.getAttribute("user");
		ModelAndView mav = new ModelAndView();
		if (null == user) {
			return this.getIndexPage(mav);
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

	@RequestMapping("approveLeave")
	public ModelAndView approveLeave(HttpServletRequest req, String level, String chain, String id, String comment)
			throws ParseException {
		HttpSession session = req.getSession();
		BeaUsers user = (BeaUsers) session.getAttribute("user");
		ModelAndView mav = new ModelAndView();
		if (null == user) {
			return this.getIndexPage(mav);
		}
		JSONArray levels = JSONArray.fromObject(chain);
		Integer currentLevel = levels.size() - 1;
		String currentSupervisorEmployeeNumber = ((JSONObject) levels.get(currentLevel)).getString("employeeNumber");
		BhrEmpDemo demo = ((BhrEmpDemo) session.getAttribute("userDetail"));
		BeaEmpLvRqst rqst = this.service.getBeaEmpLvRqstById(Integer.parseInt(id));
		if (rqst.getStatusCd() == 'P') {
			this.supService.approveLeave(rqst, demo, comment);
		} else {
			mav = this.getApproveLeaveRequestList(req, currentSupervisorEmployeeNumber);
			mav.addObject("chain", levels);
			mav.addObject("error", "Leave is arealdy under approved status");
			return mav;
		}
		mav = this.getApproveLeaveRequestList(req, currentSupervisorEmployeeNumber);
		mav.addObject("chain", levels);
		return mav;
	}

	@RequestMapping("disapproveLeave")
	public ModelAndView disapproveLeave(HttpServletRequest req, String level, String chain, String id, String comment)
			throws ParseException {
		HttpSession session = req.getSession();
		BeaUsers user = (BeaUsers) session.getAttribute("user");
		ModelAndView mav = new ModelAndView();
		if (null == user) {
			return this.getIndexPage(mav);
		}
		JSONArray levels = JSONArray.fromObject(chain);
		Integer currentLevel = levels.size() - 1;
		String currentSupervisorEmployeeNumber = ((JSONObject) levels.get(currentLevel)).getString("employeeNumber");
		BhrEmpDemo demo = ((BhrEmpDemo) session.getAttribute("userDetail"));
		BeaEmpLvRqst rqst = this.service.getBeaEmpLvRqstById(Integer.parseInt(id));
		if (rqst.getStatusCd() == 'P') {
			this.supService.disApproveLeave(rqst, demo, comment);
		} else {
			mav = this.getApproveLeaveRequestList(req, currentSupervisorEmployeeNumber);
			mav.addObject("chain", levels);
			mav.addObject("error", "Leave is arealdy under approved status");
			return mav;
		}
		mav = this.getApproveLeaveRequestList(req, currentSupervisorEmployeeNumber);
		mav.addObject("chain", levels);
		return mav;
	}

	@RequestMapping("approveLeaveRequestList")
	public ModelAndView getApproveLeaveRequestList(HttpServletRequest req, String empNbr) throws ParseException {
		HttpSession session = req.getSession();
		BeaUsers user = (BeaUsers) session.getAttribute("user");
		ModelAndView mav = new ModelAndView();
		if (null == user) {
			return this.getIndexPage(mav);
		}
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
			calendar.add(requestModels.get(i).toJSON(leaveStatus, null));
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

	@RequestMapping("leaveOverviewList")
	public ModelAndView getLeaveOverviewList(HttpServletRequest req, String empNbr, String chain, String freq,
			String startDate, String endDate, Boolean isChangeLevel) throws ParseException {
		HttpSession session = req.getSession();
		BeaUsers user = (BeaUsers) session.getAttribute("user");
		ModelAndView mav = new ModelAndView();
		if (null == user) {
			return this.getIndexPage(mav);
		}
		mav.setViewName("/supervisor/approveLeaveRequestList");
		LeaveParameters params = this.service.getLeaveParameters();
		BhrEmpDemo demo = ((BhrEmpDemo) session.getAttribute("userDetail"));
		BhrEmpDemo root = demo;
		boolean supervisorsOnly = true;
		boolean excludeTempApprovers = false;
		if (empNbr == null || ("").equals(empNbr)) {
			empNbr = demo.getEmpNbr();
		} else {
			demo = this.indexService.getUserDetail(empNbr);
		}
		JSONArray employeeDataJSON = new JSONArray();
		if ((isChangeLevel != null && isChangeLevel) || (root.getEmpNbr().equals(empNbr))) {
			List<LeaveEmployeeData> employeeData = this.supService.getDirectReportEmployee(empNbr, params.isUsePMIS(),
					supervisorsOnly, excludeTempApprovers);
			LeaveEmployeeData empty = new LeaveEmployeeData();
			List<LeaveEmployeeData> directReport = new ArrayList<LeaveEmployeeData>();
			directReport.add(empty);
			directReport.addAll(employeeData);

			for (int i = 0; i < directReport.size(); i++) {
				employeeDataJSON.add(directReport.get(i).toJSON());
			}
		} else {
			String supervisor = this.service.getFirstLineSupervisor(empNbr, params.isUsePMIS());
			List<LeaveEmployeeData> employeeData = this.supService.getDirectReportEmployee(supervisor,
					params.isUsePMIS(), supervisorsOnly, excludeTempApprovers);
			LeaveEmployeeData empty = new LeaveEmployeeData();
			List<LeaveEmployeeData> directReport = new ArrayList<LeaveEmployeeData>();
			directReport.add(empty);
			directReport.addAll(employeeData);
			for (int i = 0; i < directReport.size(); i++) {
				employeeDataJSON.add(directReport.get(i).toJSON());
			}
		}
		if (chain != null && (isChangeLevel != null && !isChangeLevel)) {
			JSONArray levels = JSONArray.fromObject(chain);
			mav.addObject("chain", levels);
		}
		JSONArray rootLevel = new JSONArray();
		JSONObject currentLevel = new JSONObject();
		currentLevel.put("level", 0);
		currentLevel.put("firstName", demo.getNameF());
		currentLevel.put("lastName", demo.getNameL());
		currentLevel.put("middleName", demo.getNameM());
		currentLevel.put("employeeNumber", demo.getEmpNbr());
		rootLevel.add(currentLevel);
		List<Code> availableFreqs = this.service.getAvailableFrequencies(demo.getEmpNbr());
		if (freq == null || ("").equals(freq))
			freq = availableFreqs.get(0).getCode();
		SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
		String start = null;
		String end = null;
		if (startDate != null && !("").equals(startDate)) {
			start = sdf2.format(sdf1.parse(startDate));
		}
		if (endDate != null && !("").equals(endDate)) {
			end = sdf2.format(sdf1.parse(endDate));
		}
		if (root.getEmpNbr().equals(empNbr)) {
			mav.addObject("chain", rootLevel);
		}
		List<AppLeaveRequest> leavesCalendar = this.supService.getLeaveDetailsForCalendar(demo.getEmpNbr(), freq, start,
				end);

		List<LeaveRequestModel> requestModels = new ArrayList<LeaveRequestModel>();
		LeaveRequestModel model;
		JSONArray calendar = new JSONArray();
		JSONArray employee = new JSONArray();
		for (int i = 0; i < leavesCalendar.size(); i++) {
			model = new LeaveRequestModel(leavesCalendar.get(i));
			requestModels.add(model);
		}
		List<Code> leaveStatus = this.referenceService.getLeaveStatus();
		List<LeaveInfo> leaveInfo = this.service.getLeaveInfo(demo.getEmpNbr(), freq, false);
		for (int i = 0; i < requestModels.size(); i++) {
			calendar.add(requestModels.get(i).toJSON(leaveStatus, null));
			if (requestModels.get(i).getEmpNbr().equals(demo.getEmpNbr()))
				employee.add(requestModels.get(i).toJSON(leaveStatus, null));
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

		mav.addObject("absRsns", absRsnsJson);
		mav.addObject("leaveTypes", leaveTypesJson);
		mav.addObject("directReportEmployee", employeeDataJSON);
		mav.addObject("selectedEmployee", empNbr);
		mav.addObject("availableFreqs", availableFreqs);
		mav.addObject("selectedFreq", freq);
		mav.addObject("startDate", startDate);
		mav.addObject("endDate", endDate);
		mav.addObject("leavesCalendar", calendar);
		mav.addObject("employeeList", employee);
		mav.addObject("leaveInfo", leaveInfo);
		mav.addObject("isEmpty", employee.size() == 0);
		mav.setViewName("/supervisor/leaveOverviewList");
		return mav;
	}

	@RequestMapping("nextLevelFromLeaveOverview")
	public ModelAndView nextLevelFromLeaveOverview(HttpServletRequest req, String level, String chain,
			String selectEmpNbr) throws ParseException {
		HttpSession session = req.getSession();
		BeaUsers user = (BeaUsers) session.getAttribute("user");
		ModelAndView mav = new ModelAndView();
		if (null == user) {
			return this.getIndexPage(mav);
		}
		mav.setViewName("/supervisor/leaveOverviewList");
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
			mav = this.getLeaveOverviewList(req, nextLevelSupervisor.getEmpNbr(), null, null, null, null, true);
			mav.addObject("chain", levels);
		} else {
			mav = this.getLeaveOverviewList(req, currentSupervisorEmployeeNumber, null, null, null, null, true);
			mav.addObject("chain", levels);
		}
		return mav;
	}

	@RequestMapping("previousLevelFromLeaveOverview")
	public ModelAndView previousLevelFromLeaveOverview(HttpServletRequest req, String level, String chain)
			throws ParseException {
		HttpSession session = req.getSession();
		BeaUsers user = (BeaUsers) session.getAttribute("user");
		ModelAndView mav = new ModelAndView();
		if (null == user) {
			return this.getIndexPage(mav);
		}
		mav.setViewName("/supervisor/approveLeaveRequestList");
		JSONArray levels = JSONArray.fromObject(chain);
		Integer prevLevel = levels.size() - 2;
		String empNbr = ((JSONObject) levels.get(prevLevel)).getString("employeeNumber");
		levels.remove(levels.size() - 1);
		mav = this.getLeaveOverviewList(req, empNbr, null, null, null, null, true);
		mav.addObject("chain", levels);
		return mav;
	}

	@RequestMapping("updateLeaveFromLeaveOverview")
	public ModelAndView updateLeaveFromLeaveOverview(HttpServletRequest req, String level, String chain, String leaveId,
			String leaveType, String absenseReason, String LeaveStartDate, String startTimeValue, String LeaveEndDate,
			String endTimeValue, String lvUnitsDaily, String lvUnitsUsed, String Remarks, String empNbr, String freq,
			String startDate, String endDate) throws ParseException {
		HttpSession session = req.getSession();
		BeaUsers user = (BeaUsers) session.getAttribute("user");
		ModelAndView mav = new ModelAndView();
		if (null == user) {
			return this.getIndexPage(mav);
		}
		JSONArray levels = JSONArray.fromObject(chain);
		BhrEmpDemo demo = ((BhrEmpDemo) session.getAttribute("userDetail"));
		BeaEmpLvRqst request;
		if (leaveId == null || ("").equals(leaveId)) {
			request = new BeaEmpLvRqst();
			// if create new leave, route to the current login user's view
			empNbr = demo.getEmpNbr();
		} else
			request = this.service.getleaveRequestById(Integer.parseInt(leaveId + ""));
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a", Locale.ENGLISH);
		request.setEmpNbr(empNbr);
		request.setPayFreq(freq.charAt(0));
		request.setLvTyp(leaveType);
		request.setAbsRsn(absenseReason);
		request.setDatetimeFrom(formatter.parse(LeaveStartDate + " " + startTimeValue));
		request.setDatetimeTo(formatter.parse(LeaveEndDate + " " + endTimeValue));
		request.setLvUnitsDaily(BigDecimal.valueOf(Double.parseDouble(lvUnitsDaily)));
		request.setLvUnitsUsed(BigDecimal.valueOf(Double.parseDouble(lvUnitsUsed)));
		request.setDtOfPay(request.getDtOfPay() == null ? "" : request.getDtOfPay());
		BeaEmpLvRqst res = this.service.saveLeaveRequest(request, (leaveId != null && !("").equals(leaveId)));
		// Create Comments
		if (Remarks != null && !("").equals(Remarks)) {
			BeaEmpLvComments comments = new BeaEmpLvComments();
			comments.setBeaEmpLvRqst(res);
			comments.setLvCommentEmpNbr(empNbr);
			comments.setLvCommentDatetime(new Date());
			comments.setLvComment(Remarks);
			comments.setLvCommentTyp('C');
			this.service.saveLvComments(comments);
		}
		// Create Workflow
		if ((leaveId == null || ("").equals(leaveId))) {
			LeaveParameters params = this.service.getLeaveParameters();
			String supervisorEmpNbr = this.service.getFirstLineSupervisor(empNbr, params.isUsePMIS());
			BeaEmpLvWorkflow flow = new BeaEmpLvWorkflow();
			flow.setBeaEmpLvRqst(res);
			flow.setInsertDatetime(new Date());
			flow.setSeqNum(1);
			flow.setApprvrEmpNbr(supervisorEmpNbr == null ? "" : supervisorEmpNbr);
			flow.setTmpApprvrExpDatetime(null);
			this.service.saveLvWorkflow(flow, demo);
		}
		mav = this.getLeaveOverviewList(req, empNbr, null, freq, startDate, endDate, false);
		mav.addObject("chain", levels);
		return mav;
	}

	@RequestMapping("calendarView")
	public ModelAndView getCalendarView(HttpServletRequest req) throws ParseException {
		HttpSession session = req.getSession();
		BeaUsers user = (BeaUsers) session.getAttribute("user");
		ModelAndView mav = new ModelAndView();
		if (null == user) {
			return this.getIndexPage(mav);
		}
		mav.setViewName("/supervisor/calendar");
		BhrEmpDemo demo = ((BhrEmpDemo) session.getAttribute("userDetail"));
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
			calendar.add(requestModels.get(i).toJSON(leaveStatus, null));
		}
		mav.addObject("absRsns", absRsnsJson);
		mav.addObject("leaveTypes", leaveTypesJson);
		mav.addObject("leavesCalendar", calendar);
		return mav;
	}

	@RequestMapping("leaveRequestTemporaryApprovers")
	public ModelAndView getLeaveRequestTemporaryApprovers(HttpServletRequest req, String empNbr) {
		HttpSession session = req.getSession();
		BeaUsers user = (BeaUsers) session.getAttribute("user");
		ModelAndView mav = new ModelAndView();
		if (null == user) {
			return this.getIndexPage(mav);
		}

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
		HttpSession session = req.getSession();
		BeaUsers user = (BeaUsers) session.getAttribute("user");
		ModelAndView mav = new ModelAndView();
		if (null == user) {
			return this.getIndexPage(mav);
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
		HttpSession session = req.getSession();
		BeaUsers user = (BeaUsers) session.getAttribute("user");
		ModelAndView mav = new ModelAndView();
		if (null == user) {
			return this.getIndexPage(mav);
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
		BeaUsers user = (BeaUsers) session.getAttribute("user");
		ModelAndView mav = new ModelAndView();
		if (null == user) {
			return this.getIndexPage(mav);
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
				// add
				if (temp.getString("id") == null || temp.getString("id").equals("")) {
					isDelete = false;
					tempApprover = new BeaEmpLvTmpApprovers();
					tempApprover.setDatetimeFrom(sdf1.parse(temp.getString("datetimeFrom")));
					tempApprover.setDatetimeTo(sdf1.parse(temp.getString("datetimeTo")));
					tempApprover.setSpvsrEmpNbr(temp.getString("spvsrEmpNbr"));
					tempApprover.setTmpApprvrEmpNbr(temp.getString("tmpApprvrEmpNbr"));
					this.supService.saveTempApprover(tempApprover, false);
					// update
				} else if (Integer.parseInt(temp.getString("id")) == records.get(j).getId()) {
					isDelete = false;
					tempApprover = records.get(j);
					tempApprover.setDatetimeFrom(sdf1.parse(temp.getString("datetimeFrom")));
					tempApprover.setDatetimeTo(sdf1.parse(temp.getString("datetimeTo")));
					tempApprover.setSpvsrEmpNbr(temp.getString("spvsrEmpNbr"));
					tempApprover.setTmpApprvrEmpNbr(temp.getString("tmpApprvrEmpNbr"));
					this.supService.saveTempApprover(tempApprover, true);
				}
			}
			if (isDelete) {
				tempApprover = records.get(j);
				this.supService.deleteTempApprover(tempApprover);
			}
		}
		mav = this.getLeaveRequestTemporaryApprovers(req, empNbr);
		mav.addObject("chain", levels);
		return mav;
	}

	public Object toJSON(BeaEmpLvTmpApprovers record, BhrEmpDemo approver) {
		JSONObject jo = new JSONObject();
		SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy");
		jo.put("id", record.getId());
		jo.put("spvsrEmpNbr", record.getSpvsrEmpNbr());
		jo.put("tmpApprvrEmpNbr", record.getTmpApprvrEmpNbr());
		jo.put("datetimeFrom", sdf1.format(record.getDatetimeFrom()));
		jo.put("datetimeTo", sdf1.format(record.getDatetimeTo()));
		jo.put("approverName", approver.getNameF() + " " + approver.getNameL());
		return jo;
	}
}
