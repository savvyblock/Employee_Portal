<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="en">
    <head>
        <title data-localize="headTitle.notFound"></title>
        <meta http-equiv="Content-Type" content="text/html;charset=utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <%@ include file="commons/header.jsp"%>
    </head>
    <body class="account-wrap">
        <%@ include file="commons/bar-account.jsp"%>
         <div class="notFoundBox">
            <div class="inner">
              <img src="/<%=request.getContextPath().split("/")[1]%>/images/error.png" alt="">
              <p class="word" data-localize="label.pleaseTryAgain"></p>
            </div>
         </div>
    </body>
</html>