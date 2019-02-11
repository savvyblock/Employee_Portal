package net.esc20.txeis.EmployeeAccess.domainobject.SelfService.Demo;

import java.io.Serializable;

public class DemoInfo implements Serializable, Cloneable
{
	private static final long serialVersionUID = -4293607069850390057L;
	
	private Name name;
	private MailAddr mailAddr;
	private AltAddr altAddr;
	private HomePhone homePhone;
	private WorkPhone workPhone;
	private CellPhone cellPhone;
	private Email email;
	private RestrictionCodes restrictionCodes;
	private Marital marital;
	private DriversLicense driversLicense;
	private EmergencyContact emergencyContact;
	private final String employeeNumber;
	private final String sex;
	private final String dob;
	private final String staffId;
	
	public DemoInfo(String employeeNumber, String sex, String dob, String staffId)
	{
		this.employeeNumber = employeeNumber;
		this.sex = sex;
		this.dob = dob;
		this.staffId = staffId;
	}
	
	public String getEmployeeNumber() {
		return employeeNumber;
	}
	
	public String getSex() {
		return sex;
	}
	
	public String getDob() {
		return dob;
	}
	
	public String getStaffId() {
		return staffId;
	}
	
	
	public Name getName() {
		return name;
	}
	public void setName(Name name) {
		this.name = name;
	}
	
	public MailAddr getMailAddr() {
		return mailAddr;
	}
	public void setMailAddr(MailAddr mailAddr) {
		this.mailAddr = mailAddr;
	}
	
	public AltAddr getAltAddr() {
		return altAddr;
	}
	public void setAltAddr(AltAddr altAddr) {
		this.altAddr = altAddr;
	}
	
	public HomePhone getHomePhone() {
		return homePhone;
	}
	public void setHomePhone(HomePhone homePhone) {
		this.homePhone = homePhone;
	}
	
	public WorkPhone getWorkPhone() {
		return workPhone;
	}
	public void setWorkPhone(WorkPhone workPhone) {
		this.workPhone = workPhone;
	}
	
	public CellPhone getCellPhone() {
		return cellPhone;
	}
	public void setCellPhone(CellPhone cellPhone) {
		this.cellPhone = cellPhone;
	}
	
	public Email getEmail() {
		return email;
	}
	public void setEmail(Email email) {
		this.email = email;
	}
	
	public RestrictionCodes getRestrictionCodes() {
		return restrictionCodes;
	}
	public void setRestrictionCodes(RestrictionCodes restrictionCodes) {
		this.restrictionCodes = restrictionCodes;
	}
	
	public DriversLicense getDriversLicense() {
		return driversLicense;
	}
	public void setDriversLicense(DriversLicense driversLicense) {
		this.driversLicense = driversLicense;
	}
	
	public EmergencyContact getEmergencyContact() {
		return emergencyContact;
	}
	public void setEmergencyContact(EmergencyContact emergencyContact) {
		this.emergencyContact = emergencyContact;
	}
	
	public Marital getMarital(){
		return marital;
	}
	
	public void setMarital(Marital marital){
		this.marital = marital;
	}
	
	@Override
	public DemoInfo clone()
	{
		DemoInfo demoInfo = new DemoInfo(employeeNumber, sex, dob, staffId);
		demoInfo.name = name.clone();
		demoInfo.mailAddr = mailAddr.clone();
		demoInfo.altAddr = altAddr.clone();
		demoInfo.homePhone = homePhone.clone();
		demoInfo.workPhone = workPhone.clone();
		demoInfo.cellPhone = cellPhone.clone();
		demoInfo.email = email.clone();
		demoInfo.restrictionCodes = restrictionCodes.clone();
		demoInfo.marital = marital.clone();
		demoInfo.driversLicense = driversLicense.clone();
		demoInfo.emergencyContact = emergencyContact.clone();;
		
		return demoInfo;
	}
	
	@Override
	public boolean equals(Object obj) 
	{
		if(obj != null && (obj.getClass().equals(this.getClass())))
		{
			DemoInfo temp = (DemoInfo)obj;
			return(temp != null &&
				   (employeeNumber.equals(temp.employeeNumber)) &&
				   (name.equals(temp.name)) && 
				   (mailAddr.equals(temp.mailAddr)) &&
				   (altAddr.equals(temp.altAddr)) && 
				   (homePhone.equals(temp.homePhone)) &&
				   (workPhone.equals(temp.workPhone)) &&
				   (cellPhone.equals(temp.cellPhone)) && 
				   (email.equals(temp.email)) &&
				   (marital.equals(temp.marital)) &&
				   (driversLicense.equals(temp.driversLicense)) && 
				   (emergencyContact.equals(temp.emergencyContact)) && 
				   (restrictionCodes.equals(temp.restrictionCodes)) &&
				   (sex.equals(temp.sex)) &&
				   (dob.equals(temp.dob)) &&
				   (staffId.equals(temp.staffId)));
		}
		return false;
	}
	
	@Override
	public int hashCode() 
	{
		return (employeeNumber.hashCode() ^
				name.hashCode() ^ 
				mailAddr.hashCode() ^ 
				altAddr.hashCode() ^
				homePhone.hashCode() ^
				workPhone.hashCode() ^
				cellPhone.hashCode() ^
				email.hashCode() ^
				marital.hashCode() ^
				driversLicense.hashCode() ^
				emergencyContact.hashCode() ^
				restrictionCodes.hashCode() ^
				sex.hashCode() ^
				dob.hashCode() ^
				staffId.hashCode());
	}
	
}