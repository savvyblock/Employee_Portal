<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html> 
<html lang="en">
    <head>
        <title>${sessionScope.languageJSON.headTitle.login}</title>
        <meta http-equiv="Content-Type" content="text/html;charset=utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="/<%=request.getContextPath().split("/")[1]%>/css/bootstrap.min.css">
        <%@ include file="commons/header.jsp"%>
        <link href="https://fonts.googleapis.com/css?family=Nunito:300,400,600&display=swap" rel="stylesheet"> </link>
        <link rel="stylesheet" href="<spring:theme code="commonPortals"/>css/newStyle.css"/>
        <link rel="stylesheet" href="/<%=request.getContextPath().split("/")[1]%>/css/newEPStyle.css">
    </head>
    <!--ALC-13 UI Alignment of ASCENDER and portals-->
    <body class="account-wrap loginPage loginBody">
            <%@ include file="commons/bar-account-new.jsp"%>
            <div class="grayBg"></div>
            <div class="mainContent loginBox">
                <ul id="homeTab" class="nav nav-tabs clearfix">
                    <li><a id="loginTab" href="#loginPanel" data-toggle="tab" class="active">${sessionScope.languageJSON.label.login}</a></li>
                    <li><a id="createTab" href="#createBody" data-toggle="tab">${sessionScope.languageJSON.createAccount.createAccount}</a></li>
                </ul>
                <div id="homeTabContent" class="tab-content">
                    <div class="tab-pane fade active in" id="loginPanel">
                        <div class="account-box flex between">
                            <form id="loginForm" method="post" class="card bv-form" action="/<%=request.getContextPath().split("/")[1]%>/loginEA">
                                <div class="form-warp">
                                    <div class="form-group has-labelRight-msg">
                                        <label class="control-field-label" for="inputEmail" id="inputEmailLabel">
                                            <span>${sessionScope.languageJSON.label.username}</span>
                                        </label>
                                        <div class="valid-wrap icon-group">
                                            <i class="fa fa-user left-icon"></i>
                                            <input type="text" id="inputEmail" class="initial_focus wrap_field form-control" placeholder="${sessionScope.languageJSON.label.username}"name="username" tabindex="1"/>
                                        </div>
                                        
                                    </div>
                                    <div class="form-group has-labelRight-msg">
                                        <label class="control-field-label" for="inputPassword" id="inputPasswordLabel">
                                            <span>${sessionScope.languageJSON.label.password}</span>
                                        </label>
                                        <div class="valid-wrap icon-group">
                                            <i class="fa fa-lock left-icon"></i>
                                            <input type="password" id="inputPassword" class="form-control" placeholder="${sessionScope.languageJSON.label.password}" name="password" tabindex="1"/>
                                        </div>
                                        
                                    </div>
                                </div>
                                
                                
                                <p class="error-hint hide errorMessage" role="alert" aria-atomic="true">${sessionScope.languageJSON.validator.usernameOrPasswordError}</p>
                                <p class="error-hint hide incorrectMessage" role="alert" aria-atomic="true">>${sessionScope.languageJSON.validator.usernameOrPasswordIncorrect}</p>
                                <c:if test="${isUserLoginFailure=='true'}">
                                    <p class="error-hint authenticateFailed" role="alert" aria-atomic="true">${sessionScope.languageJSON.validator.authenticateFailed}</p>
                                </c:if>                            
                                <c:if test="${times3}">
                                    <p class="error-hint" role="alert" aria-atomic="true">${sessionScope.languageJSON.validator.haveEnteredThree}</p>
                                </c:if>
                                <c:if test="${resetLocked}">
                                    <p class="error-hint" role="alert" aria-atomic="true">${sessionScope.languageJSON.validator.resetLocked}</p>
                                </c:if>
                                <c:if test="${userNotRegistered}">
                                    <p class="error-hint" role="alert" aria-atomic="true">${sessionScope.languageJSON.validator.userNotRegistered}</p>
                                </c:if>
                                <c:if test="${resetPsw!=null && resetPsw=='resetPswSuccess'}">
                                    <div class="valid-wrap error-hint" role="alert" aria-atomic="true">${sessionScope.languageJSON.validator.resetPswSuccess}</div>
                                </c:if>
                                <c:if test="${resetPsw!=null && resetPsw=='resetPswFaild'}">
                                    <div class="valid-wrap error-hint" role="alert" aria-atomic="true">${sessionScope.languageJSON.validator.resetPswFaild}</div>
                                </c:if>
                                <c:if test="${resetSuccess == true}">
                                    <p class="success-hint" role="alert" aria-atomic="true">${sessionScope.languageJSON.validator.sendEmailSuccess}</p>
                                </c:if>
                                <c:if test="${resetSuccess == false}">
                                    <p class="error-hint" role="alert" aria-atomic="true">${sessionScope.languageJSON.validator.sendEmailFailed}</p>
                                </c:if>
                                <div class="form-group">
                                    <!-- <a class="btn btn-primary btn-secondary" href="/<%=request.getContextPath().split("/")[1]%>/createUser/searchUser" tabindex="1">${sessionScope.languageJSON.label.newUser}</a> -->
                                    <button id="signin" type="submit" class="btn btn-new btn-login" tabindex="1">${sessionScope.languageJSON.label.login}</button>
                                </div>
                                <div class="form-group clearfix">
                                    <div class="hidden">
                                           <a href="https://docs.google.com/forms/d/e/1FAIpQLScVEpUzBsCM1XLzRVieEoJAaFWRZoPEmUU2fZcWz2TyDTsb7g/viewform?usp=pp_url&entry.372715739=Employee+Access+(TxEIS)" target="_blank">${sessionScope.languageJSON.label.contactUs}</a>
                                    </div>
                                    <div class="pull-right">
                                        <a href="/<%=request.getContextPath().split("/")[1]%>/resetPassword/forgetPassword" tabindex="1" class="last_field">${sessionScope.languageJSON.label.forgotPassword}</a>
                                    </div>
                                </div>
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            </form>
                            <!-- ALC-13 update the login form show when session timeout -->
                            <form id="loginBackForm" style="display: none;" class="card" method="post" action="/<%=request.getContextPath().split("/")[1]%>/loginEA">
                                <p class="logBackWord">${sessionScope.languageJSON.label.sessionTimeOutWord}</p>
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <div class="form-warp">
                                    <div class="form-group has-labelRight-msg">
                                        <label class="control-field-label" id="inputEmailLabelBack" for="inputEmailSession">${sessionScope.languageJSON.label.username}</label>
                                        <div class="valid-wrap icon-group">
                                            <i class="fa fa-user left-icon"></i>
                                            <input type="text" id="inputEmailSession" class="form-control" placeholder="${sessionScope.languageJSON.label.username}" name="username"/>
                                        </div>
                                        
                                    </div>
                                    <div class="form-group has-labelRight-msg">
                                        <label class="control-field-label" id="inputPasswordLabelBack" for="inputPasswordSession">${sessionScope.languageJSON.label.password}</label>
                                        <div class="valid-wrap icon-group">
                                            <i class="fa fa-lock left-icon"></i>
                                            <input type="password" id="inputPasswordSession" class="form-control" placeholder="${sessionScope.languageJSON.label.password}" name="password">
                                        </div>
                                        
                                    </div>
                                </div>
                                <p class="error-hint hide errorMessage" role="alert" aria-atomic="true">${sessionScope.languageJSON.validator.usernameOrPasswordError}</p>
                                <p class="error-hint hide incorrectMessage" role="alert" aria-atomic="true">${sessionScope.languageJSON.validator.usernameOrPasswordIncorrect}</p>
                                <c:if test="${isUserLoginFailure=='true'}">
                                    <p class="error-hint authenticateFailed" role="alert" aria-atomic="true">${sessionScope.languageJSON.validator.authenticateFailed}</p>
                                </c:if>
                                <c:if test="${times3}">
                                    <p class="error-hint" id="haveEnteredThree" role="alert" aria-atomic="true">${sessionScope.languageJSON.validator.haveEnteredThree}</p>
                                </c:if>
                                <c:if test="${resetLocked}">
                                    <p class="error-hint" role="alert" aria-atomic="true">${sessionScope.languageJSON.validator.resetLocked}</p>
                                </c:if>
                                <c:if test="${userNotRegistered}">
                                    <p class="error-hint" role="alert" aria-atomic="true">${sessionScope.languageJSON.validator.userNotRegistered}</p>
                                </c:if>
                                <c:if test="${resetPsw!=null && resetPsw=='resetPswSuccess'}">
                                    <div class="valid-wrap error-hint" role="alert" aria-atomic="true">${sessionScope.languageJSON.validator.resetPswSuccess}</div>
                                </c:if>
                                <c:if test="${resetPsw!=null && resetPsw=='resetPswFaild'}">
                                    <div class="valid-wrap error-hint" role="alert" aria-atomic="true">${sessionScope.languageJSON.validator.resetPswFaild}</div>
                                </c:if>
                                <div class="form-group">
                                    <button id="timeOutSignin" type="submit" class="btn btn-primary btn-login">${sessionScope.languageJSON.label.login}</button>
                                </div>
                                
                            </form>
                            <div class="on-screen-message-content-right">
                                <!-- ALC-13 show the picture via img tag -->
                                <div class="district-photo">
                                    <img class="hidden" src="/<%=request.getContextPath().split("/")[1]%>/getDistrictPicture/${sessionScope.districtId}" alt="District picture">
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="tab-pane fade" id="createBody">
                            <div class="panel-body step-wizard p-l-r-50">
                                    <ul class="nav nav-tabs step-anchor account-common-step" id="myTab" role="tablist">
                                        <li class="nav-item">
                                            <a class="nav-link active" id="step1-tab" role="tab" aria-controls="step1" aria-selected="true" tabindex="0"> 
                                                <span class="step-number">
                                                    ${sessionScope.languageJSON.createAccount.userInformation}
                                                </span>
                                            </a>
                                        </li>
                                        <li class="nav-item">
                                            <a class="nav-link" id="step2-tab" role="tab" aria-controls="step2" aria-selected="false" tabindex="0" aria-disabled="true"> 
                                                <span class="step-number">
                                                        ${sessionScope.languageJSON.createAccount.basicInformation}
                                                </span>
                                            </a>
                                        </li>
                                        <li class="nav-item">
                                            <a class="nav-link" id="step3-tab" role="tab" aria-controls="step3" aria-selected="false" tabindex="0" aria-disabled="true"> 
                                                <span class="step-number">
                                                        ${sessionScope.languageJSON.createAccount.securityQuestion}
                                                </span>
                                            </a>
                                        </li>
                                        <li class="nav-item">
                                            <a class="nav-link" id="step4-tab" role="tab" aria-controls="step4" aria-selected="false" tabindex="0" aria-disabled="true"> 
                                                <span class="step-number">
                                                        ${sessionScope.languageJSON.createAccount.complete}
                                                </span>
                                            </a>
                                        </li>
                                    </ul>
                                    <!-- ALC-13 make input consistent with other portals-->
                                    <div class="tab-content" id="myTabContent">
                                        <div class="tab-pane fade show active" id="step1" role="tabpanel" aria-labelledby="step1-tab">
                                            <p class="error-hint" style="display:none" role="alert" aria-atomic="true" id="EmpExitError">${sessionScope.languageJSON.validator.noUserAccountAssociated}</p>
                                            <p class="error-hint" style="display:none" role="alert" aria-atomic="true" id="noEmployeeError">${sessionScope.languageJSON.validator.noEmployeeAccountAssociated}</p>
                                            <form class="form-horizontal form-signin" id="personalDetailForm" method="post" role="form" autocomplete="off">
                                                <c:if test="${idType=='S'}">
                                                    <div class="form-group has-right-msg">
                                                        <label for="SSNumber" class="control-label-title col-md-3 text-right">
                                                            ${sessionScope.languageJSON.createAccount.ssn}(<span>${sessionScope.languageJSON.label.noDashes}</span>):
                                                        </label>
                                                        <div class="col-md-5">
                                                            <div class="input-group-relative icon-group has-hint-box">
                                                                <i class="fa fa-user left-icon" aria-hidden="true"></i> 
                                                                <input type="text" id="SSNumber" class="form-control" name="ssn" placeholder="${sessionScope.languageJSON.createAccount.ssn}" required="required" autocomplete="off" maxlength="9"> 
                                                                <div class="input-hint-message">
                                                                    <p>
                                                                        ${sessionScope.languageJSON.createAccount.SSNFormat}
                                                                    </p>
                                                                </div> 
                                                            </div>
                                                        </div>
                                                    </div>
                                                </c:if>
                                                <c:if test="${idType=='E'}">
                                                    <div class="form-group has-right-msg">
                                                        <label for="employeeNumber" class="control-label-title col-md-3 text-right">
                                                            ${sessionScope.languageJSON.label.employeeNumber}:
                                                        </label>
                                                        <div class="col-md-5">
                                                            <div class="input-group-relative icon-group has-hint-box">
                                                                <i class="fa fa-user left-icon" aria-hidden="true"></i> 
                                                                <input type="text" id="employeeNumber" class="form-control" name="empNumber" placeholder="${sessionScope.languageJSON.label.employeeNumber}" required="required" autocomplete="off" maxlength="9"> 
                                                                <div class="input-hint-message">
                                                                    <p>
                                                                        ${sessionScope.languageJSON.createAccount.empNumFormat}
                                                                    </p>
                                                                </div> 
                                                            </div>
                                                        </div>
                                                    </div>
                                                </c:if>
                                                <div class="form-group has-right-msg">
                                                    <label for="birthDate" class="control-label-title col-md-3 text-right">
                                                            ${sessionScope.languageJSON.label.dateOfBirth}(${sessionScope.languageJSON.label.mmddyyyy}):
                                                    </label>
                                                    <div class="col-md-5">
                                                        <div class="input-group-relative icon-group has-hint-box">
                                                            <i class="fa fa-calendar left-icon" aria-hidden="true"></i> 
                                                            <input type="text" id="birthDate" class="form-control" placeholder="${sessionScope.languageJSON.label.dateOfBirth}" onfocus="jsMasking(this);" data-type="DATE" placeholder="" name="birthDate" required="required" autocomplete="off">
                                                            <input type="hidden" name="dateMonth">
                                                            <input type="hidden" name="dateDay">
                                                            <input type="hidden" name="dateYear">
                                                        </div>
                                                    </div>
                                                
                                                </div>
                                                <div class="form-group has-right-msg">
                                                    <label for="zipCode" class="control-label-title col-md-3 text-right">
                                                            ${sessionScope.languageJSON.label.zipCode}:
                                                    </label>
                                                    <div class="col-md-5">
                                                        <div class="input-group-relative icon-group has-hint-box">
                                                            <i class="fa fa-key left-icon" aria-hidden="true"></i> 
                                                            <input type="text" id="zipCode" class="form-control" name="zipCode" placeholder="${sessionScope.languageJSON.label.zipCode}" required="required" autocomplete="off">
                                                            <div class="input-hint-message">
                                                                <p>
                                                                    ${sessionScope.languageJSON.createAccount.zipCodeFormat}
                                                                </p>
                                                            </div> 
                                                        </div>
                                                    </div>
                                                </div>
                                                <hr />
                                                <div class="text-right">
                                                    <button type="button" class="btn btn-success next-step1">
                                                        ${sessionScope.languageJSON.buttons.next}
                                                    </button>
                                                    <button type="button" class="btn btn-default-red cancel-btn" onClick="backToLogin('<%=request.getContextPath().split("/")[1]%>', '${sessionScope.districtId}')">
                                                        ${sessionScope.languageJSON.buttons.cancel}
                                                    </button>
                    
                                                </div>
                                            </form>
                                        </div>
                                        <div class="tab-pane fade" id="step2" role="tabpanel" aria-labelledby="step2-tab">
                                            <c:if test="${isUserExist!=null && isUserExist=='true'}">
                                                <p class="error-hint"  role="alert" aria-atomic="true" id="noUserError">${sessionScope.languageJSON.validator.userExist}</p>
                                            </c:if>
                                            <form class="form-horizontal form-signin" role="form" id="accountDetailForm">
                                                <div class="form-group has-right-msg">
                                                    <label for="usernameCreate" class="control-label-title col-md-3 text-right">
                                                            ${sessionScope.languageJSON.label.username}:
                                                    </label>
                                                    <div class="col-md-5">
                                                        <div class="input-group-relative icon-group has-hint-box">
                                                            <i class="fa fa-user left-icon" aria-hidden="true"></i> 
                                                            <input type="text" id="usernameCreate" class="form-control" name="txtUsername" placeholder="${sessionScope.languageJSON.label.username}" required="required" autocomplete="off">
                                                            <small class="help-block userNameError" style="display: none;"></small>
                                                            <div class="input-hint-message">
                                                                <p>
                                                                    ${sessionScope.languageJSON.createAccount.usernameValid}
                                                                </p>
                                                            </div> 
                                                        </div>
                                                    </div>
                                
                                                </div>
                                                <div class="form-group form-group-password invalidMore has-right-msg">
                                                    <label for="passwordCreate" class="control-label-title col-md-3 text-right">
                                                            ${sessionScope.languageJSON.label.password}:
                                                    </label>
                                                    <div class="col-md-5">
                                                        <div class="input-group-relative icon-group has-hint-box">
                                                            <i class="fa fa-key left-icon" aria-hidden="true"></i>
                                                            <!-- ALC-26  password validation-->
                                                            <input type="password" id="passwordCreate" placeholder="${sessionScope.languageJSON.label.password}"  maxlength="${sessionScope.txeisPreferences.pwd_max_length}" class="form-control" data-toggle="password"  name="txtPassword" required="required" autocomplete="new-password">
                                                                <div class="input-group-addon">
                                                                    <span class="input-group-text"><i class="fa fa-eye"></i></span>
                                                                </div>
                                                                <div class="input-hint-message">
                                                                    <ul class="validate-list">
                                                                        <li>${sessionScope.languageJSON.createAccount.usernameValid}</li>
                                                                        <li class="noneStyle">
                                                                            <ol>
                                                                                <li>${sessionScope.languageJSON.createAccount.uservalidate}</li>
                                                                                <li>${sessionScope.languageJSON.createAccount.userlowercase}</li>
                                                                                <li>${sessionScope.languageJSON.createAccount.useruppercase}</li>
                                                                                <li>${sessionScope.languageJSON.createAccount.userNumbers}</li>
                                                                            </ol>
                                                                        </li>
                                                                    </ul>
                                                                </div> 
                                                                <small class="help-block passwordError" style="display: none;"></small>
                                                        </div>
                    
                                                    </div>
                                                </div>
                    
                                                <div class="form-group form-group-password has-right-msg">
                                                    <label for="newPassword" class="control-label-title col-md-3 text-right">
                                                            ${sessionScope.languageJSON.label.passwordVerification}:
                                                    </label>
                                                    <div class="col-md-5">
                                                        <div class="input-group-relative icon-group has-hint-box">
                                                            <span class="">
                                                                <i class="fa fa-key left-icon" aria-hidden="true"></i> 
                                                            </span>
                                                            <!-- ALC-29  password validation-->
                                                            <input type="password" id="newPassword" placeholder="${sessionScope.languageJSON.label.passwordVerification}" maxlength="${sessionScope.txeisPreferences.pwd_max_length}" class="form-control" data-toggle="password" name="newPassword" required="required">
                                                            <div class="input-group-addon">
                                                                <span class="input-group-text"><i class="fa fa-eye"></i></span>
                                                            </div> 
                                                            <div class="input-hint-message">
                                                                <p>${sessionScope.languageJSON.createAccount.psdMatchEx}</p>
                                                            </div> 
                                                            <small class="help-block repasswordError" style="display: none;"></small>
                                                        </div>
                    
                                                    </div>
                                                </div>
                                                <div class="form-group form-group-password has-right-msg">
                                                    <label for="workEmail" class="control-label-title col-md-3 text-right">
                                                        ${sessionScope.languageJSON.label.workEmail}:
                                                    </label>
                                                    <div class="col-md-5">
                                                        <div class="input-group-relative icon-group has-hint-box">
                                                            <i class="fa fa-envelope left-icon" aria-hidden="true"></i> 
                                                            <c:choose>
                                                                <c:when test="${empty user.userEmail}">
                                                                    <input type="text" id="workEmail" placeholder="${sessionScope.languageJSON.label.workEmail}" class="form-control" name="workEmail" required="required">
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <div id="staticWorkEmail">
                                                                            ${user.userEmail}
                                                                    </div>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </div>
                    
                                                    </div>
                                                </div>
                                                <c:if test="${empty user.userEmail}">
                                                    <div class="form-group form-group-password has-right-msg">
                                                        <label for="verifyWorkEmail" class="control-label-title col-md-3 text-right">
                                                            ${sessionScope.languageJSON.label.workEmailVerify}:
                                                        </label>
                                                        <div class="col-md-5">
                                                            <div class="input-group-relative icon-group has-hint-box">
                                                                <i class="fa fa-envelope left-icon" aria-hidden="true"></i> 
                                                                <input type="text" id="verifyWorkEmail" placeholder="${sessionScope.languageJSON.label.workEmailVerify}" class="form-control" name="workEmailVerify" required="required">
                                                            </div>
                        
                                                        </div>
                                                    </div>
                                                </c:if>
                                                <div class="form-group form-group-password has-right-msg">
                                                        <label for="homeEmail" class="control-label-title col-md-3 text-right">
                                                            ${sessionScope.languageJSON.label.homeEmail}:
                                                        </label>
                                                        <div class="col-md-5">
                                                            <div class="input-group-relative icon-group has-hint-box">
                                                                <i class="fa fa-envelope left-icon" aria-hidden="true"></i> 
                                                                <c:choose>
                                                                    <c:when test="${empty user.userHomeEmail}">
                                                                        <input type="text" id="homeEmail" class="form-control" placeholder="${sessionScope.languageJSON.label.homeEmail}" name="homeEmail" required="required">
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <div id="staticWorkEmail">
                                                                                ${user.userHomeEmail}
                                                                        </div>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </div>
                        
                                                        </div>
                                                    </div>
                                                    <c:if test="${empty user.userHomeEmail}">
                                                        <div class="form-group form-group-password has-right-msg">
                                                            <label for="verifyHomeEmail" class="control-label-title col-md-3 text-right">
                                                                ${sessionScope.languageJSON.label.homeEmailVerify}:
                                                            </label>
                                                            <div class="col-md-5">
                                                                <div class="input-group-relative icon-group has-hint-box">
                                                                    <i class="fa fa-envelope left-icon" aria-hidden="true"></i> 
                                                                    <input type="text" id="verifyHomeEmail" class="form-control" name="homeEmailVerify" placeholder="${sessionScope.languageJSON.label.homeEmailVerify}" required="required">
                                                                </div>
                            
                                                            </div>
                                                        </div>
                                                    </c:if>
                                                <hr />
                                                <div class="text-right">
                                                    <button type="button" class="btn btn-default back-step1">
                                                        ${sessionScope.languageJSON.buttons.back}
                                                    </button>
                                                    <button type="button" class="btn btn-success next-step2">
                                                            ${sessionScope.languageJSON.buttons.next}
                                                    </button>
                                                    <button type="button" class="btn btn-default-red cancel-btn" onClick="backToLogin('<%=request.getContextPath().split("/")[1]%>','${sessionScope.districtId}')">
                                                        ${sessionScope.languageJSON.buttons.cancel}
                                                    </button>
                    
                                                </div>
                                            </form>
                    
                    
                                        </div>
                                        <div class="tab-pane fade" id="step3" role="tabpanel" aria-labelledby="step3-tab">
                                                <!-- ALC-13 Added description for security question tab -->
                                            <p>${sessionScope.languageJSON.createAccount.securityQuestionDes}</p>
                                            <hr>
                                            <form class="form-horizontal form-signin" role="form" id="securityForm">
                                                <div class="form-group">
                                                    <label for="question1" class="control-label-title col-md-3 text-right">
                                                            ${sessionScope.languageJSON.label.hintQuestion}:
                                                    </label>
                                                    <div class="col-md-5">
                                                        <div class="input-group-relative has-hint-box">
                                                            <input type="text" class="form-control" id="hintQuestion" name="hintQuestion" placeholder="${sessionScope.languageJSON.label.hintQuestion}" autocomplete="off">
                                                        </div>
                                                    </div>
                    
                                                </div>
                                                <div class="form-group has-right-msg">
                                                        <label for="question1" class="control-label-title col-md-3 text-right">
                                                                ${sessionScope.languageJSON.label.hintAnswer}:
                                                        </label>
                                                        <div class="col-md-5">
                                                            <div class="input-group-relative has-hint-box">
                                                                <input type="text" class="form-control" id="hintAnswer" name="hintAnswer" placeholder="${sessionScope.languageJSON.label.hintAnswer}" autocomplete="off">
                                                                <small class="help-block sameAnswer" role="alert" aria-atomic="true" style="display: none;">
                                                                    ${sessionScope.languageJSON.validator.notSameAnswer}
                                                                </small>
                                                            </div>
                                                        </div>
                                                        
                                                </div>
                                            </form>
                                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    
                                            <hr />
                                            <div class="text-right">
                                                <button type="button" class="btn btn-default back-step2">${sessionScope.languageJSON.buttons.back}</button>
                                                <button class="btn btn-success" id="createAccount">
                                                        ${sessionScope.languageJSON.buttons.next}
                                                </button>
                                                <button type="button" class="btn btn-default-red cancel-btn" onClick="backToLogin('<%=request.getContextPath().split("/")[1]%>', '${sessionScope.districtId}')" >
                                                    ${sessionScope.languageJSON.buttons.cancel}
                                                </button>
                    
                                            </div>
                                        </div>
                                        <div class="tab-pane fade" id="step4" role="tabpanel" aria-labelledby="step4-tab">
                    
                    
                                            <div class="row finish-content">
                                                <div class="text-center">
                                                    <div class="text-primary completeMsg">
                                                        ${sessionScope.languageJSON.createAccount.thankU}
                                                    </div>
                    
                                                </div>
                    
                                            </div>
                                            <hr />
                                            <form id="loginFormCreate" method="post" style="visibility: hidden" action="/<%=request.getContextPath().split("/")[1]%>/loginEA">
                                                <input type="hidden" id="loginUsername" class="form-control" placeholder="${sessionScope.languageJSON.label.username}" name="username"/>
                                                <input type="hidden" id="loginPassword" class="form-control" placeholder="${sessionScope.languageJSON.label.password}" name="password"/>
                                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                            </form>
                                            <div class="text-right">
                                                <a href="javascript:void(0)" class="btn btn-success" id="finishBtn">
                                                    ${sessionScope.languageJSON.buttons.finish}
                                                </a>
                                            </div>
                                        </div>
                                    </div>
                    
                                </div>
                    </div>

                </div>
                <c:if test="${not empty alertMsg}"> 
                        <div class="serverMsg" id="alert">
                            ${alertMsg}
                        </div>
                </c:if>
            </div>
            <div class="account-inner logBackBox hide">
                    <h1 class="logBackTitle">${sessionScope.languageJSON.label.sessionTimeOut}</h1>
                    <div class="logBackWord">${sessionScope.languageJSON.label.sessionTimeOutWord}</div>
                    <div class="account-box">
                        <form id="loginBackForm" class="card" method="post" action="/<%=request.getContextPath().split("/")[1]%>/loginEA">
                        	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <h1 class="title empPortColor">${sessionScope.languageJSON.label.pleaseSignIn}</h1>
                            <div class="form-group">
                                <label for="inputEmailSession">${sessionScope.languageJSON.label.username}</label>
                                <div class="valid-wrap icon-group">
                                    <i class="fa fa-user left-icon"></i>
                                    <input type="text" id="inputEmailSession" class="form-control" placeholder="${sessionScope.languageJSON.label.username}" name="username"/>
                                </div>
                                
                            </div>
                            <div class="form-group">
                                <label for="inputPasswordSession">${sessionScope.languageJSON.label.password}</label>
                                <div class="valid-wrap icon-group">
                                    <i class="fa fa-lock left-icon"></i>
                                    <input type="password" id="inputPasswordSession" class="form-control" placeholder="${sessionScope.languageJSON.label.password}" name="password">
                                </div>
                                
                            </div>
                            <p class="error-hint hide errorMessage" role="alert" aria-atomic="true">${sessionScope.languageJSON.validator.usernameOrPasswordError}</p>
                            <p class="error-hint hide incorrectMessage" role="alert" aria-atomic="true">${sessionScope.languageJSON.validator.usernameOrPasswordIncorrect}</p>
                            <c:if test="${isUserLoginFailure=='true'}">
                                <p class="error-hint authenticateFailed" role="alert" aria-atomic="true">${sessionScope.languageJSON.validator.authenticateFailed}</p>
                            </c:if>
                            <c:if test="${times3}">
                                <p class="error-hint" id="haveEnteredThree" role="alert" aria-atomic="true">${sessionScope.languageJSON.validator.haveEnteredThree}</p>
                            </c:if>
                            <c:if test="${resetLocked}">
                                <p class="error-hint" role="alert" aria-atomic="true">${sessionScope.languageJSON.validator.resetLocked}</p>
                            </c:if>
                            <c:if test="${userNotRegistered}">
                                <p class="error-hint" role="alert" aria-atomic="true">${sessionScope.languageJSON.validator.userNotRegistered}</p>
                            </c:if>
                            <c:if test="${resetPsw!=null && resetPsw=='resetPswSuccess'}">
		                        <div class="valid-wrap error-hint" role="alert" aria-atomic="true">${sessionScope.languageJSON.validator.resetPswSuccess}</div>
		                    </c:if>
		                    <c:if test="${resetPsw!=null && resetPsw=='resetPswFaild'}">
		                        <div class="valid-wrap error-hint" role="alert" aria-atomic="true">${sessionScope.languageJSON.validator.resetPswFaild}</div>
		                    </c:if>
                            <div class="form-group account-btn">
                                <button id="timeOutSignin" type="submit" class="btn btn-primary">${sessionScope.languageJSON.label.login}</button>
                            </div>
                            
                        </form>
                       
                    </div>
                    
                </div>					                           

            
            <%@ include file="commons/footerNotLoginNew.jsp"%>
    </body>
    <!-- ALC-26  password validation from back-end-->
	<script>
            var minPSDLen = "${sessionScope.txeisPreferences.pwd_length}"
            var maxPSDLen = "${sessionScope.txeisPreferences.pwd_max_length}"
            //ALC-13 get the district ID
            var districtID = '${sessionScope.districtId}'
    </script>
    <script src="<spring:theme code="commonBase"/>scripts/commonValid.js"></script>
    <script src="/<%=request.getContextPath().split("/")[1]%>/js/plug-in/bootstrap-show-password.js"></script>
    <script src="/<%=request.getContextPath().split("/")[1]%>/js/plug-in/jquery.maskedinput-1.3.1.js"></script>
        <script src="/<%=request.getContextPath().split("/")[1]%>/js/viewJs/createAccount.js"></script>
    <script src="/<%=request.getContextPath().split("/")[1]%>/js/viewJs/login.js"></script>

</html>
