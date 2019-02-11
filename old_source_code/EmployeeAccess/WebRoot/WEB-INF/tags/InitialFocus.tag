<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ attribute name="start" required="false" %>
<%@ attribute name="select" required="false" %>
<%@ attribute name="add" required="false" %>
<%@ attribute name="isNew" required="false" type="java.lang.Boolean" %>
<%@ attribute name="exclude" required="false" type="java.lang.Boolean"%>

<c:if test="${not exclude or empty exclude}">
<script type="text/javascript">
$(document).ready(function () {
	<c:choose>
		<c:when test="${notSelected}">
			$(document.getElementById('${start}')).addClass('initial_focus');
			Behaviors.applyAllIncludeSelf(document.getElementById("${start}"));
		</c:when>
		<c:otherwise>
			<c:choose>
				<c:when test="${isNew}">
					$(document.getElementById("${add}")).focus();
					$(document.getElementById('${add}')).addClass('initial_focus');
					Behaviors.applyAllIncludeSelf(document.getElementById("${add}"));
					Behaviors.applyAllIncludeSelf(document.getElementById("retrieveButton"));
				</c:when>
				<c:otherwise>
					$(document.getElementById('${select}')).addClass('initial_focus');
					Behaviors.applyAllIncludeSelf(document.getElementById("${select}"));
				</c:otherwise>
			</c:choose>
		</c:otherwise>
	</c:choose>
});
</script>
</c:if>