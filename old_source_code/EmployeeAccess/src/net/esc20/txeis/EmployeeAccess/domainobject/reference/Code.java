package net.esc20.txeis.EmployeeAccess.domainobject.reference;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.esc20.txeis.EmployeeAccess.util.StringUtil;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class Code implements ICode, Serializable, Cloneable, Comparable<Code>
{
	private static final long serialVersionUID = -182993170197294676L;
	
	protected String code ="";
	protected String subCode ="";
	protected String description ="";
	protected String displayLabel ="";

	public Code(){}
	
	public Code(String code, String description)
	{
		this.code = code;
		this.description = description;
	}
	
	public Code(String code, String subCode, String description)
	{
		this.code = code;
		this.description = description;
		this.subCode = subCode;
	}


	public void setCode(String code)
	{
		this.code = code;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getCode()
	{
		return code;
	}

	public String getDescription()
	{
		return description;
	}
	
	public void setDisplayLabel(String displayLabel) {
		
		if(displayLabel != null && !displayLabel.equals(""))
		{
			String[] displayLabelSplit = displayLabel.split(" - ");
			if( displayLabelSplit.length == 2 )
			{
				code = displayLabelSplit[0];
				description = displayLabelSplit[1];
			}
		}
		this.displayLabel = displayLabel;
	}
	
	public String getDisplayLabel()
	{
		if(displayLabel == null || displayLabel.equals(""))
		{
			if(code.trim().equals("") && !description.trim().equals(""))
				setDisplayLabel(description);
			else if(!code.trim().equals("") && description.trim().equals(""))
				setDisplayLabel(code);
			else if(code.trim().equals("") && description.trim().equals(""))
				setDisplayLabel("");
			else
				setDisplayLabel(code + " - " + description);
		}
		return displayLabel;
	}
	
	public String getDisplayLabelActual()
	{
		return displayLabel;
	}
	
	public String getSubCode() {
		return subCode;
	}
	
	public void setSubCode(String subCode) {
		this.subCode = subCode;
	}
	
	public ParameterizedRowMapper<ICode> getRowMapper(final String code, final String description)
	{
		return new CodeRowMapper(code, description);
	}
	
	public static class CodeRowMapper implements ParameterizedRowMapper<ICode>
	{
		private String codeField;
		private String descriptionField;
		
		public CodeRowMapper(String code, String description)
		{
			this.codeField = code;
			this.descriptionField = description;
		}
		
		public ICode mapRow(ResultSet rs, int rows) throws SQLException
		{			
			Code instance = createInstance();

			instance.code = StringUtil.trim(rs.getString(codeField));
			instance.description = StringUtil.trim(rs.getString(descriptionField));
			
			return instance;
		}
		
		protected Code createInstance()
		{
			return new Code();
		}
	}

	public ParameterizedRowMapper<ICode> getRowMapper()
	{
		return getRowMapper(getCode(),getDescription());
	}

	public String getTable()
	{
		return null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		String codeLabel = this.getDisplayLabel();
		result = prime * result + ((codeLabel == null) ? 0 : codeLabel.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Code) )
			return false;
		Code other = (Code) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!this.getDisplayLabel().equals(other.getDisplayLabel()))
			return false;
		return true;
	}
	
	public Object clone()
	{
		Code code = new Code();
				
		code.setCode(this.getCode());
		code.setDescription(this.getDescription());
		code.setSubCode(this.getSubCode());
		
		return code;
	}

	@Override
	public int compareTo(Code o) {
		return this.getCode().compareTo(o.getCode());
	}

}