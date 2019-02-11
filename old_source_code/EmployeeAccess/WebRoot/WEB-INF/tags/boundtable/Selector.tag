<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ attribute name="var" required="true" %>
<%@ attribute name="selected" required="true" %>
<%@ attribute name="type" required="false" %>

<c:choose>
	<c:when test="${type eq 'date'}">
		<c:if test="${selected != null}">
			<fmt:formatDate var="selected" pattern="MM-dd-yyyy" value="${selected}"/>
		</c:if>
	</c:when>
	<c:otherwise>	

	</c:otherwise>
</c:choose>

<input type="hidden" name="${var}Search" id="${var}Search" value="<c:out value='${selected}'/>"/>
