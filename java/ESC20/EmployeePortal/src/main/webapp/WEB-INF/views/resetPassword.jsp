<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> <%@ taglib
uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> <%@ page
language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html> 
<html lang="en">
    <head>
        <title>${sessionScope.languageJSON.headTitle.resetPassword}</title>
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <%@ include file="commons/header.jsp"%>
    </head>
    <body class="account-wrap">
        <!-- ALC-26 set default min and max length for password-->
<c:set var="pwd_max_length" value="${sessionScope.txeisPreferences.pwd_max_length}"></c:set>
<c:if test="${empty sessionScope.txeisPreferences.pwd_max_length}">
    <c:set var="pwd_max_length" value="46"></c:set>
</c:if>
<c:set var="pwd_min_length" value="${sessionScope.txeisPreferences.pwd_length}"></c:set>
<c:if test="${empty sessionScope.txeisPreferences.pwd_length}">
    <c:set var="pwd_min_length" value="8"></c:set>
</c:if>
        <%@ include file="commons/bar-account.jsp"%>
        <div class="account-top content-body" tabindex="-1">
            <div class="account-inner sm">
                <p>
                    <b> ${sessionScope.languageJSON.label.enterNewPSDChange}</b>
                </p>
                <form id="updatePassword" action="updatePassword" method="post">
                	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <div class="form-group">
                        <input type="hidden" name="id" value="${id}" />
                        <label class="form-title">${sessionScope.languageJSON.label.password}</label>
                        <div class="valid-wrap">
                            <input
                                type="password"
                                class="form-control"
                                placeholder="${sessionScope.languageJSON.label.newPassword}"
                                id="newPassword"
                                name="password"
                                maxlength="${pwd_max_length}"
                            />
                        </div>
                    </div>
                    <div class="form-group error-vertical">
                        <label class="form-title">${sessionScope.languageJSON.label.confirmPassword}</label>
                        <div class="valid-wrap">
                            <input
                                type="password"
                                class="form-control"
                                placeholder="${sessionScope.languageJSON.label.confirmPassword}"
                                id="newCheckPassword"
                                name="newPassword"
                                maxlength="${pwd_max_length}"
                            />
                        </div>
                    </div>
                    <p class="error-hint" role="alert" aria-atomic="true">
                            ${sessionScope.languageJSON.validator.newPasswordDifferOld}
                    </p>
                    <div class="form-group account-btn">
                        <button type="button" role="button" class="btn btn-primary" id="resetPsdBtn">
                                ${sessionScope.languageJSON.label.submit}
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </body>
    <!-- ALC-26  password validation from back-end-->
 <script>
        var minPSDLen = "${pwd_min_length}"
        var maxPSDLen = "${pwd_max_length}"
</script>
<script src="<spring:theme code="commonBase"/>scripts/commonValid.js"></script>
    <script src="/<%=request.getContextPath().split("/")[1]%>/js/viewJs/resetPassword.js"></script>
</html>
