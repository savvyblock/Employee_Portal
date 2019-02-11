package com.esc20.nonDBModels;

public enum Frequency
{
	Biweekly, //jlf 20111104 enum must match string in case due to Webflow building dynamic java code
	Semimonthly, //jlf 20111104
	Monthly;
	
	public String getCode()
	{
		if(this.equals(Biweekly)) //jlf 20111104
		{
			return "4";
		}
		else if(this.equals(Semimonthly)) //jlf 20111104
		{
			return "5";
		}
		else if(this.equals(Monthly))
		{
			return "6";
		}
		else
		{
			throw new IllegalArgumentException("Invalid frequency.");
		}
	}
	
	public static Frequency getFrequency(String code)
	{
		if("4".equals(code) || "Biweekly".equals(code))
		{
			return Biweekly; //jlf 20111104
		}
		else if("5".equals(code) || "Semimonthly".equals(code)) //jlf 20111104
		{
			return Semimonthly; //jlf 20111104
		}
		else if("6".equals(code) || "Monthly".equals(code))
		{
			return Monthly;
		}
		else
		{
			throw new IllegalArgumentException("Invalid frequency code.");
		}
	}
	
	public String getLabel()
	{
		if(this.equals(Biweekly)) //jlf 20111104
		{
			return "Biweekly";
		}
		else if(this.equals(Semimonthly)) //jlf 20111104
		{
			return "Semimonthly"; //jlf 20111104
		}
		else if(this.equals(Monthly))
		{
			return "Monthly";
		}
		else
		{
			throw new IllegalArgumentException("Invalid frequency.");
		}
	}
}