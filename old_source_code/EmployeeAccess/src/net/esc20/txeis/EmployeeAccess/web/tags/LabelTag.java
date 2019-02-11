package net.esc20.txeis.EmployeeAccess.web.tags;

import net.esc20.txeis.EmployeeAccess.conversion.EmployeeAccessConversionService;

import org.springframework.binding.convert.ConversionService;

public class LabelTag extends org.apache.taglibs.standard.tag.rt.core.OutTag
{
	private static final long serialVersionUID = -1293900139482572047L;

	private ConversionService conversionService;
	
	public LabelTag()
	{
		conversionService = new EmployeeAccessConversionService();
	}
	
	@Override
	public void setValue(Object value)
	{
		if(value != null)
		{
			super.setValue(conversionService.getConversionExecutor(value.getClass(), String.class).execute(value));
		}
	}
}