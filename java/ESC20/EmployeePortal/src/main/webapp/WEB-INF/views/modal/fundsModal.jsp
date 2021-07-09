<div class="modal fade" id="fundsModal" tabindex="-1" role="dialog" aria-labelledby="requestModal" aria-hidden="true" data-backdrop="static">
	<div class="modal-dialog requestFormDialog">
		<div class="modal-content" style="width:400px;">
			<div class="modal-header">
				<h4 id="fundModalTitle" class="modal-title new-title">Funds</h4>
				<button type="button" role="button" class="close closeModal" data-dismiss="modal" aria-label="${sessionScope.languageJSON.label.closeModal}">&times;</button>
			</div>
			<div style="overflow-y: scroll; max-height: 580px;">
				<table id="fundsTable" class="accountTable hover" style="width: 100%;">
					<thead>
						<tr>
							<th class="accountCodeName" style="text-align: center;">ID</th>
							<th class="accountCodeName" style="width: 100%;">Description</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>