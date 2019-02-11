package net.esc20.txeis.EmployeeAccess.web.tags;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import net.esc20.txeis.EmployeeAccess.util.StringUtil;

public class TabTag implements Tag, Serializable {

	private static final long serialVersionUID = -9044099412086064870L;
	
	private PageContext pc = null;
	private Tag parent = null;
	private boolean active = false;
	private boolean leftMost = false;
	private boolean disabled = false;
	private String href = null;
	private String name = null;
	private String hoverGroup = null;
	private String id = null;
	private String popuptarget = null;
	private String popupparent = null;
	private String popupeffectgroup = null;
	private String tabGroup = null;
	private String onClick = null;	
	
	
	public String getOnClick() {
		return onClick;
	}
	public void setOnClick(String onClick) {
		this.onClick = onClick;
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
	
	public void setActive(String active) {
		if (active.equalsIgnoreCase("true")) {
			this.active = true;
		} else {
			this.active = false;
		}
	}
	
	public void setLeftMost(String leftMost) {
		if (leftMost.equalsIgnoreCase("true")) {
			this.leftMost = true;
		} else {
			this.leftMost = false;
		}
	}

	public void setDisabled(String disabled) {
		if (disabled.equalsIgnoreCase("true")) {
			this.disabled = true;
		} else {
			this.disabled = false;
		}
	}

	public void setHref(String href) {
		this.href = href;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setHoverGroup(String hoverGroup) {
		this.hoverGroup = hoverGroup;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPopuptarget() {
		return popuptarget;
	}

	public void setPopuptarget(String popuptarget) {
		this.popuptarget = popuptarget;
	}

	public String getPopupparent() {
		return popupparent;
	}

	public void setPopupparent(String popupparent) {
		this.popupparent = popupparent;
	}

	public String getPopupeffectgroup() {
		return popupeffectgroup;
	}

	public void setPopupeffectgroup(String popupeffectgroup) {
		this.popupeffectgroup = popupeffectgroup;
	}

	public void setTabGroup(String tabGroup) {
		this.tabGroup = tabGroup;
	}

	public int doStartTag() throws JspException {
		try {	
						
			StringBuilder sb = new StringBuilder();

			/*
			 * <td class="active_l active_ll" tab0-hoverclass="hover_lactivel">
									&nbsp;
								</td>
								<td class="active" tab0-hoverclass="hover">
									<a class="hover_group_trigger" hovergroup="tab0" href="#">Demo</a>
								</td>
								<td class="active_r" tab0-hoverclass="hover_r">
									&nbsp;
								</td>
			 * 
			 */
			
			// first table data
			sb.append("<td id=\"");
			sb.append(id + "Left"); 
			sb.append("\" class=\"");
			
			if (active) {
				sb.append("active_l");
				
				if (leftMost) {
					sb.append(" active_ll");
				}
			} else {
				sb.append("item_l");
				
				if (leftMost) {
					sb.append(" item_ll");
				}
			}
			
			sb.append("\" ");
			
			// hover class
			//if (!active) {
				sb.append(hoverGroup);
				sb.append("-hoverclass=\"");
				
				if (leftMost) {
					if (active) {
						sb.append("hover_lactivel");
					} else {
						sb.append("hover_liteml");
					}
				} else {
					sb.append("hover_l");
				}			
			//}
			
			sb.append("\">&nbsp;</td>");
			
			// second table data
			sb.append("<td class=\"");
			
			if (active) { 
				sb.append("active");
			} else {
				sb.append("item");
			}
			
			sb.append("\" ");
						
			// hover class
			//if (!active) {
				sb.append(hoverGroup); 
				sb.append("-hoverclass=\"hover\">");
			//}
			
			if (!disabled) {
				// begin link
				sb.append("<a id=\"");
				sb.append(id); 
				sb.append("\" class=\"hover_group_trigger tab_item\"");
				
				if (!active) {
					sb.append(" hovergroup=\"");
					sb.append(hoverGroup);
					sb.append("\" ");
				}
				
				// pop up options
				if (tabGroup != null) {
					sb.append("tabgroup=\"");
					sb.append(tabGroup);
					sb.append("\" ");			
				}
				
				// onClick javascript events
				if(!StringUtil.isNullOrEmpty(onClick)) {
					sb.append(" onclick='");
					sb.append(onClick);
					sb.append("' ");
				}	
				
				// href
				sb.append("href=\"");
				sb.append(href);
				sb.append("\">");	
				// tab name
				sb.append(name);			
				sb.append("</a>");
			} else {
				sb.append("<span id=\"");
				sb.append(id); 
				sb.append("\" class=\"disabled\"");

				// onClick javascript events
				if(!StringUtil.isNullOrEmpty(onClick)) {
					sb.append(" onclick='");
					sb.append(onClick);
					sb.append("' ");
				}				
				
				sb.append(">");	
				// tab name
				sb.append(name);			
				sb.append("</span>");
			}			
			
			sb.append("</td>");
			
			// third table data
			sb.append("<td class=\"");
					
			if (active) { 
				sb.append("active_r");
			} else {
				sb.append("item_r");
			}
			
			sb.append("\" ");
			
			// hover class
			//if (!active) {
				sb.append(hoverGroup); 
				sb.append("-hoverclass=\"hover_r\">&nbsp;</td>");
			//}
			
			pc.getOut().write(sb.toString());
	
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