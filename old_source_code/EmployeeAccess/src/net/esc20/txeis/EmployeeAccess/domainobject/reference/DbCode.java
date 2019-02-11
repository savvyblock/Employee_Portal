package net.esc20.txeis.EmployeeAccess.domainobject.reference;

import java.io.Serializable;

import net.esc20.txeis.EmployeeAccess.domainobject.reference.Code;
import net.esc20.txeis.EmployeeAccess.domainobject.reference.ICode;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class DbCode extends Code implements Serializable
{
	private static final long serialVersionUID = 7498189769184310727L;
	
	private String table;
	private String codeField;
	private String descriptionField;
	
	public DbCode(){}
	
	public DbCode(String table, String codeField, String descriptionField)
	{
		this.table = table;
		this.codeField = codeField;
		this.descriptionField = descriptionField;
	}

	public String getTable()
	{
		return table;
	}
	
	public void setTable(String table)
	{
		this.table = table;
	}
	
	public String getCodeField()
	{
		return codeField;
	}
	
	public void setCodeField(String codeField)
	{
		this.codeField = codeField;
	}
	
	public String getDescriptionField()
	{
		return descriptionField;
	}
	
	public void setDescriptionField(String descriptionField)
	{
		this.descriptionField = descriptionField;
	}
	
	public ParameterizedRowMapper<ICode> getRowMapper()
	{
		return new CodeRowMapper(codeField,descriptionField)
		{
			@Override
			protected Code createInstance()
			{
				return new DbCode();
			}
		};
	}
	
	public DbCode createInstance() { return new DbCode(); }
		
	public Object clone()
	{	
		DbCode dbCode = new DbCode();		
		dbCode.setTable(this.getTable());
		dbCode.setCodeField(this.getCodeField());
		dbCode.setDescriptionField(this.getDescriptionField());
		
		// copy parents fields too
		dbCode.setCode(this.getCode());
		dbCode.setDescription(this.getDescription());
		
		return dbCode;
	}
	
}
