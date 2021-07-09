package com.esc20.nonDBModels;

import java.io.Serializable;

public class TravelInfo implements Serializable {

	private static final long serialVersionUID = -2813087688765357852L;

	private String tripNbr;
	private String firstDate;
	private String ovrnight;
	private String checkNbr;
	private String checkDate;
	private String status;
	private String requestTotal;

	public TravelInfo() {
	}

	public TravelInfo(String tripNbr, String firstDate, String ovrnight, String status) {
		super();
		this.tripNbr = tripNbr;
		this.firstDate = firstDate;
		this.ovrnight = ovrnight;
		this.status = status;
	}
		
	public TravelInfo(String checkNbr, String checkDate) {
		super();
		this.checkNbr = checkNbr;
		this.checkDate = checkDate;
	}

	public String getTripNbr() {
		return tripNbr;
	}

	public void setTripNbr(String tripNbr) {
		this.tripNbr = tripNbr;
	}

	public String getFirstDate() {
		return firstDate;
	}

	public void setFirstDate(String firstDate) {
		this.firstDate = firstDate;
	}

	public String getOvrnight() {
		return ovrnight;
	}

	public void setOvrnight(String ovrnight) {
		this.ovrnight = ovrnight;
	}
	
	public String getRequestTotal() {
		return requestTotal;
	}

	public void setRequestTotal(String requestTotal) {
		this.requestTotal = requestTotal;
	}

	public String getCheckNbr() {
		return checkNbr;
	}

	public void setCheckNbr(String checkNbr) {
		this.checkNbr = checkNbr;
	}

	public String getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}