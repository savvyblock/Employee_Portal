package com.esc20.model;
// Generated Jan 4, 2019 3:38:35 PM by Hibernate Tools 4.3.5.Final

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * BhrCalYtdBkupPaycalcs5Id generated by hbm2java
 */
@Embeddable
public class BhrCalYtdBkupPaycalcs5Id implements java.io.Serializable {
	private static final long serialVersionUID = 6715142900654758429L;
	private char cyrNyrFlg;
	private char payFreq;
	private String empNbr;
	private String calYr;
	private char ficaTyp;
	private BigDecimal contrAmt;
	private BigDecimal noncontrAmt;
	private BigDecimal supplPayAmt;
	private BigDecimal annuityDed;
	private BigDecimal ficaTax;
	private BigDecimal ficaGross;
	private BigDecimal unempIns;
	private BigDecimal unempGross;
	private BigDecimal eicAmt;
	private BigDecimal whGross;
	private BigDecimal whTax;
	private BigDecimal hlthInsDed;
	private BigDecimal dependCare;
	private BigDecimal taxedBenefits;
	private BigDecimal trsDeposit;
	private BigDecimal trsSalaryRed;
	private BigDecimal movingExpReimbr;
	private BigDecimal emplr457Contrib;
	private BigDecimal emp457Contrib;
	private BigDecimal withdraw457;
	private BigDecimal empBusinessExpense;
	private BigDecimal taxEmplrLife;
	private BigDecimal taxEmplrLifeGrp;
	private BigDecimal cafeAmt;
	private BigDecimal qtrUnemplGross;
	private BigDecimal medTax;
	private BigDecimal medGross;
	private BigDecimal nontrsReimbrExcess;
	private BigDecimal nontrsReimbrBase;
	private BigDecimal nontrsBusAllow;
	private BigDecimal nontrsNontaxBusAllow;
	private BigDecimal trsSupplComp;
	private BigDecimal teaHealthInsContrib;
	private BigDecimal nontrsNonpayBusAllow;
	private BigDecimal nontrsNontaxNonpayAllow;
	private BigDecimal emplrDependCare;
	private BigDecimal annuityRoth;
	private BigDecimal emplrDependCareTax;
	private BigDecimal hsaEmpSalRedctnContrib;
	private BigDecimal hsaEmplrContrib;
	private BigDecimal hireExemptWgs;
	private BigDecimal emplrPrvdHlthcare;
	private BigDecimal emplrFicaTax;
	private String module;
	private BigDecimal emplrMedTax;
	private BigDecimal annuityRoth457b;

	public BhrCalYtdBkupPaycalcs5Id() {
	}

	public BhrCalYtdBkupPaycalcs5Id(char cyrNyrFlg, char payFreq, String empNbr, String calYr, char ficaTyp,
			BigDecimal contrAmt, BigDecimal noncontrAmt, BigDecimal supplPayAmt, BigDecimal annuityDed,
			BigDecimal ficaTax, BigDecimal ficaGross, BigDecimal unempIns, BigDecimal unempGross, BigDecimal eicAmt,
			BigDecimal whGross, BigDecimal whTax, BigDecimal hlthInsDed, BigDecimal dependCare,
			BigDecimal taxedBenefits, BigDecimal trsDeposit, BigDecimal trsSalaryRed, BigDecimal movingExpReimbr,
			BigDecimal emplr457Contrib, BigDecimal emp457Contrib, BigDecimal withdraw457, BigDecimal empBusinessExpense,
			BigDecimal taxEmplrLife, BigDecimal taxEmplrLifeGrp, BigDecimal cafeAmt, BigDecimal qtrUnemplGross,
			BigDecimal medTax, BigDecimal medGross, BigDecimal nontrsReimbrExcess, BigDecimal nontrsReimbrBase,
			BigDecimal nontrsBusAllow, BigDecimal nontrsNontaxBusAllow, BigDecimal trsSupplComp,
			BigDecimal teaHealthInsContrib, BigDecimal nontrsNonpayBusAllow, BigDecimal nontrsNontaxNonpayAllow,
			BigDecimal emplrDependCare, BigDecimal annuityRoth, BigDecimal emplrDependCareTax,
			BigDecimal hsaEmpSalRedctnContrib, BigDecimal hsaEmplrContrib, BigDecimal hireExemptWgs,
			BigDecimal emplrPrvdHlthcare, BigDecimal emplrFicaTax, String module, BigDecimal emplrMedTax,
			BigDecimal annuityRoth457b) {
		this.cyrNyrFlg = cyrNyrFlg;
		this.payFreq = payFreq;
		this.empNbr = empNbr;
		this.calYr = calYr;
		this.ficaTyp = ficaTyp;
		this.contrAmt = contrAmt;
		this.noncontrAmt = noncontrAmt;
		this.supplPayAmt = supplPayAmt;
		this.annuityDed = annuityDed;
		this.ficaTax = ficaTax;
		this.ficaGross = ficaGross;
		this.unempIns = unempIns;
		this.unempGross = unempGross;
		this.eicAmt = eicAmt;
		this.whGross = whGross;
		this.whTax = whTax;
		this.hlthInsDed = hlthInsDed;
		this.dependCare = dependCare;
		this.taxedBenefits = taxedBenefits;
		this.trsDeposit = trsDeposit;
		this.trsSalaryRed = trsSalaryRed;
		this.movingExpReimbr = movingExpReimbr;
		this.emplr457Contrib = emplr457Contrib;
		this.emp457Contrib = emp457Contrib;
		this.withdraw457 = withdraw457;
		this.empBusinessExpense = empBusinessExpense;
		this.taxEmplrLife = taxEmplrLife;
		this.taxEmplrLifeGrp = taxEmplrLifeGrp;
		this.cafeAmt = cafeAmt;
		this.qtrUnemplGross = qtrUnemplGross;
		this.medTax = medTax;
		this.medGross = medGross;
		this.nontrsReimbrExcess = nontrsReimbrExcess;
		this.nontrsReimbrBase = nontrsReimbrBase;
		this.nontrsBusAllow = nontrsBusAllow;
		this.nontrsNontaxBusAllow = nontrsNontaxBusAllow;
		this.trsSupplComp = trsSupplComp;
		this.teaHealthInsContrib = teaHealthInsContrib;
		this.nontrsNonpayBusAllow = nontrsNonpayBusAllow;
		this.nontrsNontaxNonpayAllow = nontrsNontaxNonpayAllow;
		this.emplrDependCare = emplrDependCare;
		this.annuityRoth = annuityRoth;
		this.emplrDependCareTax = emplrDependCareTax;
		this.hsaEmpSalRedctnContrib = hsaEmpSalRedctnContrib;
		this.hsaEmplrContrib = hsaEmplrContrib;
		this.hireExemptWgs = hireExemptWgs;
		this.emplrPrvdHlthcare = emplrPrvdHlthcare;
		this.emplrFicaTax = emplrFicaTax;
		this.module = module;
		this.emplrMedTax = emplrMedTax;
		this.annuityRoth457b = annuityRoth457b;
	}

	@Column(name = "CYR_NYR_FLG", nullable = false, length = 1)
	public char getCyrNyrFlg() {
		return this.cyrNyrFlg;
	}

	public void setCyrNyrFlg(char cyrNyrFlg) {
		this.cyrNyrFlg = cyrNyrFlg;
	}

	@Column(name = "PAY_FREQ", nullable = false, length = 1)
	public char getPayFreq() {
		return this.payFreq;
	}

	public void setPayFreq(char payFreq) {
		this.payFreq = payFreq;
	}

	@Column(name = "EMP_NBR", nullable = false, length = 6)
	public String getEmpNbr() {
		return this.empNbr;
	}

	public void setEmpNbr(String empNbr) {
		this.empNbr = empNbr;
	}

	@Column(name = "CAL_YR", nullable = false, length = 4)
	public String getCalYr() {
		return this.calYr;
	}

	public void setCalYr(String calYr) {
		this.calYr = calYr;
	}

	@Column(name = "FICA_TYP", nullable = false, length = 1)
	public char getFicaTyp() {
		return this.ficaTyp;
	}

	public void setFicaTyp(char ficaTyp) {
		this.ficaTyp = ficaTyp;
	}

	@Column(name = "CONTR_AMT", nullable = false, precision = 9)
	public BigDecimal getContrAmt() {
		return this.contrAmt;
	}

	public void setContrAmt(BigDecimal contrAmt) {
		this.contrAmt = contrAmt;
	}

	@Column(name = "NONCONTR_AMT", nullable = false, precision = 9)
	public BigDecimal getNoncontrAmt() {
		return this.noncontrAmt;
	}

	public void setNoncontrAmt(BigDecimal noncontrAmt) {
		this.noncontrAmt = noncontrAmt;
	}

	@Column(name = "SUPPL_PAY_AMT", nullable = false, precision = 9)
	public BigDecimal getSupplPayAmt() {
		return this.supplPayAmt;
	}

	public void setSupplPayAmt(BigDecimal supplPayAmt) {
		this.supplPayAmt = supplPayAmt;
	}

	@Column(name = "ANNUITY_DED", nullable = false, precision = 9)
	public BigDecimal getAnnuityDed() {
		return this.annuityDed;
	}

	public void setAnnuityDed(BigDecimal annuityDed) {
		this.annuityDed = annuityDed;
	}

	@Column(name = "FICA_TAX", nullable = false, precision = 9)
	public BigDecimal getFicaTax() {
		return this.ficaTax;
	}

	public void setFicaTax(BigDecimal ficaTax) {
		this.ficaTax = ficaTax;
	}

	@Column(name = "FICA_GROSS", nullable = false, precision = 9)
	public BigDecimal getFicaGross() {
		return this.ficaGross;
	}

	public void setFicaGross(BigDecimal ficaGross) {
		this.ficaGross = ficaGross;
	}

	@Column(name = "UNEMP_INS", nullable = false, precision = 9)
	public BigDecimal getUnempIns() {
		return this.unempIns;
	}

	public void setUnempIns(BigDecimal unempIns) {
		this.unempIns = unempIns;
	}

	@Column(name = "UNEMP_GROSS", nullable = false, precision = 9)
	public BigDecimal getUnempGross() {
		return this.unempGross;
	}

	public void setUnempGross(BigDecimal unempGross) {
		this.unempGross = unempGross;
	}

	@Column(name = "EIC_AMT", nullable = false, precision = 9)
	public BigDecimal getEicAmt() {
		return this.eicAmt;
	}

	public void setEicAmt(BigDecimal eicAmt) {
		this.eicAmt = eicAmt;
	}

	@Column(name = "WH_GROSS", nullable = false, precision = 9)
	public BigDecimal getWhGross() {
		return this.whGross;
	}

	public void setWhGross(BigDecimal whGross) {
		this.whGross = whGross;
	}

	@Column(name = "WH_TAX", nullable = false, precision = 9)
	public BigDecimal getWhTax() {
		return this.whTax;
	}

	public void setWhTax(BigDecimal whTax) {
		this.whTax = whTax;
	}

	@Column(name = "HLTH_INS_DED", nullable = false, precision = 7)
	public BigDecimal getHlthInsDed() {
		return this.hlthInsDed;
	}

	public void setHlthInsDed(BigDecimal hlthInsDed) {
		this.hlthInsDed = hlthInsDed;
	}

	@Column(name = "DEPEND_CARE", nullable = false, precision = 9)
	public BigDecimal getDependCare() {
		return this.dependCare;
	}

	public void setDependCare(BigDecimal dependCare) {
		this.dependCare = dependCare;
	}

	@Column(name = "TAXED_BENEFITS", nullable = false, precision = 9)
	public BigDecimal getTaxedBenefits() {
		return this.taxedBenefits;
	}

	public void setTaxedBenefits(BigDecimal taxedBenefits) {
		this.taxedBenefits = taxedBenefits;
	}

	@Column(name = "TRS_DEPOSIT", nullable = false, precision = 9)
	public BigDecimal getTrsDeposit() {
		return this.trsDeposit;
	}

	public void setTrsDeposit(BigDecimal trsDeposit) {
		this.trsDeposit = trsDeposit;
	}

	@Column(name = "TRS_SALARY_RED", nullable = false, precision = 9)
	public BigDecimal getTrsSalaryRed() {
		return this.trsSalaryRed;
	}

	public void setTrsSalaryRed(BigDecimal trsSalaryRed) {
		this.trsSalaryRed = trsSalaryRed;
	}

	@Column(name = "MOVING_EXP_REIMBR", nullable = false, precision = 9)
	public BigDecimal getMovingExpReimbr() {
		return this.movingExpReimbr;
	}

	public void setMovingExpReimbr(BigDecimal movingExpReimbr) {
		this.movingExpReimbr = movingExpReimbr;
	}

	@Column(name = "EMPLR_457_CONTRIB", nullable = false, precision = 7)
	public BigDecimal getEmplr457Contrib() {
		return this.emplr457Contrib;
	}

	public void setEmplr457Contrib(BigDecimal emplr457Contrib) {
		this.emplr457Contrib = emplr457Contrib;
	}

	@Column(name = "EMP_457_CONTRIB", nullable = false, precision = 7)
	public BigDecimal getEmp457Contrib() {
		return this.emp457Contrib;
	}

	public void setEmp457Contrib(BigDecimal emp457Contrib) {
		this.emp457Contrib = emp457Contrib;
	}

	@Column(name = "WITHDRAW_457", nullable = false, precision = 7)
	public BigDecimal getWithdraw457() {
		return this.withdraw457;
	}

	public void setWithdraw457(BigDecimal withdraw457) {
		this.withdraw457 = withdraw457;
	}

	@Column(name = "EMP_BUSINESS_EXPENSE", nullable = false, precision = 7)
	public BigDecimal getEmpBusinessExpense() {
		return this.empBusinessExpense;
	}

	public void setEmpBusinessExpense(BigDecimal empBusinessExpense) {
		this.empBusinessExpense = empBusinessExpense;
	}

	@Column(name = "TAX_EMPLR_LIFE", nullable = false, precision = 9)
	public BigDecimal getTaxEmplrLife() {
		return this.taxEmplrLife;
	}

	public void setTaxEmplrLife(BigDecimal taxEmplrLife) {
		this.taxEmplrLife = taxEmplrLife;
	}

	@Column(name = "TAX_EMPLR_LIFE_GRP", nullable = false, precision = 9)
	public BigDecimal getTaxEmplrLifeGrp() {
		return this.taxEmplrLifeGrp;
	}

	public void setTaxEmplrLifeGrp(BigDecimal taxEmplrLifeGrp) {
		this.taxEmplrLifeGrp = taxEmplrLifeGrp;
	}

	@Column(name = "CAFE_AMT", nullable = false, precision = 9)
	public BigDecimal getCafeAmt() {
		return this.cafeAmt;
	}

	public void setCafeAmt(BigDecimal cafeAmt) {
		this.cafeAmt = cafeAmt;
	}

	@Column(name = "QTR_UNEMPL_GROSS", nullable = false, precision = 9)
	public BigDecimal getQtrUnemplGross() {
		return this.qtrUnemplGross;
	}

	public void setQtrUnemplGross(BigDecimal qtrUnemplGross) {
		this.qtrUnemplGross = qtrUnemplGross;
	}

	@Column(name = "MED_TAX", nullable = false, precision = 9)
	public BigDecimal getMedTax() {
		return this.medTax;
	}

	public void setMedTax(BigDecimal medTax) {
		this.medTax = medTax;
	}

	@Column(name = "MED_GROSS", nullable = false, precision = 9)
	public BigDecimal getMedGross() {
		return this.medGross;
	}

	public void setMedGross(BigDecimal medGross) {
		this.medGross = medGross;
	}

	@Column(name = "NONTRS_REIMBR_EXCESS", nullable = false, precision = 9)
	public BigDecimal getNontrsReimbrExcess() {
		return this.nontrsReimbrExcess;
	}

	public void setNontrsReimbrExcess(BigDecimal nontrsReimbrExcess) {
		this.nontrsReimbrExcess = nontrsReimbrExcess;
	}

	@Column(name = "NONTRS_REIMBR_BASE", nullable = false, precision = 9)
	public BigDecimal getNontrsReimbrBase() {
		return this.nontrsReimbrBase;
	}

	public void setNontrsReimbrBase(BigDecimal nontrsReimbrBase) {
		this.nontrsReimbrBase = nontrsReimbrBase;
	}

	@Column(name = "NONTRS_BUS_ALLOW", nullable = false, precision = 9)
	public BigDecimal getNontrsBusAllow() {
		return this.nontrsBusAllow;
	}

	public void setNontrsBusAllow(BigDecimal nontrsBusAllow) {
		this.nontrsBusAllow = nontrsBusAllow;
	}

	@Column(name = "NONTRS_NONTAX_BUS_ALLOW", nullable = false, precision = 9)
	public BigDecimal getNontrsNontaxBusAllow() {
		return this.nontrsNontaxBusAllow;
	}

	public void setNontrsNontaxBusAllow(BigDecimal nontrsNontaxBusAllow) {
		this.nontrsNontaxBusAllow = nontrsNontaxBusAllow;
	}

	@Column(name = "TRS_SUPPL_COMP", nullable = false, precision = 7)
	public BigDecimal getTrsSupplComp() {
		return this.trsSupplComp;
	}

	public void setTrsSupplComp(BigDecimal trsSupplComp) {
		this.trsSupplComp = trsSupplComp;
	}

	@Column(name = "TEA_HEALTH_INS_CONTRIB", nullable = false, precision = 7)
	public BigDecimal getTeaHealthInsContrib() {
		return this.teaHealthInsContrib;
	}

	public void setTeaHealthInsContrib(BigDecimal teaHealthInsContrib) {
		this.teaHealthInsContrib = teaHealthInsContrib;
	}

	@Column(name = "NONTRS_NONPAY_BUS_ALLOW", nullable = false, precision = 9)
	public BigDecimal getNontrsNonpayBusAllow() {
		return this.nontrsNonpayBusAllow;
	}

	public void setNontrsNonpayBusAllow(BigDecimal nontrsNonpayBusAllow) {
		this.nontrsNonpayBusAllow = nontrsNonpayBusAllow;
	}

	@Column(name = "NONTRS_NONTAX_NONPAY_ALLOW", nullable = false, precision = 9)
	public BigDecimal getNontrsNontaxNonpayAllow() {
		return this.nontrsNontaxNonpayAllow;
	}

	public void setNontrsNontaxNonpayAllow(BigDecimal nontrsNontaxNonpayAllow) {
		this.nontrsNontaxNonpayAllow = nontrsNontaxNonpayAllow;
	}

	@Column(name = "EMPLR_DEPEND_CARE", nullable = false, precision = 9)
	public BigDecimal getEmplrDependCare() {
		return this.emplrDependCare;
	}

	public void setEmplrDependCare(BigDecimal emplrDependCare) {
		this.emplrDependCare = emplrDependCare;
	}

	@Column(name = "ANNUITY_ROTH", nullable = false, precision = 9)
	public BigDecimal getAnnuityRoth() {
		return this.annuityRoth;
	}

	public void setAnnuityRoth(BigDecimal annuityRoth) {
		this.annuityRoth = annuityRoth;
	}

	@Column(name = "EMPLR_DEPEND_CARE_TAX", nullable = false, precision = 7)
	public BigDecimal getEmplrDependCareTax() {
		return this.emplrDependCareTax;
	}

	public void setEmplrDependCareTax(BigDecimal emplrDependCareTax) {
		this.emplrDependCareTax = emplrDependCareTax;
	}

	@Column(name = "HSA_EMP_SAL_REDCTN_CONTRIB", nullable = false, precision = 9)
	public BigDecimal getHsaEmpSalRedctnContrib() {
		return this.hsaEmpSalRedctnContrib;
	}

	public void setHsaEmpSalRedctnContrib(BigDecimal hsaEmpSalRedctnContrib) {
		this.hsaEmpSalRedctnContrib = hsaEmpSalRedctnContrib;
	}

	@Column(name = "HSA_EMPLR_CONTRIB", nullable = false, precision = 9)
	public BigDecimal getHsaEmplrContrib() {
		return this.hsaEmplrContrib;
	}

	public void setHsaEmplrContrib(BigDecimal hsaEmplrContrib) {
		this.hsaEmplrContrib = hsaEmplrContrib;
	}

	@Column(name = "HIRE_EXEMPT_WGS", nullable = false, precision = 9)
	public BigDecimal getHireExemptWgs() {
		return this.hireExemptWgs;
	}

	public void setHireExemptWgs(BigDecimal hireExemptWgs) {
		this.hireExemptWgs = hireExemptWgs;
	}

	@Column(name = "EMPLR_PRVD_HLTHCARE", nullable = false, precision = 9)
	public BigDecimal getEmplrPrvdHlthcare() {
		return this.emplrPrvdHlthcare;
	}

	public void setEmplrPrvdHlthcare(BigDecimal emplrPrvdHlthcare) {
		this.emplrPrvdHlthcare = emplrPrvdHlthcare;
	}

	@Column(name = "EMPLR_FICA_TAX", nullable = false, precision = 9)
	public BigDecimal getEmplrFicaTax() {
		return this.emplrFicaTax;
	}

	public void setEmplrFicaTax(BigDecimal emplrFicaTax) {
		this.emplrFicaTax = emplrFicaTax;
	}

	@Column(name = "MODULE", nullable = false, length = 25)
	public String getModule() {
		return this.module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	@Column(name = "EMPLR_MED_TAX", nullable = false, precision = 9)
	public BigDecimal getEmplrMedTax() {
		return this.emplrMedTax;
	}

	public void setEmplrMedTax(BigDecimal emplrMedTax) {
		this.emplrMedTax = emplrMedTax;
	}

	@Column(name = "ANNUITY_ROTH_457B", nullable = false, precision = 9)
	public BigDecimal getAnnuityRoth457b() {
		return this.annuityRoth457b;
	}

	public void setAnnuityRoth457b(BigDecimal annuityRoth457b) {
		this.annuityRoth457b = annuityRoth457b;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof BhrCalYtdBkupPaycalcs5Id))
			return false;
		BhrCalYtdBkupPaycalcs5Id castOther = (BhrCalYtdBkupPaycalcs5Id) other;

		return (this.getCyrNyrFlg() == castOther.getCyrNyrFlg()) && (this.getPayFreq() == castOther.getPayFreq())
				&& ((this.getEmpNbr() == castOther.getEmpNbr()) || (this.getEmpNbr() != null
						&& castOther.getEmpNbr() != null && this.getEmpNbr().equals(castOther.getEmpNbr())))
				&& ((this.getCalYr() == castOther.getCalYr()) || (this.getCalYr() != null
						&& castOther.getCalYr() != null && this.getCalYr().equals(castOther.getCalYr())))
				&& (this.getFicaTyp() == castOther.getFicaTyp())
				&& ((this.getContrAmt() == castOther.getContrAmt()) || (this.getContrAmt() != null
						&& castOther.getContrAmt() != null && this.getContrAmt().equals(castOther.getContrAmt())))
				&& ((this.getNoncontrAmt() == castOther.getNoncontrAmt())
						|| (this.getNoncontrAmt() != null && castOther.getNoncontrAmt() != null
								&& this.getNoncontrAmt().equals(castOther.getNoncontrAmt())))
				&& ((this.getSupplPayAmt() == castOther.getSupplPayAmt())
						|| (this.getSupplPayAmt() != null && castOther.getSupplPayAmt() != null
								&& this.getSupplPayAmt().equals(castOther.getSupplPayAmt())))
				&& ((this.getAnnuityDed() == castOther.getAnnuityDed()) || (this.getAnnuityDed() != null
						&& castOther.getAnnuityDed() != null && this.getAnnuityDed().equals(castOther.getAnnuityDed())))
				&& ((this.getFicaTax() == castOther.getFicaTax()) || (this.getFicaTax() != null
						&& castOther.getFicaTax() != null && this.getFicaTax().equals(castOther.getFicaTax())))
				&& ((this.getFicaGross() == castOther.getFicaGross()) || (this.getFicaGross() != null
						&& castOther.getFicaGross() != null && this.getFicaGross().equals(castOther.getFicaGross())))
				&& ((this.getUnempIns() == castOther.getUnempIns()) || (this.getUnempIns() != null
						&& castOther.getUnempIns() != null && this.getUnempIns().equals(castOther.getUnempIns())))
				&& ((this.getUnempGross() == castOther.getUnempGross()) || (this.getUnempGross() != null
						&& castOther.getUnempGross() != null && this.getUnempGross().equals(castOther.getUnempGross())))
				&& ((this.getEicAmt() == castOther.getEicAmt()) || (this.getEicAmt() != null
						&& castOther.getEicAmt() != null && this.getEicAmt().equals(castOther.getEicAmt())))
				&& ((this.getWhGross() == castOther.getWhGross()) || (this.getWhGross() != null
						&& castOther.getWhGross() != null && this.getWhGross().equals(castOther.getWhGross())))
				&& ((this.getWhTax() == castOther.getWhTax()) || (this.getWhTax() != null
						&& castOther.getWhTax() != null && this.getWhTax().equals(castOther.getWhTax())))
				&& ((this.getHlthInsDed() == castOther.getHlthInsDed()) || (this.getHlthInsDed() != null
						&& castOther.getHlthInsDed() != null && this.getHlthInsDed().equals(castOther.getHlthInsDed())))
				&& ((this.getDependCare() == castOther.getDependCare()) || (this.getDependCare() != null
						&& castOther.getDependCare() != null && this.getDependCare().equals(castOther.getDependCare())))
				&& ((this.getTaxedBenefits() == castOther.getTaxedBenefits())
						|| (this.getTaxedBenefits() != null && castOther.getTaxedBenefits() != null
								&& this.getTaxedBenefits().equals(castOther.getTaxedBenefits())))
				&& ((this.getTrsDeposit() == castOther.getTrsDeposit()) || (this.getTrsDeposit() != null
						&& castOther.getTrsDeposit() != null && this.getTrsDeposit().equals(castOther.getTrsDeposit())))
				&& ((this.getTrsSalaryRed() == castOther.getTrsSalaryRed())
						|| (this.getTrsSalaryRed() != null && castOther.getTrsSalaryRed() != null
								&& this.getTrsSalaryRed().equals(castOther.getTrsSalaryRed())))
				&& ((this.getMovingExpReimbr() == castOther.getMovingExpReimbr())
						|| (this.getMovingExpReimbr() != null && castOther.getMovingExpReimbr() != null
								&& this.getMovingExpReimbr().equals(castOther.getMovingExpReimbr())))
				&& ((this.getEmplr457Contrib() == castOther.getEmplr457Contrib())
						|| (this.getEmplr457Contrib() != null && castOther.getEmplr457Contrib() != null
								&& this.getEmplr457Contrib().equals(castOther.getEmplr457Contrib())))
				&& ((this.getEmp457Contrib() == castOther.getEmp457Contrib())
						|| (this.getEmp457Contrib() != null && castOther.getEmp457Contrib() != null
								&& this.getEmp457Contrib().equals(castOther.getEmp457Contrib())))
				&& ((this.getWithdraw457() == castOther.getWithdraw457())
						|| (this.getWithdraw457() != null && castOther.getWithdraw457() != null
								&& this.getWithdraw457().equals(castOther.getWithdraw457())))
				&& ((this.getEmpBusinessExpense() == castOther.getEmpBusinessExpense())
						|| (this.getEmpBusinessExpense() != null && castOther.getEmpBusinessExpense() != null
								&& this.getEmpBusinessExpense().equals(castOther.getEmpBusinessExpense())))
				&& ((this.getTaxEmplrLife() == castOther.getTaxEmplrLife())
						|| (this.getTaxEmplrLife() != null && castOther.getTaxEmplrLife() != null
								&& this.getTaxEmplrLife().equals(castOther.getTaxEmplrLife())))
				&& ((this.getTaxEmplrLifeGrp() == castOther.getTaxEmplrLifeGrp())
						|| (this.getTaxEmplrLifeGrp() != null && castOther.getTaxEmplrLifeGrp() != null
								&& this.getTaxEmplrLifeGrp().equals(castOther.getTaxEmplrLifeGrp())))
				&& ((this.getCafeAmt() == castOther.getCafeAmt()) || (this.getCafeAmt() != null
						&& castOther.getCafeAmt() != null && this.getCafeAmt().equals(castOther.getCafeAmt())))
				&& ((this.getQtrUnemplGross() == castOther.getQtrUnemplGross())
						|| (this.getQtrUnemplGross() != null && castOther.getQtrUnemplGross() != null
								&& this.getQtrUnemplGross().equals(castOther.getQtrUnemplGross())))
				&& ((this.getMedTax() == castOther.getMedTax()) || (this.getMedTax() != null
						&& castOther.getMedTax() != null && this.getMedTax().equals(castOther.getMedTax())))
				&& ((this.getMedGross() == castOther.getMedGross()) || (this.getMedGross() != null
						&& castOther.getMedGross() != null && this.getMedGross().equals(castOther.getMedGross())))
				&& ((this.getNontrsReimbrExcess() == castOther.getNontrsReimbrExcess())
						|| (this.getNontrsReimbrExcess() != null && castOther.getNontrsReimbrExcess() != null
								&& this.getNontrsReimbrExcess().equals(castOther.getNontrsReimbrExcess())))
				&& ((this.getNontrsReimbrBase() == castOther.getNontrsReimbrBase())
						|| (this.getNontrsReimbrBase() != null && castOther.getNontrsReimbrBase() != null
								&& this.getNontrsReimbrBase().equals(castOther.getNontrsReimbrBase())))
				&& ((this.getNontrsBusAllow() == castOther.getNontrsBusAllow())
						|| (this.getNontrsBusAllow() != null && castOther.getNontrsBusAllow() != null
								&& this.getNontrsBusAllow().equals(castOther.getNontrsBusAllow())))
				&& ((this.getNontrsNontaxBusAllow() == castOther.getNontrsNontaxBusAllow())
						|| (this.getNontrsNontaxBusAllow() != null && castOther.getNontrsNontaxBusAllow() != null
								&& this.getNontrsNontaxBusAllow().equals(castOther.getNontrsNontaxBusAllow())))
				&& ((this.getTrsSupplComp() == castOther.getTrsSupplComp())
						|| (this.getTrsSupplComp() != null && castOther.getTrsSupplComp() != null
								&& this.getTrsSupplComp().equals(castOther.getTrsSupplComp())))
				&& ((this.getTeaHealthInsContrib() == castOther.getTeaHealthInsContrib())
						|| (this.getTeaHealthInsContrib() != null && castOther.getTeaHealthInsContrib() != null
								&& this.getTeaHealthInsContrib().equals(castOther.getTeaHealthInsContrib())))
				&& ((this.getNontrsNonpayBusAllow() == castOther.getNontrsNonpayBusAllow())
						|| (this.getNontrsNonpayBusAllow() != null && castOther.getNontrsNonpayBusAllow() != null
								&& this.getNontrsNonpayBusAllow().equals(castOther.getNontrsNonpayBusAllow())))
				&& ((this.getNontrsNontaxNonpayAllow() == castOther.getNontrsNontaxNonpayAllow())
						|| (this.getNontrsNontaxNonpayAllow() != null && castOther.getNontrsNontaxNonpayAllow() != null
								&& this.getNontrsNontaxNonpayAllow().equals(castOther.getNontrsNontaxNonpayAllow())))
				&& ((this.getEmplrDependCare() == castOther.getEmplrDependCare())
						|| (this.getEmplrDependCare() != null && castOther.getEmplrDependCare() != null
								&& this.getEmplrDependCare().equals(castOther.getEmplrDependCare())))
				&& ((this.getAnnuityRoth() == castOther.getAnnuityRoth())
						|| (this.getAnnuityRoth() != null && castOther.getAnnuityRoth() != null
								&& this.getAnnuityRoth().equals(castOther.getAnnuityRoth())))
				&& ((this.getEmplrDependCareTax() == castOther.getEmplrDependCareTax())
						|| (this.getEmplrDependCareTax() != null && castOther.getEmplrDependCareTax() != null
								&& this.getEmplrDependCareTax().equals(castOther.getEmplrDependCareTax())))
				&& ((this.getHsaEmpSalRedctnContrib() == castOther.getHsaEmpSalRedctnContrib())
						|| (this.getHsaEmpSalRedctnContrib() != null && castOther.getHsaEmpSalRedctnContrib() != null
								&& this.getHsaEmpSalRedctnContrib().equals(castOther.getHsaEmpSalRedctnContrib())))
				&& ((this.getHsaEmplrContrib() == castOther.getHsaEmplrContrib())
						|| (this.getHsaEmplrContrib() != null && castOther.getHsaEmplrContrib() != null
								&& this.getHsaEmplrContrib().equals(castOther.getHsaEmplrContrib())))
				&& ((this.getHireExemptWgs() == castOther.getHireExemptWgs())
						|| (this.getHireExemptWgs() != null && castOther.getHireExemptWgs() != null
								&& this.getHireExemptWgs().equals(castOther.getHireExemptWgs())))
				&& ((this.getEmplrPrvdHlthcare() == castOther.getEmplrPrvdHlthcare())
						|| (this.getEmplrPrvdHlthcare() != null && castOther.getEmplrPrvdHlthcare() != null
								&& this.getEmplrPrvdHlthcare().equals(castOther.getEmplrPrvdHlthcare())))
				&& ((this.getEmplrFicaTax() == castOther.getEmplrFicaTax())
						|| (this.getEmplrFicaTax() != null && castOther.getEmplrFicaTax() != null
								&& this.getEmplrFicaTax().equals(castOther.getEmplrFicaTax())))
				&& ((this.getModule() == castOther.getModule()) || (this.getModule() != null
						&& castOther.getModule() != null && this.getModule().equals(castOther.getModule())))
				&& ((this.getEmplrMedTax() == castOther.getEmplrMedTax())
						|| (this.getEmplrMedTax() != null && castOther.getEmplrMedTax() != null
								&& this.getEmplrMedTax().equals(castOther.getEmplrMedTax())))
				&& ((this.getAnnuityRoth457b() == castOther.getAnnuityRoth457b())
						|| (this.getAnnuityRoth457b() != null && castOther.getAnnuityRoth457b() != null
								&& this.getAnnuityRoth457b().equals(castOther.getAnnuityRoth457b())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getCyrNyrFlg();
		result = 37 * result + this.getPayFreq();
		result = 37 * result + (getEmpNbr() == null ? 0 : this.getEmpNbr().hashCode());
		result = 37 * result + (getCalYr() == null ? 0 : this.getCalYr().hashCode());
		result = 37 * result + this.getFicaTyp();
		result = 37 * result + (getContrAmt() == null ? 0 : this.getContrAmt().hashCode());
		result = 37 * result + (getNoncontrAmt() == null ? 0 : this.getNoncontrAmt().hashCode());
		result = 37 * result + (getSupplPayAmt() == null ? 0 : this.getSupplPayAmt().hashCode());
		result = 37 * result + (getAnnuityDed() == null ? 0 : this.getAnnuityDed().hashCode());
		result = 37 * result + (getFicaTax() == null ? 0 : this.getFicaTax().hashCode());
		result = 37 * result + (getFicaGross() == null ? 0 : this.getFicaGross().hashCode());
		result = 37 * result + (getUnempIns() == null ? 0 : this.getUnempIns().hashCode());
		result = 37 * result + (getUnempGross() == null ? 0 : this.getUnempGross().hashCode());
		result = 37 * result + (getEicAmt() == null ? 0 : this.getEicAmt().hashCode());
		result = 37 * result + (getWhGross() == null ? 0 : this.getWhGross().hashCode());
		result = 37 * result + (getWhTax() == null ? 0 : this.getWhTax().hashCode());
		result = 37 * result + (getHlthInsDed() == null ? 0 : this.getHlthInsDed().hashCode());
		result = 37 * result + (getDependCare() == null ? 0 : this.getDependCare().hashCode());
		result = 37 * result + (getTaxedBenefits() == null ? 0 : this.getTaxedBenefits().hashCode());
		result = 37 * result + (getTrsDeposit() == null ? 0 : this.getTrsDeposit().hashCode());
		result = 37 * result + (getTrsSalaryRed() == null ? 0 : this.getTrsSalaryRed().hashCode());
		result = 37 * result + (getMovingExpReimbr() == null ? 0 : this.getMovingExpReimbr().hashCode());
		result = 37 * result + (getEmplr457Contrib() == null ? 0 : this.getEmplr457Contrib().hashCode());
		result = 37 * result + (getEmp457Contrib() == null ? 0 : this.getEmp457Contrib().hashCode());
		result = 37 * result + (getWithdraw457() == null ? 0 : this.getWithdraw457().hashCode());
		result = 37 * result + (getEmpBusinessExpense() == null ? 0 : this.getEmpBusinessExpense().hashCode());
		result = 37 * result + (getTaxEmplrLife() == null ? 0 : this.getTaxEmplrLife().hashCode());
		result = 37 * result + (getTaxEmplrLifeGrp() == null ? 0 : this.getTaxEmplrLifeGrp().hashCode());
		result = 37 * result + (getCafeAmt() == null ? 0 : this.getCafeAmt().hashCode());
		result = 37 * result + (getQtrUnemplGross() == null ? 0 : this.getQtrUnemplGross().hashCode());
		result = 37 * result + (getMedTax() == null ? 0 : this.getMedTax().hashCode());
		result = 37 * result + (getMedGross() == null ? 0 : this.getMedGross().hashCode());
		result = 37 * result + (getNontrsReimbrExcess() == null ? 0 : this.getNontrsReimbrExcess().hashCode());
		result = 37 * result + (getNontrsReimbrBase() == null ? 0 : this.getNontrsReimbrBase().hashCode());
		result = 37 * result + (getNontrsBusAllow() == null ? 0 : this.getNontrsBusAllow().hashCode());
		result = 37 * result + (getNontrsNontaxBusAllow() == null ? 0 : this.getNontrsNontaxBusAllow().hashCode());
		result = 37 * result + (getTrsSupplComp() == null ? 0 : this.getTrsSupplComp().hashCode());
		result = 37 * result + (getTeaHealthInsContrib() == null ? 0 : this.getTeaHealthInsContrib().hashCode());
		result = 37 * result + (getNontrsNonpayBusAllow() == null ? 0 : this.getNontrsNonpayBusAllow().hashCode());
		result = 37 * result
				+ (getNontrsNontaxNonpayAllow() == null ? 0 : this.getNontrsNontaxNonpayAllow().hashCode());
		result = 37 * result + (getEmplrDependCare() == null ? 0 : this.getEmplrDependCare().hashCode());
		result = 37 * result + (getAnnuityRoth() == null ? 0 : this.getAnnuityRoth().hashCode());
		result = 37 * result + (getEmplrDependCareTax() == null ? 0 : this.getEmplrDependCareTax().hashCode());
		result = 37 * result + (getHsaEmpSalRedctnContrib() == null ? 0 : this.getHsaEmpSalRedctnContrib().hashCode());
		result = 37 * result + (getHsaEmplrContrib() == null ? 0 : this.getHsaEmplrContrib().hashCode());
		result = 37 * result + (getHireExemptWgs() == null ? 0 : this.getHireExemptWgs().hashCode());
		result = 37 * result + (getEmplrPrvdHlthcare() == null ? 0 : this.getEmplrPrvdHlthcare().hashCode());
		result = 37 * result + (getEmplrFicaTax() == null ? 0 : this.getEmplrFicaTax().hashCode());
		result = 37 * result + (getModule() == null ? 0 : this.getModule().hashCode());
		result = 37 * result + (getEmplrMedTax() == null ? 0 : this.getEmplrMedTax().hashCode());
		result = 37 * result + (getAnnuityRoth457b() == null ? 0 : this.getAnnuityRoth457b().hashCode());
		return result;
	}

}
