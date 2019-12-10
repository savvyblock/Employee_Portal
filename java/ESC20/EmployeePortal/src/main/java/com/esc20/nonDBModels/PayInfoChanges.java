package com.esc20.nonDBModels;

import java.io.Serializable;

public class PayInfoChanges implements Serializable {
	private static final long serialVersionUID = -1155050870729069499L;
	
	private Boolean maritalStatusChanged = false;
	private Boolean numberOfExemptionsChanged = false;
	private Boolean filingStatusChanged = false;
	private Boolean multiJobChanged = false;
	private Boolean numberOfChildrenChanged = false;
	private Boolean numberOfOtherDependChanged = false;
	private Boolean otherIncomeAmtChanged = false;
	private Boolean otherDeductAmtChanged = false;
	private Boolean otherExemptAmtChanged = false;
	
	
	public Boolean getMaritalStatusChanged() {
		return maritalStatusChanged;
	}
	public void setMaritalStatusChanged(Boolean maritalStatusChanged) {
		this.maritalStatusChanged = maritalStatusChanged;
	}
	public Boolean getNumberOfExemptionsChanged() {
		return numberOfExemptionsChanged;
	}
	public void setNumberOfExemptionsChanged(Boolean numberOfExemptionsChanged) {
		this.numberOfExemptionsChanged = numberOfExemptionsChanged;
	}
	public Boolean getFilingStatusChanged() {
		return filingStatusChanged;
	}
	public void setFilingStatusChanged(Boolean filingStatusChanged) {
		this.filingStatusChanged = filingStatusChanged;
	}
	public Boolean getMultiJobChanged() {
		return multiJobChanged;
	}
	public void setMultiJobChanged(Boolean multiJobChanged) {
		this.multiJobChanged = multiJobChanged;
	}
	public Boolean getNumberOfChildrenChanged() {
		return numberOfChildrenChanged;
	}
	public void setNumberOfChildrenChanged(Boolean numberOfChildrenChanged) {
		this.numberOfChildrenChanged = numberOfChildrenChanged;
	}
	public Boolean getNumberOfOtherDependChanged() {
		return numberOfOtherDependChanged;
	}
	public void setNumberOfOtherDependChanged(Boolean numberOfOtherDependChanged) {
		this.numberOfOtherDependChanged = numberOfOtherDependChanged;
	}
	public Boolean getOtherIncomeAmtChanged() {
		return otherIncomeAmtChanged;
	}
	public void setOtherIncomeAmtChanged(Boolean otherIncomeAmtChanged) {
		this.otherIncomeAmtChanged = otherIncomeAmtChanged;
	}
	public Boolean getOtherDeductAmtChanged() {
		return otherDeductAmtChanged;
	}
	public void setOtherDeductAmtChanged(Boolean otherDeductAmtChanged) {
		this.otherDeductAmtChanged = otherDeductAmtChanged;
	}
	public Boolean getOtherExemptAmtChanged() {
		return otherExemptAmtChanged;
	}
	public void setOtherExemptAmtChanged(Boolean otherExemptAmtChanged) {
		this.otherExemptAmtChanged = otherExemptAmtChanged;
	}
	
	
	
}
