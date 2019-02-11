package net.esc20.txeis.EmployeeAccess.conversion;

import org.springframework.binding.convert.converters.StringToDate;

public class DisplayDate extends StringToDate
{
	public Object toObject(String string, Class targetClass) throws Exception
	{
		if(string.replaceAll("-", "").trim().length() == 0)
		{
			return null;
		}
		return super.toObject(string, targetClass);
	}
}
