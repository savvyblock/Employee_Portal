package com.esc20.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.esc20.model.BeaEmpLvComments;
import com.esc20.model.BeaEmpLvRqst;
import com.esc20.model.BeaEmpLvWorkflow;
import com.esc20.model.BhrEmpDemo;
import com.esc20.nonDBModels.LeaveEmployeeData;
import com.esc20.nonDBModels.LeaveParameters;
import com.esc20.service.LeaveRequestService;
import com.esc20.util.DateUtil;

@Controller
@RequestMapping("/baseLeaveRequest")
public class BaseLeaveRequestController{

	@Autowired
	private LeaveRequestService service;

	protected void saveLeaveRequest(String leaveId, String leaveType, String absenseReason, String LeaveStartDate,
			String startTimeValue, String LeaveEndDate, String endTimeValue, String lvUnitsDaily, String lvUnitsUsed,
			String Remarks, String freq, BhrEmpDemo demo) throws ParseException, MessagingException {
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
		Integer id = this.service.saveLeaveRequest(request, (leaveId != null && !("").equals(leaveId)));
		// Create Comments
		if (Remarks != null && !("").equals(Remarks)) {
			BeaEmpLvComments comments = new BeaEmpLvComments();
			comments.setLvId(id);
			comments.setLvCommentEmpNbr(demo.getEmpNbr());
			comments.setLvCommentDatetime(DateUtil.getUTCTime(new Date()));
			comments.setLvComment(Remarks);
			comments.setLvCommentTyp('C');
			this.service.saveLvComments(comments);
		}
		// Create Workflow upon first creation or modify disapproved leave
		if ((leaveId == null || ("").equals(leaveId)) || ((leaveId != null && !("").equals(leaveId) && isDisapproveUpdate))) {
			LeaveParameters params = this.service.getLeaveParameters();
			LeaveEmployeeData supervisorData = this.service.getFirstLineSupervisor(demo.getEmpNbr(), params.isUsePMIS());
			String supervisorEmpNbr = supervisorData ==null?null:supervisorData.getEmployeeNumber();
			if (!StringUtils.isEmpty(supervisorEmpNbr)) {
				BeaEmpLvWorkflow flow = new BeaEmpLvWorkflow();
				flow.setLvId(id);
				flow.setInsertDatetime(DateUtil.getUTCTime(new Date()));
				flow.setSeqNum(1);
				flow.setApprvrEmpNbr(supervisorEmpNbr == null ? "" : supervisorEmpNbr);
				flow.setTmpApprvrExpDatetime(null);
				//String supervisorEmail = supervisorData ==null?null:supervisorData.getEmailAddress();
				this.service.saveLvWorkflow(flow, demo);
				this.service.sendEmail(request, demo, supervisorData);
			}
		}
	}

	protected void deleteLeaveRequest(String id) {
		BeaEmpLvComments comments = new BeaEmpLvComments();
		comments.setLvId(Integer.parseInt(id));
		this.service.deleteLeaveComments(Integer.parseInt(id));
		this.service.deleteLeaveFlow(Integer.parseInt(id));
		this.service.deleteLeaveRequest(Integer.parseInt(id));
	}
}
