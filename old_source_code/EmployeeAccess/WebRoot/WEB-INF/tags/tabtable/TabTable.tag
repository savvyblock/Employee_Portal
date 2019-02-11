<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="employeeaccess" uri="http://www.esc20.net/tags/employeeaccess-util"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<%@ attribute name="defaultTab" required="false" %>
<%@ attribute name="height" required="false" %>

<div id="body">	
<c:if test="${empty selectedTab}">
	<c:set var="selectedTab" value="${defaultTab}"/>
</c:if>
<table class="tabs">
	<tr>
		<td colspan="2" style="height: 10px" class="top">
			<table class="tab_list">
				<tr>
					<jsp:doBody/>
				</tr>
			</table>
		</td>
		<td class="top_right" style="height: 10px;"></td>
	</tr>
	<tr>
		<td class="left" />
		<td class="center">
			<div class="tab_dimensions" <c:if test="${not empty height}">style="height:${height};"</c:if>>
				<tiles:insertAttribute name="content"/>
			</div>
		</td>
		<td class="right" />
	</tr>
	<tr>
		<td class="bottom_left" />
		<td class="bottom" />
		<td class="bottom_right" />
	</tr>
</table>
</div>