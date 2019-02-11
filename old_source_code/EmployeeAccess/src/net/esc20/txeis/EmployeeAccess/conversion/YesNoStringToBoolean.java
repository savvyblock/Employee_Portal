package net.esc20.txeis.EmployeeAccess.conversion;

import net.esc20.txeis.EmployeeAccess.util.StringUtil;

import org.springframework.binding.convert.converters.StringToObject;

public class YesNoStringToBoolean extends StringToObject
{
	public YesNoStringToBoolean()
	{
		super(boolean.class);
	}

	protected Object toObject(String string, Class targetClass) throws Exception
	{
		return convertToBoolean(string);
	}

	public static boolean convertToBoolean(String string)
	{
		String clean = string.trim().toUpperCase();
		
		if(clean.equals("Y"))
		{
			return true;
		}
		else if(clean.equals("N") || StringUtil.isNullOrEmpty(clean))
		{
			return false;
		}
		
		throw new IllegalArgumentException("A boolean string equivalent must be 'Y' or 'N'. (" + clean + ")");
	}
	
	protected String toString(Object object) throws Exception
	{
		return convertToYesNoString((Boolean)object);
	}
	
	public static String convertToYesNoString(boolean b)
	{
		if(b)
		{
			return "Y";
		}
		else
		{
			return "N";
		}
	}
}
