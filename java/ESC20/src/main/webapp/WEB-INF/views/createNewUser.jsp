<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> <%@ taglib
uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> <%@ page
language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="en">
    <head>
        <title data-localize="headTitle.createNewUser"></title>
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <%@ include file="commons/header.jsp"%>
    </head>
    <body class="account-wrap">
        <%@ include file="commons/bar-account.jsp"%>
        <div class="account-top">
                <div class="account-inner sm">
                        <form id="createNewUserForm" action="saveNewUser" method="post">
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
                            <div>
                                <b data-localize="label.pleaseEnterInformation"></b>
                            </div>
                            <div class="form-group">
                                <label class="form-title" data-localize="label.username" for="username"></label>
                                <div class="valid-wrap">
                                    <input
                                        type="text"
                                        class="form-control"
                                        name="username"
                                        id="username"
                                    />
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="form-title" data-localize="label.password" for="password"></label>
                                <div class="valid-wrap">
                                    <input
                                        type="text"
                                        class="form-control"
                                        name="password"
                                        id="password"
                                    />
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="form-title" data-localize="label.newPassword" for="newPassword"></label>
                                <div class="valid-wrap">
                                    <input
                                        type="text"
                                        class="form-control"
                                        name="newPassword"
                                        id="newPassword"
                                    />
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="form-title" data-localize="label.workEmail" for="workEmail"></label>
                                <div class="valid-wrap">
                                    <input
                                        type="text"
                                        class="form-control"
                                        name="workEmail"
                                        id="workEmail"
                                    />
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="form-title" data-localize="label.homeEmail" for="homeEmail"></label>
                                <div class="valid-wrap">
                                    <input
                                        type="text"
                                        class="form-control"
                                        name="homeEmail"
                                        id="homeEmail"
                                    />
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="form-title" data-localize="label.hintQuestion" for="hintQuestion"></label>
                                <div class="valid-wrap">
                                    <input
                                        type="text"
                                        class="form-control"
                                        name="hintQuestion"
                                        id="hintQuestion"
                                    />
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="form-title" data-localize="label.hintAnswer" for="hintAnswer"></label>
                                <div class="valid-wrap">
                                    <input
                                        type="text"
                                        class="form-control"
                                        name="hintAnswer"
                                        id="hintAnswer"
                                    />
                                </div>
                            </div>

 							<c:if test="${isUserExist!=null && isUserExist=='true'}">
                             	<p class="error-hint" id="noUserError" data-localize="validator.userExist"></p>
                            </c:if>
                            <c:if test="${isSuccess!=null && isSuccess=='true'}">
                             	<p class="error-hint" id="saveUserSuccess" data-localize="validator.saveUserSuccess"></p>
                            </c:if>
                            <div class="form-group account-btn">
                                <button type="submit" class="btn btn-primary" data-localize="label.submit">
                                </button>
                            </div> 
                    </form>
                </div>
            </div>
    </body>
    <script>
            $('#createNewUserForm').bootstrapValidator({
                live: 'enable',
                trigger: 'blur keyup',
                feedbackIcons: {
                    valid: 'fa fa-check ',
                    validating: 'fa fa-refresh'
                },
                fields: {
                  username: {
                        validators: {
                            notEmpty: {
                                    message: 'validator.requiredField'
                                },
                        }
                    },
                    password: {
                        validators: {
                          notEmpty: {
                            message: 'validator.requiredField'
                        },
                        stringLength: {
                            min: 6,
                            message:'validator.passwordLengthNotLessThan6'
                        }
                        }
                    },
                    newPassword: {
                        validators: {
                            notEmpty: {
                                message: 'validator.requiredField'
                            },
                            identical: {
                                field: 'password',
                                message:'validator.passwordNotMatch'
                            },
                            stringLength: {
                                min: 6,
                                message: 'validator.passwordLengthNotLessThan6'
                            }
                        }
                    },
                    workEmail: {
                        validators: {
                          emailAddress: {
                                message: 'validator.pleaseEnterCorrectFormat'
                            }
                        }
                    },
                    homeEmail: {
                        validators: {
                          emailAddress: {
                                message: 'validator.pleaseEnterCorrectFormat'
                            }
                        }
                    },
                    hintQuestion: {
                        validators: {
                            notEmpty: {
                                    message: 'validator.requiredField'
                                },
                        }
                    },
                    hintAnswer: {
                        validators: {
                            notEmpty: {
                                    message: 'validator.requiredField'
                                },
                        }
                    },
                  
              }
            })
        </script>

</html>
