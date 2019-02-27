<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> <%@ taglib
uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> <%@ page
language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="en">
    <head>
        <title data-localize="headTitle.resetPassword"></title>
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <%@ include file="commons/header.jsp"%>
    </head>
    <body class="account-wrap">
        <%@ include file="commons/bar-account.jsp"%>
        <div class="account-top content-body" tabindex="-1">
            <div class="account-inner sm">
                <form id="updatePassword" action="updatePassword" method="post">
                    <div class="form-group">
                        <input type="hidden" name="id" value="${id}" />
                        <label class="form-title" data-localize="label.password"></label>
                        <div class="valid-wrap">
                            <input
                                type="password"
                                class="form-control"
                                placeholder="Password"
                                data-localize="label.newPassword"
                                name="password"
                            />
                        </div>
                    </div>
                    <div class="form-group error-vertical">
                        <label class="form-title"data-localize="label.confirmPassword"></label>
                        <div class="valid-wrap">
                            <input
                                type="password"
                                class="form-control"
                                placeholder=""
                                data-localize="label.confirmPassword"
                                name="newPassword"
                            />
                        </div>
                    </div>
                    <div class="form-group account-btn">
                        <button type="submit" class="btn btn-primary" data-localize="label.submit">
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </body>
    <script src="/<%=request.getContextPath().split("/")[1]%>/js/viewJs/resetPassword.js"></script>
</html>
