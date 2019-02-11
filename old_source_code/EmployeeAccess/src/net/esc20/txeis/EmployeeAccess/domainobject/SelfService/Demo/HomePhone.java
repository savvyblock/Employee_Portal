package net.esc20.txeis.EmployeeAccess.domainobject.SelfService.Demo;

import java.io.Serializable;

public class HomePhone extends PhoneNumber implements Serializable
{
	private static final long serialVersionUID = -4293607069850390057L;
	
	@Override
	public HomePhone clone()
	{
		HomePhone newHomePhone = new HomePhone();
		newHomePhone.setAreaCode(getAreaCode());
		newHomePhone.setPhoneNumber(getPhoneNumber());
		return newHomePhone;
	}
}