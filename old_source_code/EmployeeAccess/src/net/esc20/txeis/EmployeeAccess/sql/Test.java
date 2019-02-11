package net.esc20.txeis.EmployeeAccess.sql;

import net.esc20.txeis.EmployeeAccess.sql.Condition.Operator;

public class Test
{
	public static void main(String[] args)
	{
		Table table = new Table("mytable");
		System.out.println(table);
		
		Select select = new Select();
		select.addQuery(table);
		Query.Field f1 = new Query.Field(table,"myfield");
		select.addField(f1);
		System.out.println(select);
		
		Table table2 = new Table("othertable");
		
		Select select2 = new Select();
		select2.addQuery(select);
		select2.addQuery(table2);
		Query.Field f2 = new Query.Field(table2,"otherfield");
		select2.addField(f2);
		select2.addField(new Query.Field(table,"myfield"));
		System.out.println(select2);
		
		select.addCondition(new Condition(f1,"",Operator.NotNull));
		select2.addCondition(new Condition(f1,f2));
		select2.setOrder(Select.Order.Ascending);
		System.out.println(select2);
		
		Table table3 = new Table("jointable");
		Join join = new Join(table,table3);
		Query.Field jf1 = new Query.Field(table,"myfield");
		Query.Field jf2 = new Query.Field(table3,"joinfield");
		join.addField(jf1);
		join.addField(jf2);
		join.addCondition(new Condition(jf1,jf2));
		join.addCondition(new Condition(jf1,"hello",Condition.Operator.NotEqual));
		System.out.println(join);
	}
}