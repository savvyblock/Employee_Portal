package net.esc20.txeis.EmployeeAccess.domainobject;

import java.io.Serializable;

public class PayPrint implements Serializable {

	private static final long serialVersionUID = -5761255201747153410L;
	
	//district info
	private String dname;
	private String daddress;
	private String dcityst;
	
	//employee info
	private String ename;
	private String eaddress;
	private String ecityst;
	private String phoneNumber;
	private String employeeNumber;
	private String dateOfBirth;
	private String gender;
	private String degree;
	private String proExperience;
	private String proExperienceDistrict;
	private String nonProExperience;
	private String nonProExperienceDistrict;
	
	public String getDname() {
		return dname;
	}
	public void setDname(String dname) {
		this.dname = dname;
	}
	public String getDaddress() {
		return daddress;
	}
	public void setDaddress(String daddress) {
		this.daddress = daddress;
	}
	public String getDcityst() {
		return dcityst;
	}
	public void setDcityst(String dcityst) {
		this.dcityst = dcityst;
	}
	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}
	public String getEaddress() {
		return eaddress;
	}
	public void setEaddress(String eaddress) {
		this.eaddress = eaddress;
	}
	public String getEcityst() {
		return ecityst;
	}
	public void setEcityst(String ecityst) {
		this.ecityst = ecityst;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getEmployeeNumber() {
		return employeeNumber;
	}
	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
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
