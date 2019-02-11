package net.esc20.txeis.EmployeeAccess.domainobject.SelfService.Demo;

import java.io.Serializable;


public abstract class PhoneNumber extends DemoDomainObject implements Serializable
{
	private static final long serialVersionUID = -4293607069850390057L;
	
	protected String areaCode;
	protected String phoneNumber;
	
	public PhoneNumber()
	{
	}
	
	public PhoneNumber(String areaCode, String phoneNumber)
	{
		this.areaCode = areaCode;
		this.phoneNumber = phoneNumber;
	}
	
	@Override
	public boolean equals(Object obj) 
	{
		if(obj != null && (obj.getClass().equals(this.getClass())))
		{
			PhoneNumber temp = (PhoneNumber)obj;
			return((areaCode.equals(temp.areaCode)) && 
				   (phoneNumber.equals(temp.phoneNumber)));
		}
		return false;
	}
	
	@Override
	public int hashCode() 
	{
		StringBuilder sb = new StringBuilder();
		sb.append(areaCode);
		sb.append(phoneNumber);
		return sb.toString().hashCode();
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public String getPhoneNumberFormatted()
	{
		if(phoneNumber.length() < 7)
			return phoneNumber;
		return String.format("%s-%s", phoneNumber.substring(0, 3), phoneNumber.substring(3));
	}
	
	public void setPhoneNumberFormatted(String phoneNumberFormatted)
	{
		phoneNumber = phoneNumberFormatted.replace("-", "");
	}
}