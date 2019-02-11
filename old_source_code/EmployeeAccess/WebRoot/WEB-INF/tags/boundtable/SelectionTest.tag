<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ attribute name="var" required="true" %>
<%@ attribute name="value" required="true" %>
<%@ attribute name="selected" required="true" %>
<%@ attribute name="type" required="false" %>

<c:choose>
	<c:when test="${type eq 'date'}">
		<fmt:formatDate var="value" pattern="MM-dd-yyyy" value="${value}"/>
	</c:when>
	<c:otherwise>
	</c:otherwise>
</c:choose>

<c:if test="${value != selected}">
	<c:set scope="request" var="rowClass" value="unselected" />
</c:if>

<script type="text/javascript">
	var selectActionObject = new Object();
	selectActionObject.key = "<c:out value='${var}'/>";
	selectActionObject.value = "<c:out value='${value}'/>";
	
	selectAction.push(selectActionObject);
</script>