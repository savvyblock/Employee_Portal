<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html> 
<html lang="en">
    <head>
        <title>${sessionScope.languageJSON.headTitle.changePassword}</title>
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
        <!-- ALC-26 set default min and max length for password-->
		<c:set var="pwd_max_length" value="${sessionScope.txeisPreferences.pwd_max_length}"></c:set>
        <c:if test="${empty sessionScope.txeisPreferences.pwd_max_length}">
            <c:set var="pwd_max_length" value="46"></c:set>
        </c:if>
        <c:set var="pwd_min_length" value="${sessionScope.txeisPreferences.pwd_length}"></c:set>
        <c:if test="${empty sessionScope.txeisPreferences.pwd_length}">
            <c:set var="pwd_min_length" value="8"></c:set>
        </c:if>
            <%@ include file="commons/bar-account-new.jsp"%>
            <div class="grayBg"></div>
            <div class="mainContent loginBox">
                <div id="homeTabContent" class="panel panel-common panel-noShadow">
                    <div id="createBody">
                            <div class="panel-heading">
                                    <h4>${sessionScope.languageJSON.changePasswordExpired.psdExpired}</h4>
                                    <p>${sessionScope.languageJSON.changePasswordExpired.psdExpiredDesc}</p>
                                </div>
                            <div class="panel-body step-wizard">
                                    <div class="tab-content" id="myTabContent">
                                        <div>
                                            <form class="form-horizontal form-signin" role="form" id="changePdsExpired">
                                                    <div class="alert alert-error" role="alert" id="errorOldPsd" style="display:none">${sessionScope.languageJSON.changePasswordExpired.oldpassErrorMsg}</div>
                                                    <div class="alert alert-error" role="alert" id="notSameError" style="display:none">${sessionScope.languageJSON.changePasswordExpired.notSameError}</div>
                                                    <div class="alert alert-success" role="alert" id="successSubmit" style="display:none">${sessionScope.languageJSON.changePasswordExpired.loginAgain}</div>
                               
                                                <div class="form-group form-group-password invalidMore has-right-msg">
                                                            <label for="currentPsd" class="control-label-title col-md-3 text-right">
                                                                    ${sessionScope.languageJSON.changePasswordExpired.currentPsd}:
                                                            </label>
                                                            <div class="col-md-5">
                                                                <div class="input-group-relative icon-group has-hint-box">
                                                                    <i class="fa fa-key left-icon" aria-hidden="true"></i>
                                                                    <!-- ALC-26  password validation-->
                                                                    <input type="password" id="currentPsd" placeholder="${sessionScope.languageJSON.changePasswordExpired.currentPsd}"  maxlength="${pwd_max_length}" class="form-control" data-toggle="password"  name="currentPsd" required="required" autocomplete="new-password">
                                                                        <div class="input-group-addon">
                                                                            <span class="input-group-text"><i class="fa fa-eye"></i></span>
                                                                        </div>
                                                                </div>
                            
                                                            </div>
                                                        </div>
                                                <div class="form-group form-group-password invalidMore has-right-msg">
                                                    <label for="inputPassword" class="control-label-title col-md-3 text-right">
                                                            ${sessionScope.languageJSON.changePasswordExpired.newPsd}:
                                                    </label>
                                                    <div class="col-md-5">
                                                        <div class="input-group-relative icon-group has-hint-box">
                                                            <i class="fa fa-key left-icon" aria-hidden="true"></i>
                                                            <!-- ALC-26  password validation-->
                                                            <input type="password" id="inputPassword" placeholder="${sessionScope.languageJSON.changePasswordExpired.newPsd}"  maxlength="${pwd_max_length}" class="form-control" data-toggle="password"  name="password" required="required" autocomplete="new-password">
                                                                <div class="input-group-addon">
                                                                    <span class="input-group-text"><i class="fa fa-eye"></i></span>
                                                                </div>
                                                                <div class="input-hint-message">
                                                                    <ul class="validate-list">
                                                                        <li>${sessionScope.languageJSON.createAccount.uservalidate}</li>
                                                                        <li class="noneStyle">
                                                                            <ol>
                                                                                <li>${sessionScope.languageJSON.createAccount.useruppercase}</li>
                                                                                <li>${sessionScope.languageJSON.createAccount.userlowercase}</li>
                                                                                <li>${sessionScope.languageJSON.createAccount.userNumbers}</li>
                                                                                <li>${sessionScope.languageJSON.createAccount.specialCharacters}</li>
                                                                            </ol>
                                                                        </li>
                                                                    </ul>
                                                                </div> 
                                                                <small class="help-block passwordError" style="display: none;"></small>
                                                        </div>
                    
                                                    </div>
                                                </div>
                    
                                                <div class="form-group form-group-password has-right-msg">
                                                    <label for="inputRePassword" class="control-label-title col-md-3 text-right">
                                                            ${sessionScope.languageJSON.label.passwordVerification}:
                                                    </label>
                                                    <div class="col-md-5">
                                                        <div class="input-group-relative icon-group has-hint-box">
                                                            <span class="">
                                                                <i class="fa fa-key left-icon" aria-hidden="true"></i> 
                                                            </span>
                                                            <!-- ALC-26  password validation-->
                                                            <input type="password" id="inputRePassword" placeholder="${sessionScope.languageJSON.label.passwordVerification}" maxlength="${pwd_max_length}" class="form-control" data-toggle="password" name="repassword" required="required">
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
     
                                                <hr />
                                                <div class="text-right">
                                                    <button type="button" class="btn btn-success" id="finishBtn">
                                                        ${sessionScope.languageJSON.buttons.submit}
                                                    </button>
                                                </div>
                                            </form>
                    
                    
                                        </div>
                                    </div>
                    
                                </div>
                    </div>

                </div>
            </div>
            <div class="loadingOn" style="display:none;">
                    <div class="loadEffect">
                        <span></span>
                        <span></span>
                        <span></span>
                        <span></span>
                        <span></span>
                        <span></span>
                        <span></span>
                        <span></span>
                </div>
            </div>
            <%@ include file="modal/sessionWarnModal.jsp"%>
            <%@ include file="commons/footerNotLoginNew.jsp"%>
    </body>
    <!-- ALC-26  password validation from back-end-->
	<script>
            var minPSDLen = "${pwd_min_length}"
            var maxPSDLen = "${pwd_max_length}"
            //ALC-13 get the district ID
            var districtID = '${sessionScope.districtId}'
    </script>
    <script src="<spring:theme code="commonBase"/>scripts/commonValid.js"></script>
    <script src="/<%=request.getContextPath().split("/")[1]%>/js/plug-in/bootstrap-show-password.js"></script>
    <script src="/<%=request.getContextPath().split("/")[1]%>/js/plug-in/jquery.maskedinput-1.3.1.js"></script>
    <script src="/<%=request.getContextPath().split("/")[1]%>/js/viewJs/changePasswordExpired.js"></script>

</html>
