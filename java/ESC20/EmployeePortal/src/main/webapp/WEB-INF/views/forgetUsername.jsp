<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html> 
<html lang="en">
    <head>
        <title>${sessionScope.languageJSON.forgotUsername.forgotUsername}</title>
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
                    <li><a id="recoverNameTab" href="#recoverNamePanel" data-toggle="tab" class="active">${sessionScope.languageJSON.forgotUsername.recoverUsername}</a></li>
                </ul>
                <div id="homeTabContent" class="tab-content">
                    <div class="tab-pane fade active in" id="recoverNamePanel">
                            <div class="panel-body step-wizard p-l-r-50">
                                    <ul class="nav nav-tabs step-anchor account-common-step" id="myTab" role="tablist">
                                        <li class="nav-item">
                                            <a class="nav-link active" id="infoTab" role="tab" aria-controls="infoStep" aria-selected="true" tabindex="0"> 
                                                <span class="step-number">
                                                    ${sessionScope.languageJSON.createAccount.userInformation}
                                                </span>
                                            </a>
                                        </li>
                                        <li class="nav-item">
                                            <a class="nav-link" id="securityTab" role="tab" aria-controls="securityStep" aria-selected="false" tabindex="0" aria-disabled="true"> 
                                                <span class="step-number">
                                                        ${sessionScope.languageJSON.createAccount.securityQuestion}
                                                </span>
                                            </a>
                                        </li>
                                        <li class="nav-item">
                                            <a class="nav-link" id="completeTab" role="tab" aria-controls="completeStep" aria-selected="false" tabindex="0" aria-disabled="true"> 
                                                <span class="step-number">
                                                        ${sessionScope.languageJSON.createAccount.complete}
                                                </span>
                                            </a>
                                        </li>
                                    </ul>
                                    <div class="tab-content" id="recoverNameContent">
                                        <div class="tab-pane fade show active" id="infoStep" role="tabpanel" aria-labelledby="infoTab">
                                            <p class="alert alert-error" style="display:none" role="alert" aria-atomic="true" id="noEmployeeError">${sessionScope.languageJSON.validator.noEmployeeAccountAssociated}</p>
                                            <form class="form-horizontal form-signin" id="personalDetailForm" method="post" role="form" autocomplete="off">
                                                <c:if test="${idType=='S'}">
                                                    <div class="form-group has-right-msg">
                                                        <label for="SSNumber" class="control-label-title col-md-3 text-right">
                                                            ${sessionScope.languageJSON.createAccount.ssn} (<span>123456789</span>):
                                                        </label>
                                                        <div class="col-md-5">
                                                            <div class="input-group-relative icon-group has-hint-box">
                                                                <i class="fa fa-user left-icon" aria-hidden="true"></i> 
                                                                <input type="text" id="SSNumber" class="form-control" name="ssn" placeholder="${sessionScope.languageJSON.createAccount.ssn}" required="required" autocomplete="off" maxlength="9"> 
                                                            </div>
                                                        </div>
                                                    </div>
                                                </c:if>
                                                <c:if test="${idType=='E'}">
                                                    <div class="form-group has-right-msg">
                                                        <label for="employeeNumber" class="control-label-title col-md-3 text-right">
                                                            ${sessionScope.languageJSON.label.employeeNumber} (000100):
                                                        </label>
                                                        <div class="col-md-5">
                                                            <div class="input-group-relative icon-group has-hint-box">
                                                                <i class="fa fa-user left-icon" aria-hidden="true"></i> 
                                                                <input type="text" id="employeeNumber" class="form-control" name="empNumber" placeholder="${sessionScope.languageJSON.label.employeeNumber}" required="required" autocomplete="off" maxlength="9"> 
                                                            </div>
                                                        </div>
                                                    </div>
                                                </c:if>
                                                <div class="form-group has-right-msg">
                                                    <label for="birthDate" class="control-label-title col-md-3 text-right">
                                                            ${sessionScope.languageJSON.label.dateOfBirth} (mm/dd/yyyy):
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
                                                            ${sessionScope.languageJSON.label.zipCode}  (12345):
                                                    </label>
                                                    <div class="col-md-5">
                                                        <div class="input-group-relative icon-group has-hint-box">
                                                            <i class="fa fa-key left-icon" aria-hidden="true"></i> 
                                                            <input type="text" id="zipCode" class="form-control" name="zipCode" placeholder="${sessionScope.languageJSON.label.zipCode}" required="required" autocomplete="off">
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
                                        <div class="tab-pane fade" id="securityStep" role="tabpanel" aria-labelledby="securityTab">
                                            <p>${sessionScope.languageJSON.forgotUsername.step2Des}</p>
                                            <hr>
                                            <p class="alert alert-error" style="display:none" role="alert" aria-atomic="true" id="answerError">
                                                    ${sessionScope.languageJSON.validator.answerError}
                                            </p>
                                            <form class="form-horizontal form-signin" role="form" id="securityForm">
                                                <div class="form-group">
                                                    <label for="question1" class="control-label-title col-md-3 text-right">
                                                            ${sessionScope.languageJSON.label.hintQuestion}:
                                                    </label>
                                                    <div class="col-md-5">
                                                        <div id="questionText" style="padding-top: 11px;">
                                                            
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
                                                                <input type="hidden" id="answerHidden" name="answerHidden">
                                                            </div>
                                                        </div>
                                                        
                                                </div>
                                            </form>
                                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    
                                            <hr />
                                            <div class="text-right">
                                                <button type="button" class="btn btn-success back-step1">${sessionScope.languageJSON.buttons.back}</button>
                                                <button class="btn btn-success" id="getUsernameBtn">
                                                        ${sessionScope.languageJSON.buttons.next}
                                                </button>
                                                <button type="button" class="btn btn-default-red cancel-btn" onClick="backToLogin('<%=request.getContextPath().split("/")[1]%>', '${sessionScope.districtId}')" >
                                                    ${sessionScope.languageJSON.buttons.cancel}
                                                </button>
                    
                                            </div>
                                        </div>
                                        <div class="tab-pane fade" id="completeStep" role="tabpanel" aria-labelledby="completeTab">
                    
                    
                                            <div class="row finish-content">
                                                <div class="text-center">
                                                    <div class="text-primary completeMsg">
                                                        ${sessionScope.languageJSON.forgotUsername.congratulation} <br>
                                                        ${sessionScope.languageJSON.forgotUsername.hereItIs}: <b id="usernameShow">username</b>
                                                    </div>
                    
                                                </div>
                    
                                            </div>
                                            <hr />
                                            <div class="text-right">
                                                <button class="btn btn-success" id="finishBtn" onClick="backToLogin('<%=request.getContextPath().split("/")[1]%>', '${sessionScope.districtId}')">
                                                    ${sessionScope.languageJSON.buttons.finish}
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                    
                                </div>
                    </div>

                </div>
            </div>				                           
            <!-- ALC-26 Show modal when Session time is out -->
            <%@ include file="modal/sessionWarnModal.jsp"%>
            <%@ include file="commons/footerNotLoginNew.jsp"%>
    </body>
	<script>
            var districtID = '${sessionScope.districtId}'
    </script>
    <script src="/<%=request.getContextPath().split("/")[1]%>/js/plug-in/jquery.maskedinput-1.3.1.js"></script>
    <script src="/<%=request.getContextPath().split("/")[1]%>/js/viewJs/forgotUsername.js"></script>
</html>
