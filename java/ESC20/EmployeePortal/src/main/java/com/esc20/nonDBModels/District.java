package com.esc20.nonDBModels;

import java.io.Serializable;

public class District implements Serializable
{
	private static final long serialVersionUID = -6380079004967418787L;
	
	private String number = "";
	private String name = "";
	private String address = "";
	private String state = "";
	private String city = "";
	private String zip = "";
	private String zip4 = "";
	private String phone = "";	
	private String ein = "";
	
	public District(Object distName, Object strNbrDist, Object strNameDist, Object cityNameDist, Object stateCd, Object zipDist,
			Object zip4Dist, Object areaCdDist, Object phoneNbrDist) {
		this.setAddress(((String) strNbrDist).trim() + " " +((String)strNameDist).trim());
		this.setName(((String) distName).trim());
		this.setCity(((String) cityNameDist).trim());
		this.setState(((String) stateCd).trim());
		this.setZip(((String) zipDist).trim());
		this.setZip4(((String) zip4Dist).trim());
		this.setPhone(((String)areaCdDist).trim() + ((String)phoneNbrDist).trim());
	}
	public District() {

	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getZip4() {
		return zip4;
	}
	public void setZip4(String zip4) {
		this.zip4 = zip4;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEin() {
		return ein;
	}
	public void setEin(String ein) {
		this.ein = ein;
	}
}
