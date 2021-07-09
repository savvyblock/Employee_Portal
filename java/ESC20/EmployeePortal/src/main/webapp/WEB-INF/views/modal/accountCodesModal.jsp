<style ype="text/css">
	.message_box input {
		margin-left: 3px;
	}
</style>
<link rel="stylesheet" href="<spring:theme code="commonPortals"/>/css/jquery-ui.css" />
<div>
	<div class="modal fade" id="accountCodesModal" tabindex="-1" role="dialog" aria-labelledby="requestModal" aria-hidden="true" data-backdrop="static">
		<div class="modal-dialog requestFormDialog">
			<div class="modal-content" style="width:1000px; max-height: 645px;">
				<div class="modal-header">
					<h4 class="modal-title new-title">Account Codes</h4>
					<button type="button" role="button" class="close closeModal" data-dismiss="modal" aria-label="${sessionScope.languageJSON.label.closeModal}">&times;</button>
				</div>
				<div >
					<table id="accountCodeSearch" class="accountTable">
						<thead>
							<tr>
								<th style="padding-left: 15px;">Fund</th>
								<th style="padding-left: 15px;">Func</th>
								<th style="padding-left: 15px;">Obj</th>
								<th style="padding-left: 15px;">Sobj</th>
								<th style="padding-left: 15px;">Org</th>
								<th colspan="6">---------------------------------------Prog---------------------------------------</th>
								<th></th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
					<table id="accountCodeInput" class="accountTable hover">
						<thead>
							<tr>
								<th style="width: 7%;"></th>
								<th style="width: 7%;"></th>
								<th style="width: 7%;"></th>
								<th style="width: 7%;"></th>
								<th style="width: 7%;"></th>
								<th style="width: 7%;"></th>
								<th style="width: 7%;"></th>
								<th style="width: 7%;"></th>
								<th style="width: 7%;"></th>
								<th style="width: 14%; text-align: center;" id="act">Account Code</th>
								<th style="text-align: center;" id="desc">Description</th>
								<th></th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
					<div id="resultLimit" /></div>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="../modal/fundsModal.jsp"%>
	<script src="/<%=request.getContextPath().split("/")[1]%>/js/plug-in/jquery-ui.js"></script>