package com.esc20.nonDBModels;

import java.math.BigDecimal;

import com.esc20.util.StringUtil;

public class EarningsLeave implements java.io.Serializable {


private static final long serialVersionUID = -1023249528812172648L;
	
	private String code ="";
	private String description="";
	private String unitsPrior ="";
	//private BigDecimal balance;
	private String balance;
	private String voidIssue;
	private String adjNumber;

	public EarningsLeave(String lvTyp, String longDescr, BigDecimal lvTaken, Character voidOrIss, Short adjNbr,
			BigDecimal lvEndBal, Character chkStubPos) {
		this.setCode(StringUtil.trim(lvTyp));
		this.setBalance(StringUtil.trim(lvEndBal.toString()));
		this.setDescription(StringUtil.trim(longDescr));
		this.setUnitsPrior(StringUtil.trim(lvTaken.toString()));
		this.setVoidIssue(StringUtil.trim(voidOrIss.toString()));
		this.setAdjNumber(StringUtil.trim(adjNbr.toString()));
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
	public String getUnitsPrior() {
		return unitsPrior;
	}
	public void setUnitsPrior(String unitsPrior) {
		this.unitsPrior = unitsPrior;
	}
//	public BigDecimal getBalance() {
//		return balance.setScale(3);
//	}
//	public void setBalance(BigDecimal balance) {
//		this.balance = balance;
//	}
	
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String getVoidIssue() {
		return voidIssue;
	}
	public void setVoidIssue(String voidIssue) {
		this.voidIssue = voidIssue;
	}
	public String getAdjNumber() {
		return adjNumber;
	}
	public void setAdjNumber(String adjNumber) {
		this.adjNumber = adjNumber;
	}
}
