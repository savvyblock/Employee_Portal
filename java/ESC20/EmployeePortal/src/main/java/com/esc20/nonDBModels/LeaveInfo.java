package com.esc20.nonDBModels;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;

import com.esc20.util.NumberUtil;

import net.sf.json.JSONObject;

public class LeaveInfo implements Serializable{

	private static final long serialVersionUID = -9075806121870313132L;
	
	private Frequency frequency;
	private Code type;
	private String daysHrs;
	private String subtractFromBalanceValue;
	private Boolean subtractFromBalance; 
	private String postAgainstZeroBalanceValue;
	private Boolean postAgainstZeroBalance;
	private BigDecimal beginBalance;
	private BigDecimal advancedEarned;
	private BigDecimal used;
	private BigDecimal pendingEarned;
	private BigDecimal pendingUsed;
	private BigDecimal pendingApproval;
	private BigDecimal pendingPayroll;
	

	public JSONObject toJSON() {
		JSONObject jo = new JSONObject();
		jo.put("frequency", frequency);
		jo.put("lvTyp", type.getCode());
		jo.put("longDescr", type.getDescription());
		jo.put("beginBalance", beginBalance);
		jo.put("advancedEarned", advancedEarned);
		jo.put("used", used);
		jo.put("pendingEarned", pendingEarned);
		jo.put("pendingUsed", pendingUsed);
		jo.put("pendingApproval", pendingApproval);
		jo.put("pendingPayroll", pendingPayroll);
		jo.put("daysHrs", daysHrs);
		jo.put("subtractFromBalanceValue", subtractFromBalanceValue);
		jo.put("subtractFromBalance", subtractFromBalance);
		jo.put("postAgainstZeroBalanceValue", postAgainstZeroBalanceValue);
		jo.put("postAgainstZeroBalance", postAgainstZeroBalance);
		jo.put("availableBalance", this.getAvailableBalance());
		jo.put("totalPendingUsed",this.getTotalPendingUsed());
		return jo;
	}
	
	public LeaveInfo(Character payFreq, String lvTyp, BigDecimal lvBeginBal, BigDecimal lvEarned,
			BigDecimal lvUsed, String longDescr, Character postAgnstZeroBal, String daysHrs, Character addSubtractBal,
			BigDecimal pendingEarned, BigDecimal pendingApproval, BigDecimal pendingPayroll, BigDecimal pendingUsed) {	
		this.frequency = Frequency.getFrequency(payFreq.toString());
		Code type = new Code();
		type.setCode(lvTyp);
		type.setDescription(longDescr);
		this.type = type;
		this.beginBalance = NumberUtil.value(lvBeginBal).setScale(3, BigDecimal.ROUND_HALF_UP);
		this.advancedEarned = NumberUtil.value(lvEarned).setScale(3, BigDecimal.ROUND_HALF_UP);
		this.used = NumberUtil.value(lvUsed).setScale(3, BigDecimal.ROUND_HALF_UP);
		this.pendingEarned = NumberUtil.value(pendingEarned==null?BigDecimal.valueOf(0L):pendingEarned).setScale(3, BigDecimal.ROUND_HALF_UP);
		this.pendingUsed = NumberUtil.value(pendingUsed==null?BigDecimal.valueOf(0L):pendingUsed).setScale(3, BigDecimal.ROUND_HALF_UP);
		this.pendingApproval = NumberUtil.value(pendingApproval==null?BigDecimal.valueOf(0L):pendingApproval).setScale(3, BigDecimal.ROUND_HALF_UP);
		this.pendingPayroll = NumberUtil.value(pendingPayroll==null?BigDecimal.valueOf(0L):pendingPayroll).setScale(3, BigDecimal.ROUND_HALF_UP);
		this.daysHrs = daysHrs==null?"":daysHrs.trim();
		this.subtractFromBalanceValue = addSubtractBal.toString().trim().toUpperCase();
		this.subtractFromBalance = addSubtractBal.toString().toUpperCase().equals("S")?true:false;
		this.postAgainstZeroBalanceValue = postAgnstZeroBal.toString().trim().toUpperCase();
		this.postAgainstZeroBalance = postAgnstZeroBal.toString().trim().toUpperCase().equals("Y")?true:false;
	}

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
		
		return availableBalanceBD;
		/*if (availableBalanceBD.compareTo(BigDecimal.ZERO)< 0) {
			return new BigDecimal(0);
		} else {
			return availableBalanceBD;
		}*/
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

	public Code getType() {
		return type;
	}
	public void setType(Code type) {
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
