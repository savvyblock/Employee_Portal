package com.esc20.nonDBModels;

import java.io.Serializable;
import java.math.BigDecimal;

public class PaymentInfo implements Serializable {
	private static final long serialVersionUID = 5325397396612731838L;

	private String calYr = "";
	private BigDecimal withholdingGross;
	private BigDecimal withholdingTax;
	private BigDecimal earnedIncomeCredit;
	private BigDecimal ficaGross;
	private BigDecimal ficaTax;
	private BigDecimal medicareGross;
	private BigDecimal medicareTax;
	private BigDecimal dependentCare;

	private BigDecimal annuityDeduction;
	//private BigDecimal annuities457;
	private BigDecimal annuity457Employee;
	private BigDecimal annuity457Employer;
	private BigDecimal annuity457Withdraw;
	private BigDecimal annuities401A;
	private BigDecimal roth403BAfterTax;
	private BigDecimal taxableBenefits;

	private BigDecimal nonTrsBusinessExpense;
	private BigDecimal nonTrsReimbursementBase;
	private BigDecimal nonTrsReimbursementExcess;
	private BigDecimal movingExpenseReimbursement;
	private BigDecimal travelPayNonTaxable;
	private BigDecimal nonTrsNonTaxBusinessAllow;
	private BigDecimal nonTrsNonTaxNonPayAllow;

	private BigDecimal trsDeposit;
	private BigDecimal salaryReduction;
	//private BigDecimal trsDeposit;
	private BigDecimal hsaEmployerContribution;
	private BigDecimal hsaEmployeeSalaryReductionContribution;

	private BigDecimal taxedLifeContribution;
	private BigDecimal taxedGroupContribution;
	private BigDecimal healthInsuranceDeduction;

	private BigDecimal emplrPrvdHlthcare;	//For calendar year >= 2011
	private BigDecimal hireExemptWgs;		//For calendar year >= 2010
	
	private BigDecimal annuityRoth457b; //for calendar year >= 2016

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
	public BigDecimal getEarnedIncomeCredit() {
		return earnedIncomeCredit;
	}
	public void setEarnedIncomeCredit(BigDecimal earnedIncomeCredit) {
		this.earnedIncomeCredit = earnedIncomeCredit;
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
	public BigDecimal getDependentCare() {
		return dependentCare;
	}
	public void setDependentCare(BigDecimal dependentCare) {
		this.dependentCare = dependentCare;
	}
	public BigDecimal getAnnuityDeduction() {
		return annuityDeduction;
	}
	public void setAnnuityDeduction(BigDecimal annuityDeduction) {
		this.annuityDeduction = annuityDeduction;
	}
//	public BigDecimal getAnnuities457() {
//		return annuities457;
//	}
//	public void setAnnuities457(BigDecimal annuities457) {
//		this.annuities457 = annuities457;
//	}
	public BigDecimal getAnnuity457Employee() {
		return annuity457Employee;
	}
	public void setAnnuity457Employee(BigDecimal annuity457Employee) {
		this.annuity457Employee = annuity457Employee;
	}
	public BigDecimal getAnnuity457Employer() {
		return annuity457Employer;
	}
	public void setAnnuity457Employer(BigDecimal annuity457Employer) {
		this.annuity457Employer = annuity457Employer;
	}
	public BigDecimal getAnnuity457Withdraw() {
		return annuity457Withdraw;
	}
	public void setAnnuity457Withdraw(BigDecimal annuity457Withdraw) {
		this.annuity457Withdraw = annuity457Withdraw;
	}
	public BigDecimal getAnnuities401A() {
		return annuities401A;
	}
	public void setAnnuities401A(BigDecimal annuities401A) {
		this.annuities401A = annuities401A;
	}
	public BigDecimal getRoth403BAfterTax() {
		return roth403BAfterTax;
	}
	public void setRoth403BAfterTax(BigDecimal roth403BAfterTax) {
		this.roth403BAfterTax = roth403BAfterTax;
	}
	public BigDecimal getTaxableBenefits() {
		return taxableBenefits;
	}
	public void setTaxableBenefits(BigDecimal taxableBenefits) {
		this.taxableBenefits = taxableBenefits;
	}
	public BigDecimal getNonTrsBusinessExpense() {
		return nonTrsBusinessExpense;
	}
	public void setNonTrsBusinessExpense(BigDecimal nonTrsBusinessExpense) {
		this.nonTrsBusinessExpense = nonTrsBusinessExpense;
	}
	public BigDecimal getNonTrsReimbursementBase() {
		return nonTrsReimbursementBase;
	}
	public void setNonTrsReimbursementBase(BigDecimal nonTrsReimbursementBase) {
		this.nonTrsReimbursementBase = nonTrsReimbursementBase;
	}
	public BigDecimal getNonTrsReimbursementExcess() {
		return nonTrsReimbursementExcess;
	}
	public void setNonTrsReimbursementExcess(BigDecimal nonTrsReimbursementExcess) {
		this.nonTrsReimbursementExcess = nonTrsReimbursementExcess;
	}
	public BigDecimal getMovingExpenseReimbursement() {
		return movingExpenseReimbursement;
	}
	public void setMovingExpenseReimbursement(BigDecimal movingExpenseReimbursement) {
		this.movingExpenseReimbursement = movingExpenseReimbursement;
	}
	public BigDecimal getTravelPayNonTaxable() {
		return travelPayNonTaxable;
	}
	public void setTravelPayNonTaxable(BigDecimal travelPayNonTaxable) {
		this.travelPayNonTaxable = travelPayNonTaxable;
	}
	public BigDecimal getNonTrsNonTaxBusinessAllow() {
		return nonTrsNonTaxBusinessAllow;
	}
	public void setNonTrsNonTaxBusinessAllow(BigDecimal nonTrsNonTaxBusinessAllow) {
		this.nonTrsNonTaxBusinessAllow = nonTrsNonTaxBusinessAllow;
	}
	public BigDecimal getNonTrsNonTaxNonPayAllow() {
		return nonTrsNonTaxNonPayAllow;
	}
	public void setNonTrsNonTaxNonPayAllow(BigDecimal nonTrsNonTaxNonPayAllow) {
		this.nonTrsNonTaxNonPayAllow = nonTrsNonTaxNonPayAllow;
	}
	public BigDecimal getTrsInsurance()
	{
		return getTrsDeposit().subtract(getSalaryReduction());
	}
	public BigDecimal getTrsDeposit() {
		return trsDeposit;
	}
	public void setTrsDeposit(BigDecimal trsDeposit) {
		this.trsDeposit = trsDeposit;
	}
	public BigDecimal getSalaryReduction() {
		return salaryReduction;
	}
	public void setSalaryReduction(BigDecimal salaryReduction) {
		this.salaryReduction = salaryReduction;
	}
//	public BigDecimal getTrsDeposit() {
//		return trsDeposit;
//	}
//	public void setTrsDeposit(BigDecimal trsDeposit) {
//		this.trsDeposit = trsDeposit;
//	}
	public BigDecimal getHsaEmployerContribution() {
		return hsaEmployerContribution;
	}
	public void setHsaEmployerContribution(BigDecimal hsaEmployerContribution) {
		this.hsaEmployerContribution = hsaEmployerContribution;
	}
	public BigDecimal getHsaEmployeeSalaryReductionContribution() {
		return hsaEmployeeSalaryReductionContribution;
	}
	public void setHsaEmployeeSalaryReductionContribution(
			BigDecimal hsaEmployeeSalaryReductionContribution) {
		this.hsaEmployeeSalaryReductionContribution = hsaEmployeeSalaryReductionContribution;
	}
	public BigDecimal getTaxedLifeContribution() {
		return taxedLifeContribution;
	}
	public void setTaxedLifeContribution(BigDecimal taxedLifeContribution) {
		this.taxedLifeContribution = taxedLifeContribution;
	}
	public BigDecimal getTaxedGroupContribution() {
		return taxedGroupContribution;
	}
	public void setTaxedGroupContribution(BigDecimal taxedGroupContribution) {
		this.taxedGroupContribution = taxedGroupContribution;
	}
	public BigDecimal getHealthInsuranceDeduction() {
		return healthInsuranceDeduction;
	}
	public void setHealthInsuranceDeduction(BigDecimal healthInsuranceDeduction) {
		this.healthInsuranceDeduction = healthInsuranceDeduction;
	}

	//************************************************************************
	//For calendar year >= 2011
	public BigDecimal getEmplrPrvdHlthcare() {
		return emplrPrvdHlthcare;
	}

	//ig20100930 - Added
	public void setEmplrPrvdHlthcare(BigDecimal emplrPrvdHlthcare) {
		this.emplrPrvdHlthcare = emplrPrvdHlthcare;
	}

	//************************************************************************
	//ig20100930 - Added
	public String getCalYr() {
		return calYr;
	}

	//ig20100930 - Added
	public void setCalYr(String calYr) {
		this.calYr = calYr;
	}

	//************************************************************************
	//For calendar year >= 2010
	public BigDecimal getHireExemptWgs() {
		return hireExemptWgs;
	}
	public void setHireExemptWgs(BigDecimal hireExemptWgs) {
		this.hireExemptWgs = hireExemptWgs;
	}
	//************************************************************************
	
	public BigDecimal getAnnuityRoth457b() {
		return annuityRoth457b;
	}

	public void setAnnuityRoth457b(BigDecimal annuityRoth457b) {
		this.annuityRoth457b = annuityRoth457b;
	}
}