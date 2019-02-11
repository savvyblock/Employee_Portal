package net.esc20.txeis.EmployeeAccess.web.mvc;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

public class ExceptionResolver extends SimpleMappingExceptionResolver {

	private String _userErrorView;
	
	public ExceptionResolver() {
		setDefaultErrorView("defaultView");
	}
	
	public final void setUserErrorView(String pValue) {
		_userErrorView = pValue;
	}

	public final String getUserErrorView() {
		return _userErrorView;
	}
	
	@Override
	protected String determineViewName(Exception ex, HttpServletRequest pRequest) {
		if ((ex instanceof UserException) && (null != getUserErrorView())) {
			return getUserErrorView();
		}
		return super.determineViewName(ex, pRequest);
	}
	
	@Override
	protected String buildLogMessage(Exception e, HttpServletRequest pRequest) {
		return String.format("[Instance ID: %s] Exception for [%s] at [%s] %s", pRequest.getAttribute("instanceId"), getUsername(), pRequest.getRequestURL(), getOtherLogHeading(e, pRequest));
	}
	
	private String getUsername() {
		if (null != SecurityContextHolder.getContext().getAuthentication()) {
			return SecurityContextHolder.getContext().getAuthentication().getName();
		}
		return "(not logged in)";
	}
	
	
	@Override
	protected void logException(Exception ex, HttpServletRequest request) {
		if (!(ex instanceof UserException) || ((UserException)ex).isLogged()) {
			super.logException(ex, request);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		// Exception should already be a request attribute, but, just to make sure, because the ErrorView relies on it
		request.setAttribute("exception", ex);
		request.setAttribute("instanceId", UUID.randomUUID());
		ModelAndView output = super.resolveException(request, response, handler, ex);
		request.setAttribute("otherErrorHTML", getOtherErrorHTML());
		if ("employeeaccess-error".equals(output.getViewName())) {
			output.setView(new ErrorView());
		}
		return output;
	}
	
	protected String getOtherLogHeading(Exception e, HttpServletRequest pRequest) {
		return "";
	}
	
	protected String getOtherErrorHTML() {
		return "";
	}
}
