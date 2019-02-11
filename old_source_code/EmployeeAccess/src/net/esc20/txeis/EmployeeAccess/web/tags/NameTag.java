package net.esc20.txeis.EmployeeAccess.web.tags;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import net.esc20.txeis.EmployeeAccess.util.StringUtil;

import org.apache.commons.lang.StringEscapeUtils;

public class NameTag implements Tag, Serializable {

	private static final long serialVersionUID = -9044099412086064870L;

	private PageContext pc = null;
	private Tag parent = null;
	private String firstName = null;
	private String middleName = null;
	private String lastName = null;
	private String generation = null;

	public void setGeneration(String generation) {
		this.generation = generation;
	}

	public void setPageContext(PageContext p) {
		pc = p;
	}

	public void setParent(Tag t) {
		parent = t;
	}

	public Tag getParent() {
		return parent;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int doStartTag() throws JspException {
		try {
			pc.getOut().write(StringEscapeUtils.escapeHtml(StringUtil.formatName(firstName, middleName, lastName,generation)));
		} catch (IOException e) {
			throw new JspTagException("An IOException occurred.");
		}
		return SKIP_BODY;
	}

	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}

	public void release() {
		pc = null;
		parent = null;
	}
}