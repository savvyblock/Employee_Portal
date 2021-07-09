<button id="skipNav">${sessionScope.languageJSON.label.skipNav}</button>
<%@ page contentType="text/html; charset=UTF-8" import="java.util.ResourceBundle" %>
<%ResourceBundle res = ResourceBundle.getBundle("system"); %>
<%
    String sessionTimeOut = res.getString("sessionTimeOut");
	long token=System.currentTimeMillis();
	session.setAttribute("token",token);
%>
<%@ page import="com.esc20.util.ManifestUtil" %>
<%
 String version = ManifestUtil.getVersionInfo(request.getServletContext());
%>
<script>
  var sessionTimeOut = "<%=sessionTimeOut%>";
</script>
<!-- Navbar -->
<nav class="main-header navbar navbar-expand navbar-light clearfix">
  <!-- Left navbar links -->
  <ul class="navbar-nav">
    <li class="nav-item">
      <button class="nav-link nobtn-style bars-btn" data-widget="pushmenu"
        aria-label="${sessionScope.languageJSON.accessHint.collapseOrExpand}">
        <i class="fa fa-bars"></i>
      </button>
    </li>
  </ul>
  <%@ include file="logo.jsp"%>
  <!-- Right navbar links -->
  <c:if test="${not empty sessionScope.options.messageEmployeeAccessSystem}">
		 <p class="empCalendarMessageColor">${sessionScope.options.messageEmployeeAccessSystem}</p>
  </c:if>
  <c:if test="${empty sessionScope.options.messageEmployeeAccessSystem}">
        <p class="empCalendarMessageColor">${sessionScope.constantJSON.label.welcomeToNewEmployeePortal}</p>
  </c:if>
  <ul class="navbar-nav nav-right">
    <!-- Notifications Dropdown Menu -->
    <li class="nav-item dropdown">
      <button class="nav-link nobtn-style" data-toggle="dropdown"
        aria-label="${sessionScope.languageJSON.accessHint.showOrHideNote}">
        <i class="fa fa-bell"></i>
        <span id="navBadge" class="navbar-badge"></span>
      </button>
      <div class="dropdown-menu dropdown-menu-right note-dropdown">
        <div class="dropdown-header">
          <label id="budgeCount">0</label> <label>${sessionScope.languageJSON.label.notification}</label>
        </div>
        <a href="/<%=request.getContextPath().split("/")[1]%>/notifications/notifications">
          <div class="dropdown-divider"></div>
          <div class="dropdown-item">
            <div id="top5Alert"></div>
          </div>
          <div class="dropdown-divider"></div>
        </a>
        <a href="/<%=request.getContextPath().split("/")[1]%>/notifications/notifications" class="dropdown-footer"
          role="link">${sessionScope.languageJSON.label.seeAllNote}</a>
      </div>
    </li>
    <li class="nav-item">
      <a class="nav-link" <c:if
        test="${sessionScope.enableSelfServiceDemographic||sessionScope.enableSelfServicePayroll}">
        href="/<%=request.getContextPath().split("/")[1]%>/profile/profile"
        </c:if>
        aria-label="${sessionScope.languageJSON.accessHint.goMyAccount}" role="link">
        <i class="fa fa-user"></i>
      </a>
    </li>
    <li class="nav-item flex-middle">
      <%@ include file="global-select.jsp"%>
    </li>
    <li class="nav-item">
      <form action="/<%=request.getContextPath().split("/")[1]%>/logoutEA" method="post">
        <button class="nav-link nobtn-style" type="submit" role="button"
          aria-label="${sessionScope.languageJSON.accessHint.logout}"
          title="${sessionScope.languageJSON.accessHint.logout}">
          <i class="fa fa-sign-out "></i>
        </button>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
      </form>
    </li>
  </ul>
</nav>
<!-- /.navbar -->

<!-- Main Sidebar Container -->
<aside class="main-sidebar sidebar-dark-primary">
  <%@ include file="logo.jsp"%>
  <!-- Sidebar -->
  <div class="sidebar">
    <!-- Sidebar user panel (optional) -->

    <div class="user-panel">
      <div class="info">
        <a <c:if test="${sessionScope.enableSelfServiceDemographic||sessionScope.enableSelfServicePayroll}">
          href="/<%=request.getContextPath().split("/")[1]%>/profile/profile"
          </c:if>
          class="d-block">${sessionScope.userDetail.nameF} ${sessionScope.userDetail.nameL}
          ${sessionScope.userDetail.genDescription}</a>
      </div>
    </div>

    <!-- Sidebar Menu -->
    <nav class="mt-2">
      <ul class="nav nav-pills nav-sidebar flex-column" data-widget="treeview" role="menu" data-accordion="false">
        <!-- Add icons to the links using the .nav-icon class
               with font-awesome or any other icon font library -->
        <!-- menu-open -->
        <li class="nav-item has-treeview" id="inquiry">
          <a href="#" class="nav-link">
            <!--  active -->
            <i class="nav-icon fa fa-list"></i>
            <p class="empPortColor">
              <span>${sessionScope.languageJSON.nav.inquiry}</span>
              <i class="right fa fa-angle-right"></i>
            </p>
          </a>
          <ul class="nav nav-treeview">
            <c:if test="${sessionScope.enableCalendarYearToDate}">
              <li class="nav-item">
                <a href="/<%=request.getContextPath().split("/")[1]%>/calendarYearToDate/calendarYearToDate"
                  class="nav-link" id="calendarYearToDate">
                  <i class="fa fa-circle nav-icon"></i>
                  <p class="empPortColor">${sessionScope.languageJSON.nav.calendarYearToDate}</p>
                </a>
              </li>
            </c:if>
            <c:if test="${sessionScope.enableCurrentPayInformation}">
              <li class="nav-item">
                <a href="/<%=request.getContextPath().split("/")[1]%>/currentPayInformation/currentPayInformation"
                  class="nav-link" id="currentPayInformation">
                  <i class="fa fa-circle nav-icon"></i>
                  <p class="empPortColor">${sessionScope.languageJSON.nav.currentPayInfo}</p>
                </a>
              </li>
            </c:if>
            <c:if test="${sessionScope.enableDeductions}">
              <li class="nav-item">
                <a href="/<%=request.getContextPath().split("/")[1]%>/deductions/deductions" class="nav-link"
                  id="deductions">
                  <i class="fa fa-circle nav-icon"></i>
                  <p class="empPortColor">${sessionScope.languageJSON.nav.deductions}</p>
                </a>
              </li>
            </c:if>
            <c:if test="${sessionScope.enableEarnings}">
              <li class="nav-item">
                <a href="/<%=request.getContextPath().split("/")[1]%>/earnings/earnings" class="nav-link" id="earnings">
                  <i class="fa fa-circle nav-icon"></i>
                  <p class="empPortColor">${sessionScope.languageJSON.nav.earning}</p>
                </a>
              </li>
            </c:if>
            <c:if test="${sessionScope.enableW2}">
              <li class="nav-item">
                <a href="/<%=request.getContextPath().split("/")[1]%>/w2Information/w2Information" class="nav-link"
                  id="w2Information">
                  <i class="fa fa-circle nav-icon"></i>
                  <p class="empPortColor">${sessionScope.languageJSON.nav.w2Info}</p>
                </a>
              </li>
            </c:if>
            <c:if test="${sessionScope.enable1095}">
              <li class="nav-item">
                <a href="/<%=request.getContextPath().split("/")[1]%>/information1095/information1095" class="nav-link"
                  id="information1095">
                  <i class="fa fa-circle nav-icon"></i>
                  <p class="empPortColor">${sessionScope.languageJSON.nav.info1095}</p>
                </a>
              </li>
            </c:if>
          </ul>
        </li>
        <c:if test="${sessionScope.enableLeave}">
          <li class="nav-item">
            <a href="/<%=request.getContextPath().split("/")[1]%>/leaveBalance/leaveBalance" class="nav-link"
              id="leaveBalance">
              <i class="nav-icon fa fa-hourglass-start text-info"></i>
              <p class="empPortColor">${sessionScope.languageJSON.nav.leaveBalances}</p>
            </a>
          </li>
        </c:if>
        <c:if test="${sessionScope.enableLeaveReq}">
          <li class="nav-item">
            <a id="leaveRequest" href="/<%=request.getContextPath().split("/")[1]%>/leaveRequest/leaveRequest"
              class="nav-link">
              <i class="nav-icon fa  fa-pencil-square-o text-info"></i>
              <p class="empPortColor">${sessionScope.languageJSON.nav.leaveRequests}</p>
            </a>
          </li>
        </c:if>
        <c:if test="${sessionScope.enableTrvl}">
          <li class="nav-item has-treeview" id="travelRequests">
            <a href="#" class="nav-link">
              <!--  active -->
              <i class="nav-icon fa fa-pencil-square-o text-info"></i>
              <p class="empPortColor">
                <span>${sessionScope.languageJSON.nav.travelReimbursementRequests}</span>
                <i class="right fa fa-angle-right"></i>
              </p>
            </a>
            <ul class="nav nav-treeview">
              <li class="nav-item">
                <a id="travelRequest" href="/<%=request.getContextPath().split("/")[1]%>/travelRequest/travelRequest"
                  class="nav-link">
                  <i class="fa fa-circle nav-icon"></i>
                  <p class="empPortColor">${sessionScope.languageJSON.nav.travelRequests}</p>
                </a>
                </li>            
              <c:if test="${sessionScope.isTravelApprover}">
                <li class="nav-item">
                  <a href="/<%=request.getContextPath().split("/")[1]%>/approveTravelRequest/approveTravelRequestList"
                    class="nav-link" id="approveTravelRequest">
                    <i class="fa fa-circle nav-icon"></i>
                    <p class="empPortColor">${sessionScope.languageJSON.nav.approveTravelRequests}</p>
                  </a>
                </li>
              </c:if>
            </ul>
          </li>
        </c:if>
        <c:if test="${sessionScope.isSupervisor || sessionScope.isTempApprover}">
          <li class="nav-item has-treeview" id="supervisor">
            <a href="#" class="nav-link">
              <!--  active -->
              <i class="nav-icon fa fa-users"></i>
              <p class="empPortColor">
                <span>${sessionScope.languageJSON.nav.supervisor}</span>
                <i class="right fa fa-angle-right"></i>
              </p>
            </a>
            <ul class="nav nav-treeview">
              <li class="nav-item">
                <a href="/<%=request.getContextPath().split("/")[1]%>/approveLeaveRequest/approveLeaveRequestList"
                  class="nav-link" id="approveLeaveRequest">
                  <i class="fa fa-circle nav-icon"></i>
                  <p class="empPortColor">${sessionScope.languageJSON.nav.approveLeaveRequests}</p>
                </a>
              </li>
             
              <c:if test="${sessionScope.isSupervisor}">
                <li class="nav-item">
                  <a href="/<%=request.getContextPath().split("/")[1]%>/leaveOverview/leaveOverviewList"
                    class="nav-link" id="leaveOverview">
                    <i class="fa fa-circle nav-icon"></i>
                    <p class="empPortColor">${sessionScope.languageJSON.nav.leaveOverview}</p>
                  </a>
                </li>
                <li class="nav-item">
                  <a href="/<%=request.getContextPath().split("/")[1]%>/supervisorCalendar/calendarView"
                    class="nav-link" id="supervisorCalendar">
                    <i class="fa fa-circle nav-icon"></i>
                    <p class="empPortColor">${sessionScope.languageJSON.nav.calendar}</p>
                  </a>
                </li>
                <li class="nav-item">
                  <a href="/<%=request.getContextPath().split("/")[1]%>/leaveRequestTemporaryApprovers/leaveRequestTemporaryApprovers"
                    class="nav-link" id="leaveRequestTemporaryApprovers">
                    <i class="fa fa-circle nav-icon"></i>
                    <p class="empPortColor">${sessionScope.languageJSON.nav.setTemporary}</p>
                  </a>
                </li>
              </c:if>
            </ul>
          </li>
        </c:if>
      </ul>
    </nav>
    <!-- /.sidebar-menu -->

    <!-- /.sidebar -->
    
    <!-- version -->
    

<%-- <div class="sideNavChin flex-center">
	       				<span>
	       					<c:out value="Version: ${sessionScope.constantJSON.label.version} "/>
	       					&nbsp&nbsp&nbsp
						
								<c:out value="Build: ${sessionScope.constantJSON.label.versionLastNumber}"/>
						
	       				</span>
						<span>
	       					<c:out value="Host: ${hostAddress}"/>
	       					&nbsp&nbsp&nbsp
	       					<c:out value=" Browser: ${browserName} ${browserVersion}"/>
	       				</span>
					</div> --%>


    <div id="version" style="position:fixed; bottom:6vh;">
      <div style=" display:flex; flex-row; ">
        <div class="column" style="text-align: right; margin-left:48%; white-space: nowrap; font-size: 10px; color: white;  ">
          <c:out value="Version: ${sessionScope.constantJSON.label.version}" />
        </div>
        <div class="column" style="text-align: left;margin-left:11.2%;; white-space: nowrap;font-size: 10px; color: white; ">
          <c:out value="Build: ${sessionScope.constantJSON.label.versionLastNumber}" />
        </div>
      </div>
      <div style=" display:flex; flex-row; margin-top: 0px;  ">
        <div class="column" style="text-align: right; margin-left:25%;white-space: nowrap; font-size: 10px; color: white; ">
          <c:out value="Host: ${hostAddress}" />
        </div>
        <div class="column" style="text-align: left; margin-left:10%; white-space: nowrap; font-size: 10px; color: white; ">
          <c:out value="Browser: ${browserName} ${browserVersion}" />
        </div>
  
      </div>
    </div>
  </div>
  <!-- version -->

</aside>
<form id="sessionOutForm" action="/<%=request.getContextPath().split("/")[1]%>/logoutEA" method="post">
  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
</form>
<%@ include file="../modal/logoutModal.jsp"%>
<script src="/<%=request.getContextPath().split("/")[1]%>/js/viewJs/commons/bar.js">
</script>