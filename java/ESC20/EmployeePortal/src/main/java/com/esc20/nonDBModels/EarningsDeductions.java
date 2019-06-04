package com.esc20.nonDBModels;

import java.io.Serializable;
import java.math.BigDecimal;

import com.esc20.util.NumberUtil;

public class EarningsDeductions implements Serializable{

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
	private BigDecimal taxableWage;
	private BigDecimal ficaWage;
	private BigDecimal medGross;
	
	public EarningsDeductions(BigDecimal stdGross, BigDecimal grossPayTot, BigDecimal absDedAmt,
			BigDecimal absDedCoded, BigDecimal nontrsNonpayBusAllow, BigDecimal whTax, BigDecimal medTax,
			BigDecimal extraDutyGross, BigDecimal ovtmGross, BigDecimal absDedRefund, BigDecimal taxedBenefits,
			BigDecimal eicAmt, BigDecimal nontrsSuppl, BigDecimal nontrsTaxPymtAmt, BigDecimal nontrsNontaxPymtAmt,
			BigDecimal trsSupplComp, BigDecimal ficaTax, BigDecimal trsSalaryRed, BigDecimal trsInsAmt,
			BigDecimal totAddlDed, BigDecimal netPay, BigDecimal nontrsNontaxNonpayAllow, BigDecimal whGross, BigDecimal ficaGross, BigDecimal medGross) {
		BigDecimal value = absDedCoded.setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal absenceRefund = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
		if (value.compareTo(BigDecimal.ZERO) < 0) {
			absenceRefund = absDedRefund.subtract(absDedCoded);
		}
		else {
			absenceRefund = absDedRefund;
		}
		absenceRefund = absenceRefund.setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal nonTrsTax = NumberUtil.value(nontrsTaxPymtAmt).add(NumberUtil.value(nontrsSuppl));
		BigDecimal total = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
		if (value.compareTo(BigDecimal.ZERO) > 0) {
			total = grossPayTot.add(absDedAmt).add(nontrsNonpayBusAllow).add(absDedCoded);
		}
		else {
			total = grossPayTot.add(absDedAmt).add(nontrsNonpayBusAllow);
		}
		total = total.setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal absenceDeduct = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP);
		if (value.compareTo(BigDecimal.ZERO) > 0) {
			absenceDeduct = absDedAmt.add(absDedCoded);
		}
		else {
			absenceDeduct = NumberUtil.value(absDedAmt);
		}
		absenceDeduct = absenceDeduct.setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal totalDed = NumberUtil.value(medTax).add(NumberUtil.value(ficaTax)).add(NumberUtil.value(whTax)).add(NumberUtil.value(trsSalaryRed)).add(NumberUtil.value(trsInsAmt)).add(NumberUtil.value(totAddlDed));
		if (absenceRefund.doubleValue() < 0) {
			absenceRefund = absenceRefund.multiply(new BigDecimal(-1));
		}
		if (absenceDeduct.doubleValue() < 0) {
			absenceDeduct = absenceDeduct.multiply(new BigDecimal(-1));
		}
		if (absenceDeduct.doubleValue() > 0) {
			totalDed = totalDed.add(absenceDeduct);
		}

		int decimalPlaces= 2;

		this.setStandardGross(NumberUtil.value(stdGross));
		this.setSupplementalPay(NumberUtil.value(extraDutyGross));
		this.setOvertimePay(NumberUtil.value(ovtmGross));
		this.setAbsenceRefund(absenceRefund.setScale(decimalPlaces,BigDecimal.ROUND_UP));
		this.setTaxedFringe(NumberUtil.value(taxedBenefits));
		this.setEarnedIncomeCred(NumberUtil.value(eicAmt));
		this.setNonTrsTax(nonTrsTax.setScale(decimalPlaces,BigDecimal.ROUND_UP));
		this.setNonTrsNonTax(NumberUtil.value(nontrsNontaxPymtAmt));
		this.setTrsSupplemental(NumberUtil.value(trsSupplComp));
		this.setTotalEarnings(total.setScale(decimalPlaces,BigDecimal.ROUND_UP));
		this.setAbsenceDed(absenceDeduct.setScale(decimalPlaces,BigDecimal.ROUND_UP));
		this.setWithholdingTax(NumberUtil.value(whTax));
		this.setFicaTax(NumberUtil.value(ficaTax));
		this.setTrsSalaryRed(NumberUtil.value(trsSalaryRed));
		this.setTrsInsurance(NumberUtil.value(trsInsAmt));
		this.setMedicareTax(NumberUtil.value(medTax));
		this.setTotOtherDed(NumberUtil.value(totAddlDed));
		this.setTotDed(totalDed.setScale(decimalPlaces,BigDecimal.ROUND_UP));
		this.setNetPay(NumberUtil.value(netPay));
		this.setNonTrsNonPayTax(NumberUtil.value(nontrsNonpayBusAllow));
		this.setNonTrsNonPayNonTax(NumberUtil.value(nontrsNontaxNonpayAllow));
		this.setTaxableWage(NumberUtil.value(nontrsNonpayBusAllow).add(NumberUtil.value(nontrsNontaxNonpayAllow)).add(NumberUtil.value(whGross)));
		this.setFicaWage(NumberUtil.value(ficaGross));
		this.setMedGross(NumberUtil.value(medGross));
	}
	public EarningsDeductions() {

	}
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
	public BigDecimal getTaxableWage() {
		return taxableWage;
	}
	public void setTaxableWage(BigDecimal taxableWage) {
		this.taxableWage = taxableWage;
	}
	public BigDecimal getFicaWage() {
		return ficaWage;
	}
	public void setFicaWage(BigDecimal ficaWage) {
		this.ficaWage = ficaWage;
	}
	public BigDecimal getMedGross() {
		return medGross;
	}
	public void setMedGross(BigDecimal medGross) {
		this.medGross = medGross;
	}
}
