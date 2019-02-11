package net.esc20.txeis.EmployeeAccess.web.view;

import java.io.Serializable;

import net.esc20.txeis.EmployeeAccess.domainobject.SelfService.Demo.DemoInfo;
import net.esc20.txeis.EmployeeAccess.domainobject.SelfService.Demo.DemoInfoFields;


public class Demo implements Serializable
{
	private static final long serialVersionUID = 7173022220222933734L;
	 
	private String fieldDisplayOptionName = "I";
	private String fieldDisplayOptionMailAddr = "I";
	private String fieldDisplayOptionAltAddr = "I";
	private String fieldDisplayOptionHomePhone = "I";
	private String fieldDisplayOptionWorkPhone = "I";
	private String fieldDisplayOptionCellPhone = "I";
	private String fieldDisplayOptionEmail = "I";
	private String fieldDisplayOptionRestrictionCodes = "I";
	private String fieldDisplayOptionMarital = "I";
	private String fieldDisplayOptionDriversLicense = "I";
	private String fieldDisplayOptionEmergencyContact = "I";

	private Boolean autoApproveName = false;
	private Boolean autoApproveMailAddr = false;
	private Boolean autoApproveAltAddr = false;
	private Boolean autoApproveHomePhone = false;
	private Boolean autoApproveWorkPhone = false;
	private Boolean autoApproveCellPhone = false;
	private Boolean autoApproveEmail = false;
	private Boolean autoApproveRestrictionCodes = false;
	private Boolean autoApproveMarital = false;
	private Boolean autoApproveDriversLicense = false;
	private Boolean autoApproveEmergencyContact = false;
	
	//binded pending changes object for payroll info (marital status and number of exceptions)
	private DemoInfo demoInfo;
	
	//booleans for if changes exist in DB (used for determining highlighting)
	private DemoInfoFields demoInfoChanges;

	public void setDemoInfo(DemoInfo demoInfo) {
		this.demoInfo = demoInfo;
	}

	public DemoInfo getDemoInfo() {
		return demoInfo;
	}

	public void setFieldDisplayOptionName(String fieldDisplayOptionName) {
		this.fieldDisplayOptionName = fieldDisplayOptionName;
	}

	public String getFieldDisplayOptionName() {
		return fieldDisplayOptionName;
	}

	public void setFieldDisplayOptionMailAddr(String fieldDisplayOptionMailAddr) {
		this.fieldDisplayOptionMailAddr = fieldDisplayOptionMailAddr;
	}

	public String getFieldDisplayOptionMailAddr() {
		return fieldDisplayOptionMailAddr;
	}

	public void setFieldDisplayOptionAltAddr(String fieldDisplayOptionAltAddr) {
		this.fieldDisplayOptionAltAddr = fieldDisplayOptionAltAddr;
	}

	public String getFieldDisplayOptionAltAddr() {
		return fieldDisplayOptionAltAddr;
	}

	public void setFieldDisplayOptionHomePhone(
			String fieldDisplayOptionHomePhone) {
		this.fieldDisplayOptionHomePhone = fieldDisplayOptionHomePhone;
	}

	public String getFieldDisplayOptionHomePhone() {
		return fieldDisplayOptionHomePhone;
	}

	public void setFieldDisplayOptionWorkPhone(
			String fieldDisplayOptionWorkPhone) {
		this.fieldDisplayOptionWorkPhone = fieldDisplayOptionWorkPhone;
	}

	public String getFieldDisplayOptionWorkPhone() {
		return fieldDisplayOptionWorkPhone;
	}

	public void setFieldDisplayOptionCellPhone(
			String fieldDisplayOptionCellPhone) {
		this.fieldDisplayOptionCellPhone = fieldDisplayOptionCellPhone;
	}

	public String getFieldDisplayOptionCellPhone() {
		return fieldDisplayOptionCellPhone;
	}

	public void setFieldDisplayOptionEmail(String fieldDisplayOptionEmail) {
		this.fieldDisplayOptionEmail = fieldDisplayOptionEmail;
	}

	public String getFieldDisplayOptionEmail() {
		return fieldDisplayOptionEmail;
	}

	public void setFieldDisplayOptionRestrictionCodes(
			String fieldDisplayOptionRestrictionCodes) {
		this.fieldDisplayOptionRestrictionCodes = fieldDisplayOptionRestrictionCodes;
	}

	public String getFieldDisplayOptionRestrictionCodes() {
		return fieldDisplayOptionRestrictionCodes;
	}

	public void setFieldDisplayOptionMarital(String fieldDisplayOptionMarital) {
		this.fieldDisplayOptionMarital = fieldDisplayOptionMarital;
	}

	public String getFieldDisplayOptionMarital() {
		return fieldDisplayOptionMarital;
	}

	public void setFieldDisplayOptionDriversLicense(
			String fieldDisplayOptionDriversLicense) {
		this.fieldDisplayOptionDriversLicense = fieldDisplayOptionDriversLicense;
	}

	public String getFieldDisplayOptionDriversLicense() {
		return fieldDisplayOptionDriversLicense;
	}

	public void setFieldDisplayOptionEmergencyContact(
			String fieldDisplayOptionEmergencyContact) {
		this.fieldDisplayOptionEmergencyContact = fieldDisplayOptionEmergencyContact;
	}

	public String getFieldDisplayOptionEmergencyContact() {
		return fieldDisplayOptionEmergencyContact;
	}

	public void setAutoApproveName(Boolean autoApproveName) {
		this.autoApproveName = autoApproveName;
	}

	public Boolean getAutoApproveName() {
		return autoApproveName;
	}

	public void setAutoApproveMailAddr(Boolean autoApproveMailAddr) {
		this.autoApproveMailAddr = autoApproveMailAddr;
	}

	public Boolean getAutoApproveMailAddr() {
		return autoApproveMailAddr;
	}

	public void setAutoApproveAltAddr(Boolean autoApproveAltAddr) {
		this.autoApproveAltAddr = autoApproveAltAddr;
	}

	public Boolean getAutoApproveAltAddr() {
		return autoApproveAltAddr;
	}

	public void setAutoApproveHomePhone(Boolean autoApproveHomePhone) {
		this.autoApproveHomePhone = autoApproveHomePhone;
	}

	public Boolean getAutoApproveHomePhone() {
		return autoApproveHomePhone;
	}

	public void setAutoApproveWorkPhone(Boolean autoApproveWorkPhone) {
		this.autoApproveWorkPhone = autoApproveWorkPhone;
	}

	public Boolean getAutoApproveWorkPhone() {
		return autoApproveWorkPhone;
	}

	public void setAutoApproveCellPhone(Boolean autoApproveCellPhone) {
		this.autoApproveCellPhone = autoApproveCellPhone;
	}

	public Boolean getAutoApproveCellPhone() {
		return autoApproveCellPhone;
	}

	public void setAutoApproveEmail(Boolean autoApproveEmail) {
		this.autoApproveEmail = autoApproveEmail;
	}

	public Boolean getAutoApproveEmail() {
		return autoApproveEmail;
	}

	public void setAutoApproveRestrictionCodes(
			Boolean autoApproveRestrictionCodes) {
		this.autoApproveRestrictionCodes = autoApproveRestrictionCodes;
	}

	public Boolean getAutoApproveRestrictionCodes() {
		return autoApproveRestrictionCodes;
	}

	public void setAutoApproveMarital(Boolean autoApproveMarital) {
		this.autoApproveMarital = autoApproveMarital;
	}

	public Boolean getAutoApproveMarital() {
		return autoApproveMarital;
	}

	public void setAutoApproveDriversLicense(Boolean autoApproveDriversLicense) {
		this.autoApproveDriversLicense = autoApproveDriversLicense;
	}

	public Boolean getAutoApproveDriversLicense() {
		return autoApproveDriversLicense;
	}

	public void setAutoApproveEmergencyContact(
			Boolean autoApproveEmergencyContact) {
		this.autoApproveEmergencyContact = autoApproveEmergencyContact;
	}

	public Boolean getAutoApproveEmergencyContact() {
		return autoApproveEmergencyContact;
	}

	public void setDemoInfoChanges(DemoInfoFields demoInfoChanges) {
		this.demoInfoChanges = demoInfoChanges;
	}

	public DemoInfoFields getDemoInfoChanges() {
		return demoInfoChanges;
	}

}