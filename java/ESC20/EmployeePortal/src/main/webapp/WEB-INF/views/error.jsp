<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html> 
<html lang="en">
    <head>
        <title>${sessionScope.languageJSON.headTitle.notFound}</title>
        <meta http-equiv="Content-Type" content="text/html;charset=utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <%@ include file="commons/header.jsp"%>
    </head>
    <body class="account-wrap">
        <%@ include file="commons/bar-account.jsp"%>
         <div class="notFoundBox content-body"  tabindex="-1">
            <div class="inner">
              <img src="/<%=request.getContextPath().split("/")[1]%>/images/error.png"  alt="${sessionScope.languageJSON.label.errorImg}">
              <p class="word">
                    ${sessionScope.languageJSON.label.pleaseTryAgain}
              </p>
               <%
                  java.lang.Exception ex = (java.lang.Exception)request.getAttribute("ex");
                  java.io.ByteArrayOutputStream  ostr = new java.io.ByteArrayOutputStream ();
                  if (ex != null)  ex.printStackTrace(new java.io.PrintStream(ostr));
              %>
              <p>&nbsp;</p>
              <div style="display: block; color: #ffffff;">
              <%=ostr.toString()%>
              </div>
            </div>
         </div>
    </body>
</html>