<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> <%@ taglib
uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> <%@ page
language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="en">
    <head>
        <title data-localize="headTitle.forgotPassword"></title>
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <%@ include file="commons/header.jsp"%>
    </head>
    <body class="account-wrap">
        <%@ include file="commons/bar-account.jsp"%>
        <div class="account-top content-body"  tabindex="-1">
            <div class="account-inner sm">
                <div class="tab-head">
                    <label class="tab-item" id="retrieve-label">
                        <input
                            class="icheck"
                            id="retrieve"
                            type="radio"
                            checked
                            name="forgot"
                        />
                        <span data-localize="label.retrieveUsername"></span>
                    </label>
                    <label class="tab-item" id="reset-label">
                        <input
                            class="icheck"
                            id="reset"
                            type="radio"
                            name="forgot"
                        />
                        <span data-localize="label.resetPassword"></span>
                    </label>
                </div>
                <form
                    id="retrieveUserName"
                    action="retrieveUserName"
                    method="post"
                >
                <!-- 
                    <div class="form-group">
                        <label class="form-title" data-localize="label.emailAddress"></label>
                        <div class="valid-wrap">
                            <input
                                type="text"
                                class="form-control"
                                placeholder=""
                                data-localize="label.emailAddress"
                                name="email"
                                value="${user.userEmail}"
                            />
                        </div>
                    </div>
                     -->
                    <div class="form-group">
                                        <label class="form-title" for="autoAdvance_1"><span data-localize="label.employeeNumber"></span>(<span data-localize="label.noDashes"></span>)</label>
                                        <div class="valid-wrap">
                                            <input type="text" id="autoAdvance_1" class="form-control autoAdvance" placeholder="" value="${user.empNumber}" name="empNumber" maxlength="6">
                                        </div>
                                    </div>
                                    <div class="m-b-10">
                                        <label class="form-title"><span data-localize="label.dateOfBirth"></span>	(<span data-localize="label.mmddyyyy"></span>)</label>
                                        <div class="valid-wrap inline">
                                                <div class="form-group">
                                            <input type="text" id="autoAdvance_2" class="form-control autoAdvance" placeholder="" title="" value="${user.dateMonth}" name="dateMonth" data-localize="label.month" maxlength="2">
                                        </div>
                                            <div class="form-group">
                                            <input type="text" id="autoAdvance_3" class="form-control autoAdvance" placeholder="" title="" value="${user.dateDay}" name="dateDay" data-localize="label.day" maxlength="2">
                                        </div>
                                            <div class="form-group">
                                            <input type="text" id="autoAdvance_4" class="form-control autoAdvance" placeholder="" title="" value="${user.dateYear}" name="dateYear" data-localize="label.year" maxlength="4">
                                        </div>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                            <label class="form-title" data-localize="label.zipCode" for="autoAdvance_5"></label>
                                            <div class="valid-wrap">
                                                <input type="text" id="autoAdvance_5" class="form-control autoAdvance" placeholder="" value="${user.zipCode}" name="zipCode" maxlength="5">
                                            </div>
                                        </div>
                    <div class="form-group">
                        <label class="form-title" data-localize="label.username"></label>
                        <div class="valid-wrap">${user.username}</div>
                    </div>
                    <c:if test="${retrieve!=null && retrieve=='false'}">
                        <div class="valid-wrap error-hint" data-localize="validator.userNotExist"></div>
                    </c:if>
                    <div class="form-group account-btn">
                        <button type="submit" class="btn btn-primary" data-localize="label.retrieveUsername">
                        </button>
                    </div>
                </form>

                <form id="resetPassword" action="resetPassword" method="post">
                    <div class="form-group">
                        <label class="form-title" data-localize="label.username" for="resetUsername"></label>
                        <div class="valid-wrap">
                            <input
                                type="text"
                                class="form-control"
                                placeholder=""
                                data-localize="label.username"
                                name="userName"
                                id="resetUsername"
                                value="${user.username}"
                            />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-title" data-localize="label.emailAddress" for="resetEmail"></label>
                        <div class="valid-wrap">
                            <input
                                type="text"
                                class="form-control"
                                placeholder=""
                                data-localize="label.emailAddress"
                                name="email"
                                id="resetEmail"
                                value="${user.userEmail}"
                            />
                        </div>
                    </div>
                    
                    <c:if test="${errorMessage!=null && errorMessage!=''}">
                        <div class="valid-wrap error-hint" data-localize="validator.usernameOrEmailNotExit"></div>
                    </c:if>
                    <div class="form-group account-btn">
                        <button type="submit" class="btn btn-primary" data-localize="label.resetPassword">
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </body>
    <script src="/<%=request.getContextPath().split("/")[1]%>/js/viewJs/forgetPassword.js"></script>

</html>
