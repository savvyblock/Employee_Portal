package com.esc20.nonDBModels;

import java.io.Serializable;

public class W4Info implements Serializable
{
	private static final long serialVersionUID = -4293607069850390059L;
	
	private String w4FileStat;
	private String w4MultiJob;
	private Double w4NbrChldrn ;
	private Double w4NbrOthrDep ;
	private Double w4OthrIncAmt ;
	private Double w4OthrDedAmt ;
	private Double w4OthrExmptAmt ;
	public String getW4FileStat() {
		return w4FileStat;
	}
	public void setW4FileStat(String w4FileStat) {
		this.w4FileStat = w4FileStat;
	}
	public String getW4MultiJob() {
		return w4MultiJob;
	}
	public void setW4MultiJob(String w4MultiJob) {
		this.w4MultiJob = w4MultiJob;
	}
	public Double getW4NbrChldrn() {
		return w4NbrChldrn;
	}
	public void setW4NbrChldrn(Double w4NbrChldrn) {
		this.w4NbrChldrn = w4NbrChldrn;
	}
	public Double getW4NbrOthrDep() {
		return w4NbrOthrDep;
	}
	public void setW4NbrOthrDep(Double w4NbrOthrDep) {
		this.w4NbrOthrDep = w4NbrOthrDep;
	}
	public Double getW4OthrIncAmt() {
		return w4OthrIncAmt;
	}
	public void setW4OthrIncAmt(Double w4OthrIncAmt) {
		this.w4OthrIncAmt = w4OthrIncAmt;
	}
	public Double getW4OthrDedAmt() {
		return w4OthrDedAmt;
	}
	public void setW4OthrDedAmt(Double w4OthrDedAmt) {
		this.w4OthrDedAmt = w4OthrDedAmt;
	}
	public Double getW4OthrExmptAmt() {
		return w4OthrExmptAmt;
	}
	public void setW4OthrExmptAmt(Double w4OthrExmptAmt) {
		this.w4OthrExmptAmt = w4OthrExmptAmt;
	}
	
}