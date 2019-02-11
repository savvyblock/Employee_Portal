package net.esc20.txeis.EmployeeAccess.web.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import net.esc20.txeis.EmployeeAccess.util.StringUtil;

@SuppressWarnings("serial")
public class ButtonTag extends TagSupport {

	private final String standardClasses = "button focusable hover_trigger ";
	private String clazz = "";
	private String id = "";
	private String size = "medium";
	protected String type = "";
	private String formid = "";;
	private String event = "";
	private String name = "";
	private String fragments = "";
	private String href = "";
	private boolean checkunsaved = true;
	private boolean disabled = false;
	private boolean ajax = false;
	private String sendFocus = "";
	private String tabindex = "";
	private String label = "";
	private boolean isDefault = false;
	private String onclick = "";
	private String onmousedown = "";
	private boolean popupdispose = false;
	private String popupid = "";
	private boolean popup = true;
	private boolean springPopup = false;
	private String flowUrl ="";
	private String target = "";

	public String commonButton() {
		StringBuilder sb = new StringBuilder();
		String butName = type + "button_" + size;

		// class section
		sb.append(" class=\"");
		sb.append(standardClasses);
		sb.append(clazz);
		sb.append(" " + butName + " ");
		if (isDefault) {
			sb.append(" default ");
		}
		if (disabled) {
			sb.append(" disabled " + butName + "_disabled ");
		}
		sb.append("\"");

		// behaviors attributes
		sb.append(" hoverclass=\"" + butName + "_hover\" ");
		sb.append(" defaultclass=\"" + butName + "_default\" ");
		sb.append(" defaulthoverclass=\"" + butName + "_default_hover\" ");
		sb.append(" focus-class=\"" + butName + "_focus\" ");
		sb.append(" defaultfocus-class=\"" + butName + "_default_focus\" ");
		sb.append(" disabled-class=\"" + butName + "_disabled\" ");
		sb.append(" send-focus=\"" + sendFocus + "\" ");

		if (tabindex.length() > 0) {
			sb.append(" tabindex=\"" + tabindex + "\" ");
		}

		return sb.toString();
	}

	public String createPostButton() {
		StringBuilder sb = new StringBuilder();

		sb.append("<a id=\"" + id + "\"");

		if(!isDisabled())
		{
			sb.append(" href=\"#\"");
			
			// add the link button class if it doesn't already exist
			//if (!clazz.contains("link_button")) {
			//	clazz += " link_button";
			//}
	
			
	
			//if (onclick.length() > 0 || !checkunsaved) {
				sb.append(" onclick=\"");
	
				if (onclick.length() > 0) {
					sb.append(onclick);
				}
	
				if (!checkunsaved) {
					sb.append(";");
				}
				
				sb.append(";$('#' + this.getAttribute('buttonid')).click();");
				sb.append("\" ");
				
				if (onmousedown.length() > 0) {
					sb.append(" onmousedown=\"" + onmousedown + "\"");
				}
			//}
		}
		else
		{
			sb.append(" onclick=\"return false;\"");
		}
		
		sb.append(commonButton());
		sb.append(" buttonid=\"" + id + "Button\" ");

		sb.append(">");
		sb.append(label);
		sb.append("</a>");

		sb.append("<input class=\"hidden\" type=\"submit\" ");
		sb.append("id=\"" + id + "Button\" ");
		if (StringUtil.isNullOrEmpty(event)) {
			sb.append("name=\"" + name + "\" />");
		} else {
			sb.append("name=\"_eventId_" + event + "\" />");
		}

		return sb.toString();
	}

	public String createGetButton() {
		StringBuilder sb = new StringBuilder();

		sb.append("<a id=\"" + id + "\"");

		if(!isDisabled())
		{
			sb.append(" href=\"" + href);
			if (!StringUtil.isNullOrEmpty(event)) {
				sb.append(flowUrl+"&_eventId=" + event);
			}				
			sb.append("\"");

			if(!StringUtil.isNullOrEmpty(target))
			{
				sb.append(" target=\"" + target + "\"");
			}
			
			if (onclick.length() > 0 || !checkunsaved) {
				sb.append(" onclick=\"");
	
				if (onclick.length() > 0) {
					sb.append(onclick);
				}
	
				if (!checkunsaved) {
					sb.append(";UnsavedDataWarning.disable();");
				}
				
				sb.append("\" ");
			}
			
			if (onmousedown.length() > 0) {
				sb.append(" onmousedown=\"" + onmousedown + "\"");
			}
		}
		else
		{
			sb.append(" onclick=\"return false;\"");
		}

		sb.append(commonButton());
		sb.append(">");
		sb.append(label);
		sb.append("</a>");

		return sb.toString();
	}

	public String createAjaxPostButton() {
		StringBuilder sb = new StringBuilder();

		sb.append("<a id=\"" + id + "\"");

		if(!isDisabled())
		{
			sb.append(" href=\"#\"");
			sb.append(" onclick=\"");
			// setup ajax on click
			if (onclick.length() > 0) {
				sb.append(onclick);
			}
	
			if (!checkunsaved) {
				sb.append(";UnsavedDataWarning.disable()");
			}
	
			// add the ajax call to the onclick
			sb.append(";Spring.remoting.submitForm('" + id + "', '" + formid + "', { _eventId : '" + event + "'});return false;");
	
			sb.append("\" ");
			
			if (onmousedown.length() > 0) {
				sb.append(" onmousedown=\"" + onmousedown + "\"");
			}
		}
		else
		{
			sb.append(" onclick=\"return false;\"");
		}
		
		sb.append(commonButton());
		sb.append(">");
		sb.append(label);
		sb.append("</a>");

		return sb.toString();
	}

	public String createAjaxGetButton() {
		StringBuilder sb = new StringBuilder();

		sb.append("<a id=\"" + id + "\"");

		sb.append(commonButton());
		
		if (!isDisabled()) {		
			sb.append(" href=\"" + href + "\"");
			
			sb.append(" onclick=\"");
			// setup ajax on click
			if (onclick.length() > 0) {
				sb.append(onclick);
			}
			
			if (!checkunsaved) {
				sb.append(";UnsavedDataWarning.disable()");
			}			
			
			// create parameters
			StringBuilder parameters = new StringBuilder();
			if (!StringUtil.isNullOrEmpty(event)) {
				parameters.append("_eventId : " + "'" + event + "'");
			}
			if (!StringUtil.isNullOrEmpty(fragments)) {
				if (!StringUtil.isNullOrEmpty(event)) {
					parameters.append(", ");
				}
				parameters.append("fragments : " + "'" + fragments + "'");
			}
			
			// add the ajax call to the onclick
			sb.append(";Spring.remoting.getLinkedResource('" + id + "', {" + parameters.toString() + "}, " + popup + "); return false;");

			sb.append("\" ");	
			
			if (onmousedown.length() > 0) {
				sb.append(" onmousedown=\"" + onmousedown + "\"");
			}
		} else {			
		}

		sb.append(">");
		sb.append(label);
		sb.append("</a>");

		return sb.toString();
	}
	
	public String createPopupDisposeButton() {
		StringBuilder sb = new StringBuilder();

		sb.append("<a id=\"" + id + "\"");

		sb.append(" href=\"#\"");

		sb.append(commonButton());

		sb.append(" onclick=\"");
		// setup ajax on click
		if (onclick.length() > 0) {
			sb.append(onclick);
		}

		if (!checkunsaved) {
			sb.append(";UnsavedDataWarning.disable();");
		}

		// add the destory call to the onclick
		if (!springPopup) {
			sb.append(";dijit.byId('" + popupid + "').hide();");
		} else {
			sb.append(";dijit.byId(document.getElementById('" + popupid + "').parentNode.parentNode.id).hide();");
		}

		sb.append("\" ");
		
		if (onmousedown.length() > 0) {
			sb.append(" onmousedown=\"" + onmousedown + "\"");
		}

		sb.append(">");
		sb.append(label);
		sb.append("</a>");

		return sb.toString();
	}

	@Override
	public int doStartTag() throws JspException {
		try {
			if (popupdispose) {
				pageContext.getOut().println(createPopupDisposeButton());
			}else if (ajax) {
				// check to see if this is for a form post or a get
				if (StringUtil.isNullOrEmpty(formid)) {
					pageContext.getOut().println(createAjaxGetButton());
				} else {
					pageContext.getOut().println(createAjaxPostButton());
				}
			} else {
				// check to see if this is for a form post or a get
				if (StringUtil.isNullOrEmpty(formid)) {
					pageContext.getOut().println(createGetButton());
				} else {
					pageContext.getOut().println(createPostButton());
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return SKIP_BODY;
	}

	public void setCssClass(String clazz) {
		this.clazz = clazz;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setFormid(String formid) {
		this.formid = formid;
	}

	public void setEvent(String event) {
		this.event = event;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void setFragments(String fragments) {
		this.fragments = fragments;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public void setCheckunsaved(String checkunsaved) {
		this.checkunsaved = Boolean.parseBoolean(checkunsaved);
	}

	public void setDisabled(String disabled) {
		this.disabled = Boolean.parseBoolean(disabled);
	}
	
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
	
	protected boolean isDisabled()
	{
		return disabled;
	}

	public void setAjax(String ajax) {
		this.ajax = Boolean.parseBoolean(ajax);
	}

	public void setSendFocus(String sendFocus) {
		this.sendFocus = sendFocus;
	}

	public void setTabindex(String tabindex) {
		this.tabindex = tabindex;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	public void setDefault(String isDefault) {
		this.isDefault = Boolean.parseBoolean(isDefault);
	}

	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}

	public void setOnmousedown(String onmousedown) {
		this.onmousedown = onmousedown;
	}

	public void setPopupdispose(String popupdispose) {
		this.popupdispose = Boolean.parseBoolean(popupdispose);
	}

	public void setPopupid(String popupid) {
		this.popupid = popupid;
	}

	public void setPopup(String popup) {
		this.popup = Boolean.parseBoolean(popup);
	}

	public void setSpringPopup(boolean springPopup) {
		this.springPopup = springPopup;
	}

	public void setFlowUrl(String flowUrl) {
		this.flowUrl = flowUrl;
	}
	public void setTarget(String target) {
		this.target = target;
	}
}
