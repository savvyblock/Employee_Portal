package net.esc20.txeis.EmployeeAccess.web.tags;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

public class SsnTag implements Tag, Serializable {

	private static final long serialVersionUID = -9044099412086064870L;
	
	private PageContext pc = null;
	private Tag parent = null;
	private String ssn = null;
	
	public void setPageContext(PageContext p) {
		pc = p;
	}

	public void setParent(Tag t) {
		parent = t;
	}

	public Tag getParent() {
		return parent;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public int doStartTag() throws JspException {
		try {
			// NOTE: always output SSN with first 5 numbers obfuscated
			String last = ssn.substring(ssn.length() - 4, ssn.length());
			String formattedSsn = "<span style=\"font-size : 9pt; vertical-align : middle; height:17px\">&bull;&bull;&bull;</span>-<span style=\"font-size : 9pt; vertical-align : middle; height:17px\">&bull;&bull;</span>-<span style=\"vertical-align : middle;\">" + last + "</span>";
		
			pc.getOut().write(formattedSsn);
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
		ssn = null;
	}
}