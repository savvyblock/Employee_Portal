package net.esc20.txeis.EmployeeAccess.domainobject.SelfService.Demo;

import java.io.Serializable;


public class CellPhone extends PhoneNumber implements Serializable
{
	private static final long serialVersionUID = -4293607069850390057L;
	
	@Override
	public CellPhone clone()
	{
		CellPhone newCellPhone = new CellPhone();
		newCellPhone.setAreaCode(getAreaCode());
		newCellPhone.setPhoneNumber(getPhoneNumber());
		return newCellPhone;
	}
}