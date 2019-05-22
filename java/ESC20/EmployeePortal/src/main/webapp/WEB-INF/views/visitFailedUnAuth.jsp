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
              <div class="noFoundPage">
                <div>
                  <h2>${sessionScope.languageJSON.label.exceptionOccurred}</h2>
                  <div class="formModule">
                    <p><span>${sessionScope.languageJSON.label.fromModule}</span>: <b>${module}</b></p>
                    <p><span>${sessionScope.languageJSON.label.actionInvoked}</span>: <b>${action}</b></p>
                    <p><span>${sessionScope.languageJSON.label.message}</span>: <b>${errorMsg}</b></p>
        
                  </div>
                  <br/><br/>
                  <p class="btm-p">
                    ${sessionScope.languageJSON.label.ifYouHaveBookMarked}
                  </p>
                  <a href="#" onclick="javascript :history.back(-1);"  class="btn btn-primary">
                    ${sessionScope.languageJSON.label.back}
                  </a>
                </div>
                </div> 
            </div>
         </div>
    </body>
</html>
