package net.esc20.txeis.EmployeeAccess.web.tags;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import net.esc20.txeis.EmployeeAccess.util.PhoneUtil;
import net.esc20.txeis.EmployeeAccess.util.StringUtil;

public class PhoneTag implements Tag, Serializable {

	private static final long serialVersionUID = -9044099412086064870L;

	private PageContext pc = null;
	private Tag parent = null;
	private String phone = null;
	private String extension = null;
	private String areaCode = null;

	public void setPageContext(PageContext p) {
		pc = p;
	}

	public void setParent(Tag t) {
		parent = t;
	}

	public Tag getParent() {
		return parent;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public int doStartTag() throws JspException {
		try {
			if (StringUtil.trim(phone).length() == 7) {
				pc.getOut().write(PhoneUtil.formatPhoneNumber(areaCode, phone, extension));
			} else {
				pc.getOut().write(phone);
			}
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
		phone = null;
	}
}