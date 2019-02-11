package net.esc20.txeis.EmployeeAccess.web.mvc;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.esc20.txeis.EmployeeAccess.domainobject.DatabaseContextHolder;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

public class LoginController extends SimpleFormController{
	
	public ModelAndView handleRequest(HttpServletRequest request,
            HttpServletResponse response) {
		String distid = request.getParameter("distid");
		
		if(distid != null && distid.matches("\\d{6}"))
		{
			Cookie cookie = new Cookie("district",distid);
			response.addCookie(cookie);
			request.getSession().setAttribute("district", distid);
			DatabaseContextHolder.setCountyDistrict((String)request.getSession().getAttribute("district"));
			return new ModelAndView("login");
		}
		else
		{
			Cookie[] cookies = request.getCookies();

			if(cookies != null)
			{
			    for(Cookie cookie : cookies) 
			    { 
			    	if (cookie.getName().equals("district")) 
			    	{
			    		request.getSession().setAttribute("district", cookie.getValue());
			    	}
			    }
			}
			
			DatabaseContextHolder.setCountyDistrict((String)request.getSession().getAttribute("district"));
			return new ModelAndView("login");
		}
    }

}