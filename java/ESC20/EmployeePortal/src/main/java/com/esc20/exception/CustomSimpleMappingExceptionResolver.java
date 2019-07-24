package com.esc20.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class CustomSimpleMappingExceptionResolver extends SimpleMappingExceptionResolver {
    private static final Logger logger = LoggerFactory.getLogger(CustomSimpleMappingExceptionResolver.class);

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, @Nullable Object handler, Exception ex) {

        logger.error("========[EMPLOYEE PORTAL]=====EXCEPTION============START====" + ex.getMessage());
        ex.printStackTrace();
        logger.error("========[EMPLOYEE PORTAL]=====EXCEPTION==============END====" + ex.getMessage());

        String viewName = this.determineViewName(ex, request);
        if(viewName != null) {
            Integer statusCode = this.determineStatusCode(request, viewName);
            if(statusCode != null) {
                this.applyStatusCodeIfPossible(request, response, statusCode.intValue());
            }

            return this.getModelAndView(viewName, ex, request);
        } else {
            return null;
        }
    }
}
