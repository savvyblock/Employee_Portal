package net.esc20.txeis.EmployeeAccess.dao.api;

import java.util.HashMap;
import java.util.List;

import net.esc20.txeis.EmployeeAccess.domainobject.User;
import net.esc20.txeis.EmployeeAccess.domainobject.SelfService.Demo.AltAddr;
import net.esc20.txeis.EmployeeAccess.domainobject.SelfService.Demo.CellPhone;
import net.esc20.txeis.EmployeeAccess.domainobject.SelfService.Demo.DemoInfo;
import net.esc20.txeis.EmployeeAccess.domainobject.SelfService.Demo.DriversLicense;
import net.esc20.txeis.EmployeeAccess.domainobject.SelfService.Demo.Ed20Command;
import net.esc20.txeis.EmployeeAccess.domainobject.SelfService.Demo.Ed25Command;
import net.esc20.txeis.EmployeeAccess.domainobject.SelfService.Demo.Email;
import net.esc20.txeis.EmployeeAccess.domainobject.SelfService.Demo.EmergencyContact;
import net.esc20.txeis.EmployeeAccess.domainobject.SelfService.Demo.HomePhone;
import net.esc20.txeis.EmployeeAccess.domainobject.SelfService.Demo.MailAddr;
import net.esc20.txeis.EmployeeAccess.domainobject.SelfService.Demo.Marital;
import net.esc20.txeis.EmployeeAccess.domainobject.SelfService.Demo.Name;
import net.esc20.txeis.EmployeeAccess.domainobject.SelfService.Demo.RestrictionCodes;
import net.esc20.txeis.EmployeeAccess.domainobject.SelfService.Demo.TraqsData;
import net.esc20.txeis.EmployeeAccess.domainobject.SelfService.Demo.WorkPhone;

public interface IDemoDao {
	DemoInfo getPendingDemoInfo(String employeeNumber, String sex, String dob, String staffId);
	DemoInfo getDemoInfo(String employeeNumber);
	String getNameFieldDisplay();
	String getMailAddrFieldDisplay();
	String getAltAddrFieldDisplay();
	String getHomePhoneFieldDisplay();
	String getWorkPhoneFieldDisplay();
	String getCellPhoneFieldDisplay();
	String getEmailFieldDisplay();
	String getRestrictionCodesFieldDisplay();
	String getMaritalFieldDisplay();
	String getDriversLicenseFieldDisplay();
	String getEmergencyContactFieldDisplay();
	Boolean getAutoApproveName();
	Boolean getAutoApproveMailAddr();
	Boolean getAutoApproveAltAddr();
	Boolean getAutoApproveHomePhone();
	Boolean getAutoApproveWorkPhone();
	Boolean getAutoApproveCellPhone();
	Boolean getAutoApproveRestrictionCodes();
	Boolean getAutoApproveEmail();
	Boolean getAutoApproveMarital();
	Boolean getAutoApproveDriversLicense();
	Boolean getAutoApproveEmergencyContact();

	List <List <String>> getRequiredFields();

	Boolean isNewRow(String employeeNumber, String tableName);
	void deleteRequestIfExists(String employeeNumber, String tableName);

	void insertNameRequest(Boolean isAutoApprove, String employeeNumber, Name newName, Name name);
	void updateNameRequest(Boolean isAutoApprove, String employeeNumber, Name newName, Name name);
	void updateNameApprove(String employeeNumber, Name newName);

	void insertMailAddrRequest(Boolean isAutoApprove, String employeeNumber, MailAddr newMailAddr, MailAddr mailAddr);
	void updateMailAddrRequest(Boolean isAutoApprove, String employeeNumber, MailAddr newMailAddr, MailAddr mailAddr);
	void updateMailAddrApprove(String employeeNumber, MailAddr newMailAddr);

	void insertAltAddrRequest(Boolean isAutoApprove, String employeeNumber, AltAddr newAltAddr, AltAddr altAddr);
	void updateAltAddrRequest(Boolean isAutoApprove, String employeeNumber, AltAddr newAltAddr, AltAddr altAddr);
	void updateAltAddrApprove(String employeeNumber, AltAddr newAltAddr);

	void insertHomePhoneRequest(Boolean isAutoApprove, String employeeNumber, HomePhone newHomePhone, HomePhone homePhone);
	void updateHomePhoneRequest(Boolean isAutoApprove, String employeeNumber, HomePhone newHomePhone, HomePhone homePhone);
	void updateHomePhoneApprove(String employeeNumber, HomePhone newHomePhone);

	void insertWorkPhoneRequest(Boolean isAutoApprove, String employeeNumber, WorkPhone newWorkPhone, WorkPhone workPhone);
	void updateWorkPhoneRequest(Boolean isAutoApprove, String employeeNumber, WorkPhone newWorkPhone, WorkPhone workPhone);
	void updateWorkPhoneApprove(String employeeNumber, WorkPhone newWorkPhone);

	void insertCellPhoneRequest(Boolean isAutoApprove, String employeeNumber, CellPhone newCellPhone, CellPhone cellPhone);
	void updateCellPhoneRequest(Boolean isAutoApprove, String employeeNumber, CellPhone newCellPhone, CellPhone cellPhone);
	void updateCellPhoneApprove(String employeeNumber, CellPhone newCellPhone);

	void insertRestrictionCodesRequest(Boolean isAutoApprove, String employeeNumber, 
										RestrictionCodes newRestrictionCodes, RestrictionCodes restrictionCodes);
	void updateRestrictionCodesRequest(Boolean isAutoApprove, String employeeNumber, 
										RestrictionCodes newRestrictionCodes, RestrictionCodes restrictionCodes);
	void updateRestrictionCodesApprove(String employeeNumber, RestrictionCodes newRestrictionCodes);

	void insertMaritalRequest(Boolean isAutoApprove, String employeeNumber, Marital newMarital, Marital marital);
	void updateMaritalRequest(Boolean isAutoApprove, String employeeNumber, Marital newMarital, Marital marital);
	void updateMaritalApprove(String employeeNumber, Marital newMarital);

	void insertDriversLicenseRequest(Boolean isAutoApprove, String employeeNumber, 
										DriversLicense newDriversLicense, DriversLicense driversLicense);
	void updateDriversLicenseRequest(Boolean isAutoApprove, String employeeNumber, 
										DriversLicense newDriversLicense, DriversLicense driversLicense);
	void updateDriversLicenseApprove(String employeeNumber, DriversLicense newDriversLicense);

	void insertEmergencyContactRequest(Boolean isAutoApprove, String employeeNumber, 
										EmergencyContact newEmergencyContact, EmergencyContact emergencyContact);
	void updateEmergencyContactRequest(Boolean isAutoApprove, String employeeNumber, 
										EmergencyContact newEmergencyContact, EmergencyContact emergencyContact);
	void updateEmergencyContactApprove(String employeeNumber, EmergencyContact newEmergencyContact);

	void insertEmailRequest(Boolean isAutoApprove, String employeeNumber, Email newEmail, Email email);
	void updateEmailRequest(Boolean isAutoApprove, String employeeNumber, Email newEmail, Email email);
	void updateEmailApprove(String employeeNumber, Email newEmail);
	HashMap<String, String> getApproverEmployeeNumbers();
	User getApproverById(String approverId);
	Email getPendingEmail(String employeeNumber);
	TraqsData getTraqsData();
	Boolean isTrsEmployee(String employeeNumber);
	String getEmployeeStaffId(String employeeNumber);
	List<TraqsData> getMD20EntriesForEmployee(String staffId);
	void updateMD20Record(String staffId, TraqsData oldEntry, Name newName);
	Boolean md25EntryExists(String staffId, TraqsData traqs);
	void updateMD25Record(String staffId, TraqsData traqs, Name oldName, Name newName);
	void createMD25Record(String staffId, TraqsData traqs, Name oldName, Name newName);
	Boolean md30EntryExists(String staffId, TraqsData traqs);
	void updateMD30Record(String staffId, TraqsData traqs, MailAddr newMailAddr, HomePhone newHomePhone);
	void createMD30Record(String staffId, TraqsData traqs, MailAddr newMailAddr, HomePhone newHomePhone);
	Email getCurrentEmail(String employeeNumber);
	//boolean updateTEAMRecords(Map<String, String> modifiedFields);
	List<Ed20Command> getEd20Records(String empNbr, String rptMon, String rptYr);
	List<Ed25Command> getEd25Records(String empNbr, String rptMon, String rptYr);
	boolean updEd20Recs(List<Ed20Command> ed20List);
	boolean insUpdEd25Recs(List<Ed25Command> ed25List);
}