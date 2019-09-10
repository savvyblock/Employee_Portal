<button id="skipNav">${sessionScope.languageJSON.label.skipNav}</button>
<%@ page contentType="text/html; charset=UTF-8" import="java.util.ResourceBundle" %>
<%ResourceBundle res = ResourceBundle.getBundle("system"); %>  
<%
    String sessionTimeOut = res.getString("sessionTimeOut");
	long token=System.currentTimeMillis();
	session.setAttribute("token",token); 
%>
<script>
	var sessionTimeOut = "<%=sessionTimeOut%>";
</script>
<!-- Navbar -->
  <nav class="main-header navbar navbar-expand navbar-light clearfix">
    <!-- Left navbar links -->
    <ul class="navbar-nav">
      <li class="nav-item">
        <button class="nav-link nobtn-style bars-btn" data-widget="pushmenu" aria-label="${sessionScope.languageJSON.accessHint.collapseOrExpand}">
          <i class="fa fa-bars"></i>
        </button>
      </li>
    </ul>
    <%@ include file="logo.jsp"%>
    <!-- Right navbar links -->
    <ul class="navbar-nav nav-right">
      <!-- Notifications Dropdown Menu -->
      <li class="nav-item dropdown">
        <button class="nav-link nobtn-style" data-toggle="dropdown" aria-label="${sessionScope.languageJSON.accessHint.showOrHideNote}">
          <i class="fa fa-bell-o"></i>
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
          <a href="/<%=request.getContextPath().split("/")[1]%>/notifications/notifications" class="dropdown-footer"  role="link">${sessionScope.languageJSON.label.seeAllNote}</a>
        </div>
      </li>
      <li class="nav-item">
      <a class="nav-link" 
      <c:if test="${sessionScope.enableSelfServiceDemographic||sessionScope.enableSelfServicePayroll}">
          href="/<%=request.getContextPath().split("/")[1]%>/profile/profile" 
        </c:if>
        aria-label="${sessionScope.languageJSON.accessHint.goMyAccount}" role="link">
        <i class="fa fa-user"></i>
      </a>
      </li>
      <li class="nav-item flex-middle">
          <%@ include file="global-select.jsp"%>
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
        <div class="image">
          <a 
            <c:if test="${sessionScope.enableSelfServiceDemographic||sessionScope.enableSelfServicePayroll}">
              href="/<%=request.getContextPath().split("/")[1]%>/profile/profile" 
            </c:if>
            aria-label="${sessionScope.languageJSON.accessHint.goMyAccount}" class="d-block noImage">
            <i class="fa fa-user"></i>
          </a>
       		
        </div>
        <div class="info">
          <a 
          <c:if test="${sessionScope.enableSelfServiceDemographic||sessionScope.enableSelfServicePayroll}">
            href="/<%=request.getContextPath().split("/")[1]%>/profile/profile" 
          </c:if>
            class="d-block">${sessionScope.userDetail.nameF} ${sessionScope.userDetail.nameL} ${sessionScope.userDetail.genDescription}</a>
        </div>
      </div>

      <!-- Sidebar Menu -->
      <nav class="mt-2">
        <ul class="nav nav-pills nav-sidebar flex-column" data-widget="treeview" role="menu" data-accordion="false">
          <!-- Add icons to the links using the .nav-icon class
               with font-awesome or any other icon font library -->
          <!-- menu-open -->
          <li class="nav-item has-treeview" id="inquiry">
            <a href="#" class="nav-link"><!--  active -->
              <i class="nav-icon fa fa-list"></i>
              <p>
                <span>${sessionScope.languageJSON.nav.inquiry}</span>
                <i class="right fa fa-angle-left"></i>
              </p>
            </a>
            <ul class="nav nav-treeview">
              <c:if test="${sessionScope.enableCalendarYearToDate}">
                <li class="nav-item">
                  <a href="/<%=request.getContextPath().split("/")[1]%>/calendarYearToDate/calendarYearToDate" class="nav-link" id="calendarYearToDate">
                    <i class="fa fa-circle-o nav-icon"></i>
                    <p>${sessionScope.languageJSON.nav.calendarYearToDate}</p>
                  </a>
                </li>
              </c:if>
              <c:if test="${sessionScope.enableCurrentPayInformation}">
              <li class="nav-item" >
                <a href="/<%=request.getContextPath().split("/")[1]%>/currentPayInformation/currentPayInformation" class="nav-link" id="currentPayInformation">
                  <i class="fa fa-circle-o nav-icon"></i>
                  <p>${sessionScope.languageJSON.nav.currentPayInfo}</p>
                </a>
              </li>
            </c:if>
              <c:if test="${sessionScope.enableDeductions}">
              <li class="nav-item">
                <a href="/<%=request.getContextPath().split("/")[1]%>/deductions/deductions" class="nav-link" id="deductions">
                  <i class="fa fa-circle-o nav-icon"></i>
                  <p>${sessionScope.languageJSON.nav.deductions}</p>
                </a>
              </li>
            </c:if>
              <c:if test="${sessionScope.enableEarnings}">
              <li class="nav-item">
                <a href="/<%=request.getContextPath().split("/")[1]%>/earnings/earnings" class="nav-link" id="earnings">
                  <i class="fa fa-circle-o nav-icon"></i>
                  <p>${sessionScope.languageJSON.nav.earning}</p>
                </a>
              </li>
            </c:if>
              <c:if test="${sessionScope.enableW2}">
              <li class="nav-item">
                <a href="/<%=request.getContextPath().split("/")[1]%>/w2Information/w2Information" class="nav-link" id="w2Information">
                  <i class="fa fa-circle-o nav-icon"></i>
                  <p>${sessionScope.languageJSON.nav.w2Info}</p>
                </a>
              </li>
            </c:if>
              <c:if test="${sessionScope.enable1095}">
              <li class="nav-item">
                <a href="/<%=request.getContextPath().split("/")[1]%>/information1095/information1095" class="nav-link" id="information1095">
                  <i class="fa fa-circle-o nav-icon"></i>
                  <p>${sessionScope.languageJSON.nav.info1095}</p>
                </a>
              </li>
            </c:if>
            </ul>
          </li>
          <li class="nav-item">
            <a href="/<%=request.getContextPath().split("/")[1]%>/leaveBalance/leaveBalance" class="nav-link" id="leaveBalance">
              <i class="nav-icon fa fa-hourglass-start text-info"></i>
              <p>${sessionScope.languageJSON.nav.leaveBalances}</p>
            </a>
          </li>
          <c:if test="${sessionScope.enableLeaveReq}">
          <li class="nav-item">
            <a id="leaveRequest" href="/<%=request.getContextPath().split("/")[1]%>/leaveRequest/leaveRequest" class="nav-link">
              <i class="nav-icon fa  fa-pencil-square-o text-info"></i>
              <p>${sessionScope.languageJSON.nav.leaveRequests}</p>
            </a>
          </li>
        </c:if>
        <c:if test="${sessionScope.isSupervisor || sessionScope.isTempApprover}">
          <li class="nav-item has-treeview" id="supervisor">
            <a href="#" class="nav-link" > <!--  active -->
              <i class="nav-icon fa fa-users"></i>
              <p>
                <span>${sessionScope.languageJSON.nav.supervisor}</span>
                <i class="right fa fa-angle-left"></i>
              </p>
            </a>
            <ul class="nav nav-treeview">
              <li class="nav-item">
                <a href="/<%=request.getContextPath().split("/")[1]%>/approveLeaveRequest/approveLeaveRequestList" class="nav-link" id="approveLeaveRequest">
                  <i class="fa fa-circle-o nav-icon"></i>
                  <p>${sessionScope.languageJSON.nav.approveLeaveRequests}</p>
                </a>
              </li>
              <c:if test="${sessionScope.isSupervisor}">
	              <li class="nav-item">
	                <a  href="/<%=request.getContextPath().split("/")[1]%>/leaveOverview/leaveOverviewList" class="nav-link" id="leaveOverview">
	                  <i class="fa fa-circle-o nav-icon"></i>
	                  <p>${sessionScope.languageJSON.nav.leaveOverview}</p>
	                </a>
	              </li>
	              <li class="nav-item">
	                <a href="/<%=request.getContextPath().split("/")[1]%>/supervisorCalendar/calendarView" class="nav-link" id="supervisorCalendar">
	                  <i class="fa fa-circle-o nav-icon"></i>
	                  <p>${sessionScope.languageJSON.nav.calendar}</p>
	                </a>
	              </li>
	              <li class="nav-item">
	                <a href="/<%=request.getContextPath().split("/")[1]%>/leaveRequestTemporaryApprovers/leaveRequestTemporaryApprovers" class="nav-link" id="leaveRequestTemporaryApprovers">
	                  <i class="fa fa-circle-o nav-icon"></i>
	                  <p>${sessionScope.languageJSON.nav.setTemporary}</p>
	                </a>
	              </li>
              </c:if>
            </ul>
          </li>
        </c:if>
        </ul>
      </nav>
      <!-- /.sidebar-menu -->
    </div>
    <!-- /.sidebar -->
    <ul class="sidebar-btm">
        <c:if test="${sessionScope.enableSelfServiceDemographic||sessionScope.enableSelfServicePayroll}">
        <li>
          <a href="/<%=request.getContextPath().split("/")[1]%>/profile/profile"
            aria-label="${sessionScope.languageJSON.accessHint.goMyAccount}" title="${sessionScope.languageJSON.accessHint.goMyAccount}">
          <i class="fa fa-user"></i>
        </a></li>
        </c:if>
        <!-- <li><a href="/" title="Full Screen"><i class="fa fa-arrows-alt"></i></a></li> -->
        <li>
          <a class="helpLink" href="https://tcc-help.net/txeis/employeeaccess/doku.php" target="_blank"
          aria-label="${sessionScope.languageJSON.accessHint.goHelp}" title="${sessionScope.languageJSON.accessHint.goHelp}">
          <i class="fa fa-question-circle"></i>
        </a></li>
        <li>
        <form action="/<%=request.getContextPath().split("/")[1]%>/logoutEA" method="post">
          <button class="logoutBtn" type="submit" role="button" aria-label="${sessionScope.languageJSON.accessHint.logout}" title="${sessionScope.languageJSON.accessHint.logout}">
            <i class="fa fa-sign-out "></i>
          </button>
          <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
         </form>
        </li>
    </ul>
  </aside>
  <form id="sessionOutForm" action="/<%=request.getContextPath().split("/")[1]%>/logoutEA" method="post">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
  </form>
   <%@ include file="../modal/logoutModal.jsp"%>
   <script src="/<%=request.getContextPath().split("/")[1]%>/js/viewJs/commons/bar.js"></script>
