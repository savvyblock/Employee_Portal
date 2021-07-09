package com.esc20.nonDBModels;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import net.esc20.txeis.WorkflowLibrary.domain.WorkflowHistory;



public class ApproveTravelRequestCommand implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -848884867843718835L;
	
	
	// District Information
    private String districtId = "";
    private String districtName = "";

    // Message Prompts
    private String msgOK = "";
    private String msgSkip = "";
    private String msgRequestor = "";
    private String msgApproved = "";

    // Workflow Information
    private boolean approvalPathFlg = false;         // Used for displaying Approval Path popup.
    private List<WorkflowHistory> approvalPath;      // Object to display pending list. (History / Current Approver / Future Approvers)
    private String radioInsertApprover = "B";        // Used on Approval Path popup to select to insert before or after an approver.
    private int insertApprover = 0;                  // Approver being inserted before or after the current approver.
    private boolean finalApprover = false;           // Flag that will enable the PO Nbr to open if Final Approver.
    private int workflowType = 0;                    // Used for the Type of Workflow.
    private String currRequestor = "";               // Store the current Requestor in case the Requestor needs to be restored.
    private String userIdEmpNbr = "";                // Used to store the approvers employee nbr from SEC_USERS;

    // Travel Request info
    private List<ApproveTravelRequest> approveTravelRequests = new ArrayList<ApproveTravelRequest>();;
    
    private boolean selectAll = false;
    private boolean anySelected = false;
    private boolean refreshData = false;
    
 // Travel Request info
    private List<ApproveTravelRequest> finalApproverTravelRequests = new ArrayList<ApproveTravelRequest>();
    
    private boolean showFinalApproverPopup = false;
    

    public String getDistrictId() {
        return this.districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getDistrictName() {
        return this.districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getMsgOK() {
        return this.msgOK;
    }

    public void setMsgOK(String msgOK) {
        this.msgOK = msgOK;
    }

    public String getMsgSkip() {
        return this.msgSkip;
    }

    public void setMsgSkip(String msgSkip) {
        this.msgSkip = msgSkip;
    }

    public String getMsgRequestor() {
        return this.msgRequestor;
    }

    public void setMsgRequestor(String msgRequestor) {
        this.msgRequestor = msgRequestor;
    }

    public String getMsgApproved() {
        return this.msgApproved;
    }

    public void setMsgApproved(String msgApproved) {
        this.msgApproved = msgApproved;
    }

    public boolean isApprovalPathFlg() {
        return this.approvalPathFlg;
    }

    public void setApprovalPathFlg(boolean approvalPathFlg) {
        this.approvalPathFlg = approvalPathFlg;
    }

    public List<WorkflowHistory> getApprovalPath() {
        return this.approvalPath;
    }

    public void setApprovalPath(List<WorkflowHistory> approvalPath) {
        this.approvalPath = approvalPath;
    }

    public String getRadioInsertApprover() {
        return this.radioInsertApprover;
    }

    public void setRadioInsertApprover(String radioInsertApprover) {
        this.radioInsertApprover = radioInsertApprover;
    }

    public int getInsertApprover() {
        return this.insertApprover;
    }

    public void setInsertApprover(int insertApprover) {
        this.insertApprover = insertApprover;
    }

    public boolean isFinalApprover() {
        return this.finalApprover;
    }

    public void setFinalApprover(boolean finalApprover) {
        this.finalApprover = finalApprover;
    }

    public int getWorkflowType() {
        return this.workflowType;
    }

    public void setWorkflowType(int workflowType) {
        this.workflowType = workflowType;
    }

    public String getCurrRequestor() {
        return this.currRequestor;
    }

    public void setCurrRequestor(String currRequestor) {
        this.currRequestor = currRequestor;
    }

    public String getUserIdEmpNbr() {
        return this.userIdEmpNbr;
    }

    public void setUserIdEmpNbr(String userIdEmpNbr) {
        this.userIdEmpNbr = userIdEmpNbr;
    }

	public List<ApproveTravelRequest> getApproveTravelRequests() {
		return approveTravelRequests;
	}

	public void setApproveTravelRequests(List<ApproveTravelRequest> approveTravelRequest) {
		this.approveTravelRequests = approveTravelRequest;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public boolean isSelectAll() {
		return selectAll;
	}

	public void setSelectAll(boolean selectAll) {
		this.selectAll = selectAll;
	}

	public boolean isAnySelected() {
		return anySelected;
	}

	public void setAnySelected(boolean anySelected) {
		this.anySelected = anySelected;
	}
	
	public List<ApproveTravelRequest> getFinalApproverTravelRequests() {
		return finalApproverTravelRequests;
	}

	public void setFinalApproverTravelRequests(List<ApproveTravelRequest> finalApproverTravelRequest) {
		this.finalApproverTravelRequests = finalApproverTravelRequest;
	}

	public boolean isShowFinalApproverPopup() {
		return showFinalApproverPopup;
	}

	public void setShowFinalApproverPopup(boolean showFinalApproverPopup) {
		this.showFinalApproverPopup = showFinalApproverPopup;
	}

	public boolean isRefreshData() {
		return refreshData;
	}

	public void setRefreshData(boolean refreshData) {
		this.refreshData = refreshData;
	}

	@Override
	public String toString() {
		return "ApproveTravelRequestCommand [approveTravelRequests=" + approveTravelRequests + ", selectAll=" + selectAll
				+ ", anySelected=" + anySelected + "]";
	}


}
