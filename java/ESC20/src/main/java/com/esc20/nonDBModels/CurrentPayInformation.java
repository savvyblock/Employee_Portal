package com.esc20.nonDBModels;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class CurrentPayInformation implements Serializable {
	
	private static final long serialVersionUID = 6715142900654758429L;
	
	
    private String jobCd;
    private String jobCdDescription;
    private short nbrAnnualPymts;
    private BigDecimal regHrsWrk;
    private short nbrRemainPymts;
    private BigDecimal contrAmt;  
    private BigDecimal dlyRateofPay;
    private BigDecimal payRate;
    private BigDecimal ovtmRate; 
    private Character primJob;
    private String campusId;
    private String campusName;
    private Frequency payFreq;
    
    public CurrentPayInformation(Object jobCd, Object jobCdDescription, Object nbrAnnualPymts, Object regHrsWrk, Object nbrRemainPymts, Object contrAmt, 
    		Object dlyRateofPay, Object payRate, Object ovtmRate, Object primJob, Object campusId, Object campusName, Object payFreq) {
		this.setJobCd((String) jobCd);
		this.setJobCdDescription((String) jobCdDescription);
		this.setNbrAnnualPymts((short) nbrAnnualPymts);
		this.setRegHrsWrk((BigDecimal) regHrsWrk);
		this.setNbrRemainPymts((short) nbrRemainPymts);
		this.setContrAmt((BigDecimal) contrAmt);
		this.setDlyRateofPay((BigDecimal) dlyRateofPay);
		this.setPayRate((BigDecimal) payRate);
		this.setOvtmRate((BigDecimal) ovtmRate);
		this.setPrimJob((Character) primJob);
		this.setCampusId((String) campusId);
		this.setCampusName((String) campusName);
		this.setPayFreq(Frequency.getFrequency((((Character) payFreq).toString()).trim()));
    }
    
	public String getJobCd() {
		return jobCd;
	}
	public void setJobCd(String jobCd) {
		this.jobCd = jobCd;
	}
	public short getNbrAnnualPymts() {
		return nbrAnnualPymts;
	}
	public void setNbrAnnualPymts(short nbrAnnualPymts) {
		this.nbrAnnualPymts = nbrAnnualPymts;
	}
	public BigDecimal getRegHrsWrk() {
		return regHrsWrk;
	}
	public void setRegHrsWrk(BigDecimal regHrsWrk) {
		this.regHrsWrk = regHrsWrk;
	}
	public short getNbrRemainPymts() {
		return nbrRemainPymts;
	}
	public void setNbrRemainPymts(short nbrRemainPymts) {
		this.nbrRemainPymts = nbrRemainPymts;
	}
	public BigDecimal getContrAmt() {
		return contrAmt;
	}
	public void setContrAmt(BigDecimal contrAmt) {
		this.contrAmt = contrAmt;
	}
	public BigDecimal getDlyRateofPay() {
		return dlyRateofPay;
	}
	public void setDlyRateofPay(BigDecimal dlyRateofPay) {
		this.dlyRateofPay = dlyRateofPay;
	}
	public BigDecimal getPayRate() {
		return payRate;
	}
	public void setPayRate(BigDecimal payRate) {
		this.payRate = payRate;
	}
	public BigDecimal getOvtmRate() {
		return ovtmRate;
	}
	public void setOvtmRate(BigDecimal ovtmRate) {
		this.ovtmRate = ovtmRate;
	}
	public Character getPrimJob() {
		return primJob;
	}
	public void setPrimJob(Character primJob) {
		this.primJob = primJob;
	}
	public String getCampusId() {
		return campusId;
	}
	public void setCampusId(String campusId) {
		this.campusId = campusId;
	}
	public String getCampusName() {
		return campusName;
	}
	public void setCampusName(String campusName) {
		this.campusName = campusName;
	}
	public Frequency getPayFreq() {
		return payFreq;
	}
	public void setPayFreq(Frequency payFreq) {
		this.payFreq = payFreq;
	}
	public String getJobCdDescription() {
		return jobCdDescription;
	}
	public void setJobCdDescription(String jobCdDescription) {
		this.jobCdDescription = jobCdDescription;
	}
}
