package net.esc20.txeis.EmployeeAccess.domainobject;

import java.io.Serializable;

public class CalendarDetail implements Serializable {

	private static final long serialVersionUID = -9205515835748284593L;
	
	String fromTime = "";
	String toTime = "";
	String units = "";
	String leaveCd = "";
	String descr = "";
	String reason = "";
	String status = "";
	String approver = "";
	String comments = "";
	String appComments = "";
	
	public CalendarDetail() {
		super();
	}

	public CalendarDetail(String ft, String tt, String u, String l, String d, String r, String s, String a, String c, String ac){
		this.fromTime = ft;
		this.toTime = tt;
		this.units = u;
		this.leaveCd = l;
		this.descr = d;
		this.reason = r;
		this.status = s;
		this.approver = a;
		this.comments = c;
		this.appComments = ac;
	}
	
	public String getFromTime() {
		return fromTime;
	}
	public void setFromTime(String fromTime) {
		this.fromTime = fromTime;
	}
	public String getToTime() {
		return toTime;
	}
	public void setToTime(String toTime) {
		this.toTime = toTime;
	}
	public String getUnits() {
		return units;
	}
	public void setUnits(String units) {
		this.units = units;
	}
	public String getLeaveCd() {
		return leaveCd;
	}
	public void setLeaveCd(String leaveCd) {
		this.leaveCd = leaveCd;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getApprover() {
		return approver;
	}
	public void setApprover(String approver) {
		this.approver = approver;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getAppComments() {
		return appComments;
	}
	public void setAppComments(String appComments) {
		this.appComments = appComments;
	}

}
