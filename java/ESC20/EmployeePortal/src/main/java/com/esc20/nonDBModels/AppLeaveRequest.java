package com.esc20.nonDBModels;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.esc20.model.BeaEmpLvRqst;
import com.esc20.model.BhrEmpDemo;
import com.esc20.model.BhrEmpLvXmital;
import com.esc20.util.DateUtil;

public class AppLeaveRequest implements Serializable {


	private Integer id;
	private Integer seqNum;
	private Character payFreq;
	private String empNbr;
	private String lvTyp;
	private String absRsn;
	private Date datetimeSubmitted;
	private Date datetimeFrom;
	private Date datetimeTo;
	private BigDecimal lvUnitsDaily;
	private BigDecimal lvUnitsUsed;
	private Character statusCd;
	private String descr;
	private String lvComment;
	private String daysHrs;
	private String longDescr;
	private String absDescr;
	private String firstName;
	private String middleName;
	private String lastName;
	private String createComment;
	private List<LeaveRequestComment> comments;
	private List<LeaveInfo> info;
	private String approver;
	
	public AppLeaveRequest() {
	}

	public AppLeaveRequest(int id) {
		this.id = id;
	}

	public AppLeaveRequest(Integer id, String lvTyp, String absRsn, Date datetimeSubmitted, Date datetimeFrom, Date datetimeTo,
			BigDecimal lvUnitsDaily, BigDecimal lvUnitsUsed, Character statusCd, String descr, String lvComment,
			String daysHrs, String longDescr, String absDescr) {
			this.id = id;
			this.lvTyp= lvTyp;
			this.absRsn = absRsn;
			this.datetimeSubmitted = DateUtil.getLocalTime(datetimeSubmitted);
			this.datetimeFrom = DateUtil.getLocalTime(datetimeFrom);
			this.datetimeTo = DateUtil.getLocalTime(datetimeTo);
			this.lvUnitsDaily = lvUnitsDaily;
			this.lvUnitsUsed = lvUnitsUsed;
			this.statusCd = statusCd;
			this.descr = descr;
			this.lvComment = lvComment;
			this.daysHrs = daysHrs;
			this.longDescr = longDescr;
			this.absDescr = absDescr;
	}

	public AppLeaveRequest(Integer seqNum, Integer id, Character payFreq, String empNbr, String lvTyp,
			String absRsn, Date datetimeSubmitted, Date datetimeFrom, Date datetimeTo, BigDecimal lvUnitsDaily, BigDecimal lvUnitsUsed,
			Character statusCd, String descr, String daysHrs, String longDescr, String absDescr, String firstName,
			String middleName, String lastName) {
		this.seqNum = seqNum;
		this.id = id;
		this.payFreq = payFreq;
		this.empNbr = empNbr;
		this.lvTyp= lvTyp;
		this.absRsn = absRsn;
		this.datetimeSubmitted = DateUtil.getLocalTime(datetimeSubmitted);
		this.datetimeFrom = DateUtil.getLocalTime(datetimeFrom);
		this.datetimeTo = DateUtil.getLocalTime(datetimeTo);
		this.lvUnitsDaily = lvUnitsDaily;
		this.lvUnitsUsed = lvUnitsUsed;
		this.statusCd = statusCd;
		this.descr = descr;
		this.daysHrs = daysHrs;
		this.longDescr = longDescr;
		this.absDescr = absDescr;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
	}

	public AppLeaveRequest(BhrEmpLvXmital item,BhrEmpDemo demo) throws ParseException {
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
		this.payFreq = item.getId().getPayFreq();
		this.empNbr = demo.getEmpNbr();
		this.lvTyp = item.getLvTyp();
		this.absRsn = item.getAbsRsn();
		this.datetimeFrom = DateUtil.getLocalTime(sdf2.parse(item.getDtOfAbs()));
		this.datetimeTo = DateUtil.getLocalTime(sdf2.parse(item.getDtOfAbs()));
		this.lvUnitsUsed = item.getLvUnitsUsed();
		this.daysHrs = "D";
		this.firstName = demo.getNameF();
		this.middleName = demo.getNameM();
		this.lastName = demo.getNameL();
		this.longDescr = (item.getProcessDt()==null||("").equals(item.getProcessDt()))?"Not Processed":"Processed";
		this.statusCd = (item.getProcessDt()==null||("").equals(item.getProcessDt()))?'N':'C';
	}

	public AppLeaveRequest(BeaEmpLvRqst item, BhrEmpDemo demo) {
		this.id = item.getId();
		this.payFreq = item.getPayFreq();
		this.empNbr = demo.getEmpNbr();
		this.lvTyp = item.getLvTyp();
		this.absRsn = item.getAbsRsn();
		this.datetimeFrom = DateUtil.getLocalTime(item.getDatetimeFrom());
		this.datetimeTo = DateUtil.getLocalTime(item.getDatetimeTo());
		this.lvUnitsDaily = item.getLvUnitsDaily();
		this.lvUnitsUsed = item.getLvUnitsUsed();
		this.statusCd = item.getStatusCd();
		this.firstName = demo.getNameF();
		this.middleName = demo.getNameM();
		this.lastName = demo.getNameL();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLvTyp() {
		return lvTyp;
	}

	public void setLvTyp(String lvTyp) {
		this.lvTyp = lvTyp;
	}

	public String getAbsRsn() {
		return absRsn;
	}

	public void setAbsRsn(String absRsn) {
		this.absRsn = absRsn;
	}

	public Date getDatetimeSubmitted() {
		return datetimeSubmitted;
	}

	public void setDatetimeSubmitted(Date datetimeSubmitted) {
		this.datetimeSubmitted = datetimeSubmitted;
	}

	public Date getDatetimeFrom() {
		return datetimeFrom;
	}

	public void setDatetimeFrom(Date datetimeFrom) {
		this.datetimeFrom = datetimeFrom;
	}

	public Date getDatetimeTo() {
		return datetimeTo;
	}

	public void setDatetimeTo(Date datetimeTo) {
		this.datetimeTo = datetimeTo;
	}

	public BigDecimal getLvUnitsDaily() {
		return lvUnitsDaily;
	}

	public void setLvUnitsDaily(BigDecimal lvUnitsDaily) {
		this.lvUnitsDaily = lvUnitsDaily;
	}

	public BigDecimal getLvUnitsUsed() {
		return lvUnitsUsed;
	}

	public void setLvUnitsUsed(BigDecimal lvUnitsUsed) {
		this.lvUnitsUsed = lvUnitsUsed;
	}

	public Character getStatusCd() {
		return statusCd;
	}

	public void setStatusCd(Character statusCd) {
		this.statusCd = statusCd;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getLvComment() {
		return lvComment;
	}

	public void setLvComment(String lvComment) {
		this.lvComment = lvComment;
	}

	public String getDaysHrs() {
		return daysHrs;
	}

	public void setDaysHrs(String daysHrs) {
		this.daysHrs = daysHrs;
	}

	public String getLongDescr() {
		return longDescr;
	}

	public void setLongDescr(String longDescr) {
		this.longDescr = longDescr;
	}

	public String getAbsDescr() {
		return absDescr;
	}

	public void setAbsDescr(String absDescr) {
		this.absDescr = absDescr;
	}

	public List<LeaveRequestComment> getComments() {
		return comments;
	}

	public void setComments(List<LeaveRequestComment> comments) {
		this.comments = comments;
	}

	public Integer getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}

	public Character getPayFreq() {
		return payFreq;
	}

	public void setPayFreq(Character payFreq) {
		this.payFreq = payFreq;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCreateComment() {
		return createComment;
	}

	public void setCreateComment(String createComment) {
		this.createComment = createComment;
	}

	public String getEmpNbr() {
		return empNbr;
	}

	public void setEmpNbr(String empNbr) {
		this.empNbr = empNbr;
	}

	public List<LeaveInfo> getInfo() {
		return info;
	}

	public void setInfo(List<LeaveInfo> info) {
		this.info = info;
	}

	public String getApprover() {
		return approver;
	}

	public void setApprover(String approver) {
		this.approver = approver;
	}



}
