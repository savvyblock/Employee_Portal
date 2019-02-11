package net.esc20.txeis.EmployeeAccess.domainobject;

import java.io.Serializable;
import java.math.BigDecimal;

public class EarningsDeductions implements Serializable
{
	private static final long serialVersionUID = -1023249528812172648L;
	
	private BigDecimal standardGross ;
	private BigDecimal supplementalPay ;
	private BigDecimal overtimePay;
	private BigDecimal absenceRefund;
	private BigDecimal taxedFringe;
	private BigDecimal earnedIncomeCred;
	private BigDecimal nonTrsTax;
	private BigDecimal nonTrsNonTax;
	private BigDecimal trsSupplemental;
	private BigDecimal totalEarnings ;
	private BigDecimal absenceDed;
	private BigDecimal withholdingTax;
	private BigDecimal ficaTax;
	private BigDecimal medicareTax;
	private BigDecimal trsSalaryRed;
	private BigDecimal trsInsurance;
	private BigDecimal totOtherDed;
	private BigDecimal totDed;
	private BigDecimal netPay;
	private BigDecimal nonTrsNonPayTax;
	private BigDecimal nonTrsNonPayNonTax;
	
	public BigDecimal getStandardGross() {
		return standardGross;
	}
	public void setStandardGross(BigDecimal standardGross) {
		this.standardGross = standardGross;
	}
	public BigDecimal getTotalEarnings() {
		return totalEarnings;
	}
	public void setTotalEarnings(BigDecimal totalEarnings) {
		this.totalEarnings = totalEarnings;
	}
	public BigDecimal getWithholdingTax() {
		return withholdingTax;
	}
	public void setWithholdingTax(BigDecimal withholdingTax) {
		this.withholdingTax = withholdingTax;
	}
	public BigDecimal getMedicareTax() {
		return medicareTax;
	}
	public void setMedicareTax(BigDecimal medicareTax) {
		this.medicareTax = medicareTax;
	}
	public BigDecimal getNonTrsNonPayNonTax() {
		return nonTrsNonPayNonTax;
	}
	public void setNonTrsNonPayNonTax(BigDecimal nonTrsNonPayNonTax) {
		this.nonTrsNonPayNonTax = nonTrsNonPayNonTax;
	}
	public BigDecimal getSupplementalPay() {
		return supplementalPay;
	}
	public void setSupplementalPay(BigDecimal supplementalPay) {
		this.supplementalPay = supplementalPay;
	}
	public BigDecimal getOvertimePay() {
		return overtimePay;
	}
	public void setOvertimePay(BigDecimal overtimePay) {
		this.overtimePay = overtimePay;
	}
	public BigDecimal getAbsenceRefund() {
		return absenceRefund;
	}
	public void setAbsenceRefund(BigDecimal absenceRefund) {
		this.absenceRefund = absenceRefund;
	}
	public BigDecimal getTaxedFringe() {
		return taxedFringe;
	}
	public void setTaxedFringe(BigDecimal taxedFringe) {
		this.taxedFringe = taxedFringe;
	}
	public BigDecimal getEarnedIncomeCred() {
		return earnedIncomeCred;
	}
	public void setEarnedIncomeCred(BigDecimal earnedIncomeCred) {
		this.earnedIncomeCred = earnedIncomeCred;
	}
	public BigDecimal getNonTrsTax() {
		return nonTrsTax;
	}
	public void setNonTrsTax(BigDecimal nonTrsTax) {
		this.nonTrsTax = nonTrsTax;
	}
	public BigDecimal getNonTrsNonTax() {
		return nonTrsNonTax;
	}
	public void setNonTrsNonTax(BigDecimal nonTrsNonTax) {
		this.nonTrsNonTax = nonTrsNonTax;
	}
	public BigDecimal getTrsSupplemental() {
		return trsSupplemental;
	}
	public void setTrsSupplemental(BigDecimal trsSupplemental) {
		this.trsSupplemental = trsSupplemental;
	}
	public BigDecimal getAbsenceDed() {
		return absenceDed;
	}
	public void setAbsenceDed(BigDecimal absenceDed) {
		this.absenceDed = absenceDed;
	}
	public BigDecimal getFicaTax() {
		return ficaTax;
	}
	public void setFicaTax(BigDecimal ficaTax) {
		this.ficaTax = ficaTax;
	}
	public BigDecimal getTrsSalaryRed() {
		return trsSalaryRed;
	}
	public void setTrsSalaryRed(BigDecimal trsSalaryRed) {
		this.trsSalaryRed = trsSalaryRed;
	}
	public BigDecimal getTrsInsurance() {
		return trsInsurance;
	}
	public void setTrsInsurance(BigDecimal trsInsurance) {
		this.trsInsurance = trsInsurance;
	}
	public BigDecimal getTotOtherDed() {
		return totOtherDed;
	}
	public void setTotOtherDed(BigDecimal totOtherDed) {
		this.totOtherDed = totOtherDed;
	}
	public BigDecimal getTotDed() {
		return totDed;
	}
	public void setTotDed(BigDecimal totDed) {
		this.totDed = totDed;
	}
	public BigDecimal getNetPay() {
		return netPay;
	}
	public void setNetPay(BigDecimal netPay) {
		this.netPay = netPay;
	}
	public BigDecimal getNonTrsNonPayTax() {
		return nonTrsNonPayTax;
	}
	public void setNonTrsNonPayTax(BigDecimal nonTrsNonPayTax) {
		this.nonTrsNonPayTax = nonTrsNonPayTax;
	}
}
