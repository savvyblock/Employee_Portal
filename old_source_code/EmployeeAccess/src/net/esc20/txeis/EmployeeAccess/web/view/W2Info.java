package net.esc20.txeis.EmployeeAccess.web.view;

import java.io.Serializable;

public class W2Info implements Serializable
{
	private static final long serialVersionUID = 1233835253276759426L;

	private String year;
	private String elecConsntW2;
	private String elecConsntMsgW2;

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getElecConsntW2() {
		return elecConsntW2;
	}

	public void setElecConsntW2(String elecConsntW2) {
		this.elecConsntW2 = elecConsntW2;
	}

	public String getElecConsntMsgW2() {
		return elecConsntMsgW2;
	}

	public void setElecConsntMsgW2(String elecConsntMsgW2) {
		this.elecConsntMsgW2 = elecConsntMsgW2;
	}
}