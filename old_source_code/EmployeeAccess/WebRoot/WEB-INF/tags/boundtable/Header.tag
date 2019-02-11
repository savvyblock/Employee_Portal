<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ attribute name="title" required="true" %>
<%@ attribute name="sort" required="false" type="java.lang.Boolean" %>
<%@ attribute name="var" required="false" %>
<%@ attribute name="ignoreHtmlEscape" required="false" type="java.lang.Boolean"%>
<%@ attribute name="cssStyle" required="false" %>

<th style="vertical-align:bottom; ${cssStyle}">
	<div id="${var}Header">
	<c:if test="${sort}">
		<a href="#" id="${var}Link" style="color: white" onclick="sort('${var}','${tableid}')">
	</c:if>
	<c:choose>
		<c:when test="${ignoreHtmlEscape}">
			${title}
		</c:when>
		<c:otherwise>
			<c:out value="${title}"/>
		</c:otherwise>
	</c:choose>
	<c:if test="${sort}">
		</a>
	</c:if>
	</div>
</th>