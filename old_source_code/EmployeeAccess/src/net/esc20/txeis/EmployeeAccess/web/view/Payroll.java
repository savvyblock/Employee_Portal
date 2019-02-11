package net.esc20.txeis.EmployeeAccess.web.view;

import java.io.Serializable;
import java.util.List;

import net.esc20.txeis.EmployeeAccess.domainobject.Bank;
import net.esc20.txeis.EmployeeAccess.domainobject.BankChanges;
import net.esc20.txeis.EmployeeAccess.domainobject.PayInfo;
import net.esc20.txeis.EmployeeAccess.domainobject.PayInfoChanges;

public class Payroll implements Serializable
{
	private static final long serialVersionUID = 7173022220222933734L;
	
	private String frequency = "Biweekly";
	private String fieldDisplayOptionBank = "N"; 
	private String fieldDisplayOptionInfo = "N"; 
	private Boolean autoApprovePayInfo = false;
	private Boolean autoApproveAccountInfo = false;

	//binded pending changes object for payroll info (marital status and number of exceptions)
	private PayInfo payInfo;
	
	//booleans for if changes exist in DB (used for determining highlighting)
	private PayInfoChanges payInfoChanges;
	
	//binded pending changes list for Bank Accounts
	private List<Bank> accountInfo;
	
	//booleans for if changes exist in DB (used for determining highlighting)
	private List<BankChanges> accountInfoChanges;
	
	private String primary; 
	
	public String getFieldDisplayOptionBank() {
		return fieldDisplayOptionBank;
	}
	public void setFieldDisplayOptionBank(String fieldDisplayOptionBank) {
		this.fieldDisplayOptionBank = fieldDisplayOptionBank;
	}
	public String getFieldDisplayOptionInfo() {
		return fieldDisplayOptionInfo;
	}
	public void setFieldDisplayOptionInfo(String fieldDisplayOptionInfo) {
		this.fieldDisplayOptionInfo = fieldDisplayOptionInfo;
	}
	public String getFrequency() {
		return frequency;
	}
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	public String getPrimary() {
		return primary;
	}
	public void setPrimary(String primary) {
		this.primary = primary;
	}
	public List<Bank> getAccountInfo() {
		return accountInfo;
	}
	public void setAccountInfo(List<Bank> accountInfo) {
		this.accountInfo = accountInfo;
		
		// set the primary account (amount is zero)
		int index=0;
		for (Bank bank : accountInfo) {
			if (bank.getDepositAmount().getAmount() == 0) {
				primary = Integer.toString(index);
				break;
			}
			index++;
		}
	}
	public PayInfo getPayInfo() {
		return payInfo;
	}
	public void setPayInfo(PayInfo payInfo) {
		this.payInfo = payInfo;
	}
	public PayInfoChanges getPayInfoChanges() {
		return payInfoChanges;
	}
	public void setPayInfoChanges(PayInfoChanges payInfoChanges) {
		this.payInfoChanges = payInfoChanges;
	}
	public List<BankChanges> getAccountInfoChanges() {
		return accountInfoChanges;
	}
	public void setAccountInfoChanges(List<BankChanges> accountInfoChanges) {
		this.accountInfoChanges = accountInfoChanges;
	
	}
	public Boolean getAutoApproveAccountInfo() {
		return autoApproveAccountInfo;
	}
	public void setAutoApproveAccountInfo(Boolean autoApproveAccountInfo) {
		this.autoApproveAccountInfo = autoApproveAccountInfo;
	}
	public Boolean getAutoApprovePayInfo() {
		return autoApprovePayInfo;
	}
	public void setAutoApprovePayInfo(Boolean autoApprovePayInfo) {
		this.autoApprovePayInfo = autoApprovePayInfo;
	}

}