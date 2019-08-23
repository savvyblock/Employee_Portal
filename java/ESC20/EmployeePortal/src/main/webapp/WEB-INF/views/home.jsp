<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html> 
<html lang="en">
<head>
    <title>${sessionScope.languageJSON.headTitle.home}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <%@ include file="commons/header.jsp"%>
</head>
<body class="hold-transition sidebar-mini">
<div class="wrapper">
    <%@ include file="commons/bar.jsp"%>
   
  
    <main  class="content-wrapper" tabindex="-1">
    	<section class="content">
    		<div class="container-fluid homeLanding">
    		  <c:if test="${not empty sessionScope.options.messageEmployeeAccessSystem}">
    		       <span>${sessionScope.options.messageEmployeeAccessSystem}</span>
              </c:if>
              <c:if test="${empty sessionScope.options.messageEmployeeAccessSystem}">
    		       <span>${sessionScope.constantJSON.label.welcomeToNewEmployeePortal}</span>
              </c:if> 
        	</div>
        </section>
    </main>
  </div>  
    <%@ include file="commons/footer.jsp"%>
        
</body>
</html>
