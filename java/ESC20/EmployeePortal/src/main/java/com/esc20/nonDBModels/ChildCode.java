package com.esc20.nonDBModels;

import java.util.List;

public class ChildCode {
	private String id;
	private List<String> childAccountCodes;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<String> getChildAccountCodes() {
		return childAccountCodes;
	}

	public void setChildAccountCodes(List<String> childAccountCodes) {
		this.childAccountCodes = childAccountCodes;
	}

}
