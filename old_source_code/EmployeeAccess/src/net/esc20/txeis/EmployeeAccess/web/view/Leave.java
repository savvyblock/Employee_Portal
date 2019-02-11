package net.esc20.txeis.EmployeeAccess.web.view;

import java.io.Serializable;
import java.util.Date;

import net.esc20.txeis.EmployeeAccess.domainobject.reference.Frequency;

public class Leave implements Serializable
{
	private static final long serialVersionUID = 7173022220222933734L;
	
	private Frequency frequency = Frequency.Biweekly; //jlf 20111104
	private Date from;
	private Date to;
	private String leaveType;
	
	public Frequency getFrequency() {
		return frequency;
	}
	public void setFrequency(Frequency frequency) {
		this.frequency = frequency;
	}
	public Date getFrom() {
		return from;
	}
	public void setFrom(Date from) {
		this.from = from;
	}
	public Date getTo() {
		return to;
	}
	public void setTo(Date to) {
		this.to = to;
	}
	public String getLeaveType() {
		return leaveType;
	}
	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}
}