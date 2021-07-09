package com.esc20.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import com.esc20.dao.AppUserDao;
import com.esc20.dao.ApproveTravelRequestDao;
import com.esc20.dao.TravelRequestDao;
import com.esc20.model.CheckTrans;
import com.esc20.nonDBModels.ApproveTravelRequest;

import net.esc20.txeis.WorkflowLibrary.domain.Workflow;
import net.esc20.txeis.WorkflowLibrary.domain.WorkflowType;
import net.esc20.txeis.WorkflowLibrary.service.WorkflowService;


@Service
@Transactional (readOnly = true)
public class ApproveTravelRequestService {
	
	private Logger logger = LoggerFactory.getLogger(TravelRequestDao.class);
	
	@Autowired 
	private ApproveTravelRequestDao approveTravelRequestDao;
	
	@Autowired
	private AppUserDao appUserDao;
	
	public List<ApproveTravelRequest> getTravelRequestApprovalSummaries(String employeeNumber) { //
		try {
			return approveTravelRequestDao.getApproveTravelRequest(employeeNumber);
			
		} catch (Exception e) {
			String errorMessage = e.getMessage() == null ? "Unable to execute request(s). Please try again" : e.getMessage().trim();
			logger.error(errorMessage, e);
	
			throw new RuntimeException(errorMessage);
		}
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = Exception.class)
	public void returnRequest(List<ApproveTravelRequest> approveTravelRequestList, boolean anySelected, String sessionSrvcId, String sessionEmpNbr) {
		try {
			if (anySelected) {
				
				this.returnRequestWorflowAndDB(approveTravelRequestList, sessionSrvcId, sessionEmpNbr);
				
			}
		} catch (Exception e) {
			String errorMessage = e.getMessage() == null ? "Unable to Return request(s). Please try again" : e.getMessage().trim();
			logger.error(errorMessage, e);

			throw new RuntimeException(errorMessage);
		}
		
	}
	
	// *************************************************************************************************************************************
	/**
	 * Return Iteration trough the Page elements while going workflow and DB call
	 * 
	 * @param approveTravelRequest
	 * @throws Exception
	 * 
	 */
	
	private void returnRequestWorflowAndDB(List<ApproveTravelRequest> approveTravelRequestList, String sessionSrvcId, String sessionEmpNbr) throws Exception {
		
		for(ApproveTravelRequest approveTravelRequest : approveTravelRequestList) {
			
			if(approveTravelRequest.isCheckboxSelected()) {
				
				this.returnRequestWorkflowService(approveTravelRequest, sessionSrvcId, sessionEmpNbr);
				
				approveTravelRequestDao.updateBeaEmpTrvlStatus(approveTravelRequest.getTravelRequestNumber(), "R");

			}
		}
	}
	
	// *************************************************************************************************************************************
	/**
	 * Creates the worflow service (return process) for the ApproveTravelRequest, creates also worflow for the obj and set final approver to the obj
	 * 
	 * @param approveTravelRequest object to create workflow service with
	 * @throws Exception 
	 * 
	 */
	
	private void returnRequestWorkflowService(ApproveTravelRequest approveTravelRequest, String sessionSrvcId, String sessionEmpNbr) throws Exception {
		
		Workflow workflow = this.getWorkflowObjForApproveTravelRequest(approveTravelRequest, sessionEmpNbr);

		WorkflowService workflowService = WorkflowService.getWorkflowService();
		
		workflowService.setDaoDataSource(StringUtils.defaultString(sessionSrvcId));
		
		workflowService.denyRequest(workflow.getCurrentApproverId(), workflow);
		
	}
	
	@Transactional (propagation = Propagation.REQUIRED, readOnly = false, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = Exception.class)
	public void approveRequest( List<ApproveTravelRequest> approveTravelRequestList, List<ApproveTravelRequest> commandFinalApproverTravelRequesList, boolean anySelected, String sessionSrvcId, String sessionEmpNbr, BindingResult bindingResultErrors) {
		
		try {
			
			if(anySelected) {
				
				this.approveRequestIterateList(approveTravelRequestList, commandFinalApproverTravelRequesList, sessionSrvcId,sessionEmpNbr, bindingResultErrors);
				
			}
			
		}catch(Exception e) {
			
			String errorMessage = e.getMessage() == null ? "Unable to Approve request(s). Please try again" : e.getMessage().trim();
			
			logger.error(errorMessage, e);

			throw new RuntimeException(errorMessage);
		}
		
	}
	
	private void approveRequestIterateList(List<ApproveTravelRequest> approveTravelRequestList, List<ApproveTravelRequest> commandFinalApproverTravelRequesList, String sessionSrvcId, String sessionEmpNbr,BindingResult bindingResultErrors) throws Exception {
		
		for(ApproveTravelRequest approveTravelReq : approveTravelRequestList) {
			if(approveTravelReq.isCheckboxSelected()) {

				this.approveRequestWorkflowService(approveTravelReq, sessionSrvcId,sessionEmpNbr);	
				
				this.finalApproverProcess(commandFinalApproverTravelRequesList, approveTravelReq, bindingResultErrors);
				

			}
		}
	}
	
	private void approveRequestWorkflowService(ApproveTravelRequest approveTravelRequest, String sessionSrvcId, String sessionEmpNbr) throws Exception {
		
		Workflow workflow = this.getWorkflowObjForApproveTravelRequest(approveTravelRequest, sessionEmpNbr);
		
		workflow.setDistrictId(StringUtils.defaultString(sessionSrvcId));
		
		WorkflowService workflowService = WorkflowService.getWorkflowService();
		
		workflowService.setDaoDataSource(StringUtils.defaultString(sessionSrvcId));
		
		approveTravelRequest.setFinalApprover(workflowService.isFinalApprover(workflow));
		
		workflowService.approveRequest(workflow.getCurrentApproverId(), workflow);

	}
	
	private void finalApproverProcess(List<ApproveTravelRequest> commandApprovedRequestList, ApproveTravelRequest approveTravelRequest, BindingResult bindingResultErrors) throws Exception {
		
		if(approveTravelRequest.isFinalApprover()) {
			
			approveTravelRequestDao.isAutoAssignPaNbrEnabled(bindingResultErrors);
			
			if (!bindingResultErrors.hasErrors()) { 
				commandApprovedRequestList.add(approveTravelRequest);
				
				this.approveRequestUpdateDB(approveTravelRequest);
			}
			
		}
	}
	
	private void approveRequestUpdateDB(ApproveTravelRequest approveTravelRequest) throws Exception {
		final String systemDtsFormatt = new SimpleDateFormat("yyyyMMddHHmmssSS").format(new java.sql.Timestamp(new Date().getTime()).getTime());
		final String  systemDts = ( systemDtsFormatt.length() > 16 ) ? systemDtsFormatt.substring( 0, 16 ) : systemDtsFormatt;
		
		final String transDt = systemDts.substring(0, 8);
		
		approveTravelRequestDao.updateTransLock();
		
		// Update Vendor Table
		this.updateVendorTable(approveTravelRequest, transDt);

		approveTravelRequestDao.createDistrictChecks(approveTravelRequest.getEmpNbr() ,approveTravelRequest.getTravelRequestNumber(), systemDts, transDt);
		
		approveTravelRequestDao.updateBeaEmpTrvlStatus(approveTravelRequest.getTravelRequestNumber(), "A");

	}
	
	//*****************************************************************************************************************************************
	private void updateVendorTable(ApproveTravelRequest approveTravelRequest, String checkDt) throws Exception {
		approveTravelRequestDao.updateVendorTable(approveTravelRequest, checkDt);
	}


	
	
	// *************************************************************************************************************************************
	/**
	 * Get Worflow obj using ApprovalDashborad Obj
	 * 
	 * set also Worflow email information
	 * 
	 * @param approveTravelRequest object to create workflow with
	 * @return Workflow OBj
	 * @throws Exception 
	 */
	
	private Workflow getWorkflowObjForApproveTravelRequest(ApproveTravelRequest approveTravelRequest, String sessionEmpNbr) throws Exception {
		
		Workflow workflow = new Workflow(WorkflowType.TRAVEL, String.valueOf(approveTravelRequest.getTravelRequestNumber()), "");
		
		workflow.setUserIdSrc("E");
		
		workflow.setCurrentApproverId(appUserDao.getSecUserForEmpNbr(sessionEmpNbr));
			
		return workflow;
	}
	
	

}
