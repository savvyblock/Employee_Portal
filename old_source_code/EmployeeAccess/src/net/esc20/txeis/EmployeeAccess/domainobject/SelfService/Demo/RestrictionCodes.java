package net.esc20.txeis.EmployeeAccess.domainobject.SelfService.Demo;

import java.io.Serializable;

import net.esc20.txeis.EmployeeAccess.domainobject.reference.Code;
import net.esc20.txeis.EmployeeAccess.service.ReferenceDataService;

public class RestrictionCodes extends DemoDomainObject implements Serializable
{
	private static final long serialVersionUID = -4293607069850390057L;
	
	private Code localRestriction;
	private Code publicRestriction;
	
	@Override
	public RestrictionCodes clone()
	{
		RestrictionCodes newRestrictionCodes = new RestrictionCodes();
		newRestrictionCodes.setLocalRestriction((Code)localRestriction.clone());
		newRestrictionCodes.setPublicRestriction((Code)publicRestriction.clone());
		return newRestrictionCodes;
	}

	@Override
	public boolean equals(Object obj) 
	{
		if(obj != null && (obj.getClass().equals(this.getClass())))
		{
			RestrictionCodes temp = (RestrictionCodes)obj;
			return((localRestriction.equals(temp.localRestriction)) && 
				   (publicRestriction.equals(temp.publicRestriction)));
		}
		return false;
	}
	
	@Override
	public int hashCode() 
	{
		StringBuilder sb = new StringBuilder();
		sb.append(localRestriction.getCode());
		sb.append(publicRestriction.getCode());
		return sb.toString().hashCode();
	}
	
	
	public void setLocalRestriction(Code localRestriction) {
		this.localRestriction = localRestriction;
	}
	
	public Code getLocalRestriction() {
		return localRestriction;
	}

	public void setLocalRestrictionCode(String code)
	{
		this.localRestriction = ReferenceDataService.getRestrictionFromCode(code);
	}
	
	public String getLocalRestrictionCode()
	{
		return localRestriction.getCode();
	}
	
	public String getLocalRestrictionString()
	{
		return localRestriction.getDisplayLabel();
	}
	
	public void setLocalRestrictionString(String status)
	{
		localRestriction = ReferenceDataService.getRestrictionFromDisplayLabel(status);
	}
	
	public void setPublicRestriction(Code publicRestriction) {
		this.publicRestriction = publicRestriction;
	}
	
	public Code getPublicRestriction() {
		return publicRestriction;
	}

	public void setPublicRestrictionCode(String code)
	{
		this.publicRestriction = ReferenceDataService.getRestrictionFromCode(code);
	}
	
	public String getPublicRestrictionCode()
	{
		return publicRestriction.getCode();
	}
	
	public String getPublicRestrictionString()
	{
		return publicRestriction.getDisplayLabel();
	}
	
	public void setPublicRestrictionString(String status)
	{
		publicRestriction = ReferenceDataService.getRestrictionFromDisplayLabel(status);
	}
	
}