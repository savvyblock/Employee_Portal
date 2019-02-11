<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:forEach var="item" items="${items}" >
	<c:out value="${item.code}"/>|<c:out value="${item.description}"/>
</c:forEach>
