<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="en">
    <head>
        <title data-localize="headTitle.login"></title>
        <meta http-equiv="Content-Type" content="text/html;charset=utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <%@ include file="commons/header.jsp"%>
    </head>
    <body class="account-wrap">
            <%@ include file="commons/bar-account.jsp"%>
            <div class="account-top content-body"  tabindex="-1">
                <div class="account-inner logBackBox">
                    <h1 class="logBackTitle" data-localize="label.sessionTimeOut"></h1>
                    <div class="logBackWord" data-localize="label.sessionTimeOutWord"> </div>
                    <div class="account-box">
                        <form id="loginForm" class="card" method="get">
                            <h1 class="title" data-localize="label.pleaseSignIn"></h1>
                            <div class="form-group">
                                <label class="form-title" for="inputEmail" data-localize="label.username"></label>
                                <div class="valid-wrap icon-group">
                                    <i class="fa fa-user left-icon"></i>
                                    <input type="text" id="inputEmail" class="form-control" placeholder="" data-localize="label.username" name="username">
                                </div>
                                
                            </div>
                            <div class="form-group">
                                <label  class="form-title" for="inputPassword" data-localize="label.password"></label>
                                <div class="valid-wrap icon-group">
                                    <i class="fa fa-lock left-icon"></i>
                                    <input type="password" id="inputPassword" class="form-control" placeholder="" name="password" data-localize="label.password">
                                </div>
                                
                            </div>
                            <p class="error-hint hide" id="errorMessage" data-localize="validator.usernameOrPasswordError"></p>
                            <p class="error-hint hide" id="incorrectMessage" data-localize="validator.usernameOrPasswordIncorrect"></p>
                            <c:if test="${resetPsw!=null && resetPsw=='resetPswSuccess'}">
		                        <div class="valid-wrap error-hint" data-localize="validator.resetPswSuccess"></div>
		                    </c:if>
		                    <c:if test="${resetPsw!=null && resetPsw=='resetPswFaild'}">
		                        <div class="valid-wrap error-hint" data-localize="validator.resetPswFaild"></div>
		                    </c:if>
                            <div class="form-group account-btn">
                                <button id="signin" type="submit" class="btn btn-primary" name="signin" data-localize="label.login"></button>
                            </div>
                            
                        </form>
                        
                    </div>
                </div>
            </div>
    </body>
    <script src="/<%=request.getContextPath().split("/")[1]%>/js/viewJs/index.js"></script>

</html>
