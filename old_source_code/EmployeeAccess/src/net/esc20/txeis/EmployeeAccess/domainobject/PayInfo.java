package net.esc20.txeis.EmployeeAccess.domainobject;

import java.io.Serializable;

import net.esc20.txeis.EmployeeAccess.domainobject.reference.Code;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.MaritalStatus;

public class PayInfo implements Serializable
{
	private static final long serialVersionUID = -1155050870729069499L;
	
	private Code maritalStatus;
	private Integer numberOfExemptions;

	public Code getMaritalStatus() {
		return maritalStatus;
	}
	public void setMaritalStatus(Code maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	public Integer getNumberOfExemptions() {
		return numberOfExemptions;
	}
	public void setNumberOfExemptions(Integer numberOfExemptions) {
		this.numberOfExemptions = numberOfExemptions;
	}
	
	
}