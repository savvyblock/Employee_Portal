package com.esc20.nonDBModels;

import java.io.Serializable;

public class PayrollFields implements Serializable {
	private static final long serialVersionUID = -4293607069850390057L;
	
	private Boolean code= false;
	private Boolean accountNumber = false;
	private Boolean accountType = false;
	private Boolean depositAmount = false;
	private Boolean maritalStatus = false;
	private Boolean numberOfExemptions = false;
	private Boolean filingStatus = false;
	private Boolean multiJob = false;
	private Boolean numberOfChildren = false;
	private Boolean numberOfOtherDepend = false;
	private Boolean otherIncomeAmt = false;
	private Boolean otherDeductAmt = false;
	private Boolean otherExemptAmt = false;
	public Boolean getCode() {
		return code;
	}
	public void setCode(Boolean code) {
		this.code = code;
	}
	public Boolean getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(Boolean accountNumber) {
		this.accountNumber = accountNumber;
	}
	public Boolean getAccountType() {
		return accountType;
	}
	public void setAccountType(Boolean accountType) {
		this.accountType = accountType;
	}
	public Boolean getDepositAmount() {
		return depositAmount;
	}
	public void setDepositAmount(Boolean depositAmount) {
		this.depositAmount = depositAmount;
	}
	public Boolean getMaritalStatus() {
		return maritalStatus;
	}
	public void setMaritalStatus(Boolean maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	public Boolean getNumberOfExemptions() {
		return numberOfExemptions;
	}
	public void setNumberOfExemptions(Boolean numberOfExemptions) {
		this.numberOfExemptions = numberOfExemptions;
	}
	public Boolean getFilingStatus() {
		return filingStatus;
	}
	public void setFilingStatus(Boolean filingStatus) {
		this.filingStatus = filingStatus;
	}
	public Boolean getMultiJob() {
		return multiJob;
	}
	public void setMultiJob(Boolean multiJob) {
		this.multiJob = multiJob;
	}
	public Boolean getNumberOfChildren() {
		return numberOfChildren;
	}
	public void setNumberOfChildren(Boolean numberOfChildren) {
		this.numberOfChildren = numberOfChildren;
	}
	public Boolean getNumberOfOtherDepend() {
		return numberOfOtherDepend;
	}
	public void setNumberOfOtherDepend(Boolean numberOfOtherDepend) {
		this.numberOfOtherDepend = numberOfOtherDepend;
	}
	public Boolean getOtherIncomeAmt() {
		return otherIncomeAmt;
	}
	public void setOtherIncomeAmt(Boolean otherIncomeAmt) {
		this.otherIncomeAmt = otherIncomeAmt;
	}
	public Boolean getOtherDeductAmt() {
		return otherDeductAmt;
	}
	public void setOtherDeductAmt(Boolean otherDeductAmt) {
		this.otherDeductAmt = otherDeductAmt;
	}
	public Boolean getOtherExemptAmt() {
		return otherExemptAmt;
	}
	public void setOtherExemptAmt(Boolean otherExemptAmt) {
		this.otherExemptAmt = otherExemptAmt;
	}
	
}
