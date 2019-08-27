<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> <%@ taglib
uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> <%@ page
language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html> 
<html lang="en">
    <head>
        <title>${sessionScope.languageJSON.headTitle.forgotPassword}</title>
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <%@ include file="commons/header.jsp"%>
    </head>
    <body class="account-wrap">
        <%@ include file="commons/bar-account.jsp"%>
        <div class="account-top content-body answerQuestion"  tabindex="-1">
                <div class="account-inner sm">
                        <form action="answerHintQuestion" method="post">
                        	<input type="hidden" name="count" value="${count}"/>
                        	<input type="hidden" name="empNbr" value="${user.empNumber}"/>
                        	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <div class="form-group">
                                <label class="form-title">${sessionScope.languageJSON.label.employeeNumber}</label>
                                <div class="valid-wrap">
                                   ${user.empNumber}
                                </div>
                                <input type="hidden" name="empNumber" value="${newUser.empNumber}" />
                            </div>
                            <div class="form-group">
                                <label class="form-title">${sessionScope.languageJSON.label.dateOfBirth}</label>
                                <div class="valid-wrap">
                                   ${ user.searchFormattedDateofBirth}
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
                            <c:if test="${user.userHomeEmail != '' || user.userEmail != ''}">
	                            <p>
	                                <b>${sessionScope.languageJSON.label.pleaseAnswerYourHintQuestion}</b>
	                            </p>
	                        
	                            <div class="form-group">
		                                <label class="form-title" for="answerQuestion">
		                                 	${user.hintQuestion}
		                                </label>
		                                <div class="valid-wrap">
		                                    <input
		                                        type="text"
		                                        class="form-control"
		                                        name="answer"
		                                        id="answerQuestion"
		                                    />
		                                </div>
	                            </div>
                           </c:if>
                            <div class="form-group">
                                <c:if test="${user.userEmail != ''}">
                                    <label class="chooseEmailGroup" for="workEmail">
                                    <input type="radio" name="email" id="workEmail" checked value="${user.userEmail}" aria-label="${sessionScope.languageJSON.label.chooseWorkEmailSend}">
                                    <span class="emailAddress">${user.userEmail}</span>
                                    <span class="emailType">${sessionScope.languageJSON.label.workEmail}</span>
                                    </label>
                                </c:if>
                                <c:if test="${user.userHomeEmail != ''}">
                                    <label class="chooseEmailGroup" for="homeEmail">
                                        <input type="radio" name="email" id="homeEmail" value="${user.userHomeEmail}" aria-label="${sessionScope.languageJSON.label.chooseHomeEmailSend}">
                                        <span class="emailAddress">${user.userHomeEmail}</span>
                                        <span class="emailType" >${sessionScope.languageJSON.label.homeEmail}</span>
                                    </label>
                                </c:if>
                                <c:if test="${user.userHomeEmail == '' && user.userEmail == ''}">
                                    <p class="error-hint" role="alert">${sessionScope.languageJSON.label.getPasswordNoEmail}</p>
                                </c:if>
                              </div>

                            <c:if test="${errorMessage!=null && errorMessage!=''}">
                            	<p class="error-hint" role="alert" aria-atomic="true" id="noUserError">
                                        ${sessionScope.languageJSON.validator.answerError}
                                </p>
                            </c:if>
                            <div class="form-group account-btn">
                            	<c:if test="${user.userHomeEmail != '' || user.userEmail != ''}">
	                                <button type="submit" role="button" class="btn btn-primary">
	                                        ${sessionScope.languageJSON.label.submit}
	                                </button>
                                </c:if>
                                <c:if test="${user.userHomeEmail == '' && user.userEmail == ''}">
                                	<a class="btn btn-primary" href="/<%=request.getContextPath().split("/")[1]%>/login?distid=${sessionScope.districtId}" role="link">
	                                        ${sessionScope.languageJSON.label.returnLogin}
	                                </a>
                                </c:if>
                            </div> 
                    </form>
                </div>
            </div>
    </body>
    <script src="/<%=request.getContextPath().split("/")[1]%>/js/viewJs/forgetPassword2.js"></script>

</html>
