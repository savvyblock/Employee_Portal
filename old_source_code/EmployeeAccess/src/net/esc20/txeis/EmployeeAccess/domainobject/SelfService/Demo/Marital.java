package net.esc20.txeis.EmployeeAccess.domainobject.SelfService.Demo;

import java.io.Serializable;

import net.esc20.txeis.EmployeeAccess.domainobject.reference.Code;
import net.esc20.txeis.EmployeeAccess.service.ReferenceDataService;

public class Marital extends DemoDomainObject implements Serializable
{
	private static final long serialVersionUID = -4293607069850390057L;
	
	private Code maritalStatus;

	@Override
	public Marital clone()
	{
		Marital newMarital = new Marital();
		newMarital.setMaritalStatus((Code)maritalStatus.clone());
		return newMarital;
	}

	@Override
	public boolean equals(Object obj) 
	{
		if(obj != null && (obj.getClass().equals(this.getClass())))
		{
			Marital temp = (Marital)obj;
			return((maritalStatus.equals(temp.maritalStatus)));
		}
		return false;
	}
	
	@Override
	public int hashCode() 
	{
		StringBuilder sb = new StringBuilder();
		sb.append(maritalStatus.getCode());
		return sb.toString().hashCode();
	}
		
	public void setMaritalStatus(Code maritalStatus) 
	{
		this.maritalStatus = maritalStatus;
	}

	public Code getMaritalStatus() 
	{
		return maritalStatus;
	}
	
	public String getMaritalStatusCode()
	{
		return maritalStatus.getCode();
	}
	
	public void setMaritalStatusCode(String code)
	{
		this.maritalStatus = ReferenceDataService.getMaritalActualStatusFromCode(code);
	}
	
	public String getMaritalStatusString()
	{
		return maritalStatus.getDisplayLabel();
	}
	
	public void setMaritalStatusString(String status)
	{
		maritalStatus = ReferenceDataService.getMaritalActualStatusFromDisplayLabel(status);
	}
	
}