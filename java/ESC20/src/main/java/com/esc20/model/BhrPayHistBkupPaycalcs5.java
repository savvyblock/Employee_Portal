package com.esc20.model;
// Generated Jan 4, 2019 3:44:27 PM by Hibernate Tools 4.3.5.Final

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * BhrPayHistBkupPaycalcs5 generated by hbm2java
 */
@Entity
@Table(name = "BHR_PAY_HIST_BKUP_PAYCALCS_5", schema = "rsccc", catalog = "rsccc")
public class BhrPayHistBkupPaycalcs5 implements java.io.Serializable {
	private static final long serialVersionUID = 6715142900654758429L;
	private BhrPayHistBkupPaycalcs5Id id;

	public BhrPayHistBkupPaycalcs5() {
	}

	public BhrPayHistBkupPaycalcs5(BhrPayHistBkupPaycalcs5Id id) {
		this.id = id;
	}

	@EmbeddedId

	@AttributeOverrides({
			@AttributeOverride(name = "cyrNyrFlg", column = @Column(name = "CYR_NYR_FLG", nullable = false, length = 1)),
			@AttributeOverride(name = "payFreq", column = @Column(name = "PAY_FREQ", nullable = false, length = 1)),
			@AttributeOverride(name = "empNbr", column = @Column(name = "EMP_NBR", nullable = false, length = 6)),
			@AttributeOverride(name = "dtOfPay", column = @Column(name = "DT_OF_PAY", nullable = false, length = 8)),
			@AttributeOverride(name = "chkNbr", column = @Column(name = "CHK_NBR", nullable = false, length = 6)),
			@AttributeOverride(name = "voidOrIss", column = @Column(name = "VOID_OR_ISS", nullable = false, length = 1)),
			@AttributeOverride(name = "adjNbr", column = @Column(name = "ADJ_NBR", nullable = false, precision = 3, scale = 0)),
			@AttributeOverride(name = "chkCleared", column = @Column(name = "CHK_CLEARED", nullable = false, length = 1)),
			@AttributeOverride(name = "payCampus", column = @Column(name = "PAY_CAMPUS", nullable = false, length = 3)),
			@AttributeOverride(name = "payDept", column = @Column(name = "PAY_DEPT", nullable = false, length = 1)),
			@AttributeOverride(name = "absDedCoded", column = @Column(name = "ABS_DED_CODED", nullable = false, precision = 7)),
			@AttributeOverride(name = "absDedRefund", column = @Column(name = "ABS_DED_REFUND", nullable = false, precision = 7)),
			@AttributeOverride(name = "absDedAmt", column = @Column(name = "ABS_DED_AMT", nullable = false, precision = 7)),
			@AttributeOverride(name = "absDedAmtTot", column = @Column(name = "ABS_DED_AMT_TOT", nullable = false, precision = 7)),
			@AttributeOverride(name = "accruCd", column = @Column(name = "ACCRU_CD", nullable = false, length = 1)),
			@AttributeOverride(name = "accruEmplrNonAnnuitContrib", column = @Column(name = "ACCRU_EMPLR_NON_ANNUIT_CONTRIB", nullable = false, precision = 9)),
			@AttributeOverride(name = "accruFedGrant", column = @Column(name = "ACCRU_FED_GRANT", nullable = false, precision = 9)),
			@AttributeOverride(name = "accruFedCare", column = @Column(name = "ACCRU_FED_CARE", nullable = false, precision = 9)),
			@AttributeOverride(name = "accruFedIns", column = @Column(name = "ACCRU_FED_INS", nullable = false, precision = 9)),
			@AttributeOverride(name = "accruFicaTax", column = @Column(name = "ACCRU_FICA_TAX", nullable = false, precision = 9)),
			@AttributeOverride(name = "accruPay", column = @Column(name = "ACCRU_PAY", nullable = false, precision = 9)),
			@AttributeOverride(name = "accruUnempIns", column = @Column(name = "ACCRU_UNEMP_INS", nullable = false, precision = 9)),
			@AttributeOverride(name = "accruDaysEarned", column = @Column(name = "ACCRU_DAYS_EARNED", nullable = false, precision = 5)),
			@AttributeOverride(name = "accruEmplrAnnuitContrib", column = @Column(name = "ACCRU_EMPLR_ANNUIT_CONTRIB", nullable = false, precision = 9)),
			@AttributeOverride(name = "accruEmplr457", column = @Column(name = "ACCRU_EMPLR_457", nullable = false, precision = 9)),
			@AttributeOverride(name = "dtPayoff", column = @Column(name = "DT_PAYOFF", nullable = false, length = 8)),
			@AttributeOverride(name = "finalPayFlg", column = @Column(name = "FINAL_PAY_FLG", nullable = false, length = 1)),
			@AttributeOverride(name = "eicAmt", column = @Column(name = "EIC_AMT", nullable = false, precision = 9)),
			@AttributeOverride(name = "earnPay", column = @Column(name = "EARN_PAY", nullable = false, precision = 9)),
			@AttributeOverride(name = "ficaElig", column = @Column(name = "FICA_ELIG", nullable = false, length = 1)),
			@AttributeOverride(name = "ficaGross", column = @Column(name = "FICA_GROSS", nullable = false, precision = 9)),
			@AttributeOverride(name = "ficaTax", column = @Column(name = "FICA_TAX", nullable = false, precision = 9)),
			@AttributeOverride(name = "regGrossSch", column = @Column(name = "REG_GROSS_SCH", nullable = false, precision = 9)),
			@AttributeOverride(name = "medTax", column = @Column(name = "MED_TAX", nullable = false, precision = 9)),
			@AttributeOverride(name = "medGross", column = @Column(name = "MED_GROSS", nullable = false, precision = 9)),
			@AttributeOverride(name = "grossPayTot", column = @Column(name = "GROSS_PAY_TOT", nullable = false, precision = 9)),
			@AttributeOverride(name = "hlthInsDed", column = @Column(name = "HLTH_INS_DED", nullable = false, precision = 7)),
			@AttributeOverride(name = "hrlyPayRate", column = @Column(name = "HRLY_PAY_RATE", nullable = false, precision = 9)),
			@AttributeOverride(name = "maritalStatTax", column = @Column(name = "MARITAL_STAT_TAX", nullable = false, length = 1)),
			@AttributeOverride(name = "nbrTaxExempts", column = @Column(name = "NBR_TAX_EXEMPTS", nullable = false, precision = 3, scale = 0)),
			@AttributeOverride(name = "taxExemptFlg", column = @Column(name = "TAX_EXEMPT_FLG", nullable = false, length = 1)),
			@AttributeOverride(name = "netAdjAmt", column = @Column(name = "NET_ADJ_AMT", nullable = false, precision = 9)),
			@AttributeOverride(name = "netPay", column = @Column(name = "NET_PAY", nullable = false, precision = 9)),
			@AttributeOverride(name = "ovtmGross", column = @Column(name = "OVTM_GROSS", nullable = false, precision = 9)),
			@AttributeOverride(name = "ovtmHrsWrk", column = @Column(name = "OVTM_HRS_WRK", nullable = false, precision = 5)),
			@AttributeOverride(name = "regHrsWrk", column = @Column(name = "REG_HRS_WRK", nullable = false, precision = 5)),
			@AttributeOverride(name = "retroactivePay", column = @Column(name = "RETROACTIVE_PAY", nullable = false, precision = 9)),
			@AttributeOverride(name = "trsSalaryRed", column = @Column(name = "TRS_SALARY_RED", nullable = false, precision = 9)),
			@AttributeOverride(name = "adjSalaryCd", column = @Column(name = "ADJ_SALARY_CD", nullable = false, length = 1)),
			@AttributeOverride(name = "stdGross", column = @Column(name = "STD_GROSS", nullable = false, precision = 9)),
			@AttributeOverride(name = "stdGrossTot", column = @Column(name = "STD_GROSS_TOT", nullable = false, precision = 9)),
			@AttributeOverride(name = "stSalary", column = @Column(name = "ST_SALARY", nullable = false, precision = 9)),
			@AttributeOverride(name = "extraDutyGross", column = @Column(name = "EXTRA_DUTY_GROSS", nullable = false, precision = 9)),
			@AttributeOverride(name = "taxedBenefits", column = @Column(name = "TAXED_BENEFITS", nullable = false, precision = 9)),
			@AttributeOverride(name = "busAllowanceNontax", column = @Column(name = "BUS_ALLOWANCE_NONTAX", nullable = false, precision = 9)),
			@AttributeOverride(name = "busAllowanceTax", column = @Column(name = "BUS_ALLOWANCE_TAX", nullable = false, precision = 9)),
			@AttributeOverride(name = "trsDeposit", column = @Column(name = "TRS_DEPOSIT", nullable = false, precision = 9)),
			@AttributeOverride(name = "trsFee", column = @Column(name = "TRS_FEE", nullable = false, precision = 9)),
			@AttributeOverride(name = "trsGross", column = @Column(name = "TRS_GROSS", nullable = false, precision = 9)),
			@AttributeOverride(name = "trsIns", column = @Column(name = "TRS_INS", nullable = false, precision = 7)),
			@AttributeOverride(name = "trsCare", column = @Column(name = "TRS_CARE", nullable = false, precision = 7)),
			@AttributeOverride(name = "unempGross", column = @Column(name = "UNEMP_GROSS", nullable = false, precision = 9)),
			@AttributeOverride(name = "unempIns", column = @Column(name = "UNEMP_INS", nullable = false, precision = 9)),
			@AttributeOverride(name = "whGross", column = @Column(name = "WH_GROSS", nullable = false, precision = 9)),
			@AttributeOverride(name = "whTax", column = @Column(name = "WH_TAX", nullable = false, precision = 9)),
			@AttributeOverride(name = "wrkCompIns", column = @Column(name = "WRK_COMP_INS", nullable = false, precision = 9)),
			@AttributeOverride(name = "drctDepositFlg", column = @Column(name = "DRCT_DEPOSIT_FLG", nullable = false, length = 1)),
			@AttributeOverride(name = "distrErrFlg", column = @Column(name = "DISTR_ERR_FLG", nullable = false, length = 1)),
			@AttributeOverride(name = "emplrInsContribFlg", column = @Column(name = "EMPLR_INS_CONTRIB_FLG", nullable = false, length = 1)),
			@AttributeOverride(name = "emplrNonAnnuityContrib", column = @Column(name = "EMPLR_NON_ANNUITY_CONTRIB", nullable = false, precision = 9)),
			@AttributeOverride(name = "emplrAnnuityContrib", column = @Column(name = "EMPLR_ANNUITY_CONTRIB", nullable = false, precision = 9)),
			@AttributeOverride(name = "emplr457", column = @Column(name = "EMPLR_457", nullable = false, precision = 9)),
			@AttributeOverride(name = "emp457", column = @Column(name = "EMP_457", nullable = false, precision = 9)),
			@AttributeOverride(name = "miscDedAmt", column = @Column(name = "MISC_DED_AMT", nullable = false, precision = 7)),
			@AttributeOverride(name = "extraDutyPayLoc", column = @Column(name = "EXTRA_DUTY_PAY_LOC", nullable = false, precision = 9)),
			@AttributeOverride(name = "annuityDed", column = @Column(name = "ANNUITY_DED", nullable = false, precision = 9)),
			@AttributeOverride(name = "cafeAmt", column = @Column(name = "CAFE_AMT", nullable = false, precision = 9)),
			@AttributeOverride(name = "whollySepAmt", column = @Column(name = "WHOLLY_SEP_AMT", nullable = false, precision = 9)),
			@AttributeOverride(name = "dependCare", column = @Column(name = "DEPEND_CARE", nullable = false, precision = 9)),
			@AttributeOverride(name = "taxEmplrLife", column = @Column(name = "TAX_EMPLR_LIFE", nullable = false, precision = 9)),
			@AttributeOverride(name = "taxEmplrLifeGrp", column = @Column(name = "TAX_EMPLR_LIFE_GRP", nullable = false, precision = 9)),
			@AttributeOverride(name = "distContribAboveSt", column = @Column(name = "DIST_CONTRIB_ABOVE_ST", nullable = false, precision = 9)),
			@AttributeOverride(name = "nontrsReimbrExcess", column = @Column(name = "NONTRS_REIMBR_EXCESS", nullable = false, precision = 9)),
			@AttributeOverride(name = "nontrsReimbrBase", column = @Column(name = "NONTRS_REIMBR_BASE", nullable = false, precision = 9)),
			@AttributeOverride(name = "nontrsBusAllow", column = @Column(name = "NONTRS_BUS_ALLOW", nullable = false, precision = 9)),
			@AttributeOverride(name = "nontrsNontaxBusAllow", column = @Column(name = "NONTRS_NONTAX_BUS_ALLOW", nullable = false, precision = 9)),
			@AttributeOverride(name = "nontrsNonpayBusAllow", column = @Column(name = "NONTRS_NONPAY_BUS_ALLOW", nullable = false, precision = 9)),
			@AttributeOverride(name = "nontrsNontaxNonpayAllow", column = @Column(name = "NONTRS_NONTAX_NONPAY_ALLOW", nullable = false, precision = 7)),
			@AttributeOverride(name = "nontrsSuppl", column = @Column(name = "NONTRS_SUPPL", nullable = false, precision = 9)),
			@AttributeOverride(name = "nontrsNontaxNonpayExpend", column = @Column(name = "NONTRS_NONTAX_NONPAY_EXPEND", nullable = false, precision = 7)),
			@AttributeOverride(name = "trsActiveCareEligFlg", column = @Column(name = "TRS_ACTIVE_CARE_ELIG_FLG", nullable = false, length = 1)),
			@AttributeOverride(name = "trsSupplComp", column = @Column(name = "TRS_SUPPL_COMP", nullable = false, precision = 7)),
			@AttributeOverride(name = "teaHealthInsContrib", column = @Column(name = "TEA_HEALTH_INS_CONTRIB", nullable = false, precision = 7)),
			@AttributeOverride(name = "accruTeaHealthInsContrib", column = @Column(name = "ACCRU_TEA_HEALTH_INS_CONTRIB", nullable = false, precision = 7)),
			@AttributeOverride(name = "trsSupplCompFactor", column = @Column(name = "TRS_SUPPL_COMP_FACTOR", nullable = false, precision = 1, scale = 0)),
			@AttributeOverride(name = "emplrTrsCareContrib", column = @Column(name = "EMPLR_TRS_CARE_CONTRIB", nullable = false, precision = 9)),
			@AttributeOverride(name = "accruEmplrTrsCareContrib", column = @Column(name = "ACCRU_EMPLR_TRS_CARE_CONTRIB", nullable = false, precision = 9)),
			@AttributeOverride(name = "daysWrk", column = @Column(name = "DAYS_WRK", nullable = false, precision = 5)),
			@AttributeOverride(name = "multiHrlyRates", column = @Column(name = "MULTI_HRLY_RATES", nullable = false, length = 1)),
			@AttributeOverride(name = "trsSupplEligCd", column = @Column(name = "TRS_SUPPL_ELIG_CD", nullable = false, length = 1)),
			@AttributeOverride(name = "perforPayGross", column = @Column(name = "PERFOR_PAY_GROSS", nullable = false, precision = 9)),
			@AttributeOverride(name = "perforPayDeposit", column = @Column(name = "PERFOR_PAY_DEPOSIT", nullable = false, precision = 9)),
			@AttributeOverride(name = "perforPayIns", column = @Column(name = "PERFOR_PAY_INS", nullable = false, precision = 7)),
			@AttributeOverride(name = "trsGrantGross", column = @Column(name = "TRS_GRANT_GROSS", nullable = false, precision = 9)),
			@AttributeOverride(name = "trsGrantDeposit", column = @Column(name = "TRS_GRANT_DEPOSIT", nullable = false, precision = 9)),
			@AttributeOverride(name = "trsGrantCare", column = @Column(name = "TRS_GRANT_CARE", nullable = false, precision = 7)),
			@AttributeOverride(name = "accruCafeAmt", column = @Column(name = "ACCRU_CAFE_AMT", nullable = false, precision = 9)),
			@AttributeOverride(name = "emplrRetGross", column = @Column(name = "EMPLR_RET_GROSS", nullable = false, precision = 9)),
			@AttributeOverride(name = "emplrRetPensnSurchg", column = @Column(name = "EMPLR_RET_PENSN_SURCHG", nullable = false, precision = 7)),
			@AttributeOverride(name = "accruEmplrRetPensnSurchg", column = @Column(name = "ACCRU_EMPLR_RET_PENSN_SURCHG", nullable = false, precision = 7)),
			@AttributeOverride(name = "emplrTrsCareSurchg", column = @Column(name = "EMPLR_TRS_CARE_SURCHG", nullable = false, precision = 7)),
			@AttributeOverride(name = "accruEmplrTrsCareSurchg", column = @Column(name = "ACCRU_EMPLR_TRS_CARE_SURCHG", nullable = false, precision = 7)),
			@AttributeOverride(name = "emplrNewTrsGross", column = @Column(name = "EMPLR_NEW_TRS_GROSS", nullable = false, precision = 9)),
			@AttributeOverride(name = "emplrNewTrsPensnContrib", column = @Column(name = "EMPLR_NEW_TRS_PENSN_CONTRIB", nullable = false, precision = 7)),
			@AttributeOverride(name = "takeRetSurchg", column = @Column(name = "TAKE_RET_SURCHG", nullable = false, length = 1)),
			@AttributeOverride(name = "emplrDependCare", column = @Column(name = "EMPLR_DEPEND_CARE", nullable = false, precision = 9)),
			@AttributeOverride(name = "accruEmplrDependCare", column = @Column(name = "ACCRU_EMPLR_DEPEND_CARE", nullable = false, precision = 9)),
			@AttributeOverride(name = "annuityRoth", column = @Column(name = "ANNUITY_ROTH", nullable = false, precision = 9)),
			@AttributeOverride(name = "micrChkNbr", column = @Column(name = "MICR_CHK_NBR", nullable = false, length = 6)),
			@AttributeOverride(name = "reconDts", column = @Column(name = "RECON_DTS", nullable = false, length = 16)),
			@AttributeOverride(name = "emplrDependCareTax", column = @Column(name = "EMPLR_DEPEND_CARE_TAX", nullable = false, precision = 7)),
			@AttributeOverride(name = "hsaEmpSalRedctnContrib", column = @Column(name = "HSA_EMP_SAL_REDCTN_CONTRIB", nullable = false, precision = 7)),
			@AttributeOverride(name = "hsaEmplrContrib", column = @Column(name = "HSA_EMPLR_CONTRIB", nullable = false, precision = 7)),
			@AttributeOverride(name = "emplrFicaTax", column = @Column(name = "EMPLR_FICA_TAX", nullable = false, precision = 9)),
			@AttributeOverride(name = "emplrMiscDedContrib", column = @Column(name = "EMPLR_MISC_DED_CONTRIB", nullable = false, precision = 9)),
			@AttributeOverride(name = "accruEmplrMiscDedContrib", column = @Column(name = "ACCRU_EMPLR_MISC_DED_CONTRIB", nullable = false, precision = 9)),
			@AttributeOverride(name = "emplrMedTax", column = @Column(name = "EMPLR_MED_TAX", nullable = false, precision = 9)),
			@AttributeOverride(name = "medGrosCalYtd", column = @Column(name = "MED_GROS_CAL_YTD", nullable = false, precision = 9)),
			@AttributeOverride(name = "emplrTrsNonOasdiContrib", column = @Column(name = "EMPLR_TRS_NON_OASDI_CONTRIB", nullable = false, precision = 9)),
			@AttributeOverride(name = "annuityRoth457b", column = @Column(name = "ANNUITY_ROTH_457B", nullable = false, precision = 9)),
			@AttributeOverride(name = "ersRetHlthElig", column = @Column(name = "ERS_RET_HLTH_ELIG", nullable = false, length = 1)),
			@AttributeOverride(name = "trsStatCd", column = @Column(name = "TRS_STAT_CD", nullable = false, length = 1)) })
	public BhrPayHistBkupPaycalcs5Id getId() {
		return this.id;
	}

	public void setId(BhrPayHistBkupPaycalcs5Id id) {
		this.id = id;
	}

}
