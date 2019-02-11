package net.esc20.txeis.EmployeeAccess.domainobject.reference;

public enum MaritalStatus
{
	Single,
	Married;

	private static String MARRIED_LABEL = "M - Married";
	private static String SINGLE_LABEL = "S - Single";
	private static String MARRIED_CODE = "M";
	private static String SINGLE_CODE ="S";
	
	public static MaritalStatus getMaritalStatusFromCode(String code)
	{
		if(code.equalsIgnoreCase(SINGLE_CODE))
		{
			return Single;
		}
		else if(code.equalsIgnoreCase(MARRIED_CODE))
		{
			return Married;
		}
		else
		{
			throw new IllegalArgumentException("Invalid marital status");
		}
	}
	
	public String getCode()
	{
		if(this.equals(Single))
		{
			return SINGLE_CODE;
		}
		else if(this.equals(Married))
		{
			return MARRIED_CODE;
		}
		else
		{
			throw new IllegalStateException("Invalid marital status");
		}
	}
	
	public String getLabel()
	{
		if(this.equals(Single))
		{
			return SINGLE_LABEL;
		}
		else if(this.equals(Married))
		{
			return MARRIED_LABEL;
		}
		else
		{
			throw new IllegalArgumentException("Invalid marital status.");
		}
	}
	
	public static MaritalStatus getMaritalStatusFromLabel(String label)
	{
		if(label.equalsIgnoreCase(SINGLE_LABEL))
		{
			return Single;
		}
		else if(label.equalsIgnoreCase(MARRIED_LABEL))
		{
			return Married;
		}
		else
		{
			throw new IllegalArgumentException("Invalid marital status");
		}
	}
}