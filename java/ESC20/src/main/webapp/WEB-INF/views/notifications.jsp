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
                            <li class="note-item">
                               <i class="fa fa-envelope left-fa"></i> 
                               <div class="msg">a new message!</div>
                               <div class="note-btn">
                                    1/28/2019 1:46 PM
                               </div>
                            </li>
                            <li class="note-item">
                                <i class="fa fa-envelope left-fa"></i> 
                                <div class="msg">a new message!</div>
                                <div class="note-btn">
                                    1/28/2019 1:46 PM
                                </div>
                             </li>
                             <li class="note-item">
                                <i class="fa fa-envelope left-fa"></i> 
                                <div class="msg">a new message!</div>
                                <div class="note-btn">
                                    <button class="a-btn"><i class="fa fa-trash"></i></button>
                                </div>
                             </li>
                       </ul>
                    </div>
                </section>
            </main>
        </div>
        <%@ include file="commons/footer.jsp"%>
    </body>
    <script>
    </script>
</html>
