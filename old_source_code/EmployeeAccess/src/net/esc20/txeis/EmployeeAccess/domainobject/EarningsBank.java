package net.esc20.txeis.EmployeeAccess.domainobject;

import java.io.Serializable;
import java.math.BigDecimal;

public class EarningsBank implements Serializable {

	private static final long serialVersionUID = -1023249528812172648L;
	
	private String name="";
	private String acctType="";
	private String acctNum ="";
	private BigDecimal amt;
	private String code="";
	private String acctTypeCode = "";
	
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
