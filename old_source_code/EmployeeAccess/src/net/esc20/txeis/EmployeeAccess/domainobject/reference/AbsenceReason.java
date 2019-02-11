package net.esc20.txeis.EmployeeAccess.domainobject.reference;

import java.io.Serializable;

public class AbsenceReason implements Serializable {

	private static final long serialVersionUID = 3046706435696837826L;

	String absRsn;
	String absRsnDescription;
	
	public String getAbsRsn() {
		return absRsn;
	}
	public void setAbsRsn(String absRsn) {
		this.absRsn = absRsn;
	}
	public String getAbsRsnDescription() {
		return absRsnDescription;
	}
	public void setAbsRsnDescription(String absRsnDescription) {
		this.absRsnDescription = absRsnDescription;
	}
	
}
