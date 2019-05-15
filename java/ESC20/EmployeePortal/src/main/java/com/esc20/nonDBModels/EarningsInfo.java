package com.esc20.nonDBModels;

import java.io.Serializable;

import com.esc20.util.StringUtil;


public class EarningsInfo implements Serializable {
	private static final long serialVersionUID = 6715142900654758429L;
	
	private String campusId="";
	private String campusName="";
	private String checkNumber="";
	private String withholdingStatus="";
	private String periodEndingDate="";
	private String numExceptions="";
	private String periodBeginningDate="";
	
	public EarningsInfo() {
		
	}

	public EarningsInfo(Object payCampus, Object campusName, Object chkNbr, Object dtPayperEnd, Object maritalStatTax, Object nbrTaxExempts,
			Object dtPayperBeg) {
		this.campusId=(String) payCampus;
		this.campusName= (String) campusName;
		this.checkNumber = (String) chkNbr;
		this.periodEndingDate = (String) dtPayperEnd;
		this.withholdingStatus = ((Character) maritalStatTax).toString();
		this.numExceptions = ((Short) nbrTaxExempts).toString();
		this.periodBeginningDate = (String) dtPayperBeg;
	}
	
	public String getCampusId() {
		return campusId;
	}
	public void setCampusId(String campusId) {
		this.campusId = campusId;
	}
	public String getCampusName() {
		return campusName;
	}
	public void setCampusName(String campusName) {
		this.campusName = campusName;
	}
	public String getCheckNumber() {
		return checkNumber;
	}
	public void setCheckNumber(String checkNumber) {
		this.checkNumber = checkNumber;
	}
	public String getWithholdingStatus() {
		return withholdingStatus;
	}
	public void setWithholdingStatus(String withholdingStatus) {
		this.withholdingStatus = withholdingStatus;
	}
	public String getPeriodEndingDate() {
		return periodEndingDate;
	}
	public void setPeriodEndingDate(String periodEndingDate) {
		this.periodEndingDate = periodEndingDate;
	}
	public String getNumExceptions() {
		return numExceptions;
	}
	public void setNumExceptions(String numExceptions) {
		this.numExceptions = numExceptions;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	
	public void formatPeriodEndingDate()
	{
		periodEndingDate = StringUtil.mid(periodEndingDate, 5, 2) + "-" + StringUtil.right(periodEndingDate, 2) + "-" + StringUtil.left(periodEndingDate, 4);
	}
	public String getPeriodBeginningDate() {
		return periodBeginningDate;
	}
	public void setPeriodBeginningDate(String periodBeginningDate) {
		this.periodBeginningDate = periodBeginningDate;
	}
	public void formatPeriodBeginningDate()
	{
		periodBeginningDate = StringUtil.mid(periodBeginningDate, 5, 2) + "-" + StringUtil.right(periodBeginningDate, 2) + "-" + StringUtil.left(periodBeginningDate, 4);
	}

}
