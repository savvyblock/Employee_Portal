<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="employeeaccess" uri="http://www.esc20.net/tags/employeeaccess-util"%>

<div id="warningPopup">
	<form:form method="post">
		<table class="layout">
			<tr>
				<c:set var="image" value="message_box_question"/>
				
				<c:if test="${isWarning}">
					<c:set var="image" value="message_box_warning"/>
				</c:if>
				<c:if test="${isOk}">
					<c:set var="image" value="message_box_info"/>
				</c:if>
			
				<td class="message_icon">
					<img src="/CommonWeb/images/${image}.gif" />
				</td>
	
				<td>
					<h1><c:out value="${title}" escapeXml="false"/></h1>
					<p><c:out value="${content}" escapeXml="false"/></p>
	
					<c:set var="cancelLabel" value="No"/>
					<c:set var="yesLabel" value="Yes"/>
					
					<c:if test="${isSave}">
						<c:set var="cancelLabel" value="Cancel"/>
					</c:if>
					
					<c:if test="${isOk}">
						<c:set var="cancelLabel" value="OK"/>
					</c:if>
					
					<c:if test="${isOkCancel}">
						<c:set var="yesLabel" value="OK"/>
						<c:set var="cancelLabel" value="Cancel" />
					</c:if>
					
					<c:if test="${isCommitCancel}">
						<c:set var="yesLabel" value="Commit"/>
						<c:set var="cancelLabel" value="Cancel" />
					</c:if>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<table>
						<tr>
							<td style="width:50%;"> </td>
							<td>
								<table>
									<tr>
										<c:if test="${!isOk}">
											<td>
												<employeeaccess:button id="yesButton" event="yes"
													tabindex="1" type="tab" formid="mainForm" 
													label="${yesLabel}" checkunsaved="false" cssClass="default" ajax="${isAjax}"/>
											</td>
										</c:if>
										<c:if test="${isSave}">
											<td>
												<employeeaccess:button id="noButton" event="no" 
													tabindex="1" type="tab" formid="mainForm"
													label="No" checkunsaved="false" cssClass="default" />
											</td>
										</c:if>
										<td>
											<employeeaccess:button id="cancelButton" event="cancel" 
												tabindex="1" type="tab" formid="mainForm"
												label="${cancelLabel}" checkunsaved="false" cssClass="default" 
												popupdispose="true" popupid="warningPopup" springPopup="true" />
										</td>
										<c:if test="${showReportButton}" >
											<td style="padding-left: 40px;">
												<employeeaccess:button href='#' label="Details..." type="tab" 
													id="scheduleButton" tabindex="1" formid="mainForm" ajax="false" 
													checkunsaved="false" onclick="showReport(); return false;" />	
											</td>
										</c:if>
									</tr>
								</table>
							</td>
							<td style="width:50%;"> </td>
						</tr>
					</table>
				</td>
			</tr>
		</table>		
	</form:form>
</div>