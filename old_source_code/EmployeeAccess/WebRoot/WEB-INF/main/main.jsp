<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="ea" uri="http://www.esc20.net/tags/employeeaccess-util"%>

<html>
<head>
	<title>Home</title>
</head>

<body>
	<div style="padding-left:10px;">
	<tiles:insertAttribute name="error" />
	<c:if test="${enableEA != 1}">
		<span style="color:red; font-weight:bold;">Access to Employee Access Portal Denied. <br><br> Please contact your personnel office or select the Enable Employee Access System option in the Human Resources Application. </span>
	</c:if>
	<c:if test="${not empty message}">
		<span style="color:red"><ea:label value="${message}"/></span><br/>
		<br/>
	</c:if>
	</div>
</body>
</html>