package net.esc20.txeis.EmployeeAccess.domainobject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class EarningsLeave implements Serializable {
	
	private static final long serialVersionUID = -1023249528812172648L;
	
	private String code ="";
	private String description="";
	private String unitsPrior ="";
	//private BigDecimal balance;
	private String balance;
	private String voidIssue;
	private String adjNumber;
	
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
