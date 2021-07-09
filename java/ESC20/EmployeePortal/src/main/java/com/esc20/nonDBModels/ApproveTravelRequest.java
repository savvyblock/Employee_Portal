package com.esc20.nonDBModels;

import java.math.BigDecimal;
import java.util.ArrayList;

import com.esc20.util.DateUtil;

public class ApproveTravelRequest {

	private Integer workflowType;
	private Integer workflowCurrentApprover;
	private String workflowRequestId;
    private String approverEmplNumber;
    private String vendorNbr;
    private String empNbr;
    private String employeeName;
    private String travelRequestNumber;
    private String dateRequested;
    private BigDecimal requestTotal;
    private String purpose;
    private String overnight;
    
    
    private boolean  checkboxSelected = false;
    
    private boolean finalApprover = false ;

    private ArrayList<String> requests = new ArrayList<String>(); 

	public String getEmpNbr() {
        return this.empNbr;
    }

    public void setEmpNbr(String empNbr) {
        this.empNbr = empNbr;
    }

    public String getVendorNbr() {
        return this.vendorNbr;
    }

    public void setVendorNbr(String vendorNbr) {
        this.vendorNbr = vendorNbr;
    }

    public String getEmployeeName() {
        return this.employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getTravelRequestNumber() {
        return this.travelRequestNumber;
    }

    public void setTravelRequestNumber(String travelRequestNumber) {
        this.travelRequestNumber = travelRequestNumber;
    }

    public String getDateRequested() {
        return this.dateRequested;
    }

    public void setDateRequested(String dateRequested) {
        this.dateRequested = dateRequested;
    }

    public BigDecimal getRequestTotal() {
		return requestTotal;
	}

	public void setRequestTotal(BigDecimal requestTotal) {
		this.requestTotal = requestTotal;
	}

	public String getPurpose() {
        return this.purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

	public boolean isCheckboxSelected() {
		return checkboxSelected;
	}

	public void setCheckboxSelected(boolean checkboxSelected) {
		this.checkboxSelected = checkboxSelected;
	}

	public Integer getWorkflowType() {
		return workflowType;
	}

	public void setWorkflowType(Integer workflowType) {
		this.workflowType = workflowType;
	}

	public Integer getWorkflowCurrentApprover() {
		return workflowCurrentApprover;
	}

	public void setWorkflowCurrentApprover(Integer workflowCurrentApprover) {
		this.workflowCurrentApprover = workflowCurrentApprover;
	}

	public String getWorkflowRequestId() {
		return workflowRequestId;
	}

	public void setWorkflowRequestId(String workflowRequestId) {
		this.workflowRequestId = workflowRequestId;
	}

	public String getApproverEmplNumber() {
		return approverEmplNumber;
	}


	public void setApproverEmplNumber(String approverEmplNumber) {
		this.approverEmplNumber = approverEmplNumber;
	}


	public String getOvernight() {
		return overnight;
	}


	public void setOvernight(String overnight) {
		this.overnight = overnight;
	}

	public boolean isFinalApprover() {
		return finalApprover;
	}

	public void setFinalApprover(boolean finalApprover) {
		this.finalApprover = finalApprover;
	}

	@Override
	public String toString() {
		return "ApproveTravelRequest [workflowType=" + workflowType + ", workflowCurrentApprover="
				+ workflowCurrentApprover + ", workflowRequestId=" + workflowRequestId + ", approverEmplNumber="
				+ approverEmplNumber + ", vendorNbr=" + vendorNbr + ", empNbr=" + empNbr + ", employeeName="
				+ employeeName + ", travelRequestNumber=" + travelRequestNumber + ", dateRequested=" + dateRequested
				+ ", requestTotal=" + requestTotal + ", purpose=" + purpose + ", overnight=" + overnight
				+ ", checkboxSelected=" + checkboxSelected + ", finalApprover=" + finalApprover + "]";
	}


}
