package com.esc20.nonDBModels;

import java.io.Serializable;

public class EmployeeInfo implements Serializable{

	private static final long serialVersionUID = 6715142900654758429L;
	
	private String highDegree;
	private String highDegreeDescription;
	private String yrsProExper;
	private String yrsProExperLoc;
	private String yrsExpDist;
	private String yrsExpDistLoc;
	
	public EmployeeInfo(Object highDegree, Object highDegreeDescription, Object yrsProExper, Object yrsProExperLoc, Object yrsExpDist, Object yrsExpDistLoc) {
		this.setHighDegree(((Character) highDegree).toString());
		if(this.getHighDegree().equals("")){
			this.setHighDegreeDescription("");
		}else {
			this.setHighDegreeDescription((String) highDegreeDescription);
		}
		String proExperience = (String) yrsProExper;		
		this.setYrsProExper(proExperience == null ? "" : proExperience.trim());
		String proExperienceDistrict = (String) yrsProExperLoc;
		this.setYrsProExperLoc(proExperienceDistrict == null ? "" : proExperienceDistrict.trim());
		String nonProExperience = (String) yrsExpDist;
		this.setYrsExpDist(nonProExperience == null ? "" : nonProExperience.trim());
		String nonProExperienceDistrict = (String) yrsExpDistLoc;
		this.setYrsExpDistLoc(nonProExperienceDistrict == null ? "" : nonProExperienceDistrict.trim());
	}
	public String getHighDegree() {
		return highDegree;
	}
	public void setHighDegree(String highDegree) {
		this.highDegree = highDegree;
	}
	public String getHighDegreeDescription() {
		return highDegreeDescription;
	}
	public void setHighDegreeDescription(String highDegreeDescription) {
		this.highDegreeDescription = highDegreeDescription;
	}
	public String getYrsProExper() {
		return yrsProExper;
	}
	public void setYrsProExper(String yrsProExper) {
		this.yrsProExper = yrsProExper;
	}
	public String getYrsProExperLoc() {
		return yrsProExperLoc;
	}
	public void setYrsProExperLoc(String yrsProExperLoc) {
		this.yrsProExperLoc = yrsProExperLoc;
	}
	public String getYrsExpDist() {
		return yrsExpDist;
	}
	public void setYrsExpDist(String yrsExpDist) {
		this.yrsExpDist = yrsExpDist;
	}
	public String getYrsExpDistLoc() {
		return yrsExpDistLoc;
	}
	public void setYrsExpDistLoc(String yrsExpDistLoc) {
		this.yrsExpDistLoc = yrsExpDistLoc;
	}
	
}
