package net.esc20.txeis.EmployeeAccess.domainobject;

import java.io.Serializable;

public class PayInfoChanges implements Serializable
{
	private static final long serialVersionUID = -1155050870729069499L;
	
	private Boolean maritalStatusChanged = false;
	private Boolean numberOfExemptionsChanged = false;
	
	public Boolean getMaritalStatusChanged() {
		return maritalStatusChanged;
	}
	public void setMaritalStatusChanged(Boolean maritalStatusChanged) {
		this.maritalStatusChanged = maritalStatusChanged;
	}
	public Boolean getNumberOfExemptionsChanged() {
		return numberOfExemptionsChanged;
	}
	public void setNumberOfExemptionsChanged(Boolean numberOfExemptionsChanged) {
		this.numberOfExemptionsChanged = numberOfExemptionsChanged;
	}
	
	
}