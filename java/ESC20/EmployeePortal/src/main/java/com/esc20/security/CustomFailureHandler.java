package com.esc20.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.esc20.dao.AppUserDao;
import com.esc20.nonDBModels.AuthenticationResponseType;
import com.esc20.util.SessionKeys;

public class CustomFailureHandler implements AuthenticationFailureHandler {

	@Autowired
	AppUserDao userDao;
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		String database = (String)request.getSession().getAttribute("districtId");
		String returnURL = "/"+request.getContextPath().split("/")[1]+"/login?distid=" + database;
		request.getSession().setAttribute("districtId", database);
		request.getSession().setAttribute("isUserLoginFailure", true);
		// ALC-26 Lock account on the 5th login failed
		String username = request.getParameter("username");
		if(exception instanceof DisabledException)
			request.getSession().setAttribute(SessionKeys.USER_LOGIN_ERROR_MSG, AuthenticationResponseType.Locked.toString());
 		else if(exception instanceof BadCredentialsException){
			if(this.updateUserPwdFailedAndLockUser(username) == true){
				request.getSession().setAttribute(SessionKeys.USER_LOGIN_ERROR_MSG, AuthenticationResponseType.WillLocked.toString());
			}else {
				request.getSession().setAttribute(SessionKeys.USER_LOGIN_ERROR_MSG, AuthenticationResponseType.BadPassword.toString());
			}
		}
 		else
 			request.getSession().setAttribute(SessionKeys.USER_LOGIN_ERROR_MSG, AuthenticationResponseType.Failure.toString());
		
        response.sendRedirect(returnURL);
	}
	// ALC-26 Lock account on the 5th login failed
	private boolean  updateUserPwdFailedAndLockUser(String username){
		userDao.updateUserPWDFailed(username);
		Integer passwordTryTimes = userDao.getUserPWDFailed(username);
		Integer lockTries = userDao.getLockTries();
		if(passwordTryTimes != null && passwordTryTimes >= lockTries){
			userDao.lockedSPUsers(username);
			return  true;
		}
		return false;
	}
}
