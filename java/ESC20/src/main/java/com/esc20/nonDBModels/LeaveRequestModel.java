package com.esc20.nonDBModels;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.util.CollectionUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class LeaveRequestModel implements Serializable {

	private static final long serialVersionUID = 6715142900654758429L;

	public LeaveRequestModel(AppLeaveRequest request) {
		this.id = request.getId();
		this.leaveType = request.getLvTyp();
		this.leaveStartDate = request.getDatetimeFrom();
		this.leaveEndDate = request.getDatetimeTo();
		this.leaveDuration = request.getLvUnitsUsed();
		this.status = request.getStatusCd().toString();
		this.remarks = request.getLvComment();
		this.absenseReason = request.getAbsRsn();
		this.lvUnitsDaily = request.getLvUnitsDaily();
		this.lvUnitsUsed = request.getLvUnitsUsed();
		this.comments = request.getComments();
		this.seqNum = request.getSeqNum();
		this.payFreq = request.getPayFreq();
		this.empNbr = request.getEmpNbr();
		this.descr = request.getDescr();
		this.daysHrs = request.getDaysHrs();
		this.longDescr = request.getLongDescr();
		this.absDescr = request.getAbsDescr();
		this.firstName = request.getFirstName();
		this.middleName = request.getMiddleName();
		this.lastName = request.getLastName();
		this.createComment = request.getCreateComment();
		this.info = request.getInfo();
		this.approver = request.getApprover();
	}

	private Integer id;
	private Integer seqNum;
	private Character payFreq;
	private String empNbr;
	private String leaveType;
	private Date leaveStartDate;
	private Date leaveEndDate;
	private BigDecimal leaveDuration;
	private BigDecimal lvUnitsDaily;
	private BigDecimal lvUnitsUsed;
	private String status;
	private String remarks;
	private String absenseReason;
	private String start;
	private String end;
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}

	public Date getLeaveStartDate() {
		return leaveStartDate;
	}

	public void setLeaveStartDate(Date leaveStartDate) {
		this.leaveStartDate = leaveStartDate;
	}

	public Date getLeaveEndDate() {
		return leaveEndDate;
	}

	public void setLeaveEndDate(Date leaveEndDate) {
		this.leaveEndDate = leaveEndDate;
	}

	public BigDecimal getLeaveDuration() {
		return leaveDuration;
	}

	public void setLeaveDuration(BigDecimal leaveDuration) {
		this.leaveDuration = leaveDuration;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getAbsenseReason() {
		return absenseReason;
	}

	public void setAbsenseReason(String absenseReason) {
		this.absenseReason = absenseReason;
	}

	public String getStart() {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		String result = sdf.format(this.leaveStartDate);
		this.start = result;
		return start;
	}

	public String getEnd() {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		String result = sdf.format(this.leaveEndDate);
		this.end = result;
		return end;
	}

	public JSONObject toJSON(List<Code> leaveStatus, List<Code> leaveTypes) {
		JSONObject jo = new JSONObject();
		jo.put("id", this.getId());
		String title = "Leave";
		if (!CollectionUtils.isEmpty(leaveTypes)) {
			for (Code type : leaveTypes) {
				if (type.getCode().equals(this.getLeaveType())) {
					title = type.getDescription();
				}
			}
		}
		jo.put("title", title);
		SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy");
		jo.put("LeaveType", this.getLeaveType());
		jo.put("AbsenseReason", this.getAbsenseReason());
		jo.put("LeaveStartDate", sdf1.format(this.getLeaveStartDate()));
		jo.put("LeaveEndDate", sdf1.format(this.getLeaveEndDate()));
		jo.put("Remarks", this.getRemarks());
		jo.put("lvUnitsDaily", this.getLvUnitsDaily());
		jo.put("lvUnitsUsed", this.getLvUnitsUsed());
		for (int i = 0; i < leaveStatus.size(); i++) {
			if (leaveStatus.get(i).getCode().equals(this.getStatus())) {
				jo.put("statusCd", this.getStatus());
				jo.put("statusDescr", leaveStatus.get(i).getDescription());
				break;
			}
		}
		jo.put("seqNum", this.getSeqNum());
		jo.put("payFreq", this.getPayFreq());
		jo.put("empNbr", this.getEmpNbr());
		jo.put("firstName", this.getFirstName());
		jo.put("middleName", this.getMiddleName());
		jo.put("lastName", this.getLastName());
		if (this.getComments() != null) {
			JSONArray comments = new JSONArray();
			JSONObject comment;
			for (int i = 0; i < this.getComments().size(); i++) {
				comment = this.getComments().get(i).formatComment();
				comments.add(comment);
			}
			jo.put("comments", comments);
		}
		if (this.getInfo() != null) {
			JSONArray infos = new JSONArray();
			JSONObject info;
			for (int i = 0; i < this.getInfo().size(); i++) {
				info = this.getInfo().get(i).toJSON();
				infos.add(info);
			}
			jo.put("infos", infos);
		}
		jo.put("approver", this.getApprover());
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm a", Locale.ENGLISH);
		String startDate = sdf.format(this.getLeaveStartDate());
		String endDate = sdf.format(this.getLeaveEndDate());
		jo.put("start", startDate);
		jo.put("end", endDate);
		return jo;
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

	public String getEmpNbr() {
		return empNbr;
	}

	public void setEmpNbr(String empNbr) {
		this.empNbr = empNbr;
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
