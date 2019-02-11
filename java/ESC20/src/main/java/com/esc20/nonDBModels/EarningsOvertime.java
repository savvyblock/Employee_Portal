package com.esc20.nonDBModels;

import java.math.BigDecimal;

import com.esc20.util.NumberUtil;
import com.esc20.util.StringUtil;


public class EarningsOvertime implements java.io.Serializable { 
	private static final long serialVersionUID = -7900725016377215097L;
	String jobCd;
	String description;
	BigDecimal overtimeUnits;
	BigDecimal overtimeRate;
	BigDecimal thisPeriod;

	public EarningsOvertime(String jobCd, String jobCdDescr, BigDecimal totSupplAmt, BigDecimal totHrsWrk,
			BigDecimal ovtmRate) {
		this.setDescription(StringUtil.trim(jobCdDescr));
		this.setJobCd(StringUtil.trim(jobCd));
		this.setOvertimeUnits(NumberUtil.value(totHrsWrk));
		this.setOvertimeRate(NumberUtil.value(ovtmRate));
		this.setThisPeriod(NumberUtil.value(totSupplAmt));
	}

	public String getJobCd() {
		return jobCd;
	}

	public void setJobCd(String jobCd) {
		this.jobCd = jobCd;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public BigDecimal getOvertimeUnits() {
		return overtimeUnits;
	}
	
	public void setOvertimeUnits(BigDecimal overtimeUnits)
	{
		this.overtimeUnits = overtimeUnits;
	}
	
	public BigDecimal getOvertimeRate() {
		return overtimeRate;
	}
	
	public void setOvertimeRate(BigDecimal overtimeRate)
	{
		this.overtimeRate = overtimeRate;
	}
	
	public BigDecimal getThisPeriod() {
		return thisPeriod;
	}
	
	public void setThisPeriod(BigDecimal thisPeriod)
	{
		this.thisPeriod = thisPeriod;
	}
}
