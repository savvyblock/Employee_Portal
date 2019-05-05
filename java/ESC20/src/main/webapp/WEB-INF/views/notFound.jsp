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
    <body class="account-wrap notFound">
        <%@ include file="commons/bar-account.jsp"%>
         <div class="notFoundBox content-body"  tabindex="-1">
            <div class="inner">
              <img src="/<%=request.getContextPath().split("/")[1]%>/images/404.png" alt="${sessionScope.languageJSON.label.errorImg}">
              <p class="word"></p>
              <a href="#" onClick="javascript :history.back(-1);">
                <button class="btn btn-primary">${sessionScope.languageJSON.label.back}</button>
              </a>
            </div>
         </div>
    </body>
</html>
