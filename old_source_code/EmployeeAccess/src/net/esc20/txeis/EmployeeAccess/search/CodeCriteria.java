package net.esc20.txeis.EmployeeAccess.search;

import java.io.Serializable;

public class CodeCriteria implements Serializable
{
	private static final long serialVersionUID = -9022957219187325657L;
	
	private String searchCode;
	private String searchDescription;
	
	public String getSearchCode() {
		return searchCode;
	}
	public void setSearchCode(String searchCode) {
		this.searchCode = searchCode;
	}
	public String getSearchDescription() {
		return searchDescription;
	}
	public void setSearchDescription(String searchDescription) {
		this.searchDescription = searchDescription;
	}
}