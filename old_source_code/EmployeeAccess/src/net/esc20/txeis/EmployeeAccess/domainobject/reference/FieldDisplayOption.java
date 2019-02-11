package net.esc20.txeis.EmployeeAccess.domainobject.reference;

public enum FieldDisplayOption
{
	Update,
	Inquiry,
	None;
	
	public static FieldDisplayOption getFieldDisplayOption(String code)
	{
		if("U".equals(code))
		{
			return Update;
		}
		else if("I".equals(code))
		{
			return Inquiry;
		}
		else if("N".equals(code))
		{
			return None;
		}
		else
		{
			throw new IllegalArgumentException("Invalid Field Display Option.");
		}
	}
	
	public String getCode()
	{
		if(this.equals(Update))
		{
			return "U";
		}
		else if(this.equals(Inquiry))
		{
			return "I";
		}
		else if(this.equals(None))
		{
			return "N";
		}
		else
		{
			throw new IllegalStateException("Invalid Field Display Option.");
		}
	}
	
	public String getLabel()
	{
		if(this.equals(Update))
		{
			return "U Update";
		}
		else if(this.equals(Inquiry))
		{
			return "I Inquiry";
		}
		else if(this.equals(None))
		{
			return "N None";
		}
		else
		{
			throw new IllegalStateException("Invalid Field Display Option.");
		}
	}
}