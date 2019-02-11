package net.esc20.txeis.EmployeeAccess.domainobject;

import java.io.Serializable;

public class EmploymentInfo implements Serializable
{
	private static final long serialVersionUID = -6313096020901937064L;
	
	private Integer totalExperience;
	private Integer districtExperience;
	
	public Integer getTotalExperience() {
		return totalExperience;
	}
	public void setTotalExperience(Integer totalExperience) {
		this.totalExperience = totalExperience;
	}
	public Integer getDistrictExperience() {
		return districtExperience;
	}
	public void setDistrictExperience(Integer districtExperience) {
		this.districtExperience = districtExperience;
	}
}