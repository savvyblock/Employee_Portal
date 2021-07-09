package com.esc20.nonDBModels;


/**
 * @author Jason Parker
 *
 */

public class Fund
{	
	private String fund = null;
	private String fiscalYr = null;
	private String description = null;
	
	public String getFund() {
		return fund;
	}
	public void setFund(String fund) {
		this.fund = fund;
	}
	public String getFiscalYr() {
		return fiscalYr;
	}
	public void setFiscalYr(String fiscalYr) {
		this.fiscalYr = fiscalYr;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
