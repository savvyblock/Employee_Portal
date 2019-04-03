package com.esc20.security;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import org.springframework.security.web.util.matcher.RequestMatcher;


public class CustomCsrfSecurityRequestMatcher implements RequestMatcher {  
    private Pattern allowedMethods = Pattern.compile("^(GET|HEAD|TRACE|OPTIONS)$");
   
    @Override  
    public boolean matches(HttpServletRequest request) {
    	if (request.getRequestURI().endsWith("UnprotectedPDF"))
    		return false;
        return !allowedMethods.matcher(request.getMethod()).matches();
    } 
}