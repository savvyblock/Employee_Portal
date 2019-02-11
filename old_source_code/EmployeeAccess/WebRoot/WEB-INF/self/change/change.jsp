<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="employeeaccess" uri="http://www.esc20.net/tags/employeeaccess-util"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<tiles:insertAttribute name="error" />

<form:form id="mainForm" modelAttribute="changePassword" method="post">
	<div class="has_default">
		<table style="margin-left:10px;">
				<tr>
					<td style="color:red"> Please enter a new password and verify it to change your current password. </td>
				</tr>
		</table>
		<table style="margin-left:10px;margin-bottom:0px">
			<tr>
				<td style="padding-top:5px;"><c:out value="New Password"/></td> 
				<td style="padding-left:10px; padding-top:5px;"><form:password  id="password" path="password" cssErrorClass="text errorInput" cssClass="initial_focus wrap_field" tabindex="1" maxlength="9" size="12"/></td>
				<td style="padding-left:20px; padding-top:5px;"><c:out value="New Password Verification"/></td> 
				<td style="padding-left:10px; padding-top:5px;"><form:password id="passwordVerification" path="passwordVerification" cssErrorClass="text errorInput" cssClass="" maxlength="9" tabindex="1" size="12"/></td>
				<td style="padding-left:10px;">
					<employeeaccess:button id="submitButton" formid="mainForm" flowUrl="${flowExecutionUrl}" event="submit" tabindex="1" onclick="UnsavedDataWarning.disable();" label="Update" checkunsaved="false" cssClass="default last_field ignore_changes" />
				</td>
			</tr>
		</table>
	</div>
</form:form>