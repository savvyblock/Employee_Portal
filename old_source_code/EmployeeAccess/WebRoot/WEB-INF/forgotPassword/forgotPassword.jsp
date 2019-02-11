<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="employeeaccess" uri="http://www.esc20.net/tags/employeeaccess-util"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<tiles:insertAttribute name="error" />
<tiles:insertAttribute name="search" />

<c:if test="${validEmployee}">
	<form:form id="mainForm" modelAttribute="user" method="post">
		<div class="has_default" style="margin-left:20px;">
			<table>
				<tr>
					<td>
						<table>
							<tr>
								<td style="padding-top:5px;">Employee Number</td> 
								<td style="padding-left:10px; padding-top:5px;"><c:out value="${user.employeeNumber}"/></td>
							</tr>
							<tr>
								<td style="padding-top:5px;">Date of Birth</td>
								<td style="padding-left:10px; padding-top:5px;"><c:out value="${user.formattedDateOfBirth}"/></td>
							</tr>
							<tr>
								<td style="padding-top:5px;">Zip Code</td>
								<td style="padding-left:10px; padding-top:5px;"><c:out value="${user.zipCode}"/></td>
							</tr>
							<tr>
								<td style="padding-top:5px;">Last Name</td>
								<td style="padding-left:10px; padding-top:5px;"><c:out value="${user.lastName}"/></td>
							</tr>
							<tr>
								<td style="padding-top:5px;">First Name</td>
								<td style="padding-left:10px; padding-top:5px;"><c:out value="${user.firstName}"/></td>
							</tr>
						</table>
						<c:choose>
							<c:when test="${user.workEmail == '' && user.homeEmail ==''}">
								<table>
									<tr>
										<td style="color:red"> No email address exists on file for employee. Please contact your personnel office to have your security information deleted. You may then re-register as a new user. </td>
									</tr>
								</table>
							</c:when>
							<c:otherwise>
								<table>
									<tr>
										<td style="color:red"> Please answer your hint question (answer is case sensitive) and select the email address to send password. </td>
									</tr>
								</table>
								<table style="margin-bottom:0px">
									<tr>
										<td style="padding-top:5px;"><c:out value="${user.hint}"/></td> 
										<td style="padding-left:10px; padding-top:5px;"><input class="textbox ignore_changes initial_focus wrap_field text" id="hintAnswerField" name="hintAnswerField" tabindex="1" maxlength="30" size="30"/></td>
									</tr>
								</table>
								<c:if test="${user.workEmail != ''}">
									<table style="margin-bottom:0px">
										<tr>
											<td style="padding-top:5px;"><input type="radio" name="emails" tabindex="1" VALUE="work" CHECKED></td>
											<td style="padding-left:10px; padding-top:5px;"><c:out value="${user.workEmail}"/></td>
											<td style="padding-left:10px; padding-top:5px;"><c:out value="Work E-mail"/></td>
										</tr>
									</table>
								</c:if>
							<c:if test="${user.homeEmail != ''}">
									<table style="margin-bottom:0px">
										<tr>
											<td style="padding-top:5px;"> <input type="radio" name="emails" tabindex="1" VALUE="home"></td>
											<td style="padding-left:10px; padding-top:5px;"><c:out value="${user.homeEmail}"/></td>
											<td style="padding-left:10px; padding-top:5px;"><c:out value="Home E-mail"/></td>
										</tr>
									</table>
								</c:if>
								<table style="margin-bottom:0px">
									<tr>
										<td>
											<employeeaccess:button id="submitButton" onclick="UnsavedDataWarning.disable();" formid="mainForm" flowUrl="${flowExecutionUrl}" event="submit" tabindex="1"  label="Submit" checkunsaved="false" cssClass="default last_field" />
										</td>
									</tr>
								</table>
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
			</table>
		</div>
	</form:form>
</c:if>