package com.esc20.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.csrf.MissingCsrfTokenException;

public class CustomAccessDeniedHandler extends AccessDeniedHandlerImpl {
	
    @Override
    public void handle(HttpServletRequest req, HttpServletResponse res, AccessDeniedException exception) throws IOException, ServletException {
        if (exception instanceof MissingCsrfTokenException && isSessionInvalid(req)) {
        	res.sendRedirect("/"+req.getContextPath().split("/")[1]+"/login");
        }
        super.handle(req, res, exception);
    }
    private boolean isSessionInvalid(HttpServletRequest req) {
        try {
            HttpSession session = req.getSession(false);
            return session == null || !req.isRequestedSessionIdValid();
        }
        catch (IllegalStateException ex) {
            return true;
        }
    }
}