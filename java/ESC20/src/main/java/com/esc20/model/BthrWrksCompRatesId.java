package com.esc20.model;
// Generated Jan 4, 2019 3:54:58 PM by Hibernate Tools 4.3.5.Final

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * BthrWrksCompRatesId generated by hbm2java
 */
@Embeddable
public class BthrWrksCompRatesId implements java.io.Serializable {

	private char wrkComp;
	private char cyrNyrFlg;

	public BthrWrksCompRatesId() {
	}

	public BthrWrksCompRatesId(char wrkComp, char cyrNyrFlg) {
		this.wrkComp = wrkComp;
		this.cyrNyrFlg = cyrNyrFlg;
	}

	@Column(name = "WRK_COMP", nullable = false, length = 1)
	public char getWrkComp() {
		return this.wrkComp;
	}

	public void setWrkComp(char wrkComp) {
		this.wrkComp = wrkComp;
	}

	@Column(name = "CYR_NYR_FLG", nullable = false, length = 1)
	public char getCyrNyrFlg() {
		return this.cyrNyrFlg;
	}

	public void setCyrNyrFlg(char cyrNyrFlg) {
		this.cyrNyrFlg = cyrNyrFlg;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof BthrWrksCompRatesId))
			return false;
		BthrWrksCompRatesId castOther = (BthrWrksCompRatesId) other;

		return (this.getWrkComp() == castOther.getWrkComp()) && (this.getCyrNyrFlg() == castOther.getCyrNyrFlg());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getWrkComp();
		result = 37 * result + this.getCyrNyrFlg();
		return result;
	}

}
