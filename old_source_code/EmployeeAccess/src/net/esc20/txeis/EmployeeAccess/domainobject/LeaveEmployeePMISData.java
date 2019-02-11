package net.esc20.txeis.EmployeeAccess.domainobject;

import java.io.Serializable;

public class LeaveEmployeePMISData implements Serializable {

	private static final long serialVersionUID = 5713525643298619012L;
	
	String billetNumber;
	String posNumber;
	public String getBilletNumber() {
		return billetNumber;
	}
	public void setBilletNumber(String billetNumber) {
		this.billetNumber = billetNumber;
	}
	public String getPosNumber() {
		return posNumber;
	}
	public void setPosNumber(String posNumber) {
		this.posNumber = posNumber;
	}
	
}
