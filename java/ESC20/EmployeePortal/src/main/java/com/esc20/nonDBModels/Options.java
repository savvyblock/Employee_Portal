package com.esc20.nonDBModels;

import java.io.Serializable;

import com.esc20.model.BhrEapOpt;

public class Options implements Serializable {

	public static enum IdType
	{
		EmployeeNumber,
		Ssn;
	}
	
	private IdType idType;
	private Boolean enableEmployeeAccessSystem;
	private Boolean enableEarnings;
	private Boolean enableLeave;
	private Boolean enableW2;
	private Boolean enableElecConsntW2;
	private Boolean enable1095;
	private Boolean enableElecConsnt1095;
	private Boolean enableCalendarYearToDate;
	private Boolean enableCurrentPayInformation;
	private Boolean enableDeductions;
	private Boolean enableSelfServiceDemographic;
	private Boolean enableSelfServicePayroll;
	private Boolean showProcessedLeave;
	private Boolean showUnprocessedLeave;
	private Boolean restrictEarnings;
	private Boolean enableLeaveReq;
	private Boolean enableTrvl;
	private Boolean usePMISSpvsrLevels;
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
	private String messageTrvl;
	private String messageElecConsentW2;
	private String messageElecConsent1095;
	
	public Options(BhrEapOpt opt) {
		String idType = opt.getIdTyp().toString();
		
		if("E".equals(idType))
		{
			this.setIdType(Options.IdType.EmployeeNumber);
		}
		else if("S".equals(idType))
		{
			this.setIdType(Options.IdType.Ssn);
		}
		
		if("Y".equals(opt.getEnblEap().toString()))
			this.setEnableEmployeeAccessSystem(true);
		else
			this.setEnableEmployeeAccessSystem(false);
		
		if("Y".equals(opt.getEnblEarn().toString()))
			this.setEnableEarnings(true);
		else
			this.setEnableEarnings(false);
		
		if("Y".equals(opt.getEnblLv().toString()))
			this.setEnableLeave(true);
		else
			this.setEnableLeave(false);
		
		if("Y".equals(opt.getEnblTrvl().toString()))
			this.setEnableTrvl(true);
		else
			this.setEnableTrvl(false);
		
		if("Y".equals(opt.getEnblW2().toString()))
			this.setEnableW2(true);
		else
			this.setEnableW2(false);
		
		if("Y".equals(opt.getEnblElecConsntW2().toString()))
			this.setEnableElecConsntW2(true);
		else
			this.setEnableElecConsntW2(false);
		
		if("Y".equals(opt.getEnbl1095().toString()))
			this.setEnable1095(true);
		else
			this.setEnable1095(false);
		
		if("Y".equals(opt.getEnblElecConsnt1095().toString()))
			this.setEnableElecConsnt1095(true);
		else
			this.setEnableElecConsnt1095(false);
		
		if("Y".equals(opt.getEnblCalYtd().toString()))
			this.setEnableCalendarYearToDate(true);
		else
			this.setEnableCalendarYearToDate(false);
		
		if("Y".equals(opt.getEnblCpi().toString()))
			this.setEnableCurrentPayInformation(true);
		else
			this.setEnableCurrentPayInformation(false);
		
		if("Y".equals(opt.getEnblDed().toString()))
			this.setEnableDeductions(true);
		else
			this.setEnableDeductions(false);
		
		if("Y".equals(opt.getEnblDemo().toString()))
			this.setEnableSelfServiceDemographic(true);
		else
			this.setEnableSelfServiceDemographic(false);
		
		if("Y".equals(opt.getEnblPay().toString()))
			this.setEnableSelfServicePayroll(true);
		else
			this.setEnableSelfServicePayroll(false);
		
		if("Y".equals(opt.getShowProc().toString()))
			this.setShowProcessedLeave(true);
		else
			this.setShowProcessedLeave(false);
		
		if("Y".equals(opt.getShowUnproc().toString()))
			this.setShowUnprocessedLeave(true);
		else
			this.setShowUnprocessedLeave(false);
		
		if("Y".equals(opt.getRstrEarnDd().toString()))
			this.setRestrictEarnings(true);
		else
			this.setRestrictEarnings(false);
		
		if("Y".equals(opt.getEnblLvReq().toString()))
			this.setEnableLeaveReq(true);
		else
			this.setEnableLeaveReq(false);
		
		if ("Y".equals(opt.getUsePmisSpvsrLevels().toString().trim()))
			this.setUsePMISSpvsrLevels(true);
		else
			this.setUsePMISSpvsrLevels(false);
		
		this.setMaxDays(opt.getMaxDays());
		this.setW2Latest(opt.getW2Latest());
		this.setUrl(opt.getUrlHm());
		this.setPreNote(opt.getPreNote()==null?"":opt.getPreNote().toString());
		this.setDdAccounts(opt.getDdAcct());
		
		this.setMessageEmployeeAccessSystem(opt.getMsgEap());
		this.setMessageCalendarYearToDate(opt.getMsgCalYtd());
		this.setMessageCurrentPayInformation(opt.getMsgCpi());
		this.setMessageDeductions(opt.getMsgDed());
		this.setMessageEarnings(opt.getMsgEarn());
		this.setMessageLeave(opt.getMsgLv());
		this.setMessageSelfServiceDemographic(opt.getMsgDemo());
		this.setMessageSelfServicePayroll(opt.getMsgPay());
		this.setMessageW2(opt.getMsgW2());
		this.setMessage1095(opt.getMsg1095());
		this.setMessageLeaveRequest(opt.getMsgLvReq());
		this.setMessageTrvl(opt.getMsgTrvl());
		this.setMessageElecConsentW2(opt.getMsgElecConsntW2());
		this.setMessageElecConsent1095(opt.getMsgElecConsnt1095());
	}

	public IdType getIdType() {
		return idType;
	}

	public void setIdType(IdType idType) {
		this.idType = idType;
	}

	public Boolean getEnableEmployeeAccessSystem() {
		return enableEmployeeAccessSystem;
	}

	public void setEnableEmployeeAccessSystem(Boolean enableEmployeeAccessSystem) {
		this.enableEmployeeAccessSystem = enableEmployeeAccessSystem;
	}

	public Boolean getEnableEarnings() {
		return enableEarnings;
	}

	public void setEnableEarnings(Boolean enableEarnings) {
		this.enableEarnings = enableEarnings;
	}

	public Boolean getEnableLeave() {
		return enableLeave;
	}

	public void setEnableLeave(Boolean enableLeave) {
		this.enableLeave = enableLeave;
	}

	public Boolean getEnableW2() {
		return enableW2;
	}

	public void setEnableW2(Boolean enableW2) {
		this.enableW2 = enableW2;
	}

	public Boolean getEnableElecConsntW2() {
		return enableElecConsntW2;
	}

	public void setEnableElecConsntW2(Boolean enableElecConsntW2) {
		this.enableElecConsntW2 = enableElecConsntW2;
	}

	public Boolean getEnable1095() {
		return enable1095;
	}

	public void setEnable1095(Boolean enable1095) {
		this.enable1095 = enable1095;
	}

	public Boolean getEnableElecConsnt1095() {
		return enableElecConsnt1095;
	}

	public void setEnableElecConsnt1095(Boolean enableElecConsnt1095) {
		this.enableElecConsnt1095 = enableElecConsnt1095;
	}

	public Boolean getEnableCalendarYearToDate() {
		return enableCalendarYearToDate;
	}

	public void setEnableCalendarYearToDate(Boolean enableCalendarYearToDate) {
		this.enableCalendarYearToDate = enableCalendarYearToDate;
	}

	public Boolean getEnableCurrentPayInformation() {
		return enableCurrentPayInformation;
	}

	public void setEnableCurrentPayInformation(Boolean enableCurrentPayInformation) {
		this.enableCurrentPayInformation = enableCurrentPayInformation;
	}

	public Boolean getEnableDeductions() {
		return enableDeductions;
	}

	public void setEnableDeductions(Boolean enableDeductions) {
		this.enableDeductions = enableDeductions;
	}

	public Boolean getEnableSelfServiceDemographic() {
		return enableSelfServiceDemographic;
	}

	public void setEnableSelfServiceDemographic(Boolean enableSelfServiceDemographic) {
		this.enableSelfServiceDemographic = enableSelfServiceDemographic;
	}

	public Boolean getEnableSelfServicePayroll() {
		return enableSelfServicePayroll;
	}

	public void setEnableSelfServicePayroll(Boolean enableSelfServicePayroll) {
		this.enableSelfServicePayroll = enableSelfServicePayroll;
	}

	public Boolean getShowProcessedLeave() {
		return showProcessedLeave;
	}

	public void setShowProcessedLeave(Boolean showProcessedLeave) {
		this.showProcessedLeave = showProcessedLeave;
	}

	public Boolean getShowUnprocessedLeave() {
		return showUnprocessedLeave;
	}

	public void setShowUnprocessedLeave(Boolean showUnprocessedLeave) {
		this.showUnprocessedLeave = showUnprocessedLeave;
	}

	public Boolean getRestrictEarnings() {
		return restrictEarnings;
	}

	public void setRestrictEarnings(Boolean restrictEarnings) {
		this.restrictEarnings = restrictEarnings;
	}

	public Boolean getEnableLeaveReq() {
		return enableLeaveReq;
	}

	public void setEnableLeaveReq(Boolean enableLeaveReq) {
		this.enableLeaveReq = enableLeaveReq;
	}
	
	public Boolean getEnableTrvl() {
		return enableTrvl;
	}

	public void setEnableTrvl(Boolean enableTrvl) {
		this.enableTrvl = enableTrvl;
	}

	public Boolean getUsePMISSpvsrLevels() {
		return usePMISSpvsrLevels;
	}

	public void setUsePMISSpvsrLevels(Boolean usePMISSpvsrLevels) {
		this.usePMISSpvsrLevels = usePMISSpvsrLevels;
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

	public void setW2Latest(Integer w2Latest) {
		this.w2Latest = w2Latest;
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
	
	public String getMessageTrvl() {
		return messageTrvl;
	}

	public void setMessageTrvl(String messageTrvl) {
		this.messageTrvl = messageTrvl;
	}

	public String getMessageSelfServiceDemographic() {
		return messageSelfServiceDemographic;
	}

	public void setMessageSelfServiceDemographic(String messageSelfServiceDemographic) {
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

	public String getMessageLeaveRequest() {
		return messageLeaveRequest;
	}

	public void setMessageLeaveRequest(String messageLeaveRequest) {
		this.messageLeaveRequest = messageLeaveRequest;
	}

	public String getMessageElecConsentW2() {
		return messageElecConsentW2;
	}

	public void setMessageElecConsentW2(String messageElecConsentW2) {
		this.messageElecConsentW2 = messageElecConsentW2;
	}

	public String getMessageElecConsent1095() {
		return messageElecConsent1095;
	}

	public void setMessageElecConsent1095(String messageElecConsent1095) {
		this.messageElecConsent1095 = messageElecConsent1095;
	}

	private static final long serialVersionUID = -1023249528812172648L;
	
	
}
