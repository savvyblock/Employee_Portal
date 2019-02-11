package net.esc20.txeis.EmployeeAccess.util;

import java.util.Collection;

import net.esc20.txeis.EmployeeAccess.domainobject.reference.ICode;

public class CodeMapper
{
	public static String getDescription(Collection<? extends ICode> types, String code)
	{
		if (code != null) 
		{
			for (ICode type : types) 
			{
				if (type.getCode().equals(code.trim())) 
				{
					return type.getDescription();
				}
			}
		}
		
		// NOTE: if the code is invalid, simply display it
		// as the description
		return code;
		//throw new LoggedUserException(String.format("The specified code was not found in the collection: %s", code),null);
	}
	
	public static String getCode(Collection<? extends ICode> types, String description)
	{
		for(ICode type : types)
		{
			if(type.getDescription().equals(description))
			{
				return type.getCode();
			}
		}
		
		throw new IllegalArgumentException(String.format("The specified description was not found in the collection: %s", description),null);
	}
}
