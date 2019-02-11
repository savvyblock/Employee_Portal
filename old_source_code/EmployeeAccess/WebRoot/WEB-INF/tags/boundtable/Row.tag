<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ attribute name="id" required="true" %>
<%@ attribute name="cssClass" required="false" %>
<%@ attribute name="cssStyle" required="false" %>
<%@ attribute name="disabled" required="false" %>
<%@ attribute name="checkUnsaved" required="false" type="java.lang.Boolean" %>
<%@ attribute name="showDetailIcon" required="false" type="java.lang.Boolean" %>
<%@ attribute name="showSingleDeleteIcon" required="false" type="java.lang.Boolean" %>
<%@ attribute name="showMultiDeleteIcon" required="false" type="java.lang.Boolean" %>
<%@ attribute name="isMarkedForDeletion" required="false" type="java.lang.Boolean" %>
<%@ attribute name="path" required="false" %>
<%@ attribute name="doPostback" required="false" %>

<c:if test="${empty doPostback}">
	<c:set var="doPostback" value="true" />
</c:if>

<c:if test="${empty checkUnsaved}">
	<c:set var="checkUnsaved" value="false"/>
</c:if>

<c:if test="${doPostback}">
	<c:set var="onclick" value="${onclick};if(doSelect)select('link_${id}',selectActionKeys${id},selectActionValues${id}, '${tableid}');"></c:set>
</c:if>

<c:if test="${disabled}">
	<c:set var="disabledStyle" value="disabledRow" />
</c:if>

<c:if test="${isMarkedForDeletion}">
	<c:set var="deleteRow" value="deleteRow"/>
	<c:set var="cssClass" value=""/>
</c:if>

<c:if test="${showDetailIcon}">
	<c:set var="hasSelectableLink" value="has_selectable_link"/>
	<c:set var="linkClick" value="${onclick}"/>
	<c:set var="onclick" value=""/>
</c:if>

<c:if test="${not empty tableid}">
	<c:set var="tableid" value="_${tableid}" />
</c:if>

<c:if test="${checkUnsaved}">
	<c:set var="onClickCheckUnsaved" value="$('#isUnsavedData').val(UnsavedDataWarning.isDirty());"/>
</c:if>

<tr id="link_${id}"
	class="${cssClass} ${disabledStyle} ${navigationButton} ${hasSelectableLink} ${deleteRow}"
	style="vertical-align: top; height: 100%; ${cssStyle}"
	onclick="${onclick}">
	
	<c:if test="${showSingleDeleteIcon}">
		<td style="text-align:center;">
			<a class="deleteRowButton" tabindex="${tabindex}" href="#"
			 onclick="Spring.remoting.submitForm('toDelete${tableid}', $(this).parents('form').attr('id'), { _eventId:'delete${tableid}', deleteIndex:'${id}' }); return false;"/>
		</td>
	</c:if>		
	
	<c:if test="${showMultiDeleteIcon}">
		<td style="text-align:center;">
			<a class="deleteRowButton<c:if test="${isMarkedForDeletion}"> deleteRowButtonSelected</c:if>" tabindex="${tabindex}"
			 onclick="toggleDelete(this, 'delete_${id}${tableid}'); return false;" href="#"/>
			<form:hidden id="delete_${id}${tableid}" path="${path}" htmlEscape="true"/>
		</td>
	</c:if>														
	
	<c:if test="${showDetailIcon}">
		<td style="text-align:center;">
			<a id="viewDetailsLink${id}" class="detailsRowButtonCenter" 
			onclick="UnsavedDataWarning.disable();${onClickCheckUnsaved}$(this).css('background-color','');${linkClick}" href="#"
			style="" tabindex="${tabindex}"></a>
		</td>
	</c:if>
	<jsp:doBody/>
</tr>
