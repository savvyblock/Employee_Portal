<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
	<c:set var="commonBase">/CommonWeb/</c:set>
    <c:set var="mainMenu">/MainMenu/</c:set>
    
    <title>TxEIS : Exit Employee Access</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<c:import url="css-includes.jsp" />
	<c:import url="js-includes-top.jsp" />
</head>

<body>
	<div id="mainDecoratorHeader">
		<div id="mainDecoratorLogo"/>
	</div>
	<div id="header">	
	  	<table>
	  		<tr>
	  			<td id="logo" rowspan="2">&nbsp;</td>
	  			<td id="title"><h1>Employee Access</h1></td>
	  		</tr>
	  		<tr>
	  			<td id="navigation">
	  				&nbsp;
	  			</td>
	  		</tr>
	  	</table>
  	</div>

	<div id="content">
  		<h1>Exit Application</h1>
		<p>You have exited the Employee Access Application.</p>
		<p><a class="jump_target" jump-target="mainmenu" href="<c:out value='${mainMenu}'/>">Back to Main Menu</a></p>
  	</div>
  	
  	<c:import url="js-includes-bottom.jsp" />
</body>
</html>

