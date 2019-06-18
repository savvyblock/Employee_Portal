<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html> 
<html lang="en">
<head>
    <title>${sessionScope.languageJSON.headTitle.notFound}</title>
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
					<div>
						<h2>${sessionScope.languageJSON.label.exceptionOccurred}</h2>
						<div class="formModule">
							<p><span>${sessionScope.languageJSON.label.fromModule}</span>: <b>${module}</b></p>
							<p><span>${sessionScope.languageJSON.label.actionInvoked}</span>: <b>${action}</b></p>
							<p><span>${sessionScope.languageJSON.label.message}</span>: <b>${errorMsg}</b></p>
	
						</div>
					  <br/><br/>
					  <p class="btm-p">
								${sessionScope.languageJSON.label.ifYouHaveBookMarked}
					  </p>
					</div>
				  </div>       
      	</div>
        </section>
    </main>
  </div>  
    <%@ include file="commons/footer.jsp"%>
        
</body>
</html>
