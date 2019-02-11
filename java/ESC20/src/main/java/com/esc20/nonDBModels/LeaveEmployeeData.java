package com.esc20.nonDBModels;

import java.io.Serializable;
import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONObject;

public class LeaveEmployeeData implements Serializable {

	private static final long serialVersionUID = -4786595821648309430L;
	
	private String firstName="";
	private String lastName="";
	private String middleName="";
	private String middleInitial="";
	private String employeeNumber="";
	private int numDirectReports=0;
	private String emailAddress="";
	private String autoCompleteString="";
	private String userLoginName="";
	
	// for PMIS
	private String billetNumber="";
	private String posNumber="";
	
	public JSONObject toJSON() {
		JSONObject jo = new JSONObject();
		jo.put("firstName", firstName);
		jo.put("lastName", lastName);
		jo.put("middleName", middleName);
		jo.put("middleInitial", middleInitial);
		jo.put("employeeNumber", employeeNumber);
		jo.put("numDirectReports", numDirectReports);
		jo.put("emailAddress", emailAddress);
		jo.put("autoCompleteString", autoCompleteString);
		jo.put("userLoginName", userLoginName);
		jo.put("billetNumber", billetNumber);
		jo.put("posNumber", posNumber);
		jo.put("selectOptionLabel", this.getSelectOptionLabel());
		jo.put("fullNameTitleCase", this.getFullNameTitleCase());
		return jo;
	}
	
	public LeaveEmployeeData(String employeeNumber, Character payFreq, String firstName, String lastName, String middleName,
			Long numDirectReports) {
		this.employeeNumber = employeeNumber;
		this.firstName = firstName;
		this.lastName = lastName;
		this.middleName = middleName;
		this.numDirectReports = numDirectReports.intValue();
	}
	public LeaveEmployeeData() {
		// TODO Auto-generated constructor stub
	}

	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		
		this.firstName = (firstName==null ? "" : firstName.trim());
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = (lastName==null ? "" : lastName.trim());
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = (middleName==null ? "" : middleName.trim());
		if (this.middleName.length() > 0) {
			this.middleInitial = this.middleName.substring(0, 1);
		} else {
			this.middleInitial = "";
		}
	}
	public String getMiddleInitial() {
		return middleInitial;
	}
	public void setMiddleInitial(String middleInitial) {
		this.middleInitial = (middleInitial==null ? "" : middleInitial.trim());
	}
	public String getEmployeeNumber() {
		return employeeNumber;
	}
	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = (employeeNumber==null ? "" : employeeNumber.trim());
	}
	public int getNumDirectReports() {
		return numDirectReports;
	}
	public void setNumDirectReports(int numDirectReports) {
		this.numDirectReports = numDirectReports;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = (emailAddress==null ? "" : emailAddress.trim());
	}
	public String getSelectOptionLabel() {
		if(employeeNumber.trim().length()==0)
			return "";
		StringBuffer labelSB = new StringBuffer((employeeNumber.trim().length()==0 ? "000000" : employeeNumber))
			.append(" : ").append(lastName).append(", ").append(firstName);
		if (middleInitial.length() > 0) {
			labelSB.append(" ").append(middleInitial);
		}
		return labelSB.toString();
	}
	public String getFullNameTitleCase() {
		StringBuffer fullNameSB = new StringBuffer(StringUtils.capitalize(firstName.toLowerCase()));
		if (middleInitial.length() > 0) {
			fullNameSB.append(" ").append(middleInitial.toUpperCase()).append(".");
		}
		fullNameSB.append(" ").append(StringUtils.capitalize(lastName.toLowerCase()));
		return fullNameSB.toString();		
	}
	public String getBilletNumber() {
		return billetNumber;
	}
	public void setBilletNumber(String billetNumber) {
		this.billetNumber = billetNumber;
	}
	public String getPosNumber() {
		return posNumber;
	}
	public void setPosNumber(String posNumber) {
		this.posNumber = posNumber;
	}
	public String getAutoCompleteString() {
		return autoCompleteString;
	}
	public void setAutoCompleteString(String autoCompleteString) {
		this.autoCompleteString = autoCompleteString;
	}
	public String getUserLoginName() {
		return userLoginName;
	}
	public void setUserLoginName(String userLoginName) {
		this.userLoginName = userLoginName;
	}	
}