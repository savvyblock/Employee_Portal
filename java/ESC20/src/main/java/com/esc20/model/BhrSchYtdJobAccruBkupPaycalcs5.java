package com.esc20.model;
// Generated Jan 4, 2019 3:45:09 PM by Hibernate Tools 4.3.5.Final

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * BhrSchYtdJobAccruBkupPaycalcs5 generated by hbm2java
 */
@Entity
@Table(name = "BHR_SCH_YTD_JOB_ACCRU_BKUP_PAYCALCS_5", schema = "rsccc", catalog = "rsccc")
public class BhrSchYtdJobAccruBkupPaycalcs5 implements java.io.Serializable {
	private static final long serialVersionUID = 6715142900654758429L;
	private BhrSchYtdJobAccruBkupPaycalcs5Id id;

	public BhrSchYtdJobAccruBkupPaycalcs5() {
	}

	public BhrSchYtdJobAccruBkupPaycalcs5(BhrSchYtdJobAccruBkupPaycalcs5Id id) {
		this.id = id;
	}

	@EmbeddedId

	@AttributeOverrides({
			@AttributeOverride(name = "cyrNyrFlg", column = @Column(name = "CYR_NYR_FLG", nullable = false, length = 1)),
			@AttributeOverride(name = "payFreq", column = @Column(name = "PAY_FREQ", nullable = false, length = 1)),
			@AttributeOverride(name = "empNbr", column = @Column(name = "EMP_NBR", nullable = false, length = 6)),
			@AttributeOverride(name = "schYr", column = @Column(name = "SCH_YR", nullable = false, length = 4)),
			@AttributeOverride(name = "jobCd", column = @Column(name = "JOB_CD", nullable = false, length = 4)),
			@AttributeOverride(name = "daysEarned", column = @Column(name = "DAYS_EARNED", nullable = false, precision = 5)),
			@AttributeOverride(name = "accruPay", column = @Column(name = "ACCRU_PAY", nullable = false, precision = 9)),
			@AttributeOverride(name = "accruFedGrant", column = @Column(name = "ACCRU_FED_GRANT", nullable = false, precision = 9)),
			@AttributeOverride(name = "accruFicaTax", column = @Column(name = "ACCRU_FICA_TAX", nullable = false, precision = 9)),
			@AttributeOverride(name = "accruUnemplIns", column = @Column(name = "ACCRU_UNEMPL_INS", nullable = false, precision = 9)),
			@AttributeOverride(name = "accruEmplrAnnuitContrib", column = @Column(name = "ACCRU_EMPLR_ANNUIT_CONTRIB", nullable = false, precision = 9)),
			@AttributeOverride(name = "accruEmplrNonAnnuitContrib", column = @Column(name = "ACCRU_EMPLR_NON_ANNUIT_CONTRIB", nullable = false, precision = 9)),
			@AttributeOverride(name = "accruFedCare", column = @Column(name = "ACCRU_FED_CARE", nullable = false, precision = 9)),
			@AttributeOverride(name = "accruEmplr457Contrib", column = @Column(name = "ACCRU_EMPLR_457_CONTRIB", nullable = false, precision = 9)),
			@AttributeOverride(name = "accruTeaHealthInsContrib", column = @Column(name = "ACCRU_TEA_HEALTH_INS_CONTRIB", nullable = false, precision = 7)),
			@AttributeOverride(name = "accruEmplrTrsCareContrib", column = @Column(name = "ACCRU_EMPLR_TRS_CARE_CONTRIB", nullable = false, precision = 9)),
			@AttributeOverride(name = "accruEmplrRetPensnSurchg", column = @Column(name = "ACCRU_EMPLR_RET_PENSN_SURCHG", nullable = false, precision = 7)),
			@AttributeOverride(name = "accruEmplrTrsCareSurchg", column = @Column(name = "ACCRU_EMPLR_TRS_CARE_SURCHG", nullable = false, precision = 7)),
			@AttributeOverride(name = "accruEmplrDependCare", column = @Column(name = "ACCRU_EMPLR_DEPEND_CARE", nullable = false, precision = 9)),
			@AttributeOverride(name = "accruEmplrMiscDedContrib", column = @Column(name = "ACCRU_EMPLR_MISC_DED_CONTRIB", nullable = false, precision = 9)),
			@AttributeOverride(name = "module", column = @Column(name = "MODULE", nullable = false, length = 25)) })
	public BhrSchYtdJobAccruBkupPaycalcs5Id getId() {
		return this.id;
	}

	public void setId(BhrSchYtdJobAccruBkupPaycalcs5Id id) {
		this.id = id;
	}

}
