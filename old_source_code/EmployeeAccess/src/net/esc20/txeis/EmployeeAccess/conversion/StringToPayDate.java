package net.esc20.txeis.EmployeeAccess.conversion;
import net.esc20.txeis.EmployeeAccess.domainobject.PayDate;

import org.springframework.binding.convert.converters.TwoWayConverter;

public class StringToPayDate implements TwoWayConverter
{

	@Override
	public Object convertTargetToSourceClass(Object arg0, Class arg1)
			throws Exception {
		if(arg0 == null) return "";
		
		return ((PayDate)arg0).getDateFreqVoidAdjChk();
	}

	@Override
	public Object convertSourceToTargetClass(Object arg0, Class arg1)
			throws Exception {
		return PayDate.getPaydate((String)arg0);
	}

	@Override
	public Class getSourceClass() {
		return String.class;
	}

	@Override
	public Class getTargetClass() {
		return PayDate.class;
	}
	
}