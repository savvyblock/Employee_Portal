package com.esc20.nonDBModels;

import java.io.Serializable;
import java.math.BigDecimal;

public class TravelRequestInfo implements Serializable {
	private static final long serialVersionUID = 8818435282188753907L;

	private String vendorNbr;
	private String addressAtn;
	private String addressStreet;
	private String addressCity;
	private String addressState;
	private String addressZip;
	private String addressZip4;
	private BigDecimal trvlCommuteDist;
	private String payCampus;
	private String campuses;
	private String campus;
	private String overnightTrip;
	private String addressType;

	public TravelRequestInfo() {
	}
	
	public TravelRequestInfo(String campuses) {
		this.campuses = campuses;
	}

	public TravelRequestInfo(String vendorNbr, String addressAtn, String addressStreet, String addressCity,
			String addressState, String addressZip, String addressZip4, BigDecimal trvlCommuteDist) {
		this.vendorNbr = vendorNbr;
		this.addressAtn = addressAtn;
		this.addressStreet = addressStreet;
		this.addressCity = addressCity;
		this.addressState = addressState;
		this.addressZip = addressZip;
		this.addressZip4 = addressZip4;
		this.trvlCommuteDist = trvlCommuteDist;
	}

	public String getVendorNbr() {
		return vendorNbr;
	}

	public void setVendorNbr(String vendorNbr) {
		this.vendorNbr = vendorNbr;
	}

	public String getAddressAtn() {
		return addressAtn;
	}

	public void setAddressAtn(String addressAtn) {
		this.addressAtn = addressAtn;
	}

	public String getAddressStreet() {
		return addressStreet;
	}

	public void setAddressStreet(String addressStreet) {
		this.addressStreet = addressStreet;
	}

	public String getAddressCity() {
		return addressCity;
	}

	public void setAddressCity(String addressCity) {
		this.addressCity = addressCity;
	}

	public String getAddressState() {
		return addressState;
	}

	public void setAddressState(String addressState) {
		this.addressState = addressState;
	}

	public String getAddressZip() {
		return addressZip;
	}

	public void setAddressZip(String addressZip) {
		this.addressZip = addressZip;
	}

	public String getAddressZip4() {
		return addressZip4;
	}

	public void setAddressZip4(String addressZip4) {
		this.addressZip4 = addressZip4;
	}

	public BigDecimal getTrvlCommuteDist() {
		return trvlCommuteDist;
	}

	public void setTrvlCommuteDist(BigDecimal trvlCommuteDist) {
		this.trvlCommuteDist = trvlCommuteDist;
	}
	
	public String getPayCampus() {
		return payCampus;
	}

	public String setPayCampus(String payCampus) {
		return this.payCampus = payCampus;
	}
	
	public String getCampuses() {
		return campuses;
	}

	public void setCampuses(String campuses) {
		this.campuses = campuses;
	}
	
	public String getOvernightTrip() {
		return overnightTrip;
	}

	public void setOvernightTrip(String overnightTrip) {
		this.overnightTrip = overnightTrip;
	}

	public String getCampus() {
		return campus;
	}

	public void setCampus(String campus) {
		this.campus = campus;
	}
	
	public String getAddressType() {
		return addressType;
	}

	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}
}