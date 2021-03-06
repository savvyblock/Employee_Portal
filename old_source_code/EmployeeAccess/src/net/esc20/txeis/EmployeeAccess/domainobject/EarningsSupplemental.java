package net.esc20.txeis.EmployeeAccess.domainobject;

import java.io.Serializable;
import java.math.BigDecimal;

public class EarningsSupplemental implements Serializable {

	private static final long serialVersionUID = -1023249528812172648L;
	
	private String code ="";
	private String description ="";
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
	public BigDecimal getAmt() {
		return amt;
	}
	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}
}
