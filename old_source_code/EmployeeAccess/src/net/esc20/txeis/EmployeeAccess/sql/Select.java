package net.esc20.txeis.EmployeeAccess.sql;

import java.util.List;
import java.util.Vector;

import net.esc20.txeis.EmployeeAccess.util.StringUtil;
import net.esc20.txeis.EmployeeAccess.util.Type;

public class Select extends Query
{	
	public static class Order extends Type
	{
		public static final Order Ascending = new Order("ASC");
		public static final Order Descending = new Order("DESC");
		
		private Order(String value)
		{
			super(value);
		}
	}
	
	protected List<Field> fields;
	protected List<Query> queries;
	protected List<Condition> conditions;
	protected Order order;
	
	public Select()
	{
		fields = new Vector<Field>();
		queries = new Vector<Query>();
		conditions = new Vector<Condition>();
	}
	
	public void addQuery(Query query)
	{
		queries.add(query);
	}
	
	public void addField(Field field)
	{
		for(Query query : getQueryPath(field))
		{
			if(query instanceof Select)
			{
				((Select)query).addFieldToList(field);
			}
		}
	}
	
	protected void addFieldToList(Field field)
	{
		if(!fields.contains(field))
		{
			fields.add(field);
		}
	}
	
	protected List<Query> getQueryPath(Field field)
	{
		List<Query> path = new Vector<Query>();
		path.add(this);
		
		if(this.equals(field.getQuery()))
		{
			return path;
		}
		else
		{
			for(Query query : queries)
			{
				try
				{
					List<Query> append = query.getQueryPath(field);
					path.addAll(append);
					return path;
				}
				catch(Exception ex)
				{}
			}
		}
		
		throw new IllegalArgumentException("The select statement does not contain this field.");
	}
	
	protected String getToken(Query query)
	{
		if(queries.contains(query))
		{
			return "Q" + queries.indexOf(query);
			
		}
		
		throw new IllegalArgumentException("The select statement does not contain this query.");
	}
	
	protected String getToken(Field field)
	{
		if(fields.contains(field))
		{
			return "F" + fields.indexOf(field);
		}
		
		throw new IllegalArgumentException("The select statement does not contain this query.");
	}
	
	protected String getRelativePath(Field field)
	{
		List<Query> path = getQueryPath(field);

		if(path.size() == 1)
		{
			return field.getName();
		}
		else if(path.size() > 1)
		{
			return getToken(path.get(1)) + "." + path.get(1).getToken(field);
		}
		
		throw new IllegalArgumentException("The select statement does not contain this field.");
	}
	
	public void addCondition(Condition condition)
	{
		if(!conditions.contains(condition))
		{
			conditions.add(condition);
		}
	}
	
	public void setOrder(Order order)
	{
		this.order = order;
	}
	
	protected String getFieldsClause()
	{
		String statement = "SELECT ";
		
		for(Field field : fields)
		{
			statement += getRelativePath(field) + " AS " + getToken(field) + ",";
		}
		
		if(fields.size() > 0)
		{
			statement = statement.substring(0,statement.length()-1);
		}
		else
		{
			throw new IllegalStateException("This select statement has no fields defined.");
		}

		return statement;
	}
	
	protected String getQueriesClause()
	{
		String statement = "FROM ";
		
		for(Query query : queries)
		{
			statement += "(" + query + ") AS " + getToken(query) + ",";
		}
		
		if(queries.size() > 0)
		{
			statement = statement.substring(0,statement.length()-1);
		}
		else
		{
			throw new IllegalStateException("This select statement has no queries defined.");
		}
		
		return statement;
	}
	
	protected String getConditionsClause()
	{
		String statement = "";
		
		if(conditions.size() > 0)
		{
			statement += "WHERE ";
			
			for(Condition condition : conditions)
			{
				Field arg1 = condition.getArgument1();
				statement += getToken(arg1) + " " + condition.getOperator().getValue();
				
				if(condition.getOperator() instanceof Condition.BinaryOperator)
				{
					if(!StringUtil.isNullOrEmpty(condition.getValue()))
					{
						statement += " '" + condition.getValue() + "'";
					}
					else if(condition.getArgument2() != null && 
							!StringUtil.isNullOrEmpty(condition.getArgument2().getName()))
					{
						statement += " " + getToken(condition.getArgument2());
					}
				}
				
				statement += " AND ";
			}
			
			statement = statement.substring(0,statement.length()-5);
		}
		
		return statement;
	}
	
	protected String getOrderClause()
	{
		String statement = "";
		
		if(order != null)
		{
			statement += "ORDER BY " + order.getValue();
		}
		
		return statement;
	}
	
	public String getStatement()
	{
		String statement = "";
		statement += getFieldsClause() + " ";
		statement += getQueriesClause() + " ";
		statement += getConditionsClause() + " ";
		statement += getOrderClause();
		
		return statement;
	}
}