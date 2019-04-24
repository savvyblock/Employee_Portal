package com.esc20.nonDBModels;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Calendar extends PaymentInfo implements Serializable
{
	private static final long serialVersionUID = -1023249528812172648L;
	
	private Frequency frequency;
	
	private BigDecimal contractPay;
	private BigDecimal nonContractPay;
	private BigDecimal supplementalPay;
	
	private BigDecimal dependentCareEmployer;
	private BigDecimal dependentCareExceeds;
	
	private Date lastPostedPayDate;

	public String getLastPostedPayDateLabel()
	{
		return lastPostedPayDate != null ? new SimpleDateFormat("MM-dd-yyyy").format(lastPostedPayDate) : "no pay date";   //jf20140110 cal ytd rpt
	}
	public Date getLastPostedPayDate() {
		return lastPostedPayDate;
	}
	public void setLastPostedPayDate(Date lastPostedPayDate) {
		this.lastPostedPayDate = lastPostedPayDate;
	}
	public Frequency getFrequency() {
		return frequency;
	}
	public void setFrequency(Frequency frequency) {
		this.frequency = frequency;
	}
	public BigDecimal getContractPay() {
		return contractPay;
	}
	public void setContractPay(BigDecimal contractPay) {
		this.contractPay = contractPay;
	}
	public BigDecimal getNonContractPay() {
		return nonContractPay;
	}
	public void setNonContractPay(BigDecimal nonContractPay) {
		this.nonContractPay = nonContractPay;
	}
	public BigDecimal getSupplementalPay() {
		return supplementalPay;
	}
	public void setSupplementalPay(BigDecimal supplementalPay) {
		this.supplementalPay = supplementalPay;
	}
	public BigDecimal getDependentCareEmployer() {
		return dependentCareEmployer;
	}
	public void setDependentCareEmployer(BigDecimal dependentCareEmployer) {
		this.dependentCareEmployer = dependentCareEmployer;
	}
	public BigDecimal getDependentCareExceeds() {
		return dependentCareExceeds;
	}
	public void setDependentCareExceeds(BigDecimal dependentCareExceeds) {
		this.dependentCareExceeds = dependentCareExceeds;
	}
}