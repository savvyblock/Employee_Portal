package com.esc20.filter;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.esc20.util.DataSourceContextHolder;

import net.sf.json.JSONObject;


public class DatabaseNameFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
			String database = (String)request.getSession().getAttribute("districtId");
			//added for multi-language change
			String language = (String)request.getSession().getAttribute("language");
			try
			{
				String districtIdInSession = (String)request.getSession().getAttribute("districtId");
				String distid = request.getParameter("distid");
				if (distid != null && distid.matches("\\d{6}")) {
					Cookie cookie = new Cookie("district",distid);
					cookie.setMaxAge(60*60*24);
					response.addCookie(cookie);
					System.out.println("cookie is set "+ cookie.getValue());
					
					if(!distid.equals(districtIdInSession)) {
						Enumeration em = request.getSession().getAttributeNames();
						while (em.hasMoreElements()) {
							request.getSession().removeAttribute(em.nextElement().toString());
						}
					}
					
					request.getSession().setAttribute("districtId", distid);
					request.getSession().setAttribute("isSwitched", true);
				}
				database = (String)request.getSession().getAttribute("districtId");
				if (database == null || "".equals(database)) {
					Cookie[] cookies = request.getCookies();
					if(cookies != null)
					{
					    for(Cookie cookie : cookies) 
					    { 
					    	if (cookie.getName().equals("district")) 
					    	{
					    		System.out.println("load from cookies "+cookie.getValue());
					    		
					    		if(!distid.equals(districtIdInSession)) {
									Enumeration em = request.getSession().getAttributeNames();
									while (em.hasMoreElements()) {
										request.getSession().removeAttribute(em.nextElement().toString());
									}
								}
					    		
					    		request.getSession().setAttribute("districtId", cookie.getValue());
					    		request.getSession().setAttribute("isSwitched", true);
					    		database = cookie.getValue();
					    	}
					    }
					}
				}
				DataSourceContextHolder.setDataSourceType("java:jboss/DB"+database);
				if (database == null || "".equals(database)) {
					logger.error("Unable to set county district.");
				}
				
				Boolean isTimeOut = (Boolean)request.getSession().getAttribute("isTimeOut");
				if(isTimeOut==null)
					isTimeOut = false;
				DataSourceContextHolder.setIstimeout(isTimeOut);
				//added for multi-language change
				if(language==null||("").equals(language)) {
					language="en";
					request.getSession().setAttribute("language",language);
					String path = request.getSession().getServletContext().getRealPath("/") +"/static/js/lang/text-"+language+".json";
					File file = new File(path);
					String input = FileUtils.readFileToString(file, "UTF-8");
					JSONObject jsonObject = JSONObject.fromObject(input);
					request.getSession().setAttribute("languageJSON", jsonObject);
					
					//these strings should never be translated per esc20
					String path1 = request.getSession().getServletContext().getRealPath("/") +"/static/js/constant/text-non-translate.json";
					File file1 = new File(path1);
					String input1 = FileUtils.readFileToString(file1, "UTF-8");
					JSONObject jsonObject1 = JSONObject.fromObject(input1);
					request.getSession().setAttribute("constantJSON", jsonObject1);
				}
			}
			catch(ClassCastException ex)
			{
				logger.error(ex);
			}
	

		chain.doFilter(request, response);

	}

}
