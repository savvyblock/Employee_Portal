package com.esc20.model;
// Generated Jan 4, 2019 3:47:57 PM by Hibernate Tools 4.3.5.Final

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * BhrTrsEr10 generated by hbm2java
 */
@Entity
@Table(name = "BHR_TRS_ER10", schema = "rsccc", catalog = "rsccc")
public class BhrTrsEr10 implements java.io.Serializable {

	private BhrTrsEr10Id id;
	private String unitsWrkedHt;
	private String unitsReqrdFt;
	private char unitsCd;
	private String svcDays;
	private String trsPosCd;
	private String begDt;
	private String endDt;
	private char disabFlg;
	private char employFtTypCd;
	private String module;

	public BhrTrsEr10() {
	}

	public BhrTrsEr10(BhrTrsEr10Id id, String unitsWrkedHt, String unitsReqrdFt, char unitsCd, String svcDays,
			String trsPosCd, String begDt, String endDt, char disabFlg, char employFtTypCd, String module) {
		this.id = id;
		this.unitsWrkedHt = unitsWrkedHt;
		this.unitsReqrdFt = unitsReqrdFt;
		this.unitsCd = unitsCd;
		this.svcDays = svcDays;
		this.trsPosCd = trsPosCd;
		this.begDt = begDt;
		this.endDt = endDt;
		this.disabFlg = disabFlg;
		this.employFtTypCd = employFtTypCd;
		this.module = module;
	}

	@EmbeddedId

	@AttributeOverrides({
			@AttributeOverride(name = "staffId", column = @Column(name = "STAFF_ID", nullable = false, length = 9)),
			@AttributeOverride(name = "rptYr", column = @Column(name = "RPT_YR", nullable = false, length = 4)),
			@AttributeOverride(name = "rptMon", column = @Column(name = "RPT_MON", nullable = false, length = 2)),
			@AttributeOverride(name = "employTypCd", column = @Column(name = "EMPLOY_TYP_CD", nullable = false, length = 1)) })
	public BhrTrsEr10Id getId() {
		return this.id;
	}

	public void setId(BhrTrsEr10Id id) {
		this.id = id;
	}

	@Column(name = "UNITS_WRKED_HT", nullable = false, length = 3)
	public String getUnitsWrkedHt() {
		return this.unitsWrkedHt;
	}

	public void setUnitsWrkedHt(String unitsWrkedHt) {
		this.unitsWrkedHt = unitsWrkedHt;
	}

	@Column(name = "UNITS_REQRD_FT", nullable = false, length = 3)
	public String getUnitsReqrdFt() {
		return this.unitsReqrdFt;
	}

	public void setUnitsReqrdFt(String unitsReqrdFt) {
		this.unitsReqrdFt = unitsReqrdFt;
	}

	@Column(name = "UNITS_CD", nullable = false, length = 1)
	public char getUnitsCd() {
		return this.unitsCd;
	}

	public void setUnitsCd(char unitsCd) {
		this.unitsCd = unitsCd;
	}

	@Column(name = "SVC_DAYS", nullable = false, length = 3)
	public String getSvcDays() {
		return this.svcDays;
	}

	public void setSvcDays(String svcDays) {
		this.svcDays = svcDays;
	}

	@Column(name = "TRS_POS_CD", nullable = false, length = 2)
	public String getTrsPosCd() {
		return this.trsPosCd;
	}

	public void setTrsPosCd(String trsPosCd) {
		this.trsPosCd = trsPosCd;
	}

	@Column(name = "BEG_DT", nullable = false, length = 8)
	public String getBegDt() {
		return this.begDt;
	}

	public void setBegDt(String begDt) {
		this.begDt = begDt;
	}

	@Column(name = "END_DT", nullable = false, length = 8)
	public String getEndDt() {
		return this.endDt;
	}

	public void setEndDt(String endDt) {
		this.endDt = endDt;
	}

	@Column(name = "DISAB_FLG", nullable = false, length = 1)
	public char getDisabFlg() {
		return this.disabFlg;
	}

	public void setDisabFlg(char disabFlg) {
		this.disabFlg = disabFlg;
	}

	@Column(name = "EMPLOY_FT_TYP_CD", nullable = false, length = 1)
	public char getEmployFtTypCd() {
		return this.employFtTypCd;
	}

	public void setEmployFtTypCd(char employFtTypCd) {
		this.employFtTypCd = employFtTypCd;
	}

	@Column(name = "MODULE", nullable = false, length = 25)
	public String getModule() {
		return this.module;
	}

	public void setModule(String module) {
		this.module = module;
	}

}
