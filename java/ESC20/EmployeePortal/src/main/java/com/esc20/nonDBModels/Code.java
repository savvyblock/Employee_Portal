package com.esc20.nonDBModels;

import java.io.Serializable;

import net.sf.json.JSONObject;

public class Code implements Serializable, Cloneable, Comparable<Code>
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
	
	public Code clone()
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

	public JSONObject toJSON() {
		JSONObject jo = new JSONObject();
		jo.put("code", this.getCode());
		jo.put("subCode", this.getSubCode());
		jo.put("description", this.getDescription());
		return jo;
	}

	public String toJSON2() {
		return this.getCode()+"_"+this.getDescription();
	}
}
