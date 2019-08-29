package com.esc20.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.esc20.model.BeaEmpLvTmpApprovers;
import com.esc20.model.BhrEmpDemo;
import com.esc20.nonDBModels.AppLeaveRequest;
import com.esc20.nonDBModels.Code;
import com.esc20.nonDBModels.LeaveRequestModel;
import com.esc20.service.ReferenceService;
import com.esc20.service.SupervisorService;
import com.esc20.util.DateUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/baseSupervisor")
public class BaseSupervisorController{

	@Autowired
	private SupervisorService supService;

	@Autowired
	private ReferenceService referenceService;

	protected JSONArray getLeaveDetails(String empNbr) {
		List<AppLeaveRequest> leaves = this.supService.getSupervisorSumittedLeaveRequests(empNbr);
		List<LeaveRequestModel> requestModels = new ArrayList<LeaveRequestModel>();
		List<Code> leaveStatus = this.referenceService.getLeaveStatus();
		List<Code> payFreqs = this.referenceService.getPayFreq();
		List<Code> gens = referenceService.getGenerations();
		LeaveRequestModel model;
		JSONArray json = new JSONArray();
		for (int i = 0; i < leaves.size(); i++) {
			model = new LeaveRequestModel(leaves.get(i));
			requestModels.add(model);
		}
		for (int i = 0; i < requestModels.size(); i++) {
			json.add(requestModels.get(i).toJSON(leaveStatus, null,payFreqs,gens));
		}
		return json;
	}

	

	public Object toJSON(BeaEmpLvTmpApprovers record, BhrEmpDemo approver) {
		JSONObject jo = new JSONObject();
		SimpleDateFormat sdf1 = new SimpleDateFormat("MM-dd-yyyy");
		jo.put("id", record.getId());
		jo.put("spvsrEmpNbr", record.getSpvsrEmpNbr());
		jo.put("tmpApprvrEmpNbr", record.getTmpApprvrEmpNbr());
		jo.put("datetimeFrom", sdf1.format(DateUtil.getLocalTime(record.getDatetimeFrom())));
		jo.put("datetimeTo", sdf1.format(DateUtil.getLocalTime(record.getDatetimeTo())));
		jo.put("approverName", approver.getNameL() + ", " + approver.getNameF());
		return jo;
	}
}
