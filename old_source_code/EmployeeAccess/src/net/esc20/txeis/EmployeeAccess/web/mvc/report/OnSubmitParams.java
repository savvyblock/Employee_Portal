package net.esc20.txeis.EmployeeAccess.web.mvc.report;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;

public class OnSubmitParams implements ISubmitParams {
	
	private HttpServletRequest request;
	private HttpServletResponse response;
	private Object command;
	private BindException errors;

	/* (non-Javadoc)
	 * @see net.esc20.txeis.common.controllers.ISubmitParams#getRequest()
	 */
	public HttpServletRequest getRequest() {
		return request;
	}

	/* (non-Javadoc)
	 * @see net.esc20.txeis.common.controllers.ISubmitParams#setRequest(javax.servlet.http.HttpServletRequest)
	 */
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	/* (non-Javadoc)
	 * @see net.esc20.txeis.common.controllers.ISubmitParams#getResponse()
	 */
	public HttpServletResponse getResponse() {
		return response;
	}

	/* (non-Javadoc)
	 * @see net.esc20.txeis.common.controllers.ISubmitParams#setResponse(javax.servlet.http.HttpServletResponse)
	 */
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public Object getCommand() {
		return command;
	}

	public void setCommand(Object command) {
		this.command = command;
	}

	public BindException getErrors() {
		return errors;
	}

	public void setErrors(BindException errors) {
		this.errors = errors;
	}

	public OnSubmitParams() {
	}

	public OnSubmitParams(HttpServletRequest request, HttpServletResponse response, Object command,
			BindException errors) {
		
		this.request = request;
		this.response = response;
		this.command = command;
		this.errors = errors;
	}

}
