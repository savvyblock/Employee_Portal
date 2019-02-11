package net.esc20.txeis.EmployeeAccess.domainobject.SelfService.Demo;

import java.io.Serializable;

import net.esc20.txeis.EmployeeAccess.conversion.YesNoStringToBoolean;


//Generic Boolean object for DemoInfo Fields. Can be used for DemoInfoChanges or DemoRequiredFields
public class DemoInfoFields implements Serializable
{
	private static final long serialVersionUID = -4293607069850390057L;
	
	private Boolean nameTitle;
	private Boolean nameLast;
	private Boolean nameFirst;
	private Boolean nameMiddle;
	private Boolean nameGeneration;
	
	private Boolean maritalLocal;
	
	private Boolean driversNum;
	private Boolean driversState;
	
	private Boolean restrictionLocal;
	private Boolean restrictionPublic;
	
	private Boolean emergencyName;
	private Boolean emergencyAreaCode;
	private Boolean emergencyPhoneNum;
	private Boolean emergencyPhoneExt;
	private Boolean emergencyRelationship;
	private Boolean emergencyNotes;
	
	private Boolean mailingAddress;
	private Boolean mailingPoBox;
	private Boolean mailingApt;
	private Boolean mailingCity;
	private Boolean mailingState;
	private Boolean mailingZip;
	private Boolean mailingZip4;
	
	private Boolean alternateAddress;
	private Boolean alternatePoBox;
	private Boolean alternateApt;
	private Boolean alternateCity;
	private Boolean alternateState;
	private Boolean alternateZip;
	private Boolean alternateZip4;
	
	private Boolean phoneHomeArea;
	private Boolean phoneHomeNum;
	
	private Boolean phoneBusArea;
	private Boolean phoneBusNum;
	private Boolean phoneBusExt;
	
	private Boolean phoneCellArea;
	private Boolean phoneCellNum;
	
	private Boolean emailWork;
	private Boolean emailHome;
	
	public Boolean getNameTitle() {
		return nameTitle;
	}
	public void setNameTitle(Boolean nameTitle) {
		this.nameTitle = nameTitle;
	}
	
	public void setNameTitle(String nameTitle) {
		this.nameTitle = YesNoStringToBoolean.convertToBoolean(nameTitle);
	}
	
	public Boolean getNameLast() {
		return nameLast;
	}
	public void setNameLast(Boolean nameLast) {
		this.nameLast = nameLast;
	}
	
	public void setNameLast(String nameLast) {
		this.nameLast= YesNoStringToBoolean.convertToBoolean(nameLast);
	}
	
	public Boolean getNameFirst() {
		return nameFirst;
	}
	
	public void setNameFirst(String nameFirst) {
		this.nameFirst = YesNoStringToBoolean.convertToBoolean(nameFirst);
	}
	
	public void setNameFirst(Boolean nameFirst) {
		this.nameFirst = nameFirst;
	}
	
	public Boolean getNameMiddle() {
		return nameMiddle;
	}
	
	public void setNameMiddle(Boolean nameMiddle) {
		this.nameMiddle = nameMiddle;
	}
	
	public void setNameMiddle(String nameMiddle) {
		this.nameMiddle = YesNoStringToBoolean.convertToBoolean(nameMiddle);
	}
	
	public Boolean getNameGeneration() {
		return nameGeneration;
	}
	
	public void setNameGeneration(Boolean nameGeneration) {
		this.nameGeneration = nameGeneration;
	}
	
	public void setNameGeneration(String nameGeneration) {
		this.nameGeneration = YesNoStringToBoolean.convertToBoolean(nameGeneration);
	}
	
	public Boolean getMaritalLocal() {
		return maritalLocal;
	}
	
	public void setMaritalLocal(Boolean maritalLocal) {
		this.maritalLocal = maritalLocal;
	}
	
	public void setMaritalLocal(String maritalLocal) {
		this.maritalLocal = YesNoStringToBoolean.convertToBoolean(maritalLocal);
	}
	
	public Boolean getDriversNum() {
		return driversNum;
	}
	
	public void setDriversNum(Boolean driversNum) {
		this.driversNum = driversNum;
	}
	
	public void setDriversNum(String driversNum) {
		this.driversNum = YesNoStringToBoolean.convertToBoolean(driversNum);
	}
	
	public Boolean getDriversState() {
		return driversState;
	}
	
	public void setDriversState(Boolean driversState) {
		this.driversState = driversState;
	}
	
	public void setDriversState(String driversState) {
		this.driversState = YesNoStringToBoolean.convertToBoolean(driversState);
	}
	
	public Boolean getRestrictionLocal() {
		return restrictionLocal;
	}
	
	public void setRestrictionLocal(Boolean restrictionLocal) {
		this.restrictionLocal = restrictionLocal;
	}
	
	public void setRestrictionLocal(String restrictionLocal) {
		this.restrictionLocal = YesNoStringToBoolean.convertToBoolean(restrictionLocal);
	}
	
	public Boolean getRestrictionPublic() {
		return restrictionPublic;
	}
	
	public void setRestrictionPublic(Boolean restrictionPublic) {
		this.restrictionPublic = restrictionPublic;
	}
	
	public void setRestrictionPublic(String restrictionPublic) {
		this.restrictionPublic = YesNoStringToBoolean.convertToBoolean(restrictionPublic);
	}
	
	public Boolean getEmergencyName() {
		return emergencyName;
	}
	
	public void setEmergencyName(Boolean emergencyName) {
		this.emergencyName = emergencyName;
	}
	
	public void setEmergencyName(String emergencyName) {
		this.emergencyName = YesNoStringToBoolean.convertToBoolean(emergencyName);
	}
	
	public Boolean getEmergencyAreaCode() {
		return emergencyAreaCode;
	}
	
	public void setEmergencyAreaCode(Boolean emergencyAreaCode) {
		this.emergencyAreaCode = emergencyAreaCode;
	}
	
	public void setEmergencyAreaCode(String emergencyAreaCode) {
		this.emergencyAreaCode = YesNoStringToBoolean.convertToBoolean(emergencyAreaCode);
	}
	
	public Boolean getEmergencyPhoneNum() {
		return emergencyPhoneNum;
	}
	
	public void setEmergencyPhoneNum(Boolean emergencyPhoneNum) {
		this.emergencyPhoneNum = emergencyPhoneNum;
	}
	
	public void setEmergencyPhoneNum(String emergencyPhoneNum) {
		this.emergencyPhoneNum = YesNoStringToBoolean.convertToBoolean(emergencyPhoneNum);
	}
	
	public Boolean getEmergencyPhoneExt() {
		return emergencyPhoneExt;
	}
	
	public void setEmergencyPhoneExt(Boolean emergencyPhoneExt) {
		this.emergencyPhoneExt = emergencyPhoneExt;
	}
	
	public void setEmergencyPhoneExt(String emergencyPhoneExt) {
		this.emergencyPhoneExt = YesNoStringToBoolean.convertToBoolean(emergencyPhoneExt);
	}
	
	public Boolean getEmergencyRelationship() {
		return emergencyRelationship;
	}
	
	public void setEmergencyRelationship(Boolean emergencyRelationship) {
		this.emergencyRelationship = emergencyRelationship;
	}
	
	public void setEmergencyRelationship(String emergencyRelationship) {
		this.emergencyRelationship = YesNoStringToBoolean.convertToBoolean(emergencyRelationship);
	}
	
	public Boolean getEmergencyNotes() {
		return emergencyNotes;
	}
	
	public void setEmergencyNotes(Boolean emergencyNotes) {
		this.emergencyNotes = emergencyNotes;
	}
	
	public void setEmergencyNotes(String emergencyNotes) {
		this.emergencyNotes = YesNoStringToBoolean.convertToBoolean(emergencyNotes);
	}
	
	public Boolean getMailingAddress() {
		return mailingAddress;
	}
	
	public void setMailingAddress(Boolean mailingAddress) {
		this.mailingAddress = mailingAddress;
	}
	
	public void setMailingAddress(String mailingAddress) {
		this.mailingAddress = YesNoStringToBoolean.convertToBoolean(mailingAddress);
	}
	
	public Boolean getMailingPoBox() {
		return mailingPoBox;
	}
	
	public void setMailingPoBox(Boolean mailingPoBox) {
		this.mailingPoBox = mailingPoBox;
	}
	
	public void setMailingPoBox(String mailingPoBox) {
		this.mailingPoBox = YesNoStringToBoolean.convertToBoolean(mailingPoBox);
	}
	
	public Boolean getMailingApt() {
		return mailingApt;
	}
	
	public void setMailingApt(Boolean mailingApt) {
		this.mailingApt = mailingApt;
	}
	
	public void setMailingApt(String mailingApt) {
		this.mailingApt = YesNoStringToBoolean.convertToBoolean(mailingApt);
	}
	
	public Boolean getMailingCity() {
		return mailingCity;
	}
	
	public void setMailingCity(Boolean mailingCity) {
		this.mailingCity = mailingCity;
	}
	
	public void setMailingCity(String mailingCity) {
		this.mailingCity = YesNoStringToBoolean.convertToBoolean(mailingCity);
	}
	
	public Boolean getMailingState() {
		return mailingState;
	}
	
	public void setMailingState(Boolean mailingState) {
		this.mailingState = mailingState;
	}
	
	public void setMailingState(String mailingState) {
		this.mailingState = YesNoStringToBoolean.convertToBoolean(mailingState);
	}
	
	public Boolean getMailingZip() {
		return mailingZip;
	}
	
	public void setMailingZip(Boolean mailingZip) {
		this.mailingZip = mailingZip;
	}
	
	public void setMailingZip(String mailingZip) {
		this.mailingZip = YesNoStringToBoolean.convertToBoolean(mailingZip);
	}
	
	public Boolean getMailingZip4() {
		return mailingZip4;
	}
	
	public void setMailingZip4(Boolean mailingZip4) {
		this.mailingZip4 = mailingZip4;
	}
	
	public void setMailingZip4(String mailingZip4) {
		this.mailingZip4 = YesNoStringToBoolean.convertToBoolean(mailingZip4);
	}
	
	public Boolean getAlternateAddress() {
		return alternateAddress;
	}
	
	public void setAlternateAddress(Boolean alternateAddress) {
		this.alternateAddress = alternateAddress;
	}
	
	public void setAlternateAddress(String alternateAddress) {
		this.alternateAddress = YesNoStringToBoolean.convertToBoolean(alternateAddress);
	}
	
	public Boolean getAlternatePoBox() {
		return alternatePoBox;
	}
	
	public void setAlternatePoBox(Boolean alternatePoBox) {
		this.alternatePoBox = alternatePoBox;
	}
	
	public void setAlternatePoBox(String alternatePoBox) {
		this.alternatePoBox = YesNoStringToBoolean.convertToBoolean(alternatePoBox);
	}
	
	public Boolean getAlternateApt() {
		return alternateApt;
	}
	
	public void setAlternateApt(Boolean alternateApt) {
		this.alternateApt = alternateApt;
	}
	
	public void setAlternateApt(String alternateApt) {
		this.alternateApt = YesNoStringToBoolean.convertToBoolean(alternateApt);
	}
	
	public Boolean getAlternateCity() {
		return alternateCity;
	}
	
	public void setAlternateCity(Boolean alternateCity) {
		this.alternateCity = alternateCity;
	}
	
	public void setAlternateCity(String alternateCity) {
		this.alternateCity = YesNoStringToBoolean.convertToBoolean(alternateCity);
	}
	
	public Boolean getAlternateState() {
		return alternateState;
	}
	
	public void setAlternateState(Boolean alternateState) {
		this.alternateState = alternateState;
	}
	
	public void setAlternateState(String alternateState) {
		this.alternateState = YesNoStringToBoolean.convertToBoolean(alternateState);
	}
	
	public Boolean getAlternateZip() {
		return alternateZip;
	}
	
	public void setAlternateZip(Boolean alternateZip) {
		this.alternateZip = alternateZip;
	}
	
	public void setAlternateZip(String alternateZip) {
		this.alternateZip = YesNoStringToBoolean.convertToBoolean(alternateZip);
	}
	
	public Boolean getAlternateZip4() {
		return alternateZip4;
	}
	
	public void setAlternateZip4(Boolean alternateZip4) {
		this.alternateZip4 = alternateZip4;
	}
	
	public void setAlternateZip4(String alternateZip4) {
		this.alternateZip4 = YesNoStringToBoolean.convertToBoolean(alternateZip4);
	}
	
	public Boolean getPhoneHomeArea() {
		return phoneHomeArea;
	}
	
	public void setPhoneHomeArea(Boolean phoneHomeArea) {
		this.phoneHomeArea = phoneHomeArea;
	}
	
	public void setPhoneHomeArea(String phoneHomeArea) {
		this.phoneHomeArea = YesNoStringToBoolean.convertToBoolean(phoneHomeArea);
	}
	
	public Boolean getPhoneHomeNum() {
		return phoneHomeNum;
	}
	
	public void setPhoneHomeNum(Boolean phoneHomeNum) {
		this.phoneHomeNum = phoneHomeNum;
	}
	
	public void setPhoneHomeNum(String phoneHomeNum) {
		this.phoneHomeNum = YesNoStringToBoolean.convertToBoolean(phoneHomeNum);
	}
	
	public Boolean getPhoneBusArea() {
		return phoneBusArea;
	}
	
	public void setPhoneBusArea(Boolean phoneBusArea) {
		this.phoneBusArea = phoneBusArea;
	}
	
	public void setPhoneBusArea(String phoneBusArea) {
		this.phoneBusArea = YesNoStringToBoolean.convertToBoolean(phoneBusArea);
	}
	
	public Boolean getPhoneBusNum() {
		return phoneBusNum;
	}
	
	public void setPhoneBusNum(Boolean phoneBusNum) {
		this.phoneBusNum = phoneBusNum;
	}
	
	public void setPhoneBusNum(String phoneBusNum) {
		this.phoneBusNum = YesNoStringToBoolean.convertToBoolean(phoneBusNum);
	}
	
	public Boolean getPhoneBusExt() {
		return phoneBusExt;
	}
	
	public void setPhoneBusExt(Boolean phoneBusExt) {
		this.phoneBusExt = phoneBusExt;
	}
	
	public void setPhoneBusExt(String phoneBusExt) {
		this.phoneBusExt = YesNoStringToBoolean.convertToBoolean(phoneBusExt);
	}
	
	public Boolean getPhoneCellArea() {
		return phoneCellArea;
	}
	
	public void setPhoneCellArea(Boolean phoneCellArea) {
		this.phoneCellArea = phoneCellArea;
	}
	
	public void setPhoneCellArea(String phoneCellArea) {
		this.phoneCellArea = YesNoStringToBoolean.convertToBoolean(phoneCellArea);
	}
	
	public Boolean getPhoneCellNum() {
		return phoneCellNum;
	}
	
	public void setPhoneCellNum(Boolean phoneCellNum) {
		this.phoneCellNum = phoneCellNum;
	}
	
	public void setPhoneCellNum(String phoneCellNum) {
		this.phoneCellNum = YesNoStringToBoolean.convertToBoolean(phoneCellNum);
	}
	
	public Boolean getEmailWork() {
		return emailWork;
	}
	
	public void setEmailWork(Boolean emailWork) {
		this.emailWork = emailWork;
	}
	
	public void setEmailWork(String emailWork) {
		this.emailWork = YesNoStringToBoolean.convertToBoolean(emailWork);
	}
	
	public Boolean getEmailHome() {
		return emailHome;
	}
	
	public void setEmailHome(Boolean emailHome) {
		this.emailHome = emailHome;
	}
	
	public void setEmailHome(String emailHome) {
		this.emailHome = YesNoStringToBoolean.convertToBoolean(emailHome);
	}
}