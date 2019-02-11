<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="ea" uri="http://www.esc20.net/tags/employeeaccess-util"%>

<%@ attribute name="id" required="false" %>
<%@ attribute name="value" required="true" type="java.lang.Object"%>
<%@ attribute name="type" required="false" %>
<%@ attribute name="items" required="false" type="java.util.Collection" %>
<%@ attribute name="cssStyle" required="false" %>

<td style="${cssStyle}">
	<div id="${id}Cell">
	<c:choose>
		<c:when test="${type eq 'date' and not empty value}">
			<fmt:formatDate pattern="MM-dd-yyyy" value="${value}"/>
		</c:when>
		<c:when test="${type eq 'code'}">
			<c:if test="${not empty value}">
				<ea:label value="${value}"/> - <ea:codemapper code="${value}" collection="${items}"/>
			</c:if>
		</c:when>
		<c:when test="${type eq 'yesno'}">
			<c:choose>
				<c:when test="${value}">
					Y
				</c:when>
				<c:otherwise>
					N
				</c:otherwise>
			</c:choose>
		</c:when>
		<c:when test="${type eq 'percent'}">
			<c:if test="${not empty value}">
				<ea:label value="${value}%"/>
			</c:if>
		</c:when>
		<c:otherwise>
			<ea:label value="${value}"/>
		</c:otherwise>
	</c:choose>
	</div>
</td>