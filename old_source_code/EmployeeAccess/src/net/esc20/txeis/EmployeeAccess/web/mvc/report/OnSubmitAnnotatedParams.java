package net.esc20.txeis.EmployeeAccess.web.mvc.report;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.support.SessionStatus;

public class OnSubmitAnnotatedParams implements ISubmitParams{
	
	private HttpServletRequest request;
	private HttpServletResponse response;
	private Object command;
	private BindingResult errors;
	private SessionStatus sessionStatus;

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public Object getCommand() {
		return command;
	}

	public void setCommand(Object command) {
		this.command = command;
	}

	public BindingResult getErrors() {
		return errors;
	}

	public void setErrors(BindingResult errors) {
		this.errors = errors;
	}

	public SessionStatus getSessionStatus() {
		return sessionStatus;
	}

	public void setSessionStatus(SessionStatus sessionStatus) {
		this.sessionStatus = sessionStatus;
	}

	public OnSubmitAnnotatedParams() {
	}

	public OnSubmitAnnotatedParams(HttpServletRequest request, HttpServletResponse response, Object command,
			BindingResult errors, SessionStatus sessionStatus) {
		
		this.request = request;
		this.response = response;
		this.command = command;
		this.errors = errors;
		this.sessionStatus = sessionStatus;
	}

}
