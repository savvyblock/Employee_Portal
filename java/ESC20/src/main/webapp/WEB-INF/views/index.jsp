<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="en">
    <head>
        <title data-localize="headTitle.login"></title>
        <meta http-equiv="Content-Type" content="text/html;charset=utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <%@ include file="commons/header.jsp"%>
    </head>
    <body class="account-wrap">
            <%@ include file="commons/bar-account.jsp"%>
            <div class="account-top">
                <div class="account-inner">
                    <div class="account-left">
                        <img src="/<%=request.getContextPath().split("/")[1]%>/images/ascender_pecan_logo.jpg" alt="" data-localize="logoName.esc">
                    </div>
                    <div class="account-box">
                        <div class="account-logo">
                            <img src="/<%=request.getContextPath().split("/")[1]%>/images/logo-account.png" alt="" data-localize="logoName.esc">
                        </div>
                        <form id="loginForm" class="card" method="get">
                        <h1 class="title" data-localize="label.pleaseSignIn"></h1>
                        <div class="form-group">
                                <label class="form-title" for="inputEmail" data-localize="label.username"></label>
                                <div class="valid-wrap icon-group">
                                    <i class="fa fa-user left-icon"></i>
                                    <input type="text" id="inputEmail" class="form-control" placeholder="" data-localize="label.username" name="username" autofocus>
                                </div>
                                
                            </div>
                            <div class="form-group">
                                <label  class="form-title" for="inputPassword" data-localize="label.password"></label>
                                <div class="valid-wrap icon-group">
                                    <i class="fa fa-lock left-icon"></i>
                                    <input type="password" id="inputPassword" class="form-control" placeholder="" name="password" data-localize="label.password">
                                </div>
                                
                            </div>
                            <p class="error-hint hide" id="errorMessage" data-localize="validator.usernameOrPasswordError"></p>
                            <p class="error-hint hide" id="incorrectMessage" data-localize="validator.usernameOrPasswordIncorrect"></p>
                            <c:if test="${resetPsw!=null && resetPsw=='resetPswSuccess'}">
		                        <div class="valid-wrap error-hint" data-localize="validator.resetPswSuccess"></div>
		                    </c:if>
		                    <c:if test="${resetPsw!=null && resetPsw=='resetPswFaild'}">
		                        <div class="valid-wrap error-hint" data-localize="validator.resetPswFaild"></div>
		                    </c:if>
                            <div class="form-group clearfix">
                                <!-- <div class="checkbox pull-left">
                                    <label>
                                        <input class="icheck" type="checkbox"> Remember Me
                                    </label>
                                </div> -->
                                <a href="https://docs.google.com/forms/d/e/1FAIpQLScVEpUzBsCM1XLzRVieEoJAaFWRZoPEmUU2fZcWz2TyDTsb7g/viewform?usp=pp_url&entry.372715739=Employee+Access+(TxEIS)" target="_blank" data-localize="label.contactUs"></a>
                                <div class="pull-right">
                                    <a href="/<%=request.getContextPath().split("/")[1]%>/forgetPassword" data-localize="label.forgotPassword"></a>
                                </div>
                            </div>
                            <div class="form-group account-btn">
                                <button id="signin" type="submit" class="btn btn-primary" name="signin" data-localize="label.login"></button>
                            </div>
                            
                        </form>
                        
                    </div>
                </div>
            </div>
    </body>
    <script type="text/javascript">
        $(function(){
          $('#loginForm').bootstrapValidator({
  //        live: 'disabled',
          message: 'This value is not valid',
        //   feedbackIcons: {
        //       valid: 'fa fa-check ',
        //       invalid: 'fa fa-times',
        //       validating: 'fa fa-refresh'
        //   },
          fields: {
              username: {
                  validators: {
                      notEmpty: {
                          message: 'validator.usernameCannotBeEmpty'
                      }
                  }
              },
              password: {
                  validators: {
                      notEmpty: {
                          message: 'validator.passwordCannotBeEmpty'
                      }
                  }
              }
          },
  
      });
            $('#signin').on('click',function(){
                var userName = $('#inputEmail').val();
                var userPwd = $('#inputPassword').val();
                var bootstrapValidator = $('#loginForm').data('bootstrapValidator');
                bootstrapValidator.validate();
                if(bootstrapValidator.isValid()){
                    
                    $.ajax({
                        type:'POST',
                        url:'<%=request.getContextPath()%>/login',
                        dataType:'JSON',
                        contentType:'application/json;charset=UTF-8',
                        data:JSON.stringify({
                            userName: userName,
                            userPwd: userPwd
                        }),
                        success : function (res) {
                            if(res.isSuccess == "true"){
                                document.location = '<%=request.getContextPath()%>/home'
                            } else {
                            	$("#incorrectMessage").show();
                            }
                        },
                        error:function(res){
                            $("#errorMessage").show();
                        }
                    });
                }
            });
        });
    </script>
</html>
