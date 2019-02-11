package net.esc20.txeis.EmployeeAccess.web.tags;

import java.io.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

import net.esc20.txeis.EmployeeAccess.util.DateUtil;



public class TimestampTag implements Tag, Serializable {

	private static final long serialVersionUID = -9044099412086064870L;
	
	private PageContext pc = null;
	private Tag parent = null;
	private String timestamp = null;
	private String toFormat = null;
	private String fromFormat = null;
	
	public void setPageContext(PageContext p) {
		pc = p;
	}

	public void setParent(Tag t) {
		parent = t;
	}

	public Tag getParent() {
		return parent;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getToFormat() {
		return toFormat;
	}

	public void setToFormat(String toFormat) {
		this.toFormat = toFormat;
	}

	public String getFromFormat() {
		return fromFormat;
	}

	public void setFromFormat(String fromFormat) {
		this.fromFormat = fromFormat;
	}

	public int doStartTag() throws JspException {
		try {
			if (toFormat == null) {
				toFormat = "MMMM dd yyyy";
			}
			
			if (fromFormat == null) {
				fromFormat = "yyyyMMdd";
			}

			if (timestamp != null) {
				pc.getOut().write(DateUtil.formatDate(timestamp, fromFormat, toFormat));
			} else {
				throw new JspTagException("Timestamp must be specified");
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
		timestamp = null;
		fromFormat = null;
		toFormat = null;
	}
}