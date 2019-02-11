package net.esc20.txeis.EmployeeAccess.domainobject;

import java.io.Serializable;
import java.math.BigDecimal;

public class Options implements Serializable
{
	private static final long serialVersionUID = -2737798069244440573L;
	
	public static enum IdType
	{
		EmployeeNumber,
		Ssn;
	}
	
	private IdType idType;
	
	private boolean enableEmployeeAccessSystem;
	private boolean enableEarnings;
	private boolean enableLeave;
	private boolean enableLeaveReq;
	private boolean enableW2;
	private boolean enableElecConsntW2;
	private boolean enableCalendarYearToDate;
	private boolean enableCurrentPayInformation;
	private boolean enableDeductions;
	private boolean enableSelfServiceDemographic;
	private boolean enableSelfServicePayroll;
	private boolean enable1095;
	private boolean enableElecConsnt1095;
	
	private boolean showProcessedLeave;
	private boolean showUnprocessedLeave;
	private boolean restrictEarnings;
	
	private Integer maxDays;
	private Integer w2Latest;
	private String url;
	private String preNote;
	private Integer ddAccounts;
	
	private String messageEmployeeAccessSystem;
	private String messageCalendarYearToDate;
	private String messageCurrentPayInformation;
	private String messageDeductions;
	private String messageEarnings;
	private String messageLeave;
	private String messageSelfServiceDemographic;
	private String messageSelfServicePayroll;
	private String messageW2;
	private String message1095;
	private String messageLeaveRequest;

	private boolean usePMISSpvsrLevels;  // necessary to determine if the logged in user has access to the Leave->Supervisor menu option
	
	public IdType getIdType() {
		return idType;
	}
	public void setIdType(IdType idType) {
		this.idType = idType;
	}
	public boolean isEnableEmployeeAccessSystem() {
		return enableEmployeeAccessSystem;
	}
	public void setEnableEmployeeAccessSystem(boolean enableEmployeeAccessSystem) {
		this.enableEmployeeAccessSystem = enableEmployeeAccessSystem;
	}
	public boolean isEnableEarnings() {
		return enableEarnings;
	}
	public void setEnableEarnings(boolean enableEarnings) {
		this.enableEarnings = enableEarnings;
	}
	public boolean isEnableLeave() {
		return enableLeave;
	}
	public void setEnableLeave(boolean enableLeave) {
		this.enableLeave = enableLeave;
	}
	public boolean isEnableW2() {
		return enableW2;
	}
	public void setEnableW2(boolean enableW2) {
		this.enableW2 = enableW2;
	}
	public boolean isEnableElecConsntW2() {
		return enableElecConsntW2;
	}
	public void setEnableElecConsntW2(boolean enableElecConsntW2) {
		this.enableElecConsntW2 = enableElecConsntW2;
	}
	public boolean isEnableCalendarYearToDate() {
		return enableCalendarYearToDate;
	}
	public void setEnableCalendarYearToDate(boolean enableCalendarYearToDate) {
		this.enableCalendarYearToDate = enableCalendarYearToDate;
	}
	public boolean isEnableCurrentPayInformation() {
		return enableCurrentPayInformation;
	}
	public void setEnableCurrentPayInformation(boolean enableCurrentPayInformation) {
		this.enableCurrentPayInformation = enableCurrentPayInformation;
	}
	public boolean isEnableDeductions() {
		return enableDeductions;
	}
	public void setEnableDeductions(boolean enableDeductions) {
		this.enableDeductions = enableDeductions;
	}
	public boolean isEnableSelfServiceDemographic() {
		return enableSelfServiceDemographic;
	}
	public void setEnableSelfServiceDemographic(boolean enableSelfServiceDemographic) {
		this.enableSelfServiceDemographic = enableSelfServiceDemographic;
	}
	public boolean isEnableSelfServicePayroll() {
		return enableSelfServicePayroll;
	}
	public void setEnableSelfServicePayroll(boolean enableSelfServicePayroll) {
		this.enableSelfServicePayroll = enableSelfServicePayroll;
	}
	public boolean isEnable1095() {
		return enable1095;
	}
	public void setEnable1095(boolean enable1095) {
		this.enable1095 = enable1095;
	}
	public boolean isEnableElecConsnt1095() {
		return enableElecConsnt1095;
	}
	public void setEnableElecConsnt1095(boolean enableElecConsnt1095) {
		this.enableElecConsnt1095 = enableElecConsnt1095;
	}
	public boolean isShowProcessedLeave() {
		return showProcessedLeave;
	}
	public void setShowProcessedLeave(boolean showProcessedLeave) {
		this.showProcessedLeave = showProcessedLeave;
	}
	public boolean isShowUnprocessedLeave() {
		return showUnprocessedLeave;
	}
	public void setShowUnprocessedLeave(boolean showUnprocessedLeave) {
		this.showUnprocessedLeave = showUnprocessedLeave;
	}
	public boolean isRestrictEarnings() {
		return restrictEarnings;
	}
	public void setRestrictEarnings(boolean restrictEarnings) {
		this.restrictEarnings = restrictEarnings;
	}
	public Integer getMaxDays() {
		return maxDays;
	}
	public void setMaxDays(Integer maxDays) {
		this.maxDays = maxDays;
	}
	public Integer getW2Latest() {
		return w2Latest;
	}
	public void setW2Latest(Integer latest) {
		w2Latest = latest;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getPreNote() {
		return preNote;
	}
	public void setPreNote(String preNote) {
		this.preNote = preNote;
	}
	public Integer getDdAccounts() {
		return ddAccounts;
	}
	public void setDdAccounts(Integer ddAccounts) {
		this.ddAccounts = ddAccounts;
	}
	public String getMessageEmployeeAccessSystem() {
		return messageEmployeeAccessSystem;
	}
	public void setMessageEmployeeAccessSystem(String messageEmployeeAccessSystem) {
		this.messageEmployeeAccessSystem = messageEmployeeAccessSystem;
	}
	public String getMessageCalendarYearToDate() {
		return messageCalendarYearToDate;
	}
	public void setMessageCalendarYearToDate(String messageCalendarYearToDate) {
		this.messageCalendarYearToDate = messageCalendarYearToDate;
	}
	public String getMessageCurrentPayInformation() {
		return messageCurrentPayInformation;
	}
	public void setMessageCurrentPayInformation(String messageCurrentPayInformation) {
		this.messageCurrentPayInformation = messageCurrentPayInformation;
	}
	public String getMessageDeductions() {
		return messageDeductions;
	}
	public void setMessageDeductions(String messageDeductions) {
		this.messageDeductions = messageDeductions;
	}
	public String getMessageEarnings() {
		return messageEarnings;
	}
	public void setMessageEarnings(String messageEarnings) {
		this.messageEarnings = messageEarnings;
	}
	public String getMessageLeave() {
		return messageLeave;
	}
	public void setMessageLeave(String messageLeave) {
		this.messageLeave = messageLeave;
	}
	public String getMessageSelfServiceDemographic() {
		return messageSelfServiceDemographic;
	}
	public void setMessageSelfServiceDemographic(
			String messageSelfServiceDemographic) {
		this.messageSelfServiceDemographic = messageSelfServiceDemographic;
	}
	public String getMessageSelfServicePayroll() {
		return messageSelfServicePayroll;
	}
	public void setMessageSelfServicePayroll(String messageSelfServicePayroll) {
		this.messageSelfServicePayroll = messageSelfServicePayroll;
	}
	public String getMessageW2() {
		return messageW2;
	}
	public void setMessageW2(String messageW2) {
		this.messageW2 = messageW2;
	}
	public String getMessage1095() {
		return message1095;
	}
	public void setMessage1095(String message1095) {
		this.message1095 = message1095;
	}
	public boolean isEnableLeaveReq() {
		return enableLeaveReq;
	}
	public void setEnableLeaveReq(boolean enableLeaveReq) {
		this.enableLeaveReq = enableLeaveReq;
	}
	public boolean isUsePMISSpvsrLevels() {
		return usePMISSpvsrLevels;
	}
	public void setUsePMISSpvsrLevels(boolean usePMISSpvsrLevels) {
		this.usePMISSpvsrLevels = usePMISSpvsrLevels;
	}
	public String getMessageLeaveRequest() {
		return messageLeaveRequest;
	}
	public void setMessageLeaveRequest(String messageLeaveRequest) {
		this.messageLeaveRequest = messageLeaveRequest;
	}
}