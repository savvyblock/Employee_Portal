package net.esc20.txeis.EmployeeAccess.domainobject.reference;

import java.io.Serializable;

public class PayFrequency implements Serializable {
	
	private static final long serialVersionUID = 696812227344794334L;
	
	String code="";
	String description="";
	
	public static long getSerialversionuid() {
		return serialVersionUID;
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
	
}
