package net.esc20.txeis.EmployeeAccess.util;

public class BmiCalculator
{
	public static double calculateBmiImperial(double height, double weight)
	{
		return calculateBmiMetric(height, weight * 703);
	}
	
	public static double calculateBmiMetric(double height, double mass)
	{
		return mass / (height * height);
	}
}