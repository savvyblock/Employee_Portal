package com.esc20.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.esc20.model.BeaEmpLvTmpApprovers;
import com.esc20.model.BeaUsers;
import com.esc20.model.BhrEmpDemo;
import com.esc20.nonDBModels.AppLeaveRequest;
import com.esc20.nonDBModels.Code;
import com.esc20.nonDBModels.District;
import com.esc20.nonDBModels.LeaveEmployeeData;
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
@RequestMapping("/leaveRequestTemporaryApprovers")
public class TempApproverController extends BaseSupervisorController {

	@Autowired
	private LeaveRequestService service;

	@Autowired
	private SupervisorService supService;
	
	@Autowired
	private ReferenceService referenceService;

	@Autowired
	private IndexService indexService;
	
	private final String module = "Set Temp Approvers";
	
	@RequestMapping("leaveRequestTemporaryApprovers")
	public ModelAndView getLeaveRequestTemporaryApprovers(HttpServletRequest req, String empNbr,String freq) throws ParseException {
		HttpSession session = req.getSession();
		BeaUsers user = (BeaUsers) session.getAttribute("user");
		BhrEmpDemo demo = this.indexService.getUserDetail(user.getEmpNbr());
        String district = (String)session.getAttribute("districtId");
        District districtInfo = this.indexService.getDistrict(district);
        demo.setEmpNbr(user.getEmpNbr());
        demo.setDob(DateUtil.formatDate(demo.getDob(), "yyyyMMdd", "MM-dd-yyyy"));
        List<Code> gens = referenceService.getGenerations();
	 	for(Code gen: gens) {
	    	if(demo.getNameGen() != null && gen.getCode().trim().equals(demo.getNameGen().toString().trim())) {
	    		demo.setGenDescription(gen.getDescription());
	    	}
	    }

        String phone = districtInfo.getPhone();
        districtInfo.setPhone(StringUtil.left(phone, 3)+"-"+StringUtil.mid(phone, 4, 3)+"-"+StringUtil.right(phone, 4));

		 session.setAttribute("userDetail", demo);
         session.setAttribute("companyId", district);
         session.setAttribute("district", districtInfo);
         
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/supervisor/leaveRequestTemporaryApprovers");
		//BhrEmpDemo demo = ((BhrEmpDemo) session.getAttribute("userDetail"));
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
		
		List<Code> leaveStatus = this.referenceService.getLeaveStatus();
		//List<Code> gens = referenceService.getGenerations();
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
			calendar.add(requestModels.get(i).toJSON(leaveStatus, null,null,gens));
		}
        mav.addObject("leavesCalendar", calendar);
        
        List<Code> leaveTypes = new ArrayList<Code>();
   
		//Add a blank for leave Types for default shown
        Code emptyType = new Code();
        emptyType.setDescription(" ");
    	leaveTypes.add(emptyType);
    	List<Code> availableFreqs = this.service.getAvailableFrequencies(demo.getEmpNbr());
    	if (freq == null || ("").equals(freq)) {
			if (availableFreqs.size() > 0) {
				freq = availableFreqs.get(0).getCode();
			}
    	}
        leaveTypes.addAll(this.service.getLeaveTypes(demo.getEmpNbr(), freq, ""));
        
    	JSONArray leaveTypesJson = new JSONArray();
		for (int i = 0; i < leaveTypes.size(); i++) {
			leaveTypesJson.add(leaveTypes.get(i).toJSON());
		}
		
		List<Code> absRsns =  new ArrayList<Code>();
		//Add a blank for absRsns for default shown
		emptyType = new Code();
		emptyType.setDescription(" ");
		absRsns.add(emptyType);
		absRsns.addAll(this.service.getAbsRsns(demo.getEmpNbr(), freq, ""));
		
		JSONArray absRsnsJson = new JSONArray();
		for (int i = 0; i < absRsns.size(); i++) {
			absRsnsJson.add(absRsns.get(i).toJSON());
		}
        
      //  List<Code> testApproves = this.supService.getEmployeeTempApproverSearch(user.getEmpNbr(), "0");
		mav.addObject("absRsns", absRsnsJson);
    	mav.addObject("leaveTypes", leaveTypesJson);
    	mav.addObject("params", params);
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
			mav = this.getLeaveRequestTemporaryApprovers(req, nextLevelSupervisor.getEmpNbr(),"");
			mav.addObject("chain", levels);
		} else {
			mav = this.getLeaveRequestTemporaryApprovers(req, currentSupervisorEmployeeNumber,"");
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
		mav = this.getLeaveRequestTemporaryApprovers(req, empNbr,"");
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
		SimpleDateFormat sdf1 = new SimpleDateFormat("MM-dd-yyyy");
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
		mav = this.getLeaveRequestTemporaryApprovers(req, empNbr,"");
		mav.addObject("chain", levels);
		return mav;
	}

	@RequestMapping(value = "getEmployeeTempApproverSearch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, List<Code>> getEmployeeTempApproverSearch(HttpServletRequest req,String searchStr) throws Exception{
	    	HttpSession session = req.getSession();
	    	Map<String, List<Code>> res = new HashMap<>();
	        BeaUsers user = (BeaUsers)session.getAttribute("user");
	    	BhrEmpDemo demo = this.indexService.getUserDetail(user.getEmpNbr());
	        demo.setEmpNbr(user.getEmpNbr());
	        demo.setDob(DateUtil.formatDate(demo.getDob(), "yyyyMMdd", "MM-dd-yyyy"));
	        List<Code> gens = referenceService.getGenerations();
		 	for(Code gen: gens) {
		    	if(demo.getNameGen() != null && gen.getCode().trim().equals(demo.getNameGen().toString().trim())) {
		    		demo.setGenDescription(gen.getDescription());
		    	}
		    }

			session.setAttribute("userDetail", demo);
	    	if(demo==null)
	    		return null;
	    	List<Code> testApproves = this.supService.getEmployeeTempApproverSearch(user.getEmpNbr(), searchStr);
	        res.put("tempApprover", testApproves);
	        return res;
	    }
}
