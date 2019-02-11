<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ea" uri="http://www.esc20.net/tags/employeeaccess-util"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/app/";
%>
	<head>
		<title>TxEIS : Employee Access -  <tiles:getAsString name="title" ignore="true"/>
		</title>
		<meta charset="UTF-8">
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		
		<c:set var="districtId" value='<%= request.getSession().getAttribute("district")%>'/>
		<c:import url="/WEB-INF/views/css-includes.jsp" />
		<fmt:setBundle var="mybundle" basename="districtIcons"/>
		<c:if test="${empty districtId}">
			<c:set var="districtId" value="default"/>
		</c:if>
		<c:import url="/WEB-INF/views/js-includes-top.jsp" />
	</head>
	<body navInquiry="active" class="tundra spring">
			<div id="mainDecoratorHeader">
				<div id="mainDecoratorLogo"/>
			</div>
			<div id="header">		
			<table>
				<tr>
					<td id="logo" rowspan="2"></td>
					<td id="title">
						<h1>
							Employee Access
							<span id="version">Version:<c:out
									value="${versionInfo.versionNumber}" />
								<c:if test="${versionInfo.showBuildNumber}">
									<c:out value=" Build: ${versionInfo.buildNumber}" />
								</c:if>
							</span>
						</h1>
					</td>
					<td id="app_options" rowspan="2">
						<table>
							<tr>
								
								<c:if test="${hideLogout}">
								
								<td class="item_left" Exit-hoverclass="left_hover"></td>
								<td class="item hit_area" Exit-hoverclass="hover">
									<a href="${pageContext.request.contextPath}/app/login"
										class="hover_group_trigger hit_area_target" hovergroup="Exit">Return to Login</a>
								</td>
								<td class="item_right" Exit-hoverclass="right_hover"></td>
								</c:if>
								
								<c:if test="${!hideLogout}">
								<td class="item_left" Exit-hoverclass="left_hover"></td>
								<td class="item hit_area" Exit-hoverclass="hover">
									<a href="<c:url value="/j_spring_security_logout" />"
										class="hover_group_trigger hit_area_target" hovergroup="Exit">Logout</a>
								</td>
								<td class="item_right" Exit-hoverclass="right_hover"></td>
								</c:if>
								
								<td class="item_left" Help-hoverclass="left_hover"></td>
								<td class="item hit_area" Help-hoverclass="hover">
		  							<a id="upperRightHelpButton"
  										href="http://${helpURL}<tiles:getAsString name='helpPath' ignore='true'/>?version=${versionInfo.versionNumber}" 
  										class="hover_group_trigger hit_area_target" hovergroup="Help" target="_blank" >Help</a>								
								</td>
								<td class="item_right" Help-hoverclass="right_hover"></td>
								
								<td class="item_left" Help-hoverclass="left_hover"></td>
							</tr>
						</table>
					</td>
				</tr>
				<c:if test="${!hideMenus && enableEA == 1}">
				<tr>
					<td id="navigation" colspan="2">
						<table>
							<tr>
								<td id="inquiry_left"
									class="side_item left_<tiles:getAsString name="navInquiry" ignore="true"/>"
									Inquiry-hoverclass="<tiles:getAsString name="navInquiry" ignore="true"/>left_hover<tiles:getAsString name="navUtilities" ignore="true"/>"
									Inquiry-popupeffectclass="<tiles:getAsString name="navInquiry" ignore="true"/>left_popup<tiles:getAsString name="navUtilities" ignore="true"/>">
								</td>

								<td
									class="nav_item hit_area <tiles:getAsString name="navInquiry" ignore="true"/>"
									Inquiry-hoverclass="<tiles:getAsString name="navInquiry" ignore="true"/>hover"
									Inquiry-popupeffectclass="<tiles:getAsString name="navInquiry" ignore="true"/>nav_popup">
									<a href="javascript:;"
										class="hover_group_trigger popup_trigger menu_item hit_area_target"
										hovergroup="Inquiry" popuptarget="inquiry_menu"
										popuppadding="-4" popupparent="inquiry_left"
										popupeffectgroup="Inquiry" menugroup="Navigation">Inquiry</a>
								</td>

								<td id="self_left"
									class="side_item left_<tiles:getAsString name="navSelf" ignore="true"/>"
									Self-hoverclass="<tiles:getAsString name="navSelf" ignore="true"/>left_hover<tiles:getAsString name="navUtilities" ignore="true"/>"
									Self-popupeffectclass="<tiles:getAsString name="navSelf" ignore="true"/>left_popup<tiles:getAsString name="navUtilities" ignore="true"/>">
								</td>

								<td
									class="nav_item hit_area <tiles:getAsString name="navSelf" ignore="true"/>"
									Self-hoverclass="<tiles:getAsString name="navSelf" ignore="true"/>hover"
									Self-popupeffectclass="<tiles:getAsString name="navSelf" ignore="true"/>nav_popup">
									<a href="#"
										class="hover_group_trigger popup_trigger menu_item hit_area_target"
										hovergroup="Self" popuptarget="self_menu"
										popuppadding="-4" popupparent="self_left"
										popupeffectgroup="Self" menugroup="Navigation">Self-Service</a>
								</td>
								
								<c:if test="${enableLeave == 1 || enableLeaveRequest == 1}">
									<td id="leave_left"
										class="side_item left_<tiles:getAsString name="navLeave" ignore="true"/>"
										Leave-hoverclass="<tiles:getAsString name="navLeave" ignore="true"/>left_hover<tiles:getAsString name="navUtilities" ignore="true"/>"
										Leave-popupeffectclass="<tiles:getAsString name="navLeave" ignore="true"/>left_popup<tiles:getAsString name="navUtilities" ignore="true"/>">
									</td>

									<td
										class="nav_item hit_area <tiles:getAsString name="navLeave" ignore="true"/>"
										Leave-hoverclass="<tiles:getAsString name="navLeave" ignore="true"/>hover"
										Leave-popupeffectclass="<tiles:getAsString name="navLeave" ignore="true"/>nav_popup">
										<a href="#"
											class="hover_group_trigger popup_trigger menu_item hit_area_target"
											hovergroup="Leave" popuptarget="leave_menu"
											popuppadding="-4" popupparent="leave_left"
											popupeffectgroup="Leave" menugroup="Navigation">Leave</a>
									</td>
								</c:if>
							</tr>
						</table>
					</td>
				</tr>
				</c:if>
			</table>
		</div>

		<table id="breadcrumbs">
			<tr>
				<td style="width: 33%">
					<tiles:getAsString name="breadcrumb" ignore="true"/>
				</td>
				<c:if test="${currentUser_firstName != null && currentUser_lastName != null}">
				<td style="width: 33%; text-align: center">
					<c:out value="${currentUser_firstName} ${currentUser_lastName}"/>
				</td>
				</c:if>
				<td id="screen_id" style="width:33%">
					Page ID: <tiles:getAsString name="windowid" ignore="true"/>
				</td>
			</tr>
		</table>

		<div id="content">
			<tiles:insertAttribute name="body" />
		</div>

		<div id="inquiry_menu" class="nav_drop_down popup hidden"
			containingmenu="Inquiry_menu">
			<table>
				<tr>
					<td class="top_left"></td>
					<td class="top"></td>
					<td class="top_right"></td>
				</tr>
				<tr>
					<td class="left"></td>
					<td class="middle">
						<ul>
							<c:if test="${enableCYD == 1}">
								<li class="hover_style_target hit_area">
									<a href="<c:url value="/app/inquiry/calendar" />"
										class="hover_trigger hit_area_target menu_item"
										menugroup="Inquiry_menu">Calendar Year to Date</a>
								</li>
							</c:if>
							
							<c:if test="${enableCYD != 1}">
								<li class="disabled">
									Calendar Year to Date
								</li>
							</c:if>
						
							<c:if test="${enableCPI == 1}">
								<li class="hover_style_target hit_area">
									<a href="<c:url value="/app/inquiry/pay" />"
										class="hover_trigger hit_area_target menu_item"
										menugroup="Inquiry_menu">Current Pay Information</a>
								</li>
							</c:if>
							
							<c:if test="${enableCPI != 1}">
								<li class="disabled">
									Current Pay Information
								</li>
							</c:if>
						
							<c:if test="${enableDeduct == 1}">
								<li class="hover_style_target hit_area">
									<a href="<c:url value="/app/inquiry/deductions" />"
										class="hover_trigger hit_area_target menu_item"
										menugroup="Inquiry_menu">Deductions</a>
								</li>
							</c:if>
							
							<c:if test="${enableDeduct != 1}">
								<li class="disabled">
									Deductions
								</li>
							</c:if>

							<c:if test="${enableEarn == 1}">
								<li class="hover_style_target hit_area">
									<a href="<c:url value="/app/inquiry/earnings" />"
										class="hover_trigger hit_area_target menu_item"
										menugroup="Inquiry_menu">Earnings</a>
								</li>
							</c:if>
							
							<c:if test="${enableEarn != 1}">
								<li class="disabled">
									Earnings
								</li>
							</c:if>
													
							<c:if test="${enableW2 == 1}">
								<li class="hover_style_target hit_area">
									<a href="<c:url value="/app/inquiry/w2" />"
										class="hover_trigger hit_area_target menu_item"
										menugroup="Inquiry_menu">W-2 Information</a>
								</li>
							</c:if>
							
							<c:if test="${enableW2 != 1}">
								<li class="disabled">
									W-2 Information
								</li>
							</c:if>
							
							<c:if test="${enable1095 == 1}">
								<li >
									<a href="<c:url value="/app/inquiry/ea1095" />"
										class="hover_trigger hit_area_target menu_item"
										menugroup="Inquiry_menu">1095 Information</a>
								</li>
							</c:if>
							
							<c:if test="${enable1095 != 1}">
								<li class="disabled">
									1095 Information
								</li>
							</c:if>
						</ul>
					</td>
					<td class="right"></td>
				</tr>
				<tr>
					<td class="bottom_left"></td>
					<td class="bottom"></td>
					<td class="bottom_right"></td>
				</tr>
			</table>
		</div>

		<div id="self_menu" class="nav_drop_down popup hidden"
			containingmenu="Self_menu">
			<table>
				<tr>
					<td class="top_left"></td>
					<td class="top"></td>
					<td class="top_right"></td>
				</tr>
				<tr>
					<td class="left"></td>
					<td class="middle">
						<ul>
							<li class="hover_style_target hit_area">
								<a href="<c:url value="/app/self/change" />"
									class="hover_trigger hit_area_target menu_item"
									menugroup="Self_menu">Change Password</a>
							</li>
						</ul>
						<ul>
							<c:if test="${enableDemo == 1}">
								<li class="hover_style_target hit_area">
									<a href="<c:url value="/app/self/demo" />"
										class="hover_trigger hit_area_target menu_item"
										menugroup="Self_menu">Demographic Information</a>
								</li>
							</c:if>
							<c:if test="${enableDemo != 1}">
								<li class="disabled">
									Demographic Information
								</li>
							</c:if>
						</ul>
						<ul>
							<c:if test="${enablePayroll == 1}">
								<li class="hover_style_target hit_area">
									<a href="<c:url value="/app/self/payroll" />"
										class="hover_trigger hit_area_target menu_item"
										menugroup="Self_menu">Payroll Information</a>
								</li>
							</c:if>
							<c:if test="${enablePayroll != 1}">
								<li class="disabled">
									Payroll Information
								</li>
							</c:if>
						</ul>
					</td>
					<td class="right"></td>
				</tr>
				<tr>
					<td class="bottom_left"></td>
					<td class="bottom"></td>
					<td class="bottom_right"></td>
				</tr>
			</table>
		</div>

	  	<div id="leave_menu" class="nav_drop_down popup hidden" containingmenu="Leave_menu">
	  	<table style="width: 100px;">
	 		<tr>
	  			<td class="top_left"></td>
	  			<td class="top"></td>
	  			<td class="top_right"></td>
	  		</tr>
	  		<tr>
	  			<td class="left"></td>
	  			<td class="middle">
	  				<ul>
	  					<c:if test="${enableLeave == 1}">
				  				<li class="hover_style_target hit_area">
				  					<a href="<c:url value="/app/inquiry/leave"/>" class="hover_trigger hit_area_target menu_item"
				  						menugroup="Leave_menu">Leave Balances</a>
			  					</li>
						</c:if>
<!-- 						<c:if test="${enableLeave != 1}">	-->
<!-- 							<li class="disabled">			-->
<!-- 								Leave Balances				-->
<!-- 							</li>							-->
<!-- 						</c:if>								-->
						<c:if test="${enableLeaveRequest == 1}">
				  				<li class="hover_style_target hit_area">
			  						<a href="<c:url value="/app/leave/createEditLeaveRequest"/>" class="hover_trigger hit_area_target menu_item"
			  							menugroup="Leave_menu">Leave Requests</a>
		  						</li>
								<c:if test="${enableLeaveRequestSupervisor==1 || enableLeaveRequestTemporaryApprover==1}">
									<li id="leave_approver_container" class="hover_style_target popup_style_target hit_area">
						  				<a href="#" class="hover_trigger menu_item popup_trigger hit_area_target" menugroup="Leave_menu"
						  					popuptarget="leave_approver_menu" popuporientation="left" popupparent="leave_approver_container"
						  					popuppadding="-5" popupoffset="-7">Supervisor</a>
									</li>
								</c:if>
						</c:if>
			  		</ul>
			  	</td>
			  	<td class="right"></td>
	  		</tr>
	  		<tr>
	  			<td class="bottom_left"></td>
	  			<td class="bottom"></td>
	  			<td class="bottom_right"></td>
	  		</tr>
	  	</table>
	  	</div>

	  	<div id="leave_approver_menu" class="nav_drop_down popup hidden">
	  	<table style="width: 100px;">
	 		<tr>
	  			<td class="top_left"></td>
	  			<td class="top"></td>
	  			<td class="top_right"></td>
	  		</tr>
	  		<tr>
	  			<td class="left"></td>
	  			<td class="middle">
	  				<ul>
	  							<c:if test="${enableLeaveRequestSupervisor==1 || enableLeaveRequestTemporaryApprover==1}">
		  							<li>
				  						<a href="<c:url value="/app/leave/leaveRequestSubmittals"/>" class="hover_trigger hit_area_target menu_item"
				  							menugroup="leave_approver_menu">Approve Leave Requests</a>			  						
			  						</li>
		  						</c:if>
		  						<c:if test="${enableLeaveRequestSupervisor==1}">
				  					<li class="hover_style_target hit_area">
			  							<a href="<c:url value="/app/leave/leaveRequestManageStaff"/>" class="hover_trigger hit_area_target">Leave Overview</a>
		  							</li> 
				  					<li class="hover_style_target hit_area">
			  							<a href="<c:url value="/app/leave/leaveRequestCalendar"/>" class="hover_trigger hit_area_target">Calendar</a>
		  							</li> 
				  					<li class="hover_style_target hit_area">
			  							<a href="<c:url value="/app/leave/leaveRequestTemporaryApprovers"/>" class="hover_trigger hit_area_target">Set Temporary Approvers</a>
		  							</li> 
		  						</c:if>
			  		</ul>
			  	</td>
			  	<td class="right"></td>
	  		</tr>
	  		<tr>
	  			<td class="bottom_left"></td>
	  			<td class="bottom"></td>
	  			<td class="bottom_right"></td>
	  		</tr>
	  	</table>
	  	</div>


		<c:import url="/WEB-INF/views/js-includes-bottom.jsp" />

	</body>
</html>
