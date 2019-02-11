package net.esc20.txeis.EmployeeAccess.web.view;

import java.io.Serializable;

import net.esc20.txeis.EmployeeAccess.domainobject.reference.Frequency;

public class Deduction implements Serializable
{
	private static final long serialVersionUID = -6121013564970129423L;
	
	private Frequency frequency = Frequency.Biweekly; //jlf 20111104

	public Frequency getFrequency() {
		return frequency;
	}

	public void setFrequency(Frequency frequency) {
		this.frequency = frequency;
	}
}