package net.esc20.txeis.EmployeeAccess.domainobject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import net.esc20.txeis.EmployeeAccess.domainobject.reference.ICode;

public class Leave implements Serializable
{
	private static final long serialVersionUID = -4886259323902947639L;
	
	public static enum Status
	{
		Processed,
		NotProcessed;
		
		public String getLabel()
		{
			if(this.equals(Processed))
			{
				return "Processed";
			}
			else if(this.equals(NotProcessed))
			{
				return "Not Processed";
			}
			else
			{
				throw new IllegalArgumentException("Invalid status");
			}
		}
	}
	
	private ICode type;
	private Date dateOfPay;
	private Date dateOfLeave;
	private BigDecimal leaveUsed;
	private BigDecimal leaveEarned;
	private Status status;
	
	public ICode getType() {
		return type;
	}
	public void setType(ICode type) {
		this.type = type;
	}
	public Date getDateOfPay() {
		return dateOfPay;
	}
	public void setDateOfPay(Date dateOfPay) {
		this.dateOfPay = dateOfPay;
	}
	public Date getDateOfLeave() {
		return dateOfLeave;
	}
	public void setDateOfLeave(Date dateOfLeave) {
		this.dateOfLeave = dateOfLeave;
	}
	public BigDecimal getLeaveUsed() {
		return leaveUsed;
	}
	public void setLeaveUsed(BigDecimal leaveUsed) {
		this.leaveUsed = leaveUsed;
	}
	public BigDecimal getLeaveEarned() {
		return leaveEarned;
	}
	public void setLeaveEarned(BigDecimal leaveEarned) {
		this.leaveEarned = leaveEarned;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
}