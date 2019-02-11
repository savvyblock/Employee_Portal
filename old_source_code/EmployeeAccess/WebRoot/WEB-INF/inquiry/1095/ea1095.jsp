<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ea" uri="http://www.esc20.net/tags/employeeaccess-util"%>

<html>
	<head>
		<title>1095 Information</title>
	</head>
	<script type="text/javascript" src="<c:url value="/scripts/ea1095Information.js" />" ></script>
	<div id="mainDiv">
		<c:if test="${not empty EA1095InfoCommand.msg1095}">
			<span style="color:red"><ea:label value="${EA1095InfoCommand.msg1095}"/></span><br/>
			<br/>
		</c:if>
		<!-- 1095 Electronic Consent Update Message -->
		<c:if test="${EA1095InfoCommand.showSuccessMsg}">
			<span id="updateMsg" style="color:red"><b>Update was successful - an email will be sent confirming your selection.</b></span><br/>
			<br/>
		</c:if>
		<!-- Begin Form Code. This is specifically for transitioning to a metex page -->
		<form:form id="" name="wHrsForm" method="POST" action="/HumanResources/app/hrs0010/wHrsFrame.htm" >
			<input type="hidden" name="action" id="<c:out value="wHrsFrame_action"/>" value="<c:out value=""/>" />
		</form:form>

		<form:form id="EA1095Form" modelAttribute="EA1095InfoCommand" method="post">
			<div>
				<table style="width:1160px;">
					<tr>
						<td style="width:25%;">
							<c:if test="${not empty EA1095InfoCommand.calYrs}">
								<c:if test="${EA1095InfoCommand.currentYr <= EA1095InfoCommand.latestYr}">
									<a id="ea1095Print" style="cursor: pointer;"
										class="button button_medium hover_trigger link_button"
										hoverclass="button_medium_hover"
										tabindex="1"
										focus-class="button_medium_focus"
										disabled-class="button_medium_disabled"
										href="javascript:void(0);"
										onclick="$('#ea1095Print').click()">Print</a>

								</c:if>
							</c:if>

							<c:if test="${EA1095InfoCommand.enableElecConsnt1095}">
								<a id="ea1095Consent" style="cursor: pointer;"
									class="button button_medium hover_trigger link_button"
									hoverclass="button_medium_hover"
									tabindex="1"
									focus-class="button_medium_focus"
									disabled-class="button_medium_disabled"
									href="javascript:void(0);"
									onclick="displayEA1095ElecConsntPopup();">1095 Consent</a>
							</c:if>
							<br/>

							<c:if test="${not empty EA1095InfoCommand.calYrs}">
								Please select a calendar year:
								<spring:bind path="currentYr">
									<select id="currentYr" name="${status.expression}" value="${status.value}" class="text initial_focus" onchange="$('#ea1095Info').click();" style="width: 70px; padding-right:10px" tabindex="1">
										<c:forEach items="${EA1095InfoCommand.calYrs}" var="calYrsList">
											<option value="${calYrsList}" <c:if test='${status.value == calYrsList}'>selected="selected"</c:if>>${calYrsList}</option>
										</c:forEach>
									</select>
								</spring:bind>
								<spring:bind path="ea1095">
									<input type="radio" name="${status.expression}" id="ea1095B" class="text selectable ${errorClass}" onchange="$('#ea1095BSelect').click();" tabindex="1" value="B" <c:if test="${EA1095InfoCommand.disableEa1095B == 'Y'}">disabled="true"</c:if><c:if test="${status.value == 'B'}">checked</c:if>>1095-B
									<input type="radio" name="${status.expression}" id="ea1095C" class="text selectable ${errorClass}" onchange="$('#ea1095CSelect').click();" tabindex="1" value="C" <c:if test="${EA1095InfoCommand.disableEa1095C == 'Y'}">disabled="true"</c:if><c:if test="${status.value == 'C'}">checked</c:if>>1095-C
								</spring:bind>
							</c:if>
						</td>
						<!-- Hidden Fields -->
						<form:hidden path="ea1095ElecConsnt" id="ea1095ElecConsnt" />
						<form:hidden path="ea1095ElecConsntMsg" id="ea1095ElecConsntMsg" />
						<form:hidden path="showEA1095ElecConsntPopup" id="showEA1095ElecConsntPopup" />
						<input type="text" class="hidden" id="enableElecConsnt1095" value="${EA1095InfoCommand.enableElecConsnt1095}"/>
						<input type="hidden" id="year" value="${EA1095InfoCommand.currentYr}"/>
						<input type="hidden" id="w2Latest" value="${EA1095InfoCommand.latestYr}"/>
						<input type="hidden" id="showReportInNewWindow" value="${EA1095InfoCommand.showReportInNewWindow}"  name="myShowReportInNewWindow" />
						<form:hidden path="disableEa1095B" id="disableEa1095B"/>
						<form:hidden path="disableEa1095C" id="disableEa1095C"/>
						<input id="ea1095Consent" class="hidden" type="submit" name="mySubmitUpdateEA1095Consent" value="Submit Query"/>
						<input id="ea1095Info" class="hidden" type="submit" name="mySubmitEAInfo" value="Submit Query" />
						<input id="ea1095BSelect" class="hidden" type="submit" name="mySubmitEA1095B" value="Submit Query" />
						<input id="ea1095CSelect" class="hidden" type="submit" name="mySubmitEA1095C" value="Submit Query" />
						<input id="ea1095Print" class="hidden" type="submit" name="mySubmitEA1095Print" value="Submit Query" />
						<input id="SortButton" class="hidden" type="submit" name="mySubmitSortButton" value="Submit Query">
						<input type="text" class="hidden" id="ea1095ElecConsntFlag" value="${EA1095InfoCommand.ea1095ElecConsnt}"/>
						<form:hidden path="sortColumn1095B" id="sortColumn1095B"/>
						<form:hidden path="sortColumn1095C" id="sortColumn1095C"/>
					</tr>
				</table>
			</div>

			<c:if test="${empty EA1095InfoCommand.calYrs}">
				<div class="error">No 1095 Information is available.</div>
			</c:if>

			<c:if test="${not empty EA1095InfoCommand.calYrs && EA1095InfoCommand.ea1095 == 'B'}">
				<br/>
				<div class="groupBox">
					<div class="groupTitle">1095-B Information</div>
					<div class="groupContent" style="width:1200px">
						<c:import url="/WEB-INF/inquiry/1095/ea1095BHist.jsp"></c:import>
					</div>
				</div>
			</c:if>
			<c:if test="${not empty EA1095InfoCommand.calYrs && EA1095InfoCommand.ea1095 == 'C'}">
				<br/>
				<div class="groupBox">
					<div class="groupTitle">1095-C Information</div>
					<div class="groupContent" style="width:1200px">
						<c:import url="/WEB-INF/inquiry/1095/ea1095CHist.jsp"></c:import>
					</div>
				</div>
			</c:if>

			<%-- Print Button Popup --%>
			<c:import url="/WEB-INF/inquiry/1095/ea1095ElecConsentPopup.jsp" />
		</form:form>
	</div>
</html>