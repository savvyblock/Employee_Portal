<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="employeeaccess" uri="http://www.esc20.net/tags/employeeaccess-util"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<link rel="stylesheet" href="<spring:theme code="commonBase" />styles/saveGap.css" type="text/css"/>

<tiles:insertAttribute name="error" />
<tiles:insertAttribute name="search" />

<c:if test="${validEmployee}">
	<form:form id="mainForm" modelAttribute="newUser" method="post">
		<div class="has_default">
			<table>
				<tr>
					<td>
						<table>
							<tr>
								<td style="padding-left:10px; padding-top:5px;">Employee Number</td> 
								<td style="padding-left:10px; padding-top:5px;"><c:out value="${newUser.employeeNumber}"/></td>
							</tr>
							<tr>
								<td style="padding-left:10px; padding-top:5px;">Date of Birth</td>
								<td style="padding-left:10px; padding-top:5px;"><c:out value="${newUser.formattedDateOfBirth}"/></td>
							</tr>
							<tr>
								<td style="padding-left:10px; padding-top:5px;">Zip Code</td>
								<td style="padding-left:10px; padding-top:5px;"><c:out value="${newUser.zipCode}"/></td>
							</tr>
							<tr>
								<td style="padding-left:10px; padding-top:5px;">Last Name</td>
								<td style="padding-left:10px; padding-top:5px;"><c:out value="${newUser.lastName}"/></td>
							</tr>
							<tr>
								<td style="padding-left:10px; padding-top:5px;">First Name</td>
								<td style="padding-left:10px; padding-top:5px;"><c:out value="${newUser.firstName}"/></td>
							</tr>
						</table>
						<table>
							<tr>
								<td style="padding-left:10px; padding-top:5px; color:red"> Please enter the security information and click the save button. </td>
							</tr>
						</table>
						<table style="margin-bottom:0px">
							<tr>
								<td style="padding-left:10px; padding-top:5px;">User Name</td>
								<td style="padding-left:10px; padding-top:5px;"><form:input id="userName" path="userName" cssErrorClass="text errorInput" tabindex="1" size="12" maxlength="8" cssClass=" text initial_focus wrap_field"/></td>
							</tr>
							<tr>
								<td style="padding-left:10px; padding-top:5px;">Password</td>
								<td style="padding-left:10px; padding-top:5px;"><form:password id="password" path="password" cssErrorClass="text errorInput" cssClass="text" tabindex="1" maxlength="9" size="12"/></td>
							</tr>
							<tr>
								<td style="padding-left:10px; padding-top:5px;">Password Verification</td>
								<td style="padding-left:10px; padding-top:5px;"><form:password id="passwordVerification" path="passwordVerification" cssClass="text" cssErrorClass="text errorInput" maxlength="9" tabindex="1" size="12"/></td>
							</tr>
							<tr>
								<td style="padding-left:10px; padding-top:5px;">Work E-mail</td>
								<c:choose>
									<c:when test="${newUser.workEmailEmpty}">
										<td style="padding-left:10px; padding-top:5px;"><form:input id="workEmail" path="workEmail" cssClass="text" cssErrorClass="text errorInput" tabindex="1" maxlength="70" size="70"/></td>
									</c:when>
									<c:otherwise><td style="padding-left:10px; padding-top:5px;"><c:out value="${newUser.workEmail}"/></td></c:otherwise>
								</c:choose>
							</tr>
							<c:if test="${newUser.workEmailEmpty}">
								<tr>
									<td style="padding-left:10px; padding-top:5px;">Work E-mail Verification</td>
									<td style="padding-left:10px; padding-top:5px;"><form:input id="workEmailVerification" cssClass="text" cssErrorClass="text errorInput" path="workEmailVerification" tabindex="1" maxlength="70" size="70"/></td>
								</tr>
							</c:if>
							  <tr>
								<td style="padding-left:10px; padding-top:5px;">Home E-mail</td>
								<c:choose>
									<c:when test="${newUser.homeEmailEmpty}">
										<td style="padding-left:10px; padding-top:5px;"><form:input id="homeEmail" path="homeEmail" cssErrorClass="text errorInput" tabindex="1" maxlength="70" size="70"/></td>
									</c:when>
									<c:otherwise><td style="padding-left:10px; padding-top:5px;"><c:out value="${newUser.homeEmail}"/></td></c:otherwise>
								</c:choose>
							</tr>
							<c:if test="${newUser.homeEmailEmpty}">
								<tr>
									<td style="padding-left:10px; padding-top:5px;">Home E-mail Verification</td>
									<td style="padding-left:10px; padding-top:5px;"><form:input id="homeEmailVerification" cssErrorClass="text errorInput" path="homeEmailVerification" tabindex="1" maxlength="70" size="70"/></td>
								</tr>
							</c:if>
							<tr>
								<td style="padding-left:10px; padding-top:5px;">Hint Question</td>
								<td style="padding-left:10px; padding-top:5px;"><form:input id="hint" path="hint" cssClass="text" cssErrorClass="text errorInput" tabindex="1" maxlength="50" size="50"/></td>
							</tr>
							<tr>
								<td style="padding-left:10px; padding-top:5px;">Hint Answer</td>
								<td style="padding-left:10px; padding-top:5px;"><form:input id="hintAnswer" cssClass="text" cssErrorClass="text errorInput" path="hintAnswer" tabindex="1" maxlength="30" size="30"/></td>
							</tr>
						</table>
						<table style="margin-left:8px; margin-top:0px">
							<tr>
								<td>
									<div class="save_position">
										<employeeaccess:button id="saveButton" formid="mainForm" onclick="UnsavedDataWarning.disable();" flowUrl="${flowExecutionUrl}" event="save" tabindex="1" type="" label="Save" checkunsaved="false" cssClass="default" />
									</div>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
	</form:form>
</c:if>