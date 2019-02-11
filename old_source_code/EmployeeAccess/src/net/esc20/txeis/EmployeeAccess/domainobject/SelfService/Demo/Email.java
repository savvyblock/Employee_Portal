package net.esc20.txeis.EmployeeAccess.domainobject.SelfService.Demo;

import java.io.Serializable;

public class Email extends DemoDomainObject implements Serializable
{
	private static final long serialVersionUID = -4293607069850390057L;
	
	private String workEmail;
	private String homeEmail;
	
	private String workEmailVerify="";
	private String homeEmailVerify="";
	
	@Override
	public Email clone()
	{
		Email newEmail = new Email();
		newEmail.setWorkEmail(workEmail);
		newEmail.setHomeEmail(homeEmail);
		newEmail.setWorkEmailVerify(workEmailVerify);
		newEmail.setHomeEmailVerify(homeEmailVerify);
		return newEmail;
	}
	
	@Override
	public boolean equals(Object obj) 
	{
		if(obj != null && (obj.getClass().equals(this.getClass())))
		{
			Email temp = (Email)obj;
			return((workEmail.equals(temp.workEmail)) && 
				   (homeEmail.equals(temp.homeEmail)) &&
				   (workEmailVerify.equals(temp.workEmailVerify)) && 
				   (homeEmailVerify.equals(temp.homeEmailVerify)));
		}
		return false;
	}
	
	@Override
	public int hashCode() 
	{
		StringBuilder sb = new StringBuilder();
		sb.append(workEmail);
		sb.append(workEmailVerify);
		sb.append(homeEmail);
		sb.append(homeEmailVerify);
		return sb.toString().hashCode();
	}
	
	public void setWorkEmail(String workEmail) {
		this.workEmail = workEmail;
	}
	public String getWorkEmail() {
		return workEmail;
	}
	public void setHomeEmail(String homeEmail) {
		this.homeEmail = homeEmail;
	}
	public String getHomeEmail() {
		return homeEmail;
	}

	public String getWorkEmailVerify() {
		return workEmailVerify;
	}

	public void setWorkEmailVerify(String workEmailVerify) {
		this.workEmailVerify = workEmailVerify;
	}

	public String getHomeEmailVerify() {
		return homeEmailVerify;
	}

	public void setHomeEmailVerify(String homeEmailVerify) {
		this.homeEmailVerify = homeEmailVerify;
	}
	
	
}