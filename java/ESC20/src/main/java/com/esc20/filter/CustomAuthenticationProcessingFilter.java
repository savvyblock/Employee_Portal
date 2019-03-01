package com.esc20.filter;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.web.servlet.ModelAndView;

import com.esc20.service.IndexService;
import com.esc20.util.DataSourceContextHolder;

public class CustomAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

	protected CustomAuthenticationProcessingFilter(String defaultFilterProcessesUrl) {
		super(defaultFilterProcessesUrl);
	}

	@Autowired
	private IndexService indexService;

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		String distid = request.getParameter("distid");
		String sessionValue = (String)request.getSession().getAttribute("district");
		if(sessionValue == null) {
			if(distid != null && distid.matches("\\d{6}")){
				Cookie cookie = new Cookie("district",distid);
				response.addCookie(cookie);
				request.getSession().setAttribute("district", distid);
			}
			else
			{
				Cookie[] cookies = request.getCookies();
				if(cookies != null){
					for(Cookie cookie : cookies) { 
					    if (cookie.getName().equals("district")) {
					    	request.getSession().setAttribute("district", cookie.getValue());
					    }
					}
				}
				
			}
		}
		DataSourceContextHolder.setDataSourceType("db"+(String)request.getSession().getAttribute("district"));
		return null;
	}
	
	
}
