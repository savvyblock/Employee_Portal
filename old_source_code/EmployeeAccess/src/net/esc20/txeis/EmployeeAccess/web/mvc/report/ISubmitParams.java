package net.esc20.txeis.EmployeeAccess.web.mvc.report;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ISubmitParams {

	public abstract HttpServletRequest getRequest();

	public abstract void setRequest(HttpServletRequest request);

	public abstract HttpServletResponse getResponse();

	public abstract void setResponse(HttpServletResponse response);

}