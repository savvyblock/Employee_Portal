<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html> 
<html lang="en">
<head>
    <title>${sessionScope.languageJSON.headTitle.home}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="<spring:theme code="commonPortals"/>css/fullcalendar.min.css" />
    <%@ include file="commons/header.jsp"%>
</head>
<body class="hold-transition sidebar-mini">
<div class="wrapper">
    <%@ include file="commons/bar.jsp"%>
    <main  class="content-wrapper" tabindex="-1">
    	<section class="content" style="height: 1270px !important;overflow: unset !important;">
    		 <div class="content-white">
    		   <div id="calendar"></div>
    		 </div>
        </section>
    </main>
    <form id="deleteForm" action="deleteLeaveRequestFromCalendar" method="post">
         <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
         <input type="hidden" value="<%=token %>" name="token"/>
         <input type="hidden" id="deleteId" name="id" aria-label="${sessionScope.languageJSON.accessHint.id}"/>
    </form>
  </div>
    <%@ include file="commons/footer.jsp"%>
    <%@ include file="modal/changePassword.jsp"%>
    <%@ include file="modal/eventModal.jsp"%>
    <%@ include file="modal/eventModalStatic.jsp"%>
    <%@ include file="modal/travelRequestModal.jsp"%>
    <%@ include file="modal/wrkjlModal.jsp"%>
    
    <%@ include file="modal/homeModal.jsp"%>
    <%@ include file="modal/deleteModal.jsp"%> 
</body>
  <script>
        var passwordModalShow = '${changePSW}'
    </script>
    <script>
        var leaveList = eval(${leaves});
        var leaveTypes = eval(${leaveTypes});
        var absRsns = eval(${absRsns});
        var haveSupervisor = '${haveSupervisor}'
        var travelList = eval(${trvlReqCalParams});
        var wrkjlList = eval(${wrkjlList});
        console.log(leaveList)
        console.log(haveSupervisor)
    </script>
    <script src="/<%=request.getContextPath().split("/")[1]%>/js/viewJs/leaveRequest/fullCalendar.js"></script>
    <script src="/<%=request.getContextPath().split("/")[1]%>/js/viewJs/home.js"></script>
</html>