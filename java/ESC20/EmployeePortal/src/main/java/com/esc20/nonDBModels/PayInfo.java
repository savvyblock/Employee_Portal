package com.esc20.nonDBModels;

import java.io.Serializable;


public class PayInfo implements Serializable {
	
	private static final long serialVersionUID = 6715142900654758429L;

	private Character maritalStatTax;
	private Integer nbrTaxExempts;
	private String w4FileStat;
	private String w4MultiJob;
	private Integer w4NbrChldrn ;
	private Integer w4NbrOthrDep ;
	private Double w4OthrIncAmt ;
	private Double w4OthrDedAmt ;
	private Double w4OthrExmptAmt ;


	
	public PayInfo(Object maritalStatTax, Object nbrTaxExempts, Object w4FileStat,
			Object w4MultiJob, Object w4NbrChldrn, Object w4NbrOthrDep, Object w4OthrIncAmt,
			Object w4OthrDedAmt, Object w4OthrExmptAmt ) {  
		this.maritalStatTax = (Character) maritalStatTax;
		this.nbrTaxExempts = (Integer) nbrTaxExempts;
		this.w4FileStat = (String) w4FileStat;
		this.w4MultiJob = (String) w4MultiJob;
		this.w4NbrChldrn = (Integer) w4NbrChldrn;
		this.w4NbrOthrDep = (Integer) w4NbrOthrDep;
		this.w4OthrIncAmt = (Double) w4OthrIncAmt;
		this.w4OthrDedAmt = (Double) w4OthrDedAmt;
		this.w4OthrExmptAmt = (Double) w4OthrExmptAmt;
	}
	
	public Character getMaritalStatTax() {
		return maritalStatTax;
	}
	public void setMaritalStatTax(Character maritalStatTax) {
		this.maritalStatTax = maritalStatTax;
	}
	public Integer getNbrTaxExempts() {
		return nbrTaxExempts;
	}
	public void setNbrTaxExempts(Integer nbrTaxExempts) {
		this.nbrTaxExempts = nbrTaxExempts;
	}

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

	public Integer getW4NbrChldrn() {
		return w4NbrChldrn;
	}

	public void setW4NbrChldrn(Integer w4NbrChldrn) {
		this.w4NbrChldrn = w4NbrChldrn;
	}

	public Integer getW4NbrOthrDep() {
		return w4NbrOthrDep;
	}

	public void setW4NbrOthrDep(Integer w4NbrOthrDep) {
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
