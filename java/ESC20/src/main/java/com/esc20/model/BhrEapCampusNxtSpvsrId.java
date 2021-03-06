package com.esc20.model;
// Generated Jan 4, 2019 3:39:45 PM by Hibernate Tools 4.3.5.Final

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * BhrEapCampusNxtSpvsrId generated by hbm2java
 */
@Embeddable
public class BhrEapCampusNxtSpvsrId implements java.io.Serializable {
	private static final long serialVersionUID = 6715142900654758429L;
	private String campusId;
	private char dept;
	private String spvsrEmpNbr;

	public BhrEapCampusNxtSpvsrId() {
	}

	public BhrEapCampusNxtSpvsrId(String campusId, char dept, String spvsrEmpNbr) {
		this.campusId = campusId;
		this.dept = dept;
		this.spvsrEmpNbr = spvsrEmpNbr;
	}

	@Column(name = "CAMPUS_ID", nullable = false, length = 3)
	public String getCampusId() {
		return this.campusId;
	}

	public void setCampusId(String campusId) {
		this.campusId = campusId;
	}

	@Column(name = "DEPT", nullable = false, length = 1)
	public char getDept() {
		return this.dept;
	}

	public void setDept(char dept) {
		this.dept = dept;
	}

	@Column(name = "SPVSR_EMP_NBR", nullable = false, length = 6)
	public String getSpvsrEmpNbr() {
		return this.spvsrEmpNbr;
	}

	public void setSpvsrEmpNbr(String spvsrEmpNbr) {
		this.spvsrEmpNbr = spvsrEmpNbr;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof BhrEapCampusNxtSpvsrId))
			return false;
		BhrEapCampusNxtSpvsrId castOther = (BhrEapCampusNxtSpvsrId) other;

		return ((this.getCampusId() == castOther.getCampusId()) || (this.getCampusId() != null
				&& castOther.getCampusId() != null && this.getCampusId().equals(castOther.getCampusId())))
				&& (this.getDept() == castOther.getDept())
				&& ((this.getSpvsrEmpNbr() == castOther.getSpvsrEmpNbr())
						|| (this.getSpvsrEmpNbr() != null && castOther.getSpvsrEmpNbr() != null
								&& this.getSpvsrEmpNbr().equals(castOther.getSpvsrEmpNbr())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getCampusId() == null ? 0 : this.getCampusId().hashCode());
		result = 37 * result + this.getDept();
		result = 37 * result + (getSpvsrEmpNbr() == null ? 0 : this.getSpvsrEmpNbr().hashCode());
		return result;
	}

}
