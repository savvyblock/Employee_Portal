package com.esc20.nonDBModels;

import java.io.Serializable;

import com.esc20.util.StringUtil;

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
		//ALC-13 to fixed null pointer issues
		this.setAddress((StringUtil.trim(strNbrDist)) + " " +(StringUtil.trim(strNameDist)));
		this.setName((StringUtil.trim( distName)));
		this.setCity((StringUtil.trim(cityNameDist)));
		this.setState((StringUtil.trim( stateCd)));
		this.setZip((StringUtil.trim( zipDist)));
		this.setZip4((StringUtil.trim(zip4Dist)));
		this.setPhone((StringUtil.trim(areaCdDist)) + (StringUtil.trim(phoneNbrDist)));
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
