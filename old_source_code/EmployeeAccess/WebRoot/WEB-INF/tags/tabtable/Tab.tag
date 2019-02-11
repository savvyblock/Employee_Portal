<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="employeeaccess" uri="http://www.esc20.net/tags/employeeaccess-util"%>

<%@ attribute name="id" required="true" %>
<%@ attribute name="title" required="true" %>
<%@ attribute name="href" required="true" %>
<%@ attribute name="first" required="false" type="java.lang.Boolean" %>
<%@ attribute name="disabled" required="false" type="java.lang.Boolean" %>
<%@ attribute name="onClick" required="false" type="java.lang.String" %>

<c:if test="${empty first}">
	<c:set var="first" value="false"/>
</c:if>
<c:if test="${empty disabled}">
	<c:set var="disabled" value="false"/>
</c:if>
<c:if test="${empty onClick}">
	<c:set var="onClick" value="false"/>
</c:if>

<c:choose>
	<c:when test="${selectedTab == id}">
		<employeeaccess:tab id="${id}Tab" href="#" name="${title}" active="true" leftMost="${first}" hoverGroup="tab0" tabGroup="maintabs"
			disabled="${disabled}" onClick="${onClick}" />
	</c:when>
	<c:otherwise>
		<employeeaccess:tab id="${id}Tab" href="${href}" name="${title}" active="false" leftMost="${first}" hoverGroup="tab2" tabGroup="maintabs" 
			disabled="${disabled}" onClick="${onClick}" />
		<script type="text/javascript">
			Spring.addDecoration(new Spring.AjaxEventDecoration( {
				elementId :"${id}Tab",
				event :"onclick",
				params : {
					fragments :"body"
				}
			}));
		</script>
	</c:otherwise>
</c:choose>