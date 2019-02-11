package com.esc20.nonDBModels;

import java.io.Serializable;
import java.math.BigDecimal;

import com.esc20.util.NumberUtil;
import com.esc20.util.StringUtil;

public class EarningsSupplemental implements Serializable {

	private static final long serialVersionUID = -1023249528812172648L;
	
	private String code ="";
	private String description ="";
	private BigDecimal amt;
	
	public EarningsSupplemental(BigDecimal edAmt, String edCd, String shortDescr, String bhrPayDeductHistEmpNbr, String bhrPayDistrHistChkNbr,
			Character bhrPayDistrHisVoidOrIss, Short bhrPayDistrHistAdjNbr, String bhrPayDistrHistDtOfPay) {
		this.setCode(StringUtil.trim(edCd));
		this.setAmt(NumberUtil.value(edAmt));
		this.setDescription(StringUtil.trim(shortDescr));
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
	public BigDecimal getAmt() {
		return amt;
	}
	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}
}
