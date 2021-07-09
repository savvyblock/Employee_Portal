package com.esc20.nonDBModels;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class BeaEmpWrkJrnlId  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4469252347321162361L;
	private String empNbr;
	private String wrkDate;
	private String  jobCode;
	private String  payFreq;
	private String seqNbr = "0";
	
	
	
	public BeaEmpWrkJrnlId() {
	}
	public BeaEmpWrkJrnlId(String empNbr, String wrkDate, String jobCode, String payFreq, String seqNbr) {
		super();
		this.empNbr = empNbr;
		this.wrkDate = wrkDate;
		this.jobCode = jobCode;
		this.payFreq = payFreq;
		this.seqNbr = seqNbr;
	}
	public String getEmpNbr() {
		return empNbr;
	}
	public void setEmpNbr(String empNbr) {
		this.empNbr = empNbr;
	}
	public String getWrkDate() {
		return wrkDate;
	}
	public void setWrkDate(String wrkDate) {
		this.wrkDate = wrkDate;
	}
	public String getJobCode() {
		return jobCode;
	}
	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
	}
	public String getPayFreq() {
		return payFreq;
	}
	public void setPayFreq(String payFreq) {
		this.payFreq = payFreq;
	}
	public String getSeqNbr() {
		return seqNbr;
	}
	public void setSeqNbr(String seqNbr) {
		this.seqNbr = seqNbr;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((empNbr == null) ? 0 : empNbr.hashCode());
		result = prime * result + ((jobCode == null) ? 0 : jobCode.hashCode());
		result = prime * result + ((payFreq == null) ? 0 : payFreq.hashCode());
		result = prime * result + ((seqNbr == null) ? 0 : seqNbr.hashCode());
		result = prime * result + ((wrkDate == null) ? 0 : wrkDate.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BeaEmpWrkJrnlId other = (BeaEmpWrkJrnlId) obj;
		if (empNbr == null) {
			if (other.empNbr != null)
				return false;
		} else if (!empNbr.equals(other.empNbr))
			return false;
		if (jobCode == null) {
			if (other.jobCode != null)
				return false;
		} else if (!jobCode.equals(other.jobCode))
			return false;
		if (payFreq == null) {
			if (other.payFreq != null)
				return false;
		} else if (!payFreq.equals(other.payFreq))
			return false;
		if (seqNbr == null) {
			if (other.seqNbr != null)
				return false;
		} else if (!seqNbr.equals(other.seqNbr))
			return false;
		if (wrkDate == null) {
			if (other.wrkDate != null)
				return false;
		} else if (!wrkDate.equals(other.wrkDate))
			return false;
		return true;
	}



}
