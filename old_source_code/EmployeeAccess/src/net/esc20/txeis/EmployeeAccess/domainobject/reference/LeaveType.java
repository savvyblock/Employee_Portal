package net.esc20.txeis.EmployeeAccess.domainobject.reference;

import java.io.Serializable;
import java.util.Date;

public class LeaveType implements Serializable {

	private static final long serialVersionUID = 3905731390465323727L;

	String lvType;
	String lvTypeDescription;
	String lvTypeUnits;  // 'D' days or 'H' hours
	boolean subtractFromBalance;
	boolean commentRequired;
	boolean postAgainstZeroBalance;
	Date absenceDateCutoff=null;	// for this leave type, leave request date (date of absence) cannot be after this date
	Date submissionDateCutoff=null;	// for this leave type, the leave request cannot be submitted for approval after this date
	
	public String getLvType() {
		return lvType;
	}
	public void setLvType(String lvType) {
		this.lvType = lvType;
	}
	public String getLvTypeDescription() {
		return lvTypeDescription;
	}
	public void setLvTypeDescription(String lvTypeDescription) {
		this.lvTypeDescription = lvTypeDescription;
	}
	public String getLvTypeUnits() {
		return lvTypeUnits;
	}
	public void setLvTypeUnits(String lvTypeUnits) {
		this.lvTypeUnits = lvTypeUnits;
	}
	public boolean isSubtractFromBalance() {
		return subtractFromBalance;
	}
	public void setSubtractFromBalance(boolean subtractFromBalance) {
		this.subtractFromBalance = subtractFromBalance;
	}
	public boolean isCommentRequired() {
		return commentRequired;
	}
	public void setCommentRequired(boolean commentRequired) {
		this.commentRequired = commentRequired;
	}
	public boolean isPostAgainstZeroBalance() {
		return postAgainstZeroBalance;
	}
	public void setPostAgainstZeroBalance(boolean postAgainstZeroBalance) {
		this.postAgainstZeroBalance = postAgainstZeroBalance;
	}
	public Date getAbsenceDateCutoff() {
		return absenceDateCutoff;
	}
	public void setAbsenceDateCutoff(Date absenceDateCutoff) {
		this.absenceDateCutoff = absenceDateCutoff;
	}
	public Date getSubmissionDateCutoff() {
		return submissionDateCutoff;
	}
	public void setSubmissionDateCutoff(Date submissionDateCutoff) {
		this.submissionDateCutoff = submissionDateCutoff;
	}
}
