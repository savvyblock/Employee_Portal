<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="en">
<head>
    <title data-localize="headTitle.notFound"></title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <%@ include file="commons/header.jsp"%>
</head>
<body class="hold-transition sidebar-mini">
<div class="wrapper">
    <%@ include file="commons/bar.jsp"%>
   
  
    <main  class="content-wrapper" tabindex="-1">
    	<section class="content">
    		<div class="container-fluid">
          <div class="noFoundPage">
              <img src="/<%=request.getContextPath().split("/")[1]%>/images/opps.png" alt="" data-localize="label.errorImg">
          </div>        
      	</div>
        </section>
    </main>
  </div>  
    <%@ include file="commons/footer.jsp"%>
        
</body>
</html>
