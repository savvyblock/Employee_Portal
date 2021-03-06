package com.esc20.model;
// Generated Jan 4, 2019 3:46:21 PM by Hibernate Tools 4.3.5.Final

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * BhrSupplXmitalBkupPaycalcs5 generated by hbm2java
 */
@Entity
@Table(name = "BHR_SUPPL_XMITAL_BKUP_PAYCALCS_5", schema = "rsccc", catalog = "rsccc")
public class BhrSupplXmitalBkupPaycalcs5 implements java.io.Serializable {

	private BhrSupplXmitalBkupPaycalcs5Id id;

	public BhrSupplXmitalBkupPaycalcs5() {
	}

	public BhrSupplXmitalBkupPaycalcs5(BhrSupplXmitalBkupPaycalcs5Id id) {
		this.id = id;
	}

	@EmbeddedId

	@AttributeOverrides({
			@AttributeOverride(name = "cyrNyrFlg", column = @Column(name = "CYR_NYR_FLG", nullable = false, length = 1)),
			@AttributeOverride(name = "payFreq", column = @Column(name = "PAY_FREQ", nullable = false, length = 1)),
			@AttributeOverride(name = "empNbr", column = @Column(name = "EMP_NBR", nullable = false, length = 6)),
			@AttributeOverride(name = "dtOfPay", column = @Column(name = "DT_OF_PAY", nullable = false, length = 8)),
			@AttributeOverride(name = "dts", column = @Column(name = "DTS", nullable = false, length = 16)),
			@AttributeOverride(name = "seqNbr", column = @Column(name = "SEQ_NBR", nullable = false, length = 5)),
			@AttributeOverride(name = "jobCd", column = @Column(name = "JOB_CD", nullable = false, length = 4)),
			@AttributeOverride(name = "campusId", column = @Column(name = "CAMPUS_ID", nullable = false, length = 3)),
			@AttributeOverride(name = "dept", column = @Column(name = "DEPT", nullable = false, length = 1)),
			@AttributeOverride(name = "trsGrantCd", column = @Column(name = "TRS_GRANT_CD", nullable = false, length = 2)),
			@AttributeOverride(name = "glFileId", column = @Column(name = "GL_FILE_ID", nullable = false, length = 1)),
			@AttributeOverride(name = "fund", column = @Column(name = "FUND", nullable = false, length = 3)),
			@AttributeOverride(name = "func", column = @Column(name = "FUNC", nullable = false, length = 2)),
			@AttributeOverride(name = "obj", column = @Column(name = "OBJ", nullable = false, length = 4)),
			@AttributeOverride(name = "sobj", column = @Column(name = "SOBJ", nullable = false, length = 2)),
			@AttributeOverride(name = "wrkComp", column = @Column(name = "WRK_COMP", nullable = false, length = 1)),
			@AttributeOverride(name = "org", column = @Column(name = "ORG", nullable = false, length = 3)),
			@AttributeOverride(name = "fsclYr", column = @Column(name = "FSCL_YR", nullable = false, length = 1)),
			@AttributeOverride(name = "pgm", column = @Column(name = "PGM", nullable = false, length = 2)),
			@AttributeOverride(name = "edSpan", column = @Column(name = "ED_SPAN", nullable = false, length = 1)),
			@AttributeOverride(name = "projDtl", column = @Column(name = "PROJ_DTL", nullable = false, length = 2)),
			@AttributeOverride(name = "supplAmt", column = @Column(name = "SUPPL_AMT", nullable = false, precision = 9)),
			@AttributeOverride(name = "addStdGrossCd", column = @Column(name = "ADD_STD_GROSS_CD", nullable = false, length = 1)),
			@AttributeOverride(name = "adjSalaryCd", column = @Column(name = "ADJ_SALARY_CD", nullable = false, length = 1)),
			@AttributeOverride(name = "ovtmCd", column = @Column(name = "OVTM_CD", nullable = false, length = 1)),
			@AttributeOverride(name = "ovtmHrsWrk", column = @Column(name = "OVTM_HRS_WRK", nullable = false, precision = 5)),
			@AttributeOverride(name = "regHrsWrk", column = @Column(name = "REG_HRS_WRK", nullable = false, precision = 5)),
			@AttributeOverride(name = "taxedBenefitFlg", column = @Column(name = "TAXED_BENEFIT_FLG", nullable = false, length = 1)),
			@AttributeOverride(name = "nonTrsPymtTyp", column = @Column(name = "NON_TRS_PYMT_TYP", nullable = false, length = 1)),
			@AttributeOverride(name = "nonTrsPymtCd", column = @Column(name = "NON_TRS_PYMT_CD", nullable = false, length = 1)),
			@AttributeOverride(name = "nonTrsNonTaxAmt", column = @Column(name = "NON_TRS_NON_TAX_AMT", nullable = false, precision = 9)),
			@AttributeOverride(name = "nonTrsTaxAmt", column = @Column(name = "NON_TRS_TAX_AMT", nullable = false, precision = 9)),
			@AttributeOverride(name = "expense373", column = @Column(name = "EXPENSE_373", nullable = false, length = 1)),
			@AttributeOverride(name = "blkIns", column = @Column(name = "BLK_INS", nullable = false, length = 1)),
			@AttributeOverride(name = "servRecDaysDeduct", column = @Column(name = "SERV_REC_DAYS_DEDUCT", nullable = false, precision = 5)),
			@AttributeOverride(name = "subTyp", column = @Column(name = "SUB_TYP", nullable = false, length = 2)),
			@AttributeOverride(name = "transmittalType", column = @Column(name = "TRANSMITTAL_TYPE", nullable = false, length = 1)),
			@AttributeOverride(name = "extraDutyCd", column = @Column(name = "EXTRA_DUTY_CD", nullable = false, length = 2)),
			@AttributeOverride(name = "processDt", column = @Column(name = "PROCESS_DT", nullable = false, length = 8)),
			@AttributeOverride(name = "userId", column = @Column(name = "USER_ID", nullable = false, length = 12)),
			@AttributeOverride(name = "taxedFringeBenefit", column = @Column(name = "TAXED_FRINGE_BENEFIT", nullable = false, length = 1)),
			@AttributeOverride(name = "perforPay", column = @Column(name = "PERFOR_PAY", nullable = false, length = 1)),
			@AttributeOverride(name = "rsn", column = @Column(name = "RSN", nullable = false, length = 30)),
			@AttributeOverride(name = "apply457", column = @Column(name = "APPLY_457", nullable = false, length = 1)),
			@AttributeOverride(name = "createdCip", column = @Column(name = "CREATED_CIP", nullable = false, length = 1)),
			@AttributeOverride(name = "module", column = @Column(name = "MODULE", nullable = false, length = 25)),
			@AttributeOverride(name = "actHrs", column = @Column(name = "ACT_HRS", nullable = false, precision = 5)),
			@AttributeOverride(name = "actDt", column = @Column(name = "ACT_DT", nullable = false, length = 8)) })
	public BhrSupplXmitalBkupPaycalcs5Id getId() {
		return this.id;
	}

	public void setId(BhrSupplXmitalBkupPaycalcs5Id id) {
		this.id = id;
	}

}
