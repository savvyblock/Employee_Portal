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
                        <form id="createNewUserForm" action="" method="post">
                        	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <div class="form-group">
                                <label class="form-title" data-localize="label.employeeNumber"></label>
                                <div class="valid-wrap">
                                   ${newUser.empNumber}
                                </div>
                                <input type="hidden" name="empNumber" value="${newUser.empNumber}" />
                            </div>
                            <div class="form-group">
                                <label class="form-title" data-localize="label.dateOfBirth"></label>
                                <div class="valid-wrap">
                                   ${ newUser.searchFormattedDateofBirth}
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="form-title" data-localize="label.zipCode"></label>
                                <div class="valid-wrap">
                                    ${newUser.zipCode}
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="form-title" data-localize="label.lastname"></label>
                                <div class="valid-wrap">
                                    ${newUser.nameL}
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="form-title" data-localize="label.firstname"></label>
                                <div class="valid-wrap">
                                    ${newUser.nameF}
                                </div>
                            </div>
                            <p>
                                <b data-localize="label.pleaseAnswerYourHintQuestion"></b>
                            </p>
                            <div class="form-group">
                                <label class="form-title" for="answerQuestion">
                                  what's your name?
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
                            <div class="form-group">
                              <label class="chooseEmailGroup" for="workEmail">
                                <input type="radio" name="sendEmail" id="workEmail" checked value="" aria-label="" data-localize="label.workEmail">
                                <span class="emailAddress">werwe@fsdf.com</span>
                                <span class="emailType" data-localize="label.workEmail"></span>
                              </label>
                              <label class="chooseEmailGroup" for="homeEmail">
                                  <input type="radio" name="sendEmail" id="homeEmail" value="" aria-label="" data-localize="label.homeEmail">
                                  <span class="emailAddress">werwe@fsdf.com</span>
                                  <span class="emailType" data-localize="label.homeEmail"></span>
                                </label>
                            </div>
                            <p class="error-hint" id="noUserError" data-localize="validator.answerError"></p>
                            <div class="form-group account-btn">
                                <button type="submit" class="btn btn-primary" data-localize="label.submit">
                                </button>
                            </div> 
                    </form>
                </div>
            </div>
    </body>
    <script src="/<%=request.getContextPath().split("/")[1]%>/js/viewJs/forgetPassword2.js"></script>

</html>
