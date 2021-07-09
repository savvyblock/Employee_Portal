package com.esc20.nonDBModels;

import java.io.Serializable;
import java.math.BigDecimal;

public class BfnOptionsTrvl implements Serializable {

	private static final long serialVersionUID = 4279214120117493876L;

	private String reqStEndTimes;
	private String reqOdometer;
	private BigDecimal mileageRate;
	private String locLocking;
	private BigDecimal brkfstRt;
	private BigDecimal lunchRt;
	private BigDecimal dinRt;
	
	public BfnOptionsTrvl() {
	}

	public BfnOptionsTrvl(String reqStEndTimes, String reqOdometer, BigDecimal mileageRate, String locLocking, BigDecimal brkfstRt, BigDecimal lunchRt, BigDecimal dinRt) {
		super();
		this.reqStEndTimes = reqStEndTimes;
		this.reqOdometer = reqOdometer;
		this.mileageRate = mileageRate;
		this.locLocking = locLocking;
		this.brkfstRt = brkfstRt;
		this.lunchRt = lunchRt;
		this.dinRt = dinRt;
	}

	public String getReqStEndTimes() {
		return reqStEndTimes;
	}

	public void setReqStEndTimes(String reqStEndTimes) {
		this.reqStEndTimes = reqStEndTimes;
	}

	public String getReqOdometer() {
		return reqOdometer;
	}

	public void setReqOdometer(String reqOdometer) {
		this.reqOdometer = reqOdometer;
	}

	public BigDecimal getMileageRate() {
		return mileageRate;
	}

	public void setMileageRate(BigDecimal mileageRate) {
		this.mileageRate = mileageRate;
	}

	public String getLocLocking() {
		return locLocking;
	}

	public void setLocLocking(String locLocking) {
		this.locLocking = locLocking;
	}

	public BigDecimal getBrkfstRt() {
		return brkfstRt;
	}

	public void setBrkfstRt(BigDecimal brkfstRt) {
		this.brkfstRt = brkfstRt;
	}

	public BigDecimal getLunchRt() {
		return lunchRt;
	}

	public void setLunchRt(BigDecimal lunchRt) {
		this.lunchRt = lunchRt;
	}

	public BigDecimal getDinRt() {
		return dinRt;
	}

	public void setDinRt(BigDecimal dinRt) {
		this.dinRt = dinRt;
	}
}
