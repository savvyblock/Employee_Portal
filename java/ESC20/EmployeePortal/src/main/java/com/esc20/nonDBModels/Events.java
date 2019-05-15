package com.esc20.nonDBModels;

import java.text.SimpleDateFormat;

import net.sf.json.JSONObject;

public class Events {

	private String title;
	private String startDate;
	private String endDate;
	private String id;
	
	public Events(AppLeaveRequest request) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		this.title = "Leave";
		this.startDate = sdf.format(request.getDatetimeFrom());
		this.id = request.getId()+"";
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public JSONObject toJson() {
		// TODO Auto-generated method stub
		JSONObject jo = new JSONObject();
		jo.put("id", this.getId());
		jo.put("startDate", this.getStartDate());
		jo.put("endDate", this.getEndDate());
		jo.put("title", this.getTitle());
		return jo;
	}

}
