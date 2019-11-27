<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> <%@ taglib
uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> <%@ page
language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html> 
<html lang="en">
    <head>
        <title>${sessionScope.languageJSON.headTitle.createNewUser}</title>
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <%@ include file="commons/header.jsp"%>
    </head>
    <body class="account-wrap">
        <%@ include file="commons/bar-account.jsp"%>
        <div class="account-top content-body createUser"  tabindex="-1">
                <div class="account-inner sm">
                        <form id="createNewUserForm" action="saveNewUser" method="post">
                        	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <div class="form-group">
                                <label>${sessionScope.languageJSON.label.employeeNumber}</label>
                                <div class="valid-wrap">
                                   ${user.empNumber}
                                </div>
                                <input type="hidden" id="empNbr" name="empNbr" value="${user.empNumber}" />
                            </div>
                            <div class="form-group">
                                <label>${sessionScope.languageJSON.label.dateOfBirth}</label>
                                <div class="valid-wrap">
                                   ${user.searchFormattedDateofBirth}
                                </div>
                            </div>
                            <div class="form-group">
                                <label>${sessionScope.languageJSON.label.zipCode}</label>
                                <div class="valid-wrap">
                                    ${user.zipCode}
                                </div>
                            </div>
                            <div class="form-group">
                                <label>${sessionScope.languageJSON.label.lastname}</label>
                                <div class="valid-wrap">
                                    ${user.nameL}
                                </div>
                            </div>
                            <div class="form-group">
                                <label>${sessionScope.languageJSON.label.firstname}</label>
                                <div class="valid-wrap">
                                    ${user.nameF}
                                </div>
                            </div>
                            <div>
                                <b>${sessionScope.languageJSON.label.pleaseEnterInformation}</b>
                            </div>
                            <div class="form-group error-vertical">
                                <label for="username">${sessionScope.languageJSON.label.username}</label>
                                <div class="valid-wrap">
                                    <input
                                        type="text"
                                        class="form-control"
                                        name="username"
                                        id="username"
                                        size="12" maxlength="8"
                                    />
                                </div>
                            </div>
                            <div class="form-group error-vertical">
                                <label for="password">${sessionScope.languageJSON.label.password}</label>
                                <div class="valid-wrap">
                                    <input
                                        type="password"
                                        class="form-control"
                                        name="password"
                                        type="password"
                                        id="password"
                                        maxlength="9"
                                    />
                                </div>
                            </div>
                            <div class="form-group error-vertical">
                                <label for="newPassword">${sessionScope.languageJSON.label.passwordVerification}</label>
                                <div class="valid-wrap">
                                    <input
                                        type="password"
                                        class="form-control"
                                        name="newPassword"
                                        type="password"
                                        id="newPassword"
                                        maxlength="9"
                                    />
                                </div>
                            </div>
                            <div class="form-group error-vertical">
                                <label for="workEmail">${sessionScope.languageJSON.label.workEmail}</label>
                                <div class="valid-wrap">
                                <c:choose>
									<c:when test="${empty user.userEmail}">
										 <input
                                        type="text"
                                        class="form-control"
                                        name="workEmail"
                                        id="workEmail"
                                        value=""
                                        maxlength="70"
                                       />
									</c:when>
									<c:otherwise>
	                                    <div style="min-height:20px;" id="staticWorkEmail">
	                                            ${user.userEmail}
	                                    </div>
									</c:otherwise>
								</c:choose>

                                </div>
                            </div>
                            <c:if test="${empty user.userEmail}">
                            <div class="form-group error-vertical">
                                <label for="workEmail">${sessionScope.languageJSON.label.workEmailVerify}</label>
                                <div class="valid-wrap">
                                    <input
                                        type="text"
                                        class="form-control"
                                        name="workEmailVerify"
                                        id="verifyWorkEmail"
                                        value=""
                                        maxlength="70"
                                    />
                                </div>
                            </div>
                            </c:if>
                            <div class="form-group error-vertical">
                                <label for="homeEmail">${sessionScope.languageJSON.label.homeEmail}</label>
                                <div class="valid-wrap">
                                 <c:choose>
									<c:when test="${empty user.userHomeEmail}">
										<input
                                        type="text"
                                        class="form-control"
                                        name="homeEmail"
                                        id="homeEmail"
                                        maxlength="70"
                                        value=""/>
									</c:when>
									<c:otherwise>
									  <div style="min-height:20px;" id="staticHomeEmail">${user.userHomeEmail}</div>
									</c:otherwise>
								</c:choose>


                                    <!-- <input
                                        type="text"
                                        class="form-control"
                                        name="homeEmail"
                                        id="homeEmail"
                                        value="${emailShowRequest.hmEmailNew}"
                                    /> -->

                                </div>
                            </div>
                            <c:if test="${empty user.userHomeEmail}">
                            <div class="form-group error-vertical">
                                <label for="homeEmail">${sessionScope.languageJSON.label.homeEmailVerify}</label>
                                <div class="valid-wrap">
                                    <input
                                        type="text"
                                        class="form-control"
                                        name="homeEmailVerify"
                                        id="verifyHomeEmail"
                                        value=""
                                        maxlength="70"
                                    />
                                </div>
                            </div>
                            </c:if>
                            <div class="form-group">
                                <label for="hintQuestion">${sessionScope.languageJSON.label.hintQuestion}</label>
                                <div class="valid-wrap">
                                    <input
                                        type="text"
                                        class="form-control"
                                        name="hintQuestion"
                                        id="hintQuestion"
                                        maxlength="50"
                                    />
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="hintAnswer">${sessionScope.languageJSON.label.hintAnswer}</label>
                                <div class="valid-wrap button-group">
                                    <input
                                        type="text"
                                        class="form-control"
                                        name="hintAnswer"
                                        id="hintAnswer"
                                        maxlength="30"
                                    />
                                    <button class="clear-btn hide" type="button" role="button" onclick="clearDate(this)" aria-label="${sessionScope.languageJSON.label.removeContent}">
                                        <i class="fa fa-times"></i>
                                    </button>
                                </div>
                                <small class="help-block sameAnswer" role="alert" aria-atomic="true" style="display: none;">
                                    ${sessionScope.languageJSON.validator.notSameAnswer}
                                </small>
                            </div>

 							<c:if test="${isUserExist!=null && isUserExist=='true'}">
                             	<p class="error-hint"  role="alert" aria-atomic="true" id="noUserError">${sessionScope.languageJSON.validator.userExist}</p>
                            </c:if>
                            <c:if test="${isSuccess!=null && isSuccess=='true'}">
                             	<p class="error-hint"  role="alert" aria-atomic="true" id="saveUserSuccess">${sessionScope.languageJSON.validator.saveUserSuccess}</p>
                            </c:if>
                            <div class="form-group account-btn">
                                <button type="button" role="button" id="saveNewUser" class="btn btn-primary">
                                        ${sessionScope.languageJSON.label.save}
                                </button>
                            </div> 
                    </form>
                    
                    <form id="loginForm" method="post" style="visibility: hidden" action="/<%=request.getContextPath().split("/")[1]%>/loginEA">
                    	<input type="text" id="loginUsername" class="form-control" placeholder="${sessionScope.languageJSON.label.username}"name="username"/>
                    	<input type="password" id="loginPassword" class="form-control" placeholder="${sessionScope.languageJSON.label.password}" name="password"/>
                    	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </form>
                </div>
            </div>
            <div class="loadingOn" style="display:none;">
                    <div class="loadEffect">
                        <span></span>
                        <span></span>
                        <span></span>
                        <span></span>
                        <span></span>
                        <span></span>
                        <span></span>
                        <span></span>
                </div>
            </div>
    </body>
    <script src="/<%=request.getContextPath().split("/")[1]%>/js/viewJs/createNewUser.js"></script>

</html>
