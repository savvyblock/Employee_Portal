package net.esc20.txeis.EmployeeAccess.domainobject;

import java.io.Serializable;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.ICode;

public class EmployeeInfo implements Serializable
{
	private static final long serialVersionUID = -3305307693541705891L;

	private ICode degree;
	private String proExperience;
	private String proExperienceDistrict;
	private String nonProExperience;
	private String nonProExperienceDistrict;

	public ICode getDegree() {
		return degree;
	}

	public void setDegree(ICode degree) {
		this.degree = degree;
	}

	public String getProExperience() {
		return proExperience;
	}

	public void setProExperience(String proExperience) {
		this.proExperience = proExperience;
	}

	public String getProExperienceDistrict() {
		return proExperienceDistrict;
	}

	public void setProExperienceDistrict(String proExperienceDistrict) {
		this.proExperienceDistrict = proExperienceDistrict;
	}

	public String getNonProExperience() {
		return nonProExperience;
	}

	public void setNonProExperience(String nonProExperience) {
		this.nonProExperience = nonProExperience;
	}

	public String getNonProExperienceDistrict() {
		return nonProExperienceDistrict;
	}

	public void setNonProExperienceDistrict(String nonProExperienceDistrict) {
		this.nonProExperienceDistrict = nonProExperienceDistrict;
	}
}