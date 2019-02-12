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
        <div class="account-top">
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
                    <div class="form-group">
                        <label class="form-title" data-localize="label.username"></label>
                        <div class="valid-wrap">${user.usrname}</div>
                    </div>
                    <p class="error-hint hide" id="errorMessage" data-localize="validator.emailNotExit"></p>
                    <div class="form-group account-btn">
                        <button type="submit" class="btn btn-primary" data-localize="label.retrieveUsername">
                        </button>
                    </div>
                </form>

                <form id="resetPassword" action="resetPassword" method="post">
                    <div class="form-group">
                        <label class="form-title" data-localize="label.username"></label>
                        <div class="valid-wrap">
                            <input
                                type="text"
                                class="form-control"
                                placeholder=""
                                data-localize="label.username"
                                name="userName"
                                value="${user.usrname}"
                            />
                        </div>
                    </div>
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
                    <p class="error-hint hide" id="errorMessage" data-localize="validator.usernameOrEmailError"></p>
                    <c:if test="${errorMessage!=null && errorMessage!=''}">
                        <div class="valid-wrap error-hint" data-localize="validator.userNotExist"></div>
                    </c:if>
                    <div class="form-group account-btn">
                        <button type="submit" class="btn btn-primary" data-localize="label.resetPassword">
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </body>
    <script>
        $('#retrieveUserName').bootstrapValidator({
            live: 'enable',
            trigger: 'blur keyup',
            feedbackIcons: {
                valid: 'fa fa-check ',
                // invalid: 'fa fa-times',
                validating: 'fa fa-refresh'
            },
            fields: {
                email: {
                    validators: {
                        notEmpty: {
                            message: 'validator.requiredField'
                        },
                        emailAddress: {
                            message: 'validator.pleaseEnterCorrectMailFormat'
                        }
                    }
                }
            }
        })
        $('#resetPassword').bootstrapValidator({
            live: 'enable',
            trigger: 'blur keyup',
            feedbackIcons: {
                valid: 'fa fa-check ',
                // invalid: 'fa fa-times',
                validating: 'fa fa-refresh'
            },
            fields: {
                userName: {
                    validators: {
                        notEmpty: {
                            message: 'validator.requiredField'
                        }
                    }
                },
                email: {
                    validators: {
                        notEmpty: {
                            message: 'validator.requiredField'
                        },
                        emailAddress: {
                            message: 'validator.pleaseEnterCorrectMailFormat'
                        }
                    }
                }
            }
        })
        $('#retrieve').on('ifChecked', function(event) {
            console.log(event)
            $('#retrieveUserName').show()
            $('#resetPassword').hide()
        })
        $('#reset').on('ifChecked', function(event) {
            console.log(event)
            $('#retrieveUserName').hide()
            $('#resetPassword').show()
		})
		let paramArry = window.location.href.split("/");
		let len = paramArry.length
		console.log(paramArry[len-1])
		if(paramArry[len-1]){
			if(paramArry[len-1]=='resetPassword'){
				$('#reset').iCheck('check');
				$('#retrieveUserName').hide()
            	$('#resetPassword').show()
			}
		}
    </script>
</html>
