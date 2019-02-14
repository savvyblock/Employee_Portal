<!-- Navbar -->
  <nav class="main-header navbar navbar-expand navbar-light clearfix">
  	
    <!-- Left navbar links -->
    <ul class="navbar-nav">
      <li class="nav-item">
        <a class="nav-link" data-widget="pushmenu" href="#"><i class="fa fa-bars"></i></a>
      </li>
    </ul>

    
    <%@ include file="logo.jsp"%>
    
    <!-- Right navbar links -->
    <ul class="navbar-nav nav-right">
      <!-- Notifications Dropdown Menu -->
      <li class="nav-item dropdown">
        <a class="nav-link" data-toggle="dropdown" tabindex="0">
          <i class="fa fa-bell-o"></i>
          <span id="navBadge" class="navbar-badge"></span>
        </a>
        <div class="dropdown-menu dropdown-menu-right note-dropdown">
          <div class="dropdown-item dropdown-header"><div id="budgeCount">0</div><span data-localize="label.notification"></span></div>
          <div class="dropdown-divider"></div>
          <a href="#" class="dropdown-item">
            <div id="top5Alert"></div>
          </a>
          <div class="dropdown-divider"></div>          
          <a data-localize="label.seeAllNote" href="/<%=request.getContextPath().split("/")[1]%>/notifications" class="dropdown-item dropdown-footer">See All Notifications</a>
        </div>
      </li>
      <li class="nav-item">
      <a class="nav-link" href="/<%=request.getContextPath().split("/")[1]%>/profile" title="" data-localize="nav.myAccount" data-localize-location="title"><i class="fa fa-user"></i></a>
      </li>
      <li class="nav-item flex-middle">
          <%@ include file="global-select.jsp"%>
      </li>
    </ul>
  </nav>
  <!-- /.navbar -->
  
  <!-- Main Sidebar Container -->
  <aside class="main-sidebar sidebar-dark-primary">
  
    <!-- Sidebar -->
    <div class="sidebar">
      <!-- Sidebar user panel (optional) -->
      <%@ include file="logo.jsp"%>
      <div class="user-panel mt-3 pb-3 mb-3 d-flex">
        <div class="image">
            <c:if test="${sessionScope.userDetail.avatar!=null}" >
                <a href="/<%=request.getContextPath().split("/")[1]%>/profile" class="d-block"  style="background-image:url(${sessionScope.userDetail.avatar})">
              </a>
            </c:if>
            <c:if test="${sessionScope.userDetail.avatar==null}">
                <a href="/<%=request.getContextPath().split("/")[1]%>/profile" class="d-block noImage">
                  <i class="fa fa-user"></i>
                </a>
            </c:if>
       		
        </div>
        <div class="info">
          <a href="/<%=request.getContextPath().split("/")[1]%>/profile" class="d-block">${sessionScope.userDetail.nameF} ${sessionScope.userDetail.nameL}</a>
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
                <span data-localize="nav.inquiry"></span>
                <i class="right fa fa-angle-left"></i>
              </p>
            </a>
            <ul class="nav nav-treeview">
              <li class="nav-item">
                <a href="/<%=request.getContextPath().split("/")[1]%>/inquiry/calendarYearToDate" class="nav-link" id="calendarYearToDate">
                  <i class="fa fa-circle-o nav-icon"></i>
                  <p data-localize="nav.calendarYearToDate"></p>
                </a>
              </li>
              <li class="nav-item" >
                <a href="/<%=request.getContextPath().split("/")[1]%>/inquiry/currentPayInformation" class="nav-link" id="currentPayInformation">
                  <i class="fa fa-circle-o nav-icon"></i>
                  <p data-localize="nav.currentPayInfo"></p>
                </a>
              </li>
              <li class="nav-item">
                <a href="/<%=request.getContextPath().split("/")[1]%>/inquiry/deductions" class="nav-link" id="deductions">
                  <i class="fa fa-circle-o nav-icon"></i>
                  <p data-localize="nav.deductions"></p>
                </a>
              </li>
              <li class="nav-item">
                <a href="/<%=request.getContextPath().split("/")[1]%>/inquiry/earnings" class="nav-link" id="earnings">
                  <i class="fa fa-circle-o nav-icon"></i>
                  <p data-localize="nav.earning"></p>
                </a>
              </li>
              <li class="nav-item">
                <a href="/<%=request.getContextPath().split("/")[1]%>/inquiry/w2Information" class="nav-link" id="w2Information">
                  <i class="fa fa-circle-o nav-icon"></i>
                  <p data-localize="nav.w2Info"></p>
                </a>
              </li>
              <li class="nav-item">
                <a href="/<%=request.getContextPath().split("/")[1]%>/inquiry/information1095" class="nav-link" id="information1095">
                  <i class="fa fa-circle-o nav-icon"></i>
                  <p data-localize="nav.info1095"></p>
                </a>
              </li>
            </ul>
          </li>
          <!-- <li class="nav-item has-treeview" id="selfService">
            <a href="#" class="nav-link">
              <i class="nav-icon fa fa-address-book-o"></i>
              <p>
                <span data-localize="nav.selfService"></span>
                <i class="right fa fa-angle-left"></i>
              </p>
            </a>
            <ul class="nav nav-treeview">
              <li class="nav-item">
                <a href="/<%=request.getContextPath().split("/")[1]%>/selfService/changePassword" class="nav-link" id="changePassword">
                  <i class="fa fa-circle-o nav-icon"></i>
                  <p data-localize="nav.changePassword"></p>
                </a>
              </li>
            </ul>
          </li> -->
          <li class="nav-item">
            <a href="/<%=request.getContextPath().split("/")[1]%>/leaveRequest/leaveBalance" class="nav-link" id="leaveBalance">
              <i class="nav-icon fa fa-hourglass-start text-info"></i>
              <p data-localize="nav.leaveBalances"></p>
            </a>
          </li>
          <li class="nav-item">
            <a id="leaveRequest" href="/<%=request.getContextPath().split("/")[1]%>/leaveRequest/leaveRequest" class="nav-link">
              <i class="nav-icon fa  fa-pencil-square-o text-info"></i>
              <p data-localize="nav.leaveRequests"></p>
            </a>
          </li>
          <!-- <li class="nav-item">
            <a href="/<%=request.getContextPath().split("/")[1]%>/leaveRequest/eventCalendar" class="nav-link">
              <i class="nav-icon fa  fa-pencil-square-o text-info"></i>
              <p>Calendar</p>
            </a>
          </li>  -->
          <li class="nav-item has-treeview" id="supervisor">
            <a href="#" class="nav-link" > <!--  active -->
              <i class="nav-icon fa fa-users"></i>
              <p>
                <span data-localize="nav.supervisor"></span>
                <i class="right fa fa-angle-left"></i>
              </p>
            </a>
            <ul class="nav nav-treeview">
              <li class="nav-item">
                <a href="/<%=request.getContextPath().split("/")[1]%>/supervisor/approveLeaveRequestList" class="nav-link" id="approveLeaveRequestList">
                  <i class="fa fa-circle-o nav-icon"></i>
                  <p data-localize="nav.approveLeaveRequests"></p>
                </a>
              </li>
              <li class="nav-item">
                <a  href="/<%=request.getContextPath().split("/")[1]%>/supervisor/leaveOverviewList" class="nav-link" id="leaveOverviewList">
                  <i class="fa fa-circle-o nav-icon"></i>
                  <p data-localize="nav.leaveOverview"></p>
                </a>
              </li>
              <li class="nav-item">
                <a href="/<%=request.getContextPath().split("/")[1]%>/supervisor/calendarView" class="nav-link" id="calendarView">
                  <i class="fa fa-circle-o nav-icon"></i>
                  <p data-localize="nav.calendar"></p>
                </a>
              </li>
              <li class="nav-item">
                <a href="/<%=request.getContextPath().split("/")[1]%>/supervisor/leaveRequestTemporaryApprovers" class="nav-link" id="leaveRequestTemporaryApprovers">
                  <i class="fa fa-circle-o nav-icon"></i>
                  <p data-localize="nav.setTemporary"></p>
                </a>
              </li>
              
            </ul>
          </li>
        </ul>
      </nav>
      <!-- /.sidebar-menu -->
    </div>
    <!-- /.sidebar -->
    <ul class="sidebar-btm">
        <li><a href="/<%=request.getContextPath().split("/")[1]%>/profile" title="" data-localize="nav.myAccount" data-localize-location="title"><i class="fa fa-user"></i></a></li>
        <!-- <li><a href="/" title="Full Screen"><i class="fa fa-arrows-alt"></i></a></li> -->
        <li><a href="https://tcc-help.net/txeis/employeeaccess/doku.php/leave/supervisor/settemporaryapprovers?version=3.3" 
          title=""  target="_blank"
          data-localize="nav.help" data-localize-location="title"><i class="fa fa-question-circle"></i></a></li>
        <li><a href="/<%=request.getContextPath().split("/")[1]%>/logout" title="" data-localize="nav.logout" data-localize-location="title"><i class="fa fa-sign-out "></i></a></li>
    </ul>
    <script>
    $(document).ready(function() {
        var path = (window.location+"").split("/");
        var menuItem = path[path.length-2];
        var item = path[path.length -1];
        console.log(path)
        console.log(menuItem)
        console.log(item)
        if(item=='eventCalendar'){
          item = 'leaveRequest'
        }
        var menuElement = $("#"+menuItem);
        var itemElement = $("#"+item);
        console.log(itemElement)
        if(menuElement){
            menuElement.addClass("menu-open");
            menuElement.children("ul").attr("style","display: block;");
        }
        if(itemElement)
        	itemElement.addClass("active");
        //update budgeCount and info every second
        getBudgeDetail();
    	updateBudgeCountAndInfo();
    });
    </script>
  </aside>

   <%@ include file="../modal/logoutModal.jsp"%>

   <script>
   var maxTime = 300; // seconds
   var time = maxTime;
   var budgeCount = 0;
    $('body').on('keydown mousemove mousedown', function(e){
    time = maxTime; // reset
  });
  startCountTime();
  function startCountTime(){
    var intervalId = setInterval(function(){
      time--;
      if(time <= 10) {
        $("#logoutModal").modal("show")
        $("#timeCounter").text(time)
        // clearInterval(intervalId);
        if(time == 0){
          window.location="/<%=request.getContextPath().split("/")[1]%>/logout"
        }
      }
    }, 1000)
  }
	function updateBudgeCountAndInfo(){
		console.log("here");
		var budgeCount = setInterval(function(){
			getBudgeDetail();
		},3000);
	}
	function getBudgeDetail(){
		$.ajax({
			type: "post",
			url: "/txeisDemo/getBudgeCount",
			cache: false,
			dataType: "json",
			success: function(data){
				if(budgeCount == data.count){
					return;
				} else {
					console.log("data", data);
					budgeCount = data.count;
					$("#budgeCount").html(budgeCount);
					$("#navBadge").html(budgeCount);
					$.ajax({
						type: "post",
						url: "/txeisDemo/getTop5Alerts",
						cache: false,
						dataType: "json",
						success: function(data){
							console.log("list",data);
							var list = "<ul>";
							var items = data.list;
							for(var s in items){
								list+= "<li>"+items[s].msgContent+"</li>"
							}
							list+="</ul>";
							$("#top5Alert").html(list);
						}
					});
				}
			}
			});
	}
  </script>