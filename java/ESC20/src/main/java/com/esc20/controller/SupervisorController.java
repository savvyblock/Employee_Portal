package com.esc20.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
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
import com.esc20.util.DateUtil;

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
		boolean initialLoad = false;
		if (empNbr == null || ("").equals(empNbr)) {
			empNbr = demo.getEmpNbr();
			initialLoad = true;
		} else {
			demo = this.indexService.getUserDetail(empNbr);
		}
		if(isChangeLevel != null && isChangeLevel) {
			initialLoad = true;
		}
		JSONArray employeeDataJSON = new JSONArray();
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
		if (root.getEmpNbr().equals(empNbr)) {
			mav.addObject("chain", rootLevel);
		}
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
			String selectedEmp = "";
			if(chain != null) {
				JSONArray levels = JSONArray.fromObject(chain);
				selectedEmp = ((JSONObject) levels.get(levels.size()-1)).getString("employeeNumber");
			}else {
				selectedEmp = currentLevel.getString("employeeNumber");
			}
			List<LeaveEmployeeData> employeeData = this.supService.getDirectReportEmployee(selectedEmp,
					params.isUsePMIS(), supervisorsOnly, excludeTempApprovers);
			LeaveEmployeeData empty = new LeaveEmployeeData();
			List<LeaveEmployeeData> directReport = new ArrayList<LeaveEmployeeData>();
			directReport.add(empty);
			directReport.addAll(employeeData);
			for (int i = 0; i < directReport.size(); i++) {
				employeeDataJSON.add(directReport.get(i).toJSON());
			}
		}
		List<Code> availableFreqs = new ArrayList<Code>();
		if(!initialLoad) {
			availableFreqs = this.service.getAvailableFrequencies(demo.getEmpNbr());
			if (freq == null || ("").equals(freq))
				freq = availableFreqs.get(0).getCode();
		}
		SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
		String start = null;
		String end = null;
		if (startDate != null && !("").equals(startDate)) {
			start = sdf2.format(sdf1.parse(startDate));
		}
		Calendar cal = Calendar.getInstance();
		if (endDate != null && !("").equals(endDate)) {
			cal.setTime(sdf1.parse(endDate));
			cal.add(Calendar.DATE, 1);
			end = sdf2.format(cal.getTime());
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
		List<LeaveInfo> leaveInfo = new ArrayList<LeaveInfo>();
		if(!initialLoad) 
			leaveInfo = this.service.getLeaveInfo(demo.getEmpNbr(), freq, false);
		for (int i = 0; i < requestModels.size(); i++) {
			calendar.add(requestModels.get(i).toJSON(leaveStatus, null));
			if(!initialLoad) {
				if (requestModels.get(i).getEmpNbr().equals(demo.getEmpNbr()))
					employee.add(requestModels.get(i).toJSON(leaveStatus, null));
			}
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
		} else
			request = this.service.getleaveRequestById(Integer.parseInt(leaveId + ""));
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a", Locale.ENGLISH);
		if (leaveId == null || ("").equals(leaveId)) {
			request.setEmpNbr(empNbr);
		}
		request.setPayFreq(freq.charAt(0));
		request.setLvTyp(leaveType);
		request.setAbsRsn(absenseReason);
		request.setDatetimeSubmitted(DateUtil.getUTCTime());
		request.setDatetimeFrom(DateUtil.getUTCTime(formatter.parse(LeaveStartDate + " " + startTimeValue)));
		request.setDatetimeTo(DateUtil.getUTCTime(formatter.parse(LeaveEndDate + " " + endTimeValue)));
		request.setLvUnitsDaily(BigDecimal.valueOf(Double.parseDouble(lvUnitsDaily)));
		request.setLvUnitsUsed(BigDecimal.valueOf(Double.parseDouble(lvUnitsUsed)));
		request.setDtOfPay(request.getDtOfPay() == null ? "" : request.getDtOfPay());
		request.setStatusCd('A');
		BeaEmpLvRqst res = this.service.saveLeaveRequest(request, (leaveId != null && !("").equals(leaveId)));
		// Create Comments
		if (Remarks != null && !("").equals(Remarks)) {
			BeaEmpLvComments comments = new BeaEmpLvComments();
			comments.setBeaEmpLvRqst(res);
			comments.setLvCommentEmpNbr(empNbr);
			comments.setLvCommentDatetime(DateUtil.getUTCTime());
			comments.setLvComment(Remarks);
			comments.setLvCommentTyp('C');
			this.service.saveLvComments(comments);
		}
		mav = this.getLeaveOverviewList(req, empNbr, chain, freq, startDate, endDate, false);
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

	public Object toJSON(BeaEmpLvTmpApprovers record, BhrEmpDemo approver) {
		JSONObject jo = new JSONObject();
		SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy");
		jo.put("id", record.getId());
		jo.put("spvsrEmpNbr", record.getSpvsrEmpNbr());
		jo.put("tmpApprvrEmpNbr", record.getTmpApprvrEmpNbr());
		jo.put("datetimeFrom", sdf1.format(DateUtil.getLocalTime(record.getDatetimeFrom())));
		jo.put("datetimeTo", sdf1.format(DateUtil.getLocalTime(record.getDatetimeTo())));
		jo.put("approverName", approver.getNameF() + " " + approver.getNameL());
		return jo;
	}
}
