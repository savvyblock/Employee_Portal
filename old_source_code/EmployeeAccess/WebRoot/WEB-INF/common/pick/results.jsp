<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="employeeaccess" uri="http://www.esc20.net/tags/employeeaccess-util"%>

<div id="results">
	<employeeaccess:Table height="400px;" width="400px;"
	pagination="true"
	pageNumber="${pick.pageNumber}" 
	pagesAvailable="${pick.pagesAvailable}" 
	records="${pick.records}"
	id="pick">
		<tr>
			<c:if test="${not empty codeHeader1}">
				<employeeaccess:Header title="${codeHeader1}"/>
			</c:if>
			<c:if test="${empty codeHeader1}">
				<employeeaccess:Header title="${codeType}"/>
			</c:if>
			
			<c:if test="${not empty codeHeader2}">
				<employeeaccess:Header title="${codeHeader2}"/>
			</c:if>
			<c:if test="${empty codeHeader2}">
				<employeeaccess:Header title="Description"/>
			</c:if>
			
		</tr>
		
		<employeeaccess:EmptyRow items="${codes}"/>
	
		<c:set var="itemCount" value="-1" scope="request"/>
		<c:forEach var="item" items="${paginatedCodes}" varStatus="counter">
			<c:set var="itemCount" value="${counter.index}" scope="request"/>
			<employeeaccess:Row id="${counter.index}" cssClass="${rowClass}" doPostback="false">
				<td>

					<a href="${flowExecutionUrl}&_eventId=selectCode&selected=${item.code}&description=${item.description}&subSelected=${item.subCode}">${item.code}</a>

					<%--
					<a href="#" id="pickLink${counter.index}"
					onclick="Spring.remoting.submitForm('pickLink${counter.index}', 'pickForm', { _eventId: select }); return false;">
					${item.code}</a>
					 --%>
				</td>
				<employeeaccess:Cell value="${item.description}" id="description"/>
			</employeeaccess:Row>
		</c:forEach>
	</employeeaccess:Table>
</div>
