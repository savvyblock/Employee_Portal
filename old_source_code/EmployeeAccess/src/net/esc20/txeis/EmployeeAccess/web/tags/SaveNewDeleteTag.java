package net.esc20.txeis.EmployeeAccess.web.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import net.esc20.txeis.EmployeeAccess.web.tags.ButtonTag;

public class SaveNewDeleteTag extends ButtonTag
{
	private static final long serialVersionUID = 485506591378268642L;
	
	private boolean savedisabled = false;
	private boolean newdisabled = false;
	private boolean deletedisabled = false;
	
	public boolean isSavedisabled()
	{
		return savedisabled;
	}

	public void setSavedisabled(boolean savedisabled)
	{
		this.savedisabled = savedisabled;
	}

	public boolean isNewdisabled()
	{
		return newdisabled;
	}

	public void setNewdisabled(boolean newdisabled)
	{
		this.newdisabled = newdisabled;
	}

	public boolean isDeletedisabled()
	{
		return deletedisabled;
	}

	public void setDeletedisabled(boolean deletedisabled)
	{
		this.deletedisabled = deletedisabled;
	}

	public int doStartTag() throws JspException
	{
		try{
			StringBuffer sb = new StringBuffer();
			setType("bluetab");

			sb.append("<table>");
			sb.append("<tr>");
			sb.append("<td>");
			sb.append(createButtonTag("Save","save",savedisabled));
			sb.append("</td>");
			sb.append("<td>");
			sb.append(createButtonTag("New","new",newdisabled));
			sb.append("</td>");
			sb.append("<td>");
			sb.append(createButtonTag("Delete","delete",deletedisabled));
			sb.append("</td>");
			sb.append("</tr>");
			sb.append("</table>");
			
			pageContext.getOut().println(sb.toString());
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		return EVAL_BODY_INCLUDE;
	}
	
	protected String createButtonTag(String label, String event, boolean disabled)
	{
		super.setLabel(label);
		super.setId(event + "Button");
		super.setDefault("false");
		if(!disabled)
		{
			super.setOnclick("UnsavedDataWarning.disable();Spring.remoting.submitForm('" + event + "Button', $(this).parents('form').attr('id'), { _eventId : '" + event + "' });return false;");
		}
		super.setDisabled("" + disabled);
		
		return super.commonButton();
	}
}