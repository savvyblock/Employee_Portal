<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html> 
<html lang="en">
    <head>
        <title>${sessionScope.languageJSON.headTitle.createNewUser}</title>
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        
        <%@ include file="commons/header.jsp"%>
    </head>
    <body class="account-wrap">
        <%@ include file="commons/bar-account.jsp"%>
        <div class="account-top content-body"  tabindex="-1">
                <div class="account-inner sm">
                        <form id="retrieveUser" action="retrieveEmployee" method="post" >
                        	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <p>
                                    <b>${sessionScope.languageJSON.label.pleaseEnterEmpBirthZipCode}</b>
                            </p>
                                    <div class="form-group">
                                        <label class="form-title" for="autoAdvance_1"><span>${sessionScope.languageJSON.label.employeeNumber}</span>&nbsp;(<span>${sessionScope.languageJSON.label.noDashes}</span>)</label>
                                        <div class="valid-wrap">
                                            <input type="text" id="autoAdvance_1" class="form-control autoAdvance" placeholder="" name="empNumber" maxlength="6">
                                        </div>
                                    </div>
                                    <div class="m-b-10">
                                        <label class="form-title"><span>${sessionScope.languageJSON.label.dateOfBirth}</span>	(<span>${sessionScope.languageJSON.label.mmddyyyy}</span>)</label>
                                        <div class="valid-wrap inline">
                                                <div class="form-group">
                                            <input type="text" id="autoAdvance_2" class="form-control autoAdvance" placeholder="${sessionScope.languageJSON.label.month}" aria-label="${sessionScope.languageJSON.label.month}" name="dateMonth" maxlength="2">
                                        </div>
                                            <div class="form-group">
                                            <input type="text" id="autoAdvance_3" class="form-control autoAdvance" placeholder="${sessionScope.languageJSON.label.day}" aria-label="${sessionScope.languageJSON.label.day}" name="dateDay" maxlength="2">
                                        </div>
                                            <div class="form-group">
                                            <input type="text" id="autoAdvance_4" class="form-control autoAdvance" placeholder="${sessionScope.languageJSON.label.year}" aria-label="${sessionScope.languageJSON.label.year}" name="dateYear" maxlength="4">
                                        </div>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                            <label class="form-title" for="autoAdvance_5">${sessionScope.languageJSON.label.zipCode}</label>
                                            <div class="valid-wrap">
                                                <input type="text" id="autoAdvance_5" class="form-control autoAdvance" placeholder="" name="zipCode" maxlength="5">
                                            </div>
                                        </div>
                                         <c:if test="${isExistUser!=null && isExistUser=='true'}">
                                         <p class="error-hint" role="alert" aria-atomic="true" id="noUserError">${sessionScope.languageJSON.validator.noUserAccountAssociated}</p>
                                         </c:if>
                                         <c:if test="${isSuccess!=null && isSuccess=='false'}">
                                        <p class="error-hint" role="alert" aria-atomic="true" id="noEmployeeError">${sessionScope.languageJSON.validator.noEmployeeAccountAssociated}</p>
                                         </c:if>
                                        <div class="form-group account-btn">
                                            <button type="submit" role="submitButton" id="retrieveBtn" class="btn btn-primary">${sessionScope.languageJSON.label.retrieve}</button>
                                        </div>
                               
                            </form>
                </div>
            </div>
    </body>
    <script src="/<%=request.getContextPath().split("/")[1]%>/js/viewJs/searchUser.js"></script>
</html>
