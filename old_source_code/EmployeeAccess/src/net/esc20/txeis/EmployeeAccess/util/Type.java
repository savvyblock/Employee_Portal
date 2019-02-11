package net.esc20.txeis.EmployeeAccess.util;

public abstract class Type
{
	private String value;
	
	protected Type(String value)
	{
		this.value = value;
	}
	
	public String getValue()
	{
		return value;
	}
	
	public String toString()
	{
		return getValue();
	}
}