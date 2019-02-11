<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ attribute name="path" required="true" %>
<%@ attribute name="id" required="true" %>
<%@ attribute name="disabled" required="false" type="java.lang.Boolean" %>

<table>
	<tr>
		<td>
			<c:set var="hasError" value="false"/>
			<form:errors path="${path}" cssStyle="display:none">
				<c:set var="hasError" value="true"/>
				<c:set var="errorStyle" value="background-color:#ff9999;"/>
			</form:errors>

			<spring:bind path="${path}">
				<input type="text" id="${id}TextBox" maxlength="8" style="width:80px;${errorStyle}" 
				class="text edit_mask" 
				value="${status.value}"
				name="${status.expression}"
				edit-mask="##:## ##"
				<c:if test="${disabled}">disabled="${disabled}"</c:if>/>
			</spring:bind>
		</td>
	<tr>
</table>