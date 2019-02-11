package net.esc20.txeis.EmployeeAccess.sql;

import java.util.List;
import java.util.Vector;

public class Table extends Query
{
	protected String name;
	
	public Table(String name)
	{
		this.name = name;
	}
	
	public String getName()
	{
		return name;
	}
	
	protected String getToken(Field field)
	{
		if(this.equals(field.getQuery()))
		{
			return field.getName();
		}
		
		throw new IllegalArgumentException("This table is not the field's defined query.");
	}
	
	protected List<Query> getQueryPath(Field field)
	{
		if(this.equals(field.getQuery()))
		{
			List<Query> path = new Vector<Query>();
			path.add(this);
			return path;
		}
		
		throw new IllegalArgumentException("This table is not the field's defined query.");
	}
	
	public String getStatement()
	{
		return getName();
	}
}