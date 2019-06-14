package com.esc20.nonDBModels;

import java.io.Serializable;
import java.math.BigDecimal;

public class LeaveParameters implements Serializable {
	private static final long serialVersionUID = 9043111069932383969L;
	
	private double standardHours=0;
	private double mealBreakHours=0;
	private boolean requireLeaveHoursRequestedEntry=false;
	private boolean usePMIS=false;
	private boolean ignoreCutoffDates=false;
	private String messageLeaveRequest;
	private String urlEAHome;

	public LeaveParameters(BigDecimal standardHours, BigDecimal mealBreakHours, Character requireLeaveHoursRequestedEntry, Character usePMIS,
			Character ignoreCutoffDates, String messageLeaveRequest, String urlEAHome) {
			this.standardHours = standardHours.doubleValue();
			this.mealBreakHours = mealBreakHours.doubleValue();
			this.requireLeaveHoursRequestedEntry = ("Y").equals((requireLeaveHoursRequestedEntry.toString()));
			this.usePMIS = ("Y").equals((usePMIS.toString()));
			this.ignoreCutoffDates = ("Y").equals((ignoreCutoffDates.toString()));
			this.messageLeaveRequest = messageLeaveRequest;
			this.urlEAHome = urlEAHome;
	}
	public double getStandardHours() {
		return standardHours;
	}
	public void setStandardHours(double standardHours) {
		this.standardHours = standardHours;
	}
	public double getMealBreakHours() {
		return mealBreakHours;
	}
	public void setMealBreakHours(double mealBreakHours) {
		this.mealBreakHours = mealBreakHours;
	}
	public boolean isRequireLeaveHoursRequestedEntry() {
		return requireLeaveHoursRequestedEntry;
	}
	public void setRequireLeaveHoursRequestedEntry(
			boolean requireLeaveHoursRequestedEntry) {
		this.requireLeaveHoursRequestedEntry = requireLeaveHoursRequestedEntry;
	}
	public boolean isUsePMIS() {
		return usePMIS;
	}
	public void setUsePMIS(boolean usePMIS) {
		this.usePMIS = usePMIS;
	}
	public boolean isIgnoreCutoffDates() {
		return ignoreCutoffDates;
	}
	public void setIgnoreCutoffDates(boolean ignoreCutoffDates) {
		this.ignoreCutoffDates = ignoreCutoffDates;
	}
	public String getMessageLeaveRequest() {
		return messageLeaveRequest;
	}
	public void setMessageLeaveRequest(String messageLeaveRequest) {
		this.messageLeaveRequest = messageLeaveRequest;
	}
	public String getUrlEAHome() {
		return urlEAHome;
	}
	public void setUrlEAHome(String urlEAHome) {
		this.urlEAHome = urlEAHome;
	}
}
