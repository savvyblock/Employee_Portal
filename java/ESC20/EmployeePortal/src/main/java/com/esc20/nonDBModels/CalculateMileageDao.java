package com.esc20.nonDBModels;

import java.io.Serializable;
import java.math.BigDecimal;

public class CalculateMileageDao implements Serializable {

	private static final long serialVersionUID = 6900889625380968063L;

	private BigDecimal begOdometer;
	private BigDecimal endOdometer;
	private BigDecimal mapMiles;
	private String roundTrip;
	private String useEmpTrvlCommuteDist;
	private BigDecimal miscAmt;
	private BigDecimal mileageAmt;
	private BigDecimal brkfstAmt;
	private BigDecimal lunchAmt;
	private BigDecimal dinAmt;
	private BigDecimal parkAmt;
	private BigDecimal taxiAmt;
	private BigDecimal accomAmt;
	private BigDecimal amount;
	private String totalAmount;

	public CalculateMileageDao(BigDecimal begOdometer, BigDecimal endOdometer, BigDecimal mapMiles, String roundTrip,
			String useEmpTrvlCommuteDist, BigDecimal miscAmt, BigDecimal mileageAmt, BigDecimal brkfstAmt,
			BigDecimal lunchAmt, BigDecimal dinAmt, BigDecimal parkAmt, BigDecimal taxiAmt,
			BigDecimal accomAmt) {
		super();
		this.begOdometer = begOdometer;
		this.endOdometer = endOdometer;
		this.mapMiles = mapMiles;
		this.roundTrip = roundTrip;
		this.useEmpTrvlCommuteDist = useEmpTrvlCommuteDist;
		this.miscAmt = miscAmt;
		this.mileageAmt = mileageAmt;
		this.brkfstAmt = brkfstAmt;
		this.lunchAmt = lunchAmt;
		this.dinAmt = dinAmt;
		this.parkAmt = parkAmt;
		this.taxiAmt = taxiAmt;
		this.accomAmt = accomAmt;
	}

	public BigDecimal getBegOdometer() {
		return begOdometer;
	}

	public void setBegOdometer(BigDecimal begOdometer) {
		this.begOdometer = begOdometer;
	}

	public BigDecimal getEndOdometer() {
		return endOdometer;
	}

	public void setEndOdometer(BigDecimal endOdometer) {
		this.endOdometer = endOdometer;
	}
	
	public BigDecimal getMapMiles() {
		return mapMiles;
	}

	public void setMapMiles(BigDecimal mapMiles) {
		this.mapMiles = mapMiles;
	}

	public String getRoundTrip() {
		return roundTrip;
	}

	public void setRoundTrip(String roundTrip) {
		this.roundTrip = roundTrip;
	}

	public String getUseEmpTrvlCommuteDist() {
		return useEmpTrvlCommuteDist;
	}

	public void setUseEmpTrvlCommuteDist(String useEmpTrvlCommuteDist) {
		this.useEmpTrvlCommuteDist = useEmpTrvlCommuteDist;
	}

	public BigDecimal getMiscAmt() {
		return miscAmt;
	}

	public void setMiscAmt(BigDecimal miscAmt) {
		this.miscAmt = miscAmt;
	}
	
	public BigDecimal getMileageAmt() {
		return mileageAmt;
	}

	public void setMileageAmt(BigDecimal mileageAmt) {
		this.mileageAmt = mileageAmt;
	}

	public BigDecimal getBrkfstAmt() {
		return brkfstAmt;
	}

	public void setBrkfstAmt(BigDecimal brkfstAmt) {
		this.brkfstAmt = brkfstAmt;
	}

	public BigDecimal getLunchAmt() {
		return lunchAmt;
	}

	public void setLunchAmt(BigDecimal lunchAmt) {
		this.lunchAmt = lunchAmt;
	}

	public BigDecimal getDinAmt() {
		return dinAmt;
	}

	public void setDinAmt(BigDecimal dinAmt) {
		this.dinAmt = dinAmt;
	}

	public BigDecimal getParkAmt() {
		return parkAmt;
	}

	public void setParkAmt(BigDecimal parkAmt) {
		this.parkAmt = parkAmt;
	}

	public BigDecimal getTaxiAmt() {
		return taxiAmt;
	}

	public void setTaxiAmt(BigDecimal taxiAmt) {
		this.taxiAmt = taxiAmt;
	}

	public BigDecimal getAccomAmt() {
		return accomAmt;
	}

	public void setAccomAmt(BigDecimal accomAmt) {
		this.accomAmt = accomAmt;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
}
