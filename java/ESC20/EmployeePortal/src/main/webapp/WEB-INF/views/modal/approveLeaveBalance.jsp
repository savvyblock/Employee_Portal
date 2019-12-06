<div
    class="modal fade"
    id="balanceModal"
    tabindex="-1"
    role="dialog"
    aria-labelledby="balanceModal"
    aria-hidden="true"
    data-backdrop="static"
>
    <div class="modal-dialog">
        <div class="modal-content">             
            <div class="modal-body">
                <p><b>${sessionScope.languageJSON.label.leaveBalanceSummaryFor} <span id="leaveBalanceSummaryFor"></span></b></p>
                <p>${sessionScope.languageJSON.label.payrollFreq}: <span id="payrollFreqEmp"></span></p>
                <table id="leaveBalanceDetail" class="table responsive-table mt-3 table-striped">
                    <thead>
                        <tr>
                                <th>${sessionScope.languageJSON.leaveBalance.leaveType}</th>
                                <th class="text-right">${sessionScope.languageJSON.leaveBalance.beginningBalance}</th>
                                <th class="text-right">${sessionScope.languageJSON.leaveBalance.advancedEarned}</th>
                                <th class="text-right">${sessionScope.languageJSON.leaveBalance.pendingEarned}</th>
                                <th class="text-right">${sessionScope.languageJSON.leaveBalance.used}</th>
                                <th class="text-right">${sessionScope.languageJSON.leaveBalance.pendingUsed}</th>
                                <th class="text-right">${sessionScope.languageJSON.leaveBalance.available}</th>
                                <th class="text-left">${sessionScope.languageJSON.leaveBalance.units}</th>
                        </tr>
                    </thead>
                    <tbody>
      
                    </tbody>
                </table>
            </div>
            <div class="modal-footer">
                <button
                    type="button" role="button"
                    class="btn btn-secondary"
                    data-dismiss="modal"
                    onclick="closeBalance()"
                >
                	${sessionScope.languageJSON.label.ok}
                </button>
            </div>             
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal -->
</div>

