package com.esc20.nonDBModels;

/*
 * Created to generate a list of the populated account codes.
 */

public class AccountCode {
	private String fund = "";
	private String fscl_yr = "";
	private String func = "";
	private String obj = "";
	private String sobj = "";
	private String org = "";
	private String pgm = "";
	private String ed_span = "";
	private String proj_dtl = "";
	private String description = "";
	private double amount = 0;
	private double percent = 0;
	private String accountCode = "";
	
	public AccountCode() {
		
	}
	
	public AccountCode(String fund, String func, String obj, String sobj, String org, String fsclyr, String pgm, String edSpan, String projDtl) {
		this.fund = fund;
		this.func = func;
		this.obj = obj;
		this.sobj = sobj;
		this.org = org;
		this.fscl_yr = fsclyr;
		this.pgm = pgm;
		this.ed_span = edSpan;
		this.proj_dtl = projDtl;
	}
	
	public String getFund() {
		return fund;
	}

	public void setFund(String fund) {
		this.fund = fund;
	}

	public String getFscl_yr() {
		return fscl_yr;
	}

	public void setFscl_yr(String fscl_yr) {
		this.fscl_yr = fscl_yr;
	}

	public String getFunc() {
		return func;
	}

	public void setFunc(String func) {
		this.func = func;
	}

	public String getObj() {
		return obj;
	}

	public void setObj(String obj) {
		this.obj = obj;
	}

	public String getSobj() {
		return sobj;
	}

	public void setSobj(String sobj) {
		this.sobj = sobj;
	}

	public String getOrg() {
		return org;
	}

	public void setOrg(String org) {
		this.org = org;
	}

	public String getPgm() {
		return pgm;
	}

	public void setPgm(String pgm) {
		this.pgm = pgm;
	}

	public String getEd_span() {
		return ed_span;
	}

	public void setEd_span(String ed_span) {
		this.ed_span = ed_span;
	}

	public String getProj_dtl() {
		return proj_dtl;
	}

	public void setProj_dtl(String proj_dtl) {
		this.proj_dtl = proj_dtl;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(fund);
		sb.append(func);
		sb.append(obj);
		sb.append(sobj);
		sb.append(org);
		sb.append(fscl_yr);
		sb.append(pgm);
		sb.append(ed_span);
		sb.append(proj_dtl);
		return sb.toString();
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getPercent() {
		return percent;
	}

	public void setPercent(double percent) {
		this.percent = percent;
	}

	public String getAccountCode() {
		return accountCode;
	}

	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}
	
	public String getFormattedAccountCode() {
		StringBuilder sb = new StringBuilder();

		sb.append(fund);
		sb.append("-");
		sb.append(func);
		sb.append("-");		
		sb.append(obj);
		sb.append(".");		
		sb.append(sobj);
		sb.append("-");		
		sb.append(org);
		sb.append("-");		
		sb.append(fscl_yr);
		sb.append(pgm);
		sb.append(ed_span);
		sb.append(proj_dtl);
		
		return sb.toString(); 
	}
}