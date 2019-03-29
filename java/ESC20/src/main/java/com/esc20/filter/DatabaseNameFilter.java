package com.esc20.filter;

import java.io.File;
import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import com.esc20.util.DataSourceContextHolder;


public class DatabaseNameFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
			String database = (String)request.getSession().getAttribute("districtId");
			try
			{
				String distid = request.getParameter("distid");
				if (distid != null && distid.matches("\\d{6}")) {
					request.getSession().setAttribute("districtId", distid);
					request.getSession().setAttribute("isSwitched", true);
				}

				DataSourceContextHolder.setDataSourceType("java:jboss/DB"+(String)request.getSession().getAttribute("districtId"));
				database = (String)request.getSession().getAttribute("districtId");
				if (database == null || "".equals(database)) {
					logger.error("Unable to set county district.");
				}
				Boolean isTimeOut = (Boolean)request.getSession().getAttribute("isTimeOut");
				if(isTimeOut==null)
					isTimeOut = false;
				DataSourceContextHolder.setIstimeout(isTimeOut);
			}
			catch(ClassCastException ex)
			{
				logger.error(ex);
			}
	

		chain.doFilter(request, response);

	}

}
