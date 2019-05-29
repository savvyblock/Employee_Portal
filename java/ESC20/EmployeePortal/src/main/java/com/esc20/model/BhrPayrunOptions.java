package com.esc20.model;
// Generated Jan 4, 2019 3:43:59 PM by Hibernate Tools 4.3.5.Final

import java.math.BigDecimal;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * BhrPayrunOptions generated by hbm2java
 */
@Entity
@Table(name = "BHR_PAYRUN_OPTIONS", schema = "rsccc", catalog = "rsccc")
public class BhrPayrunOptions implements java.io.Serializable {
	private static final long serialVersionUID = 6715142900654758429L;
	private BhrPayrunOptionsId id;
	private char zeroSchYtd;
	private char wageEarnOnly;
	private char prtBankCks;
	private String begCkNbr;
	private String begRefNbr;
	private char eft;
	private char supplCalcIncomeTax;
	private char supplCalcSocialSec;
	private char supplCalcTrs;
	private char supplCalcWrkComp;
	private char supplCalcUnempl;
	private String userId;
	private String processDt;
	private char prtVoidCks;
	private char calcTrsSupplComp;
	private char wageEarnBankAcct;
	private char dedRptSsn;
	private char reducFicaGross;
	private char calcAccruSalary;
	private char sumBenefits;
	private BigDecimal maxGross;
	private BigDecimal trsPct;
	private BigDecimal ficaRate;
	private BigDecimal ficaMaxSalary;
	private char expenseUnemployTax;
	private BigDecimal unemployRate;
	private BigDecimal unemployGrossSalary;
	private char emplrTyp;
	private BigDecimal wrkCompNetRateA;
	private BigDecimal wrkCompNetRateB;
	private BigDecimal wrkCompNetRateC;
	private BigDecimal wrkCompNetRateD;
	private String firstPaydateSchYr;
	private char srtCkAlphCampus;
	private BigDecimal wrkCompNetRateE;
	private BigDecimal wrkCompNetRateF;
	private char supplCalc457;
	private BigDecimal trsIrsSalCap;
	private BigDecimal medRate;
	private BigDecimal eicMaxSal;
	private BigDecimal emplrFicaRate;
	private BigDecimal emplrTrsNonOasdiPct;

	public BhrPayrunOptions() {
	}

	public BhrPayrunOptions(BhrPayrunOptionsId id, char zeroSchYtd, char wageEarnOnly, char prtBankCks, String begCkNbr,
			String begRefNbr, char eft, char supplCalcIncomeTax, char supplCalcSocialSec, char supplCalcTrs,
			char supplCalcWrkComp, char supplCalcUnempl, String userId, String processDt, char prtVoidCks,
			char calcTrsSupplComp, char wageEarnBankAcct, char dedRptSsn, char reducFicaGross, char calcAccruSalary,
			char sumBenefits, BigDecimal maxGross, BigDecimal trsPct, BigDecimal ficaRate, BigDecimal ficaMaxSalary,
			char expenseUnemployTax, BigDecimal unemployRate, BigDecimal unemployGrossSalary, char emplrTyp,
			BigDecimal wrkCompNetRateA, BigDecimal wrkCompNetRateB, BigDecimal wrkCompNetRateC,
			BigDecimal wrkCompNetRateD, String firstPaydateSchYr, char srtCkAlphCampus, BigDecimal wrkCompNetRateE,
			BigDecimal wrkCompNetRateF, char supplCalc457, BigDecimal trsIrsSalCap, BigDecimal medRate,
			BigDecimal eicMaxSal, BigDecimal emplrFicaRate, BigDecimal emplrTrsNonOasdiPct) {
		this.id = id;
		this.zeroSchYtd = zeroSchYtd;
		this.wageEarnOnly = wageEarnOnly;
		this.prtBankCks = prtBankCks;
		this.begCkNbr = begCkNbr;
		this.begRefNbr = begRefNbr;
		this.eft = eft;
		this.supplCalcIncomeTax = supplCalcIncomeTax;
		this.supplCalcSocialSec = supplCalcSocialSec;
		this.supplCalcTrs = supplCalcTrs;
		this.supplCalcWrkComp = supplCalcWrkComp;
		this.supplCalcUnempl = supplCalcUnempl;
		this.userId = userId;
		this.processDt = processDt;
		this.prtVoidCks = prtVoidCks;
		this.calcTrsSupplComp = calcTrsSupplComp;
		this.wageEarnBankAcct = wageEarnBankAcct;
		this.dedRptSsn = dedRptSsn;
		this.reducFicaGross = reducFicaGross;
		this.calcAccruSalary = calcAccruSalary;
		this.sumBenefits = sumBenefits;
		this.maxGross = maxGross;
		this.trsPct = trsPct;
		this.ficaRate = ficaRate;
		this.ficaMaxSalary = ficaMaxSalary;
		this.expenseUnemployTax = expenseUnemployTax;
		this.unemployRate = unemployRate;
		this.unemployGrossSalary = unemployGrossSalary;
		this.emplrTyp = emplrTyp;
		this.wrkCompNetRateA = wrkCompNetRateA;
		this.wrkCompNetRateB = wrkCompNetRateB;
		this.wrkCompNetRateC = wrkCompNetRateC;
		this.wrkCompNetRateD = wrkCompNetRateD;
		this.firstPaydateSchYr = firstPaydateSchYr;
		this.srtCkAlphCampus = srtCkAlphCampus;
		this.wrkCompNetRateE = wrkCompNetRateE;
		this.wrkCompNetRateF = wrkCompNetRateF;
		this.supplCalc457 = supplCalc457;
		this.trsIrsSalCap = trsIrsSalCap;
		this.medRate = medRate;
		this.eicMaxSal = eicMaxSal;
		this.emplrFicaRate = emplrFicaRate;
		this.emplrTrsNonOasdiPct = emplrTrsNonOasdiPct;
	}

	@EmbeddedId

	@AttributeOverrides({
			@AttributeOverride(name = "payFreq", column = @Column(name = "PAY_FREQ", nullable = false, length = 1)),
			@AttributeOverride(name = "dtOfPay", column = @Column(name = "DT_OF_PAY", nullable = false, length = 8)),
			@AttributeOverride(name = "schYr", column = @Column(name = "SCH_YR", nullable = false, length = 4)),
			@AttributeOverride(name = "adjNbr", column = @Column(name = "ADJ_NBR", nullable = false, precision = 3, scale = 0)) })
	public BhrPayrunOptionsId getId() {
		return this.id;
	}

	public void setId(BhrPayrunOptionsId id) {
		this.id = id;
	}

	@Column(name = "ZERO_SCH_YTD", nullable = false, length = 1)
	public char getZeroSchYtd() {
		return this.zeroSchYtd;
	}

	public void setZeroSchYtd(char zeroSchYtd) {
		this.zeroSchYtd = zeroSchYtd;
	}

	@Column(name = "WAGE_EARN_ONLY", nullable = false, length = 1)
	public char getWageEarnOnly() {
		return this.wageEarnOnly;
	}

	public void setWageEarnOnly(char wageEarnOnly) {
		this.wageEarnOnly = wageEarnOnly;
	}

	@Column(name = "PRT_BANK_CKS", nullable = false, length = 1)
	public char getPrtBankCks() {
		return this.prtBankCks;
	}

	public void setPrtBankCks(char prtBankCks) {
		this.prtBankCks = prtBankCks;
	}

	@Column(name = "BEG_CK_NBR", nullable = false, length = 6)
	public String getBegCkNbr() {
		return this.begCkNbr;
	}

	public void setBegCkNbr(String begCkNbr) {
		this.begCkNbr = begCkNbr;
	}

	@Column(name = "BEG_REF_NBR", nullable = false, length = 6)
	public String getBegRefNbr() {
		return this.begRefNbr;
	}

	public void setBegRefNbr(String begRefNbr) {
		this.begRefNbr = begRefNbr;
	}

	@Column(name = "EFT", nullable = false, length = 1)
	public char getEft() {
		return this.eft;
	}

	public void setEft(char eft) {
		this.eft = eft;
	}

	@Column(name = "SUPPL_CALC_INCOME_TAX", nullable = false, length = 1)
	public char getSupplCalcIncomeTax() {
		return this.supplCalcIncomeTax;
	}

	public void setSupplCalcIncomeTax(char supplCalcIncomeTax) {
		this.supplCalcIncomeTax = supplCalcIncomeTax;
	}

	@Column(name = "SUPPL_CALC_SOCIAL_SEC", nullable = false, length = 1)
	public char getSupplCalcSocialSec() {
		return this.supplCalcSocialSec;
	}

	public void setSupplCalcSocialSec(char supplCalcSocialSec) {
		this.supplCalcSocialSec = supplCalcSocialSec;
	}

	@Column(name = "SUPPL_CALC_TRS", nullable = false, length = 1)
	public char getSupplCalcTrs() {
		return this.supplCalcTrs;
	}

	public void setSupplCalcTrs(char supplCalcTrs) {
		this.supplCalcTrs = supplCalcTrs;
	}

	@Column(name = "SUPPL_CALC_WRK_COMP", nullable = false, length = 1)
	public char getSupplCalcWrkComp() {
		return this.supplCalcWrkComp;
	}

	public void setSupplCalcWrkComp(char supplCalcWrkComp) {
		this.supplCalcWrkComp = supplCalcWrkComp;
	}

	@Column(name = "SUPPL_CALC_UNEMPL", nullable = false, length = 1)
	public char getSupplCalcUnempl() {
		return this.supplCalcUnempl;
	}

	public void setSupplCalcUnempl(char supplCalcUnempl) {
		this.supplCalcUnempl = supplCalcUnempl;
	}

	@Column(name = "USER_ID", nullable = false, length = 12)
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "PROCESS_DT", nullable = false, length = 8)
	public String getProcessDt() {
		return this.processDt;
	}

	public void setProcessDt(String processDt) {
		this.processDt = processDt;
	}

	@Column(name = "PRT_VOID_CKS", nullable = false, length = 1)
	public char getPrtVoidCks() {
		return this.prtVoidCks;
	}

	public void setPrtVoidCks(char prtVoidCks) {
		this.prtVoidCks = prtVoidCks;
	}

	@Column(name = "CALC_TRS_SUPPL_COMP", nullable = false, length = 1)
	public char getCalcTrsSupplComp() {
		return this.calcTrsSupplComp;
	}

	public void setCalcTrsSupplComp(char calcTrsSupplComp) {
		this.calcTrsSupplComp = calcTrsSupplComp;
	}

	@Column(name = "WAGE_EARN_BANK_ACCT", nullable = false, length = 1)
	public char getWageEarnBankAcct() {
		return this.wageEarnBankAcct;
	}

	public void setWageEarnBankAcct(char wageEarnBankAcct) {
		this.wageEarnBankAcct = wageEarnBankAcct;
	}

	@Column(name = "DED_RPT_SSN", nullable = false, length = 1)
	public char getDedRptSsn() {
		return this.dedRptSsn;
	}

	public void setDedRptSsn(char dedRptSsn) {
		this.dedRptSsn = dedRptSsn;
	}

	@Column(name = "REDUC_FICA_GROSS", nullable = false, length = 1)
	public char getReducFicaGross() {
		return this.reducFicaGross;
	}

	public void setReducFicaGross(char reducFicaGross) {
		this.reducFicaGross = reducFicaGross;
	}

	@Column(name = "CALC_ACCRU_SALARY", nullable = false, length = 1)
	public char getCalcAccruSalary() {
		return this.calcAccruSalary;
	}

	public void setCalcAccruSalary(char calcAccruSalary) {
		this.calcAccruSalary = calcAccruSalary;
	}

	@Column(name = "SUM_BENEFITS", nullable = false, length = 1)
	public char getSumBenefits() {
		return this.sumBenefits;
	}

	public void setSumBenefits(char sumBenefits) {
		this.sumBenefits = sumBenefits;
	}

	@Column(name = "MAX_GROSS", nullable = false, precision = 9)
	public BigDecimal getMaxGross() {
		return this.maxGross;
	}

	public void setMaxGross(BigDecimal maxGross) {
		this.maxGross = maxGross;
	}

	@Column(name = "TRS_PCT", nullable = false, precision = 5, scale = 4)
	public BigDecimal getTrsPct() {
		return this.trsPct;
	}

	public void setTrsPct(BigDecimal trsPct) {
		this.trsPct = trsPct;
	}

	@Column(name = "FICA_RATE", nullable = false, precision = 5, scale = 4)
	public BigDecimal getFicaRate() {
		return this.ficaRate;
	}

	public void setFicaRate(BigDecimal ficaRate) {
		this.ficaRate = ficaRate;
	}

	@Column(name = "FICA_MAX_SALARY", nullable = false, precision = 9)
	public BigDecimal getFicaMaxSalary() {
		return this.ficaMaxSalary;
	}

	public void setFicaMaxSalary(BigDecimal ficaMaxSalary) {
		this.ficaMaxSalary = ficaMaxSalary;
	}

	@Column(name = "EXPENSE_UNEMPLOY_TAX", nullable = false, length = 1)
	public char getExpenseUnemployTax() {
		return this.expenseUnemployTax;
	}

	public void setExpenseUnemployTax(char expenseUnemployTax) {
		this.expenseUnemployTax = expenseUnemployTax;
	}

	@Column(name = "UNEMPLOY_RATE", nullable = false, precision = 7, scale = 6)
	public BigDecimal getUnemployRate() {
		return this.unemployRate;
	}

	public void setUnemployRate(BigDecimal unemployRate) {
		this.unemployRate = unemployRate;
	}

	@Column(name = "UNEMPLOY_GROSS_SALARY", nullable = false, precision = 9)
	public BigDecimal getUnemployGrossSalary() {
		return this.unemployGrossSalary;
	}

	public void setUnemployGrossSalary(BigDecimal unemployGrossSalary) {
		this.unemployGrossSalary = unemployGrossSalary;
	}

	@Column(name = "EMPLR_TYP", nullable = false, length = 1)
	public char getEmplrTyp() {
		return this.emplrTyp;
	}

	public void setEmplrTyp(char emplrTyp) {
		this.emplrTyp = emplrTyp;
	}

	@Column(name = "WRK_COMP_NET_RATE_A", nullable = false, precision = 9, scale = 6)
	public BigDecimal getWrkCompNetRateA() {
		return this.wrkCompNetRateA;
	}

	public void setWrkCompNetRateA(BigDecimal wrkCompNetRateA) {
		this.wrkCompNetRateA = wrkCompNetRateA;
	}

	@Column(name = "WRK_COMP_NET_RATE_B", nullable = false, precision = 9, scale = 6)
	public BigDecimal getWrkCompNetRateB() {
		return this.wrkCompNetRateB;
	}

	public void setWrkCompNetRateB(BigDecimal wrkCompNetRateB) {
		this.wrkCompNetRateB = wrkCompNetRateB;
	}

	@Column(name = "WRK_COMP_NET_RATE_C", nullable = false, precision = 9, scale = 6)
	public BigDecimal getWrkCompNetRateC() {
		return this.wrkCompNetRateC;
	}

	public void setWrkCompNetRateC(BigDecimal wrkCompNetRateC) {
		this.wrkCompNetRateC = wrkCompNetRateC;
	}

	@Column(name = "WRK_COMP_NET_RATE_D", nullable = false, precision = 9, scale = 6)
	public BigDecimal getWrkCompNetRateD() {
		return this.wrkCompNetRateD;
	}

	public void setWrkCompNetRateD(BigDecimal wrkCompNetRateD) {
		this.wrkCompNetRateD = wrkCompNetRateD;
	}

	@Column(name = "FIRST_PAYDATE_SCH_YR", nullable = false, length = 8)
	public String getFirstPaydateSchYr() {
		return this.firstPaydateSchYr;
	}

	public void setFirstPaydateSchYr(String firstPaydateSchYr) {
		this.firstPaydateSchYr = firstPaydateSchYr;
	}

	@Column(name = "SRT_CK_ALPH_CAMPUS", nullable = false, length = 1)
	public char getSrtCkAlphCampus() {
		return this.srtCkAlphCampus;
	}

	public void setSrtCkAlphCampus(char srtCkAlphCampus) {
		this.srtCkAlphCampus = srtCkAlphCampus;
	}

	@Column(name = "WRK_COMP_NET_RATE_E", nullable = false, precision = 9, scale = 6)
	public BigDecimal getWrkCompNetRateE() {
		return this.wrkCompNetRateE;
	}

	public void setWrkCompNetRateE(BigDecimal wrkCompNetRateE) {
		this.wrkCompNetRateE = wrkCompNetRateE;
	}

	@Column(name = "WRK_COMP_NET_RATE_F", nullable = false, precision = 9, scale = 6)
	public BigDecimal getWrkCompNetRateF() {
		return this.wrkCompNetRateF;
	}

	public void setWrkCompNetRateF(BigDecimal wrkCompNetRateF) {
		this.wrkCompNetRateF = wrkCompNetRateF;
	}

	@Column(name = "SUPPL_CALC_457", nullable = false, length = 1)
	public char getSupplCalc457() {
		return this.supplCalc457;
	}

	public void setSupplCalc457(char supplCalc457) {
		this.supplCalc457 = supplCalc457;
	}

	@Column(name = "TRS_IRS_SAL_CAP", nullable = false, precision = 9)
	public BigDecimal getTrsIrsSalCap() {
		return this.trsIrsSalCap;
	}

	public void setTrsIrsSalCap(BigDecimal trsIrsSalCap) {
		this.trsIrsSalCap = trsIrsSalCap;
	}

	@Column(name = "MED_RATE", nullable = false, precision = 5, scale = 4)
	public BigDecimal getMedRate() {
		return this.medRate;
	}

	public void setMedRate(BigDecimal medRate) {
		this.medRate = medRate;
	}

	@Column(name = "EIC_MAX_SAL", nullable = false, precision = 7)
	public BigDecimal getEicMaxSal() {
		return this.eicMaxSal;
	}

	public void setEicMaxSal(BigDecimal eicMaxSal) {
		this.eicMaxSal = eicMaxSal;
	}

	@Column(name = "EMPLR_FICA_RATE", nullable = false, precision = 5, scale = 4)
	public BigDecimal getEmplrFicaRate() {
		return this.emplrFicaRate;
	}

	public void setEmplrFicaRate(BigDecimal emplrFicaRate) {
		this.emplrFicaRate = emplrFicaRate;
	}

	@Column(name = "EMPLR_TRS_NON_OASDI_PCT", nullable = false, precision = 6, scale = 5)
	public BigDecimal getEmplrTrsNonOasdiPct() {
		return this.emplrTrsNonOasdiPct;
	}

	public void setEmplrTrsNonOasdiPct(BigDecimal emplrTrsNonOasdiPct) {
		this.emplrTrsNonOasdiPct = emplrTrsNonOasdiPct;
	}
}
