<div
    class="modal fade"
    id="balanceModal"
    tabindex="-1"
    role="dialog"
    aria-labelledby="balanceModal"
    aria-hidden="true"
    data-backdrop="static"
>
    <div class="modal-dialog" style="max-width:800px;">
        <div class="modal-content">             
            <div class="modal-body">
                <p><b>${sessionScope.languageJSON.label.leaveBalanceSummaryFor} <span id="leaveBalanceSummaryFor"></span></b></p>
                <p>${sessionScope.languageJSON.label.payrollFreq}: <span id="payrollFreqEmp"></span></p>
                <table class="table responsive-table mt-3 table-striped">
                    <thead>
                        <tr>
                                <th>${sessionScope.languageJSON.leaveBalance.leaveType}</th>
                                <th class="text-right">${sessionScope.languageJSON.leaveBalance.beginningBalance}</th>
                                <th class="text-right">${sessionScope.languageJSON.leaveBalance.advancedEarned}</th>
                                <th class="text-right">${sessionScope.languageJSON.leaveBalance.pendingEarned}</th>
                                <th class="text-right">${sessionScope.languageJSON.leaveBalance.used}</th>
                                <th class="text-right">${sessionScope.languageJSON.leaveBalance.pendingUsed}</th>
                                <th class="text-right">${sessionScope.languageJSON.leaveBalance.available}</th>
                                <th class="text-center">${sessionScope.languageJSON.leaveBalance.units}</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="leave" items="${leaveInfo}" varStatus="count">
                            <tr>
                                <td data-title="${sessionScope.languageJSON.leaveBalance.leaveType}">
                                        ${leave.type.description}
                                </td>
                                <td class="text-right" data-title="${sessionScope.languageJSON.leaveBalance.beginningBalance}">${leave.beginBalanceLabel}</td>
                                <td class="text-right" data-title="${sessionScope.languageJSON.leaveBalance.advancedEarned}">${leave.advancedEarnedLabel}</td>
                                <td class="text-right" data-title="${sessionScope.languageJSON.leaveBalance.pendingEarned}">${leave.pendingEarnedLabel}</td>
                                <td class="text-right" data-title="${sessionScope.languageJSON.leaveBalance.used}">${leave.usedLabel}</td>
                                <td class="text-right" data-title="${sessionScope.languageJSON.leaveBalance.pendingUsed}">${leave.totalPendingUsedLabel}</td>
                                <td class="text-right" data-title="${sessionScope.languageJSON.leaveBalance.available}">
                                    <span id="available${leave.type.code}">${leave.availableBalanceLabel}</span>
                                </td>
                                <td class="text-center" data-title="${sessionScope.languageJSON.leaveBalance.units}">
                                        <c:if test="${leave.daysHrs == 'D'}">													
                                          <span>${sessionScope.languageJSON.label.days}</span>
                                        </c:if> 
                                        <c:if test="${leave.daysHrs == 'H'}">													
                                          <span>${sessionScope.languageJSON.label.hours}</span>
                                        </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="modal-footer">
                <button
                    type="button" role="button"
                    class="btn btn-secondary"
                    data-dismiss="modal"
                >
                	${sessionScope.languageJSON.label.ok}
                </button>
            </div>             
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal -->
</div>

