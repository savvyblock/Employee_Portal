package com.esc20.model;


import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.esc20.nonDBModels.BeaEmpWrkJrnlId;

@Entity
@Table(name = "BEA_EMP_WRK_JRNL", schema = "rsccc", catalog = "rsccc")
public class BeaEmpWrkJrnl implements java.io.Serializable{

	 /**
	 * 
	 */
	private static final long serialVersionUID = -4518103610065316338L;
	
    private BeaEmpWrkJrnlId id;
//	 private String wrkDate;
//	 private String  jobCode;
//	 private String  payFreq;
	 private String fromTmHr;
	 private String fromTmMin;
	 private String toTmHr;
	 private String toTmMin;
	 private String commnt;
	 private String toTmAp;
	 private String fromTmAp;
//	 private String empNbr;
//	 private String seqNbr;
	 private String wrkJrnlStat;
	 private String begWrkWkDt;
	 private String totTm;
	 

	 
	 
	public BeaEmpWrkJrnl() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BeaEmpWrkJrnl(String wrkDate, String jobCode, String fromTmHr, String fromTmMin, String toTmHr,
			String toTmMin, String commnt, String toTmAp, String fromTmAp, String empNbr,BeaEmpWrkJrnlId id) {
		super();
		this.fromTmHr = fromTmHr;
		this.fromTmMin = fromTmMin;
		this.toTmHr = toTmHr;
		this.toTmMin = toTmMin;
		this.commnt = commnt;
		this.toTmAp = toTmAp;
		this.fromTmAp = fromTmAp;
		this.id = id;
	}
	
	@EmbeddedId

	@AttributeOverrides({
		@AttributeOverride(name = "wrkDate", column = @Column(name = "WRK_DATE", nullable = false, length = 9, columnDefinition = "long default 0")),
		@AttributeOverride(name = "empNbr", column = @Column(name = "EMP_NBR", nullable = false, length = 9, columnDefinition = "long default 0")),
		@AttributeOverride(name = "seqNbr", column = @Column(name = "SEQ_NBR", nullable = false, length = 9, columnDefinition = "long default 0")),
		@AttributeOverride(name = "payFreq", column = @Column(name = "PAY_FREQ", nullable = false, length = 9, columnDefinition = "long default 0")),
		@AttributeOverride(name = "jobCode", column = @Column(name = "JOB_CD", nullable = false, length = 2, columnDefinition = "long default 0 ")) })
		public BeaEmpWrkJrnlId getId() {
			return id;
		}
		
		public void setId(BeaEmpWrkJrnlId id) {
			this.id = id;
		}
	
//	@Column(name = "WRK_DATE", nullable = false, length = 1)
//	public String getWrkDate() {
//		return wrkDate;
//	}
//	public void setWrkDate(String date) {
//		this.wrkDate = date;
//	}
	
//	@Column(name = "JOB_CD", nullable = false, length = 1)
//	public String getJobCode() {
//		return jobCode;
//	}
//	
//	public void setJobCode(String jobCode) {
//		this.jobCode = jobCode;
//	}
	
	@Column(name = "FROM_TM_HR", nullable = false, length = 1)
	public String getStartHour() {
		return fromTmHr;
	}
	public void setStartHour(String fromTmHr) {
		this.fromTmHr = fromTmHr;
	}
	
	@Column(name = "FROM_TM_MIN", nullable = false, length = 1)
	public String getStartMinute() {
		return fromTmMin;
	}
	public void setStartMinute(String fromTmMin) {
		this.fromTmMin = fromTmMin;
	}
	
	@Column(name = "TO_TM_HR", nullable = false, length = 1)
	public String getEndHour() {
		return toTmHr;
	}
	public void setEndHour(String toTmHr) {
		this.toTmHr = toTmHr;
	}
	
	@Column(name = "TO_TM_MIN", nullable = false, length = 1)
	public String getEndMinute() {
		return toTmMin;
	}
	public void setEndMinute(String toTmMin) {
		this.toTmMin = toTmMin;
	}
	
	@Column(name = "COMMNT", nullable = false, length = 1)
	public String getComment() {
		return commnt;
	}
	
	public void setComment(String commnt) {
		this.commnt = commnt;
	}
	
	@Column(name = "TO_TM_AP", nullable = false, length = 1)
	public String getEndAmOrPm() {
		return toTmAp;
	}
	public void setEndAmOrPm(String toTmAp) {
		this.toTmAp = toTmAp;
	}
	
	@Column(name = "FROM_TM_AP", nullable = false, length = 1)
	public String getStartAmOrPm() {
		return fromTmAp;
	}
	public void setStartAmOrPm(String fromTmAp) {
		this.fromTmAp = fromTmAp;
	}
	
//	@Column(name = "EMP_NBR", nullable = false, length = 1)
//	public String getEmpNbr() {
//		return empNbr;
//	}
//	public void setEmpNbr(String empNbr) {
//		this.empNbr = empNbr;
//	}
//	
//	@Column(name = "PAY_FREQ", nullable = false, length = 1)
//	public String getPayFreq() {
//		return payFreq;
//	}
//	public void setPayFreq(String payFreq) {
//		this.payFreq = payFreq;
//	}
//	
//	@Column(name = "SEQ_NBR", nullable = false, length = 1)
//	public String getSeqNbr() {
//		return seqNbr;
//	}
//	public void setSeqNbr(String seqNbr) {
//		this.seqNbr = seqNbr;
//	}
	
	@Column(name = "WRK_JRNL_STAT", nullable = false, length = 1)
	public String getWrkJrnlStat() {
		return wrkJrnlStat;
	}
	public void setWrkJrnlStat(String wrkJrnlStat) {
		this.wrkJrnlStat = wrkJrnlStat;
	}
	
	@Column(name = "BEG_WRK_WK_DT", nullable = false, length = 1)
	public String getBegWrkWkDt() {
		return begWrkWkDt;
	}
	public void setBegWrkWkDt(String begWrkWkDt) {
		this.begWrkWkDt = begWrkWkDt;
	}
	
	@Column(name = "TOT_TM", nullable = false, length = 1)
	public String getTotTm() {
		return totTm;
	}
	public void setTotTm(String i) {
		this.totTm = i;
	}
	 
}
