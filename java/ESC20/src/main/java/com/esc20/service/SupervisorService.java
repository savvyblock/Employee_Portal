package com.esc20.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esc20.dao.AlertDao;
import com.esc20.dao.AppUserDao;
import com.esc20.dao.LeaveRequestDao;
import com.esc20.dao.SupervisorDao;
import com.esc20.model.BeaEmpLvComments;
import com.esc20.model.BeaEmpLvRqst;
import com.esc20.model.BeaEmpLvTmpApprovers;
import com.esc20.model.BhrEmpDemo;
import com.esc20.model.BhrPmisPosCtrl;
import com.esc20.nonDBModels.AppLeaveRequest;
import com.esc20.nonDBModels.LeaveEmployeeData;
import com.esc20.nonDBModels.LeaveRequestComment;
import com.esc20.util.DateUtil;


@Service
public class SupervisorService {

    @Autowired
    private LeaveRequestDao leaveRequestDao;
    
    @Autowired
    private SupervisorDao supervisorDao;
 
    @Autowired
    private AppUserDao appUserDao;
    
    @Autowired
    private AlertDao alertDao;   
    
	public List<LeaveEmployeeData> getDirectReportEmployee(String empNbr, boolean usePMIS, boolean supervisorsOnly,
			boolean excludeTempApprovers) {
		List<LeaveEmployeeData> directReports;
		if (usePMIS) {
			BhrPmisPosCtrl employeePMISData = leaveRequestDao.getEmployeeSupervisorPMISData(empNbr);
			if (employeePMISData!=null) {
				directReports = supervisorDao.getPMISSupervisorDirectReports(employeePMISData.getSpvsrBilletNbr(), employeePMISData.getSpvsrPosNbr());
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
    		leaves.get(i).setInfo(leaveRequestDao.getLeaveInfo(leaves.get(i).getEmpNbr(), leaves.get(i).getPayFreq().toString()));
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
		return result;
	}

	public void approveLeave(BeaEmpLvRqst rqst, BhrEmpDemo demo, String approverComment) {
		//insert approver Comment;
        BeaEmpLvComments comments = new BeaEmpLvComments();
        comments.setBeaEmpLvRqst(rqst);
        comments.setLvCommentEmpNbr(demo.getEmpNbr());
        comments.setLvCommentDatetime(DateUtil.getUTCTime());
        comments.setLvComment(approverComment==null?"":approverComment);
        comments.setLvCommentTyp('A');
        this.leaveRequestDao.saveLvComments(comments);
        //update rqst status
		rqst.setStatusCd('A');
		rqst.setDtOfPay("");
		this.leaveRequestDao.saveLeaveRequest(rqst, true);
		//create alert
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a E");
		SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		String message = sdf.format(new Date())+": "+ demo.getNameF()+" " + demo.getNameL() +
						 " had approved your leave request from "+sdf1.format(DateUtil.getLocalTime(rqst.getDatetimeFrom()))+
						 " to " + sdf1.format(DateUtil.getLocalTime(rqst.getDatetimeTo()));
		alertDao.createAlert(demo.getEmpNbr(), rqst.getEmpNbr(), message);
	}

	public void disApproveLeave(BeaEmpLvRqst rqst, BhrEmpDemo demo, String disapproveComment) {
		//insert approver Comment;
        BeaEmpLvComments comments = new BeaEmpLvComments();
        comments.setBeaEmpLvRqst(rqst);
        comments.setLvCommentEmpNbr(demo.getEmpNbr());
        comments.setLvCommentDatetime(DateUtil.getUTCTime());
        comments.setLvComment(disapproveComment==null?"":disapproveComment);
        comments.setLvCommentTyp('D');
        this.leaveRequestDao.saveLvComments(comments);
        //update rqst status
		rqst.setStatusCd('D');
		rqst.setDtOfPay("");
		this.leaveRequestDao.saveLeaveRequest(rqst, true);
		//create alert
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a E");
		SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		String message = sdf.format(new Date())+": "+ demo.getNameF()+" " + demo.getNameL() +" disapproved your leave from "+
						 sdf1.format(DateUtil.getLocalTime(rqst.getDatetimeFrom()))+" to " + sdf1.format(DateUtil.getLocalTime(rqst.getDatetimeTo())) +" with comment: " + disapproveComment;
		alertDao.createAlert(demo.getEmpNbr(), rqst.getEmpNbr(), message);
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

}
