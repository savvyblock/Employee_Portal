package com.esc20.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

public class CustomFailureHandler implements AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		String database = (String)request.getSession().getAttribute("districtId");
		String returnURL = "/"+request.getContextPath().split("/")[1]+"/login?distid=" + database;
		request.getSession().setAttribute("districtId", database);
		request.getSession().setAttribute("isUserLoginFailure", true);
        response.sendRedirect(returnURL);
	}
}
