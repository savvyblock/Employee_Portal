package net.esc20.txeis.EmployeeAccess.conversion;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;

import net.esc20.txeis.EmployeeAccess.util.StringUtil;

public class StringToBigDecimal extends org.springframework.binding.convert.converters.StringToBigDecimal
{
	private DecimalFormat f;
	
	public StringToBigDecimal()
	{
		f = new DecimalFormat();
		f.setMinimumFractionDigits(2);
		f.setGroupingSize(3);
	}
	
	public Object toObject(String string, Class targetClass) throws Exception
	{
		return convertStringToBigDecimal(string);
	}

	public BigDecimal convertStringToBigDecimal(String string) throws Exception
	{
		String clean = StringUtil.trim(string);
		
		if(clean.length() <= 1)
		{
			return new BigDecimal(BigInteger.ZERO,2);
		}
		
		return new BigDecimal(clean);
	}
	
	public String toString(Object object) throws Exception
	{
		return convertBigDecimalToString((BigDecimal)object);
	}
	
	public String convertBigDecimalToString(BigDecimal b)
	{
		return f.format(b);
	}
}
