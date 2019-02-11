<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="employeeaccess" uri="http://www.esc20.net/tags/employeeaccess-util"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<div id="pickPopup">
	<form:form id="pickForm" modelAttribute="pick" method="post">
		<c:if test="${not empty codeType}">
			Search for <c:out value="${codeType}"/>:<br/>
			<br/>
		</c:if>
		<table class="niceTable">
			<tr>
				<td>
					<c:if test="${not empty codeHeader1}">
						<c:out value="${codeHeader1}"/>:
					</c:if>
					<c:if test="${empty codeHeader1}">
						<c:out value="${codeType}"/>:
					</c:if>
				</td>
				<td>
					<c:if test="${not empty codeHeader2}">
						<c:out value="${codeHeader2}"/>:
					</c:if>
					<c:if test="${empty codeHeader2}">
						Title:
					</c:if>
				</td>
				<td> </td>
			</tr>
				<td>
					<form:input path="codeCriteria.searchCode" cssStyle="width:100px" maxlength="15" 
					cssClass="text ignore_changes" cssErrorClass="text errorInput ignore_changes" htmlEscape="true" tabindex="1"/>
				</td>
				<td>
					<form:input path="codeCriteria.searchDescription" cssStyle="width:200px" maxlength="50" 
					cssClass="text ignore_changes" cssErrorClass="text errorInput ignore_changes" htmlEscape="true" tabindex="1"/>
				</td>
				<td>
					<employeeaccess:button id="searchButton" event="search" 
						tabindex="1" type="tab" formid="pickForm" ajax="true"
						label="Search" checkunsaved="false" cssClass="default"/>
				</td>
			<tr>
			</tr>
		</table>
		<br/>
		<tiles:insertAttribute name="results"/>
		<employeeaccess:button id="cancelButton" event="cancel" 
			tabindex="1" type="tab" formid="mainForm"
			label="Cancel" checkunsaved="false" cssClass="default" 
			popupdispose="true" popupid="pickPopup" springPopup="true" />
	</form:form>
</div>
