package com.esc20.nonDBModels;

import java.io.Serializable;
import java.math.BigDecimal;

public class CalYTDPrint implements Serializable {

	private static final long serialVersionUID = -566792117257618330L;
	
	//district info
	private String dname;
	private String daddress;
	private String dcityst;
	
	//employee info
	private String ename;
	private String employeeNumber;
	
	//calendar info
	private String calYr = "";
	private String frequency;
	private String lastPostedPayDate;
	
	private BigDecimal contractPay;
	private BigDecimal nonContractPay;
	private BigDecimal supplementalPay;
	
	private BigDecimal withholdingGross;
	private BigDecimal withholdingTax;
	private BigDecimal earnedIncomeCredit;
	
	private BigDecimal ficaGross;
	private BigDecimal ficaTax;
	
	private BigDecimal dependentCare;
	private BigDecimal dependentCareEmployer;
	private BigDecimal dependentCareExceeds;
	
	private BigDecimal medicareGross;
	private BigDecimal medicareTax;

	private BigDecimal annuityDeduction;
	private BigDecimal roth403BAfterTax;
	private BigDecimal taxableBenefits;
	
	private BigDecimal annuity457Employee;
	private BigDecimal annuity457Employer;
	private BigDecimal annuity457Withdraw;
	
	private BigDecimal nonTrsBusinessExpense;
	private BigDecimal nonTrsReimbursementBase;
	private BigDecimal nonTrsReimbursementExcess;
	
	private BigDecimal movingExpenseReimbursement;
	private BigDecimal nonTrsNonTaxBusinessAllow;
	private BigDecimal nonTrsNonTaxNonPayAllow;

	private BigDecimal salaryReduction;
	private BigDecimal trsInsurance;
	
	private BigDecimal hsaEmployerContribution;
	private BigDecimal hsaEmployeeSalaryReductionContribution;
	private BigDecimal hireExemptWgs;

	private BigDecimal taxedLifeContribution;
	private BigDecimal taxedGroupContribution;
	private BigDecimal healthInsuranceDeduction;

	private BigDecimal emplrPrvdHlthcare;
	private BigDecimal annuityRoth457b;
	
	public String getDname() {
		return dname;
	}
	public void setDname(String dname) {
		this.dname = dname;
	}
	public String getDaddress() {
		return daddress;
	}
	public void setDaddress(String daddress) {
		this.daddress = daddress;
	}
	public String getDcityst() {
		return dcityst;
	}
	public void setDcityst(String dcityst) {
		this.dcityst = dcityst;
	}
	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}
	public String getEmployeeNumber() {
		return employeeNumber;
	}
	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
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
	}public BigDecimal getAnnuity457Employee() {
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
	public BigDecimal getTrsInsurance() {
		return trsInsurance;
	}
	public void setTrsInsurance(BigDecimal trsInsurance) {
		this.trsInsurance = trsInsurance;
	}
	public BigDecimal getSalaryReduction() {
		return salaryReduction;
	}
	public void setSalaryReduction(BigDecimal salaryReduction) {
		this.salaryReduction = salaryReduction;
	}
	public String getLastPostedPayDate() {
		return lastPostedPayDate;
	}
	public void setLastPostedPayDate(String lastPostedPayDate) {
		this.lastPostedPayDate = lastPostedPayDate;
	}
	public String getFrequency() {
		return frequency;
	}
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	public BigDecimal getContractPay() {
		return contractPay;
	}
	public void setContractPay(BigDecimal contractPay) {
		this.contractPay = contractPay;
	}
	public BigDecimal getNonContractPay() {
		return nonContractPay;
	}
	public void setNonContractPay(BigDecimal nonContractPay) {
		this.nonContractPay = nonContractPay;
	}
	public BigDecimal getSupplementalPay() {
		return supplementalPay;
	}
	public void setSupplementalPay(BigDecimal supplementalPay) {
		this.supplementalPay = supplementalPay;
	}
	public BigDecimal getDependentCareEmployer() {
		return dependentCareEmployer;
	}
	public void setDependentCareEmployer(BigDecimal dependentCareEmployer) {
		this.dependentCareEmployer = dependentCareEmployer;
	}
	public BigDecimal getDependentCareExceeds() {
		return dependentCareExceeds;
	}
	public void setDependentCareExceeds(BigDecimal dependentCareExceeds) {
		this.dependentCareExceeds = dependentCareExceeds;
	}
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
	public BigDecimal getEmplrPrvdHlthcare() {
		return emplrPrvdHlthcare;
	}
	public void setEmplrPrvdHlthcare(BigDecimal emplrPrvdHlthcare) {
		this.emplrPrvdHlthcare = emplrPrvdHlthcare;
	}
	public String getCalYr() {
		return calYr;
	}
	public void setCalYr(String calYr) {
		this.calYr = calYr;
	}
	public BigDecimal getHireExemptWgs() {
		return hireExemptWgs;
	}
	public void setHireExemptWgs(BigDecimal hireExemptWgs) {
		this.hireExemptWgs = hireExemptWgs;
	}
	
	public BigDecimal getAnnuityRoth457b() {
		return annuityRoth457b;
	}
	public void setAnnuityRoth457b(BigDecimal annuityRoth457b) {
		this.annuityRoth457b = annuityRoth457b;
	}

}
