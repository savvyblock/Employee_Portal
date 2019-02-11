package net.esc20.txeis.EmployeeAccess.domainobject.SelfService.Demo;

import java.io.Serializable;

import net.esc20.txeis.EmployeeAccess.domainobject.reference.Code;
import net.esc20.txeis.EmployeeAccess.service.ReferenceDataService;

public class DriversLicense extends DemoDomainObject implements Serializable
{
	private static final long serialVersionUID = -4293607069850390057L;
	
	private String number;
	private Code state;
	
	@Override
	public DriversLicense clone()
	{
		DriversLicense newDriversLicense = new DriversLicense();
		newDriversLicense.setNumber(number);
		newDriversLicense.setState((Code)state.clone());
		return newDriversLicense;
	}

	@Override
	public boolean equals(Object obj) 
	{
		if(obj != null && (obj.getClass().equals(this.getClass())))
		{
			DriversLicense temp = (DriversLicense)obj;
			return((number.equals(temp.number)) && 
				   (state.equals(temp.state)));
		}
		return false;
	}
	
	@Override
	public int hashCode() 
	{
		StringBuilder sb = new StringBuilder();
		sb.append(number);
		sb.append(state);
		return sb.toString().hashCode();
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getNumber() {
		return number;
	}

	public void setState(Code state) 
	{
		this.state = state;
	}

	public Code getState() {
		return state;
	}

	public void setStateCode(String code)
	{
		this.state = ReferenceDataService.getStateFromCode(code);
	}
	
	public String getStateCode()
	{
		return state.getCode();
	}
	
	public String getStateString()
	{
		return state.getDisplayLabel();
	}
	
	public void setStateString(String status)
	{
		state = ReferenceDataService.getStateFromDisplayLabel(status);
	}
}