<%@ page language="java" pageEncoding="ISO-8859-1"%>

<%
	 String username = (String)request.getSession().getAttribute("currentUser_userName");
	 String password = (String)request.getSession().getAttribute("currentUser_password");
	 response.sendRedirect(request.getContextPath() + "/j_spring_security_check?j_username=" +username+"&j_password="+password); %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>Redirecting to Employee Access Main Page...</title>
  </head>
  <body>
  </body>
</html>
