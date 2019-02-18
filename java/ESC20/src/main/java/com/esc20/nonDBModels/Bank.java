package com.esc20.nonDBModels;

import java.io.Serializable;

public class Bank implements Serializable
{
	private static final long serialVersionUID = -4293607069850390057L;
	
	private Frequency frequency;
	private Code code;
	private String accountNumber;
	private Code accountType;
	private Money depositAmount;
	
	//for use with self service payroll
	private Boolean isDelete = false;
	private Boolean invalidAccount = false;
	
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
	
}