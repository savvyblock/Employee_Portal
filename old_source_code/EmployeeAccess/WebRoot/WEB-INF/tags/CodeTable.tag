<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="employeeaccess" uri="http://www.esc20.net/tags/employeeaccess-util"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ attribute name="viewObject" required="true" type="net.esc20.txeis.EmployeeAccess.web.viewobjects.CommonViewObject"%>
<%@ attribute name="codeType" required="false" %>
<%@ attribute name="maxLengthCode" required="false" type="java.lang.Integer" %>
<%@ attribute name="maxLengthDescription" required="false" type="java.lang.Integer" %>

<c:if test="${empty codeType}">
	<c:set var="codeType" value="Code"/>
</c:if>

<c:if test="${empty maxLength}">
	<c:set var="maxLength" value="1"/>
</c:if>

<employeeaccess:Table height="530px;" width="400px;" 
		showAdd="true"
		checkUnsaved="false" tabindex="1">
	<tr>
		<th style="width: 50px;">Delete</th>
		<th><c:out value="${codeType}"/></th>
		<th>Description</th>
	</tr>
	
	<employeeaccess:EmptyRow items="${viewObject.codes}"/>
	
	<c:set var="itemCount" value="-1" scope="request"/>
	<c:forEach var="item" items="${viewObject.codes}" varStatus="counter">
		<c:set var="itemCount" value="${counter.index}" scope="request"/>

		<c:choose>
			<c:when test="${hasAdded and counter.index eq fn:length(viewObject.codes) - 1}">
				<c:set var="initialFocus" value="initial_focus"/>
			</c:when>
			<c:otherwise>
				<c:set var="initialFocus" value=""/>
			</c:otherwise>
		</c:choose>

		<employeeaccess:Row id="${counter.index}" cssClass="${rowClass}" disabled="${disabled}"
		showMultiDeleteIcon="true" path="selected[${counter.index}].selected" isMarkedForDeletion="${viewObject.selected[counter.index].selected}">
			<td>
				<form:input path="codes[${counter.index}].code" cssStyle="width:50px" maxlength="${maxLengthCode}" 
				cssClass="text wrap_field ${initialFocus}" cssErrorClass="text errorInput" htmlEscape="true" disabled="${notSelected}" tabindex="1"/>
			</td>
			<td>
				<form:input path="codes[${counter.index}].description" cssStyle="width:200px" maxlength="${maxLengthDescription}" 
				cssClass="text" cssErrorClass="text errorInput" htmlEscape="true" disabled="${notSelected}" tabindex="1"/>
			</td>			
		</employeeaccess:Row>
	</c:forEach>
</employeeaccess:Table>

