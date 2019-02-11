package net.esc20.txeis.EmployeeAccess.conversion;

import net.esc20.txeis.EmployeeAccess.domainobject.reference.MaritalStatus;

import org.springframework.binding.convert.converters.TwoWayConverter;

public class StringToMaritalStatus implements TwoWayConverter
{

	@Override
	public Object convertTargetToSourceClass(Object arg0, Class arg1)
			throws Exception {
		if(arg0 == null)
		{
			return "";
		}
		return ((MaritalStatus)arg0).getCode();
	}

	@Override
	public Object convertSourceToTargetClass(Object arg0, Class arg1)
			throws Exception {
		return MaritalStatus.getMaritalStatusFromCode((String)arg0);
	}

	@Override
	public Class getSourceClass() {
		return String.class;
	}

	@Override
	public Class getTargetClass() {
		return MaritalStatus.class;
	}
	
}