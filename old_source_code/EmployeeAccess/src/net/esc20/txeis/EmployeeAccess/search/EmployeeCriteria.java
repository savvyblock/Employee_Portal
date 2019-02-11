package net.esc20.txeis.EmployeeAccess.search;

import java.io.Serializable;

public class EmployeeCriteria implements Serializable
{
	private static final long serialVersionUID = -9022957219187325657L;
	
	private String searchEmployeeNumber = "";
	private String searchSSN = "";
	private String searchDateofBirthMonth = "";
	private String searchDateofBirthDay = "";
	private String searchDateofBirthYear = "";
	private String searchZipCode = "";
	private String searchZipCodeExt = "";
	
	public String getSearchEmployeeNumber() {
		return searchEmployeeNumber;
	}
	public void setSearchEmployeeNumber(String searchEmployeeNumber) {
		this.searchEmployeeNumber = searchEmployeeNumber;
	}
	public String getSearchDateofBirthMonth() {
		return searchDateofBirthMonth;
	}
	public void setSearchDateofBirthMonth(String searchDateofBirthMonth) {
		this.searchDateofBirthMonth = searchDateofBirthMonth;
	}
	public String getSearchDateofBirthDay() {
		return searchDateofBirthDay;
	}
	public void setSearchDateofBirthDay(String searchDateofBirthDay) {
		this.searchDateofBirthDay = searchDateofBirthDay;
	}
	public String getSearchDateofBirthYear() {
		return searchDateofBirthYear;
	}
	public void setSearchDateofBirthYear(String searchDateofBirthYear) {
		this.searchDateofBirthYear = searchDateofBirthYear;
	}
	public String getSearchFormattedDateofBirth() {
		return searchDateofBirthYear+searchDateofBirthMonth+searchDateofBirthDay;
	}
	public String getSearchZipCode() {
		return searchZipCode;
	}
	public void setSearchZipCode(String searchZipCode) {
		this.searchZipCode = searchZipCode;
	}
	public String getSearchZipCodeExt() {
		return searchZipCodeExt;
	}
	public void setSearchZipCodeExt(String searchZipCodeExt) {
		this.searchZipCodeExt = searchZipCodeExt;
	}
	public String getSearchSSN() {
		return searchSSN;
	}
	public void setSearchSSN(String searchSSN) {
		this.searchSSN = searchSSN;
	}
	
}