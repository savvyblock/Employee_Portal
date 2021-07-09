<div style="overflow-y: scroll;max-height: 300px;">
	<div class="modal fade" id="assignAccountCodesModal" tabindex="-1"
		role="dialog" aria-labelledby="requestModal" aria-hidden="true"
		style="margin-left:-3%" data-backdrop="static">
		<div class="modal-dialog requestFormDialog"
			style="display: flex;width: 100%; padding: 5% 0% 0% 0%;">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title new-title">${sessionScope.languageJSON.label.assignAccountCodes}</h4>
					<button type="button" role="button" class="close closeModal"
						data-dismiss="modal"
						aria-label="${sessionScope.languageJSON.label.closeModal}" tabindex="-1">&times;
					</button>
				</div>
				<div>
					<table id="accountCode_parentTbl"
						context-row-input="accountCode_tbl_contextRow">
						<thead>
							<tr>
								<th>Delete</th>
								<th id="accountCodeCdTH" align="center"
									style="text-decoration: underline;cursor:pointer;"><u>Account
										Code</u></th>
								<th></th>
								<th id="accountCodeDescrTH" align="center"
									style="text-decoration: underline;cursor:pointer;"><u>
										Description</u></th>
								<th id="accountCodePercentageTH" align="right"
									style="text-decoration: underline;cursor:pointer;padding-right:15px;">Percent</th>
								<th id="accountCodeAmountTH" align="right"
									style="text-decoration: underline;cursor:pointer;padding-right:15px;"><u>Amount</u></th>
							</tr>
						</thead>
						<tbody id="accountCodeBody"></tbody>
						<tfoot>
							<tr>
								<td />
								<td />
								<td />
								<td />
								<td align="right"><label class="form-title"
									id="accountCodePercentTotal"></label></td>
								<td align="right"><label class="form-title"
									id="accountCodeAmountTotal"></label></td>
							</tr>
							<tr>
								<td style="right-margin:100px;" colspan="6" align="right"><label class="form-title">Total Reimbursement: </label><label class="form-title"
									id="reimbursementTotal"></label></td>
							</tr>
							<tr>
								<td colspan="6"><hr></td>
							</tr>
							<tr>
								<td />
								<td colspan="2"><div class="float-left">
										<a tab href="#"
											style="margin-left:10px; color: #0065ff !important; text-decoration: underline"
											id="refreshTotals" tabindex = "-1"></a>
									</div>
									<div class="float-left">
										<a href="#"
											style="margin-left:10px; color: #0065ff !important; text-decoration: underline"
											id="calculatePercent" tabindex = "0">Calculate Percent</a>
									</div>
									<div class="float-left">
									<a href="#"
									style="margin-left:10px; color: #0065ff !important; text-decoration: underline"
									id="calculateAmount" tabindex = "0">Calculate Amount
									</a></div>
									</td>
								<td />
								<td></td>
                                <td tabindex = "0" class="float-right">
									<button style="cursor: pointer;" id="accountCodeAdd" type="button" role="button"
										class="a-btn add-new-row"
										aria-label="${sessionScope.languageJSON.label.add}">
										<i tabindex = "-1" class="fa fa-plus"></i> <span>${sessionScope.languageJSON.label.add}</span>
									</button>
							</tr>
							<tr>
								<td colspan="6"><hr></td>
							</tr>
							<tr>
								<td  />
								<td />
								<td />
								<td />
								<td />
								<td>
								<div style="margin-left:30px">
								<button style="margin-right:8px" class="btn btn-primary float-left" id="okAccountCodes" tabindex = "0">
										<span>OK</span>
									</button>
									</div>
									<div>
									<button class="btn btn-secondary float-right"
										data-dismiss="modal"
										id="cancelAccountCodes" tabindex = "0">
										<span>Cancel</span>
									</button>
									</div>
								</td>
							</tr>
							<tr>
							<td colspan="6"><div style="color: red"
										id="accountCodePercentTotalError"></div>
								<div style="color: red"
										id="accountCodeDuplicateError"></div>
								<div style="color: red"
									 id="amountCodeTotalError"></div>
								<div style="color: red"
									 id="amountZeroTotalError"></div></td>
							</tr>
						</tfoot>
					</table>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal -->
	</div>
</div>
<%@ include file="../modal/accountCodesModal.jsp"%>
<script>
	var enterAnEndHour = '${sessionScope.languageJSON.validator.enterAnEndHour}'
	var enterAnEndMinute = '${sessionScope.languageJSON.validator.enterAnEndMinute}'
	var enterAStartHour = '${sessionScope.languageJSON.validator.enterAStartHour}'
	var enterAStartMinute = '${sessionScope.languageJSON.validator.enterAStartMinute}'
	var isAddValue = '${addRow}'
</script>