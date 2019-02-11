<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="error">
<%-- 
	<c:if test="${empty messages and isTrueSave}">
		<div style="color:green">Save successful</div>
	</c:if>
	--%>
	<form:errors path="${formPath}*" htmlEscape="true">
	</form:errors>
</div>
