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
                <td data-localize="leaveBalance.leaveType">
                        ${leave.type.code}-${leave.type.description}
                </td>
                <td data-localize="leaveBalance.beginningBalance">${leave.beginBalance}</td>
                <td data-localize="leaveBalance.advancedEarned">${leave.advancedEarned}</td>
                <td data-localize="leaveBalance.pendingEarned">${leave.pendingEarned}</td>
                <td data-localize="leaveBalance.used">${leave.used}</td>
                <td data-localize="leaveBalance.pendingUsed">${leave.pendingUsed}</td>
                <td data-localize="leaveBalance.available">${leave.availableBalanceLabel}</td>
                <td data-localize="leaveBalance.units">
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