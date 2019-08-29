package com.esc20.nonDBModels;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.esc20.model.BhrEmpLvXmital;


public class LeaveBalance implements Serializable {
	private static final long serialVersionUID = 6715142900654758429L;
	private Character payFreq;
	private String empNbr;
	private Character cyrNyrFlg;
	private String dtOfPay;
	private Short lvSeq;
	private String lvTyp;
	private String absRsn;
	private String dtOfAbs;
	private String begTime;
	private BigDecimal lvUnitsUsed;
	private BigDecimal lvUnitsEarned;
	private String subst;
	private String rsn;
	private String processDt;
	private String userId;
	private String module;
	private Integer lvId;

	private static SimpleDateFormat sdf1 = new SimpleDateFormat("MM-dd-yyyy");
	private static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
	
	public LeaveBalance(BhrEmpLvXmital bhrEmpLvXmital) throws ParseException {
		this.payFreq = bhrEmpLvXmital.getId().getPayFreq();
		this.empNbr = bhrEmpLvXmital.getId().getEmpNbr();
		this.cyrNyrFlg = bhrEmpLvXmital.getId().getCyrNyrFlg();
		this.dtOfPay = sdf1.format(sdf2.parse(bhrEmpLvXmital.getId().getDtOfPay()));
		this.lvTyp = bhrEmpLvXmital.getLvTyp();
		this.absRsn = bhrEmpLvXmital.getAbsRsn();
		this.dtOfAbs = sdf1.format(sdf2.parse(bhrEmpLvXmital.getDtOfAbs()));
		this.begTime = bhrEmpLvXmital.getBegTime();
		this.lvUnitsUsed = bhrEmpLvXmital.getLvUnitsUsed();
		this.lvUnitsEarned = bhrEmpLvXmital.getLvUnitsEarned();
		this.subst = bhrEmpLvXmital.getSubst();
		this.rsn = bhrEmpLvXmital.getRsn();
		this.processDt = bhrEmpLvXmital.getProcessDt();
		this.userId = bhrEmpLvXmital.getUserId();
		this.module = bhrEmpLvXmital.getModule();
		this.lvId = bhrEmpLvXmital.getLvId();
	}

	public Character getPayFreq() {
		return payFreq;
	}

	public void setPayFreq(Character payFreq) {
		this.payFreq = payFreq;
	}

	public String getEmpNbr() {
		return empNbr;
	}

	public void setEmpNbr(String empNbr) {
		this.empNbr = empNbr;
	}

	public Character getCyrNyrFlg() {
		return cyrNyrFlg;
	}

	public void setCyrNyrFlg(Character cyrNyrFlg) {
		this.cyrNyrFlg = cyrNyrFlg;
	}

	public String getDtOfPay() {
		return dtOfPay;
	}

	public void setDtOfPay(String dtOfPay) {
		this.dtOfPay = dtOfPay;
	}

	public Short getLvSeq() {
		return lvSeq;
	}

	public void setLvSeq(Short lvSeq) {
		this.lvSeq = lvSeq;
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

	public String getDtOfAbs() {
		return this.dtOfAbs;
	}

	public void setDtOfAbs(String dtOfAbs) {
		this.dtOfAbs = dtOfAbs;
	}

	public String getBegTime() {
		return this.begTime;
	}

	public void setBegTime(String begTime) {
		this.begTime = begTime;
	}


	public BigDecimal getLvUnitsUsed() {
		return this.lvUnitsUsed;
	}

	public void setLvUnitsUsed(BigDecimal lvUnitsUsed) {
		this.lvUnitsUsed = lvUnitsUsed;
	}


	public BigDecimal getLvUnitsEarned() {
		return this.lvUnitsEarned;
	}

	public void setLvUnitsEarned(BigDecimal lvUnitsEarned) {
		this.lvUnitsEarned = lvUnitsEarned;
	}


	public String getSubst() {
		return this.subst;
	}

	public void setSubst(String subst) {
		this.subst = subst;
	}


	public String getRsn() {
		return this.rsn;
	}

	public void setRsn(String rsn) {
		this.rsn = rsn;
	}


	public String getProcessDt() {
		return this.processDt;
	}

	public void setProcessDt(String processDt) {
		this.processDt = processDt;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getModule() {
		return this.module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public Integer getLvId() {
		return this.lvId;
	}

	public void setLvId(Integer lvId) {
		this.lvId = lvId;
	}

}
