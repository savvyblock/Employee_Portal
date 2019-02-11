package net.esc20.txeis.EmployeeAccess.sql;

public abstract class Statement
{
	public abstract String getStatement();
	
	public String toString()
	{
		return getStatement();
	}
}