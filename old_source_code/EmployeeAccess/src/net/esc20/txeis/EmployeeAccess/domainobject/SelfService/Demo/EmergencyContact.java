package net.esc20.txeis.EmployeeAccess.domainobject.SelfService.Demo;

import java.io.Serializable;

public class EmergencyContact extends PhoneNumber implements Serializable
{
	private static final long serialVersionUID = -4293607069850390057L;
	
	private String name;
	private String extention;
	private String relationship;
	private String emergencyNotes;
		
	@Override
	public EmergencyContact clone()
	{
		EmergencyContact newEmergencyContact = new EmergencyContact();
		newEmergencyContact.setName(name);
		newEmergencyContact.setAreaCode(areaCode);
		newEmergencyContact.setPhoneNumber(phoneNumber);
		newEmergencyContact.setExtention(extention);
		newEmergencyContact.setRelationship(relationship);
		newEmergencyContact.setEmergencyNotes(emergencyNotes);
		
		return newEmergencyContact;
	}
	
	@Override
	public boolean equals(Object obj) 
	{
		if(obj != null && (obj.getClass().equals(this.getClass())))
		{
			EmergencyContact temp = (EmergencyContact)obj;
			return((name.equals(temp.name)) && 
				   (areaCode.equals(temp.areaCode)) &&
				   (phoneNumber.equals(temp.phoneNumber)) && 
				   (extention.equals(temp.extention)) && 
				   (relationship.equals(temp.relationship)) && 
				   (emergencyNotes.equals(temp.emergencyNotes)));
		}
		return false;
	}
	
	@Override
	public int hashCode() 
	{
		StringBuilder sb = new StringBuilder();
		sb.append(name);
		sb.append(areaCode);
		sb.append(phoneNumber);
		sb.append(extention);
		sb.append(relationship);
		sb.append(emergencyNotes);
		return sb.toString().hashCode();
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setExtention(String extention) {
		this.extention = extention;
	}

	public String getExtention() {
		return extention;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}

	public String getRelationship() {
		return relationship;
	}

	public void setEmergencyNotes(String emergencyNotes) {
		this.emergencyNotes = emergencyNotes;
	}

	public String getEmergencyNotes() {
		return emergencyNotes;
	}
	
}