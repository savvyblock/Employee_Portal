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
        <%@ include file="commons/bar-account.jsp"%>
        <div class="account-top content-body" tabindex="-1">
            <div class="account-inner sm">
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
                               
                                name="password"
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
                                name="newPassword"
                            />
                        </div>
                    </div>
                    <div class="form-group account-btn">
                        <button type="submit" role="submitButton" class="btn btn-primary">
                                ${sessionScope.languageJSON.label.submit}
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </body>
    <script src="/<%=request.getContextPath().split("/")[1]%>/js/viewJs/resetPassword.js"></script>
</html>
