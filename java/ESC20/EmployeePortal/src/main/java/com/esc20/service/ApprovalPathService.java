package com.esc20.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esc20.dao.AppUserDao;
import com.esc20.model.WorkflowApprovalPath;

import net.esc20.txeis.WorkflowLibrary.domain.StatusCode;
import net.esc20.txeis.WorkflowLibrary.domain.Workflow;
import net.esc20.txeis.WorkflowLibrary.domain.WorkflowHistory;
import net.esc20.txeis.WorkflowLibrary.domain.WorkflowType;
import net.esc20.txeis.WorkflowLibrary.service.WorkflowService;

@Service
public class ApprovalPathService {

	@Autowired
	private AppUserDao appUserDao;

	public List<WorkflowApprovalPath> getWorkFlowPath(String tripNbr, String districtId, String empNbr) {

		try {
			//Load the requisition approval path for the first requisition 
			Workflow workflow = new Workflow(WorkflowType.TRAVEL , tripNbr, "", null);
			workflow.setDistrictId(districtId);
			workflow.setCurrentApproverId(appUserDao.getSecUserForEmpNbr(empNbr));
			WorkflowService workflowService = WorkflowService.getWorkflowService();
			workflowService.setDaoDataSource(workflow.getDistrictId());

			List<WorkflowHistory> projWorkflow = new ArrayList<WorkflowHistory>();
			projWorkflow = workflowService.getProjectedFlow(workflow);
			
			List<WorkflowApprovalPath> epWFPath = new ArrayList<WorkflowApprovalPath>();

			for (int i = 0; i < projWorkflow.size(); i++) {
				WorkflowHistory historyRow = projWorkflow.get(i);

				if (!StatusCode.ByPassed.equals(historyRow.getApproved())){
					WorkflowApprovalPath WFPath = new WorkflowApprovalPath();
					WFPath.setPathSeqNbr(i + 1);
					String approverName = historyRow.getWorkflowApprover().getApprover().getName();
					WFPath.setBpoReqApprvlUserId(approverName);
					WFPath.setTitle(historyRow.getWorkflowApprover().getApprover().getTitle());

					if (historyRow.getWorkflowApprover().getAlternateApprover() != null){
						String altApproverName = historyRow.getWorkflowApprover().getAlternateApprover().getName();
						WFPath.setBpoReqApprvlAltUserId(altApproverName);
					}

					WFPath.setBpoReqApprvlStat(historyRow.getApproved().getDescription());

					WFPath.setPrtName(historyRow.getWorkflowApprover().isPrintName() ? "Y" : "N");

					Date date = historyRow.getApprovalDt();

					if(date != null){
						String approvalDt = date.toString().trim().replaceAll("-", "");
						if (!approvalDt.equals("") && approvalDt.length() == 8) {
							approvalDt = approvalDt.substring(4, 6) + "-" + approvalDt.substring(6, 8) + "-" + approvalDt.substring(0, 4); 
						}
						WFPath.setBpoReqApprvlDt(approvalDt);
					}

					epWFPath.add(WFPath);
				}
			}
			
			return epWFPath;
		}
		catch (Exception e) {
			return new ArrayList<WorkflowApprovalPath>();
		}
	}
}