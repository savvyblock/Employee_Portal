package net.esc20.txeis.EmployeeAccess.domainobject;

import java.io.Serializable;
import java.sql.Timestamp;

public class LeaveTemporaryApprover implements Serializable {

	private static final long serialVersionUID = -596769258673262256L;

	private int id;
	boolean deleteIndicated=false;
	private LeaveEmployeeData temporaryApprover;
	private Timestamp fromDateTime;
	private String fromDateString;
	private Timestamp toDateTime;
	private String toDateString;
	private boolean modified=false;
	
	// ignoreRow indicates the user added a row to the table (and so doesn't correspond to a row in the table) but did not enter any values 
	// this value is set by the validator and checked by the controller when processing a Save
	private boolean ignoreRow=false;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean isDeleteIndicated() {
		return deleteIndicated;
	}
	public void setDeleteIndicated(boolean deleteIndicated) {
		this.deleteIndicated = deleteIndicated;
	}
	public LeaveEmployeeData getTemporaryApprover() {
		return temporaryApprover;
	}
	public void setTemporaryApprover(LeaveEmployeeData temporaryApprover) {
		this.temporaryApprover = temporaryApprover;
	}
	public Timestamp getFromDateTime() {
		return fromDateTime;
	}
	public void setFromDateTime(Timestamp fromDateTime) {
		this.fromDateTime = fromDateTime;
	}
	public String getFromDateString() {
		return fromDateString;
	}
	public void setFromDateString(String fromDateString) {
		this.fromDateString = fromDateString;
	}
	public Timestamp getToDateTime() {
		return toDateTime;
	}
	public void setToDateTime(Timestamp toDateTime) {
		this.toDateTime = toDateTime;
	}
	public String getToDateString() {
		return toDateString;
	}
	public void setToDateString(String toDateString) {
		this.toDateString = toDateString;
	}
	public boolean isModified() {
		return modified;
	}
	public void setModified(boolean modified) {
		this.modified = modified;
	}
	public boolean isIgnoreRow() {
		return ignoreRow;
	}
	public void setIgnoreRow(boolean ignoreRow) {
		this.ignoreRow = ignoreRow;
	}

}
