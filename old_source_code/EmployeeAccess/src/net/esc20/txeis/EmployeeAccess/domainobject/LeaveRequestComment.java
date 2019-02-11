package net.esc20.txeis.EmployeeAccess.domainobject;

import java.io.Serializable;
import java.sql.Timestamp;

public class LeaveRequestComment implements Serializable {

	private static final long serialVersionUID = -4452126278573279987L;

	public static final String REQUEST_COMMENT_TYPE_REQUEST_CURRENT_WF = "C";
	public static final String REQUEST_COMMENT_TYPE_REQUEST_PRIOR_WF = "P";
	public static final String REQUEST_COMMENT_TYPE_APPROVAL = "A";
	public static final String REQUEST_COMMENT_TYPE_DISAPPROVAL = "D";

	private int id;
	private int leaveId;
	private String comment;
	private String commentType;
	private Timestamp commentDateTime;
	private String commentDateString;
	private String commentTimeString;
	private LeaveEmployeeData commentEmp;
//	private String commentEmpNumber;
//	private String commentEmpFirstName;
//	private String commentEmpMiddleName;
//	private String commentEmpLastName;
	
	public int getId() {
		return id;
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
	public Timestamp getCommentDateTime() {
		return commentDateTime;
	}
	public void setCommentDateTime(Timestamp commentDateTime) {
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
	public LeaveEmployeeData getCommentEmp() {
		return commentEmp;
	}
	public void setCommentEmp(LeaveEmployeeData commentEmp) {
		this.commentEmp = commentEmp;
	}

	
}
