<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div style="overflow-y: scroll; max-height: 300px;">
	<div class="modal fade" id="approvedRequestPopup" tabindex="-1" role="dialog" aria-labelledby="requestModal" aria-hidden="true" style="margin-left:-3%" data-backdrop="static">
		<div class="modal-dialog requestFormDialog" style="display: flex;width: 100%; padding: 5% 0% 0% 0%;">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title new-title">${sessionScope.languageJSON.label.finalApproval}</h4>
					<button type="button" role="button" class="close closeModal"
						data-dismiss="modal"
						aria-label="${sessionScope.languageJSON.label.closeModal}" tabindex="-1">&times;
					</button>

				</div>
				<div>
					<%@ include file="approvedRequestTable.jsp"%>
					
				</div>
				<div style="margin-left:30px" class="text-right">
					<button style="margin-right:8px" class="btn btn-primary float-right" data-dismiss="modal"  tabindex = "0" 
					onclick = "window.location.href  = window.location.href ">
							<span>OK</span>
					</button>
				
				</div>				

			</div>
		</div>
	</div>
</div>
	 	 