package net.esc20.txeis.EmployeeAccess.domainobject;

import java.io.Serializable;
import net.esc20.txeis.EmployeeAccess.util.StringUtil;

public class EarningsInfo implements Serializable
{
	private static final long serialVersionUID = -1023249528812172648L;
	
	private String campusId="";
	private String campusName="";
	private String checkNumber="";
	private String withholdingStatus="";
	private String periodEndingDate="";
	private String numExceptions="";
	private String periodBeginningDate="";
	
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
