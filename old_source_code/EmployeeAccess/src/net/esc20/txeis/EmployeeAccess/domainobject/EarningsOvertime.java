package net.esc20.txeis.EmployeeAccess.domainobject;

import java.io.Serializable;
import java.math.BigDecimal;

import net.esc20.txeis.EmployeeAccess.domainobject.reference.ICode;

public class EarningsOvertime implements Serializable {

	ICode code;
	BigDecimal overtimeUnits;
	BigDecimal overtimeRate;
	BigDecimal thisPeriod;
	
	public ICode getCode() {
		return code;
	}
	
	public void setCode(ICode code)
	{
		this.code = code;
	}
	
	public BigDecimal getOvertimeUnits() {
		return overtimeUnits;
	}
	
	public void setOvertimeUnits(BigDecimal overtimeUnits)
	{
		this.overtimeUnits = overtimeUnits;
	}
	
	public BigDecimal getOvertimeRate() {
		return overtimeRate;
	}
	
	public void setOvertimeRate(BigDecimal overtimeRate)
	{
		this.overtimeRate = overtimeRate;
	}
	
	public BigDecimal getThisPeriod() {
		return thisPeriod;
	}
	
	public void setThisPeriod(BigDecimal thisPeriod)
	{
		this.thisPeriod = thisPeriod;
	}

}