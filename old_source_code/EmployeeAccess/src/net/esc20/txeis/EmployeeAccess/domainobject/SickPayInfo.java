package net.esc20.txeis.EmployeeAccess.domainobject;

import java.io.Serializable;
import java.math.BigDecimal;

import net.esc20.txeis.EmployeeAccess.domainobject.reference.Frequency;

public class SickPayInfo implements Serializable
{
	private static final long serialVersionUID = 3284489142964135364L;
	
	private Frequency frequency;
	private BigDecimal withholdingGross;
	private BigDecimal withholdingTax;
	private BigDecimal ficaGross;
	private BigDecimal ficaTax;
	private BigDecimal medicareGross;
	private BigDecimal medicareTax;
	private BigDecimal nonTaxablePay;
	
	public Frequency getFrequency() {
		return frequency;
	}
	public void setFrequency(Frequency frequency) {
		this.frequency = frequency;
	}
	public BigDecimal getWithholdingGross() {
		return withholdingGross;
	}
	public void setWithholdingGross(BigDecimal withholdingGross) {
		this.withholdingGross = withholdingGross;
	}
	public BigDecimal getWithholdingTax() {
		return withholdingTax;
	}
	public void setWithholdingTax(BigDecimal withholdingTax) {
		this.withholdingTax = withholdingTax;
	}
	public BigDecimal getFicaGross() {
		return ficaGross;
	}
	public void setFicaGross(BigDecimal ficaGross) {
		this.ficaGross = ficaGross;
	}
	public BigDecimal getFicaTax() {
		return ficaTax;
	}
	public void setFicaTax(BigDecimal ficaTax) {
		this.ficaTax = ficaTax;
	}
	public BigDecimal getMedicareGross() {
		return medicareGross;
	}
	public void setMedicareGross(BigDecimal medicareGross) {
		this.medicareGross = medicareGross;
	}
	public BigDecimal getMedicareTax() {
		return medicareTax;
	}
	public void setMedicareTax(BigDecimal medicareTax) {
		this.medicareTax = medicareTax;
	}
	public BigDecimal getNonTaxablePay() {
		return nonTaxablePay;
	}
	public void setNonTaxablePay(BigDecimal nonTaxablePay) {
		this.nonTaxablePay = nonTaxablePay;
	}
}