<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> <%@ taglib
uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> <%@ page
language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="en">
    <head>
        <title data-localize="headTitle.notifications"></title>
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <%@ include file="commons/header.jsp"%>
    </head>
    <body class="hold-transition sidebar-mini">
        <div class="wrapper">
            <%@ include file="commons/bar.jsp"%>

            <main class="content-wrapper" id="content">
                <section class="content">
                    <h2 class="clearfix no-print section-title" data-localize="label.notification">
                    </h2>
                    <div class="content-white EMP-detail">
                       <ul class="note-list">
                       		<c:forEach var="notification" items="${unReadList}" varStatus="unReadList">
	                            <li class="note-item" <c:if test="${notification.status != 'UR' }">active</c:if>>
	                               <i class="fa fa-envelope left-fa"></i> 
                                   <div class="msg">${notification.msgContent}</div>
                                   <c:if test="${notification.status == 'UR' }">
                                    <button class="note-btn" onclick="markRead('${notification.id}')" data-localize="label.markAsRead"
                                    data-localize-location="title">
                                            <i class="fa fa-eye"></i>
                                    </button>
                                    </c:if>
	                            </li>
                            </c:forEach>
                       </ul>
                    </div>
                </section>
            </main>
            <form id="markAsReadForm" hidden="hidden" action="markAsRead" method="POST">
                <input type="text" id="noteId" name="id">
            </form>
        </div>
        <%@ include file="commons/footer.jsp"%>
    </body>
    <script>
        function markRead(id){
            $("#noteId").val(id)
            $("#markAsReadForm")[0].submit()
        }
    </script>
</html>
