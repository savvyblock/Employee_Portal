package com.esc20.nonDBModels;

import java.io.Serializable;

public class CalendarEvents implements Serializable {

	private static final long serialVersionUID = 7707740279167792070L;
	
	private String title = "";
	private String start = "";
	private String end = "";
	private String id ="";
	
	public CalendarEvents(String title, String start, String end, String id) {
		this.title = title;
		this.start = start;
		this.end = end;
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
