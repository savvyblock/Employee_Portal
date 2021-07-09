package com.esc20.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class WorkjournalId implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2153276786654292970L;

	private String empNbr;
	private String wrkDate;
	private String  jobCode;
	
	public WorkjournalId() {
	}

	public WorkjournalId(String empNbr, String wrkDate, String jobCode) {
		super();
		this.empNbr = empNbr;
		this.wrkDate = wrkDate;
		this.jobCode = jobCode;
	}

	@Column(name = "EMP_NBR", nullable = false, length = 6, columnDefinition = "long default 0")
	public String getEmpNbr() {
		return empNbr;
	}

	public void setEmpNbr(String empNbr) {
		this.empNbr = empNbr;
	}

	@Column(name = "WRK_DATE", nullable = false, length = 12, columnDefinition = "long default 0")
	public String getWrkDate() {
		return wrkDate;
	}

	public void setWrkDate(String wrkDate) {
		this.wrkDate = wrkDate;
	}

	@Column(name = "JOB_CODE", nullable = false, length = 4, columnDefinition = "long default 0")
	public String getJobCode() {
		return jobCode;
	}

	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
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
		WorkjournalId other = (WorkjournalId) obj;
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
		if (wrkDate == null) {
			if (other.wrkDate != null)
				return false;
		} else if (!wrkDate.equals(other.wrkDate))
			return false;
		return true;
	}
	
	
}
