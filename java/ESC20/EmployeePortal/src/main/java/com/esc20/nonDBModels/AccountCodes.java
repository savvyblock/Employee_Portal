package com.esc20.nonDBModels;

import java.io.Serializable;

import net.sf.json.JSONObject;

public class AccountCodes implements Serializable
{
	
	private static final long serialVersionUID = 5627562603362422988L;
	
	protected String accountCode ="";
	protected String descr ="";

	public AccountCodes(){}
	
	public AccountCodes(String accountCode, String descr)
	{
		this.accountCode = accountCode;
		this.descr = descr;
	}

	public String getAccountCode() {
		return accountCode;
	}

	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

}
