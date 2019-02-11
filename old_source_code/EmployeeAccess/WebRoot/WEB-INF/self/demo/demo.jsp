<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ea" uri="http://www.esc20.net/tags/employeeaccess-util"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>


<style type="text/css">

	.hasChange
	{
		background-color: #FFFF9D;
	}
	#content{
		padding: 34px 0 0 0;
	}
</style>

<div style="padding: 10px 10px 10px 10px">
	<tiles:insertAttribute name="error" />
	<c:if test="${demo.fieldDisplayOptionName == 'N' && demo.fieldDisplayOptionMarital == 'N' && demo.fieldDisplayOptionDriversLicense == 'N' 
		&& demo.fieldDisplayOptionRestrictionCodes == 'N' && demo.fieldDisplayOptionEmergencyContact == 'N' && demo.fieldDisplayOptionMailAddr == 'N'
		&& demo.fieldDisplayOptionAltAddr == 'N' && demo.fieldDisplayOptionHomePhone =='N' && demo.fieldDisplayOptionWorkPhone == 'N' 
		&& demo.fieldDisplayOptionEmail == 'N'}">
		<br/>
		<span style="color:red"><ea:label value="The Approver has not been set by the district. Please notify Business office."/></span>
		<br/>
	</c:if>

	<c:if test="${not empty message}">
		<br/>
		<span style="color:red"><ea:label value="${message}"/></span><br/>
	</c:if>
	<form:form id="mainForm" modelAttribute="demo" method="post">
		<table style="margin-top:20px">
			<tr>
				<td style="vertical-align:top">
					<table>
						<tr>
							<td style="padding-bottom:10px;">
								<c:set var="readOnlyName" value="false"/>
								<c:if test="${demo.fieldDisplayOptionName =='I'}"> 
									<c:set var="readOnlyName" value="true"/>
								</c:if>
							<c:if test="${demo.fieldDisplayOptionName !='N'}">
								<div class="groupBox">
									<div class="groupTitle">
										<b>Legal Name</b>
									</div>
									<div class="groupContent" style="width:600px;" >
										<table style="width:100%;overflow:auto" height="115px" class="gridTable">
											<tr>
												<td style="font-weight:bold;text-align:left;border-width:0px 0px 1px 0px"><ea:label value="Description"/></td>
												<td style="font-weight:bold;text-align:left;border-width:0px 0px 1px 0px;width:180px;padding:0px"><ea:label value="Current"/></td>
												<td style="font-weight:bold;text-align:left;border-width:0px 0px 1px 0px;width:180px;padding:0px"><ea:label value="New"/></td>
												<td style="font-weight:bold;text-align:center;border-width:0px 0px 1px 0px;width:95px;padding:0px"><ea:label value="Changes"/></td>
											</tr>
											<tr >
												<td style="border-width:0px;white-space:nowrap;"><ea:label value="Title"/></td>
												<td style="border-width:0px;white-space:nowrap;width:180px;padding:0px"><ea:label value="${demoInfoCurrent.name.titleString}"/></td>
												<td style="border-width:0px;white-space:nowrap;width:180px;padding:0px">
													<c:set var="hasChange" value=""/>
													<c:if test= "${demo.demoInfoChanges.nameTitle}">
														<c:set var="hasChange" value="hasChange"/>
													</c:if>
													<form:select path="demoInfo.name.titleString" items="${titles}" cssClass="text ${hasChange} initial_focus wrap_field" 
																 disabled="${readOnlyName}" htmlEscape="true"  cssStyle="height:22px;width:188px" tabindex="1" id="title" />
												</td>
												<c:if test="${!readOnlyName}">
													<td style="text-align:center;border-width:0px;width:95px;padding:0px">
														<a buttonid="revertNameButton" tabindex="2" class="focusable hover_trigger ignore_changes" onclick="UnsavedDataWarning.disable();$('#' + this.getAttribute('buttonid')).click();" href="#" id="nameButton">Undo</a>
														<input id="revertNameButton" class="hidden" type="submit" name="_eventId_revert_name"/>
													</td>
												</c:if>
											</tr>
											<tr>
												<td style="border-width:0px;white-space:nowrap;"><ea:label value="Last"/></td>
												<td style="border-width:0px;white-space:nowrap;width:180px;padding:0px"><ea:label value="${demoInfoCurrent.name.lastName}"/></td>
												<td style="border-width:0px;white-space:nowrap;width:180px;padding:0px">
													<c:set var="hasChange" value=""/>
													<c:if test= "${demo.demoInfoChanges.nameLast}">
														<c:set var="hasChange" value="hasChange"/>
													</c:if> 
													<form:input path="demoInfo.name.lastName" id="lastName" cssStyle="width:180px" tabindex="1" 
																disabled="${readOnlyName}" cssClass="text ${hasChange}" cssErrorClass="text errorInput"  maxlength="25"/>
												</td>
												<td style="text-align:right;border-width:0px;width:95px;padding:0px"/>
											</tr>
											<tr>
												<td style="border-width:0px;white-space:nowrap;"><ea:label value="First"/></td>
												<td style="border-width:0px;white-space:nowrap;width:180px;padding:0px"><ea:label value="${demoInfoCurrent.name.firstName}"/></td>
												<td style="border-width:0px;white-space:nowrap;width:180px;padding:0px">
													<c:set var="hasChange" value=""/>
													<c:if test= "${demo.demoInfoChanges.nameFirst}">
														<c:set var="hasChange" value="hasChange"/>
													</c:if> 
													<form:input path="demoInfo.name.firstName" id="firstName" cssStyle="width:180px" tabindex="1" 
																disabled="${readOnlyName}" cssClass="text ${hasChange}" cssErrorClass="text errorInput"  maxlength="17"/>
												</td>
												<td style="text-align:right;border-width:0px;width:95px;padding:0px"/>
											</tr>
											<tr>
												<td style="border-width:0px;white-space:nowrap;"><ea:label value="Middle"/></td>
												<td style="border-width:0px;white-space:nowrap;width:180px;padding:0px"><ea:label value="${demoInfoCurrent.name.middleName}"/></td>
												<td style="border-width:0px;white-space:nowrap;width:180px;padding:0px">
													<c:set var="hasChange" value=""/>
													<c:if test= "${demo.demoInfoChanges.nameMiddle}">
														<c:set var="hasChange" value="hasChange"/>
													</c:if> 
													<form:input path="demoInfo.name.middleName" id="middleName" cssStyle="width:180px" tabindex="1" 
																disabled="${readOnlyName}" cssClass="text ${hasChange}" cssErrorClass="text errorInput"  maxlength="14"/>
												</td>
												<td style="text-align:right;border-width:0px;width:95px;padding:0px"/>
											</tr>
											<tr>
												<td style="border-width:0px;white-space:nowrap;"><ea:label value="Generation"/></td>
												<td style="border-width:0px;white-space:nowrap;width:180px;padding:0px"><ea:label value="${demoInfoCurrent.name.generationString}"/></td>
												<td style="border-width:0px;white-space:nowrap;width:180px;padding:0px">
													<c:set var="hasChange" value=""/>
													<c:if test= "${demo.demoInfoChanges.nameGeneration}">
														<c:set var="hasChange" value="hasChange"/>
													</c:if> 
													<form:select path="demoInfo.name.generationString" items="${generations}" disabled="${readOnlyName}" 
																cssClass="text ${hasChange}" htmlEscape="true"  cssStyle="width:188px" tabindex="1" id="generation" />
												</td>
												<td style="text-align:right;border-width:0px;width:95px;padding:0px"/>
											</tr>
										</table>
									</div>
								</div>
							</c:if>							
							</td>
						</tr>
						<tr>
							<td style="padding-bottom:10px;vertical-align:top;">
								<c:set var="readOnlyMarital" value="false"/>
								<c:if test="${demo.fieldDisplayOptionMarital =='I'}"> 
									<c:set var="readOnlyMarital" value="true"/>
								</c:if>
							<c:if test="${demo.fieldDisplayOptionMarital !='N'}">
								<div class="groupBox">
									<div class="groupTitle">
										<b>Marital Status</b>
									</div>
									<div class="groupContent"  style="width:600px;">
										<table style="width:100%;overflow:auto" height="35px" class="gridTable">
											<tr>
												<td style="font-weight:bold;text-align:left;border-width:0px 0px 1px 0px;width:95px;"><ea:label value="Description"/></td>
												<td style="font-weight:bold;text-align:left;border-width:0px 0px 1px 0px;width:180px;padding:0px"><ea:label value="Current"/></td>
												<td style="font-weight:bold;text-align:left;border-width:0px 0px 1px 0px;width:180px;padding:0px"><ea:label value="New"/></td>
												<td style="font-weight:bold;text-align:center;border-width:0px 0px 1px 0px;width:95px;padding:0px"><ea:label value="Changes"/></td>
											</tr>
											<tr>
												<td style="border-width:0px;white-space:nowrap;width:95px;"><ea:label value="Local"/></td>
												<td style="border-width:0px;white-space:nowrap;width:180px;padding:0px"><ea:label value="${demoInfoCurrent.marital.maritalStatusString}"/></td>
												<td style="border-width:0px;white-space:nowrap;width:180px;padding:0px">
													<c:set var="hasChange" value=""/>
													<c:if test= "${demo.demoInfoChanges.maritalLocal}">
														<c:set var="hasChange" value="hasChange"/>
 													</c:if> 
													<form:select path="demoInfo.marital.maritalStatusString" items="${maritalStatuses}" disabled="${readOnlyMarital}" 
																cssClass="text ${hasChange}" htmlEscape="true"  cssStyle="width:188px" tabindex="3" id="maritalStatus" />
												</td>
												<c:if test="${!readOnlyMarital}">
													<td style="text-align:center;border-width:0px;width:95px;padding:0px">
														<a buttonid="revertMaritalButton" tabindex="4" class="focusable hover_trigger ignore_changes" onclick="UnsavedDataWarning.disable();$('#' + this.getAttribute('buttonid')).click();" href="#" id="maritalButton">Undo</a>
														<input id="revertMaritalButton" class="hidden" type="submit" name="_eventId_revert_marital"/>
													</td>
												</c:if>
											</tr>
										</table>
									</div>
								</div>
							</c:if>								
							</td>
						</tr>
						<tr>
							<td style="padding-bottom:10px;">
								<c:set var="readOnlyDriversLicense" value="false"/>
								<c:if test="${demo.fieldDisplayOptionDriversLicense =='I'}"> 
									<c:set var="readOnlyDriversLicense" value="true"/>
								</c:if>
							<c:if test="${demo.fieldDisplayOptionDriversLicense !='N'}">
								<div class="groupBox">
									<div class="groupTitle">
										<b>Driver's License</b>
									</div>
									<div class="groupContent"  style="width:600px;">
										<table style="width:100%;overflow:auto" height="50px" class="gridTable">
											<tr>
												<td style="font-weight:bold;text-align:left;border-width:0px 0px 1px 0px;"><ea:label value="Description"/></td>
												<td style="font-weight:bold;text-align:left;border-width:0px 0px 1px 0px;width:180px;padding:0px"><ea:label value="Current"/></td>
												<td style="font-weight:bold;text-align:left;border-width:0px 0px 1px 0px;width:180px;padding:0px"><ea:label value="New"/></td>
												<td style="font-weight:bold;text-align:center;border-width:0px 0px 1px 0px;width:95px;padding:0px"><ea:label value="Changes"/></td>
											</tr>
											<tr>
												<td style="border-width:0px;white-space:nowrap;"><ea:label value="Number"/></td>
												<td style="border-width:0px;white-space:nowrap;width:180px;padding:0px"><ea:label value="${demoInfoCurrent.driversLicense.number}"/></td>
												<td style="border-width:0px;white-space:nowrap;width:180px;padding:0px">
													<c:set var="hasChange" value=""/>
													<c:if test= "${demo.demoInfoChanges.driversNum}">
														<c:set var="hasChange" value="hasChange"/>
													</c:if> 
													<form:input path="demoInfo.driversLicense.number" id="driversLicenseNumber" cssStyle="width:180px" tabindex="5" 
																disabled="${readOnlyDriversLicense}" cssClass="text ${hasChange}" cssErrorClass="text errorInput" maxlength="19"/>
												</td>
												<c:if test="${!readOnlyDriversLicense}">
													<td style="text-align:center;border-width:0px;width:95px;padding:0px"">
														<a buttonid="revertDriversLicenseButton" tabindex="6" class="focusable hover_trigger ignore_changes" onclick="UnsavedDataWarning.disable();$('#' + this.getAttribute('buttonid')).click();" href="#" id="driversLicenseButton">Undo</a>
														<input id="revertDriversLicenseButton" class="hidden" type="submit" name="_eventId_revert_drivers_license"/>
													</td>
												</c:if>
											</tr>
											<tr>
												<td style="border-width:0px;white-space:nowrap;"><ea:label value="State"/></td>
												<td style="border-width:0px;white-space:nowrap;width:180px;padding:0px"><ea:label value="${demoInfoCurrent.driversLicense.stateString}"/></td>
												<td style="border-width:0px;white-space:nowrap;width:180px;padding:0px">
													<c:set var="hasChange" value=""/>
													<c:if test= "${demo.demoInfoChanges.driversState}">
														<c:set var="hasChange" value="hasChange"/>
													</c:if>
													<form:select path="demoInfo.driversLicense.stateString" items="${states}" disabled="${readOnlyDriversLicense}"
																cssClass="text ${hasChange}" htmlEscape="true"  cssStyle="width:188px" tabindex="5" id="driversState" />
												</td>
												<td style="text-align:right;border-width:0px;width:95px;padding:0px""/>
											</tr>
										</table>
									</div>
								</div>
							</c:if>
							</td>
						</tr>	
						<tr>
							<td style="padding-bottom:10px;""> 
								<c:set var="readOnlyRestriction" value="false"/>
								<c:if test="${demo.fieldDisplayOptionRestrictionCodes =='I'}"> 
									<c:set var="readOnlyRestriction" value="true"/>
								</c:if>
							<c:if test="${demo.fieldDisplayOptionRestrictionCodes !='N'}">								
								<div class="groupBox">
									<div class="groupTitle">
										<b>Restriction Codes</b>
									</div>
									<div class="groupContent" style="width:600px;" >
										<table style="width:100%;" height="50px" class="gridTable">
											<tr>
												<td style="font-weight:bold;text-align:left;border-width:0px 0px 1px 0px;width:95px;"><ea:label value="Description"/></td>
												<td style="font-weight:bold;text-align:left;border-width:0px 0px 1px 0px;width:180px;padding:0px"><ea:label value="Current"/></td>
												<td style="font-weight:bold;text-align:left;border-width:0px 0px 1px 0px;width:180px;padding:0px"><ea:label value="New"/></td>
												<td style="font-weight:bold;text-align:center;border-width:0px 0px 1px 0px;width:95px;padding:0px"><ea:label value="Changes"/></td>
											</tr>
											<tr>
												<td style="border-width:0px;white-space:nowrap;width:95px;"><ea:label value="Local"/></td>
												<td style="border-width:0px;white-space:nowrap;width:180px;padding:0px"><ea:label value="${demoInfoCurrent.restrictionCodes.localRestrictionString}"/></td>
												<td style="border-width:0px;width:180px;padding:0px">
													<c:set var="hasChange" value=""/>
													<c:if test= "${demo.demoInfoChanges.restrictionLocal}">
														<c:set var="hasChange" value="hasChange"/>
													</c:if>
													<form:select path="demoInfo.restrictionCodes.localRestrictionString" items="${restrictionCodes}" disabled="${readOnlyRestriction}" 
																cssClass="text ${hasChange}" htmlEscape="true"  cssStyle="width:188px" tabindex="7" id="restrictionCodesLocalRestriction" />
												</td>
												<c:if test="${!readOnlyRestriction}"> 
													<td style="text-align:center;border-width:0px;width:95px;padding:0px">
														<a buttonid="revertRestrictionCodesButton" tabindex="8" class="focusable hover_trigger ignore_changes" onclick="UnsavedDataWarning.disable();$('#' + this.getAttribute('buttonid')).click();" href="#" id="restrictionCodesButton">Undo</a>
														<input id="revertRestrictionCodesButton" class="hidden" type="submit" name="_eventId_revert_restriction_codes"/>
													</td>
												</c:if>
											</tr>
											<tr>
												<td style="border-width:0px;hite-space:nowrap;"><ea:label value="Public"/></td>
												<td style="border-width:0px;white-space:nowrap;width:180px;padding:0px"><ea:label value="${demoInfoCurrent.restrictionCodes.publicRestrictionString}"/></td>
												<td style="border-width:0px;white-space:nowrap;width:180px;padding:0px">
													<c:set var="hasChange" value=""/>
													<c:if test= "${demo.demoInfoChanges.restrictionPublic}">
														<c:set var="hasChange" value="hasChange"/>
													</c:if>
													<form:select path="demoInfo.restrictionCodes.publicRestrictionString" items="${restrictionCodes}" disabled="${readOnlyRestriction}" 
																cssClass="text ${hasChange}" htmlEscape="true"  cssStyle="width:188px" tabindex="7" id="restrictionCodesPublicRestriction" />
												</td>
												<td style="border-width:0px;width:95px;padding:0px"/>
											</tr>
										</table>
									</div>
								</div>
							</c:if>								
							</td>
						</tr>
						<tr>
							<td style="padding-bottom:10px;"> 
								<c:set var="readOnlyEmergency" value="false"/>
								<c:if test="${demo.fieldDisplayOptionEmergencyContact =='I'}"> 
									<c:set var="readOnlyEmergency" value="true"/>
								</c:if>
							<c:if test="${demo.fieldDisplayOptionEmergencyContact !='N'}">								
								<div class="groupBox" >
									<div class="groupTitle" >
										<b>Emergency Contact Information</b>
									</div>
									<div class="groupContent"  style="width:600px;" >
										<table style="width:100%;overflow:auto" height="100px" class="gridTable">
											<tr>
												<td style="font-weight:bold;text-align:left;border-width:0px 0px 1px 0px"><ea:label value="Description"/></td>
												<td style="font-weight:bold;text-align:left;border-width:0px 0px 1px 0px;width:180px;padding:0px"><ea:label value="Current"/></td>
												<td style="font-weight:bold;text-align:left;border-width:0px 0px 1px 0px;width:180px;padding:0px"><ea:label value="New"/></td>
												<td style="font-weight:bold;text-align:center;border-width:0px 0px 1px 0px;width:95px;padding:0px"><ea:label value="Changes"/></td>
											</tr>
											<tr>
												<td style="border-width:0px;white-space:nowrap;"><ea:label value="Name"/></td>
												<td style="border-width:0px;white-space:nowrap;width:180px;padding:0px"><ea:label value="${demoInfoCurrent.emergencyContact.name}"/></td>
												<td style="border-width:0px;white-space:nowrap;width:180px;padding:0px">
													<c:set var="hasChange" value=""/>
													<c:if test= "${demo.demoInfoChanges.emergencyName}">
														<c:set var="hasChange" value="hasChange"/>
													</c:if> 
													<form:input path="demoInfo.emergencyContact.name" id="emergencyContactName" disabled="${readOnlyEmergency}" tabindex="9" 
																cssStyle="width:180px" cssClass="text ${hasChange}" cssErrorClass="text errorInput" maxlength="26"/>
												</td>
												<c:if test="${!readOnlyEmergency}"> 
													<td style="border-width:0px;width:95px;padding:0px;text-align:center">
														<a buttonid="revertEmergencyContactButton" tabindex="10" class="focusable hover_trigger ignore_changes" onclick="UnsavedDataWarning.disable();$('#' + this.getAttribute('buttonid')).click();" href="#" id="emergencyContactButton">Undo</a>
														<input id="revertEmergencyContactButton" class="hidden" type="submit" name="_eventId_revert_emergency_contact"/>
													</td>
												</c:if>
											</tr>
											<tr>
												<td style="border-width:0px;white-space:nowrap;"><ea:label value="Phone Number"/></td>
												<td style="border-width:0px;white-space:nowrap;width:180px;padding:0px">
													<ea:label value="(${demoInfoCurrent.emergencyContact.areaCode}) "/>
													<ea:label value="${demoInfoCurrent.emergencyContact.phoneNumberFormatted}"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													<ea:label value="ext. ${demoInfoCurrent.emergencyContact.extention}"/>
												</td>
												<td style="border-width:0px;white-space:nowrap;width:180px;padding:0px">
													<c:set var="hasChange" value=""/>
													<c:if test= "${demo.demoInfoChanges.emergencyAreaCode}">
														<c:set var="hasChange" value="hasChange"/>
													</c:if> 
													<form:input path="demoInfo.emergencyContact.areaCode" id="emergencyContactAreaCode" disabled="${readOnlyEmergency}" tabindex="9" 
																cssStyle="width:30px" cssClass="text ${hasChange}" cssErrorClass="text errorInput" maxlength="3"/>
													<c:set var="hasChange" value=""/>
													<c:if test= "${demo.demoInfoChanges.emergencyPhoneNum}">
														<c:set var="hasChange" value="hasChange"/>
													</c:if> 
													<form:input path="demoInfo.emergencyContact.phoneNumberFormatted" id="emergencyContactPhoneNumber" disabled="${readOnlyEmergency}" 
																cssStyle="width:60px" cssClass="text ${hasChange}" cssErrorClass="text errorInput" maxlength="8" tabindex="9" />
													<c:set var="hasChange" value=""/>
													<c:if test="${demo.demoInfoChanges.emergencyPhoneExt}">
														<c:set var="hasChange" value="hasChange"/>
													</c:if> 
													&nbsp;&nbsp;ext.
													<form:input path="demoInfo.emergencyContact.extention" id="emergencyContactExtention" disabled="${readOnlyEmergency}"
																cssStyle="width:40px" cssClass="text ${hasChange}" cssErrorClass="text errorInput" maxlength="4" tabindex="9" />
												</td>
												<td style="border-width:0px;width:95px;padding:0px"/>
											</tr>
											<tr>
												<td style="border-width:0px;white-space:nowrap;"><ea:label value="Relationship"/></td>
												<td style="border-width:0px;white-space:nowrap;width:180px;padding:0px"><ea:label value="${demoInfoCurrent.emergencyContact.relationship}"/></td>
												<td style="border-width:0px;white-space:nowrap;width:180px;padding:0px">
													<c:set var="hasChange" value=""/>
													<c:if test= "${demo.demoInfoChanges.emergencyRelationship}">
														<c:set var="hasChange" value="hasChange"/>
													</c:if> 
													<form:input path="demoInfo.emergencyContact.relationship" id="emergencyContactRelationship" disabled="${readOnlyEmergency}" 
																cssStyle="width:180px" cssClass="text ${hasChange}" cssErrorClass="text errorInput" maxlength="25" tabindex="9" />
												</td>
												<td style="border-width:0px;width:95px;padding:0px"/>
											</tr>
											<tr>
												<td style="border-width:0px; white-space:nowrap;"><ea:label value="Emergency Notes"/></td>
												<td style="border-width:0px;white-space:nowrap;width:180px;padding:0px"><ea:label value="${demoInfoCurrent.emergencyContact.emergencyNotes}"/></td>
												<td style="border-width:0px;width:180px;padding:0px">
													<c:set var="hasChange" value=""/>
													<c:if test= "${demo.demoInfoChanges.emergencyNotes}">
														<c:set var="hasChange" value="hasChange"/>
													</c:if> 
													<form:input path="demoInfo.emergencyContact.emergencyNotes" id="emergencyContactEmergencyNotes" disabled="${readOnlyEmergency}" 
																cssStyle="width:180px" cssClass="text ${hasChange}" cssErrorClass="text errorInput" maxlength="25" tabindex="9" />
												</td>
												<td style="border-width:0px;width:95px;padding:0px"/>
											</tr>
										</table>
									</div>
								</div>
							</c:if>								
							</td>
						</tr>
					</table>
				</td>
				<td style="padding-left:10px; padding-top:0px; vertical-align:top">
					<table>
						<tr>
							<td style="padding-bottom:15px;">
								<c:set var="readOnlyMailAddr" value="false"/>
								<c:if test="${demo.fieldDisplayOptionMailAddr =='I'}"> 
									<c:set var="readOnlyMailAddr" value="true"/>
								</c:if>
							<c:if test="${demo.fieldDisplayOptionMailAddr !='N'}">
								<div class="groupBox">
									<div class="groupTitle">
										<b>Mailing Address</b>
									</div>
									<div class="groupContent"  style="width:600px;">
										<table style="width:100%;overflow:auto" height="165px" class="gridTable">
											<tr>
												<td style="font-weight:bold;text-align:left;border-width:0px 0px 1px 0px"><ea:label value="Description"/></td>
												<td style="font-weight:bold;text-align:left;border-width:0px 0px 1px 0px;width:180px;padding:0px"><ea:label value="Current"/></td>
												<td style="font-weight:bold;text-align:left;border-width:0px 0px 1px 0px;width:180px;padding:0px"><ea:label value="New"/></td>
												<td style="font-weight:bold;text-align:center;border-width:0px 0px 1px 0px;width:95px;padding:0px"><ea:label value="Changes"/></td>
											</tr>											
											<tr>
												<td style="border-width:0px;white-space:nowrap;"><ea:label value="Number"/></td>
												<td style="border-width:0px;white-space:nowrap;width:180px;padding:0px"><ea:label value="${demoInfoCurrent.mailAddr.number}"/></td>
												<td style="border-width:0px;width:95px;padding:0px;width:180px;padding:0px">
													<c:set var="hasChange" value=""/>
													<c:if test= "${demo.demoInfoChanges.mailingAddress}">
														<c:set var="hasChange" value="hasChange"/>
													</c:if> 
													<form:input path="demoInfo.mailAddr.number" id="mailAddrNumber" cssStyle="width:180px" disabled="${readOnlyMailAddr}" 
																cssClass="text ${hasChange}" cssErrorClass="text errorInput"  maxlength="8" tabindex="11" />
												</td>
												<c:if test="${!readOnlyMailAddr}"> 
													<td style="border-width:0px;text-align:center">
														<a buttonid="revertMailAddrButton" tabindex="12" class="focusable hover_trigger ignore_changes" onclick="UnsavedDataWarning.disable();$('#' + this.getAttribute('buttonid')).click();" href="#" id="mailAddrButton">Undo</a>
														<input id="revertMailAddrButton" class="hidden" type="submit" name="_eventId_revert_mail_addr"/>
													</td>
												</c:if>
											</tr>
											<tr>
												<td style="border-width:0px; white-space:nowrap;"><ea:label value="Street/P.O. Box"/></td>
												<td style="border-width:0px;white-space:nowrap;width:180px;padding:0px"><ea:label value="${demoInfoCurrent.mailAddr.street}"/></td>
												<td style="border-width:0px;width:95px;padding:0px;width:180px;padding:0px">
													<c:set var="hasChange" value=""/>
													<c:if test= "${demo.demoInfoChanges.mailingPoBox}">
														<c:set var="hasChange" value="hasChange"/>
													</c:if> 
													<form:input path="demoInfo.mailAddr.street" id="mailAddrNumber" cssStyle="width:180px" disabled="${readOnlyMailAddr}" 
																cssClass="text ${hasChange}" cssErrorClass="text errorInput"  maxlength="20" tabindex="11" />
												</td>
												<td style="border-width:0px;width:95px;padding:0px"/>
											</tr>
											<tr>
												<td style="border-width:0px;white-space:nowrap;"><ea:label value="Apt"/></td>
												<td style="border-width:0px;white-space:nowrap;width:180px;padding:0px"><ea:label value="${demoInfoCurrent.mailAddr.apartment}"/></td>
												<td style="border-width:0px;white-space:nowrap;width:180px;padding:0px">
													<c:set var="hasChange" value=""/>
													<c:if test= "${demo.demoInfoChanges.mailingApt}">
														<c:set var="hasChange" value="hasChange"/>
													</c:if> 
													<form:input path="demoInfo.mailAddr.apartment" id="mailAddrApartment" cssStyle="width:180px" disabled="${readOnlyMailAddr}" 
																cssClass="text ${hasChange}" cssErrorClass="text errorInput"  maxlength="7" tabindex="11" />
												</td>
												<td style="border-width:0px;width:95px;padding:0px"/>
											</tr>
											<tr>
												<td style="border-width:0px;"><ea:label value="City"/></td>
												<td style="border-width:0px;white-space:nowrap;width:180px;padding:0px"><ea:label value="${demoInfoCurrent.mailAddr.city}"/></td>
												<td style="border-width:0px;width:180px;padding:0px">
													<c:set var="hasChange" value=""/>
													<c:if test= "${demo.demoInfoChanges.mailingCity}">
														<c:set var="hasChange" value="hasChange"/>
													</c:if> 
													<form:input path="demoInfo.mailAddr.city" id="mailAddrCity" cssStyle="width:180px" disabled="${readOnlyMailAddr}" 
																cssClass="text ${hasChange}" cssErrorClass="text errorInput"  maxlength="17" tabindex="11" />
												</td>
												<td style="border-width:0px;width:95px;padding:0px"/>
											</tr>
											<tr>
												<td style="border-width:0px;white-space:nowrap;"><ea:label value="State"/></td>
												<td style="border-width:0px;white-space:nowrap;width:180px;padding:0px"><ea:label value="${demoInfoCurrent.mailAddr.stateString}"/></td>
												<td style="border-width:0px;white-space:nowrap;width:180px;padding:0px">
													<c:set var="hasChange" value=""/>
													<c:if test= "${demo.demoInfoChanges.mailingState}">
														<c:set var="hasChange" value="hasChange"/>
													</c:if> 
													<form:select path="demoInfo.mailAddr.stateString" items="${states}" disabled="${readOnlyMailAddr}"
																cssClass="text ${hasChange}" htmlEscape="true"  cssStyle="width:188px" 
																tabindex="11" id="mailAddrState" />
												</td>
												<td style="border-width:0px;width:95px;padding:0px"/>
											</tr>
											<tr>
												<td style="border-width:0px;white-space:nowrap;"><ea:label value="Zip"/></td>
												<td style="border-width:0px;white-space:nowrap;width:180px;padding:0px"><ea:label value="${demoInfoCurrent.mailAddr.zip}"/></td>
												<td style="border-width:0px;white-space:nowrap;width:180px;padding:0px">
													<c:set var="hasChange" value=""/>
													<c:if test= "${demo.demoInfoChanges.mailingZip}">
														<c:set var="hasChange" value="hasChange"/>
													</c:if> 
													<form:input path="demoInfo.mailAddr.zip" id="mailAddrZip" cssStyle="width:180px" disabled="${readOnlyMailAddr}" 
																cssClass="text ${hasChange}" cssErrorClass="text errorInput"  maxlength="5" tabindex="11" />
												</td>
												<td style="border-width:0px;width:95px;padding:0px"/>
											</tr>
											<tr>
												<td style="border-width:0px;white-space:nowrap;"><ea:label value="Zip+4"/></td>
												<td style="border-width:0px;white-space:nowrap;width:180px;padding:0px"><ea:label value="${demoInfoCurrent.mailAddr.zipPlusFour}"/></td>
												<td style="border-width:0px;white-space:nowrap;width:180px;padding:0px">
													<c:set var="hasChange" value=""/>
													<c:if test= "${demo.demoInfoChanges.mailingZip4}">
														<c:set var="hasChange" value="hasChange"/>
													</c:if> 
													<form:input path="demoInfo.mailAddr.zipPlusFour" id="mailAddrZipPlusFour" disabled="${readOnlyMailAddr}" tabindex="11" 
																cssStyle="width:180px" cssClass="text ${hasChange}" cssErrorClass="text errorInput"  maxlength="4"/>
												</td>
												<td style="border-width:0px;width:95px;padding:0px"/>
											</tr>
										</table>
									</div>
								</div>
							</c:if>								
							</td>
						</tr><tr>
							<td style="padding-bottom:15px;"> 
								<c:set var="readOnlyAltAddr" value="false"/>
								<c:if test="${demo.fieldDisplayOptionAltAddr =='I'}"> 
									<c:set var="readOnlyAltAddr" value="true"/>
								</c:if>
							<c:if test="${demo.fieldDisplayOptionAltAddr !='N'}">
								<div class="groupBox">
									<div class="groupTitle">
										<b>Alternate Address</b>
									</div>
									<div class="groupContent"  style="width:600px;">
										<table style="width:100%;overflow:auto" height="165px" class="gridTable">
											<tr>
												<td style="font-weight:bold;text-align:left;border-width:0px 0px 1px 0px"><ea:label value="Description"/></td>
												<td style="font-weight:bold;text-align:left;border-width:0px 0px 1px 0px;width:180px;padding:0px"><ea:label value="Current"/></td>
												<td style="font-weight:bold;text-align:left;border-width:0px 0px 1px 0px;width:180px;padding:0px"><ea:label value="New"/></td>
												<td style="font-weight:bold;text-align:center;border-width:0px 0px 1px 0px;width:95px;padding:0px"><ea:label value="Changes"/></td>
											</tr>	
											<tr>
												<td style="border-width:0px;white-space:nowrap;"><ea:label value="Number"/></td>
												<td style="border-width:0px;white-space:nowrap;width:180px;padding:0px"><ea:label value="${demoInfoCurrent.altAddr.number}"/></td>
												<td style="border-width:0px;white-space:nowrap;width:180px;padding:0px">
													<c:set var="hasChange" value=""/>
													<c:if test= "${demo.demoInfoChanges.alternateAddress}">
														<c:set var="hasChange" value="hasChange"/>
													</c:if> 
													<form:input path="demoInfo.altAddr.number" id="altAddrNumber" cssStyle="width:180px" disabled="${readOnlyAltAddr}"
																cssClass="text ${hasChange}" cssErrorClass="text errorInput"  maxlength="8" tabindex="13" />
												</td>
												<c:if test="${!readOnlyAltAddr}"> 
													<td style="border-width:0px;text-align:center;width:95px;padding:0px">
														<a buttonid="revertAltAddrButton" tabindex="14" class="focusable hover_trigger ignore_changes" onclick="UnsavedDataWarning.disable();$('#' + this.getAttribute('buttonid')).click();" href="#" id="altAddrButton">Undo</a>
														<input id="revertAltAddrButton" class="hidden" type="submit" name="_eventId_revert_alt_addr"/>
													</td>
												</c:if>
											</tr>
											<tr>
												<td style="border-width:0px;;white-space:nowrap;"><ea:label value="Street/P.O. Box"/></td>
												<td style="border-width:0px;white-space:nowrap;width:180px;padding:0px"><ea:label value="${demoInfoCurrent.altAddr.street}"/></td>
												<td style="border-width:0px;white-space:nowrap;width:180px;padding:0px">
													<c:set var="hasChange" value=""/>
													<c:if test= "${demo.demoInfoChanges.alternatePoBox}">
														<c:set var="hasChange" value="hasChange"/>
													</c:if> 
													<form:input path="demoInfo.altAddr.street" id="altAddrNumber" cssStyle="width:180px" disabled="${readOnlyAltAddr}" 
																cssClass="text ${hasChange}" cssErrorClass="text errorInput"  maxlength="20" tabindex="13" />
												</td>
												<td style="border-width:0px;width:95px;padding:0px"/>
											</tr>
											<tr>
												<td style="border-width:0px;;white-space:nowrap;"><ea:label value="Apt"/></td>
												<td style="border-width:0px;white-space:nowrap;width:180px;padding:0px"><ea:label value="${demoInfoCurrent.altAddr.apartment}"/></td>
												<td style="border-width:0px;white-space:nowrap;width:180px;padding:0px">
													<c:set var="hasChange" value=""/>
													<c:if test= "${demo.demoInfoChanges.alternateApt}">
														<c:set var="hasChange" value="hasChange"/>
													</c:if> 
													<form:input path="demoInfo.altAddr.apartment" id="altAddrApartment" cssStyle="width:180px" disabled="${readOnlyAltAddr}" 
																cssClass="text ${hasChange}" cssErrorClass="text errorInput" maxlength="7" tabindex="13" />
												</td>
												<td style="border-width:0px;width:95px;padding:0px"/>
											</tr>
											<tr>
												<td style="border-width:0px;;white-space:nowrap;"><ea:label value="City"/></td>
												<td style="border-width:0px;white-space:nowrap;width:180px;padding:0px"><ea:label value="${demoInfoCurrent.altAddr.city}"/></td>
												<td style="border-width:0px;white-space:nowrap;width:180px;padding:0px">
													<c:set var="hasChange" value=""/>
													<c:if test= "${demo.demoInfoChanges.alternateCity}">
														<c:set var="hasChange" value="hasChange"/>
													</c:if> 
													<form:input path="demoInfo.altAddr.city" id="altAddrCity" cssStyle="width:180px" disabled="${readOnlyAltAddr}" 
																cssClass="text ${hasChange}" cssErrorClass="text errorInput"  maxlength="17" tabindex="13" />
												</td>
												<td style="border-width:0px;width:95px;padding:0px"/>
											</tr>
											<tr>
												<td style="border-width:0px;;white-space:nowrap;"><ea:label value="State"/></td>
												<td style="border-width:0px;white-space:nowrap;width:180px;padding:0px"><ea:label value="${demoInfoCurrent.altAddr.stateString}"/></td>
												<td style="border-width:0px;white-space:nowrap;width:180px;padding:0px">
													<c:set var="hasChange" value=""/>
													<c:if test= "${demo.demoInfoChanges.alternateState}">
														<c:set var="hasChange" value="hasChange"/>
													</c:if> 
													<form:select path="demoInfo.altAddr.stateString" items="${states}" disabled="${readOnlyAltAddr}"
																cssClass="text ${hasChange}" htmlEscape="true"  cssStyle="width:188px" tabindex="13" id="altAddrState" />
												</td>
												<td style="border-width:0px;width:95px;padding:0px"/>
											</tr>
											<tr>
												<td style="border-width:0px;;white-space:nowrap;"><ea:label value="Zip"/></td>
												<td style="border-width:0px;white-space:nowrap;width:180px;padding:0px"><ea:label value="${demoInfoCurrent.altAddr.zip}"/></td>
												<td style="border-width:0px;white-space:nowrap;width:180px;padding:0px">
													<c:set var="hasChange" value=""/>
													<c:if test= "${demo.demoInfoChanges.alternateZip}">
														<c:set var="hasChange" value="hasChange"/>
													</c:if> 
													<form:input path="demoInfo.altAddr.zip" id="altAddrZip" cssStyle="width:180px" disabled="${readOnlyAltAddr}" 
																cssClass="text ${hasChange}" cssErrorClass="text errorInput"  maxlength="5" tabindex="13" />
												</td>
												<td style="border-width:0px;width:95px;padding:0px"/>
											</tr>
											<tr>
												<td style="border-width:0px;;white-space:nowrap;"><ea:label value="Zip+4"/></td>
												<td style="border-width:0px;white-space:nowrap;width:180px;padding:0px"><ea:label value="${demoInfoCurrent.altAddr.zipPlusFour}"/></td>
												<td style="border-width:0px;white-space:nowrap;width:180px;padding:0px">
													<c:set var="hasChange" value=""/>
													<c:if test= "${demo.demoInfoChanges.alternateZip4}">
														<c:set var="hasChange" value="hasChange"/>
													</c:if> 
													<form:input path="demoInfo.altAddr.zipPlusFour" id="altAddrZipPlusFour" disabled="${readOnlyAltAddr}" tabindex="13" 
																cssStyle="width:180px" cssClass="text ${hasChange}" cssErrorClass="text errorInput"  maxlength="4"/>
												</td>
												<td style="border-width:0px;width:95px;padding:0px"/>
											</tr>
										</table>
									</div>
								</div>
							</c:if>
							</td>
						</tr>
						<tr>
							<td style="padding-bottom:10px;">
								<c:set var="readOnlyHomePhone" value="false"/>
								<c:if test="${demo.fieldDisplayOptionHomePhone == 'I'}"> 
									<c:set var="readOnlyHomePhone" value="true"/>
								</c:if>
								<c:set var="readOnlyWorkPhone" value="false"/>
								<c:if test="${demo.fieldDisplayOptionWorkPhone == 'I'}"> 
									<c:set var="readOnlyWorkPhone" value="true"/>
								</c:if>
								<c:set var="readOnlyCellPhone" value="false"/>
								<c:if test="${demo.fieldDisplayOptionCellPhone == 'I'}"> 
									<c:set var="readOnlyCellPhone" value="true"/>
								</c:if>
							<c:if test="${demo.fieldDisplayOptionHomePhone !='N' || demo.fieldDisplayOptionWorkPhone != 'N' || demo.fieldDisplayOptionWorkPhone != 'N'}">
								<div class="groupBox">
									<div class="groupTitle">
										<b>Phone Numbers</b>
									</div>
									<div class="groupContent"  style="width:600px;" >
										<table style="width:100%;overflow:auto" height="85px" class="gridTable">
											<tr>
												<td style="font-weight:bold;text-align:left;border-width:0px 0px 1px 0px"><ea:label value="Description"/></td>
												<td style="font-weight:bold;text-align:left;border-width:0px 0px 1px 0px;width:180px;padding:0px"><ea:label value="Current"/></td>
												<td style="font-weight:bold;text-align:left;border-width:0px 0px 1px 0px;width:180px;padding:0px"><ea:label value="New"/></td>
												<td style="font-weight:bold;text-align:center;border-width:0px 0px 1px 0px;width:95px;padding:0px"><ea:label value="Changes"/></td>
											</tr>
											<c:if test="${demo.fieldDisplayOptionHomePhone !='N'}">
												<tr>
													<td style="border-width:0px;;white-space:nowrap;"><ea:label value="Home"/></td>
													<td style="border-width:0px;white-space:nowrap;width:180px;padding:0px">
														<ea:label value="(${demoInfoCurrent.homePhone.areaCode}) "/> <ea:label value="${demoInfoCurrent.homePhone.phoneNumberFormatted}"/>
													</td>
													<td style="border-width:0px;white-space:nowrap;width:180px;padding:0px">
														<c:set var="hasChange" value=""/>
														<c:if test= "${demo.demoInfoChanges.phoneHomeArea}">
															<c:set var="hasChange" value="hasChange"/>
														</c:if> 
														<form:input path="demoInfo.homePhone.areaCode" id="homePhoneAreaCode" disabled="${readOnlyHomePhone}" tabindex="15" 
																	cssStyle="width:30px" cssClass="text ${hasChange}" cssErrorClass="text errorInput"  maxlength="3"/>
														<c:set var="hasChange" value=""/>
														<c:if test= "${demo.demoInfoChanges.phoneHomeNum}">
															<c:set var="hasChange" value="hasChange"/>
														</c:if> 
														<form:input path="demoInfo.homePhone.phoneNumberFormatted" id="homePhonePhoneNumber"  disabled="${readOnlyHomePhone}" tabindex="15" 
																	cssStyle="width:60px" cssClass="text ${hasChange}" cssErrorClass="text errorInput"  maxlength="7"/>
													</td>
													<c:if test="${!readOnlyHomePhone}"> 
														<td style="border-width:0px;width:95px;padding:0px;text-align:center">
															<a buttonid="revertHomePhoneButton" tabindex="16" class="focusable hover_trigger ignore_changes" onclick="UnsavedDataWarning.disable();$('#' + this.getAttribute('buttonid')).click();" href="#" id="homePhoneButton">Undo</a>
															<input id="revertHomePhoneButton" class="hidden" type="submit" name="_eventId_revert_home_phone"/>
														</td>
													</c:if>
												</tr>
											</c:if>
											<c:if test="${demo.fieldDisplayOptionCellPhone !='N'}">
												<tr>
													<td style="border-width:0px;;white-space:nowrap;"><ea:label value="Cell"/></td>
													<td style="border-width:0px;white-space:nowrap;width:180px;padding:0px">
														<ea:label value="(${demoInfoCurrent.cellPhone.areaCode}) "/>
														<ea:label value="${demoInfoCurrent.cellPhone.phoneNumberFormatted}"/>
													</td>
													<td style="border-width:0px;white-space:nowrap;width:180px;padding:0px">
														<c:set var="hasChange" value=""/>
														<c:if test= "${demo.demoInfoChanges.phoneCellArea}">
															<c:set var="hasChange" value="hasChange"/>
														</c:if> 
														<form:input path="demoInfo.cellPhone.areaCode" id="cellPhoneAreaCode" disabled="${readOnlyCellPhone}" tabindex="17" 
																	cssStyle="width:30px" cssClass="text ${hasChange}" cssErrorClass="text errorInput"  maxlength="3"/>
														<c:set var="hasChange" value=""/>
														<c:if test= "${demo.demoInfoChanges.phoneCellNum}">
															<c:set var="hasChange" value="hasChange"/>
														</c:if> 
														<form:input path="demoInfo.cellPhone.phoneNumberFormatted" id="cellPhonePhoneNumber" disabled="${readOnlyCellPhone}" tabindex="17" 
																	cssStyle="width:60px" cssClass="text ${hasChange}" cssErrorClass="text errorInput"  maxlength="7"/>
													</td>
													<c:if test="${!readOnlyCellPhone}"> 
														<td style="border-width:0px;width:95px;padding:0px;text-align:center">
															<a buttonid="revertCellPhoneButton" tabindex="18" class="focusable hover_trigger ignore_changes" onclick="UnsavedDataWarning.disable();$('#' + this.getAttribute('buttonid')).click();" href="#" id="cellPhoneButton">Undo</a>
															<input id="revertCellPhoneButton" class="hidden" type="submit" name="_eventId_revert_cell_phone"/>
														</td>
													</c:if>
													
												</tr>
											</c:if>
											<c:if test="${demo.fieldDisplayOptionWorkPhone !='N'}">
												<tr>
													<td style="border-width:0px;;white-space:nowrap;"><ea:label value="Business"/></td>
													<td style="border-width:0px;white-space:nowrap;width:180px;padding:0px">
														<ea:label value="(${demoInfoCurrent.workPhone.areaCode})"/> 
														<ea:label value="${demoInfoCurrent.workPhone.phoneNumberFormatted}"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
														<ea:label value="ext. ${demoInfoCurrent.workPhone.extention}"/>
													</td>
													<td style="border-width:0px;white-space:nowrap;width:180px;padding:0px">
														<c:set var="hasChange" value=""/>
														<c:if test= "${demo.demoInfoChanges.phoneBusArea}">
															<c:set var="hasChange" value="hasChange"/>
														</c:if> 
														<form:input path="demoInfo.workPhone.areaCode" id="workPhoneAreaCode" disabled="${readOnlyWorkPhone}" tabindex="19" 
																	cssStyle="width:30px" cssClass="text ${hasChange}" cssErrorClass="text errorInput"  maxlength="3"/>
														
														<c:set var="hasChange" value=""/>
														<c:if test= "${demo.demoInfoChanges.phoneBusNum}">
															<c:set var="hasChange" value="hasChange"/>
														</c:if> 
														<form:input path="demoInfo.workPhone.phoneNumberFormatted" id="workPhonePhoneNumber" disabled="${readOnlyWorkPhone}" tabindex="19" 
																	cssStyle="width:60px" cssClass="text ${hasChange}" cssErrorClass="text errorInput"  maxlength="7"/>
														
														<c:set var="hasChange" value=""/>
														<c:if test= "${demo.demoInfoChanges.phoneBusExt}">
															<c:set var="hasChange" value="hasChange"/>
														</c:if> 
														&nbsp;&nbsp;ext.
														<form:input path="demoInfo.workPhone.extention" id="workPhoneExtention" disabled="${readOnlyWorkPhone}" tabindex="19" 
																	cssStyle="width:40px" cssClass="text ${hasChange}" cssErrorClass="text errorInput"  maxlength="4"/>
													</td>
													<c:if test="${!readOnlyWorkPhone}"> 
														<td style="border-width:0px;width:95px;padding:0px;text-align:center">
															<a buttonid="revertWorkPhoneButton" tabindex="20" class="focusable hover_trigger ignore_changes" onclick="UnsavedDataWarning.disable();$('#' + this.getAttribute('buttonid')).click();" href="#" id="workPhoneButton">Undo</a>
															<input id="revertWorkPhoneButton" class="hidden" type="submit" name="_eventId_revert_work_phone"/>
														</td>
													</c:if>
												</tr>
											</c:if>
										</table>
									</div>
								</div>
							</c:if>
							</td>
						</tr>
					</table>
				</tr>
				<tr>
					<td colspan="2">
						<c:set var="readOnlyEmail" value="false"/>
						<c:if test="${demo.fieldDisplayOptionEmail == 'I'}"> 
							<c:set var="readOnlyEmail" value="true"/>
						</c:if>
					<c:if test="${demo.fieldDisplayOptionEmail !='N'}">						
						<div class="groupBox">
							<div class="groupTitle">
								Email
							</div>
							<div class="groupContent"  style="width:1220px;">
								<table style="width:100%;overflow:auto" height="90px" class="gridTable">
									<tr>
										<td style="font-weight:bold;text-align:left;border-width:0px 0px 1px 0px;width:155px;padding:0px"><ea:label value="Description"/></td>
										<td style="font-weight:bold;text-align:left;border-width:0px 0px 1px 0px;width:485px;padding:0px"><ea:label value="Current"/></td>
										<td style="font-weight:bold;text-align:left;border-width:0px 0px 1px 0px;width:480px;padding:0px"><ea:label value="New"/></td>
										<td style="font-weight:bold;text-align:center;border-width:0px 0px 1px 0px;width:95px;padding:0px"><ea:label value="Changes"/></td>
									</tr>
									<tr>
										<td style="border-width:0px; white-space:nowrap;width:155px"><ea:label value="Work E-mail Address"/></td>
										<td style="border-width:0px;white-space:nowrap;padding:0px;width:485px" ><ea:label value="${demoInfoCurrent.email.workEmail}"/></td>
										<td style="border-width:0px;white-space:nowrap;padding:0px;width:480px">
											<c:set var="hasChange" value=""/>
											<c:if test= "${demo.demoInfoChanges.emailWork}">
												<c:set var="hasChange" value="hasChange"/>
											</c:if> 
											<form:input path="demoInfo.email.workEmail" id="emailWorkEmail" cssStyle="width:480px" disabled="${readOnlyEmail}" 
														cssClass="text ${hasChange}" cssErrorClass="text errorInput" maxlength="45" tabindex="21" />
										</td>
										<c:if test="${!readOnlyEmail}"> 
											<td style="border-width:0px;text-align:center;width:95px;padding:0px">
												<a buttonid="revertEmailButton" tabindex="22" class="focusable hover_trigger ignore_changes" onclick="UnsavedDataWarning.disable();$('#' + this.getAttribute('buttonid')).click();" href="#" id="emailButton">Undo</a>
												<input id="revertEmailButton" class="hidden" type="submit" name="_eventId_revert_email"/>
											</td>
										</c:if>
									</tr>
									<tr>
										<td style="border-width:0px;white-space:nowrap;width:155px"><ea:label value="Verify E-mail Address"/></td>
										<td style="border-width:0px;white-space:nowrap;padding:0px;width:485px"></td>
										<td style="border-width:0px;white-space:nowrap;padding:0px;width:480px">
											<form:input path="demoInfo.email.workEmailVerify" id="emailWorkEmailVerify" disabled="${readOnlyEmail}" tabindex="21" 
														cssStyle="width:480px" cssClass="text" cssErrorClass="text errorInput" maxlength="45" />
										</td>
										<td style="border-width:0px;"/>
									</tr>
									<tr>
										<td style="border-width:0px;white-space:nowrap;width:155px"><ea:label value="Home E-mail Address"/></td>
										<td style="border-width:0px;white-space:nowrap;padding:0px;width:485px"><ea:label value="${demoInfoCurrent.email.homeEmail}"/></td>
										<td style="border-width:0px;white-space:nowrap;padding:0px;width:480px">
											<c:set var="hasChange" value=""/>
											<c:if test= "${demo.demoInfoChanges.emailHome}">
												<c:set var="hasChange" value="hasChange"/>
											</c:if> 
											<form:input path="demoInfo.email.homeEmail" id="emailHomeEmail" cssStyle="width:480px" disabled="${readOnlyEmail}" 
														cssClass="text ${hasChange}" cssErrorClass="text errorInput" maxlength="45" tabindex="21" />
										</td>
										<td style="border-width:0px;"/>
									</tr>
									<tr>
										<td style="border-width:0px;white-space:nowrap;width:155px"><ea:label value="Verify E-mail Address"/></td>
										<td style="border-width:0px;white-space:nowrap;padding:0px;width:485px"></td>
										<td style="border-width:0px;white-space:nowrap;padding:0px;width:480px">
											<form:input path="demoInfo.email.homeEmailVerify" id="emailHomeEmailVerify" disabled="${readOnlyEmail}" tabindex="21" 
														cssStyle="width:480px" cssClass="text" cssErrorClass="text errorInput" maxlength="45"/>
										</td>
										<td style="border-width:0px;"/>
									</tr>
								</table>
							</div>
						</div>
					</c:if>						
					</td>
				</tr>
			</table>
		<div style ="position:absolute;left:120px;top:95px;">
			<ea:button id="resetButton" formid="mainForm" onclick="UnsavedDataWarning.enable();" flowUrl="${flowExecutionUrl}" 
					   event="retrieve" tabindex="23" type="" label="Reset" checkunsaved="false" cssClass="" />
		</div>	
		<div class="save_position">
			<table>
				<tr>
					<td>
						<ea:button id="saveButton" formid="mainForm" onclick="UnsavedDataWarning.disable();" flowUrl="${flowExecutionUrl}" event="save" tabindex="23" type="" label="Save" checkunsaved="false" cssClass="default last_field" />
					</td>
				</tr>
			</table>
		</div>
	</form:form>
</div>