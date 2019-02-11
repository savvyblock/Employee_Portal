package net.esc20.txeis.EmployeeAccess.domainobject;

import java.io.Serializable;
import java.math.BigDecimal;

import net.esc20.txeis.EmployeeAccess.domainobject.reference.Frequency;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.ICode;

public class Deduction implements Serializable
{
	private static final long serialVersionUID = -4153458170355216220L;

	private Frequency frequency;
	private ICode code;
	private BigDecimal amount;
	private boolean cafeteria;
	private BigDecimal employerContributionAmount;
	
	public Frequency getFrequency() {
		return frequency;
	}

	public void setFrequency(Frequency frequency) {
		this.frequency = frequency;
	}

	public ICode getCode() {
		return code;
	}
	
	public void setCode(ICode code)
	{
		this.code = code;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public boolean isCafeteria() {
		return cafeteria;
	}

	public void setCafeteria(boolean cafeteria) {
		this.cafeteria = cafeteria;
	}

	public BigDecimal getEmployerContributionAmount() {
		return employerContributionAmount;
	}

	public void setEmployerContributionAmount(BigDecimal employerContributionAmount) {
		this.employerContributionAmount = employerContributionAmount;
	}
}