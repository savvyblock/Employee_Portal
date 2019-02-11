package net.esc20.txeis.EmployeeAccess.web.tags;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.tagext.TagSupport;

import net.esc20.txeis.EmployeeAccess.domainobject.reference.Code;

public class DropDownAutoComplete extends TagSupport {

	private static final long serialVersionUID = -6344312609885903694L;
	
	private static final String DROP_DOWN_FORMAT;
	
	static {
		DROP_DOWN_FORMAT = new StringBuffer()
			.append("<table style=\"margin-top:0px;margin-bottom:0px;margin-left:0px;margin-right:0px; \"><tr><td style=\"text-align:left; vertical-align:bottom; margin:0px 0px 0px 0px;padding: 0px; 0px; 0px; 0px;\"><input type='text' name=\"%s\" class=\"%s\" %s  style=\"%s\" contents=\"%s\" value=\"%s\" tabindex=\"%s\" /></td>").toString();
	}
	
	private String _name = null;
	private String _id = null;
	private String _tabindex = null;
	private boolean _disabled = false;
	private String _classes = "";
	private String _attributes = "";
	private String _errorFlag = "";
	private Object _contents = null;
	private int _valueColumn = 0;
	private String _value = "";
	private boolean _showCode = false;
	private boolean _allowBlank = false;
	private String _onchange = null;
	private String _changeFunction = null;
	private String _width = null;
	private String _popupHeight = null;
	private String _popupWidth = null;
	private boolean _appendValue = false;
	private String _maxlength = "";
	private boolean _displayDesc = false;
	private boolean readonly = false;
	private boolean scriptOnly = false;
	private boolean hideScript = false;

	
	@Override
	public int doStartTag() {
		try {
			String mouse_click = "";
			String src = "\"../../../CommonWeb/images/drop_down_button.gif\"";
			if (!_disabled) {
				mouse_click = "onmousedown=\"doDropPickDropDown(\'"+_id+"\');return false;\"";
			} else {
				src = "\"../../../CommonWeb/images/drop_down_button_d.gif\"";
			}
			
			if(!hideScript && !scriptOnly)
			{
				pageContext.getOut().print(renderContents() + String.format(DROP_DOWN_FORMAT, 
							_name, renderClasses(), renderAttributes(), renderStyles(),"items_"+_id, _value, _tabindex)+
							"<td style=\"margin:0px 0px 0px 0px; padding: 0px 0px 0px 0px; line-height: 0px; height: 17px;\">"+
							"<a href=\"#\""+mouse_click+" class=\"drop_down_button_img_link autoCompleteImg\">"+
							"<img src=" + src +
							" style=\"vertical-align:bottom;margin:0px 0px 0px 0px; padding: 0px 0px 0px 0px; line-height: 0px; height: 17px;\" class=\"drop_down_button_img\"/>"+
							"</a>"+"</td></tr></table>");		
			}
			else if(hideScript && !scriptOnly)
			{
				pageContext.getOut().print(String.format(DROP_DOWN_FORMAT, 
							_name, renderClasses(), renderAttributes(), renderStyles(),"items_"+_id, _value, _tabindex)+
							"<td style=\"margin:0px 0px 0px 0px; padding: 0px 0px 0px 0px; line-height: 0px; height: 17px;\">"+
							"<a href=\"#\""+mouse_click+" class=\"drop_down_button_img_link autoCompleteImg\">"+
							"<img src=" + src +
							" style=\"vertical-align:bottom;margin:0px 0px 0px 0px; padding: 0px 0px 0px 0px; line-height: 0px; height: 17px;\" class=\"drop_down_button_img\"/>"+
							"</a>"+"</td></tr></table>");		
			}
			else if(!hideScript && scriptOnly)
			{
				pageContext.getOut().print(renderContents());		
			}
			else
			{
				pageContext.getOut().print(renderContents() + String.format(DROP_DOWN_FORMAT, 
						_name, renderClasses(), renderAttributes(), renderStyles(),"items_"+_id, _value, _tabindex)+
						"<td style=\"margin:0px 0px 0px 0px; padding: 0px 0px 0px 0px; line-height: 0px; height: 17px;\">"+
						"<a href=\"#\""+mouse_click+" class=\"drop_down_button_img_link autoCompleteImg\">"+
						"<img src=" + src +
						" style=\"vertical-align:bottom;margin:0px 0px 0px 0px; padding: 0px 0px 0px 0px; line-height: 0px; height: 17px;\" class=\"drop_down_button_img\"/>"+
						"</a>"+"</td></tr></table>");	
			}
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
		return EVAL_BODY_INCLUDE;
	}
	
	private String renderClasses() {
		StringBuffer classes = new StringBuffer();
		classes.append(_classes);
		classes.append(" text errorable focusable autoFill autoComplete_drop_down");
		if (_disabled) {
			classes.append(" disabled");
		}
		if (hasErrors()) {
			classes.append(" error");
		}
		
		if(_displayDesc){
			classes.append(" displayDesc");
		}
		
		return classes.toString();
	}
	
	private String renderAttributes() {
		StringBuffer attributes = new StringBuffer();
		attributes.append(_attributes);
		addAttribute(attributes, _id, "id");
		addAttribute(attributes, _tabindex, "tabindex");
		addAttribute(attributes, _onchange,"onchange");
		addAttribute(attributes, _changeFunction,"changeFunction");
		addAttribute(attributes, _maxlength, "maxlength");
		addAttribute(attributes, _popupWidth, "popupWidth");
		addAttribute(attributes, _popupHeight, "popupHeight");
		if (_disabled) {
			attributes.append(" disabled");
		}
		if (readonly) {
			attributes.append("readonly='readonly' ");
		}
		return attributes.toString();
	}
	
	private String renderContents() {
		StringBuffer contents = new StringBuffer();
		int counter = 0;
		
		List<List<String>> listContents = getListContents();
		if (null != listContents) {
			contents.append("<script type='text/javascript'> var items_"+_id+" = new Array(); \n\r");
			
			if (_allowBlank) {		
				contents.append("items_"+_id+"["+counter+"]"+" = "+ "[' ',' '];  \n\r");
				counter++;
			}

			for (List<String> row : listContents) {							
				StringBuffer escapeCheck;
				if(row.size() == 1)
				{
					escapeCheck= new StringBuffer(row.get(0));
					if(escapeCheck.indexOf("'") > -1)
						escapeCheck.replace(escapeCheck.indexOf("'"), escapeCheck.indexOf("'")+1, "");
					if(escapeCheck.indexOf("\"") > -1)
						escapeCheck.replace(escapeCheck.indexOf("\""), escapeCheck.indexOf("\"")+1, "");
					if(escapeCheck.indexOf("\\") > -1)
						escapeCheck.replace(escapeCheck.indexOf("\\"), escapeCheck.indexOf("\\")+1, "");
					contents.append("items_"+_id+"["+counter+"] = ['"+row.get(0)+"','"+escapeCheck.toString()+"'];  \n\r");
				
				}
				else if(row.size() == 2)
				{
					escapeCheck= new StringBuffer(row.get(1));
					if(escapeCheck.indexOf("'") > -1)
						escapeCheck.replace(escapeCheck.indexOf("'"), escapeCheck.indexOf("'")+1, "");
					if(escapeCheck.indexOf("\"") > -1)
						escapeCheck.replace(escapeCheck.indexOf("\""), escapeCheck.indexOf("\"")+1, "");
					if(escapeCheck.indexOf("\\") > -1)
						escapeCheck.replace(escapeCheck.indexOf("\\"), escapeCheck.indexOf("\\")+1, "");
					contents.append("items_"+_id+"["+counter+"] = ['"+row.get(0)+"','"+escapeCheck.toString()+"'];  \n\r");
				}
				counter++;
				
			}
			contents.append("</script>");
		}
		
		if (_appendValue) {
			boolean hasValue = false;
			if (null != listContents) {
				for (List<String> row : listContents) {
					if (_value.equals(row.get(0))) {
						hasValue = true;
						break;
					}
				}
			}
		//	if (!hasValue) {
		//		contents.append(String.format("<option value=\"%s\" selected >%s</option>", _value, _value));
		//	}
		}
		
		return contents.toString();
	}
	
	private String renderStyles(){
		StringBuffer styles = new StringBuffer();
		addStyle(styles, _width, "width");
		return styles.toString();
	}
	
	private static void addAttribute(StringBuffer attributeBuffer, String attributeValue, String attributeName) {
		if (null != attributeValue) {
			attributeBuffer.append(String.format(" %s=\"%s\"", attributeName, attributeValue));
		}
	}
	
	private static void addStyle(StringBuffer styleBuffer, String styleValue, String styleName) {
		if (null != styleValue) {
			styleBuffer.append(String.format("%s:%s;", styleName, styleValue));
		}
		styleBuffer.append("margin:0px 0px 0px 0px;padding: 0px 0px 0px 0px;");
	}
	
	public void setName(String pValue) {
		_name = pValue;
	}
	
	public void setValue(String pValue) {
		_value = pValue;
	}
	
	public void setId(String pValue) {
		_id = pValue;
	}
	
	public void setTabindex(String pValue) {
		_tabindex = pValue;
	}
	
	public void setDisabled(boolean pValue) {
		_disabled = pValue;
	}
	
	public void setAttributes(String pValue) {
		_attributes = pValue;
	}
	
	public void setClasses(String pValue) {
		_classes = pValue;
	}
	
	public void setErrorFlag(String pValue) {
		_errorFlag = pValue;
	}
	
	public void setShowCode(boolean pValue) {
		_showCode = pValue;
	}
	
	public void setAllowBlank(boolean pValue) {
		_allowBlank = pValue;
	}
	
	public void setDisplayDesc(boolean pValue) {
		_displayDesc = pValue;
	}
	
	private boolean hasErrors() {
		return !"".equals(_errorFlag);
	}

	public void setOnchange(String pValue) {
		this._onchange = pValue;
	}
	
	public void setWidth(String pValue) {
		_width = pValue;
	}
	
	public void setMaxlength(String pValue) {
		_maxlength = pValue;
	}
	
	public void setAppendValue(boolean pValue) {
		_appendValue = pValue;
	}
	
	public void setContents(Object pValue) {
		_contents = pValue;
	}
	
	public void setValueColumn(int pValue) {
		_valueColumn = pValue;
	}
	
	private List<List<String>> getListContents() {		
		if (null != _contents) {
			if (_contents instanceof ArrayList)
			{
				if(!((ArrayList)_contents).isEmpty() && ((ArrayList)_contents).get(0) instanceof Code)
					return expandCodeListLevels((List<Code> )_contents );
				else
					return getGenericContents();
			}
			
			else
				return getGenericContents();
		}
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	private List<List<String>> getGenericContents() {
		if (!(_contents instanceof List)) {
			throw new RuntimeException("Contents must be of type List<String> or List<List<String>>");
		}
		
		List<Object> contentsList = (List<Object>)_contents;
		if (((List<Object>)_contents).isEmpty()) {
			return expandListLevels(contentsList);
		}
		
		if (contentsList.get(0) instanceof String) {
			return expandListLevels(contentsList);
		}
		else if (contentsList.get(0) instanceof List) {
			return (List<List<String>>)_contents;
		}
		else {
			throw new RuntimeException("Contents must be of type List<String> or List<List<String>>");
		}
	}

	private List<List<String>> expandListLevels(List<Object> contentsList) {
		List<List<String>> outputList = new ArrayList<List<String>>();
		for (Object contents : contentsList) {
			if (contents != null) {
				List<String> innerList = new ArrayList<String>();
				innerList.add((String)contents);
				outputList.add(innerList);
			}
		}
		return outputList;
	}
	
	private List<List<String>> expandCodeListLevels(List<Code> contentsList) {
		List<List<String>> outputList = new ArrayList<List<String>>();
		for (Code contents : contentsList) {
			if (contents != null) {
				List<String> innerList = new ArrayList<String>();
				innerList.add((String)contents.getCode());
				innerList.add((String)contents.getDescription());
				outputList.add(innerList);
			}
		}
		return outputList;
	}
	
	public void setReadonly(String readonly) {
		if (readonly.equalsIgnoreCase("true")) {
			this.readonly = true;
		}
		else {
			this.readonly = false;
		}
	}

	public void setChangeFunction(String function) {
		_changeFunction = function;
	}

	public void setScriptOnly(boolean scriptOnly) {
		this.scriptOnly = scriptOnly;
	}

	public void setHideScript(boolean hideScript) {
		this.hideScript = hideScript;
	}

	public void setPopupHeight(String height) {
		_popupHeight = height;
	}

	public void setPopupWidth(String width) {
		_popupWidth = width;
	}

}
