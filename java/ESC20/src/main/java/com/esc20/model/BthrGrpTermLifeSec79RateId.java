package com.esc20.model;
// Generated Jan 4, 2019 3:51:33 PM by Hibernate Tools 4.3.5.Final

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * BthrGrpTermLifeSec79RateId generated by hbm2java
 */
@Embeddable
public class BthrGrpTermLifeSec79RateId implements java.io.Serializable {

	private String taxYr;
	private short ageFrom;

	public BthrGrpTermLifeSec79RateId() {
	}

	public BthrGrpTermLifeSec79RateId(String taxYr, short ageFrom) {
		this.taxYr = taxYr;
		this.ageFrom = ageFrom;
	}

	@Column(name = "TAX_YR", nullable = false, length = 4)
	public String getTaxYr() {
		return this.taxYr;
	}

	public void setTaxYr(String taxYr) {
		this.taxYr = taxYr;
	}

	@Column(name = "AGE_FROM", nullable = false, precision = 3, scale = 0)
	public short getAgeFrom() {
		return this.ageFrom;
	}

	public void setAgeFrom(short ageFrom) {
		this.ageFrom = ageFrom;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof BthrGrpTermLifeSec79RateId))
			return false;
		BthrGrpTermLifeSec79RateId castOther = (BthrGrpTermLifeSec79RateId) other;

		return ((this.getTaxYr() == castOther.getTaxYr()) || (this.getTaxYr() != null && castOther.getTaxYr() != null
				&& this.getTaxYr().equals(castOther.getTaxYr()))) && (this.getAgeFrom() == castOther.getAgeFrom());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getTaxYr() == null ? 0 : this.getTaxYr().hashCode());
		result = 37 * result + this.getAgeFrom();
		return result;
	}

}
