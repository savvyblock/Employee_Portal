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
	.hasInvalid
	{
		background-color: #D0D1D3;
	}
	#content{
		padding: 34px 0 0 0;
	}
</style>

<div style="padding: 10px 10px 10px 10px">
	<tiles:insertAttribute name="error" />
	<c:if test="${empty frequencies}">
		<div class="error">
			No Payroll information is available.
		</div>
		<br/>
	</c:if>
	<c:if test="${not empty frequencies}">
	<c:if test="${not empty message}">
		<br/>
		<span style="color:red"><ea:label value="${message}"/>
		</span>
		<br/><br/>
	</c:if>
	
	<form:form id="mainForm" modelAttribute="payroll" method="post">
	
		<form:hidden path="primary" id="primaryPayroll"/>
			<table style="width:1200px;">
				<tr>
					<td style="width:100%">
						Payroll frequency:
						<c:set var="payrollFocus" value=""/>
						<c:if test="${focusedField =='retrieve' && (payroll.fieldDisplayOptionInfo !='N') && (payroll.fieldDisplayOptionInfo !='I')}">
							<c:set var="payrollFocus" value="initial_focus"/>
						</c:if>
						<form:select path="frequency" items="${frequencies}" cssClass="ignore_changes default wrap_field ${payrollFocus}" 
						onchange="document.getElementById('primaryPayroll').value = '';Spring.remoting.submitForm('frequency','mainForm',{ _eventId : 'retrieve' });"
						htmlEscape="true" tabindex="1"/>
					</td>
				</tr>
			</table>

		<c:if test="${payroll.fieldDisplayOptionInfo == 'N' && payroll.fieldDisplayOptionBank == 'N'}">
			<br/>
			<span style="color:red"><ea:label value="The Approver has not been set by the district. Please notify Business office."/></span>
			<br/>
		</c:if>
			
		<table>
			<tr>
				<c:if test="${payroll.fieldDisplayOptionInfo !='N'}">
					<td style="font-weight:bold; ">
						W4 Marital Status Information
					</td>
				</c:if>
				<c:if test="${payroll.fieldDisplayOptionBank =='U' ||  payroll.fieldDisplayOptionInfo =='U'}">
					<td style="padding-left:705px;">
						<ea:button id="resetButton" formid="mainForm" onclick="UnsavedDataWarning.enable();" flowUrl="${flowExecutionUrl}" 
									event="reset" tabindex="1" type="" label="Reset" checkunsaved="false" cssClass="" />
					</td>
				</c:if>
			</tr>
		</table>
	
		<c:set var="readOnlyInfo" value="false"/>
		<c:if test="${payroll.fieldDisplayOptionInfo =='I'}"> 
			<c:set var="readOnlyInfo" value="true"/>
		</c:if>

		<c:if test="${payroll.fieldDisplayOptionInfo !='N'}">
		<ea:Table height="70px;" width="970px;" tabindex="1" showNumberRows="false">
			<ea:Header title="Description" cssStyle="width:200px;border-right:1px solid white;"/>
			<ea:Header title="Current" cssStyle="width:300px;text-align:center;border-right:1px solid white;"/>
			<ea:Header title="New" cssStyle="width:300px;text-align:center;border-right:1px solid white;"/>
			<ea:Header title="Changes" cssStyle="width:50px;text-align:center;border-right:1px solid white;"/>
				<ea:Row id="0" doPostback="false">
					<ea:Cell value="W4 Marital Status" cssStyle="border-right:0px"/>
					<c:choose>
						<c:when test="${payroll.fieldDisplayOptionInfo =='U'}"> 
							<ea:Cell value="${payInfoCurrent.maritalStatus.displayLabel}" cssStyle="text-align:left;border-right:0px" id="maritalStatusCurrent"/>
						</c:when>
						<c:otherwise><td style="border-right:0px"></td></c:otherwise>
					</c:choose>
					<td style="border-right:0px">
						<c:if test="${payroll.fieldDisplayOptionInfo !='N'}">
							<c:set var="hasChange" value=""/>
							<c:if test= "${payroll.payInfoChanges.maritalStatusChanged}">
								<c:set var="hasChange" value="hasChange"/>
							</c:if> 
							<form:select path="payInfo.maritalStatus.displayLabel" items="${maritalStatuses}" cssClass="changeField ${hasChange}" 
							htmlEscape="true"  tabindex="1" cssStyle="width:300px" disabled="${readOnlyInfo}" id="maritalStatus" onchange="compareValues(this);"/>
						</c:if>
					</td>
					<td style="border-right:0px; text-align:center">
						<c:if test="${payroll.fieldDisplayOptionInfo =='U'}">
							<a buttonid="revertMaritalButton" tabindex="1" class="focusable hover_trigger ignore_changes" onclick="UnsavedDataWarning.disable();$('#' + this.getAttribute('buttonid')).click();" href="#" id="maritalStatusButton">Undo</a>
							<input id="revertMaritalButton" class="hidden" type="submit" name="_eventId_revert_payroll"/>
						</c:if>
					</td>
				</ea:Row>
				<ea:Row id="1" doPostback="false">
					<ea:Cell value="Nbr of Exemptions" cssStyle="border-width:0px"/>
					<c:choose>
						<c:when test="${payroll.fieldDisplayOptionInfo =='U'}"> 
							<ea:Cell value="${payInfoCurrent.numberOfExemptions}" cssStyle="text-align:left;border-width:0px" id="numberOfExemptionsCurrent"/>
						</c:when>
						<c:otherwise><td style="border-width:0px"></td></c:otherwise>
					</c:choose>
					<td style="border-width:0px">
						<c:if test="${payroll.fieldDisplayOptionInfo !='N'}">
							<c:set var="hasChange" value=""/>
							<c:if test= "${payroll.payInfoChanges.numberOfExemptionsChanged}">
								<c:set var="hasChange" value="hasChange"/>
							</c:if> 
							<form:input path="payInfo.numberOfExemptions" id="numberOfExemptions" cssClass="text changeField ${hasChange}" 
							cssErrorClass="text errorInput" maxlength="2" size="53" disabled="${readOnlyInfo}" tabindex="1"/>
						</c:if>
					</td>
					<td style="border-width:0px"></td>
				</ea:Row>
		</ea:Table>
		</c:if>
		
		
		<br/>
		<br/>

		<c:if test="${payroll.fieldDisplayOptionBank !='N'}">		
			<div style="font-weight:bold; padding-bottom:10px;">Direct Deposit Bank Accounts</div>
			<input type="hidden" name="selectedBank" id="selectedBank"/>
			<ea:Table id="bankTable" showAdd="${payroll.fieldDisplayOptionBank =='U'}" height="400px;" width="970px;"  showNumberRows="false">
				<ea:Header title="Delete" cssStyle="width:40px;border-right:1px solid white;"/>
				<ea:Header title="Primary" cssStyle="width:40px;border-right:1px solid white;"/>
				<ea:Header title="Description" cssStyle="width:80px;border-right:1px solid white;"/>
				<ea:Header title="Current" cssStyle="width:300px;text-align:center;border-right:1px solid white;"/>
				<ea:Header title="New" cssStyle="width:300px;text-align:center;border-right:1px solid white;"/>
				<ea:Header title="Changes" cssStyle="width:50px;text-align:center;border-right:1px solid white;"/>
				<c:forEach items="${accountInfoHR}" var="bankInfo" varStatus="counter">
				
					<c:if test="${accountInfoCurrent[counter.index].code.code !='' || accountInfoCurrent[counter.index].accountNumber !=''}">
						<c:set var="bankInfo" value="${accountInfoCurrent[counter.index]}"/>
					</c:if>
				
					<c:set var="readOnlyBank" value="false"/>
					<c:if test="${payroll.fieldDisplayOptionBank =='I' || bankInfo.invalidAccount}"> 
						<c:set var="readOnlyBank" value="true"/>
					</c:if>
				
					<c:set var="isDeleteImage" value=""/>
					<c:set var="isDeleteRow" value=""/>
					<form:hidden path="accountInfo[${counter.index}].isDelete" id="isDeleteHidden_${counter.index}"/>
					<c:choose>
						<c:when test="${payroll.accountInfo[counter.index].isDelete}">
							<c:set var="isDeleteImage" value="deleteRowButtonSelected"/>
							<c:set var="isDeleteRow" value="deleteRow"/>
						</c:when>
						<c:otherwise>
							<c:set var="isDeleteImage" value="deleteRowButton"/>
							<c:set var="isDeleteRow" value="unselectedRow"/>
						</c:otherwise>
					</c:choose>
					
					<c:set var="readOnlyDelete" value="true"/>
					<c:if test="${isDeleteRow == 'unselectedRow'}">
						<c:set var="readOnlyDelete" value="false"/>
					</c:if>
					
					<c:set var="hasInvalid" value=""/>
					<c:if test ="${bankInfo.invalidAccount}">
						<c:set var="hasInvalid" value="hasInvalid"/>
					</c:if>
					
					<c:set var="hideDelete" value=""/>
					<c:if test="${(bankInfo.code.code =='' && bankInfo.accountNumber ==''  && bankInfo.accountType.code =='') ||  bankInfo.invalidAccount}">
						<c:set var="hideDelete" value="hidden"/>
					</c:if>
	
					<ea:Row id="bank_${counter.index}" cssClass="${isDeleteRow} filler ${hasInvalid}" doPostback="false">
						<td style = "text-align: center; vertical-align: center;border-right:0px">
							<c:if test="${payroll.fieldDisplayOptionBank =='U'}">
								<span class="${isDeleteImage} ${hideDelete} ignore_changes" id="deleteRowButton_${counter.index}" onclick="event.cancelBubble=true;UnsavedDataWarning.disable();$('#selectedBank').val(${counter.index});Spring.remoting.submitForm(this.id, 'mainForm', { _eventId : 'delete_bankTable', controlId : this.id});"></span>
							</c:if>
						</td>
						<td style = "text-align: center; vertical-align: center;border-right:0px">
							<c:if test="${payroll.fieldDisplayOptionBank =='U' && !bankInfo.invalidAccount}">
								<input type="radio" name="primaryAccount" class="primaryRadio" tabindex = "1" id="primary_${counter.index}" value="primary_${counter.index}" > 
							</c:if>
						</td>
						<td style="text-align:left;border-right:0px">
							<c:if test="${payroll.fieldDisplayOptionBank !='N'}">
								Bank
							</c:if>
						</td> 
						<c:choose>
							<c:when test="${payroll.fieldDisplayOptionBank =='U'}"> 
								<c:choose>
									<c:when test ="${bankInfo.code.code =='' && bankInfo.code.description ==''}">
										<ea:Cell value="" cssStyle="text-align:left;border-right:0px" id="bankCodeCurrent_${counter.index}"/>
									</c:when>
									<c:otherwise>
										<ea:Cell value="${bankInfo.code.description} - ${bankInfo.code.subCode}" cssStyle="text-align:left;border-right:0px" id="bankCodeCurrent_${counter.index}"/>
									</c:otherwise>
								</c:choose>
							</c:when>
							<c:otherwise><td style="border-right:0px"></td></c:otherwise>
						</c:choose>
						<td style="border-right:0px;padding-right:0px;">
							<c:if test="${payroll.fieldDisplayOptionBank !='N'}">
								<c:set var="hasChange" value=""/>
								<c:if test= "${payroll.accountInfoChanges[counter.index].codeChanged && !bankInfo.invalidAccount}">
									<c:set var="hasChange" value="hasChange"/>
								</c:if> 
								
								<c:set var="bankFocus" value=""/>
								<c:if test="${(focusedField =='bankName' && focusedRow== counter.index) || (focusedField =='retrieve' && counter.index == 0 && (payroll.fieldDisplayOptionInfo =='I' || payroll.fieldDisplayOptionInfo =='N'))}">
									<c:set var="bankFocus" value="initial_focus"/>
								</c:if>
										
								<c:set var="bankFocusLast" value=""/>
								<c:if test="${focusedField =='bankNameLast' && fn:length(accountInfosCurrent)-1 == counter.index}">
									<c:set var="bankFocusLast" value="initial_focus"/>
								</c:if>
								
								
								<table style="margin:0px;padding:0px;width:300px;">
									<tr style="border-width:0px;margin:0px;padding:0px">
										<form:hidden path="accountInfo[${counter.index}].code.code"/>
										<form:hidden path="accountInfo[${counter.index}].code.description" id="bankDescriptionHidden_${counter.index}"/>
										<td style="border-width:0px;margin:0px;padding:0px;width:210px" id="bankDescription_${counter.index}">
											<c:out value="${payroll.accountInfo[counter.index].code.description}"/>
										</td>
										<td style="border-width:0px;margin:0px;padding:0px;text-align:right">
											<ea:DropPick path="accountInfo[${counter.index}].code.subCode" id="bankCode" codeType="bankCode" isCached="true" maxLength="15" cssClass="changeField ${hasChange} ${bankFocus} ${bankFocusLast}"
											isPickList="true" context="self_service_payroll" cssStyle="width:72px;" onEdit="$('#selectedBank').val(${counter.index});" disabled="${readOnlyBank || readOnlyDelete}" disabledField="true" tabindex="1"/>
										</td>
									</tr>
								</table>
							</c:if>
						</td>
						<td style="text-align:center">
							<c:set var="hideRevert" value=""/>
							<c:if test="${isDeleteRow != 'unselectedRow' || bankInfo.invalidAccount}">
								<c:set var="hideRevert" value="hidden"/>
							</c:if>
							<c:if test="${payroll.fieldDisplayOptionBank =='U'}">
								<a buttonid="revertBankButton" tabindex="1" class="focusable hover_trigger ignore_changes ${hideRevert}" onclick="$('#selectedBank').val(${counter.index});UnsavedDataWarning.disable();$('#' + this.getAttribute('buttonid')).click();" href="#" id="bankButton_${counter.index}">Undo</a>
								<input id="revertBankButton" class="hidden" type="submit" name="_eventId_revert_bankTable"/>
							</c:if>
						</td>
					</ea:Row>
					<ea:Row id="bank_${counter.index}" cssClass="${isDeleteRow} ${hasInvalid}" cssStyle="border-width:0px;" doPostback="false">
						<td style="border-width:0px"></td>
						<td style="border-width:0px"></td>
						<td style="text-align:left;border-width:0px">
							<c:if test="${payroll.fieldDisplayOptionBank !='N'}">
								Bank Acct Nbr
							</c:if>
						</td> 
						<c:choose>
							<c:when test="${payroll.fieldDisplayOptionBank =='U'}"> 
								<ea:Cell value="${bankInfo.accountNumber}" cssStyle="text-align:left;border-width:0px" id="bankAccountNumberCurrent_${counter.index}"/>
							</c:when>
							<c:otherwise><td style="border-width:0px"></td></c:otherwise>
						</c:choose>
						<td style="border-width:0px">
							<c:if test="${payroll.fieldDisplayOptionBank !='N'}">
								<c:set var="hasChange" value=""/>
								<c:if test= "${payroll.accountInfoChanges[counter.index].accountNumberChanged && !bankInfo.invalidAccount}">
									<c:set var="hasChange" value="hasChange"/>
								</c:if> 
								<form:input path="accountInfo[${counter.index}].accountNumber" id="bankAccountNumber_${counter.index}" cssClass="text ${hasChange} changeField" 
								cssErrorClass="text errorInput" maxlength="17" size="53" disabled="${readOnlyBank || readOnlyDelete}" tabindex="1"/>
							</c:if>
						</td>
						<td style="border-width:0px"></td>
					</ea:Row>
					<ea:Row id="bank_${counter.index}" cssClass="${isDeleteRow} ${hasInvalid}" cssStyle="border-width:0px" doPostback="false">
						<td style="border-width:0px"></td>
						<td style="border-width:0px"></td>
						<td style="text-align:left;border-width:0px">
							<c:if test="${payroll.fieldDisplayOptionBank !='N'}">
								Bank Acct Type
							</c:if>
						</td> 
						<c:choose>
							<c:when test="${payroll.fieldDisplayOptionBank =='U'}"> 
								<c:choose>
									<c:when test="${bankInfo.accountType.code =='' && bankInfo.accountType.description== ''}">
										<ea:Cell value="" id="bankAccountTypeCurrent_${counter.index}" cssStyle="text-align:left;border-width:0px"/>
									</c:when>
									<c:otherwise>
										<ea:Cell value="${bankInfo.accountType.displayLabel}" id="bankAccountTypeCurrent_${counter.index}" cssStyle="text-align:left;border-width:0px"/>
									</c:otherwise>
								</c:choose>
							</c:when>
							<c:otherwise><td style="border-width:0px"></td></c:otherwise>
						</c:choose>
						<td style="border-width:0px">
							<c:if test="${payroll.fieldDisplayOptionBank !='N'}">
								<c:set var="hasChange" value=""/>
								<c:if test= "${payroll.accountInfoChanges[counter.index].accountTypeChanged && !bankInfo.invalidAccount}">
									<c:set var="hasChange" value="hasChange"/>
								</c:if> 
								<form:select path="accountInfo[${counter.index}].accountType.displayLabel" items="${accountTypes}" 
								cssClass="changeField ${hasChange}" cssErrorClass="text errorInput" htmlEscape="true" tabindex="1" cssStyle="width:302px" 
								disabled="${readOnlyBank || readOnlyDelete}" id="bankAccountType_${counter.index}" onchange="compareValues(this);"/>
							</c:if>
						</td>
						<td style="border-width:0px"></td>
					</ea:Row>
					<ea:Row id="bank_${counter.index}" cssClass="${isDeleteRow} ${hasInvalid}" cssStyle="border-width:0px" doPostback="false">
						<td style="border-width:0px"></td>
						<td style="border-width:0px"></td>
						<td style="text-align:left;border-width:0px">
							<c:if test="${payroll.fieldDisplayOptionBank !='N'}">
								Bank Acct Amt
							</c:if>
						</td> 
						<c:choose>
							<c:when test="${payroll.fieldDisplayOptionBank =='U'}"> 
								<ea:Cell value="${bankInfo.depositAmount.displayAmount}" id="bankDepositAmountCurrent_${counter.index}" cssStyle="text-align:right;border-width:0px"/>
							</c:when>
							<c:otherwise><td style="border-width:0px"></td></c:otherwise>
						</c:choose>
						<td style="border-width:0px">
							<c:if test="${payroll.fieldDisplayOptionBank !='N'}">
								<c:set var="hasChange" value=""/>
								<c:if test= "${payroll.accountInfoChanges[counter.index].depositAmountChanged && !bankInfo.invalidAccount}">
									<c:set var="hasChange" value="hasChange"/>
								</c:if> 
								<form:input path="accountInfo[${counter.index}].depositAmount.displayAmount" id="bankDepositAmount_${counter.index}" 
								cssClass="text bankAmt changeField ${hasChange}" cssErrorClass="text errorInput bankAmt" maxlength="13" size="53" cssStyle="text-align:right" 
								disabled="${readOnlyBank || readOnlyDelete}" tabindex="1"/>
							</c:if>
						</td>
						<td style="border-width:0px"></td>
					</ea:Row>
				</c:forEach>
			</ea:Table>
		</c:if>
		
		<div class="save_position">
			<ea:button id="saveButton" formid="mainForm" onclick="UnsavedDataWarning.disable(); displayEaModalDialog(${payroll.autoApproveAccountInfo}); return false;" tabindex="1" type="" label="Save" disabled="${(payroll.fieldDisplayOptionBank =='N' || payroll.fieldDisplayOptionBank =='I') && (payroll.fieldDisplayOptionInfo =='N' || payroll.fieldDisplayOptionInfo =='I')}" cssClass="default last_field" />
		</div>
		
		<div class="modal_film_add_ea_delete hidden" style="opacity: 0.4; filter: alpha(opacity = 40); background: #000000;"></div>
		<div class="modal_contents_add_ea_delete hidden">
			<div class="message_box" style="background:	#ffffff;padding: 32px 24px 16px 24px;margin: 150px auto 0 auto;width: 400px;">
				<table class="layout">
					<tr>
						<td class="message_icon">
							<img src="/CommonWeb/images/message_box_warning.gif" />
						</td>

						<td>
							<h1>Warning!</h1>
							<p>Direct Deposit Accounts are currently set for auto approval. Continuing with save will permanently delete your account(s). 
							Are you sure you wish to delete your account(s)?</p>

							<table class="button_layout">
								<tr>
									<td>
										<ea:button id="okButton" event="save"  onclick="modalOk();"
												tabindex="1" type="tab" formid="mainForm"
												label="Ok" checkunsaved="false" springPopup="true"/>	
									</td>
									<td>
										<ea:button id="cancelButton" event="cancel"  onclick="modalCancel();"
												tabindex="1" type="tab" formid="mainForm"
												label="Cancel" checkunsaved="false"  springPopup="true"/>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<c:if test="${showPopup}">
		<div class="modal_film_add_ea_invalid modal_film" style="opacity: 0.4; filter: alpha(opacity = 40); background: #000000;"></div>
		<div class="modal_contents_add_ea_invalid modal_contents">
			<div class="message_box" style="background:	#ffffff;padding: 32px 24px 16px 24px;margin: 150px auto 0 auto;width: 400px;">
				<table class="layout">
					<tr>
						<td class="message_icon">
							<img src="/CommonWeb/images/message_box_warning.gif" />
						</td>

						<td>
							<h1>Warning!</h1>
							<p>One or more requests are no longer valid due to an account information change by an administrator. If you have any questions please contact
							your personnel office for more information.</p>

							<table class="button_layout">
								<tr>
									<td>
										<ea:button id="warningButton" event="hidePopup"
												tabindex="1" type="tab" formid="mainForm"
												label="Ok" checkunsaved="false" springPopup="true"/>	
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</div>
		</div>
		</c:if>
		
	</form:form>
	</c:if>

</div>