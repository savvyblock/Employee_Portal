package com.esc20.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.esc20.model.BeaUsers;
import com.esc20.model.BhrEmpDemo;
import com.esc20.nonDBModels.AppLeaveRequest;
import com.esc20.nonDBModels.Code;
import com.esc20.nonDBModels.District;
import com.esc20.nonDBModels.LeaveInfo;
import com.esc20.nonDBModels.LeaveParameters;
import com.esc20.nonDBModels.LeaveRequestModel;
import com.esc20.nonDBModels.Options;
import com.esc20.service.IndexService;
import com.esc20.service.LeaveRequestService;
import com.esc20.service.ReferenceService;
import com.esc20.util.DateUtil;
import com.esc20.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/leaveRequestCalendar")
public class LeaveRequestCalendarController extends BaseLeaveRequestController {

	@Autowired
	private LeaveRequestService service;

	@Autowired
	private IndexService indexService;

	@Autowired
	private ReferenceService referenceService;

	private final String module = "Leave Request Calendar View";

	@RequestMapping("eventCalendar")
	public ModelAndView getEventCalendar(HttpServletRequest req, String freq, Boolean isAdd) {
		HttpSession session = req.getSession();

		BeaUsers user = (BeaUsers) session.getAttribute("user");
		BhrEmpDemo demo = this.indexService.getUserDetail(user.getEmpNbr());

		String district = (String) session.getAttribute("srvcId");
		District districtInfo = this.indexService.getDistrict(district);
		demo.setEmpNbr(user.getEmpNbr());
		demo.setDob(DateUtil.formatDate(demo.getDob(), "yyyyMMdd", "MM-dd-yyyy"));
		List<Code> gens = referenceService.getGenerations();
		for (Code gen : gens) {
			if (demo.getNameGen() != null && gen.getCode().trim().equals(demo.getNameGen().toString().trim())) {
				demo.setGenDescription(gen.getDescription());
			}
		}
		String phone = districtInfo.getPhone();
		districtInfo.setPhone(
				StringUtil.left(phone, 3) + "-" + StringUtil.mid(phone, 4, 3) + "-" + StringUtil.right(phone, 4));

		session.setAttribute("userDetail", demo);
		session.setAttribute("companyId", district);
		session.setAttribute("district", districtInfo);
		Options options = this.indexService.getOptions();

		Boolean isSupervisor = this.indexService.isSupervisor(user.getEmpNbr(), options.getUsePMISSpvsrLevels());
		Boolean isTempApprover = this.indexService.isTempApprover(user.getEmpNbr());

		session.setAttribute("isSupervisor", isSupervisor);
		session.setAttribute("isTempApprover", isTempApprover);

		ModelAndView mav = new ModelAndView();
		mav.setViewName("/leaveRequest/fullCalendar");
		if (isAdd == null) {
			isAdd = false;
		}
		if (isAdd) {
			mav.addObject("addRow", true);
		} else {
			mav.addObject("addRow", false);
		}
		AppLeaveRequest request = new AppLeaveRequest();
		// BhrEmpDemo demo = ((BhrEmpDemo) session.getAttribute("userDetail"));
		List<Code> availableFreqs = this.service.getAvailableFrequencies(demo.getEmpNbr());
		mav.addObject("availableFreqs", availableFreqs);
		LeaveParameters params = this.service.getLeaveParameters();
		mav.addObject("params", params);
		String supervisorEmpNbr = this.service.getFirstLineSupervisor(demo.getEmpNbr(), params.isUsePMIS()) == null
				? null
				: this.service.getFirstLineSupervisor(demo.getEmpNbr(), params.isUsePMIS()).getEmployeeNumber();
		if (supervisorEmpNbr == null) {
			supervisorEmpNbr = "";
			mav.addObject("haveSupervisor", false);
		} else {
			mav.addObject("haveSupervisor", true);
		}
		List<AppLeaveRequest> requests = new ArrayList<AppLeaveRequest>();
		if (freq == null || ("").equals(freq)) {
			if (availableFreqs.size() > 0) {
				freq = availableFreqs.get(0).getCode();
				requests = this.service.getLeaveRequests(request, demo.getEmpNbr(), freq);
				List<LeaveRequestModel> requestModels = new ArrayList<LeaveRequestModel>();
				List<Code> leaveStatus = this.referenceService.getLeaveStatus();
				// List<Code> gens = referenceService.getGenerations();
				LeaveRequestModel model;
				JSONArray json = new JSONArray();
				AppLeaveRequest temp;
				for (int i = 0; i < requests.size(); i++) {
					temp = requests.get(i);
					temp.setFirstName(demo.getNameF());
					temp.setLastName(demo.getNameL());
					model = new LeaveRequestModel(temp);
					requestModels.add(model);
				}
				List<LeaveInfo> leaveInfo = this.service.getLeaveInfo(demo.getEmpNbr(), freq, false);
				Code emptyCode = new Code();
				// List<Code> absRsns = this.service.getAbsRsns(demo.getEmpNbr(), freq, "");
				List<Code> absRsns = new ArrayList<Code>();
				// Add a blank for absRsns for default shown
				emptyCode = new Code();
				emptyCode.setDescription(" ");
				absRsns.add(emptyCode);
				absRsns.addAll(this.service.getAbsRsns(demo.getEmpNbr(), freq, ""));

				JSONArray absRsnsJson = new JSONArray();
				for (int i = 0; i < absRsns.size(); i++) {
					absRsnsJson.add(absRsns.get(i).toJSON());
				}
				List<Code> leaveTypes = new ArrayList<Code>();

				// Add a blank for leave Types for default shown
				emptyCode = new Code();
				emptyCode.setDescription(" ");
				leaveTypes.add(emptyCode);
				leaveTypes.addAll(this.service.getLeaveTypes(demo.getEmpNbr(), freq, ""));
				JSONArray leaveTypesJson = new JSONArray();
				for (int i = 0; i < leaveTypes.size(); i++) {
					leaveTypesJson.add(leaveTypes.get(i).toJSON());
				}
				for (int i = 0; i < requestModels.size(); i++) {
					json.add(requestModels.get(i).toJSON(leaveStatus, leaveTypes, null, gens));
				}

				List<String[]> map = this.service.mapReasonsAndLeaveTypes();
				JSONArray mapJson = new JSONArray();
				JSONObject tempMap;
				for (int i = 0; i < map.size(); i++) {
					tempMap = new JSONObject();
					tempMap.put("absRsn", map.get(i)[0]);
					for (int j = 0; j < absRsns.size(); j++) {
						if (absRsns.get(j).getCode().equals(map.get(i)[0])) {
							tempMap.put("absRsnDescrption", absRsns.get(j).getDescription());
						}
					}
					tempMap.put("leaveType", map.get(i)[1]);
					mapJson.add(tempMap);
				}
				mav.addObject("selectedFreq", freq);
				mav.addObject("absRsns", absRsnsJson);
				mav.addObject("leaveTypes", leaveTypesJson);
				mav.addObject("leaveTypesAbsrsnsMap", mapJson);
				mav.addObject("leaveInfo", leaveInfo);
				mav.addObject("leaves", json);
			}
		} else {
			requests = this.service.getLeaveRequests(request, demo.getEmpNbr(), freq);
			List<LeaveRequestModel> requestModels = new ArrayList<LeaveRequestModel>();
			List<Code> leaveStatus = this.referenceService.getLeaveStatus();
			// List<Code> gens = referenceService.getGenerations();
			LeaveRequestModel model;
			JSONArray json = new JSONArray();
			AppLeaveRequest temp;
			for (int i = 0; i < requests.size(); i++) {
				temp = requests.get(i);
				temp.setFirstName(demo.getNameF());
				temp.setLastName(demo.getNameL());
				model = new LeaveRequestModel(temp);
				requestModels.add(model);
			}
			List<LeaveInfo> leaveInfo = this.service.getLeaveInfo(demo.getEmpNbr(), freq, false);
			Code emptyCode = new Code();
			// List<Code> absRsns = this.service.getAbsRsns(demo.getEmpNbr(), freq, "");
			List<Code> absRsns = new ArrayList<Code>();
			// Add a blank for absRsns for default shown
			emptyCode = new Code();
			emptyCode.setDescription(" ");
			absRsns.add(emptyCode);
			absRsns.addAll(this.service.getAbsRsns(demo.getEmpNbr(), freq, ""));
			JSONArray absRsnsJson = new JSONArray();
			for (int i = 0; i < absRsns.size(); i++) {
				absRsnsJson.add(absRsns.get(i).toJSON());
			}
			List<Code> leaveTypes = new ArrayList<Code>();

			// Add a blank for leave Types for default shown
			emptyCode = new Code();
			emptyCode.setDescription(" ");
			leaveTypes.add(emptyCode);
			leaveTypes.addAll(this.service.getLeaveTypes(demo.getEmpNbr(), freq, ""));
			JSONArray leaveTypesJson = new JSONArray();
			for (int i = 0; i < leaveTypes.size(); i++) {
				leaveTypesJson.add(leaveTypes.get(i).toJSON());
			}
			for (int i = 0; i < requestModels.size(); i++) {
				json.add(requestModels.get(i).toJSON(leaveStatus, leaveTypes, null, gens));
			}
			List<String[]> map = this.service.mapReasonsAndLeaveTypes();
			JSONArray mapJson = new JSONArray();
			JSONObject tempMap;
			for (int i = 0; i < map.size(); i++) {
				tempMap = new JSONObject();
				tempMap.put("absRsn", map.get(i)[0]);
				for (int j = 0; j < absRsns.size(); j++) {
					if (absRsns.get(j).getCode().equals(map.get(i)[0])) {
						tempMap.put("absRsnDescrption", absRsns.get(j).getDescription());
					}
				}
				tempMap.put("leaveType", map.get(i)[1]);
				mapJson.add(tempMap);
			}
			mav.addObject("selectedFreq", freq);
			mav.addObject("absRsns", absRsnsJson);
			mav.addObject("leaveTypes", leaveTypesJson);
			mav.addObject("leaveTypesAbsrsnsMap", mapJson);
			mav.addObject("leaveInfo", leaveInfo);
			mav.addObject("leaves", json);
		}
		return mav;
	}

	@RequestMapping("submitLeaveRequestFromCalendar")
	public ModelAndView submitLeaveRequestFromCalendar(HttpServletRequest req, String leaveId, String leaveType,
			String absenseReason, String LeaveStartDate, String startTimeValue, String LeaveEndDate,
			String endTimeValue, String lvUnitsDaily, String lvUnitsUsed, String Remarks, String freq, Boolean isAdd,
			Long token) throws ParseException, MessagingException {
		HttpSession session = req.getSession();
		BhrEmpDemo demo = ((BhrEmpDemo) session.getAttribute("userDetail"));
		ModelAndView mav = new ModelAndView();
		if (leaveType == null || absenseReason == null || LeaveStartDate == null || startTimeValue == null
				|| LeaveEndDate == null || endTimeValue == null || lvUnitsDaily == null || lvUnitsUsed == null
				|| freq == null) {
			mav.setViewName("visitFailed");
			mav.addObject("module", module);
			mav.addObject("action", "Create or update leave information from leave request calendar view");
			mav.addObject("errorMsg", "Not all mandotary fields provided.");
			return mav;
		}
		Long sessionToken = (Long) session.getAttribute("token");
		if (!sessionToken.equals(token)) {
			return this.getEventCalendar(req, freq, isAdd);
		}
		this.saveLeaveRequest(leaveId, leaveType, absenseReason, LeaveStartDate, startTimeValue, LeaveEndDate,
				endTimeValue, lvUnitsDaily, lvUnitsUsed, Remarks, freq, demo);
		return this.getEventCalendar(req, freq, isAdd);
	}

	@RequestMapping("deleteLeaveRequestFromCalendar")
	public ModelAndView deleteLeaveRequestFromCalendar(HttpServletRequest req, String id, String freq, Long token) {
		ModelAndView mav = new ModelAndView();
		HttpSession session = req.getSession();
		if (id == null) {
			mav.setViewName("visitFailed");
			mav.addObject("module", module);
			mav.addObject("action", "Delete leave information from leave overview");
			mav.addObject("errorMsg", "Not all mandotary fields provided.");
			return mav;
		}
		Long sessionToken = (Long) session.getAttribute("token");
		if (!sessionToken.equals(token)) {
			return this.getEventCalendar(req, freq, false);
		}
		deleteLeaveRequest(id);
		return this.getEventCalendar(req, freq, false);
	}
}
