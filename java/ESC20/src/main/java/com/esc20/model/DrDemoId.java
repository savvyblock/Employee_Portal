package com.esc20.model;
// Generated Jan 4, 2019 3:55:15 PM by Hibernate Tools 4.3.5.Final

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * DrDemoId generated by hbm2java
 */
@Embeddable
public class DrDemoId implements java.io.Serializable {

	private String schYr;
	private String distId;

	public DrDemoId() {
	}

	public DrDemoId(String schYr, String distId) {
		this.schYr = schYr;
		this.distId = distId;
	}

	@Column(name = "SCH_YR", nullable = false, length = 4)
	public String getSchYr() {
		return this.schYr;
	}

	public void setSchYr(String schYr) {
		this.schYr = schYr;
	}

	@Column(name = "DIST_ID", nullable = false, length = 6)
	public String getDistId() {
		return this.distId;
	}

	public void setDistId(String distId) {
		this.distId = distId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof DrDemoId))
			return false;
		DrDemoId castOther = (DrDemoId) other;

		return ((this.getSchYr() == castOther.getSchYr()) || (this.getSchYr() != null && castOther.getSchYr() != null
				&& this.getSchYr().equals(castOther.getSchYr())))
				&& ((this.getDistId() == castOther.getDistId()) || (this.getDistId() != null
						&& castOther.getDistId() != null && this.getDistId().equals(castOther.getDistId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getSchYr() == null ? 0 : this.getSchYr().hashCode());
		result = 37 * result + (getDistId() == null ? 0 : this.getDistId().hashCode());
		return result;
	}

}
