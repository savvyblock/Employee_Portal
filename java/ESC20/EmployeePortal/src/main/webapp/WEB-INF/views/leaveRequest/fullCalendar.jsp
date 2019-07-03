<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ page
language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html> 
<html lang="en">
    <head>
        <title>${sessionScope.languageJSON.headTitle.leaveRequestCalendar}</title>
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <link rel="stylesheet" href="/<%=request.getContextPath().split("/")[1]%>/css/fullcalendar.min.css" />
        <%@ include file="../commons/header.jsp"%>
    </head>
    <body class="hold-transition sidebar-mini">
        <div class="wrapper">
            <%@ include file="../commons/bar.jsp"%>
			
            <main class="content-wrapper" tabindex="-1">
                <section class="content">
                    <div class="clearfix section-title">
                        <h1 class="pageTitle">${sessionScope.languageJSON.title.leaveRequest}</h1>
                        <a class="btn btn-primary pull-right" href="/<%=request.getContextPath().split("/")[1]%>/leaveRequest/leaveRequest">
                        	${sessionScope.languageJSON.label.switchToTableView}
                        </a>
                    </div>
                    <c:if test="${!haveSupervisor}">
                        <p class="topMsg error-hint" role="alert">${sessionScope.languageJSON.label.noSupervisorFound}</p>
                    </c:if>
                    <div class="content-white"><div id="calendar"></div></div>
                </section>
            </main>
            <form id="deleteForm" action="deleteLeaveRequestFromCalendar" method="post">
            		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <input type="hidden" id="deleteId" name="id" aria-label="${sessionScope.languageJSON.accessHint.id}"/>
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
        var haveSupervisor = '${haveSupervisor}'
        console.log(leaveList)
        console.log(haveSupervisor)
    </script>
    <script src="/<%=request.getContextPath().split("/")[1]%>/js/viewJs/leaveRequest/fullCalendar.js"></script>

</html>
