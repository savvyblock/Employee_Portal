package net.esc20.txeis.EmployeeAccess.domainobject;

import java.io.Serializable;
import java.math.BigDecimal;

import net.esc20.txeis.EmployeeAccess.domainobject.reference.Frequency;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.ICode;

public class Stipend implements Serializable
{
	private static final long serialVersionUID = -8077234389548308887L;
	
	private Frequency frequency;
	private ICode extraDuty;
	private String type;
	private BigDecimal amount;
	private BigDecimal remainAmount;
	private BigDecimal remainPayments;
	
	public Frequency getFrequency() {
		return frequency;
	}
	public void setFrequency(Frequency frequency) {
		this.frequency = frequency;
	}
	public ICode getExtraDuty() {
		return extraDuty;
	}
	public void setExtraDuty(ICode extraDuty) {
		this.extraDuty = extraDuty;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public BigDecimal getRemainAmount() {
		return remainAmount;
	}
	public void setRemainAmount(BigDecimal remainAmount) {
		this.remainAmount = remainAmount;
	}
	public BigDecimal getRemainPayments() {
		return remainPayments;
	}
	public void setRemainPayments(BigDecimal remainPayments) {
		this.remainPayments = remainPayments;
	}
}