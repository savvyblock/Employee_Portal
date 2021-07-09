package com.esc20.nonDBModels;

import java.io.Serializable;

public class TravelDates implements Serializable {

	private static final long serialVersionUID = -7076370709243232248L;

	private int daysCount;
	private String travelDate;
	private String travelRequests;

	public TravelDates() {
	}

	public TravelDates(int daysCount, String travelDate) {
		this.daysCount = daysCount;
		this.travelDate = travelDate;
	}

	public int getDaysCount() {
		return daysCount;
	}

	public void setDaysCount(int daysCount) {
		this.daysCount = daysCount;
	}

	public String getTravelDate() {
		return travelDate;
	}

	public void setTravelDate(String travelDate) {
		this.travelDate = travelDate;
	}
	
	public String getTravelRequests() {
		return travelRequests;
	}

	public void setTravelRequests(String travelRequests) {
		this.travelRequests = travelRequests;
	}

}
