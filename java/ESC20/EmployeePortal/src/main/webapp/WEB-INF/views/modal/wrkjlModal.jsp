
<div class="modal fade" id="wrkjlModal" tabindex="-1"
	role="dialog" aria-labelledby="wrkjlModal" aria-hidden="true"
	data-backdrop="static"> 
	<div class="modal-dialog requestFormDialog">
		<div class="modal-content" style="width: 370px;">
			<div class="modal-header">
				<h4 class="modal-title new-title">${sessionScope.languageJSON.travelInfoTable.addWrkjl}</h4>
				<button type="button" role="button" class="close closeModal"
					data-dismiss="modal" onclick="closewrkjlForm()"
					aria-label="${sessionScope.languageJSON.label.closeModal}"
					tabindex="-1">&times;</button>
			</div>
			<form id="addRequestForm" action="wrkjlMapper" method="post">
			<input type="hidden" value="true" name="isNew" id="isNew"/>
				<input type="hidden" value="<%=token%>" name="token" /> <input
					type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				<input type="hidden" name="startDateWrkjl" id="startDateWrkjl" /> 
				<div class="modal-body requestForm">
					<div  class="date-group">
						<div
							class="form-group calendar-left dateTimePeriodOverlapWrap fromDate01">
							<label class="form-title" for="startDateInput"><span>${sessionScope.languageJSON.travelInfoTable.fromDate}</span>:</label>
							<div class="valid-wrap">
								<div class="fDateGroup date" id="startDateWrkjl"
									data-date-format="mm/dd/yyyy">
									<button id="startDateFocusforWrkjl" class="prefix"
										type="button"
										aria-label="${sessionScope.languageJSON.label.showDatepicker}">
										<i class="fa fa-calendar"></i>
									</button>
									<input class="form-control dateInput" type="text"
										name="fromDate" id="startDateInputWrkjl"
										data-date-format="mm/dd/yyyy" autocomplete="off"
										placeholder="${sessionScope.languageJSON.label.mmddyyyyFormat}"
										title="${sessionScope.languageJSON.label.mmddyyyyFormat}"
										value="" />
								</div>
							</div>
							<small class="help-block startDateBeGreaterThen2000" role="alert"
								aria-atomic="true" style="display:none">
								${sessionScope.languageJSON.validator.startDateBeGreaterThen2000}
							</small> <small class="help-block toDateIsEarlierThanFromDate"
								role="alert" aria-atomic="true" style="display:none">
								${sessionScope.languageJSON.validator.toDateIsEarlierThanFromDate}
							</small>
						</div>
					</div>
					 <div class="form-group line-right">
                            <label class="form-title" for="jobCode"><span>${sessionScope.languageJSON.leaveRequest.jobCode}</span>: </label>
                            <div class="valid-wrap">
                                <select class="form-control" id="jobCode" name="jobCode">
                                        <c:forEach var="job" items="${empJobCd}" varStatus="count">
                                                <option value="${job.jobCd}">${job.jobCd} ${job.jobCdDescription}</option>
                                        </c:forEach>
                                </select>
                            </div>
                       </div>
				
				
				<div >
                        <div class="form-group calendar-left dateTimePeriodOverlapWrap timeControlStart">
                                <label class="form-title" for="startTimeValue"><span>Time In</span>: </label>
                                <div class="valid-wrap flex-middle">
                                    <input class="form-control timeControl" type="text" name="startH" 
                                    id="startH"
                                     
                                      autocomplete="off"
                                    onkeypress="return isHourKey(event)"
                                    aria-label="${sessionScope.languageJSON.accessHint.startHour}"/>
                                    <span class="oclock-colon">:</span>
                                    <input class="form-control timeControl" type="text" name="startM" 
                                    id="startM"  autocomplete="off"
                                    onkeypress="return isMinuteKey(event)"
                                    aria-label="${sessionScope.languageJSON.accessHint.startMinute}"/>
                                    <select class="form-control toAmPm" name="startAmOrPm" id="startAmOrPmW" aria-label="${sessionScope.languageJSON.accessHint.startAmOrPm}">
                                            <option value="AM">${sessionScope.languageJSON.leaveRequest.AM}</option>
                                            <option value="PM">${sessionScope.languageJSON.leaveRequest.PM}</option>
                                    </select>
                                    
                                </div>
                                <div class="form-group"><input type="hidden" name="startTimeValue" id="startTimeValue" ></div>
                        </div>

                        <div class="form-group time-right dateTimePeriodOverlapWrap timeControlEnd">
                            <label class="form-title" for="endTimeValue"><span>Time Out</span>: </label>
                            <div class="valid-wrap flex-middle">
                                <input class="form-control timeControl" type="text" name="endH" 
                                id="endH"  autocomplete="off"
                                onkeypress="return isHourKey(event)"
                                aria-label="${sessionScope.languageJSON.accessHint.endHour}"/>
                                <span class="oclock-colon">:</span>
                                <input class="form-control timeControl" type="text" name="endM" 
                                id="endM"  autocomplete="off"
                                onkeypress="return isMinuteKey(event)"
                                aria-label="${sessionScope.languageJSON.accessHint.endMinute}"/>
                                <select class="form-control toAmPm" name="endAmOrPm" id="endAmOrPmW" aria-label="${sessionScope.languageJSON.accessHint.endAmOrPm}">
                                    <option value="AM">${sessionScope.languageJSON.leaveRequest.AM}</option>
                                    <option value="PM">${sessionScope.languageJSON.leaveRequest.PM}</option>
                                </select>
                                
                            </div>
                            <div class="form-group"><input type="hidden" name="endTimeValue" id="endTimeValue"></div>
                            <div class="form-group has-error dateValidator">
                                    <small class="help-block" role="alert" aria-atomic="true">${sessionScope.languageJSON.validator.startNotBeGreaterThanEndTime}</small>
                                </div>
                        </div>
                        
                        <div class="form-group">
                        <label class="form-title" for="Remarks"><span>${sessionScope.languageJSON.accessHint.comment}</span>: </label>
                        <div class="valid-wrap">
                                <div class="commentsList" id="commentList">
                                        
                                    </div>
                            <textarea
                                style="height: auto;"
                                class="form-control"
                                name="comment"
                                id="comment"
                                rows="5"
                                cols="60"
                                maxlength="400"
                            ></textarea>
                        </div>
                    </div>
					
					</div>
				</div>
				<div class="requestModalBtn" style="padding: 1rem;border-top: 1px solid #e9ecef;">
					<div style="width: 80%;float: left;">
					<div class="clearfix" style="display:inline-block;">
						<button
							class="btn btn-primary firstSubmit save pull-left submitAdd"
							onclick="saveWrkjlRequest(true)" type="button" role="button"
							name="save">
							${sessionScope.languageJSON.label.saveAdd}</button>
					</div>
					<div class="clearfix" style="display:inline-block;">
						<button
							class="btn btn-primary firstSubmit save pull-left submitAdd"
							onclick="saveWrkjlRequest()" type="button" role="button"
							name="save">
							${sessionScope.languageJSON.label.saveClose}</button>
					</div>
					</div>
					<div class="clearfix" style="display:inline-block;">
						<button
							class="btn btn-secondary firstSubmit save pull-left submitAdd"
							onclick="closewrkjlForm()" type="button" role="button"
							name="save">
							${sessionScope.languageJSON.buttons.cancel}</button>
					</div>
				</div>
			</form>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal -->
</div>
<script>
	var startAndEndBeDifferent = '${sessionScope.languageJSON.validator.startAndEndBeDifferent}'
	var enterAStartHour = '${sessionScope.languageJSON.validator.enterAStartHour}'
	var enterAnEndHour = '${sessionScope.languageJSON.validator.enterAnEndHour}'
	var enterAStartMinute = '${sessionScope.languageJSON.validator.enterAStartMinute}'
	var enterAnEndMinute = '${sessionScope.languageJSON.validator.enterAnEndMinute}'
	var isAddValue = '${addRow}'
</script>
<script
	src="/<%=request.getContextPath().split("/")[1]%>/js/viewJs/wrkjl/clockTimeScreen.js"></script>