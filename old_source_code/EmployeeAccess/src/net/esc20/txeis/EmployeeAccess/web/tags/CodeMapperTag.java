package net.esc20.txeis.EmployeeAccess.web.tags;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import net.esc20.txeis.EmployeeAccess.domainobject.reference.ICode;
import net.esc20.txeis.EmployeeAccess.util.CodeMapper;

public class CodeMapperTag implements Tag, Serializable
{
	private static final long serialVersionUID = 1438737327834749607L;

	private Tag parent;
	private PageContext pc;
	private String code;
	private Collection<? extends ICode> collection;
	
	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public Collection<? extends ICode> getCollection()
	{
		return collection;
	}

	public void setCollection(Collection<? extends ICode> collection)
	{
		this.collection = collection;
	}

	public int doEndTag() throws JspException
	{
		return EVAL_PAGE;
	}

	public int doStartTag() throws JspException
	{
		try
		{
			pc.getOut().write(CodeMapper.getDescription(collection, code));
		}
		catch(IOException ex)
		{
			throw new JspTagException("An IOException occurred.");
		}
		
		return SKIP_BODY;
	}

	public Tag getParent()
	{
		return parent;
	}

	public void release()
	{
		parent = null;
		pc = null;
	}

	public void setPageContext(PageContext pc)
	{
		this.pc = pc;
	}

	public void setParent(Tag t)
	{
		this.parent = t;
	}
	
}