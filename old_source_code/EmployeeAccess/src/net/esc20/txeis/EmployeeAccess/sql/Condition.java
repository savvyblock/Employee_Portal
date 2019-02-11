package net.esc20.txeis.EmployeeAccess.sql;

import net.esc20.txeis.EmployeeAccess.util.Type;

public class Condition
{
	public static abstract class Operator extends Type
	{
		public static final Operator Equal = new BinaryOperator("=");
		public static final Operator NotEqual = new BinaryOperator("<>");
		public static final Operator Null = new UnaryOperator("IS NULL");
		public static final Operator NotNull = new UnaryOperator("IS NOT NULL");
		
		private Operator(String value)
		{
			super(value);
		}
	}
	
	public static class BinaryOperator extends Operator
	{
		private BinaryOperator(String value)
		{
			super(value);
		}
	}
	
	public static class UnaryOperator extends Operator
	{
		private UnaryOperator(String value)
		{
			super(value);
		}
	}
	
	protected Query.Field arg1;
	protected Query.Field arg2;
	protected String value;
	protected Operator operator;
	
	public Condition(Query.Field arg1, Query.Field arg2)
	{
		this(arg1, arg2, Operator.Equal);
	}
	
	public Condition(Query.Field arg1, Query.Field arg2, Operator operator)
	{
		this.arg1 = arg1;
		this.arg2 = arg2;
		this.operator = operator;
	}
	
	public Condition(Query.Field arg1, String value)
	{
		this(arg1, value, Operator.Equal);
	}
	
	public Condition(Query.Field arg1, String value, Operator operator)
	{
		this.arg1 = arg1;
		this.value = value;
		this.operator = operator;
	}
	
	public Query.Field getArgument1()
	{
		return arg1;
	}
	
	public Query.Field getArgument2()
	{
		return arg2;
	}
	
	public String getValue()
	{
		return value;
	}
	
	public Operator getOperator()
	{
		return operator;
	}
}