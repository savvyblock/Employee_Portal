package com.esc20.model;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "BEA_EMP_TRVL_ACCTS", schema = "rsccc", catalog = "rsccc")
public class BeaEmpTrvlAcct implements java.io.Serializable {
	private static final long serialVersionUID = 8263310952668698803L;

	private BeaEmpTrvlAccId id;
	private String fund;
	private String func;
	private String obj;
	private String sobj;
	private String org;
	private String fsclYr;
	private String pgm;
	private String edSpan;
	private String projDtl;
	private double acctAmt;
	private double acctPct;

	@EmbeddedId

	@AttributeOverrides({
			@AttributeOverride(name = "tripNbr", column = @Column(name = "TRIP_NBR", nullable = false, length = 9, columnDefinition = "long default 0")),
			@AttributeOverride(name = "tripSeqNbr", column = @Column(name = "TRIP_SEQ_NBR", nullable = false, length = 2, columnDefinition = "long default 0 ")),
			@AttributeOverride(name = "distrSeqNbr", column = @Column(name = "DISTR_SEQ_NBR", nullable = false, length = 2, columnDefinition = "long default 0")) })
	public BeaEmpTrvlAccId getId() {
		return id;
	}

	public void setId(BeaEmpTrvlAccId id) {
		this.id = id;
	}

	@Column(name = "FUND", nullable = false, length = 3)
	public String getFund() {
		return fund;
	}

	public void setFund(String fund) {
		this.fund = fund;
	}

	@Column(name = "FUNC", nullable = false, length = 2)
	public String getFunc() {
		return func;
	}

	public void setFunc(String func) {
		this.func = func;
	}

	@Column(name = "OBJ", nullable = false, length = 4)
	public String getObj() {
		return obj;
	}

	public void setObj(String obj) {
		this.obj = obj;
	}

	@Column(name = "SOBJ", nullable = false, length = 2)
	public String getSobj() {
		return sobj;
	}

	public void setSobj(String sobj) {
		this.sobj = sobj;
	}

	@Column(name = "ORG", nullable = false, length = 3)
	public String getOrg() {
		return org;
	}

	public void setOrg(String org) {
		this.org = org;
	}

	@Column(name = "FSCL_YR", nullable = false, length = 1)
	public String getFsclYr() {
		return fsclYr;
	}

	public void setFsclYr(String fsclYr) {
		this.fsclYr = fsclYr;
	}

	@Column(name = "PGM", nullable = false, length = 2)
	public String getPgm() {
		return pgm;
	}

	public void setPgm(String pgm) {
		this.pgm = pgm;
	}

	@Column(name = "ED_SPAN", nullable = false, length = 1)
	public String getEdSpan() {
		return edSpan;
	}

	public void setEdSpan(String edSpan) {
		this.edSpan = edSpan;
	}

	@Column(name = "PROJ_DTL", nullable = false, length = 2)
	public String getProjDtl() {
		return projDtl;
	}

	public void setProjDtl(String projDtl) {
		this.projDtl = projDtl;
	}

	@Column(name = "ACCT_AMT", nullable = false, length = 6, columnDefinition = "long default 0")
	public double getAcctAmt() {
		return acctAmt;
	}

	public void setAcctAmt(double acctAmt) {
		this.acctAmt = acctAmt;
	}

	@Column(name = "ACCT_PCT", nullable = false, length = 4, columnDefinition = "long default 0")
	public double getAcctPct() {
		return acctPct;
	}

	public void setAcctPct(double perCentValue) {
		this.acctPct = perCentValue;
	}

	@Override
	public String toString() {
		return "BeaEmpTrvlAcct [id=" + id + ", fund=" + fund + ", func=" + func + ", obj=" + obj + ", sobj=" + sobj
				+ ", org=" + org + ", fsclYr=" + fsclYr + ", pgm=" + pgm + ", edSpan=" + edSpan + ", projDtl=" + projDtl
				+ ", acctAmt=" + acctAmt + ", acctPct=" + acctPct + "]";
	}

}