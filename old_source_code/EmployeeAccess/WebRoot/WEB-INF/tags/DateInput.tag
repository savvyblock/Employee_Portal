<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ attribute name="path" required="true" %>
<%@ attribute name="id" required="true" %>
<%@ attribute name="disabled" required="false" type="java.lang.Boolean" %>
<%@ attribute name="onchange" required="false" %>
<%@ attribute name="tabindex" required="false" type="java.lang.Integer" %>
<%@ attribute name="cssClass" required="false" type="java.lang.String" %>

<style type="text/css">
img
{
	margin: 0 0 0 0;
}
</style>

<c:set var="hasError" value="false"/>
<form:errors path="${path}" cssStyle="display:none">
	<c:set var="hasError" value="true"/>
	<c:set var="errorClass" value="errorInput"/>
</form:errors>

<spring:bind path="${path}">
	<input type="text" id="${id}Text"  
	name="${status.expression}"
	maxlength="8" style="width:80px;" 
	class="text edit_mask ${cssClass} ignore_changes" 
	value="${status.value}"
	edit-mask="##-##-####"
	onchange="onChangeText${id}();"
	tabindex="${tabindex}"
	<c:if test="${disabled}">disabled="${disabled}"</c:if>/>
	
<c:if test="${disabled}">
	<c:set var="disabledLink" value="return false;"/>
</c:if>

<a href="javascript:void(0)" id="${id}Link" onclick="${disabledLink}onChangeText${id}();doCalendarPopup${id}();return false;"
style="text-decoration:none;" tabindex="-1">
<img src='<c:url value="/images/Calendar-silk.png" />' tabindex="-1"/>
</a>
	<input type="text" id="${id}" value="${status.value}" style="visibility:hidden; width:0px; height:0px;"/>

	<script type="text/javascript">
		function doCalendarPopup${id}()
		{
			dijit.byId('${id}').focus();
			//document.getElementById('${id}').focus();
			//document.getElementById("widget_${id}_dropdown").attr("visibility","visible");
			//document.getElementById('widget_${id}')._open();
		}
	
		function onChangeText${id}()
		{
			document.getElementById('${id}').value = document.getElementById('${id}Text').value;
			<c:if test="${not fn:contains(cssClass,'ignore_changes')}">
			UnsavedDataWarning.forceDirtyTransient();
			</c:if>
		}
	
		function onChange${id}()
		{
			var newDate = new Date();
			var currentValue = dojo.date.locale.parse(dojo.byId("${id}").value, {selector : "date", datePattern : "MM-dd-yyyy"});

			if(currentValue != null)
			{
			newDate.setTime(Date.parse(currentValue));
			var newMonth = newDate.getMonth() + 1;
			if(newMonth < 10)
			{
				newMonth = "0" + newMonth;
			}
			var newDay = newDate.getDate();
			if(newDay < 10)
			{
				newDay = "0" + newDay;
			}
			newValue = newMonth + "-" + newDay + "-" + newDate.getFullYear();
			newValue = newValue.replace(/-/g,"");

			var node = document.getElementById('${id}Text');
			
			if(!node.paste)
			{
				Behaviors.applyAllIncludeSelf(node);
			}

			node.paste(newValue);
			}
			${onchange};
			<c:if test="${not fn:contains(cssClass,'ignore_changes')}">
			UnsavedDataWarning.forceDirtyTransient();
			</c:if>
		}
		
		function setupTextBox${id}()
		{
			var node = document.getElementById('${id}Text');
			if(node.fireChangeEventOnKeyPress == undefined)
			{
				Behaviors.applyAllIncludeSelf(node);
			}
			node.fireChangeEventOnKeyPress = false;
		}

		dojo.addOnLoad(setupTextBox${id});
		
		Spring.addDecoration(new Spring.ElementDecoration( {
			elementId : "${id}",
			widgetType :"dijit.form.DateTextBox",
			widgetAttrs : {
				value : dojo.date.locale.parse(dojo.byId("${id}").value, {selector : "date", datePattern : "MM-dd-yyyy"}),
				constraints : { datePattern : "MM-dd-yyyy" },
				invalidMessage: null,
				onChange : onChange${id},
				style : "visibility:hidden; width:0px; height:0px;"
				<c:if test="${disabled}">,disabled : ${disabled}</c:if>
			}
		}));
	</script>
</spring:bind>