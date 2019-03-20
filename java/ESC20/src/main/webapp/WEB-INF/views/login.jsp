<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="en">
    <head>
        <title data-localize="headTitle.login"></title>
        <meta http-equiv="Content-Type" content="text/html;charset=utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <%@ include file="commons/header.jsp"%>
    </head>
    <body class="account-wrap loginPage">
            <%@ include file="commons/bar-account.jsp"%>
            <div class="account-top content-body"  tabindex="-1">
                <div class="account-inner loginBox">
                    <div class="account-left">
                    </div>
                    <div class="account-box">
                        <div class="account-logo">
                            <img src="/<%=request.getContextPath().split("/")[1]%>/images/logo-account.png" alt="" data-localize="logoName.esc">
                        </div>
                        <form id="loginForm" method="post" class="card bv-form" action="/<%=request.getContextPath().split("/")[1]%>/loginEA">
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
                            <p class="error-hint hide errorMessage" id="errorMessage" data-localize="validator.usernameOrPasswordError"  role="alert" aria-atomic="true"></p>
                            <p class="error-hint hide incorrectMessage" id="incorrectMessage" data-localize="validator.usernameOrPasswordIncorrect"  role="alert" aria-atomic="true"></p>
                            <p class="error-hint hide authenticateFailed" id="authenticateFailed" data-localize="validator.authenticateFailed"  role="alert" aria-atomic="true"></p>
                            <c:if test="${times3}">
                                <p class="error-hint" data-localize="validator.haveEnteredThree"  role="alert" aria-atomic="true"></p>
                            </c:if>
                            <c:if test="${resetLocked}">
                                <p class="error-hint" data-localize="validator.resetLocked" role="alert" aria-atomic="true"></p>
                            </c:if>
                            <c:if test="${userNotRegistered}">
                                <p class="error-hint" data-localize="validator.userNotRegistered" role="alert" aria-atomic="true"></p>
                            </c:if>
                            <c:if test="${resetPsw!=null && resetPsw=='resetPswSuccess'}">
		                        <div class="valid-wrap error-hint" data-localize="validator.resetPswSuccess" role="alert" aria-atomic="true"></div>
		                    </c:if>
		                    <c:if test="${resetPsw!=null && resetPsw=='resetPswFaild'}">
		                        <div class="valid-wrap error-hint" data-localize="validator.resetPswFaild" role="alert" aria-atomic="true"></div>
		                    </c:if>
                            <div class="form-group clearfix">
                                <a href="https://docs.google.com/forms/d/e/1FAIpQLScVEpUzBsCM1XLzRVieEoJAaFWRZoPEmUU2fZcWz2TyDTsb7g/viewform?usp=pp_url&entry.372715739=Employee+Access+(TxEIS)" target="_blank" data-localize="label.contactUs"></a>
                                <div class="pull-right">
                                    <a href="/<%=request.getContextPath().split("/")[1]%>/resetPassword/forgetPassword" data-localize="label.forgotPassword"></a>
                                </div>
                            </div>
                            <div class="form-group account-btn">
                                <button id="signin" type="submit" class="btn btn-primary" data-localize="label.login"></button>
                            </div>
                            <div class="text-center">
                                <a class="a-line" href="/<%=request.getContextPath().split("/")[1]%>/createUser/searchUser" data-localize="label.newUser">
                                </a>
                            </div>
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        </form>
                        
                    </div>
                </div>
                <div class="account-inner logBackBox">
                    <h1 class="logBackTitle" data-localize="label.sessionTimeOut"></h1>
                    <div class="logBackWord" data-localize="label.sessionTimeOutWord"> </div>
                    <div class="account-box">
                        <form id="loginBackForm" class="card" method="post" action="/<%=request.getContextPath().split("/")[1]%>/loginEA">
                        	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
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
                            <p class="error-hint hide errorMessage" id="errorMessage" data-localize="validator.usernameOrPasswordError"  role="alert" aria-atomic="true"></p>
                            <p class="error-hint hide incorrectMessage" id="incorrectMessage" data-localize="validator.usernameOrPasswordIncorrect"  role="alert" aria-atomic="true"></p>
                            <p class="error-hint hide authenticateFailed" id="authenticateFailed" data-localize="validator.authenticateFailed"  role="alert" aria-atomic="true"></p>
                            <c:if test="${times3}">
                                <p class="error-hint" id="haveEnteredThree" data-localize="validator.haveEnteredThree" role="alert" aria-atomic="true"></p>
                            </c:if>
                            <c:if test="${resetLocked}">
                                <p class="error-hint" data-localize="validator.resetLocked" role="alert" aria-atomic="true"></p>
                            </c:if>
                            <c:if test="${userNotRegistered}">
                                <p class="error-hint" data-localize="validator.userNotRegistered" role="alert" aria-atomic="true"></p>
                            </c:if>
                            <c:if test="${resetPsw!=null && resetPsw=='resetPswSuccess'}">
		                        <div class="valid-wrap error-hint" data-localize="validator.resetPswSuccess" role="alert" aria-atomic="true"></div>
		                    </c:if>
		                    <c:if test="${resetPsw!=null && resetPsw=='resetPswFaild'}">
		                        <div class="valid-wrap error-hint" data-localize="validator.resetPswFaild" role="alert" aria-atomic="true"></div>
		                    </c:if>
                            <div class="form-group account-btn">
                                <button id="signin" type="submit" class="btn btn-primary" data-localize="label.login"></button>
                            </div>
                            
                        </form>
                        
                    </div>
                </div>
            </div>
    </body>
    
    <script src="/<%=request.getContextPath().split("/")[1]%>/js/viewJs/login.js"></script>

</html>
