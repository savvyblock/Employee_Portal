package net.esc20.txeis.EmployeeAccess.domainobject.SelfService.Demo;

import java.io.Serializable;

public class WorkPhone extends PhoneNumber implements Serializable
{
	private static final long serialVersionUID = -4293607069850390057L;
	
	private String extention;
	
	@Override
	public WorkPhone clone()
	{
		WorkPhone newWorkPhone = new WorkPhone();
		newWorkPhone.setAreaCode(areaCode);
		newWorkPhone.setPhoneNumber(phoneNumber);
		newWorkPhone.setExtention(getExtention());
		return newWorkPhone;
	}

	@Override
	public boolean equals(Object obj) 
	{
		if(obj != null && (obj.getClass().equals(this.getClass())))
		{
			WorkPhone temp = (WorkPhone)obj;
			return((areaCode.equals(temp.areaCode)) && 
				   (phoneNumber.equals(temp.phoneNumber)) &&
				   (extention.equals(temp.extention)));
		}
		return false;
	}
	
	@Override
	public int hashCode() 
	{
		StringBuilder sb = new StringBuilder();
		sb.append(areaCode);
		sb.append(phoneNumber);
		sb.append(extention);
		return sb.toString().hashCode();
	}

	public void setExtention(String extention) {
		this.extention = extention;
	}

	public String getExtention() {
		return extention;
	}
}