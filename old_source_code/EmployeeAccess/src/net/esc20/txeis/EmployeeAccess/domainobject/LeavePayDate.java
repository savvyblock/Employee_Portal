package net.esc20.txeis.EmployeeAccess.domainobject;

import java.io.Serializable;
import java.util.Date;

public class LeavePayDate implements Serializable {
	
	private static final long serialVersionUID = 3727549621955394899L;
	
	Date payDate=null;
	Date payPeriodBeginDate=null;
	Date payPeriodEndDate=null;
	Date leaveRequestCutoffDate=null;
	Date leaveLastApprovalDate=null;
	boolean skip=false;
	
	public Date getPayDate() {
		return payDate;
	}
	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}
	public Date getPayPeriodBeginDate() {
		return payPeriodBeginDate;
	}
	public void setPayPeriodBeginDate(Date payPeriodBeginDate) {
		this.payPeriodBeginDate = payPeriodBeginDate;
	}
	public Date getPayPeriodEndDate() {
		return payPeriodEndDate;
	}
	public void setPayPeriodEndDate(Date payPeriodEndDate) {
		this.payPeriodEndDate = payPeriodEndDate;
	}
	public Date getLeaveRequestCutoffDate() {
		return leaveRequestCutoffDate;
	}
	public void setLeaveRequestCutoffDate(Date leaveRequestCutoffDate) {
		this.leaveRequestCutoffDate = leaveRequestCutoffDate;
	}
	public Date getLeaveLastApprovalDate() {
		return leaveLastApprovalDate;
	}
	public void setLeaveLastApprovalDate(Date leaveLastApprovalDate) {
		this.leaveLastApprovalDate = leaveLastApprovalDate;
	}
	public boolean isSkip() {
		return skip;
	}
	public void setSkip(boolean skip) {
		this.skip = skip;
	}
}
