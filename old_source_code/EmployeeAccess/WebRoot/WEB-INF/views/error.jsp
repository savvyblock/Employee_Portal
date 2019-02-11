<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

	<div style="margin-left:10px; margin-bottom: 10px;">
		<c:forEach items="${flowRequestContext.messageContext.allMessages}" var="message">
			<c:choose>
				<c:when test="${fn:contains(message.text,'Save successful') or fn:contains(message.text,'There are no changes to save')}">
					<div style="color:green; font-weight:bold;">${message.text}</div>
				</c:when>
				<c:otherwise>
					<div class="error">${message.text}</div>
				</c:otherwise>
			</c:choose>
		</c:forEach>
	</div>
