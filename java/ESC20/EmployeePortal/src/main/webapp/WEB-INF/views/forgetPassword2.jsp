<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> <%@ taglib
uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> <%@ page
language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html> 
<html lang="en">
	<link rel="stylesheet" href="/<%=request.getContextPath().split("/")[1]%>/css/employeePortal.css">
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
                        	<input type="hidden" name="socialSn" value="${user.ssn}"/>
                        	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        	<c:set var="empNum" value="${user.empNumber}" scope="request"/>
                        	<c:set var="socNum" value="${user.ssn }" scope="request"/>
                        	<c:if test ="${fn:length(empNum) != 0}">
                            <div class="form-group">
                                <label >${sessionScope.languageJSON.label.employeeNumber}</label>
                                <div class="valid-wrap">
                                   ${user.empNumber}
                                </div>
                                <input type="hidden" name="empNumber" value="${newUser.empNumber}" />
                            </div>
                            </c:if>
                            <c:if test ="${fn:length(empNum) == 0}">
                            <div class="form-group">
                                <label >Social Security Number</label>
                                <div class="valid-wrap">
	                                <c:set var="firstFiveChar" value="${fn:substring(user.ssn,0,5)}" scope="request"/>
	                                ${fn:replace (user.ssn , firstFiveChar , 'XXX-XX-')}
                                  
                                </div>
                                <input type="hidden" name="ssn" value="${newUser.ssn}" />
                            </div>
                            </c:if>
                            <div class="form-group">
                                <label >${sessionScope.languageJSON.label.dateOfBirth}</label>
                                <div class="valid-wrap">
                                   ${ user.searchFormattedDateofBirth}
                                </div>
                            </div>
                            <div class="form-group">
                                <label >${sessionScope.languageJSON.label.zipCode}</label>
                                <div class="valid-wrap">
                                    ${user.zipCode}
                                </div>
                            </div>
                            <div class="form-group">
                                <label >${sessionScope.languageJSON.label.lastname}</label>
                                <div class="valid-wrap">
                                    ${user.nameL}
                                </div>
                            </div>
                            <div class="form-group">
                                <label >${sessionScope.languageJSON.label.firstname}</label>
                                <div class="valid-wrap">
                                    ${user.nameF}
                                </div>
                            </div>
                            <c:if test="${user.userHomeEmail != '' || user.userEmail != ''}">
	                            <p>
	                                <b>${sessionScope.languageJSON.label.pleaseAnswerYourHintQuestion}</b>
	                            </p>
	                        
	                            <div class="form-group">
		                                <label  for="answerQuestion">
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
                                    <label >${sessionScope.languageJSON.label.workEmail}</label>
                                    <label class="chooseEmailGroup" for="workEmail">
                                    <input style="margin-left:0;" type="radio" name="email" id="workEmail" checked value="${user.userEmail}" aria-label="${sessionScope.languageJSON.label.chooseWorkEmailSend}">
                                    <span class="emailAddress">${user.userEmail}</span>
                                    </label>
                                </c:if>
                            </div>
                            <div class="form-group">
                                <c:if test="${user.userHomeEmail != ''}">
                                    <label  >${sessionScope.languageJSON.label.homeEmail}</label>
                                    <label class="chooseEmailGroup" for="homeEmail">
                                        <input style="margin-left:0;" type="radio" name="email" id="homeEmail" value="${user.userHomeEmail}" aria-label="${sessionScope.languageJSON.label.chooseHomeEmailSend}">
                                        <span class="emailAddress">${user.userHomeEmail}</span>
                                        
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
