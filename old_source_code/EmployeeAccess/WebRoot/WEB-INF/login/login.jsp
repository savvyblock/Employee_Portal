<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<!-- Minimal Web pages, starting point for Web Designers -->
<%@ page import="org.springframework.security.ui.AbstractProcessingFilter" %>
<%@ page import="org.springframework.security.ui.webapp.AuthenticationProcessingFilter"%>
<%@ page import="org.springframework.security.AuthenticationException" %>
<%@ page import="org.springframework.util.StringUtils"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
	<head>
		<title>Employee Access Login</title>
		<c:import url="/WEB-INF/views/css-includes.jsp" />
		<link rel="stylesheet" href="/CommonWeb/styles/login-layout.css" type="text/css" media="all" />

		<style type="text/css">
			body {
				background: #EEF5FF;
			}
		</style>

		<c:set var="districtId" value='<%= request.getSession().getAttribute("district")%>'/>
		<fmt:setBundle var="mybundle" basename="districtIcons"/>
		<c:if test="${empty districtId}">
			<c:set var="districtId" value="default"/>
		</c:if>
    	<fmt:message var="icon" key="${districtId}.logo" bundle="${mybundle}"/>

    	<c:if test="${fn:contains(icon,'?')}">
    		<fmt:message var="icon" key="default.logo" bundle="${mybundle}"/>
    	</c:if>
    	
		<link rel="shortcut icon" href="${icon}" />
		
		<c:import url="/WEB-INF/views/js-includes-top.jsp" />
	</head>
	
	<script type="text/javascript">
		$(document).ready(function() { 
			$(".newUserWrapper")
		        .focus(function() { 
		            $("#newUser").attr("src", "/EmployeeAccess/images/newuser_button_hover.png");
		        })
		        .blur(function() {
		            $("#newUser").attr("src", "/EmployeeAccess/images/newuser_button.png");
		        })
		        .mouseover(function() { 
		            $("#newUser").attr("src", "/EmployeeAccess/images/newuser_button_hover.png");
		        })
		        .mouseout(function() {
		            $("#newUser").attr("src", "/EmployeeAccess/images/newuser_button.png");
		        });
	
			$(".forgotPassWrapper")
		        .focus(function() { 
		            $("#forgotPass").attr("src", "/EmployeeAccess/images/forgot_button_hover.png");
		        })
		        .blur(function() {
		            $("#forgotPass").attr("src", "/EmployeeAccess/images/forgot_button.png");
		        })
		        .mouseover(function() { 
		            $("#forgotPass").attr("src", "/EmployeeAccess/images/forgot_button_hover.png");
		        })
		        .mouseout(function() {
		            $("#forgotPass").attr("src", "/EmployeeAccess/images/forgot_button.png");
		        });
			});
	</script>
  <body>
	<div>
		<table id="login-content" style="margin: 16px auto 0 auto; height: 416px;">
			<tr>
				<td id="login-left">
					<img src="${icon}"/>
				</td>

				<td style="color:#FFFFFF; padding-right:15px; height: 256px; ">
					<h1 class="form_following" style="	padding-bottom: 16px; border-bottom: 1px solid;">Employee Access</h1>
				<p id="errors">
					<c:set var="tempPass" value='<%= request.getSession().getAttribute("temp_pass")%>'/>
					<c:if test="${!empty param.login_error}">
						<c:choose>
							<c:when test="${SPRING_SECURITY_LAST_EXCEPTION.message =='Bad credentials'}">
								<c:out value="Unable to login. Username/Password is incorrect."/>
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${tempPass}">
										<c:out value="Unable to login. Temporary password has expired. Please contact your personnel office or request a new password."/>
									</c:when>
									<c:otherwise>
										<c:out value="Unable to login. User account is locked or does not exist. Please contact your personnel office or create a new user."/>
									</c:otherwise>
								</c:choose>
							</c:otherwise>
						</c:choose>
					</c:if>
				</p>
				<p id="passwordRequested">
					<c:set var="passwordRequested" value='<%= request.getSession().getAttribute("passwordRequested")%>'/>
					<c:if test="${passwordRequested && empty param.login_error}">
						<span style="font-weight:bold"><c:out value="Temporary Password sucessfully created. Please check your e-mail."/></span>
					</c:if>
				</p>
				
					<c:set var="districtId" value='<%= request.getSession().getAttribute("district")%>'/>
					<c:choose>
						<c:when test="${districtId == null || districtId == ''}">
						<p>Please enter a District Parameter in the URL.</p>
						</c:when>
						<c:otherwise>
							<form method="post" action="../j_spring_security_check">
								<table class="form_layout form_following" style="width:300px;">
									<tr>
										<td class="label" style="padding-left:15px;"><c:out value="User Name:"/></td>
										<td><input id="username" class="textbox initial_focus ignore_changes wrap_field" name="j_username" size="25" tabindex="1" maxlength="8"/></td>
									</tr>
									<tr>
										<td class="label" style="padding-left:15px"><c:out value="Password:"/></td>
										<td><input type="password" class="textbox ignore_changes" id="password" name="j_password" size="25" tabindex="1" maxlength="9"/></td>
									</tr>
									<tr>
										<td class="label" style="padding-left:15px"><c:out value="County District:"/></td>
										<td style="padding-bottom:3px"><c:out value="${districtId}"/></td>
									</tr>
								</table>
								<table>
									<tr>
										<td style="padding-left:15px">
											<input id="loginButton" type="image" class="button hover_button hidden" tabindex="1" src="${pageContext.request.contextPath}/images/login_button.png" 
												   tabindex="4" alt="Login" hoverSrc="${pageContext.request.contextPath}/images/login_button_hover.png" />
											<a 	title="Login" 
												accesskey="l"
												tabindex="4"
												class="button hover_button button_small hover_trigger link_button default last_field hover_button"
												disabled-class="button_small_disabled" 
												defaultfocus-class="button_small_default_focus" 
												focus-class="button_small_focus" 
												defaulthoverclass="button_small_default_hover" 
												defaultclass="button_small_default" 
												hoverclass="button_small_hover" 
												onclick="$('#loginButton').click();">
												Login
											</a>
										</td>
									</tr>
								</table>
								
								</form>
								
								<table>
									<tr>
										<td style="padding-left:15px">
											<a href="${pageContext.request.contextPath}/app/createNewUser" tabindex="1" class="hidden"><img style="margin:0px" id="newUser" src="${pageContext.request.contextPath}/images/newuser_button.png"  alt=""/></a>
											<a 	title="New User" 
												accesskey="l"
												tabindex="4"
												class="button hover_button button_small hover_trigger link_button default last_field hover_button newUserWrapper ignore_changes"
												disabled-class="button_small_disabled" 
												defaultfocus-class="button_small_default_focus" 
												focus-class="button_small_focus" 
												defaulthoverclass="button_small_default_hover" 
												defaultclass="button_small_default" 
												hoverclass="button_small_hover" 
												onclick="$('#newUser').click();">
												New User
											</a>
										</td>
										<td style="padding-left:10px">
											<a href="${pageContext.request.contextPath}/app/forgotPassword" tabindex="1" class="hidden"><img id="forgotPass" style="margin:0px" src="${pageContext.request.contextPath}/images/forgot_button.png"  alt=""/></a>
											<a 	title="Forgot Password" 
												accesskey="l"
												tabindex="4"
												class="button hover_button button_small hover_trigger link_button default last_field hover_button forgotPassWrapper ignore_changes last_field"
												disabled-class="button_small_disabled" 
												defaultfocus-class="button_small_default_focus" 
												focus-class="button_small_focus" 
												defaulthoverclass="button_small_default_hover" 
												defaultclass="button_small_default" 
												hoverclass="button_small_hover" 
												onclick="$('#forgotPass').click();">
												Forgot Password
											</a>
										</td>
									</tr>
								</table>
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
		</table>
		<p id="disclaimer">
			<span class="important">IMPORTANT:</span><br />
			This is a security-protected system.  Unauthorized use is prohibited.
			Only authorized personnel are allowed to use the system for authorized purposes.
			By logging on, you acknowledge that you are an authorized user.
			<br/>
			<br/>
			<span id="important" class="important">TCC Accessibility Statement:</span><br/>
			We recognize the importance of providing an application that is accessible to the widest possible audience, regardless 
			of technology or ability.  This application endeavors to conform to the World Wide Web Consortium (W3C) <a target="_blank" href="https://www.w3.org/TR/WCAG20/">Web Content Accessibility 
			Guidelines 2.0</a> and we strive to adhere to the accepted guidelines and standards for accessibility and usability as 
			comprehensively as possible.  Should you experience any difficulty accessing this application please
			<a href="https://docs.google.com/forms/d/e/1FAIpQLScVEpUzBsCM1XLzRVieEoJAaFWRZoPEmUU2fZcWz2TyDTsb7g/viewform?usp=pp_url&entry.372715739=Employee+Access+(TxEIS)" target="_blank"> contact us.</a>
		</p>

	</div>
	
	<c:import url="/WEB-INF/views/js-includes-bottom.jsp" />
	<link rel="stylesheet" href="/CommonWeb/styles/login-colors.css" type="text/css" media="all" />
</body>
</html>