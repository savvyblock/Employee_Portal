<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html> 
<html lang="en">
    <head>
        <title>${sessionScope.languageJSON.headTitle.login}</title>
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
                            <img src="/<%=request.getContextPath().split("/")[1]%>/images/logo-account.png" alt="${sessionScope.languageJSON.logoName.esc}">
                        </div>
                        <form id="loginForm" method="post" class="card bv-form" action="/<%=request.getContextPath().split("/")[1]%>/loginEA">
                            <h1 class="title">${sessionScope.languageJSON.label.pleaseSignIn}</h1>
                            <div class="form-group">
                                <label class="form-title" for="inputEmail">${sessionScope.languageJSON.label.username}</label>
                                <div class="valid-wrap icon-group">
                                    <i class="fa fa-user left-icon"></i>
                                    <input type="text" id="inputEmail" class="form-control" placeholder="${sessionScope.languageJSON.label.username}"name="username"/>
                                </div>
                                
                            </div>
                            <div class="form-group">
                                <label  class="form-title" for="inputPassword">${sessionScope.languageJSON.label.password} ${isUserLoginFailure}</label>
                                <div class="valid-wrap icon-group">
                                    <i class="fa fa-lock left-icon"></i>
                                    <input type="password" id="inputPassword" class="form-control" placeholder="${sessionScope.languageJSON.label.password}" name="password"/>
                                </div>
                                
                            </div>
                            
                            <p class="error-hint hide errorMessage" role="alert" aria-atomic="true">${sessionScope.languageJSON.validator.usernameOrPasswordError}</p>
                            <p class="error-hint hide incorrectMessage" role="alert" aria-atomic="true">>${sessionScope.languageJSON.validator.usernameOrPasswordIncorrect}</p>
                            <c:if test="${isUserLoginFailure=='true'}">
                                <p class="error-hint authenticateFailed" role="alert" aria-atomic="true">${sessionScope.languageJSON.validator.authenticateFailed}</p>
                            </c:if>                            
                            <c:if test="${times3}">
                                <p class="error-hint" role="alert" aria-atomic="true">${sessionScope.languageJSON.validator.haveEnteredThree}</p>
                            </c:if>
                            <c:if test="${resetLocked}">
                                <p class="error-hint" role="alert" aria-atomic="true">${sessionScope.languageJSON.validator.resetLocked}</p>
                            </c:if>
                            <c:if test="${userNotRegistered}">
                                <p class="error-hint" role="alert" aria-atomic="true">${sessionScope.languageJSON.validator.userNotRegistered}</p>
                            </c:if>
                            <c:if test="${resetPsw!=null && resetPsw=='resetPswSuccess'}">
		                        <div class="valid-wrap error-hint" role="alert" aria-atomic="true">${sessionScope.languageJSON.validator.resetPswSuccess}</div>
		                    </c:if>
		                    <c:if test="${resetPsw!=null && resetPsw=='resetPswFaild'}">
		                        <div class="valid-wrap error-hint" role="alert" aria-atomic="true">${sessionScope.languageJSON.validator.resetPswFaild}</div>
		                    </c:if>
                            <div class="form-group clearfix">
                                <a href="https://docs.google.com/forms/d/e/1FAIpQLScVEpUzBsCM1XLzRVieEoJAaFWRZoPEmUU2fZcWz2TyDTsb7g/viewform?usp=pp_url&entry.372715739=Employee+Access+(TxEIS)" target="_blank">${sessionScope.languageJSON.label.contactUs}</a>
                                <div class="pull-right">
                                    <a href="/<%=request.getContextPath().split("/")[1]%>/resetPassword/forgetPassword">${sessionScope.languageJSON.label.forgotPassword}</a>
                                </div>
                            </div>
                            <div class="form-group account-btn">
                                <button id="signin" type="submit" class="btn btn-primary">${sessionScope.languageJSON.label.login}</button>
                            </div>
                            <div class="text-center">
                                <a class="a-line" href="/<%=request.getContextPath().split("/")[1]%>/createUser/searchUser">${sessionScope.languageJSON.label.newUser}
                                </a>
                            </div>
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        </form>
                        
                    </div>
                </div>
                <div class="account-inner logBackBox hide">
                    <h1 class="logBackTitle">${sessionScope.languageJSON.label.sessionTimeOut}</h1>
                    <div class="logBackWord">${sessionScope.languageJSON.label.sessionTimeOutWord}</div>
                    <div class="account-box">
                        <form id="loginBackForm" class="card" method="post" action="/<%=request.getContextPath().split("/")[1]%>/loginEA">
                        	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <h1 class="title">${sessionScope.languageJSON.label.pleaseSignIn}</h1>
                            <div class="form-group">
                                <label class="form-title" for="inputEmailSession">${sessionScope.languageJSON.label.username}</label>
                                <div class="valid-wrap icon-group">
                                    <i class="fa fa-user left-icon"></i>
                                    <input type="text" id="inputEmailSession" class="form-control" placeholder="${sessionScope.languageJSON.label.username}" name="username"/>
                                </div>
                                
                            </div>
                            <div class="form-group">
                                <label  class="form-title" for="inputPasswordSession">${sessionScope.languageJSON.label.password}</label>
                                <div class="valid-wrap icon-group">
                                    <i class="fa fa-lock left-icon"></i>
                                    <input type="password" id="inputPasswordSession" class="form-control" placeholder="${sessionScope.languageJSON.label.password}" name="password">
                                </div>
                                
                            </div>
                            <p class="error-hint hide errorMessage" role="alert" aria-atomic="true">${sessionScope.languageJSON.validator.usernameOrPasswordError}</p>
                            <p class="error-hint hide incorrectMessage" role="alert" aria-atomic="true">${sessionScope.languageJSON.validator.usernameOrPasswordIncorrect}</p>
                            <c:if test="${isUserLoginFailure=='true'}">
                                <p class="error-hint authenticateFailed" role="alert" aria-atomic="true">${sessionScope.languageJSON.validator.authenticateFailed}</p>
                            </c:if>
                            <c:if test="${times3}">
                                <p class="error-hint" id="haveEnteredThree" role="alert" aria-atomic="true">${sessionScope.languageJSON.validator.haveEnteredThree}</p>
                            </c:if>
                            <c:if test="${resetLocked}">
                                <p class="error-hint" role="alert" aria-atomic="true">${sessionScope.languageJSON.validator.resetLocked}</p>
                            </c:if>
                            <c:if test="${userNotRegistered}">
                                <p class="error-hint" role="alert" aria-atomic="true">${sessionScope.languageJSON.validator.userNotRegistered}</p>
                            </c:if>
                            <c:if test="${resetPsw!=null && resetPsw=='resetPswSuccess'}">
		                        <div class="valid-wrap error-hint" role="alert" aria-atomic="true">${sessionScope.languageJSON.validator.resetPswSuccess}</div>
		                    </c:if>
		                    <c:if test="${resetPsw!=null && resetPsw=='resetPswFaild'}">
		                        <div class="valid-wrap error-hint" role="alert" aria-atomic="true">${sessionScope.languageJSON.validator.resetPswFaild}</div>
		                    </c:if>
                            <div class="form-group account-btn">
                                <button id="timeOutSignin" type="submit" class="btn btn-primary">${sessionScope.languageJSON.label.login}</button>
                            </div>
                            
                        </form>
                        
                    </div>
                </div>
            </div>
    </body>
    
    <script src="/<%=request.getContextPath().split("/")[1]%>/js/viewJs/login.js"></script>

</html>
