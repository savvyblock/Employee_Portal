package com.esc20.nonDBModels;

import java.io.Serializable;

public class BankRequest implements Serializable
{
	private static final long serialVersionUID = -4293607069850390057L;
	
	private Frequency frequency;
	private Code code;
	private String accountNumber;
	private Code accountType;
	private Money depositAmount;
	
	private Code codeNew;
	private String accountNumberNew;
	private Code accountTypeNew;
	private Money depositAmountNew;
	
	//for use with self service payroll
	private Boolean isDelete = false;
	private Boolean invalidAccount = false;
	
	private Boolean isFromAccount = false; //is true from getAccounts  else is from getAccountRequests
	
	public Boolean getIsFromAccount() {
		return isFromAccount;
	}
	public void setIsFromAccount(Boolean isFromAccount) {
		this.isFromAccount = isFromAccount;
	}
	public Boolean getInvalidAccount() {
		return invalidAccount;
	}
	public void setInvalidAccount(Boolean invalidAccount) {
		this.invalidAccount = invalidAccount;
	}
	
	public Boolean getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}
	public Frequency getFrequency() {
		return frequency;
	}
	public void setFrequency(Frequency frequency) {
		this.frequency = frequency;
	}
	public Code getCode() {
		return code;
	}
	public void setCode(Code code) {
		this.code = code;
	}
	public String getAccountNumberLabel()
	{
		String s = getAccountNumber();
		
		if(s.length() > 4)
		{
			int start = s.length()-4;
			String end = s.substring(s.length()-4,s.length());
			s="";
			for(int i = 0; i < start; i++) s+= "*";
			s += end;
		}
		
		return s;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public Code getAccountType() {
		return accountType;
	}
	public void setAccountType(Code accountType) {
		this.accountType = accountType;
	}
	public Money getDepositAmount() {
		return depositAmount;
	}
	public void setDepositAmount(Money depositAmount) {
		this.depositAmount = depositAmount;
	}
	public Code getCodeNew() {
		return codeNew;
	}
	public void setCodeNew(Code codeNew) {
		this.codeNew = codeNew;
	}
	public String getAccountNumberNew() {
		return accountNumberNew;
	}
	public void setAccountNumberNew(String accountNumberNew) {
		this.accountNumberNew = accountNumberNew;
	}
	public Code getAccountTypeNew() {
		return accountTypeNew;
	}
	public void setAccountTypeNew(Code accountTypeNew) {
		this.accountTypeNew = accountTypeNew;
	}
	public Money getDepositAmountNew() {
		return depositAmountNew;
	}
	public void setDepositAmountNew(Money depositAmountNew) {
		this.depositAmountNew = depositAmountNew;
	}
	
	
}