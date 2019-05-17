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
                                <label class="form-title">${sessionScope.languageJSON.label.employeeNumber}</label>
                                <div class="valid-wrap">
                                   ${user.empNumber}
                                </div>
                                <input type="hidden" name="empNbr" value="${user.empNumber}" />
                            </div>
                            <div class="form-group">
                                <label class="form-title">${sessionScope.languageJSON.label.dateOfBirth}</label>
                                <div class="valid-wrap">
                                   ${user.searchFormattedDateofBirth}
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="form-title">${sessionScope.languageJSON.label.zipCode}</label>
                                <div class="valid-wrap">
                                    ${user.zipCode}
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="form-title">${sessionScope.languageJSON.label.lastname}</label>
                                <div class="valid-wrap">
                                    ${user.nameL}
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="form-title">${sessionScope.languageJSON.label.firstname}</label>
                                <div class="valid-wrap">
                                    ${user.nameF}
                                </div>
                            </div>
                            <div>
                                <b>${sessionScope.languageJSON.label.pleaseEnterInformation}</b>
                            </div>
                            <div class="form-group error-vertical">
                                <label class="form-title" for="username">${sessionScope.languageJSON.label.username}</label>
                                <div class="valid-wrap">
                                    <input
                                        type="text"
                                        class="form-control"
                                        name="username"
                                        id="username"
                                    />
                                </div>
                            </div>
                            <div class="form-group error-vertical">
                                <label class="form-title" for="password">${sessionScope.languageJSON.label.password}</label>
                                <div class="valid-wrap">
                                    <input
                                        type="text"
                                        class="form-control"
                                        name="password"
                                        type="password"
                                        id="password"
                                    />
                                </div>
                            </div>
                            <div class="form-group error-vertical">
                                <label class="form-title" for="newPassword">${sessionScope.languageJSON.label.newPassword}</label>
                                <div class="valid-wrap">
                                    <input
                                        type="text"
                                        class="form-control"
                                        name="newPassword"
                                        type="password"
                                        id="newPassword"
                                    />
                                </div>
                            </div>
                            <div class="form-group error-vertical">
                                <label class="form-title" for="workEmail">${sessionScope.languageJSON.label.workEmail}</label>
                                <div class="valid-wrap">
                                    <input
                                        type="text"
                                        class="form-control"
                                        name="workEmail"
                                        id="workEmail"
                                        value="${emailShowRequest.emailNew}"
                                    />
                                </div>
                            </div>
                            <div class="form-group error-vertical">
                                <label class="form-title" for="workEmail">${sessionScope.languageJSON.profile.verifyEmail}</label>
                                <div class="valid-wrap">
                                    <input
                                        type="text"
                                        class="form-control"
                                        name="workEmailVerify"
                                        id="verifyWorkEmail"
                                        value="${emailShowRequest.emailNew}"
                                    />
                                </div>
                            </div>
                            <div class="form-group error-vertical">
                                <label class="form-title" for="homeEmail">${sessionScope.languageJSON.label.homeEmail}</label>
                                <div class="valid-wrap">
                                    <input
                                        type="text"
                                        class="form-control"
                                        name="homeEmail"
                                        id="homeEmail"
                                        value="${emailShowRequest.hmEmailNew}"
                                    />
                                </div>
                            </div>
                            <div class="form-group error-vertical">
                                <label class="form-title" for="homeEmail">${sessionScope.languageJSON.profile.verifyEmail}</label>
                                <div class="valid-wrap">
                                    <input
                                        type="text"
                                        class="form-control"
                                        name="homeEmailVerify"
                                        id="verifyHomeEmail"
                                        value="${emailShowRequest.hmEmailNew}"
                                    />
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="form-title" for="hintQuestion">${sessionScope.languageJSON.label.hintQuestion}</label>
                                <div class="valid-wrap">
                                    <input
                                        type="text"
                                        class="form-control"
                                        name="hintQuestion"
                                        id="hintQuestion"
                                    />
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="form-title" for="hintAnswer">${sessionScope.languageJSON.label.hintAnswer}</label>
                                <div class="valid-wrap">
                                    <input
                                        type="text"
                                        class="form-control"
                                        name="hintAnswer"
                                        id="hintAnswer"
                                    />
                                </div>
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
                </div>
            </div>
    </body>
    <script src="/<%=request.getContextPath().split("/")[1]%>/js/viewJs/createNewUser.js"></script>

</html>
