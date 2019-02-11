package net.esc20.txeis.EmployeeAccess.domainobject;

import java.io.Serializable;

import net.esc20.txeis.EmployeeAccess.util.StringUtil;

public class User implements Serializable
{
	public static enum Gender
	{
		Male,
		Female;
		
		public static Gender getGender(String code)
		{
			if("M".equals(code))
			{
				return Male;
			}
			else if("F".equals(code))
			{
				return Female;
			}
			else
			{
				throw new IllegalArgumentException("Invalid gender");
			}
		}
	}
	
	private static final long serialVersionUID = 5766981746216687147L;
	
	private String employeeNumber ="";
	private String ssn = "";
	private String dateOfBirth = "";
	private String address = "";
	private String city = "";
	private String state = "";
	private String zipCode ="";
	private String zipCode4 = "";
	private String userName = "";
	private String workEmail = "";
	private String homeEmail = "";
	private String workEmailVerification = "";
	private String homeEmailVerification = "";
	private String hint = "";
	private String hintAnswer = "";
	private String firstName ="";
	private String middleName = "";
	private String lastName = "";
	private String generation = "";
	private String generationDescr = "";   //jf20150113 Display description instead of code fix
	private String password;
	private String passwordVerification = "";
	private District employer;
	private Gender gender;
	private String phoneNumber;
	
	private boolean homeEmailEmpty = true;
	private boolean workEmailEmpty = true;
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public District getEmployer() {
		return employer;
	}
	public void setEmployer(District employer) {
		this.employer = employer;
	}
	public String getEmployeeNumber() {
		return employeeNumber;
	}
	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}
	public String getSsn() {
		return ssn;
	}
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getFormattedDateOfBirth() {
		if(dateOfBirth.length() >= 8)
			return StringUtil.mid(dateOfBirth, 5, 2) + "-" + StringUtil.right(dateOfBirth, 2) + "-" + StringUtil.left(dateOfBirth, 4);
		
		return "";
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getZipCode4() {
		return zipCode4;
	}
	public void setZipCode4(String zipCode4) {
		this.zipCode4 = zipCode4;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getWorkEmail() {
		return workEmail;
	}
	public void setWorkEmail(String workEmail) {
		this.workEmail = workEmail;
	}
	public String getHomeEmail() {
		return homeEmail;
	}
	public void setHomeEmail(String homeEmail) {
		this.homeEmail = homeEmail;
	}
	public String getHint() {
		return hint;
	}
	public void setHint(String hint) {
		this.hint = hint;
	}
	public String getHintAnswer() {
		return hintAnswer;
	}
	public void setHintAnswer(String hintAnswer) {
		this.hintAnswer = hintAnswer;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getGeneration() {
		return generation;
	}
	public void setGeneration(String generation) {
		this.generation = generation;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPasswordVerification() {
		return passwordVerification;
	}
	public void setPasswordVerification(String passwordVerification) {
		this.passwordVerification = passwordVerification;
	}
	public String getHomeEmailVerification() {
		return homeEmailVerification;
	}
	public void setHomeEmailVerification(String homeEmailVerification) {
		this.homeEmailVerification = homeEmailVerification;
	}
	public String getWorkEmailVerification() {
		return workEmailVerification;
	}
	public void setWorkEmailVerification(String workEmailVerification) {
		this.workEmailVerification = workEmailVerification;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public boolean isHomeEmailEmpty() {
		return homeEmailEmpty;
	}
	public void setHomeEmailEmpty(boolean homeEmailEmpty) {
		this.homeEmailEmpty = homeEmailEmpty;
	}
	public boolean isWorkEmailEmpty() {
		return workEmailEmpty;
	}
	public void setWorkEmailEmpty(boolean workEmailEmpty) {
		this.workEmailEmpty = workEmailEmpty;
	}
	public String getGenerationDescr() {   //jf20150113 Display description instead of code fix
		return generationDescr;
	}
	public void setGenerationDescr(String generationDescr) {   //jf20150113 Display description instead of code fix
		this.generationDescr = generationDescr;
	}
}