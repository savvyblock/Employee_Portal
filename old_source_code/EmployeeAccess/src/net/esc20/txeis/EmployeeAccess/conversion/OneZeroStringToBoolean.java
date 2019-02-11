package net.esc20.txeis.EmployeeAccess.conversion;

import net.esc20.txeis.EmployeeAccess.util.StringUtil;

import org.springframework.binding.convert.converters.StringToObject;

public class OneZeroStringToBoolean extends StringToObject
{
	public OneZeroStringToBoolean()
	{
		super(boolean.class);
	}

	protected Object toObject(String string, Class targetClass) throws Exception
	{
		return convertToBoolean(string);
	}

	public static boolean convertToBoolean(String string)
	{
		String clean = StringUtil.upper(StringUtil.trim(string));
		
		if(clean.equals("1"))
		{
			return true;
		}
		else if(clean.equals("0") || StringUtil.isNullOrEmpty(clean))
		{
			return false;
		}
		
		throw new IllegalArgumentException("A boolean string equivalent must be '1' or '0'. (" + clean + ")");
	}
	
	protected String toString(Object object) throws Exception
	{
		return convertToOneZeroString((Boolean)object);
	}
	
	public static String convertToOneZeroString(boolean b)
	{
		if(b)
		{
			return "1";
		}
		else
		{
			return "0";
		}
	}
}
