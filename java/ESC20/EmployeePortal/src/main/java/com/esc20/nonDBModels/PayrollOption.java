package com.esc20.nonDBModels;

import java.io.Serializable;

public class PayrollOption implements Serializable {
	private static final long serialVersionUID = 7173022220222933734L;
	 
	private String fieldDisplayOptionBank = "N"; 
	private String fieldDisplayOptionInfo = "N";
	
	public String getFieldDisplayOptionBank() {
		return fieldDisplayOptionBank;
	}
	public void setFieldDisplayOptionBank(String fieldDisplayOptionBank) {
		this.fieldDisplayOptionBank = fieldDisplayOptionBank;
	}
	public String getFieldDisplayOptionInfo() {
		return fieldDisplayOptionInfo;
	}
	public void setFieldDisplayOptionInfo(String fieldDisplayOptionInfo) {
		this.fieldDisplayOptionInfo = fieldDisplayOptionInfo;
	} 
	
	
	

}
