<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="en">
    <head>
        <title data-localize="headTitle.notFound"></title>
        <meta http-equiv="Content-Type" content="text/html;charset=utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <%@ include file="commons/header.jsp"%>
    </head>
    <body class="account-wrap notFound">
        <%@ include file="commons/bar-account.jsp"%>
         <div class="notFoundBox content-body"  tabindex="-1">
            <div class="inner">
              <div class="noFoundPage">
                <div>
                  <h2>Exception occurred</h2>
                  <p><span>From Module: </span> <span>${module}</span></p>
                  <p><span>Action invoked: </span> <span>${action}</span></p>
                  <p><span>Message: </span> <span>${errorMsg}</span></p>
                  <br/><br/>
                  <p>
                    If you have book-marked this URL, please remove it as this is related to submissions, necessary parameters will need to be provided in order for the method to function.
                  </p>
                </div>
              </div> 
              <a href="#" onClick="javascript :history.back(-1);">
                <button class="btn btn-primary" data-localize="label.back"></button>
              </a>
            </div>
         </div>
    </body>
</html>
