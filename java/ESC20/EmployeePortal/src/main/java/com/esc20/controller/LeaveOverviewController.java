package com.esc20.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.weaver.loadtime.Options;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.esc20.model.BeaEmpLvComments;
import com.esc20.model.BeaEmpLvRqst;
import com.esc20.model.BeaUsers;
import com.esc20.model.BhrEmpDemo;
import com.esc20.nonDBModels.AppLeaveRequest;
import com.esc20.nonDBModels.Code;
import com.esc20.nonDBModels.District;
import com.esc20.nonDBModels.LeaveEmployeeData;
import com.esc20.nonDBModels.LeaveInfo;
import com.esc20.nonDBModels.LeaveParameters;
import com.esc20.nonDBModels.LeaveRequestModel;
import com.esc20.service.IndexService;
import com.esc20.service.LeaveRequestService;
import com.esc20.service.ReferenceService;
import com.esc20.service.SupervisorService;
import com.esc20.util.DateUtil;
import com.esc20.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/leaveOverview")
public class LeaveOverviewController extends BaseLeaveRequestController {

	@Autowired
	private LeaveRequestService service;

	@Autowired
	private SupervisorService supService;

	@Autowired
	private IndexService indexService;

	@Autowired
	private ReferenceService referenceService;
	
	private final String module = "Leave Overview";
	
	@RequestMapping("leaveOverviewList")
	public ModelAndView getLeaveOverviewList(HttpServletRequest req, String empNbr, String chain, String freq,
			String startDate, String endDate, Boolean isChangeLevel) throws ParseException {
		HttpSession session = req.getSession();
		
		BeaUsers user = (BeaUsers) session.getAttribute("user");
		BhrEmpDemo userDetail = this.indexService.getUserDetail(user.getEmpNbr());
        String district = (String)session.getAttribute("districtId");
        District districtInfo = this.indexService.getDistrict(district);
        userDetail.setEmpNbr(user.getEmpNbr());
        userDetail.setDob(DateUtil.formatDate(userDetail.getDob(), "yyyyMMdd", "MM-dd-yyyy"));
        List<Code> gens = referenceService.getGenerations();
		 	for(Code gen: gens) {
		    	if(userDetail.getNameGen() != null && gen.getCode().trim().equals(userDetail.getNameGen().toString().trim())) {
		    		userDetail.setGenDescription(gen.getDescription());
		    	}
		    }
        String phone = districtInfo.getPhone();
        districtInfo.setPhone(StringUtil.left(phone, 3)+"-"+StringUtil.mid(phone, 4, 3)+"-"+StringUtil.right(phone, 4));

		 session.setAttribute("userDetail", userDetail);
         session.setAttribute("companyId", district);
         session.setAttribute("district", districtInfo);
         
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/supervisor/approveLeaveRequestList");
		LeaveParameters params = this.service.getLeaveParameters();
		BhrEmpDemo demo = ((BhrEmpDemo) session.getAttribute("userDetail"));
		BhrEmpDemo root = demo;
		boolean supervisorsOnly = false;// false indicates all direct reports are to be returned
		boolean excludeTempApprovers = false;// since supervisorsOnly is false, the value of this field is never checked
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
			List<LeaveEmployeeData> employeeData = this.supService.getDirectReportEmployee(empNbr,params.isUsePMIS() ,
					supervisorsOnly, excludeTempApprovers);
			 Collections.sort(employeeData, new Comparator<LeaveEmployeeData>() {
					@Override
					public int compare(LeaveEmployeeData o1, LeaveEmployeeData o2) {
						String s1 = String.valueOf(o1.getLastName());
		                String s2 = String.valueOf(o2.getLastName());
		                return s1.compareTo(s2);
					}
		    	});
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
			 Collections.sort(employeeData, new Comparator<LeaveEmployeeData>() {
					@Override
					public int compare(LeaveEmployeeData o1, LeaveEmployeeData o2) {
						String s1 = String.valueOf(o1.getLastName());
		                String s2 = String.valueOf(o2.getLastName());
		                return s1.compareTo(s2);
					}
		    	});
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
			if (freq == null || ("").equals(freq)) {
				if(availableFreqs != null && availableFreqs.size() >0) {
					freq = availableFreqs.get(0).getCode();
				   }
				else {
					freq = "0";
				}	
			   }
		}
		SimpleDateFormat sdf1 = new SimpleDateFormat("MM-dd-yyyy");
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
		//List<Code> gens = referenceService.getGenerations();
		List<LeaveInfo> leaveInfo = new ArrayList<LeaveInfo>();
		if(!initialLoad) 
			leaveInfo = this.service.getLeaveInfo(demo.getEmpNbr(), freq, false);
		for (int i = 0; i < requestModels.size(); i++) {
			calendar.add(requestModels.get(i).toJSON(leaveStatus, null,null,gens));
			if(!initialLoad) {
				if (requestModels.get(i).getEmpNbr().equals(demo.getEmpNbr()))
					employee.add(requestModels.get(i).toJSON(leaveStatus, null,null,gens));
			}
		}
		List<Code> absRsns;
		if(!initialLoad) 
			absRsns = this.service.getAbsRsns(demo.getEmpNbr(), freq, "");
		else
			absRsns = this.referenceService.getAbsRsns();
		JSONArray absRsnsJson = new JSONArray();
		for (int i = 0; i < absRsns.size(); i++) {
			absRsnsJson.add(absRsns.get(i).toJSON());
		}
		List<Code> leaveTypes =  new ArrayList<Code>();;
		//Add a blank for leave Types for default shown
		Code emptyType = new Code();
		emptyType = new Code();
		emptyType.setDescription(" ");
		leaveTypes.add(emptyType);
		if(!initialLoad) {
			//leaveTypes = this.service.getLeaveTypes(demo.getEmpNbr(), freq, "");
			leaveTypes.addAll(this.service.getLeaveTypes(demo.getEmpNbr(), freq, ""));
		}
		else {
			leaveTypes.addAll(this.referenceService.getLeaveTypes());
			//leaveTypes = this.referenceService.getLeaveTypes();
		}
		
		JSONArray leaveTypesJson = new JSONArray();
		for (int i = 0; i < leaveTypes.size(); i++) {
			leaveTypesJson.add(leaveTypes.get(i).toJSON());
		}
		List<String[]> map = this.service.mapReasonsAndLeaveTypes();
		JSONArray mapJson = new JSONArray();
		JSONObject tempMap;
		for (int i = 0; i < map.size(); i++) {
			tempMap = new JSONObject();
			tempMap.put("absRsn", map.get(i)[0]);
			for(int j=0;j<absRsns.size();j++) {
				if(absRsns.get(j).getCode().equals(map.get(i)[0])) {
					tempMap.put("absRsnDescrption", absRsns.get(j).getDescription());
				}
			}
			tempMap.put("leaveType", map.get(i)[1]);
			mapJson.add(tempMap);
		}
		mav.addObject("absRsns", absRsnsJson);
		mav.addObject("leaveTypes", leaveTypesJson);
		mav.addObject("leaveTypesAbsrsnsMap", mapJson);
		mav.addObject("directReportEmployee", employeeDataJSON);
		mav.addObject("selectedEmployee", empNbr);
		mav.addObject("availableFreqs", availableFreqs);
		mav.addObject("selectedFreq", freq);
		mav.addObject("startDate", startDate);
		mav.addObject("endDate", endDate);
		mav.addObject("leavesCalendar", calendar);
		mav.addObject("employeeList", employee);
		mav.addObject("leaveInfo", leaveInfo);
		mav.addObject("params", params);
		mav.addObject("isEmpty", employee.size() == 0);
		mav.setViewName("/supervisor/leaveOverviewList");
		return mav;
	}
	
	@RequestMapping("nextLevelFromLeaveOverview")
	public ModelAndView nextLevelFromLeaveOverview(HttpServletRequest req, String level, String chain,
			String selectEmpNbr) throws ParseException {
		ModelAndView mav = new ModelAndView();
		if(chain==null||selectEmpNbr==null) {
			mav.setViewName("visitFailed");
			mav.addObject("module", module);
			mav.addObject("action", "Next level from leave overview");
			mav.addObject("errorMsg", "Not all mandotary fields provided.");
			return mav;
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
		ModelAndView mav = new ModelAndView();
		if(chain==null) {
			mav.setViewName("visitFailed");
			mav.addObject("module", module);
			mav.addObject("action", "Previous level from leave overview");
			mav.addObject("errorMsg", "Not all mandotary fields provided.");
			return mav;
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
		ModelAndView mav = new ModelAndView();
		if(chain==null||leaveType==null||absenseReason==null||LeaveStartDate==null||startTimeValue==null||
				LeaveEndDate==null||endTimeValue==null||lvUnitsDaily==null||lvUnitsUsed==null||empNbr==null||freq==null) {
			mav.setViewName("visitFailed");
			mav.addObject("module", module);
			mav.addObject("action", "Create or update leave information from leave overview");
			mav.addObject("errorMsg", "Not all mandotary fields provided.");
			return mav;
		}
		JSONArray levels = JSONArray.fromObject(chain);
		BeaEmpLvRqst request;
		if (leaveId == null || ("").equals(leaveId)) {
			request = new BeaEmpLvRqst();
		} else
			request = this.service.getleaveRequestById(Integer.parseInt(leaveId + ""));
		SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy hh:mm a", Locale.ENGLISH);
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
		Integer id = this.service.saveLeaveRequest(request, (leaveId != null && !("").equals(leaveId)));
		// Create Comments
		if (Remarks != null && !("").equals(Remarks)) {
			BeaEmpLvComments comments = new BeaEmpLvComments();
			comments.setLvId(id);
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
	
	@RequestMapping("deleteLeaveFromLeaveOverview")
	public ModelAndView deleteLeaveFromLeaveOverview(HttpServletRequest req, String level, String chain, String leaveId,
			String empNbr, String freq, String startDate, String endDate) throws ParseException {
		ModelAndView mav = new ModelAndView();
		if(chain==null||leaveId==null||empNbr==null||freq==null) {
			mav.setViewName("visitFailed");
			mav.addObject("module", module);
			mav.addObject("action", "Delete leave from leave overview");
			mav.addObject("errorMsg", "Not all mandotary fields provided.");
			return mav;
		}
		JSONArray levels = JSONArray.fromObject(chain);
		deleteLeaveRequest(leaveId);
		mav = this.getLeaveOverviewList(req, empNbr, chain, freq, startDate, endDate, false);
		mav.addObject("chain", levels);
		return mav;
	}
}
