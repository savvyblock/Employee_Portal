package com.esc20.model;

import java.io.Serializable;

public class WorkflowApprovalPath implements Serializable{
	private static final long serialVersionUID = 8064401939051399373L;

	// Data Values
	private String reqNbr = "";

	//WorkFlow Path
	private String itemSeqNbr = "";
	private String sequence = "";
	private String apprvlUser = "";
	private String alternateUser = "";
	private String printName = "";
	private String status = "";
	private String date = "";
	
	private String bpoReqApprvlAltUserId = null;
	private String bpoReqApprvlStat = null;
	private Integer pathSeqNbr = null;
	private String bpoReqApprvlDt = null;
	private String bpoReqApprvlReqNbr = null;
	private String bpoReqApprvlUserId = null;
	private String title = "";
	private String prtName = "";
	
	public String getReqNbr() {
		return reqNbr;
	}

	public void setReqNbr(String reqNbr) {
		this.reqNbr = reqNbr;
	}

	public String getBpoReqApprvlAltUserId() {
		return bpoReqApprvlAltUserId;
	}

	public void setBpoReqApprvlAltUserId(String bpoReqApprvlAltUserId) {
		this.bpoReqApprvlAltUserId = bpoReqApprvlAltUserId;
	}

	public String getBpoReqApprvlStat() {
		return bpoReqApprvlStat;
	}

	public void setBpoReqApprvlStat(String bpoReqApprvlStat) {
		this.bpoReqApprvlStat = bpoReqApprvlStat;
	}

	public Integer getPathSeqNbr() {
		return pathSeqNbr;
	}

	public void setPathSeqNbr(Integer pathSeqNbr) {
		this.pathSeqNbr = pathSeqNbr;
	}

	public String getBpoReqApprvlDt() {
		return bpoReqApprvlDt;
	}

	public void setBpoReqApprvlDt(String bpoReqApprvlDt) {
		this.bpoReqApprvlDt = bpoReqApprvlDt;
	}

	public String getBpoReqApprvlReqNbr() {
		return bpoReqApprvlReqNbr;
	}

	public void setBpoReqApprvlReqNbr(String bpoReqApprvlReqNbr) {
		this.bpoReqApprvlReqNbr = bpoReqApprvlReqNbr;
	}

	public String getBpoReqApprvlUserId() {
		return bpoReqApprvlUserId;
	}

	public void setBpoReqApprvlUserId(String bpoReqApprvlUserId) {
		this.bpoReqApprvlUserId = bpoReqApprvlUserId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPrtName() {
		return prtName;
	}

	public void setPrtName(String prtName) {
		this.prtName = prtName;
	}

	public String getItemSeqNbr() {
		return itemSeqNbr;
	}

	public void setItemSeqNbr(String itemSeqNbr) {
		this.itemSeqNbr = itemSeqNbr;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public String getApprvlUser() {
		return apprvlUser;
	}

	public void setApprvlUser(String apprvlUser) {
		this.apprvlUser = apprvlUser;
	}

	public String getAlternateUser() {
		return alternateUser;
	}

	public void setAlternateUser(String alternateUser) {
		this.alternateUser = alternateUser;
	}

	public String getPrintName() {
		return printName;
	}

	public void setPrintName(String printName) {
		this.printName = printName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
}