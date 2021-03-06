package com.esc20.model;
// Generated Jan 4, 2019 3:51:33 PM by Hibernate Tools 4.3.5.Final

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * BthrFundToGrantId generated by hbm2java
 */
@Embeddable
public class BthrFundToGrantId implements java.io.Serializable {

	private String trsGrantCd;
	private String fund;
	private char glFileId;
	private char fsclYr;

	public BthrFundToGrantId() {
	}

	public BthrFundToGrantId(String trsGrantCd, String fund, char glFileId, char fsclYr) {
		this.trsGrantCd = trsGrantCd;
		this.fund = fund;
		this.glFileId = glFileId;
		this.fsclYr = fsclYr;
	}

	@Column(name = "TRS_GRANT_CD", nullable = false, length = 2)
	public String getTrsGrantCd() {
		return this.trsGrantCd;
	}

	public void setTrsGrantCd(String trsGrantCd) {
		this.trsGrantCd = trsGrantCd;
	}

	@Column(name = "FUND", nullable = false, length = 3)
	public String getFund() {
		return this.fund;
	}

	public void setFund(String fund) {
		this.fund = fund;
	}

	@Column(name = "GL_FILE_ID", nullable = false, length = 1)
	public char getGlFileId() {
		return this.glFileId;
	}

	public void setGlFileId(char glFileId) {
		this.glFileId = glFileId;
	}

	@Column(name = "FSCL_YR", nullable = false, length = 1)
	public char getFsclYr() {
		return this.fsclYr;
	}

	public void setFsclYr(char fsclYr) {
		this.fsclYr = fsclYr;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof BthrFundToGrantId))
			return false;
		BthrFundToGrantId castOther = (BthrFundToGrantId) other;

		return ((this.getTrsGrantCd() == castOther.getTrsGrantCd()) || (this.getTrsGrantCd() != null
				&& castOther.getTrsGrantCd() != null && this.getTrsGrantCd().equals(castOther.getTrsGrantCd())))
				&& ((this.getFund() == castOther.getFund()) || (this.getFund() != null && castOther.getFund() != null
						&& this.getFund().equals(castOther.getFund())))
				&& (this.getGlFileId() == castOther.getGlFileId()) && (this.getFsclYr() == castOther.getFsclYr());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getTrsGrantCd() == null ? 0 : this.getTrsGrantCd().hashCode());
		result = 37 * result + (getFund() == null ? 0 : this.getFund().hashCode());
		result = 37 * result + this.getGlFileId();
		result = 37 * result + this.getFsclYr();
		return result;
	}

}
