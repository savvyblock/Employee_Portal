package net.esc20.txeis.EmployeeAccess.domainobject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;

import net.esc20.txeis.EmployeeAccess.domainobject.reference.Frequency;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.ICode;

public class LeaveInfo implements Serializable
{
	private static final long serialVersionUID = -9075806121870313132L;
	
	private Frequency frequency;
	private ICode type;
	private String daysHrs;  // units of the leave type ... D for days or H hours
	private String subtractFromBalanceValue;
	private boolean subtractFromBalance; 
	private String postAgainstZeroBalanceValue;
	private boolean postAgainstZeroBalance;
	private BigDecimal beginBalance;
	private BigDecimal advancedEarned;
	private BigDecimal used;
	private BigDecimal pendingEarned;
	private BigDecimal pendingUsed;  // these are unprocessed leave requests in the BHR_EMP_LV_XMITAL table
	private BigDecimal pendingApproval; // these are leave requests in the BEA_EMP_LV_RQST table that have not yet been approved
	private BigDecimal pendingPayroll;  // these are leave requests in the BEA_EMP_LV_RQST table that have been approved or is flagged as being imported 
	
	public Frequency getFrequency() {
		return frequency;
	}

	public void setFrequency(Frequency frequency) {
		this.frequency = frequency;
	}

	public BigDecimal getAvailableBalance()
	{
		BigDecimal availableBalanceBD = null; 
		if (this.isSubtractFromBalance()) {
			availableBalanceBD = beginBalance.add(advancedEarned).subtract(used).add(pendingEarned).subtract(pendingUsed).subtract(pendingApproval).subtract(pendingPayroll);
		} else {
			availableBalanceBD = beginBalance.add(advancedEarned).add(pendingEarned);
		}
		if (availableBalanceBD.compareTo(BigDecimal.ZERO)< 0) {
			return new BigDecimal(0);
		} else {
			return availableBalanceBD;
		}
	}
	
	public String getAvailableBalanceLabel() {
		return (new DecimalFormat("0.000")).format(getAvailableBalance());
	}
	
	public BigDecimal getBalance() {
		if (this.isSubtractFromBalance()) {
			return beginBalance.add(advancedEarned).subtract(used).add(pendingEarned);
		} else {
			return beginBalance.add(advancedEarned).add(pendingEarned);
		}
	}
	
	public String getBalanceLabel() {
		return (new DecimalFormat("0.000")).format(getBalance());
	}

	public ICode getType() {
		return type;
	}
	public void setType(ICode type) {
		this.type = type;
	}
	public String getDaysHrs() {
		return daysHrs;
	}
	public void setDaysHrs(String daysHrs) {
		this.daysHrs = daysHrs;
	}
	public String getSubtractFromBalanceValue() {
		return subtractFromBalanceValue;
	}
	public void setSubtractFromBalanceValue(String subtractFromBalanceValue) {
		this.subtractFromBalanceValue = subtractFromBalanceValue;
	}
	public boolean isSubtractFromBalance() {
		return subtractFromBalance;
	}
	public void setSubtractFromBalance(boolean subtractFromBalance) {
		this.subtractFromBalance = subtractFromBalance;
	}
	public String getPostAgainstZeroBalanceValue() {
		return postAgainstZeroBalanceValue;
	}

	public void setPostAgainstZeroBalanceValue(String postAgainstZeroBalanceValue) {
		this.postAgainstZeroBalanceValue = postAgainstZeroBalanceValue;
	}

	public boolean isPostAgainstZeroBalance() {
		return postAgainstZeroBalance;
	}

	public void setPostAgainstZeroBalance(boolean postAgainstZeroBalance) {
		this.postAgainstZeroBalance = postAgainstZeroBalance;
	}

	public BigDecimal getBeginBalance() {
		return beginBalance;
	}
	public String getBeginBalanceLabel() {
		return (new DecimalFormat("0.000")).format(beginBalance);
	}
	public void setBeginBalance(BigDecimal beginBalance) {
		this.beginBalance = beginBalance.setScale(3, BigDecimal.ROUND_HALF_UP);
	}
	public BigDecimal getAdvancedEarned() {
		return advancedEarned;
	}
	public String getAdvancedEarnedLabel() {
		return (new DecimalFormat("0.000")).format(advancedEarned);
	}
	public void setAdvancedEarned(BigDecimal advancedEarned) {
		this.advancedEarned = advancedEarned.setScale(3, BigDecimal.ROUND_HALF_UP);
	}
	public BigDecimal getUsed() {
		return used;
	}
	public String getUsedLabel() {
		return (new DecimalFormat("0.000")).format(used);
	}
	public void setUsed(BigDecimal used) {
		this.used = used.setScale(3, BigDecimal.ROUND_HALF_UP);
	}
	public BigDecimal getPendingEarned() {
		return pendingEarned;
	}
	public String getPendingEarnedLabel() {
		return (new DecimalFormat("0.000")).format(pendingEarned);
	}
	public void setPendingEarned(BigDecimal pendingEarned) {
		this.pendingEarned = pendingEarned.setScale(3, BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal getPendingUsed() {
		return pendingUsed;
	}

	public String getPendingUsedLabel() {
		return (new DecimalFormat("0.000")).format(pendingUsed);
	}

	public void setPendingUsed(BigDecimal pendingUsed) {
		this.pendingUsed = pendingUsed;
	}

	public BigDecimal getPendingApproval() {
		return pendingApproval;
	}

	public String getPendingApprovalLabel() {
		return (new DecimalFormat("0.000")).format(pendingApproval);
	}
	
	public void setPendingApproval(BigDecimal pendingApproval) {
		this.pendingApproval = pendingApproval.setScale(3, BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal getPendingPayroll() {
		return pendingPayroll;
	}

	public String getPendingPayrollLabel() {
		return (new DecimalFormat("0.000")).format(pendingPayroll);
	}

	public void setPendingPayroll(BigDecimal pendingPayroll) {
		this.pendingPayroll = pendingPayroll.setScale(3, BigDecimal.ROUND_HALF_UP);
	}
	
	public BigDecimal getTotalPendingUsed()
	{
		return pendingUsed.add(pendingApproval).add(pendingPayroll);
	}

	public String getTotalPendingUsedLabel() {
		return (new DecimalFormat("0.000")).format(getTotalPendingUsed());
	}
	
}