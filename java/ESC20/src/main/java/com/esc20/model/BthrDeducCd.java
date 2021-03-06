package com.esc20.model;
// Generated Jan 4, 2019 3:51:06 PM by Hibernate Tools 4.3.5.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * BthrDeducCd generated by hbm2java
 */
@Entity
@Table(name = "BTHR_DEDUC_CD", schema = "rsccc", catalog = "rsccc")
public class BthrDeducCd implements java.io.Serializable {

	private String dedCd;
	private BthrDeducAbbrevTypCd bthrDeducAbbrevTypCd;
	private String shortDescr;
	private String longDescr;
	private String vendorNbr;
	private String sobj;
	private char liabilityChk;
	private char hraPlanTyp;
	private String hraPlanName;
	private char wire;
	private String extrDedCd;
	private char w2EmplrHlthcare;
	private String module;

	public BthrDeducCd() {
	}

	public BthrDeducCd(String dedCd, BthrDeducAbbrevTypCd bthrDeducAbbrevTypCd, String shortDescr, String longDescr,
			String vendorNbr, String sobj, char liabilityChk, char hraPlanTyp, String hraPlanName, char wire,
			String extrDedCd, char w2EmplrHlthcare, String module) {
		this.dedCd = dedCd;
		this.bthrDeducAbbrevTypCd = bthrDeducAbbrevTypCd;
		this.shortDescr = shortDescr;
		this.longDescr = longDescr;
		this.vendorNbr = vendorNbr;
		this.sobj = sobj;
		this.liabilityChk = liabilityChk;
		this.hraPlanTyp = hraPlanTyp;
		this.hraPlanName = hraPlanName;
		this.wire = wire;
		this.extrDedCd = extrDedCd;
		this.w2EmplrHlthcare = w2EmplrHlthcare;
		this.module = module;
	}

	@Id

	@Column(name = "DED_CD", nullable = false, length = 3)
	public String getDedCd() {
		return this.dedCd;
	}

	public void setDedCd(String dedCd) {
		this.dedCd = dedCd;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DED_ABBREV_TYP", nullable = false)
	public BthrDeducAbbrevTypCd getBthrDeducAbbrevTypCd() {
		return this.bthrDeducAbbrevTypCd;
	}

	public void setBthrDeducAbbrevTypCd(BthrDeducAbbrevTypCd bthrDeducAbbrevTypCd) {
		this.bthrDeducAbbrevTypCd = bthrDeducAbbrevTypCd;
	}

	@Column(name = "SHORT_DESCR", nullable = false, length = 16)
	public String getShortDescr() {
		return this.shortDescr;
	}

	public void setShortDescr(String shortDescr) {
		this.shortDescr = shortDescr;
	}

	@Column(name = "LONG_DESCR", nullable = false, length = 35)
	public String getLongDescr() {
		return this.longDescr;
	}

	public void setLongDescr(String longDescr) {
		this.longDescr = longDescr;
	}

	@Column(name = "VENDOR_NBR", nullable = false, length = 5)
	public String getVendorNbr() {
		return this.vendorNbr;
	}

	public void setVendorNbr(String vendorNbr) {
		this.vendorNbr = vendorNbr;
	}

	@Column(name = "SOBJ", nullable = false, length = 2)
	public String getSobj() {
		return this.sobj;
	}

	public void setSobj(String sobj) {
		this.sobj = sobj;
	}

	@Column(name = "LIABILITY_CHK", nullable = false, length = 1)
	public char getLiabilityChk() {
		return this.liabilityChk;
	}

	public void setLiabilityChk(char liabilityChk) {
		this.liabilityChk = liabilityChk;
	}

	@Column(name = "HRA_PLAN_TYP", nullable = false, length = 1)
	public char getHraPlanTyp() {
		return this.hraPlanTyp;
	}

	public void setHraPlanTyp(char hraPlanTyp) {
		this.hraPlanTyp = hraPlanTyp;
	}

	@Column(name = "HRA_PLAN_NAME", nullable = false, length = 10)
	public String getHraPlanName() {
		return this.hraPlanName;
	}

	public void setHraPlanName(String hraPlanName) {
		this.hraPlanName = hraPlanName;
	}

	@Column(name = "WIRE", nullable = false, length = 1)
	public char getWire() {
		return this.wire;
	}

	public void setWire(char wire) {
		this.wire = wire;
	}

	@Column(name = "EXTR_DED_CD", nullable = false, length = 6)
	public String getExtrDedCd() {
		return this.extrDedCd;
	}

	public void setExtrDedCd(String extrDedCd) {
		this.extrDedCd = extrDedCd;
	}

	@Column(name = "W2_EMPLR_HLTHCARE", nullable = false, length = 1)
	public char getW2EmplrHlthcare() {
		return this.w2EmplrHlthcare;
	}

	public void setW2EmplrHlthcare(char w2EmplrHlthcare) {
		this.w2EmplrHlthcare = w2EmplrHlthcare;
	}

	@Column(name = "MODULE", nullable = false, length = 25)
	public String getModule() {
		return this.module;
	}

	public void setModule(String module) {
		this.module = module;
	}

}
