<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ attribute name="id" required="false" %>
<%@ attribute name="label" required="true" %>
<%@ attribute name="onClick" required="false" %>
<%@ attribute name="isAjax" required="false" type="java.lang.Boolean" %>
<%@ attribute name="event" required="false" %>
<%@ attribute name="parameters" required="false" %>
<%@ attribute name="cssStyle" required="false" %>
<%@ attribute name="cssClass" required="false" %>
<%@ attribute name="tabindex" required="false" %>

<c:if test="${empty tabindex}">
	<c:set var="tabindex" value="-1"/>
</c:if>

<c:if test="${not empty parameters}">
	<c:choose>
		<c:when test="${isAjax}">
			<c:set var="parameters" value=", ${parameters}"/>
		</c:when>
		<c:otherwise>
			<c:set var="parameters" value="&${parameters}"/>
		</c:otherwise>
	</c:choose>
</c:if>

<c:set var="onClickEvent" value="${onClick}"/>
<c:set var="href" value="${flowExecutionUrl}&_eventId=${event}${parameters}"/>

<c:if test="${isAjax}">
	<c:set var="onClickEvent" value="Spring.remoting.submitForm('${id}', $('#'+'${id}').parents('form').attr('id'), { _eventId: '${event}' ${parameters}}); ${onClick}; return false;"/>
	<c:set var="href" value="#"/>
</c:if>

<a id="${id}" href="${href}" onclick="${onClickEvent}" style="${cssStyle}" class="${cssClass}" tabindex="${tabindex}">
<c:out value="${label}"/></a>