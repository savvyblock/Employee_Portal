<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="employeeaccess" uri="http://www.esc20.net/tags/employeeaccess-util"%>

<%@ attribute name="path" required="true" %>
<%@ attribute name="id" required="true" %>
<%@ attribute name="items" required="true" type="java.util.Collection" %>
<%@ attribute name="nulloption" required="false" type="java.lang.Boolean" %>
<%@ attribute name="disabled" required="false" type="java.lang.Boolean" %>
<%@ attribute name="onchange" required="false" %>
<%@ attribute name="onfocus" required="false" %>
<%@ attribute name="cssClass" required="false" %>
<%@ attribute name="cssStyle" required="false" %>
<%@ attribute name="tabindex" required="false" type="java.lang.Integer" %>

<!-- Display parameters -->

<%@ attribute name="type" required="false" %> <!-- Available types are: "code", "location", "string" -->
<%@ attribute name="showCode" required="false" type="java.lang.Boolean" %>
<%@ attribute name="showDescription" required="false" type="java.lang.Boolean" %>
<%@ attribute name="showCodeInPopup" required="false" type="java.lang.Boolean" %>
<%@ attribute name="showDescriptionInPopup" required="false" type="java.lang.Boolean" %>

<!-- Default values -->

<c:if test="${empty type}">
	<c:set var="type" value="code"/>
</c:if>

<c:if test="${empty showCode}">
	<c:set var="showCode" value="false"/>
</c:if>

<c:if test="${empty showDescription}">
	<c:set var="showDescription" value="true"/>
</c:if>

<c:if test="${empty showCodeInPopup}">
	<c:set var="showCodeInPopup" value="true"/>
</c:if>

<c:if test="${empty showDescriptionInPopup}">
	<c:set var="showDescriptionInPopup" value="true"/>
</c:if>

<!-- Start bound component -->

<spring:bind path="${path}">

	<c:if test="${empty nulloption}">
		<c:set var="nulloption" value="true"/>
	</c:if>
	
	<form:errors path="${path}" cssStyle="display:none">
		<c:set var="errorClass" value="errorInput"/>
	</form:errors>
	
	<!-- TODO: check type here? -->
	
	<!-- If both showCode and showDescription are false, the code will show -->
	

				<select id="${id}" 
					name="${status.expression }"
					class="${cssClass} ${errorClass}"
					style="min-width:40px; ${cssStyle}"
					value="${status.value}"
					onchange="${onchange}"
					tabindex="${tabindex}"
					<c:if test="${disabled}">
						disabled="true"
					</c:if>
					>


						<c:if test="${nulloption}">
							<option value=""></option>
						</c:if>
						
						<c:set var="firstLoop" value="true"/>
						<c:forEach items="${items}" var="listItem">
							<c:choose>
								<c:when test="${type eq 'code'}">
									<c:set var="itemCode" value="${listItem.code}"/>
									<c:set var="itemDescription" value="${listItem.description}"/>
								</c:when>
								<c:when test="${type eq 'location'}">
									<c:set var="itemCode" value="${listItem.id}"/>
									<c:set var="itemDescription" value="${listItem.description}"/>
								</c:when>
								<c:when test="${type eq 'string'}">
									<c:set var="itemCode" value="${listItem}"/>
								</c:when>
								<c:otherwise>
								</c:otherwise>
							</c:choose>
							
							<!-- Keep track of duplicate codes - don't allow their selection -->
							
							<c:set var="duplicateCode" value="false"/>
							
							<c:if test="${not firstLoop and prevCode eq itemCode}">
								<c:set var="duplicateCode" value="true"/>
							</c:if>
							
							<c:set var="firstLoop" value="false"/>
							<c:set var="prevCode" value="${itemCode}"/>
						
							<!-- Skip items that don't have a code -->
							
							<c:set var="selectedOption" value=""/>
							<c:if test="${itemCode eq status.value}">
								<c:set var="selectedOption" value="selected"/>
							</c:if>

							<option value="${itemCode}" ${selectedOption}>
								<c:choose>
									<c:when test="${showCode}">
										<c:set var="displayDescription" value="${itemCode} - ${itemDescription}"/>
									</c:when>
									<c:otherwise>
										<c:set var="displayDescription" value="${itemDescription}"/>
									</c:otherwise>
								</c:choose>
								
								<c:if test="${not showDescription or empty itemDescription}">
									<c:set var="displayDescription" value="${itemCode}"/>
								</c:if>
							
								<c:if test="${duplicateCode}">
									<c:set var="itemCode" value="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"/>
								</c:if>
								<c:if test="${not empty itemCode}">
									<c:if test="${showCodeInPopup}">
										${itemCode}
									</c:if>
									<c:if test="${showCodeInPopup and showDescriptionInPopup and type != 'location' and not duplicateCode}">
									 - 
									</c:if>
								</c:if>
								<c:if test="${showDescriptionInPopup}">
									${itemDescription}
								</c:if>
							</option>
						</c:forEach>
					</select>


</spring:bind>





