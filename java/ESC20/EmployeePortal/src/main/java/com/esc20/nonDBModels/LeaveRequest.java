package com.esc20.nonDBModels;

import java.math.BigDecimal;
import java.util.Date;
import com.esc20.model.BeaEmpLvRqst;

public class LeaveRequest {

	private Integer id;
	private Character payFreq;
	private String empNbr;
	private Date datetimeSubmitted;
	private Date datetimeFrom;
	private Date datetimeTo;
	private BigDecimal lvUnitsDaily;
	private BigDecimal lvUnitsUsed;
	private String lvTyp;
	private String absRsn;
	private Character statusCd;
	private String dtOfPay;

	public LeaveRequest() {
	}

	public LeaveRequest(Integer id, Character payFreq, String empNbr, Date datetimeSubmitted, Date datetimeFrom, Date datetimeTo,
			BigDecimal lvUnitsDaily, BigDecimal lvUnitsUsed, String lvTyp, String absRsn, Character statusCd,
			String dtOfPay) {
		this.id = id;
		this.payFreq = payFreq;
		this.empNbr = empNbr;
		this.datetimeSubmitted = datetimeSubmitted;
		this.datetimeFrom = datetimeFrom;
		this.datetimeTo = datetimeTo;
		this.lvUnitsDaily = lvUnitsDaily;
		this.lvUnitsUsed = lvUnitsUsed;
		this.lvTyp = lvTyp;
		this.absRsn = absRsn;
		this.statusCd = statusCd;
		this.dtOfPay = dtOfPay;
	}
	
	public LeaveRequest (BeaEmpLvRqst rqst) {
		this.id = rqst.getId();
		this.payFreq = rqst.getPayFreq();
		this.empNbr = rqst.getEmpNbr();
		this.datetimeSubmitted = rqst.getDatetimeSubmitted();
		this.datetimeFrom = rqst.getDatetimeFrom();
		this.datetimeTo = rqst.getDatetimeTo();
		this.lvUnitsDaily = rqst.getLvUnitsDaily();
		this.lvUnitsUsed = rqst.getLvUnitsUsed();
		this.lvTyp = rqst.getLvTyp();
		this.absRsn = rqst.getAbsRsn();
		this.statusCd = rqst.getStatusCd();
		this.dtOfPay = rqst.getDtOfPay();
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Character getPayFreq() {
		return this.payFreq;
	}

	public void setPayFreq(Character payFreq) {
		this.payFreq = payFreq;
	}

	public String getEmpNbr() {
		return this.empNbr;
	}

	public void setEmpNbr(String empNbr) {
		this.empNbr = empNbr;
	}

	public Date getDatetimeSubmitted() {
		return this.datetimeSubmitted;
	}

	public void setDatetimeSubmitted(Date datetimeSubmitted) {
		this.datetimeSubmitted = datetimeSubmitted;
	}

	public Date getDatetimeFrom() {
		return this.datetimeFrom;
	}

	public void setDatetimeFrom(Date datetimeFrom) {
		this.datetimeFrom = datetimeFrom;
	}

	public Date getDatetimeTo() {
		return this.datetimeTo;
	}

	public void setDatetimeTo(Date datetimeTo) {
		this.datetimeTo = datetimeTo;
	}

	public BigDecimal getLvUnitsDaily() {
		return this.lvUnitsDaily;
	}

	public void setLvUnitsDaily(BigDecimal lvUnitsDaily) {
		this.lvUnitsDaily = lvUnitsDaily;
	}

	public BigDecimal getLvUnitsUsed() {
		return this.lvUnitsUsed;
	}

	public void setLvUnitsUsed(BigDecimal lvUnitsUsed) {
		this.lvUnitsUsed = lvUnitsUsed;
	}

	public String getLvTyp() {
		return this.lvTyp;
	}

	public void setLvTyp(String lvTyp) {
		this.lvTyp = lvTyp;
	}

	public String getAbsRsn() {
		return this.absRsn;
	}

	public void setAbsRsn(String absRsn) {
		this.absRsn = absRsn;
	}

	public Character getStatusCd() {
		return this.statusCd;
	}

	public void setStatusCd(Character statusCd) {
		this.statusCd = statusCd;
	}

	public String getDtOfPay() {
		return this.dtOfPay;
	}

	public void setDtOfPay(String dtOfPay) {
		this.dtOfPay = dtOfPay;
	}

}
