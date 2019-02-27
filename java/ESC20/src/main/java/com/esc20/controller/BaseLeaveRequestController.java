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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.esc20.model.BeaEmpLvComments;
import com.esc20.model.BeaEmpLvRqst;
import com.esc20.model.BeaEmpLvWorkflow;
import com.esc20.model.BeaUsers;
import com.esc20.model.BhrEmpDemo;
import com.esc20.model.BhrEmpLvXmital;
import com.esc20.nonDBModels.AppLeaveRequest;
import com.esc20.nonDBModels.Code;
import com.esc20.nonDBModels.LeaveBalance;
import com.esc20.nonDBModels.LeaveInfo;
import com.esc20.nonDBModels.LeaveParameters;
import com.esc20.nonDBModels.LeaveRequestModel;
import com.esc20.service.LeaveRequestService;
import com.esc20.service.ReferenceService;
import com.esc20.util.DateUtil;

import net.sf.json.JSONArray;

@Controller
@RequestMapping("/baseLeaveRequest")
public class BaseLeaveRequestController extends IndexController {

	@Autowired
	private LeaveRequestService service;

	protected void saveLeaveRequest(String leaveId, String leaveType, String absenseReason, String LeaveStartDate,
			String startTimeValue, String LeaveEndDate, String endTimeValue, String lvUnitsDaily, String lvUnitsUsed,
			String Remarks, String freq, BhrEmpDemo demo) throws ParseException {
		BeaEmpLvRqst request;
		if (leaveId == null || ("").equals(leaveId))
			request = new BeaEmpLvRqst();
		else
			request = this.service.getleaveRequestById(Integer.parseInt(leaveId + ""));
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a", Locale.ENGLISH);
		request.setEmpNbr(demo.getEmpNbr());
		request.setPayFreq(freq.charAt(0));
		request.setLvTyp(leaveType);
		request.setAbsRsn(absenseReason);
		request.setDatetimeFrom(DateUtil.getUTCTime(formatter.parse(LeaveStartDate + " " + startTimeValue)));
		request.setDatetimeTo(DateUtil.getUTCTime(formatter.parse(LeaveEndDate + " " + endTimeValue)));
		request.setDatetimeSubmitted(new Date());
		request.setLvUnitsDaily(BigDecimal.valueOf(Double.parseDouble(lvUnitsDaily)));
		request.setLvUnitsUsed(BigDecimal.valueOf(Double.parseDouble(lvUnitsUsed)));
		Boolean isDisapproveUpdate = false;
		if (leaveId == null || ("").equals(leaveId)) {
			request.setStatusCd('P');
			request.setDtOfPay("");
		} else {
			if('D'==request.getStatusCd()) {
				request.setStatusCd('P');
				isDisapproveUpdate = true;
			}
			request.setDtOfPay(request.getDtOfPay() == null ? "" : request.getDtOfPay());
		}
		BeaEmpLvRqst res = this.service.saveLeaveRequest(request, (leaveId != null && !("").equals(leaveId)));
		// Create Comments
		if (Remarks != null && !("").equals(Remarks)) {
			BeaEmpLvComments comments = new BeaEmpLvComments();
			comments.setBeaEmpLvRqst(res);
			comments.setLvCommentEmpNbr(demo.getEmpNbr());
			comments.setLvCommentDatetime(DateUtil.getUTCTime(new Date()));
			comments.setLvComment(Remarks);
			comments.setLvCommentTyp('C');
			this.service.saveLvComments(comments);
		}
		// Create Workflow upon first creation or modify disapproved leave
		if ((leaveId == null || ("").equals(leaveId)) || ((leaveId != null && !("").equals(leaveId) && isDisapproveUpdate))) {
			LeaveParameters params = this.service.getLeaveParameters();
			String supervisorEmpNbr = this.service.getFirstLineSupervisor(demo.getEmpNbr(), params.isUsePMIS());
			if (!StringUtils.isEmpty(supervisorEmpNbr)) {
				BeaEmpLvWorkflow flow = new BeaEmpLvWorkflow();
				flow.setBeaEmpLvRqst(res);
				flow.setInsertDatetime(DateUtil.getUTCTime(new Date()));
				flow.setSeqNum(1);
				flow.setApprvrEmpNbr(supervisorEmpNbr == null ? "" : supervisorEmpNbr);
				flow.setTmpApprvrExpDatetime(null);
				this.service.saveLvWorkflow(flow, demo);
			}
		}
	}

	protected void deleteLeaveRequest(String id) {
		BeaEmpLvRqst request = new BeaEmpLvRqst();
		request = this.service.getBeaEmpLvRqstById(Integer.parseInt(id));
		BeaEmpLvComments comments = new BeaEmpLvComments();
		comments.setBeaEmpLvRqst(request);
		this.service.deleteLeaveComments(comments);
		BeaEmpLvWorkflow flow = new BeaEmpLvWorkflow();
		flow.setBeaEmpLvRqst(request);
		this.service.deleteLeaveFlow(flow);
		this.service.deleteLeaveRequest(request);
	}
}
