<table class="table responsive-table mt-3">
    <thead>
        <tr>
                <th data-localize="leaveBalance.leaveType"></th>
                <th data-localize="leaveBalance.beginningBalance"></th>
                <th data-localize="leaveBalance.advancedEarned"></th>
                <th data-localize="leaveBalance.pendingEarned"></th>
                <th data-localize="leaveBalance.used"></th>
                <th data-localize="leaveBalance.pendingUsed"> </th>
                <th data-localize="leaveBalance.available"></th>
                <th data-localize="leaveBalance.units"></th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="leave" items="${leaveInfo}" varStatus="count">
            <tr>
                <td data-localize="leaveBalance.leaveType"  data-localize-location="scope">
                        ${leave.type.code}-${leave.type.description}
                </td>
                <td data-localize="leaveBalance.beginningBalance"  data-localize-location="scope">${leave.beginBalanceLabel}</td>
                <td data-localize="leaveBalance.advancedEarned" data-localize-location="scope">${leave.advancedEarnedLabel}</td>
                <td data-localize="leaveBalance.pendingEarned" data-localize-location="scope">${leave.pendingEarnedLabel}</td>
                <td data-localize="leaveBalance.used" data-localize-location="scope">${leave.usedLabel}</td>
                <td data-localize="leaveBalance.pendingUsed" data-localize-location="scope">${leave.totalPendingUsedLabel}</td>
                <td data-localize="leaveBalance.available" data-localize-location="scope">${leave.availableBalanceLabel}</td>
                <td data-localize="leaveBalance.units" data-localize-location="scope">
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