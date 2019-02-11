package net.esc20.txeis.EmployeeAccess.domainobject.reference;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;

public class LeaveUnitsConversion implements Serializable {

	private static final long serialVersionUID = 4420277726554684192L;
	
	public static final String UNIT_TYPE_DAYS = "D";
	public static final String UNIT_TYPE_HOURS = "H";
	
	private String unitType=UNIT_TYPE_HOURS;
	private String leaveType="";
	private BigDecimal fractionalAmount;
	private BigDecimal fromUnit;
	private BigDecimal toUnit;
	
	public String getUnitType() {
		return unitType;
	}
	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}
	public String getLeaveType() {
		return leaveType;
	}
	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}
	public BigDecimal getFractionalAmount() {
		return fractionalAmount;
	}
	public void setFractionalAmount(BigDecimal fractionalAmount) {
		this.fractionalAmount = fractionalAmount.setScale(3, BigDecimal.ROUND_HALF_UP);
	}
	public String getFractionalAmountLabel() {
		return (new DecimalFormat("0.000")).format(fractionalAmount);
	}
	public BigDecimal getFromUnit() {
		return fromUnit;
	}
	public void setFromUnit(BigDecimal fromUnit) {
		this.fromUnit = fromUnit.setScale(3, BigDecimal.ROUND_HALF_UP);
	}
	public BigDecimal getToUnit() {
		return toUnit;
	}
	public void setToUnit(BigDecimal toUnit) {
		this.toUnit = toUnit.setScale(3, BigDecimal.ROUND_HALF_UP);
	}
	public String getUnitsRangeLabel() {
		if (unitType.equals(UNIT_TYPE_DAYS)) {
			return String.format("%.3f", fromUnit.doubleValue()) + " - " + String.format("%.3f", toUnit.doubleValue());
		} else {
			return String.format("%02d", fromUnit.intValue()) + " - " + String.format("%02d", toUnit.intValue());
		}
		
	}
}
