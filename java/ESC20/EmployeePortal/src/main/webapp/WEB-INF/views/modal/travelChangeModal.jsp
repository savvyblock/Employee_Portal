<div class="modal fade"
    id="travelChangeModal"
    tabindex="-1"
    role="dialog"
    aria-labelledby="requestModal"
    aria-hidden="true"
    data-backdrop="static">
    <div class="modal-dialog requestFormDialog">
        <div class="modal-content" style="width:400px;">
            <div class="modal-header">
                <h4 class="modal-title new-title">${sessionScope.languageJSON.travelInfoTable.changeCommute}</h4>  
                    <button
                    type="button" role="button"
                    class="close closeModal"
                    data-dismiss="modal" onclick="closeRequestForm()"
                    aria-label="${sessionScope.languageJSON.label.closeModal}">
                    &times;
                </button>
            </div>
            <form id="changeRequestForm" action="travelRequest" method="post">
                <input type="hidden" value="<%=token %>" name="token"/>
            	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <input type="hidden" name="empNbr" id="empNbrModal" value="${sessionScope.userDetail.empNbr}"/>
                <div class="modal-body requestForm">
                    <div class="form-group line-right">
                            <div class="valid-wrap">
							 <input style="width:75px; text-align:right;" class="form-control"
								type="text" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');" name="changeCommuteDist" id="changeCommuteDist"
								autocomplete="off" placeholder="0.0" minlength="1" maxlength="5"/>
                            </div>
                        </div>
                </div>
                   <div class="modal-footer requestModalBtn">
					<div class="clearfix" style="display:inline-block;">
						<button
							class="btn btn-primary firstSubmit save pull-left submitAdd"
							onclick="changeRequest(true)" type="button" role="button">
							${sessionScope.languageJSON.label.save}</button>
					</div>
				</div>
				<p class="error-hint hide availableError" role="alert"
					aria-atomic="true">${sessionScope.languageJSON.validator.availableError}</p>
			</form>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal -->
</div>
<script src="/<%=request.getContextPath().split("/")[1]%>/js/viewJs/modal/travelChangeModal.js"></script>

