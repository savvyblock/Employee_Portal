<div
    class="modal fade"
    id="requestModal"
    tabindex="-1"
    role="dialog"
    aria-labelledby="requestModal"
    aria-hidden="true"
    data-backdrop="static"
>
    <div class="modal-dialog requestFormDialog">
        <div class="modal-content">
            <div class="modal-header">
                
                <h4 class="modal-title new-title">${sessionScope.languageJSON.label.newRequest}</h4>
                <h4 class="modal-title edit-title">${sessionScope.languageJSON.label.editRequest}</h4>
                <h3 class="hide" id="leaveModalTitle">
                        <span class="editSpan hide">${sessionScope.languageJSON.leaveRequest.editLeaveRequestFor}</span>
                        <span class="addSpan hide">${sessionScope.languageJSON.leaveRequest.addLeaveRequestFor}</span>
                        <b id="currentEmpModal"></b>
                    </h3>
                    <button
                    type="button" role="button"
                    class="close closeModal"
                    data-dismiss="modal"
                    aria-label="${sessionScope.languageJSON.label.closeModal}">
                    &times;
                </button>
            </div>
            <form id="requestForm" action="submitLeaveRequest" method="post">
            	<input type="hidden" value="<%=token %>" name="token"/>
                <input type="hidden" name="mealBreakHours" id="mealBreakHours" value="${params.mealBreakHours}">
                <input type="hidden" name="standardHoursDaily" id="standardHoursDaily" value="${params.standardHours}">
                <input type="hidden" name="requireLeaveHoursRequestedEntry" id="requireLeaveHoursRequestedEntry" value="${params.requireLeaveHoursRequestedEntry}">
            	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <input type="hidden" hidden="hidden" name="chain" id="chainModal" aria-label="${sessionScope.languageJSON.accessHint.chain}"/>
                <input type="hidden" hidden="hidden" name="empNbr" id="empNbrModal" aria-label="${sessionScope.languageJSON.accessHint.employeeNumber}"/>
                <input type="hidden" name="freq" hidden="hidden" id="freqModal" value="${selectedFreq}" aria-label="${sessionScope.languageJSON.accessHint.frequency}"/>
                <input type="hidden" name="startDate" id="searchStartModal" hidden="hidden" aria-label="${sessionScope.languageJSON.leaveRequest.startDate}"/>
                <input type="hidden" name="endDate" id="searchEndModal" hidden="hidden" aria-label="${sessionScope.languageJSON.leaveRequest.endDate}"/>
                <div class="modal-body requestForm">
                    <input type="hidden" name="leaveId" id="leaveId" />
                    
                    <div class="line-2-flex">
                        <div class="form-group line-left">
                            <label class="form-title" for="modalLeaveType"> <span>${sessionScope.languageJSON.leaveRequest.leaveType}</span>: </label>
                            <div class="valid-wrap">
                                <select class="form-control" name="leaveType" id="modalLeaveType" onchange="changeLeaveType()">
                                        <c:forEach var="type" items="${leaveTypes}" varStatus="count">
                                            <c:if test="${type.description !='ALL'}">
                                                <option value="${type.code}" data-label="${type.subCode}">${type.description}</option>
                                            </c:if>
                                        </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group line-right">
                            <label class="form-title" for="absenceReason"><span>${sessionScope.languageJSON.leaveRequest.absenceReason}</span>: </label>
                            <div class="valid-wrap">
                                <select class="form-control" id="absenceReason" name="absenseReason">
                                        <c:forEach var="abs" items="${absRsns}" varStatus="count">
                                                <option value="${abs.code}">${abs.description}</option>
                                        </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>

                    <div class="date-group">
                        <div class="form-group calendar-left">
                            <label class="form-title" for="startDateInput"><span>${sessionScope.languageJSON.leaveRequest.startDate}</span>: </label>
                            <div class="valid-wrap">
                                <div class="fDateGroup date" id="startDate" data-date-format="mm/dd/yyyy">
										<button class="prefix" type="button" aria-label="${sessionScope.languageJSON.label.showDatepicker}"><i class="fa fa-calendar"></i></button>
                                        <input class="form-control dateInput" type="text" 
                                        name="LeaveStartDate"
                                        id="startDateInput"
                                        data-date-format="mm/dd/yyyy"  autocomplete="off"
                                        placeholder="${sessionScope.languageJSON.label.mmddyyyyFormat}"
                                        title="${sessionScope.languageJSON.label.mmddyyyyFormat}"
                                        value=""/>
							    </div>
                                <!-- <input
                                    class="form-control"
                                    type="text"
                                    name="LeaveStartDate"
                                    id="startDate"
                                    data-date-format="mm/dd/yyyy"  autocomplete="off"
                                    aria-label="${sessionScope.languageJSON.label.mmddyyyyFormat}"
                                    placeholder="${sessionScope.languageJSON.label.mmddyyyyFormat}"
                                    title="${sessionScope.languageJSON.label.mmddyyyyFormat}"
                                    value=""
                                /> -->
                            </div>
                        </div>

                        <div class="form-group time-right">
                                <label class="form-title" for="endDateInput"><span>${sessionScope.languageJSON.leaveRequest.endDate}</span>: </label>
                                <div class="valid-wrap">
                                    <div class="fDateGroup date" id="endDate" data-date-format="mm/dd/yyyy">
                                        <button class="prefix" type="button" aria-label="${sessionScope.languageJSON.label.showDatepicker}"><i class="fa fa-calendar"></i></button>
                                        <input class="form-control dateInput" type="text"
                                        name="LeaveEndDate"
                                        value=""
                                        id="endDateInput"
                                        data-date-format="mm/dd/yyyy"  autocomplete="off"
                                        placeholder="${sessionScope.languageJSON.label.mmddyyyyFormat}"
                                        title="${sessionScope.languageJSON.label.mmddyyyyFormat}"/>
                                    </div>
                                    <!-- <input
                                        class="form-control"
                                        type="text"
                                        name="LeaveEndDate"
                                        value=""
                                        id="endDate"
                                        data-date-format="mm/dd/yyyy"  autocomplete="off"
                                        aria-label="${sessionScope.languageJSON.label.mmddyyyyFormat}"
                                        placeholder="${sessionScope.languageJSON.label.mmddyyyyFormat}"
                                        title="${sessionScope.languageJSON.label.mmddyyyyFormat}"
                                    /> -->
                                </div>
                            
                        </div>
                    </div>
                    <div class="form-group has-error dateValidator01">
                        <small class="help-block" role="alert" aria-atomic="true">${sessionScope.languageJSON.validator.startNotBeGreaterThanEndDate}</small>
                    </div>
                    <div class="date-group">
                        <div class="form-group calendar-left">
                                <label class="form-title" for="startTimeValue"><span>${sessionScope.languageJSON.leaveRequest.startTime}</span>: </label>
                                <div class="valid-wrap flex-middle">
                                    <input class="form-control timeControl" type="text" name="startHour" 
                                    id="startHour" onchange="calcTime()"  autocomplete="off"
                                    onkeypress="return isHourKey(event)"
                                    aria-label="${sessionScope.languageJSON.accessHint.startHour}"/>
                                    <span class="oclock-colon">:</span>
                                    <input class="form-control timeControl" type="text" name="startMinute" 
                                    id="startMinute" onchange="calcTime()"  autocomplete="off"
                                    onkeypress="return isMinuteKey(event)"
                                    aria-label="${sessionScope.languageJSON.accessHint.startMinute}"/>
                                    <select class="form-control toAmPm" name="startAmOrPm" id="startAmOrPm" onchange="calcTime()" aria-label="${sessionScope.languageJSON.accessHint.startAmOrPm}">
                                            <option value="AM">${sessionScope.languageJSON.leaveRequest.AM}</option>
                                            <option value="PM">${sessionScope.languageJSON.leaveRequest.PM}</option>
                                    </select>
                                    <input hidden="hidden" type="text" name="startTimeValue" id="startTimeValue" >
                                </div>
                        </div>

                        <div class="form-group time-right">
                            <label class="form-title" for="endTimeValue"><span>${sessionScope.languageJSON.leaveRequest.endTime}</span>: </label>
                            <div class="valid-wrap flex-middle">
                                <input class="form-control timeControl" type="text" name="endHour" 
                                id="endHour" onchange="calcTime()"  autocomplete="off"
                                onkeypress="return isHourKey(event)"
                                aria-label="${sessionScope.languageJSON.accessHint.endHour}"/>
                                <span class="oclock-colon">:</span>
                                <input class="form-control timeControl" type="text" name="endMinute" 
                                id="endMinute" onchange="calcTime()"  autocomplete="off"
                                onkeypress="return isMinuteKey(event)"
                                aria-label="${sessionScope.languageJSON.accessHint.endMinute}"/>
                                <select class="form-control toAmPm" name="endAmOrPm" id="endAmOrPm" onchange="calcTime()" aria-label="${sessionScope.languageJSON.accessHint.endAmOrPm}">
                                    <option value="AM">${sessionScope.languageJSON.leaveRequest.AM}</option>
                                    <option value="PM">${sessionScope.languageJSON.leaveRequest.PM}</option>
                                </select>
                                <input hidden="hidden" type="text" name="endTimeValue" id="endTimeValue">
                            </div>
                            <div class="form-group has-error dateValidator">
                                    <small class="help-block" role="alert" aria-atomic="true">${sessionScope.languageJSON.validator.startNotBeGreaterThanEndTime}</small>
                                </div>
                        </div>
                    </div>
                    <!-- <div class="form-group has-error dateValidator">
                        <small class="help-block" role="alert" aria-atomic="true">${sessionScope.languageJSON.validator.startNotBeGreaterThanEndTime}</small>
                    </div> -->
                    <div class="date-group">
                            <div class="form-group calendar-left leaveHoursDailyWrap">
                                    <label class="form-title" for="leaveHoursDaily"><span>${sessionScope.languageJSON.leaveRequest.hourDayRequested}</span>: </label>
                                    <div class="valid-wrap">
                                        <input
                                            type="text"
                                            class="form-control"
                                            id="leaveHoursDaily"
                                            name="lvUnitsDaily"
                                            value="0.000"
                                            onkeyup="this.value=this.value.toString().match(/^\d+(?:\.\d{0,3})?/)"
                                        />
                                    </div>
                                    <small class="help-block leaveHoursDailyNotZero" role="alert" aria-atomic="true" style="display:none;">
                                        ${sessionScope.languageJSON.validator.enterNonZeroValue}
                                    </small>
                            </div>
    
                            <div class="form-group time-right">
                                <label class="form-title" for="totalRequested"><span>${sessionScope.languageJSON.leaveRequest.totalRequested}</span>: </label>
                                <div class="valid-wrap text-group">
                                    <input
                                        type="text"
                                        class="form-control"
                                        id="totalRequested"
                                        name="lvUnitsUsed"
                                        value="0.000"
                                        readonly
                                    />
                                    <span class="form-text timeUnit days">${sessionScope.languageJSON.leaveRequest.days}</span>
                                    <span class="form-text timeUnit hours">${sessionScope.languageJSON.leaveRequest.hours}</span>
                                </div>
                            </div>
                        </div>
                    <div class="form-group">
                        <label class="form-title" for="Remarks"><span>${sessionScope.languageJSON.leaveRequest.remark}</span>: </label>
                        <div class="valid-wrap">
                                <div class="commentsList" id="commentList">
                                        
                                    </div>
                            <textarea
                                style="height: auto;"
                                class="form-control"
                                name="Remarks"
                                id="Remarks"
                                rows="5"
                                cols="60"
                                maxlength="400"
                            ></textarea>
                        </div>
                    </div>
                    <p class="error-hint hide availableError" role="alert" aria-atomic="true">${sessionScope.languageJSON.validator.availableError}</p>
                    <p><b>${sessionScope.languageJSON.label.leaveBalanceSummary}</b></p>
                    <%@ include file="../commons/leaveBalanceTable.jsp"%>
                    <input type="hidden" name="isAdd" id="isAdd">
                </div>
                    <div class="modal-footer">
                        <button class="btn btn-primary firstSubmit save" onclick="saveRequest(true)"
                            type="button" role="button"
                            id="saveCreate"
                            name="save">
                        	${sessionScope.languageJSON.leaveBalance.submitAndAdd}
                        </button>
                        <button class="btn btn-primary firstSubmit save"  onclick="saveRequest()"
                            type="button" role="button"
                            id="saveCreate"
                            name="save">
                        	${sessionScope.languageJSON.leaveBalance.submitAndClose}
                        </button>
                        <button class="btn btn-primary secondSubmit save"  onclick="saveRequest()"
                            type="button" role="button"
                            id="saveEdit"
                            name="save">
                        	${sessionScope.languageJSON.leaveBalance.reSubmitForApproval}
                        </button>
                        <button
                            class="btn btn-secondary"
                            id="deleteLeave"
                            type="button" role="button"
                            onclick="deleteRequest()"
                        >
                        	${sessionScope.languageJSON.label.delete}
                        </button>
                        <button
                            class="btn btn-secondary closeModal"
                            data-dismiss="modal"
                            id="cancelAdd"
                            onclick="closeRequestForm()"
                        >
                        	${sessionScope.languageJSON.label.cancel}
                        </button>
                    </div>
                </form>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal -->
</div>
<script>
var leaveTypesAbsrsnsMap = eval(${leaveTypesAbsrsnsMap});
var isAddValue = '${addRow}'
var  enterNonZeroValueValidator = '${sessionScope.languageJSON.validator.enterNonZeroValue}'
</script>
<script src="/<%=request.getContextPath().split("/")[1]%>/js/viewJs/modal/eventModal.js"></script>

