package net.esc20.txeis.EmployeeAccess.conversion;

import org.springframework.binding.convert.service.DefaultConversionService;
import org.springframework.stereotype.Component;

@Component("conversionService")
public class EmployeeAccessConversionService extends DefaultConversionService
{
	@Override
	protected void addDefaultConverters()
	{
		super.addDefaultConverters();
		DisplayDate displayDateConverter = new DisplayDate();
		displayDateConverter.setPattern("MM-dd-yyyy");
		addConverter("displayDate", displayDateConverter);
		addConverter(new StringToBigDecimal());
		addConverter("displayFrequency", new StringToFrequency());
		addConverter("displayPayDate", new StringToPayDate());
		addConverter("displayMaritalStatus", new StringToMaritalStatus());
	}
}
