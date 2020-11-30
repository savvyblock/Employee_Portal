package com.esc20.nonDBModels;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.esc20.model.BhrCalYtd;

public class Earnings implements Serializable
{

	private static final long serialVersionUID = -1023249528812172648L;
	
	private EarningsInfo info;
	private EarningsDeductions deductions;
	private List <EarningsOther> other;
	private List <EarningsJob> job;
	private List <EarningsSupplemental> supplemental;
	private List <EarningsSupplemental> nonTrsTax;
	private List<EarningsSupplemental> nonTrsNonTax;
	private List <EarningsLeave> leave;
	private List <EarningsBank> bank;
	private List <EarningsOvertime> overtime;
	

	private BigDecimal EarningsOtherTotal;
	private BigDecimal EarningsJobTotal;
	private BigDecimal EarningsSupplementalTotal;
	private BigDecimal EarningsNonTrsTaxTotal;
	private BigDecimal EarningsNonTrsNonTaxTotal;
	private BigDecimal EarningsOtherContribTotal;
	private BigDecimal EarningsBankTotal;
	private BigDecimal EarningsOvertimeTotal;
	private BigDecimal EarningsOtherTydTotal;
	private BigDecimal emplrPrvdHlthcare;

	public Earnings() {
	}

	public Earnings(BhrCalYtd record, BigDecimal totalOtherDeductions, List<EarningsOther> otherDeductions) {
		// BRM-735 added new constructor to handle cal_ytd conversion
		this.setDeductions(new EarningsDeductions(record, totalOtherDeductions));
		this.setEmplrPrvdHlthcare(record.getEmplrPrvdHlthcare());
		this.setOther(otherDeductions);
	}

	public BigDecimal getEarningsOvertimeTotal() {
		return EarningsOvertimeTotal;
	}
	public void setEarningsOvertimeTotal(BigDecimal earningsOvertimeTotal) {
		EarningsOvertimeTotal = earningsOvertimeTotal;
	}
	public EarningsInfo getInfo() {
		return info;
	}
	public void setInfo(EarningsInfo info) {
		this.info = info;
	}
	public EarningsDeductions getDeductions() {
		return deductions;
	}
	public void setDeductions(EarningsDeductions deductions) {
		this.deductions = deductions;
	}
	public List<EarningsOther> getOther() {
		return other;
	}
	public void setOther(List<EarningsOther> other) {
		this.other = other;
	}
	public List<EarningsJob> getJob() {
		return job;
	}
	public void setJob(List<EarningsJob> job) {
		this.job = job;
	}
	public List<EarningsSupplemental> getSupplemental() {
		return supplemental;
	}
	public void setSupplemental(List<EarningsSupplemental> supplemental) {
		this.supplemental = supplemental;
	}
	
	public List<EarningsSupplemental> getNonTrsTax() {
		return nonTrsTax;
	}
	public void setNonTrsTax(List<EarningsSupplemental> nonTrsTax) {
		this.nonTrsTax = nonTrsTax;
	}
	
	public List<EarningsSupplemental> getNonTrsNonTax() {
		return nonTrsNonTax;
	}
	public void setNonTrsNonTax(List<EarningsSupplemental> nonTrsNonTax) {
		this.nonTrsNonTax = nonTrsNonTax;
	}
	public List<EarningsLeave> getLeave() {
		return leave;
	}
	public void setLeave(List<EarningsLeave> leave) {
		this.leave = leave;
	}
	public List<EarningsOvertime> getOvertime() {
		return overtime;
	}
	public void setOvertime(List<EarningsOvertime> overtime) {
		this.overtime = overtime;
	}
	public List<EarningsBank> getBank() {
		return bank;
	}
	public void setBank(List<EarningsBank> bank) {
		this.bank = bank;
	}
	public BigDecimal getEarningsOtherTotal() {
		return EarningsOtherTotal;
	}
	public void setEarningsOtherTotal(BigDecimal earningsOtherTotal) {
		EarningsOtherTotal = earningsOtherTotal;
	}
	public BigDecimal getEarningsJobTotal() {
		return EarningsJobTotal;
	}
	public void setEarningsJobTotal(BigDecimal earningsJobTotal) {
		EarningsJobTotal = earningsJobTotal;
	}
	public BigDecimal getEarningsSupplementalTotal() {
		return EarningsSupplementalTotal;
	}
	public void setEarningsSupplementalTotal(BigDecimal earningsSupplementalTotal) {
		EarningsSupplementalTotal = earningsSupplementalTotal;
	}
	public BigDecimal getEarningsNonTrsTaxTotal() {
		return EarningsNonTrsTaxTotal;
	}
	public void setEarningsNonTrsTaxTotal(BigDecimal earningsNonTrsTaxTotal) {
		EarningsNonTrsTaxTotal = earningsNonTrsTaxTotal;
	}
	public BigDecimal getEarningsNonTrsNonTaxTotal() {
		return EarningsNonTrsNonTaxTotal;
	}
	public void setEarningsNonTrsNonTaxTotal(BigDecimal earningsNonTrsNonTaxTotal) {
		EarningsNonTrsNonTaxTotal = earningsNonTrsNonTaxTotal;
	}
	public BigDecimal getEarningsOtherContribTotal() {
		return EarningsOtherContribTotal;
	}
	public void setEarningsOtherContribTotal(BigDecimal earningsOtherContribTotal) {
		EarningsOtherContribTotal = earningsOtherContribTotal;
	}
	public BigDecimal getEarningsBankTotal() {
		return EarningsBankTotal;
	}
	public void setEarningsBankTotal(BigDecimal earningsBankTotal) {
		EarningsBankTotal = earningsBankTotal;
	}
	public BigDecimal getEarningsOtherTydTotal() {
		return EarningsOtherTydTotal;
	}
	public void setEarningsOtherTydTotal(BigDecimal earningsOtherTydTotal) {
		EarningsOtherTydTotal = earningsOtherTydTotal;
	}
	public BigDecimal getEmplrPrvdHlthcare() {
		return emplrPrvdHlthcare;
	}
	public void setEmplrPrvdHlthcare(BigDecimal emplrPrvdHlthcare) {
		this.emplrPrvdHlthcare = emplrPrvdHlthcare;
	}
}

