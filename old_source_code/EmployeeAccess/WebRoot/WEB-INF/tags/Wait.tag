<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ attribute name="message" required="false" %>
<%@ attribute name="refresh" required="false" type="java.lang.Integer" %> <%-- minutes to wait between refreshes to server--%>

<c:if test="${empty message}">
	<c:set var="message" value="Processing, please wait..."/>
</c:if>

<script type="text/javascript">
	function showWait()
	{
		$('#wait').show();
		
		<c:if test="${not empty refresh}">
		setTimeout('refreshWait();',${refresh * 1000 * 60});
		</c:if>
	}
	
	<c:if test="${not empty refresh}">
	function refreshWait()
	{
		Spring.remoting.submitForm('wait', 'mainForm', { _eventId : 'refreshWait'});
		
		setTimeout('refreshWait();',${refresh * 1000 * 60});
	}
	</c:if>
</script>
<div id="wait" style="display:none;">
	<table>
		<tr>
			<td>
				<img src="<spring:theme code="commonBase" />/images/throbber_blue.gif" width="26" height="26" />
			</td>
			<td>
				<c:out value="${message}"/>
			</td>
		</tr>
	</table>
</div>