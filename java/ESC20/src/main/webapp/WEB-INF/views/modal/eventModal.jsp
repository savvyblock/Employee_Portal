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
                <button
                    type="button"
                    class="close"
                    data-dismiss="modal"
                    aria-hidden="true"
                    >
                    <span class="hide" data-localize="label.closeModal"></span>
                    &times;
                </button>
                <h4 class="modal-title new-title" data-localize="label.newRequest"></h4>
                <h4 class="modal-title edit-title" data-localize="label.editRequest"></h4>
            </div>
            <form id="requestForm" action="submitLeaveRequest" method="post">
                <input type="text" hidden="hidden" name="chain" id="chainModal" title="" data-localize="accessHint.chain">
                <input type="text" hidden="hidden" name="empNbr" id="empNbrModal" title="" data-localize="accessHint.employeeNumber">
                <input type="text" name="freq" hidden="hidden" value="${selectedFreq}" title="" data-localize="accessHint.frequency">
                <div class="modal-body requestForm">
                    <input type="hidden" name="leaveId" id="leaveId" />
                    <div class="line-2-flex">
                        <div class="form-group line-left">
                            <label class="form-title" for="modalLeaveType"> <span data-localize="leaveRequest.leaveType"></span>: </label>
                            <div class="valid-wrap">
                                <select class="form-control" name="leaveType" id="modalLeaveType">
                                        <c:forEach var="type" items="${leaveTypes}" varStatus="count">
                                                <option value="${type.code}">${type.description}</option>
                                        </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group line-right">
                            <label class="form-title" for="absenceReason"><span data-localize="leaveRequest.absenceReason"></span>: </label>
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
                            <label class="form-title" for="startDate"><span data-localize="leaveRequest.startDate"></span>: </label>
                            <div class="valid-wrap">
                                <input
                                    class="form-control"
                                    type="text"
                                    name="LeaveStartDate"
                                    id="startDate"
                                    readonly
                                    value=""
                                />
                            </div>
                        </div>

                        <div class="form-group time-right">
                                <label class="form-title" for="endDate"><span data-localize="leaveRequest.endDate"></span>: </label>
                                <div class="valid-wrap">
                                    <input
                                        class="form-control"
                                        type="text"
                                        name="LeaveEndDate"
                                        value=""
                                        id="endDate"
                                        readonly
                                    />
                                </div>
                            
                        </div>
                    </div>
                    <div class="date-group">
                        <div class="form-group calendar-left">
                                <label class="form-title" for="startTimeValue"><span data-localize="leaveRequest.startTime"></span>: </label>
                                <div class="valid-wrap flex-middle">
                                    <input class="form-control timeControl" type="text" name="startHour" 
                                    id="startHour" onchange="calcTime()" 
                                    onkeypress="return isHourKey(event)"
                                    title="" data-localize="accessHint.startHour"
                                    >
                                    <span class="oclock-colon">:</span>
                                    <input class="form-control timeControl" type="text" name="startMinute" 
                                    id="startMinute" onchange="calcTime()" 
                                    onkeypress="return isMinuteKey(event)"
                                    title="" data-localize="accessHint.startMinute"
                                    >
                                    <select class="form-control toAmPm" name="startAmOrPm" id="startAmOrPm" onchange="calcTime()" title="" data-localize="accessHint.startAmOrPm">
                                            <option value="AM" data-localize="leaveRequest.AM"></option>
                                            <option value="PM" data-localize="leaveRequest.PM"></option>
                                    </select>
                                    <input hidden="hidden" type="text" name="startTimeValue" id="startTimeValue" >
                                </div>
                        </div>

                        <div class="form-group time-right">
                            <label class="form-title" for="endTimeValue"><span data-localize="leaveRequest.endTime"></span>: </label>
                            <div class="valid-wrap flex-middle">
                                <input class="form-control timeControl" type="text" name="endHour" 
                                id="endHour" onchange="calcTime()" 
                                onkeypress="return isHourKey(event)"
                                title="" data-localize="accessHint.endHour"
                                >
                                <span class="oclock-colon">:</span>
                                <input class="form-control timeControl" type="text" name="endMinute" 
                                id="endMinute" onchange="calcTime()" 
                                onkeypress="return isMinuteKey(event)"
                                title="" data-localize="accessHint.endMinute"
                                >
                                <select class="form-control toAmPm" name="endAmOrPm" id="endAmOrPm" onchange="calcTime()" title="" data-localize="accessHint.endAmOrPm">
                                    <option value="AM" data-localize="leaveRequest.AM"></option>
                                    <option value="PM" data-localize="leaveRequest.PM"></option>
                                </select>
                                <input hidden="hidden" type="text" name="endTimeValue" id="endTimeValue">
                            </div>
                        </div>
                    </div>
                    <div class="form-group has-error dateValidator">
                        <small class="help-block" data-localize="validator.startNotBeGreaterThanEndTime"></small>
                    </div>
                    <div class="date-group">
                            <div class="form-group calendar-left">
                                    <label class="form-title" for="leaveHoursDaily"><span data-localize="leaveRequest.hourDayRequested"></span>: </label>
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
                            </div>
    
                            <div class="form-group time-right">
                                <label class="form-title" for="totalRequested"><span data-localize="leaveRequest.totalRequested"></span>: </label>
                                <div class="valid-wrap text-group">
                                    <input
                                        type="text"
                                        class="form-control"
                                        id="totalRequested"
                                        name="lvUnitsUsed"
                                        value="0.000"
                                        readonly
                                    />
                                    <span class="form-text" data-localize="leaveRequest.days"></span>
                                </div>
                            </div>
                        </div>
                    <div class="form-group">
                        <label class="form-title" for="Remarks"><span data-localize="leaveRequest.remark"></span>: </label>
                        <div class="valid-wrap">
                                <div class="commentsList" id="commentList">
                                        
                                    </div>
                            <textarea
                                style="height: auto;"
                                class="form-control"
                                name="Remarks"
                                id="Remarks"
                                rows="2"
                            ></textarea>
                        </div>
                    </div>
                    <%@ include file="../commons/leaveBalanceTable.jsp"%>
                </div>
                    <div class="modal-footer">
                        <button class="btn btn-primary firstSubmit save" 
                        type="button"
                        id="saveCreate"
                        name="save" 
                        data-localize="leaveBalance.submitForApproval"></button>
                        <button class="btn btn-primary secondSubmit save" 
                        type="button"
                        id="saveEdit"
                        name="save" 
                        data-localize="leaveBalance.reSubmitForApproval"></button>
                        <button
                            class="btn btn-secondary"
                            id="deleteLeave"
                            type="button"
                            onclick="deleteRequest()"
                            data-localize="label.delete"
                        >
                        </button>
                        <button
                            class="btn btn-secondary"
                            data-dismiss="modal"
                            aria-hidden="true"
                            id="cancelAdd"
                            data-localize="label.cancel"
                            onclick="closeRequestForm()"
                        >
                        </button>
                    </div>
                </form>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal -->
</div>
<script src="/<%=request.getContextPath().split("/")[1]%>/js/viewJs/modal/eventModal.js"></script>

