package com.esc20.nonDBModels;

import java.io.Serializable;
import java.math.BigDecimal;

import com.esc20.util.NumberUtil;
import com.esc20.util.StringUtil;

public class EarningsBank implements Serializable{
	private String name="";
	private String acctType="";
	private String acctNum ="";
	private BigDecimal amt;
	private String code="";
	private String acctTypeCode = "";
	
	public EarningsBank(String bankCd, String bankName, Character bankAcctTyp, String bankAcctTypDescr, String bankAcctNbr,  BigDecimal bankAcctAmt) {
		String tempAcctNum = StringUtil.trim(bankAcctNbr);
		tempAcctNum = StringUtil.fill("*", tempAcctNum.length()-4) + StringUtil.right(tempAcctNum, 4);
			
		this.setCode(StringUtil.trim(bankCd));
		this.setName(StringUtil.trim(bankName));
		this.setAcctNum(tempAcctNum);
		this.setAcctType(StringUtil.trim(bankAcctTypDescr));
		this.setAcctTypeCode(StringUtil.trim(bankAcctTyp.toString()));
		this.setAmt(NumberUtil.value(bankAcctAmt));
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAcctType() {
		return acctType;
	}
	public void setAcctType(String acctType) {
		this.acctType = acctType;
	}
	public String getAcctNumLabel()
	{
		String s = getAcctNum();
		
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
	public String getAcctNum() {
		return acctNum;
	}
	public void setAcctNum(String acctNum) {
		this.acctNum = acctNum;
	}
	public BigDecimal getAmt() {
		return amt;
	}
	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}
	public String getAcctTypeCode() {
		return acctTypeCode;
	}
	public void setAcctTypeCode(String acctTypeCode) {
		this.acctTypeCode = acctTypeCode;
	}
}
