<table class="table responsive-table mt-3 table-striped">
    <thead>
        <tr>
                <th data-localize="leaveBalance.leaveType"></th>
                <th class="text-right" data-localize="leaveBalance.beginningBalance"></th>
                <th class="text-right" data-localize="leaveBalance.advancedEarned"></th>
                <th class="text-right" data-localize="leaveBalance.pendingEarned"></th>
                <th class="text-right" data-localize="leaveBalance.used"></th>
                <th class="text-right" data-localize="leaveBalance.pendingUsed"> </th>
                <th class="text-right" data-localize="leaveBalance.available"></th>
                <th class="text-center" data-localize="leaveBalance.units"></th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="leave" items="${leaveInfo}" varStatus="count">
            <tr>
                <td data-localize="leaveBalance.leaveType"  data-localize-location="scope,data-title">
                        ${leave.type.code}-${leave.type.description}
                </td>
                <td class="text-right" data-localize="leaveBalance.beginningBalance"  data-localize-location="scope,data-title">${leave.beginBalanceLabel}</td>
                <td class="text-right" data-localize="leaveBalance.advancedEarned" data-localize-location="scope,data-title">${leave.advancedEarnedLabel}</td>
                <td class="text-right" data-localize="leaveBalance.pendingEarned" data-localize-location="scope,data-title">${leave.pendingEarnedLabel}</td>
                <td class="text-right" data-localize="leaveBalance.used" data-localize-location="scope,data-title">${leave.usedLabel}</td>
                <td class="text-right" data-localize="leaveBalance.pendingUsed" data-localize-location="scope,data-title">${leave.totalPendingUsedLabel}</td>
                <td class="text-right" data-localize="leaveBalance.available" data-localize-location="scope,data-title">
                    <span id="available${leave.type.code}">${leave.availableBalanceLabel}</span>
                </td>
                <td class="text-center" data-localize="leaveBalance.units" data-localize-location="scope,data-title">
                        <c:if test="${leave.daysHrs == 'D'}">													
                        	<span data-localize="label.days"></span>
                        </c:if> 
                        <c:if test="${leave.daysHrs == 'H'}">													
                        	<span data-localize="label.hours"></span>
                        </c:if>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>