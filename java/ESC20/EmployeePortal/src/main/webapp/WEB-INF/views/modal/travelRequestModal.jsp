<div class="modal fade" id="travelRequestModal" tabindex="-1"
	role="dialog" aria-labelledby="requestModal" aria-hidden="true"
	data-backdrop="static">
	<div class="modal-dialog requestFormDialog">
		<div class="modal-content" style="width:65%;">
			<div class="modal-header">
				<h4 class="modal-title new-title">${sessionScope.languageJSON.travelInfoTable.addTravelRemReq}</h4>
				<button type="button" role="button" class="close closeModal"
					data-dismiss="modal" onclick="closeRequestFormAdd()"
					aria-label="${sessionScope.languageJSON.label.closeModal}"
					tabindex="-1">&times;</button>
			</div>
			<form id="addRequestForm" action="travelRequestMapper" method="post">
				<input type="hidden" value="<%=token%>" name="token" /> <input
					type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				<input type="hidden" name="startDateTravel" id="searchStartModalTravel" /> <input
					type="hidden" name="endDate" id="searchEndModalTravel" />
				<div class="modal-body requestForm">
					<div style="width:50%;" class="date-group">
						<div
							class="form-group calendar-left dateTimePeriodOverlapWrap fromDate01">
							<label class="form-title" for="startDateInput"><span>${sessionScope.languageJSON.travelInfoTable.fromDate}</span>:</label>
							<div class="valid-wrap">
								<div class="fDateGroup date" id="startDateTravel"
									data-date-format="mm/dd/yyyy">
									<button id="startDateFocus" class="prefix startDateFocus"
										type="button"
										aria-label="${sessionScope.languageJSON.label.showDatepicker}">
										<i class="fa fa-calendar"></i>
									</button>
									<input class="form-control dateInput" type="text"
										name="fromDate" id="startDateInputTravel"
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
					<div style="width:50%;" class="date-group">
						<div
							class="form-group calendar-left dateTimePeriodOverlapWrap toDate01">
							<label class="form-title" for="endDateInput"><span>${sessionScope.languageJSON.travelInfoTable.toDate}</span>:
							</label>
							<div class="valid-wrap">
								<div class="fDateGroup date" id="endDateTravel"
									data-date-format="mm/dd/yyyy">
									<button class="prefix" type="button"
										aria-label="${sessionScope.languageJSON.label.showDatepicker}">
										<i class="fa fa-calendar"></i>
									</button>
									<input class="form-control dateInput" type="text" name="toDate"
										value="" id="endDateInputTravel" data-date-format="mm/dd/yyyy"
										autocomplete="off"
										placeholder="${sessionScope.languageJSON.label.mmddyyyyFormat}"
										title="${sessionScope.languageJSON.label.mmddyyyyFormat}" />
								</div>
							</div>
							<small class="help-block endDateBeGreaterThen2000" role="alert"
								aria-atomic="true" style="display:none">
								${sessionScope.languageJSON.validator.endDateBeGreaterThen2000}
							</small> <small class="help-block toDateIsEarlierThanFromDate"
								role="alert" aria-atomic="true" style="display:none">
								${sessionScope.languageJSON.validator.toDateIsEarlierThanFromDate}
							</small>
							<div class="form-group has-error dateValidator01">
								<small class="help-block" role="alert" aria-atomic="true">${sessionScope.languageJSON.validator.startNotBeGreaterThanEndDate}</small>
							</div>
							<input type="hidden" name="isAddTravel" id="isAddTravel">
						</div>
					</div>
					<div class="form-group line-right">
						<label class="form-title" for=""><span>${sessionScope.languageJSON.travelInfoTable.campus}</span>:
						</label>
						<div style="width:50%;" class="valid-wrap">
						<input style="background-color:rgba(239, 239, 239, 0.3)"
										class="form-control" type="text" name="payCampus"
										value="${travelRequestInfo.payCampus}" id="payCampus" disabled />
						<!-- <c:choose>
								<c:when test="${fn:length(travelcampuses) > 0}">
									<select id="PayCampuses" class="form-control"
										name="PayCampuses" autocomplete="off">
										<c:forEach var="type" items="${travelcampuses}"
											varStatus="count">
											<option value="${type.campuses}"
												<c:if test="${type.campuses == PayCampuses }">selected</c:if>>${type.campuses}</option>
										</c:forEach>
									</select>
								</c:when>
								<c:otherwise> 
								</c:otherwise>
							</c:choose>-->
						</div>
					</div>
					<div id="overnightTripShowAll" style="display:none;">
						<div class="form-group line-right">
							<label class="form-title" for="overnightTrip"><span>${sessionScope.languageJSON.travelInfoTable.overnightTrip}</span>:
								<input type="checkbox" name="overnightTrip"
								value="${travelRequestInfo.overnightTrip}" id="overnightTrip" /></label>
						</div>
						<div id="overnightTripShow" style="display:none;">
							<div style="width:70%;" class="date-group">
								<div
									class="form-group calendar-left dateTimePeriodOverlapWrap timeControlStart">
									<label class="form-title" for="addStartTime"><span>${sessionScope.languageJSON.travelInfoTable.fromTime}</span>:
									</label>
									<div class="valid-wrap flex-middle">
										<input class="form-control timeControl" type="time"
											name="addStartTime" id="addStartTime" onchange="hideDivErrorInModal()"
											autocomplete="off" />
									</div>									
									<div class="form-group has-error dateValidator01">
										<small class="help-block" role="alert" aria-atomic="true">${sessionScope.languageJSON.validator.startNotBeGreaterThanEndDate}</small>
									</div>
								</div>
							</div>
							<div style="width:70%;" class="date-group">
								<div
									class="form-group calendar-left dateTimePeriodOverlapWrap timeControlEnd">
									<label class="form-title" for="addEndTime"><span>${sessionScope.languageJSON.travelInfoTable.toTime}</span>:
									</label>
									<div class="valid-wrap flex-middle">
										<input class="form-control timeControl" type="time"
											name="addEndTime" id="addEndTime" onchange="hideDivErrorInModal()"
											autocomplete="off"/>
									<div class="form-group has-error dateValidator01">
										<small class="help-block" role="alert" aria-atomic="true">${sessionScope.languageJSON.validator.startNotBeGreaterThanEndDate}</small>
									</div>									
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer requestModalBtn">
					<div class="clearfix" style="display:inline-block;">
						<button
							class="btn btn-primary firstSubmit save pull-left submitAdd"
							onclick="saveRequestTravel(true)" type="button" role="button"
							name="save">
							${sessionScope.languageJSON.travelInfoTable.add}</button>
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
	var fromTimeRequired = '${sessionScope.languageJSON.validator.fromTimeRequired}'
	var toTimeRequired = '${sessionScope.languageJSON.validator.toTimeRequired}'
	var isAddValue = '${addRow}'
</script>
<script
	src="/<%=request.getContextPath().split("/")[1]%>/js/viewJs/travelRequest/mileageScreen.js"></script>