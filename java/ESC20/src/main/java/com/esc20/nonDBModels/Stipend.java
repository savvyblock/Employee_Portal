package com.esc20.nonDBModels;

import java.io.Serializable;
import java.math.BigDecimal;
import com.esc20.nonDBModels.Frequency;

public class Stipend implements Serializable {
	
	private static final long serialVersionUID = 6715142900654758429L;
	
	private String extraDutyCd;
	private String extraDutyDescription;
	private Character defaultAccountType;
	private BigDecimal extraDutyAmt;
	private BigDecimal remainAmt;
	private Short remainPayments;
	private Frequency frequency;
	
	public Stipend (Object extraDutyCd,Object extraDutyDescription, 
			Object defaultAccountType, Object extraDutyAmt,Object remainAmt,Object remainPayments,Object frequency) {
		this.setExtraDutyCd((String) extraDutyCd);
		this.setExtraDutyDescription((String) extraDutyDescription);
		this.setDefaultAccountType((Character) defaultAccountType);
		this.setExtraDutyAmt((BigDecimal) extraDutyAmt);
		this.setRemainAmt((BigDecimal) remainAmt);
		this.setRemainPayments((Short) remainPayments);
		this.setFrequency(Frequency.getFrequency((((Character) frequency).toString()).trim()));
	}
	
	public String getExtraDutyCd() {
		return extraDutyCd;
	}
	public void setExtraDutyCd(String extraDutyCd) {
		this.extraDutyCd = extraDutyCd;
	}
	public String getExtraDutyDescription() {
		return extraDutyDescription;
	}
	public void setExtraDutyDescription(String extraDutyDescription) {
		this.extraDutyDescription = extraDutyDescription;
	}
	public Character getDefaultAccountType() {
		return defaultAccountType;
	}
	public void setDefaultAccountType(Character defaultAccountType) {
		this.defaultAccountType = defaultAccountType;
	}
	public BigDecimal getExtraDutyAmt() {
		return extraDutyAmt;
	}
	public void setExtraDutyAmt(BigDecimal extraDutyAmt) {
		this.extraDutyAmt = extraDutyAmt;
	}
	public BigDecimal getRemainAmt() {
		return remainAmt;
	}
	public void setRemainAmt(BigDecimal remainAmt) {
		this.remainAmt = remainAmt;
	}
	public Short getRemainPayments() {
		return remainPayments;
	}
	public void setRemainPayments(Short remainPayments) {
		this.remainPayments = remainPayments;
	}
	public Frequency getFrequency() {
		return frequency;
	}
	public void setFrequency(Frequency frequency) {
		this.frequency = frequency;
	}
	
}
