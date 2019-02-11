<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ attribute name="items" required="true" type="java.util.Collection" %>

<c:if test = "${empty items}">
	<tr id="row" class="unselectedRow">
		<td colspan="0">No rows.</td>
	</tr>
</c:if>