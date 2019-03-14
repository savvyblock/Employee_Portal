package com.esc20.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.esc20.util.DataSourceContextHolder;

public class CustomSuccessLogoutHandler implements LogoutSuccessHandler{

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		String database = DataSourceContextHolder.getDataSourceType();
		database = database.split("/")[1];
		database = database.substring(2);
		Boolean isTimeOut = DataSourceContextHolder.getIstimeout();
		
        String returnURL = "/"+request.getContextPath().split("/")[1]+"/login?distid=" + database;
        if(isTimeOut)
        	returnURL += "&isTimeOut=true";
        request.getSession().setAttribute("districtId", database);
        response.sendRedirect(returnURL);
	}

}
