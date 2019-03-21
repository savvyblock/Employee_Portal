<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ page
language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="en">
    <head>
        <title data-localize="headTitle.calendar"></title>
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <link rel="stylesheet" href="/<%=request.getContextPath().split("/")[1]%>/css/fullcalendar.min.css" />
        <%@ include file="../commons/header.jsp"%>
    </head>
    <body class="hold-transition sidebar-mini">
        <div class="wrapper">
            <%@ include file="../commons/bar.jsp"%>
			
            <main class="content-wrapper" tabindex="-1">
                    <section class="content">
                            <h1 class="clearfix section-title">
                                <span data-localize="title.calendar"></span>
                            </h1>
                            <div class="container-fluid">
                                
                                <div class="content-white">
                                        <div class="container-fluid"><div id="calendar"></div></div>
                                    
                                </div>
                            </div>
                        </section>
            </main>
        </div>
        <%@ include file="../modal/eventModal.jsp"%>
        <%@ include file="../modal/eventModalStatic.jsp"%>
        <%@ include file="../commons/footer.jsp"%>
    </body>
    <script>
    var leaveList = eval(${leavesCalendar});
    var leaveTypes = eval(${leaveTypes});
    var absRsns = eval(${absRsns});
    </script>
    <script src="/<%=request.getContextPath().split("/")[1]%>/js/viewJs/supervisor/calendar.js"></script>

</html>
