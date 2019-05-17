package com.esc20.nonDBModels.report;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.util.WebUtils;

import com.esc20.validation.report.IParameterValidator;

/**
 * @author dflint
 *
 */
public class ReportParameter {

	protected String name = "";
	protected String description = "";
	protected String value = "";
	protected boolean userInputable = true;
	protected boolean multiSelect = false;
	protected boolean blankEqualsAll = false;
	protected boolean cookieValue = false;
	protected String allValue;
	protected String allName;
	protected String sessionOrCookieKey;
	protected IParameterValidator parameterValidator;
	protected int minumumValue = 0;
	protected int maximumValue = 255;
	protected String pickList = "";
    protected String reguiredValuesList = "";
    protected int specialValue = 0;
    protected boolean useLiteralValue = false;

	public boolean isUseLiteralValue() {
		return useLiteralValue;
	}

	public void setUseLiteralValue(boolean useLiteralValue) {
		this.useLiteralValue = useLiteralValue;
	}

	public String getReguiredValuesList() {
		return reguiredValuesList;
	}

	public void setReguiredValuesList(String reguiredValuesList) {
		this.reguiredValuesList = reguiredValuesList;
	}

	public int getMinumumValue() {
		return minumumValue;
	}

	public void setMinumumValue(int minumumValue) {
		this.minumumValue = minumumValue;
	}

	public int getMaximumValue() {
		return maximumValue;
	}

	public void setMaximumValue(int maximumValue) {
		this.maximumValue = maximumValue;
	}

	public IParameterValidator getParameterValidator() {
		return parameterValidator;
	}

	public void setParameterValidator(IParameterValidator parameterValidator) {
		this.parameterValidator = parameterValidator;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	//Added .trim fields where validating empty strings with spaces
	public String getValue() {
		return value.trim();
	}

	public void setValue(String value) {
		this.value = value.trim();
	}

	public boolean isUserInputable() {
		return userInputable;
	}

	public void setUserInputable(boolean userInputable) {
		this.userInputable = userInputable;
	}

	public boolean isMultiSelect() {
		return multiSelect;
	}

	public void setMultiSelect(boolean multiSelect) {
		this.multiSelect = multiSelect;
	}

	public boolean isBlankEqualsAll() {
		return blankEqualsAll;
	}

	public void setBlankEqualsAll(boolean blankEqualsAll) {
		this.blankEqualsAll = blankEqualsAll;
	}

	public String getAllValue() {
		return allValue;
	}

	public String getQuotedAllValue() {
		if (this.value == null || this.value.trim().length() <= 0)
			allValue = "'Y'";
		else
			allValue = "''";
		return allValue;
	}

	public String getAllName() {
		return allName;
	}

	public void setAllName(String allName) {
		this.allName = allName;
	}

	public void setAllValue(String allValue) {
		this.allValue = allValue;
	}

	public boolean isCookieValue() {
		return cookieValue;
	}

	public void setCookieValue(boolean cookieValue) {
		this.cookieValue = cookieValue;
	}

	public String getSessionOrCookieKey() {
		return sessionOrCookieKey;
	}

	public void setSessionOrCookieKey(String sessionOrCookieKey) {
		this.sessionOrCookieKey = sessionOrCookieKey;
	}

	public String getPickList() {
		return pickList;
	}

	public void setPickList(String pickList) {
		this.pickList = pickList;
	}

	/**
	 * @return
	 */
	public String getQuotedValue() {
		String result;

		if (multiSelect) {
			// split the values at the comma, insert ' between the values and
			// glue them back together
			String[] splitValues = getValue().split(",");

			StringBuilder quotedValues = new StringBuilder();
			boolean firstValue = true;

			for (String splitValue : splitValues) {
				if (!firstValue) {
					quotedValues.append(",");
				}
				firstValue = false;

				quotedValues.append("'");
				quotedValues.append(splitValue.trim());
				quotedValues.append("'");
			}

			result = quotedValues.toString();
		} else if ((getValue() == null || getValue().trim().length() <= 0) && !multiSelect && blankEqualsAll) {
			result = "'%'";
		} else {
			result = "'" + getValue() + "'";
		}
		return result;
	}

	/**
	 * @param request
	 */
	public void populateRequestData(HttpServletRequest request) {

		// check to see if we have a value or if it can't be entered by the user
		if ((!userInputable || value == null || value.length() <= 0) && sessionOrCookieKey != null && sessionOrCookieKey.length() > 0) {

			// see if we get the value from the session or from a cookie
			if (cookieValue) {
				Cookie cookie = WebUtils.getCookie(request, sessionOrCookieKey);

				if (cookie != null) {
					value = cookie.getValue();
				}
			} else {
				String sessionObject = (String) WebUtils.getSessionAttribute(request, sessionOrCookieKey);

				if (sessionObject != null) {
					value = sessionObject;
				}
			}
		}
	}

	public int getSpecialValue() {
		return specialValue;
	}

	public void setSpecialValue(int specialValue) {
		this.specialValue = specialValue;
	}

}
