<div class="modal fade" id="homeModal" tabindex="-1" role="dialog"
	aria-labelledby="requestModal" aria-hidden="true"
	data-backdrop="static">
	<div class="modal-dialog requestFormDialog">
		<div class="modal-content" style="width:300px; height:fit-content;">
			<div class="modal-header">
				<h4 class="modal-title new-title">Select Request Type</h4>
				<button type="button" role="button" class="close closeModal"
					 data-dismiss="modal" onclick="closeRequestFormHomeModel()"
					aria-label="${sessionScope.languageJSON.label.closeModal}">&times;
				</button>
			</div>
			<div class="requestType">
					<table>
						<thead>
						<c:if test="${eapOption.enblLvReq == 'Y'}">
							<tr>
								<td  class="center"
									style="text-align: center; vertical-align: middle;">
									<button class="btn btn-primary closeModal"
                            			data-dismiss="modal" onclick="showLeaveRequestForm()" id="new-btn" data-target="#requestModal">
										<span>Add Leave Request</span>
									</button>
								</td>
							</tr>
							</c:if>
							<c:if test="${eapOption.enblTrvl == 'Y'}">
							<tr>
								<td  class="center"
									style="text-align: center; vertical-align: middle;"><button
										class="btn btn-primary closeModal" 
										data-dismiss="modal" onclick="showTravelRequestForm()" id="new-btn" data-target="#travelRequestModal">
										<span>Add Travel Request </span>
									</button>
								</td>
							</tr>
							</c:if>
							<c:if test="${eapOption.enblWrkjl == 'Y'}">
							<tr>
								<td  class="center"
									style="text-align: center; vertical-align: middle;"><button
										class="btn btn-primary closeModal" 
										data-dismiss="modal" onclick="showWrkjlForm()" id="new-btn" data-target="#wrkjlModal">
										<span>Add WorkJournal Request</span>
									</button>
								</td>
							</tr>  
							</c:if>
						</thead>
						<tbody>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>