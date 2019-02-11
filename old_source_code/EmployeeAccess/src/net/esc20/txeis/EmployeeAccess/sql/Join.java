package net.esc20.txeis.EmployeeAccess.sql;

import net.esc20.txeis.EmployeeAccess.util.StringUtil;

public class Join extends Select
{
	public static class Type extends net.esc20.txeis.EmployeeAccess.util.Type
	{
		public static final Type Inner = new Type("INNER JOIN");
		public static final Type Natural = new Type("NATURAL JOIN");
		public static final Type Cross = new Type("CROSS JOIN");
		public static final Type Left = new Type("LEFT OUTER JOIN");
		public static final Type Right = new Type("RIGHT OUTER JOIN");
		public static final Type Full = new Type("FULL OUTER JOIN");
		
		private Type(String value)
		{
			super(value);
		}
	}
	
	protected Type type;
	
	public Join(Query queryL, Query queryR)
	{
		this(queryL, queryR, Type.Inner);
	}
	
	public Join(Query queryL, Query queryR, Type type)
	{
		addQuery(queryL);
		addQuery(queryR);
		this.type = type;
	}
	
	public void addQuery(Query query)
	{
		if(queries.size() == 2)
		{
			throw new IllegalArgumentException("A join can only have two queries.");
		}
		
		super.addQuery(query);
	}
	
	protected String getQueriesClause()
	{
		String statement = "FROM ";
		
		if(queries.size() > 1)
		{
			statement += queries.get(0) + " AS " + getToken(queries.get(0));
			statement += " " + type + " ";
			statement += queries.get(1) + " AS " + getToken(queries.get(1));
		}
		else
		{
			throw new IllegalStateException("This join has fewer than two queries.");
		}
		
		return statement;
	}
	
	protected String getConditionsClause()
	{
		String statement = "";
		
		if(conditions.size() > 0)
		{
			statement += "ON ";
			
			for(Condition condition : conditions)
			{
				Field arg1 = condition.getArgument1();
				statement += getRelativePath(arg1) + " " + condition.getOperator().getValue();
				
				if(condition.getOperator() instanceof Condition.BinaryOperator)
				{
					if(!StringUtil.isNullOrEmpty(condition.getValue()))
					{
						statement += " '" + condition.getValue() + "'";
					}
					else if(condition.getArgument2() != null && 
							!StringUtil.isNullOrEmpty(condition.getArgument2().getName()))
					{
						statement += " " + getRelativePath(condition.getArgument2());
					}
				}
				
				statement += " AND ";
			}
			
			statement = statement.substring(0,statement.length()-5);
		}
		
		return statement;
	}
}