package net.esc20.txeis.EmployeeAccess.sql;

import java.util.List;

public abstract class Query extends Statement
{
	public static class Field
	{
		protected Query query;
		protected String name;
		
		public Field(Query query, String name)
		{
			this.query = query;
			this.name = name;
		}
		
		public Query getQuery()
		{
			return query;
		}
		
		public String getName()
		{
			return name;
		}
		
		public boolean equals(Object o)
		{
			if(o instanceof Field)
			{
				Field f = (Field)o;
				return getName().equals(f.getName());
			}
			
			return false;
		}
		
		public String toString()
		{
			return getName();
		}
	}
	
	protected abstract String getToken(Field field);
	protected abstract List<Query> getQueryPath(Field field);
}