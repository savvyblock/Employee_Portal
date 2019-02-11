<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ attribute name="path" required="true" %>
<%@ attribute name="mask" required="true" %>
<%@ attribute name="size" required="false" type="java.lang.Integer"%>
<%@ attribute name="disabled" required="false" type="java.lang.Boolean" %>
<%@ attribute name="tabindex" required="false" %>
<%@ attribute name="cssStyle" required="false" %>

<c:set var="showError" value=""/>
<form:errors path="${path}" cssStyle="display:none">
	<c:set var="showError" value="errorInput"/>
</form:errors>
<spring:bind path="${path}">
	<input type="text" value="${status.value}" name="${status.expression}" size="${size}" maxlength="${size}" cssStyle="${cssStyle}" 
	class="text edit_mask ${showError}" edit-mask="${mask}" htmlEscape="true" <c:if test="${disabled}">disabled="${disabled}"</c:if> tabindex="${tabindex}" />
</spring:bind>