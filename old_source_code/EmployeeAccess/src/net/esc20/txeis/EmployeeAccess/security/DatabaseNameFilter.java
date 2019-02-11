package net.esc20.txeis.EmployeeAccess.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.esc20.txeis.EmployeeAccess.domainobject.DatabaseContextHolder;

import org.springframework.web.filter.OncePerRequestFilter;

public class DatabaseNameFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		
			DatabaseContextHolder.clearDatabase();
		
			try
			{
				String database = (String)request.getSession().getAttribute("district");
				
				DatabaseContextHolder.setCountyDistrict(database);
				
				// This should only occur if setting the County District fails.  If so, clear the database context and throw a Program Error. 
				if (database == null || !database.equals(DatabaseContextHolder.getCountyDistrict())) {
					DatabaseContextHolder.clearDatabase();
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
