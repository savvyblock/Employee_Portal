package net.esc20.txeis.EmployeeAccess.domainobject;

import java.io.Serializable;
import java.math.BigDecimal;

public class EarningsJob implements Serializable {
	
	private static final long serialVersionUID = -1023249528812172648L;
	
	private String code="";
	private String description="";
	private BigDecimal units;
	private BigDecimal payRate;
	private BigDecimal amt;
	
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
