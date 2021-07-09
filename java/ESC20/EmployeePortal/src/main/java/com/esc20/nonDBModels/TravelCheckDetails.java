package com.esc20.nonDBModels;

import java.io.Serializable;

public class TravelCheckDetails implements Serializable {

	private static final long serialVersionUID = 1620193953612012437L;

	private String checkNum;
	private String checkDt;

	public TravelCheckDetails() {
	}

	public TravelCheckDetails(String checkNum, String checkDt) {
		super();
		this.checkNum = checkNum;
		this.checkDt = checkDt;
	}

	public String getCheckNum() {
		return checkNum;
	}

	public void setCheckNum(String checkNum) {
		this.checkNum = checkNum;
	}

	public String getCheckDt() {
		return checkDt;
	}

	public void setCheckDt(String checkDt) {
		this.checkDt = checkDt;
	}
}
