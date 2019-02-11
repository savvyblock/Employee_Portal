package com.esc20.nonDBModels;

import java.io.Serializable;
import java.math.BigDecimal;

import com.esc20.util.NumberUtil;
import com.esc20.util.StringUtil;

public class EarningsJob implements Serializable {
	
	private static final long serialVersionUID = -1023249528812172648L;
	
	private String code="";
	private String description="";
	private BigDecimal units;
	private BigDecimal payRate;
	private BigDecimal amt;
	
	public EarningsJob(String jobCd, BigDecimal payRate, BigDecimal regHrsWrk, BigDecimal xmitalHrsWrk,
			Character payType, String jobCdDescr) {
		BigDecimal totalPay;
		BigDecimal transHoursWorked = NumberUtil.value(xmitalHrsWrk);
		payRate = NumberUtil.value(payRate);
		BigDecimal hoursWorked;
		
		if(transHoursWorked.doubleValue() > 0)
		{
			totalPay = transHoursWorked.multiply(payRate);
			hoursWorked = transHoursWorked;
		}
		else
		{
			totalPay = NumberUtil.value(regHrsWrk).multiply(payRate);
			hoursWorked = NumberUtil.value(regHrsWrk);
		}
		
		if(!"3".equals(payType) && !"4".equals(payType))
		{
			totalPay = payRate;
		}
		
		this.setAmt(totalPay);
		this.setCode(StringUtil.trim(jobCd));
		this.setPayRate(NumberUtil.value(payRate));
		this.setDescription(StringUtil.trim(jobCdDescr));
		this.setUnits(hoursWorked);
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public BigDecimal getUnits() {
		return units;
	}
	public void setUnits(BigDecimal units) {
		this.units = units;
	}
	public BigDecimal getPayRate() {
		return payRate;
	}
	public void setPayRate(BigDecimal payRate) {
		this.payRate = payRate;
	}
	public BigDecimal getAmt() {
		return amt;
	}
	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}
}
