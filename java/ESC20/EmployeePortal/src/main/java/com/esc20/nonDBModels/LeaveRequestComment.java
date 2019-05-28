package com.esc20.nonDBModels;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.esc20.util.DateUtil;

import net.sf.json.JSONObject;

public class LeaveRequestComment implements Serializable{

	private static final long serialVersionUID = -4452126278573279987L;

	public static final String REQUEST_COMMENT_TYPE_REQUEST_CURRENT_WF = "C";
	public static final String REQUEST_COMMENT_TYPE_REQUEST_PRIOR_WF = "P";
	public static final String REQUEST_COMMENT_TYPE_APPROVAL = "A";
	public static final String REQUEST_COMMENT_TYPE_DISAPPROVAL = "D";


	private SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
	private SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mmaa");

	private int id;
	private int leaveId;
	private String comment;
	private String commentType;
	private Date commentDateTime;
	private String commentDateString;
	private String commentTimeString;
	private String commentEmpNumber;
	private String commentEmpFirstName;
	private String commentEmpMiddleName;
	private String commentEmpLastName;

	public LeaveRequestComment(Integer id, Integer leaveId, String comment, Date lvCommentDatetime,
			Character lvCommentTyp, String lvCommentEmpNbr, String nameF, String nameM, String nameL) {
		this.id = id;
		this.leaveId = leaveId;
		this.comment = comment;
		this.commentDateTime = DateUtil.getLocalTime(lvCommentDatetime);
		this.commentDateString = dateFormat.format(this.commentDateTime);
		this.commentTimeString = timeFormat.format(this.commentDateTime);
		this.commentType = lvCommentTyp.toString();
		this.commentEmpNumber = lvCommentEmpNbr;
		this.commentEmpFirstName = nameF;
		this.commentEmpMiddleName = nameM;
		this.commentEmpLastName = nameL;
	}

	public int getId() {
		return id;
	}

	public String getCommentEmpNumber() {
		return commentEmpNumber;
	}

	public void setCommentEmpNumber(String commentEmpNumber) {
		this.commentEmpNumber = commentEmpNumber;
	}

	public String getCommentEmpFirstName() {
		return commentEmpFirstName;
	}

	public void setCommentEmpFirstName(String commentEmpFirstName) {
		this.commentEmpFirstName = commentEmpFirstName;
	}

	public String getCommentEmpMiddleName() {
		return commentEmpMiddleName;
	}

	public void setCommentEmpMiddleName(String commentEmpMiddleName) {
		this.commentEmpMiddleName = commentEmpMiddleName;
	}

	public String getCommentEmpLastName() {
		return commentEmpLastName;
	}

	public void setCommentEmpLastName(String commentEmpLastName) {
		this.commentEmpLastName = commentEmpLastName;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getLeaveId() {
		return leaveId;
	}

	public void setLeaveId(int leaveId) {
		this.leaveId = leaveId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCommentType() {
		return commentType;
	}

	public void setCommentType(String commentType) {
		this.commentType = commentType;
	}

	public Date getCommentDateTime() {
		return commentDateTime;
	}

	public void setCommentDateTime(Date commentDateTime) {
		this.commentDateTime = commentDateTime;
	}

	public String getCommentDateString() {
		return commentDateString;
	}

	public void setCommentDateString(String commentDateString) {
		this.commentDateString = commentDateString;
	}

	public String getCommentTimeString() {
		return commentTimeString;
	}

	public void setCommentTimeString(String commentTimeString) {
		this.commentTimeString = commentTimeString;
	}

	public JSONObject toJSON() {
		JSONObject jo = new JSONObject();
		jo.put("id", this.getId());
		jo.put("leaveId", this.getLeaveId());
		jo.put("comment", this.getComment());
		jo.put("commentType", this.getCommentType());
		jo.put("commentDateTime", this.getCommentDateTime());
		jo.put("commentDateString", this.getCommentDateString());
		jo.put("commentTimeString", this.getCommentTimeString());
		jo.put("commentEmpNumber", this.getCommentEmpNumber());
		jo.put("commentEmpFirstName", this.getCommentEmpFirstName());
		jo.put("commentEmpMiddleName", this.getCommentEmpMiddleName());
		jo.put("commentEmpLastName", this.getCommentEmpLastName());
		return jo;
	}

	public JSONObject formatComment() {
		JSONObject jo = new JSONObject();
		String detail = "";
		boolean middleNameExists = this.getCommentEmpMiddleName() == null || this.getCommentEmpMiddleName().equals("");
		String name = this.getCommentEmpNumber() + "-" + this.getCommentEmpFirstName()
				+ (middleNameExists ? "" : (" " + this.getCommentEmpMiddleName())) + this.getCommentEmpLastName();
		detail += name;
		detail += " commented on ";
		String date = this.getCommentDateString() + " " + this.getCommentTimeString();
		detail += date;
		detail += ": " + this.getComment();
		detail = detail.replaceAll("'", "\\" + "'");
		jo.put("detail", detail);
		return jo;
	}
}
