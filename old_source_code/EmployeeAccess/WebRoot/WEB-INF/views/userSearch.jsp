<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="employeeaccess" uri="http://www.esc20.net/tags/employeeaccess-util"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<script type="text/javascript" src="<c:url value="/scripts/autoAdvance.js" />"/></script>

<c:if test="${useSSN == null}">
	<span style="font-weight:bold; color:red">Employee Access Options in Human Resources must first be set up.</span>
</c:if>
<c:if test="${!validEmployee && useSSN != null}">
		<form:form id="mainForm" modelAttribute="searchEmployee" method="post"  cssClass="has_default">
			<div style="margin-left: 15px; margin-top:30px; color:red;">
				<c:if test="${!useSSN}"><c:out value="Please enter your employee number, date of birth, and zip and click Retrieve. "/></c:if>
				<c:if test="${useSSN}"><c:out value="Please enter your social security #, date of birth, and zip and click Retrieve. "/></c:if>
			</div>
			<table style="margin-top:5px; margin-bottom:5px">
				<tr>
					<c:if test="${!useSSN}">
					<td style="padding-left: 30px;padding-top: 5px;"><c:out value="Employee Number"/></td>
					<td style="padding-left: 10px;padding-top: 5px;"><form:input id="employeeNumber" path="searchEmployeeNumber" cssClass="text initial_focus wrap_field ignore_changes" maxlength="6" size="9" tabindex="1"/></td>
					</c:if>
					<c:if test="${useSSN}">
					<td style="padding-left: 30px;padding-top: 5px;"><c:out value="Social Security #"/></td>
					<td style="padding-left: 23px;padding-top: 5px;"><form:input id="SSN" path="searchSSN" cssClass="text initial_focus wrap_field ignore_changes" maxlength="9" size="9" tabindex="1"/></td>
					</c:if>
					<td style="padding-left: 10px;padding-top: 5px;"><c:out value="(no dashes)"/></td>
				</tr>
			</table>
			<table style="margin-top:0px; margin-bottom:0px">
				<tr>
					<td style="padding-left: 30px;"><c:out value="Date of Birth"/></td>
					<td style="padding-left: 46px;"><form:input id="date_1" path="searchDateofBirthMonth" cssClass="text autoAdvance ignore_changes"  maxlength="2" size="2" tabindex="1"/></td>
					<td style="padding-left:  5px;"><form:input id="date_2" path="searchDateofBirthDay" cssClass="text autoAdvance ignore_changes"  maxlength="2" size="2" tabindex="1"/></td>
					<td style="padding-left:  5px;"><form:input id="date_3" path="searchDateofBirthYear" cssClass="text autoAdvance ignore_changes" maxlength="4" size="4" tabindex="1"/></td>
					<td style="padding-left:  5px;">(mm dd yyyy)</td>
				</tr>
			</table>
			<table>
				<tr>
					<td style="padding-left: 30px;"><c:out value="Zip Code"/></td>
					<td style="padding-left: 63px;"><form:input id="date_4" path="searchZipCode" cssClass="text autoAdvance ignore_changes" maxlength="5" size="5" tabindex="1"/></td>
					<td style="padding-left:  5px;">
						<employeeaccess:button id="findButton" flowUrl="${flowExecutionUrl}" event="retrieve" tabindex="1" label="Retrieve" formid="mainForm" checkunsaved="false" cssClass="default last_field ignore_changes" />	
					</td>	
				</tr>
			</table>
		</form:form>
</c:if>