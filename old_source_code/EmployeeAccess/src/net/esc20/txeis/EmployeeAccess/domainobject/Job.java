package net.esc20.txeis.EmployeeAccess.domainobject;

import java.io.Serializable;
import java.math.BigDecimal;

import net.esc20.txeis.EmployeeAccess.domainobject.reference.Frequency;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.ICode;

public class Job implements Serializable
{
	private static final long serialVersionUID = -6119482235176735443L;
	
	private Frequency frequency;
	private ICode title;
	private Integer annualPayments;
	private BigDecimal regularHours;   //jf20120724 fix decimal truncation
	private Integer remainPayments;
	private BigDecimal annualSalary;
	private BigDecimal dailyRate;
	private BigDecimal hourlyRate;
	private BigDecimal overtimeRate;
	private String primaryJob;   //jf20140110 earnings report
	private String campusID;   //jf20140110 earnings report
	private String campusName;   //jf20140110 earnings report
	
	public Frequency getFrequency() {
		return frequency;
	}
	public void setFrequency(Frequency frequency) {
		this.frequency = frequency;
	}
	public ICode getTitle() {
		return title;
	}
	public void setTitle(ICode title) {
		this.title = title;
	}
	public Integer getAnnualPayments() {
		return annualPayments;
	}
	public void setAnnualPayments(Integer annualPayments) {
		this.annualPayments = annualPayments;
	}
	public BigDecimal getRegularHours() {   //jf20120724 fix decimal truncation
		return regularHours;
	}
	public void setRegularHours(BigDecimal regularHours) {   //jf20120724 fix decimal truncation
		this.regularHours = regularHours;
	}
	public Integer getRemainPayments() {
		return remainPayments;
	}
	public void setRemainPayments(Integer remainPayments) {
		this.remainPayments = remainPayments;
	}
	public BigDecimal getAnnualSalary() {
		return annualSalary;
	}
	public void setAnnualSalary(BigDecimal annualSalary) {
		this.annualSalary = annualSalary;
	}
	public BigDecimal getDailyRate() {
		return dailyRate;
	}
	public void setDailyRate(BigDecimal dailyRate) {
		this.dailyRate = dailyRate;
	}
	public BigDecimal getHourlyRate() {
		return hourlyRate;
	}
	public void setHourlyRate(BigDecimal hourlyRate) {
		this.hourlyRate = hourlyRate;
	}
	public BigDecimal getOvertimeRate() {
		return overtimeRate;
	}
	public void setOvertimeRate(BigDecimal overtimeRate) {
		this.overtimeRate = overtimeRate;
	}
	public String getPrimaryJob() {
		return primaryJob;
	}
	public void setPrimaryJob(String primaryJob) {
		this.primaryJob = primaryJob;
	}
	public String getCampusID() {
		return campusID;
	}
	public void setCampusID(String campusID) {
		this.campusID = campusID;
	}
	public String getCampusName() {
		return campusName;
	}
	public void setCampusName(String campusName) {
		this.campusName = campusName;
	}
}