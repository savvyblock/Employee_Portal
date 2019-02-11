package net.esc20.txeis.EmployeeAccess.domainobject;

import java.io.Serializable;

public class LeaveRequestError implements Serializable {

	private static final long serialVersionUID = 7901400347951895623L;
	
	int row;
	String fieldName;
	String errorMessage;
	
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	

}
