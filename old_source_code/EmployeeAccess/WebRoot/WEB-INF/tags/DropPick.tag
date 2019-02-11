<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ attribute name="path" required="true" %>
<%@ attribute name="id" required="true" %>
<%@ attribute name="codeType" required="false" %>
<%@ attribute name="items" required="false" type="java.util.Collection"%>

<%@ attribute name="cssStyle" required="false" %>
<%@ attribute name="cssClass" required="false" %>
<%@ attribute name="tabindex" required="false" %>
<%@ attribute name="onEdit" required="false" %>
<%@ attribute name="onChange" required="false" %>
<%@ attribute name="onBlur" required="false" %>
<%@ attribute name="maxLength" required="false" %>
<%@ attribute name="popupWidth" required="false" %>

<%@ attribute name="disabled" required="false" type="java.lang.Boolean" %>
<%@ attribute name="disabledField" required="false" type="java.lang.Boolean" %>
<%@ attribute name="isDropDown" required="false" type="java.lang.Boolean" %>
<%@ attribute name="isPickList" required="false" type="java.lang.Boolean" %>
<%@ attribute name="isCached" required="false" type="java.lang.Boolean" %>
<%@ attribute name="isSorted" required="false" type="java.lang.Boolean" %>
<%@ attribute name="context" required="false" %>
<%@ attribute name="descriptionField" required="false" %>

<c:if test="${not empty id}">
	<c:set var="idAppend" value="_${id}"/>
</c:if>

<c:if test="${empty tabindex}">
	<c:set var="tabindex" value="1"/>
</c:if>

<c:if test="${empty popupWidth}">
	<c:set var="popupWidth" value="300px"/>
</c:if>

<c:set var="cssClassTotal" value="text ${cssClass}"/>

<c:if test="${!isPickList}">
	<c:set var="cssClassTotal" value="dropPick ${cssClassTotal}"/>
</c:if>

<c:if test="${isDropDown}">
	<c:set var="cssClassTotal" value="${cssClassTotal} autoComplete_drop_down"/>
</c:if>

<c:if test="${!disabled}">
	<c:set var="onclick" value="${onEdit}; Spring.remoting.submitForm('${path}Link', 'mainForm', { _eventId: 'pick${idAppend}' }); return false;"/>
	<c:set var="onmousedown" value="doDropPickDropDown('${path}');return false;"/>
</c:if>

<c:set var="itemsPreId" value="${id}"/>

<c:if test="${not empty codeType}">
	<c:set var="itemsPreId" value="${codeType}"/>
</c:if>

<form:errors path="${path}" cssStyle="display:none">
	<c:set var="errorClass" value="errorInput"/>
</form:errors>

<spring:bind path="${path}">
	<table cellpadding="0" cellspacing="0" style="height: 17px;">
		<tr>
			<td style="vertical-align: top; padding: 0px; 0px; 0px; 0px;border-width:0px; height:17px;">
				<c:if test="${not empty items}">
					<script type="text/javascript">
						if(items_${itemsPreId} == undefined)
						{
							var items_${itemsPreId} = new Array();
							
							<c:forEach var="item" items="${items}" varStatus="counter">
								items_${itemsPreId}[${counter.index}] = ["${item.code}","${item.description}"];
							</c:forEach>
						}
					</script>
					
					<c:set var="itemsId" value="items_${itemsPreId}"/>
				</c:if>
			
				<input type="text" autocomplete="off" id="${path}" name="${status.expression}" class="${cssClassTotal} ${errorClass}" 
				codeType="${codeType}" isCached="${isCached}" isFiltered="${!isDropDown}" context="${context}" isSorted="${isSorted}"
				isOpen="false" descriptionField="${descriptionField}" items="${itemsId}"
				popupWidth="${popupWidth}" value="${status.value}"
				style="${cssStyle}; margin:0px 0px 2px 0px; padding: 0px 0px 0px 0px; height:15px;"
				maxlength="${maxLength}" onchange="${onChange}" onblur="${onBlur}" 
				<c:if test="${disabled || disabledField}">disabled="disabled"</c:if> 
				tabindex="${tabindex}"/>
			</td>
			<c:if test="${isDropDown}">
				<td style="vertical-align: bottom; padding: 0px; 0px; 0px; 0px;border-width:0px;">
					<a href="#" onclick="${onmousedown}">
					<img src="<spring:theme code="commonBase" />/images/drop_down_button.gif"
					style="margin:0px 0px 0px 0px; padding: 0px 0px 0px 0px; line-height: 0px; height: 17px;"/>
					</a>
				</td>
			</c:if>
			<c:if test="${isPickList}">
				<td style="vertical-align: bottom; padding: 0px; 0px; 0px; 0px;border-width:0px;">
					<a href="#" id="${path}Link"
					<%-- href="${flowExecutionUrl}&_eventId=pick&codeType=${codeType}" 
					 onclick="Spring.remoting.getLinkedResource('${codeType}', {fragments : 'body'}, true); return false;"--%>
					onclick="${onclick}"
					>
					<img src="<spring:theme code="commonBase" />images/ellipsis_icon_button.gif"
					<c:if test="${disabledField}">tabindex="${tabindex}"</c:if> 
					style="margin:0px 0px 0px 0px; padding: 0px 0px 0px 0px; line-height: 0px; height: 17px;"/>
					</a>
				</td>
			</c:if>
		</tr>
	</table>
</spring:bind>

