package com.esc20.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esc20.dao.AlertDao;
import com.esc20.dao.AppUserDao;
import com.esc20.dao.AutoCompleteDao;
import com.esc20.dao.LeaveRequestDao;
import com.esc20.dao.OptionsDao;
import com.esc20.dao.SupervisorDao;
import com.esc20.model.BeaEmpLvComments;
import com.esc20.model.BeaEmpLvRqst;
import com.esc20.model.BeaEmpLvTmpApprovers;
import com.esc20.model.BhrEmpDemo;
import com.esc20.model.BhrPmisPosCtrl;
import com.esc20.nonDBModels.AppLeaveRequest;
import com.esc20.nonDBModels.Code;
import com.esc20.nonDBModels.LeaveEmployeeData;
import com.esc20.nonDBModels.LeaveRequest;
import com.esc20.nonDBModels.LeaveRequestComment;
import com.esc20.nonDBModels.Options;
import com.esc20.util.DateUtil;
import com.esc20.util.MailUtil;
import com.esc20.util.StringUtil;


@Service
public class SupervisorService {

    @Autowired
    private OptionsDao optionsDao;
	
    @Autowired
    private LeaveRequestDao leaveRequestDao;
    
    @Autowired
    private SupervisorDao supervisorDao;
 
    @Autowired
    private AppUserDao appUserDao;
    
    @Autowired
    private AlertDao alertDao;   
    
    @Autowired
    private AutoCompleteDao autoCompleteDao;   
    
    @Autowired
    private LeaveRequestService leaveRequestService;
    
	public List<LeaveEmployeeData> getDirectReportEmployee(String empNbr, boolean usePMIS, boolean supervisorsOnly,
			boolean excludeTempApprovers) {
		List<LeaveEmployeeData> directReports;
		if (usePMIS) {
			BhrPmisPosCtrl employeePMISData = leaveRequestDao.getEmployeeSupervisorPMISData(empNbr);
			if (employeePMISData!=null) {
				directReports = supervisorDao.getPMISSupervisorDirectReports(employeePMISData.getId().getBilletNbr(), employeePMISData.getId().getPosNbr());
			} else {
				directReports = new ArrayList<LeaveEmployeeData>();
			}
		} else {
			directReports = supervisorDao.getSupervisorDirectReports(empNbr);
		}
		if (supervisorsOnly && directReports!=null) {
			// remove direct reports who are not supervisors AND who do not have submittals to process
			ArrayList<LeaveEmployeeData> directReportSupervisors = new ArrayList<LeaveEmployeeData>();
			for (LeaveEmployeeData employeeData : directReports) {
				if (employeeData.getNumDirectReports() > 0) {
					directReportSupervisors.add(employeeData);
				} else if (!excludeTempApprovers){
					// if an employee is a not a supervisor but does have submittals to process, add them
					List<AppLeaveRequest> leaveRequestSubmittals = getSupervisorSumittedLeaveRequests(employeeData.getEmployeeNumber());
					if (leaveRequestSubmittals.size() > 0) {
						directReportSupervisors.add(employeeData);
					}
				}
			}
			return directReportSupervisors;
		} else {
			return directReports;
		}
	}

	public List<AppLeaveRequest> getSupervisorSumittedLeaveRequests(String employeeNumber) {
    	List<AppLeaveRequest> leaves = leaveRequestDao.getSupervisorSumittedLeaveRequests(employeeNumber);
    	for(int i=0;i<leaves.size();i++) {
    		leaves.get(i).setComments(leaveRequestDao.getLeaveComments(leaves.get(i).getId()));
    		//leaves.get(i).setInfo(leaveRequestDao.getLeaveInfo(leaves.get(i).getEmpNbr(), leaves.get(i).getPayFreq().toString()));
    		leaves.get(i).setInfo(leaveRequestService.getLeaveInfo(leaves.get(i).getEmpNbr(), leaves.get(i).getPayFreq().toString(),false));
    	}
    	return leaves;
	}

	public List<AppLeaveRequest> getLeaveDetailsForCalendar(String empNbr, String payFreq, String start, String end) throws ParseException {
		List<LeaveEmployeeData> directEmployees = supervisorDao.getSupervisorDirectReports(empNbr);
		BhrEmpDemo supervisor = appUserDao.getUserDetail(empNbr);
		BhrEmpDemo directEmployee;
		List<AppLeaveRequest> result = new ArrayList<AppLeaveRequest>();
		List<LeaveRequestComment> emptyList = new ArrayList<LeaveRequestComment>();
		List<AppLeaveRequest> supervisorSubmitted = supervisorDao.getLeaveRequestSummital(empNbr, payFreq, start, end, supervisor);
		for(int i=0;i<supervisorSubmitted.size();i++) {
			supervisorSubmitted.get(i).setComments(emptyList);
		}
		result.addAll(supervisorSubmitted);
		List<AppLeaveRequest> supervisorPending = supervisorDao.getLeaveRequestPending(empNbr, payFreq, start, end, supervisor);
		for(int i=0;i<supervisorPending.size();i++) {
			if(supervisorPending.get(i).getStatusCd()=='P')
				supervisorPending.get(i).setInfo(leaveRequestDao.getLeaveInfo(supervisorPending.get(i).getEmpNbr(), supervisorPending.get(i).getPayFreq().toString()));
			if(supervisorPending.get(i).getStatusCd()=='A')
				supervisorPending.get(i).setApprover(supervisorDao.getApprover(supervisorPending.get(i).getId()));
			else
				supervisorPending.get(i).setApprover("");
			supervisorPending.get(i).setComments(leaveRequestDao.getLeaveComments(supervisorPending.get(i).getId()));
		}
		result.addAll(supervisorPending);
		List<AppLeaveRequest> employeeResult = new ArrayList<AppLeaveRequest>();
		for(LeaveEmployeeData data: directEmployees) {
			directEmployee = appUserDao.getUserDetail(data.getEmployeeNumber());
			employeeResult = supervisorDao.getLeaveRequestSummital(directEmployee.getEmpNbr(), payFreq, start, end, directEmployee);
			for(int i=0;i<employeeResult.size();i++) {
				employeeResult.get(i).setComments(emptyList);
				employeeResult.get(i).setApprover("");
			}
			result.addAll(employeeResult);
			employeeResult = supervisorDao.getLeaveRequestPending(directEmployee.getEmpNbr(), payFreq, start, end, directEmployee);
			for(int i=0;i<employeeResult.size();i++) {
				if(employeeResult.get(i).getStatusCd()=='P')
					employeeResult.get(i).setInfo(leaveRequestDao.getLeaveInfo(employeeResult.get(i).getEmpNbr(), employeeResult.get(i).getPayFreq().toString()));
				employeeResult.get(i).setComments(leaveRequestDao.getLeaveComments(employeeResult.get(i).getId()));
				if(employeeResult.get(i).getStatusCd()=='A')
					employeeResult.get(i).setApprover(supervisorDao.getApprover(employeeResult.get(i).getId()));
				else
					employeeResult.get(i).setApprover("");
			}
			result.addAll(employeeResult);
		}
		result.sort(new Comparator<AppLeaveRequest>(){
			@Override
			public int compare(AppLeaveRequest arg0, AppLeaveRequest arg1) {
				return arg1.getDatetimeFrom().compareTo(arg0.getDatetimeFrom());
			}
		});
		Options options = optionsDao.getOptions();
		for(int i = 0; i < result.size(); i++)
		{
			if((result.get(i).getStatusCd().equals('\0') && !options.getShowUnprocessedLeave())
				|| (!result.get(i).getStatusCd().equals('\0') && !options.getShowProcessedLeave()))
			{
				result.remove(i);
				i--;
			}
		}
		return result;
	}

	public void approveLeave(LeaveRequest request, BhrEmpDemo demo, String approverComment,LeaveEmployeeData employee) throws MessagingException {
		//insert approver Comment;
        BeaEmpLvComments comments = new BeaEmpLvComments();
        comments.setLvId(request.getId());
        comments.setLvCommentEmpNbr(demo.getEmpNbr().trim());
        comments.setLvCommentDatetime(DateUtil.getUTCTime());
        comments.setLvComment(approverComment==null?"":approverComment.trim());
        comments.setLvCommentTyp('A');
        this.leaveRequestDao.saveLvComments(comments);
		this.leaveRequestDao.saveLeaveRequest(request.getId(), "A", true);
		//create alert
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss a E");
		SimpleDateFormat sdf1 = new SimpleDateFormat("MM-dd-yyyy hh:mm a");
		String message = sdf.format(new Date())+": "+ demo.getNameF().trim()+" " + demo.getNameL().trim() +
						 " had approved your leave request from "+sdf1.format(DateUtil.getLocalTime(request.getDatetimeFrom()))+
						 " to " + sdf1.format(DateUtil.getLocalTime(request.getDatetimeTo()));
		alertDao.createAlert(demo.getEmpNbr().trim(), request.getEmpNbr().trim(), message.trim());
		
		if(employee !=null && (!StringUtil.isNullOrEmpty(employee.getEmailAddress()))) {
			String subject = "Leave Request Approved";
			String returnBody = "";
			StringBuilder emailBody = new StringBuilder();
			emailBody.append("<p>%s:</p>");
			emailBody.append("<p>Your leave request has been approved by %s.  No action is needed on your part.  The leave dates and times requested are as follows:</p>");
			emailBody.append("<p style='margin-left: 12pt;'>Dates:&nbsp;&nbsp;%s&nbsp;&nbsp;-&nbsp;&nbsp;%s<br/>Times:&nbsp;&nbsp;%s&nbsp;&nbsp;-&nbsp;&nbsp;%s</p>");		

			emailBody.append("<p style='font-weight:bold'>Please log in to Employee Protal if you wish to make changes to the leave requested and resubmit the request.</p>");
			emailBody.append("<p>Thank You</p>");
			
			SimpleDateFormat sdfD = new SimpleDateFormat("MM-dd-yyyy");
			SimpleDateFormat sdfT = new SimpleDateFormat("hh:mm a");
			returnBody = String.format(emailBody.toString(), employee.getFullNameTitleCase(),  demo.getNameF().trim()+" " + demo.getNameL().trim(), 
					sdfD.format(DateUtil.getLocalTime(request.getDatetimeFrom())),  sdfD.format(DateUtil.getLocalTime(request.getDatetimeTo())), 
					sdfT.format(DateUtil.getLocalTime(request.getDatetimeFrom())),  sdfT.format(DateUtil.getLocalTime(request.getDatetimeTo())));
			try {
				MailUtil.sendEmail(employee.getEmailAddress().trim(), subject, returnBody.trim());
			}
			catch(Exception ex) {
				ex.printStackTrace();
			}
		}
		
	}

	public void disApproveLeave(LeaveRequest request, BhrEmpDemo demo, String disapproveComment,LeaveEmployeeData employee) throws MessagingException {
		//insert approver Comment;
        BeaEmpLvComments comments = new BeaEmpLvComments();
        comments.setLvId(request.getId());
        comments.setLvCommentEmpNbr(demo.getEmpNbr().trim());
        comments.setLvCommentDatetime(DateUtil.getUTCTime());
        comments.setLvComment(disapproveComment==null?"":disapproveComment.trim());
        comments.setLvCommentTyp('D');
        this.leaveRequestDao.saveLvComments(comments);
		this.leaveRequestDao.saveLeaveRequest(request.getId(), "D", true);
		//create alert
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss a E");
		SimpleDateFormat sdf1 = new SimpleDateFormat("MM-dd-yyyy hh:mm a");
		String message = sdf.format(new Date())+": "+ demo.getNameF().trim()+" " + demo.getNameL().trim() +" disapproved your leave from "+
						 sdf1.format(DateUtil.getLocalTime(request.getDatetimeFrom()))+" to " + sdf1.format(DateUtil.getLocalTime(request.getDatetimeTo())) +" with comment: " + disapproveComment;
		alertDao.createAlert(demo.getEmpNbr().trim(), request.getEmpNbr().trim(), message.trim());
		if(employee !=null && (!StringUtil.isNullOrEmpty(employee.getEmailAddress()))) {
			String subject = "Leave Request Disapproved";
			String returnBody = "";
			StringBuilder emailBody = new StringBuilder();
			emailBody.append("<p>%s:</p>");
			emailBody.append("<p>The leave request you submitted has been disapproved by %s.  The leave dates and times requested are as follows:</p>");
			emailBody.append("<p style='margin-left: 12pt;'>Dates:&nbsp;&nbsp;%s&nbsp;&nbsp;-&nbsp;&nbsp;%s<br/>Times:&nbsp;&nbsp;%s&nbsp;&nbsp;-&nbsp;&nbsp;%s</p>");		
			emailBody.append("<p style='font-weight:bold'>Please log in to Employee Protal to view comments entered by the supervisor or if you wish to make changes to the leave requested and resubmit the request.</p>");
			
			emailBody.append("<p>Thank You</p>");
			
			SimpleDateFormat sdfD = new SimpleDateFormat("MM-dd-yyyy");
			SimpleDateFormat sdfT = new SimpleDateFormat("hh:mm a");
			returnBody = String.format(emailBody.toString(), employee.getFullNameTitleCase(),  demo.getNameF().trim()+" " + demo.getNameL().trim(), 
					sdfD.format(DateUtil.getLocalTime(request.getDatetimeFrom())),  sdfD.format(DateUtil.getLocalTime(request.getDatetimeTo())), 
					sdfT.format(DateUtil.getLocalTime(request.getDatetimeFrom())),  sdfT.format(DateUtil.getLocalTime(request.getDatetimeTo())));
			
			try {
				MailUtil.sendEmail(employee.getEmailAddress().trim(), subject, returnBody.trim());
			}
			catch(Exception ex) {
				ex.printStackTrace();
			}
			
		}
	}

	public List<BeaEmpLvTmpApprovers> getBeaEmpLvTmpApprovers(String empNbr) {
		return this.supervisorDao.getBeaEmpLvTmpApprovers(empNbr);
	}

	public void saveTempApprover(BeaEmpLvTmpApprovers tempApprover, boolean isUpdate) {
		this.supervisorDao.saveTempApprover(tempApprover, isUpdate);
	}

	public void deleteTempApprover(BeaEmpLvTmpApprovers tempApprover) {
		this.supervisorDao.deleteTempApprover(tempApprover);
	}
	
	public List<Code> getEmployeeTempApproverSearch(String excludeEmpNumber, String search){
		return this.autoCompleteDao.getEmployeeTempApproverSearch(excludeEmpNumber, search);
	}
	
	public List<Code> getEmployeeTempApproverByNumber(String excludeEmpNumber, String search){
		return this.autoCompleteDao.getEmployeeTempApproverByNumber(excludeEmpNumber, search);
	}

}
