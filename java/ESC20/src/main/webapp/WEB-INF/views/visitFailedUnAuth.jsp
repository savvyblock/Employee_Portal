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
                  <h2 data-localize="label.exceptionOccurred"></h2>
                  <div class="formModule">
                    <p><span data-localize="label.fromModule"></span>: <b>${module}</b></p>
                    <p><span data-localize="label.actionInvoked"></span>: <b>${action}</b></p>
                    <p><span data-localize="label.message"></span>: <b>${errorMsg}</b></p>
        
                  </div>
                  <br/><br/>
                  <p data-localize="label.ifYouHaveBookMarked" class="btm-p">
                  
                  </p>
                  <a href="#" onclick="javascript :history.back(-1);">
                    <button class="btn btn-primary" data-localize="label.back"></button>
                  </a>
                </div>
                </div> 
              <!-- <a href="#" onClick="javascript :history.back(-1);">
                <button class="btn btn-primary" data-localize="label.back"></button>
              </a> -->
            </div>
         </div>
    </body>
</html>
