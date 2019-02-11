<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="en">
<head>
    <title data-localize="headTitle.changePassword"></title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <%@ include file="commons/header.jsp"%>
</head>
<body class="hold-transition sidebar-mini">
<div class="wrapper">
    <%@ include file="commons/bar.jsp"%>
    <main class="content-wrapper">
        <section class="content">
            <h2 class="clearfix no-print section-title" data-localize="label.changePassword">
            </h2>
            <div class="content-white">
                <form id="updatePassword" action="updatePassword" method="post" style="max-width:350px;">
                    <div class="form-group">
                        <input type="hidden" name="id" value="${id}" />
                        <label class="form-title" data-localize="label.oldPassword"></label>
                        <div class="valid-wrap">
                            <input
                                type="password"
                                class="form-control"
                                placeholder=""
                                data-localize="label.oldPassword"
                                name="oldPassword"
                            />
                        </div>
                    </div>  
                  <div class="form-group">
                        <label class="form-title" data-localize="label.newPassword"></label>
                        <div class="valid-wrap">
                            <input
                                type="password"
                                class="form-control"
                                placeholder=""
                                data-localize="label.newPassword"
                                name="password"
                            />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-title" data-localize="label.confirmPassword"></label>
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
        </section>
    </main>
  </div>  
    <%@ include file="commons/footer.jsp"%>
        
</body>
<script>
    $('#updatePassword').bootstrapValidator({
                live: 'enable',
                trigger: 'blur keyup',
                feedbackIcons: {
                    valid: 'fa fa-check ',
                    // invalid: 'fa fa-times',
                    validating: 'fa fa-refresh'
                },
                fields: {
                  oldPassword: {
                        validators: {
                            notEmpty: {
                                message: 'validator.requiredField'
                            },
                            stringLength: {
                                min: 6,
                                message:
                                    'validator.passwordLengthNotLessThan6'
                            }
                        }
                    },
                    password: {
                        validators: {
                            notEmpty: {
                                message: 'validator.requiredField'
                            },
                            stringLength: {
                                min: 6,
                                message:
                                    'validator.passwordLengthNotLessThan6'
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
                                message:
                                    'validator.twoPasswordsNotMatch'
                            },
                            stringLength: {
                                min: 6,
                                message: 'validator.passwordLengthNotLessThan6'
                            }
                        }
                    }
                }
            })
    </script>
</html>