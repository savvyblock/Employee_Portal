package com.esc20.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esc20.dao.AlertDao;
import com.esc20.dao.LeaveRequestDao;
import com.esc20.model.BeaEmpLvComments;
import com.esc20.model.BeaEmpLvRqst;
import com.esc20.model.BeaEmpLvWorkflow;
import com.esc20.model.BhrEmpDemo;
import com.esc20.model.BhrEmpLvXmital;
import com.esc20.model.BhrPmisPosCtrl;
import com.esc20.nonDBModels.AppLeaveRequest;
import com.esc20.nonDBModels.Code;
import com.esc20.nonDBModels.LeaveBalance;
import com.esc20.nonDBModels.LeaveEmployeeData;
import com.esc20.nonDBModels.LeaveInfo;
import com.esc20.nonDBModels.LeaveParameters;
import com.esc20.nonDBModels.LeaveRequest;
import com.esc20.nonDBModels.LeaveUnitsConversion;
import com.esc20.nonDBModels.Options;
import com.esc20.util.DateUtil;
import com.esc20.util.MailUtil;
import com.esc20.util.StringUtil;

@Service
public class LeaveRequestService {

	@Autowired
	private LeaveRequestDao leaveRequestDao;
	@Autowired
	private AlertDao alertDao;
	
	@Autowired
	private IndexService indexService;

	public BeaEmpLvRqst getleaveRequestById(int id) {
		return leaveRequestDao.getleaveRequestById(id);
	}

	public Integer saveLeaveRequest(BeaEmpLvRqst request, boolean isUpdate) {
		return leaveRequestDao.saveLeaveRequest(request, isUpdate);
	}

	public void deleteLeaveRequest(Integer lvId) {
		leaveRequestDao.DeleteLeaveRequest(lvId);
	}

	public List<AppLeaveRequest> getLeaveRequests(AppLeaveRequest request, String empNbr, String freq) {
		List<AppLeaveRequest> leaves = leaveRequestDao.getLeaveRequests(request, empNbr, freq);
		for (int i = 0; i < leaves.size(); i++) {
			leaves.get(i).setComments(leaveRequestDao.getLeaveComments(leaves.get(i).getId()));
		}
		return leaves;
	}

	public LeaveParameters getLeaveParameters() {
		return leaveRequestDao.getLeaveParameters();
	}

	public List<Code> getAvailableFrequencies(String empNbr) {
		return leaveRequestDao.getAvailableFrequencies(empNbr);
	}

	public LeaveEmployeeData getFirstLineSupervisor(String empNbr, boolean usePMIS) {
		LeaveEmployeeData firstLineSupervisor = null;
		String directReportEmployeeNumber = empNbr;

		while (firstLineSupervisor == null && directReportEmployeeNumber != null) {
			BhrPmisPosCtrl employeeSupervisorPMISData = null;
			if (usePMIS) {
				employeeSupervisorPMISData = leaveRequestDao.getEmployeeSupervisorPMISData(directReportEmployeeNumber);
				if (employeeSupervisorPMISData == null) {
					return null;
				}
				firstLineSupervisor = leaveRequestDao.getPMISFirstLineSupervisor(
						employeeSupervisorPMISData.getSpvsrBilletNbr(), employeeSupervisorPMISData.getSpvsrPosNbr(),
						true);
			} else {
				firstLineSupervisor = leaveRequestDao.getFirstLineSupervisor(directReportEmployeeNumber, true);
			}
			if (firstLineSupervisor == null) {
				return null;
			} else if (empNbr.equals(firstLineSupervisor.getEmployeeNumber())) {
				firstLineSupervisor = null;
				if (usePMIS) {
					firstLineSupervisor = leaveRequestDao.getPMISFirstLineSupervisor(
							employeeSupervisorPMISData.getSpvsrBilletNbr(), employeeSupervisorPMISData.getSpvsrPosNbr(),
							false);
				} else {
					firstLineSupervisor = leaveRequestDao.getFirstLineSupervisor(directReportEmployeeNumber, false);
				}
				if (firstLineSupervisor != null) {
					directReportEmployeeNumber = firstLineSupervisor.getEmployeeNumber();
					firstLineSupervisor = null;
				} else {
					directReportEmployeeNumber = null;
				}
			}
		}
		return firstLineSupervisor;
	}

	public List<Code> getAbsRsns(String empNbr, String freq, String leaveType) {
		return leaveRequestDao.getAbsenceReasons(empNbr, freq, leaveType);
	}

	public List<Code> getLeaveTypes(String empNbr, String freq, String leaveType) {
		return leaveRequestDao.getLeaveTypes(empNbr, freq, leaveType);
	}

	public List<LeaveInfo> getLeaveInfo(String empNbr, String freq, boolean removeZeroedOutLeaveTypes) {
		List<LeaveInfo> leaveInfos = leaveRequestDao.getLeaveInfo(empNbr, freq);

		if (removeZeroedOutLeaveTypes) {
			for (int i = 0; i < leaveInfos.size(); i++) {
				if (leaveInfos.get(i).getBeginBalance().doubleValue() == 0
						&& leaveInfos.get(i).getAdvancedEarned().doubleValue() == 0
						&& leaveInfos.get(i).getUsed().doubleValue() == 0
						&& leaveInfos.get(i).getPendingUsed().doubleValue() == 0
						&& leaveInfos.get(i).getPendingPayroll().doubleValue() == 0
						&& leaveInfos.get(i).getPendingEarned().doubleValue() == 0
						&& leaveInfos.get(i).getAvailableBalance().doubleValue() == 0
						&& leaveInfos.get(i).getPendingApproval().doubleValue() == 0) {
					leaveInfos.remove(i);
					i--;
				}
			}
		}

		return leaveInfos;
	}

	public void saveLvComments(BeaEmpLvComments comments) {
		leaveRequestDao.saveLvComments(comments);
	}

	public void saveLvWorkflow(BeaEmpLvWorkflow flow, BhrEmpDemo demo) throws MessagingException {
		leaveRequestDao.saveLvWorkflow(flow);
		// create alert
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a E");
		String message = sdf.format(new Date()) + ": Leave Request from " + demo.getNameF().trim() + " " + demo.getNameL().trim()
				+ " pending your approval";
		alertDao.createAlert(demo.getEmpNbr().trim(), flow.getApprvrEmpNbr().trim(), message.trim());
	
	}

	
	public void sendEmail(BeaEmpLvRqst request,BhrEmpDemo demo,LeaveEmployeeData supervisorData ) throws MessagingException{
		String supervisorEmail = supervisorData ==null?null:supervisorData.getEmailAddress();
		if(!StringUtil.isNullOrEmpty(supervisorEmail)) {
			//Send Email
			String subject = "Leave request submitted for "+ demo.getNameF().trim() + " " + demo.getNameL().trim();
			String returnBody = "";
			StringBuilder emailBody = new StringBuilder();
			emailBody.append("<p>%s:</p>");
			emailBody.append("<p>A leave request for %s, employee number %s, has been submitted and is ready for your approval.  The leave dates and times requested are as follows:</p>");
			emailBody.append("<p style='margin-left: 12pt;'>Dates:&nbsp;&nbsp;%s&nbsp;&nbsp;-&nbsp;&nbsp;%s<br/>Times:&nbsp;&nbsp;%s&nbsp;&nbsp;-&nbsp;&nbsp;%s</p>");		
			emailBody.append("<p style='font-weight:bold'>Please log in to Employee Portal to process this submission.</p>");
			emailBody.append("<p>Thank You</p>");
			SimpleDateFormat sdfD = new SimpleDateFormat("MM/dd/yyyy");
			SimpleDateFormat sdfT = new SimpleDateFormat("hh:mm a");
			returnBody = String.format(emailBody.toString(), supervisorData.getFullNameTitleCase(), demo.getNameF().trim() + " " + demo.getNameL().trim(),demo.getEmpNbr() ,
					sdfD.format(DateUtil.getLocalTime(request.getDatetimeFrom())),  sdfD.format(DateUtil.getLocalTime(request.getDatetimeTo())), 
					sdfT.format(DateUtil.getLocalTime(request.getDatetimeFrom())),  sdfT.format(DateUtil.getLocalTime(request.getDatetimeTo())));
			try {
				MailUtil.sendEmail(supervisorEmail.trim(), subject, returnBody.trim());
			}
			catch(Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	public void deleteLeaveComments(Integer lvId) {
		leaveRequestDao.deleteLeaveComments(lvId);
	}

	public void deleteLeaveFlow(Integer lvId) {
		leaveRequestDao.deleteLeaveFlow(lvId);
	}

	public LeaveRequest getBeaEmpLvRqstById(int id) {
		return leaveRequestDao.getBeaEmpLvRqstById(id);
	}

	public List<LeaveBalance> getApprovedLeaves(String empNbr, String leaveType, String searchStart, String searchEnd,
			String freq) throws ParseException {
		List<BhrEmpLvXmital> result = leaveRequestDao.getApprovedLeaves(empNbr, leaveType, searchStart, searchEnd,
				freq);
		
		// filter processed and not processed based on options
		 Options o =  this.indexService.getOptions();
				
		 for(int i = 0; i < result.size(); i++)
		 {
			if((StringUtil.isNullOrEmpty(result.get(i).getProcessDt())&& !o.getShowUnprocessedLeave())
				||((!StringUtil.isNullOrEmpty(result.get(i).getProcessDt()))&& !o.getShowProcessedLeave())
			  )
			{
				result.remove(i);
				i--;
			}
		 }
				
		List<LeaveBalance> balances = new ArrayList<LeaveBalance>();
		LeaveBalance temp;
		for (int i = 0; i < result.size(); i++) {
			temp = new LeaveBalance(result.get(i));
			balances.add(temp);
		}
		return balances;
	}

	public List<String[]> mapReasonsAndLeaveTypes() {
		List<String[]> map = leaveRequestDao.getAbsrsnsLeaveTypesMap();
		return map;
	}
	
	public LeaveEmployeeData getEmployeeData(String employeeNumber) {
		return leaveRequestDao.getEmployeeData(employeeNumber);
	}
	
	
	public List<LeaveUnitsConversion> getMinutesToHoursConversionRecs(String payFrequency, String leaveType) {
		List<LeaveUnitsConversion> conversionRecs = leaveRequestDao.getMinutesToHoursConversionRecs(payFrequency, leaveType);
		BigDecimal fromMinute = new BigDecimal(1.0).setScale(3, BigDecimal.ROUND_HALF_UP);
		for (LeaveUnitsConversion rec : conversionRecs) {
			rec.setFromUnit(fromMinute);
			fromMinute = new BigDecimal (rec.getToUnit().intValue()+1).setScale(3, BigDecimal.ROUND_HALF_UP);
		}
		return conversionRecs;
	}
	
	public List<LeaveUnitsConversion> getHoursToDaysConversionRecs(String payFrequency, String leaveType) {
		List<LeaveUnitsConversion> conversionRecs = leaveRequestDao.getHoursToDaysConversionRecs(payFrequency, leaveType);
		BigDecimal fromHour = new BigDecimal(0.001).setScale(3, BigDecimal.ROUND_HALF_UP);
		for (LeaveUnitsConversion rec : conversionRecs) {
			rec.setFromUnit(fromHour);
			fromHour = new BigDecimal (rec.getToUnit().doubleValue()+0.001).setScale(3, BigDecimal.ROUND_HALF_UP);
		}
		return conversionRecs;
	}
	
}
