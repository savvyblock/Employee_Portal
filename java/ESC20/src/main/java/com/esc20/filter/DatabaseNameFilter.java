package com.esc20.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import com.esc20.nonDBModels.District;
import com.esc20.util.DataSourceContextHolder;

public class DatabaseNameFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		
			DataSourceContextHolder.clearDataSourceType();
		
			try
			{
				String distid = request.getParameter("distid");
				String sessionValue = (String)request.getSession().getAttribute("districtId");
				if(sessionValue == null || !sessionValue.equals(distid)) {
					if(distid != null && distid.matches("\\d{6}")){
						Cookie cookie = new Cookie("districtId",distid);
						response.addCookie(cookie);
						request.getSession().setAttribute("districtId", distid);
					}
					else
					{
						Cookie[] cookies = request.getCookies();
						if(cookies != null){
							for(Cookie cookie : cookies) { 
							    if (cookie.getName().equals("districtId")) {
							    	request.getSession().setAttribute("districtId", cookie.getValue());
							    }
							}
						}
						
					}
					DataSourceContextHolder.setDataSourceType("DB"+(String)request.getSession().getAttribute("districtId"));
				}
				
				String database = (String)request.getSession().getAttribute("districtId");
				
				// This should only occur if setting the County District fails.  If so, clear the database context and throw a Program Error. 
				if (database == null || "".equals(DataSourceContextHolder.getDataSourceType())) {
					DataSourceContextHolder.clearDataSourceType();
					logger.error("Unable to set county district.");
				}
				
			}
			catch(ClassCastException ex)
			{
				logger.error(ex);
			}
	

		chain.doFilter(request, response);

	}

}
