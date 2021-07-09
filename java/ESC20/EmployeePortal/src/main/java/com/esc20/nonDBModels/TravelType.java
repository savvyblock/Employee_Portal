package com.esc20.nonDBModels;

import java.io.Serializable;

public class TravelType implements Serializable {

	private static final long serialVersionUID = -612280615341778765L;
	
	private String code;
	private String description;
	
	public TravelType() {

	}

	public TravelType(String code, String description) {
		super();
		this.code = code;
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
