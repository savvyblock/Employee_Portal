<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ page
language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="en">
    <head>
        <title data-localize="headTitle.leaveRequestCalendar"></title>
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <link rel="stylesheet" href="/<%=request.getContextPath().split("/")[1]%>/css/fullcalendar.min.css" />
        <%@ include file="../commons/header.jsp"%>
    </head>
    <body class="hold-transition sidebar-mini">
        <div class="wrapper">
            <%@ include file="../commons/bar.jsp"%>
			
            <main class="content-wrapper" tabindex="-1">
                <section class="content">
                    <h2 class="clearfix section-title">
                        <span data-localize="title.leaveRequest"></span>
                        <a class="btn btn-primary pull-right" href="/<%=request.getContextPath().split("/")[1]%>/leaveRequest/leaveRequest" data-localize="label.switchToTableView"></a>
                    </h2>
                    <div class="content-white"><div id="calendar"></div></div>
                </section>
            </main>
            <form hidden="true" id="deleteForm" action="deleteLeaveRequestFromCalendar" method="post">
                    <input type="text" id="deleteId" name="id"  title="" data-localize="accessHint.id"/>
            </form>
        </div>
        <%@ include file="../commons/footer.jsp"%>
        <%@ include file="../modal/eventModal.jsp"%>
        <%@ include file="../modal/eventModalStatic.jsp"%>
        <%@ include file="../modal/deleteModal.jsp"%>
    </body>
    <script>
        var leaveList = eval(${leaves});
        var leaveTypes = eval(${leaveTypes});
        var absRsns = eval(${absRsns});
    </script>
    <script src="/<%=request.getContextPath().split("/")[1]%>/js/viewJs/leaveRequest/fullCalendar.js"></script>

</html>
