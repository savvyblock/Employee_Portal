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
        <div class="account-top content-body"  tabindex="-1">
            <div class="account-inner sm">
                <div class="tab-head">
                            <c:if test="${idType=='E'}">
	                            <p>
	                            	<b>${sessionScope.languageJSON.label.pleaseEnterEmpBirthZipCode}</b>
	                            </p>
                            </c:if>
                            <c:if test="${idType=='S'}">
	                            <p>
	                            	<b>${sessionScope.languageJSON.label.pleaseEnterSSoBirthZipCode}</b>
	                            </p>
                            </c:if>
                </div>
                <form
                    id="retrieveUserName"
                    action="retrieveUserName"
                    method="post"
                >
                	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <div class="form-group">
                                        <c:if test="${idType=='S'}">
                                        	<label for="autoAdvance_1"><span>Social Security Number</span>&nbsp;(<span>${sessionScope.languageJSON.label.noDashes}</span>)</label>
	                                        <div class="valid-wrap">
	                                            <input type="text" id="autoAdvance_1" class="form-control autoAdvance" placeholder="" name="ssn" maxlength="9">
	                                        </div>
                                        </c:if>
                                        <c:if test="${idType=='E'}">
                                        	<label for="autoAdvance_1"><span>${sessionScope.languageJSON.label.employeeNumber}</span>&nbsp;(<span>${sessionScope.languageJSON.label.noDashes}</span>)</label>
	                                        <div class="valid-wrap">
	                                            <input type="text" id="autoAdvance_1" class="form-control autoAdvance" placeholder="" name="empNumber" maxlength="6">
	                                        </div>
                                        </c:if>
                                    </div>
                                    <div class="m-b-10">
                                        <label><span>${sessionScope.languageJSON.label.dateOfBirth}</span>	(<span>${sessionScope.languageJSON.label.mmddyyyy}</span>)</label>
                                        <div class="valid-wrap inline">
                                                <div class="form-group">
                                            <input type="text" id="autoAdvance_2" class="form-control autoAdvance" placeholder="${sessionScope.languageJSON.label.month}" aria-label="${sessionScope.languageJSON.label.month}" value="${user.dateMonth}" name="dateMonth" maxlength="2">
                                        </div>
                                            <div class="form-group">
                                            <input type="text" id="autoAdvance_3" class="form-control autoAdvance" placeholder="${sessionScope.languageJSON.label.day}" aria-label="${sessionScope.languageJSON.label.day}" value="${user.dateDay}" name="dateDay" maxlength="2">
                                        </div>
                                            <div class="form-group">
                                            <input type="text" id="autoAdvance_4" class="form-control autoAdvance" placeholder="${sessionScope.languageJSON.label.year}" aria-label="${sessionScope.languageJSON.label.year}" value="${user.dateYear}" name="dateYear" maxlength="4">
                                        </div>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                            <label for="autoAdvance_5">${sessionScope.languageJSON.label.zipCode}</label>
                                            <div class="valid-wrap">
                                                <input type="text" id="autoAdvance_5" class="form-control autoAdvance" value="${user.zipCode}" name="zipCode" maxlength="5">
                                            </div>
                                        </div>
                    <c:if test="${retrieve!=null && retrieve=='false'}">
                        <div class="valid-wrap error-hint" role="alert" aria-atomic="true">${sessionScope.languageJSON.validator.userNotExist}</div>
                    </c:if>
                    <div class="form-group account-btn">
                        <button type="submit" role="button" class="btn btn-primary">
                                ${sessionScope.languageJSON.label.retrieve}
                        </button>
                    </div>
				
                    <c:if test="${errorMessage!=null && errorMessage!=''}">
                        <div class="valid-wrap error-hint" role="alert" aria-atomic="true">
                                ${sessionScope.languageJSON.validator.usernameOrEmailNotExit}
                        </div>
                    </c:if>
                </form>
            </div>
        </div>
    </body>
    <script src="/<%=request.getContextPath().split("/")[1]%>/js/viewJs/forgetPassword.js"></script>

</html>
