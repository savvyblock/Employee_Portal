package net.esc20.txeis.EmployeeAccess.web.mvc;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.View;

public class ErrorView implements View {

	private static String pageString;
	private static String instanceString = "<p class=\"error_instance\">Instance ID: %s</p>";
	private static String defaultErrorMessage = "A program error has occurred. Please contact your system administrator.";
	static {
		StringBuffer pageBuffer = new StringBuffer()
			.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n")
			.append("<html>\n")
			.append("<head>\n")
			.append("   <title>%s Occurred</title>\n")
			.append("   <meta http-equiv=\"pragma\" content=\"no-cache\">\n")
			.append("   <meta http-equiv=\"cache-control\" content=\"no-cache\">\n")
			.append("</head>\n")
			.append("<body>\n")
			.append("   <h1>%s</h1>\n")
			.append("   <p>%s</p>\n")
			.append("   %s\n")
			.append("   %s\n")
			.append("</body>\n")
			.append("</html>\n");
		pageString = pageBuffer.toString();
	}
	
	public String getContentType() {
		return "text/html";
	}

	@SuppressWarnings("unchecked")
	public void render(Map pModel, HttpServletRequest pRequest, HttpServletResponse pResponse) throws Exception {	
		pResponse.setHeader("content-type", "text/html");
		pResponse.getWriter().print(String.format(pageString, 
				getErrorTitle(pRequest),
				getErrorTitle(pRequest),
				getErrorMessage(pRequest),
				getInstanceString(pRequest), getOtherHTML(pRequest)));
	}
	
	// The following methods get the model values from the request's attributes instead of directly from the model.
	// The reason for this is so that we can accommodate referencing this ErrorView from inside a tile,
	// such as what is needed for Health.
	// This works because Spring uses the request's attributes to store the model values.
	// PS 7/14/09
	
	@SuppressWarnings("unchecked")
	private static String getErrorTitle(HttpServletRequest pRequest) {
		return (getException(pRequest) instanceof UserException)
			? "Error" : "Program Error";
	}
	
	@SuppressWarnings("unchecked")
	private static String getErrorMessage(HttpServletRequest pRequest) {
		return (getException(pRequest) instanceof UserException)
			? getException(pRequest).getMessage() : defaultErrorMessage;
	}

	@SuppressWarnings("unchecked")
	private static Exception getException(HttpServletRequest pRequest) {
		return ((Exception)pRequest.getAttribute("exception"));
	}
	
	private static String getOtherHTML(HttpServletRequest pRequest) {
		if (null == pRequest.getAttribute("otherErrorHTML")) {
			return "";
		}
		return ((String)pRequest.getAttribute("otherErrorHTML"));
	}

	@SuppressWarnings("unchecked")
	private static String getInstanceString(HttpServletRequest pRequest) {
		return (!(getException(pRequest) instanceof UserException) || ((UserException)getException(pRequest)).isLogged()) 
				? String.format(instanceString, pRequest.getAttribute("instanceId")) 
				: "";
	}

}
