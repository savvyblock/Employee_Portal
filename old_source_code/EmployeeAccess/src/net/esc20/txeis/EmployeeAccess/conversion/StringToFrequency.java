package net.esc20.txeis.EmployeeAccess.conversion;

import net.esc20.txeis.EmployeeAccess.domainobject.reference.Frequency;

import org.springframework.binding.convert.converters.TwoWayConverter;

public class StringToFrequency implements TwoWayConverter
{

	@Override
	public Object convertTargetToSourceClass(Object arg0, Class arg1)
			throws Exception {
		if(arg0 == null)
		{
			return "";
		}
		return ((Frequency)arg0).getCode();
	}

	@Override
	public Object convertSourceToTargetClass(Object arg0, Class arg1)
			throws Exception {
		return Frequency.getFrequency((String)arg0);
	}

	@Override
	public Class getSourceClass() {
		return String.class;
	}

	@Override
	public Class getTargetClass() {
		return Frequency.class;
	}
	
}